package com.hotent.mini.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanthink.base.model.DictVO;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.manager.SysCategoryManager;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.persistence.model.SysCategory;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * 描述：总分类表。用于显示平级或树层次结构的分类，可以允许任何层次结构。管理
 * 构建组：x5-bpmx-platform
 * 作者:zyp
 * 邮箱:zyp@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/sysType/")
public class SysTypeController extends GenericController {
	@Resource
	SysTypeManager sysTypeManager;
	@Resource
	SysCategoryManager sysCategoryManager;
	@Resource
	ICache iCache;

	/**
	 * 总分类表。用于显示平级或树层次结构的分类，可以允许任何层次结构。列表(分页条件查询)数据 TODO方法名称描述
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<SysType> sysTypeList = (PageList<SysType>) sysTypeManager.query(queryFilter);
		return new PageJson(sysTypeList);
	}

	/**
	 * 编辑总分类表。用于显示平级或树层次结构的分类，可以允许任何层次结构。信息页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysTypeEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String preUrl = RequestUtil.getPrePage(request);
		// 获取主键ID
		String id = RequestUtil.getString(request, "id");
		// 是否是根节点，1=根节点，0=其他节点
		int isRoot = RequestUtil.getInt(request, "isRoot", 0);
		// 父节点ID
		String parentId = RequestUtil.getString(request, "parentId");
		// 是否是私有的节点，1=私有节点，0=普通节点
		int isPriNode = RequestUtil.getInt(request, "isPriNode", 0);

		String parentName = "";
		SysType sysType = null;
		boolean isAdd = false;
		// 是否是数据字典
		boolean isDict = false;
		if (StringUtil.isNotEmpty(id)) {
			sysType = sysTypeManager.get(id);
			parentId = sysType.getParentId();
			isDict = CategoryConstants.CAT_DIC.key().equals(sysType.getTypeGroupKey());
			if (!"0".equals(sysType.getOwnerId())) {
				isPriNode = 1;
			}
		} else {
			SysType sysTypeTemp = sysTypeManager.getInitSysType(isRoot, parentId);
			parentName = sysTypeTemp.getName();
			sysType = new SysType();
			sysType.setStruType(sysTypeTemp.getStruType());
			sysType.setTypeGroupKey(sysTypeTemp.getTypeGroupKey());
			isDict = CategoryConstants.CAT_DIC.key().equals(sysTypeTemp.getTypeGroupKey());
			isAdd = true;
		}
		return getAutoView().addObject("sysType", sysType).addObject("isAdd", isAdd).addObject("isRoot", isRoot).addObject("isAdd", isAdd).addObject("isDict", isDict).addObject("parentId", parentId).addObject("parentName", parentName).addObject("isPriNode", isPriNode).addObject("returnUrl", preUrl);
	}

	/**
	 * 系统用户信息明细页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysTypeGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		SysType sysType = null;
		if (StringUtil.isNotEmpty(id)) {
			sysType = sysTypeManager.get(id);
		}
		return getAutoView().addObject("sysType", sysType).addObject("returnUrl", preUrl);
	}

	/**
	 * 保存系统用户信息 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @param sysType
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SysType sysType) throws Exception {
		String resultMsg = null;
		String id = sysType.getId();
		String typeKey = sysType.getTypeKey();
		String typeGroupKey = sysType.getTypeGroupKey();
		// 父节点
		String parentId = RequestUtil.getString(request, "parentId");
		// 是否根节点
		int isRoot = RequestUtil.getInt(request, "isRoot", 0);
		int isPrivate = RequestUtil.getInt(request, "isPriNode", 0);
		String curUserId = ContextUtil.getCurrentUser().getUserId();

		if (StringUtil.isEmpty(id)) {
			id = null;
		}
		boolean isKeyExist = sysTypeManager.isKeyExist(id, typeGroupKey, typeKey);
		if (isKeyExist) {
			resultMsg = "输入的分类key【" + typeKey + "】已存在!";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
		} else {
			try {
				if (StringUtil.isEmpty(id)) {
					// 如果不是根节点，则需要更新分类的叶子
					if (isRoot != 1) {
						SysType parentSysType = sysTypeManager.get(parentId);
						if (BeanUtils.isNotEmpty(parentSysType)) {
							parentSysType.setIsLeaf(SysCategory.IS_LEAF_N);
							sysTypeManager.update(parentSysType);
						}
					}

					SysType sysTypeTemp = sysTypeManager.getInitSysType(isRoot, parentId);
					// 分类key不为数据字典的情况
					if (!typeGroupKey.equals(CategoryConstants.CAT_DIC.key())) {
						sysType.setStruType(sysTypeTemp.getStruType());
					}
					// 设置是否是私有的分类 0 表示公共的分类，当前用户Id表示私有的分类
					if (isPrivate == 1) {
						sysType.setOwnerId(curUserId);
					} else {
						sysType.setOwnerId("0");
					}
					sysType.setCreateBy(ContextUtil.getCurrentUserId());
					sysType.setTypeGroupKey(typeGroupKey);
					sysType.setTypeKey(typeKey);
					sysType.setPath(sysTypeTemp.getPath());
					sysType.setParentId(parentId);
					sysType.setId(sysTypeTemp.getId());
					sysType.setDepth(1);
					sysType.setSn(0);
					sysType.setIsLeaf(SysCategory.IS_LEAF_Y);
					sysTypeManager.create(sysType);
					resultMsg = "添加分类成功";
				} else {
					sysType.setSn(0);
					sysTypeManager.update(sysType);
					resultMsg = "更新分类成功";
				}
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			}
		}
	}

	/**
	 * 批量删除系统用户记录(逻辑删除) TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String id = RequestUtil.getString(request, "id");
			sysTypeManager.delByIds(id);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除分类成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除分类失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sysTypeTree")
	public ModelAndView tree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysCategory> list = sysCategoryManager.getAll();
		// 国际化
		SysCategory sysCategory = list.get(0);
		ModelAndView mv = this.getAutoView().addObject("sysCategoryList", list).addObject("sysCategory", sysCategory);

		return mv;
	}

	/**
	 * 取得分类表用于显示树层次结构的分类可以分页列表根据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByParentId")
	@ResponseBody
	public List<SysType> getByParentId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String curUserId = ContextUtil.getCurrentUser().getUserId();
		Long catId = RequestUtil.getLong(request, "catId", 0);
		SysCategory sysCategory = sysCategoryManager.get(catId.toString());
		SysType sysType = new SysType();
		sysType.setId(sysCategory.getId());
		sysType.setName(sysCategory.getName());

		sysType.setParentId("0");
		sysType.setTypeKey(sysCategory.getGroupKey());

		List<SysType> list = sysTypeManager.getByGroupKey(sysCategory.getGroupKey(), curUserId);
		list.add(0, sysType);

		return list;

	}
	@RequestMapping("updateModelType")
	@ResponseBody
	public void updateModelType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typeId = RequestUtil.getString(request, "typeId");
		String typeName = RequestUtil.getString(request, "typeName");
		String[] ids = RequestUtil.getStringAryByStr(request, "ids");
		
		String tableName = RequestUtil.getString(request, "tableName");
		String idKey = RequestUtil.getString(request, "idKey","id_");
		String typeIdKey = RequestUtil.getString(request, "typeIdKey","type_id_");
		String typeNameKey = RequestUtil.getString(request, "typeNameKey");
		
		String idStr ="";
		for (int i = 0; i < ids.length; i++) {
			idStr =idStr + "'"+ids[i]+"'";
			if(i != ids.length-1) idStr+="," ;
		}
		
		try {
			JdbcTemplate template =(JdbcTemplate) AppUtil.getBean("jdbcTemplate");
			StringBuffer sb = new StringBuffer("update "+tableName+" set ");
			sb.append(typeIdKey+" = '"+typeId+"'");
			if(StringUtil.isNotEmpty(typeNameKey)){
				sb.append(","+typeNameKey + " = '"+typeName+"' ");
			}
			
			sb.append("where "+idKey+" in("+idStr+")");
		  template.execute(sb.toString());
		  try {
			  //清除缓存
			  if(tableName.equals("bpm_definition")){
				  for(String defId:ids){
					  String key = BpmDefinition.BPM_DEFINITION + defId;
					  iCache.delByKey(key);
				  }
			  }
		} catch (Exception e) {}
		  response.getWriter().write("1"); 
		} catch (Exception e) {
			 response.getWriter().write(e.getMessage());
		}
	}
	
	
	/**
	 * 排序列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sysTypeSortList")
	public ModelAndView sortList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = RequestUtil.getString(request, "id", "-1");
		String curUserId = ContextUtil.getCurrentUser().getUserId();
		List<SysType> sysTypes = sysTypeManager.getPrivByPartId(parentId, curUserId);
		ModelAndView mv = this.getAutoView().addObject("sysTypes", sysTypes);
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
		String[] typeIds = RequestUtil.getStringAryByStr(request, "typeIds");
		if (BeanUtils.isNotEmpty(typeIds)) {
			for (int i = 0; i < typeIds.length; i++) {
				String typeId = typeIds[i];
				int sn = i + 1;
				sysTypeManager.updSn(typeId, sn);
			}
		}
		ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "分类排序完成");
		writeResultMessage(response.getWriter(), message);

	}

	@RequestMapping("getTypesByKey")
	@ResponseBody
	public List<SysType> getTypesByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typeKey = RequestUtil.getString(request, "typeKey");
		SysCategory sysCategory = sysCategoryManager.getByTypeKey(typeKey);
		// 根节点parentId = -1； 标记
		SysType sysType = new SysType();
		sysType.setId(sysCategory.getId());
		sysType.setName(sysCategory.getName());
		sysType.setParentId("-1");
		sysType.setTypeKey(sysCategory.getGroupKey());
		
		IUser user= ContextUtil.getCurrentUser();

		List<SysType> list = sysTypeManager.getByGroupKey(sysCategory.getGroupKey(), user.getUserId());
		list.add(sysType);
		
		List<SysType> rtnList= BeanUtils.listToTree(list);

		return rtnList;

	}
	
	/**
	 * easyui comBoxTreeData
	 * 
	 * @throws Exception
	 */
	@RequestMapping("getTreeDateByTypeKey")
	@ResponseBody
	public List<SysType> getTreeDateByTypeKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typeKey = RequestUtil.getString(request, "typeKey");
		List<SysType> groupTypes = null;
		if (StringUtil.isNotEmpty(typeKey)) {
			groupTypes = sysTypeManager.getChildByTypeKey(typeKey);
		}
		List<SysType> rtnList= BeanUtils.listToTree(groupTypes);
		return rtnList;
		
	}

	/**
	 * easyui comBoxTreeData eg: 通过 DIC 获取所有的字典分类
	 */
	@RequestMapping("getByGroupKey")
	@ResponseBody
	public List<SysType> getByGroupKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String groupKey = RequestUtil.getString(request, "groupKey");
		List<SysType> types = null;
		if (StringUtil.isNotEmpty(groupKey)) {
			types = sysTypeManager.getByGroupKey(groupKey, ContextUtil.getCurrentUser().getUserId());
		}
		
		List<SysType> rtnList= BeanUtils.listToTree(types);
		return rtnList;
	}

	/**
	 * 分页查询分类组数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:36:14
	 */
	@RequestMapping("querySysTypeGroup")
	public @ResponseBody PageJson querySysTypeGroup(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("GROUP_KEY_OR_NAME", RequestUtil.getDecodeString(request, "typeGroupKey"));
		PageList<Map<String, Object>> list = sysTypeManager.querySysTypeGroup(paramMap, page);
		return new PageJson(list);
	}
	
	/**
	 * 分页查询系统分类数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:47:39
	 */
	@RequestMapping("querySysTypeByGroupKey")
	public @ResponseBody PageJson querySysTypeByGroupKey(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("TYPE_GROUP_KEY", RequestUtil.getString(request, "TYPE_GROUP_KEY"));
		PageList<Map<String, Object>> list = sysTypeManager.querySysTypeByGroupKey(paramMap, page);
		return new PageJson(list);
	}
	
	/**
	 * 保存系统分类数据信息
	 * @param request
	 * @param response
	 * @param sysType
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午6:04:20
	 */
	@RequestMapping("saveSysTypeData")
	public void saveSysTypeData(HttpServletRequest request, HttpServletResponse response,@RequestBody SysType sysType) throws Exception {
		String resultMsg = null;
		String id = sysType.getId();
		String typeKey = sysType.getTypeKey();
		String typeGroupKey = sysType.getTypeGroupKey();
		String curUserId = ContextUtil.getCurrentUser().getUserId();

		if (StringUtil.isEmpty(id)) {
			id = null;
		}
		boolean isKeyExist = sysTypeManager.isKeyExist(id, typeGroupKey, typeKey);
		if (isKeyExist) {
			resultMsg = "输入的分类key【" + typeKey + "】已存在!";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
			return;
		} else {
			try {
				if (StringUtil.isEmpty(id)) {
					sysType.setCreateBy(ContextUtil.getCurrentUserId());
					sysType.setCreateTime(new Date());
					sysType.setTypeGroupKey(typeGroupKey);
					sysType.setTypeKey(typeKey);
					sysType.setPath("");
					sysType.setParentId("");
					sysType.setId(UniqueIdUtil.getSuid());
					sysType.setDepth(1);
					sysType.setStruType(SysType.STRU_TYPE_FLAT);
//					sysType.setSn(0);
					sysType.setIsLeaf(SysCategory.IS_LEAF_Y);
					sysTypeManager.create(sysType);
					resultMsg = "添加分类成功";
				} else {
//					sysType.setSn(0);
					SysType qSysType = sysTypeManager.get(id);
					qSysType.setName(sysType.getName());
					qSysType.setSn(sysType.getSn());
					qSysType.setUpdateBy(curUserId);
					qSysType.setUpdateTime(new Date());
					sysTypeManager.update(qSysType);
					resultMsg = "更新分类成功";
				}
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			}
		}
	}
	
	/**
	 * 删除系统分类数据信息
	 * @param request
	 * @param response
	 * @param sysType
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午6:12:21
	 */
	@RequestMapping("deleteSysTypeData")
	public void deleteSysTypeData(HttpServletRequest request, HttpServletResponse response,@RequestBody SysType sysType) throws Exception {
		String resultMsg = null;
		String id = sysType.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				writeResultMessage(response.getWriter(), "缺失ID，操作失败", ResultMessage.FAIL);
				return;
			} else {
				String[] idArr = id.split(",");
				for(String tid : idArr){
					sysTypeManager.remove(tid);
				}
				resultMsg = "删除分类成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 根据typeGroupKey查询该组的分类信息
	 * 支持定义返回分类的 code 和 name 的key值， 参数名为  codeKey和nameKey， 默认为 value 和 label
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 上午10:57:33
	 */
	@RequestMapping("querySysTypeByTypeGroupKey")
	public @ResponseBody List querySysTypeByTypeGroupKey(HttpServletRequest request,HttpServletResponse response){
		
		String groupKeyStr = RequestUtil.getString(request, "groupKey", null);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		if(StringUtil.isEmpty(groupKeyStr)) {
			return returnList;
		}
		
		String codeKey = "value";
		String nameKey = "label";
		
		String pageCodeKey = RequestUtil.getString(request, "codeKey");
		if(StringUtil.isNotEmpty(pageCodeKey)){
			codeKey = pageCodeKey;
		}
		String pageNameKey = RequestUtil.getString(request, "nameKey");
		if(StringUtil.isNotEmpty(pageNameKey)){
			nameKey = pageNameKey;
		}
		
		String[] groupKeyArr = groupKeyStr.indexOf(",") > 0 ? groupKeyStr.split(",") : new String[]{groupKeyStr};
		
		for(String groupKey : groupKeyArr){
			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
			queryFilter.addFilter("TYPE_GROUP_KEY_", groupKey, QueryOP.EQUAL);
			queryFilter.addFieldSort("SN_", "ASC");
			List<SysType> qList = sysTypeManager.query(queryFilter);
			List<Map<String, Object>> typeDataDictList = new ArrayList<Map<String, Object>>();
			for(SysType stype : qList){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(codeKey, stype.getTypeKey());
				map.put(nameKey, stype.getName());
				typeDataDictList.add(map);
			}
			returnList.addAll(typeDataDictList);
		}
		
		return returnList;
	}
}
