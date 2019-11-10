<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>用户参数</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/system/sysUserParams/sysUserParams/remove">
								<span>删除</span>
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
								<li><span>USER_ID_:</span><input class="inputText" type="text" name="Q^USER_ID_^SL"></li>
								<li><span>ALIAS_:</span><input class="inputText" type="text" name="Q^ALIAS_^SL"></li>
								<li><span>VALUE_:</span><input class="inputText" type="text" name="Q^VALUE_^SL"></li>
							</ul>
						</form>
					</div>
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
		var title = action == "edit" ? "编辑用户参数" : action == "add" ? "添加用户参数" : "查看用户参数";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysUserParams" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'userId',sortName : "USER_ID_",title : 'USER_ID_',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'alias',sortName : "ALIAS_",title : 'ALIAS_',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'value',sortName : "VALUE_",title : 'VALUE_',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/sysUserParams/sysUserParams/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
