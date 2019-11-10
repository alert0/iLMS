<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单定义</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
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
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
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

								<li><span>表单名称:</span> <input type="text" class="inputText " name="Q^name_^SL"></li>
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
	});
	
	function loadGrid()
	{
		$('#formList').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/form/formDef/listJson",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         fit: true,
	         fitColumns: true,
	         pagination: true,
	         singleSelect:true,
	         autoRowHeight: false,
	         toolbar: '#gridSearch',
	         pageSize: $defaultPageSize,
	         pageList: $defaultPageList,
	         rownumbers: true,
	         striped:true,
	         columns: [[
						 { field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'center'
						 },
	                     { field: 'name',sortName:"name_", title: '名称', width:250 , align: 'center', sortable: 'true'
	                     },
	                     { field: 'key',sortName:"key_", title: 'KEY', width:200 , align: 'center', sortable: 'true'
	                     }
	           ]]
	         
	     }));     
	}
	
	function reload (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	
	// 获取选中的defId
	function getSelectId(){
		var row = $("#formList").datagrid('getChecked');
		if(row.length==1) return row[0].id;
	}
</script>