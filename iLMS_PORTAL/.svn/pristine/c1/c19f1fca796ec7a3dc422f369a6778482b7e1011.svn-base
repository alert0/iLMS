<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单定义</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/formDefList.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/updateTypeDialog.js"></script>

<script type="text/javascript">
function setTypeId(){
	new updateTypeDialog({categoryKey:'FORM_TYPE',tableName:'bpm_form_Def',typeIdKey:'type_id_',typeNameKey:'type_',setting:{view:{selectedMulti:false}}}).show();
}
</script>
<style type="text/css">
	a.noUnderLine{text-decoration: none;}
</style>
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
							<a class="btn btn-primary btn-sm fa-add" onclick="addFormStepOne()"><span>添加</span></a> 
							<a class="btn btn-primary btn-sm fa-edit" href="javascript:;" action="formDefEdit" fullWindow="true"><span>编辑</span></a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/form/formDef/remove">
								<span>删除</span>
							</a>
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"> 重置</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-filter" onclick="setTypeId()"> 设置分类</a>
							
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i class=" fa  fa-angle-double-up"></i> </a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>

								<li><span>名称:</span> <input type="text" class="inputText " name="Q^name_^SL"></li>

								<li><span>别名:</span> 
								<input type="text" class="inputText " name="Q^key_^SL">
								<li>
							</ul>
						</form>
					</div>
			</div>
			<div id="formList" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	var sysTypeTree =  null;
	var formTypeId = "";
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
			var title = value[i].supportDB == "1"?"支持表":"不支持表";
			var r = '<a  class="noUnderLine" title="'+title+'">'+value[i].desc+'</a>';
			rtn.push(r);
		}
		return rtn.join("，");
	}
	function loadGrid()
	{
		$('#formList').datagrid($.extend($defaultOptions, {
	         url: __ctx+"/form/formDef/listJson",
	         idField:"id",
       		 sortName: 'CREATE_TIME_',
	         sortOrder: 'desc',
	         columns: [[
						 { field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'left'},
						 { field: 'name',sortName:"name_", title: '名称', width:250 , align: 'left', sortable: 'true' },
						 { field: 'key',sortName:"key_", title: '别名', width:200 , align: 'center', sortable: 'true'},
                         { field: 'boDefList',  title: '业务对象', width:250 , align: 'center', formatter:getBoDef },
	                     { field: 'type',sortName:"type_", title: '分类', width:250 , align: 'center', sortable: 'true' },
	                     { field: 'colManage',  title: '操作',  align: 'center', sortable: 'true'
                	    	 ,formatter:function(value,row,index){
				                 var result = "<a class='btn btn-default fa fa-edit' onclick='$.openFullWindow(\"formDefEdit?id="+row.id+"\")' href='#'>编辑</a>"; 
				                 result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/formDef/remove?id="+row.id+"' href='#'>删除</a>";
				                 result += "<a class='btn btn-default fa fa-cog' onclick='confFormOpinion(\""+row.id+"\")' href='#'>表单意见</a>";
				                 result += "<a class='btn btn-default fa fa-cloud' onClick='relation(" + row.id + ")' herf='javaScript:void(0)'>绑定关系</a>";
				                 return result;
	                  		}
                  		 }
	          ]]
	     }));     
	}
	
	function confFormOpinion(id){
		var title="表单意见配置";
		var url=__ctx+"/form/formDef/opinionConf?id="+id;
		HT.window.openEdit(url,title, 'view', 'grid', 630, 450, null, null, id, false);
	}
	
	
	
	function reload (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	function handImport(){
		var dialog;
		var def = {  passConf : reload, title : '导入业务对象定义', width : 600, height : 400, modal : true, resizable : true, iconCls : 'fa-sign-in'};
		dialog = $.topCall.dialog( {  src : __ctx + '/form/formDef/managerImport', base : def });
	}
	// 导出
	function handExport(){
		var ids = getSelectIds();
		if (ids == null) {
		    $.topCall.error("请选择表单!");
		    return;
		}
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", "exportFrom");
		frm.addFormEl("ids", ids);
		frm.submit();
	}
	
	// 获取选中的defId
	function getSelectIds(){
		var dategrid = $("#formList").datagrid('getChecked');
		if (dategrid == null || dategrid.length < 1) {
		    return null;
		} else {
		    var ids = "";
		    for (i = 0; i < dategrid.length; i++) {
			ids += dategrid[i]['id'] + ",";
		    }
		    return ids;
		}
	}
	
	function relation(id) {
		var title ="表单元数据的绑定关系";
		var url = __ctx + "/bo/def/bODefRelation?id="+id+"&type=formDef";
		HT.window.openEdit(url, title, "bODefRelation", 'grid', 100, 100, null, null, id, true);
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
</script>