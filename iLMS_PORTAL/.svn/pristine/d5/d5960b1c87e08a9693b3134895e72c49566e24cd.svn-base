<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysUserParams/sysUserParams/sysUserParamsGetController.js"></script>
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
						<th>USER_ID_:</th>
						<td> {{data.userId }}</td>
					</tr>
					<tr>								
						<th>ALIAS_:</th>
						<td> {{data.alias }}</td>
					</tr>
					<tr>								
						<th>VALUE_:</th>
						<td> {{data.value }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>