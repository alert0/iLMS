/**
 *流程定义选择  
 *	@param conf 
 *			{isSingle:true,callback:function(){}}
 *		 其中：isSingle{true:false}
**/
var BpmDefDialog=function (conf){
	if(!conf)
		conf = {isSingle:true};
	var dialog=null,
		me = this,
		isSingle = conf.isSingle;
	var BpmButtons = [{
			text:'确定',
			iconCls:'fa fa-check-circle',
			handler:function(e){
					var win=dialog.innerWin ,   // iframe.contents();
						records = win.$('#defSelectGrid').datagrid('getRows'),
						aryDef=[];
					if($.isEmpty(records) || records.length<1){
						$.topCall.toast('提示信息','请选择记录');
						return;
					}
					$(records).each(function(i){
						var defId=this['defId'];
						var defKey=this['defKey'];
						var name=this['name'];
						var objDef={};
						objDef.defId=defId;
						objDef.defKey=defKey;
						objDef.name=name
						aryDef.push(objDef);
					});
					if(conf.callback)
						conf.callback(aryDef,records,me);
					me.closeDialog();
			}
		},{
			text:'取消',
			iconCls:'fa fa-times-circle',
			handler:function(){me.closeDialog();}
		}];
	if(!isSingle){
		var clearBtn = {
				iconCls:'fa fa-times-circle',
				text : '清空',
				handler : function() {
					var win=dialog.innerWin;
					win.$('#defSelectGrid').datagrid('loadData',{total:0,rows:[]});
					win.$('#bpmDefGridList').datagrid('unselectAll');
				}
			};
		BpmButtons.insert(1,clearBtn);
	}
	var def={
	        passConf:{defJson:conf.defJson},title:'流程选择',width:1200, height:500, modal:true,resizable:true,iconCls: 'icon-collapse fa fa-table',
	        buttonsAlign:'center',
	        buttons:BpmButtons
	};
	
	$.extend(def, conf);
	this.show=function(){
		 var type="single";
		 if(!isSingle)
			 type="multi";
		 var src=__ctx+'/flow/def/defDialog?type=' + type;
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
	
