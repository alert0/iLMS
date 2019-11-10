<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<link rel="stylesheet" href="${ctx}/js/system/query/dataRights.css">
		<style type="text/css">
			.table-form.list-table tr th{
			    text-align: center;
			}
			.table-form.list-table td{
			    text-align: center;
			}
		</style>
		<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/arrayToolService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/dataTemplate/DataRightsApp.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/dataTemplate/bpmDataTemplateController.js"></script>
		<script>
			var dataRightsJson = "",permissionList="",status,jsonObject;
			(function(){
				status = ${jsonObject.status};
				var jsons = ${jsonObject};
				if(status==0){
					HT.window.closeEdit();
					var msg = "${jsonObject.msg}";
					$.topCall.error(msg,"",function(){
						
					});
				}else{
					dataRightsJson =  ${jsonObject.DataRightsJson};
					jsonObject = ${jsonObject};
					permissionList = ${jsonObject.permissionList};
					//是否初始化模板
					if(!dataRightsJson.resetTemp){
						if(dataRightsJson.id){
							dataRightsJson.resetTemp="0";
						}else{
							dataRightsJson.resetTemp="1";
						}
					}
				}
			})();
			
			//获取添加为菜单的url
			function getTemplateResourceUrl(){
				var alias = dataRightsJson.formKey;
				var url= "/form/dataTemplate/dataList_"+ alias+".ht";
				return url;
			}
		</script>
	</head>
	<body ng-controller="ctrl">
		<form name="dataRightsForm">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<button class="btn btn-primary btn-sm fa-save" ng-model="data" ng-click="save()" ng-disabled="btnDisable.save" ><span>保存</span></button>
					<a ng-if="dataRightsJson.id" class="btn btn-primary btn-sm fa-eye" ng-click="preview()" ><span>预览</span></a>
					<a ng-if="dataRightsJson.id" class="btn btn-primary btn-sm fa-edit" ng-click="editTemplate()" ><span>编辑模板</span></a>
					<a ng-if="dataRightsJson.id" class="btn btn-primary btn-sm fa-edit" ng-click="addToResource()" ><span>添加为菜单</span></a>
					
					<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			
			<div class="easyui-tabs" style="padding:0;" data-options="fit:false,border:false">
				
				<div tabid="baseSetting" title="基本信息">
						<table class="table-form" cellpadding="0" cellspacing="0" border="0" type="main" style="border-width: 0!important;">
							<tr>
								<th  width="10%">表单别名:</th>
								<td>
									<span ng-bind="dataRightsJson.formKey"  readonly="readonly" validate="{required:true}" style="width:210px;margin-right:2px;" />
								</td>
							</tr>
							<tr>
								<th  width="10%">绑定流程:</th>
								<td>
									<input type="text"  ng-model="dataRightsJson.subject" readonly="readonly"  class="inputText"  style="width:210px;margin-right:2px;" />
									<a class="btn btn-xs btn-primary "  style="margin-right:5px;" type="button" ng-click="selectFlow()">选择</a>
									<a class="btn btn-xs btn-primary fa-rotate-left" style="margin-right:5px;" ng-click="cancel()"/>重置</a>
								</td>
							</tr>
							<tr>
								<th >是否分页:</th>
								<td>
									<label><input type="radio" ng-model="dataRightsJson.needPage" value="0" >
									不分页</label>
									<label>
									<input type="radio" ng-model="dataRightsJson.needPage" value="1" >
									分页</label>
									<span style="color:red;" ng-if="dataRightsJson.needPage==1">
										分页大小：
										<select ng-model="dataRightsJson.pageSize" >
											<option value="5"  >5</option>
											<option value="10" >10</option>
											<option value="15" >15</option>
											<option value="20" >20</option>
											<option value="50" >50</option>
										</select>
									</span>
								</td>
							</tr>
							<!-- 
							<tr>
								<th>是否初始查询:</th>
								<td>
									<select ng-model="dataRightsJson.isQuery"  ht-validate="{required:true}" class="inputText">
										<option value="1"  >是</option>
										<option value="0" >否</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>
									没有过滤条件
									<br/>
									是否需要默认过滤:
								</th>
								<td>
									<select ng-model="dataRightsJson.isFilter"  ht-validate="{required:true}" class="inputText">
										<option value="1"  >是</option>
										<option value="0" >否</option>
									</select>
								</td>
							</tr>
							 -->
							<tr>
								<th>
									是否需要初始化模板
								</th>
								<td>
									<select ng-model="dataRightsJson.resetTemp"  ht-validate="{required:true}" class="inputText">
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>数据模板:</th>
								<td>
									<select ng-model="dataRightsJson.templateAlias"  ht-validate="{required:true}" title="添加更多数据模板，请到自定义表单模板中添加类型为[业务数据模板]的模板"  ht-tip>
										<option value="">--请选择数据模板--</option>
										<c:forEach items="${jsonObject.templates}" var="template">
												<option value="${template.alias}">${template.templateName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					
				</div>
				
				<div tabid="displaySetting" title="显示列字段">
					<display-setting ></display-setting>
				</div>
				
				<div tabid=conditionSetting title="查询条件字段">
				 	<condition-setting ></condition-setting>
				</div>
				
				<div tabid="sortSetting" title="排序字段">
					<sort-setting></sort-setting>
				</div>
				<!-- <div tabid="filterSetting" title="过滤条件">
					<filter-setting></filter-setting>
				</div> -->
				<div tabid="manageSetting" title="功能按钮">
					<manage-setting></manage-setting>
				</div>
			</div>
			</form>	
	</body>
</html>