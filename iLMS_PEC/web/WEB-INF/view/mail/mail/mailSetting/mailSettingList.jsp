<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>外部邮件用户设置</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="#">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/mail/mail/mailSetting/remove">
								<span>删除</span>
							</a>
						</div>
					    <div class="toolbar-body">
							<form id="searchForm" class="search-form">
								<ul>
									<li class="mar-l-20"><span>账号名称:</span> <input
										class="inputText ipu-w-170" type="text" name="Q^USER_NAME_^SL">
									</li>
									<li class="mar-l-20"><span>邮箱地址:</span> <input
										class="inputText ipu-w-170" type="text" name="Q^MAIL_ADDRESS_^SL">
									</li>
								</ul>
							</form>
						</div>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑外部邮件用户设置" : action == "add" ? "添加外部邮件用户设置" : "查看外部邮件用户设置";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="mailSetting" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/mail/mail/mailSetting/listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'nickName',sortName : "USER_NAME_",title : '用户名称',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'mailAddress',sortName : "MAIL_ADDRESS_",title : '外部邮箱地址',width : 250,align : 'center',sortable : 'true'
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					if(row.isDefault !='1'){
						result = "<a class='btn btn-default fa fa-send' href='${ctx}/mail/mail/mailSetting/setDefault?id=" + row.id + "'>设为默认</a>";
					}
					result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/mail/mail/mailSetting/remove?id=" + row.id + "' herf='#'>删除</a>";
					result += "<a class='btn btn-default fa fa-cog' onClick='test(" + row.id + ")' herf='#'>测试</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
	
	function test(id){
		$.topCall.progress();
		var param={id:id};
		$.post("test.ht",param,function(data){
			$.topCall.closeProgress();
			var obj=new com.hotent.form.ResultMessage(data);
			if(obj.isSuccess()){//成功
				$.topCall.success("连接成功！");
		    }else{//失败
		    	$.topCall.error("测试连接失败！error:"+obj.getMessage());
		    }
		});
	}
</script>
