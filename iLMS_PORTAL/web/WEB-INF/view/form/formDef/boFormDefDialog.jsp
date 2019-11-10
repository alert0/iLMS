<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body>
		<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
							<span>搜索</span>
						</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse">
							<i class="fa fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul>
							<li><span>表单KEY:</span><input class="inputText" type="text" name="Q^formKey^SL"></li>
							<li><span>表单名称:</span><input class="inputText" type="text" name="Q^name^SL"></li>
						</ul>
					</form>
				</div>
			</div>
			<div id="bpmFormDialog" class="my-easyui-datagrid"></div>
		</div>	
	</body>
</html>
	<script>
	$(function() {
		loadGrid();

	});

	function loadGrid() {
		var isSingle = '${param.isSingle}';
		$('#bpmFormDialog').datagrid($.extend({
			url : __ctx + "/form/formDef/listJsonByBODef?defId=${defId}&formType=${formType}&topDefKey=${topDefKey}",
			sortName : 'create_time_',
			sortOrder : 'desc',
			singleSelect:isSingle,
			columns : [ [
			{field : 'formKey',sortName : "form_key_",title : '表单KEY',width :200}, 
			{field : 'name',sortName : "name_",title : '表单名称',width : 300},       
			{field : 'version',sortName : "version_",title : '主版本编号',width : 200}
			
			  ] ]
		}, $defaultOptions));
	}
	</script>
	

