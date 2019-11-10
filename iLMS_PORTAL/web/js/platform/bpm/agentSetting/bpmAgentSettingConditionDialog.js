/**
 *流程代理条件设置窗口 
 *	@param conf 
 *			{single:true,callback:function(){}}
 *		 其中：single{true:false}
**/

var BpmAgentSettingConditionDialog=function (conf){
	var dialog=null,
		me = this;
	var def={
	        passConf:conf.conditions,title:'流程代理条件设置',width:800, height:500, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ,   // iframe.contents();
							rtn = win.saveCondForm();
						if(!rtn)
							return;
						if(conf.callback)
							conf.callback(rtn,me);
						me.closeDialog();
				}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){me.closeDialog();}
			}]
	};
	
	$.extend(def, conf);
	this.show=function(){
		 var src=__ctx+'/flow/agent/agentConditionDialog?flowKey=' + conf.flowKey;
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	};
	this.closeDialog = function(){
		dialog.dialog('close');
	};
	return this;
}
	
