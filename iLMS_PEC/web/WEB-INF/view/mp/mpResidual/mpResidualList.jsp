<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>剩余量主数据</title>
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
					          <a class="btn btn-sm btn-primary fa-add" id="add" 
					            href="javascript:void(0)" onclick="openDetail('','add')">
						         <span>添加</span>
					          </a>
					           <a class="btn btn-sm btn-primary fa-remove" 
					            href="javascript:void(0)" action="/mp/mpResidual/remove">
						         <span>删除</span>
					          </a> 
					          <a class="btn btn-sm btn-primary fa-sign-out"
								href="javascript:void(0)" onclick="excelExport()"> 
								<span>Excel导出</span>
							  </a> 
							  <a class="btn btn-primary btn-sm fa-sign-in"  
							    href="javascript:void(0)" onclick="openExcelImport()">
						         <span>Excel导入</span>
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
								<!-- <select id="unloadPort" class="inputText" type="text" name="Q^UNLOAD_PORT^SL">
								    <option value=""> </option>
						            <option value="0">0</option>
						            <option value="1">1</option>
						        </select> -->
								</li>
								<li><span>零件简号:</span>
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
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
		
		HtUtil.loadComboBox({
			url:__ctx+"/mp/mpResidual/getUnloadPort",
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
		var onclickUrl = encodeURI(__ctx+"/mp/mpResidual/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑剩余量主数据" : action == "add" ? "添加剩余量主数据" : "查看剩余量主数据";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="mpResidual" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid(onclickUrl) {
		/* var partNoValue = $('#partNo').val();//这是参数
		var supplierNoValue =$('#supplierNo').val();
		var unloadPortValue = $('#unloadPort').val(); 
		var partShortNoValue = $('#partShortNo').val();  */
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns:false,
			/* queryParams: {
				'partNo': partNoValue,
				'supplierNo': supplierNoValue,
				'unloadPort': unloadPortValue,
				'partShortNo': partShortNoValue
		    },    */
			columns : [ [
			{field : 'id',sortName : "ID",checkbox : true},   
			{field : 'partNo',sortName : "PART_NO",title : '零件编号',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'unloadPort',sortName : "UNLOAD_PORT",title : '计算队列',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'preResidualNum',sortName : "PRE_RESIDUAL_NUM",title : '订购前余量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'safeNum',sortName : "SAFE_NUM",title : '安全库存',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'necessaryOrderNum',sortName : "NECESSARY_ORDER_NUM",title : '净需求数量',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'orderNum',sortName : "ORDER_NUM",title : '订购数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'realResidualNum',sortName : "REAL_RESIDUAL_NUM",title : '实际剩余量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'manuResidual',sortName : "MANU_RESIDUAL",title : '手工调整余量',
			width : 100,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\"" + row.id + "\",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(\"" + row.id + "\",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/mp/mpResidual/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
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
	
	function openExcelImport(){
		var url = __ctx + "/mp/mpResidual/mpResidualExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpResidual/downloadMpResidualModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
