<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>任务催办</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/flow/bpmTaskReminder/remove">
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
							<ul >
								<li><span>催办名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑任务催办" : action == "add" ? "添加任务催办" : "查看任务催办";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="bpmTaskReminder" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
			{field : 'taskId',sortName : "task_id_",title : '催办任务ID',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'name',sortName : "name_",title : '催办名称',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'relDate',sortName : "rel_date_",title : '相对时间',width : 250,align : 'center',sortable : 'true',formatter:dateTimeFormatter}, 
			{field : 'dueAction',sortName : "due_action_",title : '到期执行动作',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'dueScript',sortName : "due_script_",title : '调用指定方法',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'colManage',title : '操作',align : 'center',
				formatter : function(value, row, index) {
					var result = ""/* "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>" */;
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/bpmTaskReminder/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
