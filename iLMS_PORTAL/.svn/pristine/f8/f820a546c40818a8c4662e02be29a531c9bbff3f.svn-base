<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html >
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>终止流程</title>
<%@include file="/commons/include/edit.jsp" %>
	<script type="text/javascript">
		$(function(){
			var frm = $('#agreeForm'),
				json = window.passConf;
			frm.addHidden('data', JSON.stringify(json));
			$("a.fa-save").click(function(){
				if($("[name='endReason']").val()=="")
				{
					alert("请填写终止原因");
					return false;
				}
				frm.ajaxForm({success:showResponse});
				if(frm.valid()){
					frm.submit();
				}
			});
		});
	
		function showResponse(responseText) {
			var data = JSON.parse(responseText);
			var script = "var tempFunction = function(data){ "+window.parent.curent_btn_after_script_+"};"
			var afterScriptPassed =  eval(script+"tempFunction(data);");				

			
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				refreshWelcomeList();//刷新首页信息列表
				$.topCall.message({base:{type:'alert',title:'温馨提示',msg:resultMessage.getMessage(),fn:function(r){
 
					    window.parent.opener.refreshTargetGrid("grid");
					    if(afterScriptPassed!==false)window.parent.close();
					 
				}}});
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
<body>
	<div>
		<div class="toolbar-panel">
			<div class="buttons" >
				<a  href="javascript:;" class="btn btn-primary fa fa-save">确定</a>
				<a href="javascript:;" class="btn btn-default fa fa-close" onClick="javascript:window.selfDialog.dialog('close');" >取消</a>
			</div>
		</div>
		
		<form id="agreeForm" action="doEndProcess" method="post">
			<input type="hidden" name="taskId" value="${taskId}">
			<table cellspacing="0" class="table-form">
				<tr> 
					<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;">
						<abbr ht-tip title="提醒消息将会发送给发起人及所有已审批过的执行人.">提醒消息:</abbr>
					</th>
					<td >
						<c:forEach items="${handlerList}"  var="model">
							<label class="checkbox-inline">
								<input type="checkbox" name="messageType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if>>${model.title}
							</label>
						</c:forEach>
					</td>
				</tr>
				<tr> 
					<th width="20%" >
						终止原因:
					</th>
					<td>
						<textarea class="inputText" name="endReason" rows="4" 
							  cols="50" style="width: 300px; margin:5px" validate="{required:true}"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>