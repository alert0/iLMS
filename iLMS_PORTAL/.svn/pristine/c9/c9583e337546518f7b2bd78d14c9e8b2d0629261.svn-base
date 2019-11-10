<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>包装提案管理</title>
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
					<a class="btn btn-sm btn-primary fa-check"  onclick="initiateFun()">
						<span>发起提案</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit" onclick="updateStatusFun('2')" >
						<span>审核</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit" onclick="updateStatusFun('4')" >
						<span>实物审核</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit"  >
						<span>提案书导出</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit"  >
						<span>签字提案上传</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit" >
						<span>签字提案下载</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit"  >
						<span>期望录入</span>
					</a>
					<a class="btn btn-sm btn-primary fa-check"  onclick="packageFun()">
						<span>包装提案</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit"  >
						<span>组合提案</span>
					</a>
					<a class="btn btn-sm btn-primary fa-edit"  onclick="updateStatusFun('1')">
						<span>提交</span>
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
						<li><span>提案发起时间:</span>
						<input class="inputText datetime" type="text" name="createTimeStart"/></li>
						<li><span>至:</span>
						<input class="inputText datetime" type="text" name="createTimeEnd"/></li>
						<li><span>车型:</span>
						<input class="inputText" type="text" name="carType"/></li>
						<li><span>供应商代码:</span>
						<input class="inputText" name="supplierNo" /></li>
						<li><span>零件编号:</span>
						<input class="inputText" name="partNo" /></li>
						<li><span>状态:</span>
						<select id="seaStatus" class="inputText" name="proposalStatus"></select></li>
						<li><span>留用新设:</span>
						<select id="seaStayNewStatus" class="inputText" name="stayNewStatus" ></select></li>
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
		
	 	//状态下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'PKG_STATUS',
				el : '#seaStatus',
				addBlank : true
			}]
		});
	});
	
	//包装管理员发起提案
	function initiateFun(){
		var url = __ctx + "/pkg/pkgProposal/pkgInitiateList";
		HT.window.openEdit(url, '发起提案', null, 'grid', 870, 500, null, null, null, false);
	}

	//供应商包装提案
	function packageFun(){
		var records=$('#grid').datagrid('getChecked');
		if (records .length < 1 ) {
			$.messager.alert("操作提示", "未选择数据","info");
			return false;
		}
		if(records.length > 1){
			$.messager.alert("操作提示", "每次只能选择一条数据","info");
			return false;
		}
		if(records[0].proposalStatus != '发起'){
			$.messager.alert("操作提示", "请选择发起状态信息","info");
			return false;
		}
		var id = records[0].id;
		var url = __ctx + "/pkg/pkgProposal/pkgProposalEdit?id="+id;
		HT.window.openEdit(url, '包装提案', null, 'grid', 870, 500, null, null, id, false);
	}
	
	//修改提案状态
	function updateStatusFun(proposalStatus){
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
		var msg = null;
		if('2' == proposalStatus){
			msg = "提案审核是否通过？"
		}
	    if('4' == proposalStatus){
	    	msg = "实物审核是否通过？"
	    }
	    $.messager.defaults = { ok: "通过", cancel: "不通过" };
		$.messager.confirm('提示',msg,function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/pkg/pkgProposal/updateProposalStatus",
		    	    data: {
		    	        'ids' : ids,
		    	        'proposalStatus' : proposalStatus,
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
		    } else{
		    	
		    	
		    }
	    }); 
	}
	
	function findOut(){
		var onclickUrl = encodeURI(__ctx + "/pkg/pkgProposal/curdlistJson");
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
			{field : 'proposalStatus',sortName : "proposalStatus",title : '状态',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'carType',sortName : "carType",title : '车型',
			width : 150,align : 'center',sortable : 'true'}, 
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "supplierName",title : '供应商名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'area',sortName : "area",title : '区域',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partNo',sortName : "partNo",title : '零件编号',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'project',sortName : "project",title : '工程',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'boxType',sortName : "boxType",title : '箱种',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'packLength',sortName : "packLength",title : 'L(mm)',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'packWidth',sortName : "packWidth",title : 'W(mm)',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'packHeight',sortName : "packHeight",title : 'H(mm)',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'standardPackge',sortName : "standardPackge",title : '规格包装',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partWeight',sortName : "partWeight",title : '零件重量',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'packWeight',sortName : "packWeight",title : '包装重量',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'boardLocation',sortName : "boardLocation",title : '看板位置',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'stayNewStatus',sortName : "stayNewStatus",title : '留用新设',
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
