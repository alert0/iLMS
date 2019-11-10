<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>生产日历查询</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'north', closable:true"  style="height:25%;width:100%">
		   <div   class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							 <a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						         <span>搜索</span>
					          </a>
							<a class="btn btn-sm btn-primary fa-rotate-left" href="javaScript:void(0)" >
								<span>重置</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul style="margin-left: -26px">
								<li><span>工厂:</span>
								<input class="inputText" type="text" name="factoryCode"></li>
								<li><span>工作中心:</span>
								<input class="inputText" type="text" name="workCenter"></li>
								<li>
									<span>工作日从:</span>
									<input  name="workDateStrStart"  class="inputText date" />
								</li>
								<li>
								<span>至: </span>
									<input  name="workDateStrEnd" class="inputText date" />
								</li>
								<li><span>班次:</span>
								<input class="inputText" type="text" name="shiftCode"></li>
							</ul>
						</form>
					</div>
			</div>
		</div>
	    <div data-options="region:'west',split:true,collapsible:false " title="工作时间列表" style="width:60%;">
		   
		    <div id="grid" class="my-easyui-datagrid" ></div>
		</div>
		<div  data-options="region:'center',title:'休息时间列表'" >
		
		     <div id="gridEast" class="my-easyui-datagrid"></div> 
			
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		/* StartOut(); */
		loadGrid();
	});
	
	/*初始化界面*/
	/* function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	} */
	
	/*点击搜索按钮*/
	/* function findOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/curdlistJson");
		loadGrid(onclickUrl);
	} */
	/**
	*左侧界面
	*/
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx+"/pub/pubWorkCalendar/curdlistJson",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns:false,
			columns : [ [
			{field : 'id',sortName : "ID",checkbox : true},
			{field : 'factoryCode',sortName : "FACTORY_CODE",title : '工厂',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'workCenter',sortName : "WORK_CENTER",title : '工作中心',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'shiftCode',sortName : "SHIFT_CODE",title : '班次',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'workDateStr',sortName : "WORK_DATE",title : '工作日',
			width : 200,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter},
			{field : 'workStartTimeStr',sortName : "WORK_START_TIME",title : '工作开始时间',
			width : 200,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter},
			{field : 'workEndTimeStr',sortName : "WORK_END_TIME",title : '工作结束时间',
			width : 200,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter}
			] ],
			 onLoadSuccess : function(data) {
				/* 无数据时加载滚动条 */
				if(data.total==0)
				{
				var dc = $(this).data('datagrid').dc;
				var header2Row = dc.header2.find('tr.datagrid-header-row');
				dc.body2.find('table').append(header2Row.clone().css({
					"visibility":"hidden"
				}));
				}
				handGridLoadSuccess();
			}, 
			onClickRow: function (index, row) {
				
	        	 loadGridEast(index,row);
	         }
		}));
	    }
	
	/**
	*右侧界面
	*/
	   function loadGridEast(index, row) {
		var idWorkCalendar = row.id;
		$('#gridEast').datagrid($.extend($defaultOptions,{
			url :   __ctx+"/pub/pubWorkCalendar/getRowClick",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			pagination: true,
			queryParams: {
				'id': idWorkCalendar
		    },
			toolbar: null,
			striped : true,
	        columns : [ [
				{field : 'startTimeStr',sortName : "START_TIME",title : '休息开始时间',
				width : 200,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter}, 
				{field : 'endTimeStr',sortName : "END_TIME",title : '休息结束时间',
				width : 200,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter}
				 ] ],
		    onLoadSuccess : function() {
					handGridLoadSuccess();
				}  
		   }));
	       }
	
</script>
