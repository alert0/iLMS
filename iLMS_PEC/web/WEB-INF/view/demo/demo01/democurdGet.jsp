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
		<form name="form" ht-load="${ctx}/demo/demo/curdgetJson?id=${param.id}" method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
			<tr>								
				<th><span>字段一:</span></th>
				<td> {{data.col1 }}</td>
			</tr>
			<tr>								
				<th><span>字段二:</span></th>
				<td> {{data.col2 }}</td>
			</tr>
			<tr>								
				<th><span>字段三:</span></th>
				<td> {{data.col3 }}</td>
			</tr>
			<tr>								
				<th><span>字段四:</span></th>
				<td> {{data.col4 }}</td>
			</tr>
		</table>
		
		
		</form>
	</body>
</html>