<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>固定取货时间维护</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						         <span>搜索</span>
					          </a>
					          <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						         <span>重置</span>
					          </a>
					          <a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:;" onclick="openDetail('-1','add')">
						         <span>新增</span>
					          </a>
					          <a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" id="remove" action="/pup/pickupTime/remove">
						         <span>删除</span>
					          </a>
					           <a class="btn btn-primary btn-sm fa-sign-in"  
							    href="javascript:void(0)" onclick="openExcelImport()">
						         <span>批量导入</span>
					           </a>
					          <a class="btn btn-sm btn-primary fa-sign-out"
								href="javascript:void(0)" onclick="excelExport()"> 
								<span>导出</span>
							  </a> 
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
								<li><span>集货线路</span>
									<input id="routeCode" class="inputText" type="text" name="routeCode">
								</li>
							</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>	
	$(function() {
		loadGrid();
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "routeCode",
			sortName : 'ROUTE_CODE',
			sortOrder : 'desc',
			striped : true,
			columns : [[
				{field : 'id',checkbox : true},
				{field : 'colManage',  title: '操作',  align: 'center'
                	,formatter:function(value,row,index){
			           var result = "<a id='edit' class='btn btn-default fa fa-edit' onClick='openDetail("+index+",\"edit\")' herf='#'>编辑</a>";
				       return result;
			         }
                },
				{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',width : 250,align : 'center',sortable : 'true'},
			    {field : 'todayNo',sortName : "TODAY_NO",title : '当日车次',width : 250,align : 'center',sortable : 'true'},
			    {field : 'pickTime',sortName : "PICK_TIME",title : '零件名称',width : 250,align : 'center',sortable : 'true'},
				{field : 'arriveTime',sortName : "ARRIVE_TIME",title : '到货时间',width : 250,align : 'center',sortable : 'true'}
			    ]],
			    onLoadSuccess:function(data){  
			        $(".btn btn-default fa fa-edit").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});  
			    }
		}));
	}
	
	function openDetail(index, action) {
		if(index >= 0){
			$("#grid").datagrid('selectRow',index);
			var row = $('#grid').datagrid('getSelected');
			var routeCode = row["routeCode"];
		}
		
		var title = action == "edit" ? "编辑取货信息" : action == "add" ? "添加取货信息" : "查看取货信息详情";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="pupPickupTime" + action;
		if(!$.isEmpty(routeCode)){
			url+='?routeCode=' + routeCode;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, routeCode, false);
	}
	
	function openExcelImport(){
		var url = __ctx + "/pup/pickupTime/pupPickTimeImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/pup/pickupTime/downloadPickTimeModel');
		$('#downloadiframe').attr('src', downurl);
	}
</script>
