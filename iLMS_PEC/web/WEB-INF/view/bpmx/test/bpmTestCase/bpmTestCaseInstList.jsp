<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>流程实例</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript"  src="${ctx}/js/platform/flow/deleteListByAuthorize.js"></script>
</head>
<body>
	<div class="easyui-layout" style="position: fixed !important;" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/flow/instance/remove">
								<span>删除</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i
								class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span ht-tip title="流程实例标题">流程实例标题:</span><input
									type="text" class="inputText" name="Q^subject_^SL"></li>
								<li><span >实例状态:</span>
									<select class="inputText" name="Q^status_^S">
										<option value="">全部</option>
										<option value="draft">草稿</option>
										<option value="running">运行中</option>
										<option value="end">结束</option>
										<option value="manualend">人工结束</option>
								</select></li>
								<li><span>创建人:</span><input
									type="text" class="inputText" name="Q^creator_^SL"></li>
								<li><span>创建时间从:</span><input name="Q^create_time_^DL"
									class="inputText date" /></li>
								<li><span>至:</span><input name="Q^create_time_^DG"
									class="inputText date" /></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script>
	$(function(){
		loadGrid();
	});
	function openDetail(id)
	{
	    var url =  __ctx + '/flow/instance/instanceGet?id='+id;
		var index = top.layer.open({
			  type: 2,
			  title: "流程实例运行明细",
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:2005,
			  content: [url, 'yes'],
			  end: function(){
				  loadGrid();
			  }
			});
	}
	
	function openLog(id){
		var url = __ctx +  "/system/sysObjLog/sysObjLogGet?id="+id;
		
		var index = top.layer.open({
			  type: 2,
			  title: "查看流程未结束的原因",
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:2005,
			  content: [url, 'yes'],
			  end: function(){
				  loadGrid();
			  }
			});
		
	}
	
	
	
	function doNext(id){
		var index = top.layer.load(2); //换了种风格
		$.post(__ctx+"/bpmx/test/bpmTestCase/doNext",{id:id},function(result){
			// 启动流程成功
			if(result.result){
				top.layer.alert(result.message, {icon: 1}); 
			}else{
				top.layer.alert(result.message, {icon: 2}); 
			}
			//关闭
			top.layer.close(index);   
		}).error(function(res){
			top.layer.alert(res.responseJSON.message, {icon: 2}); 
			//关闭
			top.layer.close(index); 
		});
		
	}
	
	
	
	
	function loadGrid()
	{
		var url =  __ctx+"/flow/instance/listJson";
		url += "?Q^proc_def_key_^S="+$.getParameter("defKey")+"&Q^sys_code_^SLR=bpmx_test_";
		url = encodeURI(url);
		$('#grid').datagrid($.extend($defaultOptions,{
	         url:url,
	         method:"GET",
	         idField:"id",
       		 sortName: 'create_time_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true},
						{ field: 'subject',sortName:"subject", title: '流程实例标题',width: 250, align: 'left', sortable: 'true'},
	                     { field: 'procDefName',sortName:"proc_def_name_", title: '流程名称',width: 200, align: 'center', sortable: 'true'},
	                     { field: 'creator',sortName:"creator_", title: '实例创建人',width: 200, align: 'center', sortable: 'true'},
	                     { field: 'bizKey',sortName:"biz_key_", title: '业务主键', align: 'center', sortable: 'true'},
	                     { field: 'status',sortName:"status_", title: '实例状态', align: 'center', sortable: 'true'
	                    	 ,formatter:function(value,row,index){
	                    		  var arr=[{'key':'draft','value':'草稿','css':'red'},{'key':'pending','value':'挂起','css':'red'},{'key':'running','value':'运行中','css':'green'}
    		 						,{'key':'manualend','value':'人工结束','css':'red'},{'key':'revokeToStart','value':'撤销到发起人','css':'red'},{'key':'back','value':'驳回','css':'red'},{'key':'end','value':'结束','css':'red'}];
	                    		  return Object.formatItemValue(arr,value);
                   		     }	 
	                      },
	                      { field: 'isForbidden',sortName:"is_forbidden_", title: '是否挂起', align: 'center', sortable: 'true'
		                    	 ,formatter:function(value,row,index){
		                    		 var arr=[{'key':1,'value':'挂起','css':'red'},{'key':0,'value':'正常','css':'red'}];
		                    		 return Object.formatItemValue(arr,value);
	                   		     }	 
		                      },
	                      { field: 'endTime',sortName:"end_time_" , title: '实例结束时间',  align: 'center', sortable: 'true' ,formatter:dateTimeFormatter},
	                      { field: 'duration',sortName:"duration_" , title: '持续时间',  align: 'center', sortable: 'true' 
	  	                	  ,formatter:function(value){
	  	                		  return $.timeLag(value);
	  	                	  }
	  	              	  },
	                      { field: 'bpmnInstId',sortName:"bpmn_inst_id_", title: 'BPMN流程实例ID',  align: 'center', sortable: 'true'},
	 	                  { field: 'colManage',  title: '操作', width:80, align: 'center'
                  	    	 ,formatter:function(value,row,index){
				                  var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+")' herf='#'>明细</a>";
				                  
				                  if(row.status!="end"&& row.status!="manualend" ){
				                	  result += "<a class='btn btn-default fa fa-detail'  onClick='doNext("+row.id+")' herf='#'>继续运行</a>";
				                	  result += "<a class='btn btn-default fa fa-detail' onClick='openLog("+row.id+")' herf='#'>查看未正常结束流程的原因</a>";
				                  }
				                  
				                  return result;
			                  }
                  		  }
	          ]]
		}));      
	}   
	
</script>