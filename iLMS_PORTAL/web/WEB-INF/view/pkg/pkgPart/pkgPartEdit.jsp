<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
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
<script type="text/javascript">
	var id = "${param.id}";
	$(function() {
		if(!$.isEmpty(id)){
			$('#boxType').attr('disabled',true);
			$('#boxCode').attr('disabled',true);
			
		}
	})
		

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
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/pkg/pkgPart/save"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/pkg/pkgPart/curdgetJson?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
				    <tr>
						<th><span>车型:</span></th>
						<td><input id="carType" class="inputText" type="text"  ng-model="data.carType" readyonly="readyonly" ht-validate="{maxlength:10}" />
						    
						</td>
						<th><span>零件编号:</span></th>
						<td><input id="partNo" class="inputText" type="text" ng-model="data.partNo" readyonly="readyonly" ht-validate="{maxlength:30}" /></td>
					</tr>
					<tr>
						<th><span>工程:</span><span class="required">*</span></th>
						<td><input id="project" class="inputText" type="text" ng-model="data.project" ht-validate="{required:true,maxlength:33}" /></td>
					    <th><span>零件名称:</span>></th>
						<td><input id="partNameCn" class="inputText" type="text" ng-model="data.partNameCn" readyonly="readyonly"  ht-validate="{maxlength:33}" /></td>
					</tr>
					<tr>
						<th><span>零件担当:</span><span class="required">*</th>
						<td><input id="partRespUser" class="inputText" type="text" ng-model="data.partRespUser" ht-validate="{required:true,maxlength:100}" onblur="getMsgByPartNoFun()"/></td>
						<th><span>联系电话:</span></th>
						<td><input id="telNo" class="inputText" type="text" ng-model="data.telNo" ht-validate="{maxlength:100}" /></td>
					</tr>
				</table>
			</form>

		</div>
	</div>
</body>
</html>

<script>
function getMsgByPartNoFun(){

	var partRespUser=$('#partRespUser').val();
	if($.isEmpty(partRespUser)){
		return;
	}
	$.ajax({
	    type: "post",
	    url: __ctx+"/pkg/pkgPart/getTelNoByUser",
	    data: {
	    	'partRespUser' : partRespUser
	    },
	    dataType: "json",
	    success: function(result){
	    	$('#telNo').val(result.telNo);
	    }
	});
}

</script>