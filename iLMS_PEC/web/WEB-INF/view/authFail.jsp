<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统出错了</title>
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
		
		textarea{
			color: red;
		}
	</style>
</head>
<body>
		<table  class="table-common" cellspacing="0">
	          		<tr >
	          			<th >
	          				<span style="font-family: fantasy;font-size: 16px;font-weight: bold;">系统出错了</span>
	          			</th>
	          		</tr>
	          		<tr  >
	          			<td style="text-align: center;height: 160px;padding: 2px 2px 2px 2px;">
	          				<textarea rows="50" style="width: 98%;height: 100%;">${SPRING_SECURITY_LAST_EXCEPTION.getMessage() }</textarea>
	          			</td>
	          		</tr>
	          		
	       </table>
</body>
</html>