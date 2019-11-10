var app = angular.module('app', ['formDirective']);
app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
	
	
	$scope.$on("afterLoadEvent",function(event,data){
		if(data && data.photo!='' && data.photo != null){
			$scope.isShowPhoto = 'custom';
		}else{
			$scope.isShowPhoto = 'init';
		}
	});
	
	
}]);