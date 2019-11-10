<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html ng-app="bpmApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择跳转路径</title>
<%@include file="/commons/include/ngEdit.jsp" %>
<style type="text/css">
div.panel {
	border: 1px solid #ddd;
}
</style>
<script type="text/javascript"
	src="${ctx}/js/platform/bpm/service/ui-hotent.js"></script>
<script type="text/javascript">
	var bpmApp = angular.module("bpmApp", [ 'formDirective', 'ui.hotent' ]);
	bpmApp.controller("mainCtrl", [ '$scope', function($scope) {
		$scope.jumpType = '${jumpType}';
		$scope.jumpType = $scope.jumpType.split(",")[0];
		$scope.selectNode = '';
		$scope.chooseUser = function(){
			// 不必多选设置执行人的时候
			var callBack =function(data,dialog){
				var html = "";
				for(var i = 0 ,user; user=data[i++];){
					html = html + '<label class="checkbox-inline" name="nodeUserLabel"><input type="checkbox" name="nodeUser"  checked="checked" value="'+user.id+","+user.name+',user" />'+user.name+'</label>'
				}
				$("span[name='nodeUserSpan']").html(html);
			    dialog.dialog('close');
			};
			
			CustomDialog.openCustomDialog('userSelector',callBack,{
				selectNum:"-1"
			});
    	};
	} ]);
</script>
<script type="text/javascript">
	
	function getResult(){
		return getNodeUser();
	}
	
	function getNodeUser(){
	 	var destination = $("[name='destination']").val();
	 	var userArray = [];
	 	var nodeUsers =[];
	 	$("input[name='nodeUser']:checked").each(function (){
			var strVal = $(this).val().split(",");
			var user = {
				id:strVal[0],
				name:strVal[1],
				type:strVal[2]
			};
			userArray.push(user);
		});
	 	var nodeUser ={
				nodeId:destination,
				executors:userArray
		};
	 	nodeUsers.push(nodeUser);
	 	return nodeUsers;
	}

</script>
</head>
<body  ng-controller="mainCtrl">
	
	<div style="padding:0;" data-options="fit:true,border:false">
			<form id="agreeForm" name="agreeForm" action="complete" method="post">
				<table cellspacing="0" class="table-form">
					<tr ng-show="${fn:contains(jumpType,'select') || fn:contains(jumpType,'free') ||  fn:contains(jumpType,'common') }"> 
						<th>跳转类型</th>
						<td colspan="3">
								<label class="radio-inline"  ng-show="${fn:contains(jumpType,'common')==true}" > 
									<input type="radio" name="jumpType" ng-model="jumpType" value="common" ng-checked="jumpType=='common'"  />正常跳转
								</label>
								<label class="radio-inline" ng-show="${fn:contains(jumpType,'select')==true}"> 
									<input type="radio" name="jumpType" ng-model="jumpType" value="select" ng-checked="jumpType=='select'"  />选择路径跳转
								</label>
								<label class="radio-inline" ng-show="${fn:contains(jumpType,'free')==true}">
									<input type="radio" name="jumpType" ng-model="jumpType" value="free"   ng-checked="jumpType=='free'" />自由跳转
								</label>
						</td>
					</tr>
					<tr ng-if="jumpType=='select'">
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;"><abbr ht-tip title="自由跳转的目标节点">目标节点</abbr></th>
						<td><select style="margin:6px;" class="inputText" ng-model="destination" name="destination" ht-validate="{'required':true}">
								<c:forEach items="${outcomeNodes}" var="outcomeNodes">
									<option value="${outcomeNodes.nodeId}"  nodeType="${outcomeNodes.type}">${outcomeNodes.name}</option>
								</c:forEach>
							</select>
						</td>
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;">选择执行人</th>
						<td>
							<c:forEach items="${outcomeUserMap}"  var="outcomeUserMap"><!-- 选择路径这块的代码有点烂，先凑合用。。。 -->
								<div name="destinationDiv" ng-if="destination =='${outcomeUserMap.key}'">
									<span name = "nodeUserSpan">
										<c:forEach items="${outcomeUserMap.value}"  var="user">
											<label class="checkbox-inline" ><input  type="checkbox" name="nodeUser" checked="checked" value="${user.id},${user.name},${user.type}" />${user.name}</label>
										</c:forEach>
									</span>
									<a href="javascript:;" class="btn btn-xs btn-primary" ng-click="chooseUser()">选择</a>
								</div>
							</c:forEach>
						</td>	
					</tr>
					<tr ng-if="jumpType=='free'">
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;"><abbr ht-tip title="自由跳转的目标节点">目标节点</abbr></th>
						<td>
							<select style="margin:6px;" class="inputText" ng-model="destination" name="destination"  ht-validate="{'required':true}">
									<c:forEach items="${allNodeDef}" var="nodeDef">
										<option value="${nodeDef.nodeId}"  nodeType="${nodeDef.type}">${nodeDef.name}</option>
									</c:forEach>
							</select>
						</td>
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;">选择执行人</th>
						<td>
							<c:forEach items="${allNodeUserMap}"  var="allNodeUserMap">
								<div name="destinationDiv" ng-if="destination =='${allNodeUserMap.key}'">
									<span name = "nodeUserSpan">
										<c:forEach items="${allNodeUserMap.value}"  var="user">
											<label class="checkbox-inline" ><input  type="checkbox" name="nodeUser" checked="checked" value="${user.id},${user.name},${user.type}" />${user.name}</label>
										</c:forEach>
									</span>
									<a href="javascript:;" class="btn btn-xs btn-primary" ng-click="chooseUser()">选择</a>
								</div>
							</c:forEach>
						</td>	
					</tr>
				</table>
			</form>
	</div>
	
</body>
</html>