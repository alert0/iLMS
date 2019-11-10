<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>调整计划查询</title>
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
					           <a href="javascript:;" class="btn btn-sm btn-primary fa-check" 
					           onclick="getAdjPlan()">
						         <span>获取计划</span>
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
								<li><span>车型:</span>
								<input id="carType" class="inputText" type="text" name="carType"></li>
								<li><span>调整日期:</span>
								<input  name="adjDateStrStart"  class="inputText datetime" /></li>
								<li><span>至: </span>
								<input  name="adjDateStrEnd" class="inputText datetime" /></li>
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
		loadGrid(null);
	});
		
	function findOut(){
		var onclickUrl = encodeURI(__ctx+"/mp/mpAdjPlan/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl ,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [  
			{field : 'pkid',sortName : "ID",title : '计划ID',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'carType',sortName : "CAR_TYPE",title : '车型',
		    width : 100,align : 'center',sortable : 'true'},
		    {field : 'diffNum',sortName : "DIFF_NUM",title : '调整数量',
			width : 100,align : 'center',sortable : 'true'},
			{field : 'adjDateStr',sortName : "ADJ_DATE",title : '调整日期',
			width : 200,align : 'center',sortable : 'true'}
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
	
	/* 导出 */
	function excelExport(){
		var downurl = encodeURI(__ctx + '/mp/mpAdjPlan/downloadMpAdjPlanModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	/* 获取计划 */
	function getAdjPlan(){
		var adjDateStrStart = $('#adjDateStrStart').val();
		var adjDateStrEnd = $('#adjDateStrEnd').val();
		var getAdjPlanUrl = encodeURI(__ctx + '/mp/mpAdjPlan/getAdjPlan');
		$.messager.confirm('提示','是否获取计划？',function(r){
		    if (r){
		    	$.ajax({ 
		    		url: getAdjPlanUrl, 
		    		type: 'json',
		    		data : {
						"adjDateStrStart" : adjDateStrStart,
						"adjDateStrEnd" : adjDateStrEnd
					},
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
