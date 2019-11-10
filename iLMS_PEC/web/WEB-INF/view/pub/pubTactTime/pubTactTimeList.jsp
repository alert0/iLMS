<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>生产节拍查询</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							  <a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
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
								<li><span>车间:</span> <select id="workcenter"
								class="inputText" name="workcenter"></select></li>
								<li><span>生产线:</span>
								<input id="productionLine"
								class="inputText" type="text" name="productionLine"></li>
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
		/* StartOut(); */
		loadGrid();
		
		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [ {
				typeKey : 'PUB_WORKCENTER',
				el : '#workcenter',
				addBlank : true
			} ]
		});
		
	});
		
	/*初始化界面*/
	/* function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	} */
	
	/*搜索*/
	/* function findOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubTactTime/curdlistJson");
		loadGrid(onclickUrl);
	} */
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			/*加载页面的时候传递一个标志位，用于判断是否加载空数组*/
			queryParams: {
				flag : '1'
		    },
			url : __ctx+"/pub/pubTactTime/curdlistJson",
			idField : "workcenter",
			sortName : 'WORKCENTER',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'workcenter',sortName : "WORKCENTER",title : '车间',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'productionLine',sortName : "PRODUCTION_LINE",title : '生产线',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'produceTime',sortName : "PRODUCE_TIME",title : '生产节拍',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'jph',sortName : "JPH",title : '小时产能（JPH）',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'oee',sortName : "OEE",title : '开动率（OEE）',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'proCapacity',sortName : "PRO_CAPACITY",title : '年产能',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'remark',sortName : "REMARK",title : '备注',
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
