<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<title>业务数据模板</title>
<%@include file="/commons/include/bpmForm.jsp" %>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/dataTemplate/editDataController.js"></script>
</head>
<body ng-controller="ctrl" >
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<button class="btn btn-primary btn-sm fa-save" ng-click="boSave()" ng-if="action!='get'" ng-disabled="btnDisabled.save" ><span>保存</span></button>
			<button class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></button>
		</div>
	</div>
	<div class="form-container" >
		<form name="form" >
			<div ht-bind-html="html"></div>
		</form>
	</div>
</body>
</html>
