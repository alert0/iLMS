<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>零件属地维护导入</title>
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
	var downurl = encodeURI(encodeURI(__ctx + '/comm/comm/downloadExcelModel?fileName=零件属地维护导入模板.xlsx'));
	$('#downloadiframe').attr('src', downurl);
}

var isimport = false;
function importFileFn(){
	var frm = $('#importForm').form();
	var file = $("#file").val();
	if (file == "") {
		$.topCall.warn(htmsg.pub.notFindFileWarn);
		return;
	} else if ( "零件属地维护导入模板" != file){
		$.topCall.warn("模板不正确，请重新选择" + file);
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
						$('#importForm').attr("action", __ctx + "/ter/parts/importModel");
						$('#importForm').submit();
					}
				);
			}
		}
	});
	$.topCall.progress();
	$('#importForm').attr("action", __ctx + "/ter/parts/importModel");
	$('#importForm').submit();
}

function isImportFn(){
	if(!isimport){
		$.topCall.error("文件未导入或无正确数据!");
		return;
	}
	
	var waittingDialog= BootstrapDialog.waitting('正在执行，请等待...');
	var url=__ctx + "/mp/excepOrder/isImport";
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
	var downurl = encodeURI(__ctx + '/mp/excepOrder/exportTempData');
	$('#downloadiframe').attr('src', downurl);
}
	
function loadGrid() {
	$('#grid').datagrid($.extend($defaultOptions,{
		url :  __ctx + "/ter/parts/getJson",
		idField : "id",
		sortName : 'ID',
		sortOrder : 'desc',
		columns : [ [
		{field : 'id',sortName : "id",checkbox : true},     
		{field : 'part_no',sortName : "part_no",title : '零件代码',
			width : 130,align : 'center',sortable : 'true'},
		{field : 'part_name_cn',sortName : "part_name_cn",title : '零件名称',
				width : 150,align : 'center',sortable : 'true'}, 
		{field : 'part_short_no',sortName : "part_short_no",title : '零件简码',
					width : 130,align : 'center',sortable : 'true'}, 
		{field : 'supplier_no',sortName : "supplier_no",title : '供应商代码',
			width : 200,align : 'center',sortable : 'true'},
		{field : 'supplier_name',sortName : "supplier_name",title : '供应商名称',
				width : 420,align : 'center',sortable : 'true'}, 
		{field : 'unload_port',sortName : "unload_port",title : '卸货口',
					width : 130,align : 'center',sortable : 'true'}, 
		{field : 'prepare_person',sortName : "prepare_person",title : '备货工程',
			width : 130,align : 'center',sortable : 'true'},
		{field : 'carpool',sortName : "carpool",title : '台车号',
				width : 150,align : 'center',sortable : 'true'}, 
		{field : 'storage',sortName : "storage",title : '物流库地址',
					width : 200,align : 'center',sortable : 'true'}, 
		{field : 'distri_person',sortName : "distri_person",title : '配送工程',
			width : 130,align : 'center',sortable : 'true'},
		{field : 'location',sortName : "location",title : '落点地址',
				width : 150,align : 'center',sortable : 'true'}, 
		{field : 'station_code',sortName : "station_code",title : '工位号',
					width : 130,align : 'center',sortable : 'true'}, 
		{field : 'shelf_no',sortName : "shelf_no",title : '货架号',
					width : 130,align : 'center',sortable : 'true'},
		{field : 'location_num',sortName : "location_num",title : '工程深度',
					width : 130,align : 'center',sortable : 'true'},
		{field : 'model_code',sortName : "model_code",title : '车型',
					width : 150,align : 'center',sortable : 'true'}, 
		{field : 'eff_start',sortName : "eff_start",title : '生效日期',
					width : 300,align : 'center',sortable : 'true'}, 
		{field : 'eff_end',sortName : "eff_end",title : '废止日期',
					width : 300,align : 'center',sortable : 'true'}
		] ]
	}));
}
</script>