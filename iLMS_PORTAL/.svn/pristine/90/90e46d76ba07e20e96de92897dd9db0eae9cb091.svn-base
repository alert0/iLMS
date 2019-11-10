<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
	<%@include file="/commons/include/ngEdit.jsp" %>
	<script type="text/javascript">
		var app = angular.module('app', ['formDirective','arrayToolService']);
		app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
			$scope.ArrayTool=ArrayTool;
			
		}]);
	
		function getData(){
			var scope=AngularUtil.getScope();
			return angular.toJson(scope.data);
		}
	
		function isValid(){
			var scope=AngularUtil.getScope();
			return  scope.form.$valid;
		}
	</script>
</head>
	
<script type="text/javascript">
	$(function() {			
		$.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/pup/route/getJson?id=${param.id}",
            dataType: "json",
            success: function(data){
            	$("#supplierNo").val(data.supplierNo);
            	$("#locDepth").val(data.locDepth);
            	$("#supFactory").val(data.supFactory);
            	$("#pickCycle").val(data.pickCycle);
            	$("#advanceArrNum").val(data.advanceArrNum);
            	$("#supOutTime").val(data.supOutTime);
            	$("#unloadPort").val(data.unloadPort);
            	$("#transTime").val(data.transTime);
            	$("#speArriveTime").val(data.speArriveTime);
            	$("#carType").val(data.carType);
            	$("#mergeNum").val(data.mergeNum);
            	$("#batch option[value="+data.batch+"]").attr("selected", "selected");
            	$("#routeCode").val(data.routeCode);
            	$("#departTimePoint").val(data.departTimePoint);
            	$("#startSortId").val(data.startSortId);
            }
        });
		
		HtUtil.loadComboBox({
			url:__ctx+"/pup/route/getBatch",
			dictArr:[{
				el : '#batch',
				addBlank : false
			}]
		});
		
		HtUtil.loadComboBox({
			url:__ctx+"/pup/route/schedule",
			dictArr:[{
				el : '#supOutTime,#speArriveTime,#departTimePoint',
				addBlank : true
			}]
		});
	});
	
	function saveEdit(){
		$.ajax({  
		    url : "${pageContext.request.contextPath}/pup/route/saveEdit?id=${param.id}",  
		    async : false,
		    data : $('#form').serialize(),
		    contentType:"application/x-www-form-urlencoded;charset=utf-8",
		    success : function(data) {
		    	var result = new com.hotent.form.ResultMessage(data);
		    	if(result.isSuccess()){
		    		$.messager.confirm('提示信息',result.getMessage()+",是否继续?" ,function(r){
		    			if(r){
		    				HT.window.closeEdit(true,'grid');
		    			}
	    			});
		    	}else{
		    		$.topCall.error(result.getCause());
		    	}
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
		if(window.event)
		{
			keynum = e.keyCode
		}else if(e.which) 
		{
			keynum = e.which
		}if(keynum == 32){
			return false;
		}
		return true;
	} 
</script>
<body ng-controller="ctrl">
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-primary fa-save" type="submit" onClick="saveEdit()"><span>保存</span></a>
			<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
		</div>
	</div>
	<form id="form">
		<table class="table-form"  cellspacing="0" >
			<tr>
			<th>供应商代码</th>								
				<td>
					<input id="supplierNo" name="supplierNo" class="inputText" type="text" readonly="readonly">
				</td>
			<th>最早落点汇总</th>	
				<td>
					<input id="locDepth" name="locDepth" class="inputText" type="number"> 
				</td>
			<th>取货周期</th>	
				<td>
					<input id="pickCycle" name="pickCycle" class="inputText" type="text"> 
				</td>						
			</tr>
			<tr>								
				<th>出货地</th>
				<td>
					<input id="supFactory" name="supFactory" type="text" class="inputText" readonly="readonly"> 
				</td>
				<th>提前台套数</th>
				<td>
					<input id="advanceArrNum" name="advanceArrNum" type="number" class="inputText"> 
				</td>
				<th>供应商出货时间</th>
				<td>
					<select id="supOutTime" name="supOutTime" type="text" class="inputText"></select>
				</td>								
			</tr>
			<tr>								
				<th>计算队列</th>
				<td>
					<input id="unloadPort" name="unloadPort" type="text" class="inputText" readonly="readonly">
				</td>
				<th>运输周期</th>
				<td>
					<input id="transTime" name="transTime" type="number" class="inputText">
				</td>
				<th>特殊到货时间</th>
				<td>
					<select id="speArriveTime" name="speArriveTime" type="text" class="inputText"></select>
				</td>								
			</tr>
			<tr>								
				<th>车型</th>
				<td>
					<input id="carType" name="carType" type="text" class="inputText" readonly="readonly">
				</td>
				<th>合并基准</th>
				<td>
					<input id="mergeNum" name="mergeNum" type="number" class="inputText">
				</td>
				<th>是否批次取货</th>
				<td>
					<select id="batch" class="inputText" type="text" name="batch"></select>
				</td>								
			</tr>
			<tr>								
				<th>集货线路</th>
				<td>
					<input id="routeCode" name="routeCode" type="text" class="inputText" readonly="readonly">
				</td>
				<th>时间基准点</th>
				<td>
					<select id="departTimePoint" name="departTimePoint" type="text" class="inputText"></select>
				</td>
				<th><font color="red">*</font>起始sortId</th>
				<td>
					<input id="startSortId" name="startSortId" type="text" class="inputText">
				</td>								
			</tr>
		</table>		
	</form>
</body>
</html>