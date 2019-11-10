<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>手工调整订单维护</title>
<%@include file="/commons/include/list.jsp"%>
</head>

<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
					         <span>查询</span>
				          </a>
				          <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
					         <span>重置</span>
				          </a>
				           <a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" id="remove" action="/pup/manualOrder/deleteManualOrder">
					         <span>删除</span>
				          </a>
				           <a class="btn btn-primary btn-sm fa-sign-in"  
						    href="javascript:void(0)" onclick="openExcelImport()">
					         <span>批量导入</span>
				           </a>
				          <a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport()"> 
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
						<li><span>集货线路</span>
							<input id="routeCode" class="inputText" type="text" name="routeCode">
						</li>
						<li><span>车型</span>
							<input id="carType" class="inputText" type="text" name="carType">
						</li>
						<li><span>物流单号</span>
							<input id="orderNo" class="inputText" type="number" name="orderNo">
						</li>
					</ul>
					<ul style="margin-left: -26px">
						<li><span><font color="red">*</font>工作日</span>
							<input id="workDayStart" class="inputText date" type="text" name="workDayStart">
						</li>
						<li><span><font color="red">*</font>到</span>
							<input id="workDayEnd" class="inputText date" type="text" name="workDayEnd">
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
		loadQueryTime();
	});
	
	function loadQueryTime(){
		//查询结束时间控件默认当前时间
		var nowDate = new Date();
		var endDay = ("0" + nowDate.getDate()).slice(-2);
		var EndMonth = ("0" + (nowDate.getMonth() + 1)).slice(-2);
		var today = nowDate.getFullYear()+"-"+(EndMonth)+"-"+(endDay);
		$('#workDayEnd').val(today);
		//查询开始时间控件默认一周前时间
		var oneWeekAgoDate = new Date(nowDate-7*24*3600*1000);
		var startDay = ("0" + oneWeekAgoDate.getDate()).slice(-2);
		var startMonth = ("0" + (oneWeekAgoDate.getMonth() + 1)).slice(-2);
		var oneWeekAgoDay = oneWeekAgoDate.getFullYear()+"-"+(startMonth)+"-"+(startDay);
        $('#workDayStart').val(oneWeekAgoDay);
	}
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listManualOrder",
			idField : "purchaseNo",
			sortName : 'PURCHASE_NO',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'true'},
				{field : 'pickupFlag',sortName : "PICKUP_FLAG",title : '取货标识',align : 'center',sortable : 'true'},
			    {field : 'area',sortName : "AREA",title : '区域',align : 'center',sortable : 'true'},
				{field : 'orderDesc',sortName : "ORDER_DESC",title : '订单说明',align : 'center',sortable : 'true'},
				{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',align : 'center',sortable : 'true'},
				{field : 'pickupType',sortName : "PURCHASE_NO",title : '订单物理模式',align : 'center',sortable : 'true'},
				{field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
				{field : 'orderNo',sortName : "ORDER_NO",title : '物流单号',align : 'center',sortable : 'true'},
				{field : 'purchaseNo',sortName : "PURCHASE_NO",title : '采购单号',align : 'center',sortable : 'true'},
				{field : 'workday',sortName : "WORKDAY",title : '工作日',align : 'center',sortable : 'true'},
				{field : 'pickDate',sortName : "PICK_DATE",title : '计划取货日期',align : 'center',sortable : 'true'},
				{field : 'pickTime',sortName : "PICK_DATE",title : '计划取货时间',align : 'center',sortable : 'true'},
				{field : 'arriveDate',sortName : "ARRIVE_DATE",title : '计划到货日期',align : 'center',sortable : 'true'},
				{field : 'arriveTime',sortName : "ARRIVE_DATE",title : '计划到货时间',align : 'center',sortable : 'true'},
				{field : 'orderDate',sortName : "ORDER_DATE",title : '订单到货时间',align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',align : 'center',sortable : 'true'},
				{field : 'totalNo',sortName : "TOTAL_NO",title : '累计车次',align : 'center',sortable : 'true'},
				{field : 'todayNo',sortName : "TODAY_NO",title : '当日车次',align : 'center',sortable : 'true'},
				{field : 'mergeNo',sortName : "MERGE_NO",title : '合并车次',align : 'center',sortable : 'true'}
				]],
				onLoadSuccess : function (data) {
					    if(data.total==0){
		                    var dc = $(this).data('datagrid').dc;
		                    var header2Row = dc.header2.find('tr.datagrid-header-row');
		                    dc.body2.find('table').append(header2Row.clone().css({
		                    	"visibility":"hidden"
		                    }));
						}
					},
			    onClickRow : function(index, row) {
		    		var paramGroup = row["paramGroup"];
					loadRightGrid(paramGroup);
					$('#btn').show();
					$("#btn").click(function(){
						openDetail(paramGroup,'item','add');
					});
					$('#rightGrid').show();
				}
		}));
	}
	
	function openDetail(id, action) {
		console.log(id);
		var title = action == "edit" ? "编辑取货信息" : action == "add" ? "添加取货信息" : "查看取货信息详情";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="pupPickupTime" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, false);
	}
	
	function openExcelImport(){
		var url = __ctx + "/pup/manualOrder/pupManualOrderImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	function excelExport(){
		var downurl = encodeURI(__ctx + '/pup/manualOrder/exportDataForExcel');
		$('#downloadiframe').attr('src', downurl);
	}
</script>
