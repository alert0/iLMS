<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>统计栏目</title>
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
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="chooseTools(${param.layoutId})">
								<span>栏目设置</span>
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
	$(function() {
		loadGrid();
	});

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "columnJson?layoutId=${param.layoutId}",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			pagination:false,
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
			
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
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
						   toolsType : '统计栏目'
					   }),
					   success: function(data){
						   if(data.result==1){
							   $.topCall.success("温馨提示",data.message,function(){
									    HT.window.refreshTargetGrid("grid");
								});
							}else{
								$.topCall.error(data.message);
							}
					}
				}); 
			}
			dialog.dialog('close');
		}, {
			param : {
				TYPE_ : '统计栏目'
			}
		});
	}
</script>
