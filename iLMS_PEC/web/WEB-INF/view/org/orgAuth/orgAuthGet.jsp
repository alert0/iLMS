<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/orgAuth/orgAuthGetController.js"></script>
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
						<th>组织管理员:</th>
						<td> {{data.userName }}</td>
					</tr>
					<tr>								
						<th>组织管理权限:</th>
						<td>
							<label ng-if="isShow(data.orgPerms,'add')" >增加</label>
							<label ng-if="isShow(data.orgPerms,'delete')"  >删除</label>
							<label ng-if="isShow(data.orgPerms,'edit')"  >修改</label>
						 </td>
					</tr>
					<tr>								
						<th>用户管理权限:</th>
						<td> 
							<label ng-if="isShow(data.userPerms,'add')" >增加</label>
							<label ng-if="isShow(data.userPerms,'delete')"  >删除</label>
							<label ng-if="isShow(data.userPerms,'edit')"  >修改</label>
						</td>
					</tr>
					<tr>								
						<th>岗位管理权限:</th>
						<td>
							<label ng-if="isShow(data.posPerms,'add')" >增加</label>
							<label ng-if="isShow(data.posPerms,'delete')"  >删除</label>
							<label ng-if="isShow(data.posPerms,'edit')"  >修改</label>
						 </td>
					</tr>
					<tr>								
						<th>分级管理员权限:</th>
						<td>
							<label ng-if="isShow(data.orgauthPerms,'add')" >增加</label>
							<label ng-if="isShow(data.orgauthPerms,'delete')"  >删除</label>
							<label ng-if="isShow(data.orgauthPerms,'edit')"  >修改</label>
						 </td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>