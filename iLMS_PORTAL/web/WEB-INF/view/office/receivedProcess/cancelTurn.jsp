<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	
	<title>取回任务</title>
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
				$.topCall.success('任务成功取回!',function(){
					  //刷新父窗体
					  window.parentWindow.refreshTargetGrid("grid");
					  window.selfDialog.dialog('close');
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
				<button  role="button" href="javascript:;" class="btn btn-primary fa fa-save">确定</button>
			</div>
		</div>
		
		<form id="agreeForm" action="doCancelTurn" method="post">
			<input type="hidden" name="taskId" value="${taskId}">
			<table cellspacing="0" class="table-form">
				<tr> 
					<th>
						<abbr ht-tip title="提醒消息将会发送给指派人.">提醒消息:</abbr>
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