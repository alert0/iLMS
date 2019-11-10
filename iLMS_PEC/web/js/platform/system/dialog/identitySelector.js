/***
 * 流水号选择框
 * @param {} call(script)
 * @return {}
 */
var IdentitySelector=function (call,opt){
	var dialog;
	var def={
	        passConf:"",title:'流水号选择框',width:800, height:550, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ;    // iframe.contents();
						var scripts = win.getScript();
						if(call){
							call(scripts);
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
		 var src=__ctx+'/system/identity/dialog';//常用脚本
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	}
	return this;
}
	
