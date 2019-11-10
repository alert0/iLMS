<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>包装管理零件担当维护</title>
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
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)"
					onclick="findOut()">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit" id="edit"  onclick="openDetail('','edit')">
						<span>编辑</span>
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
						<li><span>车型代码:</span>
						<input class="inputText" type="text" name="pkgBox"/></li>
						<li><span>供应商代码:</span>
						<input class="inputText" type="text" name="boxCode"/></li>
						<li><span>供应商名称:</span>
						<input class="inputText" type="text" name="status"/></li>
						<li><span>留用新设状态:</span>
						<select id="seaStatus" class="inputText" name="status">
						</select></li>
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
		loadGrid(null);
		
		//留用新设状态下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'PKG_STAY_NEW',
				el : '#seaStatus',
				addBlank : true
			}]
		});
	});
		
	function openDetail(id, action) {
		if(action == 'edit'){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		   }
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		   }
		if (records.length > 1) {
			$.messager.alert("操作提示", "请选择一条数据","info");
			return false;
		   }
		id = records[0].id;
		}
		var title = action == "edit" ? "编辑" : "查看";
		action = action == "edit" ? "Edit" : "Get";
		var url = __ctx + "/pkg/pkgPart/pkgPart" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 580, 350, null, null, id, false);
	}
	
	function findOut(){
		var onclickUrl = encodeURI(__ctx + "/pkg/pkgPart/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false, 
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},
			{field : 'carType',sortName : "carType",title : '车型',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partNo',sortName : "partNo",title : '零件编号',
			width : 150,align : 'center',sortable : 'true'}, 
			{field : 'project',sortName : "project",title : '工程',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "supplierName",title : '供应商名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partRespUser',sortName : "partRespUser",title : '零件担当',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'telNo',sortName : "telNo",title : '联系方式',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'status',sortName : "status",title : '留用新设',
			width : 130,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				align : 'east',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.id + "\",\"get\");' herf='javascript:void(0)'>明细</a>";
					return result;
				}
			} 
			] ],
			autoScroll: true,
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
	
	
</script>
