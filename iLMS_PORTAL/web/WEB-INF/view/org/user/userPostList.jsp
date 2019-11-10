<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>用户的岗位列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/org/OrgUtil.js"></script>
<script>
var userId="${param.userId}";

</script>
</head>
<body>
<div class="easyui-layout" fit="true" scroll="no">
	<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javascript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
					</a>
					
					<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="setOrg()">
						<span>加入组织</span>
					</a>
					
					<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="setPos()">
						<span>加入岗位</span>
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
						<li><span>组织名称:</span><input class="inputText" type="text" name="Q^org.name_^SL"></li>
					    <li><span>岗位名称:</span><input class="inputText" type="text" name="Q^rel.rel_name_^SL"></li>
					</ul>
				</form>
			</div>
	</div>
	<div id="grid" class="my-easyui-datagrid" ></div>
</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});

	function setOrg(){
		CustomDialog.openCustomDialog("orgSelector",function(data,dialog){
	 		if(data.length>0){
	 		   var orgIds=[];
		 	    $.each(data,function(index,item){
		 	    	orgIds.push(item.id);
		 	    });
		 	    var url=__ctx + "/org/orgUser/saveUserOrg";
		 	    var params={orgId:orgIds.join(","),userId:userId};
		 	    $.post(url,params,function(data){
		 	    	$.topCall.alert("提示","分配用户组织"+data.message);
					HT.window.refreshTargetGrid("grid");
		 	    },"json");
	 		}
	 
		    dialog.dialog('close');
		});
	}
	

	function setPos() {
		CustomDialog.openCustomDialog("postSelector",function(data,dialog){
	 		if(data.length>0){
	 		    var ids=[];
	 		   var orgIds=[];
		 	    $.each(data,function(index,item){
		 	    	ids.push(item.id);
		 	    	orgIds.push(item.orgId);
		 	    });
		 	    
		 	    var url=__ctx + "/org/orgUser/saveUserOrg";
		 	    var params={orgId:orgIds.join(","),relId:ids.join(","),userId:userId};
		 	    $.post(url,params,function(data){
		 	    	$.topCall.alert("提示","分配用户岗位"+data.message);
					HT.window.refreshTargetGrid("grid");
		 	    },"json");
	 		}
		    dialog.dialog('close');
		});
	}
	
	
	
	//orgUserId,orgId,orgName,orgCode,isMaster,relName
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/user/listUserPostJson?userId="+userId,
			idField : "orgUserId",
			sortName : 'orguser.id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			   {field : 'orgName',sortName : " org.name_",title : '组织名称',width : 200,align : 'center'},
			   {field : 'relName',sortName : "rel.rel_name_",title : '岗位名称',width : 200,align : 'center'},
			   {field : 'isMaster',sortName : "orguser.is_master_",title : '主组织',width : 50,align : 'center',
				   formatter : function(value, row, index) {
						var arr = [ { 'key' : '1','value' : '是','css' : 'green'}, 
						            {'key' : '0', 'value' : '否','css' : 'red'}];
						return Object.formatItemValue(arr,value);
					}
			   },
			   {
					field : 'colManage',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/org/orgUser/removePostUser?id=" + row.orgUserId + "' herf='javascript:void(0)'>删除</a>";
						if(row.isMaster==1){
							result += "<a class='btn btn-default fa fa-thumbs-o-down' onclick='setMaster(" + row.orgUserId + ",false)' herf='javascript:void(0)'>取消主组织</a>"
						}
						else{
							result += "<a class='btn btn-default fa fa-thumbs-o-up' onclick='setMaster(" + row.orgUserId + ",true," + userId + ")' herf='javascript:void(0)'>设置主组织</a>"
						}
						return result;
					}
				} 
			   ] ]
		} ));
	}
</script>
