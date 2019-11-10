<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/org/user/userEditController.js"></script>
<script type="text/javascript">
	var id = "${param.id}";
	var orgId = "${param.orgId}";
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
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="save"><span>保存</span></a> <a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="getJson?id=${param.id}" ng-model="data">
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
						<td><input class="inputText" type="text" ng-model="data.fullname" ht-validate="{required:true,maxlength:125}" /></td>

					</tr>
					<tr>
						<th><span>账号:</span><span class="required">*</span></th>
						<td><input class="inputText" type="text" ng-model="data.account" id="account" ht-validate="{required:true,maxlength:125}" /></td>
					</tr>

					<tr ng-if="!data.id">
						<th><span>密码:</span><span class="required">*</span></th>
						<td><input class="inputText" type="password" ng-model="data.password" onkeydown="return banInputSapce(event);" ht-validate="{required:true,minlength:6,maxlength:10}" /></td>
					</tr>
					<tr>
						<th><span>邮箱:</span></th>
						<td><input class="inputText" type="text" ng-model="data.email" ht-validate="{required:false,maxlength:60,email:true}" /></td>
					</tr>
					<tr>
						<th><span>手机号码:</span></th>
						<td><input class="inputText" type="text" ng-model="data.mobile" ht-validate="{maxlength:32,phonenumber:true}" /></td>
					</tr>
					<tr>
						<th><span>微信号:</span></th>
						<td><input class="inputText" type="text" ng-model="data.weixin" ht-validate="{maxlength:60}" /></td>
					</tr>
					<tr>
						<th><span>地址:</span></th>
						<td><input class="inputText" type="text" ng-model="data.address" ht-validate="{required:false,maxlength:300}" /></td>
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
					
					<tr>
						<th><span>状态:</span><span class="required">*</span></th>
						<td><select class="inputText" ng-model="data.status" ht-validate="{required:true}">
								<option value="1">正常</option>
								<option value="0">禁用</option>
						</select></td>
					</tr>
					<tr ng-if="data.id">	 
						<th><span>创建时间:</span></th>
						<td>{{data.createTime | date :'yyyy-MM-dd'}}</td>
					</tr>
				</table>
			</form>

		</div>
		<c:if test="${!empty param.id }">
			<div id="userPostListTab" title="所属组织岗位" style="padding: 0px;">
				<iframe id="userPostList" src="${ctx}/org/user/userPostList?userId=${param.id}" frameborder="no" width="100%" height="97%"></iframe>
			</div>
			<div id="userRoleListTab" title="所属角色" style="padding: 0px;">
				<iframe id="userRoleList" src="${ctx}/org/user/userRoleList?userId=${param.id}" frameborder="no" width="100%" height="97%"></iframe>
			</div>
			<div id="userAttrListTab" title="参数属性" style="padding: 0px;">
				<iframe id="userAttrList" src="${ctx}/system/sysUserParams/sysUserParamsEdit?type=1&id=${param.id}" frameborder="no" width="100%" height="97%"></iframe>
			</div>
		</c:if>
	</div>
</body>
</html>