
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	
	if(id==""){
		$scope.data={}
		$scope.data.enabled=1;
	}
	else{
		$("#alias").attr("readonly","readonly");
	 
	}
		
	$scope.$on("afterSaveEvent",function(event,data){
		HT.window.refreshParentGrid();
		if(data.r){
		  window.location=window.location;
		}
		else{
			HT.window.closeEdit();
		}
	});
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
}]);