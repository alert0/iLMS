
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.data = {};
	var obj={
	}
	
	/**
	 * 页面进入时加载数据。
	 */
	$scope.load=function(id){
		//$scope.data.ownerNameJson = [];
		var url="getJson";
		if(id || id.length>0){
			url+="?id=" + id;
		}
		baseService.get(url).then(function(result){
			if (result) {
				result.ownerNameJson=angular.fromJson(result.ownerNameJson);
				$scope.data=result;
			} else {
				$scope.data= {};
			}
		})
	}
	/**
	 * 用户选择
	 */
	$scope.userDialog = function() {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			$scope.data.userList=data;
			$scope.$apply(function() {
				$scope.data.pendingUserName = "";
				$scope.data.pendingUserId = "";
				$(data).each(function(index) {
					if(index==0){
						$scope.data.pendingUserId += this.id;
						$scope.data.pendingUserName+= this.name;
					}else{
						$scope.data.pendingUserId +=','+ this.id;
						$scope.data.pendingUserName+=','+ this.name;
					}
				});
			});
		};
		var conf = {
			initData : $scope.data.userList,
			selectNum : ""
		};
		CustomDialog.openCustomDialog("userSelector", callBack, conf);
	};
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	// 保存成功
	$scope.$on("afterSaveEvent",function(ev,data){
		if(data.r){
			window.location.reload();
		}
		else{
			HT.window.refreshParentGrid();
			HT.window.closeEdit(true,'grid');
		}
	});
	
	/**
	 * 设置设置权限。
	 * ownerNameJson 格式。
	 * [{type:"everyone",title:"所有人"},{type:"user",title:"用户",id:"1,2",name:"ray,tom"}]
	 */
	$scope.setAuth=function(){ 
		RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
			$scope.$apply(function(){
				var tmpAry=[];
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					if(obj.id){
						var tmp={"type":obj.type,"title":obj.title, "id":obj.id,"name":obj.name};
						tmpAry.push(tmp);
					}
					else{
						var tmp={"type":obj.type,"title":obj.title};
						tmpAry.push(tmp);
					}
				}
				$scope.data.ownerNameJson=tmpAry;
			});
			dialog.dialog('close');
		},$scope.data.ownerNameJson);
	}
	/**
	 * 保存授权规则。
	 */
	$scope.save=function(){
		if($scope.bpmDefAuthorizeForm.$invalid) {
			 $.topCall.warn("表单验证不通过,请检查表单!");
			return;
		}
		if($scope.data.ispending=='0') {
			$scope.data.pendingUserId='';
			$scope.data.pendingUserName='';
		}else{
			$scope.data.ispending='1';
		}
		var obj=handSave($scope.data);
		var url=__ctx +"/business/xqmessagetype/messageType/save";
		var defer=baseService.post(url,obj);
		
		defer.then(function(data){
			if (data.result==1) {
			    $.topCall.success("保存授权成功!",  function(r){
						HT.window.closeEdit(true,'grid');
					},"提示信息"
			    );
			} else {
			    $.topCall.error(data.message);
			}
		})
	};
	
	/**
	 * 保存时处理json。
	 */
	function handSave(data){
		var obj =angular.copy(data);
		if(data.ownerNameJson != undefined){
			obj.ownerNameJson=angular.toJson(data.ownerNameJson);
			for(var i=0;i<obj.ownerNameJson.length;i++){
				var tmp=obj.ownerNameJson[i];
				tmp.right=angular.toJson(tmp.right)
			}
		}
		return obj;
	}
	
}]);