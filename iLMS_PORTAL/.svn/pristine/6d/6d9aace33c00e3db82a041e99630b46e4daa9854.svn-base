<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript">
	$(function() {
		if(!(window.navigator.userAgent.indexOf("Chrome") !== -1)){  
			$("#viewTaskTabs").tabs({
				border:false,
				onSelect:function(title){
					var tabsHeight = $("#viewTaskTabs").height()-50;
					$(".panel-body.panel-body-noheader.panel-body-noborder").height(tabsHeight);
					$('#frameImage').height(tabsHeight);
				}
			});
		}
	}); 
	var taskId = "${bpmTask.id}";

	function setExecutors() {
		var def = {
			title : "设置任务执行人",
			width : 600,
			modal : true,
			resizable : true,
			iconCls : 'fa fa-table'
		};

		def.passConf = {
			taskId : taskId
		};

		$.topCall.dialog({
			src : __ctx + '/flow/task/taskToSetExecutors?taskId=' + taskId,
			base : def
		});
	}
	function refresh() {
		location.reload();
	}
	
	function loadThisIframe(obj){
	 	var bodyHeight = $("body");
	 	obj.height = bodyHeight.height();
		$(obj).parent().css("height",obj.height);
	}
	
</script>
</head>
<body class="easyui-layout">

		<div class="toolbar-panel ">
			<div class="buttons">
				<button onclick="HT.window.closeEdit();" class="btn btn-primary btn-sm fa-close">
					<span>关闭</span>
				</button>

				<button onclick="setExecutors();" class="btn btn-success btn-sm fa-users">
					<span>更改执行人</span>
				</button>
			</div>
		</div>

		<div id="viewTaskTabs" class="easyui-tabs" data-options="border:false" style="fit: true; padding-left: 5px; height: 100%; overflow: auto;">
			
			<div title="任务信息" style="padding: 2px; overflow-y: auto;" data-options="iconCls:'bigger-120 fa fa-cog'">
				<table cellspacing="0"  class="table-form">
					<tr>
						<th><span>任务名称:</span></th>
						<td>${bpmTask.name}</td>
						<th><span>待办事项标题:</span></th>
						<td>${bpmTask.subject}</td>
					</tr>
					<tr>
						<th><span>流程实例ID:</span></th>
						<td>${bpmTask.procInstId}</td>
						<th><span>任务ID:</span></th>
						<td>${bpmTask.id}</td>
					</tr>
					<tr>
						<th><span>节点执行ID:</span></th>
						<td>${bpmTask.execId}</td>
						<th><span>任务节点ID:</span></th>
						<td>${bpmTask.nodeId}</td>
					</tr>
					<tr>
						<th><span>流程定义ID:</span></th>
						<td colspan="3">${bpmTask.procDefId}</td>
					</tr>
					<tr>
						<th><span>流程名称:</span></th>
						<td>${bpmTask.procDefName}</td>
						<th><span>任务所属人:</span></th>
						<td>${bpmTask.ownerName}</td>
					</tr>
					<tr>
						<th><span>任务执行人:</span></th>
						<td>${bpmTask.assigneeName}</td>
						<th><span>任务状态:</span></th>
						<td><c:choose>
								<c:when test="${bpmTask.status=='NORMAL'}">普通任务</c:when>
								<c:when test="${bpmTask.status=='AGENT'}">代理任务</c:when>
								<c:when test="${bpmTask.status=='DELIVERTO'}">转办任务</c:when>
								<c:when test="${bpmTask.status=='TRANSFORMED'}">流转任务</c:when>
								<c:when test="${bpmTask.status=='COMMU'}">沟通任务</c:when>
							</c:choose></td>
					</tr>
					<tr>
						<th><span>任务优先级:</span></th>
						<td>${bpmTask.priority}</td>
						<th><span>任务创建时间:</span></th>
						<td><fmt:formatDate value="${bpmTask.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<th><span>候选执行者</span></th>
						<td><c:forEach items="${bpmTask.identityList}" var="identity">
								${identity.name}&nbsp;
							</c:forEach></td>
						<th><span>是否挂起:</span></th>
						<td><c:if test="${bpmTask.suspendState eq 0}">
								<span class="label label-success">正常</span>
							</c:if> <c:if test="${bpmTask.suspendState eq 1}">
								<span class="label label-success">正常</span>
							</c:if> <c:if test="${bpmTask.suspendState eq 2}">
								<span class="label label-danger">挂起</span>
							</c:if></td>
					</tr>
					<tr>
						<th><span>父任务ID:</span></th>
						<td>${bpmTask.parentId}</td>
						<th><span>BPMN实例ID:</span></th>
						<td>${bpmTask.bpmnInstId}</td>
					</tr>
				</table>
			</div>
			
			<div title="流程图" style="padding: 10px;" data-options="iconCls:'fa fa-picture-o'">
				<iframe id="frameImage" onload="loadThisIframe(this);" style="width: 100%;"  frameborder="no" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes" src="${ctx}/flow/task/taskImage?taskId=${bpmTask.id}" pane-src="0"></iframe>
			</div>
			
			<div title="审批历史" style="padding: 10px;margin-bottom: 20px;" data-options="iconCls:'bigger-120 fa fa-bars'">
				<table cellspacing="0" class="table-list">
					<tr>
						<th>序号</th>
						<th>任务名称</th>
						<th>任务创建时间</th>
						<th>审批时间</th>
						<th>待执行人</th>
						<th>执行人</th>
						<th>审批状态</th>
						<th>审批意见</th>
					</tr>
					<c:forEach var="item" items="${opinionList}" varStatus="var">
						<c:forEach var="tem" items="${item}" varStatus="v">
							<tr>
								<c:if test="${v.index==0}">
									<td <c:if test="${fn:length(item)>1}">rowspan="${fn:length(item)}"</c:if>>${var.index+1}</td>
									<td <c:if test="${fn:length(item)>1}">rowspan="${fn:length(item)}"</c:if>>${tem.taskName}</td>
								</c:if>
								<td>
									<fmt:formatDate value="${tem.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<fmt:formatDate value="${tem.completeTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${tem.qualfiedNames}</td>
								<td>${tem.auditorName}</td>
								<td>${tem.statusVal}</td>
								<td>${tem.opinion}</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
				
			</div>
		</div>
		
</body>
</html>