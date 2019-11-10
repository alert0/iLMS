<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组合对话框预览</title>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/combinateDialog/combinateDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/combinateDialog/showController.js"></script>
<script type="text/javascript">
	var alias = "${param.alias}";
	var id="${param.id}";
	//树被点击的方法
	function treeClick(data){
		getScope().treeDataChange(data);
	}
	
	function getResult(){
		return $("#listFrame")[0].contentWindow.getResult();
	}
	
	$(function(){
		$("#treeFrame,#listFrame").height($("#container").height()-5);
	})
</script>
</head>
<body ng-app="app" ng-controller="ctrl">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="树" style="width: 250px;" id="container">
			<iframe id="treeFrame" style="overflow:auto; width: 100%; height: 100%" frameborder="0" ></iframe>
		</div>
		<div data-options="region:'center',title:'列'" >
			<iframe id="listFrame" style="overflow: auto; width: 100%; height: 100%" frameborder="0" ></iframe>
		</div>
	</div>
</body>
</html>
