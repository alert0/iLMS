<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/controller/separatorList.js"></script>
<title>联动设置</title>

<script type="text/javascript">
	function getResult() {
		var scope = AngularUtil.getScope();
		if(!scope.form.$valid){
			$.topCall.alert("温馨提示","页面校验不通过！");
			return "validError";
		}
		return parseToJson(scope.separators);
	}
</script>

</head>
<body ng-controller="ctrl">
	 <div>
	 <div class="toolbar-search ">
		 <div class="toolbar-head">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-add" ng-click="addSeparator()"> <span>添加</span></a> 
			</div>
		 </div>
	 </div>
	 <form id="form" name="form" >
		<table cellpadding="1" cellspacing="1" border="1" style="border-collapse:collapse;" class="table-list">
			<thead>
				<tr>
					<th width="280px">分组名称</th>
					<th width="80px">显示标题</th>
					<th width="80px">默认展开</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="separator in separators track by $index">
					<td>
						<input class="form-control" ng-model="separator.name" ht-validate="{required:true}" type="text" />
					</td>
					<td style="text-align: center;">
						<input ng-model="separator.isShow" type="checkbox" checked="checked"/>
					</td>
					<td style="text-align: center;">
						<input ng-model="separator.isOpen" type="checkbox" checked="checked"/>
					</td>
					<td style="text-align: center;">
						<input class="form-control" ng-model="separator.key" ng-hide="true" ng-init="separator.key = separator.key!=''?separator.key:$index+1"  type="text"/>
						<a href="javascript:javaScript:void(0)" ng-if="separator.key>0" ng-click="ArrayTool.up($index,separators)" class="btn btn-sm btn-default fa-chevron-up"></a>
						<a href="javascript:javaScript:void(0)" ng-if="separator.key>0" ng-click="ArrayTool.down($index,separators)" class="btn btn-sm btn-default fa-chevron-down"></a>
						<a class="btn btn-default fa fa-delete" ng-if="separator.key>0" title="移除" ng-click="removeSeparator(separator)"></a>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div> 
</body>
</html>