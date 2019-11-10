var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;

	$scope.data = {};
	if(categoryId){
		$scope.data.categoryId = categoryId;
	}
	$scope.data.status = "normal";
	$scope.data.supportDb = "1";
	$scope.data.deployed = "0";
	$scope.subEnts = [];
	
	$scope.isStepCreate = true;
	if(parent.formNextStep){
		$scope.isStepCreate = false;
	}

	$scope.init = function() {
		baseService.post("bODef/isEditable?id=" + id).then(function(data) {
			//只有未发布的才能编辑
			$scope.isEditable = data==='true';
		});
		
		//如果是快捷方式创建
		if(parent.boEntList){
			try {
				$scope.mainEnt = parent.boEntList[0];
				parent.boEntList.remove(0);
				if(parent.boEntList.length>0){
					$(parent.boEntList).each(function() {
						if (this.name == $scope.mainEnt.name) {
							return;
						}
						for (var i = 0; i < $scope.subEnts.length; i++) {
							var temp = $scope.subEnts[i];
							if (temp.name == this.name) {// 已存在
								return;
							}
						}
						this.relation = "onetomany";
						$scope.subEnts.push(this);
					});
				}
			} catch (e) {}
			
		}
	};

	$scope.selectMainEnt = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			$scope.$apply(function() {
				$scope.mainEnt = data[0];
			});
		};

		CustomDialog.openCustomDialog("boEntSelector", callBack, {
			param : {
				is_create_table_ : $scope.data.supportDb
			}
		});
	};

	$scope.addSubEnt = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			$scope.$apply(function() {
				$(data).each(function() {
					if (this.name == $scope.mainEnt.name) {
						return;
					}
					for (var i = 0; i < $scope.subEnts.length; i++) {
						var temp = $scope.subEnts[i];
						if (temp.name == this.name) {// 已存在
							return;
						}
					}
					this.relation = "onetomany";
					$scope.subEnts.push(this);
				});
			});
		};
		CustomDialog.openCustomDialog("boEntSelector",callBack,{
			selectNum : "-1",
			param:{
				is_create_table_:$scope.data.supportDb
			}
		});
	};

	$scope.$on("beforeSaveEvent", function(event, data) {// 提交前做数据整理
		if (!$scope.mainEnt) {
			$.topCall.error("请选择主实例");
			data.pass = false;
			return;
		}
		if(parent.formNextStep){
			$scope.data.deployed = 1;
		}

		$scope.data.ents = $.extend(true, [], $scope.subEnts);
		$scope.mainEnt.relation = "main";
		$scope.data.ents.push($scope.mainEnt);
	});
	
	$scope.afterSave=function(data){
		if(parent.formNextStep){
			if(parent.isContinue){
				parent.formNextStep("current","/bo/def/bODefEdit","bODefEdit",data.postData.alias);
			}else{
				parent.formNextStep("next","/form/formDef/addFormStepOne","addFormStepOne",data.postData.alias);
			}
		}else{
			$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
				if(!r){
					HT.window.closeEdit(true);
				}
				window.location.reload();
			});
		}
	}

	$scope.$on("afterSaveEvent", function(event, data) {
		if(!data.r){
			HT.window.closeEdit(true);
		}
		window.location.reload();
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		data.supportDb = data.supportDb + "";// 转义成字符串

		// 加载属性列表
		var rtn = baseService.post("bODef/getRelList?defId=" + data.id);
		rtn.then(function(data) {
			if (!data)
				return;
			$(data).each(function() {
				if (this.type == "main") {
					$scope.mainEnt = this.refEnt;// 主实例
					return;
				}
				this.refEnt.relation = this.type;
				$scope.subEnts.push(this.refEnt);
			});
		}, function(status) {
			$.topCall.error("请求失败");
		});
	});

} ]);