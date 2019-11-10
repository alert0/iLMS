<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/jit/jitReckon/savePartRemain"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="零件余量信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/jit/jitReckon/queryPartRemain?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
					<tr>
						<th><span>信息点:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.planCode" ht-validate="{required:true,maxlength:125}" /></td>
					</tr>
					<tr>
						<th><span>落点:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.location" ht-validate="{required:true,maxlength:125}" /></td>
					</tr>
					<tr>
						<th><span>零件编号:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.partNo" ht-validate="{required:true,maxlength:125}" /></td>
					</tr>
					<tr>
						<th><span>零件余量:</span></th>
						<td><input class="inputText" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" ng-model="data.partRemain" ht-validate="{required:true}" /></td>
					</tr>
					<tr>
						<th><span>安全库存:</span></th>
						<td><input class="inputText" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  ng-model="data.safetyInventory" ht-validate="{maxlength:32}" /></td>
					</tr>
					<tr>
						<th><span>截止日期:</span></th>
						<td><input class="inputText datetime" name="endRequireDate" ng-model="data.endRequireDate" ht-validate="{maxlength:32}" /></td>
					</tr>
					<tr>
						<th><span>截止所需零件量:</span></th>
						<td><input class="inputText" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  ng-model="data.endRequireNum" ht-validate="{maxlength:32}" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var app = angular.module('app', ['formDirective','arrayToolService']);
	app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
		$scope.ArrayTool = ArrayTool;
		
		var data={
			
		}
		
		$scope.addRow=function(classVar){
			$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
		}
		
		$scope.$on("afterLoadEvent",function(event,data){
			
		});
		
	}]);
</script>