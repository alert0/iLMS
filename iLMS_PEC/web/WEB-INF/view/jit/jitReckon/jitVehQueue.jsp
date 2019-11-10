<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动推算控制台-过点车序</title>
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
							<li><span>过点时间:</span><input class="inputText datetime" type="text" name="passTimeFrom"></li>
							<li><span>至:</span><input class="inputText datetime" name="passTimeTo"></li>	
							<li><span>排序码:</span><input class="inputText" name="sortidFrom" onkeyup="this.value=this.value.replace(/\D/g,'')"></li>				
							<li><span>到:</span><input class="inputText" name="sortidTo" onkeyup="this.value=this.value.replace(/\D/g,'')"></li>								
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
	<!-- 下载模板的框架 -->
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		queryJitVehQueuePage();
	});
	
	//过点车序查询
	function queryJitVehQueuePage(){
		var planCode = $('#planCode', parent.document).val();
		/* if($.isEmpty(planCode)){
			$.topCall.error("信息点不能为空！");
			return;
		} */
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitReckon/queryJitVehQueuePage",
			striped : true,
			fitColumns : false,
	        columns: [[
       		   { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
               { field: 'orderNo',sortName:"orderNo", title: '订单号',width : 150, align: 'center', sortable: 'true'},
               { field: 'vin',sortName:"vin" , title: 'VIN',width : 150, align: 'center', sortable: 'true'},
               { field: 'sortid',sortName:"sortid" , title: '计划排序号',width : 150, align: 'center', sortable: 'true'},
               { field: 'kbProductSeqno',sortName:"kbProductSeqno" , title: '车辆流水号',width : 150, align: 'center', sortable: 'true'},
               { field: 'passTime',sortName:"passTime" , title: '过点时间',width : 150, align: 'center', sortable: 'true'},
               { field: 'execTime',sortName:"execTime" , title: '推算时间',width : 150, align: 'center', sortable: 'true'},
               { field: 'execStatus',sortName:"execStatus" , title: '推算状态',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '计划过点批次进度',width : 150, align: 'center', sortable: 'true'}
	        ]]
		}));
	}
	
	//过点车序导出excel
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitReckon/downloadJitVehQueuePage');
		$('#downloadiframe').attr('src', downurl);
	}
	
</script>