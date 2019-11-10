<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="calendarApp">
<head>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<title>工作日历分配</title>
	<script type="text/javascript" src="${ctx}/js/common/base/jquery.easyui.topCall.js"></script>
</head>
<body ng-controller="assignCtrl">
	<div id="tb" class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa-save btn-sm" ng-click="save();">保存</a>
			<button onclick="HT.window.closeEdit(true,'assignList');" class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	
	<div ng-form name="form">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>
					<span class="required">*</span>
					工作日历:
				</th>
				<td>
					<select name="calendarId" ng-options="calendar.id as calendar.name for calendar in calendarList"
							 ht-validate="{required:true}" ng-model="prop.calendarId"></select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="required">*</span>
					被分配人类型:
				</th>
				<td>
					<select name="calendarName" ng-model="prop.assignType" ht-validate="{required:true}">
						<option value="1">用户</option>
						<option value="2">组织</option>
					</select>
				</td>
			</tr>
			<tr ng-if="prop.assignType=='1'">
				<th>
					<span class="required">*</span>
					被分配用户:
				</th>
				<td>
					<textarea id="receiver" name="receiveruser" rows="2" cols="50" style="width: 270px; height:27px;margin:5px" readonly validate="{required:true}"></textarea>
					<input type="hidden" id="receiverId" name="receiverIds" value="">
					<a ng-click="selectUsers('userSelector')" style="margin-top:-30px;" class="btn btn-default fa fa-user">选择人员</a>
				</td>
			</tr>
			<tr ng-if="prop.assignType=='2'">
				<th>
					<span class="required">*</span>
					被分配组织:
				</th>
				<td>
					<textarea id="receiver" name="receiverorg" rows="2" cols="50" style="width: 270px; height:27px;margin:5px" readonly validate="{required:true}"></textarea>
					<input type="hidden" id="receiverId" name="receiverIds" value="">
					<a ng-click="selectUsers('orgSelector')" style="margin-top:-30px;" class="btn btn-default fa fa-user">选择组织</a>
				</td>
			</tr>
		</table>
		<button type="button" ng-hide="true" class="btn btn-primary btn-sm fa-send" ng-click="test()">测试</button>
	</div>

	<script type="text/javascript">
		var assignId = '${param.id}';
		var selectNum = -1;//用户选择器是否多选；新增时多选，修改时单选；
		angular.module('calendarApp', ['formDirective'])
		.controller("assignCtrl",['$scope','$http',function($scope, $http){
			$scope.prop = {assignType:"1"};
			$scope.calendarList = [];
			
			$scope.test = function(){
				var url = __ctx + "/calendar/assign/getEndTimeByUser";
				$http.post(url,{}).success(function(){
					
				});
			}
			
			var url = __ctx + "/calendar/assign/detail?id=" + assignId;
			$http.get(url).then(function(response){
				response = response.data;
				if(response && response.calendarList){
					if(response.calendarList.length==0){
						$.topCall.warning("请先设置工作日历，再进行工作日历分配");
					}
					else{
						$scope.calendarList = response.calendarList;
					}
				}
				if(response && response.calendarAssign){
					selectNum = 1;
					$scope.prop = response.calendarAssign;
					$scope.prop.calendarId = $scope.prop.canlendarId;
                    var assigns = [];
                    var assign = new Object();
                    assign.id = $scope.prop.assignId;
                    assign.name = $scope.prop.assignUserName;
                    assigns.push(assign);
                    $scope.prop.assign = assigns;
                    setTimeout(function(){
                    	$("#receiverId").val($scope.prop.assignId);
                        $("#receiver").val($scope.prop.assignUserName);
                    },200);
				}
			},function(response){
				$.topCall.error("未能获取到工作日历信息");
			});
			
			$scope.selectUsers = function(type){
				var initData=[];
				var ids=$("#receiverId").val();
				var names=$("#receiver").val();
				if(names!=""){
					var arrName=names.split(',');
					var arrId=ids.split(',');
					$.each(arrName,function(index,item){
						initData.push({name:item,id:arrId[index]})
					})
				}
				
				var callBack =function(data,dialog){
					if(data==null||data.length<=0){
						dialog.dialog('close');
						return false;
					}
					var arrId=[];
					var arrText=[];
					$.each(data,function(index,item){
						arrId.push(item.id);
						arrText.push(item.name);
					})
					$("#receiverId").val(arrId.toString());
					$("#receiver").val(arrText.toString());
					$scope.prop.assign = data;
					!$scope.$$phase&&$scope.$digest();
					dialog.dialog('close');
			    };
				CustomDialog.openCustomDialog(type,callBack,{
					initData:initData,
					selectNum:selectNum
				});
			}
			
			$scope.save = function(){
				if(!$scope.form.$valid){
					$.topCall.error("请正确的填写表单");
					return;
				}
				var url = __ctx + "/calendar/assign/save";
				$http.post(url,{assign:$scope.prop})
					 .then(function(response){
						 var data = response.data;
						 if(data && data.result){
							 var msg = "";
							 if(data.duplicateNames && data.duplicateNames.length > 0){
								 msg = "以下用户已经分配了日历，不能再次分配：";
								 msg += data.duplicateNames.join(",");
							 }
							 else{
								 msg = "保存成功";
							 }
							 $.topCall.success(msg,function(){
								 HT.window.closeEdit(true,'assignList');
								 HT.window.refreshParentGrid();
							 });
						 }
						 else{
							 $.topCall.error(data.message); 
						 }
					 },function(response){
						 $.topCall.error("日历分配失败");
					 });
			}
		}]);
	</script>
</body>
</html>