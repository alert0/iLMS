
/**
 * GAMC用户信息录入
 */
Ext.onReady(function() {
	// 长度check常量
		// 登录名
		var MAX_LENGTH_USERNAME = 15;
		// 密码最小长度
		var MIN_LENGTH_USERPWD = 6;
		// 密码最大长度
		var MAX_LENGTH_USERPWD = 20;
		// 用户名
		var MAX_LENGTH_NAME = 15;
		// 电话
		var MAX_LENGTH_TEL = 20;
		// 手机
		var MAX_LENGTH_MOBILE = 20;
		// 传真
		var MAX_LENGTH_FAX = 20;
		// 邮箱
		var MAX_LENGTH_EMAIL = 64;

		// 登录名
		var txtUserName = new gsl.TextField( {
			fieldLabel : '登录名',
			allowBlank : false,
			dataIndex : 'userName',
			maxLength : MAX_LENGTH_USERNAME,
			vtype : 'alphanum',
			name : 'user.userName',
			tabIndex : 1,
			width : 150
		});

		// 密码
		var txtPassword = new gsl.TextField( {
			fieldLabel : '密码',
			allowBlank : false,
			minLength : MIN_LENGTH_USERPWD,
			maxLength : MAX_LENGTH_USERPWD,
			tabIndex : 2,
			inputType : 'password',
			name : 'user.userPwd',
			width : 130
		});
		// 确认密码
		var txtConfirmPassword = new gsl.TextField( {
			fieldLabel : '确认密码',
			inputType : 'password',
			minLength : MIN_LENGTH_USERPWD,
			maxLength : MAX_LENGTH_USERPWD,
			allowBlank : false,
			invalidText : "两次输入的密码不同",
			tabIndex : 3,
			name : 'user.confirmUserPwd',
			validator : function() {
				if (txtPassword.getValue() == txtConfirmPassword.getValue()) {
					return true;
				} else {
					return false;
				}
			},
			width : 130
		});
		// 姓名
		var txtName = new gsl.TextField( {
			fieldLabel : '姓名',
			maxLength : MAX_LENGTH_NAME,
			dataIndex : 'name',
			allowBlank : false,
			tabIndex : 4,
			name : 'user.name',
			width : 110
		});
		// 部门
		var cmbDepartment = new gsl.ComboBox( {
			fieldLabel : '部门',
			readOnly : true,
			store : new gsl.JsonCmbStore( {
				type : 'department',
				addBlank : true
			}),
			triggerAction : 'all',
			//allowBlank : false,
			dataIndex : 'departmentId',
			tabIndex : 5,
			hiddenName : 'user.departmentId',
			width : 130
		});
		// 电话
		var txtTelephone = new gsl.TextField( {
			fieldLabel : '电话',
			allowBlank : false,
			maxLength : MAX_LENGTH_TEL,
			tabIndex : 6,
			dataIndex : 'tel',
			name : 'user.tel',
			width : 120,
			vtype : 'telephone'
		});

		// 传真
		var txtFax = new gsl.TextField( {
			fieldLabel : '传真',
			maxLength : MAX_LENGTH_FAX,
			tabIndex : 7,
			dataIndex : 'fax',
			name : 'user.fax',
			width : 120,
			vtype : 'telephone'
		});
		// 手机
		var txtMobile = new gsl.TextField( {
			fieldLabel : '手机',
			maxLength : MAX_LENGTH_MOBILE,
			tabIndex : 8,
			dataIndex : 'mobile',
			name : 'user.mobile',
			width : 120,
			vtype : 'mobile'
		});
		// 邮箱
		var txtEmail = new gsl.TextField( {
			fieldLabel : '电子邮箱',
			maxLength : MAX_LENGTH_EMAIL,
			vtype : 'email',
			dataIndex : 'email',
			tabIndex : 9,
			allowBlank : false,
			name : 'user.email',
			width : 180
		});
		// 角色
		var cmbRole = new gsl.ComboBox( {
			fieldLabel : '角色',
			readOnly : true,
			allowBlank : false,
			dataIndex : 'roleId',
			hiddenName : 'user.roleId',
			triggerAction : 'all',
			autoLoad : true,
			store : new gsl.JsonCmbStore( {
				type : 'role',
				addBlank : true
			}),
			width : 130
		});

		
		var factoryGroup = new gsl.CheckboxGroup( {
			fieldLabel :'工厂',
			allowBlank :false,
			columns :3, 
			id:'factory_cbx',
			msgTarget :'side',
			anchor :'90%',
			url :'comm-cmb.action?type=factory&temptime='+ (new Date().getTime().toString(36))
		});
		
		var checkGroup = new gsl.CheckboxGroup( {
			fieldLabel :'角色',
			allowBlank :false,
			columns :3, 
			id:'role_cbx',
			msgTarget :'side',
			anchor :'90%',
			url :'comm-cmb.action?type=role&temptime='+ (new Date().getTime().toString(36))
		});
		
		var userAddForm = new gsl.FormPanel( {
			iconCls : 'titEntry',
			frame : true,
			width : (Ext.get('entryDiv').getWidth() > 800 ? Ext.get('entryDiv').getWidth() : 800),
			labelWidth : 80,
			renderTo : 'entryDiv',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .9,
					layout : 'form',
					items : [ txtUserName ]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ txtPassword, txtName ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ txtConfirmPassword, cmbDepartment ]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ txtTelephone ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ txtFax ]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ txtMobile ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ txtEmail ]
				} ]
			},factoryGroup, checkGroup
//			, {
//				layout : 'column',
//				items : [checkGroup]
//				
//			}
			], // 
			buttons : [ {
				text : '保存',
				tabIndex : 60,
				handler : saveUser
			} ]
		});
		
		/**
		 * 新增处理
		 */
		function saveUser() {
			var roles = [];
			checkGroup.items.each(function(item) {
				if (item.getValue()) {
					roles.push(item.getRawValue());
				}
			});
			var factories = [];
			factoryGroup.items.each(function(item){
				if(item.getValue()){
					factories.push(item.getRawValue());
				}
			});

			userAddForm.submit( {
				url : 'pub/user-insertUser.action',
				params : {
					roleIds : roles,
					factories : factories
				},
				success : function() {
					userAddForm.getForm().reset();
				}
			});
		}
		adjWidthCtrs = [userAddForm];
	});
