<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/Dialogs.js"></script>

<script type="text/javascript">
    var $copyToType = "${copyToType}";
	$(function(){
		var frm = $('#transForm').form();
		frm.addHidden("copyToType",$copyToType);
		$(".fa-save").click(function() {
			frm.ajaxForm({success:showResponse});
			if (!frm.valid()) return;
			if(!$("#userId").val()) {
				$.topCall.warn("请选择人员");
				return;
			}
			if(!isSelectMessageType()){
				$.topCall.warn("请选择通知方式");
				return ;
			}
			var msg = "确定抄送该流程？";
			if($copyToType=="1"){
				 msg = "确定转发该流程？";
			}
			$.topCall.confirm("提示信息",msg,function(r){
				if(r){
					$('#transForm').submit();
				}
			});
		});
		
		$(".fa-user").click(function(){
			var conf={single:false,callBack:function(data){
				var aryId=[];
				var aryName=[];
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					aryId.push(obj.id);
					aryName.push(obj.name);
				}
				if(aryId.length>0){
					$("#userId").val(aryId.join(","));
					$("#userName").val(aryName.join(","));
				}
			}};
			UserDialog(conf);
		})
	});
	function showResponse(responseText) {
		var resultMessage=new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			$.topCall.success(resultMessage.getMessage(),function(){
				HT.window.closeEdit();
			})
		}else{
			$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
		}
	}
	
	//判断是否已选择通知方式
	function isSelectMessageType(){
		var isSelect = false;
		$('[name="messageType"]').each(function() {  
            if (this.checked) { 
            	isSelect = true;
            	return true;
            }  
        });  
		return isSelect;
	}
</script>
</head>
<body>
	<div>
		<div class="toolbar-panel">
			<div class="buttons">
				<a role="button" href="javascript:;" class="btn btn-primary btn-sm fa fa-save">确定</a>
				<button onclick="HT.window.closeEdit();" class="btn btn-default btn-sm  fa fa-close">取消</button>
			</div>
		</div>
		
		<form id="transForm" action="instanceTrans" method="post">
			<table cellspacing="0" class="table-form">
			<tr>
				<th>通知方式：</th>
				<td>
					<c:forEach items="${handlerList}"  var="model">
						<label class="checkbox-inline">
							<input type="checkbox" name="messageType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if>>${model.title}
						</label>
					</c:forEach>
					
				</td>
			</tr>
			<tr>
				<th>人员：</th>
				<td>
					<input type="hidden" id="userId" name="userId"  >
					<input id="userName" readonly="readonly"  />
					
					<a role="button" href="javascript:"  class="btn btn-primary btn-sm fa fa-user">选择人员</a>
				</td>
			</tr>
			<tr> 
				<th>原因：</th>
				<td><textarea name="opinion" rows="4" cols="50" style="width: 300px; margin:5px"  class="inputText"></textarea></td>
			</tr>
			</table>
			<input type="hidden" name="instanceId" value="${instanceId}">
			<input type="hidden" name="nodeId" value="${nodeId}">
		</form> 
	</div>
</body>
</html>