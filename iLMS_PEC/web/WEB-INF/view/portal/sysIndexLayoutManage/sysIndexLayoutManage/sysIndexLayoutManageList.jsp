<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>布局管理列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
<script>
var authId = "${param.authId}";
var authOrgId = "${param.authOrgId}";
var layoutPerms = "${param.layoutPerms}";
$(function(){
	if(authId){
			if(layoutPerms.indexOf("add") == -1){
				$("a.layoutAdd").hide();
			}
			if(layoutPerms.indexOf("delete") == -1){
				$("a.layoutDel").hide();
			}
			if(layoutPerms.indexOf("edit") == -1  ){
				$("a.layoutEdit").hide();
			}
	}
});
function changDefault(id,action,layoutType){
	var type = "PC端";
	if(layoutType == 1){
		type = "手机端";
	}
	var msg= action == "set"?"确定设置"+type+"默认布局吗?":"确定取消"+type+"默认布局吗?";
	if(action == "set"){
		try {
			var  hasMaster = getLayoutIsDefault(layoutType);
			if(hasMaster){
				msg = "该部门"+type+"已有默认布局,确定重新设置"+type+"默认布局吗？";
			}
		} catch (e) {}
	}
	$.topCall.confirm("提示信息",msg,function(rtn){
		if(!rtn) return;
		var url = "${ctx}/portal/sysIndexLayoutManage/sysIndexLayoutManage/changDefault";
		var defParams={"id":id,"action":action,"layoutType":layoutType,"orgId":authOrgId};
		$.post(url,defParams,function(responseText){
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				refreshTargetGrid("grid");
			}
		});
	});
	
}
function getLayoutIsDefault(layoutType){
	var isDefLayoutId = false;
	var url = "${ctx}/portal/sysIndexLayoutManage/sysIndexLayoutManage/getLayoutDef";
	var defParams={"orgId":authOrgId,"layoutType":layoutType};
	$.ajax({
		url : url,
		async:false,
		data:defParams,
		dataType : 'json',
		success : function(result) {
			if(parseInt(result.result) ==1){
				isDefLayoutId = true;
			}
		}
	});
	return isDefLayoutId;
}
function design(id){
	var url = __ctx+"/portal/sysIndexLayoutManage/sysIndexLayoutManage/design?orgId="+authOrgId;
	if(id){
		url = __ctx+"/portal/sysIndexLayoutManage/sysIndexLayoutManage/design?id="+id+"&orgId="+authOrgId;
	}
	$.openFullWindow(url);
}
function designMobile(id){
	var url = __ctx+"/portal/sysIndexLayoutManage/sysIndexLayoutManage/designMobile?orgId="+authOrgId;
	if(id){
		url = __ctx+"/portal/sysIndexLayoutManage/sysIndexLayoutManage/designMobile?id="+id+"&orgId="+authOrgId;
	}
	window.open(url,'','width=564,height=647');
}
function openLayoutSetting(id){
	var url = __ctx+"/portal/sysLayoutSetting/sysLayoutSetting/sysLayoutSettingEdit?id="+id;
	HT.window.openEdit(url, '布局设置', 'edit', 'grid', 400, 300, null, null, id, true);
}
</script>
<style type="text/css">
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber{
       text-overflow: ellipsis;
   }
</style> 
</head>
<body>
	<div class="easyui-layout" fit="true" >
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
							<a class="btn btn-sm btn-primary fa-add layoutAdd " href="javascript:void(0)" onclick="design()">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add layoutAdd " href="javascript:void(0)" onclick="designMobile()">
								<span>添加手机布局</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove layoutDel" href="javascript:void(0)" action="/portal/sysIndexLayoutManage/sysIndexLayoutManage/remove">
								<span>删除</span>
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
								<li><span>布局名称:</span><input class="inputText" type="text" name="Q^m.name^SL"></li>
								<li><span>布局描述:</span><input class="inputText" type="text" name="Q^m.memo^SL"></li>
								<li>
								<span>是否是默认:</span>
								<select name="Q^m.IS_DEF^SN"  class="inputText">
									<option value="">请选择</option>
									<option value="1" >是</option>	
									<option value="0" >否</option>
								</select>
								</li>
								<li>
								<span>布局类型:</span>
								<select name="Q^m.LAYOUT_TYPE^SN"  class="inputText">
									<option value="">请选择</option>
									<option value="0" >PC端</option>	
									<option value="1" >手机端</option>
								</select>
								</li>
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
	
	/**
	 * 设置设置权限。
	 * ownerNameJson 格式。
	 * [{type:"everyone",title:"所有人"},{type:"user",title:"用户",id:"1,2",name:"ray,tom"}]
	 */
	 function setAuth(id){
		var ownerNameJson = getColumnRights(id);
		RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
			data = RightsUtil.rightsDataToArray(data);
			saveColumnRights(id,data);
			dialog.dialog('close');
		},ownerNameJson);
	}
	
	 function getColumnRights(id){
			var ownerNameJson = [];
			$.ajax({ 
		       type: "get", 
		       url: "getRights?id="+ id, 
		       async:false,
		       dataType:'json',
		       success: function(data){ 
		    	   ownerNameJson = data;
		       } 
			});
			return ownerNameJson;
		}
		
		function saveColumnRights(id,ownerNameJson){
			var params = {
				id : id,
				rightsData : JSON.stringify(ownerNameJson),
			};
			$.ajax({
				   type: "POST",
				   url: __ctx + "/portal/sysIndexLayoutManage/sysIndexLayoutManage/saveRights",
				   async:true,
				   dataType:'json',
				   data: params,
				   success: function(data){
					   if(data.result==1){
						   $.topCall.alert("温馨提示",data.message);
						}else{
							$.topCall.error(data.message);
						}
				}
			}); 
		}
		
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?orgId="+authOrgId,
			idField : "id",
			sortName : 'id',
			sortOrder : 'desc',
			columns : [ [
		    {field : 'id',sortName : "id",checkbox : true},
			{field : 'name',sortName : "name",title : '布局名称',width :160,align : 'center',sortable : 'true'}, 
			{field : 'memo',sortName : "memo",title : '布局描述',width : 150,align : 'center',sortable : 'true', formatter: function(value,row,index){
                return '<span  title='+value+'>'+value+'</span>'  
            }}, 
			{field : 'orgName',sortName : "org_id",title : '所属组织',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'isDef',sortName : "is_def",title : '是否默认',width : 150,align : 'center',sortable : 'true',
				formatter:function(value,row,index){ return value==0?"否":"是";}	
			}, 
			{field : 'layoutType',sortName : "layout_type",title : '布局类型',width : 150,align : 'center',sortable : 'true',
				formatter:function(value,row,index){ return value==0?"PC端":"手机端";}	
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					if(authId){//分级管理员
						if(layoutPerms.indexOf("edit") > -1){
							if(row.layoutType=="0"){
								result = "<a class='btn btn-default fa fa-edit layoutEdit' onClick='design("+row.id+")' href='javascript:void(0)'>编辑</a>";
							}else{
								result = "<a class='btn btn-default fa fa-edit layoutEdit' onClick='designMobile("+row.id+")' href='javascript:void(0)'>编辑</a>";
							}
						}
						if(layoutPerms.indexOf("delete") >-1){
							result += "<a class='rowInLine btn btn-default fa fa-remove layoutDel' action='/portal/sysIndexLayoutManage/sysIndexLayoutManage/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
						}
						if(row.isDef=="0"){
	                  		  result += "<a class='btn btn-default fa fa-reply-all' onClick='changDefault("+row.id+",\"set\","+row.layoutType+")' herf='#'>设为默认</a>";
	                  	}else{
		                  	result += "<a class='btn btn-default fa fa-reply-all' onClick='changDefault("+row.id+",\"cancel\","+row.layoutType+")' herf='#'>取消默认</a>";
	                  	}
					}else{
						result = "<a class='btn btn-default fa fa-edit layoutDel' onClick='design("+row.id+")' href='javascript:void(0)'>编辑</a>";
						result += "<a class='rowInLine btn btn-default fa fa-remove layoutDel' action='/portal/sysIndexLayoutManage/sysIndexLayoutManage/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
						if(row.isDef=="0"){
	                  		  result += "<a class='btn btn-default fa fa-reply-all' onClick='changDefault("+row.id+",\"set\","+row.layoutType+")' herf='#'>设为默认</a>";
	                  	}else{
	                  		result += "<a class='btn btn-default fa fa-reply-all' onClick='changDefault("+row.id+",\"cancel\","+row.layoutType+")' herf='#'>取消默认</a>";
	                  	}
					}
					result += "<a class='btn btn-default fa fa-eye' onClick='setAuth(" + row.id + ")' herf='#'>授权</a>";
					result += "<a class='btn btn-default fa fa-edit' onClick='openLayoutSetting("+row.id+")' herf='#'>布局设置</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
