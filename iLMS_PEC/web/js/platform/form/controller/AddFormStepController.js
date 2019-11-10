
var formApp = angular.module('formApp', ['base','formDirective']);

formApp.controller('addStepOneCtrl',['$scope','baseService',function($scope,baseService){
	var typeId = window.passConf?window.passConf.formTypeId:"";
	$scope.form = {typeId:typeId};
	
	//如果是快捷方式创建
	if(parent.boDefList){
		var data = parent.boDefList;
		$scope.form.bos = [];
		for(var i = 0 ;i < data.length; i++){
			data[i].desc = data[i].description;
			data[i].name = data[i].description;
			data[i].supportDB = data[i].supportDb;
			$scope.form.bos.push(data[i]);
		}
		$scope.tempBoSelect = $scope.form.bos;
	}
	
	//等页面加载完毕后在加载数据字典控件。
	$scope.$watch('$viewContentLoaded', function() {  
		$(".easyui-combotree-me").combotree({
			url:__ctx+'/system/sysType/getByGroupKey?groupKey=FORM_TYPE',
			onClick:function(node){
				$scope.$apply(function() { 
					$scope.form.typeId=node.id;
					$scope.form.typeName=node.text;
				});
			},
			onShowPanel:function(){//easyui中combotree继承combo和tree，其中onShowPanel为combo的事件，当下拉面板显示的时候触发
				$($(".combo-panel").find("ul").get(0)).attr("style","height:180px;overflow-y:auto;");
			}
		});
    }); 
	
	
	
	$scope.chooseBo =function(){
		//获取要初始化选择页面的数据
		var initData=$scope.tempBoSelect||[];
		
		var callBack=function(data,dialog){
			$scope.form.bos = [];
			for(var i = 0 ;i < data.length; i++){
				$scope.form.bos.push({
					id:data[i].id,
					desc : data[i].name ||data[i].alias,
					supportDB : data[i].supportDB
				});
			}
			$scope.tempBoSelect = data; 
			$scope.$digest();
			
		    dialog.dialog('close');//关闭弹出框
		};
		CustomDialog.openCustomDialog('boDefSelector',callBack,{
			initData:initData,
			selectNum:-1  //多选
		});
	};
	$scope.nextStep = function(){
		var typeId =$scope.form.typeId;
		var name = $scope.form.name;
		var formKey = $scope.form.key;
		var bos = $scope.form.bos;
		if(!typeId){
			 $.topCall.error("请选择分类");
			 return ;
		}else if(!name){
			 $.topCall.error("请输入表单标题");
			 return ;
		}else if(!formKey){
			$.topCall.error("请输入Key");
			 return ;
		}else if($('#key').next("label.wrong")[0]){
			$.topCall.error("表单Key已经存在");
			 return ;
		}else if(!bos){
			$.topCall.error("请选择BO对象");
			 return ;
		}
		var form = $('form');
		$("input[name='bos']").val(JSON.stringify(bos));
		return form;
	};
}]);