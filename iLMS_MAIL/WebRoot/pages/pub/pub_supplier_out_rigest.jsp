<!-- 供应商注册 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/jsp/common.jsp"%>
<% 
String chineseName = user.getSupplierName(); 
String supplierNoServer = user.getSupplierNo();
String loginIdServer = user.getUserName();
String parentNoServer = user.getParentNo();
%>
<script>
	var supplierNoServer = '<%=supplierNoServer%>';
	var loginIdServer = '<%=loginIdServer%>';
	var parentNoServer = '<%=parentNoServer%>';
	var chineseNameServer = '<%=chineseName%>';
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商注册(首次登陆)</title>
<script type="text/javascript" src="./js/pub/pub_supplier_out_rigest.js"></script>
<style type="text/css">
.carImg{float:right;margin:5px;}
.loginImg{float:left;margin:0px;padding:0px;}
</style>
</head>
<body class="bodyBg" style="background-color: white;">
<div  style="height:60px; border-bottom:1px solid #bcbcbc;">
<div style="height:60px;">
    	<img class="carImg" src="images/car.jpg"/  >
     
       <img class="loginImg" src="images/logo.jpg"/ >
    </div>
    
    </div>
	<p class="titBg"><span class="fontTitOne">供应商注册信息</span></p>
	<div id='query_div'></div>
	<s:form name="redirectForm" action="index-init.action">
	</s:form>

</body>
</html>