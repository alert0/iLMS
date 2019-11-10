<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>生产计划维护</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
							<span>搜索</span>
					    </a>
				        <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
					    	<span>重置</span>
				        </a>
				        <a class="btn btn-sm btn-primary fa-add" id="add" 
			            	href="javascript:void(0)" onclick="getProPlanFn()">
				         	<span>获取计划</span>
			            </a>
			            <a class="btn btn-primary btn-sm fa-sign-in"  
					    	href="javascript:void(0)" onclick="openExcelImport()">
				         	<span>Excel导入</span>
			            </a>
			            <a id="export" class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport()"> 
							<span>批量导出</span>
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
							<li><span>混合车型排序</span>
								<input id="mixSortId" class="inputText" type="number" name="mixSortId">
							</li>
							<li><span>分车型排序</span>
								<input id="singleSortId" class="inputText" type="text" name="singleSortId">
							</li>
							<li><span><font color="red">*</font>计划下线时间</span>
								<input id="afoffTimeStart" class="inputText date" type="text" name="afoffTimeStart">
							</li>
							<li><span><font color="red">*</font>到</span>
								<input id="afoffTimeEnd" class="inputText date" type="text" name="afoffTimeEnd">
							</li>
						</ul>
						<ul style="margin-left: -26px">
							<li><span>订单号</span>
								<input id="orderNo" class="inputText" type="number" name="orderNo">
							</li>
							<li><span>车型</span>
								<input id="carType" class="inputText" type="text" name="carType">
							</li>
							<li style="width:400px"><span><font color="red">*</font>周次</span>
								<input id="week" name="week" type="number" class="inputText"> 
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>	
	$(function() {
		loadGrid();
		loadAfoffTime();
		$("#export").attr("disabled",true);
	});
	
	function loadAfoffTime(){
		//查询结束时间控件默认当前时间
		var nowDate = new Date();
		var endDay = ("0" + nowDate.getDate()).slice(-2);
		var EndMonth = ("0" + (nowDate.getMonth() + 1)).slice(-2);
		var today = nowDate.getFullYear()+"-"+(EndMonth)+"-"+(endDay);
		$('#afoffTimeEnd').val(today);
		//查询开始时间控件默认一周前时间
		var oneWeekAgoDate = new Date(nowDate-7*24*3600*1000);
		var startDay = ("0" + oneWeekAgoDate.getDate()).slice(-2);
		var startMonth = ("0" + (oneWeekAgoDate.getMonth() + 1)).slice(-2);
		var oneWeekAgoDay = oneWeekAgoDate.getFullYear()+"-"+(startMonth)+"-"+(startDay);
        $('#afoffTimeStart').val(oneWeekAgoDay);
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/pup/proplan/listJson",
			idField : "sortId",
			sortName : 'SORT_ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'sortId',sortName : "SORT_ID",title : 'sortId',align : 'center',sortable : 'true'},
				{field : 'factoryCode',sortName : "FACTORY_CODE",title : '工厂代码',align : 'center',sortable : 'true'},
				{field : 'orderNo',sortName : "ORDER_NO",title : '订单号',align : 'center',sortable : 'true'},
			    {field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
			    {field : 'mark',sortName : "Mark",title : '标识',align : 'center',sortable : 'true'},
				{field : 'mixSortId',sortName : "MIX_SORT_ID",title : '混合车型排序',align : 'center',sortable : 'true'},
				{field : 'singleSortId',sortName : "SINGLE_SORT_ID",title : '分车型排序',align : 'center',sortable : 'true'},
				{field : 'afoffDate',sortName : "AFOFF_TIME",title : '计划下线日期',align : 'center',sortable : 'true'},
				{field : 'afoffTime',sortName : "AFOFF_TIME",title : '计划下线时间',align : 'center',sortable : 'true'},
				{field : 'sendFlag',sortName : "sendFlag",title : '已发订单标识',align : 'center',sortable : 'true'}
			    ]],
		    onLoadSuccess : function (data) {
			    if(data.total==0){
					$("#export").attr("disabled",true);
                    var dc = $(this).data('datagrid').dc;
                    var header2Row = dc.header2.find('tr.datagrid-header-row');
                    dc.body2.find('table').append(header2Row.clone().css({
                    	"visibility":"hidden"
                    }));
				}else{
					$("#export").attr("disabled",false);
				}
			}
		}));
	}
	
	function openExcelImport(){
		var url = __ctx + "/pup/proPlan/pupProPlanImport";
		HT.window.openEdit(url, '导入生产计划订单', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function excelExport(){
		if($("#export").attr("disabled")){
			return;
		}
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
				var downurl = encodeURI(__ctx + '/pup/proplan/exportPlan');
				$('#downloadiframe').attr('src', downurl);
			}
		});
	}
	
	function getProPlanFn(){
		$.messager.confirm('提示信息',"确认获取生产计划?" ,function(r){
			if(r){
				$.ajax({  
				    url : "${pageContext.request.contextPath}/pup/proplan/getProPlan", 
				    data : $('#searchForm').serialize(), 
				    async : false,
				    success : function(data) {
				    	var result = new com.hotent.form.ResultMessage(data);
				    	if(result.isSuccess()){
				    		if(result.getMessage() == '1'){
				    			$.topCall.success("获取计划成功!");
				    			loadGrid();
				    		}else{
						    	$.topCall.error("获取计划失败!");
						    }
				    	}
				    }
				});
			}
		});	
	}
</script>
