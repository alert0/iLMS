<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>转办代理事宜</title>
<%@include file="/commons/include/list.jsp" %>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'流程分类'"
			style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head" >
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> 
								<i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>请求标题:</span><input class="inputText" type="text"
									name="Q^subject_^SL" /></li>
								<li><span>节点名称:</span><input class="inputText" type="text"
									name="Q^task_name_^SL" /></li>
								<li><span>任务类型:</span> 
									<select class="inputText" name="Q^turn_type_^S">
										<option value="">全部</option>
										<option value="agent">代理</option>
										<option value="turn">转办</option>
									</select>
								</li>
							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间 :</span><input name="Q^beginTime^DL"
									class="inputText date" /></li>
								<li><span>至:</span><input name="Q^endTime^DG"
									class="inputText date" /></li>
								<li><span>状态:</span>
									<select class="inputText" name="Q^status_">
										<option value="">全部</option>
										<option value="running">正在运行</option>
										<option value="finish">结束</option>
										<option value="cancel">取消</option>
								</select></li>
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
	var sysTypeTree =  null;
	$(function (){
		//加载分类树
	  sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
	  loadGrid();
	});
	
	
	function cancelTaskturn(id,subject){
		var tmp=$("<div>" +subject +"</div>").text();
		var url='${ctx}/office/receivedProcess/cancelTurn?taskId=' + id;
		$.topCall.dialog({
			src :url ,
			base : {
				title : '取回::[' +tmp +"]",
				width : 700,
				height : 400,
				modal : true,
				resizable : true
			}
		});
	}

	function getTrunAssignList(taskTurnId){
		$.topCall.dialog({
			src:'${ctx}/office/initiatedProcess/taskTurnDialog?taskTurnId='+taskTurnId,
			base:{title:'转办详细信息',width:600,height:350, modal:true,resizable:true,closable:true}
		});
	}
	function loadGrid()
    {
		$('#grid').datagrid($.extend($defaultOptions,{
             url: __ctx+"/office/initiatedProcess/delegateJson",
             sortName: 'a.create_time_',
             sortOrder: 'desc',
             columns: [[
						{ field: 'taskSubject',sortName:"task_subject_", title: '请求标题', width: 350, align: 'left', sortable: 'true'},
                         { field: 'taskName',sortName:"task_name_", title: '任务名称', width: 200, align: 'center', sortable: 'true' },
                         { field: 'createTime', sortName:"create_time_ ",title: '创建时间', width:150, align: 'center'  ,formatter:dateTimeFormatter  },
                         
                         { field: 'turnType',sortName:"turn_type_", title: '任务类型', width: 80, align: 'center', sortable: 'true'
                             ,formatter:function(value,row,index){
                               if(value=="agent")return "代理";
                               if(value=="turn")return "转办";
                             }
                         },
                         { field: 'status',sortName:"status_" , title: '状态', width:100, align: 'center', sortable: 'true' 
                       	     ,formatter:function(value,row,index){
		  	                	  var arr=[{key:'running',value:'运行中',css:'green'},{key:'finish',value:'结束',css:'red'},{key:'cancel',value:'取消',css:'red'}];
		  	                	  return Object.formatItemValue(arr,value);
                    		   }
                         },
                         
                   	     { field: 'colManage',  title: '操作',  align: 'center'
                   	    	  ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-detail' onClick='getTrunAssignList("+row.id+")' herf='#'>查看转办详情</a>";
				                 	  if(row.status == "running"){
	 				                	 result +="<a class='btn btn-default fa fa-reply-all' onClick='cancelTaskturn("+row.taskId+","+'"'+row.taskSubject+'"'+")' herf='#'>取消转办</a>";
	 				                  }
				                  return result;
 			                   }
                   		 }
              ]]
         }));     
    }   
</script>
