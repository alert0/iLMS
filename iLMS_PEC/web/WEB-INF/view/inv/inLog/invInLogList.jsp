<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>入库查询</title>
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
						<li><span>物流单号</span>
							<input id="orderNo" class="inputText" type="text" name="orderNo">
						</li>
						<li><span>入库单号</span>
							<input id="recNo" class="inputText" type="text" name="recNo">
						</li>
						<li><span>零件编号</span>
							<input id="partNo" class="inputText" type="text" name="partNo">
						</li>
						<li><span>仓库代码</span>
							<input id="depotNo" class="inputText" type="text" name="depotNo">
						</li>
					</ul>
					<ul style="margin-left: -26px">
						<li><span>零件简号</span>
							<input id="partShortNo" class="inputText" type="text" name="partShortNo">
						</li>
						<li><span>零件名称</span>
							<input id="partName" class="inputText" type="text" name="partName">
						</li>
						<li><span>入库日期</span>
							<input id="inTime" class="inputText date" type="text" name="inTime">
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
			url : "queryForPage",
			idField : "recNo",
			sortName : 'REC_NO',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
			    {field : 'depotNo',sortName : "DEPOT_NO",title : '仓库代码',align : 'center',sortable : 'true'},
			    {field : 'recNo',sortName : "REC_NO",title : '入库单号',align : 'center',sortable : 'true'},
				{field : 'orderNo',sortName : "ORDER_NO",title : '物流单号',align : 'center',sortable : 'true'},
				{field : 'partNo',sortName : "PART_NO",title : '零件编号',align : 'center',sortable : 'true'},
				{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',align : 'center',sortable : 'true'},
				{field : 'partName',sortName : "PART_NAME_CN",title : '零件名称',align : 'center',sortable : 'true'},
				{field : 'recQty',sortName : "REC_QTY",title : '入库数量',align : 'center',sortable : 'true'},
				{field : 'inTime',sortName : "CREATION_TIME",title : '入库时间',align : 'center',sortable : 'true'}
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
	
	function excelExport(){
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
		    	var downurl = encodeURI(__ctx + '/inv/inLog/exportForExcel');
				$('#downloadiframe').attr('src', downurl);		
		    }else{
		    	HT.window.closeEdit(true,'grid');
		    }
		});
	}
</script>