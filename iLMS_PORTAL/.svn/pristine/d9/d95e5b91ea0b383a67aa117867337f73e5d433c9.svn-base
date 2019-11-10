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
					<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()" >
						<span>Excel导出</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-check" 
			           onclick="generatempExcepOrderDemandHis()">
				         <span>生成</span>
			        </a>
			        <a href="javascript:;" class="btn btn-sm btn-primary fa-check" 
			           onclick="releasempExcepOrderDemandHis()">
				         <span>发布</span>
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
						<li><span>供应商代码</span><input class="inputText" type="text" name="supplierNo"></li>
						<li><span>出货地</span><input class="inputText" type="text" name="supFactory"></li>
						<li><span>零件编号</span><input class="inputText" type="text" name="partNo"></li>
						<li><span>到货日期</span>
						<input  name="arriveDateStrStart"  class="inputText date" /></li>
						<li><span>至: </span>
						<input  name="arriveDateStrEnd" class="inputText date" /></li>
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
			
			/*搜索*/
			function findOut(){
				var onclickUrl = encodeURI( __ctx + "/mp/mpExcepOrderDemandHis/curdlistJson");
				loadGrid(onclickUrl);
			}
			
			function loadGrid(onclickUrl) {
				$('#grid').datagrid($.extend($defaultOptions,{
					url :  onclickUrl,
					idField : "id",
					fitColumns : false,
					columns : [ [
					{field : 'id',checkbox : true},     
					{field : 'orderNo',title : '物流单号',width : 100,align : 'center'}, 
					{field : 'purchaseNo',title : '采购单号',width : 200,align : 'center'}, 
					{field : 'rowNo',title : '行号',width : 100,align : 'center'},
					{field : 'partNo',title : '零件编号',width : 100,align : 'center'}, 
					{field : 'supFactory',title : '出货地',width : 100,align : 'center'}, 
					{field : 'supplierName',title : '供应商名称',width : 200,align : 'center'},
					{field : 'arriveDateStr',title : '到货日期',width : 100,align : 'center'}, 
					{field : 'orderNum',title : '净需求',width : 100,align : 'center'}, 
					{field : 'supplierNo',title : '供应商代码',width : 200,align : 'center'}
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
						handGridLoadSuccess();
					}
				}));
			}
			
			/*EXCEL导出*/
			function excelExport(){
				var downurl = encodeURI(__ctx + '/mp/mpExcepOrderDemandHis/downloadMpExcepOrderDemandHisModel');
				$('#downloadiframe').attr('src', downurl);
			}
			
			/* 生成 */
			function generatempExcepOrderDemandHis(){
				var generatempExcepOrderDemandHisUrl = encodeURI(__ctx + '/mp/mpExcepOrderDemandHis/generateMpExcepOrderDemandHis');
				$.messager.confirm('提示','是否获取计划？',function(r){
				    if (r){
				    	$.ajax({ 
				    		url: generatempExcepOrderDemandHisUrl, 
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
			function releasempExcepOrderDemandHis(){
				var releasempExcepOrderDemandHisUrl = encodeURI(__ctx + '/mp/mpExcepOrderDemandHis/releaseMpExcepOrderDemandHis');
				$.messager.confirm('提示','是否获取计划？',function(r){
				    if (r){
				    	$.ajax({ 
				    		url: releasempExcepOrderDemandHisUrl, 
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
