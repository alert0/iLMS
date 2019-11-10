/*******************************************************************************
 * 
 * 流程表单设置js
 * 
 * <pre>
 *  
 * 作者：hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-7-9-上午11:15:52
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * 
 ******************************************************************************/
 
if (typeof bpmNodeDefForm == 'undefined')
	bpmNodeDefForm = {};

bpmNodeDefForm = {
	handFormType:function(){
		this.handGlobalFormType();
		this.handInstFormType();
		this.handNodeFormType();
	},
	/**
	 * 全局表单
	 */
	handGlobalFormType:function(){
		$("select[name='globalFormType']").change(function(){
			var instForm=$("select[name='instFormType']"),
					me=$(this),
					val=me.val(),
					globalFormNodeId= me.parents(".formBlock").find("[name='nodeId']").val(),
					instFormNodeId= instForm.parents(".formBlock").find("[name='nodeId']").val(),
					globalFormBox_ =	$("#formBox_"+globalFormNodeId),
					instFormBox_ =	$("#formBox_"+instFormNodeId),
					globalForm_ = $(".form_"+globalFormNodeId),
					instForm_ = $(".form_"+instFormNodeId),
					globalFrameForm_ = $(".frame_"+globalFormNodeId),
					instFrameForm_ = $(".frame_"+instFormNodeId),
					globalUrl_ = $(".url_"+globalFormNodeId),
					instUrl_ = $(".url_"+instFormNodeId);
			
			
			instForm.val(val);
			if(val==''){
				globalFormBox_.hide();
				instFormBox_.hide();
			}else{
				globalFormBox_.show();
				instFormBox_.show();
				if(val=='inner'){//在线表单
					globalForm_.show();
					globalUrl_.hide();
					instForm_.show();
					instUrl_.hide();
					globalFrameForm_.hide();
					instFrameForm_.hide();
				}
				else if(val=='frame'){
					globalForm_.hide();
					globalUrl_.hide();
					instForm_.hide();
					instUrl_.hide();
					globalFrameForm_.show();
					instFrameForm_.show();
				}else{
					globalForm_.hide();
					globalUrl_.hide();
					instForm_.hide();
					instUrl_.hide();
					globalFrameForm_.hide();
					instFrameForm_.hide();
				}
			}
		});
	},
	/**
	 * 实例表单
	 */
	handInstFormType:function(){
		$("select[name='instFormType']").change(this.changeFormType);
	},
	/**
	 * 	节点表单
	 */
	handNodeFormType:function(){
		$("select[name='nodeFormType']").change(this.changeFormType);
	},
	changeFormType :function(){
			var me = $(this),
				val=me.val(),
				nodeId = me.parents(".formBlock").find("[name='nodeId']").val(),
				formBox_ =	$("#formBlock_"+nodeId);
				form_ = $(".form_"+nodeId),
				frame_ = $(".frame_"+nodeId);
			if(val==''){
				formBox_.hide();
				form_.hide();
				frame_.hide();
			}
			else{
				formBox_.show();
				if(val=='inner'){
					form_.show();
					frame_.hide();
				}
				else if(val=='frame'){
					form_.hide();
					frame_.show();
				}
			}
	},
	//清除表单
	clearForm :function (obj){
		var btn=$(obj),tdObj=btn.parent(),formBox=btn.closest(".formBlock")
		var formName =$("input.formName",tdObj).val();
		$("input.formKey",tdObj).val('');
		$("input.formName",tdObj).val('');
		$("span[name='spanForm']",tdObj).text('');
		$(obj).siblings("a.grant").hide();
		//$("[name='nodeFormType']",formBox).val('');
	},
	//选择表单
	selectForm:function (obj,type,isPc) {
		var _self=this;
		var formType=isPc?"pc":"mobile";
		
		
		BpmFormDialog({defId:defId,formType:formType,callback:function(data){
				var formKey = data.formKey,
				name =data.name;
				tdObj =$(obj).parent();
				$("input.formKey",tdObj).val(formKey);
				$("input.formName",tdObj).val(name);
				//给表单添加 超链接，使之能看到表单明细
				var namesUrl="<a target='_blank' href="+__ctx+"/form/form/preview?formKey="+formKey+" >"+name+"</a>";
				$("span[name='spanForm']",tdObj).html(namesUrl);
				
				_self.setExtForm(type,isPc,formKey,name,namesUrl);
			}
		}).show();
	},
	/**
	 * 批量设置手机表单审批。
	 */
	setBatNodeMobileForm:function(formKey,name,namesUrl){
		if($("#setNodeMobile:checked").length!=1) return;
		
		
		$("[name='mobileFormKey']").each(function(){
			$(this).val(formKey);
		});
		$("[name='mobileFormName']").each(function(){
			$(this).val(name);
		});
		$(".tdNodeMobile .spanNodeForm").each(function(){
			$(this).html(namesUrl);
		});
		
		$("[name='nodeFormType']").val("inner");
		
		$(".trNodeForm").show();
		$(".trNodeFrame").hide();
		$("[name='tbNodeForm']").show();
	},
	/**
	 * 设置其他的表单。
	 */
	setExtForm:function(type,isPc,formKey,name,namesUrl){
		var _self=this;
		if(type == 'global'){//全局表单  需要设置实例表单
			var instFormObj=$("div[name='instForm']");
			$("select[name='instFormType']").val("inner");
			instFormObj.show();
			
			$("#formBlock_instFormType").show();
			$("div.url_instFormType").hide();
			
	
			if(isPc){
				$("[name='instFormKey']",instFormObj).val(formKey);
				$("[name='instFormName']",instFormObj).val(name);
				$(".spanForm_pc",instFormObj).html(namesUrl);
			}
			else{
				$("[name='instMobileFormKey']",instFormObj).val(formKey);
				$("[name='instMobileFormName']",instFormObj).val(name);
				$(".spanForm_mb",instFormObj).html(namesUrl);
				//选择手机表单
				_self.setBatNodeMobileForm(formKey,name,namesUrl);
			}
		}
	},
	
	/**
	 * 处理选中所有的设置
	 */
	handChAll:function(){
		$('.chAll').unbind("click").click(function(){
			var v =	$(this).attr('var'),
				obj = $('.'+v), c = this.checked;
			if(c){
				obj.prop('checked',true);
				obj.attr('checked',true);
			}else{
				obj.prop('checked',false);
				obj.removeAttr('checked');
			}
		
		});	
	},
	//添加成功
	showResponse :function (responseText) {
		var resultMessage=new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			$.topCall.success( "保存表单设置成功");
		}else{
			$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
		}
	},
	/**
	 * 保存处理
	 */
	save:function(){
		var me = this,
		frm = $('#dataForm');
		
		$('.btn.fa-save').click(function() {
			var type=$("#instFormType").val();
			var isEmpty= me.isEmptyForm();
			if(!isEmpty){
				if(type=='inner'){
					var instFormKey=$("#instFormKey").val();
					if(!instFormKey||instFormKey==0){
						$.topCall.alert('请设置流程实例业务表单!');
						return;
					}
				}else if(type=='frame'){
					var instFormFrameUrl = $("input[name='instFormFrameUrl']").val();
					if(!instFormFrameUrl||instFormFrameUrl==''){
						$.topCall.alert('请设置流程实例明细URL地址!');
						return;
					}
				}
			}

			frm.ajaxForm({success:bpmNodeDefForm.showResponse});
			
			if (frm.valid()) {
				frm.submit();
			}; 
		});
	},
	isEmptyForm:function (){
		var isEmpty=true,
			globalFormObj=$("div[name='globalFormUrl']"),
			globalUrlObj=globalFormObj.find('input[name="globalFormUrl"]'),
			globalDetailObj=globalFormObj.find('input[name="globalDetailUrl"]'),
			globalUrl=(globalUrlObj!=undefined)?globalUrlObj.val():"",
			globalDetailUrl=(globalDetailObj!=undefined)?globalDetailObj.val():"",
			globalFormKey=($("#globalFormKey")!=undefined)?$("#globalFormKey").val():0;
		if(globalUrl!=""||globalFormKey!=0||globalDetailUrl!=""){
			isEmpty=false;
		}
		$("div[name='nodeForm']").each(function(){
			var formKeyObj=$(this).find('input[name="formKey"]'),
				formKey=(formKeyObj!=undefined)?formKeyObj.val():0,
				formUrlObj=$(this).find('input[name="formUrl"]'),
				formUrl=(formUrlObj!=undefined)?formUrlObj.val():"",
				formDetailObj=$(this).find('input[name="detailUrl"]'),
				formDetail=(formDetailObj!=undefined)?formDetailObj.val():"";
			if(formKey!=0||formUrl!=""||formDetail!=""){
				isEmpty=false;
			}
		});
		return isEmpty;
	},
	/**
	 * 验证处理器是否有效
	 */
	validHandler:function (){
		$("input.handler").blur(function(){
			var obj=$(this);
			var val=obj.val();
			if(val.trim()==""){
				return;
			}
			var params={handler:val};
			$.post("validHandler.ht",params,function(data){
				var json=eval("(" +data +")");
				if(json.result!='0'){
					$.topCall.success(json.msg,function(){},"提示信息");
				}
			});
		});
	},
	/**
	 * 表单授权
	 */
	authorize:function(obj,type,permissionTypes,nodeId,isInst){
		var formKey = $("input.formKey",$(obj).parent()).val();
		if(!formKey){
			 $.topCall.alert('请选择表单!');
			 return;
		}
		var url= __ctx + '/form/rights/rightsDialog?formKey=' + formKey;
		if(flowKey&&flowKey!='') url+="&flowKey="+flowKey;
		if(nodeId&&nodeId!='') url+="&nodeId="+nodeId;
		if(parentFlowKey&&parentFlowKey!='') url+="&parentFlowKey="+parentFlowKey;
		if(isInst)  url+="&instId=true";
		
		var width = width || 800;
		var height = height || 600;
		var dialog;
		var def = {
			title : "授权页面",
			width : width,
			height : height,
			modal : true,
			resizable : true,
		};
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
	},
	/**
	 * 子表授权
	 */
	authorizeSub:function(defId,nodeId,parentDefKey){
		var url= __ctx + '/form/rights/subRightsDialog?nodeId='+nodeId+'&defId='+defId+'&parentDefKey='+parentDefKey;
		
		var width = width || 800;
		var height = height || 600;
		var dialog;
		var def = {
			title : "子表授权页面",
			width : width,
			height : height,
			modal : true,
			resizable : true,
		};
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
	}
	
};



//初始化设置
$(function(){
	//处理表单类型
	bpmNodeDefForm.handFormType();
	//处理全选
	bpmNodeDefForm.handChAll();
	
	bpmNodeDefForm.save();
	bpmNodeDefForm.validHandler();
});
