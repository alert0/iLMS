<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>零件属地维护</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
<script>


</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search"
						href="javascript:void(0)" action="/ter/parts/getJson"> <span>查询</span>
					</a>

				</div>
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-sign-out"
						href="javascript:void(0)" onclick="downloadDataFn()"> <span>导出</span>
					</a>
				</div>
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-sign-out"
						href="javascript:void(0)" onclick="openExcelImport()"> <span>导入</span>
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
					<ul>
						<li><span>零件编号:</span>
						<input id="part_no" class="inputText" type="text" name="part_no"></li>
					</ul>
					
					<ul>
						<li><span>零件简号:</span>
						<input id="part_shot_no" class="inputText" type="text" name="part_shot_no"></li>
					</ul>
					
					<ul>
						<li><span>零件名称:</span>
						<input id="part_name_cn" class="inputText" type="text" name="part_name_cn"></li>
					</ul>
					
					<ul>
						<li><span>车型:</span>
						<input id="model_code" class="inputText" type="text" name="model_code"></li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function downloadDataFn(){
		var downurl = encodeURI(__ctx + '/ter/parts/downloadModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	
	/*EXCEL导入*/
	function openExcelImport(){
		var url = __ctx + "/ter/parts/partMaintenanceExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function loadGrid() {
		
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/ter/parts/getJson",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},     
			{field : 'part_no',sortName : "part_no",title : '零件代码',
				width : 130,align : 'center',sortable : 'true'},
			{field : 'part_name_cn',sortName : "part_name_cn",title : '零件名称',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'part_short_no',sortName : "part_short_no",title : '零件简码',
						width : 130,align : 'center',sortable : 'true'}, 
			{field : 'supplier_no',sortName : "supplier_no",title : '供应商代码',
				width : 200,align : 'center',sortable : 'true'},
			{field : 'supplier_name',sortName : "supplier_name",title : '供应商名称',
					width : 420,align : 'center',sortable : 'true'}, 
			{field : 'unload_port',sortName : "unload_port",title : '卸货口',
						width : 130,align : 'center',sortable : 'true'}, 
			{field : 'prepare_person',sortName : "prepare_person",title : '备货工程',
				width : 130,align : 'center',sortable : 'true'},
			{field : 'carpool',sortName : "carpool",title : '台车号',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'storage',sortName : "storage",title : '物流库地址',
						width : 200,align : 'center',sortable : 'true'}, 
			{field : 'distri_person',sortName : "distri_person",title : '配送工程',
				width : 130,align : 'center',sortable : 'true'},
			{field : 'location',sortName : "location",title : '落点地址',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'station_code',sortName : "station_code",title : '工位号',
						width : 130,align : 'center',sortable : 'true'}, 
			{field : 'shelf_no',sortName : "shelf_no",title : '货架号',
						width : 130,align : 'center',sortable : 'true'},
			{field : 'location_num',sortName : "location_num",title : '工程深度',
						width : 130,align : 'center',sortable : 'true'},
			{field : 'model_code',sortName : "model_code",title : '车型',
						width : 150,align : 'center',sortable : 'true'}, 
			{field : 'eff_start',sortName : "eff_start",title : '生效日期',
						width : 300,align : 'center',sortable : 'true'}, 
			{field : 'eff_end',sortName : "eff_end",title : '废止日期',
						width : 300,align : 'center',sortable : 'true'}
			] ]
		}));
	}
	
	
</script>
