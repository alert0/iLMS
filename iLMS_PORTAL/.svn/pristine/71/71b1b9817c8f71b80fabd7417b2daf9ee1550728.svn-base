<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/orgRel/orgRelEditController.js"></script>
<script type="text/javascript">
	 var id="${param.id}";
	 var orgId="${param.orgId}";
</script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
									
					<a class="btn btn-primary btn-sm fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
<a class="btn btn-primary btn-sm  fa-back" onClick="HT.window.closeEdit();"><span>关闭</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							 
							<tr id="job-tr">								
								<th><span>职务:</span></th>
								<td>
									<input class="easyui-combotree-me" id="relDefId" name="relDefId" type="text" ng-model="data.relDefId"   ht-validate="{required:true,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>岗位名称:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.relName"   ht-validate="{required:true,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>岗位编码:</span></th>
								<td>
									<input id="relCode" class="inputText" type="text" ng-model="data.relCode"   ht-validate="{required:true,maxlength:192,isexist:'${ctx}/org/orgRel/isExist?id=${param.id}'}"  ht-pinyin="data.relName"/>
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>