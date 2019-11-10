
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	var obj={
	}
	
	if(!$scope.data){
		$scope.data = {orgId:orgId,isAdd:true,demId:demId};
	}
	
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
	
	$scope.$on('$viewContentLoaded', function() {  
	    alert('1');  
	});  
	
	/**
	 * 用户选择
	 */
	$scope.userDialog = function() {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			$scope.$apply(function() {
				$scope.data.userName = "";
				$scope.data.userId = "";
				$(data).each(function() {
					$scope.data.userId = this.id;
					$scope.data.userName += this.name;
				});
			});
		};
		var conf = {
//			initData : $scope.user.list,
			selectNum : "1"// 单选
		};
		CustomDialog.openCustomDialog("userSelector", callBack, conf);
	};
	
	
	$scope.save = function(){
		if (!$scope.form.$valid) return;
		$scope.data.orgId = orgId;
		$scope.data.demId = demId;
		getData();
		var rtn = baseService.post("save", $scope.data);
		rtn.then(function(data) {
			if (data.result == 1){
				$.topCall.toast("提示信息",$scope.data.isAdd? "添加成功！":"保存成功！");
				window.parent.location.reload();
				if($scope.data.isAdd){
					$scope.data = {parentOrgName:parentOrgName,parentId:parentId,isAdd:true,demId:demId};
				}
			}else{
				var msg = "用户【"+$scope.data.userName+"】已经是组织管理员，不能重复添加"
				$.topCall.error(msg, data.cause);
			}
		})
	};
	
	function getData(){
		
		//组织权限
		var orgPermsInput = $("#orgPerms").find("input[name='orgPerm']:checkbox:checked");
		var orgPerms = "";
		for(var i=0;i<orgPermsInput.length;i++){
			if((i+1) == orgPermsInput.length){
				orgPerms += $($(orgPermsInput).get(i)).val();
			}else{
				orgPerms += $($(orgPermsInput).get(i)).val() + ",";
			}
		}
		$scope.data.orgPerms = orgPerms;
		
		//用户权限
		var userPermsInput = $("#userPerms").find("input[name='userPerm']:checkbox:checked");
		var userPerms = "";
		for(var i=0;i<userPermsInput.length;i++){
			if((i+1) == userPermsInput.length){
				userPerms += $($(userPermsInput).get(i)).val();
			}else{
				userPerms += $($(userPermsInput).get(i)).val() + ",";
			}
		}
		$scope.data.userPerms = userPerms;
		
		//岗位权限
		var posPermsInput = $("#posPerms").find("input[name='posPerm']:checkbox:checked");
		var posPerms = "";
		for(var i=0;i<posPermsInput.length;i++){
			if((i+1) == posPermsInput.length){
				posPerms += $($(posPermsInput).get(i)).val();
			}else{
				posPerms += $($(posPermsInput).get(i)).val() + ",";
			}
		}
		$scope.data.posPerms = posPerms;
		
		//分级管理权限
		var orgauthPermsInput = $("#orgauthPerms").find("input[name='orgAuthPerm']:checkbox:checked");
		var orgauthPerms = "";
		for(var i=0;i<orgauthPermsInput.length;i++){
			if((i+1) == orgauthPermsInput.length){
				orgauthPerms += $($(orgauthPermsInput).get(i)).val();
			}else{
				orgauthPerms += $($(orgauthPermsInput).get(i)).val() + ",";
			}
		}
		$scope.data.orgauthPerms = orgauthPerms;
		//布局管理权限
		var layoutPermsInput = $("#layoutPerms").find("input[name='layoutPerms']:checkbox:checked");
		var layoutPerms = "";
		for(var i=0;i<layoutPermsInput.length;i++){
			if((i+1) == layoutPermsInput.length){
				layoutPerms += $($(layoutPermsInput).get(i)).val();
			}else{
				layoutPerms += $($(layoutPermsInput).get(i)).val() + ",";
			}
		}
		$scope.data.layoutPerms = layoutPerms;
	}
	
	$scope.isSelected = function(perms,item){
		var data = $scope.data;
		if(perms){
			if(perms.indexOf(item) > -1){
				return true;
			}else{
				return false;
			}
		}
	}
	
}]);