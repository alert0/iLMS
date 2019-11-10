<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springsecurity.org/jsp"%>
<html>
<head>
<title>厂外同步订单明细</title>
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
					  <div class="col-md-4"><span>订单号:</span><span id="orderNo"></span></div>
					  <div class="col-md-4"><span>供应商代码:</span><span id="supplierNo"></span></div>
					  <div class="col-md-4"><span>到货仓库:</span><span id="arrDepot"></span></div>
				</div>
			</div>
		</div>
		<div data-options="region:'center',split:true">
			<div id="grid_detail" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		var orderNo = getQueryVariable("orderNo");
		$("#orderNo").text(orderNo);
		var supplierNo = getQueryVariable("supplierNo");
		$("#supplierNo").text(supplierNo);
		var arrDepot = getQueryVariable("arrDepot");
		$("#arrDepot").text(arrDepot);
		loadGrid();
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
	
	//厂外同步订单明细
	function loadGrid(){
		$('#grid_detail').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/sw/swDemandOrder/queryDemondOrderDetailPage",
			striped : true,
			fitColumns : false,
			columns : [ [
				{field : 'purchaseNo',sortName : "purchaseNo",title : '订单行号',width : 130,align : 'center',sortable : 'true'},
				{field : 'partNo',sortName : "partNo",title : '零件编号',width : 130,align : 'center',sortable : 'true'},
				{field : 'partName',sortName : "partName",title : '零件名称',width : 200,align : 'center',sortable : 'true'}, 
				{field : 'partShortNo',sortName : "partShortNo",title : '零件简号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'orderQity',sortName : "orderQity",title : '订购数量',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'standPackage',sortName : "standPackage",title : '包装数',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'boxesNum',sortName : "boxesNum",title : '箱数',width : 130,align : 'center',sortable : 'true'},
				{field : 'cancelNum',sortName : "cancelNum",title : '取消数量',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'totalRecQty',sortName : "totalRecQty",title : '收货数量',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'receiveStatus',sortName : "receiveStatus",title : '收货状态',width : 130,align : 'center',sortable : 'true'}
			 ] ]
		}));
	}
	
</script>