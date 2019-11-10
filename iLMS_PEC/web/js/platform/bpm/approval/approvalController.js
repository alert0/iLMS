var App = angular.module('app',['base','ApprovalService']);
App.controller("ApprovalController",['$scope','ApprService',function($scope,ApprService){
	$scope.approval={"type":"1"};
	if($type == 'personal'){
		$scope.approval={"type":"4"};
	}
	if($bpmApprovalItem){
		$scope.approval = parseToJson($bpmApprovalItem);
		if(!$scope.flowArray){
			$scope.flowArray = [];
		}
		if($scope.approval){
			if($scope.approval.typeId){
				setTimeout(function(){
					$('#groupTypeId').combotree('setValue', $scope.approval.typeId);
				},500)
			}
			if($scope.approval.defKey){
				$scope.flowArray.push({"name":$scope.approval.defName,"defKey":$scope.approval.defKey});
			}
		}
	}
	
	$scope.referDefinition = function(){
		var flowArray = $scope.flowArray;
		new BpmDefDialog({single:true,callback:function(aryDef,dialog){	
    		if( $scope.flowArray && $scope.flowArray.length>0){
    			for(var i=0 ;  i< aryDef.length ; i++){
    				var isSame = false; 
    				for(var j =0 ;  j< flowArray.length ; j++){
    					if(flowArray[j].defKey == aryDef[i].defKey){
    						isSame = true;
    						break;
    					}
    				}
    				if(!isSame){
    					flowArray.push(aryDef[i]);
    				}
    			}
    		}else{
    			$scope.flowArray = aryDef;
    		}
    		$scope.$digest();
    	}}).show();
	}
	
	$scope.removeDef = function(index){
		$scope.flowArray.splice(index , 1);
	}
	
	$scope.save = function(){
		var type = $scope.approval.type;
		if(type == 2){
			//流程分类
			var typeId = [];
			$("[name='groupTypeId']").each(function(){
				var me = $(this);
				var value = me.val();
				typeId.push(value);
			});
			if(typeId.length<1){
				$.topCall.warn("请选择流程分类！");
				return ;
			}
			$scope.approval.typeId=typeId.join(",");
			
		}else if(type == 3){
			//流程
			//处理
			var defKey = [];
			var defName = [];
			if(!$scope.flowArray||$scope.flowArray.length<1){
				$.topCall.warn("请选择流程定义！");
				return ;
			}
			var flowObj = $scope.flowArray;
			for(var i = 0 ; i< flowObj.length; i++){
				defKey.push(flowObj[i].defKey);
				defName.push(flowObj[i].name);
			}
			$scope.approval.defKey = defKey.join(",");
			$scope.approval.defName = defName.join(",");
		};
		if(!$scope.approval.expression){
			$.topCall.warn("请输入常用语！");
			return;
		}
		if(/(\<|\>)/.test($scope.approval.expression.trim())) {
			$.topCall.warn("常用语不能包含尖括号 ");
			return;
		}
		//保存数据
		ApprService.saveData({approval : JSON2.stringify($scope.approval)},function(data){
			if(data.result==1){
				var msg = "添加常用语成功！";
				if($scope.approval.type == 4 && $scope.approval.id != null){
					msg = "编辑个人常用语成功！";
				}else if($scope.approval.type == 4 && typeof($scope.approval.id) == "undefined"){
					msg = "添加常用语成功！";
				}else if($scope.approval.type != 4 && $scope.approval.id != null){
					msg = "编辑常用语成功！";
				}
				$.topCall.success(msg,function(result){
					 HT.window.closeEdit(true,'grid');
				});
			}else{
				$.topCall.error(data.message);
			}
		})
	}
}]);