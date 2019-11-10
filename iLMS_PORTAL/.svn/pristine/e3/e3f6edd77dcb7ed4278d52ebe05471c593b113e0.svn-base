
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.btnDisable = {};
	$scope.btnDisable.save = false;
	if(id){
		$("#account").attr("readonly","readonly");
	}else{
		$scope.isShowPhoto = 'init';
		$scope.data={status:1};
	}
	
	/**
	 * 数据初始化。
	 */
	$scope.init=function(){
		if(!$scope.data){
			$scope.data={status:1};
		}
	};
	
	//上传照片
	$scope.addPic = function(){
		UploadDialog({max:1,size:10,callback:picCallBack});
	};
	//删除照片
	$scope.delPic = function(){
		$scope.data.photo ="";
		$("#personPic").attr("ng-src",__ctx+"/css/image/touxian.jpeg");
	};
	function picCallBack(array){
		if(!array && array!="") return;
		var fileId=array[0].id,
			fileName=array[0].name;
		var path= __ctx + "/system/file/getFileById?fileId=" + fileId;
		if(/[\s\S]+.(png|gif|jpg)/gi.test(fileName)){
			$scope.data.photo = "/system/file/getFileById?fileId=" + fileId;
			$("#personPic").attr("src",path);
		}
			
		else
			$.topCall.error("请选择*png,*gif,*jpg类型图片!");
				
	};
	
	$scope.$on("beforeSaveEvent",function(event,data){
		$scope.btnDisable.save = true;
		$scope.data.groupId=orgId;
	});
	
	$scope.$on("afterLoadEvent",function(event,data){
		if(data && data.photo!='' && data.photo != null){
			$scope.isShowPhoto = 'custom';
		}else{
			$scope.isShowPhoto = 'init';
		}
	});
	
	$scope.$on("afterSaveEvent",function(event,data){
		$scope.btnDisable.save = false;
		if(!data.r){
			HT.window.closeEdit(true);
		}
		window.location.reload();
	});
	
	$scope.$watch("data",function(oldData,newData){
		if($scope.btnDisable.save){
			$scope.btnDisable.save = false;
		}
	},true);
	
	
	
	
}]);