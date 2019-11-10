<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>计划对比</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							  <a id ="findData" class="btn btn-sm btn-primary fa-search" href="javascript:void(0)"
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
							  <a class="btn btn-sm btn-primary fa-check"
								href="javascript:void(0)" onclick="getAdjOrderDiff()"> 
								<span>计划对比</span>
							  </a>
							  <a class="btn btn-sm btn-primary fa-check"
								href="javascript:void(0)" onclick="supplierCheck()"> 
								<span>供应商调查</span>
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
								<input id="supplierNo" class="inputText" type="text" name="supplierNo"></li>
								<li><span>零件简号:</span>
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
								
								<li><span>调整日期:</span>
								<input  name="adjDateStrStart"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="adjDateStrEnd" class="inputText datetime" /></li>
								
								<li><span>零件号:</span>
								<input id="partNo" class="inputText" type="text" name="partNo"></li>
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
			
			function findOut(){
				var onclickUrl = encodeURI( __ctx+"/mp/mpAdjOrderDiff/curdlistJson");
				loadGrid(onclickUrl);
			}
			
			function openDetail(id, action) {
				var title = action == "edit" ? "编辑计划对比" : "查看计划对比";
				action =action == "edit" ? "Edit" : "Get";
				var url="mpAdjOrderDiff" + action;
				if(!$.isEmpty(id)){
					url+='?id=' + id;
				}
				HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
			}
			
			function loadGrid(onclickUrl) {
				$('#grid').datagrid($.extend($defaultOptions,{
					url : onclickUrl ,
					idField : "id",
					sortName : 'ID',
					sortOrder : 'desc',
					striped : true,
					fitColumns : false,
					columns : [ [
					{field : 'id',sortName : "ID",checkbox : true}, 
					{field : 'idStr',sortName : "ID",title : 'ID',
					width : 100,align : 'center',sortable : 'true'},
					{field : 'partNo',sortName : "PART_NO",title : '零件编号',
				    width : 100,align : 'center',sortable : 'true'},
				    {field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
				    width : 100,align : 'center',sortable : 'true'},
				    {field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
					width : 100,align : 'center',sortable : 'true'},
					{field : 'adjDiffNum',sortName : "ADJ_DIFF_NUM",title : '调整差异数量',
					width : 100,align : 'center',sortable : 'true'},
					{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
					width : 100,align : 'center',sortable : 'true'},
					
					{field : 'adjDateStr',sortName : "ADJ_DATE",title : '调整日期',
					width : 200,align : 'center',sortable : 'true'},
					{field : 'calDiffNum',sortName : "CAL_DIFF_NUM",title : '差异数量',
					width : 100,align : 'center',sortable : 'true'},
					{field : 'sendFlag',sortName : "SEND_FLAG",title : '发送标识',
					width : 100,align : 'center',sortable : 'true'},
					{field : 'sendTimeStr',sortName : "SEND_TIME",title : '发送时间',
					width : 200,align : 'center',sortable : 'true'},
				
					{
						field : 'colManage',
						title : '操作',
						width : 80,
						align : 'center',
						formatter : function(value, row, index) {
							var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\"" + row.id + "\",\"edit\")' herf='#'>编辑</a>";
							result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(\"" + row.id + "\",\"get\")' herf='#'>明细</a>";
							return result;
						}
					} ] ],
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
			
			/*数据导出*/
			function excelExport(){
				var downurl = encodeURI(__ctx + '/mp/mpAdjOrderDiff/downloadMpAdjOrderDiffModel');
				$('#downloadiframe').attr('src', downurl);
			}
			
			/*计划对比 */
			function getAdjOrderDiff(){
				var getAdjOrderDiffUrl = encodeURI(__ctx + '/mp/mpAdjOrderDiff/getAdjOrderDiff');
				$.messager.confirm('提示','是否进行计划对比？',function(r){
				    if (r){
				    	$.ajax({ 
				    		url: getAdjOrderDiffUrl, 
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
			
		/*供应商调查 */	
		function supplierCheck(){
			var datagrid = $('#grid');
			if (null == datagrid || datagrid.length < 0){
				return false;
			   }
			var records = datagrid.datagrid('getChecked');
			if (records == null || records.length < 1) {
				$.messager.alert("操作提示", "未选择数据，请确认","info");
				return false;
			   }
			var idArr = [];
			var supplierNoArr = [];
			var partNoArr = [];
			var adjDateStrArr = [];
			var adjDiffNumArr = [];
			for (var i = 0; i < records.length; i++) {
				idArr.push(records[i].idStr);
				supplierNoArr.push(records[i].supplierNo);
				partNoArr.push(records[i].partNo);
				adjDateStrArr.push(records[i].adjDateStr);
				adjDiffNumArr.push(records[i].adjDiffNum);
			}
			var supplierCheckUrl = encodeURI(__ctx + '/mp/mpAdjOrderDiff/supplierCheck');
			$.messager.confirm('提示', '确定要调查么？', function(r) {
				if (r) {
					$.ajax({
						url : supplierCheckUrl,
						type : 'post',
						data : {
							"id" : idArr,
							"partNo" : partNoArr,
							"supplierNo" : supplierNoArr,
							"adjDateStr" : adjDateStrArr,
							"adjDiffNum" : adjDiffNumArr
						},
						traditional: true,
						Type : 'json',
						success : function(data) {
							console.log(data);
							var info = $.parseJSON(data);
							if (info.result == '1') {
								$.messager.alert("操作提示", info.message, "info");
							} else {
								$.messager.alert("操作提示", info.message, "error");
							}
							$(this).addClass("done");
						}
					});
				} else {
					return;
				}
			});
			
			
			
		}
</script>
