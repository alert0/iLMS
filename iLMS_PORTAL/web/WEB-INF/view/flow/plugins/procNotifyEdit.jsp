<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息节点</title>
<%@include file="/commons/include/ngEdit.jsp" %>
<script type="text/javascript">
	var app = angular.module('procNotifyApp',['base','formDirective']);
	app.controller("procNotifyController",function($scope){
	var procNotifyJson = '${procNotifyJson}'; 
		if(!procNotifyJson) 
			procNotifyJson = '[{"userAssignRules":[],"msgTypes":""}]'; 
		$scope.procNotifyList = eval('(' + procNotifyJson + ')');
		
		// 保存数据
		$scope.save= function(){
			param = {
						defId:'${defId}',procNotifyJson:JSON.stringify($scope.procNotifyList)
					};
			var url = __ctx + "/flow/plugins/procNotifySave";
			$.post(url,param,function(data){
				var resultMessage=new com.hotent.form.ResultMessage(data);
	        	if(resultMessage.isSuccess()){
	        		$.topCall.success("抄送配置成功!");
	        	}else{
	        		$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
	        	}
			});
		}
		
		$scope.addLine=function(){
			var procNotify = {"userAssignRules":[],"msgTypes":""};
			$scope.procNotifyList.push(procNotify);
		}
		$scope.deleteLine=function(index){
			var list=$scope.procNotifyList;
			if(list.length <=1){
				$.topCall.toast("提示信息","请至少保留一个");
				return ;
			}
			removeObjFromArr(list,index);
		}
		
		//用户规则选择
		$scope.addUserCondition=function(index,userRulesIndex){
			if(userRulesIndex == undefined){
				var userRule = null;
			}else{
				var userRule = $scope.procNotifyList[index].userAssignRules[userRulesIndex];
			}
			
			var dialog;
			var def = {
			        passConf:userRule,title:'人员条件配置',width:853,height:580, modal:true,resizable:true,iconCls: 'icon-collapse fa fa-table',
			        buttonsAlign:'center',
			        buttons:[{
						text:'确定',
						iconCls:'fa-check-circle',
						handler:function(e){
								var win=dialog.innerWin;   				
								var data = win.getUserRuleData();
								if(!data) return;
								$scope.$apply(function(){
									var userRuleList = $scope.procNotifyList[index].userAssignRules; 
									if(userRulesIndex != undefined){
										procNotify.userAssignRules[userRulesIndex] = data; 
									}else if(userRuleList && userRuleList.length > 0){
										userRuleList.push(data); 
									}else {
										var userRuleList = [];
										userRuleList.push(data);
										$scope.procNotifyList[index].userAssignRules = userRuleList;
									}  
								});
								dialog.window('close');
							}
					},{
						text:'取消',iconCls:'fa-times-circle',
						handler:function(){dialog.dialog('close');}
					}]
			};
			dialog = $.topCall.dialog({
				src:'${ctx}/flow/node/condition/conditionEdit?defId=${defId}',
				base:def
			});
			}
		
		///删除行
		$scope.deleteAttr=function(index,userRuleIndex){
			 var aa = $scope.procNotifyList[index].userAssignRules;
			 removeObjFromArr(aa,index);
		}
	});
</script>
</head>
<body ng-app="procNotifyApp" >
<div ng-controller="procNotifyController" id="procNotifyController">
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
		 <a href="javascript:;" class="btn btn-primary btn-sm fa-save" ng-click="save()">保存</a>
		 <a href="javascript:;" class="btn btn-primary btn-sm fa-add"  ng-click="addLine()">新增</a>
		 <a href="javascript:;" class="btn btn-primary  btn-sm fa-close" onclick="javascript:window.selfDialog.dialog('close')">取消</a>
		</div>
	</div>
    <table class="table-list">
    	<thead>
    		<tr>
	    		<th width="20%">通知类型</th>
	    		<th>人员配置</th>
    		</tr>
    	</thead>
		<tr ng-repeat="procNotify in procNotifyList"  ng-init="outIndex=$index"> 
			<td>
				<div style="width:100%;text-align:left;"><a class="btn btn-sm btn-danger fa-delete" ng-click="deleteLine(outIndex)" title="删除" ></a></div>
				<div style="width:100%;text-align:left;">
						<c:forEach items="${handlerList}"  var="model">
								<label><input type="checkbox" ht-checkbox  ng-model="procNotify.msgTypes" value="${model.type}"/>${model.title}</label></br>
						</c:forEach>
				</div> 
			</td> 
			<td>
					<table class="table-list">
						<thead>
						<tr>
							<th width="60px">序号</th>
							<th>条件</th>
							<th width="50px">批次</th>
							<th width="100px">操作</th>
						</tr>
						</thead>
						<tr ng-repeat="userRule in procNotify.userAssignRules" >
							<td>{{$index+1}}</td>
							<td>{{userRule.description}} </td>
							<td><input type="text" size="3" ng-model="userRule.groupNo"> </td> 
							<td>
								<a ng-click="addUserCondition(outIndex,$index)" class="btn btn-default fa-edit"></a>
								<a ng-click="deleteAttr(outIndex,$index)" class="btn btn-default fa-delete"></a> 
							</td>
						</tr> 
						<tr>
							<td style="text-align: left;" colspan="4"> 
								<a class="btn btn-primary fa-add btn-sm" href="javascript:;" ng-click="addUserCondition(outIndex)">新增人员规则</a>
						 	</td>
					   </tr>
					</table>
			</td>
		</tr>
    </table>  
    </div>
</body>
</html>
						