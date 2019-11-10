<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>配送单查询</title>
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
							<span>配送单打印</span>
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
							<li><span>零件编号:</span><input class="inputText" type="text" name="partNo"></li>
							<li><span>零件简号:</span><input class="inputText" type="text" name="partShortNo"></li>
							<li><span>配送工程:</span><input class="inputText" type="text" name="distriPerson"></li>
							<li><span>备件批次:</span><input class="inputText" type="text" name="prepareBatchNo" 
									onkeyup="this.value=this.value.replace(/\D/g,'')"></li>
							<li><span>至:</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" class="inputText" type="text" name="prepareBatchNoTo"></li>
							<li><span>是否急件:</span>
								<select id="trueFalse" class="inputText" name="isDispatch"></select>
							</li>
							<li><span>物流单号:</span><input class="inputText" type="text" name="orderNo"></li>
							<li><span>打印机:</span>
								<select id="prPrinter" class="inputText" name="prPrinter"></select>
							</li>
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
				typeKey : 'PUB_TRUE_FALSE',
				el : '#trueFalse',
				addBlank : true
			},{
				typeKey : 'MM_PR_PRINTER',
				el : '#prPrinter',
				addBlank : true
			},{
				typeKey : 'PUB_PRINT_STATUS',
				el : '#printStatus',
				addBlank : true
			}]
		});
		
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
				el : '#prPrinter', 
				addBlank : true
			}]
		});
		
		loadGrid();
		
	});
	
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitIns/queryJitInsPage",
			striped : true,
			fitColumns : false,
	        columns: [[
	           {field : 'A',sortName : "A",checkbox : true},
	           {
	   				field : 'colManage',
	   				title : '操作',
	   				align : 'center',
	   				formatter : function(value, row, index) {
	   					var result = "<a class='btn btn-default fa' onClick='openDetail(\""+ row.orderNo +"\",\""  + row.insNo
	   							+ "\", \""+ row.prepareBatchNo +"\" ,\""+ row.shipDepot +"\");' >明细</a>";
	   					return result;
	   				}
	   			},
	   		   { field: 'insNo',sortName:"insNo", title: '配送单号',width : 150, align: 'center', sortable: 'true', hidden: true},
	   		   { field: 'prepareBatchNo',sortName:"prepareBatchNo", title: '备件批次',width : 150, align: 'center', sortable: 'true', hidden: true},
       		   { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
               { field: 'shipDepot',sortName:"shipDepot", title: '出货仓库',width : 150, align: 'center', sortable: 'true'},
               { field: 'orderNo',sortName:"orderNo" , title: '物流单号',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '是否急件',width : 150, align: 'center', sortable: 'true'},
               { field: 'prepareProductSeqno',sortName:"prepareProductSeqno" , title: '备件批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'deliveryProductSeqno',sortName:"deliveryProductSeqno" , title: '发货批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'arriveProductSeqno',sortName:"arriveProductSeqno" , title: '到货批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'distriProductSeqno',sortName:"distriProductSeqno" , title: '配送批次进度',width : 150, align: 'center', sortable: 'true'},
               { field: 'preparePerson',sortName:"preparePerson" , title: '拣货工程',width : 150, align: 'center', sortable: 'true'},
               { field: 'distriPerson',sortName:"distriPerson" , title: '配送工程',width : 150, align: 'center', sortable: 'true'},
               { field: 'carpool',sortName:"carpool" , title: '台车编号',width : 150, align: 'center', sortable: 'true'},
               { field: 'printStatus',sortName:"printStatus" , title: '打印状态',width : 150, align: 'center', sortable: 'true'},
               { field: 'printTime',sortName:"printTime" , title: '打印时间',width : 150, align: 'center', sortable: 'true'},
               { field: '',sortName:"" , title: '打印机',width : 150, align: 'center', sortable: 'true'},
               { field: 'creationTime',sortName:"creationTime" , title: '创建时间',width : 150,hidden: true}
               ]]
		}));
	}
	
	
	//配送单导出
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitIns/downloadJitIns');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//查看配送单明细 (物流单号,配送单号,备件批次,仓库代码)
	function openDetail(orderNo, insNo, prepareBatchNo, shipDepot){
		var title = "订单明细";
		var url = "jitInsDetail";
		if(!$.isEmpty(orderNo)){
			url += '?orderNo=' + orderNo;
			url += '&insNo=' + insNo;
			url += '&prepareBatchNo=' + prepareBatchNo;
			url += '&shipDepot=' + shipDepot;
		}
		HT.window.openEdit(url, title, "Get", 'grid', 400, 300, null, null, orderNo, true);
	}
	
	//配送单打印
	function print(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要打印的配送单，请确认！");
			return false;
		}
		var insNoArr = [];
		for (var i = 0; i < records.length; i++) {
			insNoArr.push(records[i].insNo);
		}
		var insNo = insNoArr.join(",");
		var downurl =  __ctx+"/jit/jitIns/jitInsPrint?insNo=" + insNo;
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