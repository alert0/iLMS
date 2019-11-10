<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/rights/rightsDialogController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
<script type="text/javascript">
	var param = {};
	param.flowKey = "${param.flowKey}";
	param.formKey = "${param.formKey}";
	param.nodeId = "${param.nodeId}";
	param.type = "${param.instId}"=="true"?"2":"1";
	param.parentflowKey = "${param.parentFlowKey}";
	param.instId = "${param.instId}";

	$(function() {
		var scope = AngularUtil.getScope();
		scope.init(param);
	})
</script>
</head>
<body ng-controller="ctrl">
	<div>
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-save" href="javaScript:void(0)" ng-click="save()">
					<span>保存</span>
				</a>
				<a class="btn btn-primary fa-back" href="javaScript:window.location.reload()">
					<span>刷新</span>
				</a>
				<a class="btn btn-primary fa-refresh" href="javaScript:void(0)" ng-click="getDefaultByFormKey()">
					<span>重置</span>
				</a>
				<a class="btn btn-primary fa-refresh" ng-click="addNewField()">
					<span>添加新增字段的默认权限</span>
				</a>
			</div>
		</div>
		<table class="table-list">
			<tr>
				<th width="15%">字段</th>
				<th width="20%">只读权限</th>
				<th width="20%" ng-if="param.instId!='true'">编辑权限</th>
				<th width="20%" ng-if="param.instId!='true'">必填权限</th>
				<th width="20%" ng-if="param.instId!='true'">是否隐藏</th>
			</tr>
			<tr>
				<td>
			<!-- 		<a class="btn btn-sm btn-primary btn-mini" ng-click="hidden()">
						<span>隐藏</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="readOnly()" ng-if="param.instId!='true'">
						<span>只读</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="edit()" ng-if="param.instId!='true'">
						<span>编辑</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="mustFill()" ng-if="param.instId!='true'">
						<span>必填</span>
					</a> -->
				</td>
				<td>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('read','everyone')">
						<span>所有人</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('read','none')">
						<span>无权限</span>
					</a>
				</td>
				<td ng-if="param.instId!='true'">
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('write','everyone')">
						<span>所有人</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('write','none')">
						<span>无权限</span>
					</a>
				</td>
				<td ng-if="param.instId!='true'">
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('required','everyone')">
						<span>所有人</span>
					</a>
					<a class="btn btn-sm btn-primary btn-mini" ng-click="clickAll('required','none')">
						<span>无权限</span>
					</a>
				</td>
			  	<td style="text-align: center" ng-if="param.instId!='true'">
					<a ng-show="checkIsAllhide(subTableList)" style="width: 50px" class="btn btn-sm btn-primary btn-mini" ng-click="hideOrShowAll(subTableList)">
						<span>隐藏</span>
					</a>
					<a  ng-show="!checkIsAllhide(subTableList)" style="width: 50px" class="btn btn-sm btn-primary btn-mini" ng-click="hideOrShowAll(subTableList)">
						<span>显示</span>
					</a>
				</td>
			</tr>
			<tbody ng-repeat="table in subTableList track by $index">
				<tr>
					<th colspan="4">
						<span style="float: left;">
							<span ng-if="table.main">主表</span><span ng-if="!table.main">子表</span>——{{table.description}}
						</span>
						<span ng-if="param.instId!='true'&&!table.main">
							<input type="checkbox" ng-model="table.rights.add" />
							添加
							<input type="checkbox" ng-model="table.rights.del" />
							删除
							<input type="checkbox" ng-model="table.rights.hidden" />
							隐藏
							<input type="checkbox" ng-model="table.rights.required" />
							必填
						</span>
					</th>
				</tr>
				<tr ng-repeat="field in table.fields | orderBy: 'sn' track by $index ">
					<td>{{field.description}}</td>
					<td>
						{{rightToDesc(field['read'])}}
						<a style="float: right;" ng-click="fieldDialog(field,'read')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
					</td>
					<td ng-if="param.instId!='true'">
						{{rightToDesc(field['write'])}}
						<a style="float: right;" ng-click="fieldDialog(field,'write')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
					</td>
					<td ng-if="param.instId!='true'">
						{{rightToDesc(field['required'])}}
						<a style="float: right;" ng-click="fieldDialog(field,'required')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
					</td>
				    <td ng-if="param.instId!='true'">
						{{checkIshide(field)}}
					<a style="float: right" class="btn btn-sm btn-primary btn-mini" ng-click="hideColumn(field)">
						
						<span ng-if="checkIshide(field) =='是'">显示</span>
						<span ng-if="checkIshide(field) =='否'">隐藏</span>
					 </a>
						<!-- <a style="float: right;" ng-click="fieldDialog(field,'hide')" class="btn btn-sm btn-default btn-mini fa-edit"></a> -->
					</td>
				</tr>
			</tbody>
			<tr>
				<th colspan="5">
					<span style="float: left;">意见</span>
				</th>
			</tr>
			<tr ng-repeat="field in opinion track by $index">
				<td>{{field.description}}</td>
				<td>
					{{rightToDesc(field['read'])}}
					<a style="float: right;" ng-click="fieldDialog(field,'read')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
				</td>
				<td ng-if="param.instId!='true'">
					{{rightToDesc(field['write'])}}
					<a style="float: right;" ng-click="fieldDialog(field,'write')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
				</td>
				<td ng-if="param.instId!='true'">
					{{rightToDesc(field['required'])}}
					<a style="float: right;" ng-click="fieldDialog(field,'required')" class="btn btn-sm btn-default btn-mini fa-edit"></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>