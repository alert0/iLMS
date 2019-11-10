<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>首页布局</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="#">
								<span>搜索</span>
							</a>
							<a href="#" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/portal/sysIndexColumn/sysIndexColumn/remove">
								<span>删除</span>
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
								<li><span>栏目名称:</span><input class="inputText" type="text" name="Q^NAME^SL"></li>
								<li><span>栏目别名:</span><input class="inputText" type="text" name="Q^ALIAS^SL"></li>
								<li><span>是否支持刷新:</span>
								<select class="inputText" name="Q^SUPPORT_REFESH^SL">
								    <option value="">全部</option>
								    <option value="0">否</option>
								    <option value="1">是</option>
								</select></li>
								<li><span>栏目类型:</span>
								<select class="inputText" name="Q^IS_PUBLIC^SL">
								    <option value="">全部</option>
								    <option value="0">PC端</option>
								    <option value="1">手机端</option>
								</select></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑首页栏目" : action == "add" ? "添加首页栏目" : "查看首页栏目";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysIndexColumn" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function previewTemplate(id,columnName,isPublic){
		var url =  __ctx+"/portal/sysIndexColumn/sysIndexColumn/getTemp?id="+id+"&isPublic="+isPublic;
		url=url.getNewUrl();
		var width = 820;
		var height = 480;
		if(isPublic == 1){
			width = 360;
			height = 640;
		}
		$.topCall.dialog({
			src : url,
			base : {
				title : columnName,
				width : width,
				height : height,
				modal : true,
				resizable : true,
			}
		});
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'create_time',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},   
			{field : 'name',sortName : "name",title : '栏目名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'alias',sortName : "alias",title : '栏目别名',width : 250,align : 'center',sortable : 'true'
			
			
			},
			{field : 'dataMode',sortName : "DATA_MODE",title : '数据加载方式',width : 250,align : 'center',sortable : 'true'
				,formatter:function(value,row,index){
					var arr = [ { 'key' : '0','value' : 'Service方法'}, 
				                {'key' : '1', 'value' : '自定义查询方式'},
				                {'key' : '2', 'value' : 'webservice方法'},
				                {'key' : '3', 'value' : 'RESTful接口'}];
				return Object.formatItemValue(arr,value);
				}
			
			}, 
			{field : 'dataFrom',sortName : "DATA_FROM",title : '数据来源',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'colUrl',sortName : "COL_URL",title : '栏目URL',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'isPublic',sortName : "IS_PUBLIC",title : '栏目类型',width : 250,align : 'center',sortable : 'true'
				,formatter:function(value,row,index){
					var arr = [ { 'key' : '1','value' : '手机端'}, 
				                {'key' : '0', 'value' : 'PC端'}];
				return Object.formatItemValue(arr,value);
				}
			
			}, 
			{field : 'supportRefesh',sortName : "SUPPORT_REFESH",title : '是否支持刷新',width : 250,align : 'center',sortable : 'true'
				,formatter:function(value,row,index){
					var arr = [ { 'key' : '1','value' : '是','css' : 'green'}, 
				                {'key' : '0', 'value' : '否','css' : 'red'}];
				return Object.formatItemValue(arr,value);
				}
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='rowInLine btn btn-default fa fa-remove' action='/portal/sysIndexColumn/sysIndexColumn/remove?id=" + row.id + "' herf='#'>删除</a>";
					result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					/* result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>"; */
					result += "<a class='btn btn-default fa fa-eye' onClick='previewTemplate(" + row.id + ",\"" + row.name + "\","+row.isPublic+")' herf='#'>预览</a>";
					result += "<a class='btn btn-default fa fa-eye' onClick='setAuth(" + row.id + ")' herf='#'>授权</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
	
	/**
	 * 设置设置权限。
	 * ownerNameJson 格式。
	 * [{type:"everyone",title:"所有人"},{type:"user",title:"用户",id:"1,2",name:"ray,tom"}]
	 */
	 function setAuth(id){
		var ownerNameJson = getColumnRights(id);
		RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
			data = RightsUtil.rightsDataToArray(data);
			saveColumnRights(id,data);
			dialog.dialog('close');
		},ownerNameJson);
	}
	
	
	function getColumnRights(id){
		var ownerNameJson = [];
		$.ajax({ 
	       type: "get", 
	       url: "getColumnRights?id="+ id, 
	       async:false,
	       dataType:'json',
	       success: function(data){ 
	    	   ownerNameJson = data;
	       } 
		});
		return ownerNameJson;
	}
	
	function saveColumnRights(id,ownerNameJson){
		var params = {
			id : id,
			rightsData : JSON.stringify(ownerNameJson),
		};
		$.ajax({
			   type: "POST",
			   url: __ctx + "/portal/sysIndexColumn/sysIndexColumn/saveColumnRights",
			   async:true,
			   dataType:'json',
			   data: params,
			   success: function(data){
				   if(data.result==1){
					   $.topCall.alert("温馨提示",data.message);
					}else{
						$.topCall.error(data.message);
					}
			}
		}); 
	}
	
</script>
