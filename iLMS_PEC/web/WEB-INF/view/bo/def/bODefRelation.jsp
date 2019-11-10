<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/bo/def/bODefRelationController.js"></script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<div>
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-back" href="javascript:HT.window.closeEdit(true,'grid')">
					<span>返回</span>
				</a>
			</div>
		</div>
		<table class="table-form" cellspacing="0">
			<thead>
				<tr>
					<td colspan="2">关联的关系</td>
				</tr>
			</thead>
			<tr ng-if="type!='boEnt'">
				<th>绑定的实体</th>
				<td>
					<span style="margin-left: 10px" ng-repeat="boEnt in boList">
						<a class="btn btn-primary btn-sm" ng-href="${ctx}/bo/def/bOEntEdit?id={{boEnt.id}}" target="_blank">
							<span>{{boEnt.name}}</span>
						</a>
					</span>
				</td>
			</tr>
			<tr ng-if="type!='boDef'">
				<th>绑定的业务对象定义</th>
				<td>
					<span style="margin-left: 10px" ng-repeat="def in defList">
						<a class="btn btn-primary btn-sm" ng-href="${ctx}/bo/def/bODefEdit?id={{def.id}}" target="_blank">
							<span>{{def.name}}</span>
						</a>
					</span>
				</td>
			</tr>
			<tr ng-if="type!='formDef'">
				<th>绑定的表单元数据</th>
				<td>
					<span style="margin-left: 10px" ng-repeat="formDef in formDefList">
						<a class="btn btn-primary btn-sm" ng-href="${ctx}/form/formDef/formDefEdit?id={{formDef.id}}" target="_blank">
							<span>{{formDef.name}}</span>
						</a>
					</span>
				</td>
			</tr>
			<tr ng-if="type!='form'">
				<th>绑定的表单</th>
				<td>
					<span style="margin-left: 10px" ng-repeat="form in formList">
						<a class="btn btn-primary btn-sm" ng-href="${ctx}/form/form/formEdit?id={{form.id}}" target="_blank">
							<span>{{form.name}}</span>
						</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>绑定的流程</th>
				<td>
					<span style="margin-left: 10px" ng-repeat="flow in flowList">
						<a class="btn btn-primary btn-sm" ng-href="${ctx}/flow/def/defGet?defId={{flow.id}}" target="_blank">
							<span>{{flow.name}}</span>
						</a>
					</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>