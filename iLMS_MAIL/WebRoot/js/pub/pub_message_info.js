/** 公告信息管理 */
Ext.onReady(function(){
	var pageAuth = 'SP';//gsl.getPageAuth('010203');
	var busyFlg = false;
	
	// 公告标题
	var txtMessageTitle = new gsl.TextField( {
		fieldLabel : l_bulletinTitle, //公告标题
		id : 'txtMessageTitle',
		width : 150
	});
	
	//公告发布日期
	var txtPublishDate = new gsl.FromToDateField({
		fieldLabel : l_bulletin_publication_date, 
		fromDateValue : new Date().add(Date.DAY, -7),
		fromAllowBlank : true,
		fromDateId : "needDateFrom",
		toDateValue : new Date(),
		toAllowBlank : true,
		toDateId : "needDateTo"
	});
	
	// 查询条件
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
				items : [ txtMessageTitle]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ txtPublishDate]
			}, {
				columnWidth : .33,
				layout : 'form',
				border : false,
				items : [ ]
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
		url : 'pub/message-queryMessageManageForPage.action',
		fields : ['infoId', 'factory', 'infoTitle', 'infoDetails',
				'fileName', 'filePath', 'publishUser', 'publishUserCName',
				'publistDateStr', 'startDateStr', 'endDateStr', 'effectDateStr',
				'supGroupIdList']
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
	            'pageVO.infoTitle':txtMessageTitle.getValue(),
	            'pageVO.queryDateStartStr':Ext.fly('needDateFrom').getValue(),
	            'pageVO.queryDateEndStr':Ext.fly('needDateTo').getValue()
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
			tagValue : '',
			text : '新增',
			iconCls : 'add',
			handler : function(){
				infoAddWin.show();
			}
		}]
	});
   
   //查看方法
   var inspectFn = function(record){
   		viewForm.form.reset();
		viewWin.show();
		viewForm.getForm().setValues(record.data);
		viewDffDate.setValue(record.get("startDateStr")+ "～" + record.get("endDateStr"));
		
		if(Ext.getCmp('viewMsgContent') != null) {
    		viewForm.remove(Ext.getCmp('viewMsgContent'));
		}
        var tempPanel = new Ext.Panel({
        	style : 'margin-left:10px;background-color:white;',
        	width :450,
			height :220,
			autoScroll : true,
			id : 'viewMsgContent',
        	html : record.get('infoDetails')
        });
        viewForm.add(tempPanel);
        viewForm.doLayout();
   } 
	
	//修改
   var modifyFn = function(record){
   		infoModifyForm.form.reset();
		infoModifyWin.show();
		infoModifyForm.getForm().setValues(record.data);
		Ext.getCmp('modifyStartDate').setValue(record.get('startDateStr'));
		Ext.getCmp('modifyEndDate').setValue(record.get('endDateStr'));
		
		modifyInfo.setValue(record.get('infoDetails'));
		
		var groupIdList = record.get('supGroupIdList');
		if(!Ext.isEmpty(groupIdList)){
		
	        var groupIds = groupIdList.split(',');
	        modifySupplierGroup.items.each(function(item) {
	            for(var i = 0; i < groupIds.length; i++){
	                if(item.inputValue == groupIds[i]){
	                    item.setValue(true);
	                    return;
	                }      
	            }
	        });
		}
   }
   
   //删除
   var removeFn = function(record){
   	
   		gsl.RequestWaitAction({
			actionNm : '公告删除',
			waitMsg : '正在删除公告,请稍候……',
			url : 'pub/message-deleteMessageInfo.action',
			params : {
				'pageVO.infoId' : record.get('infoId')
			},
			maskId : "resultDiv",
			success : function(resp) {
				store.reload();
			}
		});
   }

    // 操作列
    var actionColumn = new gsl.RowActions({
    	  auth:pageAuth
    	 ,inspect:inspectFn 
         ,edit:modifyFn 
         ,remove:removeFn
    });
	
    //公告信息一览
    var gridPanel = new gsl.GridPanel( {
    	region : 'center',
//		renderTo : 'resultDiv',
		iconCls : 'titList',
		title : l_bulletinInfoGrid,
		store : store,
//		width : (Ext.get('resultDiv').getWidth() > 800 ? Ext.get('resultDiv').getWidth() : 800),
//		height : 280,
		cm : new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
			actionColumn, {
				header :l_bulletinTitle, //公告标题
				dataIndex :"infoTitle",
				width :200
			}, {
				header :l_bulletin_period, // 公告有效期
				dataIndex :"startDate",
				width :150,
				align :'left',
				renderer : function(v, m, r) {
					return r.get("startDateStr")+ "～" + r.get("endDateStr");
				}
			}, {
				header :l_publication_man, //发布人
				dataIndex :"publishUserCName",
				width :80
			}, {
				header :l_annex_name, //附件名称
				dataIndex :"fileName",
				width :190
			}, {
				header :l_publication_date, //发布日期
				dataIndex :"publistDateStr",
				width :80
			}
		]),
		tbar : toolBar,
		bbar : pagingBar,
		plugins : [ actionColumn ]
	});
	
	//*********************************查看 Form****************************************
	//公告标题
	var viewInfoTitle = new gsl.LabelField( {
		fieldLabel :l_bulletinTitle,
		dataIndex :"infoTitle"
	});
	//发布日期
	var viewPublistDate = new gsl.LabelField( {
		fieldLabel :l_publication_date,
		dataIndex :"publistDateStr"
	});
	//发布人
	var viewPublishUser = new gsl.LabelField( {
		fieldLabel :l_publication_man,
		dataIndex :"publishUserCName"
	});
	//公告有效期
	var viewDffDate = new gsl.LabelField( {
		fieldLabel : l_bulletin_period
	});
	//附件名称
	var viewFileName = new gsl.LabelField( {
		fieldLabel :l_annex_name,
		dataIndex :"fileName"
	});
	//消息内容
//	var viewMsgContent = new gsl.TextArea( {
//		width :400,
//		height :220,
//		readOnly : true,
//		disable : true,
//		dataIndex :"infoDetails",
//		fieldLabel :l_msg_content
//	});
	var viewMsgContent = new Ext.Panel({
		width :400,
		height :220,
		id : 'viewMsgContent'
	});
	
	var viewForm = new gsl.FormPanel( {
		autoScroll:true,
		items : [ {
			layout :'column',
			border :false,
			labelSeparator :'：',
			items : [ {
				columnWidth : 1,
				layout :'form',
				border :false,
				items : [viewInfoTitle, viewDffDate]
			},{
				columnWidth : 0.4,
				layout :'form',
				border :false,
				items : [viewPublishUser]
			},{
				columnWidth : 0.4,
				layout :'form',
				border :false,
				items : [viewPublistDate]
			},{
				columnWidth : 1,
				layout :'form',
				border :false,
				items : [viewFileName]
			}
			,{
				columnWidth : 1,
				layout :'form',
				border :false,
				items : [viewMsgContent]
			} 
			]
		}],
		buttonAlign :'center',
		buttons : [{
			text :l_close,
			handler : function() {
				viewWin.hide();
			}
		} ]
	});
	
	var viewWin = new gsl.Window( {
		title : '公告查看',
		width : 510,
		height : 450,
		items : [ viewForm ]
	});
	
	//*********************************查看 Form****************************************
	
	//*********************************新增****************************************
	
	//公告标题
	var addNoticeName = new gsl.TextField( {
		fieldLabel :l_bulletinTitle,
		width :400,
		name :"pageVO.infoTitle",
		allowBlank :false,
		maxLength :30
	});
	
	//公告内容
//	var addInfo = new gsl.TextArea( {
//		width :400,
//		height :100,
//		name :"pageVO.infoDetails",
//		fieldLabel :l_msg_content,
//		allowBlank :false,
//		maxLength :300
//	});
	var addInfo = new Ext.form.HtmlEditor({
//	  name:'addInfoHtmlEditor',
	  name :"pageVO.infoDetails",
	  autoHeight:false,
	  id :'addInfoHtmlEditor',
	  height:150,
	  width:400,
	  fieldLabel:l_msg_content,
	  createLinkText:'创建超链接',
	  defaultLinkValue:"http://www.",
	  enableAlignments:true,
	  enableColors:true,
	  enableFont:true,
	  enableFontSize:true,
	  enableFormat:true,
	  enableLinks:false,
	  enableLists:false,
	  enableSourceEdit:true,
	  fontFamilies:['宋体','隶书','黑体'],
	  buttonTips:{
	   bold:{title:'Bold(Ctrl+B)',text:'粗体'},
	   italic:{title:'Italic(Ctrl+T)',text:'斜体'},
	   underline:{title:'Underline(Ctrl+U)',text:'下划线'},
	   increasefontsize:{title:'Grow Text',text:'增大字体'},
	   decreasefontsize:{title:'Shrink Text',text:'缩小字体'},
	   backcolor:{title:'Text Highlight Color',text:'背景色'},
	   forecolor:{title:'Font Color',text:'前景色'},
	   justifyleft:{title:'Align Text Left',text:'左对齐'},
	   justifycenter:{title:'Center Text',text:'居中对齐'},
	   justifyright:{title:'Align Text Right',text:'右对齐'},
//	   insertunorderedlist:{title:'Bullet List',text:'项目符号'},
//	   insertorderedlist:{title:'Numbered List',text:'数字编码'},
//	   createlink:{title:'Hyperlink',text:'超链接'},
	   sourceedit:{title:'Source Edit',text:'切换源代码编辑模式'}
	  }
     }) ;

	//公告有效日期
	var addEntryDate = new gsl.FromToDateField( {
		fieldLabel :l_bulletin_period,
		fromDateId :"addStartDate",
		toDateId :"addEndDate",
		fromAllowBlank :false,
		toAllowBlank :false
	});
	
	//全选按钮事件
	function addOnSelectAll() {
	   var group = Ext.getCmp('addSupplierGroup');
	   var length = group.items.getCount();
	   var all;
	   if (this.checked==true){
	    all = true;
	   }else
	   {
	    all = false;
	   }
	   for (var i = 0;i<length;i++){
	    group.items.get(i).setValue(all);
	   }
	}

    var addCheckBoxAll = new Ext.form.Checkbox({
    	fieldLabel: '全选', 
    	id:'addCheckBoxAll',
    	checked : false,
    	handler:addOnSelectAll
    }); 
    
    //分组信息
    var addSupplierGroup = new gsl.CheckboxGroup( {
		url :'comm-cmb.action?type=supGroupId&temptime='+ (new Date().getTime().toString(36)),
		fieldLabel :'分配供应商组',
		allowBlank : true,
		columns :2, 
		id:'addSupplierGroup',
		msgTarget :'side',
		anchor :'90%'
	});
	
	// 文件路径
	var addFileUrl = new Ext.form.Hidden( {
		name :'pageVO.filePath'
	});
	
	var infoAddForm = new gsl.FormPanel( {
		fileUpload :true,
		autoScroll:true,
		items : [ {
			layout :'column',
			border :false,
			labelSeparator :'：',
			items : [ {
				columnWidth : 1,
				layout :'form',
				border :false,
				items : [ addNoticeName, addInfo, {
					xtype :'fileuploadfield',
					width :400,
					id :'addattachment',
					emptyText :l_msg_pls_choose_upload_file, //请选择一个文件上传...
					fieldLabel :l_bulletinAnnex, //公告附件
					name :'importVO.attachment',
					buttonText :l_browse //浏览...
				}, addEntryDate, addFileUrl]
			} ]
		},addCheckBoxAll, addSupplierGroup ],
		buttonAlign :'center',
		buttons : [ {
			text :l_save,
			handler :saveAddInfoFn
		}, {
			text :l_close,
			handler : function() {
				infoAddWin.hide();
			}
		} ]
	});

	// 新增窗口
	infoAddWin = new gsl.Window( {
		title : '新增公告',
		width : 580,
		height : 450,
		items : [ infoAddForm ]
	});
	
	//新增保存方法
	function saveAddInfoFn(){
//		var text = document.getElementsByName("addInfoHtmlEditor")[0].value; 
//		alert(text);
//        var re = /(<p>)(&nbsp;)+(<\/p>)/g;
//        var content = Ext.getDom('addInfoHtmlEditor').value;
//        content = content.replace(re,"");
//        alert(content);
		
        //查看
//        viewWin.show();
//        viewMsgContent.setValue(text);
//        viewMsgContent.html = text;
//        viewMsgContent.removeAll();
//        if(Ext.getCmp('viewMsgContent') != null) {
//    		viewForm.remove(Ext.getCmp('viewMsgContent'));
//		}
//        var tempPanel = new Ext.Panel({
//        	style : 'margin-left:20px;',
//        	width :400,
//			height :220,
//			id : 'viewMsgContent',
//        	html : text
//        });
//        viewForm.add(tempPanel);
//        viewForm.doLayout();
        
        //x修改
//        infoModifyWin.show();
//        modifyInfo.setValue(text);
		
//		return;
		
		var txturl = Ext.fly('addattachment').getValue();
		if(infoAddForm.form.isValid() && checkFileTypeFn(txturl) ) {
			
			if (l_msg_pls_choose_upload_file == txturl || txturl == ''
					|| txturl == 'undefined') {
				addFileUrl.setValue('');
			} else {
				addFileUrl.setValue(txturl);
			}
			
			var modifyGroupList= "";
	        addSupplierGroup.items.each(function(item) {
	               if (item.getValue()) {
	            	   if(modifyGroupList == ""){
	            		   modifyGroupList = modifyGroupList + item.getRawValue();
	            	   }else{
	            		   modifyGroupList = modifyGroupList + ',' + item.getRawValue();  
	            	   }
	               }
	         });
			
	        infoAddForm.submit({
	            url:'pub/message-addMessageInfo.action',
	            params : {
	            	'pageVO.startDateStr' :Ext.fly('addStartDate').getValue(),
					'pageVO.endDateStr' :Ext.fly('addEndDate').getValue(),
	            	'pageVO.supGroupIdList' : modifyGroupList
	            },
	            success:function(form,action){
	            	infoAddForm.getForm().reset();
	                infoAddWin.hide();
	                queryFn();
	            }
	        });
    	}
//    	else {
//    		gsl.ErrorAlert('请检查数据格式');	
//    	}
	}
	
	//*********************************新增****************************************
	
	//文件类型检查
	var checkFileTypeFn = function(txturl) {
		//请选择一个文件上传...
		if (l_msg_pls_choose_upload_file == txturl || txturl == ''
				|| txturl == 'undefined') {
			return true;
		}
		s = txturl;
		s = s.substr(s.lastIndexOf(".") + 1);
		if ( s != "rar") {  //s != "pdf" && s != "xls" && s != "doc" && s != "xlsx" && s != "docx"
			//附件只能上传pdf|xls|doc|rar类型文档！
			gsl.ErrorAlert('只允许上传扩展名为rar的文件'); //l_msg_pls_choose_upload_file_type
			return false;
		}
		return true;
	};
	
	//*********************************修改********************
	
	 // 公告ID
	var modifyInfoId = new Ext.form.Hidden( {
		dataIndex :"infoId",
		name :'pageVO.infoId'
	});
	
	//公告标题
	var modifyNoticeName = new gsl.TextField( {
		fieldLabel :l_bulletinTitle,
		width :400,
		dataIndex :"infoTitle",
		name :"pageVO.infoTitle",
		allowBlank :false,
		maxLength :30
	});
	
	//公告内容
//	var modifyInfo = new gsl.TextArea( {
//		width :400,
//		height :100,
//		dataIndex :"infoDetails",
//		name :"pageVO.infoDetails",
//		fieldLabel :l_msg_content,
//		allowBlank :false,
//		maxLength :300
//	});
	var modifyInfo = new Ext.form.HtmlEditor({
//	  name:'modifyInfoHtmlEditor',
	  name :"pageVO.infoDetails",
	  autoHeight:false,
	  id :'modifyInfoHtmlEditor',
	  height:150,
	  width:400,
	  fieldLabel:l_msg_content,
	  createLinkText:'创建超链接',
	  defaultLinkValue:"http://www.",
	  enableAlignments:true,
	  enableColors:true,
	  enableFont:true,
	  enableFontSize:true,
	  enableFormat:true,
	  enableLinks:true,
	  enableLists:false,
	  enableSourceEdit:false,
	  fontFamilies:['宋体','隶书','黑体'],
	  buttonTips:{
	   bold:{title:'Bold(Ctrl+B)',text:'粗体'},
	   italic:{title:'Italic(Ctrl+T)',text:'斜体'},
	   underline:{title:'Underline(Ctrl+U)',text:'下划线'},
	   increasefontsize:{title:'Grow Text',text:'增大字体'},
	   decreasefontsize:{title:'Shrink Text',text:'缩小字体'},
	   backcolor:{title:'Text Highlight Color',text:'背景色'},
	   forecolor:{title:'Font Color',text:'前景色'},
	   justifyleft:{title:'Align Text Left',text:'左对齐'},
	   justifycenter:{title:'Center Text',text:'居中对齐'},
	   justifyright:{title:'Align Text Right',text:'右对齐'},
//	   insertunorderedlist:{title:'Bullet List',text:'项目符号'},
//	   insertorderedlist:{title:'Numbered List',text:'数字编码'},
//	   createlink:{title:'Hyperlink',text:'超链接'},
	   sourceedit:{title:'Source Edit',text:'切换源代码编辑模式'}
	  }
     }) ;

	//公告有效日期
	var modifyEntryDate = new gsl.FromToDateField( {
		fieldLabel :l_bulletin_period,
		fromDateId :"modifyStartDate",
		toDateId :"modifyEndDate",
		fromAllowBlank :false,
		toAllowBlank :false
	});
	
	//全选按钮事件
	function modifyOnSelectAll() {
	   var group = Ext.getCmp('modifySupplierGroup');
	   var length = group.items.getCount();
	   var all;
	   if (this.checked==true){
	    all = true;
	   }else
	   {
	    all = false;
	   }
	   for (var i = 0;i<length;i++){
	    group.items.get(i).setValue(all);
	   }
	}

    var modifyCheckBoxAll = new Ext.form.Checkbox({
    	fieldLabel: '全选', 
    	id:'modifyCheckBoxAll',
    	checked : false,
    	handler:modifyOnSelectAll
    }); 
    
    // 文件路径
	var modifyFileUrl = new Ext.form.Hidden( {
		name :'pageVO.filePath'
	});
    
    //分组信息
    var modifySupplierGroup = new gsl.CheckboxGroup( {
		url :'comm-cmb.action?type=supGroupId&temptime='+ (new Date().getTime().toString(36)),
		fieldLabel :'分配供应商组',
		allowBlank :true,
		columns :2, 
		id:'modifySupplierGroup',
		msgTarget :'side',
		anchor :'90%'
	});
	
	var infoModifyForm = new gsl.FormPanel( {
		fileUpload :true,
		autoScroll:true,
		items : [ {
			layout :'column',
			border :false,
			labelSeparator :'：',
			items : [ {
				columnWidth : 1,
				layout :'form',
				border :false,
				items : [ modifyNoticeName, modifyInfo, {
					xtype :'fileuploadfield',
					width :320,
					id :'modifyattachment',
					emptyText :l_msg_pls_choose_upload_file, //请选择一个文件上传...
					fieldLabel :l_bulletinAnnex, //公告附件
					name :'importVO.attachment',
					buttonText :l_browse //浏览...
				}, modifyEntryDate, modifyFileUrl, modifyInfoId]
			} ]
		},modifyCheckBoxAll, modifySupplierGroup ],
		buttonAlign :'center',
		buttons : [ {
			text :l_save,
			handler :saveInfoFn
		}, {
			text :l_close,
			handler : function() {
				infoModifyWin.hide();
			}
		} ]
	});

	// 修改窗口
	infoModifyWin = new gsl.Window( {
		title : '修改公告',
		width : 580,
		height : 450,
		items : [ infoModifyForm ]
	});
	
	//修改保存方法
	function saveInfoFn(){
		
		var txturl = Ext.fly('modifyattachment').getValue();
		if(infoModifyForm.form.isValid() && checkFileTypeFn(txturl) ) {
			
			if (l_msg_pls_choose_upload_file == txturl || txturl == ''
					|| txturl == 'undefined') {
				modifyFileUrl.setValue('');
			} else {
				modifyFileUrl.setValue(txturl);
			}
			
			var modifyGroupList= "";
	        modifySupplierGroup.items.each(function(item) {
	               if (item.getValue()) {
	            	   if(modifyGroupList == ""){
	            		   modifyGroupList = modifyGroupList + item.getRawValue();
	            	   }else{
	            		   modifyGroupList = modifyGroupList + ',' + item.getRawValue();  
	            	   }
	               }
	         });
	         
	         infoModifyForm.submit({
	            url:'pub/message-updateMessageInfo.action',
	            params : {
	            	'pageVO.startDateStr' :Ext.fly('modifyStartDate').getValue(),
					'pageVO.endDateStr' :Ext.fly('modifyEndDate').getValue(),
	            	'pageVO.supGroupIdList' : modifyGroupList
	            },
	            success:function(form,action){
	            	infoModifyForm.getForm().reset();
	                infoModifyWin.hide();
	                queryFn();
	            }
	        });
	         
		}
//    	else {
//    		gsl.ErrorAlert('请检查数据格式');	
//    	}
	}
	
	//*****************************修改***************************
    
    
    //界面布局
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items:[queryForm, gridPanel]
	});
});