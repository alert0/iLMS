<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  ng-app="previewApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript">
		var app = angular.module('previewApp',['base']);
		app.controller("PreviewUserController",function($scope,$http,$jsonToFormData){
			var conditoins = window.passConf;
			if(conditoins){
				var userCondition = conditoins.calcs;
				$scope.variables = {};
				$scope.groupVar=[],$scope.userVar=[];
				$.each(conditoins.calcs,function(i,condition){
					if(condition.source == 'prev'){
						$scope.variables.prevUser='1';
						$scope.variables.prevUserName='';
					}else if(condition.source == 'start'){
						$scope.variables.startUser='1';
						$scope.variables.startUserName='';
					}else if(condition.source =='var'){
						if(condition.var.executorType = 'user'){
							if(!varIsExit($scope.userVar,condition.var.name)){
								$scope.userVar.push({key:condition.var.name,userVarName:'',userVar:'1'});
							}
						}else{
							if(!varIsExit($scope.groupVar,condition.var.name)){
								$scope.groupVar.push({key:condition.var.name,userVarName:'',userVar:'1'});
							}
						}
					}
				})
			}
			
			$scope.previewCondition = function(){
				var conditionArray=JSON.stringify(conditoins);
				var variables = $scope.variables;
				$.each($scope.userVar,function(i,Var){
					variables[Var.key]=Var.userVar;
				});
				$.each($scope.groupVar,function(i,Var){
					variables[Var.key]=Var.groupVar;
				});
				variables =JSON.stringify(variables); 
				var frm = $('#paramForm').form();
				if (frm.valid()){
				 $("#userGridList").datagrid({
						queryParams:{
							conditionArray:"["+conditionArray+"]",
							variables:variables
						}
					});
				 $('#preVerwTab').tabs('select',1); 
				}
			}
			$scope.selectUsers = function(str){
				new UserSelectDialog({type:'1',single:true,callback:function(userIds,fullnames,dialog){	
					$scope.$apply(function(){
						eval("$scope."+str+"=userIds[0]+''");
						eval("$scope."+str+"Name=fullnames");
					});
				}}).show();
			}
			$scope.selectGroups = function(str){
				new GroupSelectDialog({type:'1',single:true,callback:function(groupIds,names,records,dialog){
					$scope.$apply(function(){
						eval("$scope."+str+"=groupIds[0]+''"); 
						eval("$scope."+str+"Name=names"); 
					});
				}}).show();
			}
		});
		 function varIsExit(array,k){
			$.each(array,function(i,obj){
				if(obj.key == k) return true;
			});
			return false;
		}
		</script>
		<style type="text/css">
		td, th {
		 padding: 2px 3px !important;
		border: 1px solid #DDD; 
		}
		</style>
	</head>
	<body   ng-controller="PreviewUserController">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa-save" ng-click="previewCondition()"><span>预览</span></a>
			<a href="" onclick="javascript:window.selfDialog.dialog('close')" class="btn btn-primary fa-close" ><span>关闭</span></a>
		</div> 
		<div id="preVerwTab" class="easyui-tabs" fit="true">
	    <div title="预览参数" style="padding:20px;">
	    	<form id="paramForm">
	        <table class="table-form" cellspacing="0" >
	        <tr ng-hide="variables.prevUser | isEmpty">
				<th><span>上一步执行人:</span><span class="required">*</span></th>
				<td>
					<input type="text" class="inputText" ng-model="variables.prevUserName" validate="{required:true}" readonly="readonly"/>
					<a class="btn btn-primary btn-xs" ng-click="selectUsers('variables.prevUser')">请选择</a>
				</td>
			</tr>
			<tr ng-hide="variables.startUser | isEmpty">
				<th><span>发起人:</span><span class="required">*</span></th>
				<td>
					<input type="text" class="inputText" ng-model="variables.startUserName" validate="{required:true}" readonly="readonly"/>
					<a class="btn btn-primary btn-xs" ng-click="selectUsers('variables.startUser')">请选择</a>
				</td>
			</tr>
			<tr ng-repeat="var in groupVar">
				<th><span>组变量({{var.key}})</span><span class="required">*</span></th>
				<td>
					<input type="text" class="inputText" ng-model="var.groupVarName" validate="{required:true}" readonly="readonly"/>
					<a class="btn btn-primary btn-xs" ng-click="selectGroups('groupVar['+$index+'].groupVar')">请选择</a>
				</td> 
			</tr>  
			<tr ng-repeat="var in userVar">
				<th><span>人员变量({{var.key}})</span><span class="required">*</span></th>
				<td>
					<input type="text" class="inputText" ng-model="var.userVarName" validate="{required:true}" readonly="readonly"/>
					<a class="btn btn-primary btn-xs" ng-click="selectUsers('userVar['+$index+'].userVar')">请选择</a>
				</td>
			</tr>	
	        </table>
	       </form> 	
	    </div>
	   
		<div title="查询结果">
			 <div style="height: 100%;width: 100%">
			<table id="userGridList" data-options="fitColumns:false,checkOnSelect:true,pagination:true,autoRowHeight:false" 
				 url="${ctx}/flow/node/previewCondition" >
			    <thead>
				    <tr>
						<th field="fullname" title="姓名" width="130px"></th> 
						<th field="account" title="账号" width="130px"></th>
				    </tr>
			    </thead>
		    </table>
		    </div>
		</div>
		</div>
	</body>
</html>