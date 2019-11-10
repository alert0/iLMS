<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="restfulApp">
<head>
<title>接口事件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp" %>
<style type="text/css">
.inputText{
	width:95%;
}
</style>
<script type="text/javascript">
	var restfulApp = angular.module("restfulApp", [ 'base', 'formDirective', 'arrayToolService' ]);
	restfulApp.controller('restfulController', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
		$scope.ArrayTool=ArrayToolService;
		$scope.data = window.passConf;
		console.info($scope.data);
		var restfulJson = $scope.data.restful;
		if(!restfulJson||restfulJson.length<1){
			restfulJson = '[{"url":"","desc":"","invokeMode":1,"callTime":"","header":"","inParam":"","outParam":""}]'; 
			restfulJson = eval('(' + restfulJson + ')');
		}else{
			var len = restfulJson.length;
			for(var i=0;i<len;i++){
				var headerJson = restfulJson[i]['header'];
				if(headerJson&& typeof(headerJson) == "object" ){
					restfulJson[i]['header'] = angular.toJson(headerJson);
				}
			}
		}
		$scope.restfulList = restfulJson;
		
		$scope.addLine=function(){
			var restful = {"url":"","desc":"","invokeMode":1,"callTime":"","header":"","inParam":"","outParam":""};
			$scope.restfulList.push(restful);
		}
		$scope.deleteLine=function(index){
			removeObjFromArr($scope.restfulList,index);
		}
		
		///删除行
		$scope.deleteAttr=function(index,userRuleIndex){
			 var aa = $scope.restfulList[index].userAssignRules;
			 removeObjFromArr(aa,index);
		}
		
		//全局事件中是否包含节点事件
		$scope.isGlobalNode=function(callTime){
			if(callTime){
				return callTime.indexOf('taskCreate')!=-1||callTime.indexOf('taskComplete')!=-1?true:false;
			}
			return false;
		}
		
		$scope.noStart = function(value){
			if(value.type!='start')return true;
			return false;
		}
		
	}]);
</script>
</head>
<body ng-controller="restfulController">
<form id="restfulForm" name="restfulForm">
	<div id="restfulController">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
			 <a href="javascript:;" class="btn btn-primary btn-sm fa-add"  ng-click="addLine()">新增</a>
			 <a href="javascript:;" class="btn btn-primary  btn-sm fa-close" onclick="javascript:window.selfDialog.dialog('close')">取消</a>
			</div>
		</div>
	    <table class="table-list">
	    	<thead>
	    		<tr>
		    		<th width="135">操作</th>
		    		<th>接口配置</th>
	    		</tr>
	    	</thead>
			<tr ng-repeat="restful in restfulList track by $index"  ng-init="outIndex=$index"> 
				<td>
					<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,restfulList)" class="btn btn-sm btn-default fa-chevron-up"></a>
					<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,restfulList)" class="btn btn-sm btn-default fa-chevron-down"></a>
					<a class="btn btn-default fa fa-delete" title="移除" ng-click="deleteLine(outIndex)"></a>
				</td> 
				<td>
					<table class="table-form" cellspacing="0">
						<tr>
							<th>
								<span>地址:</span>
								<span class="required">*</span>
							</th>
							<td>
								<input class="inputText" type="text" ng-model="restful.url" ht-validate="{required:true}" />
							</td>
						</tr>
						<tr>
							<th>
								<span>描述:</span>
								<span class="required">*</span>
							</th>
							<td>
								<input class="inputText" type="text" ng-model="restful.desc" ht-validate="{required:true}" />
							</td>
						</tr>
						<tr>
							<th>
								<span>类型:</span>
								<span class="required">*</span>
							</th>
							<td>
								<label class="radio-inline">
									<input type="radio"  value=1 ng-model="restful.invokeMode">异步
								</label>
								<label class="radio-inline">
						  			<input type="radio"  value=0 ng-model="restful.invokeMode">同步
						  		</label>
							</td>
						</tr>
						<tr>
							<th>
								<span>触发时机:</span>
							</th>
							<td>
							 	 <label ng-if="!data.nodeId" class="checkbox-inline"><input type="checkbox" ht-checkbox ng-model="restful.callTime" value="startEvent" />流程启动时</label>
							 	 <label ng-if="!data.nodeId" class="checkbox-inline"><input type="checkbox" ht-checkbox ng-model="restful.callTime" value="endEvent" />流程结束时</label>
								 <label class="checkbox-inline"><input type="checkbox" ht-checkbox ng-model="restful.callTime" value="taskCreate" />任务创建时</label>
								 <label class="checkbox-inline"><input type="checkbox" ht-checkbox ng-model="restful.callTime" value="taskComplete"/>任务结束时</label>
							</td>
						</tr>
						<tr ng-if="!data.nodeId&&isGlobalNode(restful.callTime)">
							<th>
								<span>触发节点<a href="javascript:;" style="text-decoration: none;" title="如果选择节点则只有对应节点才执行，否则默认全部节点都会执行。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>:</span>
							</th>
							<td>
								 <label ng-repeat="node in data.nodes |filter:noStart track by $index" class="checkbox-inline">
								 	<input type="checkbox" ht-checkbox ng-model="restful.callNodes" value="{{node.nodeId}}" />{{node.name}}
								 </label>
							</td>
						</tr>
						<tr>
							<th>
								<span>接口头部（header）:</span>
							</th>
							<td>
								<input class="inputText" type="text" ng-model="restful.header" />
							</td>
						</tr>
						<tr>
							<th>
								<span>入参:</span>
							</th>
							<td>
								<input class="inputText" type="text" ng-model="restful.inParam" />
							</td>
						</tr>
						<tr>
							<th>
								<span>出参:</span>
							</th>
							<td>
								<input class="inputText" type="text" ng-model="restful.outParam" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
	    </table> 
	</div>
</form> 
</body>
<script type="text/javascript">
function getRestfulData(){
	var scope = AngularUtil.getScope();
	if(scope.restfulForm.$invalid){
		$.topCall.error("验证不通过!");
		return "validateError";
	}
	var data = AngularUtil.getScope().restfulList;
	if(data&&data.length>0){
		var len = data.length;
		for(var i=0;i<len;i++){
			var headerJson = data[i]['header'];
			if(headerJson){
				data[i]['header'] = angular.fromJson(headerJson);
			}
		}
	}
	return data;
}
</script>
</html>
						