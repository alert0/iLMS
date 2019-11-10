<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springsecurity.org/jsp"%>
<html>
<head>
<title>厂外同步订单</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a class="btn btn-sm btn-primary fa-sign-out"
						onclick="excelExport()"><span>导出</span>
					</a>
					<a class="btn btn-sm btn-primary fa-sign-out"
						onclick="excelExport_detail()"><span>明细导出</span>
					</a>
					<a class="btn btn-sm btn-primary fa-print" onclick="print()">
						<span>订单打印</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
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
					<ul>
						<li><span>出货地代码:</span><input class="inputText" type="text" name="supFactory"></li>
						<li><span>物流单号:</span><input class="inputText" type="text" name="orderNo"></li>
						<li><span>创建日期:</span><input class="inputText datetime" type="text" name="creationTimeFrom"></li>
						<li><span>至:</span><input class="inputText datetime" type="text" name="creationTimeTo"></li>
						<li><span>打印状态:</span>
							<select id="printStatus" class="inputText" name="printStatus"></select>
						</li>
						<li><span>装车代码:</span><input class="inputText" type="text" name="routeCode"></li>
						<li><span>到货批次:</span><input class="inputText" type="text" name="arriveBatchNo" onkeyup="this.value=this.value.replace(/\D/g,'')"></li>
						<li><span>至:</span><input class="inputText" type="text" name="arriveBatchNoTo" onkeyup="this.value=this.value.replace(/\D/g,'')"></li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
	<iframe id="downloadiframe_detail" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		//初始化下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [{
				typeKey : 'PUB_PRINT_STATUS',
				el : '#printStatus',
				addBlank : true
			}]
		});
		
		loadGrid();
	});
	
	//厂外同步订单
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jiso/order/queryJisoOrderPage",
			striped : true,
			fitColumns : false,
			columns : [ [
				{field : '',sortName : "",checkbox : true},
				{
					field : 'colManage',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var result = "<a class='btn btn-default fa' onClick='openDetail(\""+ row.orderNo +"\",\""  + row.supplierNo
								+ "\",\""+ row.arrDepot +"\");' >明细</a>";
						return result;
					}
				},
				{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',width : 130,align : 'center',hidden: true}, 
				{field : 'supFactory',sortName : "supFactory",title : '出货地代码',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'arrDepot',sortName : "arrDepot",title : '到货仓库',width : 130,align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "routeCode",title : '装车代码',width : 150,align : 'center',sortable : 'true'}, 
				{field : 'carBatch',sortName : "carBatch",title : '车次',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'orderNo',sortName : "orderNo",title : '物流单号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'purchaseOrderno',sortName : "purchaseOrderno",title : '采购单号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'prepareProductSeqno',sortName : "prepareProductSeqno",title : '备件批次进度',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'deliveryProductSeqno',sortName : "deliveryProductSeqno",title : '发货批次进度',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'arriveProductSeqno',sortName : "arriveProductSeqno",title : '到货批次进度',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'distriProductSeqno',sortName : "distriProductSeqno",title : '配送批次进度',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'printTime',sortName : "printTime",title : '打印时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'arriveStatus',sortName : "arriveStatus",title : '到货状态',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'isManuDeal',sortName : "isManuDeal",title : '是否手工组单',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqUser',sortName : "manuReqUser",title : '手工组单人',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqTime',sortName : "manuReqTime",title : '手工组单申请时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'creationTime',sortName : "creationTime",title : '创建时间',width : 130,align : 'center',sortable : 'true'}
				 ] ]
		}));
	}
	
	//查询订单明细 (订单号,供应商代码,到货仓库)
	function openDetail(orderNo, supplierNo, arrDepot){
		var title = "订单明细";
		var url = "JisoOrderDetail";
		if(!$.isEmpty(orderNo)){
			url += '?orderNo=' + orderNo;
			url += '&supplierNo=' + supplierNo;
			url += '&arrDepot=' + arrDepot;
		}
		HT.window.openEdit(url, title, "Get", 'grid', 400, 300, null, null, orderNo, true);
	}
	
	//导出厂外同步订单
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jiso/order/downloadJisoOrder');
		$('#downloadiframe').attr('src', downurl);
	}

	//导出厂外订单明细
	function excelExport_detail(){
		var downurl = encodeURI(__ctx + '/jiso/order/downloadJisoOrderDetail');
		$('#downloadiframe_detail').attr('src', downurl);
	}
	
	//订单打印
	function print(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要打印的订单，请确认！");
			return false;
		}
		var orderNo = records[0].orderNo;
		var orderNoArr = [];
		for (var i = 0; i < records.length; i++) {
			orderNoArr.push(records[i].orderNo);
		}
		var orderNo = orderNoArr.join(","); 
		if(0 != orderNoArr.length){
			var downurl =  __ctx+"/jiso/order/jisoOrderPrint?orderNo=" + orderNo;
			var def = {
		      title : "打印",
		      width : 800,
		      height : 500,
		      modal : true,
		      resizable : true,
		      buttons : []
		    };  
	
		    dialog = $.topCall.dialog({
		      src : downurl,
		      base : def
		    });
		}else{
			$.topCall.alert("提示", "没有选择需要打印的订单，请确认！");
		}
	}
	
</script>