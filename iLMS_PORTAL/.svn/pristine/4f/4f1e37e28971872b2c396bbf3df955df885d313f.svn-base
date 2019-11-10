<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>xq_meetingroom_appointment</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
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
							<!--<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
						 	 <a class="btn btn-sm btn-primary fa-remove" href="#" action="/business/meeting/meeting/remove">
								<span>删除</span>
							</a>  -->
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
								<!-- <li><span>会议室id:</span><input class="inputText" type="text" name="Q^MEETINGROOM_ID_^SL"></li>
								<li><span>会议id:</span><input class="inputText" type="text" name="Q^MEETING_ID_^SL"></li> -->
								<li><span>会议名称:</span><input class="inputText" type="text" name="Q^w_hybd.hymc^SL"></li>
								<li><span>主持人:</span><input class="inputText" type="text" name="Q^w_hybd.zcr^SL"></li>
								<li>
									<span>时间从:</span>
									<input name="Q^w_hybd.kssj^DL" class="inputText date" />
								</li>
								<li>
									<span>至:</span>
									<input name="Q^w_hybd.kssj^DG" class="inputText date" />
								</li>
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
		
/* 	function openDetail(id, action) {
		var title = action == "edit" ? "编辑xq_meetingroom_appointment" : action == "add" ? "添加xq_meetingroom_appointment" : "查看xq_meetingroom_appointment";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="meeting" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	} */
	   function openDetail(id)
		{
		    var title="查看会议详情";
		     HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
		    /* var url = __ctx + "/flow/instance/instanceGet?id="+id;
		    top.layer.open({  
		        type: 2,  
		        title: title,  
		        shadeClose: true,  
		        shade: 0.3,  
		        area : ['100%' , '100%'],  
		        content: [url, 'no']
		      });    */
		}
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "myCompletedJson",
			idField : "id",
			sortName : 'wfInst.create_time_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},   
			{field : 'hymc',title :'会议名称',width : 250,align : 'center',sortable : 'true'
				 ,formatter:function(value,row,index){
				        return "<span class=\"cur\" onClick=\"openDetail("+row.id+")\" >"+value+"</span>";
				  }
			
			}, 
			{field : 'zcr',title : '主持人',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'hys',title : '会议室',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'kssj',title : '开始时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			}, 
			{field : 'jssj',title : '结束时间',width : 250,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					//var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					/* result += "<a class='rowInLine btn btn-default fa fa-remove' action='/business/meeting/meeting/remove?id=" + row.id + "' herf='#'>删除</a>"; */
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
