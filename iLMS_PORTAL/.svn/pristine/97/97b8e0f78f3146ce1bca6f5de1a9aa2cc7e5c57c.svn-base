<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/form/dataTemplate/bpmDataTemplate/bpmDataTemplateGetController.js"></script>
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
						<th>自定义表ID:</th>
						<td> {{data.tableId }}</td>
					</tr>
					<tr>								
						<th>自定义表单key:</th>
						<td> {{data.formKey }}</td>
					</tr>
					<tr>								
						<th>名称:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>别名:</th>
						<td> {{data.alias }}</td>
					</tr>
					<tr>								
						<th>样式:</th>
						<td> {{data.style }}</td>
					</tr>
					<tr>								
						<th>是否需要分页:</th>
						<td> {{data.needPage }}</td>
					</tr>
					<tr>								
						<th>分页大小:</th>
						<td> {{data.pageSize }}</td>
					</tr>
					<tr>								
						<th>数据模板别名:</th>
						<td> {{data.templateAlias }}</td>
					</tr>
					<tr>								
						<th>数据模板代码:</th>
						<td> {{data.templateHtml }}</td>
					</tr>
					<tr>								
						<th>显示字段:</th>
						<td> {{data.displayField }}</td>
					</tr>
					<tr>								
						<th>排序字段:</th>
						<td> {{data.sortField }}</td>
					</tr>
					<tr>								
						<th>条件字段:</th>
						<td> {{data.conditionField }}</td>
					</tr>
					<tr>								
						<th>管理字段:</th>
						<td> {{data.manageField }}</td>
					</tr>
					<tr>								
						<th>过滤条件:</th>
						<td> {{data.filterField }}</td>
					</tr>
					<tr>								
						<th>变量字段:</th>
						<td> {{data.varField }}</td>
					</tr>
					<tr>								
						<th>过滤类型（1.建立条件,2.脚本条件）:</th>
						<td> {{data.filterType }}</td>
					</tr>
					<tr>								
						<th>数据来源:</th>
						<td> {{data.source }}</td>
					</tr>
					<tr>								
						<th>流程定义ID:</th>
						<td> {{data.defId }}</td>
					</tr>
					<tr>								
						<th>是否查询:</th>
						<td> {{data.isQuery }}</td>
					</tr>
					<tr>								
						<th>是否过滤:</th>
						<td> {{data.isFilter }}</td>
					</tr>
					<tr>								
						<th>导出字段:</th>
						<td> {{data.exportField }}</td>
					</tr>
					<tr>								
						<th>打印字段:</th>
						<td> {{data.printField }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>