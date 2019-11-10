<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript">
		var paramObj = this.window.passConf;
		var isDialog = 0;
		var dialog;
		$(function() {
			if(paramObj){
				isDialog = paramObj.isDialog;
				dialog = paramObj.dialog;
				if(isDialog==1){
					$(".toolbar-panel").hide();
				}else{
					$(".toolbar-panel").show();
				}
			}else{
				$(".toolbar-panel").show();
			}
			
			var frm = $('#messageReplyForm').form();
			$("a.btn.fa-save").click(function() {
				frm.ajaxForm({success:showResponse});
				var content = Replyue.getContent();
				if (content && content!="") {
					$('#messageReplyForm').submit();
				}else{
					$.topCall.error("请填写回复内容!");
				}
			});
		});

		function showResponse(responseText) {
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				$.topCall.confirm("温馨提示",resultMessage.getMessage()+',是否继续操作',function(r){
					if(r){
						window.location.reload(true);
					}else{
						if(isDialog==1){
							dialog.dialog('close');
						}else{
							window.location.href="${ctx}/platform/innermsg/messageReceiver/list.ht";
						}
					}
				});
			}else{
				$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
			}
		}
		</script>
	</head>
	<body class="easyui-layout" fit="true">
		<div data-options="region:'center',border:false">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel col-md-13 ">
				<div class="buttons">
					<a class="btn btn-primary fa-save" href="javascript:void(0);"><span>回复</span></a>
					<a class="btn btn-primary fa-back" name="back" href="${ctx}/platform/innermsg/messageReceiver/list.ht" ><span>返回</span></a>
				</div>
			</div>
			<form id="messageReplyForm" action="save.ht" method="post">
				<table class="table-form"  cellspacing="0" >
						<tr>								
							<th><span>标题:</span></th>
							<td>
								${sysMessage.subject}
								<input class="inputText" type="hidden" id="msgId" name="msgId" value="${sysMessage.id}" />
							</td>								
						</tr>
						<tr>								
							<th><span>内容:</span></th>
							<td>${sysMessage.content}</td>								
						</tr>
						
						<tr>								
							<th><span>回复内容:</span></th>
							<td>
								<textarea id="content" name="content" style="height: 100%;width: 100%"></textarea>	
								<script type="text/javascript">
				    				var Replyue = UE.getEditor('content');
				   				 </script>
							</td>								
						</tr>
						<tr>								
							<th><span>是否私信:</span></th>
							<td>
								<input type="radio" name="isPrivate"  value="1" />是
								<input type="radio" name="isPrivate"  value="0" checked="checked" />否
							</td>								
						</tr>
				</table>
			</form>
		</div>
	</body>
</html>