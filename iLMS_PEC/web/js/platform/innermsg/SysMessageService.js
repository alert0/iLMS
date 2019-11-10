angular.module('SysMessageService', ['baseServices'])
.service('SysMessage', ['$rootScope','BaseService',function($rootScope,BaseService) {
    var service = {
    		
    		saveData : function(params,callback){
    			BaseService.post(__ctx +'/platform/innermsg/sysMessage/save.ht',params,function(data){
    				if(callback){
    					callback(data);
    				}
    			});
    		}
    }    		
    return service;
}]);
