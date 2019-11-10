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
								<li><span>VIN:</span> 
								<input id="vin" class="inputText" type="text" name="vin"></li>
								
								<li><span>是否需补看板:</span> 
								<select id="adjustKbState"  class="inputText" name="adjustKbState"></select></li>
								
								<li><span>报废时间</span>
								<input  name="scrapTimeStrStart"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="scrapTimeStrEnd" class="inputText datetime" /></li>
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
		
		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [ {
				typeKey : 'JIT_ADJUST_KB',
				el : '#adjustKbState',
				addBlank : true
			} ]
		});
		
	});
		
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	/*搜索*/
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/jit/jitVehScrap/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "orderNo",
			sortName : 'ORDER_NO',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'vin',sortName : "VIN",title : 'VIN',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'scrapWorkcenter',sortName : "SCRAP_WORKCENTER",title : '报废车间',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'scrapStationCode',sortName : "SCRAP_STATION_CODE",title : '最后经过工位',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'scrapTime',sortName : "SCRAP_TIME",title : '报废时间',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'scrapReason',sortName : "SCRAP_REASON",title : '报废原因',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'adjustKbState',sortName : "ADJUST_KB_STATE",title : '补看板状态',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'adjustTimeStr',sortName : "ADJUST_TIME",title : '补看板时间',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'adjustUser',sortName : "ADJUST_USER",title : '操作人',
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
		var downurl = encodeURI(__ctx + '/jit/jitVehScrap/downloadJitVehScrapModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
