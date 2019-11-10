<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>零件标签查询</title>
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
							onclick="excelExport()"><span>导出</span>
						</a>
						<a class="btn btn-sm btn-primary fa-rotate-left">
							<span>重置</span>
						</a>
						<a class="btn btn-sm btn-primary fa-print" onclick="print()">
							<span>标签打印</span>
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
							<li><span>信息点:</span>
								<select id="planCode" class="inputText" name="planCode"></select>							
							</li>
							<li><span>出货仓库:</span>
								<select id="shipDepot" class="inputText" name="shipDepot"></select>
							</li>
							<li><span>零件编号:</span><input class="inputText" type="text" name="partNo"></li>
							<li><span>零件简号:</span><input class="inputText" type="text" name="partShortNo"></li>
							<li><span>备件批次:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="prepareBatchNo"></li>
							<li><span>至:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="prepareBatchNoTo"></li>
							<li><span>计划过点批次:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="kbBatchNo"></li>
							<li><span>物流单号:</span><input class="inputText" type="text" name="orderNo"></li>
							<li><span>打印状态:</span>
								<select id="printStatus" class="inputText" name="printStatus"></select>
							</li>
							<li><span>创建日期:</span><input class="inputText datetime" type="text" name="creationTimeFrom"></li>
							<li><span>至:</span><input class="inputText datetime" name="creationTimeTo"></li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		//初始化下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [{
				typeKey : 'PUB_WORKCENTER',
				el : '#workcenter',
				addBlank : true
			},{
				typeKey : 'PUB_SHIP_DEPOT',
				el : '#shipDepot',
				addBlank : true
			},{
				typeKey : 'PUB_PRINT_STATUS',
				el : '#printStatus',
				addBlank : true
			}]
		});
		loadGrid();
		
		//信息点
		HtUtil.loadComboBox({
			url : __ctx + '/jit/jitReckon/loadPlanCodeComboData',
			param : {
				planCodeType : 'JITI'
			},
			valueKey : 'valueKey',
			textKey : 'valueName',
			dictArr:[{
				typeKey:'PLAN_CODE',
				el : '#planCode', 
				addBlank : true
			}]
		});
		
	});
	
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitLabel/queryJitLabelPage",
			striped : true,
			fitColumns : false,
	        columns: [[
	           {field : 'A',sortName : "A",checkbox : true},
	           { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
	           { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
	           { field: 'shipDepot',sortName:"shipDepot", title: '出货仓库',width : 150, align: 'center', sortable: 'true'},
	           { field: 'orderNo',sortName:"orderNo" , title: '物流单号',width : 150, align: 'center', sortable: 'true'},
	           { field: 'labelRowno',sortName:"labelRowno" , title: '物流单号',width : 150, align: 'center', sortable: 'true',hidden: true},
	           { field: 'partNo',sortName:"partNo" , title: '零件编号',width : 150, align: 'center', sortable: 'true'},
	           { field: 'partShortNo',sortName:"partShortNo" , title: '零件简号',width : 150, align: 'center', sortable: 'true'},
	           { field: 'partName',sortName:"partName" , title: '零件名称',width : 150, align: 'center', sortable: 'true'},
	           { field: 'location',sortName:"location" , title: '落点',width : 150, align: 'center', sortable: 'true'},
	           { field: 'prepareBatchNo',sortName:"prepareBatchNo" , title: '备件批次进度',width : 150, align: 'center', sortable: 'true'},
	           { field: 'deliveryProductSeqno',sortName:"deliveryProductSeqno" , title: '发货批次进度',width : 150, align: 'center', sortable: 'true'},
	           { field: 'arriveProductSeqno',sortName:"arriveProductSeqno" , title: '到货批次进度',width : 150, align: 'center', sortable: 'true'},
	           { field: 'distriProductSeqno',sortName:"distriProductSeqno" , title: '配送批次进度',width : 150, align: 'center', sortable: 'true'},
	           { field: 'kbBatchNo',sortName:"kbBatchNo" , title: '计划过点批次进度',width : 150, align: 'center', sortable: 'true'},
	           { field: 'standardPackage',sortName:"standardPackage" , title: '规格包装数',width : 150, align: 'center', sortable: 'true'},
	           { field: 'distriPerson',sortName:"distriPerson" , title: '配送工程',width : 150, align: 'center', sortable: 'true'},
	           { field: 'unloadCode',sortName:"unloadCode" , title: '卸货口',width : 150, align: 'center', sortable: 'true'},
	           { field: 'supplierNo',sortName:"supplierNo" , title: '供应商代码',width : 150, align: 'center', sortable: 'true'},
	           { field: '',sortName:"" , title: '看板名称',width : 150, align: 'center', sortable: 'true'},
	           { field: 'printStatus',sortName:"printStatus" , title: '打印状态',width : 150, align: 'center', sortable: 'true'},
	           { field: 'printTime',sortName:"printTime" , title: '打印时间',width : 150, align: 'center', sortable: 'true'},
	           { field: 'creationTime',sortName:"creationTime" , title: '创建时间',width : 150, align: 'center', sortable: 'true'}
              ]]
		}));
	}
	
	//零件标签导出
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitLabel/downloadJitLabel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//订单打印
	function print(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要打印的订单，请确认！");
			return false;
		}
		var orderNoArr = [];
		var labelRownoArr = [];
		for (var i = 0; i < records.length; i++) {
			orderNoArr.push(records[i].orderNo);
			labelRownoArr.push(records[i].labelRowno);
		}
		var orderNo = orderNoArr.join(",");
		var labelRowno = labelRownoArr.join(",");
		var downurl =  __ctx+"/jit/jitLabel/jitLabelPrint?orderNo=" + orderNo + "&labelRowno=" + labelRowno;
		//var downurl =  __ctx+"/jit/jitLabel/jitLabelPrint?orderNo=" + orderNo;
		var def = {
	      title : "打印",
	      width : 800,
	      height : 500,
	      modal : true,
	      resizable : true,
	      buttons : []
	    };  

	    dialog = $.topCall.dialog({
	      src : downurl,
	      base : def
	    });
	}
</script>