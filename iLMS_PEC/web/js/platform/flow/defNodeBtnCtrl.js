var app = angular.module("btnApp", [ 'base', 'formDirective', 'arrayToolService' ]);
		app.controller('btnController', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
			$scope.buttonList = [];
			$scope.buttonNoInitList = [];
			$scope.ArrayTool = ArrayTool;
			
			//按钮状态 1.存在的，2，直接新增的，3，添加预定义的按钮。
			$scope.setStatus=function(data,status){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					obj.status=status;
				}
			};
			
			//0:获取默认初始化的按钮,1:获取配置的按钮,2:获取默认不初始化的按钮
			$scope.getButtons = function(action){
				var get = baseService.get(__ctx + "/flow/def/getNodeSet?defId="+__param.defId+"&nodeId="+__param.nodeId+"&action="+action)
				get.then(function(data){
					if(action==2){
						$scope.setStatus(data,3);
						$scope.buttonNoInitList=data;
					}
					else{
						$scope.setStatus(data,1);
						$scope.buttonList=data;
					}
					if(action==0){
						$.topCall.success("初始化成功"); 
					}
					
					
				},function(code){
					$.topCall.error("error!"+code);
				})
			};
			$scope.saveButtons = function(){
				if(!$scope.myForm.$valid){
					$.topCall.error("当前编辑按钮表单不合法！");
					return;
				}
				
				var param = __param;
				param.buttonList =  JSON.stringify($scope.buttonList);
				
				baseService.postForm(__ctx + "/flow/def/saveNodeBtns",param).then(function(){
					$.topCall.success("保存成功");
					if(window.passConf)passConf($scope.buttonList);
					window.selfDialog.dialog('close')
				},function(){});
			};
			
			$scope.addButton = function(){
				if(!$scope.myForm.$valid){
					$.topCall.error("请确认当前编辑按钮是否正确！");
					return;
				}
				
				var btn = {name:"",alias:"",status:2,supportScript:true};
				$scope.buttonList.push(btn);
				$scope.btn =btn;
				
				setTimeout(function(){
					$("#divContainer").scrollTop($("#divContainer").height());
				});
				
			};
			
			$scope.changeEditing = function(index){
				if(!$scope.myForm.$valid){
					$.topCall.error("当前编辑按钮表单不合法！");
					return;
				}
				$scope.btn = $scope.buttonList[index];
			};
			$scope.del = function(index){
				if($scope.buttonList[index]==$scope.btn)$scope.btn =false;
				$scope.buttonList.splice(index,1);
			};
			$scope.changeAlias = function(){
				for(var i=0,btn;btn=$scope.buttonList[i++];){
					if(btn.alias==$scope.btn.alias && $scope.btn !== btn){
						$.topCall.error("["+btn.alias+"]按钮alias 不可重复！");
						$scope.btn.alias = "";
						return;
					}					
				}
			};
			
			/**
			 * 判断按钮是否已经存在。
			 */
			$scope.isAliasExist=function(nBtn){
				for(var i=0,btn;btn=$scope.buttonList[i++];){
					if(btn.alias==nBtn.alias && $scope.btn !== btn){
						return true;
					}					
				}
				return false;
			};
			
			$scope.changeButtonType=function(obj){
				if(obj.alias){
					var rtn=$scope.isAliasExist(obj);
					if(rtn){
						obj.alias="";
						return;
					}
					obj.status=3;
					obj.name=$scope.getName(obj.alias);
				}
				else{
					obj.status=2;
				}
			};
			
			$scope.getName=function(alias){
				for(var i=0;i<$scope.buttonNoInitList.length;i++){
					var obj=$scope.buttonNoInitList[i];
					if(alias==obj.alias){
						return obj.name;
					}
				}
				return "";
			};
			
			
			//获取配置的按钮。
			$scope.getButtons(1);
			//获取为初始化按钮。
			$scope.getButtons(2);
			
		} ]);