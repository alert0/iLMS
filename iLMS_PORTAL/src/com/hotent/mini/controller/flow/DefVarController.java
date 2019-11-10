package com.hotent.mini.controller.flow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.core.engine.def.impl.handler.VarDefBpmDefXmlHandler;
import com.hotent.bpmx.core.model.var.DefaultBpmVariableDef;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;

/**
 * 
 * <pre>
 * 描述：流程变量管理
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2014-7-22
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/var/")
public class DefVarController extends GenericController {
	@Resource
	VarDefBpmDefXmlHandler varDefBpmDefXmlHandler;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;

	/**
	 * 流程变量列表数据
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String nodeId = RequestUtil.getString(request, "nodeId");
		String defId = RequestUtil.getString(request, "defId");
		List<BpmVariableDef> bpmVariableList = new ArrayList<BpmVariableDef>();

		if (StringUtil.isNotEmpty(nodeId) && StringUtil.isNotEmpty(defId)) {
			UserTaskNodeDef taskNodeDef = (UserTaskNodeDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
			bpmVariableList = taskNodeDef.getVariableList();
		} else {
			if (StringUtil.isNotEmpty(defId))
				bpmVariableList = getAllBpmVariableDef(defId);
		}
		return new PageJson(bpmVariableList);
	}

	/**
	 * 流程变量列表数据
	 */
	@RequestMapping("defVarlist")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String defId = RequestUtil.getString(request, "defId");

		return this.getAutoView().addObject("defId", defId);
	}

	/**
	 * 所有的变量
	 */
	private List<BpmVariableDef> getAllBpmVariableDef(String defId) {
		List<BpmVariableDef> bpmVariableList = new ArrayList<BpmVariableDef>();
		// 全局变量
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		if (defExt.getVariableList() != null)
			bpmVariableList.addAll(defExt.getVariableList());

		// 节点变量
		List<BpmNodeDef> bpmNodeDefList = bpmDefinitionAccessor.getNodesByType(defId, NodeType.USERTASK);
		bpmNodeDefList.addAll(bpmDefinitionAccessor.getNodesByType(defId, NodeType.SIGNTASK));

		for (BpmNodeDef bpmNodeDef : bpmNodeDefList) {
			UserTaskNodeDef taskNodeDef = (UserTaskNodeDef) bpmNodeDef;
			List<BpmVariableDef> nodeVarList = taskNodeDef.getVariableList();
			if (nodeVarList != null)
				bpmVariableList.addAll(nodeVarList);
		}

		return bpmVariableList;
	}

	/**
	 * 编辑节点规则
	 */
	@RequestMapping("defVarEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String varKey = RequestUtil.getString(request, "varKey");
		String defId = RequestUtil.getString(request, "defId");

		List<BpmNodeDef> nodeDefList = bpmDefinitionAccessor.getNodeDefs(defId);
		List<BpmVariableDef> list = getAllBpmVariableDef(defId);
		BpmVariableDef bpmVariableDef = null;

		for (BpmVariableDef varDef : list) {
			if (varKey.equals(varDef.getVarKey())) {
				bpmVariableDef = varDef;
			}
		}
		return getAutoView().addObject("bpmVariableDef", bpmVariableDef).addObject("nodeDefList", nodeDefList).addObject("defId", defId);
	}

	/**
	 * 删除节点变量
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String varKey = RequestUtil.getString(request, "varKey");
		try {
			List<BpmVariableDef> list = getAllBpmVariableDef(defId);
			List<BpmVariableDef> bpmVariableDefs = new ArrayList<BpmVariableDef>();

			for (BpmVariableDef varDef : list) {
				if (!varKey.equals(varDef.getVarKey())) {
					bpmVariableDefs.add(varDef);
				}
			}
			varDefBpmDefXmlHandler.saveNodeXml(defId, null, bpmVariableDefs);
			writeResultMessage(response.getWriter(), "删除成功", 1);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "删除失败", 0);
		}
	}

	/**
	 * 保存节点规则
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, DefaultBpmVariableDef variableDef) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		boolean isAdd = RequestUtil.getBoolean(request, "isAdd");
		boolean isRequired = RequestUtil.getBoolean(request, "isRequired");
		String varKey = variableDef.getVarKey();
		try {
			List<BpmVariableDef> list = getAllBpmVariableDef(defId);
			List<BpmVariableDef> bpmVariableDefs = new ArrayList<BpmVariableDef>();
			// 修改，过滤掉旧数据。 新增情况存在相同key 抛出异常。
			for (BpmVariableDef varDef : list) {
				if (varKey.equals(varDef.getVarKey())) {
					if (isAdd)
						throw new Exception("变量Key必须唯一！");
				} else {
					bpmVariableDefs.add(varDef);
				}
			}
			variableDef.setRequired(isRequired);
			bpmVariableDefs.add(variableDef);

			varDefBpmDefXmlHandler.saveNodeXml(defId, null, bpmVariableDefs);
			writeResultMessage(response.getWriter(), "操作成功！", ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "操作失败！", e.getMessage(), ResultMessage.FAIL);
		}
	}

}
