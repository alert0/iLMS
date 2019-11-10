

Ext.onReady(function() {
	var paramValReg;
	var text;
	
function queryFn() {
	if(dictQueryPanel.form.isValid()){
			if(busyFlg){
				return;
			}
			busyFlg = true;
	        store.baseParams = {
	            };
	        store.load({params:{start:0, limit:pageSize}});
    	}

	}

	
   var store = new gsl.JsonStore({
        url : 'pub/dict-querySysParam.action',
        fields:['paramCode','paramGroup','paramName','paramVal','isEdit','paramType','note',
                'lastUpdataUserName','lastUpdateTime','uda1','uda2']
    });
    Ext.MessageBox.wait(l_msg_waiMsg, l_msg_tip);
   store.load({params:{start:0, limit:pageSize},
   	callback:function(){
   		if (Ext.MessageBox.isVisible()) { Ext.MessageBox.hide();}
   	}
   });
   
   
   
   
     
   //修改方法
    var modifyFn = function(){
    	sysModifyForm.submit( {
	            url :'pub/dict-updateSysParam.action',
		        success : function(form, action) {
		            store.reload();
	    			sysModifyWin.hide();
		        }
			});
    
    };
   

//行操作
var actionColumn = new gsl.RowActions({
		auth:'SP',
        edit : function(record){
//			if('1' != record.get('isEdit')){
//				gsl.ErrorAlert('面板不可编辑');
//				return;
//			}
        	if(Ext.isEmpty(record.get('uda1'))){
        		paramValReg = null;
        	}else{
				paramValReg = new RegExp(record.get('uda1'));
        	}
			text = record.get('uda2');
			
        	sysModifyWin.show();
        	sysModifyForm.getForm().setValues(record.data);
        }
	});


	var sysInfoModel = new Ext.grid.ColumnModel([ 
	
		new gsl.RowNumberer(),
			actionColumn,{
				header : '参数编码',
				dataIndex : "paramCode",
				width : 110
			}
//			, {
//				header : '参数组',
//				dataIndex : "paramGroup",
//				width : 70
//			}
			, {
				header : '参数名',
				dataIndex : "paramName",
				width : 150
			}, {
				header : '参数值',
				width : 90,
				dataIndex : "paramVal"
			}, {
				header : '参数类型',
				width : 60,
				dataIndex : "paramType"
			}
//			, {
//				header : 'NOTE',
//				width : 60,
//				dataIndex : "note"
//			}
			,{
				header : '最后修改人',
				width : 100,
				dataIndex : "lastUpdateName"
			},{
				header : '最后修改时间',
				width : 120,
				dataIndex : "lastUpdateTime"
			}
//			,{
//				header : '创建时间',
//				width : 100,
//				dataIndex : "createTime"
//			}
//			,{
//				header : '正则表达',
//				width : 100,
//				dataIndex : "uda1"
//			}
//			,{
//				header : '正则表达描述',
//				width : 100,
//				dataIndex : "uda2"
//			}
			, new gsl.LCmbColumn({
				header : '是否可编辑',
				dataIndex : 'isEdit',
				cmbData:yesNoArray,
				width : 70
			
			})

	]);
	

	//*****************修改表单*********************************************
	
	var modLabParamName = new gsl.LabelField( { 
		fieldLabel :'参数名称',
		dataIndex : 'paramName',
		allowBlank : false,
		readOnly:true,
		FormatComma : false,
		anchor :'95%',
		name :'sysParamVO.paramName'
	});
	
	var modTxtParamVal = new gsl.TextField( {
		fieldLabel :'参数值',
		dataIndex : 'paramVal',
		allowBlank : false,
		name :'sysParamVO.paramVal',
		anchor :'95%',
		invalidText : "请输入正确的数据",
//		tabIndex : 3,
		validator : function(){
			var val = modTxtParamVal.getValue();
			if(!paramValReg){
				return true;
			}
			if (paramValReg.test(val)) {
		        return true;
		    } else {
		        modTxtParamVal.invalidText = text;
		        return false;
		    }
		}
	});
	
	var sysModifyForm = new gsl.FormPanel({
    	titleCollapse : true,
      	autoScroll : true,
        width : 500,
        labelWidth : 85,
        labelAlign : 'right',
        frame :true,
//        border :false,s
        items : [ {
            layout : 'column',
            items : [{
                columnWidth :.9,
				layout :'form',
				border :false,
				items : [ new Ext.form.Hidden({
			    	dataIndex : 'paramCode',
					name :'sysParamVO.paramCode'
				}),modLabParamName]
            }, {
                columnWidth :.9,
				layout :'form',
				border :false,
				items : [modTxtParamVal]
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
            	sysModifyWin.hide();
            }
        }]    
    });
	
	//修改窗口
    var sysModifyWin = new gsl.Window( {
    	title : '修改',
	    titleCollapse : true,
        width : 420,
        height : 150,
        border :false,
        items : [ sysModifyForm ]
    });
	
	
	//*****************************************************************


var pagingBar = new gsl.PagingToolbar({
		store : store,
		pageSize : pageSize
	});



var sysInfoPanel = new gsl.GridPanel({
		region : 'center',
		iconCls : 'titList',
		title : '系统参数信息一览',
		store : store,
		cm : sysInfoModel,
		bbar : pagingBar,
		plugins : [ actionColumn ]
	});

var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ sysInfoPanel ]
	});

});