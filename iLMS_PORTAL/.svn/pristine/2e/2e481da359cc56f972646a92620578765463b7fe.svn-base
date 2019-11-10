<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
<script type="text/javascript" src="${ctx}/js/org/org/orgGetController.js"></script>
<script type="text/javascript">
	var id="${param.id}";
</script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
 
		<form name="form" ht-load="getJson?id=${param.id}&demId=${param.demId}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th style="width:350px;"><span>组织名称:</span></th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th><span>上级组织名称:</span></th>
						<td> {{data.parentOrgName }}</td>
					</tr>
					<tr>								
						<th><span>排序号:</span></th>
						<td> {{data.orderNo }}</td>
					</tr>
					<tr>								
						<th><span>组织编码:</span></th>
						<td> {{data.code }}</td>
					</tr>
					<tr>								
						<th><span>级别:</span></th>
						<td> {{data.grade }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>