var formType = formType || "";
function newForm() {
	var url = __ctx + '/form/formDef/formDefDialog';
	var dialog = {};
	var def = {
		passConf : {
			formTypeId : formTypeId
		},
		title : '选择表单定义',
		width : 800,
		height : 600,
		modal : true,
		resizable : false,
		 
		buttonsAlign : 'center',
		buttons : [{
			text : '确定',
			iconCls : 'fa fa-check-circle',
			handler : function() {
				var id = dialog.innerWin.getSelectId();
				if(!id){
					$.topCall.error( '请选择一个表单定义！');
					return;
				}
				showSelectTemplate(id,true);
				dialog.dialog('close');
			}
		},{
			text : '取消',
			iconCls : 'fa fa-times-circle',
			handler : function(e) {
				dialog.dialog('close');
			}
		} ]
	};
	var show = function() {
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
		return dialog;
	};
	show();
};

function newVersion(id) {
	$.topCall.confirm('提示信息', '确认新建版本吗？', function(r) {
		if(!r) return;
		var url = __ctx + '/form/form/newVersion?id=' + id;
		$.post(url, function(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			if (resultMessage.isSuccess()) {
				$.topCall.success('新建成功！');
				$('#formList,#formVersions,#grid').datagrid('reload');
			} else {
				$.topCall.error(resultMessage.getMessage());
			}
		});
		
	});
}
function publishForm(id) {
	$.topCall.confirm('提示信息', '确认发布吗？', function(r) {
		if(!r) return;
			
		var url = __ctx + '/form/form/publish?id=' + id;
		$.post(url, function(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage( responseText);
			if (resultMessage.isSuccess()) {
				$.msgShow('提示信息', '发布成功！');
				$('#formList,#formVersions').datagrid('reload');
			} else {
				$.topCall.error(resultMessage.getMessage());
			}

		});
	});
}

function goToSetFormHtmlJsp(json, id) {
	var params = "?";
	params += "defId=" + id + "&" + "tableNames=" + json.tableNames + "&" + "templateAlias="+json.templateAlias+"&" + "formType="+formType ;
	if(parent.formNextStep){
		parent.formNextStep("next","/form/form/formEdit" + params,'formEdit');
	}else{
		$.openFullWindow(__ctx + '/form/form/formEdit' + params); 
	}
}
function showSelectTemplate(id,closable) {
	url = __ctx + '/form/template/selectTemplate?defId=' + id + "&isSimple=1&formType="+formType;
	var dialog = {};
	var h = 350;
	var w = 550;
	var def = {
		passConf : {},
		title : '选择表单模版',
		width : w,
		height : h,
		closable: closable,
		modal : true,
		resizable : false,
		iconCls : 'fa icon-collapse',
		buttons : [ {
			text : '确定',
			handler : function(e) {
				dialog.innerWin.ok(function(json) {
					goToSetFormHtmlJsp(json, id);
				});
				dialog.dialog('close');
			}
		} ]
	};
	this.show = function() {
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
		return dialog;
	};
	this.show();
}