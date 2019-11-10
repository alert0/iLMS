<%@page import="com.hotent.sys.util.ContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">

var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	//上传照片
	$scope.addPic = function(){
		UploadDialog({max:1,size:10,callback:picCallBack});
	};
	//删除照片
	$scope.delPic = function(){
		$scope.data.photo ="";
		$("#personPic").attr("ng-src",__ctx+"/css/image/touxian.jpeg");
	};
	function picCallBack(array){
		if(!array && array!="") return;
		var fileId=array[0].id,
			fileName=array[0].name;
		var path= __ctx + "/system/file/getFileById?fileId=" + fileId;
		if(/[\s\S]+.(png|gif|jpg)/gi.test(fileName)){
			$scope.data.photo = "/system/file/getFileById?fileId=" + fileId;
			$("#personPic").attr("src",path);
		}
			
		else
			$.topCall.error("请选择*png,*gif,*jpg类型图片!");
				
	};
	
	$scope.$on("afterLoadEvent",function(event,data){
		if(data && data.photo!='' && data.photo != null){
			$scope.isShowPhoto = 'custom';
		}else{
			$scope.isShowPhoto = 'init';
		}
	});
	
	
	
}]);


</script>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="saveUserInfo"><span>保存</span></a>
		</div>
	</div>

		<form name="form" method="post" ht-load="getJson?id=<%=ContextUtil.getCurrentUserId()%>" ng-model="data">
			<table class="table-form" cellspacing="0">
				<tr>
					<td rowspan="20" align="center" width="26%">
							<div style="background: none; height: 24px; text-align: center;">
								<a class="btn btn-sm btn-primary" href="javascript:;" ng-click="addPic();"><i class="fa fa-file-image-o"></i>上传照片</a>
								<a class="btn btn-sm btn-primary" href="javascript:;" ng-click="delPic();"><i class="fa fa-remove"></i>删除照片</a>
							</div>
							<div class="person_pic_div">
								<p>
									<img ng-if="isShowPhoto == 'init' "  id="personPic" ng-src="${ctx}/css/image/touxian.jpeg" style="height: 378px; width: 270px;margin-top: 25px;" alt="个人相片" />
									<img ng-if="isShowPhoto == 'custom' " id="personPic" ng-src="${ctx}{{data.photo}}" style="height: 378px; width: 270px;margin-top: 25px;" alt="个人相片" />
									<input type="hidden" ng-model="data.photo" id="photo"/>
								</p>
							</div>
					</td>
					<th><span>姓名:</span><span class="required">*</span></th>
					<td><input class="inputText" type="text" ng-model="data.fullname" ht-validate="{required:true,maxlength:765}" /></td>

				</tr>
				<tr>
					<th><span>账号:</span><span class="required">*</span></th>
					<td><input class="inputText" disabled="disabled" type="text" ng-model="data.account" id="account" /></td>
				</tr>

				<tr>
					<th><span>邮箱:</span></th>
					<td><input class="inputText" type="text" ng-model="data.email" ht-validate="{required:false,maxlength:192,email:true}" /></td>
				</tr>
				<tr>
					<th><span>手机号码:</span></th>
					<td><input class="inputText" type="text" ng-model="data.mobile" ht-validate="{maxlength:96,phonenumber:true}" /></td>
				</tr>
				<tr>
					<th><span>微信号:</span></th>
					<td><input class="inputText" type="text" ng-model="data.weixin" ht-validate="{maxlength:192}" /></td>
				</tr>
				<tr>
					<th><span>地址:</span></th>
					<td><input class="inputText" type="text" ng-model="data.address" ht-validate="{required:false}" /></td>
				</tr>

				<tr>
					<th><span>性别：</span></th>
					<td>
						<select class="inputText" ng-model="data.sex">
							<option value="未知">未知</option>
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>