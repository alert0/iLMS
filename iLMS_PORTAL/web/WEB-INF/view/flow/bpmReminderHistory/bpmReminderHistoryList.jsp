<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>催办历史</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/flow/bpmReminderHistory/remove">
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
								<li><span>流程实例ID:</span><input class="inputText" type="text" name="Q^inst_id_^SL"></li>
								<li><span>流程实例标题:</span><input class="inputText" type="text" name="Q^isnt_name_^SL"></li>
								<li><span>节点名称:</span><input class="inputText" type="text" name="Q^node_name_^SL"></li></br>
								<li>
									<span>执行时间从:</span><input  name="Q^execute_date_^DL"  class="inputText date" />
								</li>
								<li>
									<span>至: </span><input  name="Q^execute_date_^DG" class="inputText date" />
								</li>
								<li><span>执行类型:</span><input class="inputText" type="text" name="Q^remind_type_^SL"></li>
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
		var title = action == "edit" ? "编辑催办历史" : action == "add" ? "添加催办历史" : "查看催办历史";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="bpmReminderHistory" + action;
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
			{field : 'instId',sortName : "inst_id_",title : '流程实例ID',width : 110,align : 'center',sortable : 'true'}, 
			{field : 'isntName',sortName : "isnt_name_",title : '流程实例标题',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'nodeName',sortName : "node_name_",title : '节点名称',width : 100,align : 'center',sortable : 'true'}, 
			{field : 'nodeId',sortName : "node_id_",title : '节点ID',width : 100,align : 'center',sortable : 'true'}, 
			{field : 'executeDate',sortName : "execute_date_",title : '执行时间',width : 150,align : 'center',sortable : 'true',formatter:dateTimeFormatter}, 
			{field : 'remindType',sortName : "remind_type_",title : '执行类型',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'note',sortName : "note_",title : '说明',width : 260,align : 'center',sortable : 'true'
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var  result = "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/bpmReminderHistory/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
