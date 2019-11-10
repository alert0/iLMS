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
		<div ng-hide="${param.isheader}" class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="${ctx}/dpm/department/curdgetJson?id=${param.id}" method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
			<tr>								
				<th><span>责任组编码:</span></th>
				<td> {{data.depCode }}</td>
			</tr>
			<tr>								
				<th><span>责任组名称:</span></th>
				<td> {{data.depName }}</td>
			</tr>
			<tr>								
				<th><span>所属科室:</span></th>
				<td> {{data.belongDep }}</td>
			</tr>
			<tr>								
				<th><span>部门审核人:</span></th>
				<td> {{data.depChecker }}</td>
			</tr>
		</table>
		
		</form>
	</body>
</html>