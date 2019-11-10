<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>BOM基础数据查询</title>
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
								<li><span>车型:</span> 
								<input id="modelCode"
								class="inputText" type="text" name="modelCode"></li>
								<li><span>零件编号:</span>
								<input id="partNo"
								class="inputText" type="text" name="partNo"></li>
								<li><span>采购类型:</span> 
								<input id="purchaseType"
								class="inputText" type="text" name="purchaseType"></li>
								<li><span>MTO:</span> 
								<input id="mto"
								class="inputText" type="text" name="mto"></li>
								<li><span>供应商代码:</span> 
								<input id="supplierNo"
								class="inputText" type="text" name="supplierNo"></li>
								<li><span>下一落点:</span> 
								<input id="nextPlacement"
								class="inputText" type="text" name="nextPlacement"></li>
								<li><span>工序落点:</span> 
								<input id="process"
								class="inputText" type="text" name="process"></li>
								<li><span>特性:</span> 
								<input id="feature"
								class="inputText" type="text" name="feature"></li>
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
		
	});
		
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	/*搜索*/
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubModelBom/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "partNo",
			sortName : 'PART_NO',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'modelCode',sortName : "MODEL_CODE",title : '车型',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'factoryCode',sortName : "FACTORY_CODE",title : '工厂',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partNo',sortName : "PART_NO",title : '零件编号',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'purchaseType',sortName : "PURCHASE_TYPE",title : '采购类型',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'version',sortName : "VERSION",title : '版本',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商',
		    width : 100,align : 'center',sortable : 'true'},
		  /*   {field : 'inPlanForwardTime',sortName : "IN_PLAN_FORWARD_TIME",title : '是否多供应商',
			width : 100,align : 'center',sortable : 'true'}, */
			{field : 'process',sortName : "PROCESS",title : '工序落点',
			width : 100,align : 'center',sortable : 'true'},
			/* {field : 'effEndStr',sortName : "",title : '变更切换日期',
			width : 100,align : 'center',sortable : 'true'}, */
			
			{field : 'codeValueNameA',sortName : "A",title : '是否国内售后件',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'codeValueNameC',sortName : "C",title : '是否售后专用件',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'mto',sortName : "MTO",title : 'MTO',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'stage',sortName : "STAGE",title : '层级',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'version',sortName : "VERSION",title : '零件版本',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'partNameEn',sortName : "PART_NAME_EN",title : '英文名称',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'feature',sortName : "FEATURE",title : '特性',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partUnit',sortName : "PART_UNIT",title : '用量单位',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'nextPlacement',sortName : "NEXT_PLACEMENT",title : '下一落点',
			width : 100,align : 'center',sortable : 'true'},
			
			/* {field : 'minOrderNum',sortName : "MIN_ORDER_NUM",title : '变更通知单号',
		    width : 100,align : 'center',sortable : 'true'}, */
		    {field : 'effEndStr',sortName : "EFF_END",title : '变更失效日期',
		    width : 200,align : 'center',sortable : 'true'},
		    {field : 'codeValueNameB',sortName : "B",title : '是否国外售后件',
			width : 200,align : 'center',sortable : 'true'}
			
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
	
</script>
