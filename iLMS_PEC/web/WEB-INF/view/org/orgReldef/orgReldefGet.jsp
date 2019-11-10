<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/orgReldef/orgReldefGetController.js"></script>
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
						<th><span>名称:</span></th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th><span>编码:</span></th>
						<td> {{data.code }}</td>
					</tr>
					<tr>								
						<th><span>职务级别:</span></th>
						<td> {{data.postLevel }}</td>
					</tr>
					<tr>								
						<th><span>描述:</span></th>
						<td> {{data.description }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>