/**
 * 管理员权限管理
 */
Ext.onReady(function() {
	var adminPriPageAuth = 'SP';//gsl.getPageAuth('010101');
	
	var selectedUserCode = ''; //选中的用户模块代码
	
	var pageSize = 100;
	
	//用户Store
	var userStore = new gsl.JsonStore({
        url : 'pub/role-queryUserInfoForPage.action',
		fields : ['userName', 'name', 'departmentName']
	});
	userStore.on('load', function() {
		selectedFactoryCode = '';
		supplyModuleStoreAdd.removeAll();
		supplyModuleStoreDelete.removeAll();
		
	});
	
	
	//用户与供应商关系增加Store
	var supplyModuleStoreAdd = new gsl.JsonStore({
        url : 'pub/role-querySupplyForPage.action',
		fields : ['userName', 'supplierNo','supplierName','subRoleType']
	});
	
	//用户与供应商关系删除Store
	var supplyModuleStoreDelete = new gsl.JsonStore({
        url : 'pub/role-querySupplyForPage.action',
		fields : ['userName', 'supplierNo','supplierName','subRoleType']
	});
	
	var pagingBar = new gsl.PagingToolbar({store: userStore,pageSize : pageSize});
	var pagingBar2 = new gsl.PagingToolbar({store: supplyModuleStoreAdd,pageSize : pageSize});
	var checkbox0 =new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });
	var checkbox1 =new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });
	
	
	
	   // 用户名
		var userName = new gsl.TextField( {
			fieldLabel :'用户名',
			allowBlank :true,
			name :'userVO.userName',
			anchor :'95%'
		});
		
		// 姓名
		var userCHName = new gsl.TextField( {
			fieldLabel :'姓名',
			allowBlank :true,
			name :'userVO.userCName',
			anchor :'95%'
		});
		
		// 供应商名
		var supplierName = new gsl.TextField( {
			fieldLabel :'供应商名称',
			allowBlank :true,
			name :'userVO.supplierName',
			anchor :'95%'
		});
		
		// 供应商代码
		var supplierCode = new gsl.TextField( {
			fieldLabel :'供应商代码',
			allowBlank :true,
			name :'userVO.supplierNo',
			anchor :'95%'
		});
		
		//用户模块Model
	var userModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        {
			header : '用户名',
			width: 60,
			dataIndex : "userName"
		},{
			header : '姓名',
			dataIndex : "name",
			width: 120
		},{
			header : '部门',
			dataIndex : "departmentName",
			width: 180
		}
	]);
	
	//用户信息面板
	var modulePanel1 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '用户信息',
		store : userStore,
		cm : userModel,                                                  
		bbar: pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedUserCode = grid.getStore().getAt(rowIndex).get('userName');
				supplyModuleStoreAdd.baseParams = {
					'userVO.userName' : selectedUserCode,
					'userVO.delFlg': 'add'
					
				};
				supplyModuleStoreDelete.baseParams = {
					'userVO.userName' : selectedUserCode,
					'userVO.delFlg': 'delete'
				};
				supplyModuleStoreAdd.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
     
				supplyModuleStoreDelete.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		}                                         
	});
	
	
	
	//行操作
	var actionColumn2 = new gsl.RowActions({
		auth:'SP'

	});
	
	//未配置用户与供应商关系Model
	var supplierModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        checkbox0,
        {
			header : '供应商代码',
			dataIndex : "supplierNo",
			width: 90
		},{
			header : '供应商名称',
			dataIndex : "supplierName",
			width: 200
		}
	]);
	
	//已配置用户与供应商关系Model2
	var supplierModuleModel2 = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        checkbox1,
        {
			header : '用户名',
			width: 70,
			dataIndex : "userName"
		},{
			header : '供应商代码',
			dataIndex : "supplierNo",
			width: 105
		},{
			header : '供应商名称',
			dataIndex : "supplierName",
			width: 120
		}
	]);
	
	
	
	
	var toolBar3 = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '批量删除',
			iconCls : 'remove',
			handler : function(){
			if(Ext.isEmpty(selectedUserCode, false)){
					gsl.ErrorAlert('请先在左侧面板选择用户!');
					return;
				}
			var check = checkbox1.getSelections();
			var arr=new Array();
			for(var i=0;i<check.length;i++){
				arr.push(check[i].get("supplierNo"));
			}
			if(arr.length<=0){
				new gsl.MsgAlert({
							msg : '请至少选择一个供应商删除'
						});
				return;
			}
			Ext.MessageBox.confirm('批量删除', 
		   				String.format('本次将删除【' + check.length + '】条数据,是否确定进行删除？'), 
		   				function(btnId) {
			        		if (btnId != 'yes') {
			        		return;
			            }
			 gsl.RequestWaitAction({
				    actionNm : '删除',
					url : 'pub/role-ModifyUserSup.action',
					params : {
						'userVO.supplierGroup' : Ext.encode(arr),
						'userVO.userName' :selectedUserCode,
						'userVO.delFlg':'delete'
					},
					success : function(t, response, options) {
						
						supplyModuleStoreAdd.reload();
						supplyModuleStoreDelete.reload();
					}
					
				});	
			});
		    
			
			}
		},{
			tagValue : '01010101',
			text : '批量新增',
			iconCls : 'add',
			handler :function(){
				if(Ext.isEmpty(selectedUserCode, false)){
						gsl.ErrorAlert('请先在左侧面板选择用户!');
						return;
					}
				userModifyWin.show();
			}
			
		}]
	});
	
	
	var pagingBar3 = new gsl.PagingToolbar({store: supplyModuleStoreDelete,pageSize : pageSize});
	
	//用户与工厂关系面板（删除用户）
	var modulePanel3 = new gsl.GridPanel({ 
		region : 'center',
		width :'100%',
		iconCls : 'titList',
		title : '已配置用户与供应商关系列表',
		store : supplyModuleStoreDelete,
		cm : supplierModuleModel2, 
		sm :checkbox1,
		tbar: toolBar3,
		bbar: pagingBar3,
		listeners : {
			rowclick : function(grid, rowIndex, e){

			}
		},
		plugins : [actionColumn2]                                         
	});
	
		// 未配置供应商代码
	var txtSupplierNo = new gsl.TextField( {
		fieldLabel : '供应商代码',
		width : 110
	});
	
	
	//未配置供应商查询面板
	var notConfigQueryForm = new gsl.FormPanel( {
		region : 'north',
		height : 50,
		labelWidth : 100,
		items : [ {
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : .7,
				layout : 'form',
				border : false,
				items : [ txtSupplierNo ]
			}, {
				columnWidth : .2,
				layout : 'form',
				border : false,
				items : [ new Ext.Button( {
					text : l_query,
					handler : function(){
							if(selectedUserCode.length<=0){
								gsl.ErrorAlert("请先选择用户");
							}
			          	supplyModuleStoreAdd.baseParams = {
							'userVO.userName' : selectedUserCode,
							'userVO.delFlg': 'add',
							'userVO.supplierNo' : txtSupplierNo.getValue()
						};
				
						supplyModuleStoreAdd.load({
							params : {
								start : 0,
								limit : pageSize            
							}
						});
		     
					}
				}) ]
			}]
		} ],
		buttons : [ ],
		keys : []
	});
	
	//未配置用户与供应商关系面板
	var modulePanel2 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '用户与供应商关系列表',
		store : supplyModuleStoreAdd,
		cm : supplierModuleModel, 
		sm :checkbox0,
		bbar: pagingBar2,
		listeners : {
			rowclick : function(grid, rowIndex, e){

			}
		},
		plugins : []                                         
	});
	
	  //未配置供应商弹窗
    var userModifyWin = new gsl.Window( {
    	title : '用户与供应商关系(批量新增)',
	    titleCollapse : true,
	    autoScroll : true,
	    layout :'border',
        width : 400,
        height : 500,
        items : [notConfigQueryForm,modulePanel2],
        buttons:[{
        	text :'确定',
        	handler : function(){
        		if(Ext.isEmpty(selectedUserCode, false)){
					gsl.ErrorAlert('请先选择用户信息数据!');
					return;
				}
			var check = checkbox0.getSelections();
			var arr=new Array();
			for(var i=0;i<check.length;i++){
				arr.push(check[i].get("supplierNo"));
			}
			if(arr.length<=0){
				new gsl.MsgAlert({
							msg : '请至少选择一个供应商添加'
						});
				return;
			}
			gsl.RequestWaitAction({
					actionNm : '新增',
					url : 'pub/role-ModifyUserSup.action',
					params : {
						'userVO.supplierGroup' : Ext.encode(arr),
						'userVO.userName' :selectedUserCode,
						'userVO.delFlg':'add'
					},
					success : function(t, response, options) {
						supplyModuleStoreAdd.reload();
						supplyModuleStoreDelete.reload();
						userModifyWin.hide();
					}
					
				});	
        		
        	}
        },{
			text : '关闭',
			handler : function(){
				userModifyWin.hide();			
			}
		}]
    });
    
    var query = function(){
			if(selectedUserCode.length<=0){
				gsl.ErrorAlert("请先选择用户");
			}
	          supplyModuleStoreAdd.baseParams = {
					'userVO.userName' : selectedUserCode,
					'userVO.delFlg': 'add',
					'userVO.supplierNo' : supplierCode.getValue(),
					'userVO.supplierName' : supplierName.getValue()	
				};
				supplyModuleStoreDelete.baseParams = {
					'userVO.userName' : selectedUserCode,
					'userVO.delFlg': 'delete',
					'userVO.supplierNo' : supplierCode.getValue(),
					'userVO.supplierName' : supplierName.getValue()	
				};
				supplyModuleStoreAdd.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
     
				supplyModuleStoreDelete.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});	
		};
		
		 var queryUser = function(){
			selectedUserCode = ''; 
			userStore.removeAll();
			userStore.baseParams = {
					'userVO.userName' : userName.getValue().trim(),
					'userVO.userCName' : userCHName.getValue().trim()
				};
			userStore.reload({
				params : {
						start : 0,
						limit : pageSize            
					}
			})
		       
		};
		//供应商查询按钮
		var queryBtn = new Ext.Button( {
			text :'查询',
			handler :query
		});
		
		
		
		//用户查询按钮
		var queryUserBtn = new Ext.Button( {
			text :'查询',
			handler :queryUser
		});
		
		// 供应商查询条件form
		queryForm = new gsl.FormPanel( {
			iconCls :'titQuery',
			title :'供应商检索条件',
			region : 'north',
			height :80,
			hideCollapseTool :false,
			titleCollapse :true,
			collapsible :true,
			labelWidth :90,
			items : [ {
				layout :'column',
				border :false,
				items : [ {
					width :200,
					layout :'form',
					border :false,
					items : [ supplierName ]
				},{
					width :200,
					layout :'form',
					border :false,
					items : [ supplierCode ]
				},{
					columnWidth :1,
					layout :'form',
					border :false,
					items : [ queryBtn ]
				}]
			} ],
			keys : [ {
         			key : 13,
         			fn : query,
         			scope : this
         		}]
		});
		
		// 用户查询条件form
		queryUserForm = new gsl.FormPanel( {
			iconCls :'titQuery',
			title :'用户检索条件',
			region : 'north',
			height :80,
			hideCollapseTool :false,
			titleCollapse :true,
			collapsible :true,
			labelWidth :80,
			items : [ {
				layout :'column',
				border :false,
				items : [ {
					width :200,
					layout :'form',
					border :false,
					items : [ userName ]
				},{
					width :200,
					layout :'form',
					border :false,
					items : [ userCHName ]
				},{
					columnWidth :.9,
					layout :'form',
					border :false,
					items : [ queryUserBtn ]
				} ]
			} ],
			keys : [ {
         			key : 13,
         			fn : queryUser,
         			scope : this
         		}]
		});
	
	
		//----------------------
		
	
	
	
	
	
	//新增方法
    var addFn = function(level){
    	if('1' == level){
    		factoryAddForm.submit({
	    		url:'pub/role-addFactory.action',
	    		success:function(){
	    			factoryStore.reload();
	    			factoryAddForm.getForm().reset();
	    			factoryAddWin.hide();
				}
	    	});
    	}
    }
    
    //修改方法
    var modifyFn = function(level){
    	if('1' == level){
    		factoryModifyForm.submit( {
	            url :'pub/role-updateFactoryModule.action',
		        success : function(form, action) {
		            factoryStore.reload();
	    			factoryModifyWin.hide();
		        }
			});
    	}
    }
	
	
	//初始加载用户信息
	userStore.load({
		params : {
			start : 0,
			limit : pageSize            
		}
	});
    
	
	
	
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [new Ext.Panel({
			region : 'west',
			layout : 'border',
			width: '50%',
			items : [queryUserForm,modulePanel1]
		}), new Ext.Panel({
			region : 'center',
			layout : 'border',
			items : [queryForm,modulePanel3]
		})]
	});
});