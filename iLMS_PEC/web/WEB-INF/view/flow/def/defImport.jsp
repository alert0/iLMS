<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>

<script type="text/javascript">
	var isimport = false;
	$(function() {
		var frm = $('#importForm').form();
		$("button.fa-save").click(function() {
			frm.ajaxForm({
				success : showResponse
			});
			if (frm.valid()) {
				$('#importForm').attr("action","importSave");
				$('#importForm').submit();
			}
		});
	});

	function showResponse(responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			$.topCall.success(resultMessage.getMessage(), function() {
				isimport = true;
				closeCallBack();
			});
		}else if (resultMessage.getResult() == 0) {
			$.topCall.error(resultMessage.getMessage());
		} else {//提示其系统已存在流程定义
			$.topCall.confirm("提示",resultMessage.getMessage(),
				function(r) {
					if (!r) return;
					$('#importForm').attr("action","importSave?newVer=1");
					$('#importForm').submit();
				}
			);
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
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<button href="javascript:;" class="btn btn-primary btn-sm fa-save">
				<span>上传</span>
			</button>
			<a href="javascript:;" class="btn btn-default btn-sm fa-close" onclick="closeCallBack()">
				<span>关闭</span>
			</a>
		</div>
	</div>
	<form id="importForm" action="importSave" method="post" enctype="multipart/form-data">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>
					<span>选择文件:</span>
				</th>
				<td>
					<input type="file" size="40" name="xmlFile" id="xmlFile" accept="application/zip" class="inputText input-wh-9" validate="{required:true}" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>