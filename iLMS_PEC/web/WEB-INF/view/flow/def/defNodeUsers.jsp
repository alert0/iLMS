<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="nodeApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/flow/defNodeController.js"></script>
<style>
.form-table tr td div {
	padding-top: 11px !important;
	padding-left: 5px !important;
}
</style>
</head>
<body class="easyui-layout" ng-controller="defNodeController" ng-init="init({defId:'${bpmDefinition.defId}',parentFlowKey:'${parentFlowKey}'})">
	<div data-options="region:'center',border:false">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons" style="margin-left: 6px;">
				<a ng-click="saveAllNodeJson()" class="btn btn-sm btn-primary fa-save">全部保存</a>
			</div>
		</div>
		<table class="form-table" style="width: 100%">
			<c:forEach var="node" items="${nodeList}">
				<c:set value="<%=NodeType.USERTASK%>" var="userTask"></c:set>
				<c:set value="<%=NodeType.SIGNTASK%>" var="signTask"></c:set>
				<c:if test="${node.type == userTask || node.type == signTask}">
					<tr>
						<td style="width: 100%">
							<div style="float: left;">${node.name}(${node.nodeId})
								<c:if test="${!empty node.parentBpmNodeDef}">【${node.parentBpmNodeDef.name }】</c:if>
							</div>
							<div style="float: right;">
								<a ng-click="addNodeUserCondition('${node.type}','${node.nodeId}')" class="btn  btn-sm btn-info fa-add">添加</a>
								<a ng-click="saveNodeJson('${node.nodeId}')" class="btn btn-sm btn-info fa-save">保存</a>
							</div>
						</td>
					</tr>
					<tr style="vertical-align: baseline; min-height: 200px">
						<td>
							<div class="form-table" style="min-height: 100px">
								<table style="text-align: center;" class="table-list">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th>条件</th>
											<th width="130px">操作</th>
										</tr>
									</thead>
									<tr ng-repeat="nodeCondition in nodeConditions.${node.nodeId}">
										<td>{{$index+1}}</td>
										<td>{{nodeCondition.description}}</td>
										<td>
											<a ng-click="addNodeUserCondition('${node.type}','${node.nodeId}',$index)" class="btn btn-danger btn-sm fa-edit">修改</a>
											<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,nodeConditions.${node.nodeId})" class="btn btn-sm btn-default fa-chevron-up"></a>
											<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,nodeConditions.${node.nodeId})" class="btn btn-sm btn-default fa-chevron-down"></a>
											<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,nodeConditions.${node.nodeId})" class="btn btn-sm btn-default fa-delete"></a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</body>
</html>