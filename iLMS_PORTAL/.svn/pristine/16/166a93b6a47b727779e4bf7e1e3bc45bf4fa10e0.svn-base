package com.hotent.mini.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.api.service.FlowStatusService;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;

/**
 * 流程图处理
 * @author 
 *
 */
public class BpmImageServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String defId = request.getParameter("defId");
		String bpmnInstId = request.getParameter("bpmnInstId");
		String taskId = request.getParameter("taskId");
		String runId = request.getParameter("runId");
		
		DiagramService diagramService = (DiagramService) AppUtil.getBean("diagramService");
		FlowStatusService flowStatusService = (FlowStatusService) AppUtil.getBean("flowStatusService");
		
		InputStream is = null;
		if (StringUtils.isNotEmpty(defId)) {
			is = diagramService.getDiagramByBpmnDefId(defId);
		} else if (StringUtils.isNotEmpty(bpmnInstId)) {
			is = getDiagramByInstance(diagramService, flowStatusService, bpmnInstId);
		} else if (StringUtils.isNotEmpty(taskId)) {
			BpmTaskService bpmTaskService = (BpmTaskService) AppUtil .getBean("defaultBpmTaskService");
			BpmTask bpmTask = bpmTaskService.getByTaskId(taskId);
			is = getDiagramByInstance(diagramService, flowStatusService, bpmTask.getBpmnInstId());
		} else if (StringUtils.isNotEmpty(runId)){
			BpmInstService instService = AppUtil.getBean(BpmInstService.class);
			BpmProcessInstance instance = instService.getProcessInstance(runId);
			is = getDiagramByInstance(diagramService, flowStatusService, instance.getBpmnInstId());
		}

		if (is == null)
			return;
		response.setContentType("image/png");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			byte[] bs = new byte[1024];
			int n = 0;
			while ((n = is.read(bs)) != -1) {
				out.write(bs, 0, n);
			}
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (out != null)
				out.close();
		}
	}

	private InputStream getDiagramByInstance(DiagramService diagramService,
			FlowStatusService flowStatusService, String bpmnInstId) {
		BpmInstService bpmInstService = AppUtil.getBean(BpmInstService.class);
		BpmProcessInstanceManager bpmProcessInstanceManager = AppUtil.getBean(BpmProcessInstanceManager.class);
		BpmProcessInstance bpmProcessInstance = bpmInstService
				.getProcessInstanceByBpmnInstId(bpmnInstId);
		if(bpmProcessInstance==null)
			bpmProcessInstance=bpmProcessInstanceManager.getBpmProcessInstanceHistoryByBpmnInstId(bpmnInstId);
		Map<String, String> colorMap = flowStatusService
				.getProcessInstanceStatus(bpmProcessInstance.getId());
		return diagramService.getDiagramByDefId(
				bpmProcessInstance.getProcDefId(), colorMap);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
