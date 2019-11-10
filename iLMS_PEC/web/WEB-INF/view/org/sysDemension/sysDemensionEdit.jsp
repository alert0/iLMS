<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/sysDemension/sysDemensionEditController.js"></script>
	</head>
	<body ng-controller="ctrl" ng-init="init()">
		
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
								<th><span>维度名称:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.demName"   ht-validate="{'required':true,'maxlength':30,'isexist':'isExist?id=${param.id}'}"  />
								</td>								
							</tr>
							<tr>
								<th><span>组织别名：</span><span class="required">*</span></th>
								<td><input class="inputText" type="text" ng-model="data.demCode" ng-disabled="data.id" id="demCode" ht-validate="{required:true,maxlength:192,isexist:'isExist?id=${param.id}'}" ht-pinyin="data.demName" /></td>
							</tr>
							<tr>								
								<th><span>描述:</span></th>
								<td>
									<textarea rows="5" cols="80" ng-model="data.demDesc"   ht-validate="{required:false,maxlength:300}"  ></textarea>
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>