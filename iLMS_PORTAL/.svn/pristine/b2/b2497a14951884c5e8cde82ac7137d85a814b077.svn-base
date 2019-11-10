var app = angular.module('app', [ 'formDirective', 'arrayToolService','ui.codemirror' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
	};
	
	$scope.$on("afterLoadEvent",function(event,data){
		data.rebuildTemp=0;
	});
	
	$scope.$on("afterSaveEvent", function(event, data){
		if(!data.r){
			document.getElementById("queryViewEditBack").click();
		}
	});
} ]);