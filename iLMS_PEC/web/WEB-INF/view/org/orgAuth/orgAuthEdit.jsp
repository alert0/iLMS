<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/orgAuth/orgAuthEditController.js"></script>
		<script type="text/javascript">
			var orgId = "${param.orgId}";
			var demId = "${param.demId}";
			var isGrade = "${param.isGrade}";//是否为分级组织管理
			var authId = "${param.authId}";
			$(function(){
				if(isGrade){
					$.post(__ctx+"/org/orgAuth/getJson", {"id":authId}, function(data) {
						var result = data;
						orgAuthPerms = result.orgauthPerms;
						userPerms = result.userPerms;
						orgPerms = result.orgPerms;
						posPerms = result.posPerms;
						$("#orgAuthOption").attr("posPerms",result.posPerms);
						$("#orgAuthOption").attr("userPerms",result.userPerms);
						$("#orgAuthOption").attr("orgPerms",result.orgPerms);
						$("#orgAuthOption").attr("orgauthPerms",result.orgauthPerms);
						//根据祖先的权限进行权限隐藏
						addOrgAuth("posPerms");
						addOrgAuth("userPerms");
						addOrgAuth("orgPerms");
						addOrgAuth("orgauthPerms");
					});	
				}
			});
			
			function addOrgAuth(obj){
				var strObj = $("#orgAuthOption").attr(obj);//分级管理员权限
				   
				   if(strObj.indexOf("add")==-1) {
						$("#"+obj).find(".add").remove();
					}
					if(strObj.indexOf("edit")==-1) {
						$("#"+obj).find(".edit").remove();
					}
					if(strObj.indexOf("delete")==-1) {
						$("#"+obj).find(".del").remove();
					} 
			}
		</script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ng-click="save()"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<div style="display: none;" >
				<select id="orgAuth" style="width:99.8% !important;" >
					<option id="orgAuthOption" orgPerms="" userPerms="" orgauthPerms="" posPerms="" ></option>  
			    </select>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>组织管理员:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.userName" readonly="readonly"  ht-validate="{required:true,maxlength:192}"  />
									<input class="inputText" type="hidden" ng-model="data.userId"   ht-validate="{required:false,maxlength:192}"  />
									<input class="inputText" type="hidden" ng-model="data.orgId"   ht-validate="{required:false,maxlength:192}"  />
									<input class="inputText" type="hidden" ng-model="data.demId"   ht-validate="{required:false,maxlength:192}"  />
									<a class="btn btn-primary btn-mini"  ng-click="userDialog()">
										<span>选择</span>
									</a>
								</td>								
							</tr>
							<tr>								
								<th>组织管理权限:</th>
								<td id="orgPerms">
									<input class="inputText" type="hidden" ng-model="data.orgPerms"   ht-validate="{required:false,maxlength:600}"  />
									<input type="checkbox" ng-checked="isSelected(data.orgPerms,'add')" name="orgPerm"  value="add" class="add"  />
									<label for="userAdd"  class="add">增加</label>
									<input type="checkbox" ng-checked="isSelected(data.orgPerms,'delete')" name="orgPerm" value="delete" class="del"  />
									<label for="userAdd"  class="del">删除</label>
									<input type="checkbox" ng-checked="isSelected(data.orgPerms,'edit')" name="orgPerm" value="edit" class="edit"  />
									<label for="userAdd"  class="edit">修改</label>
								</td>								
							</tr>
							<tr>								
								<th>用户管理权限:</th>
								<td id="userPerms">
									<input class="inputText" type="hidden" ng-model="data.userPerms"   ht-validate="{required:false,maxlength:600}"  />
									<input type="checkbox" ng-checked="isSelected(data.userPerms,'add')" name="userPerm"  value="add" class="add"  />
									<label for="userAdd"  class="add">增加</label>
									<input type="checkbox" ng-checked="isSelected(data.userPerms,'delete')" name="userPerm" value="delete" class="del"  />
									<label for="userAdd"  class="del">删除</label>
									<input type="checkbox" ng-checked="isSelected(data.userPerms,'edit')" name="userPerm" value="edit" class="edit"  />
									<label for="userAdd"  class="edit">修改</label>
								</td>								
							</tr>
							<tr>								
								<th>岗位管理权限:</th>
								<td id="posPerms">
									<input class="inputText" type="hidden" ng-model="data.posPerms"   ht-validate="{required:false,maxlength:600}"  />
									<input type="checkbox" ng-checked="isSelected(data.posPerms,'add')" name="posPerm" value="add" class="add" />
									<label for="userAdd"  class="add">增加</label>
									<input type="checkbox" ng-checked="isSelected(data.posPerms,'delete')" name="posPerm" value="delete" class="del"  />
									<label for="userAdd"  class="del">删除</label>
									<input type="checkbox" ng-checked="isSelected(data.posPerms,'edit')" name="posPerm" value="edit" class="edit" />
									<label for="userAdd"  class="edit">修改</label>
								</td>								
							</tr>
							<tr>								
								<th>分级管理员权限:</th>
								<td id="orgauthPerms">
									<input class="inputText" type="hidden" ng-model="data.orgauthPerms"   ht-validate="{required:false,maxlength:600}"  />
									<input type="checkbox" ng-checked="isSelected(data.orgauthPerms,'add')" name="orgAuthPerm"  value="add" class="add" <c:if test="${fn:contains(orgAuth.orgPerms, 'add')}">checked="checked" </c:if> />
									<label for="userAdd"  class="add">增加</label>
									<input type="checkbox" ng-checked="isSelected(data.orgauthPerms,'delete')"  name="orgAuthPerm"  value="delete" class="del" <c:if test="${fn:contains(orgAuth.orgPerms, 'delete')}">checked="checked" </c:if> />
									<label for="userAdd"  class="del">删除</label>
									<input type="checkbox" ng-checked="isSelected(data.orgauthPerms,'edit')" name="orgAuthPerm"  value="edit" class="edit" <c:if test="${fn:contains(orgAuth.orgPerms, 'edit')}">checked="checked" </c:if> />
									<label for="userAdd"  class="edit">修改</label>
								</td>								
							</tr>
							<tr>								
								<th>布局管理权限:</th>
								<td id="layoutPerms">
									<input class="inputText" type="hidden" ng-model="data.layoutPerms"   ht-validate="{required:false,maxlength:600}"  />
									<input type="checkbox" ng-checked="isSelected(data.layoutPerms,'add')" name="layoutPerms"  value="add" class="add" <c:if test="${fn:contains(orgAuth.layoutPerms, 'add')}">checked="checked" </c:if> />
									<label for="userAdd"  class="add">增加</label>
									<input type="checkbox" ng-checked="isSelected(data.layoutPerms,'delete')"  name="layoutPerms"  value="delete" class="del" <c:if test="${fn:contains(orgAuth.layoutPerms, 'delete')}">checked="checked" </c:if> />
									<label for="userAdd"  class="del">删除</label>
									<input type="checkbox" ng-checked="isSelected(data.layoutPerms,'edit')" name="layoutPerms"  value="edit" class="edit" <c:if test="${fn:contains(orgAuth.layoutPerms, 'edit')}">checked="checked" </c:if> />
									<label for="userAdd"  class="edit">修改</label>
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>