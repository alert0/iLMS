/** 分组人员维护 */
Ext.onReady(function() {
	var pageAuth = 'SP';//gsl.getPageAuth('010202');
	var busyFlg = false;
	var selectedGroupId = ''; //选中的分组名称

		//**************数据Store*************
		//分组数据
		var store = new gsl.JsonStore( {
			url : 'email/msg-queryGroupForPage.action',
			fields : [ 'groupCode', 'groupName', 'pkId' ]
		});
		store.on( {
			'load' : function() {
				busyFlg = false;
				selectedGroupId = '';
				configStore.removeAll();
			}
		});

		//已配置的人员
		var configStore = new gsl.JsonStore( {
			url : 'email/msg-queryUserForPage.action',
			fields : [ 'pkId', 'groupCode', 'userCname', 'mobile', 'email',
					'userId','department', 'sortId']
		});

		//未配置的人员
		var notConfigStore = new gsl.JsonStore( {
			url : 'email/msg-queryNotUserForPage.action',
			fields : [ 'groupCode', 'userId', 'userCname', 'email', 'mobile','department']
		});

		//*******************查询面板***********
		// 分组代码
		var groupCode = new gsl.TextField( {
			fieldLabel : '分组代码',
			id : 'groupCode',
			width : 150
		});

		//分组名称
		var groupName = new gsl.TextField( {
			fieldLabel : '分组名称',
			id : 'groupName',
			width : 150
		});

		//修改方法
		var modifyFn = function() {
			suppModifyForm.submit( {
				url : 'email/msg-updateGroup.action',
				success : function(form, action) {
					store.reload();
					suppModifyWin.hide();
				}
			});
		};

		//删除
		var removeFn = function(record) {
			gsl.RowAction( {
				url : 'email/msg-deleteGroup.action',
				actionType : 'remove',
				params : {
					'pageVO.pkId' : record.get('pkId'),
					'pageVO.groupCode' : record.get('groupCode')
				},
				success : function() {
					store.reload();
				}
			});
		}

		//行操作
		var actionColumn = new gsl.RowActions( {
			auth : 'SP',
			edit : function(record) {
				suppModifyWin.show();
				suppModifyForm.getForm().setValues(record.data);
			},
			remove : removeFn
		});

		/**
		 * 新增
		 */
		var add = function() {
			suppAddForm.submit( {
				url : 'email/msg-addGroup.action',
				success : function() {
					suppAddForm.getForm().reset();
					suppAddWin.hide();
					store.reload();
				}
			});
		}

		//界面权限工具条
		var toolBar = new gsl.Toolbar( {
			auth : pageAuth,
			tools : [ {
				tagValue : '01020101',
				text : '新增',
				iconCls : 'add',
				handler : function() {
					suppAddWin.show()
				}
			} ]
		});

		//查询方法
		function queryFn() {
			if (queryForm.form.isValid()) {
				if (busyFlg) {
					return;
				}
				busyFlg = true;
				//			selectedLogCompanyCode = '';
				store.baseParams = {
					'pageVO.groupName' : groupName.getValue(),
					'pageVO.groupCode' : groupCode.getValue()
				};
				//store.reload();
				store.load( {
					params : {
						start : 0,
						limit : pageSize
					}
				});
			}
		}

		// 查询条件面板
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
					items : [ groupName ]
				},{
					columnWidth : .33,
					layout : 'form',
					border : false,
					items : [ groupCode ]
				   } ]
			} ],
			buttons : [{
				text : l_query,
				handler : queryFn
			}],
			keys : [{
				key : 13,
				fn : queryFn,
				scope : this
			}]
		});

		// 分组分页栏
		var pagingBar = new gsl.PagingToolbar( {
			store : store
		});

		//新增
		var suppAddForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : true,
			width : 500,
			labelWidth : 120,
			labelAlign : 'right',
			frame : true,
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .9,
					layout : 'form',
					border : false,
					items : [ new gsl.TextField({
						fieldLabel : '分组代码',
						regex : /^[0-9a-zA-Z_]{1,}$/,
						regexText : '不能输入汉字',
						minLength : 0,
						allowBlank : false,
						maxLength : 10,
						width : 150,
						name : 'pageVO.groupCode'
							}), new gsl.TextField( {
						fieldLabel : '分组名称',
						minLength : 0,
						allowBlank : false,
						maxLength : 10,
						width : 150,
						name : 'pageVO.groupName'
					//	anchor :'95%'
							}) ]
				} ]
			} ],
			buttons : [ {
				text : '保存',
				handler : function() {
					add();
				}
			}, {
				text : '关闭',
				handler : function() {
					suppAddWin.hide();
				}
			} ]
		});

		//新增窗口
		var suppAddWin = new gsl.Window( {
			title : '数据添加',
			titleCollapse : true,
			autoScroll : true,
			width : 400,
			height : 150,
			items : [ suppAddForm ]
		});

		//++++++++++++++++++++++++新增界面控件

		//姓名
		var USER_CNAME = new gsl.TextField( {
			fieldLabel : '姓名',
			width : 120,
			name : 'pageVO.userCname'
		});

		var EMAIL = new gsl.TextField( {
			fieldLabel : '邮箱',
			width : 120,
			vtype : 'email',
			name : 'pageVO.email'
		});

		var MOBILE = new gsl.TextField( {
			fieldLabel : '电话',
			width : 120,
			name : 'pageVO.mobile'
		});
		
		var SORT_ID = new gsl.TextField( {
			fieldLabel : '顺序',
			width : 120,
			allowBlank : false,
			name : 'pageVO.sortId'
		});

		var AddForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : false,
			width : 300,
			labelAlign : 'right',
			labelWidth : 75,
			frame : true,
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ USER_CNAME, EMAIL ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ MOBILE, SORT_ID ]
				} ]
			} ],
			buttons : [ {
				text : '保存',
				handler : function() {
					add1();
				}
			}, {
				text : '关闭',
				handler : function() {
					AddWin.hide();
				}
			} ]
		});

		//新增窗口
		var AddWin = new gsl.Window( {
			title : '新增例外人员',
			titleCollapse : true,
			autoScroll : false,
			width : 500,
			height : 150,
			items : [ AddForm ]
		});

		//新增方法
		var add1 = function() {
			AddForm.submit( {
				url : 'email/msg-addExcUser.action',
				params : {
					'pageVO.groupCode' : selectedGroupId
				},
				success : function() {
					configStore.reload();
					AddForm.getForm().reset();
					AddWin.hide();
				}
			});
		};
		//*****************************************************************

		var suppModifyForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : true,
			width : 500,
			labelWidth : 120,
			labelAlign : 'right',
			frame : true,
			//        border :false,s
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .9,
					layout : 'form',
					border : false,
					items : [ new Ext.form.Hidden( {
						dataIndex : 'pkId',
						name : 'pageVO.pkId'
					}), new gsl.TextField( {
						fieldLabel : '分组名称',
						width : 150,
						dataIndex : 'groupName',
						allowBlank : false,
						name : 'pageVO.groupName'
					//					anchor :'95%'
							}) ]
				} ]
			} ],
			buttons : [ {
				text : '保存',
				handler : function() {
					modifyFn();
				}
			}, {
				text : '关闭',
				handler : function() {
					suppModifyWin.hide();
				}
			} ]
		});

		//修改窗口
		var suppModifyWin = new gsl.Window( {
			title : '修改分组名称',
			titleCollapse : true,
			width : 400,
			height : 150,
			items : [ suppModifyForm ]
		});

		//物流公司列表
		var gridPanel = new gsl.GridPanel( {
			region : 'west',
			width : '50%',
			iconCls : 'titList',
			title : '分组信息',
			tbar : toolBar,
			store : store,
			cm : new Ext.grid.ColumnModel( [ new gsl.RowNumberer(),
					actionColumn, {
						header : '分组代码',
						dataIndex : 'groupCode',
						width : 100
					}, {
						header : '分组名称',
						dataIndex : 'groupName',
						width : 150
					} ]),
			bbar : pagingBar,
			listeners : {
				rowclick : function(grid, rowIndex, e) {
					selectedGroupId = grid.getStore().getAt(rowIndex).get(
							'groupCode');
					configStore.baseParams = {
						'groupCode' : selectedGroupId
					};
					configStore.load( {
						params : {
							start : 0,
							limit : pageSize
						}
					});
				}
			},
			plugins : [ actionColumn ]
		});

		//已配置人员分页栏
		var configPagingBar = new gsl.PagingToolbar( {
			store : configStore
		});

		//已配置人员工具条
		var configToolBar = new gsl.Toolbar( {
			auth : pageAuth,
			tools : [ {
				tagValue : '01010101',
				text : '系统人员',
				iconCls : 'add',
				handler : function() {
					if (Ext.isEmpty(selectedGroupId, false)) {
						gsl.ErrorAlert('请先选中一条分组名称!');
						return;
					}
					notConfigSupWin.show();
					queryNotConfigSupFn();
				}
			}, {
				tagValue : '01010101',
				text : '例外人员',
				iconCls : 'add',
				handler : function() {
					if (Ext.isEmpty(selectedGroupId, false)) {
						gsl.ErrorAlert('请先选中一条分组名称!');
						return;
					}
					AddWin.show();
					queryNotConfigSupFn();
				}
			}, {
				tagValue : '01010101',
				text : '删除',
				iconCls : 'gridRowDelete',
				handler : function() {
					if (Ext.isEmpty(selectedGroupId, false)) {
						gsl.ErrorAlert('请先选中一条分组名称!');
						return;
					}
					{
						var check = configCheckBox.getSelections();
						var lis = new Array();
						var arr = new Array();
						for ( var i = 0; i < check.length; i++) {
							arr.push(check[i].get("pkId"));
						}
						lis.push(arr);

						if (arr.length <= 0) {
							new gsl.MsgAlert( {
								msg : '请至少选择一条记录删除'
							});
							return;
						}
						gsl.RequestWaitAction( {
							actionNm : '批量删除',
							url : 'email/msg-batchDeleteUser.action',
							params : {
								'pageVO.delList' : lis
							},
							success : function(response) {
								new gsl.MsgAlert( {
									msg : '批量删除成功！'
								});
								configStore.reload();
							}
						});
					}

				}
			} ]
		});

		//修改方法
		var modifyFn1 = function() {
			modifyForm.submit( {
				url : 'email/msg-updateUser.action',
				success : function(form, action) {
					configStore.reload();
					modifyWin.hide();
				}
			});
		};
		
		//修改From
		var modifyForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : true,
			width : 500,
			labelWidth : 120,
			labelAlign : 'right',
			frame : true,
			//        border :false,s
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .9,
					layout : 'form',
					border : false,
					items : [ new Ext.form.Hidden( {
						dataIndex : 'pkId',
						name : 'pageVO.pkId'
					}), new gsl.TextField( {
						fieldLabel : '姓名',
						width : 150,
						dataIndex : 'userCname',
						allowBlank : false,
						name : 'pageVO.userCname'
					//					anchor :'95%'
							}), new gsl.TextField( {
						fieldLabel : '邮箱',
						vtype : 'email',
						width : 150,
						dataIndex : 'email',
						allowBlank : false,
						name : 'pageVO.email'
					//					anchor :'95%'
							}), new gsl.TextField( {
						fieldLabel : '手机',
						width : 150,
						dataIndex : 'mobile',
						name : 'pageVO.mobile'
					//					anchor :'95%'
							}), new gsl.TextField( {
								fieldLabel : '顺序',
								width : 150,
								dataIndex : 'sortId',
								allowBlank : false,
								name : 'pageVO.sortId'
							//					anchor :'95%'
									}) ]
				} ]
			} ],
			buttons : [ {
				text : '保存',
				handler : function() {
					modifyFn1();
				}
			}, {
				text : '关闭',
				handler : function() {
					modifyWin.hide();
				}
			} ]
		});

		//修改窗口
		var modifyWin = new gsl.Window( {
			title : '修改供应商组',
			titleCollapse : true,
			width : 400,
			height : 200,
			items : [ modifyForm ]
		});
		
		
		var configCheckBox = new Ext.grid.CheckboxSelectionModel();
		
		//行操作
		var actionColumn1 = new gsl.RowActions( {
			auth : 'SP',
			edit : function(record) {
				if ('例外人员' != record.get('userId')) {
					gsl.ErrorAlert('不能进行修改操作');
					return;
				}
				modifyWin.show();
				modifyForm.getForm().setValues(record.data);
			}
		});

		//已配置分组人员列表
		var configSupPanel = new gsl.GridPanel( {
			region : 'center',
			iconCls : 'titList',
			title : '分组人员信息',
			store : configStore,
			sm : configCheckBox,
			cm : new Ext.grid.ColumnModel( [ new gsl.RowNumberer(),
					configCheckBox, actionColumn1, {
						// id
						dataIndex : 'pkId',
						hidden : true
					}, {
						header : '顺序',
						dataIndex : 'sortId',
						width : 50
					}, {
						header : '人员类型',
						dataIndex : 'userId',
						width : 90
					}, {
						header : '姓名',
						dataIndex : 'userCname',
						width : 100
					},{
						header : '部门',
						dataIndex : 'department',
						width : 100
					},{
						header : '邮箱',
						dataIndex : 'email',
						width : 120
					}, {
						header : '手机',
						dataIndex : 'mobile',
						width : 100
					} ]),
			tbar : configToolBar,
			bbar : configPagingBar,
			plugins : [ actionColumn1 ]
		});



		// 用户名称
		var txtUserId = new gsl.TextField( {
			fieldLabel : '用户名称/姓名',
			anchor :'99%'
		});
		
		// 部门
		var txtDepartment = new gsl.TextField( {
			fieldLabel : '部门',
			anchor :'99%'
		});
		

		//未配置人员查询
		var notConfigQueryForm = new gsl.FormPanel( {
			region : 'north',
			height : 50,
			labelWidth : 100,
			items : [ {
				layout : 'column',
				border : false,
				items : [{
				width :200,
				layout :'form',
				border :false,
				items : [ txtUserId ]
			},{
				width :200,
				layout :'form',
				border :false,
				items : [ txtDepartment ]
			},{
				columnWidth :1,
				layout :'form',
				border :false,
				items : [ new Ext.Button( {
					text :'查询',
					handler : function(){
						queryNotConfigSupFn();
					}
				}) ]
			}]
//				items : [ {
////					columnWidth : 1,
//					layout : 'column',
//					border : false,
//					width :200,
//					items : [ {
//						columnWidth : .5,
//						layout : 'form',
//						border : false,
//						width :200,
//						items : [txtUserId  ]
//					},{
//						columnWidth : .5,
//						layout : 'form',
//						border : false,
//						items : [txtDepartment]
//				}]
//				},{
//					columnWidth : .1,
//					layout : 'form',
//					border : false,
//					items : [ new Ext.Button( {
//						text : l_query,
//						handler : queryNotConfigSupFn
//					}) ]
//				} ]
			} ],
			buttons : [],
			keys : []
		});

		//未配置人员分页栏
		var notConfigPagingBar = new gsl.PagingToolbar( {
			store : notConfigStore
		});

		var notConfigCheckBox = new Ext.grid.CheckboxSelectionModel();

		//未配置的人员列表
		var notConfigPanel = new gsl.GridPanel( {
			region : 'center',
			iconCls : 'titList',
			title : '未配置的用户列表',
			store : notConfigStore,
			sm : notConfigCheckBox,
			cm : new Ext.grid.ColumnModel( [ new gsl.RowNumberer(),
					notConfigCheckBox, {
						header : '用户名称',
						dataIndex : 'userId',
						width : 80
					}, {
						header : '姓名',
						dataIndex : 'userCname',
						width : 95
					}, {
						header : '部门',
						dataIndex : 'department',
						width : 90
					},{
						header : '电话',
						dataIndex : 'mobile',
						width : 85
					}, {
						header : '邮箱',
						dataIndex : 'email',
						width : 120
					} ]),
			bbar : notConfigPagingBar,
			plugins : []
		});

		//未配置人员弹窗
		var notConfigSupWin = new gsl.Window(
				{
					title : '增加分组配置',
					titleCollapse : true,
					autoScroll : true,
					layout : 'border',
					width : 550,
					height : 500,
					items : [ notConfigQueryForm, notConfigPanel ],
					buttons : [
							{
								text : '确定',
								handler : function() {
									if (Ext.isEmpty(selectedGroupId, false)) {
										gsl.ErrorAlert('请先选中一条数据!');
										return;
									}
									var selectedRecords = notConfigPanel
											.getSelectionModel()
											.getSelections();
									if (selectedRecords.length == 0) {
										gsl.ErrorAlert('请至少选择一条数据!');
										return;
									}
									var userIdArr = [];
									for ( var i = 0; i < selectedRecords.length; i++) {
										userIdArr.push(selectedRecords[i].get('userId'));
									}

									gsl.RequestAction( {
												actionNm : '配置系统人员',
												url : 'email/msg-addUser.action',
												params : {
													'GroupCode' : selectedGroupId,
													'userIdArr' : userIdArr
												},
												success : function(t, response,
														options) {
													configStore.reload();
													notConfigSupWin.hide();
												}
											});
								}
							}, {
								text : '关闭',
								handler : function() {
									notConfigSupWin.hide();
								}
							} ]
				});

		//未配置人员数据查询
		function queryNotConfigSupFn() {
			if (notConfigQueryForm.form.isValid()) {
				if (Ext.isEmpty(selectedGroupId, false)) {
					gsl.ErrorAlert('请先选中一条分组人员数据!');
					return;
				}

				notConfigStore.baseParams = {
					'pageVO.groupCode' : selectedGroupId,
					'pageVO.userId' : txtUserId.getValue(),
					'pageVO.department' : txtDepartment.getValue()
				};
				notConfigStore.load( {
					params : {
						start : 0,
						limit : pageSize
					}
				});
			}
		}

		//界面布局
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ queryForm, gridPanel, configSupPanel ]
		});
	});