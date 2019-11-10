<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body class="easyui-layout">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-back" href="javascript:;" onclick="HT.window.closeEdit(true,'sysMsgTemplateList')"><span>返回</span></a>
			</div>
		</div>
		<table class="table-list"   cellspacing="0">
					<tr>								
						<th><span>模版名称:</span></th>
						<td>${sysMsgTemplate.name}</td>
					</tr>
					<tr>								
						<th><span>模板分类:</span></th>
						<td>
							<c:choose>
								<c:when test="${sysMsgTemplate.typeKey=='taskCreate'}">任务创建通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmCommuSend'}">任务沟通</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmCommuFeedBack'}">通知沟通人</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnTaskTrans'}">任务流转默认</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmHandTo'}">任务转交通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='addSignTask'}">加签通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='taskComplete'}">任务完成通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='taskBack'}">任务驳回通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='processEnd'}">流程结束通知</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnApproval'}">审批提醒</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnBack'}">驳回提醒</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnRecover'}">撤销提醒</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnAgent'}">代理任务审批</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmnDelegate'}">通知被代理人</c:when>
								<c:when test="${sysMsgTemplate.typeKey=='bpmTurnCancel'}">转办取消通知</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>								
						<th><span>模版业务键:</span></th>
						<td>${sysMsgTemplate.key}</td>
					</tr>
					<tr>								
						<th><span>是否默认模板:</span></th>
						<td>
						<c:if test="${sysMsgTemplate.isDefault==1}">是</c:if>
						<c:if test="${sysMsgTemplate.isDefault==0}">否</c:if>
						</td>
					</tr>
					<tr>								
						<th><span>标题:</span></th>
						<td>${sysMsgTemplate.subject}</td>
					</tr>
					<tr>								
						<th><span>纯文本:</span></th>
						<td>${sysMsgTemplate.plain}</td>
					</tr>
					<tr>								
						<th><span>模版体HTML:</span></th>
						<td>${sysMsgTemplate.html}</td>
					</tr>
					<tr>								
						<th><span>创建时间:</span></th>
						<td><fmt:formatDate value="${sysMsgTemplate.createTime}" /></td>		
					</tr>
					<tr>								
						<th><span>更新时间:</span></th>
						<td><fmt:formatDate value="${sysMsgTemplate.updateTime}" /></td>		
					</tr>
		</table>
	</body>
</html>