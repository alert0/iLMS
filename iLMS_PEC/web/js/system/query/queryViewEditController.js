var app = angular.module('app', [ 'formDirective', 'arrayToolService', 'ui.codemirror' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.data = {};
		$scope.data.rebuildTemp = 1;
		$scope.data.sqlAlias = __param.sqlAlias;
		$scope.data.filterType = 1;
		$scope.data.needPage = 1;
		$scope.data.pageSize = 15;
		$scope.data.initQuery = 1;
		$scope.data.showRowsNum = 0;
		$scope.data.supportGroup = 0;
		
		$scope.data.filterInitType = $scope.data.filterType;//初始化时的脚本类型
		$scope.data.filterInitSql = '';//初始化时的脚本
		
		initGroup();//初始化分组信息
		
		// 获取模板列表
		baseService.postForm(__ctx + "/system/query/queryView/getTempList", {}).then(function(data) {
			$scope.tempList = data;
		});

		// 获取sql配置
		baseService.postForm(__ctx + "/system/query/querySqldef/getJson", {
			alias : __param.sqlAlias
		}).then(function(data) {
			$scope.sqldef = data;
			// 新增时需要加载初始化默认的显示字段和条件字段的
			if (!__param.id) {
				$scope.initDefaultData();
			}
		});

		// 获取对应的常量
		baseService.postForm(__ctx + "/system/util/getBean", {
			beanId : "queryViewComVarList"
		}).then(function(data) {
			$scope.comVarList = data;
		});

	};

	$scope.defaultSortChange = function(obj) {
		$($scope.data.shows).each(function(i, n) {
			if (n == obj) {
				return;
			}
			n.defaultSort = 0;
		});
	};

	$scope.clickVar = function() {
		$scope.$broadcast('CodeMirror', function(CodeMirror) {
			CodeMirror.replaceSelection($scope.selectVar);
		});
	};
	
	//当改变的脚本类型是初始化的脚本类型时，将过滤脚本设置为初始化时的脚本，否则置空
	$scope.changeFilterType = function(){
		if($scope.data.filterType == $scope.data.filterInitType){
			$scope.data.filter = $scope.data.filterInitSql;
		}else{
			if($scope.data.filterType > 1){
				$scope.data.filter = " ";
			}
		}
	}

	//保存表单时，校验表单是否通过，未通过弹出提示信息。主要是处理当未通过验证的表单不在当前显示的tab时，用户点保存按钮保存不了，也无任何提示。
	$scope.saveFormValid = function(){
		// 处理过滤条件
		if ($scope.data.filterType == 1 && filterInitPoint) {
			$scope.data.filter = getData();
		}
		if (!$scope.form.$valid){
			$.topCall.error("表单验证未通过，请检查修改，确认后再提交！");
			return ;
		};
	}

	// 初始化默认的显示字段和条件字段的
	$scope.initDefaultData = function() {
		$scope.data.shows = [];
		$scope.data.conditions = [];
		$($scope.sqldef.metafields).each(function() {
			if (this.isShow == 1) {// 显示字段
				var json = CloneUtil.shallow(this);
				json.sortable = 0;
				json.sortSeq = "asc";
				json.defaultSort = 0;
				json.frozen = 0;
				json.hidden = 0;
				json.align = "center";
				json.group = 0;
				$scope.data.shows.push(json);
			}
			if (this.isSearch == 1) {// 条件字段
				json = CloneUtil.shallow(this);
				json.hidden = 0;
				json.operate = "EQ";
				json.valueFrom = "1";
				$scope.data.conditions.push(json);
			}
		});
		// 默认的按钮
		$scope.data.buttons = JSON.parse($scope.sqldef.buttonDef);
	};

	$scope.$on("beforeSaveEvent", function(event, data) {
		// 处理过滤条件
		if ($scope.data.filterType == 1 && filterInitPoint) {
			$scope.data.filter = getData();
		}
		if($scope.data.filterType != 1&&!$scope.data.filter.trim()){//验证过滤条件为SQL或追加SQL时，脚本为必填
			data.pass=false;
			$.topCall.error("请正确填写过滤条件中的SQL语句");
		}
	});
	
	$scope.$on("afterSaveEvent", function(event, data) {
		if(!data.r){
			HT.window.closeEdit(true,'grid');
		}
		window.location.reload();
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		$scope.data.rebuildTemp = 1;
		data.buttons = JSON.parse(data.buttons);
		data.conditions = JSON.parse(data.conditions);
		data.shows = JSON.parse(data.shows);
		data.groupSetting = JSON.parse(data.groupSetting);
	});

	$scope.groupChange = function(item) {
		if (!$scope.data.groupSetting) {// 初始化一下
			initGroup();
		}
		
		if (item.group == "1") {// 新增进去
			$scope.data.groupSetting.groupField.push(item.fieldName);
			$scope.data.groupSetting.groupColumnShow.push(true);
			$scope.data.groupSetting.groupText.push("<b> " + item.fieldDesc + " : {0},数量{1} </b>");
			$scope.data.groupSetting.groupOrder.push("asc");
			$scope.data.groupSetting.groupSummary.push(true);
		} else {// 删除
			var list = $scope.data.groupSetting.groupField;
			for (var i = 0; i < list.length; i++) {
				if (list[i] == item.fieldName) {
					ArrayTool.del(i, $scope.data.groupSetting.groupField);
					ArrayTool.del(i, $scope.data.groupSetting.groupColumnShow);
					ArrayTool.del(i, $scope.data.groupSetting.groupText);
					ArrayTool.del(i, $scope.data.groupSetting.groupOrder);
					ArrayTool.del(i, $scope.data.groupSetting.groupSummary);
					break;
				}
			}
		}
	};

	$scope.groupUp = function(i) {
		ArrayTool.up(i, $scope.data.groupSetting.groupField);
		ArrayTool.up(i, $scope.data.groupSetting.groupColumnShow);
		ArrayTool.up(i, $scope.data.groupSetting.groupText);
		ArrayTool.up(i, $scope.data.groupSetting.groupOrder);
		ArrayTool.up(i, $scope.data.groupSetting.groupSummary);
	};

	$scope.groupDown = function(i) {
		ArrayTool.down(i, $scope.data.groupSetting.groupField);
		ArrayTool.down(i, $scope.data.groupSetting.groupColumnShow);
		ArrayTool.down(i, $scope.data.groupSetting.groupText);
		ArrayTool.down(i, $scope.data.groupSetting.groupOrder);
		ArrayTool.down(i, $scope.data.groupSetting.groupSummary);
	};
	
	function initGroup(){
		var groupSetting = {};
		groupSetting.groupField = [];
		groupSetting.groupColumnShow = [];
		groupSetting.groupText = [];
		groupSetting.groupOrder = [];
		groupSetting.groupSummary = [];
		$scope.data.groupSetting = groupSetting;
	}
} ]);