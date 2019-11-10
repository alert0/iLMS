<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.hanthink.gps.pub.vo.UserVO" %>
<%@ page import="com.hanthink.gps.util.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
    <base href="<%=basePath%>"></base>
	<meta http-equiv="pragma" content="no-cache"></meta>
	<meta http-equiv="cache-control" content="no-cache"></meta>
	<meta http-equiv="expires" content="0"></meta>
	<meta http-equiv="keywords" content="SupplyWEB系统"></meta>
	<meta http-equiv="description" content="SupplyWEB系统"></meta>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var topUrl = top.document.location;
	</script>
<%
String pageSize = (String) session.getAttribute("PAGE_QUERY_SIZE");
UserVO user = (UserVO) session.getAttribute(Constants.USER_KEY);
    if (user == null) {
%>	
    <script type="text/javascript">
        if (!/(?:login)/i.test(topUrl)) {
            top.document.location = "<%=basePath%>login.action";
        }
    </script>
<% } %>
   
	<link rel="stylesheet" type="text/css" href="comm/ext/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/basic.css" />
	<link rel="stylesheet" type="text/css" href="css/RowActions.css" />
	<script type="text/javascript" src="comm/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="comm/ext/ext-all.js"></script>
	<script type="text/javascript" src="comm/ext/resources/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="comm/ext/resources/locale/globe-lang-zh_CN.js"></script>
	<script type="text/javascript" src="comm/js/baseData.js"></script>
	<script type="text/javascript" src="comm/js/common.js"></script>
	<script type="text/javascript">
		//pageSize = 25;
		pageSize = <%=pageSize%>;
	</script>
	