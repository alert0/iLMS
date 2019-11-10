<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
 
<script type="text/javascript">
	var isAdd=${isAdd};
	$(function() {
		var frm = $('#dataDictForm').form();
		$(".fa-save").click(function() {
			frm.ajaxForm({
				success : showResponse
			});
			if (frm.valid()) {
				$('#dataDictForm').submit();
			}
		});

		$("#name").blur(function() {
			if ($("#key").val())
				return;
			var obj = $(this);
			var str = ChineseToPinyin({
				Chinese : obj.val()
			});
			$("#key").val(str);
		});

	});

	function showResponse(responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			$.topCall.confirm("温馨提示", resultMessage.getMessage() + ',是否继续操作',
					function(r) {
						passConf.callback&&(passConf.callback(1));
						if (r&&isAdd) {
							$("#name").val('');
							$("#key").val('');
						} else {
							selfDialog.dialog('close');
						}
					});
		} else {
			$.topCall.error(resultMessage.getMessage());
		}
	}
</script>
</head>
<body>
	<div class="toolbar-panel col-md-13">
		<div class="buttons">
			<button  class="btn btn-primary btn-sm fa-save">保存</button>
			<button class="btn btn-primary btn-sm fa-close" onclick="selfDialog.dialog('close')">关闭</button>
		</div>
	</div>
	<form id="dataDictForm" action="save" method="post">
		<div class="form-table">
			<table class="table-form" cellspacing="0">
				<tr>
					<th ><span>项名称:</span></th>
					<td>
						<input type="text" id="name" name="name" value="${dataDict.name}" class="inputText" validate="{required:false,maxlength:384}" />
					</td>
				</tr>
				<tr>
					<th><span>项值</span></th>
					<td>
						<input type="text" id="key" name="key" value="${dataDict.key}" class="inputText" validate="{required:false,maxlength:120}" /> <span style="color: gray;">当前数据字典中必须唯一</span>
					</td>
				</tr>
				<input type="hidden" id="parentId" name="parentId" value="${dataDict.parentId}${parentId}" />
				<input type="hidden" name="id" value="${dataDict.id}" />
				<input type="hidden" id="typeId" name="typeId" value="${dataDict.typeId}${typeId}" />
			</table>
		</div>
	</form>
</body>
</html>