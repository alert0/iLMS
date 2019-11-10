<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/user/userGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div ng-hide="${param.isheader}" class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}" method="post"  ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<td rowspan="10" align="center" width="26%">
								 
									<div class="person_pic_div">
										<p>
										<img ng-if="isShowPhoto == 'init' "  id="personPic" ng-src="${ctx}/css/image/touxian.jpeg" style="height: 378px; width: 270px;margin-top: 25px;" alt="个人相片" />
									    <img ng-if="isShowPhoto == 'custom' " id="personPic" ng-src="${ctx}{{data.photo}}" style="height: 378px; width: 270px;margin-top: 25px;" alt="个人相片" />
										</p>
									   <input type="hidden" ng-model="data.photo" id="photo"/>
									</div>
									</td>
						<th><span>姓名:</span></th>
						<td> {{data.fullname }}</td>
					</tr>
					<tr>								
						<th><span>账号:</span></th>
						<td> {{data.account }}</td>
					</tr>
					
					<tr>								
						<th><span>邮箱:</span></th>
						<td> {{data.email }}</td>
					</tr>
					<tr>								
						<th><span>手机号码:</span></th>
						<td> {{data.mobile }}</td>
					</tr>
					<tr>								
						<th><span>微信号:</span></th>
						<td> {{data.weixin }}</td>
					</tr>
				
					<tr>								
						<th><span>地址:</span></th>
						<td> {{data.address }}</td>
					</tr>
					 
					<tr>								
						<th><span>性别：</span></th>
						<td> {{data.sex }}</td>
					</tr>
					<tr>								
						<th><span>来源:</span></th>
						<td> {{data.from }}</td>
					</tr>
					<tr>								
						<th><span>状态:</span></th>
						<td> {{data.status==0?"禁用":"正常" }}</td> 
					</tr>
						<tr>								
						<th><span>创建时间:</span></th>
						<td> {{data.createTime | date :'yyyy-MM-dd'}}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>