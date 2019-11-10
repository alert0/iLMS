<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> 
<html ng-app="BpmService">
<head>
	<title>任务办理</title>
</head>
<%@include file="/commons/include/bpmForm.jsp" %>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript">
var isFirstNodeUserAssign = false;
bpmModel.controller('custFormCtrl',['$scope','baseService',function($scope,baseService){
	$scope.isPopWin = ${isPopWin};
}]);
</script>
<body ng-controller="custFormCtrl">
	<div class="srcoll-panel">
		<div class="gray-div">
			<div class="toolbar-panel noprint">
				<div class="buttons"style="margin-left:10px;">
					<div ht-bpm-buttons></div>
				</div>
			</div>
			
			<div ht-bpm-manager="{taskId:'${taskId}'}"></div>
		</div>
	</div>
</body>
</html>
