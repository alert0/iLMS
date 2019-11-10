<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="easyui-layout">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a  onclick="HT.window.close(true)"; href="javascript:;" class="btn btn-primary btn-sm fa-back" style="margin-left:0;"><span>返回</span></a>
			</div>
		</div>
		
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>主题:</span></th>
				<td>${sysMessage.subject}</td>
			</tr>
			<tr>
				<th><span>发信人:</span></th>
				<td>${sysMessage.owner}</td>
			</tr>
			<tr>
				<th><span>发信时间:</span></th>
				<td><fmt:formatDate value="${sysMessage.createTime}" /></td>
			</tr>
			<tr>
				<th width="20%">内容:</th>
				<td>${sysMessage.content} 
				</td>
			</tr>
		</table>

</body>
</html>