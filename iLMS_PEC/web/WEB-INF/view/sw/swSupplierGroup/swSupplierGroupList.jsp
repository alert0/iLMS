<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>厂外同步推算控制台-待组票净需求</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'north',split:true" style="height: 100px;background-color: #F5F5F5;">
			<div id="" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search">
							<span>查询</span>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>分组名称:</span><input id="groupName" class="inputText" name="groupName"/></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="group_grid" class="my-easyui-datagrid"></div>
		</div> 
		<div data-options="region:'east',split:true" style="width:50%;">
		    <div id="supplier_grid"  class="my-easyui-datagrid"></div>
		</div> 
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
/**分组id**/
var groupIdAndSupplier = null;
	$(function(){
		
		loadGrid();
	})
	
	//分组列表列表
	function loadGrid() {
		$('#group_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/sw/swSupplierGroup/queryJisoGroupPage",
			toolbar : [{
				text: '新增',
				iconCls: 'icon-add',
				handler: openDetailAdd
			},{
				text: '修改',
				iconCls: 'icon-edit',
				handler: openDetailEdit
			},{
				text: '删除',
				iconCls: 'icon-remove',
				handler: removeGroup
			},{
				text: '导出',
				iconCls: 'icon-check',
				handler: excelExport
			},{
				text: '导入',
				iconCls: 'icon-check',
				handler: openExcelImport
			}],
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : '',sortName : "",checkbox : true},
				{field : 'groupId',sortName : "groupId",title : '分组编码',width : 150,align : 'center',sortable : 'true'}, 
				{field : 'groupName',sortName : "groupName",title : '分组名称',width : 150,align : 'center',sortable : 'true'},
			 ] ],
			 onClickRow : function(index,row){
				 loadRemianGrid(index,row);
			 }
		}));
	}
	
	//新增分组名称
	function openDetailAdd(){
		var url = __ctx + "/sw/swSupplierGroup/swGroupEdit" ;
		HT.window.openEdit(url, '新增分组名称', 'add', 'Group_grid', 400, 200, null, null, null, false);
	}
	
	//修改分组名称
	function openDetailEdit(){
		var groupId = null;
		var title = "修改分组名称";
		var datagrid = $('#group_grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records.length < 1 ) {
			$.messager.alert("操作提示", "未选择数据","info");
			return false;
		}
		if(records.length > 1){
			$.messager.alert("操作提示", "每次只能选择一条数据","info");
			return false;
		}
		groupId = records[0].groupId;
		var url = __ctx + "/sw/swSupplierGroup/swGroupEdit" ;
		if(!$.isEmpty(groupId)){
			url+='?groupId=' + groupId;
		}
		HT.window.openEdit(url, title, 'edit', 'group_grid', 400, 200, null, null, groupId, false);
	}
	
	//删除分组信息
	function removeGroup(){
		var datagrid = $('#group_grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var groupIdArr = [];
		for (var i = 0; i < records.length; i++) {
			groupIdArr.push(records[i].groupId);
		}
		$.messager.confirm('提示','确认删除？',function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/sw/swSupplierGroup/removeGroup",
		    	    data: {
		    	        'groupIdArr' : groupIdArr,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#group_grid').datagrid('reload');
		    	    	$('#supplier_grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				//$('#group_grid').reload();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    }); 
	}
	
	//导出供应商分组数据
	function excelExport(){
		var downurl = encodeURI(__ctx + '/sw/swSupplierGroup/downloadSwSupplierGroupModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//导入供应商分组数据
	function openExcelImport(){
		var url = __ctx + "/sw/swSupplierGroup/swSupplierGroupExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	//供应商信息列表
	function loadRemianGrid(index,row){
		groupIdAndSupplier = row.groupId;
		$('#supplier_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/sw/swSupplierGroup/queryJisoSupplierPage",
			toolbar:[{
				text: '新增',
				iconCls: 'icon-add',
				handler: openDetailSupplierAdd
			},{
				text: '删除',
				iconCls: 'icon-remove',
				handler: removeSupplier
			}],
			queryParams: {
				'groupId': groupIdAndSupplier,
		    },
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : '',sortName : "",checkbox : true},
				{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'supplierName',sortName : "supplierName",title : '供应商名称',width : 230,align : 'center',sortable : 'true'},
			 ] ],
		}));
	}
	
	//新增供应商信息
	function openDetailSupplierAdd(){
		if(null == groupIdAndSupplier){
			$.messager.alert("操作提示", "请先选择分组","info");
			return false;
		}
		var url = __ctx + "/sw/swSupplierGroup/swSupplierEdit?groupId="+groupIdAndSupplier;
		HT.window.openEdit(url, '新增供应商信息', 'add', 'supplier_grid', 550, 500, null, null, groupIdAndSupplier, false);
		
	}
	
	//删除供应商信息
	function removeSupplier(){
		var datagrid = $('#supplier_grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var groupIdArr = [];
		for (var i = 0; i < records.length; i++) {
			supplierNoArr.push(records[i].supplierNo);
		}
		$.messager.confirm('提示','确认删除？',function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/sw/swSupplierGroup/removeSupplier",
		    	    data: {
		    	        'supplierNoArr' : supplierNoArr,
		    	        'groupId' : groupIdAndSupplier,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#supplier_grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				$('#supplier_grid').reload();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    }); 
	}
	
</script>