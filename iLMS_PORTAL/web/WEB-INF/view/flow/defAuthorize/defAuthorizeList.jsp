<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程分级授权管理列表</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">

							<a class="btn btn-primary btn-sm fa-add" href="javascript:;" onClick='openDetail("","add")'>
								<span>添加</span>
							</a>
							<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="/flow/defAuthorize/del">
								<span>删除</span>
							</a>
							<a class="btn btn-primary  btn-sm fa-search" href="javascript:;" id="btnSearch">
								<span>搜索</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li>
									<span>权限描述:</span>
									<input type="text" name="Q^authorizeDesc^SL" class="inputText" >
								</li>
							</ul>
							
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		loadGrid();
	});
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/flow/defAuthorize/listJson",
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [ 
			   {field : 'id',sortName : "id_",checkbox : true}, 
			   {field : 'authorizeDesc',title : '权限描述',width : 250,align : 'left'} ,
			   {field : 'creator',title : '创建人',width : 250,align : 'center'},
			   {field : 'createTime',title : '创建时间',width : 250,align : 'center',formatter:dateTimeFormatter},
 			   {
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/defAuthorize/del?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑分管授权" : action == "add" ? "添加分管授权" : "查看分管授权";
		var url = action != "get" ? "defAuthorizeEdit" : "defAuthorizeGet";
		HT.window.openEdit(url + '?id=' + id, title, action, 'grid', 500, 500, null, null, id, true);
	}
</script>