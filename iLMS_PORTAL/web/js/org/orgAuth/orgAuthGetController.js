var app = angular.module('app', ['formDirective']);
app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
	$scope.isShow = function(perms,item){
		var data = $scope.data;
		if(perms){
			if(perms.indexOf(item) > -1){
				return true;
			}else{
				return false;
			}
		}
	}
}]);