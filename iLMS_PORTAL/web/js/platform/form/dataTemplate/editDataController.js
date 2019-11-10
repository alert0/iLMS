var app = angular.module('app', [ 'formDirective','arrayToolService']);

app.controller('ctrl',['$scope','$rootScope','baseService','ArrayToolService','editDataService',function($scope,$rootScope,baseService,ArrayToolService,editDataService){
	$scope.service = editDataService;
	$scope.ArrayTool = ArrayToolService;
	
	$scope.formKey = $.getParameter("formKey");
	$scope.boAlias = $.getParameter("boAlias");
	$scope.id = $.getParameter("id");
	$scope.action = $.getParameter("action");
	editDataService.init($scope);
	

	$scope.add = function(path){
		var arr = path.split(".");
		if(arr.length<2)alert("subtable path is error!")
		var subTableName = arr[1].replace("sub_","")
		var tempData = $scope.data[arr[0]].initData[subTableName];
		
		if(!tempData)tempData={};
		var ary = eval("$scope.data." + path); 
		if(!angular.isArray(ary)) ary = [];
		
		ary.push(angular.copy(tempData));
		eval("$scope.data." + path+"=ary");
		!$rootScope.$$phase && $rootScope.$digest();
	};
	
	$scope.remove = function(path,index){
		var ary = eval("($scope.data." + path + ")");
		if(ary&&ary.length>0){
			ary.splice(index,1);
		}
	};
	$scope.initSubTableData = function(){
			var initSubTable = [];
			var data = $scope.data;
			$("[type='subGroup'][initdata]").each(function(i,item){
				initSubTable.push($(item).attr("tablename"));
			});
			
			for(var i=0,subTable;subTable=initSubTable[i++];){
				for(var boCode in $scope.data){
					var initData =data[boCode].initData[subTable];
					if(initData &&(!data[boCode]["sub_"+subTable]||data[boCode]["sub_"+subTable].length==0)){
						data[boCode]["sub_"+subTable] = [];
						data[boCode]["sub_"+subTable].push($.extend({},initData));
					}
				}
			}
			!$scope.$$phase&&$scope.$digest();
			
	}
	
	$scope.boSave = function(){
		editDataService.boSave($scope);
	} 
	
	$scope.startFlow = function(){
		$scope.btnDisabled.start = true;
		editDataService.startFlow($scope);
	}
	
	window.setTimeout($scope.initSubTableData,100);
	
	if(window.ngReady){
		window.setTimeout(ngReady,10,$scope);
	}
	
	showResponse = function(responseText){
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.topCall.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
				if(rtn){
					var url=location.href.getNewUrl();
					window.location.href =  location.href.getNewUrl();
				}else{
					$.closeWindow();
				}
			});
		} else {
			$.ligerDialog.error(obj.getMessage(),"提示信息");
		}
	}
	
	
}]);

app.service('editDataService', ['$rootScope','baseService', function($rootScope,baseService) {
    var service = {
    		//获取表单详情
    		init : function(scope){
    			//获取BpmForm的详情
    			baseService.get(__ctx + '/form/dataTemplate/getForm/'+scope.formKey+'/'+scope.boAlias+'.ht?id='+scope.id+'&action='+scope.action).then(
    				function(data){
    					if(data.result){
    						scope.data = data.data; 
    						scope.permission = data.permission;
    						scope.html  =data.form.formHtml;
    						window.setTimeout(scope.initSubTableData,100);
    					}else{
    						$.topCall.error("表单内容为空","",function(){
    							HT.window.closeEdit();
    						});
    					}
    				},function(){}
    			);
    		},
    		boSave : function(scope){
    			if(scope.form.$invalid){
    				$.topCall.warn("表单校验不通过");
    				return;
    			}
    			baseService.post(__ctx + '/form/dataTemplate/boSave/'+scope.boAlias+'.ht',scope.data).then(
    				function(data){
    					if(data.result){
    						
    						$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
    							if(r){
    								window.location.reload();
    							}else{
    								HT.window.closeEdit(true);
    							}
    						});
    					}else{
    						$.topCall.error(data.message);
    					}
    				},function(){}
    			);
    		},
    		startFlow : function(scope){
    			var errorMsg = "";
    			if(!scope.defId){
    				errorMsg = "您还没有绑定流程。";
    			}
    			
    			if(scope.form.$invalid){
    				errorMsg = "表单校验不通过";
    			}
    			if(errorMsg.length){
    				$.topCall.warn(errorMsg);
    				return ;
    			}
    			baseService.postForm(__ctx + '/flow/instance/start',{data:angular.toJson(scope.data, true),formType:"inner",defId:scope.defId}).then(
    				function(data){
    					scope.btnDisabled.start = false; 
    					if(data.result){
    						$.topCall.success(data.message,function(){
    							HT.window.closeEdit(true);
    						});
    					}else{
    						$.topCall.error(data.message);
    					}
    				},function(){}
    			);
    		
    		}
        }
    return service;
}]);

