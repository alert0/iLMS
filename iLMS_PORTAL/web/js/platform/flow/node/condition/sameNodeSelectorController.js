var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;

	$scope.data = calc;
	
	baseService.post(__ctx + "/flow/node/getNodes?defId="+defId).then(function(data) {
		 
		$scope.nodeList=[];
		$(data).each(function(){
			if((this.type=='signTask'||this.type=='userTask')&&this.nodeId!=nodeId){
				$scope.nodeList.push(this);
			}
		});
	});
} ]);