<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动包装明细查询</title>
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
								<li><span>车间:</span>
								<select id="workcenter" class="inputText" name="workcenter"></select>
								</li>
								
								<li><span>出货仓库:</span>
								<select id="shipDepot" class="inputText" name="shipDepot"></select>
								</li>
								
								<li><span>零件编号:</span> 
								<input id="partNo" class="inputText" type="text" name="partNo"></li>
								
								<li><span>零件简号:</span> 
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
								
								<li><span>组单状态:</span>
								<select id="orderDealFlag" class="inputText" name="orderDealFlag"></select>
								</li>
								
								<li><span>信息点:</span>
								<select id="planCode" class="inputText" name="planCode"></select>
								</li>
								
								<li><span>组单时间</span>
								<input  name="orderDealTimeStrStart"  class="inputText date" /></li>
								<li><span>至: </span>
								<input  name="orderDealTimeStrEnd" class="inputText date" /></li>
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
		
		//车间    数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'PUB_WORKCENTER',
				el : '#workcenter',
				addBlank : true
			}]
		});
		
		//出货仓库    数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'PUB_SHIP_DEPOT',
				el : '#shipDepot',
				addBlank : true
			}]
		});
		
		//组单状态     数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'JIT_GEN_ORDER_STATUS',
				el : '#orderDealFlag',
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
		
	});
		
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	/*搜索*/
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/jit/jitPkgReq/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'codeValueNameC',sortName : "WORKCENTER",title : '车间',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'planCode',sortName : "PLAN_CODE",title : '信息点',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'orderNo',sortName : "ORDER_NO",title : '物流单号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partNo',sortName : "PART_NO",title : '零件编号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'partName',sortName : "PART_NAME",title : '零件名称',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'location',sortName : "LOCATION",title : '落点',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'requireNum',sortName : "REQUIRE_NUM",title : '配送量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'standardPackage',sortName : "STANDARD_PACKAGE",title : '规格包装数',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'distriPackage',sortName : "DISTRI_PACKAGE",title : '订购包装数',
			width : 100,align : 'center',sortable : 'true'},
			
	/* 		{field : 'supLeaderTel',sortName : "SUP_LEADER_TEL",title : '计划过点批次进度',
		    width : 100,align : 'center',sortable : 'true'}, */
		    {field : 'prepareProductSeqno',sortName : "PREPARE_PRODUCT_SEQNO",title : '备件批次进度',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'deliveryProductSeqno',sortName : "DELIVERY_PRODUCT_SEQNO",title : '发货批次进度',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'arriveProductSeqno',sortName : "ARRIVE_PRODUCT_SEQNO",title : '到货批次进度',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'distriProductSeqno',sortName : "DISTRI_PRODUCT_SEQNO",title : '配送批次进度',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'sProductSeqno',sortName : "S_PRODUCT_SEQNO",title : '开始使用车辆',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'eProductSeqno',sortName : "E_PRODUCT_SEQNO",title : '结束使用车辆',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地代码',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'codeValueNameD',sortName : "ORDER_DEAL_FLAG",title : '组单状态',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'orderDealTimeStr',sortName : "ORDER_DEAL_TIME",title : '组单时间',
		    width : 100,align : 'center',sortable : 'true'}
			
			] ],
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
	
	/*数据导出*/
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitPkgReq/downloadJitPkgReqModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
