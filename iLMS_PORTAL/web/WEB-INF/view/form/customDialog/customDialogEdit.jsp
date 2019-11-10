<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/sysDataSource/sysDataSourceService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/editController.js"></script>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
<script type="text/javascript">
	var id = "${param.id}";
	function getScope() {
		return $("body").scope();
	}
</script>
</head>
<body ng-app="app" ng-controller="EditController">
	<div id="tb" class="toolbar-panel  ">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa-save btn-sm" ng-click="save();">保存</a>
			<button onclick="HT.window.closeEdit(true,'customDialogList');" class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	<form name="form">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>
					<span class="required">*</span>
					名称:
				</th>
				<td>
					<input type="text" id="name" name="name" ng-model="prop.name" class="inputText" ht-validate="{required:true}" />
				</td>

				<th>
					<span class="required">*</span>
					别名:
				</th>
				<td>
					<input ng-if="prop.system == 1" readonly="readonly" type="text" id="alias" name="alias" ht-pinyin="prop.name" ng-model="prop.alias" class="inputText" ht-validate="{required:true}" />
					<input ng-if="prop.system != 1" type="text" id="alias" name="alias" ht-pinyin="prop.name" ng-model="prop.alias" class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>

			<tr>
				<th>样式:</th>
				<td>
					<label class="radio-inline"><input value="0" type="radio" ng-model="prop.style" <c:if test="${param.id!=null}">disabled="true"</c:if> />
					列表</label>
					<label class="radio-inline"><input value="1" type="radio" ng-model="prop.style" <c:if test="${param.id!=null}">disabled="true"</c:if> />
					树形</label>
				</td>
				<th>尺寸:</th>
				<td>
					宽度:
					<input type="text" ng-model="prop.width" class="inputText" />
					高度:
					<input type="text" ng-model="prop.height" class="inputText" />
				</td>
			</tr>
			<tr ng-if="prop.style=='0'">
				<th>分页:</th>
				<td colspan="{{prop.needPage=='1'?1:3}}">
					<label class="radio-inline"><input value="1" type="radio" ng-model="prop.needPage" />
					是</label>
					<label class="radio-inline"><input value="0" type="radio" ng-model="prop.needPage" />
					否</label>
				</td>
				<th ng-if="prop.needPage=='1'">分页大小:</th>
				<td ng-if="prop.needPage=='1'">
					<input type="text" id="pageSize" name="pageSize" ng-model="prop.pageSize" class="inputText" />
				</td>
			</tr>
			<tr>
				<th>单选多选:</th>
				<td>
					<label class="radio-inline"><input value="1" type="radio" ng-model="prop.selectNum" />
					单选</label>
					<label class="radio-inline"><input value="-1" type="radio" ng-model="prop.selectNum" />
					多选</label>
				</td>
				<th>系统内部对话框:</th>
				<td>
					<select class="inputText" ng-model="prop.system" ng-options="m.value as m.key for m in CommonList.yesOrNoList">
					</select>
				</td>
			</tr>
			<!-- 树多选时 -->
			<tr ng-if="prop.style=='1'&&prop.selectNum=='-1'">
				<th>父级联动</th>
				<td>
					<span ht-boolean="1/0" text="是/否" ng-model="prop.parentCheck"></span>
				</td>
				<th>子表联动</th>
				<td>
					<span ht-boolean="1/0" text="是/否" ng-model="prop.childrenCheck"></span>
				</td>
			</tr>
			
			<tr ng-if="prop.id==''">
				<th>
					<span class="required">*</span>
					数据源:
				</th>
				<td>
					<select class="inputText" ng-model="prop.dsalias" ng-options="m.alias as m.name for m in dataSourcesInBean">
					</select>
				</td>

				<th>
					<span class="required">*</span>
					查询表(视图):
				</th>
				<td>
					<select class="inputText" ng-model="prop.isTable" ng-options="m.value as m.key for m in isTableList">
					</select>
					<input type="text" ng-model="prop.objName" class="inputText" />
					<a ng-click="getByDsObjectName()" type="button" class="btn btn-primary fa fa-search">查询</a>
				</td>
			</tr>
			<tr>
				<th>选择表或视图:</th>
				<td colspan="3" valign="top">
					<a ng-if="prop.objName!=null" ng-click="showSettingDialog();" href="javascript:;" class="btn btn-primary fa fa-edit">设置列</a>
					<span ng-if="prop.id!=''">对象名称：{{prop.objName}}所属数据源：{{prop.dsalias}}</span>
					</br>
					<div ng-if="prop.isTable==1&&tableOrViewList.length>0">
						<select class="inputText" ng-if="prop.id==''" ng-model="prop.objName" ng-options="m.name as (m.name+'('+m.comment+')') for m in tableOrViewList">
						</select>
					</div>
					<div ng-if="prop.isTable==0">
						<select class="inputText" ng-model="prop.objName" ng-options="m.name as m.name for m in tableOrViewList">
						</select>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>