<!-- 供应商信息查看-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	<%String supplierNoSer=request.getParameter("supplierNo").toString(); %>
<script type="text/javascript">
	var supplierNoServer = <%=supplierNoSer%>;
   </script>
<script type="text/javascript" src="./js/pub/pub_supplier_view.js"></script>
</head>
<body class="bodyBg"  onkeydown="forbidBackSpace();">
<div id='queryDiv' style="margin-top:2px;"></div>
<div id="resultDiv"></div>
</body>
</html>