<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="systemApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/commons/include/ngEdit.jsp"%>
	<script type="text/javascript">
		var parentScope;
		
		$(function(){
			parentScope = window.parentWindow.getScope();
			var conf = window.passConf;
			if(conf){
				var scope = getMyScope();
				scope.$apply(function(){
					scope.myParam = conf;
				});
			}
		});
		
		function clickOk() {
			if(!getMyScope().myForm.$valid)return false;
			parentScope.$apply(function(){
				var item = getMyScope().myParam;
				//合并修改过的参数
				$(parentScope.params).each(function(index,param){
					if((param.id&&param.id==item.id)||param.name==item.name){		
						parentScope.params.splice(index,1)
						return false;
					}
				});
				parentScope.params.push(item);
			});
			return true;
		};
		
		function getMyScope(){
			return angular.element($("#serviceParamDiv")).scope();
		}
		
		function getSysServiceSetIframe(){
			var iframes=$(parent.document).find("iframe");
			for(var i=iframes.length;i--;){
				if($(iframes[i]).contents()[0].URL.indexOf("system/serviceSet/invokeServiceEdit")!=-1){
					return $(iframes[i]);
				}
			}
			return null;
		}
		
		var systemApp = angular.module('systemApp', [ 'base','formDirective']);
		
		systemApp.controller("serviceParamCtrl",['$scope',function($scope){
			$scope.myParam = {};
			
			$scope.isValid = function(obj){
				return $scope.myForm.$valid;
			}
		}]);
	</script>
</head>
<body class="easyui-layout">
	<div class="scoll-panel" id="serviceParamDiv" ng-controller="serviceParamCtrl" >
	<form name="myForm">
		<table class="table-form" style="width: 100%;font-size:13px;">
			<tr>
				<th>参数名称</th>
				<td >
					<input type="text" class="inputText input-width-50" ht-validate="{required:true,maxlength:10}" placeholder="参数名称" ng-model="myParam.name"/>
				</td>
			</tr>
			<tr>
				<th>参数说明</th>
				<td>
					<textarea ng-model="myParam.desc" class="inputText input-width-80" ht-validate="{required:true,maxlength:30}" placeholder="参数说明" rows="3"></textarea>
				</td>
			</tr>
			<tr>
				<th>参数类型</th>
				<td>
					<select ng-model="myParam.type" class="form-control input-width-80" ht-validate="{required:true}">
						<option value="string">字符串</option>
						<option value="number">数字</option>
						<option value="date">日期</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>