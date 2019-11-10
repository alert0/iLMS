<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/dialog/UserSelectDialog.js"></script>
<script type="text/javascript">
	function selectUsers(){
		new UserSelectDialog({type:"1",callback:function(userIds,fullnames,dialog){		
			$("#receiverId").val(userIds);
			$("#receiver").val(fullnames);
		}}).show();
	}
	$(function(){
		$(".fa-save").click(function() {
			var frm = $('#feedbackForm').form();
			$.topCall.confirm("提示信息", '你确定要反馈吗？', function(r){
				if(!r) return;
				frm.ajaxForm({success:showResponse});
				if (frm.valid()) {
					frm.submit();
				}
			});
			$('.panel-tool-close').hide(); 
		});
	});
	function showResponse(responseText) {
		//执行后置脚本
		var data = JSON.parse(responseText);
		var script = "var tempFunction = function(data){ "+window.parent.curent_btn_after_script_+"};"
		var afterScriptPassed =  eval(script+"tempFunction(data);");
		
		var resultMessage=new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			refreshWelcomeList();//刷新首页信息列表
			$.messager.alert("成功提示", resultMessage.getMessage(), "info", function () {
				try{
				    window.parent.opener.refreshTargetGrid("grid");
				    if(afterScriptPassed!==false) window.parent.close(); 
				 }
				 catch(err){
				     window.parent.close(); 
				 }
		    });  
			$('.panel-tool-close').hide();//隐藏成功提示框右上角的关闭按钮
		}else{
			$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
		}
	}
	
	function refreshWelcomeList(){
		try {
			window.parent.opener.location.reload();
		 } catch(err){}
	}
</script>

</head>
<body >
	<div class="toolbar-panel">
		<div class="buttons">
			<a  href="javascript:;" class="btn btn-primary fa fa-save">确定</a>
		</div>
	</div> 
		<form id="feedbackForm" action="feedback" method="post">
			<table cellspacing="0" class="table-form">
			<tr>
				<th width="15%"><span>沟通发送人：</span></th>
				<td width="35%">
					${taskCommu.sender }
				</td>
				<th width="15%"><span>沟通时间：</span></th>
				<td width="35%">
					<fmt:formatDate value="${taskCommu.createtime }" pattern="yyyy年MM月dd日HH点mm分ss秒" /> 
					
				</td>
			</tr>
			<tr>
				<th><span>沟通内容：</span></th>
				<td colspan="3">
					${taskCommu.opinion }
				</td>
			</tr>
			
			<tr>
				<th><span>通知方式：</span></th>
				<td colspan="3">
					<c:forEach items="${handlerList}"  var="model">
						<label class="checkbox-inline">
							<input type="checkbox" name="messageType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if>>${model.title}
						</label>
					</c:forEach>
				</td>
			</tr>
			
			<tr> 
				<th><span>反馈内容：</span></th>
				<td colspan="3"><textarea name="opinion" rows="4" cols="50" style="width: 300px; margin:5px" validate="{required:true}"></textarea></td>
			</tr>
			</table>
			<input type="hidden" name="taskId" value="${taskId}">
		</form> 
</body>
</html>