
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.data = {type:"1",ctlType:"input",json:[]};
	
	$scope.init = function() {
		// 获取自定义对话框列表
		baseService.postForm(__ctx + "/form/customDialog/getAll", {}).then(function(data) {
			$scope.customDialogs=[];
			$(data).each(function() {
			  if(this.resultfield) {
				  this.resultfield = JSON.parse(this.resultfield);
				  $scope.customDialogs.push(this);
			   }
			});
			// 初始化当前对话框
			if ($scope.data.ctlType != "customdialog")
				return;
			$($scope.customDialogs).each(function() {
				if (this.alias == $scope.data.json.alias) {
					$scope.customDialog = this;// 当前选择的自定义对话框
				}
			});
		});
	};
	
	setTimeout($scope.init,500);
	
	
	$scope.$on("beforeSaveEvent",function(event, data){
		if($scope.data.ctlType == "select" || $scope.data.ctlType == "checkbox" || $scope.data.ctlType == "radio"){
			if( $scope.data.json=='' || !$scope.data.json || $scope.data.json.length==0){
				data.pass = false;
				$.topCall.error("请添加自定义选项");
			}		
		}
		if($scope.data.ctlType == "dic"){
//			$scope.data.json = $scope.$$childTail.dicItem;
//			//这样写得到的数据不是json数据，将导致$scope.data.json = angular.fromJson($scope.data.json)在页面上报错，
//			//因为此时的$scope.data.json不是json数据;
			$scope.data.json = {"dicItem":$scope.$$childTail.data.json.dicItem};
		}
		if($scope.data.ctlType == "customdialog"){
			$scope.data.json = {"alias":$scope.$$childTail.data.json.alias,"resultField":$scope.$$childTail.data.json.resultField,"isSingle":$scope.$$childTail.data.json.isSingle};
		}
	});
	
	$scope.$on("afterSaveEvent",function(event,data){
		HT.window.refreshParentGrid();
		if(data.r){
		  window.location.reload();
		}else{
			HT.window.closeEdit();
		}
	});
	
	$scope.$on("afterLoadEvent",function(data){
		$scope.data.json = angular.fromJson($scope.data.json);
	});
	
	$scope.addItem = function(){
		$scope.data.json.push(angular.copy({key:"",value:""}));
	}
	
	// 自定义对话框别名更改
	$scope.$watch('data.json.alias', function(newValue, oldValue, scope) {
		if (oldValue == newValue) return;
		
		$($scope.customDialogs).each(function() {
			if (this.alias == newValue) {
				$scope.customDialog = this;// 当前选择的自定义对话框
				$scope.data.json.resultField = this.resultfield[0].field;
			}
		});
	});
	
}]);