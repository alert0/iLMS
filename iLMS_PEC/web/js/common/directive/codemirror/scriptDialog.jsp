<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<script type="text/javascript">
	var parentFlowKey = "${param.parentFlowKey}";
</script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/node/condition/scriptDialogController.js"></script>
<script type="text/javascript">
	var defId = "${param.defId}";
	var nodeId = "${param.nodeId}";
	var flowKey = "${param.flowKey}";
	
	function getResult() {
		return AngularUtil.getScope().code;
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<div style="margin-bottom: 10px;">
		<div style="margin-left: 20px;"><a class="btn btn-primary btn-xs" ng-click="selectScript()">常用脚本</a></div>
		<div style="margin-left: 20px;" id="comments">请选择set类型数据</div>
	</div>
	<textarea ui-codemirror ng-model="code"></textarea>
</body>
</html>


