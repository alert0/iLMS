/*******************************************************************************
 * 
 * 代理设置js
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
 
if (typeof bpmAgentSetting == 'undefined')
	bpmAgentSetting = {};

bpmAgentSetting = {
	AGENTTYPE:{
		GLOBAL:'1',//全权代理
		PART:'2',//部分代理
		CONDITION:'3'//条件代理
	},
	/**
	 * 处理保存
	 */
	handleSave:function(){
		var me = this;
		$(".fa-save").click(function(){
			me.save();
		});
	},
	/**
	 * 保存
	 */
	save:function(){
		var jsonObj= this.getJson();
		if(!$.isEmpty(jsonObj.msg)){
			$.topCall.toast("提示信息",jsonObj.msg);
			return ;
		}
		var frm = $('#bpmAgentSettingForm');
		frm.ajaxForm({success:this.showResponse});
		if (!frm.valid()){
		    $.topCall.toast("提示信息","请填写必填项");
		    return;
		}
		var dataForm = $('#bpmAgentSettingForm').setData();
		dataForm.submit();
	},
	/**
	 * 保存成功处理
	 */
	showResponse :function (responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			$.topCall.confirm("提示信息",resultMessage.getMessage()+',是否继续操作',function(r){
				if(r)
					window.location.reload(true);
				else{
					HT.window.refreshParentGrid();
					HT.window.closeEdit(true,'grid');
				}
			});
		}else{
			if(resultMessage.getResult() == 0){
				$.topCall.toast("提示信息",resultMessage.getMessage());
			}else{
				$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
			}
		}
	},
	/**
	 * 获取json数据
	 */
	getJson:function (){
		var type=$.getRadioValue("type"),
			obj={};

		//授权人
		if(isMgr=='1'){
			obj.authId=$("#authId").val();
			obj.authName=$("#authName").val();
		}
		//代理人
		obj.agentId=$("#agentId").val();
		obj.agent=$("#agent").val();
		obj.endDate=$("#endDate").val();
		obj.startDate=$("#startDate").val();
		if(!daysBetween(obj.startDate,obj.endDate)){
			obj.msg ="开始日期不能大于结束日期!";
			return obj;
		}
		if(currentUserId == obj.agentId){
			obj.msg ="代理人不能是本人,请重新选择!";
			return obj;
		}
		if(this.AGENTTYPE.GLOBAL == type){
			if($.isEmpty(obj.agentId)){
				obj.msg ="代理人不能为空!";
				return obj;
			}
				
		}else if(this.AGENTTYPE.PART == type){
			if($.isEmpty(obj.agentId)){
				obj.msg ="代理人不能为空!";
				return obj;
			}
			var flows = $('#partFlow [name="flowKey"]');
			if(!flows || flows.length<1){
				obj.msg ="部分代理必须选择要代理的流程！" ;
				return obj;
			}
		}else if(this.AGENTTYPE.CONDITION == type){//条件代理
			obj.flowKey = $('#conditionFlowKey').val();
			if($.isEmpty(obj.flowKey)){
				obj.msg ="请选择流程定义!";
				return obj;
			}
			var conditions = $("#condition tr[type='subdata']");
			if($.isEmpty(conditions) || conditions.length<1 ){
				obj.msg ="请设置流程条件!";
				return obj;
			}
		}
		return obj;
		
	},
	/**
	 * 处理代理类型
	 */
	handleAgentType:function(){
		var me= this;
		
		$("input[name='type']").click(function(){
			var v=$(this).attr("value");
			me.handAgentType(v)
		})
	},
	handAgentType:function(v){
		switch(v){
			case this.AGENTTYPE.GLOBAL:
				$("#partFlow,#conditionFlow").hide();
				$("#agentTr").show();
				break;
			case this.AGENTTYPE.PART:
				$("#agentTr").show();
				$("#partFlow").show()
				$("#conditionFlow").hide();
				break;
			case this.AGENTTYPE.CONDITION:
				$("#partFlow").hide()
				$("#conditionFlow").show();
				$("#agentTr").hide();
				break;
		}
	},
	/**
	 * 初始化代理类型
	 */
	initAgentType:function(){
		var type =$.getRadioValue("type");
		if($.isEmpty(type) ){
			type =this.AGENTTYPE.PART;
			$('#typePart').attr("checked","checked");
		}
		this.handAgentType(type);
	},
	/**
	* 部分代理 构造一行流程(用于添加到表中)
	*/
	getRow:function (defKey,name){
		var template =$("#tableRowTemplate").val();
		return template.replaceAll("#defKey",defKey).replaceAll("#name",name);
	},
	/**
	* 获取条件代理
	*/
	getAgentConditions:function (){
		
		var conditions = [];
		$("#condition tr").each(function(){
			var tr = $(this);
			var conditionStr = $("[name='condition']",tr).val();
			condition = [];
			if(conditionStr){
				condition = $.parseJSON(conditionStr);
			}
			var conditionDesc = $("[name='conditionDesc']",tr).val();
			var agentId = $("[name='agentId']",tr).val();
			var agentName = $("[name='agentName']",tr).val();
			var item = {
					condition:condition,
					conditionDesc:conditionDesc,
					agentId:agentId,
					agentName:agentName
			};
			conditions.push(item);
		});
		return conditions;
	},
	/**
	* 设置条件代理
	*/
	setAgentConditions:function (conditions){
		var table = $("#condition");
		table.empty();
		for(var i=0;i<conditions.length;i++){
			var cond = conditions[i];
			var tr = this.getConditionRow(cond);
			table.append(tr);
		}
	},
	/**
	* 条件代理 构造一行条件(用于添加到表中)
	*/
	getConditionRow:function(cond){
		var condition = cond.condition;
		var conditionStr = JSON2.stringify(condition);
		var conditionDesc = cond.conditionDesc;
		var agentId = cond.agentid;
		var agentName = cond.agent;
		var template=$("#agentConditionTableRowTemplate").val();
		var html = template.replaceAll("#conditionDesc",conditionDesc).replaceAll("#agentId",agentId).replaceAll("#agentName",agentName);
		var item = $(html);
		$("[name='condition']",item).val(conditionStr);		
		return item;
	},
	/**
	 * 选择用户、流程选择
	 */
	handleSelect:function(){
		var me = this;
		$('.selectUser').click(function(){
			var selectId = $(this).attr('selectId'),
				selectName = $(this).attr('selectName');
			
			CustomDialog.openCustomDialog("userSelector",function(data,dialog){
				var obj=data[0];
				$("#"+selectId).val(obj.id);
				$("#"+selectName).val(obj.name);
				dialog.dialog('close');
			},{
				selectNum:"1"
			});
		});
		$('.addPartFlow').click(function(){
			 BpmDefDialog({isSingle:false,callback:function(aryDef){
					$('#firstRow').remove();
					$.each(aryDef, function(i, n){
						var defKey=	 n.defKey;
						var name= n.name;
						var row=$("#def_" + defKey);
						if(row.length>0)return true;
						var tr=me.getRow(defKey,name);
						$("#bpmAgentItem").append(tr);
					});
			 }}).show();
		});
		$("div").delegate(".remove-flow","click",function(){
			var tr=$(this).closest('tr');
			$(tr).remove();
		});
		
		$('.selectConditionFlow').click(function(){
			var selectId = $(this).attr('selectId');
			var	selectName = $(this).attr('selectName');
			 BpmDefDialog({isSingle:true,callback:function(aryDef){
				$("#"+selectId).val(aryDef[0].defKey);
				$("#"+selectName).val(aryDef[0].name);
			 }}).show();
		});
		//设置代理条件
		$('.setCondition').click(function(){
			var flowKey = $('#conditionFlowKey').val();
			if(flowKey == ""){
				$.topCall.toast("提示信息","请先选择流程！");
				return;
			}
			var conditions =  me.getAgentConditions();
			BpmAgentSettingConditionDialog({flowKey:flowKey,conditions:conditions,callback:function(conditions){
				me.setAgentConditions(conditions);
		 }}).show();
			
				
		});
	 } 
	 
};

$(function() {
	//处理保存
	bpmAgentSetting.handleSave();
	//处理代理类型
	bpmAgentSetting.handleAgentType();
	//初始化代理类型
	bpmAgentSetting.initAgentType();
	bpmAgentSetting.handleSelect();
});

