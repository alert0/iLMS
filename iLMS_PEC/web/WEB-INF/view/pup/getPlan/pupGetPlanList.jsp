<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
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
					    <a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" id="remove" action="/pup/getPlan/remove">
						         <span>删除</span>
					    </a>
			            <a class="btn btn-primary btn-sm fa-sign-in"  
					    	href="javascript:void(0)" onclick="openExcelImport()">
				         	<span>批量导入</span>
			            </a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>计划取货日期</span>
								<input id="planPickupTimeStart" class="inputText date" type="text" name="planPickupTimeStart">
							</li>
							<li><span>至</span>
								<input id="planPickupTimeEnd" class="inputText date" type="text" name="planPickupTimeEnd">
							</li>
							<li><span>供应商代码</span>
								<input id="supplierNo" class="inputText" type="number" name="supplierNo">
							</li>
							<li><span>线路代码</span>
								<input id="routeCode" class="inputText" type="text" name="routeCode">
							</li>
						</ul>
						<ul style="margin-left: -26px">
							<li><span>物流单号</span>
								<input id="orderNo" class="inputText" type="number" name="orderNo">
							</li>
							<li><span>ERP单号</span>
								<input id="purchaseNo" class="inputText" type="text" name="purchaseNo">
							</li>
							<li><span>下载状态</span>
								<select id="downloadStatus" class="inputText" type="text" name="downloadStatus"></select>
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
		
		loadPickTime();
		
		HtUtil.loadComboBox({
			url:__ctx+"/pup/getPlan/getDownloadStatus",
			dictArr:[{
				el : '#downloadStatus',
				addBlank : true
			}]
		});
	});
	
	function loadPickTime(){
		
		var nowDate = new Date();
		
		var LaterDate = new Date(nowDate.getTime() + 15*24*3600*1000);
		var endDay = ("0" + LaterDate.getDate()).slice(-2);
		var endMonth = ("0" + (LaterDate.getMonth() + 1)).slice(-2);
		var afterDate = LaterDate.getFullYear()+"-"+(endMonth)+"-"+(endDay);
		$('#planPickupTimeEnd').val(afterDate);
		
		var AgoDate = new Date(nowDate-5*24*3600*1000);
		var startDay = ("0" + AgoDate.getDate()).slice(-2);
		console.log(startDay)
		var startMonth = ("0" + (AgoDate.getMonth() + 1)).slice(-2);
		var AgoDate = AgoDate.getFullYear()+"-"+(startMonth)+"-"+(startDay);
        $('#planPickupTimeStart').val(AgoDate);
        
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : "listPlan",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : true},
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
		var url = __ctx + "/pup/getPlan/pupGetPlanImport";
		HT.window.openEdit(url, '取货计划导入', null, 'grid', 400, 300, null, null, null, true);
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
				    async : false,
				    success : function(data) {
				    	var result = new com.hotent.form.ResultMessage(data);
				    	if(result.isSuccess()){
					    	$.topCall.success(result.getMessage());
					    	loadGrid();
				    	}else{
					    	$.topCall.error(result.getMessage());
					    }
				    }
				});
			}
		});	
	}
</script>
