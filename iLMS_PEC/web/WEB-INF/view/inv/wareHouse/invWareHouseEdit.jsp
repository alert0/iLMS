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
	$(function(){
		HtUtil.loadComboBox({
			url:__ctx+"/inv/wareHouse/getWareType",
			dictArr:[{
				el : '#wareType',
				addBlank : false
			}]
		});
	})
	
	function saveEdit(){
		$.ajax({  
		    url : "${pageContext.request.contextPath}/inv/wareHouse/saveWareHouse?id=${param.id}",  
		    async : false,
		    data : $('#form').serialize(),
		    contentType:"application/x-www-form-urlencoded;charset=utf-8",
		    success : function(data) {
		    	var result = new com.hotent.form.ResultMessage(data);
		    	if(result.isSuccess()){
		    		$.messager.confirm('提示信息',result.getMessage()+",是否继续?" ,function(r){
		    			if(!r){
		    				HT.window.closeEdit(true,'grid');
		    			}else{
		    				location.reload(); 
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
				<a class="btn btn-sm  btn-primary fa-save" ng-model="data" onClick="saveEdit()"><span>保存</span></a>
				<a class="btn btn-sm  btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form id="form" name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
			<table class="table-form"  cellspacing="0" >
				<tr>								
					<th><font color="red">*</font>仓库代码</th>
					<td>
						<input class="inputText" type="text" ng-model="data.wareCode" name="wareCode"  ht-validate="{required:true,maxlength:150}"  />
					</td>
					<th>ERP仓库代码</th>	
					<td>
						<input class="inputText" type="text" ng-model="data.erpWareCode" name="erpWareCode">
					</td>							
				</tr>
				<tr>								
					<th><font color="red">*</font>仓库名称</th>
					<td>
						<input class="inputText" type="text" ng-model="data.wareName" name="wareName"   ht-validate="{required:true,maxlength:150}"  />
					</td>
					<th>仓库地址</th>	
					<td>
						<input class="inputText" type="text" ng-model="data.wareAddr">
					</td>						
				</tr>
				<tr>								
					<th><font color="red">*</font>仓库类型</th>
					<td>
						<select style="width:160px" classs="inputText" type="text" id="wareType" name="wareType" ng-model="data.wareType"
							name="wareType" ht-validate="{required:true,maxlength:150}"></select>
					</td>								
				</tr>
			</table>
		</form>	
	</body>
</html>