<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>DEMO</title>
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
							</td><td>
								<a class="btn btn-sm btn-primary fa-sign-in" href="javascript:void(0)" onclick="importFileFn2()" >
									<span>文件导入(大数量)</span>
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
	var downurl = encodeURI(encodeURI(__ctx + '/comm/comm/downloadExcelModel?fileName=DEMO导入测试模板.xlsx'));
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
			showMsg = showMsg == null || showMsg == '' ? htmsg.pub.importSuccess : showMsg;
			if (resultMessage.isSuccess()) {
				$.topCall.closeProgress();
				loadGrid();
				$.topCall.success(showMsg, function() {
					isimport = true;
				});
			}else if (resultMessage.getResult() == 0) {
				$.topCall.closeProgress();
				$.topCall.error(showMsg);
			} else {
				$.topCall.closeProgress();
				$.topCall.confirm("提示",showMsg,
					function(r) {
						if (!r) return;
						$('#importForm').attr("action", __ctx + "/demo/demo/importDemoModel");
						$('#importForm').submit();
					}
				);
			}
		}
	});
	$.topCall.progress();
	$('#importForm').attr("action", __ctx + "/demo/demo/importDemoModel");
	$('#importForm').submit();
}

function importFileFn2(){
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
			var showMsg = msg['log']==null||msg['log']==''?msg['console']:msg['log'];
			showMsg = showMsg==null||showMsg==''?"导入成功！":showMsg;
			if (resultMessage.isSuccess()) {
				$.topCall.closeProgress();
				$.topCall.success(showMsg, function() {
					isimport = true;
					loadGrid();
				});
			}else if (resultMessage.getResult() == 0) {
				$.topCall.closeProgress();
				$.topCall.error(showMsg);
			} else {
				$.topCall.closeProgress();
				$.topCall.confirm("提示",showMsg,
					function(r) {
						if (!r) return;
						$('#importForm').attr("action", __ctx + "/demo/demo/importDemoModel2");
						$('#importForm').submit();
					}
				);
			}
		}
	});
	$.topCall.progress();
	$('#importForm').attr("action", __ctx + "/demo/demo/importDemoModel2");
	$('#importForm').submit();
}

function isImportFn(){
	if(!isimport){
		$.topCall.error("请先导入文件");
		return;
	}
	
	var waittingDialog= BootstrapDialog.waitting('正在执行，请等待...');
	var url=__ctx + "/demo/demo/isImport";
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
	var downurl = encodeURI(__ctx + '/demo/demo/exportTempData');
	$('#downloadiframe').attr('src', downurl);
}
	
function loadGrid() {
	$('#grid').datagrid($.extend($defaultOptions,{
		url :  __ctx + "/demo/demo/curdlistImportTempJson",
		idField : "pkId",
		sortName : 'pk_Id',
		sortOrder : 'desc',
		columns : [ [
		{field : 'pkId',sortName : "pkId",checkbox : true},     
		{field : 'col1',sortName : "col1",title : '字段一',width : 130,align : 'center',sortable : 'true'}, 
		{field : 'col2',sortName : "col2",title : '字段二',width : 130,align : 'center',sortable : 'true'},
		{field : 'col3',sortName : "col3",title : '字段三',width : 150,align : 'center',sortable : 'true'}, 
		{field : 'col4',sortName : "col4",title : '字段四',width : 130,align : 'center',sortable : 'true'}, 
		{field : 'importStatus',sortName : "importStatus",title : '导入状态',width : 100,align : 'center',sortable : 'true',
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