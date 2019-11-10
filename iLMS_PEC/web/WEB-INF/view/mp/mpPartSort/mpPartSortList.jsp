<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>零件分组查询</title>
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
								<li><span>供应商代码:</span>
								<input id="supplierNo" class="inputText" type="text" name="supplierNo"></li>
								<li><span>出货地:</span>
								<input id="supFactory" class="inputText" type="text" name="supFactory"></li>
								<li><span>计算队列:</span>
								<select id="unloadPort" class="inputText" name="unloadPort" ></select>
								</li>
								<li><span>零件编号:</span>
								<input id="partNo" class="inputText" type="text" name="partNo"></li>
								<li><span>零件简号:</span>
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
								<li><span>物流单号:</span>
								<input id="logisticsOrder" class="inputText" type="text" name="logisticsOrder"></li>
								<li><span>线边sortId:</span>
								<input id="lineSideSortId" class="inputText" type="text" name="lineSideSortId"></li>
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
			url:__ctx+"/mp/mpPartSort/getUnloadPort",
			dictArr:[{
				el : '#unloadPort',
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
		var onclickUrl = encodeURI(__ctx+"/mp/mpPartSort/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl ,
			idField : "groupId",
			sortName : 'GROUP_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'partNo',sortName : "PART_NO",title : '零件编号',
		    width : 100,align : 'center',sortable : 'false'},
		    {field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地代码',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'unloadPort',sortName : "UNLOAD_PORT",title : '计算队列',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'workcenter',sortName : "WORKCENTER",title : '车间',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'groupId',sortName : "GROUP_ID",title : '分组号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'logisticsOrder',sortName : "LOGISTICS_ORDER",title : '物流单号',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'drSortIdStart',sortName : "DR_SORT_ID_START",title : 'D_R起始号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'drSortIdEnd',sortName : "DR_SORT_ID_END",title : 'D_R截止号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'lrSortIdStart',sortName : "LR_SORT_ID_START",title : 'L_R起始号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'lrSortIdEnd',sortName : "LR_SORT_ID_END",title : 'L_R截止号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'orderPackage',sortName : "ORDER_PACKAGE",title : '规格包装数',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'arriveTimeStr',sortName : "ARRIVE_TIME",title : '到货时间',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'lineSideTimeStr',sortName : "LINE_SIDE_TIME",title : '线边时间',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'groupQty',sortName : "GROUP_QTY",title : '分组用量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'lineSideSortId',sortName : "LINE_SIDE_SORT_ID",title : '分组号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'codeValueName',sortName : "CODE_VALUE_NAME",title : '计算状态',
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
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpPartSort/downloadMpPartSortModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
