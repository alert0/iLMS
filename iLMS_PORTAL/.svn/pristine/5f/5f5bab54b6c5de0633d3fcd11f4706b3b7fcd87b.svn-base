var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		if(initModel){
			$scope.data = angular.fromJson(initModel);
			$scope.data.supportTab = "1";
			return;
		}
		
		$scope.data = {};
		$scope.data.metafields = [];
		$scope.data.buttonDef = [ {
			inRow : "0",
			name : "导出",
			triggerType : "onclick",
			isDefault : "1",
			urlPath : "exports()"
		} ];// 默认有导出按钮
		$scope.data.dsName = "LOCAL";
		$scope.data.supportTab = "1";
	};

	$scope.save = function() {
		if (!$scope.form.$valid)
			return;
		// 提交前要经过sql验证
		$scope.checkSql(function(data) {
			beforeSave();// 保存前做的数据整理操作
			baseService.post(__ctx+"/system/query/querySqldef/save", $scope.data).then(function(data) {
				if (data.result == 1) {
					if(initModel){
						$.topCall.success("列表页面添加成功！请到自定义sql管理中，配置视图页。");
						HT.window.closeEdit(true,'grid');
						return ;
					}
					
					$.topCall.confirm(data.title?data.title:"操作成功",data.message, function(r) {
						if(!r){
							HT.window.closeEdit(true,'grid');
						}
						window.location.reload();
					});
				} else {
					if (data.cause) {
						$.topCall.errorStack(data.message, data.cause, "错误信息");
					} else {
						$.topCall.error(data.message);
					}
				}
			});
		});
	};

	/**
	 * 验证成功时的回调函数：callBack
	 */
	$scope.checkSql = function(callBack) {
		if (!callBack) {
			callBack = function(data) {
				$.topCall.success(data.message);
			};
		}

		var param = {};
		param.sql = $scope.data.sql;
		param.dsName = $scope.data.dsName;
		baseService.postForm(__ctx+"/system/query/querySqldef/checkSql", param).then(function(data){
			if (data.result == 1){
				callBack(data);
			} else {
				if (data.cause) {
					$.topCall.errorStack(data.message, data.cause, "错误信息");
				} else {
					$.topCall.error(data.message);
				}
			}
		});
	};

	$scope.addButton = function() {
		var json = {};
		json.inRow = "0";
		json.triggerType = "onclick";
		json.isDefault = "0";
		$scope.data.buttonDef.push(json);
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		data.buttonDef = JSON.parse(data.buttonDef);
		$(data.metafields).each(function(){
			if(this.alarmSetting) {
				this.alarmSetting = JSON.parse(this.alarmSetting);
			}
			if(this.controlContent) {
				this.controlContent = JSON.parse(this.controlContent);
			}
		});
	});
	
	$scope.changeSn = function(event) {
		if (event.keyCode == "13") {
			ArrayTool.turnTo(this, $scope.data.metafields);
		}
	};

	$scope.fieldDialog = function(type, field) {
		var url = __ctx+"/system/query/queryMetafield" + type + "Dialog";
		var title = "";
		var callBack = function(data, dialog) {
			jQuery.extend(field, data)
		};
		if (type == "K") {
			title = "控件设置";
		}
		if (type == "B") {
			title = "报警设置";
		}
		if (type == "X") {
			title = "添加虚拟列";
			callBack = function(data, dialog) {// 虚拟列不同于其他的回调
				$scope.data.metafields.push(data);
			};
		}
		if (type == "P") {
			title = "虚拟列配置";
		}
		DialogUtil.openDialog(url, title, CloneUtil.deep(field), function(data, dialog) {
			if (!data)// 空说明没通过验证返回了空对象，所以不需要关页面
				return;
			dialog.dialog('close');
			$scope.$apply(function() {
				callBack(data, dialog);
			});
		}, 800, 600);
	};

	/**
	 * 检查这个列有没有虚拟列
	 */
	$scope.checkHasX = function(field) {
		var b = false;
		$($scope.data.metafields).each(function() {
			if (this.isVirtual == 1 && this.virtualFrom == field.fieldName) {
				b = true;
			}
		});
		return b;
	};

	// 保存前做的数据整理操作
	function beforeSave() {
		$($scope.data.metafields).each(function(i) {
			this.sn = i+1;
		});
	}
} ]);