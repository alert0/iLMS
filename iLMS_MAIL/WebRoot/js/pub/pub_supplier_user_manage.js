/** 供应商用户管理 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010202');
	var typeTemp;
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
	
    /***************************************************************************
     * 查询操作
     **************************************************************************/
	// 登录名
    var txt_userName = new gsl.TextField({
        fieldLabel: l_loginName,
        id:'userName',
        width : 150
    });
    
    // 姓名
    var txt_name = new gsl.TextField({
        fieldLabel: l_user_name,
        id:'name',
        width : 150
    });
    
    //公司代码
    var txt_companyNo = new gsl.TextField({
        fieldLabel: '公司代码',
        id:'txt_companyNo',
        width : 150
    });
    
    //供应商角色类型
	var roleType_cbx = new gsl.BaseDataComboBox({
		fieldLabel:l_roleType,
		allowBlank : false,
		readOnly : true,
		triggerAction :'all',
        autoLoad:true,
		width:150,
		listeners : ({
			'select' : function() {
//				if(roleType_cbx.getValue() == "" || roleType_cbx.getValue() == null){
//					offloadRoleStore.removeAll();
//					role.setValue("");
//				}
//				offloadRoleStore.baseParams = {
//					'queryInfo.userType' : roleType_cbx.getValue()
//				};
//				offloadRoleStore.load();
			}
		}),
		id: 'roleType_cbx',
		baseData : supRoleTypeArray
	});
    
    // 
    var checkGroup = new gsl.CheckboxGroup( {
		url :'comm-cmb.action?type=role&temptime='+ (new Date().getTime().toString(36)),
		fieldLabel :l_role, //角色
		allowBlank :false,
		columns :3, 
		id:'role_cbx',
		msgTarget :'side',
		anchor :'90%'
	});
	
    // 查询条件
    var userForm = new gsl.FormPanel({
    	region : 'north',
		height : 133,
        iconCls:'titQuery',
        hideCollapseTool: false,
//        titleCollapse: true,
        collapsible: true,
//        renderTo:'queryDiv',
//        width :(Ext.get('queryDiv').getWidth() > 800 ?Ext.get('queryDiv').getWidth():800),
        labelWidth:120,
        items : [{
            layout : 'column',
            items : [{
                 columnWidth : .33,
                 layout : 'form',
                 items : [roleType_cbx,txt_companyNo]
             },{
                 columnWidth : .33,
                 layout : 'form',
                 items : [txt_userName]
             },{
                 columnWidth : .33,
                 layout : 'form',
                 items : [txt_name]
             }]
        }],buttons:[{
            text : l_query,
            handler : query
        }],
         keys : [ {
 			key : 13,
 			fn : query,
 			scope : this
 		}] 
    });
    
    // 检索结果
    var store = new gsl.JsonStore({
        url : 'pub/user-querySupplierUserForPage.action',
        fields:['roleType','name','userName','fax',
                'tel','mobile','email','supplierNo','supplierName',
                'userStatus','loginNum','roleList','delFlg']
    });
    store.on('load',function(){
		busyFlg = false;
    });
    
    /**
     * 查询
     */
    function query(){
    	if(userForm.form.isValid()){
		if(busyFlg){
			return;
		}
		// 执行查询，标记忙碌状态为忙碌
		busyFlg = true;
        store.baseParams = {
            'queryInfo.userName':txt_userName.getValue(),
            'queryInfo.name':txt_name.getValue(),
            'queryInfo.roleType':roleType_cbx.getValue(),
            'queryInfo.supplierNo':txt_companyNo.getValue()
        };
        store.load({params:{start:0, limit:pageSize}});
    	}
    }
    
    // 分页栏
    var pagingBar = new gsl.PagingToolbar({store : store, pageSize : pageSize});
    
   // 工具栏
   var toolBar = new gsl.Toolbar({
   		auth : pageAuth,
		tools : [{
			tagValue : '01020201',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				gsl.addTab({title:'供应商用户信息录入',url:'pub/pub_supplier_user_add.action',refresh:true});
			}
		}]
   });
   
   //修改
   var modifyFn = function(record){
   		var roleType = record.get('roleType');
   		typeTemp = roleType;
		userModifyForm.form.reset();
		userWin.setTitle(l_edit_supplierUserInfo);
		userWin.show();
		loadModifyForm(record);
   }
   
   //删除
   var removeFn = function(record){
   		gsl.RowAction( {
	        url : 'pub/user-deleteSupplierUser.action',
	        actionType : 'remove',
	        params : {
	        	'user.userName' : record.get('userName') 
	        },
	        success : function() {
	            store.reload();
	        }
	    });
   }

    // 操作列
    var actionColumn = new gsl.RowActions({
    	  auth:pageAuth
         ,edit:modifyFn 
         ,remove:removeFn
//         ,disable:{cb:disable,hide:'delFlg=='+USER_STATUS_DISABLE }
//         ,usable:{cb:usable,hide:'delFlg=='+USER_STATUS_USABLE}
    });
    
    // 用户信息一览
    var gridPanel = new gsl.GridPanel({
//        renderTo:'resultDiv',
    	region : 'center',
        iconCls:'titList',
        title:l_supplierinfo_input_grid, //供应商用户信息一览
        store: store,
//        width :(Ext.get('resultDiv').getWidth() > 800 ?Ext.get('resultDiv').getWidth():800),
//        height : 280,
        cm:new  Ext.grid.ColumnModel([
	        new gsl.RowNumberer()
            //,sm
	        ,actionColumn
//	        用户登录名
	        ,{header: l_userLogin_name,dataIndex: 'userName', width : 100}
//	        用户类型
	        ,new gsl.LCmbColumn({header: l_userType,dataIndex: 'roleType', width : 70,cmbData : supRoleTypeArray})
			//用户状态
			,new gsl.LCmbColumn({header: '状态',dataIndex: 'userStatus', width : 45,cmbData : sysUserStatusArray})
//	        公司代码
	        ,{header: l_companyNo,dataIndex: 'supplierNo',width : 80}
//	        公司名称
            ,{header: l_companyName,dataIndex: 'supplierName',width : 80}
//            用户姓名
	        ,{header: l_userName_sup,dataIndex: 'name', width : 80}
//	        联系电话
	        ,{header: l_contact_tel,dataIndex: 'tel',width : 80}
//	        传真
	        ,{header: l_fax,dataIndex: 'fax',width : 80}
//	        手机
            ,{header: l_mobilePhone,dataIndex: 'mobile',width : 80}
//            电子邮箱
            ,{header: l_email_supplier,dataIndex: 'email',width : 80}
//            用户状态
            ,new gsl.LCmbColumn({hidden : true,header:l_userStatus,dataIndex: 'delFlg',width : 80,cmbData:userStatusArray})
//            登录状态
            ,new gsl.LCmbColumn({hidden : true,header: l_loginStatus,dataIndex: 'loginStatus',width : 80,cmbData:loginStatusArray})
        ]),
        tbar: toolBar,
        bbar: pagingBar,
        plugins:[actionColumn]
    });
    
    /***************************************************************************
     *查看处理
     ***************************************************************************/
    
     /***************************************************************************
      *修改处理
      ***************************************************************************/
    // 登录名
//    var lblUserName = new gsl.TextField({
//        fieldLabel: l_loginName,
////        readOnly : true,
//        disabled : true,
//        dataIndex : 'userName',
//        name:'user.userName'
//    });
    var lblUserName = new Ext.form.Hidden({
        dataIndex : 'userName',
        name:'user.userName'
    });
    // userId
    var hdnUserId = new Ext.form.Hidden({
    	dataIndex:'userNo',
        name:'user.userNo'
    });
    //姓名
    var txtName = new gsl.TextField({
        fieldLabel: l_user_name,
        dataIndex:'name',
        name:'user.name'
    });
    
    // 密码
    var txtPassword = new gsl.TextField({
		fieldLabel: l_pwd,
        validator:function(){
			if(txtPassword.getValue() !='' && txtPassword.getValue().length < MIN_LENGTH_USERPWD){
//				该输入项的最小长度为6
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
		invalidText:l_twoPwdDifferent,
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
	
	// 电话
    var txtTelephone = new gsl.TextField({
		fieldLabel: '电话',
		allowBlank:false,
        maxLength:MAX_LENGTH_TEL,
		dataIndex:'tel',
		name: 'user.tel'
        ,width:120,
        vtype:'telephone'
	});
	// 手机
    var txtMobile = new gsl.TextField({
		fieldLabel: '手机',
        maxLength:MAX_LENGTH_MOBILE,
		dataIndex:'mobile',
		name: 'user.mobile'
        ,width:120,
        vtype:'mobile'
	});
    // 传真
    var txtFax = new gsl.TextField({
		fieldLabel: '传真',
        maxLength:MAX_LENGTH_FAX,
		dataIndex:'fax',
		name: 'user.fax'
        ,width:120,
        vtype:'telephone'
	});
    // 邮箱
    var txtEmail = new gsl.TextField({
		fieldLabel: '电子邮箱',
        maxLength:MAX_LENGTH_EMAIL,
        vtype:'email',
		dataIndex:'email',
		allowBlank:false,
		name: 'user.email'
        ,anchor:'95%'
	});
	// 公司代码
    var lblSupplierNoModify = new gsl.LabelField({
        fieldLabel: l_companyNo,
        dataIndex : 'supplierNo',
        name:'user.supplierNo'
    });
    // 公司名称
    var lblSupplierNameModify = new gsl.LabelField({
        fieldLabel: l_companyName,
        dataIndex : 'supplierName',
        name:'user.supplierName'
    });
	
	
    // 修改Form
    var userModifyForm = new gsl.FormPanel( {
        labelWidth:70,
        autoScroll:true,
        items : [hdnUserId,{
            layout:'column',
            items:[{
            	columnWidth : .9,
                layout : 'form',
                items : [lblUserName]
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
             items : [txtConfirmPassword, cmbUserStatus]
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
             items : [txtFax]
             }]
        },
        {layout : 'column',
          items : [{
             columnWidth : .5,
             layout : 'form',
             items : [lblSupplierNoModify]
             },{
              columnWidth : .5,
             layout : 'form',
             items : [lblSupplierNameModify]
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
             items : [txtEmail]
             }]
        },checkGroup],
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
    
    // 载入修改数据
    function loadModifyForm(record){
    	
    	userModifyForm.getForm().setValues(record.data);
    	
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
    	
//        userModifyForm.load({
//            url : 'supplier/supplierUser-queryById.action',
//            params : {'queryInfo.userId':userId,
//            		'queryInfo.userType':userType
//            	},
//            success:function(form,action){
//                var roles = action.result.data.roles;
//                checkGroup.items.each(function(item) {
//                    for(var i = 0; i < roles.length; i++){
//                        if(item.inputValue == roles[i].roleId){
//                            item.setValue(true);
//                            return;
//                        }      
//                    }
//                });
//            }
//        });
    }
    
    // 修改窗口
    userWin = new gsl.Window({
        width: 700,
        height: 400,
        items: [userModifyForm]
    }); 
    userWin.on('show',function(){
    	if(Ext.getCmp('role_cbx') != null) {
    		userModifyForm.remove('role_cbx');
		}
    	checkGroup = new gsl.CheckboxGroup( {
//    		角色
			fieldLabel :l_role,
			allowBlank :false,
			columns :3, 
			id:'role_cbx',
			msgTarget :'side',
			anchor :'90%',
			url :'comm-cmb.action?type=supplierRole&param='+ typeTemp
		});
		userModifyForm.add(checkGroup);
		userModifyForm.doLayout();
	});
    /**
     * 更新用户 
     */
    function updateUser(){
    	if(userModifyForm.form.isValid() && txtPassword.getValue() == txtConfirmPassword.getValue()) {
    	var roles= [];
        checkGroup.items.each(function(item) {
               if (item.getValue()) {
                 roles.push(item.getRawValue());
               }
              
         });
        userModifyForm.submit({
            url:'pub/user-updateSupplier.action',
            params : {
//                'queryInfo.userId' : hdnUserId.getValue(),
                // 角色
                roleIds:roles
            },
            success:function(form,action){
                userWin.hide();
                query();
            }
        });
    	} else if(txtPassword.getValue() != txtConfirmPassword.getValue()) {
    		txtConfirmPassword.markInvalid();
    	}
    }
    
    
    
//    new gsl.adjHeight(userForm,grid);
//    adjWidthCtrs = [userForm, grid];
//    var tempHeight = parent.document.getElementById("mainTabPanel").clientHeight - 50;
//    grid.setHeight(tempHeight -userForm.getSize().height-28 );
    
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[userForm, gridPanel]
	});
});