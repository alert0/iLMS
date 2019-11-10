package com.hotent.mini.controller.flow;

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
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmAgentSettingManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * 
 * <pre>
 * 描述：流程代理设置管理
 * 构建组：x5-bpmx-platform
 * 作者:zyg
 * 邮箱:zyg@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/agent/")
public class AgentController extends GenericController {
	@Resource
	BpmAgentSettingManager bpmAgentSettingManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;

	/**
	 * 流程代理设置列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("auth_id_",ContextUtil.getCurrentUserId(),QueryOP.EQUAL);
		PageList<BpmAgentSetting> bpmAgentSettingList = (PageList<BpmAgentSetting>) bpmAgentSettingManager
				.query(queryFilter);
		return new PageJson(bpmAgentSettingList);
	}
	
	@RequestMapping("listJsonMgr")
	public @ResponseBody
	PageJson listJsonMgr(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<BpmAgentSetting> bpmAgentSettingList = (PageList<BpmAgentSetting>) bpmAgentSettingManager
				.query(queryFilter);
		return new PageJson(bpmAgentSettingList);
	}

	/**
	 * 编辑流程代理设置信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("agentEdit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getEditView(request, response).addObject("isMgr", "0");
	}

	@RequestMapping("agentEditMgr")
	public ModelAndView editMgr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getEditView(request, response).addObject("isMgr", "1");
	}

	private ModelAndView getEditView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		BpmAgentSetting bpmAgentSetting = null;
		if (StringUtil.isNotEmpty(id))
			bpmAgentSetting = bpmAgentSettingManager.getById(id);

		return getAssignView("/flow/agent/agentEdit").addObject("bpmAgentSetting", bpmAgentSetting)
				.addObject("currentUserId", ContextUtil.getCurrentUserId())
				.addObject("returnUrl", preUrl); 
	}

	/**
	 * 流程代理设置列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("agentList")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getAutoView().addObject("isMgr", "0");
	}

	/**
	 * 流程代理设置列表管理员页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("agentListMgr")
	public ModelAndView listMgr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getAssignView("/flow/agent/agentList").addObject("isMgr", "1");

	}

	/**
	 * 获取流程代理设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("agentGet")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		BpmAgentSetting bpmAgentSetting = null;
		if (StringUtil.isNotEmpty(id))
			bpmAgentSetting = bpmAgentSettingManager.getById(id);
		return getAutoView().addObject("bpmAgentSetting", bpmAgentSetting);
	}

	/**
	 * 保存系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param bpmAgentSetting
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = "";
		try {
			BpmAgentSetting bpmAgentSetting = getAgentSetting(request);

			ResultMessage result = bpmAgentSettingManager
					.checkConflict(bpmAgentSetting);
			if (ResultMessage.FAIL == result.getResult()) {
				writeResultMessage(response.getWriter(), result.getMessage(),
						ResultMessage.FAIL);
				return;
			}
			String id = bpmAgentSetting.getId();
			if (StringUtil.isEmpty(id)) {
				bpmAgentSettingManager.create(bpmAgentSetting);
				resultMsg = "添加流程代理设置成功";
			} else {
				bpmAgentSettingManager.update(bpmAgentSetting);
				resultMsg = "更新流程代理设置成功";
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.ERROR);
		}
	}

	/**
	 * 批量删除系统用户记录(逻辑删除)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			bpmAgentSettingManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除流程代理设置成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除流程代理设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	private BpmAgentSetting getAgentSetting(HttpServletRequest request)
			throws Exception {
		String json = RequestUtil.getString(request, "json");
		String isMgr = RequestUtil.getString(request, "isMgr");

		BpmAgentSetting agentSetting = com.alibaba.fastjson.JSONObject
				.parseObject(json, BpmAgentSetting.class);

		IUser user = ContextUtil.getCurrentUser();
		if ("0".equals(isMgr)) { // 为普通入口的 受权 （0 受权人为自己） 否则为管理入口（1 受权人为页面所选择的人员）
			agentSetting.setAuthId(user.getUserId());
			agentSetting.setAuthName(user.getFullname());
		}
		if (BpmAgentSetting.TYPE_GLOBAL.equals(agentSetting.getType())) {
			agentSetting.getDefList().clear();
			agentSetting.getConditionList().clear();
			agentSetting.setFlowKey(null);
			agentSetting.setFlowName(null);
		} else if (BpmAgentSetting.TYPE_PART.equals(agentSetting.getType())) {
			agentSetting.getConditionList().clear();
			agentSetting.setFlowKey(null);
			agentSetting.setFlowName(null);
		} else if (BpmAgentSetting.TYPE_CONDITION
				.equals(agentSetting.getType())) {
			agentSetting.getDefList().clear();
			agentSetting.setAgentId(null);
			agentSetting.setAgent(null);
		} else {
			throw new Exception("无效代理设定");
		}
		//添加用户组织信息
		String id = agentSetting.getId();
		String iGroup = ContextUtil.getCurrentGroupId();
		if (StringUtil.isEmpty(id)) {
			agentSetting.setCreateBy(user.getUserId());
			agentSetting.setCreateOrgId(iGroup);
		} else {
			agentSetting.setUpdateBy(user.getUserId());
		}

		return agentSetting;

	}

	/**
	 * 条件代理设置对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("agentConditionDialog")
	public ModelAndView conditionDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flowKey = RequestUtil.getString(request, "flowKey");

		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager
				.getMainByDefKey(flowKey);
		
		return getAutoView().addObject("defId",bpmDefinition.getId());
	}

}
