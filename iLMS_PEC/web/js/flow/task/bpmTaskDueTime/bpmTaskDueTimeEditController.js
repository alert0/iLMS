
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	$scope.className = "progress-bar-success";
	$scope.percent = 0;
	$scope.progress = "";
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	$scope.$on("beforeSaveEvent",function(ev,data){
		if($scope.data.addDueTime==0){
			data.pass = false;
			$.topCall.warn("增加任务时间不能为0");
		}
		if($scope.data.expirationDate2 && $scope.data.expirationDate2.indexOf('0000-00-00 00:00:00')>-1){
			data.pass = false;
			$.topCall.warn("未计算出预计到期时间，请检查工作日相关设置");
		}
		
	});
	
	$scope.$on("afterLoadEvent",function(ev,data){
		data.addDueTime = 0;
		
		// className progress
		 $scope.className = "progress-bar-success";
  		 var percent = (data.dueTime - data.remainingTime)*100/data.dueTime;
  		 $scope.percent = parseFloat(percent.toFixed(2));
  		 if(25<$scope.percent&&$scope.percent<=50){
  			 $scope.className = " progress-bar-info";
  		 }
  		 if(50<$scope.percent&&$scope.percent<=75){
  			 $scope.className = "progress-bar-warning";
  		 }
  		 if(75<$scope.percent){
  			 $scope.className = "progress-bar-danger";
  		 }
  		 if(percent>100){
  			 $scope.percent = 100;
  		 }
  		$scope.percent = $scope.percent + "%";
		
  		$scope.data.fileId = "";
  		$scope.data.remark = "";
	});
	
	$scope.$on("afterSaveEvent",function(ev,data){
		if(data.r){
			window.location.reload();
		}else{
			HT.window.closeEdit();   
		}
	});
	
	$scope.$watch('data.addDueTime',function(newValue, oldVlaue){
		if(newValue){
			$.topCall.progress();
			baseService.get(__ctx + "/flow/task/bpmTaskDueTime/getExpirationDate?id="+$scope.data.id+"&addDueTime="+newValue).then(function(data){
				$.topCall.closeProgress();			
				$scope.data.expirationDate2 = data;			
			});
		}
	});
	
}]);