<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>订单履历</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							  <a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)"
							  onclick="findOut()">
						         <span>搜索</span>
					          </a>
					          <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						         <span>重置</span>
					          </a>
					          <a href="javascript:;" class="btn btn-sm btn-primary fa-check"
					           onclick="openDetail('','edit')">
						         <span>编辑</span>
					          </a>
					          <a class="btn btn-sm btn-primary fa-sign-out"
								href="javascript:void(0)" onclick="excelExport()"> 
								<span>Excel导出</span>
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
								<li><span>零件编号:</span>
								<input id="partNo" class="inputText" type="text" name="partNo"></li>
								<li><span>供应商代码:</span>
								<input id="supplierNo" class="inputText" type="text" name="supplierNo"></li>
								<li><span>计算队列:</span>
								<select id="unloadPort" class="inputText" name="unloadPort"></select>
								</li>
								<li><span>零件简号:</span>
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
								<li><span>订单状态:</span>
								<select id="orderStatus" class="inputText" name="orderStatus"></select>
								</li>
							</ul>
						</form>
					</div>
				
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<!-- 下载模板的框架 -->
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>
	$(function() {
		StartOut();
		
		//计算队列下拉框
		HtUtil.loadComboBox({
			url:__ctx+"/mp/mpOrderRecordHis/getUnloadPort",
			dictArr:[{
				el : '#unloadPort',
				addBlank : true
			}]
		});
		
		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'MP_ORDER_STATUS',
				el : '#orderStatus',
				addBlank : true
			}]
		});
		
	});
	
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/mp/mpOrderRecordHis/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function openDetail(id, action) {
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		   }
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		   }
		if (records.length > 1) {
			$.messager.alert("操作提示", "请选择一条数据","info");
			return false;
		   }
	    id = records[0].planOrderId;
		var partNo = records[0].partNo;
		var orderPackage = records[0].orderPackage;
		var totalOrderBox = records[0].totalOrderBox;
		var adjBox = records[0].adjBox;
		var totalOrderNum = records[0].totalOrderNum;
		var codeValueName = records[0].codeValueName;
		if (codeValueName == '已生成' || codeValueName == '已上传') {
			$.messager.alert("操作提示", "该订单已生成或已上传","info");
			return false;
		   }
		var title = action == "edit" ? "编辑" : action == "add" ? "添加" : "查看";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = __ctx + "/mp/mpOrderRecordHis/mpOrderRecordHis" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id ; 
// 					"&partNo="+partNo+
// 					"&orderPackage="+orderPackage+
// 					"&totalOrderBox="+totalOrderBox+
// 					"&adjBox="+adjBox+
// 					"&totalOrderNum="+totalOrderNum;
		}
		HT.window.openEdit(url, title, action, 'grid', 580, 350, null, null, id, false);
	}

	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl,
			idField : "planOrderId",
			sortName : 'PLAN_ORDER_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'planOrderId',sortName : "PLAN_ORDER_ID",checkbox : true},   
			{field : 'orderNo',sortName : "ORDER_NO",title : '物流单号',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'purchaseNo',sortName : "PURCHASE_NO",title : '采购单号',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 200,align : 'center',sortable : 'true'},
			
			{field : 'unloadPort',sortName : "UNLOAD_PORT",title : '计算队列',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partNo',sortName : "PART_NO",title : '零件编号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'logisticsFlag',sortName : "LOGISTICS_FLAG",title : '物流标识',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'groupId',sortName : "GROUP_ID",title : '分组号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'logisticsOrder',sortName : "LOGISTICS_ORDER",title : '临时单号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'drSortIdStart',sortName : "DR_SORT_ID_START",title : 'D_R起始号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'drSortIdEnd',sortName : "DR_SORT_ID_END",title : 'D_R结束号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'lrSortIdStart',sortName : "LR_SORT_ID_START",title : 'L_起始号',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'lrSortIdEnd',sortName : "LR_SORT_ID_END",title : 'L_结束号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'necessaryOrderResidual',sortName : "NECESSARY_ORDER_RESIDUAL",title : '系统余量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'adjDiffNum',sortName : "ADJ_DIFF_NUM",title : '计划变更数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'defectNum',sortName : "DEFECT_NUM",title : '不良数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'safeNum',sortName : "SAFE_NUM",title : '安全库存',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'necessaryNetNum',sortName : "NECESSARY_NET_NUM",title : '净需求',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'necessaryPlanNum',sortName : "NECESSARY_PLAN_NUM",title : '计划订购数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'orderNum',sortName : "ORDER_NUM",title : '订购数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'orderPackage',sortName : "ORDER_PACKAGE",title : '包装数',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'adjOrderNum',sortName : "ADJ_ORDER_NUM",title : '调整订购数量',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'totalOrderNum',sortName : "TOTAL_ORDER_NUM",title : '总订购数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'adjBox',sortName : "ADJ_BOX",title : '调整箱数',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'totalOrderBox',sortName : "TOTAL_ORDER_BOX",title : '总订购箱数',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'necessaryRealResidualNum',sortName : "NECESSARY_REAL_RESIDUAL_NUM",title : '剩余系统余量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'arriveTimeStr',sortName : "ARRIVE_TIME",title : '到货时间',
			width : 200,align : 'center',sortable : 'true'},
			
			{field : 'codeValueName',sortName : "CODE_VALUE_NAME",title : '订单状态',
			width : 100,align : 'center',sortable : 'true'}
			 ] ],
			
			autoScroll: true,
			onLoadSuccess : function(data) {
				/*无数据时加载滚动条*/
				if(data.total==0)
				{
				var dc = $(this).data('datagrid').dc;
				var header2Row = dc.header2.find('tr.datagrid-header-row');
				dc.body2.find('table').append(header2Row.clone().css({
					"visibility":"hidden"
				}));
				}
				handGridLoadSuccess();
			}
		}));
	}	
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpOrderRecordHis/downloadMpOrderRecordHisModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
