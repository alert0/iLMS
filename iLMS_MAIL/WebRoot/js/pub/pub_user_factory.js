/**
 * 用户与工厂关系维护
 */
Ext.onReady(function() {
	var adminPriPageAuth = gsl.getPageAuth('010101');
	
	var selectedFactoryCode = ''; //选中的工厂模块代码
	
	
	
	//工厂Store
	var factoryStore = new gsl.JsonStore({
        url : 'pub/role-queryFactoryForPage.action',
		fields : ['factoryName', 'factoryCode', 'factoryAddr']
	});
	factoryStore.on('load', function() {
		selectedFactoryCode = '';
		userMouduleStore.removeAll();
		userMouduleStoreDelete.removeAll();
		
	});
	
	var pagingBar = new gsl.PagingToolbar({store: factoryStore,pageSize : pageSize});
	var checkbox0 =new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });
	var checkbox1 =new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });
	
	 // 用户名
		var userName = new gsl.TextField( {
			fieldLabel :'用户名',
			allowBlank :true,
			name :'factoryVO.userName',
			anchor :'95%'
		});
		
		// 姓名
		var userCName = new gsl.TextField( {
			fieldLabel :'姓名',
			allowBlank :true,
			name :'factoryVO.userCName',
			anchor :'95%'
		});
	
	// 新增表单
	var factoryAddForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 500,
        labelWidth : 80,
        labelAlign : 'right',
        frame :true,
        items : [ {
            layout : 'column',
            items : [{
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [ new gsl.TextField( {
					fieldLabel :'工厂代码',
					allowBlank :false,
					name :'factoryVO.factoryCode',
					anchor :'95%'
				}),new gsl.TextField( { 
					fieldLabel :'工厂名称',
					allowBlank : false,
					FormatComma : false,
					anchor :'95%',
					name :'factoryVO.factoryName'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( {
					fieldLabel :'工厂地址',
					allowBlank :true,
					name :'factoryVO.factoryAddr',
					anchor :'95%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	addFn('1');
            }
        },{
            text : '关闭',
            handler : function() {
            	factoryAddWin.hide();
            }
        }]    
    });
    
    //新增窗口
    var factoryAddWin = new gsl.Window( {
    	title : '新增工厂信息',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 150,
        items : [ factoryAddForm ]
    });
    
    //修改表单
    var factoryModifyForm =  new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 500,
        labelWidth : 80,
        labelAlign : 'right',
        frame :true,
        items : [ {
            layout : 'column',
            items : [{
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [ new Ext.form.Hidden({
			    	dataIndex : 'factoryCode',
					name :'factoryVO.factoryCode'
				}),new gsl.TextField( {
					fieldLabel :'工厂名称',
					allowBlank :false,
					dataIndex : 'factoryName',
					name :'factoryVO.factoryName',
					anchor :'95%'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( { 
					fieldLabel :'工厂地址',
					allowBlank :true,
					dataIndex : 'factoryAddr',
					name :'factoryVO.factoryAddr',
					anchor :'95%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	modifyFn('1');
            }
        },{
            text : '关闭',
            handler : function() {
            	factoryModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var factoryModifyWin = new gsl.Window( {
    	title : '修改工厂信息',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 150,
        items : [ factoryModifyForm ]
    });
    
	//工厂信息工具条
	var toolBar = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				factoryAddWin.show();
			}
		}]
	});
	
	
	
	//行操作
	var actionColumn = new gsl.RowActions({
		auth:'SP',
//		auth : adminPriPageAuth,
        edit : function(record){
        	factoryModifyWin.show();
        	factoryModifyForm.getForm().setValues(record.data);
        }
        //remove : removeFn
	});
	
	//工厂模块Model
	var factoryModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn,{
			header : '工厂代码',
			width: 60,
			dataIndex : "factoryCode"
		},{
			header : '工厂名称',
			dataIndex : "factoryName",
			width: 120
		},{
			header : '工厂地址',
			dataIndex : "factoryAddr",
			width: 180
		}
	]);
	
	//工厂信息面板
	var modulePanel1 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '工厂信息',
		store : factoryStore,
		cm : factoryModel,                                                  
		tbar: toolBar,
		bbar: pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedFactoryCode = grid.getStore().getAt(rowIndex).get('factoryCode');
				userMouduleStore.baseParams = {
					'factoryVO.factoryCode' : selectedFactoryCode,
					'factoryVO.type': '1'
					
				};
				userMouduleStoreDelete.baseParams = {
					'factoryVO.factoryCode' : selectedFactoryCode,
					'factoryVO.type': '2'
				};
				userMouduleStoreDelete.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
     
				userMouduleStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [actionColumn]                                         
	});
	
	//-----------------------------
	
	//用户与工厂关系Store
	var userMouduleStore = new gsl.JsonStore({
        url : 'pub/role-queryUserForPage.action',
		fields : ['factoryCode', 'userName', 'userCName','departmentName']
	});
	
	//未非配用户store
	var userMouduleStoreDelete = new gsl.JsonStore({
        url : 'pub/role-queryUserForPage.action',
		fields : ['factoryCode', 'userName', 'userCName','departmentName']
	});
	
	
	
	var pagingBar2 = new gsl.PagingToolbar({store: userMouduleStore,pageSize : pageSize});
	
	
	
	
	
	
	//行操作
	var actionColumn2 = new gsl.RowActions({
		auth:'SP'
//		auth : adminPriPageAuth,
//        edit : function(record){
//        	userModifyWin.show();
//        	userModifyForm.getForm().setValues(record.data);
//        },
//        remove:removeFn2
	});
	
	//用户与工厂关系Model
	var userModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        checkbox1,
        {
			header : '工厂代码',
			width: 70,
			dataIndex : "factoryCode"
		},{
			header : '用户名称',
			dataIndex : "userName",
			width: 105
		},{
			header : '姓名',
			dataIndex : "userCName",
			width: 120
		},{
			header : '部门',
			dataIndex : "departmentName",
			width: 120
		}
	]);
	
	//用户与工厂关系Model2
	var userModuleModel2 = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        checkbox0,
        {
			header : '用户名称',
			dataIndex : "userName",
			width: 105
		},{
			header : '姓名',
			dataIndex : "userCName",
			width: 120
		},{
			header : '部门',
			dataIndex : "departmentName",
			width: 120
		}
	]);
	
	//用户与工厂关系面板
	var modulePanel2 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '未配置用户与工厂关系列表',
		store : userMouduleStore,
		cm : userModuleModel2, 
		sm :checkbox0,
		bbar: pagingBar2,
		listeners : {
			rowclick : function(grid, rowIndex, e){

			}
		},
		plugins : [actionColumn2]                                         
	});
	
	
	var toolBar3 = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '批量删除',
			iconCls : 'remove',
			handler : function(){
			if(Ext.isEmpty(selectedFactoryCode, false)){
					gsl.ErrorAlert('请先选择工厂信息数据!');
					return;
				}
			var check = checkbox1.getSelections();
			var arr=new Array();
			for(var i=0;i<check.length;i++){
				arr.push(check[i].get("userName"));
			}
			if(arr.length<=0){
				new gsl.MsgAlert({
							msg : '请至少选择一个用户删除'
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
			        
					url : 'pub/role-ModifyUserFac.action',
					params : {
						'factoryVO.userGroup' : Ext.encode(arr),
						'factoryVO.factoryCode' :selectedFactoryCode,
						'factoryVO.type':'delete'
					},
					success : function(t, response, options) {
						actionNm : '删除',
						userMouduleStore.reload();
						userMouduleStoreDelete.reload();
						
					}

				});
			});
			
			
			}
		},{
			tagValue : '01010101',
			text : '批量新增',
			iconCls : 'add',
			handler :function(){
				if(Ext.isEmpty(selectedFactoryCode, false)){
						gsl.ErrorAlert('请先选择工厂信息数据!');
						return;
					}
				userModifyWin.show();
			}
			
		}]
	});
	
	
	var pagingBar3 = new gsl.PagingToolbar({store: userMouduleStoreDelete,pageSize : pageSize});
	
	//已维护用户与工厂关系面板（删除用户）
	var modulePanel3 = new gsl.GridPanel({ 
		region : 'center',
		width :'100%',
		iconCls : 'titList',
		title : '已配置用户与工厂关系列表',
		store : userMouduleStoreDelete,
		cm : userModuleModel, 
		sm :checkbox1,
		tbar: toolBar3,
		bbar: pagingBar3,
		listeners : {
			rowclick : function(grid, rowIndex, e){

			}
		},
		plugins : [actionColumn2]                                         
	});
	
	
		// 未配置用户代码
	var txtUserNo = new gsl.TextField( {
		fieldLabel : '用户名',
		width : 110
	});
	
	
	//未配置用户查询面板
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
				items : [ txtUserNo ]
			}, {
				columnWidth : .2,
				layout : 'form',
				border : false,
				items : [ new Ext.Button( {
					text : l_query,
					handler : function(){
							if(selectedFactoryCode.length<=0){
								gsl.ErrorAlert("请先选择工厂");
							}  
								userMouduleStore.baseParams = {
									'factoryVO.factoryCode' : selectedFactoryCode,
									'factoryVO.type': '1',
									'factoryVO.userName' :txtUserNo.getValue()
										
								};
							
								userMouduleStore.load({
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
	
	  //未配置工厂与用户窗口
    var userModifyWin = new gsl.Window( {
	    titleCollapse : true,
	    title : '用户与工厂关系配置（批量增加）',
	    autoScroll : true,
	    layout :'border',
        width : 400,
        height : 500,
        items : [notConfigQueryForm,modulePanel2],
        buttons:[{
        	text :'确定',
        	handler : function(){
			
				if(Ext.isEmpty(selectedFactoryCode, false)){
						gsl.ErrorAlert('请先选择工厂信息数据!');
						return;
					}
				var check = checkbox0.getSelections();
				var arr=new Array();
				for(var i=0;i<check.length;i++){
					arr.push(check[i].get("userName"));
				}
				if(arr.length<=0){
					new gsl.MsgAlert({
								msg : '请至少选择一个用户添加'
							});
					return;
				}
				gsl.RequestWaitAction({
						actionNm : '新增',
						url : 'pub/role-ModifyUserFac.action',
						params : {
							'factoryVO.userGroup' : Ext.encode(arr),
							'factoryVO.factoryCode' :selectedFactoryCode,
							'factoryVO.type':'add'
						},
						success : function(t, response, options) {
							userMouduleStore.reload();
							userMouduleStoreDelete.reload();
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
			if(selectedFactoryCode.length<=0){
				gsl.ErrorAlert("请先选择工厂");
			}  
			   
				userMouduleStore.baseParams = {
					'factoryVO.factoryCode' : selectedFactoryCode,
					'factoryVO.type': '1',
					'factoryVO.userName' : userName.getValue(),
					'factoryVO.userCName' : userCName.getValue()
					
					
				};
				userMouduleStoreDelete.baseParams = {
					'factoryVO.factoryCode' : selectedFactoryCode,
					'factoryVO.type': '2',
					'factoryVO.userName' : userName.getValue(),
					'factoryVO.userCName' : userCName.getValue()
				};
				userMouduleStoreDelete.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
     
				userMouduleStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
        
			
			
		};
		
		var queryBtn = new Ext.Button( {
			text :'查询',
			handler :query
		});
    
    // 查询条件form
		queryForm = new gsl.FormPanel( {
			iconCls :'titQuery',
			title :'检索条件',
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
					items : [ userCName ]
				},{
					columnWidth :.9,
					layout :'form',
					border :false,
					items : [ queryBtn ]
				} ]
			} ],
			keys : [ {
         			key : 13,
         			fn : query,
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
	
	
	//初始加载工厂信息
	factoryStore.load({
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
			items : [modulePanel1]
		}), new Ext.Panel({
			region : 'center',
			layout : 'border',
			items : [queryForm,modulePanel3]
		})]
	});
});