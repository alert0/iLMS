var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;

	$scope.data = window.passConf.calc;

	$scope.user = {};
	$scope.user.list = [];
	// 指定人员
	if ($scope.data.source == 'spec' && $scope.data.userName != "") {

		$scope.user.name = $scope.data.userName;
		$scope.user.account = $scope.data.account;

		if($scope.user.name){
			var arrName = $scope.user.name.split(',');
			var arrAccount = $scope.user.account.split(',')
			$.each(arrName,function(i,item){
				var item={name:arrName[i],account:arrAccount[i]};
				$scope.user.list.push(item);
			})
		}
	}

	$scope.userDialog = function() {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			$scope.$apply(function() {
				$scope.user = {};
				$scope.user.list = data;
				$scope.user.id = "";
				$scope.user.name = "";
				$scope.user.account = "";
				$(data).each(function() {
					if ($scope.user.id) {
						$scope.user.id += ",";
						$scope.user.name += ",";
						$scope.user.account += ",";
					}
					$scope.user.id += this.id;
					$scope.user.name += this.name;
					$scope.user.account += this.account;
				});
			});
		};
		CombinateDialog.open({alias:"userSelector",selectNum : "-1",initData : $scope.user.list,callBack:callBack});
	};
} ]);