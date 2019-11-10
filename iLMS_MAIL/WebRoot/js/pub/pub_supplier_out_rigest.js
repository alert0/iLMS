/** 供应商注册 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	// 用户名
	var txtUserName = new gsl.LabelField({
		id : 'loginId',
		fieldLabel : l_loginName,
		value:loginIdServer
	});
	var hideUserName = new Ext.form.Hidden({
		name : 'userVO.userName',
		value:loginIdServer
	});
	// 供应商代码
	var txtSupplierNo = new gsl.LabelField({
        id:'txtSupplierNo',
		fieldLabel : l_supplierId,
		value:supplierNoServer
	});
	// 供应商代码隐藏值
	var supplierNo = new Ext.form.Hidden({
		name : 'supVO.supplierNo',
		value:supplierNoServer
	});

	// 密码
	var txtPassword = new gsl.TextField({
				id : 'password',
				fieldLabel : l_newPwd,
				inputType : 'password',
//				name : 'info.supplierPwd',
				allowBlank : false,
				maxLength : 15,
				minLength : 6,
				width : 150
			});

	// 确认密码
	var txtConfirmPwd = new gsl.TextField({
				id : 'repeat_password',
				fieldLabel : l_confirmPwd,
				inputType : 'password',
				allowBlank : false,
				maxLength : 15,
				minLength : 6,
				name : 'userVO.userPwd',
				width : 150,
				invalidText:l_twoPwdDifferent,
				validator:function(){
				if(txtPassword.getValue() ==txtConfirmPwd.getValue()){
					return true;
				}else{
					return false;
				}
			}
			});

	// 公司中文名
	var txtChineseName = new gsl.TextField({
				id : 'corp_cn_name',
				name : 'supVO.supplierName',
				fieldLabel : l_supplier_chineseName,
				maxLength : 100,
				width : 250,
				value:chineseNameServer
			});

	// 所在地
	var lblPlace = new gsl.LabelField({
//		value : l_location_M,
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
				name : 'supVO.xuqiuLianxiren',
				fieldLabel : l_order_contacts,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 联系人电话
	var xuqiuLianxiTel = new gsl.TextField({
				id : 'xuqiuLianxiTel',
				name : 'supVO.xuqiuLianxiTel',
				fieldLabel : l_order_contacts_tel,
				vtype:'telephone',
				allowBlank : false,
				maxLength : 16
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
				name : 'supVO.xuqiuEmail',
				fieldLabel : l_order_contacts_mail,
				maxLength : 60,
				vtype:'email',
				allowBlank : false,
				width : 200
			});

	// 发货联系人
	var fahuoLianxiren = new gsl.TextField({
				id : 'fahuoLianxiren',
				name : 'supVO.fahuoLianxiren',
				fieldLabel : l_delivery_contacts,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 发货联系人电话
	var fahuoLianxiTel = new gsl.TextField({
				id : 'fahuoLianxiTel',
				name : 'supVO.fahuoLianxiTel',
				fieldLabel : l_delivery_contacts_tel,
				maxLength : 16,
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
				name : 'supVO.fahuoEmail',
				fieldLabel : l_delivery_contacts_mail,
				allowBlank : false,
				maxLength : 60,
				vtype:'email',
				width : 200
			});

	// 物流联系人
	var wuliuLianxiren = new gsl.TextField({
				id : 'wuliuLianxiren',
				name : 'supVO.wuliuLianxiren',
				fieldLabel : l_log_contacts,
				allowBlank : false,
				maxLength : 5,
				width : 80
			});

	// 物流联系人电话
	var wuliuLianxiTel = new gsl.TextField({
				id : 'wuliuLianxiTel',
				name : 'supVO.wuliuLianxiTel',
				fieldLabel : l_log_contacts_tel,
				allowBlank : false,
				maxLength : 16,
				vtype:'telephone'
			});

	// 物流联系人手机
	var wuliuMobile = new gsl.TextField({
				id : 'wuliuMobile',
				name : 'supVO.wuliuMobile',
				fieldLabel : l_log_contacts_mobile,
				vtype:'mobile',
				allowBlank : false,
				maxLength : 20
			});

	// 物流联系人邮箱
	var wuliuEmail = new gsl.TextField({
				id : 'wuliuEmail',
				name : 'supVO.wuliuEmail',
				fieldLabel :l_log_contacts_mail,
				allowBlank : false,
				maxLength : 60,
				vtype:'email',
				width : 200
			});

//	// 发货方代码
//	var fahuofangId = new gsl.TextField({
//				id : 'fahuofangId',
//				name : 'info.fahuofangId',
//				fieldLabel : '发货方代码',
//				allowBlank : false,
//				maxLength : 10,
//				width : 80
//			});

	// 发货方地址
	var fahuofangAddr = new gsl.TextField({
				id : 'fahuofangAddr',
				name : 'supVO.fahuofangAddr',
				fieldLabel : l_delivery_address,
				allowBlank : false,
				maxLength : 100,
				width : 500
			});

	

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
		name :'parentNo',
		fieldLabel :l_father_supplier_no,
		value:parentNoServer
	});
//	订货联系人传真
	var xuqiuFax = new gsl.TextField( {
		id :'xuqiuFax',
		name :'supVO.xuqiuFax',
		vtype:'telephone',
		allowBlank : false,
		maxLength : 15,
		fieldLabel :l_delivery_contacts_fax
	});
//	发货联系人传真
	var fahuoFax = new gsl.TextField( {
		id :'fahuoFax',
		name :'supVO.fahuoFax',
		vtype:'telephone',
		allowBlank : false,
		maxLength : 15,
		fieldLabel :l_delivery_contacts_fax
	});
//	物流联系人传真
	var wuliuFax = new gsl.TextField( {
		id :'wuliuFax',
		name :'supVO.wuliuFax',
		vtype:'telephone',
		allowBlank : false,
		maxLength : 15,
		fieldLabel :l_log_contacts_fax
	});

	// 验证码
//	var txtRandCode = new gsl.TextField({
//				fieldLabel : '验证码',
//				anchor : '70%',
//				allowBlank:false,
//				id : 'randCode'
//			});
	queryForm = new gsl.FormPanel({
				renderTo : 'query_div',
				labelWidth : 280,
				width :(Ext.get('query_div').getWidth() > 1000 ?Ext.get('query_div').getWidth():1000),
				labelAlign : 'right',
				frame : true,
				items : [{
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [ txtUserName]
							}]
				},{
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [ txtSupplierNo,hideUserName]
							}]
				},{
					layout : 'column',
					items : [{
								columnWidth : 1,
								layout : 'form',
								items : [ txtPassword,
										txtConfirmPwd]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : 0.58,
								layout : 'form',
								items : [txtChineseName]
							},{
								columnWidth : 0.4,
								labelWidth : 73,
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
								labelWidth : 150,
								layout : 'form',
								items : [xuqiuMobile,xuqiuFax,lblXuqiuEmailDescrip]

							}]
				}, {
					layout : 'column',
					items : [ {
								columnWidth : 1,
								layout : 'form',
								items : [[]]
							}, {
								columnWidth : .5,
								layout : 'form',
								items : [fahuoLianxiren,fahuoLianxiTel,fahuoEmail]
							}, {
								columnWidth : .5,
								labelWidth : 150,
								layout : 'form',
								items : [fahuoMobile,fahuoFax,lblFahuoEmailDescrip]
							}]
				}, {
					layout : 'column',
					items : [  {
								columnWidth : 1,
								layout : 'form',
								items : [[] ]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [wuliuLianxiren ,wuliuLianxiTel ,wuliuEmail]
							},  {
								columnWidth : .5,
								labelWidth : 150,
								layout : 'form',
								items : [wuliuMobile,wuliuFax,lblWuliuEmailDescrip]
							}]
				}, {
					layout : 'column',
					items : [  {
								columnWidth : 1,
								layout : 'form',
								items : [fahuofangAddr]

							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .4,
								layout : 'form',
								items : [supplierNo]
							}]
				}],
				buttons : [{
//					保存/
							text : l_save,
							handler : save
						}]
			});

//	Ext.fly(Ext.getDom('randCode').parentNode).createChild({
//				tag : 'img',
//				style : 'margin-left:18px',
//				src : 'randCode.jsp',
//				align : 'absbottom'
//			});

	/***************************************************************************
	 * 保存供应商注册信息
	 */
	function save() {
		if (queryForm.form.isValid()) {
			queryForm.submit({
				url : 'pub/supManage-updateSupplierByRigest.action',
				success : function(form, action){
					document.redirectForm.submit();
				}
			});
		}
	};
	function close() {
		Ext.MessageBox.confirm(l_msg_tip, l_infoNotSave_isSureClose, function(btnId) {
					if (btnId == 'yes') {
					}
				});
	};
});
