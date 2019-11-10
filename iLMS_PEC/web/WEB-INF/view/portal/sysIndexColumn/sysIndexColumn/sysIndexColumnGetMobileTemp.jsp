<%--
	time:2015-01-10 08:50:10
	desc:edit the 我的首页布局
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"  %>
<%@include file="/commons/include/html_doctype.html"%>
<head>
	<%@include file="/commons/include/mobile.jsp"%>
	<title>预览模版</title>
</head>
<script type="text/javascript">
	window.onload=function(){ 
		me.parser();
	}
</script>
<body style="overflow-y:auto; ">
	<div class="index-page">${html}</div>
</body>
</html>


