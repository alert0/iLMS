<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript">
	var isimport = false;
	function upload(){
		var frm = $('#importForm').form();
		frm.ajaxForm({
			success : showResponse
		});
		if (frm.valid()) {
			$.topCall.progress();
			$('#importForm').submit();
		}
	}
	
	function showResponse(responseText) {
		$.topCall.closeProgress();
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			isimport = true;
			$("#msg").show();
			$("#content").val(resultMessage.getMessage());
			HT.window.refreshParentGrid();
			HT.window.closeEdit();
		} else {
			$("#msgContent").text("");
			$.topCall.error(resultMessage.getMessage());
		}
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function closeCallBack(){
		selfDialog.dialog('close');
		if(isimport){
			var tabElement = parent.document.getElementById("tabs");
			var jqtabElement = $(tabElement);    //jQuery对象 
			var frames = $(jqtabElement).find("iframe");
			for(var i=0;i<frames.length;i++){
				var myiframe = $(frames[i]).context;
				if(myiframe.contentWindow.reloadForImport) {
					myiframe.contentWindow.reloadForImport();
				}
			}
		}
	}
</script>
</head>
<body>
	<div class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary btn-sm fa-save" onclick="upload()"><span>上传</span> </a>
			<a href="javascript:;" class="btn btn-default btn-sm fa-close" onclick="closeCallBack()"><span>关闭</span> </a>
		</div>
	</div>
	<form id="importForm" action="${ctx}/system/query/querySqldef/import" method="post" enctype="multipart/form-data">
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>选择文件:</span></th>
				<td>
					<input type="file" size="40" name="xmlFile" id="xmlFile" accept="application/zip"
					 	     validate="{required:true}"/>
				</td>
			</tr>
			<tr hidden="true" id="msg">
				<th><span>上传结果</span></th>
				<td>
					<textarea rows="8" cols="60" id="content"  class="inputText input-width-80"></textarea>
					
				</td>
			</tr>
		</table>
	</form>
</body>
</html>