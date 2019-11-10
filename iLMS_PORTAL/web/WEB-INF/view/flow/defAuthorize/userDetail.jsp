<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp"%>
</head>
<body>
	<div class="srcoll-panel">
		<div class="gray-div">

			<div class="toolbar-panel col-md-12 ">
				<div class="buttons">
			        <a href="javascript:window.opener=null;window.open('','_self');window.close();" class="btn btn-primary" plain="true">关闭</a>
			    </div>
			</div>

			<form id="userForm" action="save" method="post">
				<div id="userTab" class="easyui-tabs">
					<div title="个人资料" style="width: 700px; ">
							<table class="table-form" type="main">
								<tr>
									<td rowspan="<c:if test="${not empty user.userId}">10</c:if><c:if test="${empty user.userId}">10</c:if>" align="center" width="26%">
										<div class="person_pic_div">
											<p>
												<img id="personPic" style="height: 380px; width: 250px" src="${ctx}/${pictureLoad}" alt="个人相片" />
											</p>
										</div>
									</td>
									<th><span>姓名:</span></th>
									<td>${user.fullname}</td>
								</tr>
								<tr>
									<th><span>账号:</span></th>
									<td>${user.account}</td>
								</tr>
								<tr>
									<th><span>状态:</span></th>
									<td>
									<c:choose>
										<c:when test="${user.status.value eq 'actived'}">激活</c:when>
										<c:when test="${user.status.value eq 'inactived'}">未激活</c:when>
										<c:when test="${user.status.value eq 'locked'}">锁定</c:when>
										<c:when test="${user.status.value eq 'deleted'}">锁定</c:when>
									</c:choose> 

									</td>
								</tr>
								<tr>
									<th><span>性别:</span></th>
									<td><c:choose>
										<c:when test="${user.sex eq 'male'}">男</c:when>
										<c:when test="${user.sex eq 'female'}">女</c:when>
									</c:choose></td>
								</tr>
								<tr>
									<th><span>手机:</span></th>
									<td>${user.mobile}</td>
								</tr>
								<tr>
									<th><span>邮件:</span></th>
									<td>${user.email}</td>
								</tr>
								<tr>
									<th><span>地址:</span></th>
									<td>${user.address}</td>
								</tr>
								<tr>
									<th><span>QQ号:</span></th>
									<td>${user.qq}</td>
								</tr>
							</table>
					
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
