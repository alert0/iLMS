<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%
String instanceId = request.getParameter("id");
String taskKey = RequestUtil.getString(request,"taskKey");
%>
<!DOCTYPE html>
<html ng-app="instanceApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/bpmForm.jsp" %>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/bpm/service/bpmInstanceService.js"></script>

</head>
<body class="easyui-layout" style="overflow: auto;">
	<div class="toolbar-panel noprint">
		<div class="buttons"><span ht-bpm-print alias="print" >打印</span></div>
	</div>
	<div ht-instance-manager="{instanceId:'<%=instanceId%>',taskKey:'<%=taskKey %>'}"></div>
</body>
</html>