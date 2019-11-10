<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>厂外同步推算控制台-过点车序</title>
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
							<li><span>VIN:</span><input class="inputText" type="text" name="vin"></li>
							<li><span>车身序号:</span><input class="inputText" type="text" name="wcSeqno"></li>
							<li><span>PA OFF日期:</span><input class="inputText datetime" name="passTimeStart"></li>	
							<li><span>至:</span><input class="inputText datetime" name="passTimeEnd"></li>								
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
	$(function() {
		loadGrid();
	});
	
	//过点车序
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jiso/jisoReckon/queryJisoVehQueuePage",
			striped : true,
			fitColumns : false,
	        columns: [[
	        		{ field: 'passTime',sortName:"passTime", title: 'PA OFF时间',width : 150, align: 'center', sortable: 'true'},
					{ field: 'vin',sortName:"vin", title: 'VIN',width : 150, align: 'center', sortable: 'true'},
	                { field: 'modelCode',sortName:"modelCode", title: '车型',width : 150, align: 'center', sortable: 'true'},
	                { field: 'phase',sortName:"phase" , title: '阶段',width : 150, align: 'center', sortable: 'true'},
	                { field: 'wcSeqno',sortName:"wcSeqno" , title: '车身序号',width : 150, align: 'center', sortable: 'true'},
	                { field: 'groupInsState',sortName:"groupInsState" , title: '组票状态',width : 150, align: 'center', sortable: 'true'},
	                { field: 'groupOrderState',sortName:"groupOrderState" , title: '组单状态',width : 150, align: 'center', sortable: 'true'},
	                { field: 'kbProductSeqno',sortName:"kbProductSeqno" , title: '计划下线批次进度',width : 150, align: 'center', sortable: 'true'}
	        ]]
		}));
	}
	
	//过点车序导出excel
	function excelExport(){
		$.messager.confirm('确认','是否确认导出excel',function(r){    
		    if (r){
				var downurl = encodeURI(__ctx + '/jiso/jisoReckon/downloadJisoVehQueuePage');
				$('#downloadiframe').attr('src', downurl);
		    }    
		});
	}
	
</script>
