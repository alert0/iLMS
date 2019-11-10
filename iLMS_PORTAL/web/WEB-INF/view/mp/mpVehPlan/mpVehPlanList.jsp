<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>车辆计划</title>
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
					          <a class="btn btn-sm btn-primary fa-remove" 
					            href="javascript:void(0)" action="/mp/mpVehPlan/remove">
						         <span>删除</span>
					          </a>
					          <a class="btn btn-sm btn-primary" 
					            href="javascript:void(0)" onclick="removeByCalStatus()">
						         <span>删除未订购</span>
					          </a> 
					          <a class="btn btn-sm btn-primary fa-sign-out"
								href="javascript:void(0)" onclick="excelExport()"> 
								<span>Excel导出</span>
							  </a> 
							   <a class="btn btn-sm btn-primary fa-check"
								href="javascript:void(0)" onclick="getVehPlan()"> 
								<span>获取生产计划</span>
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
								<li><span>车型:</span>
								<input id="carType" class="inputText" type="text" name="carType"></li>
								<li><span>订单号:</span>
								<input id="orderNo" class="inputText" type="text" name="orderNo"></li>
								<li><span>SortId:</span>
								<input id="sortId" class="inputText" type="text" name="sortId"></li>
								
								<li><span>总装下线时间 从</span>:
								<input  name="afoffTimeStartStr"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="afoffTimeEndStr" class="inputText datetime" /></li>
								
								<li><span>计算状态:</span>
								<select id="calStatus" class="inputText" name="calStatus"></select>
								</li>
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
		
		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'MP_CAL_STATUS',
				el : '#calStatus',
				addBlank : true
			}]
		});
	});
	
	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	function findOut(){
		var onclickUrl = encodeURI( __ctx+"/mp/mpVehPlan/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			/*url :   __ctx+"/mp/mpVehPlan/curdlistJson", */
			url : onclickUrl ,
			idField : "sortId",
			sortName : 'SORT_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'sortId',sortName : "SORT_ID",checkbox : true},
			{field : 'sortIdStr',sortName : "SORT_ID",title : 'sortId',
		    width : 80,align : 'center',sortable : 'true'},
			{field : 'carType',sortName : "CAR_TYPE",title : '车型',
		    width : 80,align : 'center',sortable : 'true'},
		    {field : 'carTypeSortId',sortName : "CAR_TYPE_SORT_ID",title : '车型排序',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'orderNo',sortName : "ORDER_NO",title : '订单号',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'afoffTimeStr',sortName : "AFOFF_TIME",title : '总装下线时间',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'proPhase',sortName : "PRO_PHASE",title : '生产阶段',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'codeValueName',sortName : "CODE_VALUE_NAME",title : '计算状态',
			width : 100,align : 'center',sortable : 'true'}
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
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpVehPlan/downloadMpVehPlanModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	function getVehPlan(){
		var getVehurl = encodeURI(__ctx + '/mp/mpVehPlan/getVehPlan');
		$.messager.confirm('提示','是否确认获取生产计划？',function(r){
		    if (r){
		    	$.ajax({ 
		    		url: getVehurl, 
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
	
	function removeByCalStatus(){
		var removeByCalStatusUrl = encodeURI(__ctx + '/mp/mpVehPlan/removeByCalStatus');
		$.messager.confirm('提示','是否删除未订购数据？',function(r){
		    if (r){
		    	$.ajax({ 
		    		url: removeByCalStatusUrl, 
		    		success : function(data) {
						console.log(data);
						var info = $.parseJSON(data);
						if (info.result == '1') {
							$.messager.alert("操作提示", info.message, "info");
							loadGrid();
						} else {
							$.messager.alert("操作提示", info.message, "error");
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
