angular.module('ApprovalService', ['base'])
.service('ApprService', ['baseService',function(baseService) {
    var service = {
    		saveData : function(params,callback){
    			var defer=baseService.postForm(__ctx +'/flow/approvalItem/save',params)
    			defer.then(function(data){
    				if(callback){
    					callback(data);
    				}
    			});
    		}
    }    		
    return service;
}]);
