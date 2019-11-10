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
		
		//处理结果下拉框
		$scope.$on("afterLoadEvent",function(event,data){
	 		HtUtil.loadComboBox({
	 			url:__ctx+"/dpm/ins/getUnloadDealResult",
			dictArr:[{
				el : '#dealResult',
				defaultVal : data.dealResult,
				addBlank : true
			}]
		 }); 
		}); 
		
		//不良品项目下拉框
		$scope.$on("afterLoadEvent",function(event,data){
	 		HtUtil.loadComboBox({
	 			url:__ctx+"/dpm/ins/getUnloadDpmCode",
			dictArr:[{
				el : '#dpmCode',
				defaultVal : data.dpmCode,
				addBlank : true
			}]
		 }); 
		}); 
		
		//发现区域下拉框
		$scope.$on("afterLoadEvent",function(event,data){
	 		HtUtil.loadComboBox({
	 			url:__ctx+"/dpm/area/getUnloadCode",
				dictArr:[{
					el : '#discoArea',
					defaultVal : data.discoArea,
					addBlank : true
				}]
			  }); 
		    }); 

	}]);
	
	
	
</script>
<script type="text/javascript">
	var applyNo = "${param.applyNo}";
	
	$(function() {
		$.ajax({
		    type: "post",
		    url: __ctx+"/dpm/ins/getDefaultMsg",
		    dataType: "json",
		    success: function(result){
		    	var applyDep = result.applyDep;
		    	
		    	var insDate = result.insDate;
		    	$('#applyDep').val(applyDep);
		    	$('#insDate').val(insDate);
		    	
		    }
		});
	})
		
	function save(){
		$.ajax({
		    type: "post",
		    url: __ctx+"/dpm/ins/save?dpmType=2",
		    dataType: "json",
		    data: {
		    	'insDate': insDate,
		    },
		    success: function(result){
		    	applyDep = result.applyDep;
		    }
		});
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
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save"  ht-save="${ctx}/dpm/ins/save?dpmType=1"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/dpm/ins/curdgetJson?applyNo=${param.applyNo}" ng-model="data">
				<table class="table-form" cellspacing="0">
				<tr>
						<th><span>申请科室:</span></th>
						<td><input id="applyDep" class="inputText" type="text" ng-model="data.applyDep" readonly="readonly" ht-validate="{required:false,maxlength:33}" /></td>
					    <th><span>填单日期:</span></th>
						<td><input id="insDate" class="inputText" type="text" ng-model="data.insDate" readonly="readonly"  ht-validate="{required:false,maxlength:30}" /></td>
					</tr>
				   <tr>
						<th><span>车型:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.modelCode" ht-validate="{required:true,maxlength:10}" /></td>
						<th><span>处理结果:</span><span class="required">*</span></th>
						<td><select id="dealResult" class="inputText" type="text" ng-model="data.dealResult"  ht-validate="{required:true,maxlength:20}" ></select></td>
						
					</tr>
					<tr>
						<th><span>零件编号:</span><span class="required">*</span></th>
						<td><input id="partNo" class="inputText" type="text" ng-model="data.partNo" ht-validate="{required:true,maxlength:20}" onblur="getMsgByPartNo()"/></td>
						<th><span>零件名称:</span></th>
						<td><input id="partNameCn" class="inputText" type="text" ng-model="data.partNameCn" disabled="true"  ht-validate="{required:false,maxlength:30}" />
						</td>
					</tr>
					<tr>
						<th><span>不良品数量:</span><span class="required">*</span></th>
						<td><input id="dpmNum" class="inputText" type="text" ng-model="data.dpmNum"  ht-validate="{required:true,maxlength:10}" 
						onkeyup="value=value.replace(/[^\d]/g, '').replace(/^0{1,}/g,'')"/></td>
					   <th><span>发现区域:</span></th>
						<td><select id="discoArea" class="inputText" type="text"  ng-model="data.discoArea"  ht-validate="{required:true,maxlength:20}"></select>
						</td>
					</tr>
					<tr>
					    
						<th><span>不良品项目:</span><span class="required">*</span></th>
						<td><select id="dpmCode" class="inputText" type="text" ng-model="data.dpmCode"  ht-validate="{required:true,maxlength:300}" ></select></td>
					    <th><span>不良描述:</span></th>
						<td><input class="inputText" type="text" ng-model="data.dpmDesc" ht-validate="{required:false,maxlength:300}" /></td>
					</tr>
					<tr>
					    
						<th><span>供应商代码:</span></th>
						<td><input id="supplierNo" class="inputText" type="text" ng-model="data.supplierNo" disabled="true" ht-validate="{required:false,maxlength:20}" /></td>
					    <th><span>供应商担当:</span></th>
						<td><input id="supplierContact" class="inputText" type="text" ng-model="data.contact" disabled="true" ht-validate="{required:false,maxlength:300}" /></td>
					   
					</tr>
					<tr>
						<th><span>供应商联系方式:</span></th>
						<td><input id="supplierTelNo" class="inputText" type="text" ng-model="data.telNo" disabled="true" ht-validate="{required:false,maxlength:300}" /></td>
					    <th><span>备注:</span></th>
						<td><input class="inputText" type="text" ng-model="data.remark" ht-validate="{required:false,maxlength:300}" /></td>
					</tr>
					
				</table>
			</form>

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