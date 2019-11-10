<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单列表</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/formList.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/updateTypeDialog.js"></script>
<script type="text/javascript">
function setTypeId(){
	new updateTypeDialog({categoryKey:'FORM_TYPE',tableName:'bpm_form',typeIdKey:'type_id_',typeNameKey:'type_name_',setting:{view:{selectedMulti:false}}}).show();
}
</script>
</head>
<body>
	<div class="easyui-layout" style="" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'表单分类'"
			style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-add" onclick="newForm('${formType}')"><span>添加</span></a> 
							<a class="btn btn-primary btn-sm fa-add" onclick="$.openFullWindow('formCreate?formType=${formType}')"><span>创建表单</span></a> 
							<a class="btn btn-primary btn-sm fa-edit" href="javascript:;" action="formEdit" fullWindow="true"><span>编辑</span></a>
							<a class="btn btn-primary  btn-sm fa-remove" href="javascript:;" action="/form/form/remove"><span>删除</span></a> 
							<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)"><span>导出</span></a> 
							<a class="btn btn-sm btn-primary fa-sign-in" href="javascript:void(0)"><span>导入</span></a>
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"> 重置</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-filter" onclick="setTypeId()"> 设置分类</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i
								class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>表单名称:</span> 
									<input type="text" class="inputText " name="Q^name_^SL">
								</li>

								<li><span>表单状态:</span> 
									<select name="Q^status_^SL" class="inputText">
										<option value="">请选择...</option>
										<option value="draft">草稿</option>
										<option value="deploy">发布</option>
									</select>
								<li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	var sysTypeTree =  null;
	var formTypeId = "";
	var formType="${formType}";
	$(function(){
		sysTypeTree =  new SysTypeTree( $('#sysTypeTree'),{
		   	typeKey: __CAT_FORM,
			onClick:function(event, treeId, treeNode){
				var param = {};
				if(treeNode.isRoot != 1){
					param ={'Q^type_id_^L':treeNode.id};
					formTypeId = treeNode.id;
				}
				$('.my-easyui-datagrid').datagrid('load',param);
			},
	  		onRightClick:function(event, treeId, treeNode){
	  		}
		});
		loadGrid();
		$("a.btn.fa-sign-out").click(handExport);// 绑定导出
		$("a.btn.fa-sign-in").click(handImport);// 绑定导入
	});
	function getBoDef(value,row,index){
		var rtn = [];
		for(var i = 0 ; i < value.length ; i++){
			var title = value[i].isCreateTable == "Y"?"已经生成表":"未生成表";
			var r = '<a class="owner-span" href="${ctx}/bo/def/get?id='+value[i].id+'" title="'+title+'">'+value[i].desc+'</a>';
			rtn.push(r);
		}
		return rtn.join("   ");
	}
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions, {
	         url: __ctx+"/form/form/listJson?formType=${formType}",
	         idField:"id",
       		 sortName: 'create_time_',
	         sortOrder: 'desc',
	      
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'center'},
						{ field: 'name',sortName:"name_", title: '名称', width:250 , align: 'center', sortable: 'true'},
						{ field: 'formKey',sortName:"form_key_",title: '表单KEY',  width: 250, align: 'center'},
	                     { field: 'typeName',sortName:"type_name_", title: '分类', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'status',sortName:"status_" , title: '状态', width:100, align: 'center', sortable: 'true'
		 	                    ,formatter:function(value,row,index){
		 	                    	var arr=[{'key':'draft','value':'草稿','css':'red'},{'key':'deploy','value':'已发布','css':'green'},{'key':'forbidden','value':'禁用','css':'red'}];
		 	                    	return Object.formatItemValue(arr,value);  
	 	                    	}
		 	                },
		 	               { field: 'busDataTemplateCount', title: '业务数据模板', width:100, align: 'center', sortable: 'false'
		 	                    ,formatter:function(value,row,index){
		 	                    	var arr=[{'key':'0','value':'未生成','css':'red'},{'key':'1','value':'已生成','css':'green'}];
		 	                    	return Object.formatItemValue(arr,value);  
	 	                    	}
		 	                },
	                     { field: 'version',sortName:"version_", title: '版本信息', width: 250, align: 'center', sortable: 'true'
	                    	 ,formatter:function(value,row,index){
	                    		 var str='主版本:<a href="javascript:;" onclick="$.openFullWindow(\'formEdit?id='+row.id+'\')">版本('+value+')</a>';
	                    		 str+='&nbsp;&nbsp;&nbsp;'; 
	                    		 str+='<a href="javascript:;" onclick="openVersions(\'versions?formKey='+row.formKey+'\')">管理版本('+row.versionCount+')</a>'; 
	                    		 return str;
                   		     }	 
	                      },
	 	                  { field: 'colManage',  title: '操作',  align: 'center', sortable: 'true'
                  	    	 ,formatter:function(value,row,index){
				                  var result = "<a class='btn btn-default fa fa-th-large' onclick='$.openFullWindow(\"formEdit?id="+row.id+"\")' href='#'>编辑</a>"; 
			                	 result += "<a class='btn btn-default fa fa-eye' onclick='preview(\""+row.id+"\")' href='#'>预览</a>";
				                
			                	 if(row.status != 'draft' &&'mobile'!='${formType}'){
				                	 result += "<a class='btn btn-default fa fa-cog' onclick='busSetting(\""+row.formKey+"\")'  href='#'>业务表单设置</a>"; 
				                 }
				                 if(row.status != 'draft'){
				                	 result += "<a class='btn btn-default fa fa-add' onclick='newVersion(\""+row.id+"\")'  href='#'>新建版本</a>";
				                	 result += "<a class='btn btn-default fa fa-cog' onclick='dataTemplate(\""+row.formKey+"\")'  href='#'>业务数据模板</a>"; 
				                 }
				                 if(row.status == 'draft'){
				                	 result += "<a class='btn btn-default fa fa-bookmark-o' onclick='publishForm(\""+row.id+"\")'  href='#'>发布</a>"; 
				                 }
				                 result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/form/remove?id="+row.id+"' href='#'>删除</a>";
				                 result += "<a class='btn btn-default fa fa-cloud' onClick='relation(" + row.id + ")' herf='javaScript:void(0)'>绑定关系</a>";
				                 return result;
			                  }
                  		  }
	          ]]
		}));     
	}   
	function openVersions(url)
	{
	    var title="表单版本管理";
	    url = url +"&formType=${formType}";
	    HT.window.openEdit(url,title, "list", 'grid', 500, 500, null, null, ".121", true);
	}
	function busSetting(formKey){
		var url = "formBusSetEdit?formKey="+formKey;
		HT.window.openEdit(url,"业务表单设置", 'list', 'grid', 500, 500, null, null, formKey, true);
	}
	
	function dataTemplate(formKey){
		var url = __ctx + "/form/dataTemplate/bpmDataTemplateEdit?formKey="+formKey;
		HT.window.openEdit(url,"业务数据模板设置", 'list', 'grid', 500, 500, null, null, formKey, true);
	}
	
	function preview(id){
		var dialog;
		if(formType!='mobile'){ 
			window.open("preview?id="+id);     
		}else{
			var def = {
				passConf : "",title : '预览',width : 360, height : 640, modal : true,resizable : true,
				buttonsAlign : 'center',
				buttons : [{ text : '关闭', iconCls : 'fa fa-times-circle', handler : function(e) { dialog.dialog('close'); } } ]
			};
			dialog = $.topCall.dialog({ src : __ctx + '/form/form/preview?id='+id, base : def });
		}
	}
	
	function reload (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	function handImport(){
		var dialog;
		var def = {  passConf : reload, title : '导入表单', width : 600, height : 400, modal : true, resizable : true, iconCls : 'fa fa-sign-in'};
		dialog = $.topCall.dialog( {  src : __ctx + '/form/form/formImport', base : def });
	}
	// 导出
	function handExport(){
		var ids= $.getSelectIds($("#grid"),'id');
		if (ids == null) {
		    $.topCall.error("请选择表单!");
		    return;
		}
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", "exportForm");
		frm.addFormEl("ids", ids);
		frm.submit();
	}
	
	function publishForm(id){
		$.topCall.confirm('提示信息', '确认发布吗？', function(r) {
			if(!r) return;
			
			var url = __ctx + '/form/form/publish?id=' + id;
			$.post(url, function(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				if (resultMessage.isSuccess()) {
					$.topCall.success('发布成功！',function(){
						reload();
					})
				} else {
					$.topCall.errorStack("发布出错!",resultMessage.getMessage());
				}
			});
		});
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	
	function relation(id) {
		var title ="表单的绑定关系";
		var url = __ctx + "/bo/def/bODefRelation?id="+id+"&type=form";
		HT.window.openEdit(url, title, "bODefRelation", 'grid', 100, 100, null, null, id, true);
	}
	
	function createForm(){
		window.open("preview");     
	}

</script>