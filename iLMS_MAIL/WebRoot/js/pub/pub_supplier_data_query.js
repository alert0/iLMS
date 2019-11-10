/** 供应商数据管理 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010203');
	var busyFlg = false;
	
	//供应商注册状态
	var SUP_STATUS_ACTIVE_NO = '0';//未激活
	var SUP_STATUS_ACTIVE_YES = '1';//已激活
	var SUP_STATUS_RIGEST = '2';//已注册
	var SUP_STATUS_DISABLE = '3';//已禁用
	var supplierSupStatusArray = [ [ SUP_STATUS_ACTIVE_NO, '未激活' ], [ SUP_STATUS_ACTIVE_YES, '已激活' ],
		[ SUP_STATUS_RIGEST, '已注册' ], [ SUP_STATUS_DISABLE, '已禁用' ] ];
		
	var supRoleType = '2';//供应商角色类型
	
	// 供应商名称
	var querySupplierName = new gsl.TextField( {
		fieldLabel :l_supplierName,
			width:150
	});
	// 父供应商代码
	var queryParentNo = new gsl.TextField( {
		fieldLabel :l_father_supplier_no,
		width:150
	});
	//供应商代码
	var querySupplierNo = new gsl.TextField( {
		fieldLabel :l_supplierId,
		width:150
	});
	// 供应商状态
	var cbxquerySupplierStatus = new gsl.BaseDataComboBox( {
		valueField : 'code',
		baseData : supplierSupStatusArray,
		readOnly : true,
		addBlank : true,
		fieldLabel : '供应商状态',
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
				items : [ querySupplierNo, cbxquerySupplierStatus ]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ querySupplierName]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [queryParentNo ]
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
		url : 'pub/supManage-querySupplierDataForPage.action',
		fields : [ 'supplierNo', 'supplierName', 'addr', 'loginId', 'parentNo',
			'xuqiuLianxiren', 'xuqiuLianxiTel', 'xuqiuEmail',
			'fahuoLianxiren', 'fahuoLianxiTel', 'fahuoEmail',
			'wuliuLianxiren', 'wuliuLianxiTel', 'wuliuEmail',
			'erpLianxiren', 'erpTel','erpEMail',
			'detailAddr', 'fahuofangId', 'fahuofangAddr',
			'beiliaoTime', 'status','delFlg', 'activeStatus']
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
	            'supVO.supplierNo':querySupplierNo.getValue(),
	            'supVO.parentNo':queryParentNo.getValue(),
	            'supVO.supplierName':querySupplierName.getValue(),
	            'supVO.activeStatus':cbxquerySupplierStatus.getValue()
	        };
	        store.load({params:{start:0, limit:pageSize}});
    	}
    }
	
	// 分页栏
	var pagingBar = new gsl.PagingToolbar( {
		store : store
	});
	
    //查看
	var inspectFn = function(record){
		
		gsl.addTab({
			title : l_supplierInfoInspect, //供应商信息查看
			url : 'pub/pub_supplier_view.action?supplierNo="' + record.get('supplierNo')
					+ '"&flg=1',
			refresh:true
		});
	}
    var actionColumn = new gsl.RowActions({
    	  auth:pageAuth
    	 ,inspect : inspectFn
    });
	
    //物流公司信息一览
    var gridPanel = new gsl.GridPanel( {
    	region : 'center',
//		renderTo : 'resultDiv',
		iconCls : 'titList',
		title : l_supplierList,//供应商信息一览
		store : store,
//		width : (Ext.get('resultDiv').getWidth() > 800 ? Ext.get('resultDiv').getWidth() : 800),
//		height : 280,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			actionColumn, {
				header :l_supplierId, //供应商代码
				dataIndex :"supplierNo",
				width :70,
				align :'left'
			}, {
				header :l_supplierName,//供应商名称
				dataIndex :"supplierName",
				width :70,
				align :'left'
			}, new gsl.LCmbColumn( {
				header :l_supplierStatus,//供应商状态
				width :70,
				dataIndex :'activeStatus',
				cmbData :supplierSupStatusArray
			}),{
				header : '父供应商',
				dataIndex : 'parentNo',
				width : 71,
				align : 'center'
			},{
				header :l_erp_contacts,//ERP联系人
				dataIndex :"erpLianxiren",
				width :80,
				align :'left'
			},
			{
				header :l_erp_contacts_tel,//ERP电话
				dataIndex :"erpTel",
				width :70,
				align :'left'
			},
			{
				header :l_erp_contacts_mail,//ERP邮箱
				dataIndex :"erpEMail",
				width :0,
				align :'left'
			}, {
				header :l_order_contacts,//订货联系人
				dataIndex :"xuqiuLianxiren",
				width :80,
				align :'left'
			}, {
				header :l_order_contacts_tel,//订货联系人电话
				dataIndex :"xuqiuLianxiTel",
				width :110,
				align :'left'
			}, {
				header :l_order_contacts_mail,//订货联系人邮箱
				dataIndex :"xuqiuEmail",
				width :110,
				align :'left'
			},
			{
				header :l_delivery_contacts,//发货联系人
				dataIndex :"fahuoLianxiren",
				width :80,
				align :'left'
			}, {
				header :l_delivery_contacts_tel,//发货联系人电话
				dataIndex :"fahuoLianxiTel",
				width :110,
				align :'left'
			}, {
				header :l_delivery_contacts_mail,//发货联系人邮箱
				dataIndex :"fahuoEmail",
				width :100,
				align :'left'
			}, {
				header :l_log_contacts_abnormal,//物流异常联系人
				dataIndex :"wuliuLianxiren",
				width :70,
				align :'left'
			}, {
				header :l_log_contacts_abnormal_tel,//物流异常联系人电话
				dataIndex :"wuliuLianxiTel",
				width :100,
				align :'left'
			}, {
				header :l_log_contacts_abnormal_mail,//物流异常联系人邮箱
				dataIndex :"wuliuEmail",
				width :100,
				align :'left'
			}, {
				header :l_supplierDetailAddress,//供应商详细地址
				dataIndex :"detailAddr",
				width :110,
				align :'left'
			},{
				header :l_delivery_address,//发货方地址
				dataIndex :"fahuofangAddr",
				width :110,
				align :'left'
			}, {
				header :l_preparation_time,//准备时间
				dataIndex :"beiliaoTime",
				width :80,
				align :'right'
			}
		]),
		bbar : pagingBar,
		plugins : [ actionColumn ]
	});
	
	//********************************************************
    
    
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryForm, gridPanel]
	});
});
