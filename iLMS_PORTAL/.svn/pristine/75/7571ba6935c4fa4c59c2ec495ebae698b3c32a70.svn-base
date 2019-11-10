<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<script type="text/javascript">
		function getScript(){
			var records  = $('#userGridList').datagrid('getChecked');
			if(!records ||records.length ==0){ $.topCall.warn("尚未选择任何一条脚本！"); return}
			
			var scripts = "";
			$(records).each(function(i){
				if(i>0)	scripts = scripts  +"," ;
				scripts = scripts+ this.script;
			});

			return scripts;
		}
		var parentFlowKey = "${param.parentFlowKey}";
	</script>
	<body class="easyui-layout">
		<div data-options="region:'center',border:false" >
			<!-- 顶部查询操作 -->
			 <div  class="toolbar-search col-md-13 ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">		
							<a class="btn btn-primary btn-sm fa-search"  href="javascript:;" ><span>搜索</span></a>
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
								<li><span>名称:</span><input type="text" class="inputText" name="Q^NAME_^SL"></li>
								<li><span>分类:</span>
								<select class="inputText" name="Q^CATEGORY_^SL">
									<option  value="">请选择</option>
									<c:forEach items="${categoryList}" var="categrory">
										<option value="${categrory}">${categrory}</option> 
									</c:forEach>
								</select>
								</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<!-- 顶部查询操作end -->
			<table id="userGridList" class="easyui-datagrid my-easyui-datagrid" idField="id" data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit="true" 
	 			url="${ctx}/system/script/listJson">
			    <thead>
				    <tr>
				    	<th field="id" checkbox="true" class="pk"></th>
						<th field="name" width='300' sortable="true" align = 'center' sortName="NAME_" title="名称">名称</th>
					 	<th field="category" sortable="true" width='100'   align = 'center' sortName="CATEGORY_" title="脚本分类">脚本分类</th> 
					 	<th field="memo" sortable="true" sortName="memo_" width='300'  align = 'center' title="备注">备注</th> 
				    </tr>
			    </thead>
		    </table>
		    
		</div>
	</body>
</html>