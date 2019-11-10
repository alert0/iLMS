/**
 * 物流公司录入
 */
Ext.onReady(function() {

		// 公司代码
		var txtLogisticsId = new gsl.TextField( {
			fieldLabel : l_logistics_logisticsId,
			allowBlank : false,
			vtype : 'alphanum',
			maxLength:9,
			width : 90,
			id : 'txtLogisticsId',
			name: 'pageLogCompanyVO.companyNo',
	        listeners : {
	        }
		});
		
		// 公司名称
		var txtLogisticsName = new gsl.TextField( {
			fieldLabel : l_logistics_logisticsName,
			allowBlank : false,
			maxLength:20,
			width : 200,
			id : 'txtLogisticsName',
			name: 'pageLogCompanyVO.companyName',
			listeners : {
	        }
		});

		// 公司地址
		var txtLogisticsAddr = new gsl.TextField( {
			fieldLabel : l_logistics_logisticsAddr,
			allowBlank : false,
			maxLength:100,
			width : 500,
            id : 'txtLogisticsAddr',
            name: 'pageLogCompanyVO.companyAddr'
		});
		
		//物流公司类型
		var cbxLogisticsType = new gsl.BaseDataComboBox({
			valueField: 'code',
			id: 'cbxLogisticsType',
			hiddenName: 'pageLogCompanyVO.type',
			baseData: logCompanyTypeArray,
			readOnly: true,
			allowBlank:false,
//			value : LOG_COMPANY_TYPE_TRANSIT,
			fieldLabel: l_logistics_logisticsType,
			width: 90
		});
		
		// 公司状态
		var cbxLogisticsStatus = new gsl.BaseDataComboBox( {
			valueField : 'code',
			id: 'cbxLogisticsStatus',
			hiddenName : 'pageLogCompanyVO.status',
			baseData : activateStatusArray,
			readOnly : true,
			allowBlank : false,
			fieldLabel : l_activate_status,
			value : ACTIVATE_STATUS_YES,
			width:90
		});

		
		//ERP联系人
		var txtERPlianxiren = new gsl.TextField({
			fieldLabel:l_erp_contacts,
			width: 150,
			allowBlank:false,
			maxLength: 20,
			id: 'txtERPlianxiren',
			name: 'pageLogCompanyVO.erpContactUser'
		});
		
		//联系手机
		var txtErpmobile = new gsl.TextField({
			fieldLabel:l_logistics_linkmobile,
			allowBlank: true,
			maxLength: 20,
			width:150,
			vtype:'mobile',
			id: 'txtErpmobile',
			name: 'pageLogCompanyVO.erpMobile'
		});
		
		//联系电话
		var txtLianxirenTel = new gsl.TextField({
			fieldLabel:l_contact_tel,
			allowBlank: true,
			vtype:'telephone',
			maxLength: 20,
			width:150,
			vtype:'telephone',
			id: 'txtLianxirenTel',
			name: 'pageLogCompanyVO.erpTel'
		});
		
		//联系传真
		var txtLianxirenFax = new gsl.TextField({
			fieldLabel:l_logistics_linkfax,
			vtype: 'telephone',
			maxLength: 20,
			allowBlank: true,
			width: 150,
			id: 'txtLianxirenFax',
			name: 'pageLogCompanyVO.erpFax'
		});
		
		//联系Email
		var txtLianxirenEmail = new gsl.TextField({
			fieldLabel: l_logistics_linkEmail,
			vtype:'email',
			maxLength: 30,
			allowBlank: false,
			width: 150,
			id: 'txtLianxirenEmail',
			name: 'pageLogCompanyVO.erpEmail'
		});

		var logisticsAddForm = new gsl.FormPanel( {
			frame : true,
			labelWidth : 100,
			renderTo : 'entryDiv',
			width :(Ext.get('entryDiv').getWidth() > 800 ?Ext.get('entryDiv').getWidth():800),
			items : [{
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []

						}, {
							columnWidth : .5,
							layout : 'form',
							items : [txtLogisticsId]
						},{
							columnWidth: .4,
							layout: 'form',
							items: [cbxLogisticsType]
						}]
			}, {
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [txtLogisticsName]
						},{
							columnWidth: .4,
							layout: 'form',
							items: [cbxLogisticsStatus]
						}]
			}, {
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []
						},{
						columnWidth : .8,
						layout : 'form',
						items : [txtLogisticsAddr]
					}]
			}, {
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []
						},{
							columnWidth : .4,
							layout : 'form',
							items : [txtERPlianxiren]
						}, {
							columnWidth : .4,
							layout : 'form',
							items : [txtLianxirenEmail]
						}]
			}, {
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []
						},{
							columnWidth : .4,
							layout : 'form',
							items : [txtErpmobile]
						}, {
							columnWidth : .4,
							layout : 'form',
							items : [txtLianxirenFax]
						}]
			},{
				layout : 'column',
				items : [{
							columnWidth : .1,
							layout : 'form',
							items : []
						},{
						columnWidth : .8,
						layout : 'form',
						items : [txtLianxirenTel]
					}]
			}],
			buttons : [ {
				text : l_tool_save,
				handler : saveLogistics
			} ]
		});
		// 表单提交
		function saveLogistics() {
			if (logisticsAddForm.form.isValid()) {
				logisticsAddForm.submit({
					url : 'pub/logcompany-insert.action',
					success : function(form, action) {
						logisticsAddForm.getForm().reset();
					}
				});
			}
		}
		adjWidthCtrs = [logisticsAddForm];
	});