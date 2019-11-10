package com.hotent.mini.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.helper.identity.UserQueryPluginHelper;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.org.api.model.GroupType;
import com.hotent.org.api.service.IUserGroupService;
/**
 * 用户计算控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/flow/node/usercalc/")
public class UsercalcController  extends GenericController
{
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	IUserGroupService userGroupService;
	@Resource
	private UserQueryPluginHelper userQueryPluginHelper;
//	@Resource
//	private BoDefService boDefService;
	
	/**
	 * 用户组选择框
	 */
	@RequestMapping("groupRelSelect") 
	public ModelAndView groupRelSelect(HttpServletRequest request,HttpServletResponse response,String defId,String nodeId) throws Exception{
		List<GroupType> dimensionList= userGroupService.getGroupTypes();
		return getAutoView().addObject("dimensionList",dimensionList)
				.addObject("defId", defId).addObject("nodeId", nodeId);
	}
	
	/**
	 * 用户策略选择框
	 */
	@RequestMapping("cusersSelector") 
	public ModelAndView cusersSelect(HttpServletRequest request,HttpServletResponse response,String defId,String nodeId) throws Exception{
		return getAutoView().addObject("defId", defId).addObject("nodeId", nodeId);
	}
	
	/**
	 * 角色策略选择框
	 */
	@RequestMapping("roleSelect") 
	public ModelAndView roleSelect(HttpServletRequest request,HttpServletResponse response,String defId,String nodeId) throws Exception{
		List<GroupType> dimensionList= userGroupService.getGroupTypes();
		return getAutoView().addObject("dimensionList",dimensionList)
				.addObject("defId", defId).addObject("nodeId", nodeId);
	}
	
	/**
	 * 人员脚本选择框
	 */
	@RequestMapping("hrScriptSelector") 
	public ModelAndView hrScriptSelect(HttpServletRequest request,HttpServletResponse response,String defId,String nodeId) throws Exception{
		return getAutoView().addObject("defId", defId).addObject("nodeId", nodeId);
	}
	/**
	 * 相同节点执行人
	 */
	@RequestMapping("sameNodeSelect")
	public ModelAndView conditionEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		List<BpmNodeDef> nodeDefList = bpmDefinitionAccessor.getNodeDefs(defId);
		// TODO 变量
		return getAutoView().addObject("nodeDefList", nodeDefList).addObject("defId", defId).addObject("nodeId", nodeId);
	}
	
	/**
	 * 规则设置对话框
	 */
	@RequestMapping("ruleConditionDialog") 
	public ModelAndView ruleConditionDialog(HttpServletRequest request,HttpServletResponse response,String defId,String nodeId) throws Exception{
		List<GroupType> dimensionList= userGroupService.getGroupTypes();
		return getAutoView().addObject("dimensionList",dimensionList)
				.addObject("defId", defId).addObject("nodeId", nodeId);
	}
	

}
