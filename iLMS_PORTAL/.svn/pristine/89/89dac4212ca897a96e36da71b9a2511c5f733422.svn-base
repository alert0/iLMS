<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/bpmForm.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript" src="${ctx}/js/platform/form/controller/formBusController.js"></script>
		<script type="text/javascript">
			var formKey ='${formKey}';
			var id = '${id}';
			var readonly = '${readonly}';
			var parentKey ='${parentKey}';
			var parentVal = '${parentVal}';
		</script>
	</head>

	<body ng-controller="formBusCtrl">
		<div class="toolbar-panel">
			<div class="buttons">
				<c:if test="${!readonly}"><a class="btn btn-primary fa-save" ng-click="save()"><span>保存</span></a></c:if>
				<a class="btn btn-primary fa-back" onClick="window.close();"><span>返回</span></a>
			</div>
		</div>
		<div class="srcoll-panel">
			<div class="gray-div">
				<form name='custForm' >
					<div id="formHtml" ht-bind-html="formHtml"></div>
				</form>
			</div>
		</div>
	</body>	
	
</html>