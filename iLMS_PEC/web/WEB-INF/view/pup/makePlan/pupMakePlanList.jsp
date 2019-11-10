<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>取货计划生成</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
							<span>查询</span>
					    </a>
			        	<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
				        	<span>重置</span>
			         	</a>
			         	<a class="btn btn-sm btn-primary fa-check" id="add" href="javascript:;" onclick="makeLogisticsPlanFn()">
				        	<span>生成物流计划</span>
			         	</a>
			         	<a class="btn btn-sm btn-primary fa-check" href="javascript:void(0)" id="remove" action="/pup/pickupTime/remove">
				        	<span>发布</span>
			         	</a>
			         	<a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport(1)"> 
							<span>导出趟次范围表</span>
					 	</a> 
					 	<a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport(2)"> 
							<span>导出取货时间</span>
					 	</a>
					 	<a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport(3)"> 
							<span>导出商贸DCS</span>
					 	</a>
					 	<a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport(4)"> 
							<span>导出供应商备货数据</span>
					 	</a>
					 	<a class="btn btn-primary btn-sm fa-sign-in"  
						    href="javascript:void(0)" onclick="openExcelImport()">
					         <span>导入备货信息</span>
				        </a>
					 	<a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport(5)"> 
							<span>导出收货数据</span>
					 	</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
								<li><span>订单物流模式</span>
									<input id="pickupType" class="inputText" type="text" name="pickupType">
								</li>
								<li><span>区域</span>
									<input id="area" class="inputText" type="text" name="area">
								</li>
								<li><span>集货线路</span>
									<input id="routeCode" class="inputText" type="text" name="routeCode">
								</li>
								<li><span>车型</span>
									<input id="carType" class="inputText" type="text" name="carType">
								</li>
							</ul>
							<ul style="margin-left: -26px">
								<li><span><font color="red">*</font>计划取货日期</span>
									<input id="pickDateStart" class="inputText date" type="text" name="pickDateStart">
								</li>
								<li><span><font color="red">*</font>到</span>
									<input id="pickDateEnd" class="inputText date" type="text" name="pickDateEnd">
								</li>
							</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>	
	$(function() {
		loadGrid();
		loadQueryTime();
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "queryForPage",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',sortName : "ID",checkbox : true},
				{field : 'area',sortName : "AREA",title : '区域',align : 'center',sortable : 'true'},
			    {field : 'todayNo',sortName : "TODAY_NO",title : '卸货地点',align : 'center',sortable : 'true'},
			    {field : 'pickupType',sortName : "PICKUP_TYPE",title : '订单物流模式',align : 'center',sortable : 'true'},
				{field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',align : 'center',sortable : 'true'},
				{field : 'totalNo',sortName : "TOTAL_NO",title : '累计车次',align : 'center',sortable : 'true'},
				{field : 'mergeNo',sortName : "MERGE_NO",title : '合并车次',align : 'center',sortable : 'true'},
				{field : 'workday',sortName : "WORKDAY",title : '工作日',align : 'center',sortable : 'true'},
				{field : 'todayNo',sortName : "TODAY_NO",title : '当日车次',align : 'center',sortable : 'true'},
				{field : 'pickDate',sortName : "PICK_TIME",title : '计划取货日期',align : 'center',sortable : 'true'},
				{field : 'pickTime',sortName : "PICK_TIME",title : '计划取货时间',align : 'center',sortable : 'true'},
				{field : 'arriveDate',sortName : "ARRIVE_TIME",title : '计划到货日期',align : 'center',sortable : 'true'},
				{field : 'arriveTime',sortName : "ARRIVE_TIME",title : '计划到货时间',align : 'center',sortable : 'true'},
				{field : 'assembleDate',sortName : "ASSEMBLE_TIME",title : '计算装配日期',align : 'center',sortable : 'true'},
				{field : 'assembleTime',sortName : "ASSEMBLE_TIME",title : '计算装配时间',align : 'center',sortable : 'true'}
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
	//生成物流计划
	function makeLogisticsPlanFn(){
		$.ajax({ 
		    url : "${pageContext.request.contextPath}/pup/makePlan/makeLogisticsPlan",  
		    async : false,
		    success : function(data) {
		    	var result = new com.hotent.form.ResultMessage(data);
		    	if(result.isSuccess()){
			    	$.topCall.success(result.getMessage());
			    	loadGrid();
		    	}else{
			    	$.topCall.error(result.getMessage());
			    }
		    }
		});
	}
	function loadQueryTime(){
		//查询结束时间控件默认当前时间
		var nowDate = new Date();
		var endDay = ("0" + nowDate.getDate()).slice(-2);
		var EndMonth = ("0" + (nowDate.getMonth() + 1)).slice(-2);
		var today = nowDate.getFullYear()+"-"+(EndMonth)+"-"+(endDay);
		$('#pickDateEnd').val(today);
		//查询开始时间控件默认一周前时间
		var oneWeekAgoDate = new Date(nowDate-7*24*3600*1000);
		var startDay = ("0" + oneWeekAgoDate.getDate()).slice(-2);
		var startMonth = ("0" + (oneWeekAgoDate.getMonth() + 1)).slice(-2);
		var oneWeekAgoDay = oneWeekAgoDate.getFullYear()+"-"+(startMonth)+"-"+(startDay);
        $('#pickDateStart').val(oneWeekAgoDay);
	}
	
	function excelExport(btnNum){
		var downurl;
		var row = $('#grid').datagrid('getData').total;
		
		if(btnNum == 1){
			$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
				if(r){
					var downurl = encodeURI(__ctx + '/pup/makePlan/exportTripTime');
					$('#downloadiframe').attr('src', downurl);
				}
			});
		}
		if(btnNum == 2){
			$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
				if(r){
					var downurl = encodeURI(__ctx + '/pup/makePlan/exportPickupTimes');
					$('#downloadiframe').attr('src', downurl);
				}
			});
		}
		if(btnNum == 3){
			$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
				if(r){
					var downurl = encodeURI(__ctx + '/pup/makePlan/exportPlanDCS');
					$('#downloadiframe').attr('src', downurl);
				}
			});
		}
		if(btnNum == 4){
			$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
				if(r){
					if(row < 1){
						$.topCall.warn("没有数据可以导出");
					}
					var downurl = encodeURI(__ctx + '/pup/makePlan/exportSupplierStockNum');
					$('#downloadiframe').attr('src', downurl);
				}
			});
		}
		if(btnNum == 5){
			$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
				if(r){
					var downurl = encodeURI(__ctx + '/pup/makePlan/exportPickData');
					$('#downloadiframe').attr('src', downurl);
				}
			});
		}
	}
	
	function openExcelImport(){
		var url = __ctx + "/pup/makePlan/pupMakePlanImport";
		HT.window.openEdit(url, '导入修改数据', null, 'grid', 400, 300, null, null, null, true);
	}
</script>
