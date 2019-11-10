<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
</head>
<body class="easyui-layout"
	style="overflow-y: hidden; border: 1px solid #959DA4" fit="true"
	scroll="no">
	<div data-options="region:'center',border:false">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-box border">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a href="javascript:void(0)"
							class="btn btn-primary btn-sm fa-search"><span>搜索</span></a>
						<a href="javascript:void(0);" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
					</div>
					<!-- <div class="tools">
						<a href="javascript:;" class="collapse"> <i class="fa  fa-angle-double-up"></i>
						</a>
					</div> -->
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul>
							<li><span>流程名称:</span><input class="inputText" type="text"
								name="Q^name_^SL"></li>
							<li><span>流程状态:</span> <select name="Q^status_^SL"
								class="inputText">
									<option value="">请选择...</option>
									<option value="deploy">已发布</option>
<!-- 									<option value="draft">草稿</option> -->
									<option value="forbidden">禁用</option>
									<option value="forbidden_instance">禁用实例</option>
<!-- 									<option value="deleted">删除</option> -->
							</select></li>
							<li><span>变更理由:</span><input class="inputText" type="text"
								name="Q^reason_^SL"></li>
							<br>
							<li><span>创建时间 从:</span><input name="Q^create_time_^DL"
								class="inputText date" /></li>
							<li><span>至: </span><input name="Q^create_time_^DG"
								class="inputText date" /></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
			<div id="dataGridList" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		loadGrid();
	});
	function switchMainVersion(defId){
		var  datagrid =  $("#dataGridList");
		var url =  __ctx+"/flow/def/switchMainVersion?defId="+defId;
		$.post(url,function(responseText){
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				$.topCall.toast('提示信息','设置主版本成功,将重新加载!');
				datagrid.datagrid("clearSelections");//清除选中的
				datagrid.datagrid("reload");
				window.parent.location= __ctx+"/flow/def/defDetail?defId="+defId;
			}else{
				$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
			}
		 
	   });
	
		
	}
	
	
	function removeBpmDef(defId,defKey){
		var  datagrid =  $("#dataGridList");
		var url =  __ctx+"/flow/def/removeByDefIds?defId="+defId;
		$.topCall.confirm('提示信息','确认删除吗？',function(r){
			if (r){
				$.post(url,function(responseText){
					var resultMessage=new com.hotent.form.ResultMessage(responseText);
					if(resultMessage.isSuccess()){
						$.topCall.toast('提示信息',resultMessage.getMessage());
						url =  __ctx+"/flow/def/versions?defKey="+defKey;
						datagrid.datagrid({'url':url});
						datagrid.datagrid("clearSelections");//清除选中的
						datagrid.datagrid("reload");
					}else{
						$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
					}
				 
			    });
			}
		});
	}
	
	function openDetail(defId)
	{ 
	    var href="${ctx}/flow/def/defGet?defId="+defId
	    var title="流程历史详细";

	    HT.window.openEdit(href,title, "edit", 'grid', 850, 350, null, null, defId);
	}
	function loadGrid()
	{
		$('#dataGridList').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/flow/def/versions?defId=${param.defId}",
	         idField:"defId",
       		 sortName: 'def_id_',
	         sortOrder: 'desc',
	         striped:true,
	         frozenColumns: [[
						  { field: 'defId',sortName:"def_id_",checkbox:true,  width: 250, align: 'center'
						  },
	                      { field: 'name',sortName:"name_", title: '流程名称', width:350 , align: 'center', sortable: 'true'
	                      }
         				]],
	         columns: [[
	                     { field: 'defKey',sortName:"def_key_", title: '流程Key', width: 150, align: 'center', sortable: 'true'},
	                     { field: 'desc',sortName:"desc_", title: '流程描述', width: 150, align: 'center', sortable: 'true'},

	                     { field: 'status',sortName:"status_" , title: '状态', width:100, align: 'center', sortable: 'true'
		 	                    ,formatter:function(value,row,index){
		 	                    	var arr=[{'key':'draft','value':'草稿','css':'red'},{'key':'deploy','value':'已发布','css':'green'},{'key':'forbidden','value':'禁用','css':'red'},{'key':'forbidden_instance','value':'禁止实例','css':'red'}];
		 	                    	return Object.formatItemValue(arr,value);  
	 	                    	}
		 	                },
	 	                 { field: 'testStatus',sortName:"test_status_", title: '生产状态', width: 100, align: 'center', sortable: 'true'
	 	                	,formatter:function(value,row,index){
	 	                    	var arr=[{'key':'test','value':'测试','css':'red'},{'key':'run','value':'正式','css':'green'}];
	 	                    	return Object.formatItemValue(arr,value);   
 	                    	}
	                     },
	                     { field: 'version',sortName:"version_", title: '版本号'	, align: 'center', sortable: 'true'},
	                     { field: 'reason',sortName:"reason", title: '变更理由', width: 150, align: 'center', sortable: 'true'},
		                    { field: 'isMain',sortName:"isMain", title: '是否主版本', width: 150, align: 'center', sortable: 'true'
		        	 			,formatter:function(value){
		        	 				if(value == 'Y'){
		        	 					return "主版本"
		        	 				}else{
		        	 					return "非主版本"
		        	 				}
		        	 			}
	                     },
	                     { field: 'colManage',  title: '操作', width:80, align: 'center', sortable: 'true'
                  	    	 ,formatter:function(value,row,index){
				                 var result = "<a class='btn btn-default fa fa-remove' onClick= 'removeBpmDef("+row.defId+",\""+row.defKey+"\")' href='#' >删除</a>";
				                 if (row.designer == 'FLASH') {
				                	 result += "<a class='btn btn-default fa fa-edit' onClick='$.openFullWindow(\""+__ctx+"/flow/def/defDesign?defId="+row.defId+"\")' href='#'>设计</a>";				 				}
				 				 if (row.designer == 'WEB') {
				 					result += "<a class='btn btn-default fa fa-edit' onClick='$.openFullWindow(\"${ctx}/bpm-editor/modeler.html?defId=" + row.defId + "\")' href='#'>设计</a>";
				 				 }
				                 
				                 result += "<a class='btn btn-default fa fa-detail' onclick='openDetail("+row.defId+")' href='#'>明细</a>";
				                 
                  	    		 if(row.isMain=="N"){
                  	    			 result += "<a class='btn btn-default fa fa-detail' onClick='switchMainVersion("+row.defId+")' herf='#'>设置为主版本</a>"
                  	    		 }
                  	    		 return result;
			                  }
                  		 }
	          ]],
	          onLoadSuccess: function () {
	        	  handGridLoadSuccess();
	        	  $('.datagrid-view').height($('.datagrid-view').height()-40);//防止因toolbar-panel导致datagrid高度计算错误，分页信息无法显示
	          }
	     }));     
	}   
</script>