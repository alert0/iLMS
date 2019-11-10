<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>快捷工具</title>
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
							<select  class="inputText" onchange="selectTools(this)">
									<option value="统计栏目" >统计栏目</option>	
									<option value="快捷工具" >快捷工具</option>
									<option value="常用功能" >常用功能</option>
							</select>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="chooseTools(${param.layoutId})">
								<span>工具设置</span>
							</a>
						</div>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	var type = "统计栏目";
	$(function() {
		loadGrid();
	});

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "toolsJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			pagination:false,
			rownumbers:false,
			singleSelect:true,
			queryParams : {
				layoutId : '${param.layoutId}',
				toolsType : type
			},
			columns : [ [
			{field : 'name',sortName : "NAME_",title : '名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'type',sortName : "TYPE_",title : '类型',width : 250,align : 'center',sortable : 'true'
			
			
			},
			{field : 'countMode',sortName : "COUNT_MODE",title : '统计模式',width : 250,align : 'center',sortable : 'true'
			,formatter: function(value, row, index) {
				var str = "不统计";
				if (value == 1) {
					str = "Service方法";
				} else if (value == 2) {
					str = "自定义查询";
				}
				return str;
			}
			
			}, 
			{field : 'createTime',sortName : "CREATE_TIME",title : '创建时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			},
			{field : 'col',title : '操作',width : 250,align : 'center'
				,formatter: function(value, row, index) {
					var result = '<button type="button" class="btn btn-xs btn-default" title="上移" onclick="move(true,'+index+','+row.id+')"><span class="fa fa-arrow-up"></span> <span class="sr-only">上移</span></button>';
					result += '<button type="button" class="btn btn-xs btn-default" title="下移" onclick="move(false,'+index+','+row.id+')"><span class="fa fa-arrow-down"></span> <span class="sr-only">下移</span></button>';
					result += '<button type="button" class="btn btn-xs btn-default" title="移除" onclick="deleteRow('+index+','+row.id+')"><span class="fa fa-times-circle-o"></span> <span class="sr-only">移除</span></button>';
					return result;
				}
				
			}] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			},
		}));
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
						   toolsType : type
					   }),
					   success: function(data){
						   if(data.result==1){
							   $.topCall.success(data.message,function(){
									    HT.window.refreshTargetGrid("grid");
								},"温馨提示");
							}else{
								$.topCall.error(data.message);
							}
					}
				}); 
			}
			dialog.dialog('close');
		}, {
			param : {
				TYPE_ : type
			}
		});
	}
	function selectTools(obj){
		type = obj.value;
		loadGrid();
	}
	function move(isUp,index,id) {
		var $view = $('div.datagrid-view');
        var $row = $view.find('tr[datagrid-row-index=' + index + ']');
        var sum = $("#grid").datagrid('getData').rows.length-1;
        if (isUp) {
        	if(index != 0){
	            $row.each(function(){
		            $(this).prev().before($(this));
		            movePost(isUp,id)
	            });
        	}
        }else if(index != sum){
            $row.each(function(){
            	$( this).before($(this).next());
            	 movePost(isUp,id)
            });
        }
    }
	function deleteRow(index,id) {
		var $view = $('div.datagrid-view');
        var $row = $view.find('tr[datagrid-row-index=' + index + ']');
        $row.each(function(){
        	$(this).remove();
        	var params = {"layoutId":"${param.layoutId}","toolsType":type,"toolId":id};
        	$.ajax({
 			   type: "POST",
 			   url: __ctx + "/portal/sysLayoutTools/sysLayoutTools/deleteTool",
 			   async:false,
 			   dataType:'json',
 			   data: params,
 			   success: function(data){
 				   if(data.result!=1){
 						$.topCall.error(data.message);
 				   }
	 			}
	 		}); 
        })
    }
	function movePost(isUp,id){
		var params = {"layoutId":"${param.layoutId}","toolsType":type,"isMove":isUp,"toolId":id};
		$.ajax({
			   type: "POST",
			   url: __ctx + "/portal/sysLayoutTools/sysLayoutTools/moveTool",
			   async:false,
			   dataType:'json',
			   data: params,
			   success: function(data){
				   if(data.result!=1){
						$.topCall.error(data.message);
				   }
			}
		}); 
	}
</script>
