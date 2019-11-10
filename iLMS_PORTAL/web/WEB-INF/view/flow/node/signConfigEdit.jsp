<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息节点</title>

<%@include file="/commons/include/ngEdit.jsp"%>

<script type="text/javascript">
	var app = angular.module('signApp', [ 'base' ]);

	app.controller("signController", ['$scope','baseService',function($scope,baseService) {
		
		baseService.postForm(__ctx+'/flow/node/getSignConfig?defId='+__param.defId+'&nodeId='+__param.nodeId,{}).then(function(data){
			$scope.privilegeList = data.privilegeList;
			$scope.signRule = data.signRule;
			
			if (!$scope.privilegeList.all) $scope.privilegeList.all = [];
			if (!$scope.privilegeList.oneticket) $scope.privilegeList.oneticket = [];
			if (!$scope.privilegeList.direct) $scope.privilegeList.direct = [];
			if (!$scope.privilegeList.allowAddSign) $scope.privilegeList.allowAddSign = [];
		})
		
		// 保存数据
		$scope.save = function() {
			param = {
				defId :__param.defId,
				nodeId : __param.nodeId,
				privilegeList : JSON.stringify($scope.privilegeList),
				signRule : JSON.stringify($scope.signRule)
			};
			var url = __ctx + "/flow/node/signConfigSave";
			$.post(url, param, function(data) {
				var resultMessage = new com.hotent.form.ResultMessage(data);
				if (resultMessage.isSuccess()) {
					$.topCall.success("会签节点配置成功!");
					window.passConf();//重新加载只读页面
					window.selfDialog.dialog('close');
				} else {
					$.topCall.error(resultMessage.getMessage(), resultMessage.getCause());
				}
			});
		}

		//用户规则选择
		$scope.addUserCondition = function(setting, index) {
			var ruleList = eval('$scope.privilegeList.' + setting);
			var userRule = null;
			if (index != undefined) {
				userRule = ruleList[index];
			}
			var dialog;
			var def = { passConf : userRule, title : '节点人员条件配置', width : 798, height : 580,
				modal : true, resizable : true, iconCls : 'fa-table', buttonsAlign : 'center',
				buttons : [ { 
					text : '确定', iconCls : 'fa fa-check-circle',
					handler : function(e) {
						var win = dialog.innerWin;
						var data = win.getUserRuleData();
						if (!data)
							return;
						$scope.$apply(function() {
							if (index != undefined) {
								ruleList[index] = data;
							} else if (ruleList && ruleList.length > 0) {
								ruleList.push(data);
							} else {
								var userRules = [];
								userRules.push(data);
								eval('$scope.privilegeList.' + setting + '=userRules;');
							}
						});
						dialog.window('close');
					}
				}, {
					text : '取消', iconCls : 'fa fa-times-circle',
					handler : function() {
						dialog.dialog('close');
					}
				} ]
			};
			dialog = $.topCall.dialog({
				src : '${ctx}/flow/node/condition/conditionEdit?defId='+__param.defId+'&nodeId='+__param.nodeId+'&nodeType=signTask',
				base : def
			});
		}

		///删除行
		$scope.deleteAttr = function(setting, index) {
			var aa = eval('$scope.privilegeList.' + setting);
			removeObjFromArr(aa, index);
		}

	}]);
</script>
</head>
<body ng-app="signApp">
	<div ng-controller="signController" id="signController">
		<div class="toolbar-panel col-md-13">
			<button href="javascript:;" class="btn btn-primary btn-sm fa-save" ng-click="save()">保存</button>
			<button href="javascript:;" class="btn btn-primary btn-sm fa-close" onclick="javascript:window.selfDialog.dialog('close')">取消</button>
		</div>
		<div id="signTab" data-options="tabPosition:'top'" class="easyui-tabs">
			<div title="投票规则设置" style="width: auto; height: auto;">
				<table class="table-form">
					<tr>
						<th width="20%">
							<span>决策类型</span>
						</th>
						<td>
							<input type="radio" ng-model="signRule.decideType" value="agree" name="decideType" id="agree_">
							<label class="normal_label" for="agree_">同意票</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" ng-model="signRule.decideType" value="refuse" name="decideType" id="refuse_">
							<label class="normal_label" for="refuse_">否定票</label>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>后续处理模式</span>
						</th>
						<td>
							<input type="radio" ng-model="signRule.followMode" value="wait" name="followMode" id="wait_">
							<label for="wait_" class="normal_label">等待所有人投票</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" ng-model="signRule.followMode" value="complete" name="followMode" id="complete_">
							<label for="complete_" class="normal_label">直接处理</label>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>投票类型</span>
						</th>
						<td>
							<input type="radio" ng-model="signRule.voteType" value="amount" name="voteType" id="amount_">
							<label class="normal_label" for="amount_">绝对票数</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" ng-model="signRule.voteType" value="percent" name="voteType" id="percent_">
							<label class="normal_label" for="percent_">百分比</label>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>票数:</span>
						</th>
						<td>
							<input type="text" ng-model="signRule.voteAmount" class="inputText">
							<span ng-if="signRule.voteType=='percent'">%</span>
						</td>
					</tr>
				</table>
			</div>
			<div title="特权功能设置" style="width: auto; height: auto;">
				<table class="table-form" style="width: 100%">
					<tr style="text-align: center;">
						<td>
							<label>权限类型</label>
						</td>
						<td>
							<label>人员配置</label>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>所有特权:</span>
						</th>
						<td>
							<div style="min-height: 120px">
								<table style="text-align: left;" class="table-list">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th>条件</th>
											<th width="100px">操作</th>
										</tr>
									</thead>
									<tr ng-repeat="userRule in privilegeList.all track by $index">
										<td>{{$index+1}}</td>
										<td>{{userRule.description}}</td>
										<td>
											<a ng-click="addUserCondition('all',$index)" class="btn btn-default fa-edit"></a>
											<a ng-click="deleteAttr('all',$index)" class="btn btn-default fa-delete"></a>
										</td>
									</tr>
									<tr>
										<td style="text-align: left;" colspan="4">
											<a class="btn btn-primary fa-add btn-sm" href="javascript:;" ng-click="addUserCondition('all')">新增人员规则</a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>直接处理:</span>
						</th>
						<td>
							<div style="min-height: 120px">
								<table style="text-align: left;" class="table-list">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th>条件</th>
											<th width="100px">操作</th>
										</tr>
									</thead>
									<tr ng-repeat="userRule in privilegeList.direct track by $index">
										<td>{{$index+1}}</td>
										<td>{{userRule.description}}</td>
										<td>
											<a ng-click="addUserCondition('direct',$index)" class="btn btn-default fa-edit"></a>
											<a ng-click="deleteAttr('direct',$index)" class="btn btn-default fa-delete"></a>
										</td>
									</tr>
									<tr>
										<td style="text-align: left;" colspan="4">
											<a class="btn btn-primary fa-add btn-sm" href="javascript:;" ng-click="addUserCondition('direct')">新增人员规则</a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>一票制:</span>
						</th>
						<td>
							<div style="min-height: 120px">
								<table  class="table-list">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th>条件</th>
											<th width="100px">操作</th>
										</tr>
									</thead>
									<tr ng-repeat="userRule in privilegeList.oneticket track by $index">
										<td>{{$index+1}}</td>
										<td>{{userRule.description}}</td>
										<td>
											<a ng-click="addUserCondition('oneticket',$index)" class="btn btn-default fa-edit"></a>
											<a ng-click="deleteAttr('oneticket',$index)" class="btn btn-default fa-delete"></a>
										</td>
									</tr>
									<tr>
										<td style="text-align: left;" colspan="4">
											<a class="btn btn-primary fa-add btn-sm" href="javascript:;" ng-click="addUserCondition('oneticket')">新增人员规则</a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<th width="20%">
							<span>允许加签:</span>
						</th>
						<td>
							<div style="min-height: 120px">
								<table  class="table-list">
									<tr>
										<th width="60px">序号</th>
										<th>条件</th>
										<th width="100px">操作</th>
									</tr>
									<tr ng-repeat="userRule in privilegeList.allowAddSign track by $index">
										<td>{{$index+1}}</td>
										<td>{{userRule.description}}</td>
										<td>
											<a ng-click="addUserCondition('allowAddSign',$index)" class="btn btn-default fa-edit"></a>
											<a ng-click="deleteAttr('allowAddSign',$index)" class="btn btn-default fa-delete"></a>
										</td>
									</tr>
									<tr>
										<td style="text-align: left;" colspan="4">
											<a class="btn btn-primary fa-add btn-sm" href="javascript:;" ng-click="addUserCondition('allowAddSign')">新增人员规则</a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
