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
		  <div title="计划对比" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/mp/mpAdjOrderDiffEdit/curdgetJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
						    <tr>								
								<th>调整差异数量:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.adjDiffNum" 
								  ht-validate="{required:false,number:true,maxIntLen:8}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		</div>
		</div>
	</body>
</html>