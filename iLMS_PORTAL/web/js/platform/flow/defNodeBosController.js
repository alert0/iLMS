var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;
	$scope.data = {};
	$scope.data.bodef = {};
	$scope.data.bodef.boSaveMode = "database";
	$scope.data.bodef.boDefs = [];
	$scope.nodeSetMap = {};// 节点设置数据
	$scope.isClearForm = false;
	
	
	
	var url="nodeBos?defId=" + defId + "&topDefKey=" + topDefKey ;

	baseService.post(url).then(function(data) {
		$scope.bpmDefinition = data.bpmDefinition;
		$scope.json = data.json;
		$scope.nodeDefList = data.nodeDefList;
		$scope.selectedNode = data.nodeDefList[0];// 默认选一个

		// 初始化json的数据
		if (data.json.bodef.boSaveMode) {
			$scope.data.bodef.boSaveMode = data.json.bodef.boSaveMode;
		}
		if (data.json.bodef) {
			$(data.json.bodef.boDefs).each(function() {
				var temp = {};
				temp.alias = this.key;
				temp.name = this.name;
				baseService.post(__ctx + "/bo/def/bODef/getObject?key=" + this.key).then(function(data) {
					temp.id = data.id;
					$scope.data.bodef.boDefs.push(temp);
				});
			});
		}
		if (data.json.formInitItems) {
			$(data.json.formInitItems).each(function() {
				for (var i = 0; i < this.saveFieldsSetting.length; i++) {
					var temp = {};
					var savet = this.saveFieldsSetting[i];
					var showt = this.showFieldsSetting[i];
					temp.description = savet.description;
					temp.defKey = savet.boDefCode;
					temp.beforeShow = showt.setting;
					temp.whenSave = savet.setting;

					var list = $scope.nodeSetMap[this.nodeId];
					if (!list) {
						$scope.nodeSetMap[this.nodeId] = [];
						list = $scope.nodeSetMap[this.nodeId];
					}
					list.push(temp);
				}
			});
		}

	});

	$scope.addBoDef = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			$scope.$apply(function() {
				$(data).each(function() {
					// 去重
					for (var i = 0; i < $scope.data.bodef.boDefs.length; i++) {
						var tmp = $scope.data.bodef.boDefs[i];
						if (tmp.alias == this.alias) {
							return;
						}
					}
					$scope.data.bodef.boDefs.push(this);
				});
			});
		};
		var supportDb=$scope.data.bodef.boSaveMode=="database"?1:"";
		CustomDialog.openCustomDialog("boDefSelector", callBack, {
			param : {
				support_db_ : supportDb
			}
		});
	};

	$scope.getDesc = function(nodeId) {
		for (var i = 0; i < $scope.nodeDefList.length; i++) {
			var tmp = $scope.nodeDefList[i];
			if (tmp.nodeId == nodeId) {
				return tmp.name + "(" + nodeId + ")";
			}
		}
		return nodeId;
	};

	// 编辑
	$scope.editNodeSet = function(nodeSet,nodeId) {
		var passConf = {};
		passConf.node = $scope.selectedNode;
		passConf.ids = "";
		$($scope.data.bodef.boDefs).each(function() {
			if (passConf.ids != "") {
				passConf.ids += ",";
			}
			passConf.ids += this.id;
		});

		if (nodeSet) {
			passConf.data = jQuery.extend({}, nodeSet);
			for (var i = 0; i < $scope.nodeDefList.length; i++) {
				var tmp = $scope.nodeDefList[i];
				if (tmp.nodeId == nodeId) {
					passConf.node = tmp;
					break;
				}
			}
		}

		if (!$scope.selectedNode) {
			$.topCall.error("请选择节点");
			return;
		}
		if (!passConf.ids) {
			$.topCall.error("请选择至少一个BO定义");
			return;
		}

		var dialog;
		var def = {
			passConf : passConf,
			title : "节点设置",
			width : "800",
			height : "600",
			modal : true,
			resizable : true,
			buttons : [ {
				text : '确定',
				handler : function(e) {
					$scope.$apply(function() {
						dialog.dialog('close');
						var win = dialog.innerWin;
						var data = win.getResult();
						// 编辑
						if (nodeSet) {
							nodeSet = jQuery.extend(nodeSet, data);
							return;
						}

						var list = $scope.nodeSetMap[$scope.selectedNode.nodeId];
						if (!list) {
							$scope.nodeSetMap[$scope.selectedNode.nodeId] = [];
							list = $scope.nodeSetMap[$scope.selectedNode.nodeId];
						}
						list.push(data);
					});
				}
			}, {
				text : '关闭',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		var nodeId = passConf.node.nodeId;
		dialog = $.topCall.dialog({
			src : __ctx + "/flow/def/defNodeBosDialog?defId="+defId+"&parentFlowKey="+topDefKey+"&nodeId="+nodeId,
			base : def
		});
	};
	
	// BO映射
	$scope.editNodeBoMapping = function(nodeSet) {
		var passConf = {};
		passConf.node = $scope.selectedNode;
		passConf.ids = "";
		$($scope.data.bodef.boDefs).each(function() {
			if (passConf.ids != "") {
				passConf.ids += ",";
			}
			passConf.ids += this.id;
		});

		if (nodeSet) {
			passConf.data = jQuery.extend({}, nodeSet);
		}

		if (!$scope.selectedNode) {
			$.topCall.error("请选择节点");
			return;
		}
		if (!passConf.ids) {
			$.topCall.error("请选择至少一个BO定义");
			return;
		}

		var dialog;
		var def = {
			passConf : passConf,
			title : "BO映射",
			width : "800",
			height : "600",
			modal : true,
			resizable : true,
			buttons : [ {
				text : '确定',
				handler : function(e) {
					$scope.$apply(function() {
						dialog.dialog('close');
						var win = dialog.innerWin;
						var data = win.getResult();
						// 编辑
						if (nodeSet) {
							nodeSet = jQuery.extend(nodeSet, data);
							return;
						}

						var list = $scope.nodeSetMap[$scope.selectedNode.nodeId];
						if (!list) {
							$scope.nodeSetMap[$scope.selectedNode.nodeId] = [];
							list = $scope.nodeSetMap[$scope.selectedNode.nodeId];
						}
						list.push(data);
					});
				}
			}, {
				text : '关闭',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		var nodeId = passConf.node.nodeId;
		dialog = $.topCall.dialog({
			src : __ctx + "/flow/def/defNodeBosMapDialog?defId="+defId+"&parentFlowKey="+topDefKey+"&nodeId="+nodeId,
			base : def
		});
	};

	$scope.save = function() {
		var data = jQuery.extend({}, $scope.data);
		// 拼装提交的数据
		$(data.bodef.boDefs).each(function() {
			this.key = this.alias;
		});

		data.formInitItems = [];
		$.each($scope.nodeSetMap, function(key, value) {
			var temp = {};
			temp.nodeId = key;
			temp.parentDefKey = topDefKey;
			temp.saveFieldsSetting = [];
			temp.showFieldsSetting = [];
			$(value).each(function() {
				// 都为空就不保存
				if (!this.beforeShow && !this.whenSave) {
					return;
				}
				this.beforeShow = this.beforeShow ? this.beforeShow : "";
				this.whenSave = this.whenSave ? this.whenSave : "";
				var tmp = {};
				tmp.boDefCode = this.defKey;
				tmp.description = this.description;
				tmp.setting = this.whenSave;
				temp.saveFieldsSetting.push(jQuery.extend({}, tmp));
				tmp.setting = this.beforeShow;
				temp.showFieldsSetting.push(jQuery.extend({}, tmp));
			});
			data.formInitItems.push(temp);
		});
		var param = {};
		param.json = angular.toJson(data);
		param.topDefKey = topDefKey;
		param.flowId = defId;
		param.isClearForm = $scope.isClearForm;
		
		baseService.postForm("saveSetBos", param).then(function(r) {
			if (r.result == 1) {
				$.topCall.success(r.message, function() {
					window.location.reload();
				});
			} else {
				if (r.cause) {
					$.topCall.errorStack(r.message, r.cause, "错误信息");
				} else {
					$.topCall.error(r.message);
				}
			}
		});

	};
} ]);