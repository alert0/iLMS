<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>新建流程</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false" >
			<div id="gridSearch" class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
						<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> <i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>流程名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid" ></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var sysTypeTree =  null;
	$(function (){
		//加载分类树
	  sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
	
	  loadGrid();
	});


	function startFlow(defId){
		$.openFullWindow(__ctx+'/flow/instance/instanceToStart?defId='+defId);
	}
	
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	        url: __ctx+"/office/initiatedProcess/newProcessJson",
	        sortName: 'create_time_',
	        sortOrder: 'desc',
	        columns: [[
					{ field: 'name',sortName:"name_", title: '流程名称', width: 80, align: 'left', sortable: 'true'
						,formatter:function(value,row,index)
		                   {
		                       return "<span class=\"cur\" onClick=\"startFlow("+row.id+")\" >"+value+"</span>";
		                   }
					 },
	                { field: 'defKey',sortName:"def_key_", title: '流程Key',  align: 'center', sortable: 'true' },
	                { field: 'desc',sortName:"desc_" , title: '流程描述', width:100, align: 'center', sortable: 'true' },
	                { field: 'status',sortName:"status_" , title: '流程状态', width:50, align: 'center', sortable: 'true' ,
	                	formatter:function(value,row,index){
		                	   var arr=[{key:'draft',value:'草稿',css:'red'},{key:'deploy',value:'已发布',css:'green'},{key:'forbidden',value:'禁用',css:'red'},{key:'forbidden_instance',value:'禁止流程实例',css:'red'}];
		                	   return Object.formatItemValue(arr, value);
		                    }
	                },
	                { field: 'testStatus',sortName:"test_status_" , title: '生产状态', width:50, align: 'center', sortable: 'true'
	                    ,formatter:function(value,row,index){
	                	   var arr=[{key:'test',value:'测试',css:'red'},{key:'run',value:'正式',css:'green'}];
	                	   return Object.formatItemValue(arr, value);
	                    }
	                },
	                { field: 'version',sortName:"version_" , title: '版本号', align: 'center', sortable: 'true'  }
	        ]]
	    }));    
	}
</script>