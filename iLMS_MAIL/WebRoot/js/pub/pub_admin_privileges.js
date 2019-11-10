/**
 * 管理员权限管理
 */
Ext.onReady(function() {
	var adminPriPageAuth = gsl.getPageAuth('010101');
	
	var selectedFirstModuleCode = ''; //选中的一级模块代码
	var selectedSecondModuleCode = ''; //选中的二级模块代码
	var selectedMenuCode = ''; //选中的菜单代码
	
	var zeroModelArray = [['firstmli1','供应商协同'],['firstmli2','供应链'],['firstmli3','物流管控']];
	
	//一级模块Store
	var firstModuleStore = new gsl.JsonStore({
        url : 'pub/develope-queryFirstModuleForPage.action',
		fields : ['menuLevel', 'menuId', 'parentId', 'menuName', 'sort']
	});
	firstModuleStore.on('load', function() {
		selectedFirstModuleCode = '';
		selectedSecondModuleCode = '';
		selectedMenuCode = '';
		secondModuleStore.removeAll();
		thirdModuleStore.removeAll();
		fourthModuleStore.removeAll();
	});
	
	var pagingBar = new gsl.PagingToolbar({store: firstModuleStore,pageSize : pageSize});
	
	// 新增表单
	var firstAddForm = new gsl.FormPanel({
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
					fieldLabel :'模块代码',
					maxLength: 2,
					allowBlank :false,
					name :'pageMenuVO.menuId',
					anchor :'95%'
				}),new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					name :'pageMenuVO.sort'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( {
					fieldLabel :'模块名称',
					maxLength: 16,
					allowBlank :false,
					name :'pageMenuVO.menuName',
					anchor :'95%'
				}),new gsl.BaseDataComboBox({
					valueField: 'code',
					hiddenName: 'pageMenuVO.parentId',
					baseData: zeroModelArray,
					width : 130,
					readOnly: true,
					allowBlank:true,
					fieldLabel: '所属模块'
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
            	firstAddWin.hide();
            }
        }]    
    });
    
    //新增窗口
    var firstAddWin = new gsl.Window( {
    	title : '新增一级模块',
	    titleCollapse : true,
	    autoScroll : true,
        width : 520,
        height : 180,
        items : [ firstAddForm ]
    });
    
    //修改表单
    var firstModifyForm =  new gsl.FormPanel({
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
			    	dataIndex : 'menuId',
					name :'pageMenuVO.menuId'
				}),new gsl.TextField( {
					fieldLabel :'模块名称',
					maxLength: 16,
					allowBlank :false,
					dataIndex : 'menuName',
					name :'pageMenuVO.menuName',
					anchor :'95%'
				}),new gsl.BaseDataComboBox({
					valueField: 'code',
					dataIndex : 'parentId',
					hiddenName: 'pageMenuVO.parentId',
					baseData: zeroModelArray,
					width : 130,
					readOnly: true,
					allowBlank:true,
					fieldLabel: '所属模块'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					dataIndex : 'sort',
					name :'pageMenuVO.sort'
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
            	firstModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var firstModifyWin = new gsl.Window( {
    	title : '修改一级模块',
	    titleCollapse : true,
	    autoScroll : true,
        width : 520,
        height : 180,
        items : [ firstModifyForm ]
    });
    
	//一级模块工具条
	var toolBar = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				firstAddWin.show();
			}
		}]
	});
	
	//删除
	var removeFn = function(record){
		gsl.RowAction( {
            url : 'pub/develope-deleteFirstModule.action',
            actionType : 'remove',
            params : {
            	'pageMenuVO.menuId' : record.get('menuId') 
            },
            success : function() {
                firstModuleStore.reload();
            }
        });
	}
	
	//行操作
	var actionColumn = new gsl.RowActions({
		auth:'SP',
//		auth : adminPriPageAuth,
        edit : function(record){
        	firstModifyWin.show();
        	firstModifyForm.getForm().setValues(record.data);
        },
        remove : removeFn
	});
	
	//一级模块Model
	var firstModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn,{
			header : '模块级别',
			width: 66,
			dataIndex : "menuLevel"
		},new gsl.LCmbColumn({
			header : '所属模块',
			width : 80,
			dataIndex : 'parentId',
			cmbData : zeroModelArray
		}),{
			header : '模块代码',
			dataIndex : "menuId",
			width: 105
		},{
			header : '模块名称',
			dataIndex : "menuName",
			width: 110
		},{
			header : '排序',
			width: 70,
			dataIndex : "sort"
		}
	]);
	
	//一级模块面板
	var modulePanel1 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '一级模块',
		store : firstModuleStore,
		cm : firstModuleModel,                                                  
		tbar: toolBar,
		bbar: pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedFirstModuleCode = grid.getStore().getAt(rowIndex).get('menuId');
				secondModuleStore.baseParams = {
					'parentId' : selectedFirstModuleCode
				};
				selectedSecondModuleCode = '';
				selectedMenuCode = '';
				secondModuleStore.load({
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
	
	//二级模块Store
	var secondModuleStore = new gsl.JsonStore({
        url : 'pub/develope-querySecondModuleForPage.action',
		fields : ['menuLevel', 'menuId', 'parentId', 'menuName', 'sort']
	});
	secondModuleStore.on('load', function() {
		selectedSecondModuleCode = '';
		selectedMenuCode = '';
		thirdModuleStore.removeAll();
		fourthModuleStore.removeAll();
	});
	
	var pagingBar2 = new gsl.PagingToolbar({store: secondModuleStore,pageSize : pageSize});
	
	// 新增表单
	var secondAddForm = new gsl.FormPanel({
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
					fieldLabel :'模块代码',
					maxLength: 4,
					allowBlank :false,
					name :'pageMenuVO.menuId',
					anchor :'95%'
				}),new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					name :'pageMenuVO.sort'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( {
					fieldLabel :'模块名称',
					maxLength: 16,
					allowBlank :false,
					name :'pageMenuVO.menuName',
					anchor :'95%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	addFn('2');
            }
        },{
            text : '关闭',
            handler : function() {
            	secondAddWin.hide();
            }
        }]    
    });
    
    //新增窗口
    var secondAddWin = new gsl.Window( {
    	title : '新增二级模块',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 150,
        items : [ secondAddForm ]
    });
    
    //修改表单
    var secondModifyForm =  new gsl.FormPanel({
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
			    	dataIndex : 'menuId',
					name :'pageMenuVO.menuId'
				}),new gsl.TextField( {
					fieldLabel :'模块名称',
					maxLength: 16,
					allowBlank :false,
					dataIndex : 'menuName',
					name :'pageMenuVO.menuName',
					anchor :'95%'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					dataIndex : 'sort',
					name :'pageMenuVO.sort'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	modifyFn('2');
            }
        },{
            text : '关闭',
            handler : function() {
            	secondModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var secondModifyWin = new gsl.Window( {
    	title : '修改二级模块',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 150,
        items : [ secondModifyForm ]
    });
	
	//二级模块工具条
	var toolBar2 = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				if(Ext.isEmpty(selectedFirstModuleCode, false)){
					gsl.ErrorAlert('请先选择一级模块数据!');
					return;
				}
				secondAddWin.show();
			}
		}]
	});
	
	var removeFn2 = function(record){
		gsl.RowAction( {
            url : 'pub/develope-deleteSecondModule.action',
            actionType : 'remove',
            params : {
            	'pageMenuVO.menuId' : record.get('menuId') 
            },
            success : function() {
                secondModuleStore.reload();
            }
        });
	}
	
	//行操作
	var actionColumn2 = new gsl.RowActions({
		auth:'SP',
//		auth : adminPriPageAuth,
        edit : function(record){
        	secondModifyWin.show();
        	secondModifyForm.getForm().setValues(record.data);
        },
        remove:removeFn2
	});
	
	//二级模块Model
	var secondModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn2,{
			header : '模块级别',
			width: 70,
			dataIndex : "menuLevel"
		},{
			header : '父模块代码',
			dataIndex : "parentId",
			width: 105
		},{
			header : '模块代码',
			dataIndex : "menuId",
			width: 100
		},{
			header : '模块名称',
			dataIndex : "menuName",
			width: 110
		},{
			header : '排序',
			width: 70,
			dataIndex : "sort"
		}
	]);
	
	//二级模块面板
	var modulePanel2 = new gsl.GridPanel({ 
		region : 'east',
		width : '50%',
		iconCls : 'titList',
		title : '二级模块',
		store : secondModuleStore,
		cm : secondModuleModel,                                                  
		tbar: toolBar2,
		bbar: pagingBar2,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedSecondModuleCode = grid.getStore().getAt(rowIndex).get('menuId');
				thirdModuleStore.baseParams = {
					'parentId' : selectedSecondModuleCode
				};
				selectedMenuCode = '';
				thirdModuleStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [actionColumn2]                                         
	});
	
	//----------------------
	
	//三级菜单Store
	var thirdModuleStore = new gsl.JsonStore({
        url : 'pub/develope-queryMenuPrivilegeForPage.action',
		fields : ['parentCode', 'privilegeCode', 'privilegeType', 
			'privilegeDesc', 'menuName', 'menuUrl', 'sort']
	});
	thirdModuleStore.on('load', function() {
		selectedMenuCode = '';
		fourthModuleStore.removeAll();
	});
	
	var pagingBar3 = new gsl.PagingToolbar({store: thirdModuleStore, pageSize : pageSize});
	
	// 新增表单
	var thirdAddForm = new gsl.FormPanel({
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
					fieldLabel :'权限代码',
					maxLength: 6,
					allowBlank :false,
					name :'pagePrivilegeVO.privilegeCode',
					anchor :'95%'
				}),new gsl.TextField( {
					fieldLabel :'菜单名称',
					maxLength: 15,
					allowBlank :false,
					name :'pagePrivilegeVO.menuName',
					anchor :'95%'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( {
					fieldLabel :'权限描述',
					maxLength: 40,
					allowBlank :false,
					name :'pagePrivilegeVO.privilegeDesc',
					anchor :'95%'
				}),new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					name :'pagePrivilegeVO.sort'
				})]
            },{
                columnWidth :1,
				layout :'form',
				border :false,
				items : [ new gsl.TextField( {
					fieldLabel :'菜单路径',
					maxLength: 60,
					allowBlank :false,
					name :'pagePrivilegeVO.menuUrl',
					anchor :'99%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	addFn('3');
            }
        },{
            text : '关闭',
            handler : function() {
            	thirdAddWin.hide();
            }
        }]    
    });
    
    //新增窗口
    var thirdAddWin = new gsl.Window( {
    	title : '新增菜单',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 180,
        items : [ thirdAddForm ]
    });
    
    //修改表单
    var thirdModifyForm =  new gsl.FormPanel({
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
			    	dataIndex : 'privilegeCode',
					name :'pagePrivilegeVO.privilegeCode'
				}),new gsl.TextField( {
					fieldLabel :'菜单名称',
					maxLength: 15,
					allowBlank :false,
					dataIndex : 'menuName',
					name :'pagePrivilegeVO.menuName',
					anchor :'95%'
				}),new gsl.TextField( {
					fieldLabel :'权限描述',
					maxLength: 40,
					allowBlank :false,
					dataIndex : 'privilegeDesc',
					name :'pagePrivilegeVO.privilegeDesc',
					anchor :'95%'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					dataIndex : 'sort',
					name :'pagePrivilegeVO.sort'
				})]
            },{
                columnWidth :1,
				layout :'form',
				border :false,
				items : [ new gsl.TextField( {
					fieldLabel :'菜单路径',
					maxLength: 60,
					allowBlank :false,
					dataIndex : 'menuUrl',
					name :'pagePrivilegeVO.menuUrl',
					anchor :'99%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	modifyFn('3');
            }
        },{
            text : '关闭',
            handler : function() {
            	thirdModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var thirdModifyWin = new gsl.Window( {
    	title : '修改三级菜单',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 180,
        items : [ thirdModifyForm ]
    });
	
	//三级菜单工具条
	var toolBar3 = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				if(Ext.isEmpty(selectedSecondModuleCode, false)){
					gsl.ErrorAlert('请先选择二级模块数据!');
					return;
				}
				thirdAddWin.show();
			}
		}]
	});
	
	var removeFn3 = function(record){
		gsl.RowAction( {
            url : 'pub/develope-deleteThirdMenu.action',
            actionType : 'remove',
            params : {
            	'pagePrivilegeVO.privilegeCode' : record.get('privilegeCode') 
            },
            success : function() {
                thirdModuleStore.reload();
            }
        });
	}
	
	//行操作
	var actionColumn3 = new gsl.RowActions({
		auth:'SP',
//		auth : adminPriPageAuth,
        edit : function(record){
        	thirdModifyWin.show();
        	thirdModifyForm.getForm().setValues(record.data);
        },
        remove : removeFn3
	});
	
	//三级菜单Model
	var thirdModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn3,{
			header : '父模块代码',
			dataIndex : "parentCode",
			width: 70
		},{
			header : '权限代码',
			dataIndex : "privilegeCode",
			width: 60
		},{
			header : '菜单名称',
			dataIndex : "menuName",
			width: 70
		},{
			header : '菜单路径',
			dataIndex : "menuUrl",
			width: 110
		},{
			header : '权限描述',
			dataIndex : "privilegeDesc",
			width: 130
		},{
			header : '排序',
			width: 40,
			dataIndex : "sort"
		}
	]);
	
	//三级菜单面板
	var modulePanel3 = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '三级菜单',
		store : thirdModuleStore,
		cm : thirdModuleModel,                                                  
		tbar: toolBar3,
		bbar: pagingBar3,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedMenuCode = grid.getStore().getAt(rowIndex).get('privilegeCode');
				fourthModuleStore.baseParams = {
					'parentId' : selectedMenuCode
				};
				fourthModuleStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [actionColumn3]                                         
	});
	
	//---------------------------------------
	
	//界面权限Store
	var fourthModuleStore = new gsl.JsonStore({
        url : 'pub/develope-queryPagePrivilegesForPage.action',
		fields : ['parentCode', 'privilegeCode', 'privilegeType', 
			'privilegeDesc', 'menuName', 'munuUrl', 'sort']
	});
	
	var pagingBar4 = new gsl.PagingToolbar({store: fourthModuleStore, pageSize : pageSize});
	
	// 新增表单
	var fourthAddForm = new gsl.FormPanel({
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
					fieldLabel :'权限代码',
					maxLength: 8,
					allowBlank :false,
					name :'pagePrivilegeVO.privilegeCode',
					anchor :'95%'
				}),new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					name :'pagePrivilegeVO.sort'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.TextField( {
					fieldLabel :'权限描述',
					maxLength: 40,
					allowBlank :false,
					name :'pagePrivilegeVO.privilegeDesc',
					anchor :'95%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	addFn('4');
            }
        },{
            text : '关闭',
            handler : function() {
            	fourthAddWin.hide();
            }
        }]    
    });
    
    //新增窗口
    var fourthAddWin = new gsl.Window( {
    	title : '新增菜单',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 200,
        items : [ fourthAddForm ]
    });
    
    //修改表单
    var fourthModifyForm =  new gsl.FormPanel({
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
			    	dataIndex : 'privilegeCode',
					name :'pagePrivilegeVO.privilegeCode'
				}),new gsl.TextField( {
					fieldLabel :'权限描述',
					maxLength: 40,
					allowBlank :false,
					dataIndex : 'privilegeDesc',
					name :'pagePrivilegeVO.privilegeDesc',
					anchor :'95%'
				})]
            }, {
                columnWidth :.5,
				layout :'form',
				border :false,
				items : [new gsl.NumberField( { 
					fieldLabel :'排序',
					allowBlank : false,
					FormatComma : false,
					minValue : 0,
					maxValue : 9999,
					anchor :'95%',
					dataIndex : 'sort',
					name :'pagePrivilegeVO.sort'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	modifyFn('4');
            }
        },{
            text : '关闭',
            handler : function() {
            	fourthModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var fourthModifyWin = new gsl.Window( {
    	title : '修改界面权限',
	    titleCollapse : true,
	    autoScroll : true,
        width : 500,
        height : 150,
        items : [ fourthModifyForm ]
    });
	
	//界面权限工具条
	var toolBar4 = new gsl.Toolbar({
		auth : adminPriPageAuth,
		tools : [{
			tagValue : '01010101',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				if(Ext.isEmpty(selectedMenuCode, false)){
					gsl.ErrorAlert('请先选择一条菜单数据!');
					return;
				}
				fourthAddWin.show();
			}
		}]
	});
	
	var removeFn4 = function(record){
		gsl.RowAction( {
            url : 'pub/develope-deletePagePrivilege.action',
            actionType : 'remove',
            params : {
            	'pagePrivilegeVO.privilegeCode' : record.get('privilegeCode') 
            },
            success : function() {
                fourthModuleStore.reload();
            }
        });
	}
	
	//行操作
	var actionColumn4 = new gsl.RowActions({
		auth:'SP',
//		auth : adminPriPageAuth,
      	edit : function(record){
        	fourthModifyWin.show();
        	fourthModifyForm.getForm().setValues(record.data);
        },
        remove : removeFn4
	});
	
	//界面权限Model
	var fourthModuleModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn4,{
			header : '父模块代码',
			dataIndex : "parentCode",
			width: 75
		},{
			header : '权限代码',
			dataIndex : "privilegeCode",
			width: 70
		},{
			header : '权限描述',
			dataIndex : "privilegeDesc",
			width: 120
		},{
			header : '排序',
			width: 40,
			dataIndex : "sort"
		}
	]);
	
	//界面权限面板
	var modulePanel4 = new gsl.GridPanel({ 
		region : 'east',
		width : '50%',
		iconCls : 'titList',
		title : '界面权限',
		store : fourthModuleStore,
		cm : fourthModuleModel,                                                  
		tbar: toolBar4,
		bbar: pagingBar4,
		plugins : [actionColumn4]                                         
	});
	
	
	//新增方法
    var addFn = function(level){
    	if('1' == level){
    		firstAddForm.submit({
	    		url:'pub/develope-addFirstModule.action',
	    		success:function(){
	    			firstModuleStore.reload();
	    			firstAddForm.getForm().reset();
	    			firstAddWin.hide();
				}
	    	});
    	}else if('2' == level){
    		secondAddForm.submit({
	    		url:'pub/develope-addSecondModule.action?parentId='+selectedFirstModuleCode,
	    		success:function(){
	    			secondModuleStore.reload();
	    			secondAddForm.getForm().reset();
	    			secondAddWin.hide();
				}
	    	});
    	}else if('3' == level){
    		thirdAddForm.submit({
	    		url:'pub/develope-addThirdMenu.action?parentId='+selectedSecondModuleCode,
	    		success:function(){
	    			thirdModuleStore.reload();
	    			thirdAddForm.getForm().reset();
	    			thirdAddWin.hide();
				}
	    	});
    	}else if('4' == level){
    		fourthAddForm.submit({
	    		url:'pub/develope-addPagePrivilege.action?parentId='+selectedMenuCode,
	    		success:function(){
	    			fourthModuleStore.reload();
	    			fourthAddForm.getForm().reset();
	    			fourthAddWin.hide();
				}
	    	});
    	}
    }
    
    //修改方法
    var modifyFn = function(level){
    	if('1' == level){
    		firstModifyForm.submit( {
	            url :'pub/develope-updateFirstModule.action',
		        success : function(form, action) {
		            firstModuleStore.reload();
	    			firstModifyWin.hide();
		        }
			});
    	}else if('2' == level){
    		secondModifyForm.submit( {
	            url :'pub/develope-updateSecondModule.action',
		        success : function(form, action) {
		            secondModuleStore.reload();
	    			secondModifyWin.hide();
		        }
			});
    	}else if('3' == level){
    		thirdModifyForm.submit( {
	            url :'pub/develope-updateThirdMenu.action',
		        success : function(form, action) {
		            thirdModuleStore.reload();
	    			thirdModifyWin.hide();
		        }
			});
    	}else if('4' == level){
    		fourthModifyForm.submit( {
	            url :'pub/develope-updatePagePrivilege.action',
		        success : function(form, action) {
		            fourthModuleStore.reload();
	    			fourthModifyWin.hide();
		        }
			});
    	}
    }
	
	
	//初始加载一级模块
	firstModuleStore.load({
		params : {
			start : 0,
			limit : pageSize            
		}
	});
	
	//界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [new Ext.Panel({
			region : 'north',
			height : 200,
			layout : 'border',
			items : [modulePanel1, modulePanel2]
		}), new Ext.Panel({
			region : 'center',
			layout : 'border',
			items : [modulePanel3, modulePanel4]
		})]
	});
});