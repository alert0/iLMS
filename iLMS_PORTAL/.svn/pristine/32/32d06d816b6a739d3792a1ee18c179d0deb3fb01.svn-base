<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>待办事宜</title> 
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/Dialogs.js"></script>
</head>
<body>
	<div class="easyui-layout"  fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;"">
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		
		<div data-options="region:'center',border:false" >
			<div id="gridSearch" class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search">
								<span>搜索</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse">
								<i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form" >
							<ul>
								<li>
									<span>请求标题:</span>
									<input class="inputText" type="text" name="Q^subject_^SL" />
									<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								</li>
								<li>
									<span>流程发起人:</span>
									<input class="inputText seletor" disabled="disabled" type="text" id="ownerName" name="owner_name" />
									<input type="hidden" name="Q^creatorId^L" id="ownerId" />
									<a class="btn btn-sm btn-default fa-ellipsis-h" href="javascript:void(0)" onClick="selectUser(this);" selectorId="ownerId" selectorName="ownerName"></a>
								</li>
								<li>
									<span>流程名称:</span>
									<input class="inputText" type="text" name="Q^proc_def_name_^SL" />
								</li>

							</ul>
							<ul>
								<li>
									<span>创建时间从:</span>
									<input name="Q^create_time_^DL" class="inputText date" />
								</li>
								<li>
									<span>至:</span>
									<input name="Q^create_time_^DG" class="inputText date" />
								</li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<li>
									<span>待办类型:</span>
									<select name="Q^status_^SL" class="inputText" >
										<option value=""></option>
										<option value="NORMAL">待办</option>
										<option value="AGENT">代理</option>
										<option value="DELIVERTO">转办</option>
										<option value="COMMU">沟通</option>
										<option value="TRANSFORMED">流转</option>
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
<script>
	var sysTypeTree = null;
	$(function() {
		//加载分类树
		sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
		loadGrid();

	});

	function selectUser(obj) {
		var me = $(obj), id = me.attr("selectorId"), name = me.attr("selectorName");
		
		
		UserDialog({
			callBack : function(data) {
				$('#ownerId').val(data[0].id);
				$('#ownerName').val(data[0].name);
			},isSingle:1
		});
	}

	function toTask(id) {
		var url= __ctx + '/flow/task/canLock?taskId=' + id
		$.get(url, function(rtn){
			//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定,5:这种情况一般是管理员操作，所以不用出锁定按钮。
			switch(rtn){
				case 0:
					$.topCall.success("此任务已经被其他人处理完成!");
					break;
				case 1:
				case 2:
				case 3:
				case 5:
					$.openFullWindow(__ctx + '/flow/task/taskApprove?id=' + id);
					break;
				case 4:
					$.topCall.success("此任务已经被其他人锁定!");
					break;
				case 6:
					$.topCall.error("流程已经被禁止，请联系管理员！");
					break;
			}
		});
		
	}
	
	function openTaskDueTimeDetail(taskId,name){
		 var url=__ctx+"/flow/task/bpmTaskDueTime/bpmTaskDueTimeList?taskId="+taskId;
		 HT.window.openEdit(url,name+"--延期记录", 'View', 'grid', 500, 500, null, null, taskId, true);
	}

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/office/receivedProcess/pendingJson",
			sortName : 'create_time_',
			sortOrder : 'desc',
			columns : [ [ 
			 {
				field : 'subject',
				sortName : "subject_",
				title : '标题',
				width : 350,
				align : 'left',
				sortable : 'true',
				formatter : function(value, row, index) {
					return "<span class=\"cur\" onClick=\"toTask(" + row.id + ")\" >" + value + "</span>";
				}
			 },
			 {
				field : 'procDefName',
				sortName : "proc_def_name_",
				title : '流程名称',
				width : 150,
				align : 'left',
				sortable : 'true'
			}, 
			{
					field : 'name',
					title : '任务名称',
					width : 180,
					align : 'left'
			} ,
			{
				field : 'createTime',
				sortName : "create_time_",
				title : '任务创建时间',
				width : 150,
				align : 'center',
				sortable : 'true',
				formatter : dateTimeFormatter
			},
			{ 
				field: 'dueExpDate',
				title: '任务到期时间',
				width: 150, 
				align: 'center',
				sortable: 'true' ,
				formatter:dateTimeFormatter
			},
            { 
				field: 'dueTaskTime',
				title: '期限状态', 
				width: 220, 
				align: 'center', 
				sortable: 'true',formatter:function(value,row,index){
              		 if(!row.dueDateType || !value ) return "";
              		 var className = "progress-bar-success",dateType = "工作日", 
              		 detailHtml = "",detailHtml2="";
              		 var percent = row.dueUseTaskTime*100/value;
              		 percent = parseFloat(percent.toFixed(2));
              		 if(25<percent&&percent<=50){
              			 className = " progress-bar-info";
              		 }
              		 if(50<percent&&percent<=75){
              			 className = "progress-bar-warning";
              		 }
              		 if(75<percent){
              			 className = "progress-bar-danger";
              		 }
              		 if(percent>100){
              			 percent = 100;
              		 }
              		 if(row.dueDateType == "caltime"){
              			 detailHtml = "<img src='"+__ctx+"/css/image/caltime.png' title='日历日'></img>  ";
              		 }else{
              			 detailHtml = "<img src='"+__ctx+"/css/image/worktime.png' title='工作日'></img>  ";
              		 }
              		 
              		 if(row.dueStatus==1){
              			detailHtml2 += "<a class='fa fa-detail' onClick='openTaskDueTimeDetail(\""+row.id +"\",\""+row.name+"\")' herf='#' title='明细'></a>";
              		 }
              		 
              		 
              		 var progressHtml=	'<div class="row">'+
              		 						'<div class="col-sm-2" style="margin-top:4px;">'+detailHtml+
              		 						'</div>'+
              		 						'<div class="col-sm-8" style="padding:0;">'+
		              			 				'<div class="progress progress-striped" >'+
		              		 			  			'<div class="progress-bar '+className+'" style="width:'+percent+'%">'+
		              		 			  				'<span style="color:black"> ' +  percent+'% '+ '</span>'+
		              		 			  			'</div>'+
		              		 					'</div>'+
	              		 					'</div>'+
	              		 					'<div class="col-sm-2" style="margin-left:-8px;margin-top:4px;">'+detailHtml2+
		              		 				'</div>'+
		              		 			'</div>';
              		  return progressHtml;
         		     }	 
            },
			{
				field : 'creator',
				sortName : "CREATOR_",
				title : '流程发起人',
				width : 100,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'status',
				sortName : "status_",
				title : '待办类型',
				width : 80,
				align : 'center',
				sortable : 'true',
				formatter : function(value, row, index) {
					var arr = [ {key : 'TRANSFORMED', value : '流转',css : 'red'}, 
					            {key : 'NORMAL',value : '待办',css : 'green'}, 
					            {key : 'AGENT',value : '代理',css : 'green'}, 
					            {key : 'DELIVERTO',value : '转办',css : 'red'}, 
					            {key : 'COMMU',value : '沟通',css : 'red'},
					            {key : 'ADDSIGN',value : '加签',css : 'red'},
					            {key : 'BACK',value : '被驳回',css : 'red'} ];
					if(value == "TRANSFORMED"){
						return "<span style='text-decoration:underline black' class='red' onclick='transdetail("+row.parentId+")'>流转</span>";
					}else if(value == "DELIVERTO"){
						return "<span style='text-decoration:underline black' class='red' onclick='delegateDetail("+row.id+")'>转办</span>";
					}else{
						return Object.formatItemValue(arr,value);
					}
				}
			} ] ]
		}));
	}
	
	//流转信息
	function transdetail(taskId){
		var title="流转信息";
	    HT.window.openEdit("${ctx}/office/receivedProcess/pendingTransDetail?taskId="+taskId,title, 'view', 'grid', 300, 150, null, null, "", false);
	}
	
	//转办详情
	function delegateDetail(taskId){
		var title="转办信息";
	    HT.window.openEdit("${ctx}/office/receivedProcess/delegateDetail?taskId="+taskId,title, 'view', 'grid', 450, 250, null, null, "", false);
	}
</script>
