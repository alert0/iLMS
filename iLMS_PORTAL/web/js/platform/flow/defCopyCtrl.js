var app = angular.module('app',['base','formDirective']);
app.controller("ctrl",function($scope){
	$scope.data = {};
	$scope.data.defId = $.getParameter("defId");
	$scope.$on("afterSaveEvent", function(event, data){
		HT.window.refreshParentGrid();
		if(data.r){
		  window.location.reload();
		}else{
			HT.window.closeEdit();
		}
	});
});