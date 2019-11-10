<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>仓库管理</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a id="query" class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>查询</span>
					</a>
			        <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
			       		<span>重置</span>
			       	</a>
			       	<a id="add" class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
						<span>新增</span>
					</a>
					<a class="btn btn-sm btn-primary fa-history" href="javascript:;" onclick="openDetail('1','edit')">
						<span>修改</span>
					</a>
			       	<a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/inv/wareHouse/removeWareHouse">
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
						<li><span>仓库代码</span>
							<input id="wareCode" class="inputText" type="text" name="wareCode">
						</li>
						<li><span>仓库名称</span>
							<input id="wareName" class="inputText" type="text" name="wareName">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			queryParams: {
					flag : '1'
			},
			url : "queryWareHouse",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'true'},
			    {field : 'wareCode',sortName : "WARE_CODE",title : '仓库代码',width : 200,align : 'center',sortable : 'true'},
			    {field : 'erpWareCode',sortName : "ERP_WARE_CODE",title : 'ERP仓库代码',width : 200,align : 'center',sortable : 'true'},
			    {field : 'wareAddr',sortName : "WARE_ADDR",title : '仓库名称',align : 'center',width : 200,sortable : 'true'},
				{field : 'wareName',sortName : "WARE_NAME",title : '仓库地址',align : 'center',width : 200,sortable : 'true'},
				{field : 'wareType',sortName : "WARE_TYPE",title : '仓库类型',align : 'center',width : 200,sortable : 'true'}
				]],
		    onLoadSuccess : function (data) {
			    if(data.total==0){
                    var dc = $(this).data('datagrid').dc;
                    var header2Row = dc.header2.find('tr.datagrid-header-row');
                    dc.body2.find('table').append(header2Row.clone().css({
                    	"visibility":"hidden"
                    }));
				}
			}
		}));
	}
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑仓库信息" : action == "add" ? "添加仓库信息" : "查看仓库信息";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="invWareHouse" + action;
		if(!$.isEmpty(id)){
			var rows = $("#grid").datagrid('getSelections');
			if(rows.length > 1){
				$.topCall.warn("已选择多项数据，请选择一项进行操作");
				return;
			}
			if(rows.length < 1){
				$.topCall.warn("请选择需要修改的数据");
				return;
			}
			
			id = $('#grid').datagrid('getSelected').id;
			url+='?id=' + id;
		}

		HT.window.openEdit(url, title, action, 'grid', 800, 400, null, null, id, false);
	}
</script>