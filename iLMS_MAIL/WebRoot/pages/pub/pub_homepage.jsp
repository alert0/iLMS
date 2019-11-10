<!-- 首页-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.hanthink.gps.pub.vo.UserVO" %>
<%@ page import="com.hanthink.gps.util.Constants" %>
<%
UserVO user = (UserVO) session.getAttribute(Constants.USER_KEY);
String userType = user.getUserType();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
       var userType ='<%=userType%>';
</script>
<script type="text/javascript" src="./js/pub/pub_homepage.js"></script>

</head>
<body class="bodyBg"  onkeydown="forbidBackSpace();">
<div id='messageInfoDiv'></div>
<div id="orderInfoDiv"></div>
<form name="MsgInfoDownLoadForm" method="post" action="pub/message-downloadMsgFile.action" target="FileDownload">
     <input type="hidden" name="infoId" value=""/>
     <input type="hidden" name="serverFileName" value=""/>
     <input type="hidden" name="fileDownLoadType" value=""/>
</form>
<iframe name="FileDownload" src="./comm/jsp/dummy.jsp" style='display:none'></iframe>
</body>
</html>