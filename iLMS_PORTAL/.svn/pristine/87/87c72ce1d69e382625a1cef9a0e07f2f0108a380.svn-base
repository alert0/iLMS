<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/sysDemension/sysDemensionGetController.js"></script>
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
						<th>维度名称:</th>
						<td> {{data.demName }}</td>
					</tr>
					<tr>								
						<th>维度别名:</th>
						<td> {{data.demCode }}</td>
					</tr>
					<tr>								
						<th>描述:</th>
						<td> {{data.demDesc }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>