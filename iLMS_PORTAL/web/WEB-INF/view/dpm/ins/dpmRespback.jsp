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

		//责任组下拉框
		$scope.$on("afterLoadEvent",function(event,data){
	 		HtUtil.loadComboBox({
	 			url:__ctx+"/dpm/ins/getUnloadRespDep",
			dictArr:[{
				el : '#respDep',
				defaultVal : data.respDep,
				addBlank : true
			}]
		 }); 
		}); 
		
	}]);
	
	
	
</script>
<script type="text/javascript">
	var applyNo = "${param.applyNo}";

	$(function() {
		
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
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/dpm/ins/save"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/dpm/ins/curdgetJson?applyNo=${param.applyNo}" ng-model="data">
				<table class="table-form" cellspacing="0">
				    <tr>
						<th><span>单号:</span></th>
						<td><input  class="inputText" type="text" ng-model="data.applyNo" readonly="readonly" /></td>
					    <th><span>零件编号:</span></th>
						<td><input class="inputText" type="text" ng-model="data.partNo" readonly="readonly" /></td>
					</tr>
				    <tr>
						<th><span>零件名称:</span></th>
						<td><input class="inputText" type="text" ng-model="data.partNameCn" readonly="readonly" /></td>
						<th><span>零件简号:</span></th>
						<td><input class="inputText" type="text" ng-model="data.partShortNo" readonly="readonly"  /></td>
						
					</tr>
					<tr>
						<th><span>申请科室:</span></th>
						<td><input id="partNo" class="inputText" type="text" ng-model="data.applyDep" readonly="readonly"/></td>
						<th><span>申请人:</span></th>
						<td><input class="inputText" type="text" ng-model="data.creator" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th><span>发现区域:</span></th>
						<td><input  class="inputText" type="text" ng-model="data.discoArea" readonly="readonly"   /></td>
					   <th><span>不良原因:</span></th>
						<td><input  class="inputText" type="text"  ng-model="data.dpmCode" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th><span>责任组:</span><span class="required">*</span></th>
						<td><select id="respDep" class="inputText" type="text" ng-model="data.respDep"  ht-validate="{required:true,maxlength:300}" ></select></td>
					    <th><span>备注:</span></th>
						<td><input class="inputText" type="text" ng-model="data.dpmDesc" ht-validate="{required:false,maxlength:300}" /></td>
					</tr>
				</table>
			</form>
           <div id="respDiv" ><input class="easyui-textbox" ></div>
		</div>
	</div>
</body>
</html>

<script>

function getMsgByPartNo(){
	
	
	var partNo=$('#partNo').val();
	if($.isEmpty(partNo)){
		return;
	}
	$.ajax({
	    type: "post",
	    url: __ctx+"/dpm/ins/getMsgByPartNo",
	    data: {
	    	'partNo' : partNo
	    },
	    dataType: "json",
	    success: function(result){
	    	$('#partNameCn').val(result.partNameCn);
	    	$('#supplierNo').val(result.supplierNo);
	    	$('#supplierTelNo').val(result.telNo);
	    	$('#supplierContact').val(result.contact);
	    }
	});
}

</script>