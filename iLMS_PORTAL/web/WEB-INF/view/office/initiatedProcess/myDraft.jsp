<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>

<title>我的草稿</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout"  fit="true" scroll="no">

		<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;">
			<!--<div class="tree-toolbar">
				<a class="btn btn-xs btn-primary fa-refresh" href="javascript:sysTypeTree.loadTree();"> 刷新</a> <a class="btn btn-xs btn-primary fa-expand" href="javascript:sysTypeTree.expandAll(true)"> 展开</a> <a class="btn btn-xs btn-primary fa-compress" href="javascript:sysTypeTree.expandAll(false)"> 收缩</a>
			</div>-->
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a> 
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
							<a class="btn btn-sm btn-primary fa-remove" action="/office/initiatedProcess/removeDraft"><span>删除</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> <i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>请求标题:</span><input class="inputText" type="text" name="Q^subject_^SL" /></li>
								<li><span>流程名称:</span><input class="inputText" type="text" name="Q^proc_def_name_^SL" /></li>
							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间从:</span><input name="Q^create_time_^DL" class="inputText date" /></li>
								<li><span >至:</span><input name="Q^create_time_^DG" class="inputText date" /></li>
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

	function startFlow(proInstId,procDefId){
		var url = __ctx + '/flow/instance/instanceToStart?defId='+procDefId;
		url += '&proInstId=' + proInstId;
		$.openFullWindow(url);
	}
	function removeDraft(proInstId){
		var param = {
			id:proInstId
		}
		$.post(__ctx+"/office/initiatedProcess/removeDraft",param,function(data){
			var result = new com.hotent.form.ResultMessage(data)
			if(result.isSuccess()){//成功
				$.topCall.success(result.getMessage());	
				$('.my-easyui-datagrid').datagrid('reload');
			}
			else{
				$.topCall.error(result.getMessage());
			}
		});
	}
	function loadGrid()
	    {
	    	$('#grid').datagrid($.extend($defaultOptions,{
	    		idField:"id",
	             url: __ctx+"/office/initiatedProcess/myDraftJson",
	             sortName: 'create_time_',
	             sortOrder: 'desc',
	              columns: [[
							{ field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'center',allowedRemove:'true'},
							
							{ field: 'subject',sortName:"subject_", title: '请求标题', width: 350, align: 'left', sortable: 'true'
							    ,formatter:function(value,row,index){
							        return "<span class=\"cur\" onClick=\"startFlow("+row.id+","+row.procDefId+")\" >"+value+"</span>";
							    }
							},
	                         { field: 'procDefName',sortName:"proc_def_name_", title: '流程名称', width: 200, align: 'center', sortable: 'true' },
	                         { field: 'createTime', sortName:"create_time_ ",title: '创建时间', width:150, align: 'center'  ,formatter:dateTimeFormatter },
	                         { field: 'status',sortName:"status_" , title: '状态', width:100, align: 'center', sortable: 'true' 
	                       	     ,formatter:function(value,row,index){
			  	                	  var arr=[{key:'draft',value:'草稿',css:'red'},{key:'running',value:'运行中',css:'green'},{key:'end',value:'结束',css:'red'},{key:'manualend',value:'人工结束',css:'red'}];
			  	                	  return Object.formatItemValue(arr,value);
	                    		   }
	                         },
	                   	     { field: 'colManage',  title: '操作',  align: 'center'
	                            ,formatter:function(value,row,index){
	 				                  var result="<a class='btn btn-default fa fa-detail' onClick='startFlow("+row.id+","+row.procDefId+")' herf='#'>启动流程</a>";
	 				                  	  result+="<a class='rowInLine btn btn-default fa fa-remove' action='/office/initiatedProcess/removeDraft?id="+row.id+"'>删除</a>";
	 				                  return  result;
	   			                   }
	                   		 }
	              ]]
	         }));     
	    }  
	
	
	function reloadMyDraft(){
		loadGrid();
	}
</script>