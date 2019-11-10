
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	$scope.changeGroup=function(){
		$scope.data.group=$scope.data.category;
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
}]);