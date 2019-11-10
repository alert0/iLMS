<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>已办事宜</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">

		<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>

		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
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
								<li><span>请求标题:</span><input class="inputText" type="text" name="Q^subject_^SL" /></li>
								<li><span>流程名称:</span><input class="inputText" type="text" name="Q^proc_def_name_^SL" /></li>
								<li><span>流程状态:</span>   <select name="Q^wfInst.status_^S">
										<option value="">全部</option>
										<option value="running">正在运行</option>
										<option value="end">结束</option>
										<option value="manualend">人工结束</option>
										<option value="cancel">取消</option>
										<option value="back">驳回</option>
										<option value="revoke">撤回</option>
										<option value="revokeToStart">撤回到发起人</option>
										
								</select></li>
							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间从:</span><input name="Q^wfInst.create_time_^DL" class="inputText date" /></li>
								<li><span>至:</span><input name="Q^wfInst.create_time_^DG" class="inputText date" /></li>
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

function openDetail(id,taskKey)
{
    var title="查看已办事宜";
    HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id+"&taskKey="+taskKey,title, 'view', 'grid', 500, 500, null, null, id, true);
}


function loadGrid()
{
    $('#grid').datagrid($.extend($defaultOptions,{
        url: __ctx+"/office/receivedProcess/handledJson",
        sortName: 'wfInst.create_time_',
        sortOrder: 'desc',
        
        columns: [[
				{ field: 'subject',sortName:"subject_", title: '标题', width: 350, align: 'left', sortable: 'true'
				    ,formatter:function(value,row,index) {
				        return "<span class=\"cur\" onClick=\"openDetail("+row.id+",\'"+row.taskKey+"\')\" >"+value+"</span>";
				    }
				 },
                { field: 'procDefName',sortName:"proc_def_name_", title: '流程名称', width: 200, align: 'center', sortable: 'true' },
                { field: 'taskName',  title: '任务名称', width: 150, align: 'center'},
                { field: 'createTime',sortName:"create_time_" , title: '创建时间', width:150, align: 'center', sortable: 'true' 
                    ,formatter:dateTimeFormatter    
                },
                { field: 'duration',sortName:"duration_" , title: '持续时间', width:145, align: 'center', sortable: 'true' ,
                	formatter:function(value){
                		return $.timeLag(value);
                	}
                },
                { field: 'status',sortName:"status_" , title: '流程状态', align: 'center', sortable: 'true',
                	formatter:function(value,row,index){
                	   var arr=[{key:'revoke',value:'撤回',css:'red'},{key:'revokeToStart',value:'撤回到发起人',css:'red'},{key:'draft',value:'草稿',css:'red'},{key:'running',value:'运行中',css:'green'},{key:'end',value:'结束',css:'red'},{key:'manualend',value:'人工结束',css:'red'},{key:'back',value:'驳回',css:'red'} ];
                	   return Object.formatItemValue(arr, value);
                    }
                },
                { field: 'dueTaskTime',title: '期限状态', width: 195, align: 'center', sortable: 'true'
               	 ,formatter:function(value,row,index){
               		 if(!row.dueDateType || !value ) return "";
               		 var className = "progress-bar-success",dateType = "工作日", 
               		 detailHtml = "",detailHtml2="";
               		 var percent = row.dueUseTaskTime*100/value;
               		 percent = parseFloat(percent.toFixed(2));
               		 if(25<percent&&percent<=50){
               			 className = " progress-bar-info";
               		 }
               		 if(50<percent&&percent<=75){
               			 className = "progress-bar-warning";
               		 }
               		 if(75<percent){
               			 className = "progress-bar-danger";
               		 }
               		 if(percent>100){
               			 percent = 100;
               		 }
               		 if(row.dueDateType == "caltime"){
               			 detailHtml = "<img src='"+__ctx+"/css/image/caltime.png' title='日历日'></img>  ";
               		 }else{
               			 detailHtml = "<img src='"+__ctx+"/css/image/worktime.png' title='工作日'></img>  ";
               		 }
               		 
               		 if(row.dueStatus==1){
               			detailHtml2 += "<a class='fa fa-detail' onClick='openTaskDueTimeDetail(\""+row.taskId +"\",\""+row.taskName+"\")' herf='#' title='明细'></a>";
               		 }
               		 
               		 
               		 var progressHtml=	'<div class="row">'+
               		 						'<div class="col-sm-2" style="margin-top:1px;">'+detailHtml+
               		 						'</div>'+
               		 						'<div class="col-sm-8" style="padding:0;">'+
 		              			 				'<div class="progress progress-striped" >'+
 		              		 			  			'<div class="progress-bar '+className+'" style="width:'+percent+'%">'+
 		              		 			  				'<span style="color:black"> ' +  percent+'% '+ '</span>'+
 		              		 			  			'</div>'+
 		              		 					'</div>'+
 	              		 					'</div>'+
 	              		 					'<div class="col-sm-2" style="margin-left:-8px;margin-top:2px;">'+detailHtml2+
 		              		 				'</div>'+
 		              		 			'</div>';
               		  return progressHtml;
          		     }	 
             },
       	    	{ field: 'colManage',  title: '操作', width:30, align: 'center' , sortable: 'true',
                		formatter:function(value,row,index){
                              if(row.status!="running" || row.opinionStatus!="agree")return "";
			                  var result="<a class='btn btn-default fa fa-reply-all' onClick='revoke("+row.id+","+'"'+row.subject+'"'+")' herf='#'>撤回</a>";
			                  return  result;
		                   }
       		}
        ]]
    }));    
}
function revoke(id, subject){
	    //html标签
		var tmp = $("<div>" + subject + "</div>").text();
		var url = '${ctx}/office/initiatedProcess/revoke?isHandRevoke=true&instId=' + id;
		$.topCall.dialog(
		{
		    src : url, base :
		    {
			title : '撤回:[' + tmp + "]", width : 700, height : 400, modal : true, resizable : true
		    }
		});
		 
}
function reloadLoad(){
	$('.my-easyui-datagrid').datagrid('reload');
}

function openTaskDueTimeDetail(taskId,name){
	 var url=__ctx+"/flow/task/bpmTaskDueTime/bpmTaskDueTimeList?taskId="+taskId;
	 HT.window.openEdit(url,name+"--延期记录", 'View', 'grid', 500, 500, null, null, taskId, true);
}
</script>
 