/***
 * 常用规则选择框
 * @param {} call(rule)
 * @return {}
 */
var RuleSelector=function (call,opt){
	var dialog;
	var isSingle = false;
	if(opt.isSingle){
		isSingle = true;
	}
	var def={
	        passConf:{'isSingle':isSingle},title:'引用规则',width:800, height:550, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				handler:function(e){
						var win=dialog.innerWin ;    // iframe.contents();
						var data = win.getRule();
						if(data==null){
							$.topCall.message({base:{type:'alert',title:'错误提示',msg:"请选择规则！",icon:'error'}});
							return;
						}
						if(call){
							call(data);
							dialog.dialog('close');
						}else{
							dialog.dialog('close');
						}
				}
			},{
				text:'取消',
				handler:function(){dialog.dialog('close');}
			}]
	};
	
	$.extend(def, opt); 
	this.show=function(){
		 var src=__ctx+'/bo/rule/dialog?belongType='+def.belongType;
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	}
	return this;
}
	
