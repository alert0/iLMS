<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>锁定取货计划维护</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
					         <span>查询</span>
				          </a>
				          <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
					         <span>重置</span>
				          </a>
				           <a id="remove" class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" id="remove" action="/pup/route/removeRouteMessages">
					         <span>删除</span>
				          </a>
				           <a class="btn btn-primary btn-sm fa-sign-in"  
						    href="javascript:void(0)" onclick="openExcelImport()">
					         <span>批量导入</span>
				           </a>
				          <a id="signOut" class="btn btn-sm btn-primary fa-sign-out"
							href="javascript:void(0)" onclick="excelExport()"> 
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
						<li><span>区域</span>
							<input id="area" class="inputText" type="text" name="area">
						</li>
						<li><span>卸货地点</span>
							<input id="unloadPlace" class="inputText" type="text" name="unloadPlace">
						</li>
						<li><span>计算队列</span>
							<input id="unloadPort" class="inputText" type="text" name="unloadPort">
						</li>
						<li><span>集货线路</span>
							<input id="routeCode" class="inputText" type="text" name="routeCode">
						</li>
					</ul>
					<ul style="margin-left: -26px">
						<li><span>供应商代码</span>
							<input id="supplierNo" class="inputText" type="text" name="supplierNo">
						</li>
						<li><span>订单物流模式</span>
							<input id="pickupType" class="inputText" type="text" name="pickupType">
						</li>
						<li><span>车型</span>
							<input id="carType" class="inputText" type="text" name="carType" onkeyup="value=value.replace(/[^\a-z\A-Z0-9\,\，]/g,'')">
						</li>
						<li><span>是否批次取货</span>
							<select id="batch" class="inputText" type="text" name="batch"></select>
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
				
		HtUtil.loadComboBox({
			url:__ctx+"/pup/route/getBatch",
			dictArr:[{
				el : '#batch',
				addBlank : true
			}]
		});
	});
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listRouteMessage",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [[
				{field : 'id',checkbox : 'true'},
				{field : 'colManage',  title: '操作',  align: 'center'
                	,formatter:function(value,row,index){
			           var result = "<a id='edit' class='btn btn-default fa fa-edit' onClick='openDetail("+index+",\"edit\")' herf='#'>修改</a>";
				       return result;
			         }
                },
			    {field : 'area',sortName : "AREA",title : '区域',align : 'center',sortable : 'true'},
			    {field : 'routeDist',sortName : "ROUTE_DIST",title : '路线区分',align : 'center',sortable : 'true'},
			    {field : 'unloadPlace',sortName : "UNLOAD_PLACE",title : '卸货地点',align : 'center',sortable : 'true'},
				{field : 'supplierNo',sortName : "SUPPLIER_NO",title : '供应商代码',align : 'center',sortable : 'true'},
				{field : 'supFactory',sortName : "SUP_FACTORY",title : '出货地',align : 'center',sortable : 'true'},
				{field : 'supplierName',sortName : "SUPPLIER_NAME",title : '供应商名字',align : 'center',sortable : 'true'},
				{field : 'unloadPort',sortName : "UNLOAD_PORT",title : '计算队列',align : 'center',sortable : 'true'},
				{field : 'carType',sortName : "CAR_TYPE",title : '车型',align : 'center',sortable : 'true'},
				{field : 'supCalNum',sortName : "SUP_CAL_NUM",title : '台套数',align : 'center',sortable : 'true'},
				{field : 'wareCode',sortName : "WARE_CODE",title : '仓库代码',align : 'center',sortable : 'true'},
				{field : 'pickupType',sortName : "PICKUP_TYPE",title : '订单物流模式',align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "ROUTE_CODE",title : '集货线路',align : 'center',sortable : 'true'},
				{field : 'routeName',sortName : "ROUTE_NAME",title : '路线名称',align : 'center',sortable : 'true'},
				{field : 'locDepth',sortName : "LOC_DEPTH",title : '最早落点',align : 'center',sortable : 'true'},
				{field : 'retEmptyPlatform',sortName : "RET_EMPTY_PLATFORM",title : '返空站台',align : 'center',sortable : 'true'},
				{field : 'advanceArrNum',sortName : "ADVANCE_ARR_NUM",title : '提前台套数',align : 'center',sortable : 'true'},
				{field : 'firstArriveTime',sortName : "FIRST_ARRIVE_TIME",title : '最早到货时间',align : 'center',sortable : 'true'},
				{field : 'speArriveTime',sortName : "SPE_ARRIVE_TIME",title : '特殊到货时间',align : 'center',sortable : 'true'},
				{field : 'transTime',sortName : "TRANS_TIME",title : '运输时间',align : 'center',sortable : 'true'},
				{field : 'recShiftA',sortName : "REC_SHIFT_A",title : 'A班收货',align : 'center',sortable : 'true'},
				{field : 'recShiftB',sortName : "REC_SHIFT_B",title : 'B班收货',align : 'center',sortable : 'true'},
				{field : 'wwlManager',sortName : "WWL_MANAGER",title : '外物流管理员',align : 'center',sortable : 'true'},
				{field : 'nwlManager',sortName : "NWL_MANAGER",title : '内物流管理员',align : 'center',sortable : 'true'},
				{field : 'mergeNum',sortName : "MERGE_NUM",title : '合并基准',align : 'center',sortable : 'true'},
				{field : 'pickCycle',sortName : "PICK_CYCLE",title : '取货周期',align : 'center',sortable : 'true'},
				{field : 'supOutTime',sortName : "SUP_OUT_TIME",title : '供应商出货时间',align : 'center',sortable : 'true'},
				{field : 'batch',sortName : "BATCH",title : '是否批次取货',align : 'center',sortable : 'true'
					,formatter:function(value,row,index){
						if('0' == value){
							return '否';
						}else if('1' == value){
							return '是';
						}
						return value;
					 }		
				},
				{field : 'startSortId',sortName : "START_SORT_ID",title : '供应商分组计算起始SORTID',align : 'center',sortable : 'true'},
				{field : 'departTimePoint',sortName : "DEPART_TIME_POINT",title : '发车时间基准',align : 'center',sortable : 'true'}
				]],
		    onLoadSuccess : function (data) {
			    if(data.total==0){
					$("#remove").attr('disabled',true);
					$("#signOut").attr('disabled',true);
                    var dc = $(this).data('datagrid').dc;
                    var header2Row = dc.header2.find('tr.datagrid-header-row');
                    dc.body2.find('table').append(header2Row.clone().css({
                    	"visibility":"hidden"
                    }));
				}else{
					$("#remove").attr('disabled',false);
					$("#signOut").attr('disabled',false);
				}
			}
		}));
	}
	
	function openExcelImport(){
		var url = __ctx + "/pup/route/pupRouteImport";
		HT.window.openEdit(url, '导入', null, 'grid', 400, 300, null, null, null, true);
	}
	
	function excelExport(){
		if($("#signOut").attr("disabled")){
			return;
		}
		$.messager.confirm('提示信息',"确认导出数据?" ,function(r){
			if(r){
		    	var downurl = encodeURI(__ctx + '/pup/route/exportQueryData');
				$('#downloadiframe').attr('src', downurl);		
		    }else{
		    	HT.window.closeEdit(true,'grid');
		    }
		});	
	}	
	function openDetail(index, action) {
		if(index >= 0){
			$("#grid").datagrid('selectRow',index);
			var row = $('#grid').datagrid('getSelected');
			var id = row["id"];
		}
		
		var title = action == "edit" ? "编辑路线信息" : action == "add" ? "添加路线信息" : "查看路线信息详情";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="pupRoute" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 800, 500, null, null, routeCode, false);
	}
</script>