function addFormStepOne() {
	var url = __ctx + '/form/formDef/addFormStepOne';
	var dialog = {};
	var def = {
		passConf : {
			formTypeId : formTypeId
		},
		title : '添加表单定义',
		width : 700,
		height : 400,
		modal : true,
		resizable : false,
		iconCls : 'fa fa-add',
		buttonsAlign : 'center',
		buttons : [ {
			text : '取消',
			iconCls : 'fa fa-times-circle',
			handler : function(e) {
				dialog.dialog('close');
			}
		}, {
			text : '下一步',
			iconCls : 'fa fa-check-circle',
			handler : function() {
				var form = dialog.innerWin.triggerScopeFun("nextStep");
				if (form) {
					var tempform = form.clone();
					tempform.find("select").val(form.find("select").val());
					tempform.find("textarea").val(form.find("textarea").val());
					$("body").append(tempform);
					tempform.attr("target","_blank");
					tempform.submit();
					dialog.dialog('close');
					tempform.remove();
				}
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


//表单授权
function authorize(formKey) {
	var url = __ctx + '/form/rights/rightsDialog?formKey='+ formKey;
	var dialog = {};
	var def = {
		passConf : {permissionTypes:["all"]},
		title : '表单授权',
		width : 1200,
		height : 600,
		modal : true,
		resizable : false,
		iconCls : 'icon-collapse',
		buttonsAlign : 'center',
		buttons : [ {
			text : '保存',
			iconCls : 'fa fa-check-circle',
			handler : function(e) {
				dialog.innerWin.triggerScopeFun("savePermission");
			}
		}, {
			text : '取消',
			iconCls : 'fa fa-times-circle',
			handler : function() {
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
