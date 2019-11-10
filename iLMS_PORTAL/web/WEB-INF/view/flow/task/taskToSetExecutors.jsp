<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>

<script type="text/javascript">
	$(function(){
		
		var frm = $('#frmForm').form();
		frm.valid();
		$(".fa-save").click(function() {
			if(!frm.valid() || !$("#receiverId").val()) {
				$.topCall.error("请选择人员和填写通知内容"); 
				return;
			}
			$.topCall.confirm("提示信息", '你确定要修改任务执行人吗？', function(r){
				if(r){
					frm.ajaxForm({success:showResponse});
					if (frm.valid()) {
						$('#frmForm').submit(); 
					}
				}
			});
		});
		
	});
	
	function showResponse(data) {
		if(data.result==1){
			refreshWelcomeList();//刷新首页信息列表
			$.topCall.success(data.message,function(){
				window.parentWindow.refresh();
				window.selfDialog.dialog('close');
			});
		}else{
			$.topCall.error(data.message,data.cause); 
		}
	}
	
	function refreshWelcomeList(){
		try {
			window.parent.opener.location.reload();
		 } catch(err){}
	}
	
	
	
	
	function selectUsers(){
		var initData=[];
		var ids=$("#receiverId").val();
		var names=$("#receiver").val();
		if(names!=""){
			var arrName=names.split(',');
			var arrId=ids.split(',');
			$.each(arrName,function(index,item){
				initData.push({name:item,id:arrId[index]})
			})
		}
		
		var callBack =function(data,dialog){
			var arrId=[];
			var arrText=[];
			$.each(data,function(index,item){
				arrId.push(item.id);
				arrText.push(item.name);
			})
			$("#receiverId").val(arrId.toString());
			$("#receiver").val(arrText.toString()).focus();
			dialog.dialog('close');
	    };
		CustomDialog.openCustomDialog("userSelector",callBack,{
			initData:initData,
			selectNum:-1
		});
		 
	}
</script>

</head>
<body >
	<div>
		<div class="toolbar-panel">
			<div class="buttons" >
				<a  href="javascript:;" class="btn btn-primary fa fa-save">确定</a>
				<a href="javascript:;" onClick="javascript:window.selfDialog.dialog('close');" class="btn btn-primary fa fa-close">取消</a>
			</div>
		</div> 
		<form id="frmForm" action="setTaskExecutors" method="post">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>人员：</th>
				<td  >
					<textarea  id="receiver" rows="2" cols="50" style="width: 270px; height:27px;margin:5px" readonly validate="{required:true}" ></textarea>
					<input type="hidden" id="receiverId" name="receiverIds" value="">
					<a onClick="selectUsers()" style="margin-top:-30px;" class="btn btn-default fa fa-user">选择人员</a>
				</td>
			</tr>
			<tr>
				<th>通知方式：</th>
				<td >	
					<c:forEach items="${handlerList}" var="model">
						<label class="checkbox-inline">
							<input type="checkbox" name="notifyType"  value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if> />${model.title}
						</label>
					</c:forEach>
				</td>
			</tr>
			
			<tr> 
				<th><span>通知内容：</span></th>
				<td >
					<textarea class="inputText" name="opinion" rows="4"   cols="50" style="width: 300px;" validate="{required:true}"></textarea>
				</td>
			</tr>
			</table>
			<input type="hidden" name="taskId" value="${taskId}">
		</form> 
		</div>
	</div>
</body>
</html>