<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="base">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<style type="text/css">
p {
	margin: 0 0 0 3px;
}

td, th {
	height: 35px;
}

th {
	text-align: right;
}
</style>
</head>
<body class="easyui-layout">
	<div class="scoll-panel">
		<div class="gray-div">
			<div class="form-table">
				<table cellspacing="0" class="table-form">
					<tr>
						<th>
							<span>流程实例标题:</span>
						</th>
						<td>${bpmProcessInstance.subject}</td>
						
						<th>
							<span>是否正式数据:</span>
						</th>
						<td >
							<c:choose>
								<c:when test="${bpmProcessInstance.isFormmal eq 'Y'}">
									<span class="green">是</span>
								</c:when>
								<c:when test="${bpmProcessInstance.isFormmal eq 'N'}">
									<span class="red">否</span>
								</c:when>
							</c:choose>
						</td>
						
					</tr>
					<tr>
						<th>
							<span>流程实例ID:</span>
						</th>
						<td>${bpmProcessInstance.id}</td>
						<th>
							<span>BPMN流程实例ID:</span>
						</th>
						<td>${bpmProcessInstance.bpmnInstId}</td>
					</tr>
					<tr>
						<th>
							<span>流程定义ID:</span>
						</th>
						<td>${bpmProcessInstance.procDefId}</td>
						<th>
							<span>BPMN流程定义ID:</span>
						</th>
						<td>${bpmProcessInstance.bpmnDefId}</td>
					</tr>
					<tr>
						<th>
							<span>流程定义Key:</span>
						</th>
						<td>${bpmProcessInstance.procDefKey}</td>
						<th>
							<span>流程名称:</span>
						</th>
						<td>${bpmProcessInstance.procDefName}</td>
					</tr>
					<tr>
						<th>
							<span>关联数据业务主键:</span>
						</th>
						<td>${bpmProcessInstance.bizKey}</td>
						<th>
							<span>创建人:</span>
						</th>
						<td>${bpmProcessInstance.creator}</td>
					</tr>
					<tr>
						<th>
							<span>创建时间:</span>
						</th>
						<td>
							<fmt:formatDate value="${bpmProcessInstance.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<th>
							<span>结束时间:</span>
						</th>
						<td>
							<fmt:formatDate value="${bpmProcessInstance.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<th>
							<span>共用时:</span>
						</th>
						<td>{{'${bpmProcessInstance.duration}' | htTime}}</td>
						<th>
							<span>父实例Id:</span>
						</th>
						<td>${bpmProcessInstance.parentInstId}</td>
					</tr>
					
				</table>
			</div>
		</div>
	</div>
</body>
</html>