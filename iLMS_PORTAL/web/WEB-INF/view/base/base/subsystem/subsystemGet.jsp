<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/subsystem/subsystemGetController.js"></script>
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
						<th>系统名称:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>系统别名:</th>
						<td> {{data.alias }}</td>
					</tr>
					<tr>								
						<th>logo地址:</th>
						<td> {{data.logo }}</td>
					</tr>
					<tr>								
						<th>是否可用:</th>
						<td> {{data.enabled == 0? "禁用":"可用" }}</td>
					</tr>
					<tr>								
						<th>主页地址:</th>
						<td> {{data.homeUrl }}</td>
					</tr>
					<tr>								
						<th>基础地址:</th>
						<td> {{data.baseUrl }}</td>
					</tr>
					<tr>								
						<th>租户名称:</th>
						<td> {{data.tenant }}</td>
					</tr>
					<tr>								
						<th>备注:</th>
						<td> {{data.memo }}</td>
					</tr>
					<tr>								
						<th>创建人ID:</th>
						<td> {{data.creatorId }}</td>
					</tr>
					<tr>								
						<th>创建人:</th>
						<td> {{data.creator }}</td>
					</tr>
					<tr>								
						<th>创建时间:</th>
						<td> {{data.createTime | date :'yyyy-MM-dd'}}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>