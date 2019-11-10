<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>用户的角色列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script>
var userId="${param.userId}";
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
								<span>搜索</span>
							</a>
								<a href="javascript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="openDetail('','selectAdd')">
								<span>加到角色</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul style="margin-left: -26px">
								<li><span>角色名称:</span><input class="inputText" type="text" name="Q^r.name_^SL"></li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid" ></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});


	function openDetail(id, action) {
		CustomDialog.openCustomDialog("roleSelector",function(data,dialog){
		 	 
	 		if(data.length>0){
	 		    var ids=[];
		 	    $.each(data,function(index,item){
		 	    	ids.push(item.id);
		 	    });
		 		var data=Object.toAjaxJson(__ctx + "/org/userRole/","saveUserRole",{roleId:ids.toString(),userId:userId});
				$.topCall.alert("提示",data.message);
				HT.window.refreshTargetGrid("grid");
	 		}
	 
		    dialog.dialog('close');
		});
	}
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/userRole/listJson?userId="+userId,
			idField : "id",
			sortName : 'ur.id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			        {field : 'roleName',sortName : "r.name_",title : '角色名称',width : 500,align : 'center'},
			     	{
						field : 'colManage',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/org/userRole/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
							return result;
						}
					}
			   ] ]
		}));
	}
</script>
