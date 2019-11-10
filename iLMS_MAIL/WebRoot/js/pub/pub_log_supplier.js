/** 物流公司供应商关系 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010202');
	var busyFlg = false;
	var selectedLogCompanyCode = ''; //选中的物流公司代码
	
	//**************数据Store*************
	//物流公司数据
	var store = new gsl.JsonStore( {
		url : 'pub/logcompany-queryLogCompanyForPage.action',
		fields : ['pkId', 'companyNo', 'companyName', 'companyAddr',
				'type', 'status', 'erpContactUser', 'erpTel',
				'erpMobile', 'erpEmail', 'erpFax']
	});
	store.on( {
		'load' : function() {
			busyFlg = false;
			selectedLogCompanyCode = '';
			configStore.removeAll();
		}
	});
	
	//已配置的供应商数据
	var configStore = new gsl.JsonStore( {
		url : 'pub/logcompany-queryLogConfigSupplierForPage.action',
		fields : ['companyNo', 'supplierNo', 'supplierName']
	});
	
	//未配置的供应商数据
	var notConfigStore = new gsl.JsonStore( {
		url : 'pub/logcompany-queryLogNotConfigSupplierForPage.action',
		fields : ['supplierNo', 'supplierName']
	});
	
	//*******************查询面板***********
	// 公司代码
	var txtLogisticsId = new gsl.TextField( {
		fieldLabel : l_companyNo,
		id : 'txtLogisticsId',
		width : 150
	});
	// 公司名称
	var txtLogisticsName = new gsl.TextField( {
		fieldLabel : l_companyName,
		id : 'txtLogisticsName',
		width : 150
	});
	// 物流公司类型
	var cbxLogisticsType = new gsl.BaseDataComboBox( {
		valueField : 'code',
		id : 'cbxLogisticsType',
		hiddenName : 'logistics.logisticsType',
		baseData : logCompanyTypeArray,
		readOnly : true,
		addBlank : true,
		fieldLabel : l_logistics_logisticsType,
		width : 150
	});
	
	//查询方法
	function queryFn(){
    	if(queryForm.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
			selectedLogCompanyCode = '';
	        store.baseParams = {
	            'pageLogCompanyVO.companyNo':txtLogisticsId.getValue(),
	            'pageLogCompanyVO.companyName':txtLogisticsName.getValue(),
	            'pageLogCompanyVO.type':cbxLogisticsType.getValue()
	        };
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
				items : [ txtLogisticsId ]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ txtLogisticsName ]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ cbxLogisticsType ]
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
	
	//物流公司列表
	var gridPanel = new gsl.GridPanel( {
    	region : 'west',
    	width : '50%',
		iconCls : 'titList',
		title : l_logistics_list,
		store : store,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			{
				header : l_logistics_logisticsId,
				dataIndex : 'companyNo',
				width : 92
			},new gsl.LCmbColumn( {
				header : l_logistics_logisticsType,
				dataIndex : 'type',
				width : 85,
				cmbData : logCompanyTypeArray
			}), {
				header : l_logistics_logisticsName,
				dataIndex : 'companyName',
				width : 180
			}
		]),
		bbar : pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedLogCompanyCode = grid.getStore().getAt(rowIndex).get('companyNo');
				configStore.baseParams = {
					'companyNo' : selectedLogCompanyCode
				};
				configStore.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [  ]
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
				if(Ext.isEmpty(selectedLogCompanyCode, false)){
					gsl.ErrorAlert('请先选中一条物流公司数据!');
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
				if(Ext.isEmpty(selectedLogCompanyCode, false)){
					gsl.ErrorAlert('请先选中一条物流公司数据!');
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
					url : 'pub/logcompany-deleteConfigSupplier.action',
					params : {
						'companyNo' : selectedLogCompanyCode,
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
				header : l_logistics_logisticsId,
				dataIndex : 'companyNo',
				width : 90
			}, {
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
				if(Ext.isEmpty(selectedLogCompanyCode, false)){
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
					url : 'pub/logcompany-addConfigSupplier.action',
					params : {
						'companyNo' : selectedLogCompanyCode,
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
    		
    		if(Ext.isEmpty(selectedLogCompanyCode, false)){
				gsl.ErrorAlert('请先选中一条物流公司数据!');
				return;
			}
    		
	        notConfigStore.baseParams = {
	        	'pageLogCompanyVO.companyNo' : selectedLogCompanyCode,
	            'pageLogCompanyVO.supplierNo':txtWSupplierNo.getValue()
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