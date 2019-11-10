<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx}/js/platform/flow/node/condition/cusersSelectorController.js"></script>
<script type="text/javascript">
	var calc = window.passConf.calc;

	var defId = '${defId}';
	var nodeId = '${nodeId}';
	var varTree;
	$(function() {
		varTree = new ZtreeCreator('varTree', "${ctx}/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : setVariable
		}).makeCombTree("tempTree").initZtree({
			defId : '${defId}',
			nodeId : '${nodeId}',
			includeBpmConstants : true
		}, 1);

		$(".varTree").bind("click", varTree.showMenu);

	});

	function setVariable(event, treeId, node) {
		var keyStr = node.name;
		var source="BO";
		var parentNode = node.getParentNode();
		
		// 子表情况做提示
		 if (node.nodeType == 'sub'||(node.path&&node.path.indexOf('.sub_')!=-1)){
			keyStr = "";
			$.topCall.alert('提示信息','不支持子表');
		 }// 主表bo
		 else if(parentNode.nodeType == 'main'){
			keyStr = node.path+'.'+node.name;
		}else if(node.nodeType == 'var'){
			keyStr =node.name;
			source="flowVar";
		}else return ;
		varTree.hideMenu();
		var scope = AngularUtil.getScope();
		scope.$apply(function() {
		  var json= {source:source,name:keyStr,executorType:"user",userValType:"account"}
		   scope.data.vars=JSON.stringify(json);
		   scope.data["var"]=json;
		});
	}
	function showFlowMenu(obj){
		targetObj = $(obj);
		varTree.showMenu(targetObj);
	}

	function getResult() {
		var scope = AngularUtil.getScope();

		//指定人员
		if (scope.data.source == 'spec') {
			scope.data.userName = scope.user.name;
			scope.data.account = scope.user.account;
		}

		//处理描叙
		if (scope.data.source == 'currentUser') {
			scope.data.description="当前登录用户";
		} else if (scope.data.source == 'start') {
			scope.data.description="发起人";
		}else if (scope.data.source == 'prev') {
			scope.data.description="上一步执行人";
		}else if (scope.data.source == 'var') {
			scope.data.description=scope.data['var']?"[变量]"+scope.data['var'].name:"[变量]";
		}else if (scope.data.source == 'spec') {
			if(scope.user.name){
				scope.data.description="[指定用户]"+scope.user.name;
			}else{
				scope.data.description="";
			}
		}
		return scope.data;
	}
</script>
</head>
<body ng-controller="ctrl">
<div id="tempTree"></div>
	<table class="table-form" cellspacing="0">
		<tr>
			<th>
				<span>用户：</span>
			</th>
			<td>
			    <input type="radio" ng-model="data.source" value="currentUser" id="source_currentUser">
				<label class="normal_label" for="source_currentUser">当前登录用户</label> 

				<input type="radio" ng-model="data.source" value="start" id="source_start">
				<label class="normal_label" for="source_start">发起人</label>

				<input type="radio" ng-model="data.source" value="prev" id="source_prev">
				<label class="normal_label" for="source_prev">上一步执行人</label>
				<br>

				<input type="radio" ng-model="data.source" value="var" id="source_var">
				<label class="normal_label" for="source_var">变量</label>

				<input type="radio" ng-model="data.source" value="spec" id="source_spec" checked="checked">
				<label class="normal_label" for="source_spec">指定用户</label>
			</td>
		</tr>
		<tr ng-show="data.source=='var'">
			<th>
				<span>变量选择：</span>
			</th>
			<td>
				<a class="btn btn-primary btn-xs" title="可选变量" id="asdf" onclick="showFlowMenu(this)">可选变量</a>
               </br>
               <textarea id="vars" readonly="readonly"  style="width:300px;height:30px;margin-top: 5px;" ng-model="data.var.name" rows="" cols=""></textarea>
			</td>
		</tr>
		<tr ng-show="data.source=='spec'">
			<th>
				<span>指定用户：</span>
			</th>
			<td>
				<textarea ng-model="user.name" readonly="readonly" style="height: 37px; width: 200px"></textarea>
				<a href="javascript:javaScript:void(0)" class="btn btn-xs btn-primary" ng-click="userDialog()">选择用户</a>
			</td>
		</tr>
	</table>
</body>
</html>


