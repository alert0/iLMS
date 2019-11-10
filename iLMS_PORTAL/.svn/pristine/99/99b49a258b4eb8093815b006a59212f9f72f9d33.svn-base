<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/userUnder/userUnderGetController.js"></script>
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
						<th>用户id:</th>
						<td> {{data.userId }}</td>
					</tr>
					<tr>								
						<th>下属用户id:</th>
						<td> {{data.underUserId }}</td>
					</tr>
					<tr>								
						<th>下属用户名:</th>
						<td> {{data.underUserName }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>