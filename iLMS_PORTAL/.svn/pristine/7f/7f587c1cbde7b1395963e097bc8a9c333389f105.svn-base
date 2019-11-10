<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>厂外同步推算控制台-待组单指示票</title>
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
							<li><span>装车代码:</span><input class="inputText" type="text" name="routeCode"></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="groupOrder_grid" class="my-easyui-datagrid"></div>
		</div> 
		<div data-options="region:'east',split:true" style="width:50%;">
		    <div id="notGroupBill_grid"  class="my-easyui-datagrid"></div>
		</div> 
	</div>
</body>
</html>
<script>
	$(function(){
		loadGrid();
	})
	
	//组单信息列表
	function loadGrid() {
		$('#groupOrder_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jiso/jisoReckon/queryJisoGroupOrderPage",
			toolbar : [{
				text: '+手工组单',
				iconCls: 'icon-edit',
				handler: manuDealOrder
			}],
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : 'id',sortName : "id",checkbox : true},     
				{field : 'planCode',sortName : "planCode",title : '信息点',width : 150,align : 'center',sortable : 'true',hidden: true},
				{field : 'supFactory',sortName : "supFactory",title : '出货地代码',width : 150,align : 'center',sortable : 'true'},
				{field : 'routeCode',sortName : "routeCode",title : '装车代码',width : 150,align : 'center',sortable : 'true'}, 
				{field : 'carBatchSeqno',sortName : "carBatchSeqno",title : '车次',width : 150,align : 'center',sortable : 'true'},
				{field : 'nextGroupOrderWay',sortName : "nextGroupOrderWay",title : '下一组单方式',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqTime',sortName : "manuReqTime",title : '手工组单请求时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'dealTime',sortName : "dealTime",title : '手工组单时间',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'manuReqUser',sortName : "manuReqUser",title : '手工组单操作人',width : 130,align : 'center',sortable : 'true'}
			 ] ],
			 onClickRow : function(index,row){
				 loadNotGroupBillGrid(index,row);
			 }
		}));
		
	}
	
	//未组单指示票列表
	function loadNotGroupBillGrid(index,row){
		var planCode = row.planCode;
		var supFactory = row.supFactory;
		var routeCode = row.routeCode;
		var carBatchSeqno = row.carBatchSeqno;
		$('#notGroupBill_grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/jiso/jisoReckon/queryNotGroupBillPage",
			toolbar: null,
			queryParams: {
				'planCode': planCode,
				'supFactory' : supFactory,
				'routeCode': routeCode,
				'carBatchSeqno': carBatchSeqno
		    },
		    striped : true,
			fitColumns : false,
			columns : [ [
				{field : 'insNo',sortName : "insNo",title : '指示票号',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'partgroupName',sortName : "partgroupName",title : '零件组名称',width : 130,align : 'center',sortable : 'true'},
				{field : 'partgroupNo',sortName : "partgroupNo",title : '零件组代码',width : 130,align : 'center',sortable : 'true'}, 
				{field : 'supFactory',sortName : "supFactory",title : '出货地代码',width : 130,align : 'center',sortable : 'true'},
				{field : 'arrDepot',sortName : "arrDepot",title : '到货仓库',width : 130,align : 'center',sortable : 'true'},
				{field : 'location',sortName : "location",title : '落点',width : 130,align : 'center',sortable : 'true'},
				{field : 'creationTime',sortName : "creationTime",title : '创建时间',width : 130,align : 'center',sortable : 'true'}
			 ] ]
		}));
		
	}
	
	//手工组单
	function manuDealOrder(){
		var datagrid = $('#groupOrder_grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.topCall.alert("提示", "没有选择需要组单的数据，请确认！");
			return false;
		}
		var planCodeArr = [];
		var supFactoryArr = [];
		var routeCodeArr = [];
		var carBatchSeqnoArr = [];
		for (var i = 0; i < records.length; i++) {
			planCodeArr.push(records[i].planCode);
			supFactoryArr.push(records[i].supFactory);
			routeCodeArr.push(records[i].routeCode);
			carBatchSeqnoArr.push(records[i].carBatchSeqno);
		}
		
		$.messager.confirm('确认','是否确认手工组单？',function(r){    
		    if (r){    
		    	$.ajax({
					type: 'post',
					url: __ctx + "/jiso/jisoReckon/manuDealOrder",
					data: {'planCodeArr': planCodeArr, 'supFactoryArr': supFactoryArr, 'routeCodeArr': routeCodeArr, 'carBatchSeqnoArr': carBatchSeqnoArr},
					traditional: true,
					dataType: "json",
					success: function(res){
						if(res.result == 1){
							$('#groupOrder_grid').datagrid('reload'); 
							$('#notGroupBill_grid').datagrid('loadData', []);
							$.topCall.success("手工组单操作成功");
						}else{
							$.topCall.error("手工组单失败，请联系管理员！");
						}
					}
				});
		    }    
		});
		
	}
	
	
</script>