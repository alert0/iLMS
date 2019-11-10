<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/orgReldef/orgReldefEditController.js"></script>
		<script>
		var id="${param.id}";
		</script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn  btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th><span>名称:</span><span class="required">*</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>编码:</span><span class="required">*</span></th>
								<td>
									<input class="inputText"  id="code" type="text" ng-model="data.code"   ht-validate="{required:true,maxlength:192,isexist:'${ctx}/org/orgReldef/isExist?id=${param.id}'}"  ht-pinyin="data.name" />
								</td>								
							</tr>
							<tr>								
								<th><span>职务级别:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.postLevel"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>描述:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.description"   ht-validate="{required:false,maxlength:300}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>