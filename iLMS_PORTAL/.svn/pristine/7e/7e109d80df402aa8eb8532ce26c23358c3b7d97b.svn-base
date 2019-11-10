<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/formList.js"></script>
</head>
<body style="overflow-y: hidden">
	<!-- 顶部按钮   begin-->
	<div class="toolbar-panel col-md-13 text-left">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary btn-sm fa-close" onclick="closeWindow()"><span>关闭</span></a>
		</div>
	</div>
	<!-- 顶部按钮   end-->
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="formVersions" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		loadGrid();
	});
	
	function closeWindow (){
		window.parent.reload();
		HT.window.closeEdit();
	}
	function removeForm(id){
		var param = {
			id:id
		}
		$.post(__ctx+"/form/form/remove",param,function(data){
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
		$('#formVersions').datagrid($.extend($defaultOptions,{
	         url: __ctx+"/form/form/listVersions?formKey=${formKey}&formType=${formType}",
	         idField:"id",
       		 sortName: 'id_',
	         sortOrder: 'desc',
	         striped:true,
	         columns:[[
						 { field: 'id',sortName:"id_",checkbox:true,  width: 300, align: 'left'
						 },
	                     { field: 'name',sortName:"name_", title: '名称', width: '20%', align: 'center', sortable: 'true'
	                     },
	          
						 { field: 'version',sortName:"version_", title: '版本号', width: '20%', align: 'center', sortable: 'true'},
						 { field: 'isMain',sortName:"is_main_", title: '是否主版本', width: '20%', align: 'center', sortable: 'true'
							 ,formatter:function(value){
								 if(value=="Y"){
									 return "是";
								 }else
									 return "否";
							 }
						 },
						 { field: 'status',sortName:"status_", title: '状态', width: '20%', align: 'center', sortable: 'true' 
							 ,formatter:function(value,row){
								   var arr=[{'key':'draft','value':'草稿','css':'red'},{'key':'deploy','value':'已发布','css':'green'},{'key':'forbidden','value':'禁用','css':'red'}];
	 	 	                	   var result="";
	 	 	                	   var tempalet="<span class='{0}'>{1}</span>"
	 	 	                	   $.each(arr,function(n,item){
	 	 	                	       if(item.key==value){result=Object.toStringFormat(tempalet,item.css,item.value);}
	 	 	                	   });
	 	 	                	   return  result; 
							 }
						 },
						 { field: 'colManage', title: '操作', align: 'center', sortable: 'true' ,width: '20%'
	                    	 ,formatter:function(value,row,index){
	                    		 var result = "<a class='btn btn-default  fa fa-th-large' onclick='$.openFullWindow(\"formEdit?id="+row.id+"\")' href='#'>编辑表单</a>&nbsp;&nbsp;&nbsp;"; 
	                    		 
	                    		 if(row.status != 'draft'){
				                	 result += "<a class='btn btn-default  fa fa-add' onclick='newVersion(\""+row.id+"\")'  href='#'>新建版本</a>&nbsp;&nbsp;&nbsp;"; 
				                 }
	                    		 
	                    		 if(row.status == 'draft'){
				                	 result += "<a class='btn btn-default  fa fa-bookmark-o' onclick='publishForm(\""+row.id+"\")'  href='#'>发布</a>&nbsp;&nbsp;&nbsp;"; 
				                 }
	                    		 
	                    		 if(row.isMain =='N'){
					                 result += "<a class='btn btn-default fa fa-reply-all'href='setDefaultVersion?id="+row.id+"&formKey="+row.formKey+"'>设为主版本</a>";
					                 result += "<a class='rowInLine btn btn-default  fa fa-remove' action='/form/form/remove?id="+row.id+"' href='#'>删除</a>";
				                 }
	                    		 result +="<a class='rowInLine btn btn-default  fa fa-eye' target='_blank' href='${ctx}/form/form/preview?id="+row.id+"'>预览</a>";
	                    		 return result;
                   		     }	 
	                      }
	          ]],
	          onLoadSuccess: function () {
	        	  //此代码在linkButtons.js中。
	        	  handGridLoadSuccess();
	          }	
	     }));     
	} 
</script>
