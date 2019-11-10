/*******************************************************************************
 * Time: 2010.02.23 * Author: 桑 海超 * E-mail: sanghaichao@ustcsoft.com *
 ******************************************************************************/

Ext.BLANK_IMAGE_URL = './comm/ext/resources/images/default/s.gif';
Ext.MAX_SEARCH_NUM = 8000;
Ext.Ajax.timeout=3000000;

// 隔行变色
if (Ext.grid.GridPanel) {
	Ext.apply(Ext.grid.GridPanel.prototype, {
		stripeRows : true
	});
}

// 标题与内容间隔
if (Ext.layout.FormLayout) {
	Ext.apply(Ext.layout.FormLayout.prototype, {
		labelSeparator : '：'
	});
}

// label对齐方式
if (Ext.FormPanel) {
	Ext.apply(Ext.FormPanel.prototype, {
		labelAlign : 'right'
	});
}

// 屏蔽退格键、F5键、Ctrl+R
document.onkeydown = function(e) {
	var event = window.event || e;
	var eType = document.activeElement.type;
	if ((eType == "text" || eType == "textarea" || eType == "password")
			&& !document.activeElement.readOnly) {
		if ((event.keyCode == 116) || // 屏蔽 F5 刷新键
				(event.ctrlKey && event.keyCode == 82))// Ctrl + R
		{
			event.keyCode = 0;
			event.returnValue = false;
		}
	} else {
		if ((event.keyCode == 8) || // 屏蔽退格删除键
				(event.keyCode == 116) || // 屏蔽 F5 刷新键
				(event.ctrlKey && event.keyCode == 82)) { // Ctrl + R
			event.keyCode = 0;
			event.returnValue = false;
		}
	}
};

// if(Ext.Panel){
// if(Ext.FormPanel.prototype.layout=='form'){
// Ext.apply(Ext.Panel.prototype, {
// border : false
// });
// }
// }

Ext.onReady(function() {
	// 显示提示信息
		Ext.QuickTips.init();

	});

// add by zyc 2010/03/16 start
// 重写可以输入的combobox的失去焦点操作,错误的值也保持,不用前一次正确的值覆盖.
Ext.override(Ext.form.ComboBox, {
	beforeBlur : function() {
		// 获取comboboc输入值
		var val = this.getRawValue();
		// 查找指定的输入值是否存在
		var rec = this.findRecord(this.displayField, val);
		// 不存在,且为强制性输入的
		if (!rec && this.forceSelection) {
			if (val.length > 0 && val != this.emptyText) {
				// 不对的如果有之前输入正确的值,就用之前输入正确的值覆盖错误的值
				// this.el.dom.value = Ext.isDefined(this.lastSelectionText) ?
				// this.lastSelectionText : '';
				// 不对的就清空
				this.el.dom.value = '';
				this.applyEmptyText();
			} else {
				this.clearValue();
			}
		} else {
			if (rec) {
				val = rec.get(this.valueField || this.displayField);
			}
			this.setValue(val);
		}
	}
});
// add by zyc 2010/03/16 end
// --------------------Grid大小自适应--------------------------
var Autofit_Grids;
var Autofit_WOffset;
var Autofit_HOffset;
var Autofit_TabPanels;
Ext.HywlAutofitGrid = function(config) {
	Autofit_Grids = config.grids;
	Autofit_WOffset = config.wOffset;
	Autofit_HOffset = config.hOffset;
	setgridwh();
};

Ext.HywlAutofitTabPanel = function(config) {
	Autofit_TabPanels = config.tabPanels;
	if (Autofit_TabPanels == undefined || Autofit_TabPanels == ''
			|| Autofit_TabPanels.length == 0)
		return;
	for ( var i = 0; i < Autofit_TabPanels.length; i++) {
		Autofit_TabPanels[i].setWidth(Ext.getBody().getWidth() - 30);
		Autofit_TabPanels[i].setHeight(parent.document.body.scrollHeight - 90);
	}
};

function setgridwh() {
	if (Ext.isEmpty(Autofit_Grids) || Autofit_Grids.length == 0)
		return;
	if (Ext.isEmpty(Autofit_WOffset))
		Autofit_WOffset = 30;
	if (Ext.isEmpty(Autofit_HOffset))
		Autofit_HOffset = 140;
	for ( var i = 0; i < Autofit_Grids.length; i++) {
		Autofit_Grids[i].setWidth(Ext.getBody().getWidth() - Autofit_WOffset);
		Autofit_Grids[i].setHeight(parent.document.body.scrollHeight
				- Autofit_HOffset);
	}
};

Ext.EventManager.onWindowResize(function(width, height) {
	if (Ext.isEmpty(Autofit_Grids) || Autofit_Grids.length == 0)
		return;
	for ( var i = 0; i < Autofit_Grids.length; i++) {
		Autofit_Grids[i].setWidth(width - Autofit_WOffset);
		Autofit_Grids[i].setHeight(height - Autofit_HOffset);
	}
	if (Autofit_TabPanels == undefined || Autofit_TabPanels == ''
			|| Autofit_TabPanels.length == 0)
		return;
	for ( var i = 0; i < Autofit_TabPanels.length; i++) {
		Autofit_TabPanels[i].setWidth(width - 30);
		Autofit_TabPanels[i].setHeight(height - 90);
	}
});

/**
 * 提示
 */
if (Ext.form.BasicForm) {
	Ext.apply(Ext.form.BasicForm.prototype, {
		waitTitle : '提示'
	});
}
/** ************************GSL封装控件******************* */
gsl = {
	version : 1.0
};
Ext.ns('gsl');

/**
 * @class gsl.FileUploadField
 * @extends Ext.form.TextField
 * Creates a file upload field.
 * @xtype fileuploadfield
 */
gsl.FileUploadField = Ext.extend(Ext.form.TextField,  {
    /**
     * @cfg {String} buttonText The button text to display on the upload button (defaults to
     * 'Browse...').  Note that if you supply a value for {@link #buttonCfg}, the buttonCfg.text
     * value will be used instead if available.
     */
//    buttonText: 'Browse...',
    buttonText: '浏览...',
    emptyText: '选择文件......',
    /**
     * @cfg {Boolean} buttonOnly True to display the file upload field as a button with no visible
     * text field (defaults to false).  If true, all inherited TextField members will still be available.
     */
    buttonOnly: false,
    /**
     * @cfg {Number} buttonOffset The number of pixels of space reserved between the button and the text field
     * (defaults to 3).  Note that this only applies if {@link #buttonOnly} = false.
     */
    buttonOffset: 3,
    /**
     * @cfg {Object} buttonCfg A standard {@link Ext.Button} config object.
     */

    // private
    readOnly: true,

    /**
     * @hide
     * @method autoSize
     */
    autoSize: Ext.emptyFn,

    // private
    initComponent: function(){
        gsl.FileUploadField.superclass.initComponent.call(this);

        this.addEvents(
            /**
             * @event fileselected
             * Fires when the underlying file input field's value has changed from the user
             * selecting a new file from the system file selection dialog.
             * @param {gsl.FileUploadField} this
             * @param {String} value The file value returned by the underlying file input field
             */
            'fileselected'
        );
    },

    // private
    onRender : function(ct, position){
        gsl.FileUploadField.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');
        this.createFileInput();

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    },
    
    bindListeners: function(){
        this.fileInput.on({
            scope: this,
            mouseenter: function() {
                this.button.addClass(['x-btn-over','x-btn-focus']);
            },
            mouseleave: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click']);
            },
            mousedown: function(){
                this.button.addClass('x-btn-click');
            },
            mouseup: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click']);
            },
            change: function(){
                if (navigator.userAgent.indexOf("MSIE")!=-1) {
                    var v = this.fileInput.dom.value;
                    this.setValue(v);
                    this.fireEvent('fileselected', this, v);
                }else if(navigator.userAgent.indexOf("Firefox")!=-1 || navigator.userAgent.indexOf("Mozilla")!=-1){
                     try {  
                            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");   
                           } catch (e) { 
                               alert('Unable to access local files due to browser security settings. To overcome this, follow these steps: (1) Enter "about:config" in the URL field; (2) Right click and select New->Boolean; (3) Enter "signed.applets.codebase_principal_support" (without the quotes) as a new preference name; (4) Click OK and try loading the file again.');   
                            return; 
                           }
                           var fileName=this.fileInput.dom.value; 
                           this.setValue(fileName);
                           this.fireEvent('fileselected', this, v);
                } else {
                    var v = this.fileInput.dom.value;
                    this.setValue(v);
                    this.fireEvent('fileselected', this, v);
                }
            }
        }); 
    },
    
    createFileInput : function() {
        
        this.fileInput = this.wrap.createChild({
            id: this.getFileInputId(),
            name: this.name||this.getId(),
            cls: 'x-form-file',
            tag: 'input',
            type: 'file',
            size: 1
        });
    },
    
    reset : function(){
        this.fileInput.remove();
        this.createFileInput();
        this.bindListeners();
        gsl.FileUploadField.superclass.reset.call(this);
    },

    // private
    getFileInputId: function(){
        return this.id + '-file';
    },

    // private
    onResize : function(w, h){
        gsl.FileUploadField.superclass.onResize.call(this, w, h);

        this.wrap.setWidth(w);

        if(!this.buttonOnly){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },

    // private
    onDestroy: function(){
        gsl.FileUploadField.superclass.onDestroy.call(this);
        Ext.destroy(this.fileInput, this.button, this.wrap);
    },
    
    onDisable: function(){
        gsl.FileUploadField.superclass.onDisable.call(this);
        this.doDisable(true);
    },
    
    onEnable: function(){
        gsl.FileUploadField.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    // private
    doDisable: function(disabled){
        this.fileInput.dom.disabled = disabled;
        this.button.setDisabled(disabled);
    },


    // private
    preFocus : Ext.emptyFn,

    // private
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});

Ext.reg('fileuploadfield', gsl.FileUploadField);

// backwards compat
Ext.form.FileUploadField = gsl.FileUploadField;

// 打印报表
gsl.PrintRpt = function() {
	// var url =
	// String.format('gsl/preview?__report=reports/{1}&__format=pdf&rptParams={2}',contextPath,o.rptName,Ext.encode(o.params));
	var url = '../../gsl/preview?__report=reports/myreport.rptdesign&__format=html';
	var winParams = 'height=600, width=600, top=50, left=50, toolbar=no, menubar=no, location=no, status=no';
	window.open(url, '报表', winParams);
};
/**
 * addTab
 */
gsl.addTab = function(opts) {
	parent.addNewTab(opts.title, opts.url, opts.refresh, opts.destoryFn);
};

gsl.closeTab = function(opts) {
	parent.removeTab(opts.title);
};

/**
 * 检查
 * @param {} opts
 */
gsl.tabpanelIsExist = function(opts){
	if(!parent.tabpanelIsExist){
		return false;
	}
	return parent.tabpanelIsExist(opts);
};

/**
 * TextField
 */
gsl.TextField = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	// var opts = Ext.apply({
	// },opts);
	gsl.TextField.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.TextField, Ext.form.TextField, {});
Ext.reg('gsltextfield', gsl.TextField);

/**
 * TextArea
 * 
 * @param {}
 *            opts
 */
gsl.TextArea = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	gsl.TextArea.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.TextArea, Ext.form.TextArea, {
	onRender : function(ct, position) {
		gsl.TextArea.superclass.onRender.call(this, ct, position);
		this.el.dom.qtip = String.format('请输入{0}字以内。', this.maxLength);
	}
});
Ext.reg('gsltextarea', gsl.TextArea);

/**
// * TimeField
// */
//gsl.TimeField = function(opts) {
//    if (opts.allowBlank == false) {
//        opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
//    }
//    gsl.TimeField.superclass.constructor.call(this, opts);
//};
//Ext.extend(gsl.TimeField, Ext.form.TimeField, {
//	reset : function() {
//		Ext.form.TimeField.superclass.reset.call(this);
//		this.setMinValue('');
//		this.setMaxValue('');
//	}
//});
//Ext.reg('gsltimefield', gsl.TimeField);

////重写时间控件的reest方法，清空minValue和maxValue
//Ext.override(Ext.form.TimeField, {
//	reset : function() {
//	Ext.form.TimeField.superclass.reset.call(this);
//	this.setMinValue('');
//	this.setMaxValue('');
//}
//});
/**
 * 时间控件TimeField
 */
gsl.TimeField = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	var opts = Ext.apply( {
		format : 'H:i',
		altFormats : 'H:i:s'
//		,readOnly : false
	}, opts);
	
	gsl.TimeField.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.TimeField, Ext.form.TimeField, {
	reset : function() {
		Ext.form.TimeField.superclass.reset.call(this);
//		this.setMinValue('');
//		this.setMaxValue('');
	}
});
Ext.reg('gsltimefield', gsl.TimeField);


/**
 * ComboBox
 */
gsl.ComboBox = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	opts = Ext.apply( {
		valueField : 'code',
		displayField : 'name',
		blankText : '请选择...',
		editable : false,
		emptyText : '请选择...'
	}, opts);
	gsl.ComboBox.superclass.constructor.call(this, opts);
};
Ext
		.extend(
				gsl.ComboBox,
				Ext.form.ComboBox,
				{
					initComponent : function() {
						if (!this.tpl) {
							this.tpl = '<tpl for="."><div class="x-combo-list-item" style="height:20px;">{' + this.displayField + '}</div></tpl>';
							// this.tpl = new Ext.XTemplate(
							// '<tpl for="."><div class="x-combo-list-item"
							// style="height:20px;">{',
							// this.displayField, ':this.blank}</div></tpl>', {
							// blank : function(value) {
							// return value === '' ? '&nbsp' : value;
							// }
							// });
						}
						gsl.ComboBox.superclass.initComponent.call(this);
					}

					,
					setValue : function(v) {
						// 如果远程数据还没有加载,在设值之前先加载一次
					if (this.mode == 'remote' && this.store.getCount() == 0) {
						this.store.on("load", function() {
							gsl.ComboBox.superclass.setValue.call(this, v);
						}, this);
						// this.doQuery(this.allQuery, true);
						if (this.triggerAction == 'all') {
							this.doQuery(this.allQuery, true);
						} else {
							this.doQuery(this.getRawValue());
						}
					} else {
						gsl.ComboBox.superclass.setValue.call(this, v);
					}
				}
				});

/**
 * 基础数据ComboBox
 * 
 * @class gsl.BaseDataComboBox
 * @extends gsl.ComboBox
 */
gsl.BaseDataComboBox = Ext.extend(gsl.ComboBox, {

	/**
	 * 基础数据
	 * 
	 * @type Array
	 */
	baseData : []
	/**
	 * 有无空行
	 * 
	 * @type Boolean
	 */
	,
	addBlank : false,
	mode : 'local',
	triggerAction : 'all',
	initComponent : function() {
		gsl.BaseDataComboBox.superclass.initComponent.call(this);
		if (!this.store) {

			this.store = new Ext.data.SimpleStore( {
				fields : [ 'code', 'name' ],
				data : this.baseData
			});
			if (this.addBlank)
				this.store.insert(0, new Ext.data.Record( [ '', '' ]));
		}
	}
});

/**
 * 检索用ComboBox
 * 
 * @class gsl.QueryComboBox
 * @extends gsl.ComboBox
 */
gsl.QueryComboBox = function(opts) {
	opts = Ext.apply( {
		queryParam : 'param',
		minChars : 1,
		blankText : '请选择...',
		editable : true,
		emptyText : '请至少输1位...'
	}, opts);
	gsl.QueryComboBox.superclass.constructor.call(this, opts);
};

Ext.extend(gsl.QueryComboBox, gsl.ComboBox, {
// findRecord: function(prop, value) {
		// return this.store.findRecord(function(r) {
		// return (r.data[prop] == value);
		// });
		// }
		});

/**
 * getLCmbNmByCd
 * 
 * 根据Value，取得LocalComboBox的DisplayText
 */
gsl.getLCmbNmByCd = function(cmbData, code) {
	try {
		for ( var i = 0; i < cmbData.length; i++) {
			if (cmbData[i][0] == code)
				return cmbData[i][1];
		}
	} catch (err) {
		return '';
	}
};

/**
 * gsl.request
 */
gsl.request = function(opts) {
	Ext.Ajax.request( {
		url : opts.url,
		success : function(response, options) {
			if (opts.success)
				opts.success.call(this, response, options);
		},
		failure : function(response, options) {
			if (opts.failure)
				opts.failure.call(this, response, options);
		},
		params : opts.params
	});
};

gsl.RequestAction = function(opts) {
	var actionNm = l_msg_action_name_long;
	if (!Ext.isEmpty(opts.actionNm))
		actionNm = opts.actionNm;
	Ext.MessageBox.confirm(l_msg_tip, String.format(l_msg_confirm, actionNm),
			function(btnId) {
				if (btnId != 'yes')
					return;
				Ext.Ajax.request( {
					url : opts.url,
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							if (opts.success)
								opts.success.call(this, response, options);
							gsl.TipShow( {
								type : 'info',
								text : String.format(l_msg_execute_success,
										actionNm)
							});
							// 业务上执行失败
						} else {
							if (result.type == 'timeout') {
								gsl.handleTimeout();
							} else {
								var msg = result.msg ? result.msg : String
										.format(l_msg_execute_fail, actionNm);
								gsl.ErrorAlert(msg, function() {
									if (opts.failure)
										opts.failure.call(this, response,
												options);
								});
							}
						}
					},
					// 响应超时
					failure : function(response, options) {
						gsl.ErrorAlert(String.format(l_msg_execute_fail,
								actionNm), function() {
							if (opts.failure)
								opts.failure.call(this, response, options);
						});
					},
					params : opts.params
				});
			});
};

gsl.RequestWaitAction = function(opts) {
	var actionNm = l_msg_action_name_long;
	var waitMsg = '正在执行...';
	if (!Ext.isEmpty(opts.actionNm)){
		actionNm = opts.actionNm;
	}
	if(!Ext.isEmpty(opts.waitMsg)){
		waitMsg = opts.waitMsg;
	}
	var confirmMsg = '';
	if(opts.confirmMsg && !Ext.isEmpty(opts.confirmMsg)){
		confirmMsg = opts.confirmMsg;
	}else{
		confirmMsg = String.format(l_msg_confirm, actionNm);
	}
	Ext.MessageBox.confirm(l_msg_tip, confirmMsg,
			function(btnId) {
				if (btnId != 'yes')
					return;
					
				Ext.MessageBox.wait(waitMsg, l_msg_tip);
				Ext.Ajax.request( {
					url : opts.url,
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							if (opts.success)
								opts.success.call(this, response, options);
							gsl.TipShow( {
								type : 'info',
								text : String.format(l_msg_execute_success,
										actionNm)
							});
							if (Ext.MessageBox.isVisible()) {
						        Ext.MessageBox.hide();
						    }
							
							// 业务上执行失败
						} else {
							if (result.type == 'timeout') {
								gsl.handleTimeout();
							} else {
								var msg = result.msg ? result.msg : String.format(l_msg_execute_fail, actionNm);
								gsl.ErrorAlert(msg, function() {
									if (opts.failure)
										opts.failure.call(this, response,
												options);
								});
							}
						}
					},
					// 响应超时
					failure : function(response, options) {
						gsl.ErrorAlert(String.format(l_msg_execute_fail,
								actionNm), function() {
							if (opts.failure)
								opts.failure.call(this, response, options);
						});
					},
					params : opts.params
				});
			});
};

gsl.JsonStoreNew = function(opts) {
	var opts = Ext.apply( {
		root : 'items',
		listeners: {
            'load': function() {
				if (this.getTotalCount() > Ext.MAX_SEARCH_NUM) {
					this.removeAll();
					Ext.Msg.show({
					   title:'警告',
					   msg: '检索件数超过最大值！',
					   width:160,
					   buttons: Ext.Msg.OK
					});
				}
            }
          },
		totalProperty : 'totalCount'
	}, opts);
	gsl.JsonStoreNew.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonStoreNew, Ext.data.JsonStore, {});


/**
 * 多用户类型出货仓库
 */
gsl.JsonShipDepotloadCmbStore = function(opts) {
	
	if (Ext.isEmpty(opts.addBlank))
		opts.addBlank = false;

	var opts = Ext.apply( {
		url : 'comm-cmbByFactory.action',
		fields : [ 'code', 'name' ],
		baseParams : {
			'type' : opts.type,
			'addBlank' : opts.addBlank
		}
	}, opts);
	if (opts.param)
		opts.baseParams.param = opts.param;
	gsl.JsonShipDepotloadCmbStore.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonShipDepotloadCmbStore, Ext.data.JsonStore, {});

/**
 * 多工厂卸货口
 */
gsl.JsonMpUnloadCmbStore = function(opts) {
	
	if (Ext.isEmpty(opts.addBlank))
		opts.addBlank = false;

	var opts = Ext.apply( {
		url : 'comm-cmbMpUnload.action',
		fields : [ 'code', 'name' ],
		baseParams : {
			'type' : opts.type,
			'addBlank' : opts.addBlank
		}
	}, opts);
	if (opts.param)
		opts.baseParams.param = opts.param;
	gsl.JsonMpUnloadCmbStore.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonMpUnloadCmbStore, Ext.data.JsonStore, {});


/**
 * JsonCmbStore
 */
gsl.JsonCmbStore = function(opts) {
	if (Ext.isEmpty(opts.addBlank))
		opts.addBlank = false;

	var opts = Ext.apply( {
		url : 'comm-cmb.action',
		fields : [ 'code', 'name' ],
		baseParams : {
			'type' : opts.type,
			'addBlank' : opts.addBlank
		}
	}, opts);
	if (opts.param)
		opts.baseParams.param = opts.param;
	gsl.JsonCmbStore.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonCmbStore, Ext.data.JsonStore, {
// findRecord:function(prop, value){
		// var fn, scope;
		// if (typeof prop != 'function'){
		// fn = function(r){
		// alert(value.trim());
		// return (r.data[prop].trim() == value.trim());
		// };
		// scope = this;
		// }
		// else{
		// fn = prop;
		// scope = value;
		// }
		// var record;
		// if(this.getCount() > 0){
		// this.each(function(r){
		// if(fn.call(scope, r)){
		// record = r;
		// return false;
		// }
		// });
		// }
		// return record;
		// }

		});

/**
 * NumberField
 */
gsl.NumberField = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	 var opts = Ext.apply({
	     
         decimalPrecision :0,   
         allowNegative :false,   
         allowDecimals :false  
         // allowBlank : false   
         ,   
         cls :'numReadOnly'  
         // private   
         ,   
         FormatComma :true  
         // private   
         ,   
         initEvents : function() {   
             Ext.form.NumberField.superclass.initEvents   
                     .call(this);   
             var allowed = this.baseChars + '';   
             if (this.allowDecimals) {   
                 allowed += this.decimalSeparator;   
             }   
             if (this.FormatComma) {   
                 allowed += ",";   
             }   
             if (this.allowNegative) {   
                 allowed += "-";   
             }   
             this.stripCharsRe = new RegExp(   
                     '[^' + allowed + ']', 'gi');   
             var keyPress = function(e) {   
                 var k = e.getKey();   
                 if (!Ext.isIE   
                         && (e.isSpecialKey()   
                                 || k == e.BACKSPACE || k == e.DELETE)) {   
                     return;   
                 }   
                 var c = e.getCharCode();   
                 if (allowed.indexOf(String   
                         .fromCharCode(c)) === -1) {   
                     e.stopEvent();   
                 }   
             };   
             this.el.on("keypress", keyPress, this);   
         },   
         // private   
         validateValue : function(value) {   
             if (!Ext.form.NumberField.superclass.validateValue   
                     .call(this, value)) {   
                 return false;   
             }   
             if (value.length < 1) { // if it's blank   
                 // and textfield   
                 // didn't flag   
                 // it then it's   
                 // valid   
                 return true;   
             }   
             if (this.FormatComma) {   
                 value = this.removeCommas(String(value));   
             }   
             value = String(value).replace(   
                     this.decimalSeparator, ".");   
             if (isNaN(value)) {   
                 this.markInvalid(String.format(   
                         this.nanText, value));   
                 return false;   
             }   
             var num = this.parseValue(value);   
             if (num < this.minValue) {   
                 this.markInvalid(String   
                         .format(this.minText,   
                                 this.minValue));   
                 return false;   
             }   
             if (num > this.maxValue) {   
                 this.markInvalid(String   
                         .format(this.maxText,   
                                 this.maxValue));   
                 return false;   
             }   
             return true;   
         },   
         fixPrecision : function(value) {   
             var nan = isNaN(value);   
             if (!this.allowDecimals   
                     || this.decimalPrecision == -1  
                     || nan || !value) {   
                 return nan ? '' : value;   
             }   
             return parseFloat(parseFloat(value)   
                     .toFixed(this.decimalPrecision));   
         },   
 
         setValue : function(v) {   
             v = typeof v == 'number' ? v : (String(   
                     this.removeCommas(v)).replace(   
                     this.decimalSeparator, ".")   
                     );   
             v = isNaN(v) ? '' : String(v).replace(   
                     ".", this.decimalSeparator);   
             if (String(v).length > 0)   
                 v = parseFloat(v).toFixed(   
                         this.decimalPrecision);   
             // if(this.FormatComma)   
             // v=this.formatCommaStyle(v);   
             Ext.form.NumberField.superclass.setValue   
                     .call(this, v);   
             if (this.FormatComma   
                     && String(v).length > 0) {   
                 v = this.addCommas(v);   
                 Ext.form.NumberField.superclass.setRawValue   
                         .call(this, v);   
             }   
         },   
         parseValue : function(value) {   
             if (this.FormatComma)   
                 value = this.removeCommas(String(value));   
             value = parseFloat(String(value)   
                     .replace(this.decimalSeparator,   
                             "."));   
 
             return isNaN(value) ? '' : value;   
         },   
         beforeBlur : function() {   
             var v = this.parseValue(this  
                     .getRawValue());   
 
             if (String(v).trim().length > 0) {   
                 this.setValue(this.fixPrecision(v));   
 
             }   
         }   
 
         ,   
         addCommas : function(nStr) {   
 
             nStr += '';   
             if (nStr.length == 0)   
                 return '';   
             x = nStr.split('.');   
             x1 = x[0];   
             x2 = x.length > 1 ? '.' + x[1] : '';   
             var rgx = /(\d+)(\d{3})/;   
             while (rgx.test(x1)) {   
                 x1 = x1.replace(rgx,   
                         '$1' + ',' + '$2');   
             }   
             return x1 + x2;   
 
         },   
         removeCommas : function(nStr) {   
 
             nStr = nStr + '';   
             var r = /(\,)/;   
             while (r.test(nStr)) {   
                 nStr = nStr.replace(r, '');   
             }   
             return nStr;   
         }   
     
	 },opts);
	gsl.NumberField.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.NumberField, Ext.form.NumberField, {});
Ext.reg('gslnumberfield', gsl.NumberField);

/****NumberField 格式化********************************************************/
//if (Ext.form.NumberField) {   
//    Ext.apply(   
//        Ext.form.NumberField.prototype,   
//        {   
//            decimalPrecision :0,   
//            allowNegative :false,   
//            allowDecimals :false  
//            // allowBlank : false   
//            ,   
//            cls :'numReadOnly'  
//            // private   
//            ,   
//            FormatComma :true  
//            // private   
//            ,   
//            initEvents : function() {   
//                Ext.form.NumberField.superclass.initEvents   
//                        .call(this);   
//                var allowed = this.baseChars + '';   
//                if (this.allowDecimals) {   
//                    allowed += this.decimalSeparator;   
//                }   
//                if (this.FormatComma) {   
//                    allowed += ",";   
//                }   
//                if (this.allowNegative) {   
//                    allowed += "-";   
//                }   
//                this.stripCharsRe = new RegExp(   
//                        '[^' + allowed + ']', 'gi');   
//                var keyPress = function(e) {   
//                    var k = e.getKey();   
//                    if (!Ext.isIE   
//                            && (e.isSpecialKey()   
//                                    || k == e.BACKSPACE || k == e.DELETE)) {   
//                        return;   
//                    }   
//                    var c = e.getCharCode();   
//                    if (allowed.indexOf(String   
//                            .fromCharCode(c)) === -1) {   
//                        e.stopEvent();   
//                    }   
//                };   
//                this.el.on("keypress", keyPress, this);   
//            },   
//            // private   
//            validateValue : function(value) {   
//                if (!Ext.form.NumberField.superclass.validateValue   
//                        .call(this, value)) {   
//                    return false;   
//                }   
//                if (value.length < 1) { // if it's blank   
//                    // and textfield   
//                    // didn't flag   
//                    // it then it's   
//                    // valid   
//                    return true;   
//                }   
//                if (this.FormatComma) {   
//                    value = this.removeCommas(String(value));   
//                }   
//                value = String(value).replace(   
//                        this.decimalSeparator, ".");   
//                if (isNaN(value)) {   
//                    this.markInvalid(String.format(   
//                            this.nanText, value));   
//                    return false;   
//                }   
//                var num = this.parseValue(value);   
//                if (num < this.minValue) {   
//                    this.markInvalid(String   
//                            .format(this.minText,   
//                                    this.minValue));   
//                    return false;   
//                }   
//                if (num > this.maxValue) {   
//                    this.markInvalid(String   
//                            .format(this.maxText,   
//                                    this.maxValue));   
//                    return false;   
//                }   
//                return true;   
//            },   
//            fixPrecision : function(value) {   
//                var nan = isNaN(value);   
//                if (!this.allowDecimals   
//                        || this.decimalPrecision == -1  
//                        || nan || !value) {   
//                    return nan ? '' : value;   
//                }   
//                return parseFloat(parseFloat(value)   
//                        .toFixed(this.decimalPrecision));   
//            },   
//    
//            setValue : function(v) {   
//                v = typeof v == 'number' ? v : (String(   
//                        this.removeCommas(v)).replace(   
//                        this.decimalSeparator, ".")   
//                        );   
//                v = isNaN(v) ? '' : String(v).replace(   
//                        ".", this.decimalSeparator);   
//                if (String(v).length > 0)   
//                    v = parseFloat(v).toFixed(   
//                            this.decimalPrecision);   
//                // if(this.FormatComma)   
//                // v=this.formatCommaStyle(v);   
//                Ext.form.NumberField.superclass.setValue   
//                        .call(this, v);   
//                if (this.FormatComma   
//                        && String(v).length > 0) {   
//                    v = this.addCommas(v);   
//                    Ext.form.NumberField.superclass.setRawValue   
//                            .call(this, v);   
//                }   
//            },   
//            parseValue : function(value) {   
//                if (this.FormatComma)   
//                    value = this.removeCommas(String(value));   
//                value = parseFloat(String(value)   
//                        .replace(this.decimalSeparator,   
//                                "."));   
//    
//                return isNaN(value) ? '' : value;   
//            },   
//            beforeBlur : function() {   
//                var v = this.parseValue(this  
//                        .getRawValue());   
//    
//                if (String(v).trim().length > 0) {   
//                    this.setValue(this.fixPrecision(v));   
//    
//                }   
//            }   
//    
//            ,   
//            addCommas : function(nStr) {   
//    
//                nStr += '';   
//                if (nStr.length == 0)   
//                    return '';   
//                x = nStr.split('.');   
//                x1 = x[0];   
//                x2 = x.length > 1 ? '.' + x[1] : '';   
//                var rgx = /(\d+)(\d{3})/;   
//                while (rgx.test(x1)) {   
//                    x1 = x1.replace(rgx,   
//                            '$1' + ',' + '$2');   
//                }   
//                return x1 + x2;   
//    
//            },   
//            removeCommas : function(nStr) {   
//    
//                nStr = nStr + '';   
//                var r = /(\,)/;   
//                while (r.test(nStr)) {   
//                    nStr = nStr.replace(r, '');   
//                }   
//                return nStr;   
//            }   
//        });   
//}  


/**
 * JsonStore
 */
gsl.JsonStore = function(opts) {
	var opts = Ext.apply( {
		root : 'items',
		totalProperty : 'totalCount',
		listeners : {
			loadexception : function(s, o, response, error) {
				var result = Ext.decode(response.responseText);
				gsl.ErrorAlert(result.msg);
			}
		}
	}, opts);
	gsl.JsonStore.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonStore, Ext.data.JsonStore, {});

gsl.JsonNoPgStore = function(opts) {
	var opts = Ext.apply( {
		listeners : {
			loadexception : function(s, o, response, error) {
				var result = Ext.decode(response.responseText);
				gsl.ErrorAlert(result.msg);
				var errorObj = Ext.getCmp(result.focusId);
				if (errorObj != null) {
					errorObj.markInvalid();
				}
			}
		}
	}, opts);
	gsl.JsonNoPgStore.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.JsonNoPgStore, Ext.data.JsonStore, {});

/**
 * PagingToolbar
 */
gsl.PagingToolbar = function(opts) {
	var opts = Ext.apply( {
		pageSize : pageSize,
		displayInfo : true
	}, opts);
	gsl.PagingToolbar.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.PagingToolbar, Ext.PagingToolbar, {});

/**
 * Toolbar
 */
gsl.Toolbar = function(opts) {
	var auth = opts.auth || '';
	var tools = [];
	var configTools = opts.tools || [];
	if(0 < configTools.length){
		for(var i = 0; i < configTools.length; i ++){
			if(hasAuth(auth, configTools[i].tagValue) ){
				tools.push(configTools[i]);
			}
		}
	}
	gsl.Toolbar.superclass.constructor.call(this, {
		items : tools
	});
};
Ext.extend(gsl.Toolbar, Ext.Toolbar, {});

/**
 * AuthButton 根据权限,决定是否显示
 */
gsl.AuthButton = function(opts) {
	auth = '';
	opts.cls = 'x-hide-display';
	if (opts.auth) {
		switch (opts.authType) {
		case 'save':
			auth = '10';
			break;
		default:
			break;
		}
		if (hasAuth(opts.auth, auth)) {
			opts.cls = '';
		}
	}
	gsl.AuthButton.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.AuthButton, Ext.Button, {});
Ext.reg('gslauthbutton', gsl.AuthButton);

/**
 * Window
 */
gsl.Window = function(opts) {
	var opts = Ext.apply( {
		resizable : false,
		shadow : true,
		layout : 'fit',
		bodyStyle : 'padding:5px;',
		closeAction : 'hide',
		modal : true,
		plain : true
	}, opts);
	gsl.Window.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.Window, Ext.Window, {});

/**
 * FormPanel
 */
gsl.FormPanel = function(opts) {
	var opts = Ext.apply( {
		frame : true,
		border : false
	}, opts);
	if (Ext.isEmpty(opts.title)) {
		switch (opts.iconCls) {
		case 'titQuery':
			opts.title = "查询条件";
			break;
		case 'titList':
			break;
		case 'titEntry':
			opts.title = "录入内容";
			break;
		case 'save':
			opts.title = "保存";
			break;
		case 'titImport':
			break;
		default:
			break;
		}
	}
	gsl.FormPanel.superclass.constructor.call(this, opts);
};

Ext.extend(gsl.FormPanel, Ext.FormPanel, {
	load : function(options) {
		options = Ext.apply( {
			waitMsg : '正在加载数据，请稍后...'
		}, options);
		this.form.load(options);
	},
	submit : function(options) {
		if (this.form.isValid()) {
			Ext.MessageBox.confirm("提示", "您确定要保存吗？", function(btnId) {
				if (btnId == 'yes') {
					opts = Ext.apply( {
						waitMsg : '数据保存中...'
					}, options);
					opts.success = function(form, action, o) {
						if (options.success) {
							options.success.call(this, form, action, o);
						}
						gsl.TipShow( {
							type : 'info',
							text : '信息已被保存。'
						});
					};
					opts.failure = function(form, action, o) {

						gsl.ErrorAlert(action.result == undefined
								|| action.result.msg == undefined
								|| Ext.isEmpty(action.result.msg) ? "信息保存失败。"
								: action.result.msg, function() {
							if (action.result != 'undefined'
									&& !Ext.isEmpty(action.result)
									&& action.result.focusId != 'undefined'
									&& !Ext.isEmpty(action.result.focusId)) {
								var errorObj = Ext
										.getCmp(action.result.focusId);
								if (errorObj != null) {
									errorObj.markInvalid(action.result.msg);
								}
							}
							if (options.failure) {
								options.failure.call(this, form, action, o);
							}
						});
					};
					this.form.submit(opts);
				}
			}, this);
		}
	}
});

/**
 * 弹出信息(alert)
 */
gsl.MsgAlert = function(opts) {
	var opts = Ext.apply( {
		title : "提示",
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.INFO
	}, opts);
	Ext.MessageBox.show(opts);
};
gsl.InfoAlert = function(msg, fn) {
	gsl.MsgAlert( {
		msg : msg,
		fn : fn,
		icon : Ext.MessageBox.INFO
	});
};
gsl.WarnAlert = function(msg, fn) {
	gsl.MsgAlert( {
		msg : msg,
		fn : fn,
		icon : Ext.MessageBox.WARNING,
		title : '警告'
	});
};
gsl.ErrorAlert = function(msg, fn) {
	gsl.MsgAlert( {
		msg : msg,
		fn : fn,
		icon : Ext.MessageBox.ERROR,
		title : '错误'
	});
};

/**
 * TipShow
 */
gsl.TipShow = function(config) {
	// 取得信息种类
	var type = "";
	if (config.type == 'info')
		type = "提示<br/>";
	if (config.type == 'warn')
		type = "警告<br/>";
	if (config.type == 'error')
		type = "错误<br/>";
	// 取得信息内容
	var text = "";
	if (!Ext.isEmpty(config.id))
		text = config.id;
	else if (!Ext.isEmpty(config.text))
		text = config.text;
	gsl.SlideTip(type, text);
};

gsl.SlideTip = function(title, format) {
	var id = (new Date().getTime().toString(36));
	function createBox(t, s) {
		return [
				'<div class="msg" style="display:none;z-index:1;top:0;left:0;position:absolute;width:250px" id="' + id + '">',
				'<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
				'<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>',
				t,
				'</h3>',
				s,
				'</div></div></div>',
				'<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
				'</div>' ].join('');
	}
	var s = String.format.apply(String, Array.prototype.slice
			.call(arguments, 1));
	var m = Ext.DomHelper.append(document.body, {
		html : createBox(title, s)
	}, true);
	Ext.get(id).slideIn('t', {
		easing : 'easeIn',
		duration : 3
	}).pause(3).ghost("t", {
		easing : 'easeOut',
		duration : 3,
		remove : true
	});
};
/**
 * GridPanel
 */
gsl.GridPanel = function(opts) {
	var opts = Ext.apply( {
		labelWidth : 80,
		loadMask : true,
		enableColumnHide : false,
		enableColumnMove : false
	}, opts);

	/**
	 * 统一为列追加排序功能,选择列,行号列,操作列除外
	 */
	var cols = opts.cm || opts.columns;
	Ext.each(cols.config, function(col) {
		if (col instanceof Ext.grid.RowNumberer
				|| col instanceof Ext.grid.CheckboxSelectionModel
				|| col instanceof gsl.RowActions) {
			return;
		}
		Ext.apply(col, {
			sortable : true
		});
	});

	gsl.GridPanel.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.GridPanel, Ext.grid.GridPanel, {});

/**
 * EditorGridPanel
 */
gsl.EditorGridPanel = function(opts) {
	var opts = Ext.apply( {
		labelWidth : 80,
		loadMask : true,
		enableColumnHide : false,
		enableColumnMove : false
	}, opts);

	/**
	 * 统一为列追加排序功能,选择列,行号列,操作列除外
	 */
	var cols = opts.cm || opts.columns;
	Ext.each(cols.config, function(col) {
		if (col instanceof Ext.grid.RowNumberer
				|| col instanceof Ext.grid.CheckboxSelectionModel
				|| col instanceof gsl.RowActions) {
			return;
		}
		Ext.apply(col, {
			sortable : true
		});
	});

	gsl.EditorGridPanel.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.EditorGridPanel, Ext.grid.EditorGridPanel, {});

//*******************************************************
//方法名称:trimSpace
//处理内容:Trim字符串
//参数 :sValue 字符串
//返回值 :Trim后的字符串
//*******************************************************
function trimSpace(sValue) {
	if ((sValue == null) || (sValue == "")) {
		return "";
	}
	var len1 = 0;
	var len2 = 1;
	while (len1 != len2) {
		len1 = sValue.length;
		sValue = sValue.replace(/(^\s*)|(\s*$)/g, "");
		sValue = sValue.replace(/(^\u3000*)|(\u3000*$)/g, "");
		len2 = sValue.length;
	}
	return sValue;
}

//*****************************************************
//方法名称:isNullOrEmpty
//处理内容:Null或者空判断
//参数 :strSrc 文字列
//返回值 :True - 检查OK False - 检查失敗
//*******************************************************
function isNullOrEmpty(strSrc) {
	if ((strSrc == null) || (strSrc == "") || (strSrc == "null")) {
		return true;
	} else {
		return false;
	}
}

//*******************************************************
//方法名称:filterHtml
//处理内容:html字符的过滤处理
//参数 :strSrc 字符串
//返回值 :处理后的字符串
//*******************************************************
function filterHtml(strSrc) {
	var str = trimSpace(strSrc);
	var ch;
	var strResult = "";
	if (isNullOrEmpty(str)) {
		return strResult;
	}
	for (i = 0; i < str.length; i++) {
		ch = str.charAt(i);
		switch (ch) {
			case '<' :
				strResult = strResult.concat("&lt;");
				break;
			case '>' :
				strResult = strResult.concat("&gt;");
				break;
			case '"' :
				strResult = strResult.concat("&quot;");
				break;
			case '&' :
				strResult = strResult.concat("&amp;");
				break;
			case '\t' :
				break;
			default :
				strResult = strResult.concat(ch);
				break;
		}
	}
	return strResult;
}

//*******************************************************
//方法名称:filterAddCommas
//处理内容:向数据添加comma
//参数 : 数字字符串
//返回值 :处理后的字符串
//*******************************************************
function filterAddCommas(nStr) {
    if(nStr){
    
        nStr += '';   
        if (nStr == 0)   
            return '';   
        x = nStr.split('.');   
        x1 = x[0];   
        x2 = x.length > 1 ? '.' + x[1] : '';   
        var rgx = /(\d+)(\d{3})/;   
        while (rgx.test(x1)) {   
            x1 = x1.replace(rgx,   
                    '$1' + ',' + '$2');   
        }   
        return x1 + x2;   
    } else {
        return "0";
    }
}

function filterAddCommas2(nStr) {
    if(nStr){
    
        nStr += '';   
        x = nStr.split('.');   
        x1 = x[0];   
        x2 = x.length > 1 ? '.' + x[1] : '';   
        var rgx = /(\d+)(\d{3})/;   
        while (rgx.test(x1)) {   
            x1 = x1.replace(rgx,   
                    '$1' + ',' + '$2');   
        }   
        return x1 + x2;   
    } else {
        return "0";
    }
}

/**
 * RowNumberer
 */
gsl.RowNumberer = function(opts) {
	var opts = Ext.apply( {
		header : "No.",
		width : 28
	}, opts);
	gsl.RowNumberer.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.RowNumberer, Ext.grid.RowNumberer, {});

/**
 * LabelField
 */
gsl.LabelField = Ext
		.extend(
				Ext.form.Field,
				{
					format : "Y-m-d",
					altMinFormats : "Y-m-dTH:i",
					altFormats : "Y-m-dTH:i:s",
					type : '',
					defaultAutoCreate : {
						tag : "span",
						style : "line-height:1.8em;border-bottom:#666666  solid 1px;word-wrap:break-word;word-break:break-all;"
					},
					initComponent : function() {
						Ext.form.TextField.superclass.initComponent.call(this);
					},

					// private
					initEvents : function() {
						gsl.LabelField.superclass.initEvents.call(this);
					},

					processValue : function(value) {
						if (this.stripCharsRe) {
							var newValue = value.replace(this.stripCharsRe, '');
							if (newValue !== value) {
								this.setRawValue(newValue);
								return newValue;
							}
						}
						return value;
					},

					filterValidation : function(e) {
						if (!e.isNavKeyPress()) {
							this.validationTask.delay(this.validationDelay);
						}
					},

					reset : function() {
						gsl.LabelField.superclass.reset.call(this);
						this.applyEmptyText();
					},

					applyEmptyText : function() {
						if (this.rendered && this.emptyText
								&& this.getRawValue().length < 1
								&& !this.hasFocus) {
							this.setRawValue(this.emptyText);
							this.el.addClass(this.emptyClass);
						}
					},

					setValue : function(v) {
						if (this.rendered) {
							if (this.type == "Date" || this.type == "DateMin"
									|| this.type == "DateTime")
								v = this.formatDate(Date.parseDate(v,
										this.altFormats));
							if (Ext.isGecko)
								this.el.dom.textContent = (v === null
										|| v === undefined ? '' : v);
							else
								this.el.dom.innerText = (v === null
										|| v === undefined ? '' : v);
						}
						// if(this.emptyText && this.el && !Ext.isEmpty(v)){
						// this.el.removeClass(this.emptyClass);
						// }
						// Ext.form.TextField.superclass.setValue.apply(this,
						// arguments);
						// this.applyEmptyText();
					},
					getValue : function() {
						if (this.rendered) {
							if (Ext.isGecko)
								return this.el.dom.textContent;
							else
								return this.el.dom.innerText;
						}
					},
					selectText : function(start, end) {
					},

					formatDate : function(date) {
						var f;
						if (this.type == "Date")
							f = this.format;
						else if (this.type == "DateMin")
							f = "Y-m-d H:i";
						else
							f = "Y-m-dTH:i:s";
						return Ext.isDate(date) ? date.dateFormat(f) : date;
					}
				});
Ext.reg('labelfield', gsl.LabelField);

/**
 * DateField
 */
gsl.DateField = function(opts) {
	if (opts.allowBlank == false) {
		// opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
		opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	var opts = Ext.apply( {
		format : 'Y-m-d',
		altFormats : 'Y-m-dTH:i:s',
		readOnly : true
	}, opts);
	gsl.DateField.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.DateField, Ext.form.DateField, {});
Ext.reg('gsldatefield', gsl.DateField);



//added by wangzhibin

//end addition




/**
 * DateColumn
 */
gsl.DateColumn = function(opts) {
	if (typeof opts.dateFormat == 'undefined')
		opts.dateFormat = "Y-m-d";
	return Ext.apply(opts, {
		type : "date",
		renderer : new Ext.util.Format.dateRenderer(opts.dateFormat)
	});
};

/**
 * LCmbColumn
 */
gsl.LCmbColumn = function(opts) {
	var lcmbRenderer = function(data) {

		return function(v) {
			return gsl.getLCmbNmByCd(data, v);
		};
	};
	return Ext.apply(opts, {
		renderer : new lcmbRenderer(opts.cmbData)
	});
};

gsl.StoreDateField = function(opts) {
	var options = {};
	if (typeof opts == 'string')
		options.name = opts;
	else
		options = opts;
	return Ext.apply(options, {
		type : 'date',
		dateFormat : "Y-m-dTH:i:s"
	});
};

/**
 * FromToDateField
 */
gsl.FromToDateField = Ext
		.extend(
				Ext.form.Field,
				{
				    fromTabIndex : "",
				    toTabIndex : "",

					fromMinDate : false,
					/**
					 * @cfg {String} FromDateformat 开始时间的格式
					 */
					fromDateformat : "Y-m-d",
					/**
					 * @cfg {String} fromDateTimeName 开始日期控件的id属性
					 */
					fromDateId : "",
					/**
					 * @cfg {String} fromDateName 开始日期控件的name属性
					 */
					fromDateName : "",
					fromDataIndex : "",
					fromAllowBlank : true,
					
					fromDateValue : null,
					
					toAllowBlank : true,
					/**
					 * {Element} fromDateEl 开始时间控件
					 */

					/**
					 * @cfg {String} toDateformat 结束时间的格式
					 */
					toDateformat : "Y-m-d",
					/**
					 * @cfg {String} toDateId 开始日期控件的id属性
					 */
					toDateId : "",
					/**
					 * @cfg {String} toDateName 开始日期控件的name属性
					 */
					toDateName : "",
					toDataIndex : "",
					
					toDateValue : null,
					
					/**
					 * {Element} toDateEl 结束时间控件
					 */
					// private
					defaultAutoCreate : {
						tag : "div",
						style : "padding-left:3px!important",
						autocomplete : "off"
					},

					initComponent : function() {
						if (!this.fromAllowBlank || !this.toAllowBlank) {
							this.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
						}
						gsl.FromToDateField.superclass.initComponent.call(this);
					},
					// private
					onRender : function(ct, position) {
						gsl.FromToDateField.superclass.onRender.call(this, ct,
								position);
						var fromDateId = this.fromDateId || this.getId();
						var toDateId = this.toDateId || this.getId();
						this.fromDateEl = new Ext.form.DateField( {
							id : fromDateId,
							name : this.fromDateName || this.fromDateId,
							dataIndex : this.fromDataIndex,
							hiddenLabel : true,
							format : this.fromDateformat || 'Y-m-d',
							anchor : '99%',
							endDateField : toDateId,
							tabIndex : this.fromTabIndex,
							vtype : 'daterange',
							allowBlank : this.fromAllowBlank
						});
						if (this.fromMinDate) {
							this.fromDateEl.setMinValue((new Date())
									.format('yyyy-mm-dd'));
						}
						
						if (Ext.isIE6 || Ext.isIE7) {
							this.fromDateEl.render(this.el);
							Ext.fly(this.fromDateEl.el.dom.parentNode)
									.addClass("x-column");
						} else {
							this.fromDateEl.render(Ext.DomHelper.append(
									this.el, {
										tag : 'div',
										cls : 'x-column'
									}, true));
						}

						this.el.createChild( {
							tag : 'div',
							html : '~',
							cls : 'x-column'
						});

						this.toDateEl = new Ext.form.DateField( {
							id : toDateId,
							name : this.toDateName || this.toDateId,
							dataIndex : this.toDataIndex,
							hiddenLabel : true,
							format : this.fromDateformat || 'Y-m-d',
							anchor : '99%',
							startDateField : fromDateId,
							tabIndex : this.toTabIndex,
							vtype : 'daterange',
							allowBlank : this.toAllowBlank

						});
						if (Ext.isIE6 || Ext.isIE7) {
							this.toDateEl.render(this.el);
						} else {
							this.toDateEl.render(Ext.DomHelper.append(this.el,
									{
										tag : 'div',
										cls : 'x-column'
									}, true));
						}
						Ext.DomHelper.append(this.el, {
							tag : 'div',
							cls : 'x-clear'
						});
						if(this.fromDateValue){
							this.fromDateEl.setValue(this.fromDateValue);
						}
						if(this.toDateValue){
							this.toDateEl.setValue(this.toDateValue);
						}
					},
					reset : function() {
						this.fromDateEl.reset();
						this.toDateEl.reset();
					},
					validate : function() {
						var fv = !this.fromDateEl.validate();
						var tv = !this.toDateEl.validate();
						if (fv || tv) {
							return false;
						}
						return true;
					}
				});
Ext.reg('fromtodatefield', gsl.FromToDateField);

//yanqiang 新增
/**
 * FromToTimeField
 */
gsl.FromToTimeField = Ext
		.extend(
				Ext.form.Field,
				{
					increment : 10,
					fromMinTime : false,
					/**
					 * @cfg {String} FromTimeformat 开始时间的格式
					 */
					fromTimeformat : "H:i",
					/**
					 * @cfg {String} fromTimeId 开始日期控件的id属性
					 */
					fromTimeId : "",
					/**
					 * @cfg {String} fromTimeName 开始日期控件的name属性
					 */
					fromTimeName : "",
					fromAllowBlank : true,
					toAllowBlank : true,
					/**
					 * {Element} fromTimeEl 开始时间控件
					 */

					/**
					 * @cfg {String} toTimeformat 结束时间的格式
					 */
					toTimeformat : "H:i",
					/**
					 * @cfg {String} toTimeId 开始日期控件的id属性
					 */
					toTimeId : "",
					/**
					 * @cfg {String} toTimeName 开始日期控件的name属性
					 */
					toTimeName : "",
					/**
					 * {Element} toTimeEl 结束时间控件
					 */
					// private
					defaultAutoCreate : {
						tag : "div",
						style : "padding-left:3px!important",
						autocomplete : "off"
					},

					initComponent : function() {
						if (!this.fromAllowBlank || !this.toAllowBlank) {
							this.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
						}
						gsl.FromToTimeField.superclass.initComponent.call(this);
					},
					// private
					onRender : function(ct, position) {
						gsl.FromToTimeField.superclass.onRender.call(this, ct,
								position);
						var fromTimeId = this.fromTimeId || this.getId();
						var toTimeId = this.toTimeId || this.getId();
						this.fromTimeEl = new Ext.form.TimeField( {
							id : fromTimeId,
							increment : this.increment || 15,
							name : this.fromTimeName || this.fromTimeId,
							hiddenLabel : true,
							format : this.fromTimeformat || 'H:i',
							anchor : '99%',
							endTimeField : toTimeId,
							//vtype : 'daterange',
							allowBlank : this.fromAllowBlank
							,width : 80
						});
						if (this.fromMinTime) {
							this.fromTimeEl.setMinValue((new Time())
									.format('H:i'));
						}
						if (Ext.isIE6 || Ext.isIE7) {
							this.fromTimeEl.render(this.el);
							Ext.fly(this.fromTimeEl.el.dom.parentNode)
									.addClass("x-column");
						} else {
							this.fromTimeEl.render(Ext.DomHelper.append(
									this.el, {
										tag : 'div',
										cls : 'x-column'
									}, true));
						}

						this.el.createChild( {
							tag : 'div',
							html : '~',
							cls : 'x-column'
						});

						this.toTimeEl = new Ext.form.TimeField( {
							id : toTimeId,
							name : this.toTimeName || this.toTimeId,
							increment : this.increment || 15,
							hiddenLabel : true,
							format : this.fromTimeformat || 'H:i',
							anchor : '99%',
							startTimeField : fromTimeId,
							vtype : 'daterange',
							allowBlank : this.toAllowBlank
							,width : 80
						});
						if (Ext.isIE6 || Ext.isIE7) {
							this.toTimeEl.render(this.el);
						} else {
							this.toTimeEl.render(Ext.DomHelper.append(this.el,
									{
										tag : 'div',
										cls : 'x-column'
									}, true));
						}
						Ext.DomHelper.append(this.el, {
							tag : 'div',
							cls : 'x-clear'
						});
					},
					reset : function() {
						this.fromTimeEl.reset();
						this.toTimeEl.reset();
					},
					validate : function() {
						var fv = !this.fromTimeEl.validate();
						var tv = !this.toTimeEl.validate();
						if (fv || tv) {
							return false;
						}
						return true;
					}
				});
Ext.reg('fromtotimefield', gsl.FromToTimeField);


Ext.override(Ext.form.BasicForm, {
	findField : function(id) {
		var field = this.items.get(id);
		if (!(field && typeof field == 'object')) {
			this.items
					.each(function(f) {

						if (f.isFormField
								&& (f.dataIndex == id || f.id == id || f
										.getName() == id)) {
							field = f;
							return false;
						} else if (f.isFormField
								&& (f instanceof gsl.FromToDateField)) {
							if (f.toDateEl.dataIndex == id
									|| f.toDateEl.id == id
									|| f.toDateEl.getName() == id) {
								field = f;
								return false;
							} else if (f.fromDateEl.dataIndex == id
									|| f.fromDateEl.id == id
									|| f.fromDateEl.getName() == id) {
								field = f;
								return false;
							}
						}
					});
		}
		return field;
	}
});
/** *********同步请求的CheckboxGroup************** */
gsl.CheckboxGroup = function(opts){
	// 必须输入时，在冒号后追加红色星号
	if(opts.allowBlank==false){
        opts.labelSeparator =Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	//opts.url
	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
	// 防止刷新无效
	opts.url += '&temptime='+ (new Date().getTime().toString(36));
    conn.open("GET", opts.url, false);
    conn.send();
    var response = Ext.decode(conn.responseText);
    var items = [];
    Ext.each(response,function(item){
    	items.push({boxLabel:item.name,inputValue:item.code});
    });
	if(opts.allowBlank==false){
		opts.labelSeparator =Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
	}
	 
	var chkPanel = new Ext.Panel({id:"chkpanelId" + opts.id,items:[{}],layout:'form'});
//	
//	var items = [];
//	chkPanel.reset=function(){
//		this.items.each(function(c){
//            if(c.reset){
//                c.reset();
//            }
//        }, this);
//	};
	
//	chkPanel.prototype.reset=function(){
//		alert('bbb');
//		items.each(function(item){item.checked = false;});
//	};
//	
//	var chkStore = new Ext.data.JsonStore({
//		url : 'comm-chk.action?type=role',
//		fields : ['id', 'name', 'checked']
//	});
//	chkStore.load();
//	chkStore.on("load",createChkGroup);
	
//  var conn = Ext.lib.Ajax.getConnectionObject().conn;
//  conn.open("GET", 'comm-cmb.action?type=role', false);
//  conn.send();
//  var response = Ext.decode(conn.responseText);
//  var items = [];
//  Ext.each(response,function(item){
//      items.push({boxLabel:item.name,inputValue:item.code});
//  });
    
    opts.items=items;
    // 角色
    return new Ext.form.CheckboxGroup(opts);
};

/**
 * TabCloseMenu
 */
gsl.TabCloseMenu = function() {
	var tabs, menu, ctxItem;
	this.init = function(tp) {
		tabs = tp;
		tabs.on('contextmenu', onContextMenu);
	};
	function onContextMenu(ts, item, e) {
		if (!menu) { // create context menu on first right click
			menu = new Ext.menu.Menu( [ {
				id : tabs.id + '-close',
				text : '关闭选项卡',
				handler : function() {
					tabs.remove(ctxItem);
				}
			}, {
				id : tabs.id + '-close-others',
				text : '关闭其他项卡',
				handler : function() {
					tabs.items.each(function(item) {
						if (item.closable && item != ctxItem) {
							tabs.remove(item);
						}
					});
				}
			} ]);
		}
		ctxItem = item;
		var items = menu.items;
		items.get(tabs.id + '-close').setDisabled(!item.closable);
		var disableOthers = true;

		tabs.items.each(function() {
			if (this != item && this.closable) {
				disableOthers = false;
				return false;
			}
		});
		items.get(tabs.id + '-close-others').setDisabled(disableOthers);
		menu.showAt(e.getPoint());
	}
};

gsl.RowActions2 = function(opts){
	var auth = opts.auth || '';
	var actions = opts.actionColumns || [];
	var rowConfig = {};
	if(0 < actions.length){
		rowConfig.auth = 'SP';
		for(var i = 0; i < actions.length; i ++){
			if(hasAuth(auth, actions[i].tagValue) ){
				rowConfig[actions[i].type] = actions[i].hander;
			}
		}
	}
	return new gsl.RowActions(rowConfig);
}

gsl.RowActions = function(config) {
	this.auth = '';
	Ext.apply(this, config);
	// call parent
	gsl.RowActions.superclass.constructor.call(this);
	// actions
	this.eas = [];
	this.eas['inspect'] = {
		cls : this.inspectCls,
		qtip : '查看',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['copyAdd'] = {
		cls : this.copyAddCls,
		qtip : '复制新增',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['sortplan'] = {
		cls : this.inspectCls,
		qtip : '估计材料',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['roleInspect'] = {
		cls : this.roleInspectCls,
		qtip : '权限配置',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['config'] = {
		cls : this.roleInspectCls,
		qtip : '配置',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['edit'] = {
		cls : this.editCls,
		qtip : '编辑',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['stockshiftdata'] = {
			cls : this.stockshiftdataCls,
			qtip : '查询推移数据',
			hide : '',
			text : '',
			align : 'right'
	};
	this.eas['stockshiftpic'] = {
			cls : this.stockshiftpicCls,
			qtip : '库存推移图',
			hide : '',
			text : '',
			align : 'right'
	};
	this.eas['maintain'] = {
		cls : this.maintainCls,
		qtip : '维护',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['remove'] = {
		cls : this.removeCls,
		qtip : '删除',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['cancel'] = {
		cls : this.removeCls,
		qtip : '取消',
		hide : '',
		text : '',
		align : 'right'
	};

	this.eas['print'] = {
		cls : this.printCls,
		qtip : '预览打印',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['printOrder'] = {
		cls : this.printOrderCls,
		qtip : '装箱单打印',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['resetPwd'] = {
		cls : this.resetPwdCls,
		qtip : '密码重置',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['usable'] = {
		cls : this.usableCls,
		qtip : '可用',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['disable'] = {
		cls : this.disableCls,
		qtip : '禁用',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['response'] = {
		cls : this.responseCls,
		qtip : '响应',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['active'] = {
		cls : this.activeCls,
		qtip : '激活',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['reActive'] = {
		cls : this.reActiveCls,
		qtip : '密码重置',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['printList'] = {
		cls : this.printCls,
		qtip : '订单打印',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['send'] = {
		cls : this.sendCls,
		qtip : '发货',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['receive'] = {
		cls : this.receiveCls,
		qtip : '收货',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['download'] = {
		cls : this.downloadCls,
		qtip : '下载附件',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['up'] = {
		cls : this.upCls,
		qtip : '上移',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['down'] = {
		cls : this.downCls,
		qtip : '下移',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['refuse'] = {
		cls : this.refuseCls,
		qtip : '拒绝',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['approve'] = {
		cls : this.approveCls,
		qtip : '认可',
		hide : '',
		text : '',
		align : 'right'
	};
	this.eas['publish']={cls:this.publishCls,qtip:'发布',hide:'',text:'',align:'right'};
	this.eas['checkBack']={cls:this.checkBackCls,qtip:'审核回退',hide:'',text:'',align:'right'};
	this.eas['passPrice']={cls:this.passPriceCls,qtip:'价格审核',hide:'',text:'',align:'right'};
	this.eas['printSettlement']={cls:this.printSettlementCls,qtip:'开票通知单打印',hide:'',text:'',align:'right'};
    this.eas['printSummarySettlement']={cls:this.printSummarySettlementCls,qtip:'开票通知单汇总打印',hide:'',text:'',align:'right'};
    this.eas['reject']={cls:this.rejectCls,qtip:'差异',hide:'',text:'',align:'right'};
	// param
	this.acs = {
		'inspect' : hasAuth(config.auth, '01') ? this.inspect : undefined,		
		'stockshiftpic' : hasAuth(config.auth, '02') ? this.stockshiftpic : undefined,
		'stockshiftdata' : hasAuth(config.auth, '03') ? this.stockshiftdata : undefined,	
		'copyAdd' : hasAuth(config.auth, '04') ? this.copyAdd : undefined,
		'sortplan' : hasAuth(config.auth, '05') ? this.sortplan : undefined,
		'roleInspect' : hasAuth(config.auth, '06') ? this.roleInspect: undefined,
		'config' : hasAuth(config.auth, '07') ? this.config : undefined,
		'edit' : hasAuth(config.auth, '08') ? this.edit : undefined,
		'maintain' : hasAuth(config.auth, '09') ? this.maintain : undefined,
		'remove' : hasAuth(config.auth, '10') ? this.remove : undefined,
		'print' : hasAuth(config.auth, '11') ? this.print : undefined,
		'printOrder' : hasAuth(config.auth, '12') ? this.printOrder : undefined,
		'resetPwd' : hasAuth(config.auth, '13') ? this.resetPwd : undefined,
		'usable' : hasAuth(config.auth, '14') ? this.usable : undefined,
		'disable' : hasAuth(config.auth, '15') ? this.disable : undefined,
		'response' : hasAuth(config.auth, '16') ? this.response : undefined,
		'receive' : hasAuth(config.auth, '17') ? this.receive : undefined,
		'active' : hasAuth(config.auth, '18') ? this.active : undefined,
		'reActive' : hasAuth(config.auth, '19') ? this.reActive : undefined,
		'printList' : hasAuth(config.auth, '20') ? this.printList : undefined,

		'send' : hasAuth(config.auth, '21') ? this.send: undefined,
		'download' : hasAuth(config.auth, '22') ? this.download : undefined,
		'up' : hasAuth(config.auth, '23') ? this.up : undefined,
		'down' : hasAuth(config.auth, '24') ? this.down : undefined,
		'approve' : hasAuth(config.auth, '25') ? this.approve : undefined,
		'cancel' : hasAuth(config.auth, '26') ? this.cancel : undefined,
		'refuse' : hasAuth(config.auth, '27') ? this.refuse : undefined,
		'publish':hasAuth(config.auth,'28')?this.publish:undefined,
		'checkBack':hasAuth(config.auth,'29')?this.checkBack:undefined,
		'passPrice':hasAuth(config.auth,'30')?this.passPrice:undefined,
		'printSettlement':hasAuth(config.auth,'31')?this.printSettlement:undefined,
    	'printSummarySettlement':hasAuth(config.auth,'32')?this.printSummarySettlement:undefined,
    	'reject':hasAuth(config.auth,'33')?this.reject:undefined
    		  
	};

};

// 判断是否拥有权限
var hasAuth = function(auth, func) {
	if (typeof auth == "undefined")
		return false;
	if (/sp/i.test(auth))
		return true;
	var reg = new RegExp("," + func + ",");
	if (auth.search(reg) != -1)
		return true;
	else
		return false;
};

Ext.extend(gsl.RowActions, Ext.util.Observable, {
	/**
	 * actions:[{iconCls:'',tooltip:'',callback(或者cb):function(){}}]
	 */
	actions : [],
	inspectCls : 'gridRowInspect',
	copyAddCls : 'copyAdd',
	roleInspectCls : 'gridRoleInspect',
	editCls : 'gridRowEdit',
	stockshiftdataCls : 'gridRowStockShiftData',
	stockshiftpicCls : 'gridRowStockShiftPic',
	maintainCls : 'gridRowMaintain',
	removeCls : 'gridRowDelete',
	resetPwdCls : 'gridRowResetPwd',
	usableCls : 'gridRowUsable',
	disableCls : 'gridRowDisable',
	responseCls : 'gridRowResponse',
	printCls : 'gridRowPrint',
	printOrderCls : 'gridRowOrderPrint',
	sendCls : 'gridRowSend',
	receiveCls : 'gridRowReceive',
	activeCls : 'gridActive',
	reActiveCls : 'gridReActive',
	downloadCls : 'gridDownload',
	upCls : 'gridRowUp',
	downCls : 'gridRowDown',
	refuseCls : 'gridRowRefuse',
	approveCls : 'gridRowApprove',
	publishCls:'gridRowPublish',
	checkBackCls:'gridRowReject',
	passPriceCls:'gridRowPassPrice',
	printSettlementCls:'gridRowPrintSettlement',
    printSummarySettlementCls:'gridRowPrintSummarySettlement',
    rejectCls:'gridRowReject',
	actionEvent : 'click',
	align : 'center',
	autoWidth : true,
	dataIndex : '',
	editable : false,
	header : '操作',
	isColumn : true,
	keepSelection : true,
	menuDisabled : true,
	sortable : false,
	auth : '',
	tplRow : '<div class="ux-row-action">' + '<tpl for="actions">'
			+ '<div class="ux-row-action-item {cls} <tpl if="text">'
			+ 'ux-row-action-text</tpl>" style="{hide}{style}" qtip="{qtip}">'
			+ '<tpl if="text"><span qtip="{qtip}">{text}</span></tpl></div>'
			+ '</tpl>' + '</div>'

	,
	hideMode : 'display',
	widthIntercept : 4,
	widthSlope : 21,
	init : function(grid) {
		this.grid = grid;

		// the actions column must have an id for Ext 3.x
		this.id = this.id || Ext.id();

		// for Ext 3.x compatibility
		var lookup = grid.getColumnModel().lookup;
		delete (lookup[undefined]);
		lookup[this.id] = this;

		if (!this.tpl) {
			this.tpl = this.processActions(this.actions);
		}
		// calculate width
		if (this.autoWidth) {
			var length = (this.acs.stockshiftpic ? 1 : 0) + (this.acs.stockshiftdata ? 1 : 0) + (this.acs.inspect ? 1 : 0) + (this.acs.copyAdd ? 1 : 0) 
					+ (this.acs.sortplan ? 1 : 0) +(this.acs.edit ? 1 : 0)
					+ (this.acs.remove ? 1 : 0) + (this.acs.resetPwd ? 1 : 0)
					+ (this.active ? 1 : 0) + (this.reActive ? 1 : 0)
					+ (this.up ? 1 : 0) + (this.down ? 1 : 0)
					+ (this.acs.config ? 1 : 0)
					+ (this.acs.roleInspect ? 1 : 0)
					+ (this.acs.disable ? 1 : 0) + (this.acs.usable ? 1 : 0)
					+ (this.acs.printList ? 1 : 0)
					+ (this.acs.send ? 1 : 0) + (this.acs.receive ? 1 : 0)
					+ (this.acs.cancel ? 1 : 0) + (this.acs.print ? 1 : 0)
					+ (this.acs.printOrder ? 1 : 0)
					+ (this.acs.maintain ? 1 : 0) + (this.acs.approve ? 1 : 0)
					+ (this.acs.refuse ? 1 : 0) 
					+ (this.acs.download ? 1 : 0)
					+ (this.acs.publish ? 1 : 0) + (this.acs.checkBack ? 1 : 0)
					+ (this.acs.passPrice ? 1 : 0) + (this.acs.reject ? 1 : 0)
					+ (this.acs.printSettlement?1:0) + (this.acs.printSummarySettlement?1:0)
					+ this.actions.length;

			this.width = this.widthSlope * length + this.widthIntercept;
			if (this.width < 40)
				this.width = 40;
			this.fixed = true;
		}
		// body click handler
		var view = grid.getView();
		var cfg = {
			scope : this
		};
		cfg[this.actionEvent] = this.onClick;
		grid.afterRender = grid.afterRender.createSequence(function() {
			view.mainBody.on(cfg);
			grid.on('destroy', this.purgeListeners, this);
		}, this);

		// setup renderer
		if (!this.renderer) {
			this.renderer = function(value, cell, record, row, col, store) {
				cell.css += (cell.css ? ' ' : '') + 'ux-row-action-cell';
				return this.tpl.apply(this.getData(value, cell, record, row,
						col, store));
			}.createDelegate(this);
		}

		// cancel click
		if (true === this.keepSelection) {
			grid.processEvent = grid.processEvent.createInterceptor(function(
					name, e) {
				if ('mousedown' === name) {
					return !this.getAction(e);
				}
			}, this);
		}

	},
	getData : function(value, cell, record, row, col, store) {
		return record.data || {};
	}

	,
	processActions : function(actions, template) {
		var acts = [];
		this.callbacks = this.callbacks || {};
		var acs = this.acs;
		for ( var ac in acs) {
			if (undefined == acs[ac])
				continue;
			var func = ('function' === typeof acs[ac]) ? acs[ac]
					: (acs[ac].cb || acs[ac].callback);
			var hide = Ext.util.Format.htmlEncode(acs[ac].hide || '');
			this.eas[ac].hide = hide ? '<tpl if="'
					+ hide
					+ '">'
					+ ('display' === this.hideMode ? 'display:none'
							: 'visibility:hidden') + ';</tpl>' : '';
			this.callbacks[this.eas[ac].cls] = func;
			acts.push(this.eas[ac]);
		}

		// actions loop
		Ext.each(actions, function(a, i) {
			// save callback
				if (a.iconCls && 'function' === typeof (a.callback || a.cb)) {
					this.callbacks = this.callbacks || {};
					this.callbacks[a.iconCls] = a.callback || a.cb;
				}

				// data for intermediate template
				var o = {
					cls : a.iconCls ? a.iconCls : '',
					qtip : a.tooltip || a.qtip ? a.tooltip || a.qtip : '',
					text : a.text ? a.text : '',
					hide : a.hide ? ('<tpl if="'
							+ Ext.util.Format.htmlEncode(hide)
							+ '">'
							+ ('display' === this.hideMode ? 'display:none'
									: 'visibility:hidden') + ';</tpl>') : '',
					align : a.align || 'right',
					style : a.style ? a.style : ''
				};
				acts.push(o);

			}, this); // eo actions loop

		var xt = new Ext.XTemplate(template || this.tplRow);
		return new Ext.XTemplate(xt.apply( {
			actions : acts
		}));

	} // eo function processActions
	// }}}
	,
	getAction : function(e) {
		var action = false;
		var t = e.getTarget('.ux-row-action-item');
		if (t) {
			action = t.className.replace(/ux-row-action-item /, '');
			if (action) {
				action = action.replace(/ ux-row-action-text/, '');
				action = action.trim();
			}
		}
		return action;
	} // eo function getAction

	/**
	 * Grid body actionEvent event handler
	 * 
	 * @private
	 */
	,
	onClick : function(e, target) {

		var view = this.grid.getView();

		// handle row action click
	var row = e.getTarget('.x-grid3-row');
	var col = view.findCellIndex(target.parentNode.parentNode);
	var action = this.getAction(e);
	if (false !== row && false !== col && false !== action) {
		var record = this.grid.store.getAt(row.rowIndex);

		// call callback if any
		if (this.callbacks && 'function' === typeof this.callbacks[action]) {
			this.callbacks[action]
					(record, this.grid, action, row.rowIndex, col);
		}
	}
}
});
gsl.RowAction = function(opts) {
	var actionNm = '该';
	if (opts.actionType == 'remove') {
		actionNm = '删除';
	} else if (opts.actionType == 'resetPwd') {
		actionNm = '密码重置';
	} else if (opts.actionType == 'usable') {
		actionNm = '可用';
	} else if (opts.actionType == 'disable') {
		actionNm = '禁用';
	} else if (opts.actionType == 'cancel') {
		actionNm = '取消';
	} else if (opts.actionType == 'save') {
		actionNm = '保存';
	}

	else if (opts.actionType == 'active') {
		actionNm = '激活';
	} else if (opts.actionType == 'receive') {
		actionNm = '收货';
	} else if (opts.actionType == 'send') {
		actionNm = '发货';
	}else if (opts.actionType == 'modify') {
		actionNm = '修改';
	}
	
	Ext.MessageBox.confirm("提示", String.format("您确定要{0}吗？", actionNm),
			function(btnId) {
				if (btnId != 'yes')
					return;
				Ext.Ajax.request( {
					url : opts.url,
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							if (opts.success)
								opts.success.call(this, response, options);
							gsl.TipShow( {
								type : 'info',
								text : String.format("{0}执行成功！", actionNm)
							});
							// 业务上执行失败
						} else {
							var msg = result.msg ? result.msg : String.format(
									"{0}执行失败！", actionNm);
							var errorObj = Ext.getCmp(result.focusId);
							if (errorObj != null) {
								errorObj.markInvalid();
							}
							gsl.ErrorAlert(msg, function() {
								if (opts.failure)
									opts.failure.call(this, response, options);
							});
						}
					},
					// 响应超时
					failure : function(response, options) {
						gsl.ErrorAlert(String.format("{0}执行失败！", actionNm),
								function() {
									if (opts.failure)
										opts.failure.call(this, response,
												options);
								});
					},
					params : opts.params
				});
			});
};
gsl.RowDelete = function(opts) {
	opts.actionType = 'remove';
	gsl.RowAction(opts);
};

/**
 * 根据页面ID,取得页面权限信息
 */
gsl.getPageAuth = function(pageId) {
	try {
		var tempAuth = parent.pagesAuth || pagesAuth;
		return tempAuth[pageId];
	} catch (err) {
		return '';
	}
};

/**
 * 
 * 由一个控件的收缩和展开，引起另一个控件高度的调整， 维持二者高度总和不变。
 * 
 * initiativeCtr 主动调整高度者 passiveCtr 被动影响者
 */
gsl.adjHeight = function(initiativeCtr, passiveCtr) {
    this.iCtr = initiativeCtr;
    this.pIctr = passiveCtr;
    var getOffset = function(panel) {
        this.offset = panel.getSize().height - panel.header.getHeight();
        this.iCtr.un('beforecollapse', getOffset, this);
    };
    this.iCtr.on('beforecollapse', getOffset, this);
    this.iCtr.on('collapse', function(panel) {
        this.pIctr.setHeight(this.pIctr.getSize().height + this.offset);
    }, this);
    this.iCtr.on('expand', function(panel) {
        this.pIctr.setHeight(this.pIctr.getSize().height - this.offset);
    }, this);
};

// 重写日期控件的reest方法，清空minValue和maxValue
Ext.override(Ext.form.DateField, {
	reset : function() {
		Ext.form.DateField.superclass.reset.call(this);
		this.setMinValue('');
		this.setMaxValue('');
	}
});


Ext.apply(Ext.form.VTypes, {
	// 日期范围
	daterange : function(val, field) {
		var date = field.parseDate(val);
		var dispUpd = function(picker) {
			var ad = picker.activeDate;
			picker.activeDate = null;
			picker.update(ad);
		};
		if (field.startDateField) {
			var sd = Ext.getCmp(field.startDateField);
			sd.maxValue = date;
			if (sd.menu && sd.menu.picker) {
				sd.menu.picker.maxDate = date;
				dispUpd(sd.menu.picker);
			}
		} else if (field.endDateField) {
			var ed = Ext.getCmp(field.endDateField);
			ed.minValue = date;
			if (ed.menu && ed.menu.picker) {
				ed.menu.picker.minDate = date;
				dispUpd(ed.menu.picker);
			}
		}
		return true;
	},
	// 日期+时间范围
	datetimerange : function(val, field) {
	    if(field.getValue() == '' ){
	        if (field.startDateTimeField) {
	            var sd = Ext.getCmp(field.startDateTimeField);
	            sd.maxValue = undefined;
	            if (sd.menu && sd.menu.picker) {
	                sd.menu.picker.maxDate =undefined;
	            }
	        } else if (field.endDateTimeField) {
	            var ed = Ext.getCmp(field.endDateTimeField);
	            ed.minValue = undefined;
	            if (ed.menu && ed.menu.picker) {
	                ed.menu.picker.minDate = undefined;
	            }
	        }
	    }else {
	    
            var date = field.parseDate(val,"Y-m-d H:i");
            if (field.startDateTimeField) {
                var sd = Ext.getCmp(field.startDateTimeField);
                sd.maxValue = date;
                if (sd.menu && sd.menu.picker) {
                    sd.menu.picker.maxDate = date;
                }
            } else if (field.endDateTimeField) {
                var ed = Ext.getCmp(field.endDateTimeField);
                ed.minValue = date;
                if (ed.menu && ed.menu.picker) {
                    ed.menu.picker.minDate = date;
                }
            }
	    }
        return true;
    },
	// 手机号码
	mobile : function(val) {
		return /^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/.test(val);
	},
	mobileMask : /[0-9]/,

	// 电话号码
	telephone : function(val) {
		return /^[0-9-]*$/.test(val);
	},
	telephoneMask : /[0-9-]/

});



/**********gsl.DatetimeField START**********
 * Includes : gsl.DatetimePicker
 *            gsl.DatetimeItem
 *            gsl.DatetimeMenu
 *            gsl.DatetimeField
 * Format   : Y-m-d H:i
 * Author   : wangzhibin
 * Date     : 2010-6-29
 * Use      : new gsl.DatetimeField({
 *                fieldLabel: 'datetime',
 *                name: 'datetime'
 *            })
 ******************************************/

/********** CONSTRUCTOR ******
 * Parameters: as per Ext.DatePicker
 ****/
gsl.DatetimePicker = function(config){
    /** Call superclass constructor **/
    gsl.DatetimePicker.superclass.constructor.call(this, config);
};

Ext.extend(gsl.DatetimePicker, Ext.DatePicker, {

    hourText:'', //'时',  
    miniteText:'', //'分',  
//    secondText: '', //'秒',  

    /**
    * Method Name: onRender
    * Description: as per Ext.DatePicker's onRender, except renders year in its own cell with arrow-changers in additional columns
    * Parameters: as per Ext.DatePicker's onRender
    * Returns: n/a
    * Throws: n/a
    */
    selectToday : function(){
        this.setValue(new Date().clearTime());
        var val1 = this.value;
        val1.setHours(this.theHours);
        val1.setMinutes(this.theMinutes);
//        val1.setSeconds(this.theSeconds);
        this.fireEvent("select", this, val1);
    },
    
    //确定所选;
    selectOK: function(){
        this.setValue(this.activeDate);
        var val1 = this.value;
        val1.setHours(this.theHours);
        val1.setMinutes(this.theMinutes);
//        val1.setSeconds(this.theSeconds);;
        this.fireEvent("select", this, this.activeDate);
    },
    
    //取消所选;
    selectCancel : function(){  
        this.fireEvent("clear", this, "");  
        
    },
    handleDateClick : function(e, t){
        e.stopEvent();
        if(t.dateValue && !Ext.fly(t.parentNode).hasClass("x-date-disabled")){
            this.setValue(new Date(t.dateValue));
            var val1 = this.value;
            val1.setHours(this.theHours);
            val1.setMinutes(this.theMinutes);
//            val1.setSeconds(this.theSeconds); 
            this.fireEvent("select", this, val1);
        }
    },
    onRender : function(container, position){
        var m = [
             '<table cellspacing="0" width="160px">',
                '<tr><td colspan="3"><table cellspacing="0" width="100%"><tr><td class="x-date-left"><a href="#" title="', this.prevText ,'">&#160;</a></td><td class="x-date-middle" align="center"></td><td class="x-date-right"><a href="#" title="', this.nextText ,'">&#160;</a></td></tr></table></td></tr>',
                '<tr><td colspan="3"><table class="x-date-inner" cellspacing="0"><thead><tr>'];
        var dn = this.dayNames;
        for(var i = 0; i < 7; i++){
            var d = this.startDay+i;
            if(d > 6){
                d = d-7;
            }
            m.push("<th><span>", dn[d].substr(0,1), "</span></th>");
        }
        m[m.length] = "</tr></thead><tbody><tr>";
        for(i = 0; i < 42; i++) {
            if(i % 7 === 0 && i !== 0){
                m[m.length] = "</tr><tr>";
            }
            m[m.length] = '<td><a href="#" hidefocus="on" class="x-date-date" tabIndex="1"><em><span></span></em></a></td>';
        }

         //代码很有意思，相当push(obj)了;  
         m[m.length] = '</tr></tbody></table></td></tr><tr><td class="minutecss"><table cellspacing="0" ><tr>';  
         m[m.length] = '<td class="y-hour-left"><a href="#" title="down"> </a></td><td class="y-hour-middle" align="center"></td><td class="y-hour-right"><a href="#" title="up"> </a></td>'
            + '<td class="hour-minute"><center><b>:</b></center></td>';           //添加:分隔;  
         m[m.length] = '<td class="y-minute-left"><a href="#" title="down"> </a></td><td class="y-minute-middle" align="center"></td><td class="y-minute-right"><a href="#" title="up"> </a></td>';
//            + '<td class="hour-minute"><center><b>:</b></center></td>';           //添加:分隔;  
//         m[m.length] = '<td class="y-second-left"><a href="#" title="down"> </a></td><td class="y-second-middle" align="center"></td><td class="y-second-right"><a href="#" title="up"> </a></td>';  
         m[m.length] = '</tr></table></td></tr><tr><td><table class="minutecss" width="100%"><tr>' +  
                 '<td class="x-date-today" align="center"></td>' +  
                 '<td class="x-date-btn-ok" align="center"></td>' +  
                 '<td class="x-date-btn-cancle" align="center"></td>' +  
                 '</tr></table></td></tr></table><div class="x-date-mp"></div>';  

        var el = document.createElement("div");
        el.className = "x-date-picker";
        el.innerHTML = m.join("");

        container.dom.insertBefore(el, position);

        this.el = Ext.get(el);
        this.eventEl = Ext.get(el.firstChild);

        new Ext.util.ClickRepeater(this.el.child("td.x-date-left a"), {
            handler: this.showPrevMonth,
            scope: this,
            preventDefault:true,
            stopDefault:true
        });

        new Ext.util.ClickRepeater(this.el.child("td.x-date-right a"), {
            handler: this.showNextMonth,
            scope: this,
            preventDefault:true,
            stopDefault:true
        });
        
        //时;
        new Ext.util.ClickRepeater(this.el.child("td.y-hour-left a"), {
            handler: function(){
                if(this.theHours>0){
                    this.theHours--;
                    this.theHours = this.theHours %24;
                    var txt = '';
                    if(this.theHours<10){
                        txt='0'+this.theHours;
                    }
                    else{
                        txt= this.theHours;
                    }
                    this.hourLabel.update(txt+this.hourText);
                    
                }
            }.createDelegate(this), 
            scope: this
        });
        new Ext.util.ClickRepeater(this.el.child("td.y-hour-right a"), {
            handler: function(){
                this.theHours++;
                this.theHours = this.theHours % 24;
                var txt = '';
                if(this.theHours<10){
                    txt='0'+this.theHours;
                }
                else{
                    txt= this.theHours;
                }
                this.hourLabel.update(txt+this.hourText);
            }.createDelegate(this), 
            scope: this
        });
        
        //分;
        new Ext.util.ClickRepeater(this.el.child("td.y-minute-left a"), {
            handler: function(){
                if(this.theMinutes>0){
                    this.theMinutes--;
                    this.theMinutes = this.theMinutes % 60;
                    var txt = '';
                    if(this.theMinutes<10){
                        txt='0'+this.theMinutes;
                    }
                    else{
                        txt= this.theMinutes;
                    }
                    this.minuteLabel.update(txt+this.miniteText);
                    
                }
            }.createDelegate(this), 
            scope: this
        });
        new Ext.util.ClickRepeater(this.el.child("td.y-minute-right a"), {
            handler: function(){
                this.theMinutes++;
                this.theMinutes = this.theMinutes % 60;
                var txt = '';
                if(this.theMinutes<10){
                    txt='0'+this.theMinutes;
                }
                else{
                    txt= this.theMinutes;
                }   
                this.minuteLabel.update(txt+this.miniteText);
            }.createDelegate(this), 
            scope: this
        });
        
//        //秒,向左;  
//        new Ext.util.ClickRepeater(this.el.child("td.y-second-left a"), {  
//            handler: function(){  
//                if(this.theSeconds>0){  
//                    this.theSeconds--;  
//                    this.theSeconds = this.theSeconds % 60;  
//                    var txt = '';  
//                    if(this.theSeconds<10){  
//                        txt='0'+this.theSeconds;  
//                    }  
//                    else{  
//                        txt= this.theSeconds;  
//                    }  
//                    this.secondLabel.update(txt+this.secondText);  
//                     
//                }  
//            }.createDelegate(this),  
//            scope: this  
//        });  
//        //秒,向右;  
//        new Ext.util.ClickRepeater(this.el.child("td.y-second-right a"), {  
//            handler: function(){  
//                this.theSeconds++;  
//                this.theSeconds = this.theSeconds % 60;  
//                var txt = '';  
//                if(this.theSeconds<10){  
//                    txt='0'+this.theSeconds;  
//                }  
//                else{  
//                    txt= this.theSeconds;  
//                }     
//                this.secondLabel.update(txt+this.secondText);  
//            }.createDelegate(this),  
//            scope: this  
//         });  
          
        this.eventEl.on("mousewheel", this.handleMouseWheel,  this);

        this.monthPicker = this.el.down('div.x-date-mp');
        this.monthPicker.enableDisplayMode('block');
        
        var kn = new Ext.KeyNav(this.eventEl, {
            "left" : function(e){
                e.ctrlKey ?
                    this.showPrevMonth() :
                    this.update(this.activeDate.add("d", -1));
            },

            "right" : function(e){
                e.ctrlKey ?
                    this.showNextMonth() :
                    this.update(this.activeDate.add("d", 1));
            },

            "up" : function(e){
                e.ctrlKey ?
                    this.showNextYear() :
                    this.update(this.activeDate.add("d", -7));
            },

            "down" : function(e){
                e.ctrlKey ?
                    this.showPrevYear() :
                    this.update(this.activeDate.add("d", 7));
            },

            "pageUp" : function(e){
                this.showNextMonth();
            },

            "pageDown" : function(e){
                this.showPrevMonth();
            },

            "enter" : function(e){
                e.stopPropagation();
                return true;
            },

            scope : this
        });

        this.eventEl.on("click", this.handleDateClick,  this, {delegate: "a.x-date-date"});

        this.eventEl.addKeyListener(Ext.EventObject.SPACE, this.selectToday,  this);

        this.el.unselectable();
        
        this.cells = this.el.select("table.x-date-inner tbody td");
        this.textNodes = this.el.query("table.x-date-inner tbody span");

        this.mbtn = new Ext.Button({
            text: "&#160;",
            tooltip: this.monthYearText,
            renderTo: this.el.child("td.x-date-middle", true)
        });

        this.mbtn.on('click', this.showMonthPicker, this);
        this.mbtn.el.child(this.mbtn.menuClassTarget).addClass("x-btn-with-menu");

        var dt1 = new Date();
        var txt = '';
        
        //时;
        this.hourLabel = this.el.child("td.y-hour-middle");
        this.theHours = dt1.getHours();
        if(this.theHours<10){
            txt='0'+this.theHours;
        }
        else{
            txt= this.theHours;
        }   
        this.hourLabel.update(txt+this.hourText);

        //分;
        this.minuteLabel = this.el.child("td.y-minute-middle");
        this.theMinutes = dt1.getMinutes();
        if(this.theMinutes<10){
            txt='0'+this.theMinutes;
        }
        else{
            txt= this.theMinutes;
        }   
        this.minuteLabel.update(txt+this.miniteText);
        
//        //秒;
//        this.secondLabel = this.el.child("td.y-second-middle");
//        this.theSeconds = dt1.getSeconds();
//        if(this.secondLabel<10){
//            txt='0'+this.secondLabel;
//        }
//        else{
//            txt= this.secondLabel;
//        }   
//        this.secondLabel.update(txt+this.secondText);

        var today = (new Date()).dateFormat(this.format);
        var todayBtn = new Ext.Button({ //今天;
            renderTo: this.el.child("td.x-date-today", true),
            text: String.format(this.todayText, today),
            tooltip: String.format(this.todayTip, today),
            handler: this.selectToday,
            scope: this
        });
        
        var okBtn = new Ext.Button({ //确定;  
            renderTo: this.el.child("td.x-date-btn-ok", true),  
            text: String.format(this.okText, today),  
            tooltip: String.format(this.okText, today),  
            handler: this.selectOK,  
            scope: this  
        });  
          
        var cancelBtn = new Ext.Button({ //取消;  
            renderTo: this.el.child("td.x-date-btn-cancle", true),  
            text: String.format(this.cancelText, today),  
            tooltip: String.format(this.cancelText, today),  
            handler: this.selectCancel,  
            scope: this  
        });
        
        
        if(Ext.isIE){
            this.el.repaint();
        }
        this.update(this.value);
    },

    /**
    * Method Name: update
    * Description: as per Ext.DatePicker's update, except updates year label in its own cell
    * Parameters: as per Ext.DatePicker's update
    * Returns: n/a
    * Throws: n/a
    */
    update : function(date, forceRefresh){
//        if(date == null){
//            date = new Date();
//        }
        var vd = this.activeDate;
        this.activeDate = date;
        if(vd && this.el){
            var t = date.getTime();
            if(vd.getMonth() == date.getMonth() && vd.getFullYear() == date.getFullYear()){
                this.cells.removeClass("x-date-selected");
                this.cells.each(function(c){
                   if(c.dom.firstChild.dateValue == t){
                       c.addClass("x-date-selected");
                       setTimeout(function(){
                            try{c.dom.firstChild.focus();}catch(e){}
                       }, 50);
                       return false;
                   }
                });
                return;
            }
        }
        var days = date.getDaysInMonth();
        var firstOfMonth = date.getFirstDateOfMonth();
        var startingPos = firstOfMonth.getDay()-this.startDay;

        if(startingPos <= this.startDay){
            startingPos += 7;
        }

        var pm = date.add("mo", -1);
        var prevStart = pm.getDaysInMonth()-startingPos;

        var cells = this.cells.elements;
        var textEls = this.textNodes;
        days += startingPos;

        // convert everything to numbers so it's fast
        var day = 86400000;
        var d = (new Date(pm.getFullYear(), pm.getMonth(), prevStart)).clearTime();
        var today = new Date().clearTime().getTime();
        var sel = date.clearTime().getTime();
        
        var min = this.minDate ? this.minDate : Number.NEGATIVE_INFINITY;
        var max = this.maxDate ? this.maxDate : Number.POSITIVE_INFINITY;
        var dmin = min;
        var dmax = max;
        if(min != Number.NEGATIVE_INFINITY){
            dmin = (new Date(min)).clearTime().getTime();
        }
        if(max != Number.POSITIVE_INFINITY){
            dmax = (new Date(max)).clearTime().getTime();
        }
//        var dmin = (new Date(this.minDate.getFullYear(), this.minDate.getMonth(), this.minDate.getDaysInMonth)).clearTime();
//        var dmax = (new Date(this.maxDate.getFullYear(), this.maxDate.getMonth(), this.maxDate.getDaysInMonth)).clearTime();
//        var min1 = this.minDate ? this.minDate.clearTime() : Number.NEGATIVE_INFINITY;
//        var max1 = this.maxDate ? this.maxDate.clearTime() : Number.POSITIVE_INFINITY;
        var ddMatch = this.disabledDatesRE;
        var ddText = this.disabledDatesText;
        var ddays = this.disabledDays ? this.disabledDays.join("") : false;
        var ddaysText = this.disabledDaysText;
        var format = this.format;

        var setCellClass = function(cal, cell){
            cell.title = "";
            var t = d.getTime();
            cell.firstChild.dateValue = t;
            if(t == today){
                cell.className += " x-date-today";
                cell.title = cal.todayText;
            }
            if(t == sel){
                cell.className += " x-date-selected";
                setTimeout(function(){
                    try{cell.firstChild.focus();}catch(e){}
                }, 50);
            }
            // disabling
//            date.setTime(date.getTime()-24*3600*1000);
            if(t < dmin) {
                cell.className = " x-date-disabled";
                cell.title = cal.minText;
                return;
            }
            if(t > dmax) {
                cell.className = " x-date-disabled";
                cell.title = cal.maxText;
                return;
            }
            if(ddays){
                if(ddays.indexOf(d.getDay()) != -1){
                    cell.title = ddaysText;
                    cell.className = " x-date-disabled";
                }
            }
            if(ddMatch && format){
                var fvalue = d.dateFormat(format);
                if(ddMatch.test(fvalue)){
                    cell.title = ddText.replace("%0", fvalue);
                    cell.className = " x-date-disabled";
                }
            }
        };

        var i = 0;
        for(; i < startingPos; i++) {
            textEls[i].innerHTML = (++prevStart);
            d.setDate(d.getDate()+1);
            cells[i].className = "x-date-prevday";
            setCellClass(this, cells[i]);
        }
        for(; i < days; i++){
            intDay = i - startingPos + 1;
            textEls[i].innerHTML = (intDay);
            d.setDate(d.getDate()+1);
            cells[i].className = "x-date-active";
            setCellClass(this, cells[i]);
        }
        var extraDays = 0;
        for(; i < 42; i++) {
             textEls[i].innerHTML = (++extraDays);
             d.setDate(d.getDate()+1);
             cells[i].className = "x-date-nextday";
             setCellClass(this, cells[i]);
        }

        this.mbtn.setText(this.monthNames[date.getMonth()] + " " + date.getFullYear());

        //时;
        if(this.theHours<10){
            txt='0'+this.theHours;
        }
        else{
            txt= this.theHours;
        }
        this.hourLabel.update(txt+this.hourText);

        //分;
        if(this.theMinutes<10){
            txt='0'+this.theMinutes;
        }
        else{
            txt= this.theMinutes;
        }   
        this.minuteLabel.update(txt+this.miniteText);
           
//        //秒;  
//        if(this.theSeconds<10){  
//            txt='0'+this.theSeconds;  
//        }  
//        else{  
//            txt= this.theSeconds;  
//        }     
//        this.secondLabel.update(txt+this.secondText);    

        if(!this.internalRender){
            var main = this.el.dom.firstChild;
            var w = main.offsetWidth;
            this.el.setWidth(w + this.el.getBorderWidth("lr"));
            Ext.fly(main).setWidth(w);
            this.internalRender = true;
            // opera does not respect the auto grow header center column
            // then, after it gets a width opera refuses to recalculate
            // without a second pass
            if(Ext.isOpera && !this.secondPass){
                main.rows[0].cells[1].style.width = (w - (main.rows[0].cells[0].offsetWidth+main.rows[0].cells[2].offsetWidth)) + "px";
                this.secondPass = true;
                this.update.defer(10, this, [date]);
            }
        }
    },
    
    /***** Public Instance Variables *****/
    
    /**
    * Variable Name: nextYearText, prevYearText
    * Description: Hover text for the previous year and next year arrow changers
    * Default: as shown
    * Type: string
    */
    nextYearText: 'Next Year (Control+Up)',
    prevYearText: 'Previous Year (Control+Down)'
});


/** Class Name: gsl.DatetimeItem
 * Inherits From: Ext.menu.Adapter
 * Contains: gsl.DatetimePicker
 * Purpose: Effectively overrides Ext.menu.DateItem so that it contains gsl.DatetimePicker instead of Ext.DatePicker
 * Note: ORIGINAL and NEW comments are used to denote what differs from Ext.menu.DateItem
 */
gsl.DatetimeItem = function(config){
    // ORIGINAL:
    //Ext.menu.DateItem.superclass.constructor.call(this, new Ext.DatePicker(config), config);
    // NEW:
    gsl.DatetimeItem.superclass.constructor.call(this, new gsl.DatetimePicker(config), config);
    // END NEW
    this.picker = this.component;
    this.addEvents({select: true});

    this.picker.on("render", function(picker){
        picker.getEl().swallowEvent("click");
        picker.container.addClass("x-menu-date-item");
    });

    this.picker.on("clear", this.onSelect, this);   //监听清除事件;  
    this.picker.on("select", this.onSelect, this);
};

Ext.extend(gsl.DatetimeItem, Ext.menu.Adapter, {
    onSelect : function(picker, date){
        this.fireEvent("select", this, date, picker);
        // ORIGINAL:
        //Ext.menu.DateItem.superclass.handleClick.call(this);
        // NEW:
        gsl.DatetimeItem.superclass.handleClick.call(this);
        // END NEW
    }
});


/** Class Name: gsl.DatetimeMenu
 * Inherits From: Ext.menu.Menu
 * Contains: gsl.DatetimeItem
 * Purpose: Effectively overrides Ext.menu.DateMenu so that it contains gsl.DatetimeItem instead of Ext.menu.DateItem
 * Note: ORIGINAL and NEW comments are used to denote what differs from Ext.menu.DateMenu
 */
gsl.DatetimeMenu = function(config){
    // ORIGINAL:
    //Ext.menu.DateMenu.superclass.constructor.call(this, config);
    //this.plain = true;
    //var di = new Ext.menu.DateItem(config);
    // NEW:
    gsl.DatetimeMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var di = new gsl.DatetimeItem(config);
    // END NEW
    this.add(di);
    this.picker = di.picker;
    this.relayEvents(di, ["select"]);
};
Ext.extend(gsl.DatetimeMenu, Ext.menu.Menu);

/** 
 * 扩展Ext.form.DateField,增加,时,分,秒; 
 */  

gsl.DatetimeField = function(opts) {
    if (opts.allowBlank == false) {
        // opts.fieldLabel = '<font color=red>*</font>'+opts.fieldLabel;
        opts.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
    }
    gsl.DatetimeField.superclass.constructor.call(this, opts);
};
Ext.extend(gsl.DatetimeField, Ext.form.DateField, {  
    initComponent: function (){  
        gsl.DatetimeField.superclass.initComponent.apply(this , arguments);  
        this.menu = new gsl.DatetimeMenu();  
    },  
    //覆盖其他父类方法     
    onRender: function (){    
        gsl.DatetimeField.superclass.onRender.apply(this , arguments);    
    },
    onTriggerClick : function() {
        gsl.DatetimeField.superclass.onTriggerClick.apply(this , arguments);
        var ad = this.menu.picker.activeDate;
        this.menu.picker.activeDate = null;
        this.menu.picker.update(ad);
    },
    format:'Y-m-d H:i',
    width : 130
});
Ext.reg('datetimefield', gsl.DatetimeField);

//gsl.DatetimeField = Ext.extend(Ext.form.DateField, {  
//    initComponent: function (){  
//        gsl.DatetimeField.superclass.initComponent.apply(this , arguments);  
//        this.menu = new gsl.DatetimeMenu();  
//    },  
//    //覆盖其他父类方法     
//    onRender: function (){    
//        gsl.DatetimeField.superclass.onRender.apply(this , arguments);    
//    },
//    onTriggerClick : function() {
//        gsl.DatetimeField.superclass.onTriggerClick.apply(this , arguments);
//        var ad = this.menu.picker.activeDate;
//        this.menu.picker.activeDate = null;
//        this.menu.picker.update(ad);
//    },
//    format:'Y-m-d H:i',
//    width : 130
//});
/**********gsl.DatetimeField END**********
 * Author   : wangzhibin
 * Date     : 2010-6-29
 * Use      : new gsl.DatetimeField({
 *                fieldLabel: 'datetime',
 *                name: 'datetime'
 *            })
 ******************************************/


/**********gsl.FromToDateTimeField START**********
 * Includes : gsl.DatetimeField
 * Format   : Y-m-d H:i
 * Author   : wangzhibin
 * Date     : 2010-6-29
 * Use      : new gsl.FromToDateTimeField({
 *                fieldLabel: 'fromToDateTime',
 *                fromDateTimeId : "dateTimeFrom",
 *                toDateTimeId: "dateTimeTo"
 *            })
 ******************************************/
gsl.FromToDateTimeField = Ext
		.extend(
				Ext.form.Field,
				{

					fromMinDateTime : false,
					/**
					 * @cfg {String} fromDateTimeFormat 开始时间的格式
					 */
					fromDateTimeFormat : "Y-m-d H:i",
					/**
					 * @cfg {String} fromDateTimeId 开始日期控件的id属性
					 */
					fromDateTimeId : "from",
					/**
					 * @cfg {String} fromDateTimeName 开始日期控件的name属性
					 */
					fromDateTimeName : "from",
					fromAllowBlank : true,
					toAllowBlank : true,
					/**
					 * {Element} fromDateEl 开始时间控件
					 */

					/**
					 * @cfg {String} toDateTimeFormat 结束时间的格式
					 */
					toDateTimeFormat : "Y-m-d H:i",
					/**
					 * @cfg {String} toDateTimeId 开始日期控件的id属性
					 */
					toDateTimeId : "to",
					/**
					 * @cfg {String} toDateTimeName 开始日期控件的name属性
					 */
					toDateTimeName : "to",
					/**
					 * {Element} toDateEl 结束时间控件
					 */
					// private
					defaultAutoCreate : {
						tag : "div",
						style : "padding-left:3px!important",
						autocomplete : "off"
					},

					initComponent : function() {
						if (!this.fromAllowBlank || !this.toAllowBlank) {
							this.labelSeparator = Ext.layout.FormLayout.prototype.labelSeparator + '<font color=red>*</font>';
						}
						gsl.FromToDateTimeField.superclass.initComponent.call(this);
					},
					// private
					onRender : function(ct, position) {
						gsl.FromToDateTimeField.superclass.onRender.call(this, ct,
								position);
						var fromDateTimeId = this.fromDateTimeId;
						var toDateTimeId = this.toDateTimeId;
						this.fromDateTimeEl = new gsl.DatetimeField( {
							id : fromDateTimeId,
							name : this.fromDateTimeName || this.fromDateTimeId,
							hiddenLabel : true,
							format : this.fromDateTimeFormat || 'Y-m-d H:i',
							anchor : '99%',
							endDateTimeField : toDateTimeId,
							vtype : 'datetimerange',
							allowBlank : this.fromAllowBlank
							,width:140
						});
						if (this.fromMinDateTime) {
							this.fromDateTimeEl.setMinValue((new Date())
									.format('Y-m-d H:i'));
						}
						if (Ext.isIE6 || Ext.isIE7) {
							this.fromDateTimeEl.render(this.el);
							Ext.fly(this.fromDateTimeEl.el.dom.parentNode)
									.addClass("x-column");
						} else {
							this.fromDateTimeEl.render(Ext.DomHelper.append(
									this.el, {
										tag : 'div',
										cls : 'x-column'
									}, true));
						}

						this.el.createChild( {
							tag : 'div',
							html : '~',
							cls : 'x-column'
						});

						this.toDateTimeEl = new gsl.DatetimeField( {
							id : toDateTimeId,
							name : this.toDateTimeName || this.toDateTimeId,
							hiddenLabel : true,
							format : this.toDateTimeFormat || 'Y-m-d H:i',
							anchor : '99%',
							startDateTimeField : fromDateTimeId,
							vtype : 'datetimerange',
							allowBlank : this.toAllowBlank
							,width:140
						});
						if (Ext.isIE6 || Ext.isIE7) {
							this.toDateTimeEl.render(this.el);
						} else {
							this.toDateTimeEl.render(Ext.DomHelper.append(this.el,
									{
										tag : 'div',
										cls : 'x-column'
									}, true));
						}
						Ext.DomHelper.append(this.el, {
							tag : 'div',
							cls : 'x-clear'
						});
					},
					reset : function() {
						this.fromDateTimeEl.reset();
						this.toDateTimeEl.reset();
					},
					validate : function() {
						var fv = !this.fromDateTimeEl.validate();
						var tv = !this.toDateTimeEl.validate();
						if (fv || tv) {
							return false;
						}
						return true;
					}
				});
Ext.reg('fromtodatetimefield', gsl.FromToDateTimeField);

Ext.override(Ext.form.BasicForm, {
	findField : function(id) {
		var field = this.items.get(id);
		if (!(field && typeof field == 'object')) {
			this.items
					.each(function(f) {

						if (f.isFormField
								&& (f.dataIndex == id || f.id == id || f
										.getName() == id)) {
							field = f;
							return false;
						} else if (f.isFormField
								&& (f instanceof gsl.FromToDateTimeField)) {
							if (f.toDateTimeEl.dataIndex == id
									|| f.toDateTimeEl.id == id
									|| f.toDateTimeEl.getName() == id) {
								field = f;
								return false;
							} else if (f.fromDateTimeEl.dataIndex == id
									|| f.fromDateTimeEl.id == id
									|| f.fromDateTimeEl.getName() == id) {
								field = f;
								return false;
							}
						}
					});
		}
		return field;
	}
});
/**********gsl.FromToDateTimeField END**********
 * Author   : wangzhibin
 * Date     : 2010-6-29
 * Use      : new gsl.FromToDateTimeField({
 *                fieldLabel: 'fromToDateTime',
 *                fromDateTimeId : "dateTimeFrom",
 *                toDateTimeId: "dateTimeTo"
 *            })
 ******************************************/

/**
 * 是否打切的控件，根据值改变颜色
 */
gsl.LCmbColumnForIsCutoff = function(opts) {
	var lcmbRenderer = function(data) {
		return function(v, m, rd, r, c, s) {
			if(v==0)
			{
				m.attr = 'style="color:Red;"';
			}
			return gsl.getLCmbNmByCd(data, v);
		};
	};
	return Ext.apply(opts, {
		renderer : new lcmbRenderer(opts.cmbData)
	});
};


/**
 * 禁用backspace键
 */
function forbidBackSpace() {
	if (window.event.keyCode == 8) {
		event.keyCode = 0; 
        event.cancelBubble = true; 
        return false; 
	}
}

/**
 * 输入框textfield后面放字unitText定义
 */
Ext.override(Ext.form.TextField, {
	 unitText : '',
	 onRender : function(ct, position) {
	  Ext.form.TextField.superclass.onRender.call(this, ct, position);

	  if (this.unitText != '') {
	   this.unitEl = ct.createChild({
	      tag : 'div',
	      html : this.unitText
	     });
	   this.unitEl.addClass('x-form-unit');
	   // 增加单位名称的同时按单位名称大小减少文本框的长度 初步考虑了中英文混排 未考虑为负的情况
//	   this.width = this.width
//	     - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6 + 2);
	   // 同时修改错误提示图标的位置
//	   this.alignErrorIcon = function() {
//	    this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);
//	   };
	  }
	 }
	});

	


/***************************几个共通的下拉控件 START****************************/
//定义供应商代码Store并填充数据
//var cmbsupplierStore = supplierIdStore = new gsl.JsonCmbStore( {
var cmbsupplierStore = new gsl.JsonCmbStore( {
    type : 'partnerId'
});
// 供应商代码
var cmbSupplierId = new gsl.QueryComboBox( {
    fieldLabel : l_supplierId,
    triggerAction : 'all',
    tabIndex : 2,
    autoLoad : true,
    width : 150,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'code',
    store :cmbsupplierStore,
    listeners : ( {
        'select' : function() {
        	gsl.request({
					url : 'comm-querySupplierNameById.action',
					params : {
						'cmbItemVO.code' : cmbSupplierId.getRawValue()
					},
					success : function(response) {
						var supplierName = Ext.decode(response.responseText).data.name;
						lblSupplierName.setValue(supplierName);
			            // 厂家代码默认值
						cmbFactoryId.store.reload();
			            cmbFactoryId.setRawValue(cmbSupplierId.getRawValue());
			            cmbFactoryId.addClass('blackFont');
			            cmbFactoryId.emptyClass = 'grayFont';
			            lblFactoryName.setValue(supplierName);
					}
			});
        },
        'change' : function() {
            // 联动设置零件编号
            //重新设置cmbPartIdS的参数type和param
            cmbPartId.getStore().baseParams.type = 'querySupplierId,'+cmbSupplierId.getRawValue()+",false";
            //reload cmb
            cmbPartId.getStore().reload();
        },
        'blur' : function() {
            if (cmbSupplierId.getRawValue() == '') {
                lblSupplierName.setValue('');
                cmbFactoryId.setValue('');
                lblFactoryName.setValue('');
            }
        }
    })
});

// 供应商名称
var lblSupplierName = new gsl.LabelField( {
    fieldLabel : l_supplierName,
    id : 'txtSupplierName',
    name : 'supplierName'
});

// 厂家代码
var cmbFactoryId = new gsl.QueryComboBox( {
    fieldLabel : l_factoryId,
    //hiddenName : 'factoryId',// ！！
    width : 150,
    tabIndex : 3,
    triggerAction : 'all',
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'code',
    store : new gsl.JsonCmbStore( {
        type : 'partnerId'
    }),
    listeners : ( {
        'select' : function() {
            //lblFactoryName.setValue(cmbFactoryId.getValue());
        	gsl.request({
					url : 'comm-querySupplierNameById.action',
					params : {
						'cmbItemVO.code' : cmbFactoryId.getRawValue()
					},
					success : function(response) {
						var factoryName = Ext.decode(response.responseText).data.name;
			            lblFactoryName.setValue(factoryName);
					}
			});
        },
        'blur' : function() {
            if (cmbFactoryId.getRawValue() == '') {
                lblFactoryName.setValue('');
            }
        }
    })
});

// 厂家名称
var lblFactoryName = new gsl.LabelField( {
    fieldLabel : l_factoryName,
    id : 'lblFactoryName',
    name : 'factoryName'
});

// 卸货口
var cmbUnload = new gsl.QueryComboBox( {
    fieldLabel : l_unloadPort,
    //hiddenName : 'unloadPort',// ！！
    triggerAction : 'all',
    width : 150,
    tabIndex : 4,
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'name',
    store : new gsl.JsonCmbStore( {
        type : 'unloadIdSpecial'
    }),
    listeners : ( {
        'select' : function() {
            String[ strUnloadAog = cmbUnload.getValue().split("+")];
            lblUnloadPlace.setValue(strUnloadAog[0]);
            lblAogFactoryForUnload.setValue(strUnloadAog[1]);
        },
        'blur' : function() {
            if (cmbUnload.getRawValue() == '') {
                lblUnloadPlace.setValue('');
                lblAogFactoryForUnloade.setValue('');
            }
        }
    })
});

// 卸货场所
var lblUnloadPlace = new gsl.LabelField( {
    fieldLabel : l_unloadPalce,
    id : 'lblUnloadPlace',
    allowBlank : false,
    name : 'unloadPlace'
});

// 与卸货口绑定的到货工厂
var lblAogFactoryForUnload = new gsl.LabelField( {
    fieldLabel : l_aogFactory_lable,
    id : 'aogFactory',
    name : 'aogFactory'
});

// 零件编号
var cmbPartId = new gsl.QueryComboBox( {
    fieldLabel : l_partId,
    triggerAction : 'all',
    tabIndex : 1,
    width : 150,
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'name',
    store : new gsl.JsonCmbStore( {
        type : 'partId'
    }),
    listeners : ( {
        'select' : function() {
            String[ strPart = cmbPartId.getValue().split("+")];
            lblPartNameCn.setValue(strPart[0]);
            lblPartUnit.setValue(strPart[1]);
        },
        'change' : function() {
            // 联动设置供应商代码&厂家代码---根据PartId查询
//            cmbSupplierId.getStore().baseParams.type = 'queryPartId,'+ cmbPartId.getRawValue()+",false";
          	cmbPartId.getStore().baseParams.type = 'partId,'+cmbPartId.getRawValue()+",false";

        	//reload cmb
            cmbPartId.getStore().reload();
        },
        'blur' : function() {
            if (cmbPartId.getRawValue() == '') {
                lblPartNameCn.setValue('');
            }
        }
    })
});

// 零件名称
var lblPartNameCn = new gsl.LabelField( {
    fieldLabel : l_partNameCn,
    allowBlank : false,
    id : 'txtPartName',
    name : 'partNameCn'
});

// 零件单位
var lblPartUnit = new gsl.LabelField( {
    fieldLabel : l_partUnit,
    allowBlank : false,
    id : 'txtPartUnit',
    name : 'partUnit'
});


//到货工厂
var lblAogFactory = new gsl.LabelField( {
    fieldLabel : l_aogFactory_lable,
    id : 'aogFactory',
    name : 'aogFactory',
    value : l_aogFactory_value
});

//到货工厂 --- 待删除
var aogFactory = new gsl.LabelField( {
    fieldLabel : l_aogFactory_lable,
    id : 'aogFactory',
    name : 'aogFactory',
    value : l_aogFactory_value
});

//定义供应商代码Store并填充数据
var supplierIdStore = new gsl.JsonCmbStore( {
    type : 'partnerIdSpecial'
});
/**
 * 带'*'的模糊插入共通控件
 */
//零件编号
var cmbPartIdS = new gsl.QueryComboBox( {
    fieldLabel : l_partId,
    //hiddenName : 'factoryId',// ！！
    width : 150,
    tabIndex : 1,
    triggerAction : 'all',
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'name',
    store : new gsl.JsonCmbStore( {
        type : 'partIdSpecial'
    }),
    listeners : ( {
        'select' : function() {
            lblPartNameCnS.setValue(cmbPartIdS.getValue());
        },
        'change' : function() {
            // 联动设置供应商代码&厂家代码
            if(cmbPartIdS.getRawValue()=='*'){//查询全部
                //重新设置cmbSupplierIdS的参数type
                cmbSupplierIdS.getStore().baseParams.type = 'partnerIdSpecial';
                //reload cmb
                cmbSupplierIdS.getStore().reload();
            }else{//根据PartId查询
            	//重新设置cmbSupplierIdS的参数type和param
                cmbSupplierIdS.getStore().baseParams.type = 'queryPartId,'+cmbPartIdS.getRawValue()+",true";
                //reload cmb
                cmbSupplierIdS.getStore().reload();
            }
        },
        'blur' : function() {
            if (cmbPartIdS.getRawValue() == '') {
                lblPartNameCnS.setValue('');
            }
        }
    })
});

// 零件名称
var lblPartNameCnS = new gsl.LabelField( {
    fieldLabel : l_partNameCn,
    allowBlank : false,
    id : 'txtPartName',
    name : 'partNameCn'

});
// 供应商代码
var cmbSupplierIdS = new gsl.QueryComboBox( {
    fieldLabel : l_supplierId,
    triggerAction : 'all',
    tabIndex : 2,
    autoLoad : true,
    width : 150,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'code',
    store : supplierIdStore,
    listeners : ( {
        'select' : function() {
        	if(cmbSupplierIdS.getRawValue() != '*'){
	        	gsl.request({
					url : 'comm-querySupplierNameById.action',
					params : {
						'cmbItemVO.code' : cmbSupplierIdS.getRawValue()
					},
					success : function(response) {
						var supplierName = Ext.decode(response.responseText).data.name;
						lblSupplierNameS.setValue(supplierName);
			            // 厂家代码默认值
						cmbFactoryIdS.store.reload();
			            cmbFactoryIdS.setRawValue(cmbSupplierIdS.getRawValue());
			            cmbFactoryIdS.addClass('blackFont');
			            cmbFactoryIdS.emptyClass = 'grayFont';
			            lblFactoryNameS.setValue(supplierName);
					}
				});
        	}else{
        		lblSupplierNameS.setValue('*');
			    // 厂家代码默认值
			    cmbFactoryIdS.setValue('*');
			    lblFactoryNameS.setValue('*');
        	}
        },
        'change' : function() {
            // 联动设置零件编号
            if(cmbSupplierIdS.getRawValue()=='*'){//查询全部
                //重新设置cmbPartIdS的参数type
                cmbPartIdS.getStore().baseParams.type = 'partIdSpecial';
                //reload cmb
                cmbPartIdS.getStore().reload();
            }else{//根据SupplierId查询
                //重新设置cmbPartIdS的参数type和param
                cmbPartIdS.getStore().baseParams.type = 'querySupplierId,'+cmbSupplierIdS.getRawValue()+",true";
                //reload cmb
                cmbPartIdS.getStore().reload();
            }
        },
        'blur' : function() {
            if (cmbSupplierIdS.getRawValue() == '') {
                lblSupplierNameS.setValue('');
                cmbFactoryIdS.setValue('');
                lblFactoryNameS.setValue('');
              }
        }
    })
});

// 供应商名称
var lblSupplierNameS = new gsl.LabelField( {
    fieldLabel : l_supplierName,
    id : 'txtSupplierName',
    name : 'supplierName'
});

// 厂家代码
var cmbFactoryIdS = new gsl.QueryComboBox( {
    fieldLabel : l_factoryId,
    //hiddenName : 'factoryId',// ！！
    width : 150,
    tabIndex : 3,
    triggerAction : 'all',
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'code',
    store : new gsl.JsonCmbStore( {
        type : 'partnerIdSpecial'
    }),
    listeners : ( {
        'select' : function() {
            //lblFactoryNameS.setValue(cmbFactoryIdS.getValue());
        	if(cmbFactoryIdS.getRawValue() != '*'){
	        	gsl.request({
					url : 'comm-querySupplierNameById.action',
					params : {
						'cmbItemVO.code' : cmbFactoryIdS.getRawValue()
					},
					success : function(response) {
						var factoryName = Ext.decode(response.responseText).data.name;
			            lblFactoryNameS.setValue(factoryName);
					}
				});
        	}else{
			    lblFactoryNameS.setValue('*');
        	}
        },
        'blur' : function() {
            if (cmbFactoryIdS.getRawValue() == '') {
                lblFactoryNameS.setValue('');
            }
        }
    })
});

// 厂家名称
var lblFactoryNameS = new gsl.LabelField( {
    fieldLabel : l_factoryName,
    id : 'lblFactoryName',
    name : 'factoryName'
});

//出货仓库---不带*的
var cmbShipDepot = new gsl.QueryComboBox({
    fieldLabel : l_shipDepot,
    //hiddenName : 'unloadPort',// ！！
    triggerAction : 'all',
    width : 85,
    tabIndex : 4,
    autoLoad : true,
    displayField : 'code',
    valueField : 'name',
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
	store : new gsl.JsonShipDepotloadCmbStore( {
        type : 'shipDepotByParam',
        addBlank : true
    })
});

// 卸货口---带*的
var cmbUnloadS = new gsl.QueryComboBox( {
    fieldLabel : l_unloadPort,
    //hiddenName : 'unloadPort',// ！！
    triggerAction : 'all',
    width : 150,
    tabIndex : 4,
    autoLoad : true,
    allowBlank : false,
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
    displayField : 'code',
    valueField : 'name',
    store : new gsl.JsonCmbStore( {
        type : 'unloadIdSpecial'
    }),
    listeners : ( {
        'select' : function() {
            String[ strUnloadAog = cmbUnloadS.getValue().split("+")];
            
//            lblUnloadPlaceS.setValue(strUnloadAog[0]);
//            txtsupplier_nameS.setValue(strUnloadAog[1]);
            lblUnloadPlaceS.setValue(strUnloadAog[1]);
            txtsupplier_nameS.setValue(strUnloadAog[2]);
        },
        'blur' : function() {
            if (cmbUnloadS.getRawValue() == '') {
                lblUnloadPlaceS.setValue('');
                txtsupplier_nameS.setValue('');
            }
        }
    })
});

//卸货口---不带*的
var cmbUnloadNo = new gsl.QueryComboBox( {
    fieldLabel : l_unloadPort,
    //hiddenName : 'unloadPort',// ！！
    triggerAction : 'all',
    width : 150,
    tabIndex : 4,
    autoLoad : true,
    allowBlank : false,
    displayField : 'code',
    valueField : 'name',
    // 如果输入的值是错的，自动把它当作空的，为下面的自动设空服务
    forceSelection : true, 
	store : new gsl.JsonMpUnloadCmbStore( {
        type : 'unloadIdSpecialNo'
    }),
    listeners : ( {
        'select' : function() {
            String[ strUnloadAog = cmbUnloadNo.getValue().split("+")];
//            lblUnloadPlaceS.setValue(strUnloadAog[0]);
//            txtsupplier_nameS.setValue(strUnloadAog[1]);
            lblUnloadPlaceS.setValue(strUnloadAog[1]);
            txtsupplier_nameS.setValue(strUnloadAog[2]);
            
        },
        'blur' : function() {
            if (cmbUnloadNo.getRawValue() == '') {
                lblUnloadPlaceS.setValue('');
                txtsupplier_nameS.setValue('');
            }
        }
    })
});

// 卸货场所
var lblUnloadPlaceS = new gsl.LabelField( {
    fieldLabel : l_unloadPalce,
    id : 'lblUnloadPlace',
    allowBlank : false,
    name : 'unloadPlace'
});
    
// 到货工厂
var txtsupplier_nameS = new gsl.LabelField( {
    id : 'supplier_name',
    fieldLabel : l_AogFactory_D09,
    allowBlank : false,
    name : 'lblsupplier_name'
});
/*****************************几个共通的下拉控件  END***************************/


