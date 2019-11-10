package com.hotent.mini.controller.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanthink.base.model.DictVO;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.manager.DataDictManager;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.persistence.model.DataDict;


/**
 * 
 * <pre> 
 * 描述：数据字典管理
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miao@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/dataDict/")
public class DataDictController extends GenericController{
	public static final String DATA_DICT_CACHE_KEY = "dictDataCache";
	
	@Resource
	DataDictManager dataDictManager;
	@Resource
	SysTypeManager sysTypeManager;
	/**
	 * 数据字典列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<DataDict> dataDictList=(PageList<DataDict>)dataDictManager.query(queryFilter);
		return new PageJson(dataDictList);
	}
	
	/**
	 * 编辑数据字典信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("dataDictEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		int isAdd = RequestUtil.getInt(request, "isAdd");
		int isRoot = RequestUtil.getInt(request, "isRoot",0);
		ModelAndView mv = getAutoView();
		mv.addObject("returnUrl", preUrl).addObject("isAdd",isAdd);
		
		DataDict dataDict=null;
		// 根节点的id = typeId
		if(isRoot == 1 && isAdd == 1){
			mv.addObject("typeId", id);
			mv.addObject("parentId", id);
			return mv;
		}//普通节点添加
		else if(isAdd == 1 && StringUtil.isNotEmpty(id)){
			mv.addObject("parentId", id);
			dataDict=dataDictManager.get(id);
			mv.addObject("typeId", dataDict.getTypeId());
			return mv;
		}// 编辑情况
		else if(StringUtil.isNotEmpty(id)){
			dataDict=dataDictManager.get(id);
			return mv.addObject("dataDict", dataDict);
		}
		return mv; 
	}
	
	@RequestMapping("getByTypeId")
	public @ResponseBody List<DataDict> getByTypeId(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String typeId = RequestUtil.getString(request, "typeId");
		if(StringUtil.isEmpty(typeId)) return null;
		SysType dictType = sysTypeManager.get(typeId);
		return getDataDict(dictType,true);
	}
	
	@RequestMapping("getByTypeKey")
	@ResponseBody
	public List<DataDict> getByTypeKey(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String typeKey = RequestUtil.getString(request, "typeKey");
		if(StringUtil.isEmpty(typeKey)) return null;
		SysType dictType = sysTypeManager.getByKey(typeKey);
		
		return getDataDict(dictType,false); 
	}
	
	/**
	 * 通过typeId获取数据字典
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByTypeIdForComBo")
	@ResponseBody
	public List<DataDict> getByTypeIdForComBo(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String typeId = RequestUtil.getString(request, "typeId");
		if(StringUtil.isEmpty(typeId)) return null;
		SysType dictType = sysTypeManager.get(typeId);
		
		List<DataDict> list= getDataDict(dictType,false);
		
		List<DataDict> rtnList=BeanUtils.listToTree(list);
		return rtnList;
	}
	
	/**
	 * 通过groupKey、typeKey获取数据字典
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByTypeKeyForComBo")
	@ResponseBody
	public List<DataDict> getByTypeKeyForComBo(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String typeKey = RequestUtil.getString(request, "typeKey");
		if(StringUtil.isEmpty(typeKey)) return null;
		SysType dictType = sysTypeManager.getByTypeKeyAndGroupKey(CategoryConstants.CAT_DIC.key(), typeKey);
		List<DataDict> list= getDataDict(dictType,false);
		
		List<DataDict> rtnList=BeanUtils.listToTree(list);
		
		return rtnList;
	}
	
	
	
	/**
	 * 根据数据字典分类获取数据字典值
	 * @param dictType
	 * @param tileNeedRoot 平铺结构的数据字典是否需要返回根节点（在管理数据字典值得时候需要返回，在使用数据字典的时候不需要返回）
	 * @return
	 */
	private List<DataDict> getDataDict(SysType dictType,Boolean tileNeedRoot){
		if(BeanUtils.isEmpty(dictType))return null;
		String typeId = dictType.getId();
		List<DataDict> dataDictList=dataDictManager.getByTypeId(typeId);
		//树形结构，将根节点添加到数据项中
		if(tileNeedRoot){
			//根节点
			DataDict dict = new DataDict();
			dict.setId(dictType.getId());
			dict.setParentId("-1");
			dict.setName(dictType.getName());
			dict.setTypeId(typeId);
			dict.setKey("");
			dataDictList.add(dict);  
		}
		return dataDictList;
	}
	/**
	 * 根据数据字典分类获取数据字典值
	 * @param dictType
	 * @param tileNeedRoot 平铺结构的数据字典是否需要返回根节点（在管理数据字典值得时候需要返回，在使用数据字典的时候不需要返回）
	 * @return
	 */
	private List<DataDict> getDataDictInfo(SysType dictType,Boolean tileNeedRoot){
		if(BeanUtils.isEmpty(dictType))return null;
		String typeId = dictType.getId();
		List<DataDict> dataDictList=dataDictManager.getInfoByTypeId(typeId);
		//树形结构，将根节点添加到数据项中
		if(tileNeedRoot){
			//根节点
			DataDict dict = new DataDict();
			dict.setId(dictType.getId());
			dict.setParentId("-1");
			dict.setName(dictType.getName());
			dict.setTypeId(typeId);
			dict.setKey("");
			dataDictList.add(dict);  
		}
		return dataDictList;
	}
	
	/**
	 * 数据字典明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("dataDictGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		DataDict dataDict=null;
		if(StringUtil.isNotEmpty(id)){
			dataDict=dataDictManager.get(id);
		}
		return getAutoView().addObject("dataDict", dataDict).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存数据字典信息
	 * @param request
	 * @param response
	 * @param dataDict
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,DataDict dataDict) throws Exception{
		String resultMsg=null;
		String id=dataDict.getId();
		try {
			if(StringUtil.isEmpty(id)){
				// 验证字典key 是否已经存在
				DataDict dict= dataDictManager.getByDictKey(dataDict.getTypeId(),dataDict.getKey());
				if(dict != null){
					writeResultMessage(response.getWriter(),"该字典项值已经存在",ResultMessage.FAIL);
					return;
				}
				// 如果是root节点添加。typeId = parentId 
				dataDict.setId(UniqueIdUtil.getSuid());
				dataDictManager.create(dataDict);
				resultMsg="添加数据字典成功";
			}else{
				 // 如果改变了key ，验证改字典key 中是否已经存在
				if(!dataDictManager.get(id).getKey().equals(dataDict.getKey())){
					DataDict dict= dataDictManager.getByDictKey(dataDict.getTypeId(),dataDict.getKey());
					if(dict != null){
						writeResultMessage(response.getWriter(),"该字典项值已经存在",ResultMessage.FAIL);
						return;
					}
				}
				dataDictManager.update(dataDict);
				resultMsg="更新数据字典成功";
			}
			
			//清除对应类型缓存信息
			ICache cache = AppUtil.getBean(ICache.class);
			Map<String, List<Map<String, Object>>> dictCache = (Map<String, List<Map<String, Object>>>)cache.getByKey(DATA_DICT_CACHE_KEY);
			if(null != dictCache){
				SysType dicType = sysTypeManager.get(dataDict.getTypeId());
				if(null != dictCache.get(dicType.getTypeKey())){
					dictCache.remove(dicType.getTypeKey());
				}
			}
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除数据字典记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			
			//清除对应类型缓存信息
			ICache cache = AppUtil.getBean(ICache.class);
			Map<String, List<Map<String, Object>>> dictCache = (Map<String, List<Map<String, Object>>>)cache.getByKey(DATA_DICT_CACHE_KEY);
			if(null != dictCache){
				for(String dictId : aryIds){
					DataDict dataDict = dataDictManager.get(dictId);
					SysType dicType = sysTypeManager.get(dataDict.getTypeId());
					if(null != dictCache.get(dicType.getTypeKey())){
						dictCache.remove(dicType.getTypeKey());
					}
				}
			}
			
			dataDictManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除数据字典成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除数据字典失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	
	@RequestMapping("getDataDictByTypeId")
	@ResponseBody
	public  List<DataDict> getDataDictByType(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String typeId = RequestUtil.getString(request, "typeId");
		List<DataDict> dataDictList=dataDictManager.getByTypeId(typeId);
		
		List<DataDict> rtnList=BeanUtils.listToTree(dataDictList);
		
		return rtnList;
	}
	
	
	/**
	 * 排序列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sortList")
	public ModelAndView sortList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = RequestUtil.getString(request, "id", "-1");
		List<DataDict> dataDictList = dataDictManager.getFirstChilsByParentId(parentId);
		ModelAndView mv = this.getAutoView().addObject("dataDictList", dataDictList);
		return mv;
	}

	/**
	 * 排序
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sort")
	public void sort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] dicIds = RequestUtil.getStringAryByStr(request, "dicIds");
		if (BeanUtils.isNotEmpty(dicIds)) {
			for (int i = 0; i < dicIds.length; i++) {
				String dicId = dicIds[i];
				int sn = i + 1;
				dataDictManager.updSn(dicId, sn);
			}
		}
		ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "排序完成");
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 加载数据字典信息
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月10日 下午5:39:27
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("loadDataDict")
	public @ResponseBody PageJson loadDataDict(HttpServletRequest request,HttpServletResponse response){
		
		String typeKeyStr = RequestUtil.getString(request, "typeKeyArr", null);
		if(StringUtil.isEmpty(typeKeyStr)) {
			return null;
		}
		
		ICache cache = AppUtil.getBean(ICache.class);
		Map<String, List<Map<String, Object>>> dictCache = (Map<String, List<Map<String, Object>>>)cache.getByKey(DATA_DICT_CACHE_KEY);
		if(null == dictCache){
			dictCache = new HashMap<String, List<Map<String, Object>>>();
			cache.add(DATA_DICT_CACHE_KEY, dictCache);
		}
		
		String[] typeKeyArr = typeKeyStr.indexOf(",") > 0 ? typeKeyStr.split(",") : new String[]{typeKeyStr};
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		for(String typeKey : typeKeyArr){
			
			//先从缓存获取
			List<Map<String, Object>> cacheDictMapList = dictCache.get(typeKey);
			
			if(null != cacheDictMapList && 0 < cacheDictMapList.size()){
				dataDictList.addAll(cacheDictMapList);
			}else{
				SysType dictType = sysTypeManager.getByTypeKeyAndGroupKey(CategoryConstants.CAT_DIC.key(), typeKey);
				List<DataDict> list = getDataDict(dictType,false);
				List<Map<String, Object>> typeDataDictList = new ArrayList<Map<String, Object>>();
				for(DataDict dict : list){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("typeKey", dictType.getTypeKey());
					map.put("typeName", dictType.getName());
					map.put("valueKey", dict.getKey());
					map.put("valueName", dict.getName());
					map.put("sortNum", dict.getSn());
					typeDataDictList.add(map);
				}
				dataDictList.addAll(typeDataDictList);
				
				//添加到缓存
				dictCache.put(typeKey, typeDataDictList);
			}
			
		}
		
		return new PageJson(dataDictList);
	}
	
	/**
	 * 数据字典下拉框封装
	 * <p>return: PageJson</p>  
	 * <p>Description: DataDictController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月20日
	 * @version 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("loadDataDictJson")
	public @ResponseBody PageJson loadDataDictInfo(HttpServletRequest request,HttpServletResponse response){
		
		String typeKeyStr = RequestUtil.getString(request, "typeKeyArr", null);
		if(StringUtil.isEmpty(typeKeyStr)) {
			return null;
		}
		
		ICache cache = AppUtil.getBean(ICache.class);
		Map<String, List<Map<String, Object>>> dictCache = (Map<String, List<Map<String, Object>>>)cache.getByKey(DATA_DICT_CACHE_KEY);
		if(null == dictCache){
			dictCache = new HashMap<String, List<Map<String, Object>>>();
			cache.add(DATA_DICT_CACHE_KEY, dictCache);
		}
		
		String[] typeKeyArr = typeKeyStr.indexOf(",") > 0 ? typeKeyStr.split(",") : new String[]{typeKeyStr};
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		for(String typeKey : typeKeyArr){
			
			//先从缓存获取
			List<Map<String, Object>> cacheDictMapList = dictCache.get(typeKey);
			
			if(null != cacheDictMapList && 0 < cacheDictMapList.size()){
				dataDictList.addAll(cacheDictMapList);
			}else{
				/**
				 * 获取数据字典表填充下拉框
				 * add by linzhuo
				 * 2018/09/20
				 */
				List<DictVO> codeType = sysTypeManager.queryPubDataDictByCodeType(typeKey);
				List<Map<String, Object>> typeDataDictList = new ArrayList<Map<String, Object>>();
				for(DictVO dict : codeType){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("valueKey", dict.getValueKey());
					map.put("valueName", dict.getValueName());
					map.put("typeKey", typeKey);
					typeDataDictList.add(map);
				}
				dataDictList.addAll(typeDataDictList);
				
				//添加到缓存
				dictCache.put(typeKey, typeDataDictList);
			}
			
		}
		
		return new PageJson(dataDictList);
	}
	
	/**
	 * 数据字典下拉框封装
	 * <p>return: PageJson</p>  
	 * <p>Description: DataDictController.java</p>  
	 * @author Vondser  
	 * @date 2018年10月16日
	 * @version 1.0
	 */
	@RequestMapping("queryDataDictJson")
	public @ResponseBody List queryDataDictInfo(HttpServletRequest request,HttpServletResponse response){
		
		String typeKeyStr = RequestUtil.getString(request, "typeKeyArr", null);
		if(StringUtil.isEmpty(typeKeyStr)) {
			return null;
		}
		
		String[] typeKeyArr = typeKeyStr.indexOf(",") > 0 ? typeKeyStr.split(",") : new String[]{typeKeyStr};
		
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		for(String typeKey : typeKeyArr){
			List<DictVO> codeType = sysTypeManager.queryPubDataDictByCodeType(typeKey);
			List<Map<String, Object>> typeDataDictList = new ArrayList<Map<String, Object>>();
			for(DictVO dict : codeType){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("value", dict.getValueKey());
				map.put("label", dict.getValueName());
//					map.put("typeKey", typeKey);
				typeDataDictList.add(map);
			}
			dataDictList.addAll(typeDataDictList);
		}
		
		return dataDictList;
	}
}
