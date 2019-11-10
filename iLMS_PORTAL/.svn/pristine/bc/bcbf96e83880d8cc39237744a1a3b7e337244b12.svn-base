<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>未分组供应商信息列表</title>
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
					<a class="btn btn-sm btn-primary fa-check"  onclick="insertSupplier()">
						<span>确定</span>
					</a>
					 <a class="btn btn-sm btn-primary fa-remove" onClick="HT.window.closeEdit(true);">
						<span>取消</span>
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
						<li><span>供应商代码:</span>
						<input id="supplierNo" class="inputText" type="text" name="supplierNo"></li>
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
var groupId = "${param.groupId}";

	$(function() {
		loadGrid();
	});
		
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/sw/swSupplierGroup/getSupplierListUnGroup",
			idField : "supplierNo",
			sortName : 'SUPPLIER_NO',
			sortOrder : 'desc',
			queryParams: {
				'groupId': groupId,
		    },
		    striped : true,
			fitColumns : false,
			columns : [ [
			{field : '',sortName : "",checkbox : true},     
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "supplierName",title : '供应商名称',
			width : 150,align : 'center',sortable : 'true'}, 
			{field : 'detailAddr',sortName : "detailAddr",title : '供应商地址',
			width : 150,align : 'center',sortable : 'true'}, 
			] ]
		}));
	}
	
	//选中数据点击确定，将数据写入到MM_SW_SUP_GROUP_MEMBERS表中
	function insertSupplier(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var supplierNoArr = [];
		for (var i = 0; i < records.length; i++) {
			supplierNoArr.push(records[i].supplierNo);
		}
		$.messager.confirm('提示','确认添加到分组？',function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/sw/swSupplierGroup/insertSupplier",
		    	    data: {
		    	        'supplierNoArr' : supplierNoArr,
		    	        'groupId' : groupId,
		    	    },
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	//$('#grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				//$('#grid').reload();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    });
	}
	
</script>
