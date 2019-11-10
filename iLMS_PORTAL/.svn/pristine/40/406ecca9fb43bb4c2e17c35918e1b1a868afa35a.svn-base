<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'组织管理',split:true" style="width:200px;overflow: auto;">
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<option value="0">---------全部---------</option>
				</select>
			</div>
	        <div id="orgTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;">
			 	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"  onload="loadcomplete()"></iframe>
		</div>
		<div class="hidden">
			<div id="orgMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'add'">添加子级组织</div>
				 <div data-options="name:'edit'">编辑当前组织</div>
				 <div data-options="name:'remove'">删除当前组织</div> 
				 <div data-options="name:'fresh'">刷新</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
//加载树
var orgTree ;
var isloading=false;
function loadTree(demId) {
	var param = {"demId":demId};//行政维度id
	var ztreeCreator = new ZtreeCreator('orgTree', __ctx + "/org/org/getTreeDataByDemid")
 			.setCallback({onClick:zTreeOnLeftClick,onRightClick:zTreeOnRightClick})
 			.initZtree({"demId":demId},1,function(treeObj){orgTree = treeObj});
	
	ztreeCreator.setAsync({
		enable : true,
		url :  __ctx + "/org/org/getTreeDataByDemid",
		autoParam : ["id"],
		otherParam: param
	});

};

function loadcomplete() {
	$.topCall.closeProgress();
};

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	var parentId = treeNode.id;
	var url = "${ctx}/org/org/orgFrame?id=" + curSelectNode.id+"&demId="+curSelectNode.demId;
	if(curSelectNode.id=="0"){
		$.topCall.warn('该节点为根节点，请点击具体的组织');
		return false;
	}
	$.topCall.progress();
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
		var selectNode = getSelectNode();
		refreshNode(selectNode.demId);
	}else if("auth" == item.name){
		orgAuth();
	}
};
function refreshNode(demId) {
	loadTree(demId);
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
	var url = "${ctx}/org/org/orgEdit";
	url+=(type==1) ? "?id=" + id : "?parentId=" + id;
	url+= "&demId="+demensionId;
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
			refreshNode(demId);
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
	var url = "${ctx}/org/sysDemension/listJson";
	var params = {};
	$.post(url, params, function(result) {
		var demensionList = result.rows;
		var optionStr = "<option value='0'>------------------</option>";
		/* var optionStr = ""; */
		for(var i=0;i<demensionList.length;i++){
			optionStr += "<option value='"+demensionList[i].id+"' >"+demensionList[i].demName+"</option>"
		}
		$("#demensionId").find("option").remove();
		$("#demensionId").append(optionStr);
	});
}
</script>
<script>
	$(function() {
		loadTree(1);
		loadDemension();
		$("#demensionId").val("1");
		$('#demensionId').change(function() {
			var demensionId = $(this).val();
			if(demensionId == 0)return;
			loadTree(demensionId);
		});
	});

</script>
