<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript">
		</script>
	</head>
	<body class="easyui-layout">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th><span>主题:</span></th>
						<td>${sysMessage.subject}</td>
					</tr>
					<tr>								
						<th><span>消息类型:</span></th>
						<td>${sysMessage.messageType}</td>
					</tr>
					<tr>								
						<th><span>收信人:</span></th>
						<td>${sysMessage.receiverName}</td>
					</tr>
					<tr>								
						<th><span>发送时间:</span></th>
						<td><fmt:formatDate value="${sysMessage.createTime}" /></td>		
					</tr>
					<tr>								
						<th><span>内容:</span></th>
						<td>${sysMessage.content}
						<%-- <textarea id="content" name="content" style="height: 100%;width: 100%" >${sysMessage.content}</textarea>	
						<script type="text/javascript">
		    				var ue = UE.getEditor('content',{  
	    		                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  预览
	    		                 toolbars:[ ['FullScreen', 'Source', 'Undo', 'Redo','bold','test']],   
	    		                //关闭字数统计  
	    		                wordCount:false,  
	    		                //关闭elementPath  
	    		                elementPathEnabled:false
	    		            });
		   				 </script> --%>
						</td>
					</tr>
		</table>
	</body>
</html>