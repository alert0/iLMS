/** 供应商分组维护 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010202');
	var busyFlg = false;
	var selectedSuppGroupId = ''; //选中的供应商分组名称
	
	//**************数据Store*************
	//物流公司数据
	var store = new gsl.JsonStore( {
		url : 'pub/supp-querySupplierGroupForPage.action',
		fields : ['groupId', 'groupName', 'supplierNo','supplierName']
	});
	store.on( {
		'load' : function() {
			busyFlg = false;
			selectedSuppGroupId = '';
			configStore.removeAll();
		}
	});
	
	//已配置的供应商数据
	var configStore = new gsl.JsonStore( {
		url : 'pub/supp-querySupplierForPage.action',
		fields : ['groupId','supplierNo','supplierName']
	});
	
	//未配置的供应商数据
	var notConfigStore = new gsl.JsonStore( {
		url : 'pub/supp-queryNotSupplierForPage.action',
		fields : ['groupId','supplierNo', 'supplierName']
	});
	
	//*******************查询面板***********
	// 公司代码
	var groupName = new gsl.TextField( {
		fieldLabel : '分组名称',
		id : 'groupName',
		width : 150
	});
	
	//修改方法
    var modifyFn = function(){
    	suppModifyForm.submit( {
	            url :'pub/supp-updateSuppGroup.action',
		        success : function(form, action) {
		            store.reload();
	    			suppModifyWin.hide();
		        }
			});
    
    };
	
	
	//删除
   var removeFn = function(record){
 		gsl.RowAction( {
	        url : 'pub/supp-deleteSuppGroup.action',
	        actionType : 'remove',
	        params : {
	        	'suppGroupVO.groupId' : record.get('groupId') 
	        },
	        success : function() {
	            store.reload();
	        }
	    });
   }
   
	//行操作
	var actionColumn = new gsl.RowActions({
		auth:'SP',
        edit:function(record){
	        suppModifyWin.show();
	        suppModifyForm.getForm().setValues(record.data);
		},
        remove:removeFn
	});
	
	
	
	
   
    
    
    
     		/**
 * 新增
 */
  var add = function(){
	    suppAddForm.submit({
	    		url:'pub/supp-addSuppGroup.action',
	    		success:function(){
	    			suppAddForm.getForm().reset();
	    			suppAddWin.hide();
	    			store.reload();
				}
	    	});
}
   
   
	//界面权限工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [ {
			tagValue : '01020101',
			text : '新增',
			iconCls : 'add',
			handler :function(){
			  suppAddWin.show();
						}
	        	
		} ]
	});
   
	
	//查询方法
	function queryFn(){
    	if(queryForm.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
//			selectedLogCompanyCode = '';
	        store.baseParams = {
	            'suppGroupVO.groupName':groupName.getValue()
	        };
	        store.reload();
	        store.load({params:{start:0, limit:pageSize}});
    	}
    }
	
	// 查询条件面板
	var queryForm = new gsl.FormPanel( {
		region : 'north',
		height : 100,
		iconCls : 'titQuery',
		hideCollapseTool : false,
//		titleCollapse : true,
		collapsible : true,
//		renderTo : 'queryDiv',
//		width : (Ext.get('queryDiv').getWidth() > 800 ? Ext.get('queryDiv').getWidth() : 800),
		labelWidth : 100,
		items : [ {
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ groupName]
			} ]
		} ],
		buttons : [ {
			text : l_query,
			handler : queryFn
		} ],
		keys : [ {
			key : 13,
			fn : queryFn,
			scope : this
		} ]
	});
	
	// 物流公司分页栏
	var pagingBar = new gsl.PagingToolbar( {
		store : store
	});
	
	
	//新增
	var suppAddForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 500,
        labelWidth : 120,
        labelAlign : 'right',
        frame :true,
//        border :false,s
        items : [ {
            layout : 'column',
            items : [{
                columnWidth :.9,
				layout :'form',
				border :false,
				items : [ new gsl.TextField( {
					fieldLabel :'供应商分组名称',
					minLength : 0,
					allowBlank :false,
					maxLength : 32,
					width:150,
					name :'suppGroupVO.groupName'					
//					anchor :'95%'
				})]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
        		 add();
            }
        },{
            text : '关闭',
            handler : function() {
            	suppAddWin.hide();
            }
        }]    
    });
	
	//新增窗口
	var suppAddWin = new gsl.Window({
		title : '数据添加',
		titleCollapse : true,
		autoScroll : true,
		width : 400,
		height : 150,
		items : [ suppAddForm ]
	});
	
//修改	
	
//	var suppModifyForm = new gsl.FormPanel({
//    	titleCollapse : true,
//      	autoScroll : true,
//        width : 500,
//        labelWidth : 120,
//        labelAlign : 'right',
//        frame :true,
//        items : [ {
//            layout : 'column',
//            items : [{
//                columnWidth :.9,
//				layout :'form',
//				border :false,
//				items : [
//					new Ext.form.Hidden({
//			    	dataIndex : 'groupId',
//					name :'suppGroupVO.groupId'
//				}),new gsl.TextField({
//					fieldLabel :'供应商分组名称',
//					width:150,
//					dataIndex : 'groupName',
//					allowBlank :false,
//					name :'suppGroupVO.groupName'
////					anchor :'95%'
//				})]
//        }],
//        buttons :[{
//            text : '保存',
//            handler : function(){
//        		modifyFn();
//            }
//        },{
//            text : '关闭',
//            handler : function() {
//            	suppModifyWin.hide();
//            }
//        }]    
//    }]
//    });
    var suppModifyForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 500,
        labelWidth : 120,
        labelAlign : 'right',
        frame :true,
//        border :false,s
        items : [ {
            layout : 'column',
            items : [{
                columnWidth :.9,
				layout :'form',
				border :false,
				items : [
					new Ext.form.Hidden({
				    	dataIndex : 'groupId',
						name :'suppGroupVO.groupId'
					}),new gsl.TextField({
						fieldLabel :'供应商分组名称',
						width:150,
						dataIndex : 'groupName',
						allowBlank :false,
						name :'suppGroupVO.groupName'
	//					anchor :'95%'
					})
				]
            }]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
        		 modifyFn();
            }
        },{
            text : '关闭',
            handler : function() {
            	suppModifyWin.hide();
            }
        }]    
    });
    
	//修改窗口
    var suppModifyWin = new gsl.Window( {
    	title : '修改供应商组',
	    titleCollapse : true,
        width : 400,
        height : 150,
        items : [ suppModifyForm ]
    });
    
	
	//物流公司列表
	var gridPanel = new gsl.GridPanel( {
    	region : 'west',
    	width : '50%',
		iconCls : 'titList',
		title : '供应商分组信息',
		tbar : toolBar,
		store : store,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			actionColumn,
			{   header : '分组名称',
				dataIndex : 'groupName',
				width : 92
			}
		]),
		bbar : pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedSuppGroupId = grid.getStore().getAt(rowIndex).get('groupId');
				configStore.baseParams = {
					'groupId' : selectedSuppGroupId
				};
				configStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [actionColumn]
	});
	
	
	//已配置供应商分页栏
	var configPagingBar = new gsl.PagingToolbar( {
		store : configStore
	});
	
	//已配置供应商工具条
	var configToolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [{
			tagValue : '01010101',
			text : '增加',
			iconCls : 'add',
			handler : function(){
				if(Ext.isEmpty(selectedSuppGroupId, false)){
					gsl.ErrorAlert('请先选中一条供应商分组名称!');
					return;
				}
				notConfigSupWin.show();
				queryNotConfigSupFn();
			}
		},{
			tagValue : '01010101',
			text : '删除',
			iconCls : 'gridRowDelete',
			handler : function(){
				if(Ext.isEmpty(selectedSuppGroupId, false)){
					gsl.ErrorAlert('请先选中一条供应商分组名称!');
					return;
				}
				var configSupRecords = configSupPanel.getSelectionModel().getSelections();
				if (configSupRecords.length == 0) {
					gsl.ErrorAlert('请至少选择一条供应商数据!');
					return ;
				}
				var supplierNoArr = [];
				for ( var i = 0; i < configSupRecords.length; i++) {
					supplierNoArr.push(configSupRecords[i].get('supplierNo'));
				}
				
				gsl.RequestAction({
					actionNm : '删除',
					url : 'pub/supp-deleteConfigSupplier.action',
					params : {
						'groupId' : selectedSuppGroupId,
						'supplierNoArr' : supplierNoArr
					},
					success : function(t, response, options){
						configStore.reload();
					}
					
				});
				
			}
		}]
	});
	
	var configCheckBox = new Ext.grid.CheckboxSelectionModel();
	
	//已配置的供应商列表
	var configSupPanel = new gsl.GridPanel( {
    	region : 'center',
		iconCls : 'titList',
		title : '已配置的供应商列表',
		store : configStore,
		sm : configCheckBox,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			configCheckBox,
			{
				header : '供应商代码',
				dataIndex : 'supplierNo',
				width : 90
			}, {
				header : '供应商名称',
				dataIndex : 'supplierName',
				width : 180
			}
		]),
		tbar: configToolBar,
		bbar : configPagingBar,
		plugins : [  ]
	});
	
	//未配置窗口查询条件
	
	// 供应商代码
	var txtWSupplierNo = new gsl.TextField( {
		fieldLabel : '供应商代码',
		width : 110
	});
	
	var notConfigQueryForm = new gsl.FormPanel( {
		region : 'north',
		height : 47,
		labelWidth : 100,
		items : [ {
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : .7,
				layout : 'form',
				border : false,
				items : [ txtWSupplierNo ]
			}, {
				columnWidth : .2,
				layout : 'form',
				border : false,
				items : [ new Ext.Button( {
					text : l_query,
					handler : queryNotConfigSupFn
				}) ]
			}]
		} ],
		buttons : [ ],
		keys : []
	});
	
	//未配置供应商分页栏
	var notConfigPagingBar = new gsl.PagingToolbar( {
		store : notConfigStore
	});
	
	var notConfigCheckBox = new Ext.grid.CheckboxSelectionModel();
	
	//未配置的供应商列表
	var notConfigPanel = new gsl.GridPanel( {
    	region : 'center',
		iconCls : 'titList',
		title : '未配置的供应商列表',
		store : notConfigStore,
		sm : notConfigCheckBox,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			notConfigCheckBox,
			{
				header : '供应商代码',
				dataIndex : 'supplierNo',
				width : 90
			}, {
				header : '供应商名称',
				dataIndex : 'supplierName',
				width : 200
			}
		]),
		bbar : notConfigPagingBar,
		plugins : [  ]
	});
	
	//未配置供应商弹窗
    var notConfigSupWin = new gsl.Window( {
    	title : '增加供应商配置',
	    titleCollapse : true,
	    autoScroll : true,
	    layout : 'border',
        width : 500,
        height : 400,
        items : [ notConfigQueryForm, notConfigPanel ],
        buttons : [{
			text : '确定',
			handler : function(){
				if(Ext.isEmpty(selectedSuppGroupId, false)){
					gsl.ErrorAlert('请先选中一条物流公司数据!');
					return;
				}
				var selectedRecords = notConfigPanel.getSelectionModel().getSelections();
				if (selectedRecords.length == 0) {
					gsl.ErrorAlert('请至少选择一条供应商数据!');
					return ;
				}
				var supplierNoArr = [];
				for ( var i = 0; i < selectedRecords.length; i++) {
					supplierNoArr.push(selectedRecords[i].get('supplierNo'));
				}
				
				gsl.RequestAction({
					actionNm : '增加配置供应商',
					url : 'pub/supp-addConfigSupplier.action',
					params : {
						'GroupId' : selectedSuppGroupId,
						'supplierNoArr' : supplierNoArr
					},
					success : function(t, response, options){
						configStore.reload();
						notConfigSupWin.hide();
					}
				});
			}
		},{
			text : '关闭',
			handler : function(){
				notConfigSupWin.hide();			
			}
		}]
    });
    
    
    //未配置供应商数据查询
    function queryNotConfigSupFn(){
    	if(notConfigQueryForm.form.isValid()){
    		
    		if(Ext.isEmpty(selectedSuppGroupId, false)){
				gsl.ErrorAlert('请先选中一条物流公司数据!');
				return;
			}
    		
	        notConfigStore.baseParams = {
	        	'suppGroupVO.groupId' : selectedSuppGroupId,
	            'suppGroupVO.supplierNo':txtWSupplierNo.getValue()
	        };
	        notConfigStore.load({params:{start:0, limit:pageSize}});
    	}
    } 
    
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryForm, gridPanel, configSupPanel]
	});
});