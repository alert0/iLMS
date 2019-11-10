<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>新车型计划维护导入</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-refresh" href="javascript:void(0)" onclick="refreshDataFn()" >
						<span>刷新</span>
					</a>
					<a class="btn btn-sm btn-primary fa-save" href="javascript:void(0)" onclick="isImportFn()" >
						<span>确定导入</span>
					</a>
					<a class="btn btn-primary btn-sm fa-sign-out"  href="javascript:void(0)" onclick="downloadDataFn()">
						<span>数据导出</span>
					</a>
					<a class="btn btn-primary btn-sm fa-sign-in"  href="javascript:void(0)" onclick="downloadTemplateFn()">
						<span>模板下载</span>
					</a>
					<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit(true,'grid');">
						<span>返回</span>
					</a>
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class=" fa  fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<div class="toolbar-body">
				<form style="margin-top:10px;" id="importForm" method="post" enctype="multipart/form-data">
					<table class="table-form" cellspacing="0">
						<tr>
							<th>
								<span style="font-size:12pt;font-weight:normal">选择文件:</span>
							</th>
							<td>
								<input type="file" size="40" name="file" id="file" accept=".xls,.xlsx" class="inputText input-wh-9" />
							</td><td>
								<a class="btn btn-sm btn-primary fa-sign-in" href="javascript:void(0)" onclick="importFileFn()" >
									<span>文件导入</span>
								</a>
							</td>
						</tr>
					</table>
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
	loadGrid();
});

function refreshDataFn(){
	loadGrid();
}

function downloadTemplateFn(){
	var downurl = encodeURI(encodeURI(__ctx + '/comm/comm/downloadExcelModel?fileName=MM_PUP_ROUTE_LOAD.xlsx'));
	$('#downloadiframe').attr('src', downurl);
}

var isimport = false;
function importFileFn(){
	var frm = $('#importForm').form();
	var file = $("#file").val();
	if (file == "") {
		$.topCall.warn(htmsg.pub.notFindFileWarn);
		return;
	}
	frm.ajaxForm({
		success : function(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			var msg = JSON.parse(resultMessage.getMessage());
			var showMsg = msg['log'] == null || msg['log'] == '' ? msg['console'] : msg['log'];
			var flag = msg['flag'];
			showMsg = showMsg == null || showMsg == '' ? htmsg.pub.importSuccess : showMsg;
			if (resultMessage.isSuccess()) {
				$.topCall.closeProgress();
				loadGrid();
				$.topCall.success(showMsg, function() {
					if(flag == 1){
						isimport = true;
					}
				});
			}else if (resultMessage.getResult() == 0) {
				$.topCall.closeProgress();
				$.topCall.error(showMsg);
			} else {
				$.topCall.closeProgress();
				$.topCall.confirm("提示",showMsg,
					function(r) {
						if (!r) return;
						$('#importForm').attr("action", __ctx + "/pup/route/importRouteMessage");
						$('#importForm').submit();
					}
				);
			}
		}
	});
	$.topCall.progress();
	$('#importForm').attr("action", __ctx + "/pup/route/importRouteMessage");
	$('#importForm').submit();
}

function isImportFn(){
	if(!isimport){
		$.topCall.error("文件未导入或无正确数据!");
		return;
	}
	
	var waittingDialog= BootstrapDialog.waitting('正在执行，请等待...');
	var url=__ctx + "/pup/route/isImport";
    $.post(url,function(data){
        var obj = new com.hotent.form.ResultMessage(data);
        if(obj.isSuccess()){
        	loadGrid();
        	isimport = false;
            $.topCall.success("确定导入执行成功","提示信息");
            waittingDialog.close();
        }else{
            $.topCall.alert("确定导入执行失败",obj.data.cause);
	        waittingDialog.close();
        }
    });
}

function downloadDataFn(){
	var downurl = encodeURI(__ctx + '/pup/pickupTime/exportTempData');
	$('#downloadiframe').attr('src', downurl);
}
	
function loadGrid() {
	$('#grid').datagrid($.extend($defaultOptions,{
		url : "queryImportRouteMessage",
		idField : "id",
		sortName : 'ID',
		sortOrder : 'desc',
		fitColumns : false,
		columns : [[
			{field : 'id',checkbox : 'true'},
		    {field : 'area',sortName : "AREA",title : '区域',align : 'center',sortable : 'true'},
		    {field : 'routeDist',sortName : "ROUTE_DIST",title : '路线区分',align : 'center',sortable : 'true'},
		    {field : 'unloadPlace',sortName : "UNLOAD_PLACE",title : '卸货地点',align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',align : 'center',sortable : 'true'},
			{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',align : 'center',sortable : 'true'},
			{field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
			{field : 'supCalNum',sortName : "SUP_CAL_NUM",title : '台套数',align : 'center',sortable : 'true'},
			{field : 'wareCode',sortName : "WARE_CODE",title : '仓库代码',align : 'center',sortable : 'true'},
			{field : 'pickupType',sortName : "PICKUP_TYPE",title : '订单物流模式',align : 'center',sortable : 'true'},
			{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',align : 'center',sortable : 'true'},
			{field : 'routeName',sortName : "ROUTE_NAME",title : '路线名称',align : 'center',sortable : 'true'},
			{field : 'locDepth',sortName : "LOC_DEPTH",title : '最早落点',align : 'center',sortable : 'true'},
			{field : 'retEmptyPlatform',sortName : "RET_EMPTY_PLATFORM",title : '返空站台',align : 'center',sortable : 'true'},
			{field : 'advanceArrNum',sortName : "ADVANCE_ARR_NUM",title : '提前台套数',align : 'center',sortable : 'true'},
			{field : 'firstArriveTime',sortName : "FIRST_ARRIVE_TIME",title : '最早到货时间',align : 'center',sortable : 'true'},
			{field : 'speArriveTime',sortName : "SPE_ARRIVE_TIME",title : '特殊到货时间',align : 'center',sortable : 'true'},
			{field : 'transTime',sortName : "TRANS_TIME",title : '运输时间',align : 'center',sortable : 'true'},
			{field : 'recShiftA',sortName : "REC_SHIFT_A",title : 'A班收货',align : 'center',sortable : 'true'},
			{field : 'recShiftB',sortName : "REC_SHIFT_B",title : 'B班收货',align : 'center',sortable : 'true'},
			{field : 'wwlManager',sortName : "WWL_MANAGER",title : '外物流管理员',align : 'center',sortable : 'true'},
			{field : 'nwlManager',sortName : "NWL_MANAGER",title : '内物流管理员',align : 'center',sortable : 'true'},
			{field : 'mergeNum',sortName : "MERGE_NUM",title : '合并基准',align : 'center',sortable : 'true'},
			{field : 'pickCycle',sortName : "PICK_CYCLE",title : '取货周期',align : 'center',sortable : 'true'},
			{field : 'supOutTime',sortName : "SUP_OUT_TIME",title : '供应商出货时间',align : 'center',sortable : 'true'},
			{field : 'batch',sortName : "BATCH",title : '是否批次取货',align : 'center',sortable : 'true'
				,formatter:function(value,row,index){
					if('0' == value){
						return '否';
					}else if('1' == value){
						return '是';
					}
					return value;
				 }		
			},
			{field : 'startSortId',sortName : "START_SORT_ID",title : '供应商分组计算起始SORTID',align : 'center',sortable : 'true'},
			{field : 'departTimePoint',sortName : "DEPART_TIME_POINT",title : '发车时间基准',align : 'center',sortable : 'true'},
			{field : 'importStatus',sortName : "IMPORT_STATUS",title : '导入状态',width : 100,align : 'center',sortable : 'true',
				formatter:function(value,row,index){
					if('0' == value){
						return '<span style="color:red;">未导入</span>';
					}else if('1' == value){
						return '<span style="color:green;">已导入</span>';
					}
					return value;
				 }
			    },
			{field : 'checkResult',sortName : "checkResult",title : '校验结果',width : 130,align : 'center',sortable : 'true',
				formatter:function(value,row,index){
					if('0' == value){
						return '<span style="color:red;">错误</span>';
					}else if('1' == value){
						return '<span style="color:green;">通过</span>';
					}else if('2' == value){
						return '<span style="color:blue;">存在</span>';
					}
				}
			},
			{field : 'checkInfo',sortName : "checkInfo",title : '校验信息',width : 300,align : 'center',sortable : 'true'}
		 ] ]
	}));
}
</script>