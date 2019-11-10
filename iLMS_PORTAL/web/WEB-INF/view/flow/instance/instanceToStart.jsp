<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.hotent.base.core.encrypt.Base64"%>
<%
String defId = request.getParameter("defId");
String proInstId = request.getParameter("proInstId");
String params = request.getParameter("params");
params =params==null||params.equals("")?"": Base64.getBase64(params);
params = URLEncoder.encode(params);
proInstId = proInstId==null?"":proInstId;
%>
<!DOCTYPE html>
<html ng-app="BpmService">
<head>
	<title>启动流程</title>
	<%@include file="/commons/include/bpmForm.jsp" %>
	<%@include file="/commons/include/zTree.jsp"%>
	<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
	
</head>
<style>
.ng-scope{margin-right:5px;}
</style>

<script type="text/javascript">
var isFirstNodeUserAssign=${prop.firstNodeUserAssign};
var isSkipFirstNode=${prop.skipFirstNode};
bpmModel.controller('custFormCtrl',['$scope','baseService',function($scope,baseService){

}]);
</script>
<body ng-controller="custFormCtrl">
	<div class="toolbar-panel">
		<div class="buttons" >
			<div ht-bpm-buttons></div>
		</div>
	</div>
	<div ht-bpm-manager="{defId:'<%=defId%>',proInstId:'<%=proInstId%>',params:'<%=params%>'}"></div>
</body>
</html>