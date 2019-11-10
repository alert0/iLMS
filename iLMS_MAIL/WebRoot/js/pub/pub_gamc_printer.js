/**
 * GAMC 打印机配置信息维护
 */
Ext.onReady(function() {
	var pageAuth = 'SP'; 
	var busyFlg = false;
	
	//用户信息Store
	var store = new gsl.JsonStore({
        url : 'pub/mmPrinter-queryForPage.action',
        fields:['factory','factoryName','printerGroup','printerGroupDesc','billType','billTypeDesc','printerName','printerDesc','note','id']
    });
	
	/***********************同步零件信息查询部分*********************************/
	//工厂
//	var cmbFactory = new gsl.ComboBox({
//    	tabIndex : 1,
//        fieldLabel: '工厂',
//        id:'factory_query',
//        triggerAction : 'all',
//        anchor : '80%',
//        autoLoad : true,
//        store : new gsl.JsonCmbStore( {
//            type : 'factory',
//            addBlank : true
//        })
//    });
	
		var cmbFactory = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '工厂',
        id:'factory_query',
        triggerAction : 'all',
        allowBlank :false,
        anchor : '80%',
        autoLoad : true,
        store : new Ext.data.JsonStore( {
        	url : 'comm-cmbByFactory.action',
    		fields : [ 'code', 'name' ],
    		baseParams : {
    			'type' : 'factoryByCondition',
    			'addBlank' : false
    		}
        })
    });
	
	// 所属分组  
    var cmbPrinterGroup = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '所属分组',
        id:'printerGroup_query',
        triggerAction : 'all',
        anchor : '80%',
        autoLoad : true,
        store : new gsl.JsonCmbStore( {
            type : 'printerGroup',
            addBlank : true
        })
    });
	
    store.on('load',function(){
		busyFlg = false;
	});
	
	//查询面板
	var queryPanel = new gsl.FormPanel({
		region : 'north',
		height : 100,
        iconCls:'titQuery',
        hideCollapseTool: false,
        collapsible: true,
        labelWidth:80,
        items : [{
            layout : 'column',
            items : [{
                	columnWidth : .3,
                	layout : 'form',
                	items : [cmbPrinterGroup]
                },{
                	columnWidth : .3,
                	layout : 'form',
                	items : []
                }]
        }],buttons:[{
            text : '查询',
            handler : queryFn
        },{
            text : '重置',
            handler : queryReset
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
    		// 工厂
			'printerVO.factory':cmbFactory.getValue(),
			// 所属分组
			'printerVO.printerGroup':cmbPrinterGroup.getValue()
        };
        store.load({params:{start:0, limit:pageSize}});
    }
	
    //重置查询条件
    function queryReset(){
    	queryPanel.getForm().reset();
    }
    
    //++++++++++++++++++++++++新增界面控件
  //工厂
//	var cmbFactory_new = new gsl.ComboBox({
//    	tabIndex : 1,
//        fieldLabel: '工厂',
//        id:'factory_new',
//        allowBlank :false,
//        triggerAction : 'all',
//        anchor : '95%',
//        autoLoad : true,
//        store : new gsl.JsonCmbStore( {
//            type : 'factory',
//            addBlank : true
//        })
//    });
	
	var cmbFactory_new = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '工厂',
        id:'factory_new',
        triggerAction : 'all',
        allowBlank :false,
        anchor : '95%',
        autoLoad : true,
        store : new Ext.data.JsonStore( {
        	url : 'comm-cmbByFactory.action',
    		fields : [ 'code', 'name' ],
    		baseParams : {
    			'type' : 'factoryByCondition',
    			'addBlank' : false
    		}
        })
    });
	
	// 所属分组    
    var cmbPrinterGroup_new = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '所属分组',
        id:'printerGroup_new',
        allowBlank :false,
        triggerAction : 'all',
        anchor : '95%',
        autoLoad : true,
        store : new gsl.JsonCmbStore( {
            type : 'printerGroup',
            addBlank : true
        })
    });
    
    // 打印单据类型   
    var cmbBillType_new = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '打印单据类型',
        id:'billType_new',
        triggerAction : 'all',
        allowBlank :false,
        anchor : '95%',
        autoLoad : true,
        store : new gsl.JsonCmbStore( {
            type : 'billType',
            addBlank : true
        })
    });
    
	var printerNameNew =new gsl.TextField( {
		fieldLabel :'打印机名字',
		maxLength: 20,
		allowBlank :false,
		regex:/^[\u4e00-\u9fa5A-Za-z0-9-_]*$/,
		regexText : '请输入正确的打印机名字',
		anchor :'95%'
	});
	
	var printerDescNew =new gsl.TextField( {
		fieldLabel :'打印机描述',
		maxLength: 20,
		allowBlank :false,
		anchor :'95%'
	});
	
	var noteNew =new gsl.TextField({
		fieldLabel :'备注',
		maxLength: 20,
		anchor :'95%'
	});
	
	// 新增表单
	var PartForm = new gsl.FormPanel( {
        labelWidth:100,
        autoScroll:true,
        items : [{layout : 'column',
            items : [{
               columnWidth : .5,
               layout : 'form',
               items : [cmbFactory_new]
               },{
               columnWidth : .5,
               layout : 'form',
               items : [cmbPrinterGroup_new]
               }]
          },{layout : 'column',
            items : [{
               columnWidth : .5,
               layout : 'form',
               items : [cmbBillType_new]
               },{
                columnWidth : .5,
               layout : 'form',
               items : [printerNameNew]
               }]
          },{layout : 'column',
            items : [{
          	  columnWidth : .5,
                layout : 'form',
                items : [printerDescNew]
                },{
        	  columnWidth : .5,
                layout : 'form',
                items : [noteNew]
              }]
          }],
        buttons:[{
        	text : '保存',
        	handler : function(){
            	addFn();
            }
        },{
        	text : '关闭',
            handler : function(){
            	PrinterWin.hide();
            }
        }]
    }); 
	
	//新增窗口
    var PrinterWin = new gsl.Window( {
    	title : '新增打印机配置信息',
	    titleCollapse : true,
	    autoScroll : true,
        width : 600,
        height : 200,
        items : [ PartForm ]
    });
	
  //新增方法
    var addFn = function(){
    	PartForm.submit({
    		url:'pub/mmPrinter-addPrinter.action',
    		params:{
    			//工厂ID
            	'printerVO.factory':cmbFactory_new.getValue(),
            	// 所属分组
		 		'printerVO.printerGroup':cmbPrinterGroup_new.getValue(),
		 		// 打印单据类型
    	 		'printerVO.billType':cmbBillType_new.getValue(),
		 		// 打印机名字
    	 		'printerVO.printerName':printerNameNew.getValue(),
    	 		// 打印机描述
    	 		'printerVO.printerDesc':printerDescNew.getValue(),
    	 		// 备注
    	 		'printerVO.note': noteNew.getValue()
	        },
    		success:function(){
    			store.reload();
    			PartForm.getForm().reset();
    			PrinterWin.hide();
    			queryFn();
			}
    	});
    };
   
    //++++++++++++++++++++++++修改界面控件
    // 工厂
//    var cmbFactory_update = new gsl.ComboBox({
//    	tabIndex : 1,
//        fieldLabel: '工厂',
//        allowBlank :false,
//        id:"factory",
//        triggerAction : 'all',
//        anchor : '95%',
//        autoLoad : true,
//        store : new gsl.JsonCmbStore( {
//            type : 'factory',
//            addBlank : true
//        })
//    });
    
    var cmbFactory_update = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '工厂',
        id:'factory',
        triggerAction : 'all',
        allowBlank :false,
        anchor : '95%',
        autoLoad : true,
        store : new Ext.data.JsonStore( {
        	url : 'comm-cmbByFactory.action',
    		fields : [ 'code', 'name' ],
    		baseParams : {
    			'type' : 'factoryByCondition',
    			'addBlank' : false
    		}
        })
    });
    
	// 隐藏
	var hiddenId = new Ext.form.Hidden( {
		id :'id'
	});
	
	// 所属分组 
    var cmbPrinterGroup_update = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '所属分组',
        allowBlank :false,
        id:'printerGroup',
        triggerAction : 'all',
        anchor : '95%',
        autoLoad : true,
        store : new gsl.JsonCmbStore( {
            type : 'printerGroup',
            addBlank : true
        })
    });
    
    // 打印单据类型   
    var cmbBillType_update = new gsl.ComboBox({
    	tabIndex : 1,
        fieldLabel: '打印单据类型',
        allowBlank :false,
        id:'billType',
        triggerAction : 'all',
        anchor : '95%',
        autoLoad : true,
        store : new gsl.JsonCmbStore( {
            type : 'billType',
            addBlank : true
        })
    });
    
    var printerNameUpdate =new gsl.TextField( {
		fieldLabel :'打印机名字',
		id:'printerName',
		maxLength: 20,
		allowBlank :false,
		regex:/^[\u4e00-\u9fa5A-Za-z0-9-_]*$/,
		regexText : '请输入正确的打印机名字',
		anchor :'95%'
	});
    
    var printerDescUpdate =new gsl.TextField( {
		fieldLabel :'打印机描述',
		id:'printerDesc',
		maxLength: 20,
		allowBlank :false,
		anchor :'95%'
	});
    
    var noteUpdate =new gsl.TextField( {
		fieldLabel :'备注',
		id:'note',
		maxLength: 20,
		anchor :'95%'
	});
    
	// 修改Form
	var mmPrinterModifyForm = new gsl.FormPanel( {
        labelWidth:100,
        autoScroll:true,
        items : [{layout : 'column',
            items : [{
               columnWidth : .5,
               layout : 'form',
               items : [cmbFactory_update]
               },{
               columnWidth : .5,
               layout : 'form',
               items : [cmbPrinterGroup_update]
               }]
          },{layout : 'column',
              items : [{
                  columnWidth : .5,
                  layout : 'form',
                  items : [cmbBillType_update]
                  },{
                  columnWidth : .5,
                  layout : 'form',
                  items : [printerNameUpdate]
                  }]
           },{layout : 'column',
               items : [{
                   columnWidth : .5,
                   layout : 'form',
                   items : [printerDescUpdate ]
                   },{
                   columnWidth : .5,
                   layout : 'form',
                   items : [noteUpdate]
                   }]
            },{layout : 'column',
              items : [{
  		         columnWidth : .5,
  		         layout : 'form',
  		         items : [hiddenId]
  		         }]
            }],
        buttons:[{
        	text : '保存',
        	handler : function(){
        		updatePrinter();
            }
        },{
        	text : '关闭',
            handler : function(){
            	mmPrinterModifyWin.hide();
            }
        }]
    });
	
  //修改窗口
    var mmPrinterModifyWin = new gsl.Window( {
    	title : '修改打印机配置信息',
	    titleCollapse : true,
	    autoScroll : true,
        width : 600,
        height : 200,
        items : [ mmPrinterModifyForm ]
    });
    
 // 修改方法
    function updatePrinter(){
        mmPrinterModifyForm.submit({
            url:'pub/mmPrinter-updatePrinter.action',
            params : {
            	//工厂ID
            	'printerVO.factory': cmbFactory_update.getValue(),
            	//所属分组
            	'printerVO.printerGroup': cmbPrinterGroup_update.getValue(),
            	//打印单据类型
            	'printerVO.billType': cmbBillType_update.getValue(),
            	// 打印机名字
		 		'printerVO.printerName': printerNameUpdate.getValue(),
		 		// 打印机描述
		 		'printerVO.printerDesc': printerDescUpdate.getValue(),
		 		// 备注
		 		'printerVO.note': noteUpdate.getValue(),
    	 		// id
				'printerVO.id' : hiddenId.getValue()
            },
            success:function(form,action){
                mmPrinterModifyWin.hide();
                queryFn();
            }
        });
    }
    
  //删除数据
	var removeFn = function(record){
		gsl.RowAction( {
			url : 'pub/mmPrinter-deletePrinter.action',
            actionType : 'remove',
            params : {
		 		// ID
    	 		'printerVO.id':record.get('id')
            },
            success : function() {
            	store.reload();
            }
        });
	};
    
    //行操作
	var actionColumn = new gsl.RowActions({
		auth : pageAuth,
		edit : function(record){
			mmPrinterModifyWin.show();
			mmPrinterModifyForm.getForm().setValues(record.data);
        },
        remove : removeFn
	});
	
	//++++++++++++++++++++++
    //打印机配置信息列
	var mmPrinterInfoModel = new Ext.grid.ColumnModel([ 
        new gsl.RowNumberer(),
        actionColumn,{
			header : '工厂',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
				return v;
			},
			width: 120,
			dataIndex : 'factory'
		},{
			header : '所属分组',
			width: 100,
			dataIndex : 'printerGroupDesc'
		},{
			header : '打印单据类型',
			width: 150,
			dataIndex : 'billTypeDesc'
		},{
			header : '打印机名字',
			width: 100,
			dataIndex : 'printerName'
		},{
			header : '打印机描述',
			width: 120,
			dataIndex : 'printerDesc'
		},{
			header : '备注',
			width: 120,
			dataIndex : 'note'
		}
		// 隐藏域
        ,{
	        // 工厂id
            dataIndex: 'factory',
            hidden:true
        },{
			hidden:true,
			dataIndex : 'printerGroup'
		},{
			hidden:true,
			dataIndex : 'billType'
		},{
	        // 工作中心id
            dataIndex: 'id',
            hidden:true
        }
	]);
    
	//界面权限工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [{
			text : '新增',
			iconCls : 'add',
			handler : function(){
				PartForm.getForm().reset();
				PrinterWin.show();
			}
		}]
	});
	
	var pagingBar = new gsl.PagingToolbar({store : store, pageSize : pageSize});
	
    //打印机配置信息面板
    var mmPrinterInfoPanel = new gsl.GridPanel({ 
		region : 'center',
		iconCls : 'titList',
		title : '打印机配置信息一览',
		store : store,
		cm : mmPrinterInfoModel,                                                  
		tbar: toolBar,
		bbar: pagingBar,
		plugins : [actionColumn]                                         
	});
    
	//界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryPanel, mmPrinterInfoPanel]
	});
});