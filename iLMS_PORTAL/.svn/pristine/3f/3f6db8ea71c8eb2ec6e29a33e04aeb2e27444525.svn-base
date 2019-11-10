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
		<form name="form" ht-load="${ctx}/dpm/ins/curdgetJson?applyNo=${param.applyNo}" method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>
						<th><span>不良品类型:</span></th>
						<td>{{data.dpmTypeName  }}</td>
						<th><span>申请单号:</span></th>
						<td> {{data.applyNo  }}</td>
					</tr>
					<tr>
					    <th><span>状态:</span></th>
						<td>{{data.insStatusName }}</td>
						<th><span>申请科室:</span></th>
						<td> {{data.applyDep }}</td>
					</tr>
			        <tr>
					    <th><span>填单日期:</span></th>
						<td>{{data.insDate }}</td>
						<th><span>车型:</span></th>
						<td>{{data.modelCode }}</td>
					</tr>
				    <tr>
						<th><span>处理结果:</span></th>
						<td>{{data.dealResultName }}</td>
						<th><span>零件编号:</span></th>
						<td>{{data.partNo }}</td>
					</tr>
					<tr>
						<th><span>不良品数量:</span></th>
						<td>{{data.dpmNum }}</td>
						<th><span>零件名称:</span></th>
						<td>{{data.partNameCn }}</td>
					</tr>
					<tr>
					   <th><span>发现区域:</span></th>
						<td>{{data.discoArea }}</td>
						<th><span>不良品项目:</span></th>
						<td>{{data.dpmCode }}</td>
					</tr>
					<tr>
						<th><span>供应商代码:</span></th>
						<td>{{data.supplierNo }}</td>
					    <th><span>不良描述:</span></th>
						<td>{{data.dpmDesc }}</td>
					</tr>
					<tr>
					    <th><span>供应商担当:</span></th>
						<td>{{data.contact }}</td>
					   <th><span>供应商联系方式:</span></th>
						<td>{{data.telNo }}</td>
					</tr>
					<tr>
					    <th><span>备注:</span></th>
						<td>{{data.remark }}</td>
					</tr>
			</table>
		</form>
	</body>
</html>