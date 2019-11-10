/**
 * jquery自定义表单验证插件
 * 使用方法：
 * 在需要做验证的输入框，单选框，多选框，下拉框中加入validate属性
 * validate:写法如下：
 * {required:true,email:true,maxLength:50}
 * 如：
 * <input type="text" name="username" value="" validate="{required:true,maxlength:50}"/>
 * 注意一组单选框，或多选框  只需在其中一个input标记中 加入validate 属性
 * 如：
 * <input type="checkbox" name="a" value="1" validate="{required:true}" tipId="errorA"/>
 * <input type="checkbox" name="a" value="2" />
 * <input type="checkbox" name="a" value="3" />
 * 
 * tipId:错误信息显示的容器ID,设置了这个属性后，错误信息会显示到该标签中。
 * <input type="text" name="name" validate="{required:true}" tipId="errorA"/><label id="errorA"></label>
 * 
 * 
 * 调用方式:
 * 
 * $("a.save").click(function(){
 *			var rtn=$("#shipOrderForm").form().valid();
 *			if(rtn){
 *				$("#shipOrderForm").submit();
 *			}
 *	});
 *	同时也可以扩展验证的规则
 *	var rtn=$("#form").form({
 *		//扩展验证规则 追加到已有的规则中
 *		rules:[{
 *			//规则名称
 *			name:"QQ",
 *			//判断方法  返回 true 或false
 *			rule:function(v){
 *			},
 *			//错误的提示信息
 *			msg:""
 *		
 *		}],
 *		//显示的错误信息样式 element 当前验证的元素，msg：错误信息
 *		errorPlacement:function(element,msg){
 *		},
 *		//成功后的样式 element 当前验证的元素
 *		success:function(element){
 *		},
 *		excludes:":hidden"
 *		}).valid()
 */
(function($) {
	$.extend($.fn, {
		// 表单初始化，可以添加自定义规则，出错处理和成功后的处理。
		form : function(conf) {
			if (conf) {
				if (conf.errorPlacement) {
					this.errorPlacement = conf.errorPlacement;
				};
				if (conf.rules) {
					for (var i = 0, len = conf.rules.length; i < len; i++) {
						this.addRule(conf.rules[i]);
					}
				};
				if (conf.success) {
					this.success = conf.success;
				};
				if (conf.excludes) {
					this.excludes = conf.excludes;
				}
				
			}
			var form = this;
			form.delegate("input[validate],select[validate],textarea[validate]", "blur", function() {
						form.handValidResult(this);
					});
			form.delegate("input[validate],select[validate],textarea[validate]", "focus", function() {
						form.success(this);
					});
			form.delegate("[ht-field-valid]", "blur", function() {
				$(this).validMe();
			});		
			//处理验证ckeditor
			$("[validate].ckeditor",form).each(function(){
				var me= $(this),name = me.attr("name");
				setTimeout(function(){//等待ckeditor渲染完成再进行处理
					var editor=  CKEDITOR.instances[name],ck=me.next();
					if(editor){
						editor.on( 'blur', function(){
							form.handValidResult(me);
						});
						editor.on( "focus", function(){
							form.success(me);
						});
					}
				},1000);
				
			});
		
			return this;
		},
		// 添加验证规则。
		// 扩展规则和现有的规则名称相同，则覆盖，否则添加。
		addRule : function(rule) {
			var len = ConstUtil.rules.length;
			for (var i = 0; i < len; i++) {
				var r = ConstUtil.rules[i];
				if (rule.name == r.name) {
					ConstUtil.rules[i] = rule;
					return;
				}
			}
			ConstUtil.rules.push(rule);
		},
		/**
		 * 判断元素是否在不需要校验的范围内。
		 */
		isInNotValid : function(obj) {
			if($(obj).is(":hidden"))return true;
			if (!this.excludes)
				return false;
			var scope = $(this.excludes, this);
			var aryInput = $(
					"input:text,input:hidden,textarea,select,input:checkbox,input:radio",
					scope);
			for (var i = 0, len = aryInput.length; i < len; i++) {
				var tmp = aryInput.get(i);
				if (obj == tmp) {
					return true;
				}
			}
			return false;
		},
		// 对所有有validate表单控件进行验证。
		valid : function(conf) {
			if(!conf){
				this.ignoreRequired=false;
			}
			else{
				if(conf.ignoreRequired==undefined){
					this.ignoreRequired=false;
				}
				else{
					this.ignoreRequired=conf.ignoreRequired;
				}
			}
			var _v = true, form = this;
			$('[validate]', form).each(function() {
						var rtn = form.handValidResult(this);
						if (!rtn)
							_v = false;
					});
			if(_v) _v = form.isNgModelValid();
			return _v;
		},
		// 显示表单处理结果
		handValidResult : function(obj) {
			// 是否在不需要验证的范围内，在的话就不需要验证。
			if (this.isInNotValid(obj))
				return true;
			var msg = this.validEach(obj);
			if (msg != '') {
				this.errorPlacement(obj, msg);
				return false;
			} else {
				this.success(obj);
				if($(obj).hasClass('validError')){             //引对子表单的
					$(obj).removeClass('validError');
				}
				return true;
			}
		},
		// 验证单个控件。
		validEach : function(obj) {
			var element = $(obj), 
				rules = ConstUtil.rules,
				validRule = element.attr('validate'),
				value = "",
				name = element.attr("name");
			// 处理单选框和多选框
			if(element.is(":checkbox,:radio")) {
				var parentObj = element.closest("[formtype]"),
					brotherObjs = (parentObj&&parentObj.length>0)?$(":checked[name='" + name + "']",parentObj):$(":checked[name='" + name + "']");
				
					brotherObjs.each(function() {
							if (value == "") {
								value = $(this).val();
							} else {
								value += "," + $(this).val();
							}
						});
			}else if (element.is("select")) {// 处理select
				value = element.find("option:selected").val();
                if(typeof(value)==undefined || value==null || $.trim(value) == '' || value.indexOf("请选择")>-1||value=="?"){
                	value = '';
                }
			}else if (element.hasClass("ckeditor") ) {// 处理ckeditor编辑器
				var editor=  CKEDITOR.instances[name];
				if($.isEmpty(editor))//ckeditor没渲染的，则取textarea的值
					value = element.val();
				else
					value = editor.getData();
			}else if(element.hasClass("selectFile")){//处理附件
				value = element.siblings("textarea[controltype='attachment']").val();
			}else {
				value = element.val();
			}
			// 处理值
			value = value == null ? "" : value.trim();

			// 获取json。
			var json = eval('(' + validRule + ')');
			var isRequired = json.required;
			
		
			
			// 非必填的字段且值为空 那么直接返回成功。
			if ((isRequired == false || isRequired == undefined) && value == "")
				return "";
			
			
			
			//忽略必填规则。
			if(this.ignoreRequired==true && value == "") return "";
			
			// 遍历json规则。
			for (var name in json) {
				var validValue = json[name];
				//验证规则
				var msg = this._validRules({
					rules:rules,//规则json
					ruleName:name,// 规则名称
					validValue:validValue,//验证的值
					value:value,//实际的值
					element:$(obj)
				});
				if (msg!=true&&msg != '') 
					return msg;
			}
			return "";
		},
		/**
		 * 验证规则
		 **/
		_validRules :function(conf){
			var  _valid = true,
				rules = conf.rules,//规则json
				ruleName = conf.ruleName,// 规则名称
				validValue = conf.validValue,//验证的值
				value =conf.value;//实际的值
			for (var m = 0; m < rules.length; m++) {
				// 取得验证规则。
				var rule = rules[m];
				if (ruleName.toLowerCase() != rule.name.toLowerCase()) continue;
				// 验证规则如下：
				// email:true,url:true.
				//验证规则是boolean类型
				if ($.type(validValue)  === "boolean") 
					_valid = (!rule.rule(value) && validValue == true) ? false:true; 	
				else 
					_valid = rule.rule(value, validValue);
				
				if (!_valid) //验证不通过返回消息
					return this.format(rule.msg, validValue);
			}
			return "";
		},
		/**
		 * 消息格式化
		 **/
		format:function(msg,args){
			//boolean类型的直接返回
			if ($.type(args)  === "boolean") 
				return  msg;
			if (!$.isArray(args)) //不是数组类型的
				args = [args];
			//数组类型的
			$.each(args,function(d, e) {
				msg = msg.replace(RegExp("\\{" + d + "\\}", "g"), e)
			});
			return msg;		
		},
		// 错误显示位置。
		errorPlacement : function(element, msg) {
			var errorId = $(element).attr("tipId");
			if (errorId) {
				$('#' + errorId).find('label.error').remove();
				$('#' + errorId).qtipError(msg);
				return;
			}
			$(element).qtipError(msg);
		},
		// 验证成功时，删除错误提示信息。
		success : function(element) {
			var errorId = $(element).attr("tipId");
			if (errorId) {
				$('#' + errorId).qtipSuccess();
				return;
			}
			$(element).qtipSuccess();	
		},
		
		
		//-----普通表单校验的方法 把它从 customValid.js 搬到这里。
		
		
		
		
		qtipSuccess : function(){
			var obj = $(this);
			obj.removeClass("has-error");
			obj.addClass("has-success");
			obj.qtip('destroy');
		},
		qtipError : function(text,hasFieldHome){
			var obj = $(this);
			if(!hasFieldHome) obj.addClass("field-home");
			obj.removeClass("has-success");
			obj.addClass("has-error");
			obj.qtip('destroy');
			obj.qtip({
				   content: text, // Set the tooltip content to the
										// current corner
	               position: {
		            	my: 'left center',  // Position my top left...
                        at: 'right center',
                        target: 'mouse', // Track the mouse as the positioning target
                        adjust: { x: 5, y: 5 } 
	               },
	               hide: {
	            	   event:"mouseleave" // Don't specify a hide event
	               },
	               style: {
	            	   classes: 'qtip-default  qtip qtip-bootstrap qtip-shadow'
	               }
			});
		},
		isNgModelValid:function (){
			if($(".has-error",$(this)).length>0||$(this).hasClass("has-error")) return false;
			return true;
		},
		__checkValidRule : function(input,validRule,element){
		       if(!validRule)return {success:true};
			validRule = parseToJson(validRule);
			var unNullable=validRule.required;
			if(unNullable){
				if(this.__checkNull(input)){
					return {success:false,tip:(element[0].nodeName=="SELECT")?("请选择"):("必填")};//(element.attr("placeholder")&&("必填 : "+element.attr("placeholder")))||
				}
			}
			var rules = validRule.rules;
			if(!rules) {
				element.removeClass("has-error");
				element.addClass("has-success");
				return {success:true};
			}
			var res = this.__checkRules(input,rules);
			return res;
		},
		__checkRules : function(input,rules){
			for(var i=0;i<rules.length;i++){
				var rule = rules[i];
				if(rule.isDefault){
					rule = ConstUtil.getRuleByName(rule.text);
					if(!rule.rule(input)){
						return {success:false,tip:rule.msg};
					}
				}else if(!this.__checkRegex(input,rule.text)) {
					return {success:false,tip:rule.tip};
				}
			}
			return {success:true};
		},
		/**
		 * 功能:判断是否为空 input : 校验对象
		 */
		__checkNull:function (input){
			if(input==null||input==""){
				return true;
			}
			return false;
		},
		__checkRegex:function (input,reg){
			var regex=new RegExp(reg,'g');
			var r=input.match(regex);
			if(r==null){
				return false;
			}
			return true;
		},
		// 对所有有validate表单控件进行验证。
		valid : function(conf) {
			if(!conf){
				this.ignoreRequired=false;
			}
			else{
				if(conf.ignoreRequired==undefined){
					this.ignoreRequired=false;
				}
				else{
					this.ignoreRequired=conf.ignoreRequired;
				}
			}
			var _v = true, form = this;
			$('[validate]', form).each(function() {
						var rtn = form.handValidResult(this);
						if (!rtn)
							_v = false;
					});
			if(_v) _v = form.isNgModelValid();
			return _v;
		}
		
		
		
	});

})(jQuery);
