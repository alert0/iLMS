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
			$('#orderPackage').attr('disabled',true);
			$('#totalOrderBox').attr('disabled',true);
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
					<a class="btn btn-primary fa-save" ng-model="data"  ht-save="${ctx}/mp/mpOrderRecordHis/save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
		<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		  <div title="订单履历" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/mp/mpOrderRecordHis/curdgetJson?planOrderId=${param.id}" ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>零件编号:</th>
								<td>
									<input id ="partNo" class="inputText" type="text" ng-model="data.partNo"  
									 ht-validate="{required:false,maxlength:20} "  />
								</td>								
							</tr>
							<tr>								
								<th>包装数:</th>
								<td>
									<input id ="orderPackage" class="inputText" type="text" ng-model="data.orderPackage" 
								   ht-validate="{required:false,maxlength:20}"  />
								</td>								
							</tr>
							<tr>								
								<th>订购箱数:</th>
								<td>
									<input id ="totalOrderBox" class="inputText" type="text" ng-model="data.totalOrderBox"  
								   ht-validate="{required:false,maxlength:10}"  />
								</td>								
							</tr>
							<tr>								
								<th>调整箱数:</th>
								<td>
									<input id ="adjBox" class="inputText" type="text" ng-model="data.adjBox" 
								  ht-validate="{required:false,number:true,maxIntLen:8}"  />
								</td>								
							</tr>
						    <tr>								
								<th>调整订购数量:</th>
								<td>
									<input id ="adjOrderNum" class="inputText" type="text" ng-model="data.adjOrderNum" 
								  ht-validate="{required:false,number:true,maxIntLen:8}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		</div>
		</div>
	</body>
</html>
<script> 
/* $(function() {
	var url = location.search; //获取url中"?"符后的字串   
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) { 
	    	  if(strs[i].split("=")[0]=='partNo'){
	    		  $("#partNo").val(strs[i].split("=")[1]);
	    	  }
	    	  if(strs[i].split("=")[0]=='orderPackage'){
	    		  $("#orderPackage").val(strs[i].split("=")[1]);
	    	  }
	    	  if(strs[i].split("=")[0]=='totalOrderBox'){
	    		  $("#totalOrderBox").val(strs[i].split("=")[1]);
	    	  }
	    	  if(strs[i].split("=")[0]=='adjBox'){
	    		  $("#adjBox").val(strs[i].split("=")[1]);
	    	  }
	    	  if(strs[i].split("=")[0]=='totalOrderNum'){
	    		  $("#totalOrderNum").val(strs[i].split("=")[1]);
	    	  }
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   }   
	   
	   console.log(theRequest);
	   //return theRequest;  


});*/</script>