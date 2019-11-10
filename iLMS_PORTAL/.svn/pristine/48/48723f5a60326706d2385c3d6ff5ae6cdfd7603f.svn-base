var IconDialog=function (opt){
	var dialog;
	var def={
	        passConf:'',title:'图标选择框',width:500, height:400, modal:true,resizable:true,iconCls: 'fa fa-meh-o',
	        buttonsAlign:'center',
			buttons:[{
				text:'选择',
				iconCls:'fa fa-check-circle',
				handler:function(e){
					var win=dialog.innerWin ; 
					var select=win.$('.selected');
					if(select && select.length>0){
						if(opt.callback){
							opt.callback(select.attr('alias'),dialog);
						}else{
							dialog.dialog('close');
						}
					}else{
						$.topCall.alert("提示信息","请选择图标");
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
		dialog=$.topCall.dialog({
			src:__ctx+'/system/resources/resourcesIcons',
			base:def
		});
		return dialog;
	}
	return this;
}
	
