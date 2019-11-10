angular.module('MessageTypeService', ['base'])
.service('MsgType', ['$rootScope','baseService',function($rootScope,baseService) {
    var service = {
		getMsgType : function(callback){
			baseService.post(__ctx +'/innermsg/sysMessage/getMessageType.ht',{}).then(function(data){
				if(callback){
    				callback(data);
    			 }
			});
		}
    }    		
    return service;
}]);
