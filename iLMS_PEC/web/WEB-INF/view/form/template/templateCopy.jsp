<%@page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript">
	var obj = window.passConf;
	var valid;
	$(function() {
		$("#templateName").val(obj.templateName);
		$("#alias").val(obj.alias);
		valid = $("#formCopy").form();
	});
	function save(dialog,callBack) {
		var rtn = valid.valid();
		if (!rtn) {
			return;
		}
		var templateId = obj.templateId;
		var newTemplateName = $("#newTemplateName").val();
		var oldTemplateName = $("#templateName").val();
		var alias = $("#alias").val();
		var newAlias = $("#newAlias").val();

		var url = "copyTemplate";
		var para = "templateId=" + templateId + "&newTemplateName=" + newTemplateName + "&newAlias=" + newAlias;

		if (newTemplateName == "" || newAlias == "") {
			//window.close();
		} else {
			if (newTemplateName == oldTemplateName) {
				$.topCall.error('模板名不能同名！');
			} else {
				$.post(url, para, function(data) {
					var obj = new com.hotent.form.ResultMessage(data);
					if (obj.isSuccess()) {
						$.topCall.success(obj.getMessage(), function() {
							dialog.dialog('close');
							if(callBack){
								callBack();
							}
						}, "提示信息");
					} else {
						$.topCall.error("复制模板失败", obj.getMessage());
					}
				});
			}
		}
	}
</script>
<style>
html {
	overflow-x: hidden;
}

.table-detail th {
	background-color: #EBEBEB;
	border-bottom: 1px solid #CACACA;
	border-right: 1px solid #CACACA;
	font-size: 12px;
	height: 32px;
	width: 20%;
	padding-right: 6px;
	text-align: right;
	font-weight: 100;
}
</style>
</head>
<body>
	<div class="panel"></div>
	<div class="panel-body">
		<form id="formCopy" name="formCopy">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="30%">原模板名称:</th>
					<td>
						<input type="text" id="templateName" name="templateName" class="inputText" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<th width="30%">新模板名称:</th>
					<td>
						<input type="text" id="newTemplateName" name="newTemplateName" class="inputText" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<th width="30%">原模板别名:</th>
					<td>
						<input type="text" id="alias" name="alias" class="inputText" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<th width="30%">新模板别名:</th>
					<td>
						<input type="text" id="newAlias" name="newAlias" class="inputText" validate="{required:true}" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>
