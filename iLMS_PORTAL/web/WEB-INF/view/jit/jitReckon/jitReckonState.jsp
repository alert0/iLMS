<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>拉动推算控制台-推算服务状态查询</title>
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
					</div>
				</div>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
	
	//推算服务状态查询
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx+"/jit/jitReckon/queryJitReckonStatePage",
			striped : true,
			fitColumns : false,
	        columns: [[
       		   { field: 'planCodeDesc',sortName:"planCodeDesc", title: '信息点',width : 150, align: 'center', sortable: 'true'},
			   {field : 'execState',sortName : "execState",title : '推算服务状态',width : 150,align : 'center',sortable : 'true',
					formatter:function(value,row,index){
						if('0' == value){
							return '<span style="color:red;">未推算</span>';
						}else if('1' == value){
							return '<span style="color:green;">推算中</span>';
						}
						return value;
					}
				},
               { field: 'lastExecTime',sortName:"lastExecTime", title: '最近推算时间',width : 150, align: 'center', sortable: 'true'}
   			]]
		}));
	}
	
</script>