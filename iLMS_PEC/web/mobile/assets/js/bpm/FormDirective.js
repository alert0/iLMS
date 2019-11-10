/**
 * 页面表单通用指令。
 */
var directive = angular.module("formDirective", [ "base","formServiceModule" ])
/**
 * 校验指令. 用法： <input type="text" ng-model="user.name"
 * ht-validate="{require:true}" />
 * 
 * 具体的规则： /js/common/CustomValid.js 的rules 内置规则。
 */
.directive('htValidate', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			var customValidator = function(value) {
				var validate = attr.htValidate;
				var validateJson = eval('(' + validate + ')');
				if (!validate)
					return true;
				handlTargetValue(validateJson);
				 var validity =  $.fn.validRules(value,validateJson,element,ctrl.$dirty);
                 ctrl.$error=validity.errMsg
                 ctrl.$setValidity("customValidate", validity._valid);
				return validity ? value : undefined;
			};

			ctrl.$formatters.push(customValidator);
			ctrl.$parsers.push(customValidator);

			// 获取比较目标字段的值。 所有比较的都包含target对象eg:{eq:{target:data.mian.name}}
			var handlTargetValue = function(validateJson) {
				for (key in validateJson) {
					if (validateJson[key].target) {
						validateJson[key].targetVal = eval("scope." + dateRange.target);
					}
				}
			}
		}
	};
} ])
/**
 * 表单的常用保存指令，看例子说话： 
 * <input type="button" ng-model="data" ht-save="bOEnt/save"/>
 * 
 * ps:<form name ="form">元素必须是 name ="form"
 * 
 * 参数介绍： ng-model :代表保存对象 ht-save :是保存的url地址
 * 
 * 后台controller： 可以参照 BOEntController.save方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获保存后抛出的事件进行个性化操作（也可以不捕获）
 * eg:
 * $scope.$on("afterSaveEvent",function(event,data){
 * console.info("我捕获了afterSaveEvent事件"); console.info(data); });
 * data.r是选择的"是"true 和 "否"false
 * beforeSaveEvent一样
 */
.directive('htSave', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			element.on("click", function() {
				if (!scope.form.$valid) return;
				
				var configObj={};
				//读取配置。
				var config=attr.config;
				if(config){
					configObj=angular.fromJson(config);
				}
				
				var data={};//数据
				data.pass = true;//前置事件用来控制能否提交的参数
				scope.$root.$broadcast('beforeSaveEvent',data);
				// 表单验证
				if (!data.pass) return;
				
				var postData=scope[attr.ngModel];
				
				var rtn = baseService.post(attr.htSave, postData);
				rtn.then(function(data) {
					if (data.result == 1) {
						data.postData=postData;
						//如果设置了配置。
						if(configObj.afterSave){
							eval("scope." +configObj.afterSave +"(data)" );
							return;
						}
						
						$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
							data.r=r;
							// 发布保存事件用于给用户自定义操作
							scope.$root.$broadcast('afterSaveEvent', data);
						});
					} else {
						$.topCall.error(data.message, data.cause);
					}
				}, function(status) {
					$.topCall.error("请求失败");
				});
			});
		}
	};
} ])
/**
 * 表单的常用初始化数据指令，看例子说话： 
 * <form name="form"  ht-load="bOEnt/getObject?id=${param.id}" ng-model="data"></form>
 * ps:当初始化对象为空时不作任何操作的 参数介绍： ht-load ：能返回一个对象的请求后台地址 
 * ng-model :把获取的对象赋值到该对象
 * 
 * 后台controller： 可以参照 BOEntController.getObject方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获初始化数据后抛出的事件进行个性化操作（也可以不捕获）eg:
 * $scope.$on("afterLoadEvent",function(event,data){
 * console.info("我捕获了afterLoadEvent事件"); console.info(data); });
 */
.directive('htLoad', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			if (!attr.htLoad || attr.htLoad == "") {
				return;
			}
			var rtn = baseService.post(attr.htLoad);
			rtn.then(function(data, status) {
				if (!data) return;
				scope[attr.ngModel] = data;
				scope.$root.$broadcast('afterLoadEvent', data);// 发布加载事件用于给用户自定义操作
			}, function(status) {
				$.topCall.error("请求失败");
			});
		}
	};
} ])
/**
 * 数据源下拉框选择，
 * eg <div ht-ds-selector="data.dsName"></div> 会把选择的别名返回到指定对象中
 */
.directive('htDsSelector', [ 'baseService', function(baseService) {
	return {
		scope : {
			htDsSelector : "=",
			ngDisabled:"="
		},
		link : function(scope, element, attr, ctrl) {
			var rtn = baseService.post(__ctx + '/system/sysDataSource/getDataSourcesInBean');
			rtn.then(function(data) {
				scope.dataSourcesInBean = data;
			}, function(status) {
				$.topCall.error("请求失败");
			});
		},
		template : '<select class="inputText" ng-model="htDsSelector" ' +
			' ng-options="m.alias as m.name for m in dataSourcesInBean"></select>',
		replace : true
	};
} ])
/**
 * ht-select-ajax 动态加载select的options数据 
 * 例如： 
 * <select ng-model="form.typeId"  ng-options="(m.id) as m.text for m in formTypeList"
 * 	ht-select-ajax="{url:'${ctx}/platform/system/sysType/getByGroupKey.ht?groupKey=FORM_TYPE',field:'formTypeList'}">
 * <option value="">请选择</option> 
 * </select> 
 * 传入参数 url ： 请求地址 
 * field ： formTypeList
 * 对应于 ng-options 中的 formTypeList （两者必须相同）
 */
.directive('htSelectAjax', function($injector) {
	return {
		restrict : 'A',
		require :'?ngModel',
		link : function(scope, element, attrs,ctrl) {
			var baseService = $injector.get("baseService");
			var option = attrs["htSelectAjax"];
			option = eval("(" + option + ")");
			if (scope.$root.$$childHead[option.field])
				return;
			var def = baseService.get(option.url);
			def.then(function(data) {
				if (option.dataRoot) {
					data = data[option.dataRoot];
				}
				scope[option.field] = data;
				scope.$root.$$childHead[option.field] = scope[option.field];
				// select option 生成后，让控件从新更新视图
				window.setTimeout(function(){
					ctrl.$render();
				},10)
			}, function() {
			});
		}
	};
})
/**
 * 下拉选择框。
 * 属性说明：
 * 	ht-select：指令 属性值为scope的数据路径。
 *  permission：权限，值为r,w
 *  options:可选值
 * <select ht-select="data.main.hobbys" permission="w" ng-model=""></select>
 */
.directive('htSelect',['commonService', function(commonService) {
	 return {
		restrict : 'AE',
		require: "ngModel",
		scope:{
			htSelect:"="
		},
		link: function(scope, element, attrs,ctrl){
			var isMultiple =attrs.multiple!=undefined;
			if(!isMultiple) return;
			ctrl.$formatters.push(function(value){
				 if(value) return value.split(",")
				 return []
			});
	        ctrl.$parsers.push(function(value){
	        	 if(value&&value.length>0) return value.join(",")
	        	 return "";
	        });
	        $.isFunction($.fn.select2) && $(element).select2({language: "zh-CN"});
		}
			       
}}])
/**
 * 汉字转拼音，例如 A 填写了 你好，当A失去焦点时，B自动填充为nh fullpinyin:1 全拼，不填0默认首字母 
 * eg: 
 * <input  type="text" ng-model="chinese" value=汉字/> 
 * <input type="text" ng-model="pingyin"  ht-pinyin="chinese" type="0" fullpinyin="1"/>
 */
.directive('htPinyin', [ 'baseService', function(baseService) {
	return {
		restrict : 'A',
		require : "ngModel",
		scope : {
			ngModel : "="
		},
		link : function(scope, elm, attrs) {
			var type = attrs.fullpinyin || 0;

			// 利用jq方法绑定失去焦点事件
			$("[ng-model='" + attrs.htPinyin + "']", elm.parent().closest(".ng-scope")).blur(function() {
				if (elm.val()) return;
				
				var obj = $(this);

				var value = obj.val();
				if (!value) return;

				var param = { Chinese : value, type : type };
				var rtn = baseService.postForm(__ctx + "/pinyinServlet", param);
				rtn.then(function(data) {
					scope.ngModel = data;
					//延迟触发blur,ngModel 还未将值设置进input
					window.setTimeout(function(){
						elm.trigger("blur");
					},100);
				}, function(errorCode) {
				});
			});
		}
	};
} ])
/**
 * 富文本框指令：
 * <ht-editor config="editorConfig" ng-model="content" height="100"></ht-editor>
 * ng-model：scope 数据表达式
 * config:编辑器配置
 * height:文本框高度
 * 
 * 使用方法：
 * 
 * <body ng-controller="ctrl">
 *	<div config="editorConfig" ht-editor ng-model="content1" style="width: 80%">测试</div>
 *	<script>
 *		angular.module('example',['formDirective']).controller('ctrl', function ($scope,$sce) {
 *    		$scope.content1="hello world";
 * 		});
 *  </script>
 * </body>
 */
.directive('htEditor', function() {
	return {
		restrict : 'AE',
		transclude : true,
		template : '',
		require : '?ngModel',
		scope : {
			config : '='
		},
		link : function(scope, element, attrs, ngModel) {
			var editor = new UE.ui.Editor(scope.config || {
				focus : true,
				toolbars : [ [ 'source', 'undo', 'redo', 'bold', 'italic', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist' ] ],
				initialFrameHeight : attrs.height || 150
			});
			editor.render(element[0]);
			if (ngModel) {
				// Model数据更新时，更新百度UEditor
				ngModel.$render = function() {
					try {
						editor.setContent(ngModel.$viewValue);
					} catch (e) {
					}
				};
				// 设置内容。
				editor.ready(function() {
					ngModel.$viewValue && editor.setContent(ngModel.$viewValue);
				});
				// 百度UEditor数据更新时，更新Model
				editor.addListener('contentChange', function() {
					setTimeout(function() {
						scope.$apply(function() {
							var content = editor.getContent();
							if(attrs.htEditor=="getContentTxt"){
								// 获取文本， 不带html格式的
								content = editor.getContentTxt();
							}
							ngModel.$setViewValue(content);
						})
					}, 0);
				});
			}

		}
	}
})
/**
 * 功能说明:
 * htCheckbox 指令用于收集checkbox数据。
 * 在页面中使用 
 * 	属性指令：ht-checkbox
 * 	对应的值为scope对应的数据data.users。
 * 示例:
  	<div >
        <input type="checkbox" ht-checkbox ng-model="data.users"  value="1" />红
		<input type="checkbox" ht-checkbox ng-model="data.users"   value="2" />绿
		<input type="checkbox" ht-checkbox ng-model="data.users"   value="3" />蓝
		<span>{{data.users}}</span>
   </div>
   <script>
       var app=angular.module("app",["directive"]);
		app.controller('ctrl', ['$scope',function($scope){
			$scope.data={users:"1,2"};
			$scope.getData=function(){
				console.info($scope.data.users)
			}
		}])
    </script>
 */
.directive('htCheckbox', [ function() {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			var checkValue = attrs.value;
			
			//modelValue转viewValue的过程
			ctrl.$formatters.push(function(value) {
				if (!value) return false;
				
				var valueArr = value.split(",");
				if (valueArr.indexOf(checkValue) == -1) return false;
				
				return true;
			});
			
			//viewValue转modelValue的过程
			ctrl.$parsers.push(function(value){
				var valueArr = [];
				if(ctrl.$modelValue){
					valueArr = ctrl.$modelValue.split(",");
				}
				var index = valueArr.indexOf(checkValue);
				if (value) {
					// 如果checked modelValue 不含当前value
					if (index == -1) valueArr.push(checkValue);
				} else {
					if (index != -1) valueArr.splice(index, 1);
				}
				
				return valueArr.join(",");
			});
		}
	}
}])
/**
 * 在对象上显示提示框。 使用方法: <div class="flowNode"
 * ht-tip="{content:getMenuContent,alignX:'center',alignY:'bottom',offsetY:10}" ></div>
 */
.directive('htTip', function($injector) {
	return {
		restrict : 'A',
		scope : {
			htTip : "="
		},
		link : function(scope, element, attrs) {
			var defaultSetting = {
				hide : {
					event : 'mouseleave',
					leave : false,
					fixed : true,
					delay : 100
				},
				style : {
					classes : 'qtip-default  qtip qtip-bootstrap qtip-shadow'
				}
			};
			var setting = angular.extend(scope.htTip || {}, defaultSetting);
			element.qtip(setting);
		}
	};
})

/**
 * 列表全选指令，这个指令用于 ng-repeat 列表全选，反选。 指令的用法： 在列表项checkbox控件上增加指令
 * ht-checked="selectAll",属性值为全选checkbox的 ng-model属性。 
 * eg: <input type="checkbox"  ng-model="selectAll"/>
 * 
 * <tr  ng-repeat="item in data.defNameJson  track by $index">
 * <td> <input ng-model="item.selected" type="checkbox" ht-checked="selectAll">
 * </td>
 * </tr>
 * 
 */
.directive('htChecked', function() {
	return {
		restrict : 'A',
		require : "ngModel",
		scope : {
			ngModel : "="
		},
		link : function(scope, elm, attrs, ctrl) {
			scope.$parent.$watch(attrs.htChecked, function(newValue, oldValue) {
				if (newValue == undefined)
					return;
				ctrl.$setViewValue(newValue);
				ctrl.$render();
			});
		}
	};
})
/**
 * eg:	
 * <span ht-ztree="services" child-key="soapBindingOperationInfos" 
 * callback="{onDblClick:onOperationDbClick,onClick:onSingleClick}"></span>
 * @说明 默认为simpleData模式。数据格式为list 配置id，pid的key，当然我们系统默认是匹配的。
 * @dataKey:{idKey:"idKEY名称",pIdKey:"",name:"显示名称",title,rootPid}
 * idKey 默认 id ； pIdKey 默认 parentId ；name 默认 name；title 默认 name
 * 
 * @callback:{beforeClick：beforeClick,onClick:onClick,beforeCheck:beforeCheck}
 * @setCheckbox="" or { "Y": "p", "N": "s" }
 * @childKey 默认children 或者设置
 * @expandLevel="number" 展开层级
 * @url:"" 远程调用的情况。默认直接获取htZtree 的数据
 * @param:eg {name:data.name}; 远程调用提供的参数
 */
.directive('htZtree',['baseService',function(baseService) {
    return {
        restrict: 'A',
        scope:{
        	htZtree:"=",
        	callback:"="
        },
        link: function(scope, element, attrs) {
        	element.addClass("ztree");
        	if(!element.prop("id")) {
        		element.prop("id",Math.random().toString(36).substring(2, 15));
        	}
        	var param = attrs.param ?  eval('(' + attrs.param + ')') : {};
        	var dataKey=attrs.dataKey? eval('(' + attrs.dataKey + ')'):{};
        	
        	scope._setting = {
        			data: {
        				key:{
        					name: dataKey.name || "name",
        					title:  dataKey.title || "name"
        				},
        				simpleData: {
        					enable: true,
        					idKey: dataKey.idKey || "id",
        					pIdKey: dataKey.pIdKey || "parentId",
        					rootPId: dataKey.rootPid || 0
        				}
        			},
        			async: {enable: false},
        			edit: {
        					drag: {isCopy:true},
        					enable: true,
        					showRemoveBtn: false,
        					showRenameBtn: false
        				},
        			view:{
        				nameIsHTML: true,
        				selectedMulti: true,
        				showIconFont:true,
        			    showIcon: null
        			},
        			check: {
        				enable: false,
        				chkboxType: { "Y": "", "N": "" }
        			},
        			callback: scope.callback ||{}
        		}
        	
        	if(attrs.childKey!=undefined){
        		scope._setting.data.simpleData.enable = false;
        		scope._setting.data.key.children =attrs.childKey||"children";
        	} 
        	if(attrs.setCheckbox != undefined ){
        		scope._setting.check.enable = true;
        		if(attrs.setCheckbox){
        			scope._setting.check.chkboxType=eval('(' + attrs.setCheckbox + ')');
        		}
        	}
        	
        	if(attrs.url){
        		$.post(url,param,function(result){
        			if(Object.prototype.toString.call(result) === "[object String]") result =eval('(' + result + ')');
        			initTree(result);
        		});
        		
        	}
        	else{
        		scope.$watch("htZtree",function(newVal,oldVal){
            		if(newVal!==oldVal){
            			initTree(newVal); 
            		}
            	},true);
        	}
        	
        	
        	function initTree(data){
        		var _treeObj = $.fn.zTree.init(element,scope._setting,data);
        		
        		if(attrs.expandLevel){
        			_treeObj.expandAll(false);
        			expandTree(_treeObj,_treeObj.getNodes(),attrs.expandLevel);
        		}
        		else{
        			_treeObj.expandAll(true);
    			}
        	}
        	
        }
    };
}])
/**
 *日期控件。
 *控件用法：
 *<input type="text" ht-date="yyyy-MM-dd HH:mm:00" ng-model="date1" />
 *<input type="text" ht-date ng-model="date1" />
 *需要增加：ht-date 指令。
 * ht-date="yyyy-MM-dd HH:mm:ss"
 * 属性为日期格式。
 */
.directive('htDate', function() {
	var link = function(scope, element, attrs, $ctrl) {
		element.addClass("dateformat");
		var format=attrs.htDate || "yyyy-MM-dd";
		$ctrl.$formatters.push(function(value) {
			if(value){
				if(/^y{4}-M{2}-d{2}$/.test(format)){
					return new Date(value).format(format);
				}
				else{
					return value;
				}
			}
		});
		return;
	};
	return {
		restrict : 'A',
		require : "ngModel",
		compile : function() {
			return link;
		}
	};
})
/**
 *  计算日期间隔
 */ 
.directive('htDatecalc', ['formService','$filter',function (formService,$filter) {
      return {
    	  restrict : 'A',
          require: "ngModel",
          scope:{
        	  htDatecalc:"="
          },
          link: function ($scope, element, attr, ctrl) {
        	  if(!$scope.htDatecalc || !ctrl) return;
        	  var ngModel =attr.ngModel;
    		  var dateCalc = $scope.htDatecalc;
    		  var startSrc =dateCalc.start;
    		  var endSrc = dateCalc.end;
    		  var diffType =dateCalc.diffType;
			  $scope.$parent.$watch(startSrc,function(newValue, oldValue) {
	        	   if(newValue!==oldValue || !ctrl.$viewValue){
	        		   var endDate =eval("$scope.$parent."+endSrc);
	        		   if(!endDate) return;
	        		   var int =formService.doDateCalc(newValue,endDate,diffType);
        			   ctrl.$setViewValue(int);
        			   ctrl.$render();
	        	   }
    		   });
			  $scope.$parent.$watch(endSrc,function(newValue, oldValue) {
	        	   if(newValue!==oldValue || !ctrl.$viewValue){
	        		   var start =eval("$scope.$parent."+startSrc);
	        		   if(!start) return;
	        		   var int = formService.doDateCalc(start,newValue,diffType);
        			   ctrl.$setViewValue(int);
        			   ctrl.$render();
	        	   }
    		   });
    			  
          }
      };
}])
/**
 * @ht-boolean 可为空， 格式以“/”分割truevalue 和falsevalue  
 * @text 按钮名字 （可以通过/分割则为trueName/falseName）  
 * <span  ht-boolean="Y/N" class="checkbox disabled" text="测试按钮" ng-model="prop.skipFirstNode" ht-tip title="我是按钮的提示"></span>
 */
.directive('htBoolean', function() {
	return {
		restrict : 'A',
		require:'ngModel',
		scope : true,
		link : function(scope, element, attrs,ctrl) {
			var booleanConf =attrs.htBoolean;
			var textArr = attrs.text.split("/");
			scope.trueText = textArr[0];
			scope.falseText =textArr.length==2?textArr[1]:textArr[0];
			if(booleanConf)	{
				booleanConf=booleanConf.split("/");
			}
			else {
				booleanConf = [true,false];
			}
			
			var trueValue = booleanConf[0],falseValue = booleanConf[1];
			
			ctrl.$formatters.push(function(value){
				if(typeof(value)== 'number')value =""+value;
				return value === trueValue;
			});
			
            ctrl.$parsers.push(function(value){
            	return value ? trueValue : falseValue; 
            });
            
            ctrl.$render = function(){
                scope.checked = ctrl.$viewValue;
            };
            
            element.on('click', function(){
            	if($(this).attr("disabled"))return ; 
                scope.$apply(function() {
                  scope.checked = !scope.checked;
                  ctrl.$setViewValue(scope.checked);
                });
            });
		},
		template:'<lable class="btn label-sm {{checked ? \'active\':\'inactive\'}}" >{{checked ?trueText:falseText}}</lable>'  
	};
})

/**
 * 页面要引入
 * <%@include file="/commons/include/zTree.jsp"%>
 * <script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
 * 数据字典指令。
 * dictype：数据字典别名。
 */
.directive('htDic', function($injector){
	return {
		restrict: 'A',
		scope:{
			htDic:'='
    	},
    	template:getDirectiveTplHtml("htDic"),
		link: function(scope, element, attrs) { 
			element.removeClass();
			scope.permission = "b";
			if(attrs.permission){
				scope.permission = attrs.permission;
			}
			var dicKey =attrs.dickey; //数据字典的类型
			var url=attrs.url|| __ctx +"/system/dataDict/getByTypeKeyForComBo?typeKey="+dicKey;
			var keyName =attrs.keyName||"key";
			var valName=attrs.valName||"text";
			
			scope.treeId = parseInt(Math.random()*1000)
			scope.dicData={};
        	scope.treeClick = function(event,treeId,treeNode){
        		
        		scope.dicData.key =treeNode[keyName];
        		scope.dicData.value =treeNode[valName];
        		scope.htDic =scope.dicData.key;
        		
        		!scope.$parent.$$phase &&scope.$parent.$digest();
        		
        		// 树隐藏
        		if(window.FORM_TYPE_!='mobile'){
        			$('#dropBody'+treeId).dropdown('close');
        		}else{
        			$.closeModal($('#popover'+treeId));
        		}
        	}
        	
        	scope.loadTree = function(){
        		new ZtreeCreator(scope.treeId,url)
        		.setDataKey({idKey:keyName,name:valName})
 				.setCallback({onClick:scope.treeClick})
 				.setChildKey()
 				.setOutLookStyle()
 				.initZtree({},0,function(treeObj,treeId){
 					
 					//树列表的dropBody初始化
 					if(window.FORM_TYPE_!='mobile'){
 						$('#dropBody'+treeId).dropdown({justify: '#dropDown'+treeId});
 					}else{
 						$(".page").after($('#popover'+treeId));
 					}
 					
 					// 通过key 回显Value
 					if(scope.htDic){
 						//获取key 的那个Value
 						var node = treeObj.getNodesByFilter( function(node){ if(node[keyName]==scope.htDic) return true; else return false; },true);
 						if(node){
 							scope.dicData.key =node[keyName];
 			        		scope.dicData.value =node[valName];
 			        		!scope.$parent.$$phase&&scope.$parent.$digest();
						}
 					}else{
 						scope.$apply(function(){
 			          		scope.dicData.value="点击选择";
 			        		});
 					}
				});
        	}
        	scope.loadTree();
		}
	};
})
/**
 * 
 * <input ht-category="DIC" ng-model="editingField.option.dic" />
 */
.directive('htCategory',[function(){
	return {
		 restrict: 'A',
		 replace: true,
		 require: '^ngModel',
		 scope:{
			 htCategory:"@",
			 ngModel:"="
		 },
		 template: '<input class="easyui-combotree-me" />',
		 link:function(scope, element, attrs){
			 scope.$watch("ngModel",function(){
			 	var comboTree = $(element); 
				if(comboTree.length==0) return;
				if(comboTree.data("ready")){
					setValue(comboTree);
					return;
				}  
				comboTree.data("ready",true);
				comboTree.combotree({
					url:__ctx+'/system/sysType/getByGroupKey?groupKey='+scope.htCategory,
					onClick:function(node){
						scope.$apply(function(){
							scope.ngModel=node.typeKey;
		                });
					},
					onLoadSuccess:function(node,data){
						setValue(comboTree);
					}
				});
			 },true);
			 
			 function setValue(comboTree){
				 if(scope.ngModel){
					comboTree.eachComboNode(function(node){
						if(node.typeKey&&node.typeKey==scope.ngModel){
							comboTree.combotree('setValue',node.id);
							return false;
						}
						return true;
					});
				  }
			 }
				
			//easyui的combotree控件内容超过范围时设置滚动条
			window.setTimeout(function(){
				var combotreeDiv = $("[class='combo-panel panel-body panel-body-noheader']");
				if(combotreeDiv&&combotreeDiv.length>0){
					combotreeDiv.css("overflow-y","auto");
				}
			},100)
			
		 }
	};
}])
/*
 * 上传指令。
 * 使用方法:
 * 	<input ht-upload="data.main.name" permission='w' />
 * 	ht-input对应的属性值为scope对应的数据值路径。
 * 	permission:
 * 		取值有两种：
 * 			r:只读
 *  		w:可输入
 */
.directive('htUpload',["$rootScope",function($rootScope) {
	return {
    	restrict : 'A',
    	template : getDirectiveTplHtml('htUpload'),
    	scope: {
    		htUpload:"="
    	},
		link: function (scope,element,attrs,ctrl){
			element.removeClass();
			scope.permission = angular.isUndefined(attrs.permission)? "w":attrs.permission;
			var max = (attrs.issingle&&eval(attrs.issingle)) ? 1:20;
			
        	var jsonStr = scope.htUpload?scope.htUpload.replace(/￥@@￥/g,"\""):"[]";
        	scope.files = eval("(" +jsonStr +")"); 
        	
        	scope.dialogOk = function(data){
        		scope.files = data;
        		scope.htUpload=JSON.stringify(data);
        		!$rootScope.$$phase&&$rootScope.$digest();
        	}
        	
    		scope.showDialog = function(){
    			var conf ={callback:scope.dialogOk,max:max}; 
    			UploadDialog(conf);
    		}
    		scope.onClick = function(id){
    			window.location.href=__ctx+"/system/file/download?id="+id;
    		}
    		
    		scope.$watch('htUpload',  function(newValue, oldValue) {
    			if(newValue==oldValue) return ;
    			
    			if(!newValue) scope.files=[];
    			else scope.files = eval("(" +newValue +")");
    			
    			!$rootScope.$$phase&&$rootScope.$digest();
        	});
    		scope.remove = function(index){
				scope.files.splice(index,1);
				//更新字段值
				if(!scope.files ||scope.files.length==0) scope.htUpload="";
				else scope.htUpload=JSON.stringify(scope.files);
			};
			
		}
}}])
/**
 * 计算时间（分钟）
 */
.directive('htTimes', [ function() {
		return {
			restrict : 'A',
			scope:{
				ngModel:"="
			},
			require : "ngModel",
			link : function(scope,element,attrs,ctrl){
				scope.hourArr = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
				scope.minuteArr = [0,1,2,3,4,5,10,15,20,30,40,50];
				scope.permission = angular.isUndefined(attrs.permission)? "w":attrs.permission;
				scope.day=0;
				scope.hour=0;
				scope.minute =0;
				ctrl.$formatters.push(function(time) {
					if(typeof time =='undefined' || Number.isNaN(time))return;
					
					 scope.day =Math.floor(time / (60 * 24));
					 scope.hour =Math.floor((time -scope.day * (60 * 24)) / 60) ;
					 scope.minute =time - scope.day * (60 * 24) - scope.hour * 60;
				});
				
				scope.changeMin = function(){
					var modelValue = 0;
					modelValue += parseInt(60 * 24 * scope.day);
					modelValue += parseInt(60 * scope.hour);
					modelValue += parseInt(scope.minute);
					ctrl.$setViewValue(modelValue);
					ctrl.$render();
				}
				
				scope.reset = function(){
					scope.day=0;
					scope.hour=0;
					scope.minute =0;
				}
				scope.reset();
			},
			template:'<div ng-show="permission!=\'r\'"><input type="text" ng-model="day" ng-change="changeMin()" class="inputText" style="width: 80px;" placeholder="天数" ht-validate="{number:true}"/><label>天</label>\
						<select  ng-model="hour" ng-change="changeMin()" class="inputText" style="width: 80px;" ng-options="hour as (hour+\'小时\') for hour in hourArr">\
						</select><label>小时</label>\
						<select ng-model="minute" ng-change="changeMin()" class="inputText" style="width: 80px;" ng-options="minute as (minute+\'分\') for minute in minuteArr">\
						</select><label>分</label></div>\
				<div ng-show="permission==\'r\'"><span ng-bind="day"></span><label>天</label><span ng-bind="hour"></span><label>小时</label><span ng-bind="minute"></span><label>分</label></div></div>'   
		};
}])

/**
 * 页面要引入
 * <%@include file="/commons/include/zTree.jsp"%>
 * <script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
 * 数据字典指令。
 * dictype：数据字典别名。
 */
.directive('htDiccheckbox', function($injector){
	return {
		restrict: 'A',
		link: function(scope, element, attrs) { 
			element.removeClass();
			scope.permission = "b";
			if(attrs.permission){
				scope.permission = attrs.permission;
			}
			var isOnlyShow=attrs.isonlyshow||false;
			var dicKey =attrs.dickey; //数据字典的类型
			var url=attrs.url|| __ctx +"/system/dataDict/getByTypeKeyForComBo?typeKey="+dicKey;
			var keyName =attrs.keyName||"key";
			var valName=attrs.valName||"text";
			var baseService = $injector.get("baseService");
			var rtn = baseService.postForm(url,{});
			var fileName=attrs.ngModel.split('.')[1];
			rtn.then(function(data) {
				var htmlStr='';
				setTimeout(function() {
					for(var i=0;i<data.length;i++){
						var isHas=false;
						var hasOpArr=[];
						if(JSON.stringify(scope.data) !='{}' && scope.data[fileName]){
							hasOpArr=scope.data[fileName].split('|');
						}
						for(var j=0;j<hasOpArr.length;j++){
							if(data[i][valName]==hasOpArr[j]){
								isHas=true;
							}
						}
						if(isOnlyShow){
							if(isHas){
								htmlStr+=data[i][valName]+"&nbsp;&nbsp;";
							}
						}else{
							if(isHas){
								htmlStr+="<label><input type='checkbox' checked='checked'  name="+fileName+"  value="+data[i][keyName]+">"+data[i][valName]+"</label>&nbsp;&nbsp;";
							}else if(!isOnlyShow){
								htmlStr+="<label><input type='checkbox' name="+fileName+"   value="+data[i][keyName]+">"+data[i][valName]+"</label>&nbsp;&nbsp;";
							}
						}
					}
				  $(element).after(htmlStr);
					$(":checkbox",$(element).parent()).click(function(){
						scope.data[fileName]='';
						$(":checkbox:checked",$(element).parent()).each(function(){ 
							scope.data[fileName]+=$(this).parent().text()+'|';
						});  
					});
				}, 100);
			}, function(errorCode) {
			});
		}
	};
})

/**
 * 数字转成中文大写。
 * 用法：
 * <input class="inputText" type="text" ng-model="jinge"   />
 * 
 * {{jinge | cnCapital}}
 */
.filter('cnCapital', function() { 
	return function(input) {
		if(!input) return "";
		return $.convertCurrency(input);
	}; 
}); 



