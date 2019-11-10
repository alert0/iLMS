<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>常用脚本</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" >
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a class="btn btn-primary btn-sm fa-add" onclick="openDetail('','add')" href="javascript:;"><span>添加</span></a> 
							<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="remove"><span>删除</span></a>
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
								<li><span>名 称:</span><input type="text" class="inputText" name="Q^NAME_^SL"></li>
								<li>
									<span>分类:</span> 
									<select class="inputText" name="Q^CATEGORY_^SL">
										<option value="">请选择</option>
										<c:forEach items="${categoryList}" var="categrory">
											<option value="${categrory}">${categrory}</option>
										</c:forEach>
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
	function openDetail(id,action){
	    var title=action=="scriptEdit"?"编辑常用脚本":action=="add"?"添加常用脚本":"查看常用脚本";
	    action=action=="add"?"scriptEdit":action;
	    HT.window.openEdit(action+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	

	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/system/script/listJson",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true},
						{ field: 'name',sortName:"NAME_", title: '名称',width: 350,  align: 'center', sortable: 'true'},
	                     { field: 'category',sortName:"CATEGORY_", title: '脚本分类', width: 250, align: 'center', sortable: 'true'},
						 { field: 'colManage',  title: '操作',  align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"scriptGet\")' herf='#'>详细</a>";
				                 	  result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"scriptEdit\")' herf='#'>编辑</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/script/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result;
			                  }
                  		  }
	          ]]
		}));
	}   

</script>