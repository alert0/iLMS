<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>货架标签信息管理</title>
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
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="openDetail('','add')">
						<span>添加</span>
					</a>
					 <a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" onclick = "removeByBatch()" action ="/ter/shelves/deleteByBatch?id=" + idDelete;>
						<span>删除</span>
					</a>
					
					<a class="btn btn-primary btn-sm fa-sign-out" href="javascript:void(0)"  onclick="downloadDataFn()">
						<span>导出</span>
					</a>
					
					<a class="btn btn-primary btn-sm fa-sign-out"
						href="javascript:void(0)" onclick="openExcelImport()"> <span>导入</span>
					</a>
					
					<a class="btn btn-primary btn-sm fa-print" href="javascript:void(0)" onclick = "print()">
						<span>打印</span>
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
						<input id="partNo" class="inputText" type="text" name="partNo"></li>
					</ul>
					<ul>
						<li><span>零件简号:</span>
						<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
					</ul>
					<ul>
						<li><span>零件名称:</span>
						<input id="partName" class="inputText" type="text" name="partName"></li>
					</ul>
					<ul>
						<li><span>车型:</span>
						<input id="carType" class="inputText" type="text" name="carType"></li>
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
		var idDelete;
	});
	
	/*EXCEL导入*/
	function openExcelImport(){
		var url = __ctx + "/ter/shelf/shelfExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	//批量删除
	function removeByBatch() {
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要删除的信息，请确认！");
			return false;
		}
		var idArr = [];
		for (var i = 0; i < records.length; i++) {
			idArr.push(records[i].id);
		}
		idDelete = idArr.join(",");
		alert(idDelete);
	}
	
	function downloadDataFn(){
		var downurl = encodeURI(__ctx + '/ter/shelves/downloadModel');
		$('#downloadiframe').attr('src', downurl);
	}
	//打印
	function print(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要打印的信息，请确认！");
			return false;
		}
		var idArr = [];
		for (var i = 0; i < records.length; i++) {
			idArr.push(records[i].id);
		}
		var id = idArr.join(",");
		var downurl =  __ctx+"/ter/shelves/Print?id=" + id;
		var def = {
	      title : "打印",
	      width : 800,
	      height : 500,
	      modal : true,
	      resizable : true,
	      buttons : []
	    };  

	    dialog = $.topCall.dialog({
	      src : downurl,
	      base : def
	    });
	}
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑" : action == "add" ? "添加" : "查看";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = __ctx + "/ter/shelf/shelfManager" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 580, 350, null, null, id, false);
	}
	
	function loadGrid() {
		
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/ter/shelves/getJson",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},     
			{field : 'partNo',sortName : "partNo",title : '零件编号',
				width : 130,align : 'center',sortable : 'true'},
			{field : 'partName',sortName : "partName",title : '零件名称',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'partShortNo',sortName : "partShortNo",title : '零件简号',
					width : 130,align : 'center',sortable : 'true'}, 
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
					width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "supplierName",title : '供应商名称',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'shelvesAddr',sortName : "shelvesAddr",title : '货架地址',
					width : 130,align : 'center',sortable : 'true'}, 
			{field : 'standardPack',sortName : "standardPack",title : '规格包装',
					width : 130,align : 'center',sortable : 'true'},
			{field : 'safeStock',sortName : "safeStock",title : '安全库存',
					width : 150,align : 'center',sortable : 'true'}, 
			{field : 'carType',sortName : "carType",title : '车型',
					width : 130,align : 'center',sortable : 'true'}, 
			{field : 'stackLayers',sortName : "stackLayers",title : '叠放次数',
					width : 130,align : 'center',sortable : 'true'},
			{field : 'modifiedTime',sortName : "modifiedTime",title : '更新日期',
					width : 150,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+ row.id +"\",\"edit\");' herf='javascript:void(0)'>修改</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/ter/shelves/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					
					return result;
				}
			} ] ]
		}));
	}
	
	
</script>
