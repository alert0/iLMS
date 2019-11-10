<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>岗位分配人员</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript">
var orgId="${param.orgId}";
var relId="${param.relId}";
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
						<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
							<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:void(0)" onclick="openDetail('','add')">
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
							<ul>
								<li><span>姓名:</span><input class="inputText" type="text" name="Q^fullname_^SL"></li>
								<li><span>账号:</span><input class="inputText" type="text" name="Q^account_^SL"></li>
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
		var callBack = function(data,dialog){
	 		if(data.length>0){
	 		    var ids=[];
		 	    $.each(data,function(index,item){
		 	    	ids.push(item.id);
		 	    });
		 		var data=Object.toAjaxJson(__ctx + "/org/orgUser/","saveOrgUserRel",{userId:ids.toString(),relId:relId,orgId:orgId});
				$.topCall.alert("提示",data.message);
				HT.window.refreshTargetGrid("grid");
	 		}
		    dialog.dialog('close');
		}
		CustomDialog.openCustomDialog("sysUserIdSelector",callBack);
	}
	
	function removeRow(id) {
		$.topCall.confirm('温馨提示', '确定删除？', function(r){
			if(!r)return false;
		var param = {
			id : id
		}
		$.post(__ctx + "/org/orgUser/removePostUser", param, function(data) {
			var result = new com.hotent.form.ResultMessage(data)
			if (result.isSuccess()) {//成功
				$.topCall.success(result.getMessage());
				$('.my-easyui-datagrid').datagrid('reload');
			} else {
				$.topCall.error(result.getMessage());
			}
		});
	  });
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend( $defaultOptions,{
			url :  __ctx+"/org/user/getUserByGroupJson?orgId="+orgId+"&relId="+relId,
			idField : "orgUserId",
			sortName : 'orguser.id_',
			sortOrder : 'asc',
			striped : true,
		 
			columns : [ [
			 {field : 'fullName',sortName : "u.fullname_",title : '姓名',width :180,align : 'center',sortable : 'true'}, 
			 {field : 'account',sortName : "account_",title : '账号',width : 180,align : 'center',sortable : 'true'},
			 {field : 'isMaster',sortName : "orguser.is_master_",title : '主组织',width : 50,align : 'center',
				   formatter : function(value, row, index) {
						var arr = [ { 'key' : '1','value' : '是','css' : 'green'}, 
						            {'key' : '0', 'value' : '否','css' : 'red'}];
						return Object.formatItemValue(arr,value);
					}
			},
			{
				field : 'colManage',title : '操作',align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/org/orgUser/removePostUser?id=" + row.orgUserId + "' herf='javascript:void(0)'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
