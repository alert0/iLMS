<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>角色下的用户</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript">
var roleId="${param.roleId}";
$(function(){
	$('#fullname').keydown( function(e) {
	    var key = window.event?e.keyCode:e.which;
	    if(key.toString() == "13"){
	    	$('a.fa-search').click();
	        return false;
	    }
	});
})
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
						<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add','user')">
								<span>加入用户</span>
							</a>
							
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add','org')">
								<span>加入组织用户</span>
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
								<li><span>姓名:</span><input id="fullname" class="inputText" type="text" name="Q^fullname_^SL"></li>
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
		  $("#searchForm").submit(function(e){  
	            e.preventDefault();  
	        });  
	});
		
	function openDetail(id, action ,type) {
		var selector = "userSelector";
		if(type=='org'){
			selector = "orgSelector"
		}
		CustomDialog.openCustomDialog(selector,function(data,dialog){
			if(!data ||  data.length==0){
				dialog.dialog('close');
			}
 		    var ids=[];
	 	    $.each(data,function(index,item){
	 	    	ids.push(item.id);
	 	    });
	 	    var data=Object.toAjaxJson(__ctx + "/org/userRole/","save",{userId:ids.toString(),roleId:roleId,type:type});
			$.topCall.success(data.message,function(){
				HT.window.refreshTargetGrid("grid");
				dialog.dialog('close');
			});
		});
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/userRole/listJson?roleId="+roleId,
			idField : "id",
			sortName : 'ur.id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'account',title : '账号',width : 180,align : 'center'	}, 
			{field : 'fullname',sortName : "fullname_",title : '用户姓名',width : 180,align : 'center'}, 
			{
				field : 'colManage',title : '操作',align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/org/userRole/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					return result;
				}
			} ] ]
		} ));
	}
</script>
