
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	var obj={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
    $.ajaxSetup({
        async : false
    })

    $scope.$on("beforeSaveEvent",function(ev,data){
        var  name=$scope.data.name
        var url ="queryName";
        $.post(url,{name:name},function(responseText){
            var resultMessage = new com.hotent.form.ResultMessage(responseText);
            if(!resultMessage.isSuccess()){
                data.pass = false;
                $.topCall.warn(resultMessage.getMessage());
            }
        });

    });
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
	
	// 保存前 base64编码
	$scope.$on("beforeSaveEvent",function(ev,data){
		$scope.data.templateHtml = $.base64.encode($scope.data.templateHtml_temp,"utf-8");
	});
	
	
	// base64解码
	$scope.$on("afterLoadEvent",function(ev,data){
		data.templateHtml_temp = $.base64.decode(data.templateHtml,"utf-8");
	});
	
}]);