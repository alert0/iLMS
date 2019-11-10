<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/org/org/orgEditController.js"></script>
<script type="text/javascript">
	var parentId = "${parentId}";
	var parentOrgName = "${parentOrgName}";
	var demId = "${demId}";
	var id = "${id}";
	var code = "${code}";
	var authId = "${param.authId}";//分级管理id
	var authOrgId = "${param.authOrgId}";//分级管理组织id
</script>
</head>
<body ng-controller="ctrl">

	<!-- 顶部标题 -->
	<div class="panel-header" style="width:100%;">
		<div class="panel-title" ng-show="${id}">编辑当前组织</div>
		<div class="panel-title" ng-show="${empty id}">添加子级组织</div>
	</div>
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-primary btn-sm fa-save" ng-model="data" ng-click="save()"><span>保存</span></a>
		</div>
	</div>
	<form name="form" method="post" ng-model="data" ht-load="getJson?id=${id}&demId=${demId}">
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>上级组织：</span></th>
				<td><input ng-model="data.id" name="data.id" type="hidden"> <input  ng-model="data.parentId" name="data.parentId" type="hidden"> <input class="inputText" ng-model="data.parentOrgName" disabled="disabled" style="border: 0" name="parentOrgName" /></td>
			</tr>
			<tr>
				<th><span>组织名称：</span><span class="required">*</span></th>
				<td><input class="inputText" type="text" ng-model="data.name" ht-validate="{required:true,maxlength:20}" /></td>
			</tr>
			<tr>
				<th><span>组织编码：</span><span class="required">*</span></th>
				<td><input class="inputText" type="text" ng-model="data.code" ng-disabled="data.id" id="code" ht-validate="{required:true,maxlength:192,isexist:'${ctx}/org/org/isExist?oldCode=${code}'}" ht-pinyin="data.name" /></td>
			</tr>

			<tr>
				<th><span>排序号：</span></th>
				<td><input class="inputText" type="text" ng-model="data.orderNo" ht-validate="{required:false,number:true,maxIntLen:10}" /></td>
			</tr>

			<tr>
				<th><span>级别：</span></th>
				<td><input class="inputText" type="text" ng-model="data.grade" ht-validate="{required:false,maxlength:192}" /></td>
			</tr>
		</table>
	</form>
</body>
</html>