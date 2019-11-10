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
                    <a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()" >
						<span>Excel导出</span>
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
						<li><span>供应商代码:</span>
						<input class="inputText" name="supplierNo" /></li>
						<li><span>供应商名称:</span>
						<input class="inputText" name="partNo" /></li>
						<li><span>零件编号:</span>
						<input id="seaStatus" class="inputText" name="proposalStatus" /></li>
						<li><span>查询类型:</span>
						<input type="radio" name="type" />全车型
						<input type="radio" name="type" />单车型
						<input type="radio" name="type" />选择组合
						</li>
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

	});

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
			{field : 'carType',sortName : "carType",title : '车型',
			width : 150,align : 'center',sortable : 'true'}, 
			{field : 'partNo',sortName : "partNo",title : '零件编号',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'boxType',sortName : "boxType",title : '箱种',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'boxType',sortName : "boxCode",title : '箱CODE',
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
			{field : 'isComPack',sortName : "isComPack",title : '组合包装',
			width : 130,align : 'center',sortable : 'true'},
			{field : 'comPackRemark',sortName : "comPackRemark",title : '组合包装备注',
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
	
	//Excel导出
	 function excelExport(){
			var downurl = encodeURI(__ctx + '/pkg/pkgProposal/downloadPkgProposalModel');
			$('#downloadiframe').attr('src', downurl);
		}
	
</script>