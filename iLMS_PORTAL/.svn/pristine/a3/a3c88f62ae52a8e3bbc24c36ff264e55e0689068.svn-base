<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/getController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/Dialogs.js"></script>
 
<script type="text/javascript">
	var id = "${param.id}";
	var paramValueString = "${param.paramValueString}";
	var selectNum = "${param.selectNum}";
	//处理收缩、展开
	$(function() {
		var h = 60 + $('.toolbar-body').offset().top;
		$('#toolbar-search').height(h + "px");
		var $obj = $('.toolbar-box .tools .collapse, .toolbar-box .tools .expand');
		$obj.unbind('click');

		//收缩、展开
		$obj.bind("click", function() {
			var me = $(this);
			var el = me.parents(".toolbar-box").children(".toolbar-body");
			if (me.hasClass("collapse")) {
				me.attr("title", "展开");
				me.removeClass("collapse").addClass("expand");
				var i = me.children(".fa-angle-double-up");
				i.removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
				me.parents('.toolbar-box').addClass("toolbar-border-bottom");
				el.slideUp(200);
				$('#toolbar-search').height('60px');
			} else {
				me.attr("title", "收缩");
				me.removeClass("expand").addClass("collapse");
				var i = me.children(".fa-angle-double-down");
				i.removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
				me.parents('.toolbar-box').removeClass("toolbar-border-bottom");
				el.slideDown(200);
				$('#toolbar-search').height(h + "px");
			}
		});
	});
</script>
<script type="text/javascript">
	function getResult() {
		var scope = $("body").scope();
		if (scope.prop.style == "0") {
			return scope.getResult();
		} else if (scope.prop.style == "1") {
			return scope.getTreeResult();
		}
	}
</script>

<style>
.search-form ul>li:first-child {
	padding-left: 21px;
}
</style>
</head>
<body ng-app="app" ng-controller="Controller">
	<div id="cc" class="easyui-layout" data-options="fit:true">
		<div region="center" data-options="fit:true">
			<div ng-if="showSearch!=false" region="north" style="height: 120px;" collapsible="false">
				<div class="toolbar-search col-md-13 ">
					<div class="toolbar-box border">
						<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons">
								<a class="btn btn-primary fa-search" href="javaScript:void(0)">
									<span ng-click="search()">搜索</span>
								</a>
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
									<i class=" fa  fa-angle-double-up"></i>
								</a>
							</div>
						</div>
						<div class="toolbar-body">
							<form id="searchForm" class="search-form">
								<ul>
									<li ng-repeat="column in prop.conditionfield" ng-if="column.defaultType=='1'">
										{{column.comment}}:
										<!-- 日期类控件 -->
										<input ng-if="column.dbType=='date'" type="text" validate="{date:true}" class="inputText wdateTime" ng-model="column.value" name="date" ht-date="wdateTime" />
										<nobr ng-if="column.dbType=='date'&&column.condition=='BETWEEN'">
											至
											<input type="text" ng-model="column.endDate" validate="{date:true}" class="inputText wdateTime" name="date" ht-date="wdateTime" />
										</nobr>
										<!-- 其他非日期类的控件-->
										<input class="inputText" type="text" ng-if="column.dbType!='date'" ng-model="column.value" />
										<a href="javascript:;" class="btn btn-default fa fa-folder-open" ng-if="column.dbType!='date'&&column.controllerType!='1'" ng-click="showCt(column);">选择</a>
									</li>
								</ul>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div region="center">
				<table class="table-grid">
					<tr>
						<th width="20px">
							<a ng-if="prop.selectNum!=1" href="javascript:;" ng-click="addPageToSelectList()" class="fa fa-sitemap">全选</a>
						</th>
						<th ng-repeat="column in prop.displayfield">
							<span>{{column.comment}}</span>
						</th>
					</tr>
					<tr ng-repeat="row in page.rows" ng-click="addToSelectList(row,$index);">
						<td>
							<input type="radio" name="radio" id="radio{{$index}}" />
						</td>
						<td ng-repeat="r in row.displayRow">{{r.value}}</td>
					</tr>
				</table>
				<!-- 分页 -->
				<span ng-if="page.pageResult!=null">
					<a href="javascript:void(0)" class="l-btn l-btn-plain" ng-click="search(1)">
						<span class="l-btn-left">
							<span class="l-btn-text">
								<span class="l-btn-empty pagination-first">&nbsp;</span>
							</span>
						</span>
					</a>
					<a href="javascript:void(0)" class="l-btn l-btn-plain" ng-click="search(page.pageResult.prePage)">
						<span class="l-btn-left">
							<span class="l-btn-text">
								<span class="l-btn-empty pagination-prev">&nbsp;</span>
							</span>
						</span>
					</a>
					|
					<span style="padding-left: 6px;">第</span>
					<input class="pagination-num" ng-model="page.pageResult.page" type="text" size="2" ng-keydown="pageChangePress($event,page.pageResult.page);">
					<span style="padding-right: 6px;">共{{page.pageResult.totalPages}}页</span>
					|
					<a href="javascript:void(0)" class="l-btn l-btn-plain" ng-click="search(page.pageResult.nextPage)">
						<span class="l-btn-left">
							<span class="l-btn-text">
								<span class="l-btn-empty pagination-next">&nbsp;</span>
							</span>
						</span>
					</a>
					<a href="javascript:void(0)" class="l-btn l-btn-plain" ng-click="search(page.pageResult.totalPages)">
						<span class="l-btn-left">
							<span class="l-btn-text">
								<span class="l-btn-empty pagination-last">&nbsp;</span>
							</span>
						</span>
					</a>
					|
					<a href="javascript:void(0)" class="l-btn l-btn-plain" ng-click="search(page.pageResult.page)">
						<span class="l-btn-left">
							<span class="l-btn-text">
								<span class="l-btn-empty pagination-load">&nbsp;</span>
							</span>
						</span>
					</a>
				</span>
			</div>
		</div>
		<!-- 显示已选 -->
		<div region="east" title="选中数据" split="true" style="width: 200px;">
			<!--显示第一个字段信息 -->
			<table class="table-grid">
				<tr>
					<th>
						<span>{{prop.displayfield[nameKeyIndex].comment}}</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<tr ng-repeat="row in selectedList">
					<td>{{row.displayRow[nameKeyIndex].value}}</td>
					<td>
						<a href="javascript:;" ng-click="ArrayTool.del($index,selectedList)" class="btn btn-sm btn-danger fa fa-times"></a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="ztree" class="ztree"></div>
</body>
</html>