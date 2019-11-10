angular.module('ConditionScriptService', ['base'])
.service('ConditionScript', ['$rootScope','baseService', '$http',function($rootScope,baseService,$http) {
    var service = {    		
    		getById : function(id,callback){
    			//根据id获取的详情
    			baseService.postForm(__ctx +'/system/conditionScript/getObject',{id:id}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		}
        }
    return service;
}]);
