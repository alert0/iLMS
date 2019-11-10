<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnGetController.js"></script>
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
						<th>栏目名称:</th>
						<td> {{data.NAME }}</td>
					</tr>
					<tr>								
						<th>栏目别名:</th>
						<td> {{data.ALIAS }}</td>
					</tr>
					<tr>								
						<th>栏目分类:</th>
						<td> {{data.CATALOG }}</td>
					</tr>
					<tr>								
						<th>栏目类型（0：一般栏目、1：图表栏目、2、滚动栏目）:</th>
						<td> {{data.colType }}</td>
					</tr>
					<tr>								
						<th>数据加载方式(0:服务方法;1:自定义查询):</th>
						<td> {{data.dataMode }}</td>
					</tr>
					<tr>								
						<th>数据来源:</th>
						<td> {{data.dataFrom }}</td>
					</tr>
					<tr>								
						<th>数据别名:</th>
						<td> {{data.dsAlias }}</td>
					</tr>
					<tr>								
						<th>数据源名称:</th>
						<td> {{data.dsName }}</td>
					</tr>
					<tr>								
						<th>栏目高度:</th>
						<td> {{data.colHeight }}</td>
					</tr>
					<tr>								
						<th>栏目URL:</th>
						<td> {{data.colUrl }}</td>
					</tr>
					<tr>								
						<th>栏目模版:</th>
						<td> {{data.templateHtml }}</td>
					</tr>
					<tr>								
						<th>是否公共栏目:</th>
						<td> {{data.isPublic }}</td>
					</tr>
					<tr>								
						<th>所属组织ID:</th>
						<td> {{data.orgId }}</td>
					</tr>
					<tr>								
						<th>是否支持刷新:</th>
						<td> {{data.supportRefesh }}</td>
					</tr>
					<tr>								
						<th>刷新时间:</th>
						<td> {{data.refeshTime }}</td>
					</tr>
					<tr>								
						<th>展示效果:</th>
						<td> {{data.showEffect }}</td>
					</tr>
					<tr>								
						<th>描述:</th>
						<td> {{data.MEMO }}</td>
					</tr>
					<tr>								
						<th>数据参数:</th>
						<td> {{data.dataParam }}</td>
					</tr>
					<tr>								
						<th>首页分页:</th>
						<td> {{data.NEEDPAGE }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>