angular.module('BpmFormService', ['base'])
.service('BpmForm', ['$rootScope','baseService', function($rootScope,baseService) {
    var service = {
    		//获取表单详情
    		detail : function(bpmForm,callback){
    			if(!bpmForm||!bpmForm.id){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    			//获取BpmForm的详情
    			
    			baseService.postForm(__ctx + '/form/formDef/get',{id:bpmForm.id}).then(
    				function(data){
    					if(callback){
    	    				callback(data);
    	    			 }
    				},function(){}
    			);
    		},
    		save : function(formStr,callback){
    			if(!formStr){
    				if(callback){
    					callback();
    				}
    				return;
    			}    
    			baseService.postForm(__ctx+'/form/formDef/save',{form:formStr}).then(
        				function(data){
        					if(callback){
        	    				callback(data);
        	    			 }
        				},function(){}
        			);
    			
    		},
    		//获取表单所引用BO的json数据
    		boJson : function(bpmForm,callback){
    			if(!bpmForm||!bpmForm.id){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    		}
        }
    return service;
}]);
