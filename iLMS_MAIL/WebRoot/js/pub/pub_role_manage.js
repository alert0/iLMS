Ext.onReady( function() {
	var roleManagePageAuthRow = 'SP';
	/***************************************************************************
	 * 常量区
	 **************************************************************************/
	var ROLE_NAME_LENGTH = 20;
	var ROLE_DESC_LENGTH = 50;
	var busyFlg = false;
	/***************************************************************************
	 * 信息检索区
	 **************************************************************************/
	

	
	// 角色combobox
		var roleCmb = new gsl.ComboBox( {
			fieldLabel :'角色',
			readOnly :true,
			width : 150,
			store :new gsl.JsonCmbStore( {
				type :'role',
				addBlank :true
			}),
			triggerAction :'all'
		});
		// 部门combobox
		var queryDepartmentCmb = new gsl.ComboBox( {
			fieldLabel :'部门',
			readOnly :true,
			width : 150,
			store :new gsl.JsonCmbStore( {
				type :'department',
				addBlank :true
			}),
			triggerAction :'all'
		});
		
		// 角色类型combobox
		var queryRoleTypeCmb = new gsl.ComboBox( {
			fieldLabel :'角色类型',
			readOnly :true,
			width : 150,
			store :new gsl.JsonCmbStore( {
				type :'roleType',
				addBlank :true
			}),
			triggerAction :'all'
		});

		// 查询Button
		var queryBtn = new Ext.Button( {
			text :'查询',
			handler :query
		});

		// 查询条件form
		queryForm = new gsl.FormPanel( {
			region : 'north',
			height : 100,
			iconCls :'titQuery',
			title :'查询条件',
//			renderTo :'queryDiv',
			hideCollapseTool :false,
//			titleCollapse :true,
			collapsible :true,
//			width :(Ext.get('queryDiv').getWidth() > 800 ? Ext.get('queryDiv').getWidth() : 800),
			labelWidth :80,
			items : [ {
				layout :'column',
				border :false,
				items : [ {
					columnWidth : .33,
					layout :'form',
					border :false,
					items : [ queryRoleTypeCmb ]
				}, {
					columnWidth : .33,
					layout :'form',
					border :false,
					items : [ queryDepartmentCmb ]
				}, {
					columnWidth : .33,
					layout :'form',
					border :false,
					items : [ ]
				} ]
			} ],
			buttons:[{
		            text : l_query,
		            handler : query
        		    }
				],
			keys : [ {
         			key : 13,
         			fn : query,
         			scope : this
         		}]
		});

		/***********************************************************************
		 * 信息显示区
		 **********************************************************************/
		// 工具条
		var toolBar = new gsl.Toolbar( {
			auth:roleManagePageAuthRow,
			tools : [{
			tagValue : '01010201',
			text : '新增',
			iconCls : 'add',
			handler : addRole
		}]
		
		});
		// 检索结果store
		var roleStore = new gsl.JsonStore( {
			url :'pub/role-queryForPage.action',
			fields : [ 'roleId', 'roleName', 'roleDesc', 'departmentId','roleCount',
					'departmentName', 'roleType', 'roleStatus' ]
		});
		 roleStore.on('load',function(){
    	// 在roleStore加载完毕后，取消忙碌状态
			busyFlg = false;
    	});
		// 分页条
		var pagingBar = new gsl.PagingToolbar( {
			store :roleStore
		});
		// 操作列
		var actionColumn = new gsl.RowActions( {
			auth:'SP',
			edit :editRole,
			remove :removeRole,
			roleInspect:showRoleMange
		});

		// 角色检索结果grid
		var roleGrid = new gsl.GridPanel( {
			region : 'center',
			store :roleStore,
			tbar :toolBar,
			bbar :pagingBar,
//			renderTo :'resultDiv',
			iconCls :'titList',
			title :'角色信息一览',
//			width :Ext.fly("resultDiv").getWidth(),
			// 配置表格列
			cm : new Ext.grid.ColumnModel( [
			new gsl.RowNumberer(), actionColumn, {
				header :"角色",
				width :160,
				dataIndex :'roleName'
			}, {
				header :"部门",
				width :150,
				dataIndex :'departmentName'
			}, {
				header :"角色说明",
				width :180,
				dataIndex :'roleDesc'
			},new gsl.LCmbColumn( {
				header :"角色类型",
				width :60,
				dataIndex :'roleType',
				cmbData :roleTypeArray
			}),new gsl.LCmbColumn( {
				header :"状态",
				width :60,
				dataIndex :'roleStatus',
				cmbData :roleStatusArray
			}) ]),
			plugins : [ actionColumn ]
		});
		// 角色id隐藏域
		var roleIdHdn = new Ext.form.Hidden( {
			dataIndex :"roleId",
			name :"roleVo.roleId"
		});
		// 角色状态下拉框
		var StatusCmb = new gsl.BaseDataComboBox( {
			baseData :roleStatusArray,
			forceSelection :true,
			readOnly :true,
			hiddenName :'roleVo.roleStatus',
			dataIndex :'roleStatus',
			allowBlank :false,
			fieldLabel :'状态',
			listeners:{'beforeselect':function(cmb,record,index){
			if(record.get('roleStatus') == '1') {
				gsl.WarnAlert( '对不起,该角色有用户在使用,不可置为无效!');
				return false;
			}
			return true;
			}},
			anchor :'90%',
			value :'0'
		});

		// 部门下拉框
		var departmentCmb = new gsl.ComboBox( {
			fieldLabel :'部门',
			readOnly :true,
			dataIndex :'departmentId',
			hiddenName :'roleVo.departmentId',
			store :new gsl.JsonCmbStore( {
				type :'department',
				addBlank :true
			}),
			triggerAction :'all',
			anchor :'90%'
		});
		
		// 角色类型下拉框
		var roleTypeCmb = new gsl.ComboBox( {
			fieldLabel :'角色',
			readOnly :true,
			dataIndex :'roleType',
			hiddenName :'roleVo.roleType',
			store :new gsl.JsonCmbStore( {
				type :'roleType',
				addBlank :true
			}),
			allowBlank :false,
			triggerAction :'all',
			anchor :'90%'
		});

		// 角色名
		var roleName = new gsl.TextField( {
			fieldLabel :'角色',
			maxLength :ROLE_NAME_LENGTH,
			allowBlank :false,
			dataIndex :'roleName',
			name :'roleVo.roleName',
			anchor :'95%'
		});
		// 角色描述
		var roleDesc = new gsl.TextField( {
			fieldLabel :'角色描述',
			maxLength :ROLE_DESC_LENGTH,
			allowBlank :false,
			dataIndex :'roleDesc',
			name :'roleVo.roleDesc',
			anchor :'95%'
		});
		
		
		
		// 用户隐藏角色使用数
		var hiddenRoleCount = new Ext.form.Hidden({});
		// 角色form
		var roleForm = new gsl.FormPanel( {
			width :500,
			labelWidth :80,
			items : [ roleIdHdn, roleName, roleDesc, {
				layout :'column',
				border :false,
				items : [ {
					width :230,
					layout :'form',
					border :false,
					items : [ departmentCmb ]
				}, {
					columnWidth :1,
					border :false
				}, {
					width :230,
					layout :'form',
					border :false,
					items : [ StatusCmb ]
				} ]
			},{
				layout :'column',
				border :false,
				items : [ {
					width :230,
					layout :'form',
					border :false,
					items : [ roleTypeCmb ]
				}, {
					columnWidth :230,
					border :false
				} ]
			}],
			buttons : [ {
				text :'保存',
				handler :saveRole
			}, {
				text :'关闭',
				handler : function() {
					roleWin.hide();
				}
			} ]
		});
		// 角色Id
		var rolePrivIdHdn = new Ext.form.Hidden();
		// 角色名
		var roleNameLabel = new gsl.LabelField( {
			fieldLabel :'角色'
		});

		// 角色窗口
		roleWin = new gsl.Window( {
			layout :'fit',
			width :500,
			closeAction :'hide',
			height :200,
			constrain :true,
			modal :true,
			closable :true,
			animCollapse :true,
			items : [ roleForm ]
		});
		// 权限树loader
		var treeLoader = new Ext.tree.TreeLoader( {
			preloadChildren :true,
			baseAttrs : {
				clearOnLoad :true,
				uiProvider :Ext.tree.TriStateNodeUI
			}
		});
		
		// 权限树
		var tree = new Ext.tree.TreePanel( {
			width :300,
			root :new Ext.tree.AsyncTreeNode( {
				text :'所有权限',
				leaf :false,
				checked :false,
				draggable :false,
				listeners : {
					checkchange : function(node, checked) {
				          node.expand();      
				          node.attributes.checked = checked;      
				          node.eachChild(function(child) {  
				              child.ui.toggleCheck(checked);      
				              child.attributes.checked = checked;      
				              this.fireEvent('checkchange', child, checked);      
				          });  
			}
		},
		expanded :true
			}),
			loader :treeLoader,
			rootVisible :true,
			trackMouseOver :true,
			containerScroll :true,
			autoScroll :true,
			anchor :'100%'
		});
//		tree.getRootNode().on('checkchange',function(node,checked){});
		//获取角色权限
		tree.on('beforeload',function(node){
					tree.loader.dataUrl='pub/role-queryRolePrivilege.action?roleVo.roleId='+rolePrivIdHdn.getValue();
				});
		
		
		// 权限设定form
		var privilegeForm = new gsl.FormPanel( {
			labelWidth :40,
			autoScroll :true,
			items : [ roleNameLabel, rolePrivIdHdn, {
				autoScroll :true,
				items : [ tree ]
			} ]

		});
		// 权限设定窗口
		var treeWin = new gsl.Window( {
			layout :'fit',
			width :400,
			title :'权限配置',
			minimizable :false,
			maximizable :false,
			height :400,
			constrain :true,
			shadow :true,
			modal :true,
			closable :true,
			animCollapse :true,
			items : [ privilegeForm ],
			buttonAlign :'center',
			buttons : [ {
				text :'提交',
				handler :savePrivilege
			}, {
				text :'关闭',
				handler : function() {
					treeWin.hide();
				}
			} ]
		});

		/***********************************************************************
		 * 方法区
		 **********************************************************************/

		/**
		 * 查询
		 */
		function query() {
			if(queryForm.form.isValid()){
				// 如果查询没有结束。不执行查询操作
			if(busyFlg){
				return;
			}
			// 执行查询，标记忙碌状态为忙碌
			busyFlg = true;
			// 参数
			roleStore.baseParams = {
				'roleVo.roleId' :roleCmb.getValue()// 角色ID
				,
				'roleVo.departmentId' :queryDepartmentCmb.getValue(),
				'roleVo.roleType' :queryRoleTypeCmb.getValue()
			// 部门ID
			};
			// 检索
			roleStore.load( {
				params : {
					start :0,
					limit :pageSize
				}
			});
			}
		}

		/**
		 * 新增角色
		 */
		function addRole() {
			roleForm.form.reset();
			roleForm.isAdd = true;
			roleWin.setTitle("新增角色信息");
			roleWin.show();
		}

		/**
		 * 角色权限设定
		 */
		function showRoleMange(record) {
			rolePrivIdHdn.setValue(record.get('roleId'));
			treeWin.show();
		    roleNameLabel.setValue(record.get('roleName'));
			var node = tree.getRootNode();
			if(node. isLoaded()){
				tree.root.reload();
			}
			node.ui.beforeLoad();
			node.ui.afterLoad();
            tree.expandAll();

		}

		/**
		 * 角色编辑
		 */
		function editRole(record) {
			roleForm.getForm().reset();
			roleForm.isAdd = false;
			var roleId = record.get("roleId");
			roleWin.setTitle('修改角色信息');
			roleWin.show();
			// 载入数据
			roleForm.load( {
				url :'pub/role-queryByRoleId.action',
				params : {
					'roleVo.roleId' :roleId
				}
			});
		}

		/**
		 * 角色删除
		 */
		function removeRole(record) {
			if (!removeCheck(record)) {
				gsl.WarnAlert("系统业务用角色不可删除");
				return;
			}
			
			gsl.RowDelete( {
				url :"pub/role-delete.action",
				params : {
					'roleVo.roleId' :record.get('roleId'),
					'roleVo.roleName' :record.get('roleName')
				},
				success : function() {
					roleStore.reload();
				}
			});
		}

		/**
		 * 系统角色check
		 */
		function removeCheck(record) {
			var checked = true;
			var roleId = record.get('roleId');
			Ext.each(roleArray, function(item) {
				if (item[0] == roleId) {
					checked = false;
					return false;
				}
			});
			return checked;
		}

		/**
		 * 保存角色信息
		 */
		function saveRole() {
			if(departmentCmb.getValue() == null) {
				departmentCmb.setValue("");
			}
			var url = 'pub/role-insert.action';
			if (!roleForm.isAdd) {
				url = 'pub/role-update.action';
			}
			roleForm.submit( {
				url :url,
				success : function(form, action) {
					roleWin.hide();	
					roleStore.reload();

				}
			});
		}

		/**
		 * 设定权限
		 */
		function savePrivilege() {
			Ext.MessageBox.confirm('提示', "确认要提交权限修改吗?", function(btn) {
				if (btn != 'yes')
					return;
				var selNodes = tree.getChecked();
				var dataList = new Array();
				//遍历获取所有的节点数据
				Ext.each(selNodes, function (node) {
				                //子节点 
				                  
				                    var  data= new Object();
				                    data.level = node.getDepth();
				                    data.id = node.id;
				                    data.checkStatus = '1';
				                    data.roleId = rolePrivIdHdn.getValue();
				                    dataList.push(data);
				    
				});
		    
			gsl.RequestWaitAction( {
				actionNm : '权限修改',
				url :'pub/role-updatePrivilege.action',
				params : {
					'jsonStr' :Ext.encode(dataList)
				},
				success : function(t, response, options) {
					
						treeWin.hide();
				
				}
			});
		}	);
		}
		
		
		//界面布局
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items:[queryForm, roleGrid]
		});
		
//		// 调整sendQueryForm,grid的高度(自动调整)
//    		new gsl.adjHeight(queryForm, roleGrid);
//    		// 调整sendQueryForm,grid的宽度(自动调整)
//    		adjWidthCtrs = [ queryForm, roleGrid ];
//    		// 调整grid的高度(适应分辨率)
//    		var tempHeight = parent.document.getElementById("mainTabPanel").clientHeight - 50;
//    		roleGrid.setHeight(tempHeight - queryForm.getSize().height);
		
	});