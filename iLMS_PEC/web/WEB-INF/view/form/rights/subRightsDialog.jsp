<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/form/rights/subRightsDialogController.js"></script>
<script type="text/javascript">
	var scope;
	$(function() {
		scope = AngularUtil.getScope();

		varTree = new ZtreeCreator('tree', __ctx + "/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : nodeOnClick
		}).initZtree({
			defId : __param.defId,
			nodeId : __param.nodeId,
			includeBpmConstants : false,
			removeSub : false,
			removeMain : true
		}, 1, function(treeObj) {
			var node = {
				desc : "bpm_bus_link(中间业务表)",
				icon : "fa fa-bold dark",
				nodeType : "root",
				children : [ {
					icon : "ico_string dark",
					desc : "创建人ID",
					nodeType : "bfield",
					fieldName : "start_id_"
				}, {
					icon : "ico_string dark",
					desc : "组织ID",
					nodeType : "bfield",
					fieldName : "start_group_id_",
				} ]
			};
			treeObj.addNodes(null, node);
		});
	});

	function nodeOnClick(event, treeId, treeNode, clickFlag) {
		//先处理tableName的赋值
		var preTableName=scope.editingRight.tableName;
		if (treeNode.nodeType == "sub") {
			scope.$apply(function() {
				scope.editingChange(treeNode.name);
			});
		}
		if (treeNode.nodeType == "field") {
			scope.$apply(function() {
				scope.editingChange(treeNode.getParentNode().name);
			});
		}
		if(preTableName!=scope.editingRight.tableName){
			return;
		}
		
		//处理脚本添加
		if (scope.editingRight.rightType != "script" || (treeNode.nodeType != "field" && treeNode.nodeType != "bfield")) {
			return;
		}
		var prefix;
		if (treeNode.nodeType == "bfield") {
			prefix = "b";
		} else {
			prefix = "a";
		}
		var str = prefix + "." + treeNode.fieldName;
		scope.insetCode(str);
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()" class="easyui-layout">
	<div data-options="region:'west',border:true,title:'子表'" style="text-align: center; width: 250px;">
		<div id="tree" class="ztree" style="height: 100%;"></div>
	</div>
	<div data-options="region:'center',border:true,title:'子表权限设置'" style="width: 400px">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-save" ng-model="data" ht-save="saveSub?defId=${param.defId}&nodeId=${param.nodeId}&parentDefKey=${param.parentDefKey}">
					<span>保存</span>
				</a>
				<a class="btn btn-primary fa-delete" ng-click="clean()">
					<span>删除权限</span>
				</a>
			</div>
		</div>
		<form name="form" ng-model="data" ht-load="initSub?defId=${param.defId}&nodeId=${param.nodeId}&parentDefKey=${param.parentDefKey}">
			<table class="table-form" cellspacing="0">
				<tr>
					<th>操作子表</th>
					<td>{{editingRight.tableName?editingRight.tableName:"请先选择需要编辑的子表"}}</td>
				</tr>
				<tr>
					<th>条件类型</th>
					<td>
						<label>当前人员
						<input type="radio" value="curUser" ng-model="editingRight.rightType" />
						</label>
						<label>
						当前组织
						<input type="radio" value="curOrg" ng-model="editingRight.rightType" />
						</label>
						<label>
						脚本<span class="fa bigger-120 fa-info-circle ng-isolate-scope" ht-tip title='String sql =" a.user_id_ = \"" + scriptImpl.getCurrentUserId() + "\"" + " and a.user_name_=\"jason\""; return sql; ' ></span>
						<input type="radio" value="script" ng-model="editingRight.rightType" />
						
						</label>
					</td>
				</tr>
				<tr ng-if="editingRight.rightType=='script'" >
					<td colspan="2">
						<div>
							<a class="btn btn-primary btn-xs" ng-click="selectScript()">常用脚本</a>
						</div>
						<textarea ui-codemirror ng-model="editingRight.script"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>