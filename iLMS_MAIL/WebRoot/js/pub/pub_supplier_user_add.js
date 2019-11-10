/**
 * 供应商用户录入
 */
// 重写隐藏事件
Ext.onReady( function() {
	
	
	
	
			Ext.override(
							Ext.layout.FormLayout,
							{
								renderItem : function(c, position, target) {
									if (c && !c.rendered && c.isFormField
											&& c.inputType != 'hidden') {
										var args = [
												c.id,
												c.fieldLabel,
												c.labelStyle || this.labelStyle
														|| '',
												this.elementStyle || '',
												typeof c.labelSeparator == 'undefined' ? this.labelSeparator
														: c.labelSeparator,
												(c.itemCls
														|| this.container.itemCls || '')
														+ (c.hideLabel ? ' x-hide-label'
																: ''),
												c.clearCls || 'x-form-clear-left' ];
										if (typeof position == 'number') {
											position = target.dom.childNodes[position]
													|| null;
										}
										if (position) {
											c.itemCt = this.fieldTpl
													.insertBefore(position,
															args, true);
										} else {
											c.itemCt = this.fieldTpl.append(
													target, args, true);
										}
										c.actionMode = 'itemCt';
										c.render('x-form-el-' + c.id);
										c.container = c.itemCt;
										c.actionMode = 'container';
									} else {
										Ext.layout.FormLayout.superclass.renderItem
												.apply(this, arguments);
									}
								}
							});
			Ext.override(Ext.form.TriggerField, {
				actionMode :'wrap',
				onShow :Ext.form.TriggerField.superclass.onShow,
				onHide :Ext.form.TriggerField.superclass.onHide
			});
			Ext.override(Ext.form.Checkbox, {
				actionMode :'wrap',
				getActionEl :Ext.form.Checkbox.superclass.getActionEl
			});
			Ext.override(Ext.form.HtmlEditor, {
				actionMode :'wrap'
			});

			// 长度check常量
			// 登录名
			var MAX_LENGTH_USERNAME = 15;
			// 密码最小长度
			var MIN_LENGTH_USERPWD = 6;
			// 密码最大长度
			var MAX_LENGTH_USERPWD = 20;
			// 用户名
			var MAX_LENGTH_NAME = 15;
			// 电话
			var MAX_LENGTH_TEL = 20;
			// 手机
			var MAX_LENGTH_MOBILE = 20;
			// 传真
			var MAX_LENGTH_FAX = 20;
			// 邮箱
			var MAX_LENGTH_EMAIL = 64;

			// 登录名
			var txtUserName = new gsl.TextField( {
				fieldLabel :l_loginName,
				allowBlank :false,
				dataIndex :'loginId',
				maxLength :MAX_LENGTH_USERNAME,
				vtype :'alphanum',
				name :'user.userName',
				tabIndex :3,
				width :150
			});

			// 密码
			var txtPassword = new gsl.TextField( {
				fieldLabel :l_pwd,
				allowBlank :false,
				minLength :MIN_LENGTH_USERPWD,
				maxLength :MAX_LENGTH_USERPWD,
				tabIndex :5,
				inputType :'password',
				name :'user.userPwd',
				width :150
			});

			// 确认密码
			var txtConfirmPassword = new gsl.TextField(
					{
						fieldLabel :l_confirmPwd,
						inputType :'password',
						minLength :MIN_LENGTH_USERPWD,
						maxLength :MAX_LENGTH_USERPWD,
						allowBlank :false,
						invalidText :l_twoPwdDifferent,
						tabIndex :6,
						name :'user.confirmUserPwd',
						validator : function() {
							if (txtPassword.getValue() == txtConfirmPassword
									.getValue()) {
								return true;
							} else {
								return false;
							}
						},
						width :150
					});
			// 姓名
			var txtName = new gsl.TextField( {
				fieldLabel :l_user_name,
				maxLength :MAX_LENGTH_NAME,
				allowBlank :false,
				tabIndex :4,
				name :'user.name',
				width :150
			});
			// 电话
			var txtTelephone = new gsl.TextField( {
				fieldLabel :l_tel,
				allowBlank :false,
				maxLength :MAX_LENGTH_TEL,
				tabIndex :7,
				dataIndex :'tel',
				name :'user.tel',
				width :150,
				vtype :'telephone'
			});

			// 传真
			var txtFax = new gsl.TextField( {
				fieldLabel :l_fax,
				maxLength :MAX_LENGTH_FAX,
				tabIndex :9,
				dataIndex :'fax',
				name :'user.fax',
				width :150,
				vtype :'telephone'
			});
			// 手机
			var txtMobile = new gsl.TextField( {
				fieldLabel :l_mobilePhone,
				maxLength :MAX_LENGTH_MOBILE,
				tabIndex :8,
				dataIndex :'mobile',
				name :'user.mobile',
				width :150,
				vtype :'mobile'
			});
			// 邮箱
			var txtEmail = new gsl.TextField( {
				fieldLabel :l_email_supplier,
				maxLength :MAX_LENGTH_EMAIL,
				vtype :'email',
				dataIndex :'email',
				tabIndex :10,
				allowBlank :false,
				name :'user.email',
				width :200
			});

			// //
			// var checkGroup = new Ext.form.CheckboxGroup({
			// fieldLabel: '角色',
			// allowBlank:false,
			// columns:4,
			// msgTarget:'side',
			// items:items,
			// anchor:'90%'
			// })

			//公司代码Store
			var relativeStore = new gsl.JsonCmbStore( {
				type :'relativeId',
				addBlank :false,
				param :''
			});
			
			// 角色相应选项cmb
			var relativeCmb = new gsl.ComboBox( {
				fieldLabel :l_supplierId,
				id :'suplogno',
				readOnly :true,
				hidden :true,
				tabIndex :2,
				store :relativeStore,
				triggerAction :'all',
				allowBlank :false,
				hiddenName :'user.supplierNo',
				width :150,
				// 追加选择后,用户名前面自动加供应商代码
				listeners :( {
					'select' : function() {
						txtUserName.setValue(relativeCmb.getRawValue());
					}
				})
			});

			// 角色类型
			var roleType_cbx = new gsl.BaseDataComboBox(
					{
						fieldLabel :l_roleType,
						allowBlank :false,
						readOnly :true,
						// emptyText :'角色类型',
						triggerAction :'all',
						autoLoad :true,
						width :150,
						tabIndex :1,
						hiddenName :'user.roleType',
						id :'roleType_cbx',
						baseData : supRoleTypeArray,
						listeners : {
							'beforeselect' : function(combo,record,index) {
								// opts.url
//								var conn = Ext.lib.Ajax.getConnectionObject().conn; 
//								// 防止刷新无效
//								var url = 'comm-cmb.action?type=supplierRole&param='+ record.get('code')+'&temptime='+ (new Date().getTime().toString(36));
//							    conn.open("GET", url, false);
//							    conn.send();
//							    var response = Ext.decode(conn.responseText);
//							    if(response.length > 0){
//							    	return true;
//							    }
////							    "角色不存在,请在角色权限管理画面创建角色"
//							    gsl.InfoAlert(record.get('name') + l_role_not_exist_pls_create, function() {
//								});
//								return false;
							},
							'select' : function() {
								// 如果相关的代码cmb是隐藏的就让他显示出来
							if (relativeCmb.hidden) {
								relativeCmb.clearInvalid();
								relativeCmb.show();
							}
							// 查询面板元素的所有子节点，找到id为suplogno的字段对应的label元素
							var label = Ext.getCmp('userAddForm').el
									.child(
											'label[class="x-form-item-label"][for="suplogno"]',
											true);
							if (label) {
								if (this.getValue() == SUP_ROLE_TYPE_SUPPLIER) {
									// 给元素重新赋值
									label.innerHTML = '供应商代码：<font color=red>*</font>';
								} else if (this.getValue() == SUP_ROLE_TYPE_TRANSIT) {
									// 给元素重新赋值
									label.innerHTML = '中转仓代码：<font color=red>*</font>';
								} else if (this.getValue() == SUP_ROLE_TYPE_WX) {
									// 给元素重新赋值
									label.innerHTML = '外协仓代码：<font color=red>*</font>';
								}
							}
							relativeStore.baseParams = {
								'param' :this.getValue(),
								'type' :'relativeId',
								'addBlank' :false
							};
//							// 20100818 zhangye 追加 自动清值
							relativeCmb.clearValue();
							txtUserName.reset();
							relativeStore.load({
								callback : function(){
									
								}
							});
							
							if (Ext.getCmp('role_cbx') != null) {
								userAddForm.remove('role_cbx');
							}
							role_cbx = new gsl.CheckboxGroup(
									{
										fieldLabel :l_role,
										allowBlank :false,
										columns :3,
										id :'role_cbx',
										msgTarget :'side',
										anchor :'90%',
										url :'comm-cmb.action?type=supplierRole&param=' + roleType_cbx.getValue()
									});
							userAddForm.add(role_cbx);
							userAddForm.doLayout();
						}
						}
					});


			var userAddForm = new gsl.FormPanel( {
				iconCls :'titEntry',
				frame :true,
				id :'userAddForm',
				width :(Ext.get('entryDiv').getWidth() > 800 ? Ext.get('entryDiv').getWidth() : 800),
				labelWidth :85,
				renderTo :'entryDiv',
				items : [
						{
							layout :'column',
							items : [ {
								columnWidth :.44,
								layout :'form',
								items : [ roleType_cbx ]
							} ]
						},
						{
							layout :'column',
							items : [ {
								columnWidth :.44,
								layout :'form',
								items : [ relativeCmb ]
							} ]
						},
						{
							layout :'column',
							items : [
									{
										columnWidth :.44,
										layout :'form',
										items : [ txtUserName, txtPassword,
												txtTelephone, txtFax ]
									},
									{
										columnWidth :.44,
										layout :'form',
										items : [ txtName, txtConfirmPassword,
												txtMobile, txtEmail ]
									} ]
						} ],
				buttons : [ {
//					保存
					text :l_save,
					tabIndex :60,
					handler :saveUser
				} ]
			});

			/**
			 * 新增处理
			 */
			function saveUser() {
				if (!userAddForm.form.isValid()) {
					return;
				}
				var roles = [];
				role_cbx.items.each( function(item) {
					if (item.getValue()) {
						roles.push(item.getRawValue());
					}
				});

				userAddForm.submit( {
					url :'pub/user-insertSupplierUser.action',
					params : {
						roleIds : roles
					},
					success : function() {
						userAddForm.getForm().reset();
					}
				});
			}
			adjWidthCtrs = [ userAddForm ];
		});
