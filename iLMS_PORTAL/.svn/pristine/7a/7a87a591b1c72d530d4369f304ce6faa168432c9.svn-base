<%@page import="org.springframework.security.access.AccessDeniedException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%
AccessDeniedException ex=(AccessDeniedException)request.getAttribute("ex");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>当前页面没有被授权</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<link rel="stylesheet" href="${ctx}/css/base.css"> 
	<style type="text/css">
		table.table-common{
			margin: 80px auto;
			width: 400px;
			border: 1px solid silver;
			
		}
		
		table.table-common th{
			height:32px;
			text-align:center;
			border-bottom: 1px solid silver;
			background: #F9F9F9;
		}
	</style>
</head>
<body style="overflow: hidden;" >
			<table  class="table-common" cellspacing="0">
	          		<tr height="25" align="center">
	          			<th >
	          			<span style="font-family: fantasy;font-size: 18px;font-weight: bold;">访问被拒绝!</span>
	          			</th>
	          		</tr>
	          		<tr height="160" align="center">
	          			<td>
	          				拒绝原因:<%=ex.getMessage() %>
	          			</td>
	          		</tr>
	          		
	       </table>
</body>
</html>