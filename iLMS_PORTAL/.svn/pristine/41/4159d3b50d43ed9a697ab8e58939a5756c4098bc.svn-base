<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>常用语</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout"   fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search apval-item" href="javascript:void(0);"><span>搜索</span></a> <a class="btn btn-sm  btn-primary fa-add apval-item" href="javascript:;" onclick="openDetail('','add')" href="javascript:;"><span>添加</span></a>
							<a class="btn btn-sm btn-primary fa-remove apval-item" href="javascript:void(0);" action="/flow/approvalItem/remove"><span>删除</span></a>
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
								<li><span>常用语:</span><input class="inputText" type="text" name="Q^EXPRESSION_^SL"></li>
								<c:if test="${param.type=='flow' }">
									<li><span>流程分类:</span> <select class="inputText" name="Q^TYPE_ID_^SL">
											<option value="">全部</option>
											<c:forEach items="${sysTypeList}" var="sysType">
												<option value="${ sysType.id}">${sysType.name }</option>
											</c:forEach>
									</select></li>
									<li><span>流程名称:</span><input class="inputText" type="text" name="Q^DEF_NAME_^SL"></li>
								</c:if>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid" ></div>
		
	</div>
</body>
</html>

<script>
	var isPersonal = '${param.type}'=='personal'?true:false;
	$(function(){
		loadGrid();
	});
	function openDetail(id,action)
	{
	    var title=action=="edit"?"编辑常用语":action=="add"?"添加常用语":"查看常用语";
	    action=action=="add"?"edit":action;
	    var type = '${param.type}';
	    var page = 'approvalItemEdit';
	    if(action=='get'){
	    	page = 'approvalItemGet';
	    }
	    HT.window.openEdit(page+'?id='+id+'&type='+type,title, action, 'grid', 500, 500, null, null, id, true);
	}
	function loadGrid(){
		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/flow/approvalItem/listJson?isPersonal="+isPersonal,
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         columns: [[
						{ field: 'id',sortName:"id_",checkbox:true,  width: 250, align: 'left'},
						{ field: 'expression',sortName:"EXPRESSION_", title: '常用语', width: 250, align: 'center', sortable: 'true',formatter:function(value,row,index){
							 if(value&&value.length>this.width/11.9){
								   return "<abbr ht-tip title='"+value+"'><span>"+value+"</span></abbr>"; 
							   }else{
								   return value;
							   }
							}
						},
	                     { field: 'typeId',sortName:"TYPE_ID_" , title: '作用范围', width:100,hidden:true, align: 'center', sortable: 'true'
	 	                    ,formatter:function(value,row,index){
	 	                    	if(row.type=='1') return "系统全局"; 
	 	                    	else if(row.type == '2') return row.typeId; 
	 	                    	else if(row.type == '3') return row.defKey; 
	 	                    	else return "个人常用语";  
 	                    	}
	 	                 },
	 	                
	                     { field: 'defName',sortName:"DEF_NAME_" , title: '流程/流程分类名称',hidden:isPersonal, width:100, align: 'center', sortable: 'true'
	 	                 },
	 	                { field: 'type',sortName:"TYPE_" , title: '常用语类型', width:100,hidden:isPersonal, align: 'center', sortable: 'true'
	 	                    ,formatter:function(value,row,index){
	 	                	   var arr=[{key:'1',value:'系统全局',css:'red'},{key:'2',value:'流程分类',css:'green'},
	 	               				{key:'3',value:'流程定义',css:'green'},{key:'4',value:'个人常用语',css:'black'}];
	 	                	  return Object.formatItemValue(arr,value);
	 	                    }
	 	                 },
	 	                {field : 'colManage',title : '操作',align : 'center',formatter:function(value,row,index){
				                  var result = "<a class='btn btn-default fa fa-edit' onclick='openDetail(\""+row.id+"\",\"edit\")' href='#'>编辑</a>"; 
				                  result += "<a class='btn btn-default fa fa-eye' onclick='openDetail(\""+row.id+"\",\"get\")' href='#'>查看</a>"; 
				                  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/flow/approvalItem/remove?id="+row.id+"' href='#'>删除</a>";
				                 return result;
			                  }
                 		  }
	          ]]
		}));     
	}   
</script>