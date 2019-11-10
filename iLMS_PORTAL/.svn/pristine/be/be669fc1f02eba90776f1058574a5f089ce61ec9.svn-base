/**
 * 表单选择框  
*	conf 参数{single:true,callback:function(){}}
*single{true:false}
**/
var BpmFormDialog = function (conf){
	if(!conf)
		conf = {isSingle:true};
	var dialog=null,
		me = this,
		isSingle = conf.isSingle?(conf.isSingle==true?true:false):true;
	
	var def={
	        passConf:'',title:'表单选择框',width:800, height:500, modal:true,resizable:true,
	        buttonsAlign:'center',
	        buttons:[{
				text:'选择',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ;
						records = win.$('#bpmFormDialog').datagrid('getSelections');
						ary=[];
						
						if($.isEmpty(records) || records.length<1){
							$.topCall.toast('提示信息','请选择记录');
							return;
						}
						
						$(records).each(function(i){
							var id=this['id'],
								formKey=this['formKey'],
								name=this['name'],
								obj={};
							obj.id=id;
							obj.formKey=formKey;
							obj.name=name;
							
							ary.push(obj);
						});
					
						if(conf.callback){
							if(isSingle)
								conf.callback(ary[0],records[0],dialog);
							else
								conf.callback(ary,records,dialog);
							me.closeDialog();
						}else{
							me.closeDialog();
						}
				}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){
					me.closeDialog();
				}
			}]
	};
	
	$.extend(def, conf);
	
	this.show = function(){
		 var defId=conf.defId;
		 var formType=conf.formType;
		 var topDefKey=conf.topDefKey;
		 var nodeId=conf.nodeId;
		 var src=__ctx+'/form/formDef/boFormDefDialog?isSingle=' + isSingle ;
		 if(defId){
			 src+='&defId=' +defId;
		 }
		 if(formType){
			 src+='&formType=' +formType;
		 }
		 if(topDefKey){
			 src+="&topDefKey=" + topDefKey ;
		 }
		  
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
	
