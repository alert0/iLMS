<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>检查到XSS攻击</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<link rel="stylesheet" href="${ctx}/css/base.css"> 
	<style type="text/css">
	
		
		
		table.table-common{
			margin: 100px auto;
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
	          		<tr  align="center">
	          			<th >
	          				<span style="font-family: fantasy;font-size: 18px;font-weight: bold;">检测到XSS攻击</span>
	          			</th>
	          		</tr>
	          		<tr height="160" align="center">
	          			<td>
	          				当前检测到了XSS攻击,请检查页面是否输入了HTML标签!
	          			</td>
	          		</tr>
	          		
	       </table>
</body>
</html>