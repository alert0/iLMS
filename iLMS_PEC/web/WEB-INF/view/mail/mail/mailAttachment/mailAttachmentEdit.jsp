<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mailAttachment/mailAttachmentEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>文件名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.FILENAME"   ht-validate="{required:false,maxlength:384}"  />
								</td>								
							</tr>
							<tr>								
								<th>文件存放路径:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.FILEPATH"   ht-validate="{required:false,maxlength:384}"  />
								</td>								
							</tr>
							<tr>								
								<th>邮件ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.MAILID"   ht-validate="{required:false,maxlength:60}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>