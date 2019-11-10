<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义查询</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary  btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a class="btn btn-primary btn-sm fa-add" href="javascript:;" onClick='openDetail("","add")'><span>添加</span></a> 
							<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="/form/customQuery/remove"><span>删除</span></a> 
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i
								class="fa fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li ><span>名称:</span><input
									class="inputText " type="text" name="Q^name_^SL"></li>
								<li><span>别名:</span><input
									class="inputText" type="text" name="Q^alias_^SL"
									></li>
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
	function customQueryPreview(id){
		var url=__ctx+'/form/customQuery/customQueryGet?id='+id;
		$.topCall.dialog({
			src:url,
			base:{title:"预览",width:600, height:350, modal:true,resizable:true}
		});
	}
	function openDetail(id,action)
	{
	    var title=action=="edit"?"编辑自定义查询":action=="add"?"添加自定义查询":"查看自定义查询";
	    HT.window.openEdit("customQueryEdit"+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/form/customQuery/listJson",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true, align: 'center'},
						{ field: 'name',sortName:"name_", title: '名称', width:250, align: 'left', sortable: 'true'},
	                     { field: 'alias',sortName:"alias_", title: '别名',width:250, align: 'center', sortable: 'true'},
	                     { field: 'objName',sortName:"obj_name_", title: '对象名称',width:250,  align: 'center', sortable: 'true'},
						 { field: 'dsalias',sortName:"dsalias_", title: '数据源别名', width:250, align: 'center', sortable: 'true'},
						 { field: 'colManage',  title: '操作',align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-eye' onClick='customQueryPreview("+row.id+");' herf='#'>预览</a>";
				                 	  result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"edit\")' herf='#'>编辑</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/customQuery/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result;
			                  }
                  		  }
	          ]]
	     }));     
	}   
</script>