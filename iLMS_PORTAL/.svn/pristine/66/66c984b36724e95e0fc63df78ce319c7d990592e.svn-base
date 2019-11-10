<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
		
		<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
		<script>
		$(function() {
			var frm = $('#bpmDataTemplate').form();
			$(".buttons>a.fa-save").click(function() {
				frm.ajaxForm({
					success : showResponse
				});
				InitMirror.save();
				$('#bpmDataTemplate').submit();
			});

		 
	    });

	   

	    function showResponse(responseText){
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			if (resultMessage.isSuccess()) {
			    $.topCall.confirm("温馨提示", resultMessage.getMessage() + ',是否继续操作', function(r){
				if (r) {
				    window.location.reload(true);
				} else {
					HT.window.closeEdit(true,'grid');
				}
			    });
			} else {
			    $.topCall.error(resultMessage.getMessage(), resultMessage.getCause());
			}
	    }
		</script>
	</head>
	<body class="easyui-layout">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a href="javascript:;" class="btn btn-sm btn-primary fa-save"><span>保存</span></a>
				<a onclick="window.close();" class="btn btn-primary btn-sm fa-close" ><span>关闭</span></a>
			</div>
		</div>
		<form id="bpmDataTemplate" name="bpmDataTemplate" action="saveTemplate" method="post">
			<input type="hidden" name="id" value="${bpmDataTemplate.id }">
			<table cellspacing="0"  class="table-form">
				<tr >								
					<th><span>别名:</span></th>
					<td >${bpmDataTemplate.alias}</td>								
				</tr>
				<tr >								
					<th><span>模板内容:</span></th>
					<td><textarea id="html" name="templateHtml" codemirror="true" mirrorheight="500px" cols=100 rows=20 readonly>${fn:escapeXml(bpmDataTemplate.templateHtml)}</textarea></td>								
				</tr>
						
			</table>
		</form>
	</body>
</html>