/** 供应商信息查看 */
Ext.onReady( function() {
	Ext.QuickTips.init();
	// 用户名
		var txtUserName = new gsl.LabelField( {
			id :'loginId',
			name :'loginId',
			fieldLabel :l_loginName
		});

		// 公司中文名
		var txtChineseName = new gsl.LabelField( {
			id :'corp_cn_name',
			name :'chineseName',
			fieldLabel :l_supplier_chineseName
		});
		var detailAddr = new gsl.LabelField( {
			id :'detailAddr',
			name :'detailAddr',
			fieldLabel :l_supplierDetailAddress
		});
		// 供应商地址
		var addr = new gsl.LabelField( {
			id :'addr',
			name :'addr',
			fieldLabel :l_supplierAddressCode
		});
         // 邮政编码
		var youZhengBianMa = new gsl.LabelField( {
			id :'youZhengBianMa',
			name :'youZhengBianMa',
			fieldLabel :l_postCode
		});
		// 联系人
		var xuqiuLianxiren = new gsl.LabelField( {
			id :'xuqiuLianxiren',
			name :'xuqiuLianxiren',
			fieldLabel :l_order_contacts
		});

		// 联系人电话
		var xuqiuLianxiTel = new gsl.LabelField( {
			id :'xuqiuLianxiTel',
			name :'xuqiuLianxiTel',
			fieldLabel :l_order_contacts_tel
		});

		// 联系人手机
		var xuqiuMobile = new gsl.LabelField( {
			id :'xuqiuMobile',
			name :'xuqiuMobile',
			fieldLabel :l_order_contacts_mobile
		});

		// 需求人邮箱
		var xuqiuEmail = new gsl.LabelField( {
			id :'xuqiuEmail',
			name :'xuqiuEmail',
			fieldLabel :l_order_contacts_mail
		});
		
		// ERP联系人
		var erpLianxiren = new gsl.LabelField( {
			id :'erpLianxiren',
			name :'erpLianxiren',
			fieldLabel :l_erp_contacts
		});

		// ERP联系人电话
		var erpTel = new gsl.LabelField( {
			id :'erpTel',
			name :'erpTel',
			fieldLabel :l_erp_contacts_tel
		});

		// 发货联系人
		var fahuoLianxiren = new gsl.LabelField( {
			id :'fahuoLianxiren',
			name :'fahuoLianxiren',
			fieldLabel :l_delivery_contacts
		});

		// 发货联系人电话
		var fahuoLianxiTel = new gsl.LabelField( {
			id :'fahuoLianxiTel',
			name :'fahuoLianxiTel',
			fieldLabel :l_delivery_contacts_tel
		});

		// 联系人手机
		var fahuoMobile = new gsl.LabelField( {
			id :'fahuoMobile',
			name :'fahuoMobile',
			fieldLabel :l_delivery_contacts_mobile
		});
		
		// 发货联系人邮箱
		var fahuoEmail = new gsl.LabelField( {
			id :'fahuoEmail',
			name :'fahuoEmail',
			fieldLabel :l_delivery_contacts_mail
		});

		// 物流异常联系人
		var wuliuLianxiren = new gsl.LabelField( {
			id :'wuliuLianxiren',
			name :'wuliuLianxiren',
			fieldLabel :l_log_contacts_abnormal
		});

		// 物流异常联系人电话
		var wuliuLianxiTel = new gsl.LabelField( {
			id :'wuliuLianxiTel',
			name :'wuliuLianxiTel',
			fieldLabel :l_log_contacts_abnormal_tel
		});

		// 联系人手机
		var wuliuMobile = new gsl.LabelField( {
			id :'wuliuMobile',
			name :'wuliuMobile',
			fieldLabel :l_log_contacts_abnormal_mobile
		});

		// 物流异常联系人邮箱
		var wuliuEmail = new gsl.LabelField( {
			id :'wuliuEmail',
			name :'wuliuEmail',
			fieldLabel :l_log_contacts_abnormal_mail
		});

		/*// 发货方代码
		var fahuofangId = new gsl.LabelField( {
			id :'fahuofangId',
			name :'fahuofangId',
			fieldLabel :'发货方代码'
		});*/

		// 发货方地址
		var fahuofangAddr = new gsl.LabelField( {
			id :'fahuofangAddr',
			name :'fahuofangAddr',
			fieldLabel :l_delivery_address
		});

		// 供应商代码
		var supplierNo = new gsl.LabelField( {
			id :'supplierNo',
			name :'supplierNo',
			fieldLabel :l_supplierId
		});
		// 父供应商代码
		var parentNo = new gsl.LabelField( {
			id :'parentNo',
			name :'parentNo',
			fieldLabel :l_father_supplier_no
		});

		// 备料天数
		var beiliaoTime = new gsl.LabelField( {
			id :'beiliaoTime',
			name :'beiliaoTime',
			fieldLabel :l_preparation_time
		});
		var erpEMail = new gsl.LabelField( {
			id :'erpEMail',
			name :'erpEMail',
			fieldLabel :l_erp_mail
		});
		var xuqiuFax = new gsl.LabelField( {
			id :'xuqiuFax',
			name :'xuqiuFax',
			fieldLabel :l_order_contacts_fax
		});
		var fahuoFax = new gsl.LabelField( {
			id :'fahuoFax',
			name :'fahuoFax',
			fieldLabel :l_delivery_contacts_fax
		});
		var wuliuFax = new gsl.LabelField( {
			id :'wuliuFax',
			name :'wuliuFax',
			fieldLabel :l_log_contacts_abnormal_fax
		});
		var wuliuTime = new gsl.LabelField( {
			id :'wuliuTime',
			name :'wuliuTime',
			fieldLabel :l_transit_time
		});
		queryForm = new gsl.FormPanel(
				{
					renderTo :'queryDiv',
					width :(Ext.get('queryDiv').getWidth() > 800 ?Ext.get('queryDiv').getWidth():800),
//					region : 'center',
					labelWidth :180,
					items : [ {
						layout :'column',
						items : [
								{
									layout :'form',
									columnWidth :.5,
									items : [ supplierNo ]//, txtUserName
								},
								{
									layout :'form',
									labelWidth :140,
									columnWidth :.5,
									items : [ parentNo ]//, addr
								}]
					},{
						layout :'column',
						items : [
								{
									columnWidth :.55,
									layout :'form',
									items : [ txtChineseName]
								},
								{
									columnWidth :.45,
									labelWidth :80,
									layout :'form',
									items : [ youZhengBianMa]
								}]
					},{
						layout :'column',
						items : [
								{
									columnWidth :1,
									layout :'form',
									items : [ detailAddr]
								}]
					},{
						layout :'column',
						items : [
								{
									columnWidth :.5,
									layout :'form',
									items : [ erpLianxiren,erpEMail]
								},
								{
									columnWidth :.5,
									labelWidth :140,
									layout :'form',
									items : [ erpTel]
								}]
					}, {
						layout :'column',
						items : [
								{
									layout :'form',
									columnWidth :.5,
									items : [[], xuqiuLianxiren, xuqiuLianxiTel,xuqiuEmail]
								},
								{
									layout :'form',
									columnWidth :.5,
									labelWidth :140,
									items : [[], xuqiuMobile,xuqiuFax]
								} ]
					}, {
						layout :'column',
						items : [
								{
									layout :'form',
									columnWidth :.5,
									items : [[], fahuoLianxiren,fahuoLianxiTel,fahuoEmail]
								},
								{
									layout :'form',
									columnWidth :.5,
									labelWidth :140,
									items : [ [],fahuoMobile,fahuoFax ]
								}]
					}, {
						layout :'column',
						items : [
								{
									layout :'form',
									columnWidth :.5,
									items : [[],wuliuLianxiren, wuliuLianxiTel,wuliuEmail]
								},
								{
									layout :'form',
									columnWidth :.5,
									labelWidth :140,
									items : [ [],wuliuMobile,wuliuFax ]
								} ]
					}, {
						layout :'column',
						items : [
								{
									layout :'form',
									columnWidth :.5,
									items : [wuliuTime]
								},
								{
									layout :'form',
									columnWidth :.5,
									labelWidth :140,
									items : [beiliaoTime ]
								}, {
									columnWidth :1,
									layout :'form',
									items : [ fahuofangAddr  ]
								} ]
					} ]
				});
		queryForm.load( {
			url :'pub/supManage-querySupplierInfoBySupplierNo.action',
			params : {
				'supVO.supplierNo' :supplierNoServer
			}
		});
		function close() {
//			供应商信息查看
			gsl.closeTab({title:l_supplierInfoInspect});
		}
		adjWidthCtrs = [queryForm];
//		var viewport = new Ext.Viewport( {
//			layout : 'border',
//			items:[queryForm]
//		});
	});
