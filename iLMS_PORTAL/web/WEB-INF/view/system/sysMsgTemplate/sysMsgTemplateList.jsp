<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息模板</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
							<div class="buttons">
								<a class="btn btn-primary btn-sm fa-search" href="javascript:void(0);"><span>搜索</span></a> 
								<a class="btn btn-primary btn-sm fa-add" onClick='openDetail("","sysMsgTemplateEdit")' href="javascript:;"><span>添加</span></a>
								<a class="btn btn-primary btn-sm fa-download" 	href="javascript:void(0);" onclick="ExportXml()"><span>导出</span></a>
								<a class="btn btn-primary btn-sm fa-upload" 	href="javascript:void(0);" onclick="ImportXml()"><span>导入</span></a>
								<!--<a class="btn btn-primary fa-remove"  href="javascript:void(0);"  action="remove"><span>删除</span></a> -->
								<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"> <i
									class="fa  fa-angle-double-up"></i>
								</a>
							</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>模版名称:</span><input class="inputText" type="text"
									name="Q^name_^SL"></li>
								<li><span>模板分类:</span> <select class="inputText"
									name="Q^type_key_^SL">
										<option value="">全部</option>
										<option value="taskCreate">任务创建通知</option>
										<option value="bpmCommuSend">任务沟通</option>
										<option value="bpmCommuFeedBack">通知沟通人</option>
										<option value="bpmnTaskTrans">任务流转默认</option>
										<option value="bpmHandTo">任务转交通知</option>
										<option value="addSignTask">加签通知</option>
										<option value="taskComplete">任务完成通知</option>
										<option value="taskBack">任务驳回通知</option>
										<option value="processEnd">流程结束通知</option>
										<option value="bpmnApproval">审批提醒</option>
										<option value="bpmnBack">驳回提醒</option>
										<option value="bpmnRecover">撤销提醒</option>
										<option value="bpmnAgent">代理任务审批</option>
										<option value="bpmnDelegate">通知被代理人</option>
										<option value="bpmEndProcess">流程终止</option>
										<option value="bpmTransCancel">撤销流转代办</option>
										<option value="copyTo">流程实例抄送</option>
										<option value="bpmTransFeedBack">流转反馈通知</option>
										<option value="taskCancel">任务取消通知</option>
										<option value="bpmTurnCancel">转办取消通知</option>
								</select>
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
<script type="text/javascript">
$(function() {
	loadGrid();
});
function loadGrid()
{
	$('#grid').datagrid($.extend($defaultOptions,{
         url: __ctx+"/system/sysMsgTemplate/listJson",
         idField:"id",
   		 sortName: 'id_',
         sortOrder: 'desc',
         columns: [[
                    { field: 'id',sortName:"id_",checkbox:true},
                    { field: 'name',sortName:"name_", title: '模版名称',width: 250, align: 'left', sortable: 'true' },
                     { field: 'key',sortName:"key_", title: '模版业务键',width: 150, align: 'left', sortable: 'true'},
                     { field: 'typeKey',sortName:"type_key_", title: '模板分类',width: 150, align: 'center', sortable: 'true'
                    	 ,formatter:function(value,row,index){
                    		 var arr=[{"key":'taskCreate',"value":'任务创建通知',"css":'black'},
	   									{"key":'bpmCommuSend',"value":'任务沟通',"css":'black'},
										{"key":'bpmCommuFeedBack',"value":'通知沟通人',"css":'black'},
										{"key":'bpmnTaskTrans',"value":'任务流转默认',"css":'black'},
										{"key":'bpmHandTo',"value":'任务转交通知',"css":'black'},
										{"key":'addSignTask',"value":'加签通知',"css":'black'},
										{"key":'taskComplete',"value":'任务完成通知',"css":'black'},
										{"key":'taskBack',"value":'任务驳回通知',"css":'black'},
										{"key":'processEnd',"value":'流程结束通知',"css":'black'},
										{"key":'bpmnApproval',"value":'审批提醒',"css":'black'},
										{"key":'bpmnBack',"value":'驳回提醒',"css":'black'},
										{"key":'bpmnRecover',"value":'撤销提醒',"css":'black'},
										{"key":'bpmTransCancel',"value":'撤销流转',"css":'black'},
										{"key":'bpmnAgent',"value":'代理任务审批',"css":'black'},
	 									{"key":'bpmnDelegate',"value":'通知被代理人',"css":'black'},
	 									{"key":'bpmEndProcess',"value":'流程终止',"css":'black'},
	 									{"key":'bpmTransCancel',"value":'撤销流转代办',"css":'black'},
	 									{"key":'copyTo',"value":'流程实例抄送',"css":'black'},
	 									{"key":'bpmTransFeedBack',"value":'流转反馈通知',"css":'black'},
	 									{"key":'taskCancel',"value":'任务取消通知',"css":'black'},
	 									{"key":'bpmTurnCancel',"value":'转办取消通知',"css":'black'}];
                    		 return Object.formatItemValue(arr,value);
             		     }
                     },
                     { field: 'smsTemplateNo',  title: '短信模版ID',width: 150, align: 'center'},
                     { field: 'voiceTemplateNo',  title: '语音模板ID',width: 150, align: 'center'},
                     { field: 'isDefault',sortName:"is_default_", title: '是否默认', align: 'center', sortable: 'true'
                    	 ,formatter:function(value,row,index){
                    		  var arr=[{"key":'0',"value":'否',"css":'red'},{"key":'1',"value":'是',"css":'green'}];
                    		  return Object.formatItemValue(arr,value);
              		     }	 
                      },
 	                  { field: 'colManage',  title: '操作',  align: 'center'
             	    	 ,formatter:function(value,row,index){
             	    		  var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail("+row.id+",\"sysMsgTemplateEdit\")' herf='#'>编辑</a>";
		                	 	  result += "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+",\"sysMsgTemplateGet\")' herf='#'>明细</a>";  
		                  	  	  result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/sysMsgTemplate/remove?id="+row.id+"' herf='#'>删除</a>";
		                  	  if(row.isDefault=="0"){
		                  		  result += "<a class='btn btn-default fa fa-reply-all' onClick='changDefault("+row.id+")' herf='#'>设为默认</a>";
		                  	  }
	                  	  	  return result;
		                  }
             		  }
          ]]
	}));     
}   

//导出
function ExportXml(){
	var ids= $.getSelectIds($("#grid"),'id');
	if (!ids) {
		$.topCall.warn( '请选择至少一项记录!');
		return;
	}
	var frm = new com.hotent.form.Form();
	frm.creatForm("exportXml", "exportXml");
	frm.addFormEl("ids", ids);
	frm.submit();
};
//导入
function ImportXml(){
	var def={
	        passConf:'',title:'导出流程定义',width:600, height:400, modal:true,resizable:true,iconCls: 'fa fa-user',
	        buttonsAlign:'center'
		};
		$.topCall.dialog({
			src: __ctx+'/system/sysMsgTemplate/sysMsgTemplateImport',
			base:def
		});
}
	
function changDefault(id){
	url = "${ctx}/system/sysMsgTemplate/setDefault?id="+id;
	$.post(url,function(responseText){
		var resultMessage=new com.hotent.form.ResultMessage(responseText);
		if(resultMessage.isSuccess()){
			$.topCall.success(resultMessage.getMessage());
			$('.my-easyui-datagrid').datagrid('reload');							
		}else{
			$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
		} 
	});
}
function openDetail(id,action)
{
    var title=action=="edit"?"编辑消息模板":action=="add"?"添加消息模板":"查看消息模板";
    action=action=="add"?"sysMsgTemplateEdit":action;
    HT.window.openEdit(action+'?id='+id,title, action, 'grid', 500, 500, null, null, id, true);
}
</script>