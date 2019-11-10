<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		var scope = AngularUtil.getScope();
		scope.data.script = AngularUtil.getScope().code;
		scope.data.description = "[人员脚本]"+AngularUtil.getScope().description;
		return scope.data;
	}
	function toggoleDetail() {
		$("#code").toggle();
	}
	$(function() {
		toggoleDetail();
	});
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">

	<div style="padding-left: 10px; padding-top: 10px;">
		<a class="btn btn-primary btn-xs" ng-click="selectHrScript()">选择人员脚本</a> <a title="查看详细的脚本代码" href="javascript:void(0)" onclick="toggoleDetail()"><i class="fa fa-eye"></i></a>
	</div>
	<div style="padding: 10px; padding-top: 3px;">

		<textarea type="text" readonly="readonly" class="inputText" style="width: 95%; height: 100px" ng-model="description"></textarea>
	</div>
	<div id="code" style="padding: 10px; padding-top: 3px;">
		<textarea ng-model="code" style="width: 95%; height: 100px"></textarea>
	</div>
</body>
</html>


