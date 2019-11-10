<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<f:link href="formEdit.css"></f:link>
		<f:link href="component.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/bpmx/test/bpmTestCase/bpmTestCaseEditController.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
	</head> 
	<body ng-controller="ctrl">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ng-click="save()" ><span>保存</span></a>
					<a class="btn btn-primary fa-back" ng-click="closeFrame()"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>测试用例名称:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:128}"  />
								</td>
								
								<!-- <th>发起人:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.startorFullName"  ng-readonly="true"  ht-validate="{required:true,maxlength:128}" />
									<input class="inputText" type="hidden" ng-model="data.startorAccount"   ht-validate="{required:true,maxlength:128}" />
									<a class="btn btn-sm btn-primary fa-search" ng-click="showUserDialog()">选择</a>
								</td> -->
							</tr>
							<tr>								
								<th ht-tip title="用户组里的所有用户都作为发起人" >用户组（发起人）:</th>
								<td colspan="3" >
									<a class="btn btn-sm btn-primary fa fa-cogs" ng-click="setStartor()">用户组设置</a>
								</td>	
							</tr>
							<tr>								
								<th>表单数据:</th>
								<td colspan="3" >
									<a class="btn btn-sm btn-primary fa fa-cogs" ng-click="settingBoFormData()">表单数据设置</a>
								</td>	
							</tr>
							
							<tr>
								<th ht-tip title="没有设置的节点,默认同意提交">审批动作:</th>
								<td colspan="4" >
									<table style="width:100%" >
										<tr ng-repeat="item in baseFlowInfo.defKeys track by $index">
											<th> {{item.defName}}--{{item.defKey}}</th>
											<td  colspan="4">
												<a class="btn btn-sm btn-primary fa fa-add"  href="javascript:;" ng-click="addItem('actionType',item.defKey)">添加审批动作</a>
												<table style="width:100%">
													<tr ng-repeat=" actionType in data.actionType[item.defKey]" >
														<td>
															<select class="inputText" ng-model="actionType.nodeId" 
															 ng-options="node.nodeId as node.nodeName  for node in baseFlowInfo.nodeInfo[item.defKey] "
															 ng-change="selectNodeId('actionType',item.defKey,actionType,$index)"
															 ht-validate="{required:true,maxlength:128}"
															 >
															</select>
														</td>
														<td>
															<select class="inputText" ng-model="actionType.actionName" ht-validate="{required:true,maxlength:128}">
																<option value="agree">同意</option>
																<option value="oppose">反对</option>
																<option value="reject">驳回到上一步</option>
																<option value="backToStart">驳回到发起人</option>
																<option value="endProcess">终止流程</option>
															</select>
															<span ng-show="actionType.actionName=='reject' || actionType.actionName=='backToStart' "
															style="display: block;padding-top: 5px;" >
																驳回次数设置
																<input  ng-model="actionType.count"  
																class="inputText" style="width:85px" type="number"  ht-validate="{required:true,maxlength:1,range:[1,5]}" />
															</span>
														</td>
														<th colspan="2" style="text-align: center;" >
															<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,data.actionType[item.defKey])" class="btn btn-sm btn-default fa-delete"></a>
														</th>
													</tr>
												</table>
												
											</td>
											
										</tr>
									</table>
								</td>
															
							</tr>
							
			<!-- 				<tr>								
								<th>流程变量:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.flowVars"   ht-validate="{required:false,maxlength:512}"  />
								</td>								
							</tr> -->
							<tr>				
								<th ht-tip title="流程运行到指定节点时停止继续运行" >断点测试设置:</th>
								
								<td colspan="4" >
									<table style="width:100%" >
										<tr ng-repeat="item in baseFlowInfo.defKeys track by $index">
											<th> {{item.defName}}--{{item.defKey}}</th>
											<td  colspan="4">
												<a class="btn btn-sm btn-primary fa fa-add"  href="javascript:;" ng-click="addItem('bpmDebugger',item.defKey)">添加审批动作</a>
												<table>
													<tr ng-repeat=" key in data.bpmDebugger[item.defKey] track by $index " >
														<td>
															<select class="inputText" ng-model="key" 
															 ng-options="node.nodeId as node.nodeName  for node in baseFlowInfo.nodeInfo[item.defKey] "
															 ng-change="selectNodeId('bpmDebugger',item.defKey,key,$index)"
															 ht-validate="{required:true,maxlength:128}"
															 >
															</select>
														</td>
														<th colspan="2" >
															<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,data.bpmDebugger[item.defKey])" class="btn btn-sm btn-default fa-delete"></a>
														</th>
													</tr>
												</table>
												
											</td>
											
										</tr>
									</table>
								</td>
								
															
							</tr>
				</table>
				
				
			</form>
			<br/><br/><br/>
		
	</body>
</html>