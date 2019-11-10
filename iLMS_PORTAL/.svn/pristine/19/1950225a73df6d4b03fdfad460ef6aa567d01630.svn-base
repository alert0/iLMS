<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<%@include file="/commons/include/list.jsp"%>
	</head>
	<div class="easyui-layout" fit="true" style="margin:0px 0 0 0;">
		<div data-options="region:'center',border:false">
				<!-- 顶部查询操作 -->
			    <div  class="toolbar-search col-md-13 ">
					<div class="toolbar-box border">
						<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons"> 		
								<a class="btn btn-primary btn-sm fa-search"  href="javascript:;" ><span>搜索</span></a>
								<a class="btn btn-primary btn-sm fa-add"    onclick="openDetail('','add')"><span>添加</span></a>
						        <a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="remove">删除 </a>
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
									<i class=" fa  fa-angle-double-up"></i>
								</a>
							</div>
						</div>
						<div class="toolbar-body">
								<form  id="searchForm" class="search-form" >
									<ul>
										<li><span>别名:</span><input type="text" class="inputText" name="Q^alias_^SL"></li>
										<li><span>wsdl&nbsp;地址:</span><input type="text" class="inputText" name="Q^url_^SL"></li>
									</ul>
								</form>
							</div>
					</div>
				</div>
				<!-- 顶部查询操作end -->
				<table id="userGridList" class="easyui-datagrid my-easyui-datagrid" idField="id" data-options="fitColumns:true,singleSelect:true,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit=true 
					 url="${ctx}/system/serviceSet/listJson">	 
				    <thead>
					    <tr>
					    	<th field="id" checkbox="true" class="pk" ></th>
					    	<th field="name" sortable="true" sortName="name_" width="100">名称</th>
							<th field="alias" sortable="true" sortName="alias_" width="100">别名</th>
							<th field="url" sortable="true" sortName="url_" width="100">wsdl&nbsp;地址</th>
							<th field="methodName" sortable="true" sortName="methodName_" width="100">名称空间</th>
					    </tr>
				    </thead>
			    </table>
		</div>
	</div>
	</body>
</html>