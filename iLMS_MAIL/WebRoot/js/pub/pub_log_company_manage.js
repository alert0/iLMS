/** 物流公司管理 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010203');
	var busyFlg = false;
	
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
	// 公司状态
	var cbxLogisticsStatus = new gsl.BaseDataComboBox( {
		valueField : 'code',
		id : 'cbxLogisticsStatus',
		hiddenName : 'logistics.activateStatus',
		baseData : activateStatusArray,
		readOnly : true,
		addBlank : true,
		fieldLabel : l_activate_status,
		width : 150
	});
	// 查询条件
	var queryForm = new gsl.FormPanel( {
		region : 'north',
		height : 130,
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
				items : [ txtLogisticsId, txtLogisticsName ]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ cbxLogisticsStatus]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [cbxLogisticsType ]
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
	
	//Store
	var store = new gsl.JsonStore( {
		url : 'pub/logcompany-queryLogCompanyForPage.action',
		fields : ['pkId', 'companyNo', 'companyName', 'companyAddr',
				'type', 'status', 'erpContactUser', 'erpTel',
				'erpMobile', 'erpEmail', 'erpFax']
	});
	store.on( {
		'load' : function() {
			busyFlg = false;
		}
	});
	
	//查询方法
	function queryFn(){
    	if(queryForm.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
	        store.baseParams = {
	            'pageLogCompanyVO.companyNo':txtLogisticsId.getValue(),
	            'pageLogCompanyVO.companyName':txtLogisticsName.getValue(),
	            'pageLogCompanyVO.status':cbxLogisticsStatus.getValue(),
	            'pageLogCompanyVO.type':cbxLogisticsType.getValue()
	        };
	        store.load({params:{start:0, limit:pageSize}});
    	}
    }
	
	// 分页栏
	var pagingBar = new gsl.PagingToolbar( {
		store : store
	});
	
	//工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [{
			tagValue : '01020301',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				gsl.addTab({title:'物流公司信息录入',url:'pub/pub_log_company_add.action',refresh:true});
			}
		}]
	});
	
	//修改
   var modifyFn = function(record){
   		logisticsModifyForm.form.reset();
		logisticsModifyWin.setTitle(l_logistics_modify);
		logisticsModifyWin.show();
		logisticsModifyForm.getForm().setValues(record.data);
   }
   
   //删除
   var removeFn = function(record){
   		gsl.RowAction( {
	        url : 'pub/logcompany-deleteLogCompany.action',
	        actionType : 'remove',
	        params : {
	        	'pageLogCompanyVO.pkId' : record.get('pkId'),
	        	'pageLogCompanyVO.companyNo' : record.get('companyNo')
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
    });
	
    //物流公司信息一览
    var gridPanel = new gsl.GridPanel( {
    	region : 'center',
//		renderTo : 'resultDiv',
		iconCls : 'titList',
		title : l_logistics_list,
		store : store,
//		width : (Ext.get('resultDiv').getWidth() > 800 ? Ext.get('resultDiv').getWidth() : 800),
//		height : 280,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			actionColumn, {
				header : l_logistics_logisticsId,
				dataIndex : 'companyNo',
				width : 90
			}, {
				header : l_logistics_logisticsName,
				dataIndex : 'companyName',
				width : 90
			}, {
				header : l_logistics_logisticsAddr,
				dataIndex : 'companyAddr',
				width : 90
			}, new gsl.LCmbColumn( {
				header : l_logistics_logisticsType,
				dataIndex : 'type',
				width : 80,
				cmbData : logCompanyTypeArray
			}), new gsl.LCmbColumn( {
				header : l_activate_status,
				dataIndex : 'status',
				width : 80,
				cmbData : activateStatusArray
			}), {
				header : l_erp_contacts,
				dataIndex : 'erpContactUser',
				width : 90
			}, {
				header : l_logistics_linkmobile,
				dataIndex : 'erpMobile',
				width : 90
			}, {
				header : l_contact_tel,
				dataIndex : 'erpTel',
				width : 90
			}, {
				header : l_logistics_linkfax,
				dataIndex : 'erpFax',
				width : 90
			}, {
				header : l_logistics_linkEmail,
				dataIndex : 'erpEmail',
				width : 80
			} 
		]),
		tbar : toolBar,
		bbar : pagingBar,
		plugins : [ actionColumn ]
	});
	
	//**************修改********************
	//物流公司ID
	var logCompanyId = new Ext.form.Hidden({
    	dataIndex:'pkId',
        name:'pageLogCompanyVO.pkId'
    });
	// 物流公司代码
	var lblmLogisticsId = new gsl.LabelField( {
		fieldLabel : l_logistics_logisticsId,
		dataIndex : 'companyNo',
		name : 'pageLogCompanyVO.companyNo'
	});
	// 物流公司名称
	var txtmLogisticsName = new gsl.TextField( {
		fieldLabel : l_logistics_logisticsName,
		allowBlank : false,
		maxLength : 20,
		width : 200,
		id : 'txtmLogisticsName',
		dataIndex : 'companyName',
		name : 'pageLogCompanyVO.companyName'
	});
	// 物流公司地址
	var txtmLogisticsAddr = new gsl.TextField( {
		fieldLabel : l_logistics_logisticsAddr,
		allowBlank : false,
		maxLength : 100,
		width : 400,
		id : 'txtmLogisticsAddr',
		dataIndex : 'companyAddr',
		name : 'pageLogCompanyVO.companyAddr'
	});

	// 物流公司类型
	var cbxmLogisticsType = new gsl.BaseDataComboBox( {
		valueField : 'code',
		id : 'cbxmLogisticsType',
		dataIndex : 'type',
		hiddenName : 'pageLogCompanyVO.type',
		baseData : logCompanyTypeArray,
		readOnly : true,
		allowBlank : false,
		fieldLabel : l_logistics_logisticsType,
		width : 90
	});
	// 物流公司激活状态
	var cbxmLogisticsStatus = new gsl.BaseDataComboBox( {
		valueField : 'code',
		id : 'cbxmLogisticsStatus',
		dataIndex : 'status',
		hiddenName : 'pageLogCompanyVO.status',
		baseData : activateStatusArray,
		readOnly : true,
		allowBlank : false,
		fieldLabel : l_activate_status,
		width : 90
	});

	// ERP联系人
	var txtmERPlianxiren = new gsl.TextField( {
		fieldLabel : l_erp_contacts,
		width : 150,
		maxLength : 20,
		allowBlank : false,
		id : 'txtmERPlianxiren',
		dataIndex : 'erpContactUser',
		name : 'pageLogCompanyVO.erpContactUser'
	});

	// 联系手机
	var txtmErpmobile = new gsl.TextField( {
		fieldLabel : l_logistics_linkmobile,
		allowBlank : true,
		vtype : 'telephone',
		maxLength : 20,
		width : 150,
		id : 'txtmErpmobile',
		dataIndex : 'erpMobile',
		name : 'pageLogCompanyVO.erpMobile'
	});

	// 联系电话
	var txtmLianxirenTel = new gsl.TextField( {
		fieldLabel : l_contact_tel,
		allowBlank : true,
		vtype : 'telephone',
		maxLength : 20,
		width : 150,
		id : 'txtmLianxirenTel',
		dataIndex : 'erpTel',
		name : 'pageLogCompanyVO.erpTel'
	});

	// 联系传真
	var txtmLianxirenFax = new Ext.form.TextField( {
		fieldLabel : l_logistics_linkfax,
		vtype : 'telephone',
		maxLength : 20,
		allowBlank : true,
		width : 150,
		id : 'txtmLianxirenFax',
		dataIndex : 'erpFax',
		name : 'pageLogCompanyVO.erpFax'
	});

	// 联系Email
	var txtmLianxirenEmail = new gsl.TextField( {
		fieldLabel : l_logistics_linkEmail,
		vtype : 'email',
		maxLength : 30,
		allowBlank : false,
		width : 150,
		id : 'txtLianxirenEmail',
		dataIndex : 'erpEmail',
		name : 'pageLogCompanyVO.erpEmail'
	});
	var logisticsModifyForm = new gsl.FormPanel( {
		frame : true,
		labelWidth : 100,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ lblmLogisticsId, logCompanyId ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ txtmLogisticsName ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ txtmLogisticsAddr ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ cbxmLogisticsType ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ cbxmLogisticsStatus ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ txtmERPlianxiren ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ txtmLianxirenEmail ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ txtmErpmobile ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ txtmLianxirenFax ]
			} ]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ txtmLianxirenTel ]
			} ]
		} ],
		buttons : [ {
			text : l_tool_save,
			handler : saveLogisticsFn
		}, {
			text : l_close,
			handler : function() {
				logisticsModifyWin.hide();
			}
		} ]

	});

	// 修改窗口
	logisticsModifyWin = new gsl.Window( {
		width : 650,
		height : 400,
		items : [ logisticsModifyForm ]
	});
	
	//修改保存方法
	function saveLogisticsFn(){
		if(logisticsModifyForm.form.isValid() ) {
	        logisticsModifyForm.submit({
	            url:'pub/logcompany-updateLogCompany.action',
	            params : {
	            },
	            success:function(form,action){
	                logisticsModifyWin.hide();
	                queryFn();
	            }
	        });
    	}else {
    		gsl.ErrorAlert('请检查数据格式');	
    	}
	}
	
	//********************************************************
    
    
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryForm, gridPanel]
	});
});