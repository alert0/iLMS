<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>查看未读信息</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript">
	
	// 查看下一条信息
	function nextMsg(){
		var url=__ctx + "/platform/innermsg/sysMessage/readMsgDialog.ht";
		window.location.href =url;
	}
		
	// 关闭窗口
	function winCls(){
		window.top.refresh();//在父窗体执行关闭动作；
	}
	
	// 提交回复
	function msgReply(){
		$("#messageReplyForm").ajaxForm({success:showResponse });
		var content = Replyue.getContent();
		
		if(content && content!=""){
			$("#messageReplyForm").submit(); 
		} else{
			$.topCall.error("请填写回复内容!");
		}
	}
	
	function showResponse(responseText)  { 
		var obj=new com.hotent.form.ResultMessage(responseText);
		if(obj.isSuccess()){//成功
			$.topCall.success(obj.getMessage(),function(rtn){
					$('#btnReply').attr('disabled',true);
			},'提示信息');
			
	    }else{//失败
	    	$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
	    }
	}
	
	// 回复是否私密
	function changePrivate(value){
		$("#isPrivate").val(value);
	} 
	</script>
</head>


<body class="easyui-layout" fit="true">
		<div data-options="region:'center',border:false">
				<table class="table-form"  cellspacing="0" >
						<tr>								
							<th ><span>标题:</span></th>
							<td colspan="3">
								${sysMessage.subject}
								<input class="inputText" type="hidden" id="msgId" name="msgId" value="${sysMessage.id}" />
							</td>								
						</tr>
						<tr>
							<th ><span>发信人:</span></th>
							<td>${sysMessage.owner}</td>
							<th ><span>发送时间:</span></th>
							<td><fmt:formatDate value="${sysMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>	
						<tr>								
							<th><span>内容:</span></th>
							<td colspan="3">
							${sysMessage.content}
							</td>								
						</tr>
						
						<c:if test="${sysMessage.canReply==1}">
							<tr>
								<th ><span>私密回复:</span> </th>
								<td colspan="3">
									<input type="radio" name="rdoPrivate" value="1" checked="checked" onclick="changePrivate(value)"/>是
									<input type="radio" name="rdoPrivate" value="0" onclick="changePrivate(value)"/>否
								</td>
							</tr>
							<tr>
								<th ><span>回复内容:</span></th>
								<td colspan="3">
									<form id="messageReplyForm" name="messageReplyForm" action="${ctx}/platform/innermsg/messageReply/save.ht" method="post">
									   <textarea id="content" name="content" style="height: 90%;width:100%"  ></textarea>	
										<script type="text/javascript">
						    				var Replyue = UE.getEditor('content');
						   				 </script>
									    <input type="hidden"  value="${msgReply.msgId}" name="msgId" />
									    <input type="hidden" value="${msgReply.isPrivate}"  id="isPrivate" name="isPrivate" />
									    </form>
								</td>
							</tr>
						</c:if>
				</table>
				
		</div>
		<div data-options="region:'south'" style="height:50px;margin-top:10px;text-align:center;">
			<form id="nextMsgForm" action=""></form>
			<c:if test="${flag==true}">
			 	<a class="btn btn-primary fa-close" href="javascript:void(0);" onclick="nextMsg()"><span>下一条</span></a>
			</c:if>
			<c:if test="${sysMessage.canReply==1}">
				<a class="btn btn-primary fa-save" id="btnReply" href="javascript:void(0);" onclick="msgReply()"><span>回复</span></a>
			</c:if>
				<a class="btn btn-primary fa-close" href="javascript:void(0);" onclick="winCls()"><span>关闭</span></a>
		</div>
	</body>
</html>
