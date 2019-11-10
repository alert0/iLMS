var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.baseFlowInfo = {};
	
	var defKey = $.getParameter("defKey");
	
	var obj={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	$scope.$on("afterLoadEvent",function(){
		if(defKey){
			$scope.data.defKey = defKey;
		}
		if($scope.data.boFormData){
			$scope.data.boFormData =  angular.fromJson($scope.data.boFormData); 
		}
		
		if( $scope.data.actionType ){
			$scope.data.actionType =  angular.fromJson($scope.data.actionType); 
		}else{
			$scope.data.actionType = {};
		}
		
		if( $scope.data.bpmDebugger ){
			$scope.data.bpmDebugger =  angular.fromJson($scope.data.bpmDebugger); 
		}else{
			$scope.data.bpmDebugger = {};
		}
		
		if( $scope.data.startor ){
			$scope.data.startor =  angular.fromJson($scope.data.startor); 
		}else{
			$scope.data.startor = [];
		}
		
		baseService.get("getBaseInfo?defKey=" + $scope.data.defKey).then(function(data){
			if(data.state){
				$scope.baseFlowInfo = data.value;
			}else{
				top.layer.alert(result.message, {icon: 2}); 
			}
		},function(data){
			top.layer.alert(result.message, {icon: 2}); 
		});
	});
	
	$scope.save = function(){
		
		if (!$scope.form.$valid){
			
			top.layer.alert("表单校验失败", {icon: 2}); 
			
			return;
		}
		
		baseService.post("save",$scope.data).then(function(result){
			if(result.result==1){
				top.layer.confirm(result.message + " 是否继续操作？",function(idx){
					top.layer.close(idx);
					window.location.reload();
				},function(){
					$scope.closeFrame();
				});
			}else{
				top.layer.alert(result.message, {icon: 2}); 
			}
			
		},function(result){
			top.layer.alert(result.message, {icon: 2}); 
		});
		
		
	}
	
	$scope.showUserDialog = function(){
		var conf = {
				isSingle:true,
				callBack:callBack
		};
		UserDialog(conf);
	}
	
	function callBack(data){
		if(data[0] && data[0].account ){
			$scope.data.startorAccount  =  data[0].account;
			$scope.data.startorFullName =  data[0].name;
			AngularUtil.setData($scope);
		}
	}
	
	// 关闭窗口
	$scope.closeFrame = function(){
		var index = top.layer.getFrameIndex(window.name);  
		top.layer.close(index);  
	}
	
	// 设置表单数据
	$scope.settingBoFormData = function(){
		if(!$scope.baseFlowInfo.formId){
			top.layer.alert("没有表单", {icon: 2}); 
			return;
		}
		var url = __ctx + "/form/form/preview?id=" + $scope.baseFlowInfo.formId ;
		var index = top.layer.open({
			  type: 2,
			  title: "设置表单数据",
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:8888,
			  content: [url, 'yes'],
			  btn: ['确定', '取消'],
			  success:function(layero,index){
				  var iframeWin = top[layero.find('iframe')[0]['name']]; 
				  if($scope.data.boFormData){
					  iframeWin.setFormData($scope.data.boFormData);
				  }
			  },
		      yes: function(index, layero){
		    	   var iframeWin = top[layero.find('iframe')[0]['name']]; 
		    	   var data = iframeWin.getFormData();
		    	   $scope.data.boFormData = data;
		    	   AngularUtil.setData($scope);
		    	   top.layer.close(index);  
		      },
		      btn2: function(index, layero){
		      }
			});
	}
	
	// 添加审批动作
	$scope.addItem = function(type,defKey){
		if(type=="actionType"){
			var obj = {"nodeId":"","actionName":"agree","count":1};
			if($scope.data.actionType[defKey]){
				$scope.data.actionType[defKey].push(obj);
			}else{
				$scope.data.actionType[defKey]=[];
				$scope.data.actionType[defKey].push(obj);
			}
		}
		
		if(type=="bpmDebugger"){
			if($scope.data.bpmDebugger[defKey]){
				$scope.data.bpmDebugger[defKey].push("");
			}else{
				$scope.data.bpmDebugger[defKey]=[""];
			}
		}
		
	}
	// 选择节点
	$scope.selectNodeId = function(type,defKey,key,idx){
		
		if(type=="actionType"){
			
			var __arr = $.cloneObject($scope.data.actionType[defKey]);
			__arr.splice(idx,1);
			if( isInArrayByKey(__arr,key,"nodeId")){
				top.layer.alert("已经设置了该节点,请重新选择,请不要重复设置", {icon: 2}); 
				key.nodeId = "";
			}
		}
		
		if(type=="bpmDebugger"){
			var __arr = $.cloneObject($scope.data.bpmDebugger[defKey]);
			__arr.splice(idx,1);
			if(isInArray(__arr, key)){
				top.layer.alert("已经设置了该节点,请重新选择,请不要重复设置", {icon: 2}); 
				key = "";
				return;
			}
			$scope.data.bpmDebugger[defKey].splice(idx,1);
			$scope.data.bpmDebugger[defKey].insert(idx,key);
		}
	}
	
	// 删除
	$scope.delItem = function(type,defKey,key){
		if(type == "actionType"){
			delete $scope.data.actionType[defKey][key];
		}
		
		if(type=="bpmDebugger"){
			$scope.data.bpmDebugger[defKey].remove(key);
		}
	}
	
	// 设置用户组作为发起人
	$scope.setStartor = function(){
		RightsUtil.openGroupDialog("defaultObjectRightType",function(data, dialog){
			$scope.data.startor = data;
			dialog.dialog('close');
		},$scope.data.startor);
	}
		
	
}]);