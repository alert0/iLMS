var app = angular.module('app', [ 'base', 'commonListService', 'CustomQueryService', 'SysDataSourceService', 'CustomDialogService', 'formDirective' ]);
app.controller("EditController", [ '$scope', 'baseService', 'CommonListService', 'CustomQuery', 'SysDataSource', 'CustomDialog', function($scope, BaseService, CommonListService, CustomQuery, SysDataSource, CustomDialog) {
	$scope.CommonList = CommonListService;

	$scope.prop = {};// 初始化，设置默认值
	$scope.prop.style = 0;
	$scope.prop.width = 800;
	$scope.prop.height = 600
	$scope.prop.needPage = 1;
	$scope.prop.pageSize = 10;
	$scope.prop.selectNum = 1;
	$scope.prop.system = false;
	$scope.prop.isTable = 1;
	$scope.prop.dsalias = "LOCAL";// 本地数据源
	$scope.tableOrViewList = [];

	// 如果id不为空，获取初始化数据,利用发请求的方式
	if (id != "") {
		CustomDialog.detail({
			id : id
		}, function(data) {
			$scope.prop = data;
			$scope.prop.displayfield = JSON.parse(data.displayfield);
			$scope.prop.resultfield = JSON.parse(data.resultfield);
			$scope.prop.sortfield = JSON.parse(data.sortfield);
			$scope.prop.conditionfield = JSON.parse(data.conditionfield);
		});
	}

	// 获取数据源池，新建才可以选择
	if (id == "") {
		$scope.prop.id = "";
		SysDataSource.getDataSourcesInBean(function(data) {
			$scope.dataSourcesInBean = data;
		});
	}

	// 获取表或视图列表
	$scope.getByDsObjectName = function() {
		if ($scope.prop.dsalias == null) {
			$.topCall.error("请选择数据源");
			return;
		}

		var params = {
			dsalias : $scope.prop.dsalias,
			isTable : $scope.prop.isTable,
			objName : $scope.prop.objName
		};
		CustomQuery.getByDsObjectName(params, function(data) {
			if (!data || data.length == 0) {
				$.topCall.error("该数据源中未查询到表或视图");
			}
			$scope.tableOrViewList = data;
			if ($scope.tableOrViewList[0])
				$scope.prop.objName = $scope.tableOrViewList[0].name;
		});
	}

	$scope.showSettingDialog = function() {
		if ($scope.prop.objName == null) {
			$.topCall.error("请选择目标表或视图");
			return;
		}

		var title = "未命名";
		if ($scope.prop.name != null) {
			title = $scope.prop.name;
		}
		var url = __ctx + '/form/customDialog/customDialogSetting';
		$.topCall.dialog({
			src : url,
			base : {
				title : '' + title + "-设置列",
				width : 1020,
				height : 580,
				modal : true,
				resizable : true,
				passConf : {
					prop : $scope.prop,
					parentScope : getScope()
				}
			}
		});
	}

	$scope.save = function() {
		if (!$scope.form.$valid)
			return;
		if ($scope.prop.objName == null) {
			$.topCall.error("请选择目标表或视图");
			return;
		}
		CustomDialog.save($scope.prop, function(data) {
			if (data.result == 1) {
				$.topCall.confirm("温馨提示", data.message + ',是否继续操作', function(r) {
					if (r) {
						window.location.reload(true);
					} else {
						HT.window.closeEdit(true, 'grid');
					}
				});
			} else {
				$.topCall.error(data.message, data.cause);
			}
		});
	}

	// 视图和表选择数组
	$scope.isTableList = [ {
		key : '视图',
		value : 0
	}, {
		key : "表",
		value : 1
	} ];
} ]);