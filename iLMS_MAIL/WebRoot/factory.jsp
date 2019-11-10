<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script type="text/javascript">

</script>
<style type="text/css">

body, p, ul,h5 ,table ,h6 { 
    font-size           : 12px; 
    margin              : 0px;
    padding             : 0px;
    background-color:#FFF;
}
.loginTabBg{
    background-position: center center;
    width:100%;
    height:430px;
    margin-top:20px;
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
<title>广汽新能源i-LMS系统</title>
</head>
<body id="gsl-index" style="background-color:white;">

<div class="tabStyle">
<table class="loginTabBg"   align="center" border="0"  >
<tr>
    <td align="center" colspan="4">
    	<img src="images/gps_title.png" style="border:none;">
    </td>
</tr>

<s:iterator value="factoryVOs" id='factory' status='st'> 
     <s:if test="#st.Odd"> 
         
		 <tr>
		 <td width="25%">&nbsp;</td>
		 <td align="center" width="25%">
			<div style="border:0px solid green;width:463;height:254;">
				<a href="login-factoryLogin.action?loginFactoryCode=<s:property value="#factory.factoryCode" />" title="点击进入">
					<img src="images/factory_<s:property value="#factory.factoryCode" />.png" style="border:none;">
				</a>
			</div>
			<div style = "text-align:center;">
				<a href="login-factoryLogin.action?loginFactoryCode=<s:property value="#factory.factoryCode" />" title="点击进入">
				<s:property value="#factory.factoryName" />
				</a>
			</div>
		</td>

		<s:if test="#st.Last"> 
			 <td align="center" width="25%">&nbsp;</td>
			 <td width="25%">&nbsp;</td>
			 </tr>
		 </s:if>

     </s:if> 
	 <s:if test="#st.Even"> 
         <td align="center" width="25%">
			<div style="border:0px solid green;width:463;height:254;">
				<a href="login-factoryLogin.action?loginFactoryCode=<s:property value="#factory.factoryCode" />" title="点击进入">
					<img src="images/factory_<s:property value="#factory.factoryCode" />.png" style="border:none;">
				</a>
			</div>
			<div style = "text-align:center;">
				<a href="login-factoryLogin.action?loginFactoryCode=<s:property value="#factory.factoryCode" />" title="点击进入">
				<s:property value="#factory.factoryName" />
				</a>
			</div>
		</td>
		<td width="25%">&nbsp;</td>
		 </tr>
     </s:if>
</s:iterator>


</table>
</div>

</body>
</html>



