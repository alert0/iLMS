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
		<form name="form" ht-load="${ctx}/pkg/pkgPart/curdgetJson?id=${param.id}" method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
			<tr>								
				<th><span>车型:</span></th>
				<td> {{data.carType }}</td>
				<th><span>零件编号:</span></th>
				<td> {{data.partNo }}</td>
			</tr>
		
			<tr>								
				<th><span>工程:</span></th>
				<td> {{data.project }}</td>
				<th><span>零件名称:</span></th>
				<td> {{data.partNameCn }}</td>
			</tr>
			<tr>								
				<th><span>供应商代码:</span></th>
				<td> {{data.supplierNo }}</td>
				<th><span>供应商名称:</span></th>
				<td> {{data.supplierName }}</td>
			</tr>
			<tr>								
				<th><span>零件担当:</span></th>
				<td> {{data.partRespUser }}</td>
				<th><span>联系方式:</span></th>
				<td> {{data.telNo }}</td>
			</tr>
			<tr>								
				<th><span>留用新设:</span></th>
				<td> {{data.status }}</td>
			</tr>
		</table>
		
		</form>
	</body>
</html>