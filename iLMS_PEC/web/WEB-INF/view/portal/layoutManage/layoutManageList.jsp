<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>布局管理</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
</head>
<body>
	<div id="dataShow" class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',title:'组织布局管理',split:true" style="width:200px;overflow: auto;">
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
	</div>
	<div id="warnTip" class="hiddden" style="margin-left: 400px;margin-top: 300px;font-size: 15px;">
		您不是布局管理员，或者尚未授权任何组织管理布局
	</div>
</body>
</html>
<script>
//加载树
var orgTree ;
function loadTree(param) {
	var ztreeCreator = new ZtreeCreator('orgTree', __ctx + "/org/orgAuth/getAuthOrgTree")
 			.setCallback({onClick:zTreeOnLeftClick})
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
	
	var layoutPerms = curSelectNode.layoutPerms;
	var url = "${ctx}/portal/sysIndexLayoutManage/sysIndexLayoutManage/sysIndexLayoutManageList?authId="+arr[0]+"&authOrgId="+curSelectNode.id;
	url += "&layoutPerms="+layoutPerms;
	if(curSelectNode.id=="0"){
		$.topCall.warn('该节点为根节点，请点击具体的组织');
		return false;
	}
	$("#listFrame").attr("src", url);
};


//加载行政维度
function loadDemension(){
	var url = "${ctx}/org/orgAuth/getCurrentUserAuthOrgLayout";
	var params = {};
	$.post(url, params, function(result) {
		var userAuthList = result;
		var optionStr = "<option value='0'>-------请选择-----</option>";
		/* var optionStr = ""; */
		for(var i=0;i<userAuthList.length;i++){
			var val = userAuthList[i].id + "," +userAuthList[i].orgId;
			var name =userAuthList[i].orgName+ "---["+userAuthList[i].demName+"]";
			optionStr += "<option value='"+val+"'  >"+name+"</option>"
		}
		$("#demensionId").find("option").remove();
		$("#demensionId").append(optionStr);
		if(userAuthList.length == 0 ){
			$("#dataShow").addClass("hidden");
			$("#warnTip").removeClass("hidden");
		}else{
			$("#warnTip").remove();
		}
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
