package com.hotent.mini.controller.flow;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmApprovalItemManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.BpmApprovalItem;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：常用语管理 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 11:27:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/flow/approvalItem/")
public class ApprovalItemController extends GenericController{
	@Resource
	BpmApprovalItemManager bpmApprovalItemManager;
	
	@Resource
	SysTypeManager sysTypeManager;

	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	/**
	 * 常用语管理列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		boolean isPersonal = RequestUtil.getBoolean(request, "isPersonal");
		List<BpmApprovalItem> bpmApprovalItemts = null;
		if(!isPersonal){
			queryFilter.addFilter("TYPE_", 4, QueryOP.NOT_EQUAL);
			bpmApprovalItemts =  bpmApprovalItemManager.query(queryFilter);
		}else {
			String currUserId = ContextUtil.getCurrentUserId();
			queryFilter.addFilter("TYPE_", 4, QueryOP.EQUAL);
			queryFilter.addFilter("USER_ID_", currUserId, QueryOP.EQUAL);
			bpmApprovalItemts =  bpmApprovalItemManager.query(queryFilter);
		}
		QueryFilter queryFilter2 = new DefaultQueryFilter();
		queryFilter2.addFilter("type_group_key_", CategoryConstants.CAT_FLOW.key(), QueryOP.EQUAL);
		List<SysType> sysTypeList = sysTypeManager.query(queryFilter2);
		List<DefaultBpmDefinition> bpmDefinitionlList = bpmDefinitionManager.getAll();
		//根据类型，把常用于的作用对象初始化
		for (BpmApprovalItem approvalItem : bpmApprovalItemts) {
			if (approvalItem.getType() == BpmApprovalItem.TYPE_FLOW ) {
				for (DefaultBpmDefinition bpmDefinition : bpmDefinitionlList) {
					if (approvalItem.getDefKey().equals(bpmDefinition.getDefKey())) {
						approvalItem.setDefKey(bpmDefinition.getName());						
					}
				}
			}else if(approvalItem.getType() == BpmApprovalItem.TYPE_FLOWTYPE) {
				for (SysType sysType : sysTypeList) {
					if (approvalItem.getTypeId().equals(sysType.getId())) {
						approvalItem.setTypeId(sysType.getName());
					}
				}
			}else {
				approvalItem.setDefKey("所有流程");
			}
		}
		PageList<BpmApprovalItem> bpmApprovalItemList=(PageList<BpmApprovalItem>)bpmApprovalItemts;
		return new PageJson(bpmApprovalItemList);
	}
	
	/**
	 * 常用语的列表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("approvalItemList")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("type_group_key_", CategoryConstants.CAT_FLOW.key(), QueryOP.EQUAL);
		String isAdmin = ContextUtil.getCurrentUserId(); 
		String currUserId = ContextUtil.getCurrentUserId();
		List<SysType> sysTypeList = sysTypeManager.query(queryFilter);
		return getAutoView().addObject("sysTypeList",sysTypeList).addObject("isAdmin", isAdmin).addObject("currUserId", currUserId);
	}
	
	/**
	 * 编辑常用语管理信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 */
	@RequestMapping("approvalItemEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		String type = RequestUtil.getString(request, "type"); 
		BpmApprovalItem bpmApprovalItem=null;
		if(StringUtil.isNotEmpty(id)){
			bpmApprovalItem=bpmApprovalItemManager.get(id);
		}
		return getAutoView().addObject("bpmApprovalItem", BeanUtils.isEmpty(bpmApprovalItem)?null:JSONObject.fromObject(bpmApprovalItem)).addObject("returnUrl", preUrl).addObject("type", type);
	}
	
	/**
	 * 常用语管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("approvalItemGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		BpmApprovalItem bpmApprovalItem=null;
		if(StringUtil.isNotEmpty(id)){
			bpmApprovalItem=bpmApprovalItemManager.get(id);
		}
		return getAutoView().addObject("bpmApprovalItem", bpmApprovalItem).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存常用语管理信息
	 * @param request
	 * @param response
	 * @param bpmApprovalItem
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		String approvalItem = RequestUtil.getString(request, "approval");
		BpmApprovalItem bpmApprovalItem = (BpmApprovalItem) JSONObject.toBean(JSONObject.fromObject(approvalItem), BpmApprovalItem.class);
		try {
			String dealType = StringUtil.isNotEmpty(bpmApprovalItem.getId())?"编辑":"添加";
			bpmApprovalItemManager.addTaskApproval(bpmApprovalItem);
			resultMsg= dealType+"常用语成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对常用语操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除常用语管理记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			bpmApprovalItemManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除常用语成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除常用语失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
