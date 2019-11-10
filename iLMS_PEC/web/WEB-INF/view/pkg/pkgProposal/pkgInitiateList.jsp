<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>发起提案</title>
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
					<a class="btn btn-sm btn-primary fa-search" onclick="findOut()">
						<span>搜索</span>
					</a>
					<a class="btn btn-sm btn-primary fa-save" onclick="saveFun()">
						<span>确定</span>
					</a>
					<a class="btn btn-sm btn-primary fa-cancle" onClick="HT.window.closeEdit(true);">
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
						<li><span>车型:</span>
						<input class="inputText" type="text" name="createTimeStart"/></li>
						<li><span>零件编号:</span>
						<input class="inputText" type="text" name="createTimeEnd"/></li>
						<li><span>供应商代码:</span>
						<input class="inputText" type="text" name="carType"/></li>
						<li><span>新设留用状态:</span>
						<select id="seaStayNewStatus" class="inputText" name="supplierNo" /></select></li>
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
				el : '#seaStayNewStatus',
				addBlank : true
			}]
		});
	});

	function findOut(){
		var onclickUrl = encodeURI(__ctx + "/pkg/pkgPart/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function saveFun(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var ids = [];
		for (var i = 0; i < records.length; i++) {
			ids.push(records[i].id);
		}
		$.messager.confirm('提示','确认发起提案',function(r){
			var url =__ctx + "/pkg/pkgProposal/pkgReplyDateWin";
		    if (r){
		    	HT.window.openEdit(url, '选择回复期限', null, 'grid', 400, 200, null, null, null, false);
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/pkg/pkgProposal/save",
		    	    data: {
		    	        'ids' : ids,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				$('#grid').reload();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    }); 
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
			width : 80,align : 'center',sortable : 'true'}, 
			{field : 'partNo',sortName : "partNo",title : '零件编号',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'project',sortName : "project",title : '工程',
			width : 80,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partRespUser',sortName : "partRespUser",title : '零件担当',
			width : 80,align : 'center',sortable : 'true'},
			{field : 'stayNewStatus',sortName : "stayNewStatus",title : '留用新设',
			width : 80,align : 'center',sortable : 'true'},
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
