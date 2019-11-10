<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>我的抄送转发</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'流程分类'"style="width: 200px;" >
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="gridSearch"  class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
						<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> <i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>标题:</span><input class="inputText" type="text" name="Q^subject^SL" /></li>
								<li><span>类型:</span> <select class="inputText" name="Q^type^S" style="margin-left: -3px">
										<option value="">全部</option>
										<option value="copyto">抄送</option>
										<option value="trans">转发</option>
								</select></li>

							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间 :</span><input name="Q^createTimeStart^DL" class="inputText date" /></li>
								<li><span>至:</span><input name="Q^createTimeEnd_DG" class="inputText date" /></li>
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
    var sysTypeTree = null;
    $(function(){
	//加载分类树
		sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
		loadGrid();
    });
    
    function showDetail(id)
	{
	    var title="我的抄送转发";
	    HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
	}
    
    function toCopyTo(instId){
		var title="流程转发";
		var url="${ctx}/flow/instance/instanceToCopyTo?instanceId="+instId;
		 HT.window.openEdit(url,title, 'view', 'grid', 500, 350, null, null, instId, false);
	}
    
    function loadGrid()
    {
    	$('#grid').datagrid($.extend($defaultOptions,{
             url: __ctx+"/office/initiatedProcess/myCopyToJson",
             sortName: 'create_time_',
             sortOrder: 'desc',
             columns: [[
						{ field: 'subject',sortName:"subject_", title: '请求标题', width: 350, align: 'left', sortable: 'true'},
                         { field: 'recever',sortName:"recever", title: '接收人', width: 200, align: 'center', sortable: 'true' },
                         { field: 'createTime', sortName:"create_time_ ",title: '创建时间', width:150, align: 'center'  ,formatter:dateTimeFormatter  },
                         { field: 'type',sortName:"type_" , title: '类型', width:100, align: 'center', sortable: 'true' 
                       	     ,formatter:function(value,row,index){
                       	    	  var arr=[{'key':'copyto','value':'抄送','css':'green'},{'key':'trans','value':'转发','css':'red'}];
                       	    	  return Object.formatItemValue(arr,value);
                    		   }
                         },
                   	     { field: 'colManage',  title: '操作', align: 'center'
                   	    	  ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='showDetail("+row.instId+")' herf='#'>查看详情</a>"
				                 				+"<a class='btn btn-default fa fa-detail' onClick='toCopyTo("+row.instId+")' herf='#'>转发</a>";
				                  return result;
 			                   }
                   		 }
              ]]
         }));     
    }   

</script>