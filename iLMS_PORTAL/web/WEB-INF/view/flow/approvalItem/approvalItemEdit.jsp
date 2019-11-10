<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
<script type="text/javascript" src="${ctx}/js/platform/bpm/approval/approvalController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/bpm/approval/approvalService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
		<script type="text/javascript">
			var $type = '${param.type}';
			var $id = '${param.id}';
			var $bpmApprovalItem = '${bpmApprovalItem}';
		</script>
	</head>
	<body class="easyui-layout" fit="true" ng-app="app" ng-controller="ApprovalController">
			<div class="toolbar-panel">
				<div class="buttons">	      
					<a class="btn btn-primary btn-sm fa-save" href="javascript:void(0);" ng-click="save()"><span>保存</span></a>
                <button onclick="HT.window.closeEdit(true,'grid');" class="btn btn-primary btn-sm fa-close" ><span>关闭</span></button>
				</div>
			</div>
	    	<table class="table-form">
	    		<tr id="trAppType">
	    			<th>常用语类型:</th>
	    			<td>
	    				<c:if test="${param.type=='flow' }">
							<label class="radio-inline"><input type="radio" name="approType" id="approType1" ng-model="approval.type" value="1">全局设置</label>
						</c:if>
						<c:if test="${param.type=='personal' }">
							<label class="radio-inline"><input type="radio" name="approType" id="approType2" ng-model="approval.type" value="4">个人常用语</label>
						</c:if>
	    				<c:if test="${param.type=='flow' }">
							<label class="radio-inline"><input type="radio" name="approType" ng-model="approval.type" value="2">根据流程分类设置</label>
							<label class="radio-inline"><input type="radio" name="approType" ng-model="approval.type" value="3">根据流程设置</label>
						</c:if>
	    			</td>
	    		</tr>
		    	<tr id="trFlowType" ng-show="approval.type=='2'">
					<th >流程分类:</th>
					<td>
						  <select id="groupTypeId" name="groupTypeId" class="easyui-combotree"
						data-options="url:'${ctx}/system/sysType/getByGroupKey?groupKey=FLOW_TYPE',method:'get',required:true,cascadeCheck:false,width:180"></select>
					</td>
				</tr>
				<tr id="trFlow" ng-if="approval.type=='3'">
		    		<th >流程定义:</th>
		    		<td >
			    		<span ng-repeat="flow in flowArray" >
			    			<button class="btn btn-xs btn-default  fa-delete" ng-click="removeDef($index)">{{flow.name}}</button>
			    		</span>
						<a class="btn btn-xs btn-primary" href="javascript:void(0);" ng-click="referDefinition()">选择流程</a>
		    		</td>
		    	</tr>
				<tr>
					<th>
						<abbr title="一行就是一条常用语" ht-tip>常用语:</abbr>
					</th>
					<td>
						<textarea rows="5" cols="80" id="expression" name="expression"  ng-model="approval.expression"
								></textarea>
					</td>
				</tr>
			</table>
	
	</body>
</html>