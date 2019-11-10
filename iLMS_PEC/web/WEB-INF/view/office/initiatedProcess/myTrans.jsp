<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<title>我流转出去的任务</title>
</head>
<body>
	<div class="easyui-layout"  fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
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
								<li><span>请求标题:</span><input class="inputText" type="text" name="Q^subject^SL" /></li>
								<li><span>流程名称:</span><input class="inputText" type="text" name="Q^defName^SL" /></li>
							</ul>
							<ul>
								<li><span>流转时间:</span><input name="Q^transTimeStart_DL" class="inputText date" /></li>
								<li><span >至:</span><input name="Q^transTimeEnd_DG" class="inputText date" /></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
    $(function(){
    	loadGrid();
    });

    function revoke(id, subject){
		//去除html标签
		var tmp = $("<div>" + subject + "</div>").text();
	
		var url = '${ctx}/office/initiatedProcess/revokeTrans?taskId=' + id;
		$.topCall.dialog({
		    src : url, base :
		    {
			title : '撤销::[' + tmp + "]", width : 700, height : 400, modal : true, resizable : true
		    }
		});
		reloadLoad();
    }
    function showDetail(id)
	{
	    var title="我流转的任务";
	    HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
	}
    function showTaskTransDetail(id)
	{
	    var title="流转明细";
	    HT.window.openEdit("${ctx}/flow/task/taskTransDetail?id="+id,title, 'view', 'grid', 600, 350, null, null, id, false);
	}
    
    function reloadLoad(){
		$('.my-easyui-datagrid').datagrid('reload');
    }
    function loadGrid()
    {
    	$('#grid').datagrid($.extend($defaultOptions,{
             url: __ctx+"/office/initiatedProcess/myTransRecordJson",
             sortName: 'trans_time_',
             sortOrder: 'desc',
             columns: [[
						 { field: 'taskSubject',sortName:"task_subject_", title: '请求标题', width: 350, align: 'left', sortable: 'true'
							 ,formatter:function(value,row,index){
						        return "<span class=\"cur\" onClick=\"showDetail("+row.procInstId+")\" >"+value+"</span>";
						      }	 
						 },
                         { field: 'defName',sortName:"def_name_", title: '流程名称', width: 200, align: 'center', sortable: 'true' },
                         { field: 'transTime',sortName:"trans_time_" , title: '流转时间', width:150, align: 'center', sortable: 'true' 
     	                	  ,formatter:dateTimeFormatter  
     	                 },
     	                { field: 'status',sortName:"status_" , title: '状态', width:150, align: 'center', sortable: 'true' 
     	                	,formatter : function(value, row, index) {
     	   					var arr = [ {key : '0', value : '流转中',css : 'green'}, 
     	   					            {key : '1',value : '流转结束',css : 'green'}, 
     	   					            {key : '2',value : '流转撤销',css : 'red'}];
     	   					return Object.formatItemValue(arr,value);
     	   				}
   	                 },
                   	     { field: 'colManage',  title: '操作', align: 'center'
                   	    	  ,formatter:function(value,row,index){
				                  	  var result = "<a class='btn btn-default fa fa-detail' onClick='showTaskTransDetail("+row.id+")' herf='#'>流转详细</a>";
				                  	  if(row.status == 0 ){
				                  		result += "<a class='btn btn-default fa fa-reply-all' onClick='revoke("+row.taskId+","+'"'+row.taskSubject+'"'+")' herf='#'>撤销流转</a>";
				                  	  }
				                  return result;
 			                   }
                   		 }
              ]]
    	 }));      
    }   
</script>