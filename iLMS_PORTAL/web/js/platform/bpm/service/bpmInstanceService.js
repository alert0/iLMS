angular.module('instanceApp', ['base','formDirective','BpmService'])
.factory('bpm', ['$rootScope','baseService', function($rootScope,baseService) {
	var instanceId = null,
		Instance = {
					isInstance:true,
					init : function(optionStr){
						var option = parseToJson(optionStr);
						instanceId = option.instanceId;
						taskKey = option.taskKey;
						
						if(instanceId){
							$.topCall.progress();
							baseService.postForm(__ctx + "/flow/instance/getInstFormAndBO",{proInstId:instanceId,taskKey:taskKey}).then(
								function(data,status){
									if(data.hasOwnProperty('form')){
										$.topCall.closeProgress();
										$rootScope.$broadcast('html:update', data);
									}else{
										$.topCall.error("表单不存在！");
										$.topCall.closeProgress();
									}
									
								},function(code){
									$.topCall.error("加载失败！error:"+code);
									$.topCall.closeProgress();
								}		
							);
						}
					},
					isCreateInstance:function(){
						return false;
					},
					getProInstId:function(){
						return instanceId;
					}
				};
	return Instance;
}])
.directive('htInstanceManager', ['bpm','$filter', function(bpm,$filter){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			bpm.init(attrs.htInstanceManager);
			
			scope.$on("html:update",function(event,data){
				scope.form = data.form;
				scope.data = data.data;
				scope.opinionList = data.opinionList;
				scope.permission = parseToJson(data.permission);
			});
		},
		template: '<div ng-show="form.type==\'INNER\'" ht-bind-html="form.formHtml"></div>\
				   <div ng-show="form.type==\'FRAME\'" ht-frame-form ng-model="form"></div>'
	};
}])

/**
 * IFRAME方式加载表单。
 * ng-model：需要绑定表单的地址。
 * <div ng-show="form.type==\'FRAME\'" ht-inner-form ng-model="form"></div>
 */
.directive('htFrameForm', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"A",
		require : '?ngModel', 
		link:function(scope, element, attrs,ngModel){
			if(!ngModel) return;
			
			ngModel.$render = function () {
				if(!ngModel.$modelValue) return ;
				if(ngModel.$modelValue.type!="FRAME") return;
				
				var url=ngModel.$modelValue.formValue;
				url=url.startWith("http")?url :__ctx +url;
				
				var frameObj=$("<iframe id='frmFrame' src="+url +" onload='iframeHeight(this)' style='width:100%;border:none;'></iframe>")
				//将表单加载到系统中
				element.append(frameObj);
			}
		}
	};
}]);