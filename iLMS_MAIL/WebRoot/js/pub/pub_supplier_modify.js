/** 供应商信息修改(供应商) */
Ext.onReady(function() {
	Ext.QuickTips.init();
	// 用户名
//	var txtUserName = new gsl.LabelField({
//				id : 'loginId',
//				dataIndex:'loginId',
//				name : 'info.loginId',
//				fieldLabel : l_loginName,
//				value:loginIdServer
//			});
	// 供应商代码
	var txtSupplierNo = new gsl.LabelField({
		        id:'txtSupplierNo',
		        dataIndex:'supplierNo',
//				name : 'supVO.supplierNo',
				fieldLabel : l_supplierId
			});

	// 公司中文名
	var txtChineseName = new gsl.TextField({
				id : 'corp_cn_name',
				dataIndex:'supplierName',
				name : 'supVO.supplierName',
				fieldLabel : l_supplier_chineseName,
				maxLength : 100,
				width : 250
			});

	// 所在地
	var lblPlace = new gsl.LabelField({
		value : l_location_M,
		style : 'padding-top:0px;height:26px;;width:138px;text-align:right;padding-right:11px;border:0'
	});

	// 邮政编码
	var youZhengBianMa = new gsl.TextField({
				id : 'youZhengBianMa',
				dataIndex:'youZhengBianMa',
				name : 'supVO.youZhengBianMa',
				fieldLabel : l_postCode,
				vtype:'telephone',
				allowBlank : false,
				maxLength : 10,
				width : 80
			});
	// 联系人
	var xuqiuLianxiren = new gsl.TextField({
				id : 'xuqiuLianxiren',
				dataIndex:'xuqiuLianxiren',
				name : 'supVO.xuqiuLianxiren',
				fieldLabel : l_order_contacts,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 联系人电话
	var xuqiuLianxiTel = new gsl.TextField({
				id : 'xuqiuLianxiTel',
				dataIndex:'xuqiuLianxiTel',
				name : 'supVO.xuqiuLianxiTel',
				fieldLabel : l_order_contacts_tel,
				vtype:'telephone',
				allowBlank : false,
				maxLength : 15
			});

	// 联系人手机
	var xuqiuMobile = new gsl.TextField({
				id : 'xuqiuMobile',
				name : 'supVO.xuqiuMobile',
				fieldLabel : l_order_contacts_mobile,
				vtype:'mobile',
				allowBlank : false,
				maxLength : 20
			});

	// 需求人邮箱
	var xuqiuEmail = new gsl.TextField({
				id : 'xuqiuEmail',
				dataIndex:'xuqiuEmail',
				name : 'supVO.xuqiuEmail',
				vtype:'email',
				fieldLabel : l_order_contacts_mail,
				maxLength : 50,
				allowBlank : false,
				width : 200
			});

	// 发货联系人
	var fahuoLianxiren = new gsl.TextField({
				id : 'fahuoLianxiren',
				dataIndex:'fahuoLianxiren',
				name : 'supVO.fahuoLianxiren',
				fieldLabel : l_delivery_contacts,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 发货联系人电话
	var fahuoLianxiTel = new gsl.TextField({
				id : 'fahuoLianxiTel',
				dataIndex:'fahuoLianxiTel',
				name : 'supVO.fahuoLianxiTel',
				fieldLabel : l_delivery_contacts_tel,
				maxLength : 15,
				vtype:'telephone',
				allowBlank : false
			});

	// 发货联系人手机
	var fahuoMobile = new gsl.TextField({
				id : 'fahuoMobile',
				name : 'supVO.fahuoMobile',
				fieldLabel : l_delivery_contacts_mobile,
				vtype:'mobile',
				allowBlank : false,
				maxLength : 20
			});

	// 发货联系人邮箱
	var fahuoEmail = new gsl.TextField({
				id : 'fahuoEmail',
				dataIndex:'fahuoEmail',
				vtype:'email',
				name : 'supVO.fahuoEmail',
				fieldLabel : l_delivery_contacts_mail,
				allowBlank : false,
				maxLength : 50,
				width : 200
			});

	// 物流异常联系人
	var wuliuLianxiren = new gsl.TextField({
				id : 'wuliuLianxiren',
				dataIndex:'wuliuLianxiren',
				name : 'supVO.wuliuLianxiren',
				fieldLabel : l_log_contacts_abnormal,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 物流异常联系人电话
	var wuliuLianxiTel = new gsl.TextField({
				id : 'wuliuLianxiTel',
				vtype:'telephone',
				dataIndex:'wuliuLianxiTel',
				name : 'supVO.wuliuLianxiTel',
				fieldLabel : l_log_contacts_abnormal_tel,
				allowBlank : false,
				maxLength : 15,
				vtype:'telephone'
			});

	// 物流联系人手机
	var wuliuMobile = new gsl.TextField({
				id : 'wuliuMobile',
				name : 'supVO.wuliuMobile',
				fieldLabel : l_log_contacts_abnormal_mobile,
				vtype:'mobile',
				allowBlank : false,
				maxLength : 20
			});

	// 物流异常联系人邮箱
	var wuliuEmail = new gsl.TextField({
				id : 'wuliuEmail',
				dataIndex:'wuliuEmail',
				name : 'supVO.wuliuEmail',
				vtype:'email',
				fieldLabel : l_log_contacts_abnormal_mail,
				allowBlank : false,
				maxLength : 50,
				width : 200
			});

	// 发货方代码
	var fahuofangId = new gsl.TextField({
				id : 'fahuofangId',
				dataIndex:'fahuofangId',
				name : 'supVO.fahuofangId',
				fieldLabel : l_delivery_no,
				allowBlank : false,
				maxLength : 10,
				width : 80
			});

	// 发货方地址
	var fahuofangAddr = new gsl.TextField({
				id : 'fahuofangAddr',
				dataIndex:'fahuofangAddr',
				name : 'supVO.fahuofangAddr',
				fieldLabel : l_delivery_address,
				allowBlank : false,
				maxLength : 100,
				width : 500
			});

	// 供应商编号
//	var supplierNo = new Ext.form.Hidden({
//				id : 'supplierNo',
//				dataIndex:'supplierNo',
//				name : 'supVO.supplierNo'
//			});

	// 邮箱说明
	var lblXuqiuEmailDescrip = new Ext.form.Label({
				id : 'lblXuqiuEmailDescrip',
//				注:该邮箱作为重要联系方式，请认真填写。
				text : l_pls_fill_take_attention,
				style : 'color:red;line-height:2em;'
			});
	// 邮箱说明
	var lblWuliuEmailDescrip = new Ext.form.Label({
				id : 'lblWuliuEmailDescrip',
//				注:该邮箱作为重要联系方式，请认真填写。
				text : l_pls_fill_take_attention,
				style : 'color:red;line-height:2em;'
			});
	// 邮箱说明
	var lblFahuoEmailDescrip = new Ext.form.Label({
				id : 'lblFahuoEmailDescrip',
//				注:该邮箱作为重要联系方式，请认真填写。
				text : l_pls_fill_take_attention,
				style : 'color:red;line-height:2em;'
			});
	// 父供应商代码
	var parentNo = new gsl.LabelField( {
		id :'parentNo',
		dataIndex:'parentNo',
		name :'parentNo',
		fieldLabel :l_father_supplier_no,
		value:parentNoServer
	});
	var xuqiuFax = new gsl.TextField( {
		id :'xuqiuFax',
		dataIndex:'xuqiuFax',
		name :'supVO.xuqiuFax',
		vtype:'telephone',
		allowBlank : false,
		fieldLabel :l_order_contacts_fax
	});
	var fahuoFax = new gsl.TextField( {
		id :'fahuoFax',
		dataIndex:'fahuoFax',
		vtype:'telephone',
		name :'supVO.fahuoFax',
		allowBlank : false,
		fieldLabel :l_delivery_contacts_fax
	});
	var wuliuFax = new gsl.TextField( {
		id :'wuliuFax',
		dataIndex:'wuliuFax',
		vtype:'telephone',
		name :'supVO.wuliuFax',
		allowBlank : false,
		fieldLabel :l_log_contacts_abnormal_fax
	});

	
	/***************************************************************************
	 * 保存供应商注册信息
	 */
	 save=function() {
		if (queryForm.form.isValid()) {
			queryForm.submit({
				url : 'pub/supManage-updateSupInfoBySupUser.action',
				params : {
					'supVO.supplierNo' : txtSupplierNo.getValue()
				},
				success : function(form, action){
		         document.body.scrollTop=0;
				}
			});
		}
	};
	
	queryForm = new gsl.FormPanel({
				renderTo : 'query_div',
				labelWidth : 180,
				width :(Ext.get('query_div').getWidth() > 800 ?Ext.get('query_div').getWidth():800),
				labelAlign : 'right',
				frame : true,
				items : [
//					{
//					layout : 'column',
//					items : [{
//								columnWidth : 1,
//								layout : 'form',
//								items : [ txtUserName]
//							}]
//				},
					{
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [ txtSupplierNo]
							}]
				}, {
					layout : 'column',
					items : [{
						columnWidth : 0.58,
						layout : 'form',
						items : [txtChineseName]
					},{
						columnWidth : 0.4,
						labelWidth : 78,
						layout : 'form',
						items : [youZhengBianMa]
					}]

				}, {
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [[]]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [xuqiuLianxiren,xuqiuLianxiTel,xuqiuEmail ]
							}, {
								columnWidth : .5,
								labelWidth : 140,
								layout : 'form',
								items : [xuqiuMobile,xuqiuFax,lblXuqiuEmailDescrip]

							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [[]]
							}, {
								columnWidth : .5,
								layout : 'form',
								items : [fahuoLianxiren,fahuoLianxiTel,fahuoEmail]
							}, {
								columnWidth : .5,
								labelWidth : 140,
								layout : 'form',
								items : [fahuoMobile,fahuoFax,lblFahuoEmailDescrip]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [[]]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [wuliuLianxiren ,wuliuLianxiTel ,wuliuEmail]
							},  {
								columnWidth : .5,
								labelWidth : 140,
								layout : 'form',
								items : [wuliuMobile,wuliuFax,lblWuliuEmailDescrip]
							}]
				}, {
					layout : 'column',
					items : [ {
								columnWidth : 1,
								layout : 'form',
								items : [ fahuofangAddr]

							}]
				}, {
					layout : 'column',
					items : [
//						{
//								columnWidth : .4,
//								layout : 'form',
//								items : [supplierNo]
//							}
							]
				}],
				buttons : [{
//					保存
							text : l_save,
							handler : save
						}]
			});

	queryForm.load( {
		url :'pub/supManage-querySupModifyInfo.action',
		params : {
//			'queryInfo.supplierNo' : supplierNoServer
		},
		success:function(form,action){
			// zhangye 20100730 追加 画面load后，清除红色错误标记
			queryForm.form.clearInvalid();
			
		},
		failure : function(form,action){
			gsl.ErrorAlert(action.result == undefined
								|| action.result.msg == undefined
								|| Ext.isEmpty(action.result.msg) ? "查询信息失败。"
								: action.result.msg);
		}
	});
	
	function close() {
//		录入信息尚未保存，确定要关闭画面吗？
		Ext.MessageBox.confirm(l_msg_tip, l_infoNotSave_isSureClose, function(btnId) {
					if (btnId == 'yes') {
					}
				});
	};
	
	adjWidthCtrs = [queryForm];
});