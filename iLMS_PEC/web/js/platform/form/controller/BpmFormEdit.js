var formApp = angular.module('formApp',[ 'base','formDirective']);

formApp.controller('formEditCtrl',['$scope','$compile','formEditService',function($scope,$compile,formEditService){
	var frm = $('#bpmForm');
	$scope.isStepCreate = true;
	if(parent.formNextStep){
		$scope.isStepCreate = false;
	}
	
	$scope.save= function(){
		if ($scope.isSourceMode) {
			$.topCall.warn('不能在源代码模式下保存');
			return;
		}
		
		$scope.handContent();
		
		frm.ajaxForm({success:showResponse});
		if ($scope.bpmForm.$valid) {
			frm.submit();
       	}else{
       		if(!$scope.bpmForm.formKey.$modelValue){
       			if(!$scope.bpmForm.formKey.$viewValue){
       				$.topCall.error('请填写表单标识！');
       			}else{
       				$.topCall.error('请填写正确的表单标识！');
       			}
       		}else{
       			$.topCall.error('请完善表单！');
       		}
       	}
	};
	
	$scope.initBoTree = function(){
		var ztreeCreator = new ZtreeCreator('boTree',__ctx+"/form/formDef/getFormFieldTree?defId="+defId)
		  .setDataKey({name:"desc",title:"name"})
		  .setChildKey("children")
		  .setCallback({onClick:formEditService.genFieldHtml})
		  .initZtree(function(treeObj){});
	}
	$scope.initBoTree();
	
	
	//打开选择模板的选择窗口
	$scope.showSelectTemplate = function(){
		formEditService.showSelectTemplate(function(json){
			json.formId=formId;
			json.formDefId = defId;
			$.post(__ctx+'/form/form/genByTemplate', json, function(data) {
				formEditService.setHTML(data);
			});
		},"");
	};
	$scope.preview= function(){
		window.onbeforeunload  =  null ;
		var formHtml=$scope.handContent();
		var frm=new com.hotent.form.Form();
		frm.creatForm("bpmPreview",__ctx+"/form/form/preview");
		frm.addFormEl("id",formId);
		frm.addFormEl("defId",defId);
		frm.addFormEl("formType",formType);
		frm.addFormEl("formHtml",formHtml);
		frm.addFormEl("isEditPreview",true);
		frm.setTarget("_blank");
		frm.setMethod("post");
		frm.submit();
		frm.clearFormEl();
	};
	// 显示历史记录
	$scope.showHis = function (){
		var dialog = "";
		var setHtml = function(html){
			formEditService.setHTML(html,dialog);
		};
		var def = {
				passConf : {setHtml:setHtml},
				title : '表单历史操作记录',
				width : 700,
				height : 500,
				modal : true,
				resizable : false,
				iconCls : 'icon-collapse fa fa-table',
				buttonsAlign : 'center',
				buttons : [ {
					text : '恢复',
					iconCls : 'fa fa-check-circle',
					handler : function(e) {
						var win=dialog.innerWin,
							records = win.$('#formHistoryDialog').datagrid('getSelections');
						if($.isEmpty(records) || records.length<1){
							$.topCall.toast('提示信息','请选择记录');
							return;
						}
						if(records.length>1){
							$.topCall.toast('提示信息','只能选择一条记录');
							return;
						}
						$(records).each(function(){
							setHtml(this['formHtml']);
						});
					}
				},{
					text : '取消',
					iconCls : 'fa fa-times-circle',
					handler : function(e) {
						dialog.dialog('close');
					}
				}]
			};
			this.show = function() {
				dialog = $.topCall.dialog({
					src : __ctx + '/form/history/historyList?formId='+formId,
					base : def
				});
				return dialog;
			};
			
			this.show();
	};
	window.setTimeout(function(){
		var ue = UE.getEditor('editor',{initialFrameHeight : $("#center").height()-200, initialFrameWidth:$("#center").width()-15});
		ue.addListener('ready',function(){
			$scope.editor = ue;
			formEditService.init(ue);
			$scope.initTab();
		});
		ue.addListener("sourceModeChanged",function(t,m){
			$scope.isSourceMode = m;
		});
	});
	$scope.name=name;$scope.formKey = formKey;
	
	
	$scope.currentEdit=0;
	
	//初始化tab
	$scope.initTab = function(){
		var formData = $scope.editor.getContent();
		var tabTitle = $("#formTabTitle").val()||"主页面";
		$scope.aryTitle = tabTitle.split("#tab#");
		$scope.aryForm = formData.split("#tab#");

		if ($scope.aryTitle.length > 1){
			$scope.editor.setContent($scope.aryForm[0]);
		};
		$scope.$apply();
	}
	
	$scope.handContent = function(){
		//设置当前编辑页面
		 var oldContent = $scope.editor.getContent($scope.currentEdit);
		 $scope.aryForm[$scope.currentEdit] =oldContent;
		
		 //设置页面tabs 和titles
		 var html = $scope.aryForm.join("#tab#");
		 $("[name='formHtml']").val(html);
		 $("#formTabTitle").val($scope.aryTitle.join("#tab#"))
		 return html;
	}
	
	//切换tab
	$scope.changeEdit = function(index,notSaveOld){
		if($scope.currentEdit==index)return;
		if ($scope.isSourceMode) {
			$.topCall.warn('不能在源代码模式切换！');
			return;
		}
		
		//isSaveOld
		if(!notSaveOld){
			var oldContent = $scope.editor.getContent($scope.currentEdit);
			$scope.aryForm[$scope.currentEdit] =oldContent;
		}
		
		$scope.currentEdit=index;
		$scope.editor.setContent($scope.aryForm[index]);
	}
	$scope.toEdit =function($index,edit){
		if(edit) {
			$scope.currentToEdit = $index
			return;
		}
		$scope.currentToEdit = 99;
		
	}
	
	//添加tab
	$scope.addTab = function(){
		$scope.aryTitle.push("新页面"+$scope.aryTitle.length);
		$scope.aryForm.push("");
	}
	//删除tab
	$scope.delTab = function(){
		if($scope.aryTitle.length==1)return;
		$scope.aryTitle.splice($scope.currentEdit,1);
		$scope.aryForm.splice($scope.currentEdit,1);
		$scope.changeEdit($scope.aryTitle.length-1,true);
	}
}]);
formApp.service('formEditService',['$rootScope',function($rootScope){
	var editor = "",
		that = "";
	var service = {
			init:function(ue){
				editor = ue;
				that = this;
			},
			/**
			 * 获取表单的HTML代码，在表单保存和预览的时候用到
			 * @returns
			 */
			getFormHtml:function(){
				return	$('#bpmForm [name="formHtml"]').val();
			},
			showResponse:function(responseText) {
				showResponse(responseText);//调用页面的就行 liyj
			},
			//tableName为生成某个表
			showSelectTemplate : function(call,tableName){
				url = __ctx+'/form/template/selectTemplate?defId='+defId+"&isSimple=1&tableName="+tableName+"&formType="+formType;
				var dialog = {};
				var h = 350;
				var w = 550; 
				var that = this;
				var def = {
					passConf : {},
					title : '选择表单模版',
					width : w,
					height : h,
					modal : true,
					resizable : false,
					iconCls : 'icon-collapse',
					buttonsAlign : 'center',
					buttons : [ {
						text : '确定',
						iconCls : 'fa fa-check-circle',
						handler : function(e) {
							dialog.innerWin.ok(call);
							dialog.dialog('close');
						}
					}]
				};
				this.show = function() {
					dialog = $.topCall.dialog({
						src : url,
						base : def
					});
					return dialog;
				};
				this.show();
			},
			setHTML:function(html,dialog){
				$.topCall.confirm('提示信息', '确认恢复该版本HTML数据？', function(r) {
					if (r) {
						editor.setContent(html);
						$.msgShow('提示信息', '恢复成功!');
						if(dialog){
						  dialog.dialog('close');
						}
						return true;
					}else{
						return false;
					}
				});
			},
			genFieldHtml:function(e,treeId,node){
				
				if (node.type =='sub' ||node.type =='main'){
					that.showSelectTemplate(function(json){
							json.formId=formId;
							json.formDefId=defId;
							$.post(__ctx+'/form/form/genByTemplate', json, function(data) {
								editor.execCommand('inserthtml', data,1);
							});
					},node.name);
				}
				else if(node.fieldType=='field'){
					if(editor.getAllHtml().indexOf('permission="permission.fields.'+node.tableName+"."+node.name+"\"")!=-1){
						$.msgShow('提示信息', '该字段已经添加');
					}else{
						$.post(__ctx+'/form/form/genByField', {defId:defId,attrId:node.boAttrId,formType:formType}, function(data) {
							editor.execCommand('inserthtml', data,1);
						});
					}
				}
			}
			
	};
	return service;
} ]);
