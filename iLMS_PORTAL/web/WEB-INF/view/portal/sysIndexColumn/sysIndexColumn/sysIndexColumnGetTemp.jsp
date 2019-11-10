<%--
	time:2015-01-10 08:50:10
	desc:edit the 我的首页布局
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"  %>
<%@include file="/commons/include/html_doctype.html"%>
<head>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<script type="text/javascript" src="${ctx}/js/hotent/index/jquery.blockUI.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/echarts/echarts.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/index/indexPage.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/index/myHome.js"></script>
	<f:link href="myHome/myHome.css"></f:link>
	<f:link href="other/myHome-other.css"></f:link>
	<title>预览模版</title>
</head>
<script type="text/javascript">
	window.onload=function(){ 
		me.parser();
	}
</script>
<body >
	<div class="index-page">${html}</div>
</body>
</html>


