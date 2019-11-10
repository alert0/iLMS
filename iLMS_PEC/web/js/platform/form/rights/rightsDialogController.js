var app = angular.module("app", [ 'base', 'formDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', function($scope, baseService) {
	var _oldPermission = null;
	$scope.tableSn = [];
	$scope.init=function(params){
		$scope.param=params;
		baseService.postForm("getPermission",params).then(function(data) {
			// 使用克隆 
			_oldPermission = $.cloneObject(data);
			handleBackData(data);
			$scope.permissionList = data.permissionList;
		});
	}
	
	
	
	/**
	 * field:某个权限对象 rightType：权限类型 只读-"read" 编辑-"write" 必填-"required"
	 */
	$scope.fieldDialog = function(field, rightType) {
		RightsUtil.openDialog("formPermissionCalcList",function(data, dialog){
			$scope.$apply(function(){
				field[rightType] = data;
			})
			dialog.dialog('close');
		},CloneUtil.list(field[rightType]));
	};
	/**
	 * field:某个权限对象 rightType：权限类型 只读-"read" 编辑-"write" 必填-"required"
	 */
	$scope.hideColumn = function(field) {
		
		if($scope.checkIshide(field)=='否'){
			field.read =  [{type: "none"}];
			field.required =  [{type: "none"}];
			field.write =  [{type: "none"}];
		}else{
			field.read =  [{type: "everyone"}];
			field.required =  [{type: "none"}];
			field.write =  [{type: "everyone"}];
		}
	
		!$scope.$$phase	&&$scope.$apply();
	};

	/**
	 * 批量权限选择 rightType：权限类型 只读-"read" 编辑-"write" 必填-"required"
	 */
	$scope.clickAll = function(rightType, type) {
		$($scope.subTableList).each(function() {
			for ( var key in this.fields) {
				this.fields[key][rightType] = [ {type : type} ];
			}
		});

		for ( var key in $scope.opinion) {
			$scope.opinion[key][rightType] = [ {type : type} ];
		}
	};

	$scope.rightToDesc = function(right) {
		var desc = "";
		$(right).each(function() {
			if (desc) {
				desc += " 和 ";
			}
			var str = $scope.permissionList[this.type];
			if (this.name) {
				str += ":" + this.name;
			} else if (this.id) {
				str += ":" + this.id;
			}
			desc += str;
		});
		return desc;
	};
	$scope.checkIshide = function(file) {
		var desc = "是";
	    if(file && file.read && file.read.length>0){
	    	if(file.read[0].type!='none'){
	    		desc='否';
	    		return desc;
	    	}
	    }
	    if(file && file.required && file.required.length>0){
	    	if(file.required[0].type!='none'){
	    		desc='否';
	    		return desc;
	    	}
	    }
	    if(file && file.write && file.write.length>0){
	    	if(file.write[0].type!='none'){
	    		desc='否';
	    		return desc;
	    	}
	    }
		return desc;
	};
	
	$scope.checkIsAllhide = function(list) {
		if(list && list.constructor.name=='Array'){
		  for(var i=0;i<list.length;i++){
			  var fields=list[i].fields;
				if(fields && fields.constructor.name=='Array'){
					  for(var j=0;j<fields.length;j++){
						  var file=fields[j];
						  if($scope.checkIshide(file)=='否'){
							  return true;
						  }
					  }
					}
		  }
		}
		return false;     
	};
	$scope.hideOrShowAll = function(list) {
		if($scope.checkIsAllhide(list)){
			$scope.hidden();
		}else{
			$scope.edit();
		}  
	};
	$scope.hidden = function() {
		$scope.clickAll("read", "none");
		$scope.clickAll("write", "none");
		$scope.clickAll("required", "none");
	};

	$scope.readOnly = function() {
		$scope.clickAll("read", "everyone");
		$scope.clickAll("write", "none");
		$scope.clickAll("required", "none");
	};

	$scope.edit = function() {
		$scope.clickAll("read", "everyone");
		$scope.clickAll("write", "everyone");
		$scope.clickAll("required", "none");
	};

	$scope.mustFill = function() {
		$scope.clickAll("read", "everyone");
		$scope.clickAll("write", "everyone");
		$scope.clickAll("required", "everyone");
	};

	$scope.save = function() {
		var param={};
		angular.extend(param, $scope.param);
	
		// 提交前拼装回json
		param.permission={};
		param.permission.table = {};
		var tmp = $.cloneObject($scope.subTableList);
		$(tmp).each(function() {
			var _fields = {};
			$(this.fields).each(function(){
				_fields[this.fieldName] = this;
			});
			this.fields = _fields;
			param.permission.table[this.tableName] = this;
		});
		
		//如果为实例情况，清除读写权限。
		if(param.type=="2"){
			handPermissionData(param.permission);
		}
		
		param.permission.opinion = $scope.opinion;
		param.permission = angular.toJson(param.permission);

		
		console.info($scope.subTableList);
		
		baseService.postForm("save", param).then(function(r) {
			console.info($scope.subTableList);
			if (r.result == 1) {
				$.topCall.success(r.message, function(rtn) {
					window.location.reload();
				});
			} else {
				if (r.cause) {
					$.topCall.errorStack(r.message, r.cause, "错误信息");
				} else {
					$.topCall.error(r.message);
				}
			}
		});
	};
	
	//去除读写权限。
	function handPermissionData(json){
		//param.permission
		var tableJson=json.table;
		//遍历表
		for(var key in tableJson){
			//获取字段
			var fields=tableJson[key].fields;
			//遍历字段
			for(var fieleName  in fields){
				//字段信息
				var field=fields[fieleName];
				delete field.write;
				delete field.required;
			}
		}
		
		var opinions=json.opinion;
		if(!opinions) return;
		
		for(var key in opinions){
			var opinion=opinions[key];
			delete opinion.write;
			delete opinion.required;
		}
	}
	
	/**
	 * 获取默认数据
	 */
	$scope.getDefaultByFormKey = function() {
		baseService.postForm("getDefaultByFormKey", param).then(function(data) {
			handleBackData(data);
		});
	};
	
	function deepMerge(obj1, obj2) {
	    var key;
	    for(key in obj2) {
	        // 如果target(也就是obj1[key])存在，且是对象的话再去调用deepMerge，否则就是obj1[key]里面没这个对象，需要与obj2[key]合并
	        if(!obj2.hasOwnProperty(key)) continue;
	    	obj1[key] = obj1[key] && obj1[key].toString() === "[object Object]" ?
	        deepMerge(obj1[key], obj2[key]) : obj1[key] = obj2[key];
	    }
	    return obj1;
	}
	
	$scope.addNewField = function() {
		baseService.postForm("getDefaultByFormKey", param).then(function(data) {
			if(_oldPermission){
				// 不存在的字段，添加进来
				deepMerge(data,_oldPermission);
			}
			handleBackData(data);
		});
	};
	
	
	/**
	 * 处理后台来的权限数据
	 */
	function handleBackData(data){
		$scope.tableSn = data.tableSn;
		data = data.json;
		$scope.subTableList = [];
		$scope.opinion = data.opinion;
		angular.forEach($scope.tableSn, function(obj,index,array){
			var temp = data.table[obj.name_];
			temp.tableName = obj.name_;

			var tempArr = [];
			for( var fieldName in  temp.fields ){
				var fieldTemp = temp.fields[fieldName];
				fieldTemp.fieldName = fieldName;
				tempArr.push(fieldTemp);
			}
			temp.fields = tempArr;
			
			$scope.subTableList.push(temp);
		});
	}
} ]);