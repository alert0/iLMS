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
	<div>
		<a class="btn btn-primary btn-xs" ng-click="selectScript()">常用脚本</a>
		<a class="btn btn-primary btn-xs" ng-click="selectConditionScript()">条件脚本</a>
		<a class="btn btn-primary btn-xs" ng-click="selectIdentity()">流水号</a>
		<a class="varTree btn btn-primary btn-xs" id="tempTree">可选变量</a>
		<a class="bigger-180 fa fa-question" ht-tip="{style:{width:'200px'}}" title="1、返回值为JSONObject,list类型为jsonArray  data<br>
										2、可以从invokeResult 获取返回信息<br>
										3、可以通过pluginSession 获取节点信息或者设置流程变量<br>
										eg:pluginSession.getBpmDelegateExecution().setVariable(variableName,value);
									"></a>
	</div>
	<textarea ui-codemirror ng-model="code"></textarea>
</body>
</html>


