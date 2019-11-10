<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/flow/node/condition/varDialogController.js"></script>
<script type="text/javascript">
	var scope;
	var defId = "${param.defId}";
	var flowKey = "${param.flowKey}";
	var parentFlowKey = "${param.parentFlowKey}";
	
	$(function() {
		scope = AngularUtil.getScope();
		var ztreeCreator = new ZtreeCreator('tree', __ctx + "/flow/node/varTree").setChildKey("children").setDataKey({
			idKey : "id",
			name : "desc",
			title : "desc"
		}).setCallback({
			onClick : nodeOnClick
		}).initZtree({
			defId : defId,
			flowKey:flowKey,
			parentFlowKey:parentFlowKey,
			removeSub:true
		});
	});

	function nodeOnClick(event, treeId, treeNode, clickFlag) {
		var def;
		if (treeNode.nodeType == "field") {
			def = treeNode.getParentNode();
			scope.$apply(function() {
				scope.data.executorVar.name = def.boDefAlias+"."+treeNode.name+"";
				scope.data.dataType = treeNode.columnType;
			});
		}else if (treeNode.nodeType == "var"){
			scope.$apply(function() {
				scope.data.executorVar.name = treeNode.name+"";
			});
		}
		
	}

	function getResult() {
		//组装对象
		scope.data.conDesc=$("#conDesc").text();
		scope.data.executorVar.source = "BO";//TODO　增加了其他变量就要修改
		if(scope.data.executorVar.executorType!="user"){
			delete scope.data.executorVar.userValType;
		}
		return scope.data;
	}
</script>
<style type="text/css">
.everyoneRadio, .noneRadio {
	float: left;
	margin-right: 5px;
	text-align: center;
}

.everyoneRadio {
	margin-left: 27%;
}

.noneRadio>*, .everyoneRadio>* {
	cursor: pointer;
}

input[type='radio'] {
	width: 0px;
	height: 0px;
	display: none;
}

input[type='radio']:checked ~label {
	color: #fff;
	background-color: #5cb85c;
	border-color: #4cae4c;
}

input[type='radio']:not (:checked )~label {
	background-color: #D3D3D3;
	color: white;
}

.label-sm {
	margin-left: 3px;
	padding: 3px;
	font-size: 15px;
}
.table-form td{ text-align:left;}
</style>
</head>
<body class="easyui-layout" ng-controller="ctrl" ng-init="init()">
	<div data-options="region:'west',border:true,title:'变量'" style="text-align: center; width: 250px;">
		<div id="tree" class="ztree" style="height: 100%;overflow-y:auto;"></div>
	</div>

	<div data-options="region:'center',border:true,title:'设置'" style="text-align: center; width: 400px">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>值类型</th>
				<td>
					<select ng-model="data.executorVar.executorType" class="inputText" ng-change="data.executorVar.value='';data.executorVar.valueText=''">
						<option value="user">用户</option>
						<option value="fixed">固定值</option>
					</select>
					<select ng-model="data.executorVar.userValType" class="inputText" ng-show="data.executorVar.executorType =='user'">
						<option value="userId">userId</option>
						<option value="account">account</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>匹配值</th>
				<td>
					<input ng-model="data.executorVar.value" ng-disabled="data.executorVar.executorType!='fixed'" class="inputText">
					<a class="btn btn-default" ng-hide="data.executorVar.executorType =='fixed'" ng-click="dialog()">选择</a>
				</td>
			</tr>
			<tr>
				<th>变量目标</th>
				<td>
					<input ng-model="data.executorVar.name" disabled="disabled" class="inputText">
				</td>
			</tr>
			<tr>
				<th>表达式</th>
				<td>
					<div class="pull-left">
						<input type="radio" ng-model="data.expression" value="equals" id="equals">
						<label for="equals" class="btn label-sm">相等</label>
					</div>
					<div class="pull-left">
						<input type="radio" ng-model="data.expression" value="notEquals" id="notEquals">
						<label for="notEquals" class="btn label-sm">不等</label>
					</div>
					<div class="pull-left">
						<input type="radio" ng-model="data.expression" value="contains" id="contains">
						<label for="contains" class="btn label-sm">包含</label>
					</div>
					<div class="pull-left">
						<input type="radio" ng-model="data.expression" value="notContains" id="notContains">
						<label for="notContains" class="btn label-sm">不包含</label>
					</div>
					<div ng-show="data.dataType =='number'|| data.dataType =='date'">
						<div class="pull-left">
							<input type="radio" ng-model="data.expression" value=">" id="bigger">
							<label for="bigger" class="btn label-sm">大于</label>
						</div>
						<div class="pull-left">
							<input type="radio" ng-model="data.expression" value="<" id="lesser">
							<label for="lesser" class="btn label-sm">小于</label>
						</div>
						<div class="pull-left">
							<input type="radio" ng-model="data.expression" value=">=" id="biggerEq">
							<label for="biggerEq" class="btn label-sm">大于等于</label>
						</div>
						<div class="pull-left">
							<input type="radio" ng-model="data.expression" value="<=" id="lesserEq">
							<label for="lesserEq" class="btn label-sm">小于等于</label>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<th>条件预览</th>
				<td>
					<span id="conDesc">[{{data.executorVar.name}}] {{data.expression}} [{{data.executorVar.valueText?data.executorVar.valueText:data.executorVar.value}} ]</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>