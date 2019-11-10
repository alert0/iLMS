<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript">
	     var app = angular.module('app', ['formDirective','arrayToolService']);
	     app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
		 $scope.ArrayTool = ArrayTool;
		
		 var data={
			
		 }
		
		 $scope.addRow=function(classVar){
			$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
		 }
		
	}]);
       </script>
	</head>
	
<script type="text/javascript">
	var id = "${param.id}";
	$(function() {
		if(!$.isEmpty(id)){
			$('#partNo').attr('disabled',true);
			$('#supFactory').attr('disabled',true);
			$('#supplierNo').attr('disabled',true);	
			$('#arriveTimeStr').attr('disabled',true);
		}
	});
	
	/**
	* 禁止空格输入
	* @param e
	* @returns {Boolean}
	*/
	function banInputSapce(e)
	{
		var keynum;
		if(window.event) // IE
		{
			keynum = e.keyCode
		}else if(e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which
		}if(keynum == 32){
			return false;
		}
		return true;
	} 
	</script>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
		<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		  <div title="新车型需求计算" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/mp/mpTrialDemand/curdgetJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>零件编号:</th>
								<td>
									<input id ="partNo" class="inputText" type="text" ng-model="data.partNo"  
									 ht-validate="{required:false,maxlength:20} "  />
								</td>								
							</tr>
							<tr>								
								<th>出货地:</th>
								<td>
									<input id ="supFactory" class="inputText" type="text" ng-model="data.supFactory" 
								   ht-validate="{required:false,maxlength:20}"  />
								</td>								
							</tr>
							<tr>								
								<th>供应商代码:</th>
								<td>
									<input id ="supplierNo" class="inputText" type="text" ng-model="data.supplierNo" 
								    ht-validate="{required:false,maxlength:20}"  />
								</td>								
							</tr>
							<tr>								
								<th>到货日期:</th>
								<td>
									<input id ="arriveTimeStr" class="inputText" type="text" ng-model="data.arriveTimeStr" 
									ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th>净需求:</th>
								<td>
									<input id ="orderNum"  class="inputText" type="text" ng-model="data.orderNum" 
								  ht-validate="{required:false,number:true,maxIntLen:10}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		</div>
		</div>
	</body>
</html>