<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sys_trans_def管理</title>
		<%@include file="/commons/include/list.jsp"%>
</head>
<body class="easyui-layout">
	<div  class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons"> 		
					<a class="btn btn-primary btn-sm fa-search"  href="javascript:;" ><span>查询</span></a>
					<a class="btn btn-primary btn-sm fa-add"    onclick="openDetail('','edit')"><span>添加</span></a>
					<a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/system/sysTransDef/del">删除</a>
					<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left"> <span>重置</span></a> 
			        <a class="btn btn-primary btn-sm fa-database" onclick="showLog()" href="javascript:;"><span></span>日志</a>
			        <a class='btn btn-primary btn-sm fa-play-circle' onClick='shujuzhuanyi()' href='#'> 执行转移</a>
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
						<li><span>名称:</span><input type="text" class="inputText"  name="Q^name_^SL"></li>
						<li><span>是否启用:</span>
							<select name="Q^state_^S" value="${param['Q_state_S']}" class="inputText" >
								<option value ="">请选择</option>
								<option value ="1">是</option>
						  		<option value ="0">否</option>
							</select>
						</li>
						<li><span>创建人:</span><input type="text" name="Q^creator_^SL"  class="inputText" value="" /></li>
					</ul>
				</form>
			</div>
		</div>
	
	<table id="userGridList" class="easyui-datagrid my-easyui-datagrid" idField="id" data-options="fitColumns:true,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit=true 
		 url="${ctx}/system/sysTransDef/listJson">	 
	    <thead>
		    <tr>
		    	<th field="id" checkbox="true" class="pk" ></th>
		    	<th field="name" sortable="true" sortName="name_" width="100">名称</th>
				<th field="state" sortable="true" sortName="state_" width="100" formatter="getStatus">是否启用</th>
				<th field="creator" sortable="true" sortName="creator_" width="100">创建人</th>
				<th field="createTime" sortable="true" sortName="createTime_" formatter="dateTimeFormatter">创建时间</th>
				<th field="namespace" formatter="manager" >操作</th>
		    </tr>
	    </thead>
    </table>
    
</body>
</html>
<script type="text/javascript">
	function relaod(){
		$('#userGridList').datagrid('reload');
	}

	function manager(value,row,index){
		 var result ="";
		 result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+row.id+"\",\"edit\")' href='#'>编辑</a>"; 
		return result;
	}
	function shujuzhuanyi(id){
		HT.window.openEdit("sysTransDefSet","权限转移","edit", 'grid', 400, 300, null, null, id, true);
	}
	function showLog(){
		HT.window.openEdit("${ctx}/system/sysObjLog/sysObjLogList?type=SysTransDef","权限转移日志","edit", 'grid', 400, 300, null, null, 111, true);
	}
	
	function getStatus(value,row,index){
		 if(value==0) return '<span class="red">停用</span>';
		 else return '<span class="green">启动</span>';
	}
	
	function openDetail(id, action){
		var title = action == "edit" ? "编辑" : "查看";
		if(!id) title = "添加";
		action = action == "edit" ? "Edit" : "Get";
		var url= "sysTransDef"+action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url,title,action, 'grid', 400, 300, null, null, id, true);
	}

</script>

