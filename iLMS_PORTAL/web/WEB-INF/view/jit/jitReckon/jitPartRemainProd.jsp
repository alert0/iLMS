<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动推算控制台-截止产品编号零件余量</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search">
							<span>查询</span>
						</a>
						<a class="btn btn-sm btn-primary fa-sign-out"
							onclick="excelExport()"><span>Excel导出</span>
						</a> 
						<a class="btn btn-sm btn-primary fa-rotate-left">
							<span>重置</span>
						</a>
					</div>
					<div class="tools">
						<a class="collapse">
							<i class="bigger-190 fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>信息点:</span><input class="inputText" name="planCode"></li>
							<li><span>VIN:</span><input class="inputText" name="vin"></li>
							<li><span>零件编号:</span><input class="inputText" name="partNo"></li>	
							<li><span>零件简号:</span><input class="inputText" name="partShortNo"></li>				
							<li><span>落点:</span><input class="inputText" name="location"></li>								
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
	<!-- 下载模板的框架 -->
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function(){
		queryJitPartRemainProdPage();
	})
	
	//截止产品编号零件余量
	function queryJitPartRemainProdPage(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitReckon/queryJitPartRemainProdPage",
			striped : true,
			fitColumns : false,
	        columns: [[
       		   { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
			   { field: '',sortName:"", title: 'VIN',width : 150, align: 'center', sortable: 'true'},
               { field: 'location',sortName:"location", title: '落点',width : 150, align: 'center', sortable: 'true'},
               { field: 'partNo',sortName:"partNo" , title: '零件编号',width : 150, align: 'center', sortable: 'true'},
               { field: 'partShortNo',sortName:"partShortNo" , title: '零件简号',width : 150, align: 'center', sortable: 'true'},
               { field: 'partNameCn',sortName:"partNameCn" , title: '零件名称',width : 150, align: 'center', sortable: 'true'},
               { field: 'endRemain',sortName:"endRemain" , title: '零件余量',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '计划过点批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '计划过点时间',width : 150, align: 'center', sortable: 'true'}
	        ]]
		}));
	}
	
	//截止产品编号零件余量导出excel
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitReckon/downloadJitpartRemainProdPage');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>
