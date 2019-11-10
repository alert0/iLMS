<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/combinateDialog/combinateDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/combinateDialog/editController.js"></script>
<script type="text/javascript">
	var id = "${param.id}";
</script>
</head>
<body class="easyui-layout" fit="true" ng-app="app" ng-controller="ctrl">
	<div data-options="region:'center',border:false">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-save" href="javascript:void(0);" ng-model="prop" ht-save="save">
					<span>保存</span>
				</a>
				<a class="btn btn-primary btn-sm fa-back" href="javascript:HT.window.closeEdit(true,'grid')">
					<span>返回</span>
				</a>
			</div>
		</div>
		<form name="form">
			<table class="table-form" cellspacing="0">
				<tr>
					<th>
						<span>名称:</span>
					</th>
					<td>
						<input class="inputText" type="text" ng-model="prop.name" ht-validate="{required:true}" />
					</td>
					<th>
						<span>别名:</span>
					</th>
					<td>
						<input class="inputText" type="text" ng-model="prop.alias" ht-pinyin="prop.name" ht-validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<th>
						<span>宽:</span>
					</th>
					<td>
						<input class="inputText" type="text" ng-model="prop.width" ht-validate="{required:true}" />
					</td>
					<th>
						<span>高:</span>
					</th>
					<td>
						<input class="inputText" type="text" ng-model="prop.height" ht-validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<th>
						<span>左边树对话框:</span>
					</th>
					<td>
						{{prop.treeDialogName}}
						<a class="btn btn-primary btn-sm fa-search" ng-click="selectTreeDialog()">
							<span>选择</span>
						</a>
					</td>
					<th>
						<span>右边列表对话框:</span>
					</th>
					<td>
						{{prop.listDialogName}}
						<a class="btn btn-primary btn-sm fa-search" ng-click="selectListDialog()">
							<span>选择</span>
						</a>
					</td>
				</tr>
				<tr ng-show="param.treeDialog&&param.listDialog">
					<th width="20%">组合规则:</th>
					<td colspan="3">
						<table class="table-form" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th colspan="3">
									<a href="javaScript:void(0)" class="btn fa-add pull-left" ng-click="createField()">添加</a>
								</th>
							</tr>
							<tr style="height: 35px" ng-repeat="item in prop.field">
								<td>
									<select class="inputText" ng-model="item.tree" ng-options="m as m.comment for m in param.treeDialog.resultfield track by m.field"></select>
								</td>
								<td>
									<select class="inputText" ng-model="item.list" ng-options="m as m.comment for m in param.listDialog.conditionfield track by m.field"></select>
								</td>
								<td>
									<a href="javaScript:void(0)" class="btn fa-remove" ng-click="ArrayTool.del($index,prop.field)">删除</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>