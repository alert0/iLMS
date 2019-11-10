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
	<style>
		.combo-panel {
		  height:200px;
		  overflow: auto;
		}
	</style>
</head>
	
<script type="text/javascript">
	$(function() {			
		var flag = "${param.routeCode}";

		if(!$.isEmpty(flag)){
			$('#routeCode').textbox('readonly',true);
			$('#todayNo').textbox('readonly',true);
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
				<th><font color="red">*</font>路线代码</th>
				<td>
					<input id="routeCode" name="routeCode" class="easyui-textbox" type="text" 
							data-options="required:true,validType:'length[1,20]'" style="width:160px;height:25px"> 
				</td>								
			</tr>
			<tr>								
				<th><font color="red">*</font>当日车次</th>
				<td>
					<input id="todayNo" name="todayNo" type="text" class="easyui-numberspinner"
       						  data-options="required:true,min:0,editable:true,validType:'length[1,10]'" style="width:160px;height:25px" > 
				</td>								
			</tr>
			<tr>								
				<th><font color="red">*</font>取货时间</th>
				<td>
					<input id="pickTime" name="pickTime" type="text" class="easyui-combobox" style="width:160px;height:25px"/>
				</td>								
			</tr>
			<tr>								
				<th><font color="red">*</font>到货时间</th>
				<td>
					<input id="arriveTime" name="arriveTime" type="text" class="easyui-combobox" style="width:160px;height:25px" />
				</td>								
			</tr>
		</table>		
	</form>
</body>
<script type="text/javascript">
	$(function(){
		$.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/pup/pickupTime/getPickTimeJson?routeCode=${param.routeCode}",
            dataType: "json",
            success: function(data){
            	if(data != null){
	            	$("#routeCode").textbox("setValue",data.routeCode);
	            	$("#todayNo").numberspinner("setValue",data.todayNo);
	            	$("#pickTime").combobox("setValue",data.pickTime);
	            	$("#arriveTime").combobox("setValue",data.arriveTime);
            	}
            }
        });
		loadTimeList();
	});
	
	function loadTimeList(){
		$.ajax({  
		    url : "${pageContext.request.contextPath}/pup/pickupTime/schedule",  
		    async : false,
		    success : function(data) {
		    	$("#pickTime,#arriveTime").combobox({ 
		        	data: data.rows,
		            valueField : 'id',  
		            textField : 'text', 
		            editable : false,  
		            required : true,
		            onLoadSuccess: function () {
		                var val = $(this).combobox("getData");
		                for (var item in val[0]) {
		                    if (item == "id") {
		                        $(this).combobox("select", val[0][item]);
		                    }
		                }
		            }
		        });  
		    }  
		});
	}
	function saveEdit(){
		$.ajax({  
		    url : "${pageContext.request.contextPath}/pup/pickupTime/saveEdit?flag=${param.routeCode}",  
		    async : false,
		    data : $('#form').serialize(),
		    contentType:'application/x-www-form-urlencoded',
		    success : function(data) {
		    	var result = new com.hotent.form.ResultMessage(data);
		    	var flag = "${param.routeCode}";
		    	if(result.isSuccess()){
		    		$.messager.confirm('提示信息',result.getMessage()+",是否继续?" ,function(r){
		    			if(r){
		    				if(flag == null){
		    					$("#form").form('clear');
		    				}
			    		}else{
			    			HT.window.closeEdit(true,'grid');
			    			}
		    			});
		    	}else{
		    		$.topCall.error(result.getCause());
		    	}
		    }
		});
	}
</script>
</html>