<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>供应商能力反馈查询</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false"
			style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">

				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)"
						onclick="findOut()"> 
							<span>搜索</span>
						</a> <a href="javascript:;"
							class="btn btn-sm btn-primary fa-rotate-left"> <span>重置</span>
						</a> <a href="javascript:;" class="btn btn-sm btn-primary fa-check"
							onclick="getCommit(id)"> <span>提交</span>
						</a> <a class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport()"> <span>Excel导出</span>
						</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"> <i
							class="bigger-190 fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>供应商代码:</span> <input id="supplierNo"
								class="inputText" type="text" name="supplierNo"></li>
							<li><span>出货地:</span> <input id="supFactory"
								class="inputText" type="text" name="supFactory"></li>
							<li><span>；零件编号:</span> <input id="partNo" class="inputText"
								type="text" name="partNo"></li>
							<li><span>供应日期:</span> <input name="supplyDateStrStart"
								class="inputText datetime" /></li>
							<li><span>至: </span> <input name="supplyDateStrEnd"
								class="inputText datetime" /></li>
							<li><span>是否可供应:</span> <select id="isSupply"
								class="inputText" name="isSupply"></select></li>
						</ul>
					</form>
				</div>

			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<!-- 下载模板的框架 -->
		<iframe id="downloadiframe" style="display: none;"></iframe>
		<div id="dd" class="easyui-dialog" title="供应商能力反馈"
			style="width: 500px; height: 300px; text-align: center"
			data-options="iconCls:'icon-save',resizable:true,modal:false,closed:true">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data"
						onclick="sendCommit()"><span>提交</span></a> <a
						class="btn btn-primary fa-back"
						onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>

			<table class="table-form" cellspacing="0">
				<tr>
					<th>是否供应:</th>
					<td><select id="isSupplySecond" class="inputText" type="text"
						ht-validate="{required:false,maxlength:20} "></select></td>
				</tr>
				<tr>
					<th>供应原因:</th>
					<td><input id="supplyReason" class="easyui-textbox"
						data-options="multiline:true"
						value="库存不足，无法供应"
						style="width: 300px; height: 100px"> </td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		StartOut();
		$('#dd').dialog('close');

		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [ {
				typeKey : 'PUB_TRUE_FLASE',
				el : '#isSupply',
				addBlank : true
			} ]
		});

		//数据字典下拉框
		HtUtil.loadComboBoxJsonByDict({
			dictArr : [ {
				typeKey : 'PUB_TRUE_FLASE',
				el : '#isSupplySecond',
				addBlank : true
			} ]
		});
	});

	/*初始化界面*/
	function StartOut(){
		var onclickUrl = encodeURI(__ctx+"/pub/pubWorkCalendar/startJson");
		loadGrid(onclickUrl);
	}
	
	function findOut(){
		var onclickUrl = encodeURI(__ctx + "/mp/mpAdjSupFeedback/curdlistJson");
		loadGrid(onclickUrl);
	}
	
	function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions, {
			url : onclickUrl ,
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [ 
				{field : 'id',sortName : "ID",checkbox : true}, 
				{field : 'partNo',sortName : "PART_NO",title : '零件编号',
				width : 100,align : 'center',sortable : 'true'}, 
				{field : 'partShortNo',sortName : "PART_SHORT_NO",title : '零件简号',
				width : 100,align : 'center',sortable : 'true'},
				{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',
				width : 100,align : 'center',sortable : 'true'}, 
				{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',
				width : 100,align : 'center',sortable : 'true'},
				{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名称',
				width : 200,align : 'center',sortable : 'true'},
				{field : 'diffNum',sortName : "DIFF_NUM",title : '差异数量',
				width : 100,align : 'center',sortable : 'true'}, 
				{field : 'isSupply',sortName : "IS_SUPPLY",title : '是否可供应',
				width : 100,align : 'center',sortable : 'true'},
				{field : 'partNameCn',sortName : "PART_NAME_CN",title : '零件名称',
				width : 100,align : 'center',sortable : 'true'},
				{field : 'supplyReason',sortName : "SUPPLY_REASON",title : '供应原因',
				width : 200,align : 'center',sortable : 'true'}, 
				{field : 'feedbackTimeStr',sortName : "FEEDBACK_TIME",title : '反馈时间',
				width : 200,align : 'center',sortable : 'true'} 
				] ],
			onLoadSuccess : function(data) {
				/*无数据时加载滚动条*/
				if(data.total==0)
				{
				var dc = $(this).data('datagrid').dc;
				var header2Row = dc.header2.find('tr.datagrid-header-row');
				dc.body2.find('table').append(header2Row.clone().css({
					"visibility":"hidden"
				}));
				}
				handGridLoadSuccess();
			}
		}));
	}

	function excelExport() {
		var downurl = encodeURI(__ctx
				+ '/mp/mpAdjSupFeedback/downloadMpAdjSupFeedbackModel');
		$('#downloadiframe').attr('src', downurl);
	}

	function excelExport2() {
		var downurl = encodeURI(__ctx
				+ '/mp/mpAdjSupFeedback/downloadMpAdjSupFeedbackModel2');
		$('#downloadiframe').attr('src', downurl);
	}

	function getCommit(id) {
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "请选择至少一条记录", "error");
			return false;
		}
		$('#dd').dialog('open');

	}

	function sendCommit() {
		//var rows = $('#grid').datagrid('getChecked');
		/* console.log(rows); */
			//var idArr = [];
	//	for (var i = 0; i <= rows.length; i++) {
		//	idArr.push(rows[i].partNo);
			/* var item = rows[i];
			var str = JSON.stringify(item);
			
			//去除str 前后的双引号
			var str1 = $.parseJSON(str);
			idArr.push(str1.id);
			/* console.log(idArr); */
		//}
		var rows = $('#grid');
		var records = rows.datagrid('getChecked');
		var planCodeArr = [];
		for (var i = 0; i < records.length; i++) {
			planCodeArr.push(records[i].id);
		}
		var isSupplySecond = $('#isSupplySecond').val();
		var supplyReason = $('#supplyReason').val();
		var getCommitUrl = encodeURI(__ctx + '/mp/mpAdjSupFeedback/getCommit');
		$.messager.confirm('提示', '确定要提交么？', function(r) {
			if (r) {
				$.ajax({
					url : getCommitUrl,
					type : 'post',
					data : {
						"isSupply" : isSupplySecond,
						"supplyReason" : supplyReason,
						"id" : planCodeArr
					},
					traditional: true,
					dataType : 'json',
					success : function(data) {
						console.log(data);
						var info = $.parseJSON(data);
						if (info.result == '1') {
							$.messager.alert("操作提示", info.message, "info");
						} else {
							$.messager.alert("操作提示", info.message, "error");
						}
						$(this).addClass("done");
					}
				});
			} else {
				return;
			}
		});
	}
</script>
