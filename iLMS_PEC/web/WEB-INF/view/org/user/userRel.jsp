<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'用户关系管理',split:true" style="width:300px;overflow: auto;">
	        <div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;">
			   <div id="sysUserRelTree" class="ztree"></div>
		</div>
		<div class="hidden">
			<div id="orgMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'add'">分配下级</div>
				<div data-options="name:'edit'">编辑</div>
				<div data-options="name:'remove'">删除</div> 
				<div data-options="name:'fresh'">刷新</div>
			</div>
			<div id="typeMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'add'">添加用户</div>
				<div data-options="name:'fresh'">刷新</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
//加载树
var sysTypeTree =  null, sysUserRelTree = null,typeId="";
function loadTree() {
	sysTypeTree =  new SysTypeTree( $('#sysTypeTree'),{
	   	typeKey: __CAT_USER_REL,
		onClick:zTreeOnLeftClick
	});
};

function loadSysUserRelTree(typeId){
	// 获取当前分类下的用户关系
	sysUserRelTree = new ZtreeCreator('sysUserRelTree', __ctx + "/org/sysUserRel/getTreeData?typeId="+typeId)
			.setDataKey({idKey :"id",pIdKey : "parentId",name : "text",title: "text"})
 			.setCallback({onClick:zTreeOnRightClick,onRightClick:zTreeOnRightClick})
 			.initZtree({},function(treeObj){sysUserRelTree = treeObj});
}

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	typeId = treeNode.id;
	if(curSelectNode.isRoot){
		$.topCall.warn('该节点为根节点，请点击具体的节点');
		return false;
	}
	loadSysUserRelTree(typeId);
};
//左击
function zTreeOnLeftClick2(event, treeId, treeNode) {
	$.topCall.toast('提示','请您右击操作');
};
/**
 * 树右击事件
 */
function zTreeOnRightClick(e, treeId, treeNode) {
	if (treeNode) {
		sysUserRelTree.selectNode(treeNode);
		curSelectNode=treeNode;
		var isfolder = treeNode.isFolder;
		var h = $(window).height();
		var w = $(window).width();
		var menuWidth = 120;
		var menuHeight = 75;
		var menu = null;
		if (treeNode != null) {
			if(curSelectNode.parentId){
				menu = $('#orgMenu');
			}else{
				menu = $('#typeMenu');
			}
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
//菜单对应项
function menuHandler(item) {
	if ('add' == item.name) {
		editNode();
	} else if ('remove' == item.name) {
		delNode();
	} else if ('sort' == item.name) {
		sortNode();
	} else if ('edit' == item.name) {
		setLevel();
	} else if ('fresh' == item.name) {
		refreshNode();
	} 
};
function refreshNode() {
	loadSysUserRelTree(typeId);
};
//添加节点
function editNode() {
	var selectNode = getSelectNode();
	if (!selectNode) 	return;
	//根节点 不能编辑
	if(selectNode.id=="0"){
		$.topCall.warn('该节点为根节点，不可编辑');
		return;
	}
	openUserRelList(selectNode);
	
};

function openUserRelList(selectNode){
	var dialogName = "为【"+selectNode.userName+"】用户分配下级";
	if(!curSelectNode.account){
		dialogName = "添加用户";
	}
	var url=__ctx+"/org/user/userRelList?typeId="+selectNode.typeId + "&id="+selectNode.id;
	HT.window.openEdit(url, dialogName, "add", 'grid', 800, 600, null, null, null, false);
}

// 设置级别
function setLevel(){
	var selectNode = getSelectNode();
	if (!selectNode) 	return;
	//根节点 不能编辑
	if(selectNode.id=="0"){
		$.topCall.warn('该节点为根节点，不可设置');
		return;
	}
	
	var def={
	        passConf:'',title:'编辑',width:400, height:300, modal:true,resizable:true,iconCls: 'fa fa-user',
	        buttonsAlign:'center'
		};
		$.topCall.dialog({
			src: __ctx+'/org/user/userRelEdit?id='+selectNode.id+'&userId='+selectNode.userId+'&userName='+selectNode.userName,
			base:def
		});
	
}

//删除
function delNode() {
	var selectNode = getSelectNode();
	var nodeId = selectNode.id;
	if (selectNode.parentId == "0") {
		$.topCall.warn('该节点为根节点，不可删除');
		return;
	} 
	
	var nodeIds = [];
	nodeIds.push(nodeId);
	getNodeIds(selectNode,nodeIds);
	nodeIds = nodeIds.join(",");
	var callback = function(rtn) {
		if (!rtn)
			return;
		var url = "${ctx}/org/sysUserRel/remove";
		var params = {
			id : nodeIds
		};
		$.post(url, params, function(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {//成功
				sysUserRelTree.removeNode(selectNode);
			} else {
				$.topCall.error(obj.getMessage());
			}
			refreshNode();
		});
	};
	$.topCall.confirm('温馨提示', '确定删除该用户【'+selectNode.userName+'】以及其下的所有下级吗？', callback);
};
//选择资源节点。
function getSelectNode() {
	sysTypeTree = $.fn.zTree.getZTreeObj("sysUserRelTree");
	var nodes = sysTypeTree.getSelectedNodes();
	var node = nodes[0];
	return node;
};

function getNodeIds(selectNode,nodeIds){
	 for( var idx =0; idx<selectNode.children.length;idx++ ){
		 nodeIds.push(selectNode.children[idx].id);
		 if( selectNode.children[idx].children ){
			 getNodeIds(selectNode.children[idx],nodeIds)
		 }
	 }
}

</script>
<script>
	$(function() {
		loadTree();
	});

</script>
