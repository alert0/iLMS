<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<f:link href="link-div-default.css"></f:link>
<f:link href="condition-edit.css"></f:link>
<script type="text/javascript" src="${ctx}/js/system/query/queryMetafieldBDialogController.js"></script>
<script type="text/javascript">
	function getResult() {
		var scope = AngularUtil.getScope();
		if (!scope.form.$valid)
			return;
		return scope.field;
	}
</script>
<Style>
.table-title{
	font-size:14px;
	margin:5px;
}
.table-plus{
	border:1px solid #5bb65b; 
	border-radius:4px; 
	padding:2px 5px; 
	font-size:14px;
}
legend{padding:10px 0;}
</Style>
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
				<td colspan="3">{{field.dataType}}</td>
			</tr>
		</table>
		<fieldset class="scheduler-border">
			<legend>
				<span>
					<span class="table-title">预警规则</span> 
					<a href="javaScript:void(0)" class="table-plus" ng-click="addSetting()">+</a>
				</span>
			</legend>
			<table class="table table-bordered table-hover" cellspacing="0">
				<tr ng-repeat="item in field.alarmSetting track by $index">
					<th>
						颜色
						<input type="color" ng-model="item.color" />
					</th>
					<td>
						<span ng-repeat="con in item.condition track by $index">
							<span ng-show="$index>0" style="color: orange;">and</span>
							<select class="inputText" ng-model="con.op" style="width: 100px">
								<option value=">">大于</option>
								<option value="<">小于</option>
								<option value="==">等于</option>
								<option value=">=">大于等于</option>
								<option value="<=">小于等于</option>
							</select>
							<input type="text" class="inputText" style="width: 90px" ng-model="con.val" ht-validate="{required:true}" />
						</span>
						<a href="javascript:javaScript:void(0)" ng-click="item.condition.push({op:'='})" class="btn btn-sm btn-default fa-add"></a>
					</td>
					<td>
						<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,field.alarmSetting)" class="btn btn-sm btn-default fa-delete"></a>
						<a href="javascript:javaScript:void(0)" ng-click="addSetting()" class="btn btn-sm btn-default fa-add" ng-show="$index==field.alarmSetting.length-1"></a>
					</td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="scheduler-border">
			<legend>
				<span>
					<span class="table-title">表单格式设置</span>
					<a href="javaScript:void(0)" style="text-decoration: none; color: red;" title="这个格式javascript代码。代码返回列格式化后的结果，这些代码作为function sex_Formater(cellvalue, options, rowObject){},函数体的一部分。[cellvalue]:为列代表的值，[rowObject]：表示为一行数据，可以通过 rowObject.sex类似的代码获取改行其他列的数据。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
				</span>
			</legend>
			<textarea rows="8" style="width: 100%" ng-model="field.formater"></textarea>
		</fieldset>
	</form>
</body>
</html>