<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<%@include file="/commons/include/list.jsp"%>
		<script type="text/javascript">
			function serviceCall(id){
				var dialog = {};
    			var def = {
    				passConf : id,
    				title : '调用服务',
    				width : 800,
    				height : 600,
    				modal : true,
    				resizable : false,
    				iconCls : 'icon-collapse'    				
    			};

    			$.topCall.dialog({src : __ctx+ '/system/serviceSet/serviceSetInvoke',
    							  base : def});
			}
			
			 function manager(value,row,index){
				 var result ="";
				 result += "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"serviceSetEdit\")' href='#'>编辑</a>"; 
		         result += "<a class='btn btn-default fa fa-detail' onClick='serviceCall("+row.id+")' href='#'>调用</a>"; 
				 return result;
			 }
			
			function openDetail(id,action){
			    var title=action=="add"?"添加webService接口":"编辑webService接口";
			    action=action=="add"?"serviceSetEdit":action;
			   	var url=action+'?id='+id;
			    HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
			}
			
			function reload (){
				$('#grid').datagrid('reload');
			}

		</script>
	</head>
<body>
	<div class="easyui-layout" fit="true" >
				<!-- 顶部查询操作 -->
			    <div  class="toolbar-search ">
						<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons"> 		
								<a class="btn btn-primary btn-sm fa-search"  href="javascript:;" ><span>搜索</span></a>
								<a class="btn btn-primary btn-sm fa-add"    onclick="openDetail('','add')"><span>添加</span></a>
						        <a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="/system/serviceSet/remove">删除 </a>
						        <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
									<i class=" fa  fa-angle-double-up"></i>
								</a>
							</div>
						</div>
						<div class="toolbar-body" >
								<form  id="searchForm" class="search-form" >
									<ul>
										<li><span>别名:</span><input type="text" class="inputText" name="Q^alias_^SL"></li>
										<li><span>wsdl&nbsp;地址:</span><input type="text" class="inputText" name="Q^url_^SL"></li>
									</ul>
								</form>
						</div>
	
				</div>
				<!-- 顶部查询操作end -->
				<table id="grid" class="easyui-datagrid my-easyui-datagrid" idField="id" data-options="fitColumns:true,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit=true 
					 url="${ctx}/system/serviceSet/listJson">	 
				    <thead>
					    <tr>
					    	<th field="id" checkbox="true" class="pk" ></th>
					    	<th field="name" sortable="true" sortName="name_" width="100">名称</th>
							<th field="alias" sortable="true" sortName="alias_" width="100">别名</th>
							<th field="url" sortable="true" sortName="url_" width="100">wsdl&nbsp;地址</th>
							<th field="methodName" sortable="true" sortName="methodName_" width="100">名称空间</th>
							<th field="namespace" formatter="manager" >操作</th>
					    </tr>
				    </thead>
			    </table>
	</div>
</body>

</html>