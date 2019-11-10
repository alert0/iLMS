<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<f:link href="link-div-default.css"></f:link>
<f:link href="condition-edit.css"></f:link>
<script type="text/javascript" src="${ctx}/js/system/query/queryMetafieldPDialogController.js"></script>
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
				<td>
					<input type="text" class="inputText" ng-model="field.fieldDesc" ht-validate="{required:true}" />
				</td>
				<th>字段名称</th>
				<td>
					<input type="text" class="inputText" ng-model="field.fieldName" ht-validate="{required:true}" ht-pinyin="field.fieldDesc" />
				</td>
			</tr>
			<tr>
				<th>关联字段</th>
				<td>{{field.virtualFrom}}</td>
				<th>数据来源</th>
				<td>
					<select class="inputText" ng-model="field.resultFromType">
						<option value="sql">SQL</option>
						<option value="script">脚本</option>
					</select>
					<a href="javaScript:void(0)" style="text-decoration: none; color: red;" title="#CON# 代表来源字段的值" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
				</td>
			</tr>
			<tr>
				<th>配置</th>
				<td colspan="3">
					<textarea rows="8" style="width: 100%" ng-model="field.resultFrom" ht-validate="{required:true}"></textarea>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>