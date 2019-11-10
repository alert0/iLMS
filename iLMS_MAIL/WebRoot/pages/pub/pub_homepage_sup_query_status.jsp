<!-- 公告信息查看跟踪 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="./js/pub/pub_homepage_sup_query_status.js"></script>
<%String infoId = request.getParameter("infoId").toString(); %>
	<script type="text/javascript">
		var infoId = <%=infoId%>;
    </script>
</head>
<body class="bodyBg"  onkeydown="forbidBackSpace();">
<div id='totalDiv'></div>
<div id="infoDiv"></div>

</body>
</html>