
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.data = {};
	$scope.data.layoutId = id;
	if ($scope.data.logo==null || $scope.data.logo=='') {
		$scope.data.logo = '/css/image/logo.svg';
	}
	$scope.$on("afterSaveEvent",function(event,data){
		window.location.reload();
	});
	
	//上传照片
	$scope.addPic = function(){
		UploadDialog({max:1,size:10,callback:picCallBack});
	};
	//删除照片
	$scope.delPic = function(){
		$scope.data.logo ="";
		$("#logoImg").attr("src","${ctx}/css/image/logo.svg");
	};
	function picCallBack(array){
		if(!array && array!="") return;
		var fileId=array[0].id,
			fileName=array[0].name;
		var path= __ctx + "/system/file/getFileById?fileId=" + fileId;
		if(/[\s\S]+.(png|gif|jpg|svg)/gi.test(fileName)){
			$scope.data.logo = "/system/file/getFileById?fileId=" + fileId;
			$("#logoImg").attr("src",path);
		}
			
		else
			$.topCall.error("请选择*png,*gif,*jpg,*svg类型图片!");
				
	};
	
}]);