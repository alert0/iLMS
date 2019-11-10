<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/platform/util/customUtil.js"></script>

<script type="text/javascript">
	function selectUsers(){
		var initData=[];
		var ids = $("#receiverId").val();
		var names = $("#receiver").val();
		if(names!=""){
			var arrName=names.split(',');
			var arrId=ids.split(',');
			$.each(arrName,function(index,item){
				initData.push({name:item,id:arrId[index]})
			})
		}	
		
		var callBack = function(data,dialog){
			if(data==null||data.length<=0){
				dialog.dialog('close');
				return false;
			}
			var userId="";
			var userName="";
			for(var i=0;i<data.length;i++){
				if(i==0){
					userId+=data[i].id;
					userName+=data[i].name;
				}
				else{
					userId+="," +data[i].id;
					userName+="," +data[i].name;
				}
			}
			$("#receiverId").val(userId);
			$("#receiver").val(userName);
			dialog.dialog('close');
	    };
		CustomDialog.openCustomDialog("userSelector",callBack,{
			initData:initData,
			selectNum:-1
		});
	}
	$(function(){
		var frm = $('#commuForm').form();
		$("a.btn.btn-primary.fa-save").click(function() {
			if (frm.valid()) {
				$.topCall.confirm("提示信息", '你确定要沟通吗？',function(r){
					if(r){
						frm.ajaxForm({success:showResponse});
						$('#commuForm').submit();
					}
				});
			}else{
				var receiver = $('#receiver').val();
				if(!receiver){
					$.topCall.error("沟通人员不能为空，请选择沟通人员！"); 
					return false;
				}
				var opinion = $('#opinion').val();
				if(!opinion){
					$.topCall.error("沟通内容不能为空，请输入沟通内容！"); 
					return false;
				}
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
			$.messager.alert("成功提示", "沟通发送成功!", "info", function () {
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
<style type="text/css">
fieldset.scheduler-border {
	border: 1px groove #ddd !important;
	padding: 0 0.4em 1.4em 0.8em !important;
}

.fa-add {
	margin-top: -30px;
}

.panel-tool a {
	background-color: #fff;
}
</style>
</head>
<body>
	<div class="toolbar-panel ">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa fa-save">确定</a> <a
				onClick="javascript:window.selfDialog.dialog('close');"
				class="btn btn-default fa fa-close">取消</a>
		</div>
	</div>
	<form id="commuForm" action="saveCommu" method="post">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>通知方式：</th>
				<td><c:forEach items="${handlerList}" var="model">
						<label class="checkbox-inline"> <input type="checkbox"
							name="notifyType" value="${model.type}"
							<c:if test="${ model.isDefault}">checked='checked'</c:if> />${model.title}
						</label>
					</c:forEach></td>
			</tr>
			<tr>
				<th>沟通人员：<span class="required">*</span></th>
				<td><textarea id="receiver" rows="2" cols="50"
						style="width: 270px; height: 27px; margin: 5px" readonly
						validate="{required:true}"></textarea>
				<input type="hidden"
					id="receiverId" name="receiverIds" value=""> <a
					onClick="selectUsers()" style="margin-top: -30px;"
					class="btn btn-default fa fa-user">选择人员</a></td>
			</tr>
			<tr>
				<th>沟通内容：<span class="required">*</span></th>
				<td><textarea name="opinion" rows="4" cols="50"
						style="width: 300px; margin: 5px" validate="{required:true}">${taskCommu.opinion}</textarea></td>
			</tr>
		</table>
		<input type="hidden" name="bpmTaskId" value="${bpmTaskId}">
	</form>
	</div>
	<c:if test="${not empty commuReceivers}">
		<fieldset class="scheduler-border" style="margin: 5px 5px 5px 5px;">
			<legend style="width: 90px">沟通回复</legend>
			<table cellspacing="0" class="table-form">
				<c:forEach items="${commuReceivers}" var="receiver">
					<tr>
						<th><span>回复人：</span></th>
						<td>${receiver.receiver}</td>
						<th><span>反馈时间：</span></th>
						<td><fmt:formatDate value="${receiver.feedbackTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<th><span>沟通意见：</span></th>
						<td colspan="3" style="height: 80px"><c:if
								test="${empty receiver.feedbackTime}">
								<span style="color: red"> 尚未回复</span>
							</c:if> ${receiver.opinion}</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
	</c:if>
</body>
</html>