<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp"%>
		<script type="text/javascript">
		$(function() {
			var frm = $('#importForm').form();
			$("a.btn.fa-save").click(function() {
				frm.ajaxForm({
					success : showResponse
				});
				if (frm.valid()) {
					$('#importForm').submit();
				}
			});
		});

		function showResponse(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			if (resultMessage.isSuccess()) {
				$.topCall.success(resultMessage.getMessage());	
			} else {
				$.topCall.error(resultMessage.getMessage());
			}
		}
		</script>
	</head>
	<body  class="easyui-layout" fit="true" scroll="no">
		<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary btn-sm fa-save"><span>导入</span> </a>
			<a href="javascript:;" class="btn btn-sm btn-default fa-close" onclick="selfDialog.dialog('close')"><span>关闭</span> </a>
		</div>
	</div>
	<form id="importForm" action="importXml" method="post"
		enctype="multipart/form-data">
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>选择文件:</span></th>
				<td>
					<input type="file" size="40" name="xmlFile" id="xmlFile" accept="application/zip" 
						   class="inputText input-wh-9" validate="{required:true}"/>
				</td>
			</tr>
			<tr>
				<th><span>是否删除同用途的模版:</span></th>
				<td>
					<input type="checkbox"  name="clearAll" value="true" checked="checked" />
				</td>
			</tr>
			<tr>
				<th><span>是否设置为默认:</span></th>
				<td>
					<input type="checkbox"  name="setDefault" value="true" checked="checked" />
				</td>
			</tr>
		</table>
	</form>
	</body>
</html>