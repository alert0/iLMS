angular.module('IdentityService', ['base'])
.service('IdenService', ['baseService',function(BaseService) {
	var service = {
    		detail : function(identity,callback){
    			if(!identity ||!identity.id ){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    			var defer=BaseService.postForm(__ctx +'/system/identity/getById',{id:identity.id});
    			defer.then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		
    		saveData : function(params,callback){
    			var defer=BaseService.post(__ctx +'/system/identity/save',params);
    			defer.then(function(data){
    				if(callback){
    					callback(data);
    				}
    			});
    		}
    }    		
    return service;
}]);
