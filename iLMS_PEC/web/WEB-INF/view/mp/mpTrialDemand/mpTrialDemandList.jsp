<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>新车型需求计算</title>
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
					          <a class="btn btn-sm btn-primary fa-add" id="add" 
					            href="javascript:void(0)" onclick="openDetail('','add')">
						         <span>添加</span>
					          </a>
					          <a class="btn btn-sm btn-primary fa-remove" 
					            href="javascript:void(0)" action="/mp/mpTrialDemand/remove">
						         <span>删除</span>
					          </a> 
					          <a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" 
					           onclick="excelExport()"> 
								<span>Excel导出</span>
							  </a> 
							  <a class="btn btn-primary btn-sm fa-sign-in" href="javascript:void(0)" 
							   onclick="openExcelImport()">
						         <span>Excel导入</span>
					           </a>
					          <a href="javascript:;" class="btn btn-sm btn-primary fa-check" 
					           onclick="generateMpTrialDemand()">
						         <span>生成</span>
					          </a>
					          <a href="javascript:;" class="btn btn-sm btn-primary fa-check" 
					           onclick="releaseMpTrialDemand()">
						         <span>发布</span>
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
								<li><span>出货地:</span>
								<input id="supFactory" class="inputText" type="text" name="supFactory"></li>
								<li><span>零件编号:</span>
								<input id="partNo" class="inputText" type="text" name="partNo"></li>
								<li><span>零件简号:</span>
								<input id="partShortNo" class="inputText" type="text" name="partShortNo"></li>
								<li><span>到货时间:</span>
								<input  name="arriveTimeStrStart"  class="inputText date" /></li>
								<li><span>至: </span>
								<input  name="arriveTimeStrEnd" class="inputText date" /></li>
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
		var onclickUrl = encodeURI(__ctx+"/mp/mpTrialDemand/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑新车型需求计算" : action == "add" ? "添加新车型需求计算" : "查看新车型需求计算";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="mpTrialDemand" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'id',sortName : "ID",checkbox : true},   
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',
		    align : 'center',sortable : 'true'}, 
		    {field : 'partNo',sortName : "PART_NO",title : '零件编号',
			align : 'center',sortable : 'true'},
			{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
			align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
			align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
			align : 'center',sortable : 'true'},
			{field : 'arriveTimeStr',sortName : "ARRIVE_TIME",title : '到货日期',
			align : 'center',sortable : 'true'},
			{field : 'orderNum',sortName : "ORDER_NUM",title : '净需求',
			align : 'center',sortable : 'true'},
			{field : 'purchaseType',sortName : "PURCHASE_TYPE",title : '采购类型',
			align : 'center',sortable : 'true'},
			{field : 'excOrderNum',sortName : "EXC_ORDER_NUM",title : '例外需求',
			align : 'center',sortable : 'true'},
			{field : 'totalOrderNum',sortName : "TOTAL_ORDER_NUM",title : '总需求',
			align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\"" + row.id + "\",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(\"" + row.id + "\",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/mp/mpTrialDemand/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
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
	
	function openExcelImport(){
		var url = __ctx + "/mp/mpTrialDemand/mpTrialDemandExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpTrialDemand/downloadMpTrialDemandModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	/* 生成 */
	function generateMpTrialDemand(){
		var generateMpTrialDemandUrl = encodeURI(__ctx + '/mp/mpTrialDemand/generateMpTrialDemand');
		$.messager.confirm('提示','是否获取计划？',function(r){
		    if (r){
		    	$.ajax({ 
		    		url: generateMpTrialDemandUrl, 
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
	function releaseMpTrialDemand(){
		var releaseMpTrialDemandUrl = encodeURI(__ctx + '/mp/mpTrialDemand/releaseMpTrialDemand');
		$.messager.confirm('提示','是否获取计划？',function(r){
		    if (r){
		    	$.ajax({ 
		    		url: releaseMpTrialDemandUrl, 
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
