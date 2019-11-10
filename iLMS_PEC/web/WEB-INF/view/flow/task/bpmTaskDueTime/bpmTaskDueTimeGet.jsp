<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/flow/task/bpmTaskDueTime/bpmTaskDueTimeGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th>时间类型:</th>
						<td>
							<span ng-if="data.dateType == 'worktime'" >工作日</span>
							<span ng-if="data.dateType != 'worktime'" >日历日</span>
						</td>
					</tr>
					<tr>								
						<th>任务审批期限 (分钟):</th>
						<td> {{data.dueTime }}</td>
					</tr>
					<tr>								
						<th>任务开始时间:</th>
						<td> {{data.startTime | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th>任务到期时间:</th>
						<td> {{data.expirationDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th>增加审批时间(分钟):</th>
						<td> {{data.addDueTime}}</td>
					</tr>
					<tr>								
						<th>延期处理时间:</th>
						<td> {{data.createTime | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th>备注:</th>
						<td> {{data.remark }}</td>
					</tr>
					<tr>								
						<th>附件:</th>
						<td><div ht-upload="data.fileId" permission="r" ></div> </td>
					</tr>
		</table>
		</form>
	</body>
</html>