<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>外部邮件</title>
<%@include file="/commons/include/list.jsp"%>
<style type="text/css">
.tbar-title {
    background-color:#E3F5E3;
    border-bottom: solid 1px #A0BDBB;
   display: none;
    font-size: 13px;
    font-weight: bold;
    height: 24px;
    margin-top: 0px;
    padding-left: 5px;
    padding-top: 5px;
}
span.tbar-label{
	color:#000;
}
.panel-toolbar {
	height: 26px;
    margin-top: 2px;
    padding:8px 5px 5px;
    background:#E3F5E3;
    border-bottom: solid 1px #E3F5E3;
    border-top: solid 1px #E3F5E3;
  
}
</style>
<script type="text/javascript">

function emailsync(type){
	var outMailSetId=$('#outMailSetId').val();
	
	$.topCall.progress();
	$.post("sync",{id:outMailSetId,types:type},function(data){
		$.topCall.closeProgress();
		var obj=new com.hotent.form.ResultMessage(data);
		if(obj.isSuccess()){
			$.topCall.success(obj.getMessage(),'成功',function(){
				types = 1;
				mailSetId = outMailSetId;
				loadGrid();
			});
		}else{
			$.topCall.error("同步邮件出错！error:"+obj.getMessage());
		}
		
	});
}

</script>
</head>
<body>
	<input name="outMailSetId" id="outMailSetId" type="hidden" value="${mailUserSet.id}"/>
	<input name="types" id="types" type="hidden" value="${types}"/>
	<div class="easyui-layout" fit="true" scroll="no">
		<div style="display:none;">
			<div class="tbar-title">
				<span class="tbar-label">${mailUserSet.nickName}(${mailUserSet.mailAddress})
					<c:choose>
						<c:when test="${types=='1'}">收件箱</c:when>
						<c:when test="${types=='2'}">发件箱</c:when>
						<c:when test="${types=='3'}">草稿箱</c:when>
						<c:otherwise>垃圾箱</c:otherwise>
					</c:choose> 
				</span>
			</div>
		</div>
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
							<c:if test="${types==1}">
								<a  class="btn btn-sm btn-primary fa-refresh" href="#" onclick="emailsync()">
									<span>同步邮件</span>
								</a>
							</c:if>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/mail/mail/mail/remove?types=${types}">
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
						<form id="searchForm" class="search-form" action="listJson">
							<ul >
								<li><span>主题:</span><input class="inputText" type="text" name="Q^TITLE_^SL"></li>
								<c:if test="${types==1}">
							    	<li><span>是否已读:</span>
			                           <select name="Q^IS_READ_^S" value="${param['Q^IS_READ_^S']}">
				                            <option value="">全部</option>
				                            <option value="1" <c:if test="${param['Q^IS_READ_^S'] == 1}">selected</c:if>>已读</option>
				                            <option value="0" <c:if test="${param['Q^IS_READ_^S'] == 0}">selected</c:if>>未读</option>
			                           </select>
			                         </li>
		                        </c:if>     
								<li>
									<span>日期 从</span>:<input  name="Q^SEND_DATE_^DL"  class="inputText date" />
								</li>
								<li>
									<span>至: </span><input  name="Q^SEND_DATE_^DG" class="inputText date" />
								</li>
							</ul>
							<input type="hidden" type="text" name="Q^SET_ID_^Q" value="${mailSetId}">
							<input type="hidden" type="text" name="Q^TYPE_^Q" value="${types}">
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	var types = '${types}';
	var mailSetId = '${mailSetId}';
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑外部邮件" : action == "add" ? "添加外部邮件" : "查看外部邮件";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="mail" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		var datagrid = $('#grid').datagrid($.extend($defaultOptions,{
			url :  'listJson?types='+types+'&mailSetId='+mailSetId,
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'senderAddress',sortName : "SENDER_ADDRESSES_",title : '发件人',width : 250,align : 'center',sortable : 'true'
				
				
			}, 
			{field : 'subject',sortName : "TITLE_",title : '主题',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			
			
			{field : 'sendDate',sortName : "SEND_DATE_",title : '日期',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			}, 
			
			{field : 'isRead',sortName : "IS_READ_",title : '是否已读',width : 250,align : 'center',sortable : 'true'
			 ,formatter : function(value,row,index){
				 return (value=='1'?'已读':'未读');
			  }
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var delName = "删除";
					var result = "";
					if(row.type==3){
						result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					}else if(row.type==4){
						delName = "彻底删除";
					}
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/mail/mail/mail/remove?id=" + row.id + "&types="+row.type+"&mailSetId="+mailSetId+"' herf='#'>"+delName+"</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
