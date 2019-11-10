<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript">
	$(function(){
		var frm = $('#delegateForm').form();
		$("a.btn.btn-primary.fa-save").click(function() {
			frm.ajaxForm({success:showResponse});
			if (!frm.valid()) return;
			if(!$("#userId").val()) {
				alert("请选择人员");
				return;
			}
			$.topCall.confirm("提示信息", '确定转交该流程？', function(r){
				if(r){
					$('#delegateForm').submit();
				}
			});
			
		});
	});
	function showResponse(responseText) {
		var resultMessage=new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			refreshWelcomeList();//刷新首页信息列表
			$.topCall.success(resultMessage.getMessage(),function(){
				try{
					window.parent.close();
					window.close();
				}
				catch(err){
					window.close();
				}
			});
		
		}else{
			$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
		}
	}
	
	function refreshWelcomeList(){
		try {
			window.parent.opener.location.reload();
		 } catch(err){}
	}
	
	function selectUser(valueInput,textInput){
		CustomDialog.openCustomDialog("userSelector",function(data,dialog){
				if(data==null||data.length<=0){
					dialog.dialog('close');
					return false;
				}
				var resultValue=data[0]["id"];
				$("#"+valueInput).val(data[0]["id"]);
				$("#"+textInput).val(data[0]["name"]);
				dialog.dialog('close');
		    }
		,{selectNum:1});
	}
</script>
</head>
<body>
	<div>
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a role="button" href="javascript:;" class="btn btn-primary btn-sm fa fa-save">确定</a>
				<button onclick="javascript:window.selfDialog.dialog('close');" class="btn btn-default btn-sm  fa fa-close">取消</button>
			</div>
		</div>
		
		<form id="delegateForm" action="delegate" method="post">
			<table cellspacing="0" class="table-form w100">
			<tr>
				<th><span>通知方式：</span></th>
				<td>
					<c:forEach items="${handlerList}"  var="model">
						<label class="checkbox-inline">
							<input type="checkbox" name="messageType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if>>${model.title}
						</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th><span>转办人员：</span></th>
				<td>
					<input type="hidden" id="userId" name="userId"  >
					<input type="text" readonly="readonly" style=" margin:5px"  class="inputText" id="UserFullName" name="UserFullName"  >
					<a href="javascript:void(0)"   onclick="selectUser('userId','UserFullName')"><i class="fa fa-search"></i>选择</a>
				</td>
			</tr>
			<tr> 
				<th><span>转办原因：</span></th>
				<td><textarea name="opinion" rows="4" cols="50" style="width: 300px; margin:5px"  class="inputText"></textarea></td>
			</tr>
			</table>
			<input type="hidden" name="bpmTaskId" value="${taskId}">
		</form> 
	</div>
</body>
</html>