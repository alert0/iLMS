<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/defNodeBosDialogController.js"></script>
<script type="text/javascript">
	var scope;
	var varTree;
	$(function() {
		scope = AngularUtil.getScope();

		var node = window.passConf.node;
		var ids = window.passConf.ids;

		var ztreeCreator = new ZtreeCreator('sysTypeTree', __ctx + "/bo/def/bODef/getBOTree").setChildKey("children").setDataKey({
			idKey : "id",
			name : "desc",
			title : "desc"
		}).setCallback({
			onClick : nodeOnClick
		}).initZtree({
			ids : ids
		});
		
		varTree = new ZtreeCreator('varTree', "${ctx}/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : setVariable
		}).makeCombTree("tempTree").initZtree({
			defId : '${param.defId}',
			nodeId : '${param.nodeId}',
			parentFlowKey:'${param.parentFlowKey}',
			includeBpmConstants : true
		}, 1);
		$(".varTree").bind("click", varTree.showMenu);

		
	});
	
	function nodeOnClick(event, treeId, treeNode, clickFlag) {
		var def;
		var pDef;//父定义
		if (!treeNode.nodeType || treeNode.nodeType == "main") {
			return;
		}

		if (treeNode.nodeType == "field") {
			def = treeNode.getParentNode();
		} else {
			def = treeNode;
		}
		if (def.nodeType == "sub") {
			pDef = def.getParentNode();
		}
		scope.$apply(function() {
			if (!scope.data.description) {
				scope.data.description = treeNode.desc;
			}

			var txt = "";
			if (treeNode.nodeType == "field") {//字段
				if (!pDef) {//主表字段
					pDef = def;
					txt = pDef.boDefAlias + '.set("' + treeNode.name + '","");';
				} else {//子表字段
					txt = pDef.boDefAlias + '.setInitData("' + def.name + '","' + treeNode.name + '","");';
				}
			} else {//子表
				txt = 'List list = ' + pDef.boDefAlias + '.getSubByKey("' + def.name + '");';
			}

			var activeTab = $('#tabs').tabs('getSelected');//当前活动tab
			scope.CodeMirrorBroadcast = "afterScript";
			if (activeTab.panel("options").id == "beforeShow") {
				scope.CodeMirrorBroadcast = "preScript";
			} 
			scope.$broadcast(scope.CodeMirrorBroadcast, function(CodeMirror) {
				CodeMirror.replaceSelection(txt);
			});
			scope.data.defKey = pDef.boDefAlias;
		});
	}
	
	function getResult() {
		return scope.data;
	}
	
	function setVariable(event, treeId, node) {

			var keyStr = node.name;
			var parentNode = node.getParentNode();
			var parentNode2 = node.getParentNode();
			var boDefAlias = parentNode2.boDefAlias;
			while(parentNode2 && !parentNode2.boDefAlias){
				parentNode2 = parentNode2.getParentNode();
				if(!parentNode2){
					break;
				}
				boDefAlias = parentNode2.boDefAlias;
			}
			
			// 子表情况做提示
			 if (node.nodeType == 'sub'){
				keyStr = boDefAlias+".getSubByKey('"+node.name+"') /* 获取子表,return List<BoData> */";
			 }// 主表bo
			 else if(parentNode.nodeType == 'main'){
				keyStr = boDefAlias+'.getValByKey("'+node.name+'") /*数据类型：'+node.dataType+'*/';
			}else if(parentNode.nodeType == 'sub'){
				var mainTableName = boDefAlias;
				keyStr = mainTableName+'.getSubByKey("'+parentNode.name+'") /*获取子表数据 ，返回数据：return List<BoData> 。子表字段：'+node.name+', 请根据实际情况处理子表数据的获取*/';
			}else if(node.nodeType == 'var'){
				keyStr =node.name;
			}else return ;
		
		
		
		varTree.hideMenu();
		var activeTab = $('#tabs').tabs('getSelected');//当前活动tab
		scope.CodeMirrorBroadcast = "afterScript";
		if (activeTab.panel("options").id == "beforeShow") {
			scope.CodeMirrorBroadcast = "preScript";
		} 
		scope.$broadcast(scope.CodeMirrorBroadcast, function(CodeMirror) {
			CodeMirror.replaceSelection(keyStr);
		});
	}
	function selectScript(){
		new ScriptSelector(function(script){
			CodeMirror.editor.insertCode(script);
		 }).show();
	}
	
</script>
</head>
<body class="easyui-layout" ng-controller="ctrl">
	<div id="tempTree"></div>
	<div data-options="region:'west',border:true,title:'字段'" style="text-align: center; width: 250px;overflow:auto;">
		<div id="sysTypeTree" class="ztree" style="height: 100%;"></div>
	</div>


	<div data-options="region:'center',border:true,title:'BO字段设置'" style="text-align: center; width: 400px">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>描述</th>
				<td>
					<input class="inputText" type="text" ng-model="data.description" style="width: 300px" />
				</td>
			</tr>
		</table>
		<div style="text-align: left; margin-left: 5px;">
			<a class="btn btn-primary btn-xs" title="常用脚本" id="asdf" ng-click="selectScript()">常用脚本</a>
			<a class="btn btn-primary btn-xs" ng-click="selectIdentity()">流水号</a>
			<a class="varTree btn btn-primary btn-xs">流程变量</a>
		</div>
		<div id="tabs" class="easyui-tabs" style="text-align: center; height: 430px;">

			<div title="显示前" id="beforeShow">
				<textarea ui-codemirror  id="beforeShowCode" style="height: 400px; width: 500px;" ng-model="data.beforeShow" broadcast="preScript"></textarea>
			</div>
			<div title="保存时" id="whenSave">
				<textarea ui-codemirror id="whenSaveCode" style="height: 390px; width: 500px;" ng-model="data.whenSave" broadcast="afterScript"></textarea>
			</div>
		</div>
	</div>
</body>
</html>