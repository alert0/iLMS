<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动推算控制台-当前零件余量</title>
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
						<a class="btn btn-sm btn-primary fa-add" id="add" onclick="openDetail('','add')">
							<span>添加</span>
						</a>
						<a class="btn btn-sm btn-primary fa-sign-out"
							onclick="excelExport()"><span>Excel导出</span>
						</a>
						<a class="btn btn-primary btn-sm fa-sign-in" onclick="openExcelImport()">
							<span>Excel批量导入</span>
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
							<li><span>出货仓库:</span><input class="inputText" name="shipDepot"></li>
							<li><span>零件编号:</span><input class="inputText" name="partNo"></li>	
							<li><span>零件简号:</span><input class="inputText" name="partShortNo"></li>				
							<li><span>仅显示使用中的落点:</span><input type="checkbox" name="isSelectLocation"></li>								
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
		queryJitPartRemainPage();
	});
	
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑零件余量" : action == "add" ? "添加零件余量" : "查看当前零件余量";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = "jitPartRemain" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 400, 300, null, null, id, false);
	}
	
	//当前零件余量
	function queryJitPartRemainPage(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitReckon/queryJitPartRemainPage",
			striped : true,
			fitColumns : false,
	        columns: [[
	           {field : 'id',sortName : "id",checkbox : true},
	           {
	   				field : 'colManage',
	   				title : '操作',
	   				align : 'center',
	   				formatter : function(value, row, index) {
	   					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+ row.id +"\",\"edit\");' herf='javascript:void(0)'>编辑</a>";
	   					
	   					return result;
	   				}
	   			},
       		   { field: 'workcenter',sortName:"workcenter", title: '车间',width : 150, align: 'center', sortable: 'true'},
			   { field: 'planCode',sortName:"planCode", title: '信息点',width : 150, align: 'center', sortable: 'true'},
               { field: 'location',sortName:"location", title: '落点',width : 150, align: 'center', sortable: 'true'},
               { field: 'partNo',sortName:"partNo" , title: '零件编号',width : 150, align: 'center', sortable: 'true'},
               { field: 'partShortNo',sortName:"partShortNo" , title: '零件简号',width : 150, align: 'center', sortable: 'true'},
               { field: 'partNameCn',sortName:"partNameCn" , title: '零件名称',width : 150, align: 'center', sortable: 'true'},
               { field: 'partRemain',sortName:"partRemain" , title: '零件余量',width : 150, align: 'center', sortable: 'true'},
               { field: 'safetyInventory',sortName:"safetyInventory" , title: '安全库存',width : 150, align: 'center', sortable: 'true'},
               { field: 'shipDepot',sortName:"shipDepot" , title: '出货仓库',width : 150, align: 'center', sortable: 'true'},
               { field: 'endRequireDate',sortName:"endRequireDate" , title: '截止日期',width : 150, align: 'center', sortable: 'true'},
               { field: 'endRequireNum',sortName:"endRequireNum" , title: '截止所需需求量',width : 150, align: 'center', sortable: 'true'}
               ]]
		}));
	}
	
	//当前零件余量导出excel
	function excelExport(){
		var downurl = encodeURI(__ctx + '/jit/jitReckon/downloadJitpartRemainPage');
		$('#downloadiframe').attr('src', downurl);
	}
	
	//当前零件余量批量导入excel
	function openExcelImport(){
		var url = __ctx + "/jit/jitReckon/jitPartRemainExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 500, 600, null, null, null, true);
	}
	
</script>