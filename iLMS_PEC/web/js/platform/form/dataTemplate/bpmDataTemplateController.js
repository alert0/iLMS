var bpmDataTemplateApp = angular.module('app', [ 'formDirective','arrayToolService','DataRightsApp' ]);

bpmDataTemplateApp.controller('ctrl',['$scope','baseService','dataRightsService','$timeout','ArrayToolService',function($scope,BaseService,dataRightsService,$timeout,ArrayToolService){
	var service = dataRightsService;
	$scope.service = service;
	$scope.ArrayTool = ArrayToolService;
	$scope.btnDisable = {};
	
	if(status==0){
		return;
	}
	service.init($scope);
	
	$scope.filterUrl = __ctx + "/form/dataTemplate/bpmDataTemplateFilterDialog?tableId=";
	$scope.save = function(){
		if ($scope._validForm()) {
			$scope.btnDisable.save = true;
			service.customFormSubmit(showResponse);
		}
	}
	
	showResponse = function(responseText){
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.topCall.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
				if(rtn){
					var url=location.href.getNewUrl();
					window.location.href =  location.href.getNewUrl();
				}else{
					HT.window.refreshParentGrid();
					HT.window.closeEdit();
				}
			});
		} else {
			$.topCall.error(obj.getMessage());
		}
	}
	
	$scope._validForm = function (){
		var form=$('#dataRightsForm');
		if($scope.dataRightsForm.$invalid) {
			$.topCall.error("表单校验不通过，请检查！");
			return false;
		}
		//判断排序字段太多报错问题
		if($scope.sortFields&&$scope.sortFields.length>3){
			$.topCall.error("排序字段不能设置超过3个，请检查！");
			//$scope.tab.selectTabItem("sortSetting");
			return false;
		}
		//判断管理字段
		if(service.manageFieldValid($scope.manageFields)){
			$.topCall.error("功能按钮出现重复的类型，请检查！");
			//$scope.tab.selectTabItem("manageSetting");
			return false;
		}
		return true;
	}
	//预览
	$scope.preview = function (){
		var alias = $scope.dataRightsJson.formKey;
		if($.isEmpty(alias)){
			$.topCall.error("请设置完信息保存后预览!");
			return ;
		}
		var url=__ctx+ "/form/dataTemplate/dataList_"+ alias+".ht";
		url=url.getNewUrl();
		$.openFullWindow(url);
	}
	//编辑模板
	$scope.editTemplate = function (){
		var id = $scope.dataRightsJson.id;
		if($.isEmpty(id)){
			$.topCall.error("请设置完信息保存后编辑模板!");
			return ;
		}
		var url=__ctx+ "/form/dataTemplate/editTemplate?id="+id;
		url=url.getNewUrl();
		$.openFullWindow(url);
	}
	//添加菜单
	$scope.addToResource = function (){
		var id = "1";
		var url = __ctx +"/base/base/sysResource/sysResourceList?systemId=" + id;
		HT.window.openEdit(url, "资源管理", "", 'grid', 500, 500, null, null, id, true);
	}
	
	/**
	* 选择流程
	*/
	$scope.selectFlow = function (){
		var conf = {
		param:{DESC_:$scope.dataRightsJson.boDefAlias}
		};
		
		CustomDialog.openCustomDialog('bpmDefinitionByBOCodeSelector',function(data,dialog){

		    //本对话框的返回格式:data:[{"DEF_ID_":"DEF_ID_0","NAME_":"NAME_0"},{"DEF_ID_":"DEF_ID_0","NAME_":"NAME_0"}];这里两个数据

		    //注意：当单选的也是jsonArray，可以data[0]得到结果，然后data[0].name或者data[0].id
			
			$scope.dataRightsJson.defId = data[0].defId;
			$scope.$apply(function(){
				$scope.dataRightsJson.subject = data[0].name;
			});

		    dialog.dialog('close');//关闭弹出框

		},conf);
		
		/*CombinateDialog.open({alias:"bpmDefinitionCombinateSelector",callBack:function(data,dialog){
			$scope.dataRightsJson.defId = data[0].defId;
			$scope.$apply(function(){
				$scope.dataRightsJson.subject = data[0].name;
			});
			dialog.dialog('close');
		}});*/
		
	};
	$scope.cancel = function (){
		$scope.dataRightsJson.defId = "";
		$scope.dataRightsJson.subject = "";
	}
}]);
