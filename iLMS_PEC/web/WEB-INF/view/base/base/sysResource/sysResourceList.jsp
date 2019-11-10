<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>子系统资源</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script>
	$(function(){
		var obj=$("#listFrame");
		$(obj).height($(obj).parent().height()-10);
		if(parent.getTemplateResourceUrl){
			$.topCall.alert("温馨提示", "请选择左侧要添加到的目录，鼠标右键“添加资源”！", "info");
		}
	})
</script>

</head>
<body >
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'资源管理',split:true" style="width:200px;overflow:auto;">
	        <div id="sysResourceTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;height:100%;">
			 	<iframe id="listFrame"   frameborder="no" style="width:100%;height:100%" ></iframe>
		</div>
		<div class="hidden">
			<div id="hasChildMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				 <div data-options="name:'add'" class="menu-item">添加资源</div>
				 <div data-options="name:'edit'"  class="menu-item">编辑资源</div> 
				 <div data-options="name:'remove'"  class="menu-item">删除资源</div> 
				 <div data-options="name:'move'"  class="menu-item">移动资源</div> 
				 <div data-options="name:'fresh'"  class="menu-item">刷新</div>
			</div>
			
			<div id="noChildMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				 <div data-options="name:'edit'"  class="menu-item">编辑资源</div> 
				 <div data-options="name:'remove'"  class="menu-item">删除资源</div> 
				 <div data-options="name:'move'"  class="menu-item">移动资源</div> 
				 <div data-options="name:'fresh'"  class="menu-item">刷新</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
var systemId="${param.systemId}";
//加载树
var expandByDepth = 0;
var curSelectNode;
var resourceTree;

function loadTree() {
	var setting = {
		async : {enable : false},
		data : {
			key : {name : "name"},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : 1
			}
		},
		view : {
			selectedMulti : false
		},
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		callback : {
			onClick : zTreeOnLeftClick,
			onRightClick : zTreeOnRightClick,
		}
	};
	var url = __ctx + "/base/base/sysResource/getTreeData";
	var params = {systemId:systemId};
	$.post(url, params, function(result) {
		resourceTree = $.fn.zTree.init($("#sysResourceTree"), setting, result);
		resourceTree.expandAll(true);
		if(curSelectNode){
			resourceTree.selectNode(curSelectNode);
		}
	});

};

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	var parentId = treeNode.id;
	var url = "${ctx}/base/base/sysResource/sysResourceGet?id=" + curSelectNode.id;
	if(curSelectNode.id=="0"){
		return false;
	}
	$("#listFrame").attr("src", url);
};
/**
 * 树右击事件
 */
function zTreeOnRightClick(e, treeId, treeNode) {
	//debugger;
	if (treeNode) {
		resourceTree.selectNode(treeNode);
		curSelectNode=treeNode;
		var hasChild = treeNode.hasChildren;
		var h = $(window).height();
		var w = $(window).width();
		var menuWidth = 120;
		var menuHeight = 75;
		var menu = hasChild?$('#hasChildMenu'):$('#noChildMenu');

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
		editNode(0);
	} else if ('remove' == item.name) {
		delNode();
	} else if ('move' == item.name) {
		moveNode();
	} else if ('edit' == item.name) {
		editNode(1);
	} else if ('fresh' == item.name) {
		refreshNode();
	}
};

function refreshNode() {
	loadTree();
};



// 移动节点
function moveNode() {
	var selectNode = getSelectNode();
	var nodeId = selectNode.id;
	if (nodeId == "0") {
		$.topCall.warn('该节点为根节点，不可移动');
		return;
	} 
	
	var conf = {param:{"SYSTEM_ID_":systemId}};

	CustomDialog.openCustomDialog('resourceSelector',function(data,dialog){
	    var parentId = data[0].ID_;
			
			var callback = function(rtn) {
				if (!rtn)
					return;
				var url = "${ctx}/base/base/sysResource/moveResource";
				var params = {
					id : nodeId,
					parentId : parentId
				};
				$.post(url, params, function(responseText) {
					var obj = new com.hotent.form.ResultMessage(responseText);
					if (obj.isSuccess()) {//成功
					} else {
						$.topCall.error(obj.getMessage());
					}
					refreshNode();
				});
			};
			$.topCall.confirm('温馨提示', '确定移动？', callback);

	    dialog.dialog('close');//关闭弹出框

	},conf);
}
//添加节点
function editNode(type) {
	var selectNode = getSelectNode();
	var nodeId = selectNode.id;
	if (type == 1 && nodeId == "0") {
		$.topCall.warn('该节点为根节点，不可编辑');
		return;
	} 
	var id=selectNode.id;
	var systemId = selectNode.systemId;
	var url = "${ctx}/base/base/sysResource/sysResourceEdit" ;
	url += (type==1)? "?id=" + id : "?parentId=" + id;
	url += "&systemId=" + systemId;
	$("#listFrame").attr("src", url);

};

/**
 * 添加节点。
 */
function appendNode(id,alias,name,hasChild){
	var node={hasChildren:hasChild,name:name,alias:alias,id:id,systemId:systemId};
	var nodeId = curSelectNode.id;
	if ( nodeId == "0"){
		resourceTree.addNodes(null, node);
	}
	else{
		resourceTree.addNodes(curSelectNode, node);
	}
}

function updateNode(name){
	curSelectNode.name=name;
	resourceTree.updateNode(curSelectNode);
}


//删除
function delNode() {
	var selectNode = getSelectNode();
	var nodeId = selectNode.id;
	if (nodeId == "0") {
		$.topCall.warn('该节点为根节点，不可删除');
		return;
	} 
 
	var callback = function(rtn) {
		if (!rtn)
			return;
		var url = "${ctx}/base/base/sysResource/remove";
		var params = {
			id : nodeId
		};
		$.post(url, params, function(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {//成功
				resourceTree.removeNode(selectNode);
				$("#listFrame").attr("src", "about:blank");
			} else {
				$.topCall.error(obj.getMessage());
			}
			refreshNode();
		});
	};
	$.topCall.confirm('提示信息', '确定删除？', callback);
};
//选择资源节点。
function getSelectNode() {
	resourceTree = $.fn.zTree.getZTreeObj("sysResourceTree");
	var nodes = resourceTree.getSelectedNodes();
	var node = nodes[0];
	return node;
};
</script>
<script>
	$(function() {
		loadTree();
	});

</script>
