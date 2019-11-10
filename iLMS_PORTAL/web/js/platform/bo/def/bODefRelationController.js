var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.init = function() {
		baseService.post("bODef/initRelation?id=" + __param.id+"&type=" + __param.type).then(function(data) {
			$scope.boList = data.boList;
			$scope.defList = data.defList;
			$scope.formList = data.formList;
			$scope.formDefList = data.formDefList;
			$scope.flowList = data.flowList;
			$scope.type = __param.type;
		});
	};
} ]);