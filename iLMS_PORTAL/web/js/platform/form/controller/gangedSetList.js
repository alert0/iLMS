var gangedApp = angular.module('app', [ 'formDirective','arrayToolService']);

gangedApp.controller('ctrl',['$scope','baseService','ArrayToolService',function($scope,BaseService,ArrayToolService){
	$scope.ArrayTool=ArrayToolService;
	$scope.fields = [];
	$scope.datas = [];
	$scope.mainTabFields = [];
	$scope.subTabFields = [];
	$scope.gangedFields = [];
	$scope.selectField = {};
	$scope.fieldMap = {};
	$scope.separators = [];
	$scope.delaySelectField = [];
	
	$scope.$on("afterLoadEvent",function(ev,data){
		
	});
	
	
	//获取所选值控件类型
	$scope.getFieldType = function(pathName){
		var fieldJson = getField(pathName);
		if(fieldJson){
			var ctrlType = fieldJson['ctrlType'];
			if(ctrlType=="select"||ctrlType=="checkbox"||ctrlType=="radio"||ctrlType=="multiselect"){
				return "select";
			}else if(ctrlType=="date"){
				return "date";
			}else if(ctrlType=="selector"||ctrlType=="customdialog"){
				return "selector";
			}else if(ctrlType=="dic"){
				return "dic";
			}else{
				return "text";
			}
		}
		return "text";
	}
	
	if(window.passConf){//初始化
		$scope.datas = window.passConf.scope.fields;
		if(window.passConf.scope.form.ganged){
			$scope.gangedFields = parseToJson(window.passConf.scope.form.ganged) || [];
			for(var i=0;i<$scope.gangedFields.length;i++){
				var temp=$scope.gangedFields[i].chooseField||[];
				var pathArr='';
				if(temp[0]&& temp[0].pathName){
					pathArr=temp[0].pathName.split('.');
				}
			   var tablePath='';
			   var isSub='';
			   if(pathArr.length==3){
				   $scope.gangedFields[i].isSub='true';
				   tablePath=pathArr[0]+'.'+pathArr[1];
				   isSub='true';
			   }else if(pathArr.length==2){
				   if(pathArr[0] !='separators') {
					   $scope.gangedFields[i].isSub='false';
					   tablePath=pathArr[0];
					   isSub='false';
				   }
			   }
			   for(var z=0;z<$scope.datas.length;z++){
				   if(isSub=='false'){
					   $scope.gangedFields[i].changefieldData=$scope.datas;
				   }else{
					   if($scope.datas[z].path==tablePath)  $scope.gangedFields[i].changefieldData=[$scope.datas[z]];
				   }
				   if($scope.datas[z].path==tablePath){
					   $scope.gangedFields[i].choosefieldData=[$scope.datas[z]];
				   }
			   }	
			}
		}
		if(window.passConf.scope.separators){
			$scope.separators = window.passConf.scope.separators;
		}
		if($scope.datas){
			for (var i = 0; i < $scope.datas.length; i++) {
				var childrens = $scope.datas[i].children;
				if($scope.datas[i].fieldType=="main"){
					$scope.mainTabFields.push($scope.datas[i]);
				}else if($scope.datas[i].fieldType=="sub"){
					$scope.subTabFields.push($scope.datas[i]);
				}
				if(childrens){
					for(var m=0;m<childrens.length;m++){
						var children = childrens[m];
						var pathName = children['path']+'.'+children['name'];
						$scope.fieldMap[pathName] = children;
						if($scope.getFieldType(pathName)=='select'){
							addToSelectField(children,pathName);
						}
					}
				}
			}
		}
	}
	
	
	function addToSelectField(field,pathName){
		if(field.option.choiceType=='static'){
			$scope.selectField[pathName] = field.option.choice;
		}else if(field.option.choiceType=='dynamic'){
			getDynamicOpinions(field,pathName);
		}
	}

	//添加联动规则
	$scope.addGanged = function(){
		var ganged = {};
		ganged.id = 0;
		ganged.isSub='true';
		ganged.choosefieldData=$scope.datas;
		ganged.changefieldData=[];
		ganged.chooseField = [{}];
		ganged.changeField = [{}];
		$scope.gangedFields.push(ganged);
	}
	//移除联动规则
	$scope.removeGanged = function(ganged){
		$scope.gangedFields.remove(ganged);
	}
	//添加所选字段
	$scope.addChooseField = function(editingChooseFields){
		editingChooseFields.push({});
	}
	//移除所选字段
	$scope.removeChooseField = function(editingChooseFields,chooseField){
		editingChooseFields.remove(chooseField);
	}
	//添加变换字段
	$scope.addChangeField = function(editingChangeFields){
		editingChangeFields.push({});
	}
	//移除变换字段
	$scope.removeChangeField = function(editingChangeFields,changeField){
		editingChangeFields.remove(changeField);
	}
	
	//获取日期格格式
	$scope.getDataFormat = function(name){
		var fieldJson = getField(name);
		var formate = "yyyy-MM-dd";
		if(fieldJson){
			formate = fieldJson['option'].dataFormat;
		}
		return formate;
	}
	
	//获取变换类型
	$scope.getChangeType = function(changeField,ctrlType){
		if(!ctrlType) return;
		var changeTypes = [];
		var fieldType = getByCtrlType(name);
		switch (fieldType) {
			case 'select' :
				changeTypes = [{value : "1",text : "隐藏"},{value : "2",text : "显示"},{value : "3",text : "只读"},{value : "4",text : "非只读"},{value : "5",text : "必填"},{value : "6",text : "非必填"},{value : "7",text : "置空"},{value : "8",text : "级联"}];
				break;
			case 'text' :
			case 'date' :
				changeTypes = [{value : "1",text : "隐藏"},{value : "2",text : "显示"},{value : "3",text : "只读"},{value : "4",text : "非只读"},{value : "5",text : "必填"},{value : "6",text : "非必填"},{value : "7",text : "置空"}];
				break;
		}
		changeField.changeTypes = changeTypes;
	}
	
	//获取所选值控件类型
	function getByCtrlType(ctrlType){
		if(ctrlType=="select"||ctrlType=="checkbox"||ctrlType=="radio"||ctrlType=="multiselect"){
			return "select";
		}else if(ctrlType=="date"){
			return "date";
		}else{
			return "text";
		}
		return "text";
	}
	
	//获取下拉框、单选按钮、复选框的静态选项
	$scope.getSelectOpinion = function(pathName){
		if(pathName){
			return $scope.selectField[pathName];
		}
	}
	
	function getField(pathName){
		return $scope.fieldMap[pathName];
	}
	
	//设置字段其他信息
	$scope.setFieldOtherInfo = function(editField,pathName,elem,parentIndex,index){
		if(!pathName)return ;
		if(pathName.indexOf("separator.")==0){
			editField['groupTitle']  = getGroupTitle(pathName.replace('separator.',''));
		}else{
			var field = getField(pathName);
			if(field){
				if(typeof parentIndex =='number' && typeof index =='number'){
					console.log(pathName);
					 var pathArr=pathName.split('.');
					 var tablePath='';
					 var isSub='';
					   if(pathArr.length==3){
						   tablePath=pathArr[0]+'.'+pathArr[1];
						   isSub='true';
					   }else if(pathArr.length==2){
						   if(pathArr[0] !='separators') {
							   tablePath=pathArr[0];
							   isSub='false';
						   }
					   }
					   for(var z=0;z<$scope.datas.length;z++){
						   if(isSub=='false'){
							   $scope.gangedFields[parentIndex].changefieldData=$scope.datas;
						   }else{
							   if($scope.datas[z].path==tablePath)  $scope.gangedFields[parentIndex].changefieldData=[$scope.datas[z]];
						   }
						   if($scope.datas[z].path==tablePath){
							   $scope.gangedFields[parentIndex].choosefieldData=[$scope.datas[z]];
						   }
					   }				
					   $scope.gangedFields[parentIndex].isSub=isSub;
				}
				 
				editField['name'] = field.name;
				editField['dateName'] = field.dateName;
				editField['dataType'] = field.type;
				editField['ctrlType'] = field.ctrlType;
				editField['tableName'] = field.tableName;
				editField['boAttrId'] = field.boAttrId;
				editField['desc'] = field.desc;
				!$scope.$$phase &&$scope.$digest();
				var tdElem = getScopeElm(elem.$id);		         
				setTimeout(function() {  
		     		if(tdElem){
						var trElem = $(tdElem).parent().parent();
						var values = findChooseSelected(trElem);
						if(values.length>0){
							var changeElems = $(trElem).find("[ng-model='changeField.pathName']");
							for (var m = 0; m < changeElems.length; m++) {
								hideOrShowOpinions(changeElems[m],values);
							}
						}					
					 }
		            }, 50);  
		
			}
		}
	}
	
	function hideOrShowOpinions(elem,values){
		var options = elem.options;
		for (var i = 0; i < options.length; i++) {
			options[i].hidden = false;
		}
		for (var m = 0; m < values.length; m++) {
			for (var i = 0; i < options.length; i++) {
				if(values[m]==options[i].value){
					if(options[i].selected){
						$(elem).val("");
					}
					options[i].hidden = true;
				}
			}
		}
	}
	
	function findChooseSelected(trElem){
		var values = [];
		var chooseElems = $(trElem).find("[ng-model='chooseField.pathName']");
		if(chooseElems){
			for (var i = 0; i < chooseElems.length; i++) {
				if(chooseElems[i].value){
					values.push(chooseElems[i].value);
				}
			}
		}
		return values;
	}
	
	function getScopeElm(id) {
		 var elem;
		 $('.ng-scope').each(function(){
			 var s = angular.element(this).scope(),
			 sid = s.$id;
			 if(sid == id) {
				 elem = this;
				 return false;
			 }
		 });
		 return elem;
	}
	
	function getGroupTitle(pathName){
		var separators = $scope.separators;
		for (var i = 0; i < separators.length; i++) {
			if(separators[i].key==pathName){
				return separators[i].name;
			}
		}
		return "";
	}
	
	//获取自定义查询的选项
	function getDynamicOpinions(field,pathName){
		var customQuery = field.option.customQuery;
		var labelKey = customQuery.labelBind;
		var valueKey = customQuery.valueBind;
		var options =[];
		// 初始化param
		var queryParam = {alias:customQuery.alias};
		queryParam.querydata = {};
		DoQuery(queryParam,function(data){
			$.each(data,function(i,item){
				var option ={}; option.key=item[valueKey]; option.value =item[labelKey];
				options.push(option);
			})
			$scope.selectField[pathName] = options;
		});
	}
	
	$scope.showSelectDialog = function(chooseField,pathName){
		var field = getField(pathName);
		var alias = field.option.selector.type.alias;
		showDialog(field,chooseField);
	}
	
	function showDialog(field,chooseField) {
		var callBack = function(data, dialog) {
			dialog.dialog("close");
			var ids='';
			var names='';
			$(data).each(function(){
				if(ids&&this.id){
					ids+=",";
					names+=",";
				}
				if(this.id) ids+=this.id;
				if(this.name)names+=this.name;
			});
			$scope.$apply(function() {
				var key = getSelectBindType(field);
				if(key){
					chooseField.value = key=='id'?ids:names;
					chooseField.selectedNames = names;
				}
			});
		};
		
		var conf = {
			initData : chooseField.value
		};
		CustomDialog.openCustomDialog(getSelector(field.option.selector.type.alias),callBack,conf);
	}
	
	function getSelector(type){
		if("UserDialog"==type){
			return "userSelector";
		}else if("OrgDialog"==type){
			return "orgSelector";
		}else if("PostDialog"==type){
			return "postSelector";
		}
	}
	
	function getSelectBindType(field){
		var key = null;
		var binds = field.option.bind;
		for(var i=0;binds.length;i++){
			try {
				if(binds[i].json.path&&binds[i].json.path==field.path&&binds[i].json.name==field.name){
					key = binds[i].key;
					break;
				}
			} catch (e) {}
		}
		return key;
	}
	
	$scope.isSeparator = function(pathName){
		if(pathName){
			return pathName.indexOf("separator.")!=-1?true:false;
		}
	}
	
	
	$scope.openSetCascade = function(changeField){
		var field = {};
		field.opinions = $scope.getSelectOpinion(changeField.pathName);
		field.fieldKey = changeField.desc;
		field.boAttrId = changeField.boAttrId;
		field.cascade = changeField.cascade;
		var dialog = {};
		var def = {
			passConf : {field:field},
			title : '级联设置',
			width : 520,
			height : 380,
			modal : true,
			resizable : false,
			iconCls : 'icon-collapse',
			buttonsAlign:'center',
			buttons : [
					{
						text : '确定',
						iconCls:'fa fa-check-circle',
						handler : function(e) {
							$scope.$apply(function() {
								var win = dialog.innerWin;
								var data = win.getResult();
								if(data!="validError"){
									dialog.dialog('close');
									changeField.cascade = data;
								}
							});
						}
					}, {
						text : '取消',
						iconCls:'fa fa-times-circle',
						handler : function() {
							dialog.dialog('close');
						}
					} ]
		};

		var show = function() {
			dialog = $.topCall.dialog({
						src : __ctx+ '/form/formDef/bpmGangedSetCascade',
						base : def
					});
			return dialog;
		};
		show();
	}
	
}]);