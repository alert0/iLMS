angular.module('ServiceSetService', ['base'])
.service('ServiceSet', ['$rootScope','baseService','$filter', function($rootScope,baseService,$filter) {
    var service = {
    		//webservie解析
    		serviceInfo : function(url,callback){
    			if(!url){
    				alert("url cannot be null");return;
    			}
    			
    			baseService.postForm(__ctx + '/system/serviceSet/serviceInfo',{url:url})
    			.then(function(data){
    					if(callback){
    						callback(data);
    					}
    				},function(status){alert("error code:"+status)});
    		},
    		//service设置详情
    		detail : function(serviceSetId,callback){
    			if(!serviceSetId){
    				alert("url cannot be null");
    				return;
    			}
    			baseService.postForm(__ctx + '/system/serviceSet/serviceSetGet',{id:serviceSetId}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			},function(status){alert("error code:"+status)});
    		},
    		getByAlias : function(alias,callback){
    			if(!alias){
    				alert("alias cannot be null");
    				return;
    			}
    			baseService.postForm(__ctx + '/system/serviceSet/serviceSetGet',{alias:alias}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			},function(status){alert("error code:"+status)});
    		},
    		save : function(serviceSet,callback){
    			if(!serviceSet){
    				if(callback){
    					callback();
    				}
    				return;
    			}    			
    			baseService.postForm(__ctx+'/system/serviceSet/save',{service:serviceSet}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			},function(status){alert("error code"+status)});
    		},
    		invokeService : function(serviceSetId,params,callback){
    			if(!serviceSetId){
    				alert("error! serviceSetId cannot be null");
    				return;
    			}    			
    			baseService.postForm(__ctx+'/system/serviceSet/invokeService',{serviceSetId:serviceSetId,params:params}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			},function(status){alert("error code:"+status)});
    		},
    		addParamDialog : function(opt) {
    			var dialog = {};
    			var def = {
    				passConf : '',
    				title : '添加参数',
    				width : 400,
    				height : 300,
    				modal : true,
    				resizable : false,
    				iconCls : 'icon-collapse',
    				buttons : [
    						{
    							text : '确定',
    							handler : function(e) {
    								var result = dialog.innerWin.clickOk();
    								if(result){
    									dialog.dialog('close');
    								}
    							}
    						}, {
    							text : '取消',
    							handler : function() {
    								dialog.dialog('close');
    							}
    						} ]
    			};

    			$.extend(def, opt);
    			this.show = function() {
    				dialog = $.topCall.dialog({
    							src : __ctx+ '/system/serviceSet/serviceSetParamEdit',
    							base : def
    						});
    				return dialog;
    			};
    			return this;
    		},
    		handlerStructure : function(inputParams){
    			return service.translate(inputParams[0].structureInfos);
    		},
    		//将service模块解析出来的参数数据 加工为正确的json数据
    		translate : function(obj){
    			var ary = [];
    			for(var key in obj){
    				var item = {key:key};
    				var val = obj[key];
    				if(angular.isObject(val)){
    					if(val["_raw"]){
    						var typeAry = [];
    						//是否泛型
    						item.generics = true;
    						item.children = [];
    						var actual = val["_actual"];
    						for(var i in actual){
    							var sun = {key:i}, 
    								k = actual[i];
    							typeAry.push(i);
    							if(angular.isObject(k)){
    								sun.type = "Bean";
    								sun.children = service.translate(k);
    							}
    							else{
    								sun.type = k;
    							}
    							sun.name = sun.key + "(" + sun.type + ")";
    							sun.nocheck = true;
    							item.children.push(sun);
    						}
    						item.type = val["_raw"] + "<" + typeAry.join(',') + ">";
    					}
    					else{
    						item.type = "Bean";
    						item.children = service.translate(val);
    					}
    				}
    				else{
    					item.type = val;
    				}
    				item.name = item.key + "(" + item.type + ")";
    				ary.push(item);
    			}
    			return ary;
    		},
    		inputAttrs : ['name','key','type','checked','nocheck','bind','generics','children'],
    		filterNodes : function(node){
    			for(var i=0,c;c=node[i++];){
    				for(var key in c){
    					if(key=='children'){
    						service.filterNodes(c[key]);
    					}
    					else if(service.inputAttrs.indexOf(key)==-1){
    						delete c[key];
    					}
    				}
    			}
    		}
        };
    return service;
}])
.service('ServiceParam',['baseService',function(baseService){
	var service = {
		list : function(serviceSetId,callback){
			if(!serviceSetId){
				if(callback){
					callback();
				}
				return;
			}
			baseService.postForm(__ctx+'/system/serviceSet/getParams',{serviceSetId:serviceSetId}).then(function(data){
				if(callback){
    				callback(data);
    			 }
			},function(code){alert("error code "+code)});
		}
	};
	return service;
}]);
