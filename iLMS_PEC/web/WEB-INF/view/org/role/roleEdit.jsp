<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/role/roleEditController.js"></script>
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
									<input class="inputText" id="alias" type="text" ng-model="data.alias"   ht-validate="{required:true,maxlength:192}" ht-pinyin="data.name" />
								</td>								
							</tr>
							<tr>								
								<th><span>状态:</span><span class="required">*</span></th>
								<td>
								<select class="inputText" ng-model="data.enabled" ht-validate="{required:true}"  >
								  <option value="0">禁用</option>
								   <option value="1">正常</option>
								</select>
									 
								</td>								
							</tr>
							<tr>								
								<th><span>描述:</span></th>
								<td>
									<textarea class="inputText" id="description"   ng-model="data.description"></textarea>
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>