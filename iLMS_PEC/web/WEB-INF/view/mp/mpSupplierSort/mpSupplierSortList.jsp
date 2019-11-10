<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>供应商分组查询</title>
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
							
								<li><span>首台车下线时间 从</span>:
								<input  name="finalUnderlineTimeStartStrStart"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="finalUnderlineTimeEndStrEnd" class="inputText datetime" /></li>
								
								<li><span>计算队列:</span>
								<select id="unloadPort" class="inputText" name="unloadPort" value="ADT01"></select>
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
			url:__ctx+"/mp/mpSupplierSort/getUnloadPort",
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
		var onclickUrl = encodeURI(__ctx+"/mp/mpSupplierSort/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "groupId",
			sortName : 'GROUP_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'unloadPort',sortName : "UNLOAD_PORT",title : '计算队列',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'groupId',sortName : "GROUP_ID",title : '分组号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'drSortIdStart',sortName : "DR_SORT_ID_START",title : 'D_R起始号',
				
			width : 100,align : 'center',sortable : 'true'},
			{field : 'drSortIdEnd',sortName : "DR_SORT_ID_END",title : 'D_R截止号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'finalUnderlineTimeStartStr',sortName : "FINAL_UNDERLINE_TIME_START",title : '首台车下线时间',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'finalUnderlineTimeEndStr',sortName : "FINAL_UNDERLINE_TIME_END",title : '末台车下线时间',
			width : 200,align : 'center',sortable : 'true'},
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
		var downurl = encodeURI(__ctx + '/mp/mpSupplierSort/downloadMpSupplierSortModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
