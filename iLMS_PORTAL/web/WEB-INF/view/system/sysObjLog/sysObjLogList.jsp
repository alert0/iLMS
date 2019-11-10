<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>常用日志</title>
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
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class="bigger-190 fa  fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul style="margin-left: -26px">
						<li><span>操作人:</span><input class="inputText" type="text" name="Q^OPERATOR_^SL"></li>
						<li>
							<span>创建时间 从:</span><input  name="Q^CREATE_TIME_^DL"  class="inputText date" />
						</li>
						<li>
							<span>至: </span><input  name="Q^CREATE_TIME_^DG" class="inputText date" />
						</li>
						<li><span>分类:</span><input class="inputText" type="text" name="Q^OBJ_TYPE_^SL"></li>
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
		var title = action == "edit" ? "编辑常用日志" : action == "add" ? "添加常用日志" : "查看常用日志";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysObjLog" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?type=${param.type}",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'name',sortName : "NAME_",title : '名称',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'content',sortName : "CONTENT_",title : '内容',width : 350,align : 'center',sortable : 'true'
			}, 
			{field : 'operator',sortName : "OPERATOR_",title : '操作人',width : 100,align : 'center',sortable : 'true'
			}, 
			{field : 'createTime',sortName : "CREATE_TIME_",title : '创建时间',width : 150,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter}, 
			{field : 'objType',sortName : "OBJ_TYPE_",title : '分类',width : 150,align : 'center',sortable : 'true'
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					return  "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
