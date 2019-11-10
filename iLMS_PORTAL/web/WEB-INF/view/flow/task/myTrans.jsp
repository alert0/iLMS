<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript">
		function withDraw(id){
			if(!id) alert("taskId ："+id +"不合法");
			$("#taskId").val(id);
			$('#withDraw').dialog("open");
		}
		function toWithDraw(){
			var frm = $('#withDrawForm').form();
				frm.ajaxForm({success:showResponse});
				if(frm.valid()){
				   $('#withDrawForm').submit(); 
				}
		}
		function showResponse(responseText) {
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:resultMessage.getMessage()+',是否关闭',fn:function(r){
					if(r){
						window.selfDialog.dialog('close'); 
					}else{
					}
				}}});
			}else{
				$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
			}
		}
		</script>
	</head>
	<body  class="easyui-layout">
				<div id="tb" >
				</div>
				<table id="userGridList" class="easyui-datagrid"  data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'#tb'" fit="true" 
					 url="${ctx}/flow/task/getMyTransTask">
				    <thead>
					    <tr>
					    	<th field="name" sortable="true" sortName="name_" >任务名称</th>
							<th field="subject" sortable="true" sortName="subject_" >待办事项标题</th>
							<th field="createTime" sortable="true" sortName="create_time_"  formatter="ht" dateFormat="YYYY-MM-DD HH:mm:ss" title="任务创建时间">
							</th>
							<th field="dueTime" sortable="true" sortName="due_time_" formatter="ht" dateFormat="YYYY-MM-DD HH:mm:ss" title="任务到期时间">
							</th>
							<th manager=true >	
						     	<f:a alias="pk"  action="#" onclick="withDraw({id})" href="javascript:;" css="btn btn-default fa-history" >追回</f:a>
						    </th>
					    </tr>
				    </thead>
			    </table>
	
		<div id="withDraw" class="easyui-dialog" title="追回任务" 
       	 		data-options="modal:true,closed:true,
       	 		buttons:[{text:'确定',handler:function(){
       	 				toWithDraw();
       	 				}},
       	 				{text:'取消',handler:function(){
       	 					$('#withDraw').dialog('close'); 
				   		}}
				   ]"> 
			<div class="table-form">
				<form id="withDrawForm" action="${ctx}/flow/task/withDraw" method="post">
					<table cellspacing="0">
					<tr>
						<td ><span>通知方式：</span></td>
						<td>
							<input id="mess0" type="checkbox"  name="notifyType" value="inner" validate="{required:true}"><label for="mess0">内部消息</label>
							<input id="mess1" type="checkbox"  name="notifyType" value="mail" ><label for="mess1">邮件</label>
							<input id="mess2" type="checkbox"  name="notifyType" value="sms"  ><label for="mess2">短信</label>
						</td>
					</tr>
					<tr> 
						<td ><span>追回原因：</span></td>
						<td><textarea name="opinion" rows="4" cols="50"  validate="{required:true}"></textarea></td>
					</tr>
					</table>
					<input type="hidden" name="taskId" id="taskId">
				</form> 
			</div>
		</div>
	</body>
</html>