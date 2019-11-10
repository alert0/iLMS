<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>任务期限统计</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
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
		var title = action == "edit" ? "编辑任务期限统计" : action == "add" ? "添加任务期限统计" : "延期记录明细";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="bpmTaskDueTime" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	function downFile(id){
		window.location.href=__ctx+"/system/file/download?id="+id;
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?Q^task_id_^S="+${param.taskId},
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'dateType',sortName : "DATE_TYPE_",title : '时间类型',width : 250,align : 'center',sortable : 'true'
			,formatter:function(value, row, index){
					var result ="工作日";
					if(value=="caltime")
						result = "日历日";
					return result;
				}			
			}, 
			{field : 'startTime',sortName : "START_TIME_",title : '任务开始时间',width : 250,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter
			},
			{field : 'expirationDate',sortName : "EXPIRATION_DATE_",title : '任务到期时间',width : 250,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter
			}, 
			{field : 'dueTime',sortName : "DUE_TIME_",title : '任务审批期限 (分钟)',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'addDueTime',sortName : "add_due_time_",title : '增加审批时间（分钟）',width : 250,align : 'center',sortable : 'true'
			}, 
			/* {field : 'remainingTime',sortName : "REMAINING_TIME_",title : '剩余审批时间',width : 250,align : 'center',sortable : 'true'
			}, */ 
			
			/* {field : 'userName',sortName : "USER_NAME_",title : '审批人姓名',width : 250,align : 'center',sortable : 'true'
			},  */
			
			{field : 'createTime',sortName : "CREATE_TIME_",title : '延期处理时间',width : 250,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter
			}, 
			{field : 'remark',sortName : "REMARK_",title : '备注',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'fileId',sortName : "FILE_ID_",title : '附件',width : 250,align : 'left',sortable : 'true',
				formatter:function(value,row,index){
					if(!value) return "";
					var jsonArr = jQuery.parseJSON(value);
					var fmtHtml = "";
					$(jsonArr).each(function(idx,obj){
						fmtHtml +=  '<a class="btn  fa-cloud-download" style="margin-top:-7px" onclick="downFile('+obj.id+')" title="下载该文件">'+obj.name+'</a>';
					});
					return fmtHtml;
				}
			},
             { field: 'colManage',  title: '操作', width:80, align: 'center'
    	    	 ,formatter:function(value,row,index){
	                 var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail("+row.id+")' herf='#'>明细</a>";
	                 return result;
                }
    		  }			
			] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
