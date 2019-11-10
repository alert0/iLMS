<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/platform/flow/deleteListByAuthorize.js"></script>
<style>
.search-form {
	margin-left: 5px;
}
</style>
</head>
<body>
	<div class="easyui-layout" style="position: fixed !important;"
		fit="true" scroll="no">
	<div data-options="region:'center',border:false"
		style="border-left: 1px solid #eee">
			<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-box border">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a href="javascript:void(0);"
							class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>  
					<a href="javascript:void(0);" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
					
					
					</div>
					<!-- <div class="tools">
						<a href="javascript:;" class="collapse"> <i
							class="fa  fa-angle-double-up"></i>
						</a>
					</div> -->
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul>
							<li><span ht-tip title="流程实例标题">流程实例标题:</span><input
								type="text" class="inputText" name="Q^subject_^SL"></li>
							<li><span>实例状态:</span> <select class="inputText"
								name="Q^status_^SL">
									<option value="">全部</option>
									<option value="draft">草稿</option>
									<option value="running">运行中</option>
									<option value="pending">挂起</option>
									<option value="end">结束</option>
									<option value="manualend">人工结束</option>
							</select></li>
							<li><span>创建人:</span><input type="text" class="inputText"
								name="Q^creator_^SL"></li>
						</ul>
						<ul>
							<li><span>创建时间 从:</span><input name="Q^create_time_^DL"
								class="inputText date" /></li>
							<li><span>至:</span><input name="Q^create_time_^DG"
								class="inputText date" /></li>
						</ul>
					</form>
				</div>
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
	function openDetail(id)
	{
	    HT.window.openEdit(__ctx+'/flow/instance/instanceGet?id='+id,'查看流程实例', 'View', 'grid', 500, 500, null, null, id, true);
	}
	function removeInstance(id){
		var param = {
			id:id
		}
		$.post(__ctx+"/flow/instance/remove",param,function(data){
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
	         url: encodeURI(__ctx+"/flow/instance/listJson?Q^proc_def_id_^S=${param.defId}"),
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
	         			{ field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'center'
						 },
	                     { field: 'subject',sortName:"subject_", title: '流程实例标题', width:350 , align: 'center', sortable: 'true'
	                    	 ,formatter:function(value,row,index)
	                         {
	                             return "<span class=\"cur\" href='"+__ctx+"/flow/instance/instanceGet?id='"+row.id+" >"+value+"</span>";
	                         }
	                     },
	                     { field: 'bizKey',sortName:"biz_key_", title: '关联数据业务主键', align: 'center', sortable: 'true'},
	                     { field: 'status',sortName:"status_", title: '实例状态', align: 'center', sortable: 'true'
	                    	 ,formatter:function(value,row,index){
	                    		  var arr=[{'key':'draft','value':'草稿','css':'red'},{'key':'pending','value':'挂起','css':'red'},{'key':'running','value':'运行中','css':'green'}
    		 						,{'key':'manualend','value':'人工结束','css':'red'},{'key':'revokeToStart','value':'撤销到发起人','css':'red'}];
	                    		  return Object.formatItemValue(arr,value);
	                    		  
                   		     }	 
	                      },
	                      { field: 'endTime',sortName:"end_time_" , title: '实例结束时间',  align: 'center', sortable: 'true' ,formatter:dateTimeFormatter},
	                      { field: 'duration',sortName:"duration_" , title: '持续时间',  align: 'center', sortable: 'true' 
	  	                	  ,formatter:function(value){
	  	                		  return $.timeLag(value);
	  	                	  }
	  	              	  },
	                      { field: 'resultType',sortName:"result_type_", title: '审批结果',  align: 'center', sortable: 'true'
	                    	  ,formatter:function(value,row,index){  
	  	              		  	  var arr=[{'key':'agree','value':'审批通过','css':'green'},{'key':'oppose','value':'审批不通过','css':'red'},{'key':'uncheck','value':'未审批','css':'red'}
              		 					,{'key':'pass','value':'审批通过','css':'green'},{'key':'refuse','value':'审批不通过','css':'red'},{'key':'manualend','value':'人工结束','css':'red'}];
	  	              		  		return Object.formatItemValue(arr,value);
	                    	  }
	                      },
	                      { field: 'bpmnInstId',sortName:"bpmn_inst_id_", title: 'BPMN流程实例ID',  align: 'center', sortable: 'true'},
	 	                  { field: 'colManage',  title: '操作',  align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+")' herf='#'>明细</a>";
				                  if(row.authorizeRight.instanceDel=="Y"){
				                	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/instance/remove?id="+row.id+"' herf='#'>删除</a>";
				                  }
				                  return result;
			                  }
                  		  }
	          ]],
	          onLoadSuccess:function(){
	        	  $('.datagrid-view').height($('.datagrid-view').height()-40);//防止因toolbar-panel导致datagrid高度计算错误，分页信息无法显示
	          }
	     }));     
	}   
</script>