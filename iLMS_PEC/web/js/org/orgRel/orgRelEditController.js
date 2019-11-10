
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.data={}
	//初始化职务列表控件
	$(".easyui-combotree-me").combobox({
		url:__ctx + "/org/orgReldef/getAllJson",
		valueField:'id',  
		textField:'name',
		required:true,
		onChange : function(n,o){
			$scope.$apply(function(){
			   $scope.data.relDefId=n;
			});
		},
		onShowPanel:function(){
			$($(".panel").find("div").get(0)).css({"overflow-y":"auto"});
		}
	});
 

	if(id==""){
		$scope.data.orgId=orgId;
	}
	else{
		$("#relCode").attr("readonly","readonly");
		$("#job-tr").hide();
	}
	$scope.$on("beforeSaveEvent",function(event,data){
		if(id==""){
			$scope.$apply(function(){
				  $scope.data.orgId=orgId;
				  $scope.data.relDefId=$('.easyui-combotree-me').combobox('getValue');
			});

		}
		
	});
	$scope.$on("afterSaveEvent",function(event,data){
		HT.window.refreshParentGrid();
		if(data.r){
		  window.location=window.location;
		}else{
			HT.window.closeEdit();
		}
	});
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
}]);