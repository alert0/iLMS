<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>控件组合定义</title>
<%@include file="/commons/include/list.jsp"%>
</head>

<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary fa-search btn-sm" href="javascript:;"> <span>搜索</span></a> 
							<a class="btn btn-primary fa-add btn-sm" onclick="openDetail('','add')" href="javascript:;"> <span>添加</span></a> 
							<a class="btn btn-primary fa-remove btn-sm" href="javascript:;" action="/form/selectorDef/remove"> <span>删除</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
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
								<li><span>名称:</span>
									<input type="text" class="inputText" name="Q^name_^SL"></li>
								<li ><span>别名:</span>
									<input type="text" class="inputText" name="Q^alias_^SL"></li>
								<li ><span>系统预定义:</span> 
									<select name="Q^flag_^SL" class="inputText" >
										<option value="">请选择...</option>
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</li>
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
	function openDetail(id,action)
	{
	    var title=action=="selectorDefEdit"?"编辑控件组合":action=="add"?"添加控件组合":"查看控件组合";
	    action=action=="add"?"selectorDefEdit":action;
	    HT.window.openEdit(action+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/form/selectorDef/listJson",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
	                    { field: 'id',sortName:"id_",checkbox:true},
	                     { field: 'name',sortName:"name_", title: '名称', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'alias',sortName:"alias_", title: '别名【与对话框JS名称一致】', width: 500, align: 'center', sortable: 'true'},
						 { field: 'flag',sortName:"flag_", title: '系统预定义', width: 250, align: 'center', sortable: 'true'
							 ,formatter:function(value){
				                  if(value == "0"){
				                	  return "否";
				                  }else{
				                	  return "是"
				                  }
			                  }
	                     },
						 { field: 'colManage',  title: '操作', align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"selectorDefGet\")' herf='#'>明细</a>";
				                 	  result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"selectorDefEdit\")' herf='#'>编辑</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/selectorDef/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result; 
			                  }
                  		  }
	          ]]
		}));   
	}   
</script>