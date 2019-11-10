<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var app = angular.module('app', ['formDirective','arrayToolService']);
	app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function(scope, baseService,ArrayTool) {
		scope.ArrayTool=ArrayTool;
		scope.changePsw = function(){
			if(scope.form.$invalid)return;
			if(scope.newPassword!=scope.password){
				$.topCall.error("新密码与旧密码不同！");
				return ;
			}
			baseService.postForm("updateUserPsw",{oldPassWorld:scope.oldPassword,newPassword:scope.newPassword}).then(
			function(data){
				if(data.result == 1){
					$.topCall.success("修改成功！");
				}else{
					if (data.cause) {
						$.topCall.errorStack(data.message, data.cause, "错误信息");
					} else {
						$.topCall.error(data.message);
					}
				}
			},
			function(code){}
			)
		}
		
	}]);
</script>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-click="changePsw()"><span>保存</span></a>
		</div>
	</div>
	<form name="form" method="post"  ng-model="data">
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>旧密码:</span><span class="required">*</span></th>
				<td><input class="inputText" type="password" ng-model="oldPassword" ht-validate="{required:true,maxlength:192}" /></td>
			</tr>

			<tr>
				<th><span>新密码:</span><span class="required">*</span></th>
				<td><input class="inputText" type="password" ng-model="newPassword" ht-validate="{required:true,maxlength:192}" /></td>
			</tr>
			
			<tr>
				<th><span>重复密码:</span><span class="required">*</span></th>
				<td>
					<input class="inputText" type="password" ng-model="password" ht-validate="{required:true,maxlength:192}" />
					<span ng-if="newPassword!=password" style="color: red;">新密码与旧密码不同！</span>
				</td>
			</tr>
			
		</table>
	</form>
</body>
</html>