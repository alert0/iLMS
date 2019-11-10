<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
</head>
<body>
	<div id="dataShow" class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',title:'分级组织管理',split:true" style="width:200px;overflow: auto;">
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<option value="0">---------全部---------</option>
				</select>
			</div>
	        <div id="orgTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;">
			 	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
		</div>
		<div class="hidden">
			<div id="orgMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div class="hidden" id="addOrg" data-options="name:'add'">添加子级组织</div>
				 <div class="hidden" id="editOrg" data-options="name:'edit'">编辑当前组织</div>
				 <div class="hidden" id="delOrg" data-options="name:'remove'">删除当前组织</div> 
				 <div data-options="name:'fresh'">刷新</div>
			</div>
		</div>
	</div>
	<div id="warnTip" class="hiddden" style="margin-left: 400px;margin-top: 300px;font-size: 15px;">
		您不是组织管理员，或者尚未授权管理任何组织
	</div>
</body>
</html>
<script>
//加载树
var orgTree ;
function loadTree(param) {
	var ztreeCreator = new ZtreeCreator('orgTree', __ctx + "/org/orgAuth/getAuthOrgTree")
 			.setCallback({onClick:zTreeOnLeftClick,onRightClick:zTreeOnRightClick})
 			.initZtree(param,1,function(treeObj){orgTree = treeObj});
	
	ztreeCreator.setAsync({
		enable : true,
		url :  __ctx + "/org/orgAuth/getAuthOrgTree",
		autoParam : ["id"],
		otherParam: param
	});

};

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	var parentId = treeNode.id;
	
	var demensionId = $("#demensionId").val();
	var arr = demensionId.split(",");
	var param =  {"authId":arr[0],"orgId":arr[1]};
	
	var userPerms = curSelectNode.userPerms;
	var posPerms =curSelectNode.posPerms;
	
	var url = "${ctx}/org/orgAuth/orgAuthFrame?id=" + curSelectNode.id+"&demId="+curSelectNode.demId+"&authId="+arr[0]+"&authOrgId="+arr[1];
	url += "&userPerms="+userPerms+"&posPerms="+posPerms;
	if(curSelectNode.id=="0"){
		$.topCall.warn('该节点为根节点，请点击具体的组织');
		return false;
	}
	$("#listFrame").attr("src", url);
};
/**
 * 树右击事件
 */
function zTreeOnRightClick(e, treeId, treeNode) {
	if (treeNode) {
		orgTree.selectNode(treeNode);
		curSelectNode=treeNode;
		var isfolder = treeNode.isFolder;
		var h = $(window).height();
		var w = $(window).width();
		var menuWidth = 120;
		var menuHeight = 75;
		var menu = null;
		
		getMenu(curSelectNode);
		if (treeNode != null) {
			menu = $('#orgMenu');
		}
		
		var x = e.pageX, y = e.pageY;
		if (e.pageY + menuHeight > h) {
			y = e.pageY - menuHeight;
		}
		if (e.pageX + menuWidth > w) {
			x = e.pageX - menuWidth;
		}
		menu.menu('show', {
			left : x,
			top : y
		});
	}
};

function getMenu(selectNode){
	var posPerms = selectNode.posPerms;
	var orgPerms = selectNode.orgPerms;
	var orgauthPerms = selectNode.orgauthPerms;
	var menuStr = "";
	if(orgPerms.indexOf("add") ==-1){
		$("#orgMenu").find("#addOrg").addClass("hidden");
	}else{
		$("#orgMenu").find("#addOrg").removeClass("hidden");
	}
	if(orgPerms.indexOf("edit") == -1){
		$("#orgMenu").find("#editOrg").addClass("hidden");
	}else{
		$("#orgMenu").find("#editOrg").removeClass("hidden");
	}
	if(orgPerms.indexOf("delete") ==-1){
		$("#orgMenu").find("#delOrg").addClass("hidden");
	}else{
		$("#orgMenu").find("#delOrg").removeClass("hidden");
	}
	
}

//菜单对应项
function menuHandler(item) {
	if ('add' == item.name) {
		editNode(0);
	} else if ('remove' == item.name) {
		delNode();
	} else if ('sort' == item.name) {
		sortNode();
	} else if ('edit' == item.name) {
		editNode(1);
	} else if ('fresh' == item.name) {
		var demensionId = $("#demensionId").val();
		var arr = demensionId.split(",");
		var param =  {"authId":arr[0],"orgId":arr[1]};
		refreshNode(param);
	}else if("auth" == item.name){
		orgAuth();
	}
};
function refreshNode(param) {
	loadTree(param);
};

//添加节点
function editNode(type) {
	var selectNode = getSelectNode();
	if (!selectNode) 	return;
	//根节点 不能编辑
	if(selectNode.id=="0" && type==1 ){
		$.topCall.warn('该节点为根节点，不可编辑');
		return;
	}
	
	var demensionId = selectNode.demId;
	var id=selectNode.id;
	
	var arr = $("#demensionId").val().split(",");
	var authId = arr[0];
	
	var url = "${ctx}/org/org/orgEdit";
	url+=(type==1) ? "?id=" + id : "?parentId=" + id;
	url+= "&demId="+demensionId + "&authId="+authId+"&authOrgId="+arr[1];
	$("#listFrame").attr("src", url);

};

//删除
function delNode() {
	var selectNode = getSelectNode();
	var nodeId = selectNode.id;
	var demId = selectNode.demId;
	if (nodeId == "0") {
		$.topCall.warn('该节点为根节点，不可删除');
		return;
	} 
 
	var callback = function(rtn) {
		if (!rtn)
			return;
		var url = "${ctx}/org/org/remove";
		var params = {
			id : nodeId
		};
		$.post(url, params, function(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {//成功
				orgTree.removeNode(selectNode);
				$("#listFrame").attr("src", "about:blank");
			} else {
				$.topCall.error(obj.getMessage());
			}
			var arr = $("#demensionId").val().split(",");
			var param = {"authId":arr[0],"orgId":arr[1]};
			
			refreshNode(param);
		});
	};
	$.topCall.confirm('温馨提示', '确定删除该组织以及该组织下的所有子组织？', callback);
};
//选择资源节点。
function getSelectNode() {
	orgTree = $.fn.zTree.getZTreeObj("orgTree");
	var nodes = orgTree.getSelectedNodes();
	var node = nodes[0];
	return node;
};

//加载行政维度
function loadDemension(){
	var url = "${ctx}/org/orgAuth/getCurrentUserAuthJson";
	var params = {};
	$.post(url, params, function(result) {
		var optionStr = "<option value='0'>-------请选择-----</option>";
		/* var optionStr = ""; */
		if(result && result.rows ){
			var userAuthList = result.rows;
			for(var i=0;i<userAuthList.length;i++){
				var val = userAuthList[i].id + "," +userAuthList[i].orgId;
				var name =userAuthList[i].orgName+ "---["+userAuthList[i].demName+"]";
				optionStr += "<option value='"+val+"'  >"+name+"</option>"
			}
			if(userAuthList.length == 0 ){
				$("#dataShow").addClass("hidden");
				$("#warnTip").removeClass("hidden");
			}
		}

		$("#demensionId").find("option").remove();
		$("#demensionId").append(optionStr);

	});
}
</script>
<script>
	$(function() {
		//loadTree(1);
		loadDemension();
		//$("#demensionId").val("1");
		$('#demensionId').change(function() {
			var demensionId = $(this).val();
			if(demensionId == 0)return;
			var arr = demensionId.split(",");
			var param = {"authId":arr[0],"orgId":arr[1]};
			loadTree(param);
		});
	});

</script>
