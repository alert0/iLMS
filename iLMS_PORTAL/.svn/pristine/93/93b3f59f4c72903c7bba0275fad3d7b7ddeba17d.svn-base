<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程任务</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" style="" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">				 
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
	 	 					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i class="fa  fa-angle-double-up"></i></a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>任务标题:</span><input class="inputText" type="text"
									name="Q^subject_^SL"></li>
								<li><span>任务名称:</span><input class="inputText"
									type="text" name="Q^name_^SL"></li>
								<li>
									<span>任务状态:</span>
									<select class="inputText" type="text" name="Q^status_^SL">
										<option value="">全部</option>
										<option value="NORMAL">普通任务</option>
										<option value="AGENT">代理任务</option>
										<option value="DELIVERTO">转交任务</option>
										<option value="TRANSFORMING">流转源任务</option>
										<option value="TRANSFORMED">接收流程任务</option>
										<option value="COMMU">通知任务</option>
										<option value="BACK">被驳回任务</option>
									</select>
								</li>
							</ul>
							<ul>
								<li><span>创建时间从:</span><input name="Q^create_time_^DL"
									class="inputText date" /></li>
								<li><span>至:</span><input name="Q^create_time_^DG"
									class="inputText date" /></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		loadGrid();
	});
	function loadGrid()
	{
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/flow/task/listJson",
	         idField:"id",
       		 sortName: 'create_time_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'subject',sortName:"subject", title: '任务标题',width: 300, align: 'left', sortable: 'true'},
	                     { field: 'name',sortName:"name_", title: '任务名称',width: 150, align: 'left', sortable: 'true'},
	                     { field: 'ownerName',sortName:"owner_id_", title: '所属人',width: 150, align: 'center', sortable: 'true'},
	                     { field: 'assigneeName',sortName:"assignee_id_", title: '执行人',width: 150, align: 'center', sortable: 'true'},
	                     { field: 'status',sortName:"status_", title: '类型', align: 'center', sortable: 'true'
	                    	 ,formatter:function(value,row,index){
	                    		  var arr=[{'key':'NORMAL','value':'普通','css':'red'},{'key':'ADDSIGN','value':'加签','css':'red'},{'key':'AGENT','value':'代理','css':'green'},{'key':'DELIVERTO','value':'转办','css':'red'},{'key':'TRANSFORMED','value':'接收流转','css':'red'},{'key':'COMMU','value':'沟通','css':'red'},{'key':'TRANSFORMING','value':'流转源','css':'green'},{'key':'BACK','value':'被驳回','css':'red'}];
	                    		  return Object.formatItemValue(arr,value);
                   		     }	 
	                      },
	                      { field: 'createTime',sortName:"create_time_" , title: '任务创建时间',width: 150,  align: 'center', sortable: 'true' ,formatter:dateTimeFormatter},
	                      { field: 'dueExpDate',title: '任务到期时间',width: 150,  align: 'center', sortable: 'true' ,formatter:dateTimeFormatter},
	                      { field: 'dueTaskTime',title: '期限状态', width: 180, align: 'center', sortable: 'true'
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
		                      			detailHtml2 += "<a class='fa fa-detail' onClick='openTaskDueTimeDetail(\""+row.id +"\",\""+row.name+"\")' herf='#' title='明细'></a>";
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
	 	                  { field: 'colManage',  title: '操作', width:80, align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+")' herf='#'>明细</a>";
				                  	  result += "<a class='btn btn-default fa fa-edit' onClick='taskDoNext("+row.id+")' herf='#'>处理</a>";
				                  return result;
			                  }
                  		  }
	          ]]
		}));   
	}   
	
	function openTaskDueTimeDetail(taskId,name){
		 var url=__ctx+"/flow/task/bpmTaskDueTime/bpmTaskDueTimeList?taskId="+taskId;
		 HT.window.openEdit(url,name+"--延期记录", 'View', 'grid', 500, 500, null, null, taskId, true);
	}
	
	function removeTask(id){
		var param = {
			id:id
		}
		$.post(__ctx+"/flow/task/remove",param,function(data){
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
	function showInfo(id,type){
		if(type == "user"){
			UserInfoDialog(id).show();
		}else{
			alert("类型："+type+"   ID:"+id);
		}
	
	}
	
	function showAssignee(value,row,index){
		var val = "";
		if(!row.identityList)
			return val;
		$.each(row.identityList,function(k,v){
			if(!v.name)
				return true;
			val +=  '<span class="owner-span"><a href="javascript:showInfo(\''+v.id+'\',\''+v.type+'\')">'+v.name+'</a></span>'; 
		});
		return val;
	}
	function doNext(id){
		$.openFullWindow('doNext?id='+id);
	}
	
	function openDetail(id)
	{
	    HT.window.openEdit('taskGet?id='+id,'查看任务', 'View', 'grid', 500, 500, null, null, id, true);
	}
	
	function taskDoNext(id){
		var url= __ctx + '/flow/task/isForbindden?taskId=' + id
		$.get(url, function(rtn){
			if(!rtn){
				$.openFullWindow("taskDoNext?id="+id);
			}else if (rtn==1){
				$.topCall.error("流程已经被禁止，请联系管理员！");
			}else if (rtn==2){
				$.topCall.error("任务不存在，可能已经被处理！");
			}else if (rtn==3){
				$.topCall.error("没有处理此任务的权限!");
			}
		});
	}
</script>