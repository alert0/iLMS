<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script src="${ctx }/js/platform/util/Dialogs.js" ></script>
<script type="text/javascript">
	var id = "${param.id}",userId = "${param.userId}",userName = "${param.userName}";
	var app = angular.module('app', ['formDirective','arrayToolService']);
	app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function(scope, baseService,ArrayTool) {
		scope.ArrayTool=ArrayTool;
		scope.data = {id:id};
		
		scope.$on('afterLoadEvent',function(ev,data){
			scope.data.userName = userName;
		});
		
		scope.setLevel = function(){
			if(scope.form.$invalid)return;
			baseService.postForm(__ctx+"/org/sysUserRel/setLevel",scope.data).then(
					function(data){
						if(data.result == 1){
							$.topCall.success("修改成功！",function(){
								var _window = window;
								_window.parentWindow.refreshNode();
								selfDialog.dialog('close');
							});
						}else{
							if (data.cause) {
								$.topCall.errorStack(data.message, data.cause, "错误信息");
							} else {
								$.topCall.error(data.message);
							}
						}
					},
					function(code){}
					);
		}
		
		scope.selectUser = function(){
			var conf = {callBack:function(data){
				scope.data.userId = data[0].id;
				scope.data.userName = data[0].name;
				scope.$digest();
			},isSingle:1};
			UserDialog(conf);
		}
		
		
		
	}]);
</script>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-click="setLevel()"><span>保存</span></a>
			<a class="btn btn-sm btn-primary fa-back" onClick="selfDialog.dialog('close')"><span>关闭</span></a>
		</div>
	</div>
	<form name="form" method="post" ht-load="${ctx }/org/sysUserRel/getJson?id=${param.id }" ng-model="data" >
		<table class="table-form" cellspacing="0">
			<tr>
				<th><span>用户:</span></th>
				<td><input class="inputText" type="text" ng-model="data.userName" ng-disabled="true" ht-validate="{required:true,maxlength:30}"/>
					<a class="btn btn-primary btn-xs" ng-click="selectUser()">选择</a>
				</td>
			</tr>
			<tr>
				<th><span>级别:</span></th>
				<td><input class="inputText" type="text" ng-model="data.level" ht-validate="{required:false,maxlength:30}"/></td>
			</tr>
			
		</table>
	</form>
</body>
</html>