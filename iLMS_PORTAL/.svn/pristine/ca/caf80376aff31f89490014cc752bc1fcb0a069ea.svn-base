<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/flow/node/condition/sameNodeSelectorController.js"></script>
<script type="text/javascript">
	var calc = window.passConf.calc;
	var defId = "${param.defId}";
	var nodeId = "${param.nodeId}";

	function getResult() {
		var scope = AngularUtil.getScope();
		
		//获取描叙
		$(scope.nodeList).each(function(){
			if(this.nodeId==scope.data.nodeId){
				scope.data.description = this.name;
			}
		});
		
		return scope.data;
	}
</script>
</head>
<body ng-controller="ctrl">
	<table class="table-form" cellspacing="0">
		<tr>
			<th style="width:100px;">
				<span>节点选择：</span>
			</th>
			<td>
				<select ng-model="data.nodeId" ng-options="m.nodeId as m.name for m in nodeList" class="inputText">
				</select>
			</td>
		</tr>
	</table>
</body>
</html>


