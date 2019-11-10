/* 流程变量选择框  
*opt参数{type:user/group/both,callback:function({}){}}
*type 如果是用户的变量。则选择一个用户类型的。
*defId：,nodeId： 获取节点的变量
**/
var FlowVarDialog=function (opt){
	var dialog;
	var def={
	        passConf:opt.type,title:'流程变量选择',width:600, height:300, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ;    // iframe.contents();
						var varJson = win.getVarJson();
						if(varJson == false) return false;
						if(opt.callback){
							opt.callback(varJson,dialog);
							dialog.dialog('close');
						}else{
							dialog.dialog('close');
						}
				}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){dialog.dialog('close');}
			}]
	};
	
	$.extend(def, opt);
	this.show=function(){
		 var src=__ctx+'/flow/node/flowVarDialog?defId='+ opt.defId+"nodeId="+opt.nodeId;
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	}
	return this;
}
	
