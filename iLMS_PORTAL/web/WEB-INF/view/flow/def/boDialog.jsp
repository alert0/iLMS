<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript">
		var isSingle = window.isSingleSelector;
		var boSaveMode="${param.boSaveMode}";
		$(function() {
			//回显数据
			initBoData();
			//初始化快速查找
			initQuickFind();
			if(boSaveMode=="database")
			$('#boDialog').datagrid('options').url="${ctx}/bo/def/listJson?Q^data_type_^SL=json&Q^is_create_table_^S=Y"
			 
		});
		
		function initBoData(){
			var conf = window.passConf;
	  	    if(conf){      //上级窗口的传过来的参数接收方法 
	  	    	var aryBo = conf.aryBo;
	  	    	if(aryBo){
	  	    		for(var i=0;i<aryBo.length;i++){
		  	    		$('#boSelectGrid').datagrid('appendRow',aryBo[i]);	
					}
	  	    	}	
	  	    }
		}
		
		//初始化点击列表事件
		var onClickRow = function(rowIndex,rowData){
			var records  = $('#boSelectGrid').datagrid('getRows');
			if(isSingle){
				if(records.length == 1)		
				$('#boSelectGrid').datagrid('deleteRow',0);
			}
			for(var i=0;i<records.length;i++){
				if(records[i]['id']==rowData['id']){
					return; 
				}
			} 
			$('#boSelectGrid').datagrid('appendRow',rowData);		
		}
		
		//快速查找
		function initQuickFind(){
			$("input.quick-find").bind('keyup',function(){
				var str = $(this).val();
				if(!str)return;
				var records  = $('#boSelectGrid').datagrid('getRows');
				for(var i=0;i<records.length;i++){
					if(records[i]['desc'].indexOf(str)>-1){
						var rowData = records[i];
						 $('#boSelectGrid').datagrid('deleteRow',i);
						 $('#boSelectGrid').datagrid('insertRow',{index:0, row: rowData});
					}
				} 
			});
		}
		
		function deleteSelect(id,name){
			var records  = $('#boSelectGrid').datagrid('getRows');
			for(var i=0;i<records.length;i++){
				if(records[i]['id']==id){
					 $('#boSelectGrid').datagrid('deleteRow',i);
				}
			}
		}
		</script>
		<style>
			.panel-fit .panel.datagrid[style='width: auto;'] .datagrid-wrap.panel-body.panel-body-noheader {
				border-left:none;
				border-right:none;
				border-bottom:none;
				width: inherit !important;
			}
			.panel-fit .panel.datagrid[style='width: auto;'] .datagrid-wrap.panel-body.panel-body-noheader>.datagrid-view{
				width: inherit !important;
			}
			
			.datagrid-body table{ width:100%;}
		</style>
	</head>
	<body  class="easyui-layout">
		<div data-options="region:'center',border:false"  >
				<!-- 顶部查询操作 -->
			    <div  class="toolbar-search col-md-13 ">
					<div class="toolbar-box border">
						<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons"> 		
								<a class="btn btn-sm  btn-primary fa-search"  href="javascript:;" ><span>搜索</span></a>
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
										<li>
											<span>名称:</span>
											<input type="text" class="inputText"  name="Q^name_^SL">
										</li>
										<li>
											<span>描述:</span>
											<input type="text" class="inputText"  name="Q^desc_^SL">
										</li>
									</ul> 
							</form>
						</div> 
				    </div>
				</div>
				<table id="boDialog" class="easyui-datagrid"  data-options="fitColumns:false,onClickRow:onClickRow,singleSelect:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit=true 
					 url="${ctx}/bo/def/listJson?Q^data_type_^SL=json">
				    <thead>
					    <tr>
							<th field="name" sortable="true" sortName="name_" >名称</th>
							<th field="desc" sortable="true" sortName="desc_" >描述</th>
							<th field="code" sortable="true" sortName="code_" >编码</th>
							<th field="packagePath" sortable="true" sortName="package_" >路径</th>
							<th field="dataType" >数据类型</th>
							
							<th field="isCreateTable" sortable="true" sortName="is_create_table_" 
								formatter="ht" title="是否已生成表" dataFormat="[{key:'Y',value:'是',css:'green'},{key:'N',value:'否',css:'red'}]"></th>
							
							<th field="status" sortable="true" sortName="status_" formatter="ht" title="状态">
								function(value,row,index){
							    	if(value=='actived')
										return "激活";
									if(value=='inactive')
										return "未激活";
									if(value=='forbidden')
										return "禁止";
								}
							</th> 
					    </tr>
				    </thead>
			    </table>
		</div>
		
		<div data-options="region:'east',border:false"  style="text-align:center;width:180px" >
				<table  class="form-table">
				     <tr>
					     <td colspan="2" style="width:175px; height: 40px;text-align:left; padding-left:5px;" >
					                       已选业务对象:<input size="5" type="text" class="quick-find">
					     </td>
				     </tr>
				</table>
				
				<table id="boSelectGrid" class="easyui-datagrid h100"  data-options="fitColumns:false,singleSelect:false,pagination:false" >
				    <thead>
					    <tr>
					    	<th field="id" data-options="hidden:true" >主键</th>
							<th field="name" data-options="hidden:true" >名称</th>
							<th field="code" data-options="hidden:true" >编号</th>
							<th field="packagePath" data-options="hidden:true" >路径</th>
							<th field="status" data-options="hidden:true" >状态</th>
							<th field="desc"  sortName="desc_" width="140px" >描述</th>
							<th manager="true"  width="40" >
								<div>
									<a onClick="javascript:deleteSelect('{id}','{name}')"  class="btn btn-default fa-delete"> </a>	
								</div>
							</th>
					    </tr>
				    </thead>
			    </table>

		</div>
	
	</body>
</html>