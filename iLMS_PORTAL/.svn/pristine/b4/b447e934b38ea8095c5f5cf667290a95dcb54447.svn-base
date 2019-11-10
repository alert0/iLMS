/*******************************************************************************
 * 
 * 流程代理条件设置js
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
 
if (typeof bpmAgentSettingCondition == 'undefined')
	bpmAgentSettingCondition = {};

 conditionTemp = null;

bpmAgentSettingCondition = {
	/**
	 * 初始化条件设置
	 */
	initConditions:function(conditions){
		for(var i=0;i<conditions.length;i++){
			this.addAgentCondition(conditions[i]);
		}	
	},
	/**
	 * 添加一个代理条件
	 */
	addAgentCondition:function(cond){
		var conditionTemp = $("#divAgentTemplate [name='conditionItem']").clone(true,true),
			agentId = "",
			agentName= "",
			condition =  "";
		if(cond){
			agentId = cond.agentId;
			agentName= cond.agentName;
			condition = cond.condition;
		}
	
		$('[name="agentId"]',conditionTemp).val(agentId);
		$('[name="agentName"]',conditionTemp).val(agentName);
		$("#agentConditionForm").append(conditionTemp);
		
		$("div[name='ruleDiv']",conditionTemp).linkdiv({data:condition,updateContent:updateContent,rule2json:rule2json});
		

	},
	/**
	 * 删除一个代理条件
	 */
	delAgentCondition:function(obj){
		$(obj).closest("[name='conditionItem']").remove();
	},
	//规则设置  begin ===
	initDiv:function(){
		var me = this;
		$('.del-agent-condition').click(function(){
			var obj = this;
			$.topCall.confirm('提示信息','确认删除吗？',function(r){
				if (r)
					me.delAgentCondition(obj);
			});
		});
		$('.add-agent-condition').click(function(){
			me.addAgentCondition();
		});
		$('.add-rule').click(function(){
			me.addDiv(this,1);
		});
		$('.add-script').click(function(){
			me.addDiv(this,2);
		});
		$('.assemble').click(function(){
			me.assembleDiv(this);
		});
		$('.split').click(function(){
			me.splitDiv(this);
		});
		$('.remove').click(function(){
			me.removeDiv(this);
		});
		$('.get-data').click(function(){
			me.getData(this);
		});
		$('.selectUser').click(function(){
			var self =$(this),
				selectId = self.attr('selectId'),
				selectName = self.attr('selectName'),
				id = self.siblings("[name='"+selectId+"']"),
				name = self.siblings("[name='"+selectName+"']")
			 UserSelectDialog({isSingle:true,type:'1',callback:function(userIds,fullnames){
					id.val(userIds[0]);
					name.val(fullnames[0]);			 	
			 }}).show();
			
		});
	},
	getRuleDiv:function (t){ 
		var parent = $(t).parent().parent();
		return $("div[name='ruleDiv']",parent);
	},

	addDiv:function (obj,ruleType){
		var aa = this;
		var json ={ruleType:ruleType}; 
		if(ruleType ==1){
			new RuleConditionDialog({json:json,defId:defId,nodeId:nodeId,callBack:function(data){
				aa.getRuleDiv(obj).linkdiv("addDiv",data);
				}}).show();
		}else{
			new ScriptDialog({json:json,defId:defId,nodeId:nodeId,callBack:function(data){
				aa.getRuleDiv(obj).linkdiv("addDiv",data);
			}}).show();
		}
		
	},

	removeDiv:function (obj){
		this.getRuleDiv(obj).linkdiv("removeDiv");	
	},

	assembleDiv:function (obj){
		this.getRuleDiv(obj).linkdiv("assembleDiv");
	},

	splitDiv:function (obj){
		this.getRuleDiv(obj).linkdiv("splitDiv");
	},

	getData:function (obj){
		var json =this.getRuleDiv(obj).linkdiv("getData");
		return json;
	},
	// 规则设置 end ===
	saveData:function(){
		var msg="",
			conditions = this.getConditions();
		
		if(!conditions || conditions.length<1)
			msg= '请设置至少一个以上 <b>代理人设置</b>!';
	
		
		for(var i=0;i<conditions.length;i++){
			var cond = conditions[i];
			if(!cond.agentId)
				msg+=String.format('第{0}个 <b>代理人设置</b> 没有指定代理人!<br/>',i+1);
		}
		
		if(!$.isEmpty(msg)){
			$.topCall.toast("提示信息",msg);
			return false;
		}
		return conditions;
	},
	/**
	* 获取代理条件设置
	*/
	getConditions:function (){
		var me= this, conditions = [],
			conditionItems = $("div[name='conditionItem']",$("#agentConditionForm"));
		conditionItems.each(function(){
			var item = $(this);
			var id = $("[name='agentId']",item).val();
			var name = $("[name='agentName']",item).val();//可以处理一下这里，修改json获取方式
			var condition = me.getData(item);
			var conditionDesc = '';
			var cond={
					agentId:id,
					agentName:name,
					condition:condition,
					conditionDesc:conditionDesc
				};
			conditions.push(cond);
		});
		return conditions;
	}
}


 //  加载页面回显数据
$(function(){

	bpmAgentSettingCondition.initConditions(conditions);	
	if($("#agentConditionForm [name='conditionItem']").length==0)
		bpmAgentSettingCondition.addAgentCondition();
	bpmAgentSettingCondition.initDiv();
});
