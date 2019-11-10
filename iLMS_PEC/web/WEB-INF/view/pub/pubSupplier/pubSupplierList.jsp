<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>供应商主数据查询</title>
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
								<input id="supplierNo"
								class="inputText" type="text" name="supplierNo"></li>
								<li><span>出货地代码:</span>
								<input id="supFactory"
								class="inputText" type="text" name="supFactory"></li>
								<li><span>供应商名称:</span>
								<input id="supplierName"
								class="inputText" type="text" name="supplierName"></li>
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
		
	});
		
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	/*搜索*/
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubSupplier/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : onclickUrl,
			idField : "supFactory",
			sortName : 'SUP_FACTORY',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地代码',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'detailAddr',sortName : "DETAIL_ADDR",title : '供应商地址',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'contact',sortName : "CONTACT",title : '联系人',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'telNo',sortName : "TEL_NO",title : '联系电话',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'mobileNo',sortName : "MOBILE_NO",title : '联系手机',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'email',sortName : "EMAIL",title : '联系邮箱',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'codeValueName',sortName : "CODE_VALUE_NAME",title : '供应商状态',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'supLeaderName',sortName : "SUP_LEADER_NAME",title : '领导联系人',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'supLeaderTel',sortName : "SUP_LEADER_TEL",title : '领导联系电话',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'supLeaderMail',sortName : "SUP_LEADER_MAIL",title : '领导联系邮箱',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'orderContact',sortName : "ORDER_CONTACT",title : '订货联系人',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'orderTel',sortName : "ORDER_TEL",title : '订货联系电话',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'orderMail',sortName : "ORDER_MAIL",title : '订货联系邮箱',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'delivContact',sortName : "DELIV_CONTACT",title : '发货联系人',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'deliveryTel',sortName : "DELIVERY_TEL",title : '发货联系电话',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'deliveryMail',sortName : "DELIVERY_MAIL",title : '发货联系邮箱',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'excepContact',sortName : "EXCEP_CONTACT",title : '物流异常联系人',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'excepTel',sortName : "EXCEP_TEL",title : '物流异常联系电话',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'excepMail',sortName : "EXCEP_MAIL",title : '物流异常联系邮箱',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'packDeptName',sortName : "PACK_DEPT_NAME",title : '包装管理部门',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'packDeptTel',sortName : "PACK_DEPT_TEL",title : '包装管理员联系电话',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'packDeptMail',sortName : "PACK_DEPT_MAIL",title : '包装管理员联系邮箱',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'packDeptMinister',sortName : "PACK_DEPT_MINISTER",title : '包装管理部长',
			width : 100,align : 'center',sortable : 'true'},
			
			{field : 'packDeptChief',sortName : "PACK_DEPT_CHIEF",title : '包装管理科长',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'packDeptContact',sortName : "PACK_DEPT_CONTACT",title : '包装管理担当',
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
	
	/*数据导出*/
	function excelExport(){
		var downurl = encodeURI(__ctx + '/pub/pubSupplier/downloadPubSupplierModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
