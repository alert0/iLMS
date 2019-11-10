<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/org/sysUserGroup/groupSetDialogController.js"></script>
<script type="text/javascript">
	var scope;
	$(function() {
		scope = AngularUtil.getScope();
	});

	//要拼装返回对象
	function getResult() {
		return scope.getResult();
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
			<tr ng-repeat="item in noNeedSetting track by $index" >
				<th>{{item.title}}</th>
				<td></td>
				<td>
					<input type="checkbox"  ng-click="checkNoSetting($event,item)"  ng-checked="item.checked" />
				</td>
			</tr>
			<tbody>
				<tr ng-repeat="item in needSetting track by $index" ng-show="showNeedRight" >
					<th>{{item.title}}</th>
					<td>{{item.name}}</td>
					<td>
						<a class="btn btn-primary btn-mini"  ng-click="dialog(item)">
							<span>选择</span>
						</a>
						<a class="btn btn-primary btn-mini"  ng-click="reset(item)">
							<span>重置</span>
						</a>
					</td>
				</tr>
			</tbody>
			
		</table>
	</div>
</body>
</html>