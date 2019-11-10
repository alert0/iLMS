/***
 *  <pre>
 *  
 *	流程用户是否可以指定 的条件规则选择框
 *
 *	</pre>
 */
/**
 * 脚本规则 conf{}
 * @param json:回显的脚本{Script：‘’，conDesc:'条件描述'}
 * @param defId: 必须 流程定义的ID
 * @param nodeId: 某节点的Id
 * @param callBack 回调方法（json），返回填充值了的json
 */
var ScriptDialog = function (conf){
	var dialog;
	
	if(!conf.json) conf.json={ruleType:2};
	if(!conf.nodeId) conf.nodeId = "";
	var def={
		        passConf:{json:conf.json,defId:conf.defId,nodeId:conf.nodeId},title:'构造脚本',width:650,height:363, modal:true,resizable:true,buttonsAlign:'center',
				buttons:[{
					text:'确定',
					iconCls:'fa fa-check-circle',
					handler:function(e){
						var win=dialog.innerWin;   				
						var data = win.getScript();
						if(data == false) return;
						
						if(conf.callBack) conf.callBack(data);
						dialog.dialog('close');
					}
				},{
					text:'取消',
					iconCls:'fa fa-times-circle',
					handler:function(){dialog.dialog('close');}
				}]
	};
	
	this.show=function(){
		 dialog=$.topCall.dialog({
			src:__ctx+'/flow/def/scriptDialog?defId='+conf.defId+'&nodeId='+conf.nodeId,
			base:def
		});
	};
	return this;
};
	
/**
 * 条件规则 {json:json,defId:defId,nodeId:nodeId}
 * @param json:回显的脚本{Script：‘’，conDesc:'条件描述'}
 * @return {}
 */
var RuleConditionDialog = function (conf){
	var dialog;
	if(!conf.json) conf.json={ruleType:1};
	if(!conf.nodeId) conf.nodeId = "";
	var def={
	      		passConf:conf.json,title:"构造条件",width:650,height:350, modal:true,resizable:true,buttonsAlign:'center',
				buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin;   				
						var data = win.getData();
						if(data == false) return;
						if(conf.callBack){
							conf.callBack(data);
						}
						dialog.dialog('close');
					}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){dialog.dialog('close');}
			}]
	};
	
	this.show=function(){
		 dialog=$.topCall.dialog({
			src: __ctx+'/flow/usercalc/ruleConditionDialog?defId='+conf.defId+'&nodeId='+conf.nodeId,
			base:def
		});
		return dialog;
	};
	
	return this;
};