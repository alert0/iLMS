var  app = angular.module("app", ['base','formDirective','arrayToolService']);
app.controller('ctrl', ['$scope','baseService','ArrayToolService',function($scope,baseService,ArrayToolService){
	$scope.data={};
	$scope.data.status = "enabled";
	$scope.data.isExternal = "1";
	$scope.data.dsName="LOCAL";
	$scope.data.packageId = packageId;
	$scope.data.isCreateTable="1";
	$scope.data.attributeList=[];
	
	$scope.isStepCreate = true;
	if(parent.formNextStep){
		$scope.isStepCreate = false;
	}
	
	
	$scope.afterSave=function(data){
		if(parent.formNextStep){
			if(parent.isContinue){
				parent.formNextStep("current","/bo/def/bOEntExtEdit",'bOEntExtEdit',data.postData.name);
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
	
	$scope.$on("afterSaveEvent",function(event,data){
		if(!data.r){
			HT.window.closeEdit(true);
		}
		window.location.reload();
	});
	
	$scope.$on("afterLoadEvent",function(event,data){
		//加载属性列表
		var rtn=baseService.post("bOEnt/getAttrList?entId="+data.id);
  	  	rtn.then(function(data){
  			if(!data) return;
  			$scope.data.attributeList=data;
  	  		},function(status){
  	  			$.topCall.error("请求失败");
  	  		}
  	  	);
	});
	
	$scope.$on("beforeSaveEvent",function(event,data){
		if($scope.data.id) return;
		//提交前删除外键字段
		for(var i=0;i<$scope.data.attributeList.length;i++){
			if($scope.data.attributeList[i].name==$scope.data.fk){
				ArrayToolService.del(i,$scope.data.attributeList);
				break;
			}
		}
	});
	
	/**
	 * 获取外部数据源的表
	 */
	$scope.getExternalTable = function(){
		var rtn=baseService.postForm(__ctx +'/form/customQuery/getByDsObjectName',{dsalias:$scope.data.dsName,isTable:"1",objName:$scope.data.tableName});
		rtn.then(function(data){
  		  	$scope.externalTable= data;
  		  	
  		  	$scope.data.tableName="";
		});
	};
	
	/**
	 * 获取外部数据源的表
	 */
	$scope.tableChange = function(){
		if(!$scope.data.tableName){
			return;
		}
		
		var rtn=baseService.postForm(__ctx +'/form/customQuery/getTable',{dsalias:$scope.data.dsName,isTable:"1",objName:$scope.data.tableName});
		rtn.then(function(data){
			if(!data){
				return;
			}
			if(data.table.primayKey.length==0){
				alert("该表无主键!");
				return;
			}
			$scope.data.pk = data.table.primayKey[0]["fieldName"];
			var pkType = data.table.primayKey[0]["columnType"];
			//主键类型不是字符串就是数字
			if(pkType.indexOf("varchar")>=0){
				$scope.data.pkType="varchar";
			}else{
				$scope.data.pkType="number";
			}
			$scope.data.attributeList=[];//重置字段
			//拼装成字段
			$(data.table.columnList).each(function(){
				if(this.isPk) return;//主键不展示
				
				var attr = {};
				attr.desc = this.comment;
				attr.name = this.fieldName;
				attr.fieldName = this.fieldName;
				attr.isRequired = this.isNull?"0":"1";
				attr.dataType = this.columnType;
				if(this.columnType=="number"){
					attr.attrLength = this.intLen;
					attr.decimalLen = this.decimalLen;
				}else{
					attr.attrLength = this.charLen;
				}
				
				attr.defaultValue = this.defaultValue;
				attr.fcolumnType = this.fcolumnType;
				$scope.data.attributeList.push(attr);
			});
			
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
		temp.status = 1; 
		$scope.data.attributeList.push(temp);
	};

	
}]);