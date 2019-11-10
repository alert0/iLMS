<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/bpmReminderHistory/bpmReminderHistoryGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th><span>流程实例ID:</span></th>
						<td> {{data.instId }}</td>
					</tr>
					<tr>								
						<th><span>流程实例标题:</span></th>
						<td> {{data.isntName }}</td>
					</tr>
					<tr>								
						<th><span>节点名称:</span></th>
						<td> {{data.nodeName }}</td>
					</tr>
					<tr>								
						<th><span>节点ID:</span></th>
						<td> {{data.nodeId }}</td>
					</tr>
					<tr>								
						<th><span>执行时间:</span></th>
						<td> {{data.executeDate | date :'yyyy-MM-dd'}}</td>
					</tr>
					<tr>								
						<th><span>执行类型:</span></th>
						<td> {{data.remindType }}</td>
					</tr>
					<tr>								
						<th><span>user_id_:</span></th>
						<td> {{data.userId }}</td>
					</tr>
					<tr>								
						<th><span>说明:</span></th>
						<td> {{data.note }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>