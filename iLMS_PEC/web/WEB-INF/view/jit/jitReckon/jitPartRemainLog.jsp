<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动推算控制台-零件余量修改日志查询</title>
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
							<li><span>零件编号:</span><input class="inputText" type="text" name="partNo"></li>
							<li><span>零件简号:</span><input class="inputText" type="text" name="partShortNo"></li>
							<li><span>操作时间:</span><input class="inputText datetime" name="opeTimeFrom"></li>	
							<li><span>至:</span><input class="inputText datetime" name="opeTimeTo"></li>				
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		queryJitVehQueuePage();
	});
	
	//零件余量修改日志查询
	function queryJitVehQueuePage(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitReckon/queryJitPartRemainLogPage",
			striped : true,
			fitColumns : false,
	        columns: [[
       		   { field: '',sortName:"", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: '',sortName:"", title: '信息点',width : 150, align: 'center', sortable: 'true'},
               { field: 'location',sortName:"location", title: '落点',width : 150, align: 'center', sortable: 'true'},
               { field: 'partNo',sortName:"partNo" , title: '零件编号',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '零件简号',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '零件名称',width : 150, align: 'center', sortable: 'true'},
               { field: 'partRemainOld',sortName:"partRemainOld" , title: '修改前余量',width : 150, align: 'center', sortable: 'true'},
               { field: 'partRemainNew',sortName:"partRemainNew" , title: '修改后余量',width : 150, align: 'center', sortable: 'true'},
               { field: 'opeModifyNum',sortName:"opeModifyNum" , title: '手工调整量',width : 150, align: 'center', sortable: 'true'},
               { field: 'safetyInventory',sortName:"safetyInventory" , title: '安全库存',width : 150, align: 'center', sortable: 'true'},
               { field: 'endRequireDate',sortName:"endRequireDate" , title: '截止日期',width : 150, align: 'center', sortable: 'true'},
               { field: 'endRequireNum',sortName:"endRequireNum" , title: '截止所需零件量',width : 150, align: 'center', sortable: 'true'},
               { field: 'opeUser',sortName:"opeUser" , title: '操作人',width : 150, align: 'center', sortable: 'true'},
               { field: 'opeIp',sortName:"opeIp" , title: '操作IP',width : 150, align: 'center', sortable: 'true'},
               { field: 'opeTime',sortName:"opeTime" , title: '操作时间',width : 150, align: 'center', sortable: 'true'}
	        ]]
		}));
	}
	
	//零件余量修改日志查询导出excel
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitReckon/downloadJitPartRemainLogPage');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>