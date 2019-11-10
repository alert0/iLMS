<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<script type="text/javascript">
		function getScript(){
			var records  = $('#identityList').datagrid('getChecked');
			if(!records ||records.length ==0){ $.topCall.warn("未选择任何流水号！"); return}
			
			var scripts = "";
			$(records).each(function(i){
				if(i>0)	scripts = scripts  +",";
				
				scripts = scripts+ this.name;
			});

			return scripts;
		}
	
	</script>
	<body class="easyui-layout">
		<div data-options="region:'center',border:false" >
			<!-- 顶部查询操作 -->
			 <div  class="toolbar-search col-md-13 ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">		
							<a class="btn btn-primary fa-search"  href="javascript:;" ><span>搜索</span></a>
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
								<li><span>流水号名称:</span><input type="text" class="inputText" name="Q^NAME_^SL"></li>
								<li><span>流水号别名:</span><input type="text" class="inputText" name="Q^alias_^SL" style="width: 170px;"></li>
								<li><span>生成类型:</span><select name="Q^gen_type_^SL"
									class="inputText">
										<option value="">请选择...</option>
										<option value="1">每天生成</option>
										<option value="0">递增</option>
								</select></li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<!-- 顶部查询操作end -->
			<table id="identityList" class="easyui-datagrid" idField="id" data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit=true 
	 			url="${ctx}/system/identity/listJson">
			    <thead>
				    <tr>
				    	<th field="id" checkbox="true" class="pk"></th>
						<th field="name" sortable="true" sortName="NAME_" title="名称"></th>
						<th field="alias" sortable="true" sortName="alias_" title="别名"></th>
					 	<th field="genType" sortable="true" sortName="gen_type_" title="生成类型" formatter="ht" dataFormat="[{key:'1',value:'每天生成'},{key:'0',value:'递增'}]"></th> 
					 	<th field="regulation" sortable="true" sortName="regulation_" title="规则"></th> 
				    </tr>
			    </thead>
		    </table>
		    
		</div>
	</body>
</html>