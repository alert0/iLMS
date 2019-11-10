<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加会签任务</title>
	<%@include file="/commons/include/edit.jsp"%>
	<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
	<script type="text/javascript">
		$(function(){
			var frm = $('#addSignTaskForm').form();
			var	json = window.passConf;
			//frm.addHidden('data',JSON.stringify(json));
			$("#save-btn").click(function(){
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
				$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:resultMessage.getMessage()+',是否关闭',fn:function(r){
					if(r){
						window.parent.close();
					}
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
		
		function selectUser(obj){
			var me =$(obj), id =me .attr("selectorId"),
				name = me.attr("selectorName");
			
		 
			var callBack = function(data,dialog){
				if(data==null||data.length<=0){
					dialog.dialog('close');
					return false;
				}
				var ids=[];
				var names=[];
				$.each(data,function(index,item){
					ids.push(item.id);
					names.push(item.name);
					
				})
				$("input[name='"+id+"']").val(ids.toString());
				$("textarea[name='"+name+"']").val(names.toString());
				dialog.dialog('close');
		    };
			CustomDialog.openCustomDialog("userSelector",callBack,{
				selectNum:-1
			});
		}
	</script>
	</head>
<body>
	<div class="container-fluid">
			<div class="toolbar-panel col-md-13 ">
				<div class="buttons">
					<a  href="javascript:;" id="save-btn" class="btn btn-primary fa fa-save">确定</a>
					<a href="javascript:;" onclick="javascript:window.selfDialog.dialog('close');" class="btn btn-primary fa fa-back">取消</a>
				</div>
			</div>
		
		<form id="addSignTaskForm" action="doAddSignTask" method="post">
			<input type="hidden" name="taskId" value="${taskId}">
			<table cellspacing="0" class="form-table w95">
				<tr> 
					<th width="20%">
						加签人员:
					</th>
					<td colspan="3">
						<input type="hidden" name="addSignTaskUserId" />
						<textarea name="addSignTaskUserName" rows="2" cols="50" class="inputText" 
							  	  style="width: 270px; height:30px;margin:5px" readonly="readonly" 
							  	  validate="{required:true}" ></textarea>
						<a onclick="selectUser(this)" selectorId="addSignTaskUserId" 
						   selectorName="addSignTaskUserName" class="btn btn-default fa fa-add">选择人员</a>
					</td>
				</tr>
				<tr> 
					<th width="20%">
						提醒消息:
					</th>
					<td colspan="3">
						<label class="checkbox-inline">
						  <input type="checkbox" name="messageType" value=inner>内部消息
						</label>
						<label class="checkbox-inline">
						  <input type="checkbox" name="messageType" value="mail">邮件
						</label>
						<label class="checkbox-inline">
						  <input type="checkbox" name="messageType" value="sms">短信
						</label>
					</td>
				</tr>
				<tr> 
					<th width="20%">
						加签原因:
					</th>
					<td colspan="3">
						<textarea class="form-control" name="addReason" rows="3"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>