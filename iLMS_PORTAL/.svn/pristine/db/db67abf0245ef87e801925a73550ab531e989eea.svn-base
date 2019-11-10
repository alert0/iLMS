<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程变量</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" style="position: fixed !important;" fit="true">
			<div data-options="region:'center',border:false">
				<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-box border">
						<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons" style="margin-left: 6px;">
								<a id="btnAdd" href="javascript:void(0);" class="btn btn-primary btn-sm fa-add"><span>添加</span></a> 
							</div>
						</div>
					</div>
				</div>
				<div id="grid" class="my-easyui-datagrid" style="overflow:auto !important;" ></div>
			</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var defId = '${param.defId}';
	$(function() {
		loadGrid();
		
		$("#btnAdd").click(function() {
			addVal();
		});
		

	});
	function addVal(varKey) {
		if (!varKey)
			varKey = "";
		var dialog;
		var def = {
			passConf : {},
			title : "添加变量",
			width : 500,
			height : 350,
			modal : true,
			resizable : true,
			buttons : [ {
				text : '确定',
				handler : function(e) {
					var win = dialog.innerWin;
					win.saveData();
				}
			}, {
				text : '取消',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		dialog = $.topCall.dialog({
			src : __ctx + '/flow/var/defVarEdit?defId=${param.defId}&varKey=' + varKey,
			base : def
		});
	}
	function reload_() {
		$('#grid').datagrid('reload');
	}
	
	function loadGrid(){
		$('#grid').datagrid({
			 url: __ctx+"/flow/var/listJson?defId=${param.defId}",
	         idField:"varKey",
	         fit: true,
	         fitColumns: true,
       		 sortName: '',
	         sortOrder: 'desc',
	         striped:true,
	         autoRowHeight: false,
	         frozenColumns: [[
	                      { field: 'nodeId',sortName:"name_", title: '流程实例标题', width:150 , align: 'center', sortable: 'true'
							  ,formatter:function(value){
								  if(!value){
									  return "全局变量"
								  }else{
									  return value;
								  }
							  }
	                       },{ field: 'name',sortName:"name_", title: '变量名', width: 150, align: 'center', sortable: 'true'},
		                     { field: 'varKey',sortName:"name_", title: '变量Key', width: 150, align: 'center', sortable: 'true'}
       					]],
	          columns: [[
	                     
	                     { field: 'dataType',sortName:"name_", title: '数据类型', width: 150, align: 'center', sortable: 'true'},
	                     { field: 'defaultVal',sortName:"name_", title: '默认值', width: 150, align: 'center', sortable: 'true'},
	                     { field: 'required',  title: '是否必填', width: 150, align: 'center' ,formatter:boolFormatter},
	                     { field: 'colManage',  title: '操作', width:80, align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                 var result = "<a class='rowInLine btn btn-default fa fa-remove' action= '/flow/var/remove?defId=${param.defId}&varKey="+row.varKey+"' href='#' >删除</a>";
				                 result += "<a class='btn btn-default fa fa-edit' onClick='addVal(\""+row.varKey+"\")' href='#'>编辑</a>";
                  	    		 return result;
			                  }
                  		 }
	          	]],
	          	onLoadSuccess: function () {
		        	  //此代码在linkButtons.js中。
		        	  handGridLoadSuccess();
		        	  $(".datagrid-body").height($(".easyui-layout").height()-100);
		        }	
	     });     
	}   
</script>