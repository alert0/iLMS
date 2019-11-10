<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springsecurity.org/jsp"%>
<html>
<head>
<title>拉动订单明细查询</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
</head>
<body>
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'north',split:true" style="background-color: #F5F5F5;height:30px;" >
			<div class="container-fluid" style="margin-top: 3px;">
				<div class="row">
					  <div class="col-md-3"><span>订单号:</span><span id="orderNo"></span></div>
					  <div class="col-md-3"><span>出货仓库:</span><span id="shipDepot"></span></div>
					  <div class="col-md-3"><span>最后备件批次:</span><span id="eprepareBatchNo"></span></div>
					  <div class="col-md-3"><span>发货批次:</span><span id="deliveryBatchNo"></span></div>
				</div>
			</div>
		</div>
		<div data-options="region:'center',split:true">
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		var orderNo = getQueryVariable("orderNo");
		$("#orderNo").text(orderNo);
		var shipDepot = getQueryVariable("shipDepot");
		$("#shipDepot").text(shipDepot);
		var eprepareBatchNo = getQueryVariable("eprepareBatchNo");
		$("#eprepareBatchNo").text(eprepareBatchNo);
		var deliveryBatchNo = getQueryVariable("deliveryBatchNo");
		$("#deliveryBatchNo").text(deliveryBatchNo);
		loadGrid(orderNo);
	});
	
	//js获取url参数
	function getQueryVariable(variable){
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return("");
	}
	
	//厂外同步指示票
	function loadGrid(orderNo){
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jit/jitOrder/queryJitOrderDetailPage?orderNo=" + orderNo,
			striped : true,
			fitColumns : false,
			//pagePosition : 'top',
			columns : [ [
				{field : 'orderNo',sortName : "orderNo",title : '订单号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'orderRowno',sortName : "orderRowno",title : '行号',width : 130,align : 'center',sortable : 'true'},
				{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',width : 200,align : 'center',sortable : 'true'}, 
				{field : 'partNo',sortName : "partNo",title : '零件编号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'partShortNo',sortName : "partShortNo",title : '零件简号',width : 130,align : 'center',sortable : 'true'},
				{field : 'partName',sortName : "partName",title : '零件名称',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'location',sortName : "location",title : '落点',width : 150,align : 'center',sortable : 'true'},
				{field : 'standardPackage',sortName : "standardPackage",title : '规格包装数',width : 150,align : 'center',sortable : 'true'},
				{field : 'boxNum',sortName : "boxNum",title : '箱数',width : 150,align : 'center',sortable : 'true'},
				{field : 'prepareNum',sortName : "prepareNum",title : '配送量',width : 150,align : 'center',sortable : 'true'},
				{field : 'arriveStatus',sortName : "arriveStatus",title : '收货状态',width : 150,align : 'center',sortable : 'true'}
			 ] ]
		}));
		
	}
</script>