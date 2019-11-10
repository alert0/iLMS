<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/innermsg/MessageTypeController.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/innermsg/MessageTypeService.js"></script>
		<script type="text/javascript">
			
		function getGridSelectedField(grid, fieldName) {
			    var rows = $('#' + grid).datagrid('getSelections');
			    if (!rows || rows.length == 0) {
			    	$.topCall.warn('请选择至少一条未读记录!');
			        return "";
			    }
			    var parm;
			    $.each(rows, function (i, n) {
			        if (i == 0) {
			            parm = n[fieldName]
			        } else {
			            parm += "," + n[fieldName];
			        }
			    });
			    return parm;
			}
			
			function mark(){
				var idStr = getGridSelectedField("grid", "rid");
				if(!idStr) return;
				var url = __ctx + '/innermsg/messageReceiver/mark.ht';
				var params={ids:idStr};
				$.post(url,params,function(d){
					var resultMessage=new com.hotent.form.ResultMessage(d);
					if(resultMessage.isSuccess()){
				   		//$.msgShow('提示信息',resultMessage.getMessage());
				   		$.topCall.success(resultMessage.getMessage());
				   		reloadLoad();
					}else{
						$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
					}
				});
			}
		</script>
	</head>
	<body  class="easyui-layout" fit="true"   scroll="no">
		<div data-options="region:'center',border:false"  >
			<div class="easyui-layout" fit="true" scroll="no">
		 <div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons"> 		
					<a class="btn btn-primary btn-sm fa-search"  href="javascript:void(0);" ><span>搜索</span></a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
			        <a class="btn btn-primary btn-sm fa-remove"  href="javascript:void(0);"  action="/innermsg/messageReceiver/remove.ht"><span>删除</span></a>
			        <a class="btn btn-primary btn-sm"  href="javascript:void(0);" onclick="mark()" ><span>标记为已读</span></a>
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
							<li><span>标题:</span><input class="inputText" type="text" name="Q^subject^SL"></li>
							<!-- <li ng-app="msgTypeApp" ng-controller="MessageTypeController"><span>消息类型:</span>
								<select  class="inputText" id="messageType" name="Q^messageType^SL" ng-model="sysMessage.messageType" >
								 	<option value="">全部</option>
									<option ng-repeat="msgType in allMsgType" value="{{msgType.value}}">{{msgType.key}}</option>
								</select>
							</li> -->
							<li><span>是否已读:</span>
								<select name="Q^receiveTime^S" class="inputText">
								    <option value="">全部</option>
									<option value="1" >未读</option>
									<option value="2" >已读</option>
							    </select>
							</li>
							<li>
								<span>发送时间:</span><input class="inputText datetime" type="text" name="Q^beginreceiveTime^DL">
								<span>至:</span><input class="inputText datetime" type="text" name="Q^endreceiveTime^DG">
							</li>
						</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	    
	    </div>
	</body>
</html>
<script>
function openDetail(id,isPublic,canReply){
	var url = __ctx +"/innermsg/messageRead/get?id="+id+"&isPublic="+isPublic+"&canReply="+canReply;
	var title = "查看详细信息";
	var option = {closable: false};
 	HT.window.openEdit(url, title, "", 'grid', '100%', '100%', option, null, null, false);
}

function loadGrid() {
	$('#grid').datagrid($.extend($defaultOptions,{
		url :__ctx+"/innermsg/messageReceiver/listJson.ht",
		idField : "rid",
		sortName : 'createTime',
		sortOrder : 'desc',
		columns : [ [
		{field : 'id',sortName : "id_",checkbox : true},     
		{field : 'owner',sortName : "owner",title : '发信人',width : 130,align : 'center',sortable : 'true'}, 
		{field : 'subject',sortName : "subject",title : '标题',width : 130,align : 'center',sortable : 'true'},
		/* {field : 'messageType',sortName : "messageType",title : '消息类型',width : 130,align : 'center',sortable : 'true'}, */
		{field : 'createTime',sortName : "createTime",title : '发信时间',width : 130,align : 'center',sortable : 'true',formatter:dateTimeFormatter},
		{field : 'receiveTime',sortName : "receiveTime",title : '收信时间',width : 130,align : 'center',sortable : 'true',formatter:dateTimeFormatter2},
		{
			field : 'colManage',
			title : '操作',
			align : 'center',
			formatter : function(value, row, index) {
				var result = '<a onClick="openDetail('+row.id+","+row.isPlublic+","+row.canReply+')"  href="javascript:void(0);"   class="btn btn-default fa-detail" >详细</a>';
				result += '<a  href="javascript:;" action="/innermsg/messageReceiver/remove.ht?rid='+row.rid+'"   class="btn btn-default rowInLine fa-remove" >删除</a>';
				return result;
			}
		}
		] ]
	}));
}

function dateTimeFormatter2(value, row, index){
	 if (value == null || value == "")
		 return "未读消息"
	return dateTimeFormatter(value, row, index);
}

$(function(){
	loadGrid(); 
});

function reloadLoad(){
	$('.my-easyui-datagrid').datagrid('reload');
}
</script>