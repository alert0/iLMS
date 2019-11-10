<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动订单查询</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search">
							<span>查询</span>
						</a>
						<a class="btn btn-sm btn-primary fa-sign-out"
							onclick="excelExport()"><span>导出</span>
						</a>
						<a class="btn btn-sm btn-primary fa-sign-out"
							onclick="excelExport_detail()"><span>明细导出</span>
						</a>
						<a class="btn btn-sm btn-primary fa-rotate-left">
							<span>重置</span>
						</a>
						<a class="btn btn-sm btn-primary fa-print" onclick="print('order')">
							<span>订单打印</span>
						</a>
						<a class="btn btn-sm btn-primary fa-print" onclick="print('label')">
							<span>标签打印</span>
						</a>
						<a class="btn btn-sm btn-primary fa-print" onclick="print('ins')">
							<span>配送单打印</span>
						</a>
						<a class="btn btn-sm btn-primary fa-print" onclick="print('tpLabel')">
							<span>托盘标签打印</span>
						</a>
					</div>
					<div class="tools">
						<a class="collapse">
							<i class="bigger-190 fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>车间:</span>
								<select id="workcenter" class="inputText" name="workcenter"></select>
							</li>
							<li><span>信息点:</span>
								<select id="planCode" class="inputText" name="planCode"></select>							
							</li>
							<li><span>出货仓库:</span>
								<select id="shipDepot" class="inputText" name="shipDepot"></select>
							</li>
							<li><span>供应商代码:</span><input class="inputText" type="text" name="supplierNo"></li>
							<li><span>备件批次:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="sprepareBatchNo"></li>
							<li><span>至:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="eprepareBatchNo"></li>
							<li><span>创建日期:</span><input class="inputText datetime" type="text" name="creationTimeFrom"></li>
							<li><span>至:</span><input class="inputText datetime" name="creationTimeTo"></li>	
							<li><span>打印状态:</span>
								<select id="printStatus" class="inputText" name="printStatus"></select>
							</li>
							<li><span>物流单号:</span><input class="inputText" type="text" name="orderNo"></li>
							<li><span>发货批次:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="deliveryBatchNo"></li>
							<li><span>至:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="deliveryBatchNoTo"></li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
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
				typeKey : 'PUB_WORKCENTER',
				el : '#workcenter',
				addBlank : true
			},{
				typeKey : 'PUB_SHIP_DEPOT',
				el : '#shipDepot',
				addBlank : true
			},{
				typeKey : 'PUB_PRINT_STATUS',
				el : '#printStatus',
				addBlank : true
			}]
		});
		
		//信息点
		HtUtil.loadComboBox({
			url : __ctx + '/jit/jitReckon/loadPlanCodeComboData',
			param : {
				planCodeType : 'JITI'
			},
			valueKey : 'valueKey',
			textKey : 'valueName',
			dictArr:[{
				typeKey:'PLAN_CODE',
				el : '#planCode', 
				addBlank : true
			}]
		});
		
		loadGrid();
		
	});
	
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitOrder/queryJitOrderPage",
			striped : true,
			fitColumns : false,
	        columns: [[
	           {field : 'A',sortName : "A",checkbox : true},
	           {
	   				field : 'colManage',
	   				title : '操作',
	   				align : 'center',
	   				formatter : function(value, row, index) {
	   					var result = "<a class='btn btn-default fa' onClick='openDetail(\""+ row.orderNo +"\",\""  + row.shipDepot
	   							+ "\", \""+ row.eprepareBatchNo +"\" ,\""+ row.deliveryBatchNo +"\");' >明细</a>";
	   					return result;
	   				}
	   			},
       		   { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
               { field: 'shipDepot',sortName:"shipDepot", title: '出货仓库',width : 150, align: 'center', sortable: 'true'},
               { field: 'orderNo',sortName:"orderNo" , title: '物流单号',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '是否急件',width : 150, align: 'center', sortable: 'true'},
               { field: 'supplierNo',sortName:"supplierNo" , title: '供应商代码',width : 150, align: 'center', sortable: 'true'},
               { field: 'supplierName',sortName:"supplierName" , title: '供应商名称',width : 150, align: 'center', sortable: 'true'},
               { field: 'dispatchProductSeqno',sortName:"dispatchProductSeqno" , title: '发车批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'sprepareProductSeqno',sortName:"sprepareProductSeqno" , title: '备件批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'deliveryProductSeqno',sortName:"deliveryProductSeqno" , title: '发货批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'arriveProductSeqno',sortName:"arriveProductSeqno" , title: '到货批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '看板名称',width : 150, align: 'center', sortable: 'true'},
               { field: 'printStatus',sortName:"printStatus" , title: '打印状态',width : 150, align: 'center', sortable: 'true'},
               { field: 'printTime',sortName:"printTime" , title: '打印时间',width : 150, align: 'center', sortable: 'true'},
               { field: 'arriveStatus',sortName:"arriveStatus" , title: '收货状态',width : 150, align: 'center', sortable: 'true'},
               { field: 'creationTime',sortName:"creationTime" , title: '创建时间',width : 150, align: 'center', sortable: 'true'},
               { field: 'deliveryBatchNo',sortName:"deliveryBatchNo" , title: '发货批次',width : 150,hidden: true},
               { field: 'eprepareBatchNo',sortName:"eprepareBatchNo" , title: '最后备件批次',width : 150,hidden: true},
               ]]
		}));
	}
	
	//拉动订单导出
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitOrder/downloadJitOrder');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//拉动订单明细导出
	function excelExport_detail(){
		var downurl = encodeURI(__ctx + '/jit/jitOrder/downloadJitOrderDetail');
		$('#downloadiframe_detail').attr('src', downurl);
	}
	
	//查询订单明细 (订单号,出货仓库,最后备件批次,发货批次)
	function openDetail(orderNo, shipDepot, eprepareBatchNo, deliveryBatchNo){
		var title = "订单明细";
		var url = "jitOrderDetail";
		if(!$.isEmpty(orderNo)){
			url += '?orderNo=' + orderNo;
			url += '&shipDepot=' + shipDepot;
			url += '&eprepareBatchNo=' + eprepareBatchNo;
			url += '&deliveryBatchNo=' + deliveryBatchNo;
		}
		HT.window.openEdit(url, title, "Get", 'grid', 400, 300, null, null, orderNo, true);
	}
	
	//订单打印
	function print(printType){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要打印的订单，请确认！");
			return false;
		}
		var orderNoArr = [];
		for (var i = 0; i < records.length; i++) {
			orderNoArr.push(records[i].orderNo);
		}
		var orderNo = orderNoArr.join(",");
		if(printType == 'order'){
			var downurl =  __ctx+"/jit/jitOrder/jitOrderPrint?orderNo=" + orderNo;
		}else if(printType == 'label'){
			var downurl =  __ctx+"/jit/jitOrder/jitOrderPrintLabel?orderNo=" + orderNo;
		}else if(printType == 'ins'){
			var downurl =  __ctx+"/jit/jitOrder/jitOrderPrintIns?orderNo=" + orderNo;
		}else if(printType == 'tpLabel'){
			var downurl =  __ctx+"/jit/jitOrder/jitOrderPrintTpLabel?orderNoStr=" + '1001';
		}
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
	}
	
</script>