/**
 *重置流程分类  
 *	@param conf 
 *			{single:true,callback:function(){}}
 *		 其中：single{true:false}
**/
var BpmSetCategoryDialog=function (conf){
	var dialog=null,
		me = this;
	var def={
	        passConf:'',title:'重置流程分类',width:400, height:250, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ,   // iframe.contents();
							records ;
						win.$("[name='groupTypeId']").each(function(){
							var me = $(this);
							records = me.val();
						});
						if($.isEmpty(records) || records.length<1){
							$.topCall.toast('提示信息','请选择分类');
							return;
						}
						
						if(conf.callback)
							conf.callback(records,me);
				}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){me.closeDialog();}
			}]
	};
	
	$.extend(def, conf);
	this.show=function(){
		 var src=__ctx+'/flow/def/defSetCategoryDialog';
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
};
	
