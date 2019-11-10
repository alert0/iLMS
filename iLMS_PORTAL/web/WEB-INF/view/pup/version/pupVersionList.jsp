<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>版本比对</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="topPanel" class="easyui-panel" style="width:100%;height:100px;padding:10px;background:#fafafa;"   
		        data-options="region:'north',split:true""> 
        	<form style="margin-top:10px;" id="importForm" method="post" enctype="multipart/form-data">
			    <p>
				    <div class="toolbar-body">
						<table class="table-form" cellspacing="0">
							<tr>
								<th>
									<span style="font-size:12pt;font-weight:normal"><font color="red">*</font>选择文件:</span>
								</th>
								<td>
									<input type="file" style="width:300px" size="60" name="file" id="file" accept=".xls,.xlsx" class="inputText input-wh-9" />
								</td>
								<td>
									<a style="margin-left:5px" class="btn btn-primary btn-sm fa-sign-in"  href="javascript:void(0)" onclick="downloadTemplateFn()">
										<span>模板下载</span>
									</a>
								</td>
							</tr>
						</table>	
					</div>
			    </p>
			    <p style="margin-left:85%">
			    	<a class="btn btn-sm btn-primary fa-sign-in" href="javascript:void(0)" onclick="importFileFn()" >
						<span>确定</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-times" onclick="clearFileInput()">
			       		<span>取消</span>
			       	</a>
			    </p>
			</form>
		</div>
		<div data-options="region:'center'">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>版本对比</span>
					</a>
			        <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
			       		<span>重置</span>
			       	</a>
			       	<a id="signOut" class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()"> 
						<span>批量导出</span>
			 	   	</a> 
			       	<a id="chooseVersion" class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick=""> 
						<span>版本选择</span>
			 	   	</a> 
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class="bigger-190 fa  fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul style="margin-left: -26px">
						<li><span>集货线路</span>
							<input id="routeCode" class="inputText" type="text" name="routeCode">
						</li>
						<li><span>车型</span>
							<input id="carType" class="inputText" type="text" name="carType" onkeyup="value=value.replace(/[^\a-z\A-Z0-9\,\，]/g,'')">
						</li>
						<li><span>差异标识</span>
							<select id="diffFlag" class="inputText" type="text" name="diffFlag"></select>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
		
		HtUtil.loadComboBox({
			url:__ctx+"/pup/version/getDiffFlag",
			dictArr:[{
				el : '#diffFlag',
				addBlank : true
			}]
		});
		$("#signOut").attr("disabled",false);
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "queryVesion",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'true'},
			    {field : 'area',sortName : "AREA",title : '区域',align : 'center',sortable : 'true'},
			    {field : 'unloadPlace',sortName : "UNLOAD_PLACE",title : '卸货地点',align : 'center',sortable : 'true'},
			    {field : 'pickupType',sortName : "PICKUP_TYPE",title : '订单物流模式',align : 'center',sortable : 'true'},
				{field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',align : 'center',sortable : 'true'},
				{field : 'totalNo',sortName : "TOTAL_NO",title : '累计车次',align : 'center',sortable : 'true'},
				{field : 'mergeNo',sortName : "MERGE_NO",title : '合并车次',align : 'center',sortable : 'true'},
				{field : 'workday',sortName : "WORKDAY",title : '新工作日',align : 'center',sortable : 'true'},
				{field : 'workday',sortName : "WORKDAY",title : '原工作日',align : 'center',sortable : 'true'},
				{field : 'workday',sortName : "WORKDAY",title : '工作日差异',align : 'center',sortable : 'true'},
				{field : 'todayNo',sortName : "TODAY_NO",title : '当日车次',align : 'center',sortable : 'true'},
				{field : 'pickDate',sortName : "PICK_DATE",title : '计划取货日期',align : 'center',sortable : 'true'},
				{field : 'pickTime',sortName : "PICK_DATE",title : '计划取货时间',align : 'center',sortable : 'true'},
				{field : 'arriveDate',sortName : "ARRIVE_DATE",title : '计划到货日期',align : 'center',sortable : 'true'},
				{field : 'arriveTime',sortName : "ARRIVE_DATE",title : '计划到货时间',align : 'center',sortable : 'true'},
				{field : 'assembleDate',sortName : "ASSEMBLE_DATE",title : '计划装配日期',align : 'center',sortable : 'true'},
				{field : 'assembleDate',sortName : "ASSEMBLE_DATE",title : '原计划装配日期',align : 'center',sortable : 'true'},
				{field : 'assembleTime',sortName : "ASSEMBLE_DATE",title : '计划装配时间',align : 'center',sortable : 'true'},
				{field : 'assembleTime',sortName : "ASSEMBLE_DATE",title : '原计划装配时间',align : 'center',sortable : 'true'}
				]],
		    onLoadSuccess : function (data) {
			    if(data.total==0){
					$("#signOut").attr("disabled",false);
                    var dc = $(this).data('datagrid').dc;
                    var header2Row = dc.header2.find('tr.datagrid-header-row');
                    dc.body2.find('table').append(header2Row.clone().css({
                    	"visibility":"hidden"
                    }));
				}else{
					$("#signOut").attr("disabled",true);
				}
			}
		}));
	}
	
	function clearFileInput() {
        var file = $("#file");
        file.after(file.clone().val(""));
        file.remove();
    } 
	
	function excelExport(){
		if($("#signOut").attr("disabled")){
			return;
		}
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
		    	var downurl = encodeURI(__ctx + '/pup/version/exportQueryData');
				$('#downloadiframe').attr('src', downurl);		
		    }else{
		    	HT.window.closeEdit(true,'grid');
		    }
		});	
	}
	
	var isimport = false;
	function importFileFn(){
		var frm = $('#importForm').form();
		var file = $("#file").val();
		if (file == "") {
			$.topCall.warn(htmsg.pub.notFindFileWarn);
			return;
		}
		frm.ajaxForm({
			success : function(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				var msg = JSON.parse(resultMessage.getMessage());
				var showMsg = msg['log'] == null || msg['log'] == '' ? msg['console'] : msg['log'];
				var flag = msg['flag'];
				showMsg = showMsg == null || showMsg == '' ? htmsg.pub.importSuccess : showMsg;
				if (resultMessage.isSuccess()) {
					$.topCall.closeProgress();
					loadGrid();
					$.topCall.success(showMsg, function() {
						if(flag == 1){
							isimport = true;
						}
					});
				}else if (resultMessage.getResult() == 0) {
					$.topCall.closeProgress();
					$.topCall.error(showMsg);
				} else {
					$.topCall.closeProgress();
					$.topCall.confirm("提示",showMsg,
						function(r) {
							if (!r) return;
							$('#importForm').attr("action", __ctx + "/pup/version/importVersion");
							$('#importForm').submit();
						}
					);
				}
			}
		});
		$.topCall.progress();
		$('#importForm').attr("action", __ctx + "/pup/version/importVersion");
		$('#importForm').submit();
	}
	function downloadTemplateFn(){
		var downurl = encodeURI(encodeURI(__ctx + '/comm/comm/downloadExcelModel?fileName=已取货数据导入模版.xlsx'));
		$('#downloadiframe').attr('src', downurl);
	}
</script>