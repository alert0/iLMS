<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="overflow: auto;padding:0px 5px 15px 0px;">
		<table cellspacing="0" class="table-list">
				<tr>
					<th>序号</th>
					<th>任务名称</th>
					<th>任务创建/更新时间</th>
					<th>审批时间</th>
					<th>待执行人</th>
					<th>执行人</th>
					<th>审批状态</th>
					<th>审批意见</th>
				</tr>


			<c:forEach var="item" items="${llist}" varStatus="var">
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
</body>
</html>