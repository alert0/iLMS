/** 物流公司供应商关系 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010202');
	var busyFlg = false;
	var selectedCodeTypeRecord = null; //选中的数据类型Record
	
	//**************数据Store*************

	
	//类型数据
	var typeStore = new gsl.JsonStore( {
		url :'pub/dict-queryDataDictForPage.action',
		fields : ['codeType', 'codeTypeName'] //,'pkId'
	});
	
	//未配置的供应商数据
	var store = new gsl.JsonStore( {
		url : 'pub/dict-queryValueForPage.action',
		fields : ['pkId','codeTypeName','codeType','codeValue','codeValueName','otherCodeValue',
                'remark','sortNo','isEdit']
	});

	// 分页栏
	var pagingBar = new gsl.PagingToolbar( {
		store : typeStore
	});
	
	// 分页栏
	var pagingBar1 = new gsl.PagingToolbar( {
		store : store
	});
	
	//*******************查询面板***********
	// 公司代码
	// 类型名称
	var txtCodeTypeName = new gsl.TextField({
		fieldLabel : '类型名称',
		id : 'codeTypeName',
		width : 150
	});

	// 编码名称
	var txtCodeType = new gsl.TextField({
		fieldLabel : '类型编码',
		id : 'txtCodeType',
		width : 150
	});
	
	 typeStore.on('load',function(){
		busyFlg = false;
	});
	
	//查询方法
	function queryFn(){
    	if(queryForm.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
			selectedCodeTypeRecord = null;
	        typeStore.baseParams = {
	            'dictMenuVO.codeTypeName':txtCodeTypeName.getValue(),
	            'dictMenuVO.codeType':txtCodeType.getValue()
	        };
	        typeStore.load({params:{start:0, limit:pageSize}});
    	}
    }
	
	// 查询条件面板
	var queryForm = new gsl.FormPanel( {
		region : 'north',
		height : 110,
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
				columnWidth : .4,
				layout : 'form',
				border : false,
				items : [ txtCodeType ]
			}, {
				columnWidth : .4,
				layout : 'form',
				border : false,
				items : [ txtCodeTypeName ]
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
		} ]
	});
//*****************************************************************************************
	 /**
	 * 新增类型信息
	 */
	  var add = function(){
		    typeAddForm.submit({
		    		url:'pub/dict-addDataDict.action',
		    		success:function(){
		    			typeAddForm.getForm().reset();
		    			typeAddWin.hide();
		    			typeStore.reload();
					}
		    	});
	}
	
	//新增
	var typeAddForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 360,
        labelAlign : 'right',
        labelWidth:120,
        frame :true,
        items : [{
        	layout : 'column',
            items : [
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [new gsl.TextField( {
					fieldLabel :'类型编码',
					width:120,
					minLength : 0,
					maxLength : 32,
					allowBlank :false,
					name :'dictMenuVO.codeType'
//					anchor :'95%'
				})
	            ,
				new gsl.TextField( {
					fieldLabel :'类型名称',
					width:120,
					minLength : 0,
					maxLength : 128,
					allowBlank :false,
					name :'dictMenuVO.codeTypeName'
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'第三方系统代码',
					minLength : 0,
					maxLength : 32,
					width:120,
					name :'dictMenuVO.otherCodeValue'					
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'备注',
					width:120,
					minLength : 0,
					maxLength : 128,
					name :'dictMenuVO.remark'
//					anchor :'95%'
			    })
	         ]},
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [new gsl.TextField( {
					fieldLabel :'编码',	
					width:120,
					minLength : 0,
					maxLength : 32,
					allowBlank :false,
					name :'dictMenuVO.codeValue'
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'编码名称',
					width : 120,
					minLength : 0,
					maxLength : 128,
					allowBlank :false,
					name :'dictMenuVO.codeValueName'
//					anchor :'95%'
				}),
				new gsl.NumberField( {
					fieldLabel :'顺序',
					width:120,
					minLength : 0,
					maxLength : 4,
					allowBlank :false,
					name :'dictMenuVO.sortNo'
					
				}),new gsl.BaseDataComboBox( {
					valueField : 'code',
					hiddenName : 'dictMenuVO.isEdit',
					allowBlank :false,
					baseData : yesNoArray,
					readOnly : true,
					addBlank : false,
					fieldLabel : '是否可编辑',
					value:1,
					width : 120
				})]
            }
            ]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	add();
            }
        },{
            text : '关闭',
            handler : function() {
            	typeAddWin.hide();
            }
        }]    
    });

	//新增窗口
	var typeAddWin = new gsl.Window({
		title : '数据添加',
		titleCollapse : true,
		autoScroll : true,
		width : 660,
		height : 230,
		items : [ typeAddForm ]
	});
	
	
	//界面权限工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [ {
			tagValue : '01020101',
			text : '新增',
			iconCls : 'add',
			handler :function(){
			  typeAddWin.show();
			
			}
	        	
		} ]
	});
	//物流公司列表
	var gridPanel = new gsl.GridPanel( {
    	region : 'west',
    	width : '40%',
		iconCls : 'titList',
		title : '类型列表',
		store : typeStore,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			{
				header : '类型编码',
				dataIndex : 'codeType',
				width : 180
			}, {
				header : '类型名称',
				dataIndex : 'codeTypeName',
				width : 180
			}
		]),
		tbar: toolBar,
		bbar : pagingBar,
		listeners : {
			rowclick : function(grid, rowIndex, e){
				selectedCodeTypeRecord = grid.getStore().getAt(rowIndex);
				store.baseParams = {
					'codeType' : selectedCodeTypeRecord.get('codeType'),
					'codeTypeName' : selectedCodeTypeRecord.get('codeTypeName')
				};
				store.load({
					params : {
						start : 0,
						limit : pageSize            
					}
				});
			}
		},
		plugins : [  ]
	});
	
	
//***********************************************************************************************	
  /**
 * 新增数据字典值
 */
  var add1 = function(){
	    AddForm.submit({
//	    		url:'pub/dict-addValue.action',
	    		url:'pub/dict-addDataDict.action',
	    		params : {
						'dictMenuVO.codeType' : selectedCodeTypeRecord.get('codeType'),
						'dictMenuVO.codeTypeName' : selectedCodeTypeRecord.get('codeTypeName')
					},
	    		success:function(){
	    			AddForm.getForm().reset();
	    			AddWin.hide();
	    			store.reload();
				}
	    	});
}
	// 新增表单
	var AddForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 360,
        labelAlign : 'right',
        labelWidth:120,
        frame :true,
        items : [{
        	layout : 'column',
            items : [
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [new gsl.TextField( {
					fieldLabel :'编码',	
					width:120,
					minLength : 0,
					maxLength : 32,
					allowBlank :false,
					name :'dictMenuVO.codeValue'
//					anchor :'95%'
				}),

				new gsl.TextField( {
					fieldLabel :'第三方系统代码',
					minLength : 0,
					maxLength : 32,
					width:120,
					name :'dictMenuVO.otherCodeValue'					
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'备注',
					width:120,
					minLength : 0,
					maxLength : 128,
					name :'dictMenuVO.remark'
//					anchor :'95%'
			    })
	         ]},
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [
				new gsl.TextField( {
					fieldLabel :'编码名称',
					width : 120,
					minLength : 0,
					maxLength : 128,
					allowBlank :false,
					name :'dictMenuVO.codeValueName'
//					anchor :'95%'
				}),
				new gsl.NumberField( {
					fieldLabel :'顺序',
					width:120,
					minLength : 0,
					maxLength : 4,
					allowBlank :false,
					name :'dictMenuVO.sortNo'
					
				}),new gsl.BaseDataComboBox( {
					valueField : 'code',
					hiddenName : 'dictMenuVO.isEdit',
					allowBlank :false,
					baseData : yesNoArray,
					readOnly : true,
					addBlank : false,
					fieldLabel : '是否可编辑',
					value:1,
					width : 120
				})]
            }
            ]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	add1();
            }
        },{
            text : '关闭',
            handler : function() {
            	AddWin.hide();
            }
        }]    
    });
	
	
	//新增窗口
	var AddWin = new gsl.Window({
		title : '数据添加',
		titleCollapse : true,
		autoScroll : true,
		width : 660,
		height : 210,
		items : [ AddForm ]
	});
	
	//已配置供应商工具条
	var ToolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [{
			tagValue : '01010101',
			text : '增加',
			iconCls : 'add',
			handler : function(){
				
				if(!selectedCodeTypeRecord || null == selectedCodeTypeRecord){
					gsl.ErrorAlert('请先选中一条类型名称!');
					return;
				}
				AddWin.show()
			}
		}]
	});
	
	
	//行操作
	//删除
   var removeFn = function(record){
			if('1' != record.get('isEdit')){
				gsl.ErrorAlert('不能进行删除操作');
				return;
			}
	   var pkId = record.get('pkId');
   		gsl.RowAction( {
	        url : 'pub/dict-deleteDataDict.action',
	        actionType : 'remove',
	        params : {
	        	'dictMenuVO.pkId' : pkId
	        },
	        success : function() {
	            store.reload();
	        }
	    });
   }
	var actionColumn = new gsl.RowActions({
		auth:'SP',
        edit : function(record){
			if('1' != record.get('isEdit')){
				gsl.ErrorAlert('面板不可编辑');
				return;
			}
        	dictModifyWin.show();
        	dictModifyForm.getForm().setValues(record.data);
        },
        remove : removeFn
	});
	var configSupPanel = new gsl.GridPanel( {
    	region : 'center',
		iconCls : 'titList',
		title : '编码列表',
		store : store,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			actionColumn,
			{
				header : '编码',
				dataIndex : 'codeValue',
				width : 90
			}, {
				header : '编码名称',
				dataIndex : 'codeValueName',
				width : 90
			}, {
				header : '第三方系统代码',
				dataIndex : 'otherCodeValue',
				width : 150
			}, {
				header : '备注',
				dataIndex : 'remark',
				width : 170
			}, {
				header : '顺序',
				width : 60,
				dataIndex : "sortNo"
			}, new gsl.LCmbColumn({
				header : '是否可编辑',
				dataIndex : 'isEdit',
				cmbData:yesNoArray,
				width : 60
			//cmbData : 
			})
		]),
		tbar: ToolBar,
		bbar : pagingBar1,
		plugins : [ actionColumn ]
	});
	
	//修改-----------------------------------------------------
	//修改表单
	var dictModifyForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 360,
        labelAlign : 'right',
        labelWidth:120,
        frame :true,
        items : [{
        	layout : 'column',
            items : [
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [new Ext.form.Hidden({
			    	dataIndex : 'pkId',
					name :'dictMenuVO.pkId'
				}),new Ext.form.Hidden( {
//					fieldLabel :'类型',
//					width:120,
//       				readOnly:true,
					dataIndex : 'codeType',
//					allowBlank :false,
					name :'dictMenuVO.codeType'
//					anchor :'95%'
				})
	            ,
	            new Ext.form.Hidden( {
//					fieldLabel :'类型名称',
//					width:120,
					dataIndex : 'codeTypeName',
//					allowBlank :false,
					name :'dictMenuVO.codeTypeName'
//					anchor :'95%'
				}),
	           
				new gsl.TextField( {
					fieldLabel :'编码',	
					readOnly:true,
					width:120,
					dataIndex : 'codeValue',
					allowBlank :false,
					name :'dictMenuVO.codeValue'
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'第三方系统代码',
					width:120,
					dataIndex : 'otherCodeValue',
					name :'dictMenuVO.otherCodeValue'
//					anchor :'95%'
				}),
				new gsl.TextField( {
					fieldLabel :'备注',
					width:120,
					dataIndex : 'remark',
					
					name :'dictMenuVO.remark'
//					anchor :'95%'
			    })
	         ]},
            {
            	columnWidth : .4,
	            layout : 'form',
	            items : [
				 new gsl.TextField( {
					fieldLabel :'编码名称',
					width : 120,
					allowBlank :false,
					dataIndex : 'codeValueName',
					name :'dictMenuVO.codeValueName'
//					anchor :'95%'
				}),
				new gsl.NumberField( {
					fieldLabel :'顺序',
					width:120,
					dataIndex : 'sortNo',
					minValue : 0,
					maxValue : 9999,
					allowBlank :false,
					name :'dictMenuVO.sortNo'
					
				}),new gsl.BaseDataComboBox( {
					valueField : 'code',
					hiddenName : 'dictMenuVO.isEdit',
					allowBlank :false,
					dataIndex : 'isEdit',
					baseData : yesNoArray,
					addBlank : false,
					fieldLabel : '是否可编辑',
					width : 120	
				})]
            }
            ]
        }],
        buttons :[{
            text : '保存',
            handler : function(){
            	modifyFn();            }
        },{
            text : '关闭',
            handler :  function() {
            	dictModifyWin.hide();
            }
        }]    
    });
    
    //修改窗口
    var dictModifyWin = new gsl.Window( {
    	title : '修改',
	    titleCollapse : true,
	    autoScroll : true,
        width : 660,
        height : 230,
        items : [ dictModifyForm ]
    });
    
    var modifyFn = function(){
    	dictModifyForm.submit( {
	            url :'pub/dict-updateDataDict.action',
		        success : function(form, action) {
		            store.reload();
	    			dictModifyWin.hide();
		        }
			});
    
    };
	//修改----------------------------------------------------------------
  
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryForm, gridPanel, configSupPanel]
	});
});