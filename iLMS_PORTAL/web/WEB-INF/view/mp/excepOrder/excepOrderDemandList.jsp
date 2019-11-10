<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>例外订单需求</title>
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
				    <a class="btn btn-sm btn-primary fa-sign-out"
				    href="javascript:void(0)" onclick="excelExport()"> 
						<span>Excel导出</span>
				    </a> 
					<a class="btn btn-primary btn-sm fa-sign-in"  href="javascript:void(0)" onclick="openExcelImport()">
						<span>Excel导入</span>
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
						<li><span>供应商</span><input class="inputText" type="text" name="supplierNo"></li>
						<li><span>零件号</span><input class="inputText" type="text" name="partNo"></li>
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
				StartOut();
			});
			
			/*初始化界面*/
			function StartOut(){
				var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
				loadGrid(onclickUrl);
			}
			
			function findOut(){
				var onclickUrl = encodeURI( __ctx + "/mp/excepOrder/curdlistJson");
				loadGrid(onclickUrl);
			}
			
			function loadGrid(onclickUrl) {
				$('#grid').datagrid($.extend($defaultOptions,{
					url :  onclickUrl,
					idField : "id",
					fitColumns : false,
					async : false,
					columns : [ [
					{field : 'ID',checkbox : true},     
					{field : 'factoryCode',title : '工厂',width : 100,align : 'center'}, 
					{field : 'partNo',title : '零件号',width : 200,align : 'center'}, 
					{field : 'supplierNo',title : '供应商代码',width : 100,align : 'center'},
					{field : 'supFactory',title : '出货地',width : 100,align : 'center'}, 
					{field : 'orderNum',title : '零件用量',width : 100,align : 'center'}, 
					{field : 'arriveDateStr',title : '到货时间',width : 200,align : 'center'} 
					] ],
					onLoadSuccess : function(data) {
						if(data.total==0)
							{
							var dc = $(this).data('datagrid').dc;
							var header2Row = dc.header2.find('tr.datagrid-header-row');
							dc.body2.find('table').append(header2Row.clone().css({
								"visibility":"hidden"
							}));
							}
						refreshTargetGrid();
					}
				}));
			}
			
			/*EXCEL导入*/
			function openExcelImport(){
				var url = __ctx + "/mp/excepOrder/excepOrderDemandExcelImport";
				HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
			}
			
			/*EXCEL导出*/
			function excelExport(){
				var downurl = encodeURI(__ctx + '/mp/excepOrder/downloadExcepOrderModel');
				$('#downloadiframe').attr('src', downurl);
			}
			
			/* 生成 */
			function generateExcepOrderDemand(){
				var generateExcepOrderDemandUrl = encodeURI(__ctx + '/mp/excepOrder/generateExcepOrderDemand');
				$.messager.confirm('提示','是否获取计划？',function(r){
				    if (r){
				    	$.ajax({ 
				    		url: generateExcepOrderDemandUrl, 
				    		type: 'json',
				    		success: function(data){
				    			console.log(data);
				    			var info = $.parseJSON(data);
				    			if(info.result == '1'){
				    				$.messager.alert("操作提示", info.message,"info");
				    				loadGrid();
				    			}else{
				    				$.messager.alert("操作提示", info.message,"error");
				    			}
					            $(this).addClass("done");
					          }
				    	});
				    }else{
				    	return;
				    }
				});
			}
			
			/* 发布 */
			function releaseExcepOrderDemand(){
				var releaseExcepOrderDemandUrl = encodeURI(__ctx + '/mp/excepOrder/releaseExcepOrderDemand');
				$.messager.confirm('提示','是否获取计划？',function(r){
				    if (r){
				    	$.ajax({ 
				    		url: releaseExcepOrderDemandUrl, 
				    		type: 'json',
				    		success: function(data){
				    			console.log(data);
				    			var info = $.parseJSON(data);
				    			if(info.result == '1'){
				    				$.messager.alert("操作提示", info.message,"info");
				    				loadGrid();
				    			}else{
				    				$.messager.alert("操作提示", info.message,"error");
				    			}
					            $(this).addClass("done");
					          }
				    	});
				    }else{
				    	return;
				    }
				});
			}
			
</script>
