var app = angular.module("app", [ 'base', 'formDirective', 'arrayChangeService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayChangeService', function($scope, baseService, ArrayChangeService) {
	$scope.ArrayChange = ArrayChangeService;

	$scope.data = {};
	if(packageId){
		$scope.data.packageId = packageId;
	}
	$scope.data.status = "enabled";
	$scope.data.isExternal = "0";
	$scope.data.dsName = "LOCAL";
	$scope.data.isCreateTable = "0";
	$scope.data.attributeList = [];
	$scope.selectedAttrList = [];// 被选中的属性
	
	$scope.isStepCreate = true;
	if(parent.formNextStep){
		$scope.isStepCreate = false;
	}
	
	$scope.init = function(){
		baseService.post("bOEnt/isEditabled?id=" + id).then(function(data){
			$scope.isEditabled=data=="true";
		});
	};
	
	$scope.$on("beforeSaveEvent", function(event, data) {
		if($scope.data.attributeList.length<1){
			$.topCall.error("实例至少需要一个字段");
			data.pass=false;
			return;
		}
		//保存数据前，设置排序字段
		$($scope.data.attributeList).each(function(i) {
			this.sn = i+1;
		});
		
		if ($scope.isEditabled) {// 出于任意修改状态就不要增加字段提醒
			return;
		}
		var tmpDesc = [];
		for (var i = 0; i < $scope.data.attributeList.length; i++) {
			var attr = $scope.data.attributeList[i];
			if (!attr.id) {// 如果有ID为空，说明是在表生成的情况下新增了字段，那么需要提示一下
				tmpDesc.push(attr.desc);
			}
		}
		if(tmpDesc.length){
			var rtn=window.confirm('检查到新字段('+tmpDesc.join(",")+"),将在表结构中插入该字段?");
			if(!rtn){
				data.pass=false;
			}
		}
	});
	
	
	$scope.afterSave=function(data){
		if(parent.formNextStep){
			if(parent.isContinue){
				parent.formNextStep("current","/bo/def/bOEntEdit",'bOEntEdit',data.postData.name);
			}else{
				parent.formNextStep("next","/bo/def/bODefEdit",'bODefEdit',data.postData.name);
			}
		}else{
			$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
				if(!r){
					HT.window.closeEdit(true);
				}
				window.location.reload();
			});
		}
	}
	
	$scope.$on("afterSaveEvent", function(event, data) {
		if(!data.r){
			HT.window.closeEdit(true);
		}
		window.location.reload();
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		// 加载属性列表
		var rtn = baseService.post("bOEnt/getAttrList?entId=" + data.id);
		rtn.then(function(data) {
			if (!data)
				return;
			data.sort(function(a,b){
		            return a.sn-b.sn;
		        });
			$scope.data.attributeList = data;
		}, function(status) {
			$.topCall.error("请求失败");
		});
	});

	/**
	 * 获取外部数据源的表
	 */
	$scope.getExternalTable = function() {
		var rtn = baseService.postForm(__ctx + '/form/customQuery/getByDsObjectName', {
			dsalias : $scope.data.dsName,
			isTable : "1",
			objName : $scope.data.tableName
		});
		rtn.then(function(data) {
			$scope.externalTable = data;
		});
	};

	/**
	 * 添加一条新属性
	 */
	$scope.addAttr = function() {
		var temp = {};
		temp.desc = "";
		temp.isRequired = "0";
		temp.dataType = "varchar";
		temp.attrLength = "50";
		temp.isNew=true;
		$scope.data.attributeList.push(temp);
	};

	/**
	 * 修改数据类型。
	 */
	$scope.changeDbType = function(row) {
		var type = row.dataType;
		switch (type) {
		case "number":
			row.attrLength = 10;
			row.decimalLen = 0;
			break;
		case "varchar":
			row.attrLength = 50;
			break;
		case "date":
			row.format = "yyyy-MM-dd HH:mm:ss";
			break;
		default:
			row.attrLength = 0;
			row.decimalLen = 0;
			break;
		}
	}

} ]);

angular.module('arrayChangeService', [])
.service('ArrayChangeService', [function() {
    var service = {
    		//上移按钮
	    	up:function(idx,list){
	    		if(idx<1){
	    			return;
	    		}
	    		var t=list[idx-1];
	    		list[idx-1]=list[idx];
	    		list[idx]=t;
	    		//给所有ng-model='attr.name'元素增加排序标记，以便在对实体字段进行排序时，不用验证字段别名是否重复
                $($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	//下移按钮
	    	down:function(idx,list){
	    		if(idx>=list.length-1){
	    			return;
	    		}
	    		var t=list[idx+1];
	    		list[idx+1]=list[idx];
	    		list[idx]=t;
	    		$($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	resumeSn:function(list){
	    		for(var k = 0 ; k < list.length ; k++){
	    			list[k].sn = k;
				}
	    		return list;
	    		$($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	/**
	    	 * idx 原位置
	    	 * num 目标位置
	    	 * list 数组
	    	 */
	    	moveToNum:function(idx,target,list){
	    		if(target==-1){
	    			target = 0;
	    		}else if(idx >= target){
	    			target = target+1;
	    		}
	    		var t= list.splice(idx,1);
	    		list.insert(target,t[0]);
	    		this.resumeSn(list);
	    		$($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	//默认ngModel turnToIndex
	    	turnTo:function(rowScope,list){
	    		var toIndex =rowScope.turnToIndex - 1;
	    		if(!rowScope.turnToIndex || toIndex<0 || toIndex>=list.length) return; 
	    		
	    		var index = rowScope.$index;
	    		if(toIndex == index) return;
	    		
	    		var row= list.splice(index,1);
	    		list.insert(toIndex,row[0]);
	    		
	    		rowScope.turnToIndex= "";
	    		$($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	//删除按钮
	    	del:function(idx,list){
	    		list.splice(idx,1);
	    	},
	    	//找到指定元素的位置
	    	idxOf:function(val,list){
	    		for (var i = 0; i < list.length; i++) {  
	    	        if (list[i] == val) return i;  
	    	    }  
	    	    return -1; 
	    	},
	    	//删除指定元素
	    	remove:function(val,list){
	    		var idx = this.idxOf(val,list);  
	    	    if (idx > -1) {  
	    	    	list.splice(idx, 1);  
	    	    }
	    	},
	    	//置顶
	    	top:function(idx,list){
	    		if(idx>=list.length || idx<1){
	    	           return;
	    		}
	    		//逐个交换
	            for(var i=0;i<idx;i++){
		            var temp=list[i];
		            list[i]=list[idx];
		            list[idx]=temp;
	            }
	            $($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	},
	    	//置底
	    	bottom:function(idx,list){
	    		if(idx>=list.length-1 || idx<0){
	                return;
	            }
	            //逐个交换
                for(var i=list.length-1;i>idx;i--){
	                var temp=list[i];
	                list[i]=list[idx];
	                list[idx]=temp;
                }
                //给所有ng-model='attr.name'元素增加排序标记，以便在对实体字段进行排序时，不用验证字段别名是否重复
                $($("[ng-model='attr.name']")).each(function(){$(this).attr("sort","true");});
	    	}
    };
    return service;
}]);