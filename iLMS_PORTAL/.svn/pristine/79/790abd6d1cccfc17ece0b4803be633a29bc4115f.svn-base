angular.module('TemplateService', ['base'])
.service('Template', ['$rootScope','baseService',function($rootScope,BaseService) {
    var service = {
    		detail : function(Template,callback){
    			if(!Template ||!Template.id ){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    			BaseService.postForm(__ctx +'/system/sysMsgTemplate/getById',{id:Template.id}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			})
    		},
    		
    		saveData : function(params,callback){
    			BaseService.postForm(__ctx +'/system/sysMsgTemplate/save',params).then(function(data){
    				if(callback){
    					callback(data);
    				}
    			});
    		}
    }    		
    return service;
}]);
