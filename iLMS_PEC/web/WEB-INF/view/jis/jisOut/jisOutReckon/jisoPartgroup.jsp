<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>厂外同步推算控制台-待组票净需求</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'north',split:true" style="height: 100px;background-color: #F5F5F5;">
			<div id="" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search">
							<span>查询</span>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>零件组:</span><select id="partgroup" class="inputText" name="partgroupNo"></select></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="partGroup_grid" class="my-easyui-datagrid"></div>
		</div> 
		<div data-options="region:'east',split:true" style="width:50%;">
		    <div id="remain_grid"  class="my-easyui-datagrid"></div>
		</div> 
	</div>
</body>
</html>
<script>
	$(function(){
		//厂外同步零件组
		HtUtil.loadComboBox({
			url : __ctx + '/jiso/jisoReckon/queryPartgroupComboData',
			valueKey : 'valueKey',
			textKey : 'valueName',
			dictArr:[{
				typeKey:'JISO_PARTGROP',
				el : '#partgroup', 
				addBlank : true 
			}]
		});
		loadGrid();
	})
	
	//零件数组列表
	function loadGrid() {
		$('#partGroup_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jiso/jisoReckon/queryJisoPartGroupPage",
			toolbar : [{
				text: '+手工组票',
				iconCls: 'icon-edit',
				handler: manuDealBill
			}],
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : 'id',sortName : "id",checkbox : true},     
				{field : 'partgroupNo',sortName : "partgroupNo",title : '零件组代码',width : 150,align : 'center',sortable : 'true'}, 
				{field : 'partgroupName',sortName : "partgroupName",title : '零件组名称',width : 150,align : 'center',sortable : 'true'},
				{field : 'insProductNum',sortName : "insProductNum",title : '组票台套数',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'nextGroupInsWay',sortName : "nextGroupInsWay",title : '下一个组票方式',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqTime',sortName : "manuReqTime",title : '手工组票请求时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'dealTime',sortName : "dealTime",title : '手工组票时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqUser',sortName : "manuReqUser",title : '手工组票操作人',width : 130,align : 'center',sortable : 'true'}
			 ] ],
			 onClickRow : function(index,row){
				 loadRemianGrid(index,row);
			 }
		}));
		
	}
	
	//未组票零件净需求
	function loadRemianGrid(index,row){
		var planCode = row.planCode;
		var partgroupNo = row.partgroupNo;
		$('#remain_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jiso/jisoReckon/queryRemainByPartgroupNo",
			toolbar: null,
			queryParams: {
				'planCode': planCode,
				'partgroupNo' : partgroupNo
		    },
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : 'vin',sortName : "vin",title : 'VIN',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'wcSeqno',sortName : "wcSeqno",title : '车身序号',width : 130,align : 'center',sortable : 'true'},
				{field : 'passTime',sortName : "passTime",title : 'PA OFF时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'supFactory',sortName : "supFactory",title : '出货地代码',width : 130,align : 'center',sortable : 'true'},
				{field : 'supFactory',sortName : "supFactory",title : '到货仓库',width : 130,align : 'center',sortable : 'true'},
				{field : 'partNo',sortName : "partNo",title : '零件编号',width : 130,align : 'center',sortable : 'true'},
				{field : 'partShortNo',sortName : "partShortNo",title : '零件简号',width : 130,align : 'center',sortable : 'true'},
				{field : 'partNameCn',sortName : "partNameCn",title : '零件名称',width : 130,align : 'center',sortable : 'true'},
				{field : 'partMark',sortName : "partMark",title : '记号',width : 130,align : 'center',sortable : 'true'},
				{field : 'requireNum',sortName : "requireNum",title : '数量',width : 130,align : 'center',sortable : 'true'}
			 ] ]
		}));
	}
	
	//手工组票
	function manuDealBill(){
		var datagrid = $('#partGroup_grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要组票的零件组，请确认！");
			return false;
		}
		var planCodeArr = [];
		var partGroupNoArr = [];
		for (var i = 0; i < records.length; i++) {
			planCodeArr.push(records[i].planCode);
			partGroupNoArr.push(records[i].partgroupNo);
		}
		
		$.messager.confirm('确认','是否确认手工组票？',function(r){    
		    if (r){    
		    	$.ajax({
					type: 'post',
					url: __ctx + "/jiso/jisoReckon/manuDealBill",
					data: {'planCodeArr': planCodeArr, 'partGroupNoArr': partGroupNoArr},
					traditional: true,
					dataType: "json",
					success: function(res){
						if(res.result == 1){
							$('#partGroup_grid').datagrid('reload'); 
							$('#remain_grid').datagrid('loadData', []);
							$.topCall.success("手工组票操作成功");
						}else{
							$.topCall.error("手工组票失败，请联系管理员！");
						}
					}
				});
		    }    
		});
	}
	
</script>