<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/sysResource/sysResourceGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			 <div class="buttons">
				<a class="btn btn-primary btn-sm fa-back" onClick="window.parent.parent.location.reload();"><span>返回</span></a>
			</div>  
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					
					<tr>								
						<th>资源别名:</th>
						<td> {{data.alias }}</td>
					</tr>
					<tr>								
						<th>资源名:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>默认地址:</th>
						<td> {{data.defaultUrl }}</td>
					</tr>
					<tr>								
						<th>显示到菜单:</th>
						<td> {{data.enableMenu == null? "" : data.enableMenu == 1? "是" : "否"}}</td>
					</tr>
					<tr>								
						<th>是否有子节点:</th>
						<td> {{data.hasChildren == null? "" : data.hasChildren == 1 ? "有" : "无" }}</td>
					</tr>
					<tr style="display: none;">								
						<th>OPENED_:</th>
						<td> {{data.opened == null? "" : data.opened == 1? "有" : "无" }}</td>
					</tr>
					<tr>								
						<th>图标:</th>
						<td> {{data.icon }}</td>
					</tr>
					<tr>								
						<th>打开新窗口:</th>
						<td> {{data.newWindow == null? "" : data.newWindow == 1? "打开" : "不打开"}}</td>
					</tr>
					<tr>								
						<th>排序:</th>
						<td> {{data.sn }}</td>
					</tr>
		</table>
			
			<table ng-if="!data.hasChildren" class="table-form"  cellspacing="1">
					<tr style="font-size: 16">
						<th width="35%">名称</th>
						<th width="45%">URL</th>
					</tr>
					<tr ng-repeat="relResource in data.relResources" >
						<th width="35%">{{relResource.name}}</th>
						<th width="45%">{{relResource.resUrl}}</th>
					</tr>
			</table>	
				
		</form>
	</body>
</html>