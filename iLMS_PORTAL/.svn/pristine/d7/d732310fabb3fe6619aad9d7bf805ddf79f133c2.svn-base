angular.module('MessageReplyService', ['baseServices'])
.service('MsgReply', ['$rootScope','BaseService',function($rootScope,BaseService) {
    var service = {
    	getMsgData : function(param,callback){
			if(!param ||!param.messageId ){
				if(callback){
					callback();
				}
				return;
			}
			
			BaseService.post(__ctx +'/platform/innermsg/messageReply/getByMessageId.ht',{messageId:param.messageId},function(data){
				if(callback){
					callback(data);
				 }
			});
		},
		
		save : function(){
			
		}
    }    		
    return service;
}]);
