var App = angular.module('app',['IdentityService','formDirective']);
App.controller("IdentityController",['$scope','IdenService',function($scope,IdenService){
	$scope.identity={"genType":"1","step":"1","initValue":"1","noLength":"5","regulation":"{yyyy}{MM}{dd}{NO}"};
	if(id!=""){
		IdenService.detail({id:id},function(data){
			$scope.identity=data;
		});
	}
	
	$scope.save = function(){
		if($scope.identityForm.$invalid){
			$.topCall.error("表单校验失败","");
			return;
		}
		
		IdenService.saveData($scope.identity,function(data){
			if(data.result==1){
				$.topCall.confirm("温馨提示",data.message+',是否继续操作',function(result){
					if(result){
						window.location.reload(true);
					}else{
						HT.window.refreshParentGrid();
						HT.window.closeEdit(true,'grid');
					}
				});
			}else{
				$.topCall.error(data.message);
			}
		})
	}
	
	$scope.close = function(){
		HT.window.closeEdit(true,'identityList');
		HT.window.refreshParentGrid();
	}
	
}]);