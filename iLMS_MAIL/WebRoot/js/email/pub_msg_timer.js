/** 定时器 */
Ext.onReady(function() {
	var pageAuth = 'SP';
	var busyFlg = false;
	var runStatusArray = [ [ 0, '停止' ], [ 1, '运行中' ] ];

	var store = new gsl.JsonStore( {
		url : 'email/email-queryEmailTimerForPage.action',
		fields : [ 'pkId', 'timerCode', 'timerDesc', 'runState',
				new gsl.StoreDateField('startTime'), 'lastRunTime',
				'nextRunTime', 'timerParam', 'modifyDateStr', 'groupCode' ]
	});
	store.on('load', function() {
		busyFlg = false
	});

	// 定时器编码
		var timerCode = new gsl.TextField( {
			fieldLabel : '定时器编码',
			id : 'timerCode',
			width : 150
		});

		// 定时器描述
		var timerDesc = new gsl.TextField( {
			fieldLabel : '定时器描述',
			id : 'timerDesc',
			width : 150
		});

		// 运行状态
		var runState = new gsl.BaseDataComboBox( {
			valueField : 'code',
			baseData : runStatusArray,
			readOnly : true,
			addBlank : true,
			fieldLabel : '运行状态',
			width : 150
		});

		var queryBtn = new Ext.Button( {
			text : '查询',
			handler : queryFn
		});

		//查询面板
		var queryPanel = new gsl.FormPanel( {
			region : 'north',
			height : 120,
			iconCls : 'titQuery',
			hideCollapseTool : false,
			collapsible : true,
			labelWidth : 120,
			items : [ {
				layout : 'column',
				border : false,
				items : [ {
					layout : 'form',
					columnWidth : .33,
					border : false,
					items : [ timerCode ]
				}, {
					layout : 'form',
					columnWidth : .33,
					border : false,
					items : [ timerDesc ]
				}, {
					layout : 'form',
					columnWidth : .33,
					border : false,
					items : [ runState ]
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

		//查询方法
		function queryFn() {
			if (queryPanel.form.isValid()) {
				if (busyFlg) {
					return

				}
				busyFlg = true;
				store.baseParams = {
					'pageVO.timerCode' : timerCode.getValue(),
					'pageVO.timerDesc' : timerDesc.getValue(),
					'pageVO.runState' : runState.getValue()
				};
				store.load( {
					params : {
						start : 0,
						limit : pageSize
					}
				});
			}

		}
		//************************查询结束***********************************************   
		//行操作
		function useFn(record){
				if('停止' != record.get('runState')){
			
   		gsl.RowAction( {
	        url : 'email/email-updateRunstate.action',
	        actionType : 'disable',
	        params : {
	        	'pageVO.pkId' : record.get('pkId') 
	        },
	        success : function() {
	            store.reload();
	        }
	    });
   		}else{
   			gsl.ErrorAlert('定时器已停止');
				return;
   		}
		}
		var actionColumn = new gsl.RowActions( {
			auth : 'SP',
			edit : 	{cb:showModifyWin, hide : 'runState == "运行中"'},
			config : function(record) {
				configForm.form.reset();
				configWin.show();
				configForm.getForm().setValues(record.data);
				var groupCode = record.get('groupCode');
				var groupCodes = groupCode.split(',');
				checkGroup.items.each(function(item) {
					for ( var i = 0; i < groupCodes.length; i++) {
						if (item.inputValue == groupCodes[i]) {
							item.setValue(true);
							return
						}
					}
				});
				},disable : {cb:useFn, hide:'runState == "停止"'}	
		});

		
		//定时器管理信息模块
		var infoModel = new Ext.grid.ColumnModel( [ 
			new gsl.RowNumberer(),
				actionColumn, {
					header : '定时器编码',
					dataIndex : "timerCode",
					width : 100
				}, {
					header : '定时器描述',
					dataIndex : "timerDesc",
					width : 220
				},  {
					header : '运行状态',
					dataIndex : 'runState',
					cmbData : runStatusArray,
					width : 70,
					renderer : function(v, m, rd, r, c, s) {
			     if(v == '运行中'){
						m.attr = 'style="color:green;"'
						}else if(v == '停止')
							m.attr = 'style="color: red;"'
						return v;
					}
				}, new gsl.DateColumn( {
					header : '开始运行时间',
					dateFormat : "Y-m-d H:i:s",
					dataIndex : 'startTime',
					width : 120
				}), {
					header : '最后运行时间',
					width : 120,
					dataIndex : "lastRunTime"
				}, {
					header : '下次运行时间',
					width : 120,
					dataIndex : "nextRunTime"
				}, {
					header : '定时器参数',
					width : 100,
					dataIndex : "timerParam"
				}, {
					header : "修改时间",
					dataIndex : 'modifyDateStr',
					align : 'left',
					width : 120
				//				renderer: function (value){
				////					var result = value.toISOString().slice(0,20);
				////					var result = value.getFullYear()+'-'+(value.getMonth()+1)+'-'+value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
				//					var year = value.getFullYear();
				//					var month = (value.getMonth()+1).toString().length < 2 ? '0'+(value.getMonth()+1).toString() : (value.getMonth()+1).toString();
				//					var date = value.getDate().toString().length < 2 ? '0' + value.getDate().toString() : value.getDate().toString();
				//					var hours = value.getHours().toString().length < 2 ? '0' + value.getHours().toString() : value.getHours().toString();
				//					var minutes = value.getMinutes().toString().length < 2 ? '0' + value.getMinutes().toString() : value.getMinutes().toString();
				//					var seconds = value.getSeconds().toString().length < 2 ? '0' + value.getSeconds().toString() : value.getSeconds().toString();
				//					
				//					return year  + '-' + month+ '-' + date+ ' ' + hours+ ':' + minutes + ':' + seconds;
				//				}
				}
		//			new gsl.DateColumn({
				//			header : '生效日期',
				//			width: 80,
				//			dataIndex : "modifyDate"
				//			
				//		})

				]);

		/** ----------------------修改start------------------------- */
		var editpkId = new Ext.form.Hidden( {
			dataIndex : 'pkId',
			name : 'pageVO.pkId'
		});
		//定时器参数
		var txtTimerParam = new gsl.TextField( {
			fieldLabel : '定时器参数',
			id : 'txtTimerParam',
			maxLength : 50,
			//allowBlank :false,
			dataIndex : 'timerParam',
			name : 'pageVO.timerParam',
			width : 150
		});

		//定时器描述
		var txtTimerDesc = new gsl.TextField( {
			fieldLabel : '定时器描述',
			id : 'txtTimerDesc',
			maxLength : 50,
			//allowBlank :false,
			//readOnly: true,
			dataIndex : 'timerDesc',
			name : 'pageVO.timerDesc',
			width : 223
		});

		//开始时间
		var startTime = new Ext.form.DateField( {
			fieldLabel : "开始时间",
			id : 'startTime',
			format : 'Y-m-d H:i:s',
			//allowBlank :false,
			dataIndex : 'startTime',
			name : 'pageVO.startTime',
			width : 150
		});
		//运行状态
		var cbxRunState = new gsl.BaseDataComboBox( {
			baseData : runStatusArray,
			addBlank : false,
			//	allowBlank : false,
			fieldLabel : '运行状态',
			hiddenName : 'pageVO.runState',
			dataIndex : 'runState',
			valueFiled : 'code',
			//name :'pageVO.runState',
			width : 150
		});
		// 定时器编码
		var txtTimerCode = new gsl.LabelField( {
			fieldLabel : '定时器编码',
			width : 100,
			dataIndex : 'timerCode'
		});
        //人员分组
		var checkGroup = new gsl.CheckboxGroup( {
			fieldLabel : '人员分组',
			//allowBlank :false,
			//layout:'auto',
			columns : 2,
			id : 'groupCode',
			//autoWidth : true,
			msgTarget : 'side',
			//anchor:'95%',
			url : 'comm-cmb.action?type=uCode&temptime=' + (new Date()
					.getTime().toString(36))
		});

		//修改界面
		var modifyForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : true,
			width : 850,
			labelWidth : 95,
			labelAlign : 'right',
			frame : true,
			items : [
					{
						layout : 'column',
						items : [
								{
									columnWidth : .5,
									layout : 'form',
									border : false,
									items : [ txtTimerCode, txtTimerParam,
											editpkId, txtTimerDesc ]
								}, {
									columnWidth : .5,
									layout : 'form',
									border : false,
									items : [ startTime, cbxRunState ]
								} ]
					} ],
			buttons : [ {
				text : l_save_D12,
				handler : saveUpdate
			}, {
				text : l_close,
				handler : function() {
					modifyWin.hide()
				}
			} ]
		});

		//保存修改内容
		function saveUpdate () {
			modifyForm.submit( {
				url : 'email/email-update.action',
				
				success : function(form, action) {
					modifyWin.hide();
					queryFn()
				}
			})
		}
		
		//修改方法
		function showModifyWin(record) {
				modifyForm.form.reset();
				modifyWin.show();
				modifyForm.getForm().setValues(record.data);
		}

		//修改窗口
		modifyWin = new gsl.Window( {
			title : '修改定时器信息',
			titleCollapse : true,
			autoScroll : true,
			shadow : false,
			width : 850,
			height : 200,
			items : [ modifyForm ]
		});

//**********************************************************************
  //配置
		var saveConfigFn = function() {
			var groupCodes = [];
			checkGroup.items.each(function(item) {
				if (item.getValue()) {
					groupCodes.push(item.getRawValue())
				}
			});
			configForm.submit( {
				url : 'email/email-configGroup.action',
				params : {
					groups : groupCodes
				},
				success : function(form, action) {
					store.reload();
					configWin.hide()
				}
			});
		};
		
		// 定时器编码
		var lfTimerCode = new gsl.LabelField( {
			fieldLabel : '定时器编码',
			width : 80,
			dataIndex : 'timerCode'
		});
		var configForm = new gsl.FormPanel( {
			titleCollapse : true,
			autoScroll : true,
			width : 800,
			labelWidth : 100,
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
					}),// 定时器编码
		 lfTimerCode,checkGroup ]
				} ]
			} ],
			buttons : [ {
				text : '保存',
				handler : function() {
					saveConfigFn();
				}
			}, {
				text : '关闭',
				handler : function() {
					configWin.hide();
				}
			} ]
		});

		
		//配置窗口
		var configWin = new gsl.Window( {
			title : '配置分组名称',
			titleCollapse : true,
			shadow : false,
			width : 700,
			height : 250,
			items : [ configForm ]
		});
		
		
		var pagingBar = new gsl.PagingToolbar( {
			store : store,
			pageSize : pageSize
		});

		//字典信息面板
		var dictInfoPanel = new gsl.GridPanel( {
			region : 'center',
			iconCls : 'titList',
			title : '定时器信息一览',
			store : store,
			cm : infoModel,
			bbar : pagingBar,
			plugins : [ actionColumn ]
		});

		//界面布局
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ queryPanel, dictInfoPanel ]
		});
	})