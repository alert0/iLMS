<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/rights/fieldDialogController.js"></script>
<script type="text/javascript">
	var scope;
	$(function() {
		scope = AngularUtil.getScope();
	});

	//要拼装返回对象
	function getResult() {
		var data = scope.data;
		var result = [];
		//这个页面的权限分为两部分
		//一部分是要特定处理的，要写死的，例如:所有人，没有人，和脚本
		if (data.everyone == "true") {//所有人
			var json = {};
			json.type = "everyone";
			result.push(json);
			return result;
		}
		if (data.none == "true") {//没有人
			var json = {};
			json.type = "none";
			result.push(json);
			return result;
		}
		//第二部分是通赔的组选择器类型的权限
		for(var key in scope.groupPermissionList){
			if(scope.data[key].id){
				var json = {};
				json.type=key;
				json.id=scope.data[key].id;
				json.name=scope.data[key].name;
				result.push(json);
			}
		}
		return result;
	}
</script>
</head>
<body ng-controller="ctrl">
	<div>
		<table class="table-form">
			<thead>
				<th>权限类型</th>
				<th>内容</th>
				<th>操作</th>
			</thead>
			<tr ng-if="permissionList.everyone">
				<th>所有人</th>
				<td></td>
				<td>
					<input type="checkbox" ng-true-value="true" ng-false-value="false" ng-model="data.everyone" />
				</td>
			</tr>
			<tr ng-if="permissionList.none">
				<th>无权限</th>
				<td></td>
				<td>
					<input type="checkbox" ng-true-value="true" ng-false-value="false" ng-model="data.none" />
				</td>
			</tr>
			<tr ng-repeat="(key,value) in groupPermissionList" ng-show="data.everyone!='true'&&data.none!='true'" >
				<th>{{value}}</th>
				<td>{{data[key].name}}</td>
				<td>
					<a class="btn btn-primary btn-mini" href="javaScript:void(0)" ng-click="dialog(key)">
						<span>选择</span>
					</a>
					<a class="btn btn-primary btn-mini" href="javaScript:void(0)" ng-click="reset(key)">
						<span>重置</span>
					</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>