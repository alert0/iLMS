<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>库存管理</title>
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
					<a class="btn btn-sm btn-primary fa-history" href="javascript:;" onclick="openDetail()">
						<span>修改</span>
					</a>
			       	<a id="signOut" class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()"> 
						<span>导出</span>
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
						<li><span>零件编号</span>
							<input id="partNo" class="inputText" type="text" name="partNo">
						</li>
						<li><span>零件简号</span>
							<input id="partShortNo" class="inputText" type="text" name="partShortNo">
						</li>
						<li><span>零件名称</span>
							<input id="partName" class="inputText" type="text" name="partName">
						</li>
					</ul>
					<ul style="margin-left: -26px">
						<li><span>在库数量</span>
							<input id="stock" class="inputText" type="number" min="0" max="9999999999" name="stock">
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
			url : "queryStockForPage",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'true'},
			    {field : 'wareCode',sortName : "WARE_CODE",title : '仓库代码',align : 'center',sortable : 'true'},
			    {field : 'wareName',sortName : "WAREHOUSE_NAME",title : '仓库名称',align : 'center',sortable : 'true'},
				{field : 'partNo',sortName : "PART_NO",title : '零件编号',align : 'center',sortable : 'true'},
				{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',align : 'center',sortable : 'true'},
				{field : 'partName',sortName : "PART_NAME_CN",title : '零件名称',align : 'center',sortable : 'true'},
				{field : 'baseLocation',sortName : "BASE_LOCATION",title : '库位',align : 'center',sortable : 'true'},
				{field : 'safeStock',sortName : "SAFE_STOCK",title : '安全库存数',align : 'center',sortable : 'true'},
				{field : 'stock',sortName : "STOCK",title : '在库数量',align : 'center',sortable : 'true'}
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
	
	function openDetail() {
		var title = "修改零件组信息";
		var url="invStockEdit";
		var rows = $("#grid").datagrid('getSelections');
		
		if(rows.length > 1){
			$.topCall.warn("已选择多项数据，请选择一项进行操作");
			return;
		}
		
		if(rows.length < 1){
			$.topCall.warn("请选择需要修改的数据");
			return;
		}
		
		var id = $('#grid').datagrid('getSelected').id;
		url+='?id=' + id;

		HT.window.openEdit(url, title, 'Edit', 'grid', 400, 300, null, null, id, false);
	}
	
	function excelExport(){
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
		    	var downurl = encodeURI(__ctx + '/inv/stock/exportForExcel');
				$('#downloadiframe').attr('src', downurl);		
		    }else{
		    	HT.window.closeEdit(true,'grid');
		    }
		});
	}
</script>