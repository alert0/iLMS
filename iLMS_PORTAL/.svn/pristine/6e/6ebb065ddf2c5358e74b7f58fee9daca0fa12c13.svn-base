	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>流程的测试用例设置</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
				<div>
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href='javascript:void(0);'>
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href='javascript:void(0);' onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href='javascript:void(0);' action="/bpmx/test/bpmTestCase/remove">
								<span>删除</span>
							</a>
							<a class="btn btn-sm btn-primary" href='javascript:void(0);' onclick="batchStartTest()">
								<span>批量启动</span>
							</a>
							<a class="btn btn-sm btn-primary" href='javascript:void(0);' onclick="openTestCaseDetail()">
								<span>查看测试实例</span>
							</a>
							<a class="btn btn-sm btn-primary" href='javascript:void(0);' onclick="openTestCaseReport()">
								<span>测试用例报表</span>
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
							<ul>
								<li><span>测试用例名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
								<!-- <li><span>发起人:</span><input class="inputText" type="text" name="Q^startor_full_name_^SL"></li> -->
								<!-- 回车搜索bug,原因未明  --> <li><input class="inputText1" type="text" style="display:none;"></li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑流程的测试用例设置" : action == "add" ? "添加流程的测试用例设置" : "查看流程的测试用例设置";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url =__ctx + "/bpmx/test/bpmTestCase/bpmTestCase" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}else{
			url+='?defKey=' + $.getParameter("defKey");
		}
		
		var index = top.layer.open({
			  type: 2,
			  title: title,
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:2001,
			  content: [url, 'yes'],
			  end: function(){
				  loadGrid();
			  }
			});
		//top.layer.full(index);
		
	}
	
	function batchStartTest(){

		var ids = getSelectIds("#grid","id");
		if(!ids){
			top.layer.alert("请至少选择一条记录", {icon: 2}); 
			return ;
		}
		startTest(ids);
	}
	
	function startTest(id){
		var index = top.layer.load(2); //换了种风格
		$.post(__ctx+"/bpmx/test/bpmTestCase/startTest",{ids:id},function(result){
			// 启动流程成功
			if(result.result){
				top.layer.alert(result.message, {icon: 1}); 
			}else{
				top.layer.alert(result.message, {icon: 2}); 
			}
			//关闭
			top.layer.close(index);   
		}).error(function(res){
			top.layer.alert(res.responseJSON.message, {icon: 2}); 
			//关闭
			top.layer.close(index); 
		});
		
	}
	
	// 
	function openTestCaseDetail(){
		var url =  __ctx +  "/bpmx/test/bpmTestCase/bpmTestCaseInstList?defKey="+$.getParameter("defKey");
		var index = top.layer.open({
			  type: 2,
			  title: "流程实例",
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:2001,
			  content: [url, 'yes'],
			  end: function(){
				 // loadGrid();
			  }
			});
	}
	
	// 查看测试用例报表
	function openTestCaseReport(){
		
		var ids = getSelectIds("#grid","id");
		if(!ids){
			top.layer.alert("请至少选择一条记录", {icon: 2}); 
			return ;
		}
		if(top.$("#hiddenSelectIds").length){
			top.$("#hiddenSelectIds").val(ids);
		}else{
			top.$("body").append("<input type='hidden' id='hiddenSelectIds' value='"+ids+"' >");
		}
		var url =  __ctx +  "/bpmx/test/bpmTestCase/bpmTestCaseReport";
		
		// 当前窗口位置打开
		var index = top.layer.open({
			  type: 2,
			  title: "测试用例报告",
			  closeBtn: 1, //不显示关闭按钮
			  area: ['85%', '80%'],
		      maxmin: true, //开启最大化最小化按钮
			  anim: 2,
			  zIndex:2007,
			  content: [url, 'yes'],
			  end: function(){
			  }
			});
	}
	
	function loadGrid() {
		var defKey = $.getParameter("defKey");
		var url = "listJson.ht?Q^def_key_^S="+defKey;
		url = encodeURI(url);
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  url,
			method: 'GET',
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "id_",checkbox : true},   
			{field : 'name',sortName : "name_",title : '测试用例名称',width : 250,align : 'center',sortable : 'true'
			}, 
			/* {field : 'startorFullName',sortName : "startor_full_name_",title : '发起人',width : 250,align : 'center',sortable : 'true'
			}, */
			{field : 'defKey',sortName : "def_key_",title : '流程定义key',width : 250,align : 'center',sortable : 'true'
			
			
			},
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-send' onClick='startTest(" + row.id + ")' href='javascript:void(0);'>启动测试</a>";
					 result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' href='javascript:void(0);'>编辑</a>";
					/* result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' href='javascript:void(0);'>明细</a>"; */
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/bpmx/test/bpmTestCase/remove?id=" + row.id + "' href='javascript:void(0);'>删除</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
