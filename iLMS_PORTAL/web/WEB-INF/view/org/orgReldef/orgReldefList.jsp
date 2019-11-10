<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织关系定义</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/org/orgReldef/remove">
								<span>删除</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
								<li><span>编码:</span><input class="inputText" type="text" name="Q^code_^SL"></li>
								<li><span>职务级别:</span><input class="inputText" type="text" name="Q^post_level_^SL"></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑职务定义" : action == "add" ? "添加职务定义" : "查看职务定义";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="orgReldef" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function removeRow(id) {
		$.topCall.confirm('温馨提示', '确定删除？', function(r){
			if(!r)return false;
		var param = {
			id : id
		}
		$.post("remove", param, function(data) {
			var result = new com.hotent.form.ResultMessage(data)
			if (result.isSuccess()) {//成功
				$.topCall.success(result.getMessage());
				$('.my-easyui-datagrid').datagrid('reload');
			} else {
				$.topCall.error(result.getMessage());
			}
		});
		});
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
            {field : 'id',sortName : "id_",title : '名称',checkbox : true,width : 250,align : 'center',sortable : 'true'}, 
			{field : 'name',sortName : "name_",title : '名称',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'code',sortName : "code_",title : '编码',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'postLevel',sortName : "post_level_",title : '职务级别',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'description',sortName : "description_",title : '描述',width : 250,align : 'center',sortable : 'true'}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/orgReldef/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
