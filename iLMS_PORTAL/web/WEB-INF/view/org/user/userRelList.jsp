<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>下级用户</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript">
var typeId="${param.typeId}", parentId = "${param.id}";
function closeDialog(){
	HT.window.closeEdit();
	if(parent.refreshNode){
		parent.refreshNode()
	}
}
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
						<a class="btn btn-sm btn-primary fa-back" onClick="closeDialog();"><span>返回</span></a>
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
								<span>加入用户</span>
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
							<ul >
								<li><span>姓名:</span><input class="inputText" type="text" name="Q^u.fullname_^SL"></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		CustomDialog.openCustomDialog("userSelector",function(data,dialog){
			if(!data ||  data.length==0){
				dialog.dialog('close');
			}
 		    var ids=[];
	 	    $.each(data,function(index,item){
	 	    	ids.push(item.id);
	 	    });
	 	    var data=Object.toAjaxJson(__ctx + "/org/sysUserRel/","saveUserRel",{userIds:ids.toString(),parentId:parentId,typeId:typeId});
			$.topCall.success(data.message,function(){
				dialog.dialog('close');
			});
			
			HT.window.refreshTargetGrid("grid");
			if(parent.refreshNode){
				parent.refreshNode()
			}
		});
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/sysUserRel/getUnderUser?typeId="+typeId+"&parentId="+parentId,
			idField : "id",
			sortName : 'userRel.id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'account',title : '账号',width : 180,align : 'center'	}, 
			{field : 'userName',sortName : "user_name_",title : '用户姓名',width : 180,align : 'center'}] ]
		} ));
	}
</script>
