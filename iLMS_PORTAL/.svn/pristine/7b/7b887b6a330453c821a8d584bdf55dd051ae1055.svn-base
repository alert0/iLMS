var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;
	$scope.data = {};
	$scope.data.executorVar = {};
	
	// 默认值
	$scope.data.executorVar.executorType="fixed";
	$scope.data.executorVar.userValType="userId";
	$scope.data.executorVar.value="";
	$scope.data.executorVar.valueText="";
	$scope.data.expression="equals";
	
	$scope.init = function(){
		if(window.passConf.data){
			$scope.data=window.passConf.data;		
		}
	};
	
	$scope.dialog = function(){
		if($scope.data.executorVar.executorType=="user"){
			userDialog();
		}
	};
	
	$scope.user={};
	$scope.user.list=[];
	function userDialog() {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			$scope.user={};
			$scope.user.list=data;
			$scope.user.id = "";
			$scope.user.account="";
			$scope.user.name="";
			$(data).each(function(){
				if($scope.user.id){
					$scope.user.id+=",";
					$scope.user.account+=",";
					$scope.user.name+=",";
				}
				$scope.user.id+=this.id;
				$scope.user.account+=this.account;
				$scope.user.name+=this.name;
			});
			$scope.$apply(function() {
				if($scope.data.executorVar.userValType=="userId"){
					$scope.data.executorVar.value=$scope.user.id;
				}else{
					$scope.data.executorVar.value=$scope.user.account;
				}
				$scope.data.executorVar.valueText=$scope.user.name;
			});
		};
		
		var conf = {
			initData : $scope.user.list
		};
		CustomDialog.openCustomDialog("userSelector",callBack,conf);
	}
} ]);