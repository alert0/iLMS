<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>物料供应商关系查询</title>
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
								<li><span>供应商代码:</span> 
								<input id="supplierNo"
								class="inputText" type="text" name="supplierNo"></li>
								<li><span>零件编号:</span>
								<input id="partNo"
								class="inputText" type="text" name="partNo"></li>
								<li><span>零件简号:</span> 
								<input id="partShortNo"
								class="inputText" type="text" name="partShortNo"></li>
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
		var onclickUrl = encodeURI(__ctx+"/pub/pubPartSupplier/curdlistJson");
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
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地代码',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partNo',sortName : "PART_NO",title : '零件编号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'minOrderNum',sortName : "MIN_ORDER_NUM",title : '最小采购量',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'standardPackage',sortName : "STANDARD_PACKAGE",title : '规格包装数',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'inPlanForwardTime',sortName : "IN_PLAN_FORWARD_TIME",title : '提前期',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'effStartStr',sortName : "EFF_START",title : '生效日期',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'effEndStr',sortName : "EFF_END",title : '失效日期',
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
	
</script>
