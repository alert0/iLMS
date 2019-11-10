<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
<head>
	<title>办结事宜</title>
	<%@include file="/commons/include/list.jsp" %>
	<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
	
	<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;background:#fff">
		<div id="sysTypeTree" class="ztree" style="height:100%;" ></div>	
	</div>
	<div data-options="region:'center',border:false"  >
			<div  id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons"> 	
					        <a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
			    		 
			    		<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
			    		</div>
			    		<div class="tools">
							<a href="javascript:void(0);" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body" >
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>请求标题:</span><input class="inputText" type="text" name="Q^subject_^SL"/></li>
								<li><span>流程名称:</span><input class="inputText" type="text" name="Q^proc_def_name_^SL"/></li>
								<li><span>状态:</span>
									<select name="Q^wfInst.status_^SL">
									    <option value="">请选择</option>
										<option value="manualend">人工结束</option>
										<option value="end">结束</option>
									</select>
									<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								</li>
								
							</ul>	
							<ul>

								<li><span>创建时间从:</span><input  name="Q^wfInst.create_time_^DL"  class="inputText date" /></li>
								<li><span>至:</span><input  name="Q^wfInst.create_time_^DG" class="inputText date" /></li>
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
	
	
	function openDetail(id)
	{
	    var title="查看办结事宜";
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
	        url: __ctx+"/office/receivedProcess/completedJson",
	        sortName: 'wfInst.create_time_',
	        sortOrder: 'desc',
	        columns: [[
					{ field: 'subject',sortName:"subject_", title: '标题', width: 350, align: 'left', sortable: 'true',
						formatter:function(value,row,index) {
					        return "<span class=\"cur\" onClick=\"openDetail("+row.id+")\" >"+value+"</span>";
					    }
					 },
					 { field: 'procDefName',sortName:"proc_def_name_", title: '流程名称', width: 200, align: 'center', sortable: 'true' },
	                { field: 'createTime',sortName:"create_time_" , title: '创建时间', width:150, align: 'center', sortable: 'true' 
	                    ,formatter:dateTimeFormatter    
	                },
	                { field: 'duration',sortName:"duration_" , title: '持续时间', width:150, align: 'center', sortable: 'true' ,
	                	formatter:function(value){
	                		return $.timeLag(value);
	                	}
	                },
	                { field: 'status',sortName:"status_" , title: '流程状态',  align: 'center', sortable: 'true'
	                    ,formatter:function(value,row,index){
	                	   var arr=[{key:'draft',value:'草稿',css:'red'},{key:'running',value:'运行中',css:'green'},{key:'end',value:'结束',css:'red'},{key:'manualend',value:'人工结束',css:'red'}];
	                	   return Object.formatItemValue(arr,value);
	                    }
	                },
	                { field: 'colManage',  title: '操作', width:100, align: 'center'
                        ,formatter:function(value,row,index){
                    	   var result='<a class="btn btn-default fa fa-detail" onClick="toCopyTo('+row.id+');" herf="#">转发</a>';
                    	   return  result;
                        }
	                }
	        ]]
	    }));    
	}
</script>