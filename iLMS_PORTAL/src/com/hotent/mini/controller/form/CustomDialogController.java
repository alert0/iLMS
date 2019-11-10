package com.hotent.mini.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.portal.persistence.model.SysIndexTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageListUtil;
import com.hotent.form.persistence.manager.CombinateDialogManager;
import com.hotent.form.persistence.manager.CustomDialogManager;
import com.hotent.form.persistence.model.CombinateDialog;
import com.hotent.form.persistence.model.CustomDialog;
import com.hotent.sys.util.SysDataSourceUtil;

/**
 * 
 * <pre>
 * 描述：自定义对话框管理
 * 构建组：x5-bpmx-platform
 * 作者:liyj_aschs
 * 邮箱:liyj_aschs@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/customDialog/")
public class CustomDialogController extends GenericController {
	@Resource
	CustomDialogManager customDialogManager;
	@Resource
	CombinateDialogManager combinateDialogManager;
//	@Resource
//	BpmDefUserManager bpmdefUserManager;
	/**
	 * 自定义对话框列表(分页条件查询)数据 TODO方法名称描述
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
		PageList<CustomDialog> customDialogList = (PageList<CustomDialog>) customDialogManager.query(queryFilter);
		return new PageJson(customDialogList);
	}

	@RequestMapping("getAll")
	@ResponseBody
	public List<CustomDialog> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<CustomDialog> customDialogs = customDialogManager.getAll();
		return customDialogs;
	}
	
	/**
	 * 返回组合对话框和自定义对话框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllDialogs")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getAllDialogs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object> customDialogs = (List)customDialogManager.getAll();
		customDialogs.addAll(combinateDialogManager.getAll());
		return customDialogs;
	}
	

	@RequestMapping("help")
	public ModelAndView help(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getAutoView();
	}

	@RequestMapping("getById")
	@ResponseBody
	public CustomDialog getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		CustomDialog customDialog = null;
		if (StringUtil.isNotEmpty(id)) {
			customDialog = customDialogManager.get(id);
		}
		return customDialog;
	}

	@RequestMapping("getByAlias")
	@ResponseBody
	public CustomDialog getByAlias(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		CustomDialog customDialog = null;
		if (StringUtil.isNotEmpty(alias)) {
			customDialog = customDialogManager.getByAlias(alias);
		}
		if(customDialog==null){
			customDialog=new CustomDialog();
		}
		return customDialog;
	}

	/**
	 * 保存自定义对话框信息 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String json = FileUtil.inputStream2String(request.getInputStream());
		CustomDialog customDialog = GsonUtil.toBean(json, CustomDialog.class);

		String resultMsg = null;
		int result = ResultMessage.SUCCESS;

		try {
			if (StringUtil.isEmpty(customDialog.getId())) {
				if (customDialogManager.getByAlias(customDialog.getAlias()) != null) {
					writeResultMessage(response.getWriter(), customDialog.getAlias() + "，已存在", ResultMessage.FAIL);
					return;
				}
				customDialog.setId(UniqueIdUtil.getSuid());
				customDialogManager.create(customDialog);
				resultMsg = "添加成功";
			} else {
				customDialogManager.update(customDialog);
				resultMsg = "更新成功";
			}
		} catch (Exception e) {
			resultMsg = "保存错误";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
			return;
		}

		writeResultMessage(response.getWriter(), resultMsg, result);
	}

	/**
	 * 批量删除自定义对话框记录
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
			String msg = "删除成功";
			int result = ResultMessage.SUCCESS;
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			for (String id : aryIds) {
				CustomDialog customDialog = customDialogManager.get(id);
				if (customDialog.getSystem() == null || customDialog.getSystem() == false) {
					customDialogManager.remove(id);
				} else {
					result = ResultMessage.FAIL;
					msg += "id:" + id + "是系统默认不能删除";
					break;
				}
			}
			message = new ResultMessage(result, msg);
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除自定义对话框失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 取得树形数据。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List getTreeData(HttpServletRequest request) throws Exception {
		Map<String, Object> param = RequestUtil.getParameterValueMap(request, false, true);

		// 先检查id，后检查alias
		String id = RequestUtil.getString(request, "id");
		String alias = RequestUtil.getString(request, "dialog_alias_");
		CustomDialog customDialog = null;

		if (StringUtil.isNotEmpty(id))
			customDialog = customDialogManager.get(id);
		if (StringUtil.isNotEmpty(alias))
			customDialog = customDialogManager.getByAlias(alias);

		if (customDialog == null) {
			return null;
		}
		if(StringUtil.isNotEmpty(alias)&&alias.contains("orgSelector")){
			String conditionfields = customDialog.getConditionfield();
			String demId = RequestUtil.getString(request, "demId");
			if(StringUtil.isNotEmpty(conditionfields)&&StringUtil.isNotEmpty(demId)){
				JSONArray condition = JSONArray.parseArray(conditionfields);
				for (Object object : condition) {
					JSONObject obj = JSONObject.parseObject(object.toString());
					if("DEM_ID_".equals(obj.getString("field"))){
						obj.put("defaultValue", demId);
						condition.remove(object);
						condition.add(obj);
						customDialog.setConditionfield(condition.toJSONString());
						break;
					}
				}
			}
		}

		// 获取数据源类型
		String dbType = SysDataSourceUtil.getDbType(customDialog.getDsalias());

		// 改变当前数据源
		DbContextHolder.setDataSource(customDialog.getDsalias(), dbType);// 转换这次进程的数据源
		String pid = getParentId(request, customDialog);
		List list = customDialogManager.geTreetData(customDialog, param, dbType);

		return list;
	}

	@RequestMapping("getListData")
	@ResponseBody
	public Object getListData(HttpServletRequest request) throws Exception {
		Boolean isMobile = RequestUtil.getBoolean(request, "isMobile");//【手机对话框】
		String jsonParam = RequestUtil.getString(request, "queryParam_");//【手机对话框】
		
		String alias = RequestUtil.getString(request, "dialog_alias_");
		CustomDialog customDialog = customDialogManager.getByAlias(alias);

		// 获取数据源类型
		String dbType = SysDataSourceUtil.getDbType(customDialog.getDsalias());
		// 改变当前数据源
		DbContextHolder.setDataSource(customDialog.getDsalias(), dbType);// 转换这次进程的数据源

		Map<String, Object> param = RequestUtil.getParameterValueMap(request, false, false);
		
		//不让param 与page等信息放在一起  【手机对话框】
		if(StringUtil.isNotEmpty(jsonParam)){
			param.putAll((Map<String,Object>) JSON.parseObject(jsonParam, Map.class));
		}
		List list = customDialogManager.getListData(customDialog, param, dbType);
//		if("sygjxz".equals(alias)){
//			List sygjxzList=new ArrayList();
//			List<String> authorizeIdsByUserMap = bpmdefUserManager.getAuthorizeIdsByUserMap(SysIndexTools.INDEX_TOOLS);
//			for (int i = 0; i <list.size() ; i++) {
//				Map map= (Map) list.get(i);
//				String toolId=map.get("ID_")+"";
//				if(authorizeIdsByUserMap.contains(toolId)){
//					sygjxzList.add(list.get(i));
//				}
//			}
//			list=sygjxzList;
//		}




		if(isMobile)return new PageJson(list);//【手机对话框】
		
		if (list instanceof PageList) {
			return PageListUtil.toJqGridMap(((PageList) list));
		} else {
			return MapUtil.buildMap("rows", list);
		}
	}

	/**
	 * 获取父级ID。
	 * 
	 * @param request
	 * @param customDialog
	 * @return
	 */
	private String getParentId(HttpServletRequest request, CustomDialog customDialog) {
		String isRoot = RequestUtil.getString(request, "isRoot");
		String pid = "";
		if (isRoot.equals("1")) {
			JSONObject jo = JSONObject.parseObject(customDialog.getDisplayfield());
			String pvalue = jo.getString("pvalue");
			String isScript = jo.getString("isScript");

			if (isScript != null && isScript.equals("true")) {// 是脚本，开始解释这段脚本
				GroovyScriptEngine groovyScriptEngine = (GroovyScriptEngine) AppUtil.getBean(GroovyScriptEngine.class);
				pid = groovyScriptEngine.executeObject(pvalue, null).toString();
			} else {
				pid = pvalue;
			}
		} else {
			String idKey = RequestUtil.getString(request, "idKey");
			pid = idKey;
		}
		return pid;
	}

	/**
	 * 手机端自定义对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mobileCustomDialog")
	public ModelAndView mobileDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Boolean isCombine = RequestUtil.getBoolean(request, "isCombine", false);
		String alias = RequestUtil.getString(request, "dialog_alias_");

		CustomDialog customDialog = null;
		CustomDialog treeDialog = null;

		try {
			if (isCombine) {
				CombinateDialog combine = combinateDialogManager.getByAlias(alias);
				treeDialog = combine.getTreeDialog();
				customDialog = combine.getListDialog();
				mv.addObject("combineField", combine.getField());
				mv.addObject("returnField", combine.getListDialog().getResultfield());
				mv.addObject("isSingle", combine.getListDialog().getSelectNum() == 1);
			} else {
				CustomDialog dialog = customDialogManager.getByAlias(alias);
				Boolean isTree = dialog.getStyle() == 1;
				if (isTree) {
					treeDialog = dialog;
					//有疑问
					String disPlayName = JSONObject.parseObject(dialog.getDisplayfield()).getString("displayName");
					mv.addObject("displayName",disPlayName.toUpperCase());
				} else
					customDialog = dialog;
				
				mv.addObject("returnField", dialog.getResultfield());
				mv.addObject("isSingle", dialog.getSelectNum() == 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// string to object
		if (customDialog != null) {
			JSONObject custDialog = (JSONObject) JSON.toJSON(customDialog);
			custDialog.put("conditionfield", JSON.parse(customDialog.getConditionfield()));
			custDialog.put("displayfield", JSON.parse(customDialog.getDisplayfield()));
			mv.addObject("listDialog", custDialog);
		}

		if (treeDialog != null) {
			mv.addObject("treeDialog", treeDialog);
		}
		
	
		mv.addObject("isCombine", isCombine);
		return mv;
	}

}
