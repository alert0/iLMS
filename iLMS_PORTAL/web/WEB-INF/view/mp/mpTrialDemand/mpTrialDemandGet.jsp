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
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="${ctx}/mp/mpTrialDemand/curdgetJson?id=${param.id}"   method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th>零件编号:</th>
						<td> {{data.partNo }}</td>
					</tr>
					<tr>								
						<th>出货地:</th>
						<td> {{data.supFactory }}</td>
					</tr>
					<tr>								
						<th>供应商代码:</th>
						<td> {{data.supplierNo }}</td>
					</tr>
					<tr>								
						<th>到货日期:</th>
						<td> {{data.arriveTimeStr | date :'yyyy-MM-dd'}}</td>
					</tr>
					<tr>								
						<th>例外需求:</th>
						<td> {{data.excOrderNum }}</td>
					</tr>
					<tr>								
						<th>创建人:</th>
						<td> {{data.creationUser }}</td>
					</tr>
					<tr>								
						<th>创建时间:</th>
						<td> {{data.creationTime | date :'yyyy-MM-dd'}}</td>
					</tr>
					<tr>								
						<th>修改人:</th>
						<td> {{data.lastModifiedUser }}</td>
					</tr>
					<tr>								
						<th>修改时间:</th>
						<td> {{data.lastModifiedTime | date :'yyyy-MM-dd'}}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>