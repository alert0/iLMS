<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="calendarApp">
<head>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<title>班次设置</title>
</head>
<body ng-controller="shiftCtrl">
	<div id="tb" class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa-save btn-sm" ng-click="save();">保存</a>
			<button onclick="HT.window.closeEdit(true,'shiftList');" class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	
	<div ng-form name="form">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>
					<span class="required">*</span>
					班次名称:
				</th>
				<td>
					<input type="text" id="name" name="name" ng-model="prop.name" 
						   class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="required">*</span>
					班次说明:
				</th>
				<td>
					<input type="text" id="memo" name="memo" ng-model="prop.memo" class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="required">*</span>
					<abbr ht-tip title="在下方添加班次的工时段会自动统计总工时">工时(分钟):</abbr>
				</th>
				<td>
					<input type="text" id="minutes" readonly="readonly" ht-validate="{'minvalue':1}"
						   name="minutes" ng-model="prop.minutes" class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>
		</table>
		
		<div class="easyui-panel">
			<button class="btn btn-primary btn-sm fa-plus" ng-click="addPeroid()">添加</button>
			<table class="table-form">
				<thead>
				 	<tr>
						<th style="text-align:center;">开始时间</th>
						<th style="text-align:center;">结束时间</th>
						<th style="text-align:center;">工时段(分钟)</th>
						<th style="text-align:center;">说明</th>
						<th style="text-align:center;">管理</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="peroid in peroids">
						<td style="text-align:center;">
							<input class="inputText dateformat" type="text" ht-date="HH:mm:00"
								   ng-model="peroid.startTime" ht-validate="{'required':true,'time':true}" >
						</td>
						<td style="text-align:center;">
							<input class="inputText dateformat" type="text" ht-date="HH:mm:00"
								   ng-model="peroid.endTime" ht-validate="{'required':true,'time':true}" >
						</td>
						<td style="text-align:center;">
							<input type="text" class="inputText" ng-change="peroidMinutes()" readonly="readonly" ng-model="peroid.minutes" ht-validate="{'minvalue':60}"
								   ht-datecalc="{'diffType':'minute','start':'peroid.startTime','end':'peroid.endTime'}"
						</td>
						<td style="text-align:center;">
							<input type="text" ng-model="peroid.memo" class="inputText" />
						</td>
						<td style="text-align:center;">
							<button class="btn btn-danger fa fa-remove" ng-click="removePeroid(peroid)">删除</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		var shiftId = '${param.id}';
	
		angular.module('calendarApp', ['formDirective'])
		.controller("shiftCtrl",['$scope','$http',function($scope, $http){
			$scope.prop = {};
			$scope.peroids = [];
			
			if(shiftId){
				var url = __ctx + "/calendar/shift/detail?id=" + shiftId;
				$http.get(url).then(function(response){
					if(response && response.data && response.data.shift){
						$scope.prop = response.data.shift;
					}
					if(response && response.data && response.data.shiftPeroids){
						$scope.peroids = response.data.shiftPeroids;
					}
				},function(response){
					$.topCall.error("未能正确加载班次信息");
				});
			}
			
			$scope.peroidMinutes = function(){
				$scope.prop.minutes = 0;
				angular.forEach($scope.peroids,function(peroid){
					if(peroid && peroid.minutes){
						var m = Number.parseInt(peroid.minutes);
						if(Number.isInteger(m)){
							$scope.prop.minutes += m;
						}
					}
				});
			}
			
			$scope.addPeroid = function(){
				$scope.peroids.push({});
			}
			
			$scope.removePeroid = function(peroid){
				$scope.peroids.remove(peroid);
			}
			
			$scope.save = function(){
				if(!$scope.form.$valid){
					$.topCall.error("请正确的填写表单");
					return;
				}
				if($scope.peroids.length<1){
					$.topCall.error("请添加工时段");
					return;
				}
				var url = __ctx + "/calendar/shift/save";
				$http.post(url,{shift:$scope.prop,shiftPeroids:$scope.peroids})
					 .then(function(response){
						 var data = response.data;
						 if(data && data.result){
							 $.topCall.success(data.message,function(){
								 HT.window.closeEdit(true,'shiftList');
								 HT.window.refreshParentGrid();
							 });
						 }
						 else{
							 $.topCall.error(data.message); 
						 }
					 },function(response){
						 $.topCall.error("班次保存失败");
					 });
			}
		}]);
	</script>
</body>
</html>