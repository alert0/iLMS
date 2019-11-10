<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=EDGE;">
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" /> --%>
<%@include file="/commons/include/edit.jsp"%>
<f:link href="list.css"></f:link>
<f:link href="bootstrap.min.css"></f:link>
<f:link href="font-awesome.min.css"></f:link>
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/ui.jqgrid.css">
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/style.min.css">

<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/system/query/queryViewShow.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.peity.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.jqGrid.src.js"></script>


<script type="text/javascript" src="${ctx}/js/common/util/util.js"></script>
<%-- <script type="text/javascript">
var __ctx='${ctx}';
<%
Map map = RequestUtil.getParameterValueMap(request, true, false);
Object json = JSON.toJSON(map);
%>
var __param=<%=json%>;
</script> --%>

<script type="text/javascript">
	var alias = "${queryView.alias}";
	var sqlAlias = "${queryView.sqlAlias}";
	$(function() {
		$.jgrid.defaults.styleUI="Bootstrap";
		
		jqGridInit();
		//重新构建jqGrid大小
		//
		
		$("#btnSearch").click(function() {
			search();
		});
		jQuery("#gridList").jqGrid('setFrozenColumns');
		
		resizeJqGrid();
		$(window).bind("resize",function(){resizeJqGrid()});
	});
	
	
</script>
</head>
<body>
${queryView.template}
</body>
</html>
