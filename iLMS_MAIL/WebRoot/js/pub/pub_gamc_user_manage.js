/**
 * GAMC用户信息管理
 */
Ext.onReady(function() {
	var pageAuth = 'SP'; //gsl.getPageAuth('010201');
	var busyFlg = false;
	
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
	
	//用户信息Store
	var store = new gsl.JsonStore({
        url : 'pub/user-queryUserForPage.action',
        fields:['userName','name','departmentName','roleList','factoryList',
                'tel','mobile','email','userStatus','loginNum', 'departmentId']
    });
    store.on('load',function(){
		busyFlg = false;
	});
	
	// 用户名
    var userName = new gsl.TextField({
        fieldLabel: l_loginName,
        id:'userName',
        width : 150
    });
    
    // 姓名
    var name = new gsl.TextField({
        fieldLabel: l_user_name,
        id:'name',
        width : 150
    });
	
	//查询面板
	var queryPanel = new gsl.FormPanel({
		region : 'north',
		height : 110,
        iconCls:'titQuery',
        hideCollapseTool: false,
//        titleCollapse: true,
        collapsible: true,
//        renderTo:'queryDiv',
//        width :(Ext.get('queryDiv').getWidth() > 800 ?Ext.get('queryDiv').getWidth():800),
        labelWidth:80,
        items : [{
            layout : 'column',
            items : [{
            	columnWidth : .33,
	            layout : 'form',
	            items : [userName]
            },{
            columnWidth : .33,
	            layout : 'form',
	            items : [name]
            }]
        }],buttons:[{
            text : l_query,
            handler : queryFn
        }],
        keys : [ {
 			key : 13,
 			fn : queryFn,
 			scope : this
 		}]
    });
	
    //查询方法
    function queryFn(){
    	if(busyFlg){
			return;
		}
		busyFlg = true;
        store.baseParams = {
            'queryInfo.userName':userName.getValue(),
            'queryInfo.name':name.getValue()
//            'queryInfo.roleId':role.getValue(),
//            'queryInfo.departmentId':department.getValue()
        };
        store.load({params:{start:0, limit:pageSize}});
    }
    
    //---------------------------
    // 登录名
    var lblmUserName = new gsl.LabelField({
        fieldLabel: l_loginName,
        dataIndex:'userName',
        name:'user.userName'
    });
    // userId
//    var hdnUserId = new Ext.form.Hidden({
//    	dataIndex:'userName',
//        name:'user.userName'
//    });
    
    var txtName = new gsl.TextField({
        fieldLabel: '姓名',
        dataIndex:'name',
        width : 120,
        name:'user.name'
    });
    
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
		width : 120
	});
	
	//状态
	var cmbUserStatus = new gsl.BaseDataComboBox( {
		valueField : 'code',
		id : 'cmbUserStatus',
		dataIndex : 'userStatus',
		hiddenName : 'user.userStatus',
		baseData : sysUserStatusArray,
		readOnly : true,
		addBlank : false,
		allowBlank : false,
		fieldLabel : '状态',
		width : 120
	});
    
//    checkGroup.columns =3;
    // 密码
    var txtPassword = new gsl.TextField({
		fieldLabel: '密码',
        validator:function(){
			if(txtPassword.getValue() !='' && txtPassword.getValue().length < MIN_LENGTH_USERPWD){
				this.invalidText=l_msg_minLenght_6;
				return false;
			}else if(txtPassword.getValue() !='' && txtPassword.getValue().length > MAX_LENGTH_USERPWD){
//				该输入项的最大长度为40
				this.invalidText=l_msg_maxLenght_40;
				return false;
			}
			return true;
		},
		inputType: 'password',
		name: 'user.userPwd'
        ,anchor:'95%'
	});
    // 确认密码
    var txtConfirmPassword = new gsl.TextField({
		fieldLabel: l_confirmPwd,
		inputType: 'password',
//		两次输入的密码不同
		invalidText:"两次输入的密码不同",
		name: 'user.confirmUserPwd',
		validator:function(){
			if(txtPassword.getValue() ==txtConfirmPassword.getValue()){
				return true;
			}else{
				return false;
			}
		}
        ,anchor:'95%'
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
		
		var factoryGroup = new gsl.CheckboxGroup( {
			url :'comm-cmb.action?type=factory&temptime='+ (new Date().getTime().toString(36)),
			fieldLabel :'工厂',
			allowBlank :false,
			columns :3, 
			id:'factory_cbx',
			msgTarget :'side',
			anchor :'90%'
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
    
    // 修改Form
    var userModifyForm = new gsl.FormPanel( {
        labelWidth:70,
        autoScroll:true,
        items : [//hdnUserId,
        	{
            layout:'column',
            items:[{
            	columnWidth : .9,
                layout : 'form',
                items : [lblmUserName]
            }]
        },
        {layout : 'column',
          items : [{
             columnWidth : .5,
             layout : 'form',
             items : [txtPassword,txtName]
             },{
             columnWidth : .5,
             layout : 'form',
             items : [txtConfirmPassword,cmbDepartment]
             }]
        },
        {layout : 'column',
          items : [{
             columnWidth : .5,
             layout : 'form',
             items : [txtTelephone]
             },{
              columnWidth : .5,
             layout : 'form',
             items : [cmbUserStatus]
             }]
        },
        {layout : 'column',
          items : [{
        	  columnWidth : .5,
              layout : 'form',
              items : [txtMobile]
              },{
            	  columnWidth : .5,
             layout : 'form',
             items : [txtFax]
             }]
        },{layout : 'column',
          items : [{
        	  columnWidth : .5,
              layout : 'form',
              items : [txtEmail]
              }]
        },factoryGroup, checkGroup],
        buttons:[{
//        	保存
            text : l_save,
            handler : updateUser
        },{
//        	关闭
            text : l_close,
            handler : function(){
                userWin.hide();
            }
        }]
    });
    
    // 修改窗口
    userWin = new gsl.Window({
        width: 700,
        height: 400,
        items: [userModifyForm]
    }); 
    userWin.on('show',function(){
//    	if(Ext.getCmp('role_cbx') != null) {
//    		userModifyForm.remove('role_cbx');
//		}
//    	factoryGroup = new gsl.CheckboxGroup( {
//			fieldLabel : '工厂',
//			allowBlank :false,
//			columns :3, 
//			id:'role_cbx',
//			msgTarget :'side',
//			anchor :'90%',
//			url :'comm-cmb.action?type=factory&temptime='+ (new Date().getTime().toString(36))
//		});
//		userModifyForm.add(factoryGroup);
//		userModifyForm.doLayout();
	});
	
	function updateUser(){
		
    	if(userModifyForm.form.isValid() && txtPassword.getValue() == txtConfirmPassword.getValue()) {
    	var factories= [];
        factoryGroup.items.each(function(item) {
               if (item.getValue()) {
                 factories.push(item.getRawValue());
               }
         });
         var roles = [];
         checkGroup.items.each(function(item){
         	   if (item.getValue()) {
	             roles.push(item.getRawValue());
	           }
         });
        userModifyForm.submit({
            url:'pub/user-update.action',
            params : {
                'user.userName' :lblmUserName.getValue(),
                departmentId :cmbDepartment.getValue(),
                //角色
                roleIds : roles,
                //  工厂
                factories:factories
            },
            success:function(form,action){
                userWin.hide();
                queryFn();
            }
        });
    	} else if(txtPassword.getValue() != txtConfirmPassword.getValue()) {
    		txtConfirmPassword.markInvalid();
    	}
    }
    
    
    //行操作
	var actionColumn = new gsl.RowActions({
		auth : pageAuth,
      	edit : function(record){
       
	        userModifyForm.form.reset();
	        userWin.setTitle(l_userModify);
	        userWin.show();
	        
	        userModifyForm.getForm().setValues(record.data);
	        
	        var factoryList = record.get('factoryList');
	        var factories = factoryList.split(',');
            factoryGroup.items.each(function(item) {
                for(var i = 0; i < factories.length; i++){
                    if(item.inputValue == factories[i]){
                        item.setValue(true);
                        return;
                    }      
                }
            });
            
            var roleList = record.get('roleList');
	        var roles = roleList.split(',');
            checkGroup.items.each(function(item) {
                for(var i = 0; i < roles.length; i++){
                    if(item.inputValue == roles[i]){
                        item.setValue(true);
                        return;
                    }      
                }
            });
	        
        },
        remove : function(record){
        	gsl.RowAction( {
	            url : 'pub/user-deleteUser.action',
	            actionType : 'remove',
	            params : {
	            	'user.userName' : record.get('userName') 
	            },
	            success : function() {
	                store.reload();
	            }
	        });
        }
	});
    
    //用户信息Model
	var userInfoModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn,{
			header : '登录名',
			dataIndex : "userName",
			width: 75
		},{
			header : '姓名',
			dataIndex : "name",
			width: 70
		},{
			header : '部门',
			dataIndex : "departmentName",
			width: 100
		}
//		,{
//			header : '角色',
//			width: 120,
//			dataIndex : "roleList"
//		}
		,{
			header : '工厂',
			width: 120,
			dataIndex : "factoryList"
		},{
			header : '联系电话',
			width: 90,
			dataIndex : "tel"
		},{
			header : '手机',
			width: 100,
			dataIndex : "mobile"
		},{
			header : '邮箱',
			width: 110,
			dataIndex : "email"
		}
//		,{
//			header : '用户状态',
//			width: 60,
//			dataIndex : "userStatus"
//		}
		,new gsl.LCmbColumn({
			header: '状态',
			dataIndex: 'userStatus', 
			width : 45,
			cmbData : sysUserStatusArray
		})
		,{
			header : '登录次数',
			width: 60,
			dataIndex : "loginNum"
		}
	]);
    
	//界面权限工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [{
			tagValue : '01020101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				gsl.addTab({title:'用户信息录入',url:'pub/pub_gamc_user_add.action',refresh:true});
			}
		}]
	});
	
	var pagingBar = new gsl.PagingToolbar({store : store, pageSize : pageSize});
	
    //用户信息面板
    var userInfoPanel = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '用户信息一览',
		store : store,
		cm : userInfoModel,                                                  
		tbar: toolBar,
		bbar: pagingBar,
		plugins : [actionColumn]                                         
	});
    
	//界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryPanel, userInfoPanel]
	});
});