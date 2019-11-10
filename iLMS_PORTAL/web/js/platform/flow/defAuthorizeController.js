var  app = angular.module("app", ['formDirective']);
app.controller('ctrl', ['$scope','baseService','commonService',function($scope,baseService,commonService){
	
	/**
	 * 页面进入时加载数据。
	 */
	$scope.load=function(id){
		var url=__ctx +"/flow/defAuthorize/defAuthorizeJson";
		if(id || id.length>0){
			url+="?id=" + id;
		}

		baseService.get(url).then(function(result){
			handData(result);
			$scope.data=result;
		})
	}
	
	/**
	 * 选择流程定义。
	 */
	$scope.selectDef=function(){
		new BpmDefDialog({isSingle:false,callback:function(aryDef,dialog){	
			if(aryDef.length==0) return;
			//处理数据。defKey,name
			$scope.$apply(function(){
				for(var i=0;i<aryDef.length;i++){
					var json=aryDef[i];
					var rightObj={"m_edit":false,"m_del":false,"m_start":false,"m_set":false,"m_clean":false,"i_del":false};
					var obj={"defName":json.name,"defKey":json.defKey,"right":rightObj};
					var isExist=commonService.isExist("defKey",json.defKey,$scope.data.defNameJson);
					if(!isExist){
						$scope.data.defNameJson.push(obj);
					}
				}
			});
		}}).show();
	};
	
	/**
	 * 设置设置权限。
	 * ownerNameJson 格式。
	 * [{type:"everyone",title:"所有人"},{type:"user",title:"用户",id:"1,2",name:"ray,tom"}]
	 */
	$scope.setAuth=function(){ 
		RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
			$scope.$apply(function(){
				var tmpAry=[];
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					if(obj.id){
						var tmp={"type":obj.type,"title":obj.title, "id":obj.id,"name":obj.name};
						tmpAry.push(tmp);
					}
					else{
						var tmp={"type":obj.type,"title":obj.title};
						tmpAry.push(tmp);
					}
				}
				$scope.data.ownerNameJson=tmpAry;
			});
			dialog.dialog('close');
		},$scope.data.ownerNameJson);
	}
	
	/**
	 * 删除选中的流程。
	 */
	$scope.delDef=function(){
		var aryJson=$scope.data.defNameJson;
		
		for(var i=aryJson.length-1;i>=0;i--){
			var obj=aryJson[i];
			if(obj.selected){
				aryJson.splice(i,1); 
			}
		}
	}
	
	/**
	 * 保存授权规则。
	 */
	$scope.save=function(){
		if($scope.data.ownerNameJson.length==0){
            $.topCall.warn("请选择授权人员名称!");
            return;
		}
		if ($scope.data.defNameJson.length==0){
            $.topCall.warn("请选择要授权的流程!");
            return;
		}
		if($scope.bpmDefAuthorizeForm.$invalid) {
			 $.topCall.warn("表单验证不通过,请检查表单!");
			return;
		}
		var obj=handSave($scope.data);
		var url=__ctx +"/flow/defAuthorize/save";
		var defer=baseService.post(url,obj);
		
		defer.then(function(data){
			if (data.result==1) {
			    $.topCall.success("保存授权成功!",  function(r){
						HT.window.closeEdit(true,'grid');
					},"提示信息"
			    );
			} else {
			    $.topCall.error(data.message);
			}
		})
	};
	
	/**
	 * 权限全选。
	 */
	$scope.checkAll=function(event,str){
		var checked=event.currentTarget.checked;
		var ary= $scope.data.defNameJson;
		for(var i=0;i<ary.length;i++){
			var obj=ary[i];
			obj.right[str]=checked;
		}
	};
	
	/**
	 * 全选所有的流程定义。
	 */
	$scope.checkAllRow=function(event){
		var checked=event.currentTarget.checked;
		var ary= $scope.data.defNameJson;
		for(var i=0;i<ary.length;i++){
			ary[i].selected=checked;
		}
	}
	
	/**
	 * 保存时处理json。
	 */
	function handSave(data){
		var obj =angular.copy(data);
		obj.authorizeTypes=angular.toJson(data.authorizeTypes);
		obj.defNameJson=angular.toJson(data.defNameJson);
		obj.ownerNameJson=angular.toJson(data.ownerNameJson);
		for(var i=0;i<obj.defNameJson.length;i++){
			var tmp=obj.defNameJson[i];
			tmp.right=angular.toJson(tmp.right)
		}
		return obj;
	}
	
	
	/**
	 * 数据转成JSON对象。
	 */
	function handData(data){
		if(data.authorizeTypes==""){
			data.authorizeTypes={"start":false,"management":true,"task":false,"instance":false};
		}
		else{
			data.authorizeTypes=angular.fromJson(data.authorizeTypes);
		}
		data.defNameJson=angular.fromJson(data.defNameJson);
		for(var i=0;i<data.defNameJson.length;i++){
			var tmp=data.defNameJson[i];
			tmp.right=angular.fromJson(tmp.right)
		}
		data.ownerNameJson=angular.fromJson(data.ownerNameJson);
	}
	
	
}]);