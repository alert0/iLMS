<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>DEMO</title>
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
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="openDetail('','add')">
						<span>添加</span>
					</a>
					 <a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/demo/demo/remove">
						<span>删除</span>
					</a>

					<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport()" >
						<span>Excel导出</span>
					</a>
					<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)" onclick="excelExport2()" >
						<span>Excel导出(大数量)</span>
					</a>
					
					<a class="btn btn-primary btn-sm fa-sign-in"  href="javascript:void(0)" onclick="openExcelImport()">
						<span>Excel导入</span>
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
						<li><span>字段一:</span><input class="inputText" type="text" name="Q^COL1^SL"></li>
						<li><span>字段二:</span><input class="inputText" type="text" name="Q^COL2^SL"></li>
						<li><span>字段三:</span>
						<select class="inputText" name="Q^COL3^S">
						    <option value=""> </option>
						  <option value="0">0</option>
						   <option value="1">1</option>
						</select>
						 </li>
						<li><span>字段四(下拉框):</span>
							<select id="searchFormCol4" class="inputText" defaultValue="0" name="Q^COL4^S"></select>
						 </li>
						 <li><span>字段五(下拉框):</span>
							<select id="searchFormCol5" class="inputText" name="COL"></select>
						 </li>
						 <li><span>字段六(下拉框):</span>
							<select id="searchFormCol6" class="inputText" name="COL"></select>
						 </li>
						 <li><span>字段七(下拉框):</span>
							<select id="searchFormCol7" class="inputText" name="COL"></select>
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
		loadGrid();
		
		//数据字典下拉框
		HtUtil.loadComboBoxByDict({
			dictArr:[{
				typeKey:'YES_NO',
				el : '#searchFormCol4',
				addBlank : true
			},{
				typeKey:'w',
				el : '#searchFormCol5',
				addBlank : false
			}]
		});
		
		//业务数据下拉框
		/* HtUtil.loadComboBox({
			url : __ctx + '/demo/demo/loadComboData',
			param : {
				test : 'abc'
			},
			valueKey : 'valueKey1',
			textKey : 'valueName1',
			dictArr:[{
				typeKey:'YES_NO',
				el : '#searchFormCol6', 
				addBlank : true 
			},{
				typeKey:'YES_NO2',
				el : '#searchFormCol7', 
				addBlank : true 
			}]
		}); */
		
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑" : action == "add" ? "添加" : "查看";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = __ctx + "/demo/demo01/democurd" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 400, 300, null, null, id, false);
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/demo/demo/curdlistJson",
			idField : "pkId",
			sortName : 'pk_Id',
			sortOrder : 'desc',
			columns : [ [
			{field : 'pkId',sortName : "pkId",checkbox : true},     
			{field : 'col1',sortName : "col1",title : '字段一',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'col2',sortName : "col2",title : '字段二',width : 130,align : 'center',sortable : 'true'},
			{field : 'col3',sortName : "col3",title : '字段三',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'col4',sortName : "col4",title : '字段四',width : 130,align : 'center',sortable : 'true'}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+ row.pkId +"\",\"edit\");' herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.pkId + "\",\"get\");' herf='javascript:void(0)'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/user/remove?id=" + row.pkId + "' herf='javascript:void(0)'>删除</a>";
					
					return result;
				}
			} ] ]
		}));
	}
	
	function openExcelImport(){
		var url = __ctx + "/demo/demo01/democurdExcelImport";
		HT.window.openEdit(url, '导入', null, 'grid', 500, 600, null, null, null, true);
	}
	
	function excelExport(){
		var downurl = encodeURI(__ctx + '/demo/demo/downloadDemoModel');
		$('#downloadiframe').attr('src', downurl);
	}
	
	function excelExport2(){
		var downurl = encodeURI(__ctx + '/demo/demo/downloadDemoModel2');
		$('#downloadiframe').attr('src', downurl);
	}
	
	
</script>
