<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<f:link href="formEdit.css"></f:link>
<f:link href="component.css"></f:link>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>

<script type="text/javascript" src="${ctx}/js/platform/flow/defNodeBosMapDialogController.js"></script>
<script type="text/javascript">
	var scope;

	$(function() {
		scope = AngularUtil.getScope();
		var node = window.passConf.node;
		var ids = window.passConf.ids;

		var ztreeCreator = new ZtreeCreator('sysTypeTree', "${ctx}/flow/node/varTree").setChildKey("children").setDataKey({
			idKey : "id",
			name : "desc",
			title : "desc"
		}).setCallback({
			onClick : nodeOnClick
		}).initZtree({
			defId : '${param.defId}',
			nodeId : '${param.nodeId}',
			parentFlowKey:'${param.parentFlowKey}',
			includeBpmConstants : true
		}, 1);
	});

	function nodeOnClick(event, treeId, treeNode, clickFlag) {
		var def;
		var pDef;//父定义
		if (treeNode.nodeType == "field") {
			def = treeNode.getParentNode();
		} else {
			def = treeNode;
		}
		if (def.nodeType == "sub") {
			pDef = def.getParentNode();
		}
		scope.$apply(function() {
			if(treeNode.nodeType == "sub"){
				$.topCall.error("目前只支持主表之间的映射，不支持子表间的映射");
				return ;
			}
			if (!treeNode.nodeType || treeNode.nodeType == "main") {
				scope.setEditingField(treeNode);
			}else{
				if (treeNode.nodeType == "field" || treeNode.nodeType == "var" ) {
					if(!pDef && treeNode.nodeType != "var"){
						pDef = def;
					}
					scope.nodeBeforeClick(treeId, treeNode, clickFlag);
				}
			}
			if(pDef){
				scope.data.defKey = pDef.boDefAlias;
			}
		});
	}

	function getResult() {
		scope.getBeforeShow();
		return scope.data;
	}
	

</script>
</head>
<body class="easyui-layout" ng-controller="ctrl">
	<div data-options="region:'west',border:true,title:'可选变量'" style="text-align: center; width: 250px;overflow:auto;">
		<div id="sysTypeTree" class="ztree" style="height: 100%;"></div>
	</div>


	<div data-options="region:'center',border:true,title:'BO字段映射'" style="text-align: center; width: 400px;">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>描述</th>
				<td>
					<input class="inputText" type="text" ng-model="data.description" style="width: 300px" />
				</td>
				<th>
					<a href="javascript:;" ng-click="editingField.option = {}" class="btn btn-sm btn-primary fa-rotate-right">
						<span>重置</span>
					</a>
				</th>
			</tr>
		</table>
		<div class="margin-10">
			<div class="panel panel-default" style="margin:5px;height:420px;overflow-y:scroll; ">
				<div class="panel-heading" style="font-size: 14px;">{{editingField.option.node.desc}}</div>
				<div ng-repeat="s in editingField.option.bind" style="margin-top:5px;">
					<div class="input-group">
						<span class="input-group-addon">{{s.comment}}</span>
						<span class="form-control inline-block w70" ng-click="focusBind($event)" style="padding: 0; width:360px!important; height: 40px; -webkit-appearance: textfield;">
							<span class="span-user owner-span hover-pointer" ng-show="s.json.desc">
								<span ng-bind="s.json.desc"></span>
								<a class="fa fa-remove floatTools" ng-click="s.json = null" ng-if="!s.hasRequired"></a> 
							</span>
							<input type="text" class="hide" ng-model="s.json"></input>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>