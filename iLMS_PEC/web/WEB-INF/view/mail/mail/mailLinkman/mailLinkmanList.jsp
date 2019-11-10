<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>外部邮件最近联系</title>
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
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/mail/mail/mailLinkman/remove">
								<span>删除</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul style="margin-left: -26px">
								<li><span>用户ID:</span><input class="inputText" type="text" name="Q^USERID^SL"></li>
								<li><span>邮件ID:</span><input class="inputText" type="text" name="Q^MAILID^SL"></li>
								<li>
									<span>送送时间 从</span>:<input  name="Q^SENDTIME^DL"  class="inputText date" />
								</li>
								<li>
									<span>至: </span><input  name="Q^SENDTIME^DG" class="inputText date" />
								</li>
								<li><span>联系人名称:</span><input class="inputText" type="text" name="Q^LINKNAME^SL"></li>
								<li><span>联系人地址:</span><input class="inputText" type="text" name="Q^LINKADDRESS^SL"></li>
								<li><span>发送次数:</span><input class="inputText" type="text" name="Q^SENDTIMES^SL"></li>
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
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑外部邮件最近联系" : action == "add" ? "添加外部邮件最近联系" : "查看外部邮件最近联系";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="mailLinkman" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "LINKID",
			sortName : 'LINKID',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'LINKID',sortName : "LINKID",checkbox : true},   
			{field : 'USERID',sortName : "USERID",title : '用户ID',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'MAILID',sortName : "MAILID",title : '邮件ID',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'SENDTIME',sortName : "SENDTIME",title : '送送时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			}, 
			{field : 'LINKNAME',sortName : "LINKNAME",title : '联系人名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'LINKADDRESS',sortName : "LINKADDRESS",title : '联系人地址',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'SENDTIMES',sortName : "SENDTIMES",title : '发送次数',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/mail/mail/mailLinkman/remove?LINKID=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
