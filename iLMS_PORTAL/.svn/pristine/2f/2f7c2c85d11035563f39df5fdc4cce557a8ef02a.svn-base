
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	var id = $.getParameter("id");
	
	// 初始化值
	$scope.$on("afterLoadEvent",function(ev,data){
		if(!id){
			$scope.data.type = '统计栏目';
			$scope.data.countMode = '1';
		}
	});
	
	var obj={
	}
	//等页面加载完毕后在加载数据字典控件。
	$scope.$watch('$viewContentLoaded', function() {  
		$(".easyui-combotree-me").combotree({
			url:__ctx+'/system/dataDict/getByTypeKeyForComBo?typeKey=INDEX_TOOLS_TYPE',
			onClick:function(node){
				$scope.$apply(function() { 
					$scope.data.type=node.id;
					$scope.data.typeName=node.text;
				});
			},
			onShowPanel:function(){
				$($(".combo-panel").find("ul").get(0)).attr("style","height:180px;overflow-y:auto;");
			}
		});
    }); 
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
	
	
	$scope.showSetParamDialog = function() {
		var url = __ctx + '/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnSetParam';
		$.topCall.dialog({
			src : url,
			base : {
				title : "参数设置",
				width : 820,
				height : 480,
				modal : true,
				resizable : true,
				passConf : {
					dataParam : $scope.data.countParam,
					callBack : function(data){
						$scope.$apply(function() {
							$scope.data.countParam = data;
						});
					}
				}
			}
		});
	}
	
	$scope.selectQuery = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			$scope.$apply(function() {
				if(data.length>0){
					$scope.data.counting = data[0].ALIAS_;
				}
			});
		};

		CustomDialog.openCustomDialog("customQuerySelector", callBack);
	};
	
}]);