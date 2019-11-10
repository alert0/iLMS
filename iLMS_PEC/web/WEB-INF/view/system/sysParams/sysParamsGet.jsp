<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysParams/sysParamsGetController.js"></script>
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
						<th>参数名:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>参数key:</th>
						<td> {{data.alias }}</td>
					</tr>
					<tr>								
						<th>参数类型:</th>
						<td ng-if=" data.type == '1' ">
							用户参数
						</td>
						<td ng-if=" data.type == '2' ">
							组织参数
						</td>
					</tr>
					<tr>								
						<th>数据来源:</th>
						<td ng-if=" data.ctlType == 'input' ">手动录入</td>
						<td ng-if=" data.ctlType == 'dic' ">数据字典</td>
						<td ng-if=" data.ctlType == 'select' ">下拉框</td>
						<td ng-if=" data.ctlType == 'checkbox' ">复选框</td>
						<td ng-if=" data.ctlType == 'radio' ">单选按钮</td>
						<td ng-if=" data.ctlType == 'date' ">日期</td>
						<td ng-if=" data.ctlType == 'number' ">数字</td>
						<td ng-if=" data.ctlType == 'customdialog' ">自定义对话框</td>
					</tr>
					<tr>								
						<th>数据:</th>
						<td> {{data.json }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>