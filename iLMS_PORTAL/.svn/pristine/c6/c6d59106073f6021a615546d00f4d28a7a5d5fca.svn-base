var  app = angular.module("app", ['base','formDirective','arrayToolService','ui.codemirror' ]);
app.controller('ctrl', ['$scope','baseService','ArrayToolService',function($scope,baseService,ArrayToolService){
	$scope.ArrayTool = ArrayToolService;
	$scope.data={};
	$scope.editingField = {"option":{}};
	$scope.data.beforeShow = "";
	$scope.data.whenSave="";
	var data=window.passConf.data;
	//初始化数据
	if(data){
		$scope.data = data;
	}
	
	//选中需要绑定bo属性的控件  
	$scope.focusBind = function(event){
		$(".border-focused").removeClass("border-focused");
		$(event.currentTarget).addClass("border-focused");
		$(event.currentTarget).parent().siblings().find("span[ng-click]").css("border", null).removeClass("border-focused");
		$scope.currentBindTarget = $(event.currentTarget).find("input");
		event.stopPropagation();
	};
	//取消绑定bo属性的控件  
	$scope.removeFocusTarget = function(event){
		if(event&&!$(event.currentTarget).closest("#fieldBind")[0]){
			$scope.currentBindTarget=null;
			$(".border-focused").removeClass("border-focused");
		}
	};
	
	/**将BO转成 {表.children(字段)}的形式*/
	$scope.treeNode2Field = function(treeNode){
		if(treeNode.children && treeNode.children.length>0){
			var table = getField(treeNode);
			table.children =[];
			for (var int = 0,node;node=treeNode.children[int++];) {
				if(node.children && node.children.length>0||node.nodeType!="field")continue;
				//获取字段
				var field =getField(node,int-1);
				field.tableName=table.name;

				table.children.push(field);
			}
			return table;
		}else{
			return getField(treeNode);
		}
		//boDefAlias
		function getField(node,sn){
			var isRequired = node.isRequired ? true : false;
			var field = {
					name	:	node.name,
					desc	:	node.desc, 
					path	:	node.path,
					type	:	node.dataType||node.nodeType, 		//字段的类型。表的类型 sub main
					fieldType:	node.nodeType,
					boAttrId:	node.id,		
					boDefId	:	node.boDefId, 	
					entId	:	node.nodeType=="field"?node.entId:node.id,
					sn		:	sn,  
					validRule:	{"required":isRequired},
					relation:	node.type  // 子表的时候才有用。为表的关系 
				}; 
			
			//给字段一个默认的控件类型
			$($scope.controlType).each(function(index,control){
				if(isInArray(control.supports,field.type)){
					field.ctrlType = control.id;
					return false;
				}
			});
			field.option={};
			
			if(field.type=="number"){
				try {
					//添加对整数位长度的校验
					field.validRule["maxIntLen"] = node.intLen;
					//添加对小数位长度的校验
					field.validRule["maxDecimalLen"] = node.decimalLen;
				} catch (e) {}
				
				var json = [{name:"数字",text:"number"}];
				field.validRule["rules"] = json;
			}
			if(field.type=="date"){
				field.option.dataFormat =node.format;
				field.option.unmodifiable = true;
				var json = [{name:"日期",text:"date"}];
				if(node.format == 'HH:mm:ss'){
					 json = [{name:"时间",text:"time"}];
				}
				field.validRule["rules"] = json; 
			}
			
			return field
		}
	} 
	
	
	$scope.nodeBeforeClick = function(treeId, treeNode, clickFlag){
		// 如果是字段、则不允许点击、除非当设置选择器的字段时候
		if(!treeNode.children||treeNode.children.length==0 || treeNode.id=="0"){
			if(!$scope.editingField.option.node && treeNode.nodeType != "var"){
				$.topCall.warn('还没有选择需映射的BO对象，请先选择BO对象。');
				return ;
			}
			if(!$scope.editingField.option.node && treeNode.nodeType == "var"){
				$scope.setEditingField(treeNode);
				return ;
			}
			if(treeNode.nodeType != "var"){
				var paths = treeNode.path.split(".sub_");
				if(paths.length>1){
					$.topCall.error("目前只支持主表之间的映射，不支持子表间的映射");
					return ;
				}
			}
			if (!scope.data.description) {
				scope.data.description = treeNode.desc;
			}
			//设置绑定字段
			var field = $scope.treeNode2Field(treeNode);
			if($scope.currentBindTarget){
				var parent = treeNode.getParentNode();
			    var path ="";
			    if(parent.type=="sub" || parent.nodeType=="sub"){
			    	path="item";
			    }else{
			    	path = parent.boDefAlias;
			    }
			    field.path =path;
			    if($scope.editingField.ctrlType=="dic"  ){
			    	if($scope.editingField.path == field.path &&$scope.editingField.name == field.name ){
			    		$.topCall.toast('警告！','请绑定其它字段');
			    		return false;
			    	}
			    	$scope.editingField.bindDicName = field;
			    	!$scope.$$phase&&($scope.$digest());
			    	return ;
			    }
			    
			    var s = angular.element($scope.currentBindTarget).scope();
			      s.s.json = field; 
			    !$scope.$$phase&&($scope.$digest());
			    return false;
			}
			return false;
		}
		return true;
	};
	
	$scope.setEditingField = function(treeNode){
		var bind= [];
		$scope.editingField.option.node = treeNode;
		if(treeNode.nodeType == "var"){
			treeNode.comment = treeNode.name;
			bind.push(treeNode);
		}else{
			$.each(treeNode.children,function(i,item){
				if(item.nodeType == "field"){
					bind.push(item);
				}
			});
		}
		$scope.editingField.option.bind=bind;
	};
	
	$scope.getBeforeShow = function(){
		var beforeShow = "";
		$.each($scope.editingField.option.bind,function(i,item){
			if(item.json){
				var setScript = "";
				var getScript = "";
				if(item.nodeType == "var"){
					setScript = "task.setVariable(\""+item.name+"\",";
				}else{
					setScript = item.path+".set(\""+item.name+"\",";
				}
				if(item.json.fieldType && item.json.fieldType=="var"){
					getScript = item.json.name+");";
				}else{
					getScript = item.json.path+".getByKey(\""+item.json.name+"\"));"
				}
				beforeShow += (setScript+getScript);
			}
		});
		$scope.data.beforeShow = beforeShow;
	}
}]);