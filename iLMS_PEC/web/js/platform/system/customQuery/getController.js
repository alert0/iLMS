var app = angular.module('app',['base','CustomQueryService']);
app.controller("Controller",['$scope','baseService','CustomQuery',function($scope,BaseService,CustomQuery){
	$scope.querydata = {};
    CustomQuery.detail({id:id},function(data){
    	$scope.prop=data;
    	if(!data.conditionfield)data.conditionfield="[]";
    	$scope.prop.conditionfield=JSON.parse(data.conditionfield);
    	for(var i in $scope.prop.conditionfield){
    		var c = $scope.prop.conditionfield[i];
    		if(c.defaultType!='1'){
    			continue;
    		}
    		c.defaultValue='';//置空
    		$scope.querydata[c.field] = "";
    	}
	});
    
    $scope.search=function(){
    	var querydata = [];
    	//获取参数输入
    	for(var i in $scope.prop.conditionfield){
    		var column = $scope.prop.conditionfield[i];

    		if(column.defaultType!='1'){
    			continue;
    		}
    		var param = {};
    		param.key=column.field;
    		param.value="";
    		param.value=column.defaultValue;
    		if(column.condition=='BETWEEN'&&column.endDate!=null){
    			param.value+="|"+column.endDate;
    		}
    		querydata.push(param);
    	}
    	
		var params = {
				querydata:JSON.stringify(querydata),
				alias:$scope.prop.alias,
				page:1
		};
		CustomQuery.search(params,function(data){
			$scope.page=data;
		});
	}
    
    $scope.show=function(msg){
		alert(msg);
	}
}]);