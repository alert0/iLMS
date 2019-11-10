<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/portal/sysIndexLayout/sysIndexLayout/sysIndexLayoutEditController.js"></script>
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
								<th>布局名称:*</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name" id="name"  ht-validate="{required:true,maxlength:30}"  />
								</td>								
							</tr>
							<tr>								
								<th>排序:*</th>
								<td>
									<input class="inputText" type="text" ng-model="data.sn" id="sn"  ht-validate="{posinteger:true,required:true,maxlength:11}"  />
								</td>								
							</tr>
							<tr>								
								<th>描述:</th>
								<td>
									<textarea rows="3" cols="80" type="text" ng-model="data.memo" id="memo"  ht-validate="{maxlength:255}" ></textarea>
								</td>								
							</tr>
							<tr>								
								<th>模板:*</th>
								<td>
									<textarea id="templateHtml" ng-model="data.templateHtml_temp" style="width: 90%;height: 220px;"  ht-validate="{required:true}" ></textarea>
								</td>								
							</tr>
				</table>
				
			</form>
		
	</body>
</html>