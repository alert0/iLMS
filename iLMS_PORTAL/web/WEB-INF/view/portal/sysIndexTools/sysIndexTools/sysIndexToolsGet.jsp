<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/portal/sysIndexTools/sysIndexTools/sysIndexToolsGetController.js"></script>
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
						<th>名称:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>图标:</th>
						<td> {{data.icon }}</td>
					</tr>
					<tr>								
						<th>链接:</th>
						<td> {{data.url }}</td>
					</tr>
					<tr>								
						<th>类型:</th>
						<td> {{data.type }}</td>
					</tr>
					<tr>								
						<th>统计模式:</th>
						<td> {{data.countMode==0?"不统计":data.countMode==1?"Service方法":"自定义查询" }}</td>
					</tr>
					<tr>								
						<th>统计算法:</th>
						<td> {{data.counting }}</td>
					</tr>
					<tr>								
						<th>创建时间:</th>
						<td> {{data.createTime | date :'yyyy-MM-dd'}}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>