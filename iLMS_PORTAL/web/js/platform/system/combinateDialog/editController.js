var app = angular.module('app', [ 'base', 'arrayToolService', 'CombinateDialogService', 'CustomDialogService', 'formDirective' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', 'CombinateDialog', 'CustomDialog', function($scope, BaseService, ArrayToolService, CombinateDialog, CustomDialogService) {
	$scope.ArrayTool = ArrayToolService;
	// 对象
	$scope.prop = {};
	$scope.prop.field = [];
	$scope.prop.width = "800";
	$scope.prop.height = "400";

	// 页面用到的参数或临时变量
	$scope.param = {};

	// 初始化
	if (id != "") {
		CombinateDialog.detail({
			id : id
		}, function(data) {
			$scope.prop = data;
			$scope.prop.field = JSON.parse(data.field);

			// 初始化树对话框和列表对话框
			CustomDialogService.detail({
				id : data.treeDialogId
			}, function(data) {// 树
				$scope.param.treeDialog = data;
				$scope.param.treeDialog.resultfield = JSON.parse(data.resultfield);
			});
			CustomDialogService.detail({
				id : data.listDialogId
			}, function(data) {// 树
				$scope.param.listDialog = data;
				var list = [];
				$(JSON.parse(data.conditionfield)).each(function() {
					if (this.defaultType == '4') {
						list.push(this);
					}
				});
				$scope.param.listDialog.conditionfield = list;
			});
		});
	}

	// 选择列表对话框
	$scope.selectListDialog = function() {
		var callBack = function(data, dialog) {
			$scope.$apply(function() {
				$scope.prop.listDialogId = data[0].id;
				$scope.prop.listDialogName = data[0].name;
				CustomDialogService.detail({
					id : data[0].id
				}, function(data) {
					$scope.param.listDialog = data;
					var list = [];
					$(JSON.parse(data.conditionfield)).each(function() {
						if (this.defaultType == '4') {
							list.push(this);
						}
					});
					$scope.param.listDialog.conditionfield = list;
				});
			});
			dialog.dialog('close');
		};
		CustomDialog.openCustomDialog("customDialogSelector", callBack, {
			param : {
				STYLE_ : "0"
			}
		});
	};

	// 选择树对话框
	$scope.selectTreeDialog = function() {
		var callBack = function(data, dialog) {
			$scope.$apply(function() {
				$scope.prop.treeDialogId = data[0].id;
				$scope.prop.treeDialogName = data[0].name;

				CustomDialogService.detail({
					id : data[0].id
				}, function(data) {
					$scope.param.treeDialog = data;
					$scope.param.treeDialog.resultfield = JSON.parse(data.resultfield);
				});
			});
			dialog.dialog('close');
		};
		CustomDialog.openCustomDialog("customDialogSelector",callBack,{
			param:{
				STYLE_:"1"
			}
		});
	};

	$scope.createField = function() {
		var json = {};
		$scope.prop.field.push(json);
	};

	/**
	 * 提交前要判断规则是否正确
	 */
	$scope.$on("beforeSaveEvent", function(event, data) {
		if (!$scope.prop.treeDialogId || !$scope.prop.listDialogId || $scope.prop.field.length <= 0) {
			data.pass = false;
			$.topCall.error("请正确配置组合规则");
		}
	});
	
	$scope.$on("afterSaveEvent", function(event, data) {
		if (data.r) {
			window.location.reload(true);
		} else {
			HT.window.closeEdit(true, 'grid');
		}
	});
} ]);