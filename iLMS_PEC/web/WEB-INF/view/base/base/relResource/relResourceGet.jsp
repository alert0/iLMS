<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/relResource/relResourceGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<!-- <div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>  -->
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th>资源ID:</th>
						<td> {{data.resId }}</td>
					</tr>
					<tr>								
						<th>名称:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>资源地址:</th>
						<td> {{data.resUrl }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>