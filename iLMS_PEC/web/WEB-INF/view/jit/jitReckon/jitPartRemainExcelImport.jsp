<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>当前零件余量-excel批量导入</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-refresh" onclick="refreshDataFn()" >
						<span>刷新</span>
					</a>
					<a class="btn btn-sm btn-primary fa-save" onclick="isImportFn()" >
						<span>确定导入</span>
					</a>
					<a class="btn btn-primary btn-sm fa-sign-out" onclick="downloadDataFn()">
						<span>数据导出</span>
					</a>
					<a class="btn btn-primary btn-sm fa-sign-in" onclick="downloadTemplateFn()">
						<span>模板下载</span>
					</a>
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class=" fa fa-angle-double-up"></i>
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
								<a class="btn btn-sm btn-primary fa-sign-in" onclick="importFileFn()" >
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
	
	//刷新
	function refreshDataFn(){
		loadGrid();
	}
	
	//模板下载
	function downloadTemplateFn(){
		var downurl = encodeURI(encodeURI(__ctx + '/comm/comm/downloadExcelModel?fileName=A.xlsx'));
		$('#downloadiframe').attr('src', downurl);
	}
	
	var isimport = false;
	//选择文件
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
							$('#importForm').attr("action", __ctx + "/jit/jitReckon/importJitPartRemain");
							$('#importForm').submit();
						}
					);
				}
			}
		});
		$.topCall.progress();
		$('#importForm').attr("action", __ctx + "/jit/jitReckon/importJitPartRemain");
		$('#importForm').submit();
	}
	
	//确认导入
	function isImportFn(){
		/* if(!isimport){
			$.topCall.error("请先导入文件");
			return;
		} */
		
		var waittingDialog= BootstrapDialog.waitting('正在执行，请等待...');
		var url=__ctx + "/jit/jitReckon/isImport";
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
	
	//数据导出
	function downloadDataFn(){
		var downurl = encodeURI(__ctx + '/jit/jitReckon/exportTempData');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//loadGrid
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jit/jitReckon/queryImportTempPage",
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},     
			{field : 'planCode',sortName : "planCode",title : '信息点',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'location',sortName : "location",title : '落点',width : 130,align : 'center',sortable : 'true'},
			{field : 'partNo',sortName : "partNo",title : '零件编号',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'partRemain',sortName : "partRemain",title : '零件余量',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'safetyInventory',sortName : "safetyInventory",title : '安全库存',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'endRequireDate',sortName : "endRequireDate",title : '截止日期',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'endRequireNum',sortName : "endRequireNum",title : '截止所需零件量',width : 130,align : 'center',sortable : 'true'}, 
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