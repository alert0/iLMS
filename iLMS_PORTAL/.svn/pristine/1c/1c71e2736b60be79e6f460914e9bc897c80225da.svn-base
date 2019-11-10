<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html >
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>撤销流转任务</title>
	<%@include file="/commons/include/edit.jsp"%>
	<script type="text/javascript">
		var clicked_=false;
		$(function(){
			var frm = $('#agreeForm').form();
			$(".fa-save").click(function(){
				if(clicked_){
					$.topCall.alert("提示消息",'请等待返回不要重复点击!');
					return;
				}
				frm.ajaxForm({success:showResponse});
				if(frm.valid()){
					clicked_=true;
					frm.submit();
				}
			});
		});
	
		function showResponse(responseText) {
			clicked_=false;
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				$.topCall.success('流转任务被成功撤回!',function(){
					window.selfDialog.dialog('close');
					parentWindow.reloadLoad();
				});
			}else{
				$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
				
			}
		}
	</script>
	</head>
<body>
	<div>
		<div class="toolbar-panel">
			<div class="buttons">
				<button  href="javascript:;" class="btn btn-primary fa fa-save">确定</button>
			</div>
		</div>
		
		<form id="agreeForm" action="doRevokeTrans" method="post">
			<input type="hidden" name="instId" value="${instId}">
			<input type="hidden" name="taskId" value="${taskId}">
			
			<table cellspacing="0" class="table-form">
				<tr> 
					<th >
						<abbr ht-tip title="提醒消息将会发送给发起人及所有已审批过的执行人.">提醒消息:</abbr>
					</th>
					<td >
						<c:forEach items="${handlerList}" var="handler">
						<label class="checkbox-inline">
						  <input type="checkbox" name="messageType" value="${handler.type }" <c:if test="${handler.isDefault }">checked='checked'</c:if>  >${handler.title}
						</label>
						</c:forEach>
					</td>
				</tr>
				<tr> 
					<th >
						原因:
					</th>
					<td >
						<textarea class="inputText" name="cause" rows="4" 
							  cols="50" style="width: 300px; margin:5px" validate="{required:true}"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>