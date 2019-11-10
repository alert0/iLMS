<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript">
		var app = angular.module('app', ['formDirective']);
		app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
			
		}]);
		</script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="logErrGetJson?id=${param.id}"   ng-model="data">
			<table class="table-form"   cellspacing="0">
				<tr>								
					<th>帐号:</th>
					<td> {{data.account }}</td>
				</tr>
				<tr>								
					<th>IP来源:</th>
					<td> {{data.ip }}</td>
				</tr>
				<tr>								
					<th>创建时间:</th>
					<td> {{data.createTime | date :'yyyy-MM-dd'}}</td>
				</tr>
				<tr>								
					<th>URL:</th>
					<td> {{data.url }}</td>
				</tr>
				<tr>								
					<th>内容:</th>
					<td> {{data.content }}</td>
				</tr>
			</table>
		
		
		</form>
	</body>
</html>