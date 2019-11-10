<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/query/queryMetafieldKDialogController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/form/service/formDefService.js"></script>
<script type="text/javascript">
	function getResult() {
		var scope = AngularUtil.getScope();
		if (!scope.form.$valid)
			return;
		return scope.field;
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<form name="form">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>字段描叙</th>
				<td>{{field.fieldDesc}}</td>
				<th>字段名称</th>
				<td>{{field.fieldName}}</td>
			</tr>
			<tr>
				<th>字段类型</th>
				<td>{{field.dataType}}</td>
				<th>控件类型</th>
				<td>
					<select ng-model="field.controlType" ng-options="m.id as m.name for m in controlTypes" class="inputText">
					</select>
				</td>
			</tr>
			<tr ng-if="field.controlType=='select'">
				<th>下拉框选项</th>
				<td colspan="3">
					<table class="table table-bordered table-hover" cellspacing="0">
						<tr>
							<td colspan="2">
								<a class="btn btn-primary fa-add" ng-click="field.controlContent.push({})">
									<span>添加</span>
								</a>
							</td>
						</tr>
						<tr ng-repeat="item in field.controlContent track by $index">
							<td>
								值:
								<input type="text" class="inputText" ng-model="item.optionKey" ht-validate="{required:true}" />
								选项:
								<input type="text" class="inputText" ng-model="item.optionValue" ht-validate="{required:true}" />
							</td>
							<td>
								<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,field.controlContent)" class="btn btn-sm btn-default fa-chevron-up"></a>
								<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,field.controlContent)" class="btn btn-sm btn-default fa-chevron-down"></a>
								<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,field.controlContent)" class="btn btn-sm btn-default fa-delete"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr ng-if="field.controlType=='customdialog'">
				<th>自定义对话框</th>
				<td>
					<select ht-validate="{required:true}" ng-model="field.controlContent.alias" ng-options="m.alias as m.name for m in customDialogs" class="inputText">
					</select>
				</td>
				<th>返回字段</th>
				<td>
					<select ht-validate="{required:true}" ng-model="field.controlContent.resultField" ng-options="m.comment as m.comment for m in customDialog.resultfield" class="inputText">
					</select>
				</td>
			</tr>
			<tr ng-if="field.controlType=='date'">
				<th>日期格式</th>
				<td>
					<select ht-validate="{required:true}" ng-model="field.dateFormat" class="inputText">
						<option value="yyyy-MM-dd HH:mm:ss" ng-selected=" 'yyyy-MM-dd HH:mm:ss' == field.dateFormat " >yyyy-MM-dd HH:mm:ss</option>
						<option value="yyyy-MM-dd HH:mm" >yyyy-MM-dd HH:mm</option>
						<option value="yyyy-MM-dd HH" >yyyy-MM-dd HH</option>
						<option value="yyyy-MM-dd" >yyyy-MM-dd</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>