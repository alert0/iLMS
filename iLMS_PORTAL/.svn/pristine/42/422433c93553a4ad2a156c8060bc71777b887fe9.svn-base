
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	var formKey = $.getParameter("formKey");
	baseService.get(__ctx+ "/form/dataTemplate/bpmDataTemplate/getJson?formKey="+formKey).then(function(data){
		if(data.status==0){
			HT.window.closeEdit();
			$.topCall.warn(data.msg);
			return;
		}
		console.log(data);
		
		$scope.data = data;
		
	});
	
}]);