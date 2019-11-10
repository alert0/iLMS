/* BO选择框  
*opt参数{single:true,callback:function(){}}
*single{true:false}
**/
var BoSelectDialog=function (opt){
	var dialog = "";
	var def={
	        passConf:'',title:'业务对象选择框',width:950, height:600, modal:true,resizable:true,
	        buttonsAlign:'center',
			buttons:[{
				text:'选择',
				iconCls:'fa fa-check-circle',
				handler:function(e){
						var win=dialog.innerWin ;    // iframe.contents();
						var records = win.$('#boSelectGrid').datagrid('getRows');
						var aryBo=[];
						$(records).each(function(i){
							var id=this['id'];
							var name=this['name'];
							var code=this['code'];
							var desc=this['desc'];
							var packagePath=this['packagePath'];
							
							var objBo={};
							objBo.id=id;
							objBo.name=name;
							objBo.code=code;
							objBo.desc=desc;
							objBo.packagePath=packagePath;
							objBo.isCreateTable=this['isCreateTable'];
							aryBo.push(objBo);
						});
						if(opt.callback){
							opt.callback(aryBo,records,dialog);
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
		 var type="multi";
		 if(opt!=undefined && opt.isSingle){
			 type="single";
		 }
		  
		 var src=__ctx+'/flow/def/boDialog?type=' + type+'&boSaveMode='+opt.boSaveMode;
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	};
	return this;
};

/* 流程BO设置框  
*opt参数{single:true,callback:function(){}}
*single{true:false}
**/
var BoSetDialog=function (opt){
	var dialog ="";
	var def={
	        passConf:'',title:'流程业务对象设置框',width:950, height:550, modal:true,resizable:true,
			buttons:[{
				text:'确定',
				handler:function(e){
						var win=dialog.innerWin ;    // iframe.contents();
						var records = win.saveSetAttr();
						if(opt.callback){
							opt.callback(records,dialog);
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
		 var type="multi";
		 if(opt!=undefined && opt.isSingle){
			 type="single";
		 }
		
		 var src=__ctx+'/flow/def/boSetDialog?type=' + type;
		 dialog=$.topCall.dialog({
			src:src,
			base:def
		});
		return dialog;
	};
	return this;
};
	
