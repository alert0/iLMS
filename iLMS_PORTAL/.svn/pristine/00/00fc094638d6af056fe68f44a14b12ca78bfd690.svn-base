var formApp = angular.module('formApp', ['base','arrayToolService','formDefModel','formDirective' ]);

formApp.controller('fieldCtrl',['$scope','baseService','formDefService','ArrayToolService','$timeout','$filter',function($scope,baseService,formDefService,ArrayToolService,$timeout,$filter){
	$scope.ArrayTool=ArrayToolService;
	$scope.bodefAttrs = [];
	$scope.fields = [];
	$scope.editingField = {};
	$scope.separators = [];

	$scope.isStepCreate = true;
	if(parent.formNextStep){
		$scope.isStepCreate = false;
	}
	
	
	var hh = $("#layoutCenter").height();
	$scope.fieldListHeight = (hh-80)+"px";
	$scope.fieldProptyHeight = (hh-135)+"px";
	
	$(window).resize(function () { 
		setTimeout(function(){
			var hh =$("body").height();
			$scope.fieldListHeight = (hh-125)+"px";
			AngularUtil.setData($scope);
		},100);
	});
	
	var orderBy = $filter('orderBy');
	$scope.order = function() {
	    $scope.fields = orderBy($scope.fields, "sn", false);
	};
	
	
	$(document).delegate(".togglechevron","click",function(){
		var isUp =$(this).hasClass("fa-chevron-up");
		$(this).removeClass(isUp?"fa-chevron-up":"fa-chevron-down")
		$(this).addClass(isUp?"fa-chevron-down":"fa-chevron-up");
	});
	$scope.selectBodef = {id:0};
	$scope.form = {name:'',desc:'',ganged:[]};
	if(typeId){
		$scope.form.typeId = typeId;
		$scope.form.type = type;
		$scope.form.name = name;
		$scope.form.key = key;
		$scope.boDefList = parseToJson(bos);
		$scope.form.desc = desc;
	}
	$scope.controlType = formDefService.controlType;
	$(document).delegate("input[ht-field-valid]","blur",function(){
		if($scope.editingField) {
			$scope.__checkFieldValid([$scope.editingField],true);
			$scope.$digest();
		}
	});
	var boTreeObject;
	$scope.__initBoTree = function(){
		var boIds=[];
		for(var i = 0 ;i <$scope.boDefList.length;i++){
			boIds.push($scope.boDefList[i].id);
		}
		$scope.boIds =boIds;
		
		var ztreeCreator = new ZtreeCreator('sysTypeTree',__ctx+"/bo/def/bODef/getBOTree")
		.setChildKey("children").setDataKey({idKey:"id",name:"desc",title:"desc"})
		.setCallback({beforeClick:nodeBeforeClick,onClick:nodeOnClick})
		.initZtree({ids:boIds.join(",")},function(treeObj){
				boTreeObject =treeObj 
			}); 

		function nodeOnClick(event, treeId, treeNode, clickFlag){
			var ary = [];
			var isFaChevronUp = true;
			var outTime = 600;
			ary.push($scope.treeNode2Field(treeNode));
			
			for(var int = 0,child; child=treeNode.children[int++];) {
				if(child.children && child.children.length>0 && child.nodeType=="sub"){
					child.parentName=treeNode.name;
					ary.push($scope.treeNode2Field(child));
				}
			}
			if(! $scope.fields) $scope.fields=[];
			if($scope.fields.length>0){
				for(var i = 0;i<$scope.fields.length;i++){
					if($scope.fields[i].path == treeNode.path){
						isFaChevronUp = false;
						outTime = 100;
						break;
					}
				}
			}
			
			for(var i = 0,f; f=ary[i++];){
				if($("."+f.entId+"ent").length==0){
					f.sn = $scope.fields.length;
					$scope.fields.push(f);
				}
			}
			
			initFieldsSeparator();
			
			!$scope.$$phase&&($scope.$digest());
			$scope.currentUpchevron =treeNode.id; 
			if(isFaChevronUp){
				$(".fa-chevron-up").click();
			}
			window.setTimeout(function(){
				$("[data-target='."+$scope.currentUpchevron+"ent']").click();
			},outTime);
		}

		function nodeBeforeClick(treeId, treeNode, clickFlag){
			// 如果是字段、则不允许点击、除非当设置选择器的字段时候
			if(!treeNode.children||treeNode.children.length==0 || treeNode.id=="0"){
				//设置绑定字段
				var field = $scope.treeNode2Field(treeNode);
				if($scope.currentBindTarget){
					var parent = treeNode.getParentNode();
					if($scope.editingField.path != treeNode.path){
						$.topCall.toast('警告！','绑定字段与目标字段BO不一致！     如果双方不是BO主表字段将会出现异常情况。');
					}
				    var path ="";
				    if(parent.type=="sub" || parent.nodeType=="sub"){
				    	path="item";
				    }
				    else{
				    	path = parent.boDefAlias;
				    }
				    field.path =path;
				    if($scope.editingField.ctrlType=="dic"){
				    	if($scope.editingField.path == field.path &&$scope.editingField.name == field.name ){
				    		$.topCall.toast('警告！','请绑定其它字段');
				    		return false;
				    	}
				    	$scope.editingField.bindDicName = field;
				    	!$scope.$$phase&&($scope.$digest());
				    	return ;
				    }
				    //如果是下拉框动态选项，则参数不能传入当前字段
				    if(($scope.editingField.ctrlType=="select" ||$scope.editingField.ctrlType=="multiselect") && $scope.editingField.option.choiceType=='dynamic'){
				    	if($scope.editingField.path == field.path &&$scope.editingField.name == field.name ){
				    		$.topCall.toast('警告！','请绑定其它字段');
				    		return false;
				    	}
				    }
				    
				    var s = angular.element($scope.currentBindTarget).scope();
				      s.s.json = field; 
				    !$scope.$$phase&&($scope.$digest());
				    return false;
				}
				//点击字段的情况，向表中添加字段
				for(var i=0,table;table=$scope.fields[i++];){
					// 如果同一张表则push进去
					if(field.path == table.path){
						//判断是否已经存在字段
						for(var j=0,f;f=table.children[j++];){
							if(f.name==field.name){
								$.topCall.toast('提示信息',table.desc+'属性字段'+field.desc+"已经存在！");
								return false;
							}
						}
						$scope.$apply(function(){
							field.sn = table.children.length;
							field.tableName= table.name;
							table.children.push(field);
						})
						return false;
					}
				}
				return false;
			}
			return true;
		}
	};
	
	if(!$scope.separators||$scope.separators.length==0){
		var obj = {};
		obj.key = "0";
		obj.name = "基本信息";
		obj.isShow = true;
		obj.isOpen = true;
		$scope.separators.push(obj);
	}
	if(!formId){
		$scope.__initBoTree();
	}else{
		//编辑表单时从后台获取表单信息
		baseService.postForm(__ctx+"/form/formDef/get",{id:formId}).then(
			function(data){
				$scope.form = data.bpmForm;
				var json =parseToJson($scope.form.expand);
				$scope.boDefList = json.boDefList;
				$scope.fields =json.fields;
				if(json.separators){
					$scope.separators = json.separators;
				}
				
				for (var int = 0,field;field=json.fields[int++];) {
					for (var i=0,f;f=field.children[i++];) {
						if(f.isEditing)f.isEditing=false;
						if(!f.separator)f.separator=0;
					}
				}
				
				!$scope.$$phase&&($scope.$digest());
				
				$scope.__initBoTree();
				window.setTimeout(function(){
					$(".fa-chevron-up").slice(1).click();  
				},100)
				
			},function(error){ }
		);
	}
	
	//监视选择业务对象的下拉框，选择某个业务对象时获取其属性
	$scope.$watch('selectBodef.id',function(newVal,oldVal){
		if(newVal!==oldVal){
			if(newVal){
				/*BODef.detail({id:newVal},function(data){
					if(data){
						$scope.selectBodef = data;
						if(data.boattributeList){
							$scope.bodefAttrs = data.boattributeList;
						}
					}
				});*/
			}
		}
	},false);
	
	//监听正在编辑的对象属性 如果是数值型  是否存在格式化货币字符 若存在 则显示出来
	$scope.$watch('editingField.option.numberFormat.coinValue',function(newVal,oldVal){
		if(newVal!==oldVal){
			if(newVal){
				$scope.editingField.option.numberFormat.isShowCoin=true;
			}
		}
	},false);
	
	//监听正在编辑的对象属性 如果是数值型  是否显示格式化货币字符 若显示  则联动显示千分位
	$scope.$watch('editingField.option.numberFormat.isShowCoin',function(newVal,oldVal){
		if(newVal!==oldVal){
			if(newVal){
				$scope.editingField.option.numberFormat.isShowComdify=true;
				if(!$scope.editingField.option.numberFormat.coinValue)$scope.editingField.option.numberFormat.coinValue ="￥";
			}else{
				$scope.editingField.option.numberFormat.isShowComdify = false;
				$scope.editingField.option.numberFormat.coinValue = "";
			}
		}
	},false);
	
	$scope.$watch('editingField.option.numberFormat.isShowComdify',function(newVal,oldVal){
		if(newVal!==oldVal){
			if(newVal){
				$scope.editingField.option.numberFormat.intValue = "";
				$scope.editingField.option.numberFormat.decimalValue = 3;
			}
		}
	},false);
	
	
	//监视控件类型修改
	$scope.$watch('editingField.ctrlType',function(newVal,oldVal){
		if(newVal&&newVal!==oldVal){
			if(newVal!="select"&&newVal!="checkbox"&&newVal!="radio"&&newVal!="multiselect"){
				delete $scope.editingField.option.choiceType;
				delete $scope.editingField.option.choice;
				delete $scope.editingField.option.customQuery;
			}
			else{
				//复选框和单选按钮没有动态选项
				if(newVal=="checkbox" || newVal=="radio"){
					$scope.editingField.option.choiceType='static';
				}
				($scope.editingField.option&&!$scope.editingField.option.choiceType)&&($scope.editingField.option.choiceType='static');
			}
			//当字段为数字，并且控件选择为非单行文本时，清空数字格式化设置
			if(oldVal=='onetext' && $scope.editingField.type=='number'){
				delete $scope.editingField.option.numberFormat;
				delete $scope.editingField.validRule.maxvalue;
				delete $scope.editingField.validRule.minvalue;
				$scope.editingField.numberBigger = false;
				$scope.editingField.numberLess = false;
			}
			if(oldVal=='date'){
				delete $scope.editingField.daterangestart;
				delete $scope.editingField.daterangeend;
				delete $scope.editingField.validRule.daterangestart;
				delete $scope.editingField.validRule.daterangeend;
				delete $scope.editingField.option.dataFormat;
				delete $scope.editingField.option.showCurrentDate;
			}
			if(newVal!='onetext'&&newVal!='multitext'&&newVal!='hidtext'){
				delete $scope.editingField.option.statFun;
				delete $scope.editingField.datecalc;
			}
			if(newVal='officeplugin'){
				if(!$scope.editingField.option.hasOwnProperty('height')){
					$scope.editingField.option.height = 600;
				}
				if(!$scope.editingField.option.hasOwnProperty('width')){
					$scope.editingField.option.width = 800;
				}
			}
		}
	},false);
	$scope.$watch("selectors",function(newVal,oldVal){
		if(newVal!==oldVal){
			changeFieldBind('selectors');
		}
	});

	$scope.$watch("editingField.option.choiceType",function(newVal,oldVal){
		if(newVal!==oldVal){
			var json = $.extend({},$scope.editingField);
			if(newVal=='static'){
				(!$scope.editingField.option.choice||$scope.editingField.option.choice.length==0)&&($scope.editingField.option.choice=[{}]);
				delete $scope.editingField.option.customQuery;
			}
			else if(newVal=='dynamic'){
				(!$scope.editingField.option.customQuery)&&($scope.editingField.option.customQuery={});
				delete $scope.editingField.option.choice;
			}
		}
	},false);
	$scope.setCurrentCustomQuery = function(){
		if(!$scope.editingField.option.customQuery.alias||!$scope.customQuerys){
			return;
		}
		$.each($scope.customQuerys,function(i,item){
			if(item.alias ==$scope.editingField.option.customQuery.alias) {
				$scope.editingField.option.customQuery =$.extend({},item);
				return;
			}
		});
		if(typeof $scope.editingField.option.customQuery.conditionfield=='string'){
			$scope.editingField.option.customQuery.conditionfield = parseToJson($scope.editingField.option.customQuery.conditionfield);
			$scope.editingField.option.customQuery.resultfield = parseToJson($scope.editingField.option.customQuery.resultfield);
		}
		 //删除掉无效的参数
		var bind= [];
		$.each($scope.editingField.option.customQuery.conditionfield,function(i,item){
			if(item.defaultType==1) {
				bind.push(item);
			}
		});
		$scope.editingField.option.bind=bind;
		
	}
	/**
	 * 监听 editingField.option.selector.type 的变化 
	 *  editingField.option.selector.type 的值格式为 
	 *  {
	 *  	id:'1000001002' ,  			 //对应的选择器对像的id  
	 *  	type:"selectors" 			 //customDialogs或者selectors
	 *  }
	 */
	$scope.$watch("editingField.option.selector.type",function(newVal,oldVal){
		if(newVal!==oldVal){
			changeFieldBind();
		}
	},true);
	/**
	 * 当选择器对象的选项改变之后  editingField.option.bind的值也随之改变 
	 * editingField.option.bind的格式为 [{
								id : 110011,
								key: "userid",
								value: "用户id"
							},{
								id : 110011,
								key: "username",
								value: "用户名称"
							}]
	 */
	function changeFieldBind(type){
		if(!$scope.editingField||!$scope.editingField.option||!$scope.editingField.option.selector) return;
		var json = $scope.editingField.option.selector.type;
		var alias = json.alias  ;
		if(!type){
			type = json.type ;
		}
		if(!$scope[type]||!alias) return ;
		var oldBind = $scope.editingField.option.bind;
		if(oldBind&&oldBind.length>0&&$scope.editingField.option.bind[0].alias==alias) return;
		var keyValues = [];
		switch(type){
			case "selectors":
				for(var i =0 ; i< $scope[type].length;i++){
					if(alias == $scope[type][i].alias){
						var groupField = parseToJson($scope[type][i].groupField);
						for(var j =0 ; j<groupField.length;j++){
							keyValues.push({
								alias : alias,
								key: groupField[j].key,
								value: groupField[j].name
							});
						}
						break;
					}
				}
				break;
			case "customDialogs":
				for(var i =0 ; i< $scope[type].length;i++){
					if(alias == $scope[type][i].alias){
						var resultfield = parseToJson($scope[type][i].resultfield);
						for(var j =0 ; j<resultfield.length;j++){
							keyValues.push({
								alias : alias,
								key: resultfield[j].comment,
								value: resultfield[j].comment
							});
						}
						break;
					}
				}
				break;
		}
		$scope.editingField.option.bind=keyValues;
	}
	
	$scope.objectIsEmpty = function (obj){
		// 内容是是否为空
		if(typeof(obj)==undefined||obj==null||obj=='' || (obj&&obj.length == 0)){
			return true;
		}else{
			return false;
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
					attrLength :   node.nodeType=="field"?node.attrLength:0,//字段长度
					decimalLen :   node.nodeType=="field" && node.dataType=='number'?node.decimalLen:0,//小数位长度
					validRule:	{"required":isRequired},
					required: isRequired,
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
			if(field.type=="varchar"){
				try {
					//添加对字符串长度验证
					field.validRule["maxLength"] = node.attrLength;
				} catch (e) {}
				
			}
			
			return field
		}
	} 
	

	
	//打开隐藏的div，以dialog的方式
	$scope.editFieldCheck = function(rule){
		$scope.editingRule={};
		if(rule==null) {
			$scope.newRule=true;
			editingRule={};
		}else{
			editingRule=rule;
			$scope.editingRule.name=rule.name;//这样赋值就不会得到索引，相当于一个新的{}
			$scope.editingRule.text=rule.text;
			$scope.editingRule.tip=rule.tip;
			$scope.newRule=false;
		}
		var url= __ctx + '/form/formDef/fieldCheckDialog';
		var dialog = {};
		var def = {
			passConf : {editingRule:$scope.editingRule},
			title : '字段验证',
			width : 700,
			height : 400,
			modal : true,
			resizable : false,
			iconCls : 'icon-collapse',
			buttonsAlign:'center',
			buttons : [{
                text:'确定',
            	iconCls:'fa fa-check-circle',
                handler:function(){
                	$scope.editingRule = dialog.innerWin.triggerScopeFun("getEditingRule");
                	if(!$scope.editingRule) return ;
                	$scope.saveEditingRule();
                	dialog.dialog('close');
                }
            },{
                text:'取消',
            	iconCls:'fa fa-times-circle',
                handler:function(){
                	dialog.dialog('close');
                }
            }]
		};
		var show = function() {
			dialog = $.topCall.dialog({src : url,base : def});
			return dialog;
		};
		show();
	};
	
	var editingRule={};//这个是保存着编辑字段验证的索引
	
	//保存一个字段验证
	$scope.saveEditingRule=function(notdoValid){
		if(!notdoValid){// TODO 校验 
			$.topCall.error("请检查表单数据是否正确");
			return false ;
		}
		//初始化
		if($scope.editingField.validRule==null) {
			$scope.editingField.validRule={};
		}
		if($scope.editingField.validRule.rules==null) {
			$scope.editingField.validRule.rules=[];
		}
		if($scope.newRule){
			$scope.editingField.validRule.rules.push($scope.editingRule);
		}else{	
			editingRule.name=$scope.editingRule.name;
			editingRule.text=$scope.editingRule.text;
			editingRule.tip=$scope.editingRule.tip;
		}
		$scope.editingField.validRule.rules.unique();
		editingRule=null;
		$scope.editingRule={};
		$scope.newRule=false;
		try{if(!$scope.$parse) $scope.$digest();}catch(e){}
	};
	
	//选择了一个常用验证
	$scope.selectCommonRule=function(key){
		if(key==null||key=="") return;
		var rule = {};
		for(var i = 0 ; i < $scope.common_ruleList.length ; i++){
			if($scope.common_ruleList[i].text == key) {
				rule = $scope.common_ruleList[i];
				break;
			}
		}
		$scope.newRule=true;
		$scope.editingRule={};
		$scope.editingRule.name=rule.name;
		$scope.editingRule.tip=rule.msg;
		$scope.editingRule.text=rule.text;
		$scope.editingRule.isDefault=rule.isDefault;
		$scope.saveEditingRule(true);
		$scope.commonRule="";
		this.commonRule = "";
	};
	
	//当前编辑字段变更方法
	$scope.editing = function(field,tag,time){
		if(field==$scope.editingField)return;
		if((field.children)&&!tag) {
			$scope.__initEditing(field,time);
			return;
		}
		$scope.__initEditing(field,time);
		//把字段验证字符串变成jsonarray
		if($scope.editingField.validRule) {
			$scope.editingField.validRule=angular.fromJson(field.validRule);
		}
		if(field.ctrlType=="date" && !$scope.editingField.option.dataFormat){
			$scope.editingField.option.dataFormat =field.format?field.format:"yyyy-MM-dd";
		}
	};
	
	$scope.__validField = function(field){
		var validField="";
		if(field.children) validField=field.children;
		else if($scope.editingField.attrType=="hasmany") validField=[$scope.editingField];
		else if($scope.editingField.attrType!="hasmany"&&$scope.editingField.children) validField=$scope.editingField.children;
		else  validField=[$scope.editingField];
		$scope.__checkFieldValid(validField,true);
	};
	$scope.__initEditing=function(field,time){
		if($scope.editingField) {
			$scope.editingField.isEditing=false;
			$scope.__validField(field);
		}
		field.isEditing=true;
		$scope.editingField = field;
		if(field.fieldType=='field'){
			window.setTimeout(function(){
				if($("#formDetial.collapse.in")[0])$("[data-target='#formDetial']").click(); 
			},time||0);
		}
	};
	$scope.addOption = function(){
		$scope.editingField.option.choice.push({});
	};
	$scope.removeOption = function(opt){
		$scope.editingField.option.choice.remove(opt);
	};

	$scope.arrayToolUp = function(index){
		$scope.ArrayTool.up(index,$scope.fields);
		for(var k = 0 ; k < $scope.fields.length ; k++){
			$scope.fields[k].sn = k;
		 }
	}
	$scope.getDateTargets = function(dateField){
		var targetFields=[];
		for(var k = 0,table ; table=$scope.fields[k++];){
			if(table.path==dateField.path){
				for(var i = 0,field ; field=table.children[i++];){
					if(field.ctrlType=='date' && field.name!=dateField.name){
						if(table.fieldType=="sub")field.dateName = "item."+field.name
						else field.dateName = "data."+field.path+"."+field.name;
						targetFields.push(field);
					}
				}
			}
		 }
		
		return targetFields;
	}
	$scope.arrayToolDown = function(index){
		$scope.ArrayTool.down(index,$scope.fields);
		for(var k = 0 ; k < $scope.fields.length ; k++){
			$scope.fields[k].sn = k;
		 }
	}
	//移除分组、复合类型
	$scope.removeGroup = function(field,$event){
		$scope.fields.remove(field);
		 for(var k = 0 ; k < $scope.fields.length ; k++){
			 $scope.fields[k].sn = k;
		 }
		$scope.editingField = {};
		if ($event.stopPropagation) $event.stopPropagation();
	    if ($event.preventDefault) $event.preventDefault();
	    $event.cancelBubble = true;
	    $event.returnValue = false;
	};
	
	//清空字段列表
	$scope.clearList = function(){
		$.topCall.confirm('提示信息', '确认清空字段列表吗？', function(r) {
			if (r) {
				$scope.$apply(function(){
					$scope.fields=[]; 
					$scope.form.ganged = [];
                });
			}
		});
	};
	//选择不同数据类型的字段时，其可选择的控件类型也不同
	$scope.filterControlType = function(val){
		var result = false;
		if(val&&$scope.editingField){
			var type = $scope.editingField.type;
			if(val.supports.indexOf(type)>-1){
				result = true;
			}
		}
		return result;
	};
	$scope.radioChange = function(list){
		angular.forEach(list,function(item){
			delete item["selected"];
		});
	};
	//保存表单
	$scope.save = function(){
		var form = $scope.form;
		if(!$scope.formEdit.$valid){
			$.topCall.error("表单校验不通过，请检查！");
			return ;
		}
		if($scope.fields.length==0){
			$.topCall.error("请至少添加一个字段！");
			return ;
		}else{
			//if(!$scope.__checkFieldValid(form.bpmFormFieldList)) return ;
		}
		form.expand ={};  
		form.expand.separators = $scope.separators;
		form.expand.fields = $scope.fields;
		form.expand.boDefList =$scope.boDefList;
		baseService.post(__ctx+"/form/formDef/save",form).then(
		 	function(data){
		 		if(data.result){
		 			if(parent.newForm){
		 				parent.toSelectTemplate(form.key);
		 			}else{
		 				var msg = "保存成功" ;
			 			$.topCall.message({base:{type:'alert',title:'温馨提示',msg:msg,fn:function(r){
			 				if(window.opener)window.opener.reload();
		 					window.close();
			 				}
			 			}});
		 			}
		 		}else{
		 			$.topCall.error(data.message);
		 		} 
		 	},
			function(status){
		 		$.topCall.error("出现异常："+status);
	    	 });
		
	};
	$scope.delAttr=function(attrPath){
		if(attrPath.indexOf('editingField.validRule.date')>-1){
			$scope.removeAllDate(attrPath);
		}else{
			eval("delete $scope."+attrPath);
			!$scope.$$phase&&$scope.$digest();
		}
	}
	
	$scope.removeAllDate=function(attrPath){
		eval("delete $scope.editingField.validRule.daterangestart");
		eval("delete $scope.editingField.validRule.datemorethan");
		eval("delete $scope.editingField.validRule.daterangeend");
		eval("delete $scope.editingField.validRule.datelessthan");
		var validRule=attrPath.split('.')[2];
		if($scope.editingField.daterangestart && validRule!='daterangestart'){
			$scope.editingField.daterangestart=false;
		}
        if($scope.editingField.datemorethan && validRule!='datemorethan'){
        	$scope.editingField.datemorethan=false;
		}
        if($scope.editingField.daterangeend && validRule!='daterangeend'){
        	$scope.editingField.daterangeend=false;
		}
        if($scope.editingField.datelessthan && validRule!='datelessthan'){
        	$scope.editingField.datelessthan=false;
		}
		!$scope.$$phase&&$scope.$digest();
	}
	
	/*--------->联动规则start<---------------*/
	$scope.conditions=[];
	$scope.results=[];
	
	/**
	 * 校验表单是否填写正确字段
	 */
	$scope.__checkFieldValid = function(fieldList,noCircle){
		for(var i =0,c;c=fieldList[i++];){
			if(!c.name){
				c.isError=true;
				!noCircle&&$.topCall.error("请填写字段名称");
				!noCircle&&$scope.editing(c,null,300);
				return ;
			}
			if(!c.desc){
				c.isError=true;
				!noCircle&&$.topCall.error("请填写字段标签！");
				!noCircle&&$scope.editing(c,null,300);
				return ;
			}
			if(c.attrType=="hasmany"||c.attrType=="hasone"){
				if (c.children) {
					if (!$scope.__checkFieldValid(c.children, noCircle)){
						var ai = $(".editing-error").closest(".box").children(".field-container").find(".expand")[0];
						ai&&ai.click();
						return;
					}
				}
			}
			if(c.ctrlType=="select"||c.ctrlType=="checkbox"||c.ctrlType=="radio"||c.ctrlType=="multiselect"){
				if(c.option && c.option.choice){
					for(var j =0,d;d=c.option.choice[j++];){
						if(d.key==undefined||d.key.trim()==""||d.value==undefined||d.value.trim()=="") {
							c.isError=true;
							!noCircle&&$.topCall.error("请检查名为\""+c.name+"\"的字段选项列表是否正确");
							!noCircle&&$scope.editing(c,null,300);
							return;
						};
					};
				}
			}else if(c.ctrlType=="selector"){
				if(!c.option.selector||!c.option.selector.type){
						c.isError=true;
						!noCircle&&$.topCall.error("请选择名为\""+c.name+"\"的选择器类型");
						!noCircle&&$scope.editing(c,null,300);
						return ;
					};
			}else if(c.ctrlType=="office"){
				if(!c.option.office){
					c.isError=true;
				}else {
					if(!c.option.office.x||!c.option.office.x.value||!c.option.office.x.unit)
						 c.isError=true;
					else if(!c.option.office.y||!c.option.office.y.value||!c.option.office.y.unit)
						 c.isError=true;
					else c.isError=false;
				};
				if(c.isError){
					!noCircle&&$.topCall.error("请选择名为\""+c.name+"\"的选择器类型");
					!noCircle&&$scope.editing(c,null,300);
					return ;
				}
			}
			c.isError=false;
		}
		return true;
	};
	
	
	//------------->以下是数组
	//常用的字段验证列表
	$scope.common_ruleList=[];
	for(var i = 1 ;i < ConstUtil.rules.length ; i++){
		var tempRule = ConstUtil.rules[i];
		if(tempRule.formRule){
			$scope.common_ruleList.push({
				name : tempRule.title,
				text : tempRule.name,
				msg:tempRule.msg,
				isDefault:true
			})
		}
	}
	$scope.orderByBodefAttrs=function(attr){
		var temp=attr.desc||attr.name;
		return temp.length;
	};
	
	//选中需要绑定bo属性的控件  
	$scope.focusBind = function(event){
		//同时只能有一个是选中状态，所以选中前先将之前选中的状态去除
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
	//为是否需要在页面上显示DIV内容做判断的方法
	$scope.isShowDIVByRule=function(keys){
		if(!$scope.editingField)return false;
		return isInArray(keys.split(","),$scope.editingField.ctrlType);
	}; 
	

	$scope.statFun = function() {
		var dialog = {};
		var def = {
			passConf : {scope:$scope},
			title : '统计函数设计对话框',
			width : 750,
			height : 500,
			modal : true,
			resizable : false,
			iconCls : 'icon-collapse',
			buttonsAlign:'center',
			buttons : [
					{
						text : '确定',
						iconCls:'fa fa-check-circle',
						handler : function(e) {
							dialog.innerWin.clickOk();
							dialog.dialog('close');
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
						src : __ctx+ '/js/platform/system/dialog/MathExpEditor.jsp',
						base : def
					});
			return dialog;
		};
		show();
	}
	
	$scope.removeFieldTags =function(fields){
		var noSelected = true;
		for(var i=fields.length-1;i>=0;i--){
			var obj=fields[i];
			if(obj.selected){
				noSelected = false;
				fields.remove(obj);
			}
		}
		if(noSelected){
			$.topCall.toast('提示！','请选择要移除的字段');
		}
	}
	
	$scope.openGanged = function(){
		var dialog = {};
		var def = {
			passConf : {scope:$scope},
			title : '表单联动',
			width : 950,
			height : 520,
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
									$scope.form.ganged = data;
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
						src : __ctx+ '/form/formDef/gangedSetList',
						base : def
					});
			return dialog;
		};
		show();
	}
	
	//多选时批量更新选择字段的分组
	$scope.updSelectFieldSep = function(separator){
		if(separator){
			var tabs = $scope.fields;
			for (var i = 0; i < tabs.length; i++) {
				var tab = tabs[i];
				if(tab.type=='main'){
					var fields = tab.children;
					for (var m = 0; m < fields.length; m++) {
						if(fields[m].selected){
							fields[m].separator = separator;
						}
					}
				}
			}
		}
	}
	
	
	$scope.openSeparator = function(){
		var dialog = {};
		var def = {
			passConf : {separators:$scope.separators},
			title : '编辑分组',
			width : 600,
			height : 420,
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
									for (var i = 0; i < data.length; i++) {
										data[i].key = data[i].key.toString();
									}
									$scope.separators = data;
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
						src : __ctx+ '/form/formDef/separatorList',
						base : def
					});
			return dialog;
		};
		show();
	}
	
	function initFieldsSeparator(treeObj){
		for (var int = 0,field;field=$scope.fields[int++];) {
			if(field.relation=='main'){
				for (var i=0,f;f=field.children[i++];) {
					if(!f.separator)f.separator=0;
				}
			}
		}
	}
}]);