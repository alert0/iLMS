/**
 * 
 * 广汽乘用车系统管理模块-操作日志查询
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 04/13 0.01  anMin    新建
 * 
 */
Ext.onReady(function() {
	var pageAuth = 'SP';
	var busyFlg = false;
	
	var store = new gsl.JsonStore({
        url : 'pub/opeLog-queryOpeLogRecordForPage.action',
        fields:['tableName','fromName','trxType','columns','oldValue','loclIp',
                'userName','trxTimeStr']
    });
    store.on('load',function(){
		busyFlg = false;
	});

	// 表名
	var txtOpeTableName = new gsl.TextField({
		fieldLabel : '业务',
		id : 'tableName',
		width : 150
	});
	//操作类型
    var cmbTxType = new gsl.BaseDataComboBox({
        fieldLabel: '操作类型',
        triggerAction : 'all',
        autoLoad : true,
        displayField : 'name',
        valueField : 'code',
        addBlank : true,
        anchor : '80%',
        baseData : trxTypeArray
    });
    
    // 操作日期
	var txtTrxTime = new gsl.FromToDateField( {
			fieldLabel : "操作时间",
			fromDateValue : new Date().add(Date.DAY, -1),
			fromAllowBlank : false,
			fromDateId : "trxTimeStartStr",
			toDateId : "trxTimeEndStr"
		});
	
	
	
	var queryBtn = new Ext.Button( {
			text :'查询',
			handler :queryFn
		});
	
	

	//查询面板
	var OpeLogQueryPanel = new gsl.FormPanel({
		region : 'north',
		height : 100,
		iconCls : 'titQuery',
		hideCollapseTool : false,
		collapsible : true,
		labelWidth : 120,
			items : [ {
				layout :'column',
				border :false,
				items : [ {
					layout :'form',
					columnWidth : .33,
					border :false,
					items : [ txtOpeTableName ]
				},{
					layout :'form',
					columnWidth : .33,
					border :false,
					items : [ cmbTxType ]
				},{
					layout :'form',
					columnWidth : .33,
					border :false,
					items : [ txtTrxTime ]
				}]
			} ],
				buttons : [ {
				text : l_query,
				handler : queryFn
			} ],
			keys : [ {
     			key : 13,
     			fn : queryFn,
     			scope : this
     		}]
	});

	

	//查询方法
	function queryFn() {
	if(OpeLogQueryPanel.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
	        store.baseParams = {
	        	'opeLogVO.tableName':txtOpeTableName.getValue(),
	            'opeLogVO.trxType':cmbTxType.getValue(),
	            'opeLogVO.trxTimeStartStr' : Ext.fly('trxTimeStartStr').getValue(),
				'opeLogVO.trxTimeEndStr' : Ext.fly('trxTimeEndStr').getValue()
	            };
	        store.load({params:{start:0, limit:pageSize}});
    	}
	}
	
	//操作日志
	var OpeLogCm = new Ext.grid.ColumnModel([ 
		new gsl.RowNumberer(),
			{
				header : '业务',
				dataIndex : "tableName",
				width : 120
			}, {
				header : '触发方式',
				dataIndex : "fromName",
				width : 120
			},new gsl.LCmbColumn( {
	        	// 操作类型
				header : '操作类型',
				width : 70,
                sortable: true,
				dataIndex :'trxType',
				align : 'center',
				cmbData : trxTypeArray
			}),{
				header : '操作内容',
				width : 400,
				dataIndex : "oldValue"
			}, {
				header : '操作人',
				width : 60,
				dataIndex : "userName"
			}, {
				header : '操作IP',
				width : 80,
				dataIndex : "loclIp"
			},{
				header : '操作时间',
				width : 120,
				dataIndex : 'trxTimeStr'
			}
	]);

	var pagingBar = new gsl.PagingToolbar({
		store : store,
		pageSize : pageSize
	});

	//字典信息面板
	var dictInfoPanel = new gsl.GridPanel({
		region : 'center',
		iconCls : 'titList',
		title : '操作日志一览',
		store : store,
		cm : OpeLogCm,
		bbar : pagingBar,
		plugins : []
	});

	//界面布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ OpeLogQueryPanel, dictInfoPanel ]
	});
});