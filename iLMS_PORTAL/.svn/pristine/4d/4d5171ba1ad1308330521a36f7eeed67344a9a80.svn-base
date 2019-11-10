<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/query/queryViewShow.js"></script>
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/jquery-ui.css">
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/ui.jqgrid.css">
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.jqGrid.src.js"></script>
<script type="text/javascript">
	$(function() {
		jqGridInit();
		//重新构建jqGrid大小
		resizeJqGrid();
		$("#btnSearch").click(function() {
			search();
		});
		jQuery("#gridList").jqGrid('setFrozenColumns');
	});
</script>
</head>
<body>
	<!-- 顶部查询操作 -->
	<div id="panelTop" class="toolbar-search ">
		<div class="toolbar-box border">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-primary  btn-sm fa-search" id="btnSearch" href="javaScript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class=" fa fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<!-- 查询字段 -->
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul>
						<li class="mar-l-20">
							<span>FULLNAME_:</span>
							<input class="inputText ipu-w-170" type="text" name="Q^FULLNAME_">
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div style="margin-left: 10px">
		<table id="gridList"></table>
		<div id="pagerNav"></div>
	</div>
</body>

<!--下面放的是jqgrid的初始化代码 -->
<script type="text/javascript">
	function jqGridInit() {
		jQuery("#gridList").jqGrid({
			url : __ctx + '/system/query/queryView/data_cs/cs.ht',
			datatype : "json",
			postData : __param,
			jsonReader : {
				root : "rows",// json中代表实际模型数据的入口
				total : "total", // json中代表总页数的数据
				page : "page", // json中代表当前页码的数据
				records : "records",// json中代表数据行总数的数据
				// 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
				repeatitems : false
			},
			colNames : [ "FULLNAME_","STATUS_","SEX_","ztxnl" ],
			colModel : [ {
				label : "FULLNAME_",
				name : "FULLNAME_",
				index : 'FULLNAME_',
				sortable : false,
				frozen : true,
				width : 100,
				align : "center"
			}, {
				label : "STATUS_",
				name : "STATUS_",
				index : 'STATUS_',
				sortable : false,
				frozen : false,
				align : "center",
			}, {
				label : "SEX_",
				name : "SEX_",
				index : 'SEX_',
				sortable : false,
				frozen : false,
				align : "center"
			}, {
				label : "状态虚拟列",
				name : "ztxnl",
				index : 'ztxnl',
				sortable : false,
				frozen : false,
				align : "center",
			}

			],
			rowNum : 15,
			rowList : [ 5, 10, 15, 20, 30 ],
			pager : '#pagerNav',
			prmNames : {
				page : "page",
				rows : "pageSize",
				sort : "sortField",
				order : "orderSeq",
				search : "initSearch"
			},
			sortname : 'FULLNAME_',
			sortorder : "asc",
			mtype : "post",
			viewrecords : true,
			grouping : true,
			groupingView : {
				groupField : [ 'FULLNAME_' ]
			},
			 height: 'auto'
		});
	}
</script>
</html>
