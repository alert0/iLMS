/** 数据字典 */
Ext.onReady(function() {
	var pageAuth = 'SP';
	var busyFlg = false;
	
	
	
	var store = new gsl.JsonStore({
        url : 'pub/dict-queryDataDictForPage.action',
        fields:['pkId','codeTypeName','codeType','codeValue','codeValueName','otherCodeValue',
                'remark','sortNo','isEdit']
    });
    store.on('load',function(){
		busyFlg = false;
	});

	// 类型名称
	var codeTypeName = new gsl.TextField({
		fieldLabel : '类型名称',
		id : 'codeTypeName',
		width : 150
	});

	// 编码名称
	var codeValueName = new gsl.TextField({
		fieldLabel : '编码名称',
		id : 'codeValueName',
		width : 150
	});
	
	
	
	var queryBtn = new Ext.Button( {
			text :'查询',
			handler :queryFn
		});
	
	

	//查询面板
	var dictQueryPanel = new gsl.FormPanel({
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
					items : [ codeTypeName ]
				},{
					layout :'form',
					columnWidth : .33,
					border :false,
					items : [ codeValueName ]
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
	if(dictQueryPanel.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
	        store.baseParams = {
	        	'dictMenuVO.codeTypeName':codeTypeName.getValue(),
	            'dictMenuVO.codeValueName':codeValueName.getValue()
	            };
	        store.load({params:{start:0, limit:pageSize}});
    	}

	}
	
	
//删除
   var removeFn = function(record){
			if('1' != record.get('isEdit')){
				gsl.ErrorAlert('不能进行删除操作');
				return;
			}
   		gsl.RowAction( {
	        url : 'pub/dict-deleteDataDict.action',
	        actionType : 'remove',
	        params : {
	        	'dictMenuVO.pkId' : record.get('pkId') 
	        },
	        success : function() {
	            store.reload();
	        }
	    });
   }
   
   
   		/**
 * 新增数据字典信息
 */
  var add = function(){
	    dictAddForm.submit({
	    		url:'pub/dict-addDataDict.action',
	    		success:function(){
	    			dictAddForm.getForm().reset();
	    			dictAddWin.hide();
	    			store.reload();
				}
	    	});
}
  
	   
    
    
    
   //修改方法
    var modifyFn = function(){
    	dictModifyForm.submit( {
	            url :'pub/dict-updateDataDict.action',
		        success : function(form, action) {
		            store.reload();
	    			dictModifyWin.hide();
		        }
			});
    
    };
   
   
	//行操作
	
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
        
         
     
	
	
	//字典信息模块
	var dictInfoModel = new Ext.grid.ColumnModel([ 
		new gsl.RowNumberer(),
			actionColumn,{
				header : '类型',
				dataIndex : "codeType",
				width : 100
			}, {
				header : '类型名称',
				dataIndex : "codeTypeName",
				width : 70
			}, {
				header : '编码',
				dataIndex : "codeValue",
				width : 100
			}, {
				header : '编码名称',
				width : 100,
				dataIndex : "codeValueName"
			}, {
				header : '第三方系统代码',
				width : 90,
				dataIndex : "otherCodeValue"
			}, {
				header : '备注',
				width : 60,
				dataIndex : "remark"
			}, {
				header : '顺序',
				width : 60,
				dataIndex : "sortNo"
			}, new gsl.LCmbColumn({
				header : '是否可编辑',
				dataIndex : 'isEdit',
				cmbData:yesNoArray,
				width : 70
			//cmbData : 
			})

	]);
	
	
	

	
	// 新增表单
	var dictAddForm = new gsl.FormPanel({
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
					fieldLabel :'类型',
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
            	dictAddWin.hide();
            }
        }]    
    });

	
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
				}),new gsl.TextField( {
					fieldLabel :'类型',
					width:120,
       				readOnly:true,
					dataIndex : 'codeType',
					allowBlank :false,
					name :'dictMenuVO.codeType'
//					anchor :'95%'
				})
	            ,
	            new gsl.TextField( {
					fieldLabel :'类型名称',
					width:120,
					dataIndex : 'codeTypeName',
					allowBlank :false,
					name :'dictMenuVO.codeTypeName'
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
	            items : [new gsl.TextField( {
					fieldLabel :'编码',	
					readOnly:true,
					width:120,
					dataIndex : 'codeValue',
					allowBlank :false,
					name :'dictMenuVO.codeValue'
//					anchor :'95%'
				}),
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
	//新增窗口
	var dictAddWin = new gsl.Window({
		title : '数据添加',
		titleCollapse : true,
		autoScroll : true,
		width : 660,
		height : 230,
		items : [ dictAddForm ]
	});
	
	//修改窗口
    var dictModifyWin = new gsl.Window( {
    	title : '修改数据模块',
	    titleCollapse : true,
	    autoScroll : true,
        width : 660,
        height : 230,
        items : [ dictModifyForm ]
    });
    

	//界面权限工具条
	var toolBar = new gsl.Toolbar({
		auth : pageAuth,
		tools : [ {
			tagValue : '01020101',
			text : '新增',
			iconCls : 'add',
			handler :function(){
			  dictAddWin.show();
			
			}
	        	
		} ]
	});

	var pagingBar = new gsl.PagingToolbar({
		store : store,
		pageSize : pageSize
	});
	
	

  

    
   
    

	//字典信息面板
	var dictInfoPanel = new gsl.GridPanel({
		region : 'center',
		iconCls : 'titList',
		title : '数据字典信息一览',
		store : store,
		cm : dictInfoModel,
		tbar : toolBar,
		bbar : pagingBar,
		plugins : [ actionColumn ]
	});

	//界面布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ dictQueryPanel, dictInfoPanel ]
	});
})