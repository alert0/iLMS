<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>下属管理</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="#">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="setUserUnder('${param.userId}','${param.orgId }')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/org/userUnder/remove">
								<span>删除</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul style="margin-left: -26px">
								<li><span>下属用户名:</span><input class="inputText" type="text" name="Q^underUserName^SL"></li>
								<input value="${param.userId }" class="inputText" type="hidden" name="Q^userId^S">
								<input value="${param.orgId }" class="inputText" type="hidden" name="Q^orgId^S">
							</ul>
						</form>
					</div>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑下属管理" : action == "add" ? "添加下属管理" : "查看下属管理";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="userUnder" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	//设置下级
	function setUserUnder(userId,orgId){
		var callBack = function(data,dialog){
		 	 
	 		if(data.length>0){
	 			var flag = true;
	 		    var ids=[];
	 		    var names = [];
		 	    $.each(data,function(index,item){
		 	    	//判断是否有当前用户
		 	    	if(item.id == userId){
		 	    		flag = false;
		 	    		return;
		 	    	}
		 	    	ids.push(item.id);
		 	    	names.push(item.name);
		 	    });
		 	    if(flag){
			 		var data=Object.toAjaxJson(__ctx + "/org/userUnder/","saveUserUnders",{userNames:names.toString(),userUnderId:ids.toString(),orgId:orgId,userId:userId});
					$.topCall.alert("提示",data.message);
					HT.window.refreshTargetGrid("grid");
		 	    }else{
		 	    	$.topCall.alert("提示","不能将自己添为下属");
	 	    		dialog.dialog('close');
		 	    }
	 		}
	 
		    dialog.dialog('close');
		};
		var demId = '${param.demId}';
		var conf = {
				"selectNum":-1,
				"param":{"demId":demId}
		}
		CombinateDialog.open({alias:"userSelector",callBack:callBack});
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?userId=${param.userId}&orgId=${param.orgId}",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'underUserName',sortName : "UNDER_USER_NAME_",title : '下属用户名',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/org/userUnder/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
