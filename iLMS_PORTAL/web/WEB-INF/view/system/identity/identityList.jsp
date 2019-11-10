<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<title>流水号</title>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" >
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a class="btn btn-primary btn-sm fa-add" onclick="openDetail('','add')"><span>添加</span></a> 
							<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="/system/identity/remove"><span>删除</span></a>
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
							<ul >
								<li><span>流水号名称:</span><input type="text" class="inputText"
									name="Q^name_^SL" ></li>
								<li><span>流水号别名:</span><input type="text" class="inputText"
									name="Q^alias_^SL" ></li>
								<li><span>生成类型:</span>
									<select name="Q^gen_type_^SL" class="inputText">
										<option value="">请选择...</option>
										<option value="1">每天生成</option>
										<option value="0">递增</option>
								</select></li>
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
		ConditionScript.showDialog();
	}
	function openDetail(id,action)
	{
	    var title=action=="identityEdit"?"编辑流水号":action=="add"?"添加流水号":"查看流水号";
	    action=action=="add"?"identityEdit":action;
	    HT.window.openEdit(action+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/system/identity/listJson",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true},
						{ field: 'name',sortName:"NAME_", title: '名称', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'alias',sortName:"alias_", title: '别名', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'regulation',sortName:"regulation_", title: '规则', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'genType',sortName:"gen_type_", title: '生成类型', width: 250, align: 'center', sortable: 'true'
	                    	 ,formatter:function(value){
				             	if(value=="0") return "递增";
				             	if(value=="1") return "每天生成";
	                    	 }
	                     },
	                     { field: 'noLength',sortName:"no_length_", title: '长度', width: 250, align: 'center', sortable: 'true'},
	                     { field: 'colManage',  title: '操作',  align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"identityGet\")' herf='#'>详细</a>";
				                 	  result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"identityEdit\")' herf='#'>编辑</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/identity/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result;
			                  }
                  		  }
	          ]]
		})); 
	} 
</script>