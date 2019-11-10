<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>报废信息查询</title>
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
								<li><span>信息点:</span>
								<select id="planCode" class="inputText" name="planCode"></select></li>
								
								<li><span>VIN:</span> 
								<input id="vin" class="inputText" type="text" name="vin"></li>
								
								<li><span>过点时间</span>
								<input  name="realKbTimeStrStart"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="realKbTimeStrEnd" class="inputText datetime" /></li>
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
		var onclickUrl = encodeURI(__ctx+"/jit/jitRealKbBatch/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "orderId",
			sortName : 'ORDER_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'planCode',sortName : "PLAN_CODE",title : '信息点',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'planCodeDesc',sortName : "PLAN_CODE_DESC",title : '信息点描述',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'vin',sortName : "VIN",title : 'VIN',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'stationCode',sortName : "STATION_CODE",title : '过点工位',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'sortid',sortName : "SORTID",title : '计划过点批次进度',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'realKbProductSeqno',sortName : "REAL_KB_PRODUCT_SEQNO",title : '实际过点批次进度',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'realKbTimeStr',sortName : "REAL_KB_TIME",title : '工位过点时间',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'passTime',sortName : "PASS_TIME",title : '信息点过点时间',
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
		var downurl = encodeURI(__ctx + '/jit/jitRealKbBatch/downloadJitRealKbBatchModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
