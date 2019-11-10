<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.hanthink.gps.pub.vo.UserVO" %>
<%@ page import="com.hanthink.gps.util.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广汽新能源i-LMS系统</title>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme() + "://"
           + request.getServerName() + ":" + request.getServerPort()
           + path + "/";
   UserVO user = (UserVO) session.getAttribute(Constants.USER_KEY);
   if (user == null) {
%>
    <script type="text/javascript">
        var topUrl = top.document.location;
        if (!/(?:login|outRegister)/i.test(topUrl)) {
            top.document.location = "<%=basePath%>login.action"
        }
    </script>
<% } %>
<script type="text/javascript">
function reloadImage() 
{ 
 //document.getElementById("codeImg").src='randCode.jsp?temp='+ (new Date().getTime().toString(36));
} 
</script>
<style type="text/css">
body, p, ul,h5 ,table ,h6 { 
    font-size           : 12px; 
    margin              : 0px;
    padding             : 0px;
    background-color:#FFF;
}
.loginTabBg{
    background-image:url(./images/bgimg.jpg);
    background-repeat: no-repeat;
    background-position: center center;
    width:100%;
    height:430px;
    margin-top:100px;
}
.login td{
    padding:3px;
    font-size:12px;
    color:#333;  
}

.text{
	width: 120px;
	height: 15px;
}
.textNext{
    width:90px;
    height:15px;
}
.select{
    width:100px;
}
.tabStyle {margin-top:20px;padding:0px;}
</style>
</head>

<body >
<div id="loginDiv" style="z-index: 100; top:40%; left: 47%; right:25%; position: absolute;"></div>
<div class="tabStyle">
<table class="loginTabBg"   align="center" border="0"  >
<tr>
    <td rowspan="2" align="right" ><img src="images/login.png" width="463" height="254"></td>
    <td height="95" valign="bottom"><img src="images/banner.jpg" width="320" height="60"></td>
</tr>
<tr>
    <td valign="top">
    <s:form action="login-query.action">
    <s:hidden name="hasTypeError" />
        <table width="100%" border="0" cellpadding="0"  class="login" cellspacing="0">
            <tr>
                <td width="20%" align="right" class='fistCloumn' style="">用户名:</td>
              <td align="left"  colspan="2"><s:textfield tabindex="1"  cssClass="text"  theme="simple" required="true" name="userName"/></td>
            </tr>
            <tr>
                 <td align="right" class='fistCloumn'>密码:</td>
              <td align="left" colspan="2"><s:password cssClass="text" tabindex="2" theme="simple" required="true" value="" name="password"/></td>
            </tr>
            <s:if test = "hasTypeError">
            <tr>
                 <td align="right" class='fistCloumn' >身份类型:</td>                     
              <td align="left"colspan="2"><s:select cssClass="select" theme="simple" tabindex="3" name="type" list="#{'0':'供应商','1':'GAMC','2':'物流公司'}" headerKey="" headerValue="请选择..."/></td>
            </tr>
			</s:if>
            <tr>
                 <td >&nbsp;</td>
                <td >&nbsp;</td>
                 <td width="43%" align="left"><s:submit theme="simple" value="登录"/></td>
                 <td width="33%"  align="left">
                   <s:url id="register" value="data/supplierOutRigest.action">
                     <s:param name="type">outRegister</s:param>
                   </s:url>
                 </td>
         </tr>
            <tr>
                 <td >&nbsp;</td>
                 <td style="color:red;text-align:left;font-size:12px;list-height:1.2em; "colspan="2" align="left"><s:fielderror/><s:actionerror/><s:actionmessage/></td>
            </tr>
        </table>
 </s:form>
    </td>
</tr>
</table>
</div>
</body>
</html>