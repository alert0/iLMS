<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<title>条件脚本</title>
<script type="text/javascript"src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript">
var type=${type};
</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
					<a class="btn btn-primary btn-sm fa-add" onclick="openDetail('','add')" href="javascript:;"><span>添加</span></a> 
					<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="/system/conditionScript/remove"><span>删除</span></a> 
					<a class="btn btn-primary btn-sm fa-eye" href="javascript:;" onClick="preview()"><span>测试脚本对话框</span></a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"> <i
						class=" fa  fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul>
						<li><span>方法名称:</span><input type="text" class="inputText"
							name="Q^METHOD_NAME_^SL" ></li>
						<li><span>脚本所在类名:</span><input type="text"
							class="inputText" name="Q^CLASS_NAME_^SL"></li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		loadGrid();
	});
	function preview(){
		var parm={type:type};
		ConditionScript.showDialog(parm,function(data,dialog){
		   var str=JSON.stringify(data);
		   alert(str);
		});
	}
	
	function openDetail(id,action)
	{
		var url=action=="add"?"conditionScriptEdit":action=="edit"?"conditionScriptEdit":"conditionScriptGet";
		if(type==1){
			var title=action=="edit"?"编辑条件脚本":action=="add"?"添加条件脚本":"查看条件脚本";
		}
		else{
			var title=action=="edit"?"编辑人员脚本":action=="add"?"添加人员脚本":"查看人员脚本";
		}
	    HT.window.openEdit(url+'?type='+type+'&id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/system/conditionScript/listJson?type="+type,
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         frozenColumns: [[
						 
	         ]],
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true},
						{ field: 'methodName',sortName:"METHOD_NAME_", title: '方法名称', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'methodDesc',sortName:"METHOD_DESC_", title: '方法描述', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'className',sortName:"CLASS_NAME_", title: '脚本所在类名', width: 250, align: 'center', sortable: 'true'},
						 { field: 'classInsName',sortName:"CLASS_INS_NAME_", title: '类实例名', width: 250, align: 'center', sortable: 'true'},
						 { field: 'enable', sortName:"ENABLE_", title: '是否有效', width:250, align: 'center', sortable: 'true'
                  	    	 ,formatter:function(value){
                  	    		 if(value =="1"){
                  	    			 return "是";
                  	    		 }else{
                  	    			 return "否";
                  	    		 }
			                  }
                  		 },
						 { field: 'colManage',  title: '操作',align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"get\")' herf='#'>详细</a>";
				                 	  result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"edit\")' herf='#'>编辑</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/conditionScript/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result;
			                  }
                  		  }
	          ]]
		}));  
	}   
</script>