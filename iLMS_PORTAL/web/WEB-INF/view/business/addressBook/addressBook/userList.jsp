<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织用户</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/org/OrgUtil.js"></script>
<script>
var orgId="${param.orgId}";
var authId = "${param.authId}";
var authOrgId = "${param.authOrgId}";
$(function(){
	if(orgId==""){
		$("#add").hide();
	}
	
	if(authId){
		$.post(__ctx+"/org/orgAuth/getJson", {"id":authId}, function(data) {
			var result = data;
			userPerms = result.userPerms;
			if(userPerms.indexOf("add") == -1){
				$("a.userAdd").hide();
			}
			if(userPerms.indexOf("delete") == -1){
				$("a.userDel").hide();
			}
			if(userPerms.indexOf("edit") == -1  ){
				$("a.userEdit").hide();
			}
		});	
	}
	
});


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
							<!-- <a class="btn btn-sm btn-primary fa-add userAdd" id="add" href="javascript:void(0)" onclick="openDetail('','add')">
								<span>新增用户</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add userAdd" id="add" href="javascript:void(0)" onclick="openDetail('','selectAdd')">
								<span>加入用户</span>
							</a> -->
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
								<li><span>姓名:</span><input class="inputText" type="text" name="Q^fullname_^SL"></li>
								<li><span>账号:</span><input class="inputText" type="text" name="Q^account_^SL"></li>
								<li><span>状态:</span>
								<select class="inputText" name="Q^status_^SL">
								    <option value="">全部</option>
								  <option value="0">禁用</option>
								   <option value="1">正常</option>
								</select>
								 </li>
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
		if(action=="selectAdd")
		{
			var callBack = function(data,dialog){
			 	 
		 		if(data.length>0){
		 		    var ids=[];
			 	    $.each(data,function(index,item){
			 	    	ids.push(item.id);
			 	    });
			 		var data=Object.toAjaxJson(__ctx + "/org/orgUser/","saveOrgUser",{userId:ids.toString(),orgId:orgId});
					$.topCall.alert("提示",data.message);
					HT.window.refreshTargetGrid("grid");
		 		}
		 
			    dialog.dialog('close');
			};
			CustomDialog.openCustomDialog("sysUserIdSelector",callBack);
		}else{
			var title = action =="edit" ? "编辑用户" : action == "add" ? "添加用户" : "查看用户";
			action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
			var url=__ctx+"/org/user/user" + action+"?orgId="+orgId;
			if(!$.isEmpty(id)){
				url+='&id=' + id;
			}
			HT.window.openEdit(url, title, action, 'grid', 400, 300, null, null, id, true);
		}
		

	}
	
	function order(id){
		var title = "排序";
		 
		var url=__ctx+"/org/org/orgUserEdit" +"?id="+id;
		HT.window.openEdit(url, title, "edit", 'grid', 500, 300, null, null, id, false);
	}
	
	//下属列表
	function underUserList(userId,orgId){
		var demId = '${param.demId}';
		var url=__ctx+"/org/userUnder/userUnderList" +"?userId="+userId+"&orgId="+orgId+"&demId="+demId;
		HT.window.openEdit(url, "下属管理", "", 'grid', 800, 500, null, null, userId, false);
	}
	
	//设置下级
	function setUserUnder(userId,orgId){
		var callBack = function(data,dialog){
		 	 
	 		if(data.length>0){
	 		    var ids=[];
	 		    var names = [];
		 	    $.each(data,function(index,item){
		 	    	//判断是否有当前用户
		 	    	if(item.ID_ == userId){
		 	    		$.topCall.alert("提示","不能将自己添为下属");
		 	    		dialog.dialog('close');
		 	    		return;
		 	    	}
		 	    	ids.push(item.ID_);
		 	    	names.push(item.FULLNAME_);
		 	    });
		 		var data=Object.toAjaxJson(__ctx + "/org/userUnder/","saveUserUnders",{userNames:names.toString(),userUnderId:ids.toString(),orgId:orgId,userId:userId});
				$.topCall.alert("提示",data.message);
				HT.window.refreshTargetGrid("grid");
	 		}
	 
		    dialog.dialog('close');
		};
		/* var conf = {
				"selectNum":-1,
				"param":{"ORG_ID_":orgId}
		} */
		CustomDialog.openCustomDialog("sysUserIdSelector",callBack);
	}
	
	function removeRow(id) {
		$.topCall.confirm('温馨提示', '确定删除？', function(r){
			if(!r)return false;
			var param = {
					userId : id,
					orgId:orgId
				}
				$.post(__ctx+"/org/orgUser/removeByOrgIdUserId", param, function(data) {
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
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/user/getUserByGroupJson?orgId="+orgId,
			idField : "userId",
			sortName : 'userId',
			sortOrder : 'asc',
			striped : true,
			columns : [ [
             {field : 'fullName',sortName : "u.fullname_",title : '姓名',width : 130,align : 'center',sortable : 'true'}, 
             {field : 'orgName',title : '部门名称',width : 130,align : 'center'},
             {field : 'relName',title : '岗位名称',width : 130,align : 'center'},
		/* 	{field : 'isMaster',sortName : "orguser.is_master_",title : '是否主岗位',width : 80,align : 'center',
				 formatter:function(value){
					 var arr = [{ 'key' : '1','value' : '是','css' : 'green'}, 
						            {'key' : '0', 'value' : '否','css' : 'red'}];
						return Object.formatItemValue(arr,value);
				}
			}, */
			/* {field : 'isCharge',sortName : "orguser.is_charge",title : '是否负责人',width : 80,align : 'center',
				 formatter:function(value){
					 var arr = [{ 'key' : '2','value' : '主','css' : 'green'},{ 'key' : '1','value' : '是','css' : 'green'}, 
						            {'key' : '0', 'value' : '否','css' : 'red'}];
						return Object.formatItemValue(arr,value);
				}
			}, */
			{field : 'email',title : '邮箱',width : 130,align : 'center'},
			{field : 'mobile',title : '手机号码',width : 130,align : 'center'
				,formatter:function(value,row,index){
           		 if (row.encry=="true" && row.userId!=${currentUser.id}) {
           			  return "******";
					}
	                  return value;
                }}, 
			{field : 'phone',title : '办公电话',width : 130,align : 'center'
				,formatter:function(value,row,index){
           		 if (row.encry=="true" && row.userId!=${currentUser.id}) {
						return "******";
					}
	                  return value;
                }}, 
			{field : 'qq',title : 'qq',width : 130,align : 'center'
				,formatter:function(value,row,index){
           		 if (row.encry=="true" && row.userId!=${currentUser.id}) {
						return "******";
					}
	                  return value;
                }}, 
			{field : 'weixin',title : '微信号',width : 130,align : 'center'
				,formatter:function(value,row,index){
           		 if (row.encry=="true" && row.userId!=${currentUser.id}) {
						return "******";
					}
	                  return value;
                }}
			
		/* 	{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					if(authId){//分级管理员下
						if(userPerms.indexOf("delete") > -1){
							result = "<a  class='rowInLine btn btn-default fa fa-remove userDel' action='/org/orgUser/remove?id="+row.orgUserId+"' herf='javascript:void(0)'>删除</a>";
						}
						if(row.isMaster==1 && userPerms.indexOf("edit") > -1 ){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setMaster(" + row.orgUserId + ",false)' herf='javascript:void(0)'>取消主岗位</a>"
						}
						else if(userPerms.indexOf("edit") > -1){
							result += "<a class='btn btn-default fa fa-thumbs-o-up userEdit' onclick='setMaster(" + row.orgUserId + ",true," + row.userId + ")' herf='javascript:void(0)'>设置主岗位</a>"
						}
						
						if(row.isCharge!=2 && userPerms.indexOf("edit") > -1){
							result += "<a class='btn btn-default fa fa-thumbs-o-up  userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",true,"+row.orgId+")' herf='javascript:void(0)'>设置为主负责人</a>"
						}
						if(row.isCharge==1 && userPerms.indexOf("edit") > -1){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>取消负责人</a>"
						}
						
						if(row.isCharge==0 && userPerms.indexOf("edit") > -1){
							result += "<a class='btn btn-default fa fa-thumbs-o-up userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>设置为负责人</a>"
						}
						if(row.isCharge==2 && userPerms.indexOf("edit") > -1){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>取消主负责人</a>"
						}
					}else{
						result = "<a  class='rowInLine btn btn-default fa fa-remove userDel' action='/org/orgUser/remove?id="+row.orgUserId+"' herf='javascript:void(0)'>删除</a>";
						if(row.isMaster==1){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setMaster(" + row.orgUserId + ",false)' herf='javascript:void(0)'>取消主岗位</a>"
						}
						else{
							result += "<a class='btn btn-default fa fa-thumbs-o-up userEdit' onclick='setMaster(" + row.orgUserId + ",true," + row.userId + ")' herf='javascript:void(0)'>设置主岗位</a>"
						}
						
						if(row.isCharge!=2){
							result += "<a class='btn btn-default fa fa-thumbs-o-up  userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",true,"+row.orgId+")' herf='javascript:void(0)'>设置为主负责人</a>"
						}
						if(row.isCharge==1){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>取消负责人</a>"
						}
						
						if(row.isCharge==0 ){
							result += "<a class='btn btn-default fa fa-thumbs-o-up userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>设置为负责人</a>"
						}
						if(row.isCharge==2){
							result += "<a class='btn btn-default fa fa-thumbs-o-down userEdit' onclick='setCharge(" + row.userId + ","+row.isCharge+",false,"+row.orgId+")' herf='javascript:void(0)'>取消主负责人</a>"
						}
					}
					
					result += "<a class='btn btn-default fa ' onclick='order(" + row.orgUserId+")' >修改排序</a>";
					result += "<a class='btn btn-default fa ' onclick='underUserList(" + row.userId+","+row.orgId+")' >下属管理</a>";
					return result;
				}
			} */ ] ]
		}));
	}
</script>
