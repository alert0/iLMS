<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理授权</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary  btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a class="btn btn-primary btn-sm fa-add" href="javascript:;" onClick='openDetail("","${isMgr==1?'agentEditMgr':'agentEdit' }")'><span>添加</span></a>
							<a class="btn btn-primary btn-sm fa-remove" href="javascript:;" action="/flow/agent/remove"><span>删除</span></a> 
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>标题:</span><input class="inputText" type="text" name="Q^subject_^SL"></li>
								<c:if test="${isMgr==1 }">
									<li><span>授权人姓名:</span><input class="inputText"
										type="text" name="Q^auth_name_^SL"></li>
								</c:if>
								<li><span>是否有效:</span><select class="inputText"
									name="Q^is_enabled_^SL">
										<option value="">请选择...</option>
										<option value="Y">有效</option>
										<option value="N">禁用</option>
								</select></li>
								<li><span>代理类型:</span><select class="inputText"
									name="Q^type_^SN">
										<option value="">请选择...</option>
										<option value="1">全权</option>
										<option value="2">部分</option>
										<option value="3">条件</option>
								</select></li>
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
	function openDetail(id,action)
	{
	    var title = "添加代理";
	    if(id){
	    	title = action=="agentEdit" ? "编辑代理":"查看代理";
	    }
	    action=action=="add"?"edit":action;
	    HT.window.openEdit(action+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function loadGrid(){
		var isMgr=${isMgr}==1;
		var action=isMgr?"agentEditMgr":"agentEdit";
		var url=isMgr?__ctx+"/flow/agent/listJsonMgr" : __ctx+"/flow/agent/listJson";
		
		$('#grid').datagrid($.extend($defaultOptions,{
	         url:url,
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'left'
						},
						{ field: 'subject',sortName:"subject_", title: '标题', width: 250, align: 'center', sortable: 'true'
						},
						 { field: 'authName',sortName:"auth_name_", title: '授权人姓名', width: 250, align: 'center', sortable: 'true'
						 },
						 { field: 'agent',sortName:"agent_", title: '代理人', width: 250, align: 'center', sortable: 'true' 
							 ,formatter:function(value,row){
								 if(row.type == "3"){
									 return "条件代理暂时无法计算出代理人";
								 }
								 else{
									 return value;
								 }
							 }
						 },
			             { field: 'startDate',sortName:"start_date_" , title: '开始日期', width:150, align: 'center', sortable: 'true' ,formatter:dateTimeFormatter
						 },
						 { field: 'endDate',sortName:"end_date_" , title: '结束日期', width:150, align: 'center', sortable: 'true' ,formatter:dateTimeFormatter
						 },
	                     { field: 'isEnabled',sortName:"is_enabled_" , title: '是否有效', width:100, align: 'center', sortable: 'true'
	 	                    ,formatter:function(value,row,index){
	 	                    	var arr=[{key:'Y',value:'有效',css:'green'},{key:'N',value:'禁用',css:'red'}];
	 	                    	return Object.formatItemValue(arr,value);
 	                    	}
	 	                 },
	 	                 { field: 'type',sortName:"type_" , title: '代理类型', width:100, align: 'center', sortable: 'true'
	 	                    ,formatter:function(value,row,index){
	 	                    	if(value =='1'){
	 	                    		return "全权代理";
	 	                    	}
	 	                    	if(value =='2'){
	 	                    		return "部分代理";
	 	                    	}
	 	                    	if(value =='3'){
	 	                    		return "条件代理";
	 	                    	}
	 	                    }
	 	                  },
	 	                  { field: 'colManage',  title: '操作',  align: 'center'
                  	    	  ,formatter:function(value,row,index){
				                  var result ="<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\""+action+"\")' herf='#'>编辑</a>";
				                  	  result += "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"agentGet\")' herf='#'>详细</a>";
				                  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/agent/remove?id="+row.id+"' herf='#'>删除</a>";
				                  return result;
			                   }
                  		  }
	          ]]
		}));     
	}   
</script>