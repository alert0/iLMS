<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>部门审核人</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
<script>
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool = ArrayTool;
	
	var data={
		
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
}]);

</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" >
						<span>搜索</span>
					</a>
					<a  class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
                    </a>
                    <a class="btn btn-sm btn-primary fa-check" onclick="savaFullNameFun()">
                        <span>确定</span></a>
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
						<li><span>姓名:</span>
						<input class="inputText" name="fullname"/>
						</li>
						<li><span>部门:</span>
						<input class="inputText" name="name"/>
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
		
		  $('#grid').datagrid({
			  onDblClickRow : function (index, row){
				var fullName = row.fullname;
				var id = row.id;
				$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/dpm/depDepartment/checker",
		    	    data: {
		    	        'fullName' : fullName,
		    	        'id' : id,
		    	    },		
		    	    dataType: "json",
		    	    traditional: true,
		    	    success: function(data){
		    	    	$('#grid').datagrid('reload');
		    	    	HT.window.closeEdit(true);
		    	 }
		      });
				$('#getDepChecker').val(fullName);
				
			}
		}); 
		 
	});
	
/* 	function savaFullNameFun(){
		var datagrid = $('#grid');
		if (null == datagrid || datagrid.length < 0){
			return false;
		}
		var records = datagrid.datagrid('getChecked');
		if (records == null || records.length < 1) {
			$.messager.alert("操作提示", "未选择数据，请确认","info");
			return false;
		}
		var idArr = [];
		for (var i = 0; i < records.length; i++) {
			applyNoArr.push(records[i].applyNo);
		}
	} */
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/dpm/department/getDepChecker",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			columns : [ [
			/* {field : 'id',sortName : "id",checkbox : true},  */    
			{field : 'fullname',sortName : "fullname",title : '姓名',width : 80,align : 'center',sortable : 'true'}, 
			{field : 'name',sortName : "name",title : '部门',width : 80,align : 'center',sortable : 'true'},
		    {
		      onDbclickRow : function (value, row, index){
			               var fullName = row.fullname;
			              alert(fullName);
			               $('#getDepChecker').val(fullName);
			                HT.window.closeEdit(true);
		    }
		   }] ]
		}));
	}
	
	
</script>
