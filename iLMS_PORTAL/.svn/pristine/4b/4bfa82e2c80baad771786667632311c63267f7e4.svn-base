var app = angular.module('app', ['formDirective']);
	app.controller("formBusCtrl", [ '$scope', 'baseService', function(scope, baseService) {
		
		//获取数据
		baseService.postForm(__ctx+"/form/formBus/"+formKey+"/getData",{id:id,readonly:readonly}).then(function(data){
			scope.data = {};
			scope.boCode = data.boCode;
			//树形情况设置值
			if(parentKey){
				data.data[parentKey]=parentVal;
			}
			
			scope.data[data.boCode]=data.data; //表单中结构都为 boCode{boJson} 
			scope.permission = data.permission;
			scope.formHtml = data.formHtml;
			scope.formBusSet = data.formBusSet;
			// 子表必填，一对一数据填充
			window.setTimeout(scope.initSubTableData,100);
		})
		
		scope.save  = function(){
			if (!scope.custForm.$valid) return;
			
			var jsonData = angular.toJson(scope.data[scope.boCode]);
			// js 前置事件
			if(!scope.excuteScript(true,scope.data[scope.boCode])) return;
			
			var rtn = baseService.postForm(__ctx+"/form/formBus/"+formKey+"/save",{json:jsonData});
			rtn.then(function(data) {
				// tree列表添加后刷新
				if(parent && parent.refreshZtrrListNode){
					parent.refreshZtrrListNode();
				}
				
				// js 后置事件
				if(!scope.excuteScript(false,data)) return;
				
				if (data.result == 1) {
					$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
					});
				} else {
					if (data.cause) { $.topCall.errorStack(data.message, data.cause, "错误信息"); } 
					else { $.topCall.error(data.message);}
				}
			}, function(status) {
				$.topCall.error("请求失败");
			});
		}
		scope.excuteScript = function(isBefore,data){
			var code = isBefore? scope.formBusSet.jsPreScript : scope.formBusSet.jsAfterScript;
			var script = "var tempFunction = function(data){ "+code+"};"
			var result =  eval(script+"tempFunction(scope);");
			if(false===result) return false;
			return true;
		}
		
		scope.add = function(path){
			var arr = path.split(".");
			if(arr.length<2)alert("subtable path is error!")
			var subTableName = arr[1].replace("sub_","")
			var tempData = scope.data[arr[0]].initData[subTableName];
			
			if(!tempData)tempData={};
			var ary = eval("scope.data." + path); 
			if(!angular.isArray(ary)) ary = [];
			
			ary.push(angular.copy(tempData));
			eval("scope.data." + path+"=ary");
			!scope.$$phase && scope.$digest();
		};
		
		scope.remove = function(path,index){
			var ary = eval("(scope.data." + path + ")");
			if(ary&&ary.length>0){
				ary.splice(index,1);
			}
		};
		
		scope.initSubTableData = function(){
			var permission = scope.permission;
			
			var initSubTable = [];
			for(var subTable in permission.table){
				if(permission.table[subTable].required){
					initSubTable.push(subTable);
				}
			}
			
			$("[type='subGroup'][initdata]").each(function(i,item){
				initSubTable.push($(item).attr("tablename"));
			});
			var data = scope.data;
			for(var i=0,subTable;subTable=initSubTable[i++];){
				for(var boCode in data){ 
					var initData =data[boCode].initData[subTable];
					if(initData &&(!data[boCode]["sub_"+subTable]||data[boCode]["sub_"+subTable].length==0)){
						data[boCode]["sub_"+subTable] = [];
						data[boCode]["sub_"+subTable].push($.extend({},initData));
					}
				}
			}
			!scope.$$phase&&scope.$digest(); 
		}
	}]);
	
