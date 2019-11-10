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
		
		$scope.$on("afterLoadEvent",function(event,data){
			HtUtil.loadComboBoxByDict({
				dictArr:[{
					typeKey:'w',
					el : '#workcenter',
					defaultVal : data.col1,
					addBlank : false
				}]
			});
		});
		
		$scope.uploadFile = function(){
			
			//打开文件上传弹窗
			UploadDialog({
				max:1, 
				size:10, 
				storeType : 'ftp', //文件存储类型 ftp\disk\db  可忽略,由系统属性参数指定默认值
				callback : uploadCallBack
			});
		};
		function uploadCallBack(array){
			if(!array && array!="") return;
			var fileId=array[0].id,
				fileName=array[0].name;
			
			//回调函数获取ID测试下载，也 可将ID和其它业务数据一起保存
			var path= __ctx + "/system/file/download?id=" + fileId;
			$('#downloadiframe').attr('src', path);
					
		};
		
	}]);
</script>
<script type="text/javascript">
	var id = "${param.id}";
	$(function() {
		
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
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/demo/demo/save"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
			<a class="btn btn-sm btn-primary" href="javascript:;" ng-click="uploadFile();"><i class="fa fa-file-image-o"></i>上传文件</a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/demo/demo/curdgetJson?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
					<tr>
						<th><span>车间:</span><span class="required">*</span></th>
						<td><select id="workcenter" class="inputText" ng-model="data.col1" ht-validate="{required:true}">
						</select></td>
					</tr>
					<tr>
						<th><span>字段一:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.col1" ht-validate="{required:true,maxlength:125}" /></td>

					</tr>
					<tr>
						<th><span>字段二:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.col2" ht-validate="{required:true,maxlength:125}" /></td>
					</tr>
					<tr>
						<th><span>字段三:</span></th>
						<td><input class="inputText" type="text" ng-model="data.col3" ht-validate="{required:false,maxlength:60,email:true}" /></td>
					</tr>
					<tr>
						<th><span>字段四:</span></th>
						<td><input class="inputText" type="text" ng-model="data.col4" ht-validate="{maxlength:32,phonenumber:true}" /></td>
					</tr>
				</table>
			</form>

		</div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>