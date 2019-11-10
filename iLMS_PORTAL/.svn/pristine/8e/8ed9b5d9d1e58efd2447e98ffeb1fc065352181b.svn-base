<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>不良品申请</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
<script>


</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" onclick="findOut()">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add"  onclick="openDetail('','','','CbAdd')">
						<span>材不添加</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add"  onclick="openDetail('','','','JbAdd')">
						<span>加不添加</span>
					</a>
					<a class="btn btn-sm btn-primary fa-remove"  action="/dpm/ins/remove">
						<span>删除</span>
					</a>
					<a class="btn btn-sm btn-primary fa-check"  onclick="submitFn('1')" >
						<span>提交</span>
					</a>
					<a class="btn btn-sm btn-primary fa-print" href="javascript:void(0)" onclick="Print()">
						<span>打印</span>
					</a>
					<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()" >
						<span>Excel导出</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add"  onclick="respFun()">
						<span>责任反馈</span>
					</a>
					<a class="btn btn-sm btn-primary fa-remove"  onclick="submitFn('4')">
						<span>转为材不</span>
					</a>
					<a class="btn btn-sm btn-primary fa-check"  onclick= "ExcepOrderFn('5')">
						<span>生成紧急例外订单</span>
					</a>
					<a class="btn btn-sm btn-primary fa-check"  onclick="submitFn('2')">
						<span>审核通过</span>
					</a>
					<a class="btn btn-sm btn-primary fa-remove"  onclick="submitFn('3')">
						<span>驳回</span>
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
						<li><span>申请单号:</span>
						<input class="inputText" name="applyNo"/>
						</li>
						<li><span>零件编号:</span>
						<input class="inputText" name="partNo"/>
						</li>
						<li><span>零件简号:</span>
						<input class="inputText" name="partShortNo"/>
						</li>
						<li><span>供应商代码:</span>
						<input class="inputText" name="supplierNo"/>
						</li>
						<li><span>填单日期:</span>
						<input class="inputText datetime" name="insDateStart"/>
						</li>
					    <li><span>至:</span>
					    <input class="inputText datetime" type="text" name="insDateEnd">
					    </li>
					    <li><span>不良品项目:</span>
						<select id="seaDpmCode" class="inputText" name="dpmCode"></select>
						</li>
						<li><span>发现区域:</span>
						<select id="seaDpmDesc" class="inputText" name="discoArea"></select>
						</li>
						<li><span>状态:</span>
						<select id="seaInsStatus" class="inputText" name="insStatus"></select>
						</li>
						<li><span>打印状态:</span>
						<select id="seaPrintStatus" class="inputText" name="printStatus"></select>
						</li>
						<li><span>不良品类型:</span>
						<select id="seaDpmType" class="inputText" name="dpmType"></select>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		//发现区域下拉框
 		HtUtil.loadComboBox({
 			url:__ctx+"/dpm/area/getUnloadCode",
			dictArr:[{
				el : '#seaDpmDesc',
				addBlank : true
			}]
		  }); 
		
 		//不良品项目下拉框
	 	HtUtil.loadComboBox({
	 		url:__ctx+"/dpm/ins/getUnloadDpmCode",
			dictArr:[{
				el : '#seaDpmCode',
				addBlank : true
			}]
		 }); 
		
 		//状态下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey :'DPM_DOCUMENT_STATUS',
				el : '#seaInsStatus',
				addBlank : true
			}]
		});
		
 		//打印状态下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'PUB_PRINT_STATUS',
				el : '#seaPrintStatus',
				addBlank : true
			}]
		});
 		
	 	//不良品类型下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'DPM_TYPE',
				el : '#seaDpmType',
				addBlank : true
			}]
		});
	 	
	 	 loadGrid(null);
	});
	
	function findOut(){
		var onclickUrl = encodeURI(__ctx + "/dpm/ins/curdlistJson");
		loadGrid(onclickUrl);
	}
		
 	function submitFn(submit){
 		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var applyNoArr = [];
		for (var i = 0; i < records.length; i++) {
			applyNoArr.push(records[i].applyNo);
		}
 		var msg=null;
 		if(submit == '1'){
 			msg = "确认提交";
 		}
 		if(submit == '2'){
 		    msg = "确认审核通过？";
 		}
 		if(submit == '3'){
 			msg = "确认驳回？";
 		}
 		if(submit == '4'){
 			msg = "确认转为材料不良？"
 		}
 		if(submit == '5'){
 			msg = "确认生成例外订单？"
 		}
		   $.messager.confirm('提示',msg,function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/dpm/ins/submit",
		    	    data: {
		    	        'submit' : submit,
		    	        'applyNoArr' : applyNoArr,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				$('#grid').reload();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    }); 
 	  }
 	
 	function ExcepOrderFn(){
 		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var applyNoArr = [];
		for (var i = 0; i < records.length; i++) {
			applyNoArr.push(records[i].applyNo);
		}
		$.messager.confirm('提示',"确认生成例外订单？",function(r){
		    if (r){
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/dpm/ins/excepOrder",
		    	    data: {
		    	        'applyNoArr' : applyNoArr,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#grid').datagrid('reload');
		    			if(data.result == '1'){
		    				$.messager.alert("操作提示", data.message,"info");
		    				loadGrid();
		    			}else{
		    				$.messager.alert("操作提示", data.message,"error");
		    	    }
		    	 }
		      });
		    } 
	    }); 
 		
 	}

	
	function openDetail(applyNo,insStatus,dpmType, action) {
		if(insStatus != "未提交" && action == "edit"){
			$.messager.alert("操作提示", "只能修改未提交数据，该条数据无法修改","info");
			return;
		}
		var url = __ctx + "/dpm/ins/dpmIns";
		if(!$.isEmpty(applyNo)){
		   if(dpmType == "材料不良"){
		     url = __ctx + "/dpm/ins/dpmIns" + 'Cb';
		   }
		   if(dpmType == "加工不良"){
			 url = __ctx + "/dpm/ins/dpmIns" + 'Jb';
		   }
		}
		var title = action == "edit" ? "编辑" : action == "CbAdd" ? "材不添加" :   action == "JbAdd" ?"加不添加" : "查看";
		action = action == "edit" ? "Edit" : action == "CbAdd" ? "CbEdit" : action == "JbAdd" ? "JbEdit" : "Get";
		url+= action + "";
		if(!$.isEmpty(applyNo)){
			url+='?applyNo=' + applyNo ;
		}
		HT.window.openEdit(url, title, action, 'grid', 650, 450, null, null, applyNo, false);
	}

	
	 function loadGrid(onclickUrl) {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  onclickUrl ,
			idField : "applyNo",
			sortName : 'applyNo',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false, 
			columns : [ [
			{field : '',sortName : "",checkbox : true},     
			{field : 'dpmType',sortName : "dpmType",title : '不良品类型',width : 80,align : 'center',sortable : 'true'}, 
			{field : 'applyNo',sortName : "applyNo",title : '申请单号',width : 100,align : 'center',sortable : 'true'},
			{field : 'insStatus',sortName : "insStatus",title : '状态',width : 80,align : 'center',sortable : 'true'},
			{field : 'insDate',sortName : "insDate",title : '填单时间',width : 80,align : 'center',sortable : 'true'},
			{field : 'creator',sortName : "creator",title : '申请人',width : 80,align : 'center',sortable : 'true'},
			{field : 'applyDep',sortName : "applyDep",title : '申请科室',width : 80,align : 'center',sortable : 'true'}, 
			{field : 'partNo',sortName : "partNo",title : '零件编号',width : 80,align : 'center',sortable : 'true'},
			{field : 'partShortNo',sortName : "partShortNo",title : '零件简号',width : 80,align : 'center',sortable : 'true'},
			{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',width : 80,align : 'center',sortable : 'true'},
			{field : 'dpmNum',sortName : "dpmNum",title : '不良品数量',width : 80,align : 'center',sortable : 'true'},
			{field : 'discoArea',sortName : "discoArea",title : '发现区域',width : 80,align : 'center',sortable : 'true'},
			{field : 'dpmCode',sortName : "dpmCode",title : '不良品项目',width : 80,align : 'center',sortable : 'true'},
			{field : 'dpmDesc',sortName : "dpmDesc",title : '不良描述',width : 80,align : 'center',sortable : 'true'},
			{field : 'respDep',sortName : "respDep",title : '责任组',width : 80,align : 'center',sortable : 'true'},
			{field : 'modelCode',modelCode : "depName",title : '车型',width : 80,align : 'center',sortable : 'true'},
			{field : 'dealResult',sortName : "dealResult",title : '处理结果',width : 80,align : 'center',sortable : 'true'},
			{field : 'printStatus',sortName : "printStatus",title : '打印状态',width : 80,align : 'center',sortable : 'true'},
			{field : 'supplierNo',sortName : "supplierNo",title : '供应商代码',width : 80,align : 'center',sortable : 'true'},
			{field : 'supplierName',sortName : "supplierName",title : '供应商名称',width : 80,align : 'center',sortable : 'true'},
			{field : 'contact',sortName : "contact",title : '供应商担当',width : 80,align : 'center',sortable : 'true'},
			{field : 'telNo',sortName : "telNo",title : '供应商联系方式',width : 80,align : 'center',sortable : 'true'},
			{field : 'isUrgent',sortName : "isUrgent",title : '是否紧急',width : 80,align : 'center',sortable : 'true'},
			{field : 'orderStatus',sortName : "orderStatus",title : '订单生成状态',width : 80,align : 'center',sortable : 'true'},
			{field : 'excepStatus',sortName : "execpStatus",title : '例外订单生成状态',width : 80,align : 'center',sortable : 'true'},
			{field : 'remark',sortName : "remark",title : '备注',width : 80,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\"" + row.applyNo + "\",\""+row.insStatus+"\",\""+row.dpmType+"\",\"edit\");' herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.applyNo + "\",\""+row.insStatus+"\",\""+row.dpmType+"\",\"get\");' herf='javascript:void(0)'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/dpm/ins/remove?applyNo=" + row.applyNo + "' herf='javascript:void(0)'>删除</a>";
				    
					return result;
				}
			} ] ],
			autoScroll: true,
			onLoadSuccess : function(data) {
				if(data.total==0){
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
	
	 //Excel导出
	 function excelExport(){
			var downurl = encodeURI(__ctx + '/dpm/ins/downloadDpmInsModel');
			$('#downloadiframe').attr('src', downurl);
		}
	 
	 //打印
	 function Print(){
		 var datagrid = $('#grid');
			if (null == datagrid || datagrid.length < 0){
				return false;
			}
			var records = datagrid.datagrid('getChecked');
			if (records == null || records.length < 1) {
				alert("没有选择需要打印的数据，请确认！");
				return false;
			}
			var applyNoArr = [];
			for (var i = 0; i < records.length; i++) {
				if("审核通过" != records[i].insStatus){
					$.messager.alert("操作提示", "只能打印审核通过数据，请确认","info");
					return;
				}
				applyNoArr.push(records[i].applyNo);
			}
			var downurl =  __ctx+"/dpm/ins/print?applyNoArr=" + applyNoArr;
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
	 
	 //责任反馈
	 function respFun(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records=datagrid.datagrid('getChecked');
		if (records .length < 1 ) {
			$.messager.alert("操作提示", "未选择数据","info");
			return false;
		}
		if(records.length > 1){
			$.messager.alert("操作提示", "每次只能选择一条数据","info");
			return false;
		}
		if(records[0].insStatus != '已提交'){
			$.messager.alert("操作提示", "请选择已提交信息进行责任反馈","info");
			return false;
		}
		var applyNo = records[0].applyNo;
		var url = __ctx + "/dpm/ins/dpmRespback?applyNo="+applyNo;
		HT.window.openEdit(url, "责任反馈", "", 'grid', 650, 450, null, null, applyNo, false);
		 
	 }
	
</script>
