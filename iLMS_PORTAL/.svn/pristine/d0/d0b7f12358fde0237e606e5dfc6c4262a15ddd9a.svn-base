var systemApp = angular.module('systemApp', [ 'base','ServiceSetService','formDirective' ]);

systemApp.controller("serviceSetEditCtrl",['$scope','ServiceSet','ServiceParam',function($scope,ServiceSet,ServiceParam){
	$scope.url = "";
	$scope.services =[];
	$scope.soapBindingOperationInfo = {};
	$scope.serviceSet = {};
	$scope.params = [];
	$scope.input = {};
	$scope.inputTreeId;
	
	//双击某个接口方法
	$scope.onOperationDbClick = function(event,treeId,treeNode){
		//双击服务对象
		if(!treeNode||treeNode.soapBindingOperationInfos){
			return;
		}
		$scope.$apply(function(){
			$scope.soapBindingOperationInfo = treeNode;
			var str = JSON.stringify($scope.soapBindingOperationInfo.inputParams);
			var elementFormDefault = treeNode.getParentNode().getParentNode().elementFormDefault;
			$scope.serviceSet = {
					alias:$scope.soapBindingOperationInfo.name,
					methodName:$scope.soapBindingOperationInfo.name,
					namespace:$scope.soapBindingOperationInfo.namespace,
					url:$scope.soapBindingOperationInfo.getParentNode().getParentNode().wsdlUrl,
					address:$scope.soapBindingOperationInfo.getParentNode().address,
					soapAction:angular.equals(elementFormDefault,"qualified")?'Y':'N',
					inputSet:ServiceSet.handlerStructure($scope.soapBindingOperationInfo.inputParams)
			};
			$scope.serviceSet.outputSet = ServiceSet.handlerStructure($scope.soapBindingOperationInfo.outputParams);
		});
	}
	
	//单击参数时添加/显示参数绑定设置
	$scope.onInputParamClick = function(event,treeId,treeNode){
		$scope.inputTreeId = treeId;
		$scope.$apply(function(){
			$scope.input = treeNode;
			});
	}
	
	$scope.beforeInputParamCheck = function(treeId, treeNode){
		$scope.inputTreeId = treeId;
		if(treeNode.checked){
			$.topCall.confirm("提示信息","是否删除参数绑定设置?",function(r){
				//删除绑定设置
				if(r){
					$scope.$apply(function(){
						delete treeNode["bind"];
					});
				}
				//重新勾选
				else{
					treeNode.checked = true;
				}
			});
		}
		else{
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			treeNode.bind = {type:1};
			zTree.selectNode(treeNode);
			$scope.$apply(function(){
				$scope.input = treeNode;
			});
		}
	}
	
	//保存数据
	$scope.saveforFlow = function(pluginType,defId,nodeId){    
		if(getScope().inputTreeId){
			var treeObj = $.fn.zTree.getZTreeObj(getScope().inputTreeId),
				Innodes = treeObj.getNodes();
			ServiceSet.filterNodes(Innodes);
			getScope().serviceSet.input = Innodes;
		}
		if(getScope().outputTreeId){
			var treeObj = $.fn.zTree.getZTreeObj(getScope().outputTreeId),
				outNodes = treeObj.getNodes();
			ServiceSet.filterNodes(outNodes);
			getScope().serviceSet.output = outNodes;
		}
		getScope().serviceSet.pluginType = pluginType;
		var serviceStr = JSON.stringify(getScope().serviceSet);
		
		param = {defId:defId,nodeId:nodeId,jsonStr:serviceStr};
		var url = __ctx + "/flow/node/autoTaskPluginSave";
		
		$.post(url,param,function(data){
			var resultMessage=new com.hotent.form.ResultMessage(data);
        	if(resultMessage.isSuccess()){
        		$.topCall.success("webService配置成功!");
        	}else{
        		$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
        	}
		}); 
	}
	//OutputParam单击参数时添加/显示参数绑定设置
	getScope().onOutputParamClick = function(event,treeId,treeNode){
		getScope().outputTreeId = treeId;
		getScope().$apply(function(){
			getScope().output = treeNode;
		});
	}
	
	getScope().beforeOutputParamCheck = function(treeId, treeNode){
		getScope().outputTreeId = treeId;
		if(treeNode.checked){
			$.topCall.confirm("提示信息","是否删除参数绑定设置?",function(r){
				//删除绑定设置
				if(r){
					getScope().$apply(function(){
						delete treeNode["bind"];
					});
				}
				//重新勾选
				else{ 
					treeNode.checked = true;
				}
			});
		}
		else{
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			treeNode.bind = {type:1};
			zTree.selectNode(treeNode);
			getScope().$apply(function(){
				getScope().output = treeNode;
			});
		} 
	}
	$scope.onSingleClick = function(e,treeId,treeNode){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandNode(treeNode);
	}
	
	$scope.parse = function(){
		if(!$scope.url){
			$.topCall.alert("提示信息","请输入服务的wsdl地址");
			return;
		}
		$.topCall.progress();
		ServiceSet.serviceInfo($scope.url,function(data){
			$.topCall.closeProgress();
			if(!data || !data.soapService) return;
			
			var soapServiceInfos = data.soapService.soapServiceInfos;
			
			if(!angular.isArray(soapServiceInfos)) return;
			
			var soapServiceInfo = soapServiceInfos[0];
			
			if(!soapServiceInfo) return;
			
			var tag = true;
			$($scope.services).each(function(index,service){
				if(service.name==soapServiceInfo.name){
					tag = false;
					return false;
				}
			});
			if(tag){
				$scope.services.push({name:soapServiceInfo.name,
					  wsdlUrl:$scope.url,
					  elementFormDefault:soapServiceInfo.elementFormDefault,
					  soapBindingOperationInfos:soapServiceInfo.soapBindingInfos});
			}
		});
	}
	
	$scope.save = function(){
		if(!$scope.myForm.$valid){
			$.topCall.error('表单校验不通过，请检查！');
			return;
		}
		if($scope.inputTreeId){
			var treeObj = $.fn.zTree.getZTreeObj($scope.inputTreeId),
				nodes = treeObj.getNodes();
			ServiceSet.filterNodes(nodes);
			$scope.serviceSet.inputSet = nodes;
		}
		$scope.serviceSet.sysServiceParamList = $scope.params;
		var serviceStr = JSON.stringify($scope.serviceSet);
		ServiceSet.save(serviceStr,function(data){
			if(data.result){
				$.topCall.success('保存成功!',function(){
					HT.window.closeEdit(true);
				});
			}
			else{
				$.topCall.error('保存失败!' + data.message);
			}
		});
	}
	
	$scope.addUpdateParam = function(param){
		ServiceSet.addParamDialog({passConf:param}).show(param);
	}
	
	$scope.removeParam = function(param){
		$scope.params.remove(param);
	}
	
	if(serviceSetId){
		ServiceSet.detail(serviceSetId,function(data){
			if(data.result){
				$scope.serviceSet = data.serviceSet;
				if(angular.isString($scope.serviceSet.inputSet)){
					$scope.serviceSet.inputSet = angular.fromJson($scope.serviceSet.inputSet);
				}
			}
		});
		ServiceParam.list(serviceSetId,function(data){
			if(data.result){
				$scope.params = data.params;
			}
		});
	}
}]);