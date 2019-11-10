<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>首页工具</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
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
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/portal/sysIndexTools/sysIndexTools/remove">
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
						<form id="searchForm" class="search-form" onsubmit="return false;">
							<ul style="margin-left: -26px">
								<li><span>名称:</span><input class="inputText" type="text" name="Q^NAME_^SL"></li>
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
		var title = action == "edit" ? "编辑首页工具" : action == "add" ? "添加首页工具" : "查看首页工具";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysIndexTools" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'name',sortName : "NAME_",title : '名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'icon',sortName : "ICON_",title : '图标',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'url',sortName : "URL_",title : '链接',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'type',sortName : "TYPE_",title : '类型',width : 250,align : 'center',sortable : 'true'
			
			
			},
			{field : 'countMode',sortName : "COUNT_MODE",title : '统计模式',width : 250,align : 'center',sortable : 'true'
			,formatter: function(value, row, index) {
				var str = "自定义查询";
				if (value == 0) {
					str = "不统计";
				} else if (value == 1) {
					str = "Service方法";
				}
				return str;
			}
			
			}, 
			{field : 'counting',sortName : "COUNTING",title : '统计算法',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'createTime',sortName : "CREATE_TIME",title : '创建时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/portal/sysIndexTools/sysIndexTools/remove?id=" + row.id + "' herf='#'>删除</a>";
					result += "<a class='btn btn-default fa fa-eye' onClick='setAuth(" + row.id + ")' herf='#'>授权</a>";
					/* result += "<a class='btn btn-default fa fa-eye' onClick='chooseTools(" + row.id + ")' herf='#'>选择工具</a>"; */
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
		var ownerNameJson = getRights(id);
		RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
			data = RightsUtil.rightsDataToArray(data);
			saveRights(id,data);
			dialog.dialog('close');
		},ownerNameJson);
	}
	
	
	function getRights(id){
		var ownerNameJson = [];
		$.ajax({ 
	       type: "get", 
	       url: "getRights?id="+ id, 
	       async:false,
	       dataType:'json',
	       success: function(data){ 
	    	   ownerNameJson = data;
	       } 
		});
		return ownerNameJson;
	}
	
	function saveRights(id,ownerNameJson){
		var params = {
			id : id,
			rightsData : JSON.stringify(ownerNameJson),
		};
		$.ajax({
			   type: "POST",
			   url: __ctx + "/portal/sysIndexTools/sysIndexTools/saveRights",
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
	
	function chooseTools(layoutId) {
		CustomDialog.openCustomDialog("sygjxz", function(data,dialog){
			if(data.length>0){
				var toolIds = [];
				$.each(data,function(index,item){
					toolIds.push(item.ID_);
		 	    });
				$.ajax({
					   type: "POST",
					   url: __ctx + "/portal/sysLayoutTools/sysLayoutTools/save",
					   async:false,
					   dataType:'json',
					   contentType: "application/json",
					   data: JSON.stringify({
						   layoutId : layoutId,
						   toolsIds : toolIds.toString(),
						   toolsType : '计数统计'
					   }),
					   success: function(data){
						   if(data.result==1){
							   $.topCall.alert("温馨提示",data.message);
							}else{
								$.topCall.error(data.message);
							}
					}
				}); 
			}
			dialog.dialog('close');
		}, {
			param : {
				TYPE_ : '计数统计'
			}
		});
	}
</script>
