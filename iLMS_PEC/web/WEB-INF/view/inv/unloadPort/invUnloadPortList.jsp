<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>卸货口管理</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a id="query" class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>查询</span>
					</a>
			        <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
			       		<span>重置</span>
			       	</a>
			       	<a id="signOut" class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()"> 
						<span>导出</span>
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
						<li><span>工作中心</span>
							<select id="workCenter" class="inputText" type="text" name="workCenter"></select>
						</li>
						<li><span>卸货口</span>
							<input id="unloadPort" class="inputText" type="text" name="unloadPort">
						</li>
						<li><span>是否有P链</span>
							<select id="plFlag" class="inputText" type="text" name="plFlag"></select>
						</li>
						<li><span>物流模式</span>
							<select id="logisticsMode" class="inputText" type="text" name="logisticsMode"></select>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
		
		//数据字典下拉框加载P链有无
		HtUtil.loadComboBox({
			url:__ctx+"/inv/unloadPort/getPL",
			dictArr:[{
				el : '#plFlag',
				addBlank : true
			}]
		});
		
		//数据字典下拉框加载工作中心
		HtUtil.loadComboBox({
			url:__ctx+"/inv/unloadPort/getWorkCenter",
			dictArr:[{
				el : '#workCenter',
				addBlank : true
			}]
		});
		
		//数据字典下拉框加载物流模式
		HtUtil.loadComboBox({
			url:__ctx+"/inv/unloadPort/getPickType",
			dictArr:[{
				el : '#logisticsMode',
				addBlank : true
			}]
		});
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			queryParams: {
					flag : '1'
			},
			url : "queryUnloadPort",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'false'},
			    {field : 'workCenter',sortName : "WORK_CENTER",title : '车间',width : 200,align : 'center',sortable : 'true'},
			    {field : 'unloadPort',sortName : "UNLOAD_PORT",title : '卸货口',width : 200,align : 'center',sortable : 'true'},
			    {field : 'note',sortName : "NOTE",title : '备注',align : 'center',width : 200,sortable : 'true'},
				{field : 'plFlag',sortName : "IS_PL",title : '是否有P链',align : 'center',width : 80,sortable : 'true'},
				{field : 'logisticsMode',sortName : "IS_PL",title : '物流模式',align : 'center',width : 200,sortable : 'true'},
				{field : 'wareType',sortName : "WARE_TYPE",title : '类别',align : 'center',width : 200,sortable : 'true'}
				]],
		    onLoadSuccess : function (data) {
			    if(data.total==0){
                    var dc = $(this).data('datagrid').dc;
                    var header2Row = dc.header2.find('tr.datagrid-header-row');
                    dc.body2.find('table').append(header2Row.clone().css({
                    	"visibility":"hidden"
                    }));
				}
			}
		}));
	}
	
	function excelExport(){
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
		    	var downurl = encodeURI(__ctx + '/inv/unloadPort/exportForExcel');
				$('#downloadiframe').attr('src', downurl);		
		    }else{
		    	HT.window.closeEdit(true,'grid');
		    }
		});
	}
</script>