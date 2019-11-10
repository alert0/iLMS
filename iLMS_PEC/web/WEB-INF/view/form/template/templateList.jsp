<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript"
	src="${ctx }/js/platform/form/copyTemplateDialog.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"> 重置</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;"
								onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-codepen" href="javascript:;"
								action="init" onClick="initFormTemplate();">
								<span>初始化模板</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> <i
								class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>模板名称:</span> <input type="text"
									class="inputText" name="Q^template_name_^SL"></li>
								<li><span>模板类型:</span> <select
									name="Q^template_type_^SL" class="inputText"
									value="${param['Q^template_type_^SL']}">
										<option value="">全部</option>
										<option value="main" <c:if test="${param['Q^template_type_^SL'] == 'main'}">selected</c:if>>主表模板</option>
										<option value="subTable" <c:if test="${param['Q^template_type_^SL'] == 'subTable'}">selected</c:if>>子表模板</option>
										<option value="macro" <c:if test="${param['Q^template_type_^SL'] == 'macro'}">selected</c:if>>宏模板</option>
										<option value="mobileMacro" <c:if test="${param['Q^template_type_^SL'] == 'mobileMacro'}">selected</c:if>>手机宏模板</option>
										<option value="mobileSub" <c:if test="${param['Q^template_type_^SL'] == 'mobileSub'}">selected</c:if>>手机子表</option>
										<option value="mobileMain" <c:if test="${param['Q^template_type_^SL'] == 'mobileMain'}">selected</c:if>>手机主表</option>
										<option value="dataTemplate" <c:if test="${param['Q^template_type_^SL'] == 'dataTemplate'}">selected</c:if>>业务数据模板</option>
								</select>
								<li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	
		</div>
	</div>
</body>
</html>
<script>
	function openDetail(id,action)
	{
	    var title=action=="templateEdit"?"编辑表单模板":action=="add"?"添加表单模板":"查看表单模板";
	    action=action=="add"?"templateEdit":action;
	    HT.window.openEdit(action+'?templateId='+id,title, action, 'grid', 500, 500, null, null, id, true);
	}
   	$(function(){//设置不能编辑的行不能选中
   		$(document).delegate("[datagrid-row-index]","hover",function(event){
   			var checkbox = $(this).find("input");
   			if(checkbox.attr("disabled")=="disabled"){
  				   checkbox.attr("checked",false);
   			}
   		});
   		loadGrid();
   	});
   	
   	function confirmCallback(rtn){
   		if(rtn){
			var form=new com.hotent.form.Form();
			form.creatForm('form', "init");
			form.submit();
		}
   	}
   
   	//处理初始化模板
    function initFormTemplate(){
  		 $.topCall.confirm("提示信息","初始化表单模板将会导致自定义模板信息丢失，确定初始化吗？",confirmCallback)
    }
   	function copyTemplate(templateId,templateName,alias){
   		CopyTemplateDialog({templateId:templateId,templateName:templateName,alias:alias}).show();
   	}
   	
   	function loadGrid()
	{
   		$('#grid').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/form/template/listJson",
	         idField:"templateId",
       		 sortName: 'template_id_',
	         sortOrder: 'desc',
	         columns: [[
						  { field: 'templateId',sortName:"template_id_",checkbox:true,  width: 250, align: 'center'},
						  { field: 'templateName',sortName:"template_name_", title: '名称', width:300 , align: 'left', sortable: 'true'},
	                      { field: 'alias',sortName:"alias_", title: '别名', width: 250, align: 'left', sortable: 'true'},
	                      { field: 'templateType',sortName:"template_type_" , title: '类型', width:100, align: 'center', sortable: 'true'
		 	                    ,formatter:function(value,row,index){
		 	                    	var arr=[{'key':'subTable','value':'PC子表模板','css':'green'},{'key':'main','value':'PC主表模板','css':'green'},{'key':'macro','value':'PC宏模板','css':'red'},
		 	                    	{'key':'mobileMacro','value':'Mobile宏模板','css':'red'},{'key':'mobileMain','value':'手机主表模板','css':'blue'},{'key':'mobileSub','value':'手机子表模板','css':'blue'},
		 	                    	{'key':'queryDataTemplate','value':'自定义SQL查询模版','css':'orange'},{'key':'dataTemplate','value':'业务数据模板','css':'green'}];		 	                    	
		 	                    	return Object.formatItemValue(arr,value); 
	 	                    	}
		 	                },
		 	                { field: 'canedit',sortName:"canedit_", title: '模板来源', width: 100, align: 'center', sortable: 'true'
		 	                	,formatter:function(value,row,index){
		 	                    	var arr=[{'key':'0','value':'系统模板','css':'red'},{'key':'1','value':'自定义模板','css':'green'}];
		 	                    	return Object.formatItemValue(arr,value); 
	 	                    	}
		                    },
	 	                    { field: 'colManage',  title: '操作',  align: 'center', sortable: 'true'
	                  	    	 ,formatter:function(value,row,index){
					                 var result ="";
					                 result += "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.templateId+",\"templateGet\")' href='#'>详细</a>"; 
				                	 result += "<a class='btn btn-default fa fa-copy' onclick='copyTemplate(\""+row.templateId+"\",\""+row.templateName+"\",\""+row.alias+"\")' href='#' >复制</a>";
					                 if(row.canedit==1){
				                	 	result += "<a class='btn btn-default fa fa-copyright' href='backUp?templateId="+row.templateId+"' >备份</a>";
					                	 result += "<a class='rowInLine btn btn-default fa fa-remove' action = '/form/template/remove?templateId="+row.templateId+"' href='#'>删除</a>";
						                 result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.templateId+",\"templateEdit\")' href='#'>编辑</a>"; 
					                 }
					                 return result;
				            	} 
                  			}
	          ]]
   		}));     
	}   
</script>