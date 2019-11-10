
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	if(!$scope.data){
		$scope.data={};
	}
	
	var obj={
	}
	/**
	 * 用户选择
	 */
	$scope.userDialog = function() {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			$scope.data.userList=data;
			$scope.$apply(function() {
				$scope.data.pendingUserName = "";
				$scope.data.pendingUserId = "";
				$(data).each(function(index) {
					if(index==0){
						$scope.data.pendingUserId += this.id;
						$scope.data.pendingUserName+= this.name;
					}else{
						$scope.data.pendingUserId +=','+ this.id;
						$scope.data.pendingUserName+=','+ this.name;
					}
				});
			});
		};
		var conf = {
			initData : $scope.data.userList,
			selectNum : ""
		};
		CustomDialog.openCustomDialog("userSelector", callBack, conf);
	};
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	// 保存成功
	$scope.$on("afterSaveEvent",function(ev,data){
		if(data.r){
			window.location.reload();
		}
		else{
			HT.window.refreshParentGrid();
			HT.window.closeEdit(true,'grid');
		}
	});
	$scope.save = function(){
		if (!$scope.form.$valid) return;
		if($scope.data.needPending=='0') {
			$scope.data.pendingUserId='';
			$scope.data.pendingUserName='';
		}
		var rtn = baseService.post("save", $scope.data);
		rtn.then(function(data) {
			if (data.result == 1){
				$.topCall.toast("提示信息",$scope.data.isAdd? "添加成功！":"保存成功！");
				window.parent.location.reload();
			}else{
				$.topCall.error("", data.cause);
			}
		})
	};
	
}]);