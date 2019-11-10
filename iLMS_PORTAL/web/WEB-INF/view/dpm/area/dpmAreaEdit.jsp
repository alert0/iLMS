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
		
		//仓库下拉框
		$scope.$on("afterLoadEvent",function(event,data){
		HtUtil.loadComboBox({
			url:__ctx+"/dpm/area/getUnloadWareCode",
			dictArr:[{
				el : '#wareCode',
				defaultVal : data.wareCode,
				addBlank : true
			}]
		});
		});
		
		//车间下拉框
		$scope.$on("afterLoadEvent",function(event,data){
			HtUtil.loadComboBox({
			url:__ctx+"/dpm/area/getUnloadWorkcenter",
			dictArr:[{
				el : '#workcenter',
				defaultVal:data.workcenter, 
				addBlank : true
			}]
		  }); 
		}); 
	
		
	}]);
	
	
	
</script>
<script type="text/javascript">
	var id = "${param.id}";

	$(function() {
		if(!$.isEmpty(id)){
			$('#areaCode').attr('disabled',true);
		}
		
		

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
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/dpm/area/save"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/dpm/area/curdgetJson?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
				    <tr>
						<th><span>车间:</span><span class="required">*</span></th>
						<td><select id="workcenter" class="inputText" type="text"  ng-model="data.workcenter" ht-validate="{required:true,maxlength:10}" >
						    </select>
						</td>
					</tr>
					<tr>
						<th><span>发现区域代码:</span><span class="required">*</span></th>
						<td><input class="inputText" id="areaCode" type="text" ng-model="data.areaCode" ht-validate="{required:true,maxlength:30}" /></td>
					</tr>
					<tr>
						<th><span>发现区域名称:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.areaName" ht-validate="{required:true,maxlength:33}" /></td>
					</tr>
					<tr>
						<th><span>发现区域描述:</span></th>
						<td><input class="inputText" type="text" ng-model="data.areaDesc" ht-validate="{required:false,maxlength:100}" /></td>
					</tr>
					<tr>
						<th><span>仓库代码:</span><span class="required">*</th>
						<td><select id="wareCode" class="inputText" type="text" ng-model="data.wareCode"  ht-validate="{required:true,maxlength:30}" onchange="getWareName()">
						    </select>
						</td>
					</tr>
					<tr>
						<th><span>仓库名称:</span></th>
						<td><input id="wareName" class="inputText" type="text" ng-model="data.wareName" disabled="true" ht-validate="{required:false,maxlength:300}" /></td>
					</tr>
				</table>
			</form>

		</div>
	</div>
</body>
</html>

<script>

function getWareName(){
	var wareCode=$('#wareCode').val();
	if($.isEmpty(wareCode)){
		return;
	}
	$.ajax({
	    type: "post",
	    url: __ctx+"/dpm/area/getWareNameByCode",
	    data: {
	    	'wareCode':wareCode
	    },
	    dataType: "json",
	    success: function(result){
	    	
	    	$('#wareName').val(result.wareName);
	    }
	});
}

</script>