<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysDataSource/sysDataSourceService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/editController.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
		<script type="text/javascript">
			var id ="${param.id}";
			function getScope(){
				return $("body").scope();
			}
			
			$(function() {
				$("#name").blur(function(){
					var prop=getScope().prop;
					if(prop.name==null||prop.name==""||(prop.alias!= null&&prop.alias!="")) return;
					getScope().$apply(function(){
						prop.alias=ChineseToPinyin({Chinese:prop.name});
			        });
				});
			});
		</script>
	</head>
	<body ng-app="app" ng-controller="Controller" class="easyui-layout">
		<div id="tb" class="toolbar-panel">
		    <div class="buttons" >
		        <a href="javascript:;" class="btn btn-primary fa-save btn-sm" ng-click="save();"><span>保存</span></a>
		        <a href="list" onclick="HT.window.closeEdit(true,'grid');" class="btn btn-primary btn-sm fa-back" ><span>关闭</span></a>
		    </div>
		</div>
		<form name="form">
			<table class="table-form" cellspacing="0">
				<tr>
					<th><span class="required">*</span>名字:</th>
					<td>
						<input type="text" id="name" name="name" ng-model="prop.name" class="inputText" ht-validate="{required:true}"  />
					</td>
					
					<th><span class="required">*</span>别名:</th>
					<td>
						<input type="text" id="alias" name="alias" ng-model="prop.alias" class="inputText" ht-validate="{required:true}"  />
					</td>								
				</tr>
				
				<tr>								
					<th>分页:</th>
					<td>
						<label class="radio-inline">
							<input value="1" type="radio" ng-model="prop.needPage"/>是
						</label>
						<label class="radio-inline">
							<input value="0" type="radio" ng-model="prop.needPage"/>否
						</label>
					</td>
					
					<th ng-if="prop.needPage">分页大小:</th>
					<td ng-if="prop.needPage">
						<input type="text" id="pageSize" name="pageSize" ng-model="prop.pageSize"  class="inputText" ht-validate="{number:true}"  />
					</td>									
				</tr>
				<tr ng-if="prop.id==''">								
					<th><span class="required">*</span>数据源:</th>
					<td>
						<select class="inputText"  ng-model="prop.dsalias" ng-options="m.alias as m.name for m in dataSourcesInBean">
						</select>
					</td>
					
					<th><span class="required">*</span>查询表(视图):</th>
					<td>
						<select class="inputText"  ng-model="prop.isTable" ng-options="m.value as m.key for m in isTableList">
						</select>
						<input type="text" ng-model="prop.objName" class="inputText"/>
						<a ng-click="getByDsObjectName();" type="button" class="btn btn-primary fa fa-search">查询</a>
					</td>			
				</tr>
				<tr>
					<th><span>选择表或视图:</span></th>
					<td colspan="3"  valign="top">
						<a ng-if="prop.objName!=null" ng-click="showSettingDialog();" href="javascript:;" class="btn btn-primary fa fa-edit">设置列</a><span ng-if="prop.id!=''">对象名称：{{prop.objName}}所属数据源：{{prop.dsalias}}</span></br>
						<div ng-if="prop.isTable==1">
							<select class="inputText" style="width: 320px" ng-if="prop.id==''" size="10" ng-model="prop.objName" ng-options="m.name as (m.name+'('+m.comment+')') for m in tableOrViewList">
							</select>
						</div>
						<div ng-if="prop.isTable==0">
							<select class="inputText" style="width: 320px" size="10" ng-model="prop.objName" ng-options="m.name as m.name for m in tableOrViewList">
							</select>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>