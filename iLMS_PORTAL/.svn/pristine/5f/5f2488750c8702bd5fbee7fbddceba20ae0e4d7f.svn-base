angular.module('FieldService', [ 'baseServices','CustomQueryService'])
/**
 * 数字格式化指令  对应的控件对象格式为 
 * <input type="text" 
 * 	ng-model="data.person.girls" 
 *  ht-number-format="{
 *  		'capital':true,			是否变成金额大写
 *  		'isShowComdify':false,	是否千分位格式化
 *  		'isShowCoin':false,		是否金额格式化
 *  		'coinValue':'',			金额格式化 前缀  isShowCoin=true时有效
 *  		'intValue':'13',		整数前位数
 *  		'decimaValue':'2' 		小数位数
 *  	}">
 */
.directive('htNumberFormat', function($injector) {
			var link = function(scope, element, attrs, $ctrl) {
				var fieldService = $injector.get("fieldService");
				scope.$watch(attrs["ngModel"], function(newVal, oldVal) {
					if (oldVal != "") {
						if (newVal == oldVal) {
							fieldService.delaComdify(element);
						}
					}
				});
				element.on("blur", function() {
					fieldService.delaComdify(element);
				});
				element.on("focus", function() {
					element.val(fieldService.toNumber(element, true));
				});
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
 * 对字段进行校验  控件格式
 * <input type="text"
 *  ng-model="data.person.name"
 *	ht-field-valid="{
 *		'required':true,
 *		'rules':[
 *				{'name':'日期','text':'表达式xxx','tip':'日期填写错误'},
 *				{'name':'数字','text':'表达式xxx','tip':'数字格式错误'}
 *			]
 *		}"
 *		 >
 *	在rules中，text为正则表达式，tip表示当校验失败时 提示的错误信息
 */
.directive('htFieldValid', function($injector) {
	return {
		priority:10,
		restrict : 'A',
		link:function(scope, element, attrs, $ctrl) {
			if(attrs['htFieldValid'].indexOf('"required":false') == -1) {
				element.validMe();
			}
			var exp = attrs["ngModel"];
			if(!exp) exp = attrs["childModel"]; 
			if(!exp) exp = attrs["tempModel"]; 
			scope.$watch(exp, function(newVal, oldVal) {
				if (newVal !== oldVal) {
					element.validMe({text:newVal});
				}
			});
			element.on("blur", function() {
				element.validMe();
			});
		}
	};
})
/**
 * 函数统计
 * <input type="text" 
 * 	ng-model="data.person.name"
 * 	ht-stat-fun="{女朋友个数(data.person.girls)}+2" 
 * >
 */
.directive('htStatFun', function($injector) {
	var link = function(scope, element, attrs, $ctrl) {
		var fieldService = $injector.get("fieldService"),
			statFun=attrs['htStatFun'],
			watchField=getWatchField(statFun),
			subFormDiv = element.closest("div.sub-form-div"); //是否是子表的数据
		
		if(watchField.length>0){
			if(!!subFormDiv[0]){
				for(var i=0,f;f=watchField[i++];){
					subFormDiv.delegate("[ng-model='"+f+"']","change",f,function(){//监听输入框每一次改变
						fieldService.doMath("",element,statFun,subFormDiv);//输入框每一次改变后都要计算每一条记录
					});
				}
			}else{
				for(var i=0,f;f=watchField[i++];){
					$(document).delegate("[ng-model='"+f+"']","change",function(){
						fieldService.doMath($(this),element,statFun);
					});
				}
			}
		}
		function getWatchField(statFun){
			var myregexp = /\(([data|item].*?)\)/g;
			var match = myregexp.exec(statFun);
			var arrs=[];
			while (match != null) {
				arrs.push(match[1]);
				match = myregexp.exec(statFun);
			}
			return arrs.unique(function(x,y){
				if(x==y) return true;
			});
		}
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
 * 删除子表
 */
.directive('hoverDelete', function($injector) {
	var link = function(scope, element, attrs, $ctrl) {
		
	};
	return {
		restrict : 'A',
		scope:{
			'data-index':"=",
			'data-list':"="
		},
		require : "ngModel",
		compile : function() {
			return link;
		}
	};
})
/**
 * 流水号处理
 */
.directive('htIdentity', function($injector) {
	var link = function(scope, element, attrs, $ctrl) {
		var url =__ctx + "/system/identity/getNextIdByAlias";
		var oldVal= getValByScope(element,attrs.ngModel,scope);
		var htIdentity = parseToJson(attrs['htIdentity']);
		if((!oldVal||oldVal=="")&&htIdentity&&htIdentity.alias)//如果没数据才会取下一个流水号
			$.post(url,{alias:htIdentity.alias},function(data){
				setValToScope(element,data);
			})
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
 * 	实现custombutton 
 *	custom-button="{"title":"自定义按钮","img":"fa fa-eye","cid":"10000002729002","conditionfield":[{"field":"id","target":"person.sex"},{"field":"name","target":"W_PERSON.name"}],"resultfield":[{"field":"主键","target":"W_PERSON.ADDRESS"},{"field":"名字","target":"W_PERSON.W_DETAIL.nationality"}]}" 
 * >
 */
.directive('customButton', function($injector) {
	var link = function(scope, element, attrs, $ctrl) {
		var fieldService = $injector.get('fieldService');
		var customButton=JSON.parse(attrs['customButton']);
		
		element.click(function(){
			var paramValueString='';
			for(var i=0;cf=customButton.conditionfield[i++];){
				var value='';
				try{
					value=eval('scope.'+getNgModelName(cf.target));
					if(value==null||value=='') continue;
				}catch (e) {
					continue;
				}
				
				var str=cf.field+':';
				str+=value;
				paramValueString+=str;
				if(i<customButton.conditionfield.length-1){
					paramValueString+='|';
				}
			}
			//TODO
		});
	};
	//根据name来返回ngmodel的名字
	var getNgModelName=function(name){
		var strs=name.split('.');
		if(strs.length>2){
			return 'item.'+strs[strs.length-1];
		}else{
			return 'data.'+name;
		}
	};
	return {
		restrict : 'A',
		compile : function() {
			return link;
		}
	};
})
/**
 * 实现ganged-setting
 * ganged-setting="{"cid":"10000002308001","condition":"id","triggerEvent":0,"resultfield":[{"field":"id","target":"person.sex"},{"field":"name","target":"W_PERSON.ADDRESS"}]}";
 */
.directive('gangedSetting', function($injector) {
	var link = function(scope, element, attrs, $ctrl) {
		var customQuery = $injector.get('CustomQuery');
		var gangedSetting=JSON.parse(attrs['gangedSetting']);
		var triggerEvent = gangedSetting.triggerEvent;
		element.keyup(function(e) {
			//0:回车 e.leyCode=13是回车
			if(triggerEvent==0&&e.keyCode != 13){
				return;
			}
			
			var value = element.get(0).value;
			var params={};
			params.id=gangedSetting.cid;
			params.page=1;
			params.pageSize=5;
			
			var querydata=[];
			if(value!=null&&value!=''){
				var param={};
				param.key=gangedSetting.condition;
				param.value=value;
				querydata.push(param);
			}
			params.querydata=JSON.stringify(querydata);
			
			customQuery.search(params,function(data){
				var rows = data.rows;
				
				for(var i=0;rf=gangedSetting.resultfield[i++];){
					var value='';
					for(var j=0;j<rows.length;j++){
						value+=eval('rows[j].'+rf.field);
						if(j!=rows.length-1){
							value+=","
						}
					}
					var target=getNgModelName(rf.target);
					setValToScope(null,value,null,target,scope);
				}
			});
		});
		
	};
	//根据name来返回ngmodel的名字
	var getNgModelName=function(name){
		var strs=name.split('.');
		if(strs.length>2){
			return 'item.'+strs[strs.length-1];
		}else{
			return 'data.'+name;
		}
	};
	return {
		restrict : 'A',
		compile : function() {
			return link;
		}
	};
})
/**
 * fieldService 提供表单字段的一些方法 
 */
.service('fieldService',['$rootScope','BaseService',function($rootScope, BaseService) {
	var service = {
		/**
		 * 格式化数据 
		 * @param obj    正在编辑的控件对象
		 * @param target 函数作用的目标控件对象
		 * @param exp	 函数表达式
		 * @returns {Boolean}
		 */
		doMath:function(obj,target,exp,sub){
			var me = sub?$(target):$(obj);
			var value = FormMath.replaceSingleValue(exp,me,sub);
			value = FormMath.replaceMultiValue(value,sub);
			try{
				//计算表达式进行运算
				value = eval("("+value+")");
			}
			catch(e){
				return true;
			}
			if(/^(Infinity|NaN)$/i.test(value))return true;
			
			//按字段的小数点设置处理小数点的问题（四舍五入）
			if(!isNaN(value)){
				value = Number(value);
				var splitArr = (""+value).split("."),
				floatError = !!(splitArr.length == 2 && splitArr[1].length>10);
				if((!target.attr("ht-number-format")&&splitArr.length == 2)||floatError) value = value.toFixed(3);
				setValToScope(target,Number(value));
			}else{
				setValToScope(target,value);
			}
			this.delaComdify(target);
			$(target).trigger("change");
		},
		/**
		 * 转为number类型
		 * @param varObj    控件对象
		 * @param nocomdify	是否格式化为千分位 true表示 不格式化
		 * @returns {String} 返回的数据
		 */
		toNumber : function(varObj, nocomdify) {
			var json = this.getFormatJson(varObj), coinvalue = json.coinValue, iscomdify = json.isShowComdify, capital = json.capital, ngModel = $(
					varObj).attr("ng-Model"), scope = angular
					.element($(varObj)).scope(), value = "";
			try {
				value = scope&& (eval("scope." + ngModel))+ "";
			} catch (e) {}
			if(!value||value=="undefined") return;
			// 转换金额大写为小写
			if (capital) {}
			// 去除货币标签
			if (coinvalue != null && coinvalue != '') {
				if (value.split(coinvalue) != -1) {
					var ary = value.split(coinvalue);
					value = ary.join("");
				}
			}

			if (value.indexOf(',') != -1) {
				value = $.toNumber(value);
			}
			if (iscomdify && !nocomdify) {
				value = $.comdify(value);
			}
			return value;
		},
		/**
		 * 获取数字格式化信息的json对象
		 * @param varObj
		 * @param scope
		 * @returns
		 */
		getFormatJson : function(varObj, scope) {
			scope = scope || angular.element($(varObj))
							.scope();
			var jsonStr = $(varObj).attr("ht-number-format");
			return eval('(' + jsonStr + ')');
		},
		/**
		 * 千分位格式化处理
		 * @param varObj  控件对象
		 */
		delaComdify : function(varObj) {
			var me = $(varObj);
			var json = null;
			try {
				var jsonStr = me
						.attr("ht-number-format");
				json = eval('(' + jsonStr + ')');
			} catch (err) {
			}
			if (json != null) {
				var value = this.toNumber(varObj);
				if(!value||value=="undefined") return;
				if(isNaN(value)) return;
				var coinvalue = json.coinValue;
				var decimallen = json.decimaValue;
				var capital = json.capital;

				// 金额大写处理
				if (capital) {
					value = $.convertCurrency(value);
					me.val(value);
					return;
				}

				// 小数处理
				if (decimallen > 0 && value != '') {
					if (value.indexOf('.') != -1) {
						var ary = value.split('.');
						var temp = ary[ary.length - 1];
						if (temp.length > 0
								&& temp.length < decimallen) {
							var zeroLen = '';
							for ( var i = 0; i < decimallen
									- temp.length; i++) {
								zeroLen = zeroLen + '0';
							}
							value = value + zeroLen;
						}
					} else {
						var zeroLen = '';
						for ( var i = 0; i < decimallen; i++) {
							zeroLen = zeroLen + '0';
						}
						value = value + '.' + zeroLen;
					}
				}
				// 添加货币标签
				if (coinvalue != null
						&& coinvalue != ''
						&& value != '') {
					value = coinvalue + value;
				}
				me.val(value);
			}
			
		},
		isValid:function (el){
			if($(el)[0]&&$(el)[0].querySelectorAll(".has-error").length>0) return false;
			return true;
		}
	};
	return service;
} ]);

