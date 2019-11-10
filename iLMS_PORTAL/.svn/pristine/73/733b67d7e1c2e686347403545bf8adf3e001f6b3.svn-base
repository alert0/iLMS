(function (){
    var paths  = [
                  "/js/platform/system/customDialog/customDialogUtil.js",
                  "/js/platform/system/customQuery/customQueryService.js"
              ];
    for (var i=0,pi;pi = paths[i++];) {
        document.write('<script type="text/javascript" src="'+ __ctx + pi +'"></script>');
    }
})();
angular.module('ComplexService', ['baseServices'])
.service('ComplexDialog',['$q','BaseService',function($q,BaseService){
	var _complexDic = {};
	//获取选择器相关配置
	var _getSelector = function(alias){
		var deferred = $q.defer();
		if(!_complexDic['selector']){
			_complexDic['selector'] = {};
		}
		var selector = _complexDic['selector'];
		if(!selector[alias]){
			var url = __ctx +'/form/selectorDef/getAll';
			BaseService.get(url,function(data,status){
				angular.forEach(data,function(item){
					selector[item.alias] = item;
				});
				if(selector[alias]){
					deferred.resolve(selector[alias]);
				}
				else{
					deferred.reject();
				}
			});
		}
		else{
			deferred.resolve(selector[alias]);
		}
		return deferred.promise;
	}
	//格式化选择器的已选数据参数格式
	var _formatSelectorData = function(data,json,conf){
		if($.isEmpty(data)||!data.id){
			return;
		}
		var returnData = [];
		for(var i = 0 ;i<data.id.length;i++){
			if(json.alias=="userSelector"){
				returnData.push({
					userId:data.id[i],
					fullname:data.name[i]
				});
			}else{
				returnData.push({
					groupId:data.id[i],
					name:data.name[i]
				});
			}
		}
		conf[json.confKey] = returnData;
	}
	
	var getDimKey = function(data){
		var dimKey;
		if(data.alias == "posSelector"){
			dimKey = "position";
		}
		if(data.alias == "orgSelector"){
			dimKey = "org";
		}
		return dimKey;
	}
	
	this.choose = function(json,d,callback){
		switch(json.type){
			case "selector":
				_getSelector(json.alias).then(function(data){
					var selectorMethod = data.method;
					var	selectorFn;
					
					selectorMethod&&(selectorFn=eval("("+ selectorMethod + ")"));
					if(typeof selectorFn=="function"){
						var conf = {};
						//区分组选择器类型，org，position,area...
						var dimKey =  getDimKey(data);
						
						if(d){
							_formatSelectorData(d, data, conf);
						}
						
						var params = {passConf:conf,dimKey:dimKey,isSingle:json.isSingle,title:data.name,type:'1',callback:function(ids,names,records,dialog){
							if(callback){
								var ary = [];
								for(var i=0;i<ids.length;i++){
									ary.push({id:ids[i],name:names[i]});
								}
								callback(ary);
								
								dialog.dialog('close');
							}
							
						}};
						selectorFn(params).show();
					}
					else{
						$.topCall.error("打开选择器对话框失败");
					}
				},function(data,status){
					$.topCall.error("打开选择器对话框失败");
				});
				break;
			case "dialog":
				CustomDialog.openCustomDialog(json.alias,function(data,dialog){
					if(callback){
						callback(data);
					}
					dialog.dialog('close');
				});
				break;
		}
	}
}])
.directive('htComplex', ['ComplexDialog', function(ComplexDialog) {
	return {
		restrict: 'EAC',
		scope:{
			htComplex:'='
		},
		link: function(scope, element, attrs) {
			//打开选择器对话框
			scope.choose = function(){
				ComplexDialog.choose(scope.setting,scope,function(args){
					for(var k in scope.setting.bind){
						var v = [];
						angular.forEach(args,function(item){
							v.push(item[k]);
						});
						scope[k] = v;//TODO 自定义对话框绑定子表字段的情况
					}
					scope.updateValue(true);
					element.validMe();
				});
			}
			
			//移除某项
			scope.remove = function(index){
				for(var k in scope.setting.bind){
					scope[k]&&scope[k].splice(index,1);
				}
				scope.updateValue(false);
			}
			
			scope.updateValue = function(digest){
				//更新数据到父作用域
				if("ng"==scope.setting.bindType){
					for(var k in scope.setting.bind){
						var v = scope.setting.bind[k];
						var key=v +"" +k;
						try
						{
						eval('scope.$parent.' + v + '="' + scope[k].join(',') +'"');
						
						}catch(err){
						    scope.$parent[key]= + scope[k].join(',') ;
						}

					}
					digest&&scope.$parent.$digest();
				}
				//更新数据到对象元素
				else if("jq"==scope.setting.bindType){
					for(var k in scope.setting.bind){
						var v = scope.setting.bind[k];
						scope.jqObj[k].val(scope[k].join(','));
					}
					digest&&scope.$digest();
				}
			}
			//对值进行双向绑定
        	/*$scope.$watch('htComplex',  function(newValue, oldValue) {
        		if(newValue!=oldValue){
        			$scope.initData();
        			$scope.render();
        		}
        	});*/
			var setting = {
					type:'selector',	//selector:选择器，对应控件组合定义；dialog：自定义对话框。	
					alias:'userSelector',
					single:false,
					showCurrent:false,
					bindType:'ng',	//ng:ng-model模式，jq:jquery模式
					bind:{}
			};
			
			if(scope.htComplex&&$.extend(true,setting,scope.htComplex)){
				scope.setting = setting;
				//初始化赋值
				if("ng"==scope.setting.bindType){
					for(var k in scope.setting.bind){
						var v = scope.setting.bind[k];
						element.data(v,k);
						scope.$parent.$watch(v,function(newVal,oldVal){
							if(typeof newVal!='string')return;
							var curK = element.data(this.exp);
				    		if (newVal !== oldVal || !element.data(curK)) {
				    			element.data(curK, true);
								scope[curK] = newVal.split(',');
								!newVal&&(scope[curK]=[]);
							}
						},false);
					}
				}
				else if("jq"==scope.setting.bindType){
					scope.jqObj = {};
					for(var k in scope.setting.bind){
						var v = scope.setting.bind[k];
						scope.jqObj[k] = $(v);
						if(scope.jqObj[k]&&scope.jqObj[k].val()){
							var jqVal = scope.jqObj[k].val()
							if(/,/.test(jqVal)){
								scope[k] = jqVal.split(',');
							}else{
								scope[k] = new Array(jqVal.toString());
							}
						}
					}
				}
			}
		},
		template: '<span><div class="ht-input" ng-if="setting.type==\'selector\'"><span class="span-user owner-span" ng-repeat="item in name">{{item}}'
				 +'<a class="btn btn-xs fa-remove" style="margin-bottom:4px;" title="移除该项" ng-click="remove($index)"></a></span>'
				 +'</div><a class="btn btn-sm btn-primary fa-search" ng-click="choose()">选择</a></span>',
        replace: true
	};
}])
.directive('htQuery', ['CustomQuery', function(CustomQuery) {
	return {
		restrict: 'EAC',
		scope:{
			htQuery:'='
		},
		link: function(scope, element, attrs) {
			scope.loadData = function(){
				CustomQuery.get(scope.htQuery.alias)
						   .then(function(data){
							   return data;
						   })
						   .then(function(customQuery){
							   //TODO 查询条件
							   var params = {id:customQuery.id,needPage:"false"}; //自定义查询绑定给下拉框时不分页
							   
							   CustomQuery.search(params,function(data){
									scope.optionRows = data.rows;
									
									angular.forEach(scope.optionRows,function(option){
										option.label = option[scope.htQuery.labelBind];
										option.value = option[scope.htQuery.valueBind];
									});
								});
						   });
			}
			
			scope.shouldSelected = function(v){
				if(!scope.$value){
					scope.$value = getValByScope(null,scope.htQuery.bind.key,scope.$parent);
				}
				return scope.$value == v;
			}
			
			if(scope.htQuery){
				scope.loadData();
			}
		},
		template:'<div><option value="">请选择</option><option ng-repeat="option in optionRows" ng-selected="shouldSelected(option.value)" value="{{option.value}}">{{option.label}}</option></div>'
	};
}]);