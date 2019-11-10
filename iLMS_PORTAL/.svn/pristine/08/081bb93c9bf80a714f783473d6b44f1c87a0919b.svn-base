<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html ng-app="bpmApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务办理</title>
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
			CombinateDialog.open({alias:"userSelector",callBack:callBack});
    	};
	} ]);
</script>
<script type="text/javascript">
	$(function() {
		var frm = $('#agreeForm');
		var json = window.passConf.data;
		
		frm.addHidden('bpmFormId', window.passConf.bpmFormId);
		var opinion=window.passConf.opinion;
		$("#opinion").val(opinion);
		
		$("a.fa-save").click(function(){
			var scope = AngularUtil.getScope();
			if(scope.agreeForm.$invalid){
				$.topCall.error("表单校验不通过");
				return;
			}
			var data=handlerOpinionJson(json);
			frm.addHidden('data', data);
			
			
			var nodeUsers = getNodeUser();
			frm.find("[name='nodeUsers']").remove();
			frm.addHidden('nodeUsers', nodeUsers);
			
			var ele = document.getElementById("bussinessFrom");
			var iframeObj = ele && ele.contentWindow; 
			var defer;
			if(iframeObj && iframeObj.saveForm){
				defer = iframeObj.saveForm();
				defer.done(function(){
					frm.ajaxForm({
						success : showResponse
					});
					$.topCall.progress();
					frm.submit();
				});
				defer.fail(function(msg){
					$.topCall.error( msg || "保存业务表单数据失败");
				});
			}else{
				frm.ajaxForm({
					success : showResponse
				});
				$.topCall.progress();
				frm.submit();
			}
		});
	});
	
	
	function handlerOpinionJson(json){
		var opinion=$("#opinion").val();
		var jsonObj=eval("(" +json+")");
		var opinionObj=jsonObj.__form_opinion;
		if(!opinionObj) return json;
		for(var key in opinionObj){
			opinionObj[key]=opinion;
		}
		return angular.toJson(jsonObj);
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
	 	nodeUsers = JSON.stringify(nodeUsers)
	 	return nodeUsers;
	}
	function showResponse(responseText) {
		$.topCall.closeProgress();
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		//执行后置脚本
		var data = JSON.parse(responseText);
		var script = "var tempFunction = function(data){ "+window.parent.curent_btn_after_script_+"};"
		var afterScriptPassed =  eval(script+"tempFunction(data);");
	
		if (resultMessage.isSuccess()) {
			refreshWelcomeList();//刷新首页信息列表
			$.topCall.message({
				base : {
					type : 'alert',
					title : '温馨提示',
					msg : resultMessage.getMessage(),
					fn : function(r) {
						 try {
						    window.parent.opener.refreshTargetGrid("grid");
						    if(afterScriptPassed !== false)window.parent.close(); 
						 }
						 catch(err)
						 {
						     window.parent.close(); 
						 }
					}
				}
			});
		} else {
			$.topCall.error(resultMessage.getMessage(), resultMessage
					.getCause());
		}
	}
	
	function refreshWelcomeList(){
		try {
			window.parent.opener.location.reload();
		 } catch(err){}
	}

	function setOpinion(approvalItem){
		var oldOpinion = $("#opinion").val();
		$("#opinion").val(oldOpinion+approvalItem);
	}
</script>
</head>
<body  ng-controller="mainCtrl">
		<div class="toolbar-panel ">
		<div class="buttons" >
			<a href="javascript:;" class="btn btn-primary fa fa-save">确定</a> 
			<a href="javascript:;" onclick="javascript:window.selfDialog.dialog('close');" class="btn btn-default fa fa-close">取消</a>
			<c:if test="${directHandlerSign}">
				<div class="checkbox-inline">
					  <label ht-tip title="您拥有会签特权，可以对会签任务进行直接处理。">
						<input type="checkbox" name="directHandlerSign" value="true" />特权：直接处理
					  </label>
				</div>
			</c:if>
		</div>
	</div>
	
	<div <c:if test="${!empty param.urlForm}"> class="easyui-tabs" </c:if> style="padding:0;" data-options="fit:true,border:false">
		<div tabid="agreeForm" title="审批">
			<form id="agreeForm" name="agreeForm" action="complete" method="post">
				<input type="hidden" name="taskId" value="${taskId}"> 
				<input type="hidden" name="actionName" value="${actionName}">
				<table cellspacing="0" class="table-form">
					<c:if test="${actionName eq 'agree' or actionName eq 'oppose' }">
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
					</c:if>
					<tr ng-if="jumpType=='common' && !${isGoNextJustEndEvent} && !${ actionName eq 'opposeTrans'}">
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;">选择执行人</th>
						<td>
							<div name="destinationDiv">
								<span name = "nodeUserSpan">
								</span>
								<a href="javascript:;" class="btn btn-xs btn-primary" ng-click="chooseUser()">选择</a>{{isGoNextJustEndEvent}}
							</div>
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
					<tr name="nomalTaskAgree">
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; color:#10451A;"><c:choose>
								<c:when test="${actionName eq 'agree'}">审批意见</c:when>
								<c:when test="${actionName eq 'oppose'}">反对意见</c:when>
								<c:when test="${actionName eq 'abandon'}">弃权原因</c:when>
								<c:when test="${actionName eq 'agreeTrans' or actionName eq 'opposeTrans'}">审批意见</c:when>
							</c:choose></th>
						<td colspan="3">
						<textarea id = "opinion" name="opinion" cols="60" rows="5" class="inputText input-width-80" style="margin:6px;"></textarea></td>
					</tr>
					<tr name="nomalTaskAgree">
						<th width="20%" style="text-align:right; font-size:14px; font-weight:normal;padding-right:5px; padding-top:10px; padding-bottom:10px; color:#10451A;">常用语</th>
						<td colspan="3">
						   <c:forEach items="${approvalItem}" var="approvalItem">
						   		<abbr ht-tip title='${approvalItem}'><a style="margin-bottom: 5px;max-width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" class="btn btn-default fa" onClick="setOpinion('${approvalItem}')">${approvalItem}</a></abbr>
						   </c:forEach>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<c:if test="${!empty param.urlForm}">
			<div title="业务表单">
				<iframe id="bussinessFrom" onload="loadIframe(this)" style="width: 100%; height: 100%; "  frameborder="no" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes" src="${param.urlForm }" pane-src="0"></iframe>
			</div>
		</c:if>
	</div>
	
</body>
</html>