<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'通讯录',split:true" style="width:200px;overflow: auto;">
			<!-- <div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<option value="0">---------全部---------</option>
				</select>
			</div> -->
	        <div id="orgTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;">
			 	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
		</div>
<!-- 		<div class="hidden">
			<div id="orgMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'add'">添加子级组织</div>
				 <div data-options="name:'edit'">编辑当前组织</div>
				 <div data-options="name:'remove'">删除当前组织</div> 
				 <div data-options="name:'fresh'">刷新</div>
			</div>
		</div> -->
	</div>
</body>
</html>
<script>
//加载树
var orgTree ;
function loadTree(demId) {
	var param = {"demId":demId,"scope":"son"};//行政维度id
	var ztreeCreator = new ZtreeCreator('orgTree', __ctx + "/org/org/getTreeDataByDemid")
	.setCallback({onClick:zTreeOnLeftClick})
	.initZtree(param,1,function(treeObj){
		orgTree = treeObj;
		if(orgTree.getNodes().length >0){
			var curSelectNode = orgTree.getNodes()[0];
			var url = "${ctx}/business/addressBook/addressBook/addressBookFrame?id=" + curSelectNode.id+"&demId="+curSelectNode.demId;
			$("#listFrame").attr("src", url);
		}
	});

	ztreeCreator.setAsync({
		enable : true,
		url :  __ctx + "/org/org/getTreeDataByDemid",
		autoParam : ["id"],
		otherParam: param
	});
};

//左击
 function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	var parentId = treeNode.id;
	var url = "${ctx}/business/addressBook/addressBook/addressBookFrame?id=" + curSelectNode.id+"&demId="+curSelectNode.demId;
	$("#listFrame").attr("src", url);
}; 

function refreshNode(demId) {
	loadTree(demId);
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
	});

</script>
