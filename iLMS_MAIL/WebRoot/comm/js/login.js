Ext.onReady( function() {

	// 用户名
		var txtUserName = new gsl.TextField( {
			fieldLabel :l_userName,
			anchor :'90%',
			allowBlank :false,
			name :'userName'
		});
		// 密码
		var txtUserPwd = new gsl.TextField( {
			fieldLabel :'密　码',
			anchor :'90%',
			allowBlank :false,
			name :'password',
			inputType :'password'
		});
		// 用户身份类型
		var cmbUserType = new gsl.BaseDataComboBox( {
			fieldLabel :'身份类型',
			baseData :userTypeArray,
			addBlank :true,
			forceSelection :true,
			triggerAction :'all',
			allowBlank :false,
			readOnly :true,
			anchor :'90%',
			hiddenName :'type'
		});
		// 验证码
		var txtRandCode = new gsl.TextField( {
			fieldLabel :'验证码',
			allowBlank :false,
			anchor :'50%',
			id :'randCode',
			name :'randCode'
		});

		// 验证码刷新按钮
		var btnRefresh = new Ext.Button( {
			text :'看不清',
			id :'refresh'
		});

		// 登录Form
		var loginForm = new gsl.FormPanel( {
			labelWidth :80,
			keys : [ {
				key : [ 10, 13 ],
				fn :sbmtLoginForm
			} ],
			items : [ txtUserName, txtUserPwd, cmbUserType, txtRandCode ]
		});

		var w = new gsl.Window( {
			title :l_welcome,
			closable :false,
			layout :'fit',
			width :400,
			height :190,
			items : [ loginForm ],
			buttonAlign :'center',
			buttons : [ {
				text :l_login,
				id :'login',
				handler :sbmtLoginForm
			}, {
				text :l_clear,
				id :'clear',
				handler : function() {
					loginForm.form.reset();
					Ext.fly('userName').focus();
				}
			} ]
		});

		w.show();
		Ext.fly(Ext.getDom('randCode').parentNode).createChild( {
			id:'codeImg',
			tag :'img',
			src :'randCode.jsp',
			align :'absbottom'
		});
	 reloadImage=function() 
		{ 
		 document.getElementById("codeImg").src='randCode.jsp?temp='+ (new Date().getTime().toString(36));
			//Ext.getDom('codeImg').src = url; 
		} 
		Ext.fly(Ext.getDom('randCode').parentNode).createChild( {
			tag :'a',
			href :"javaScript:reloadImage()",
			align :'absbottom',
			html:'重新获得验证码'
		});
		Ext.fly('userName').focus();

		// 表单提交函数
		function sbmtLoginForm() {
			if (loginForm.form.isValid()) {
				Ext.getCmp('login').disable();
				Ext.getCmp('clear').disable();
				loginForm.form.submit( {
					waitMsg :'请稍等',
					url :'login-search.action',
					success : function(form, action) {
					window.location.href = "index-init.action";
//						if (cmbUserType.getValue() == '0') {
////							w.hide();
////							supplierWin.show();
//							window.location.href = "supplier/supplier-addInit.action";
//						} else {
//							window.location.href = "index-init.action";
//						}
					},
					failure : function(form, action) {
						gsl.ErrorAlert(action.result.msg);
						Ext.getCmp('login').enable();
						Ext.getCmp('clear').enable();
						// Ext.display.msg('Color Selected', 'You chose {0}.',
					// action.result.msg);
				}
				});
			}
		}
	});
