//修改某一个对象的分类名称分类Id
//conf {tableName:'必须',typeIdKey:'必须',typeNameKey:'没有可不写',categoryKey:'分类KEY'}
var updateTypeDialog=function (conf){
	var  dialog = null;
	var typeId,typeName;
	var def={
	        passConf:{},title:'设置分类',width:300, height:500, modal:true,resizable:true,iconCls: 'icon-collapse',
	        buttonsAlign:'center',
	        buttons:[{
				text:'确定',
				iconCls:'fa fa-check-circle',
				handler:function(e){
				if (!typeId) {
					$.msgShow('提示信息', '请选择分类!');
					return false;
				}
				conf.typeId = typeId;
				conf.typeName = typeName;
				$.post(__ctx + "/system/sysType/updateModelType",conf,function(data){
					if(data=="1"){
						$.topCall.success("设置分类成功！");
						$('.my-easyui-datagrid').datagrid('reload');
					}
					else $.topCall.error("设置分类失败！"+data);
				})
						
				dialog.dialog("close");
				}
			},{
				text:'取消',
				iconCls:'fa fa-times-circle',
				handler:function(){dialog.dialog("close");}
			}]
	};
	this.setTypeId = function(event, treeId, treeNode){
		typeId = treeNode.id;
		typeName = treeNode.name;
	}
	this.show=function(){
		var datagrid = $('.my-easyui-datagrid');
		var idField = datagrid.datagrid('options').idField;
		var ids=$.getSelectIds(datagrid,idField)
		
		
		if (!ids) {
			$.msgShow('提示信息', '请在页面中勾选记录!');
			return false;
		}
		
		
		conf.ids = ids;
		
		var div = $('<div id="updataTypeZtree" style="overflow-y:auto;"><span class="ztree" id="type__Tree_"></span></div>');
		if($("#updataTypeZtree").length==0)$('body').append(div);
		
		dialog = $('#updataTypeZtree').dialog(def);
		var ztreeCreator = new ZtreeCreator('type__Tree_',__ctx + "/system/sysType/getTypesByKey")
		.setCallback({onClick:this.setTypeId})
		.initZtree({typeKey:conf.categoryKey,setting:conf.setting},function(treeObj){}); 
	};
};
	
