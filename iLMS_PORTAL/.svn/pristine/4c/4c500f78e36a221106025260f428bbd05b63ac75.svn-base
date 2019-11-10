<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>

<title>抄送转发事宜</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout"  fit="true" scroll="no">

		<div data-options="region:'west',split:true,border:false,title:'流程分类'" style="width: 200px;">
			<!--<div class="tree-toolbar">
				<a class="btn btn-xs btn-primary fa-refresh" href="javascript:sysTypeTree.loadTree();"> 刷新</a> <a class="btn btn-xs btn-primary fa-expand" href="javascript:sysTypeTree.expandAll(true)"> 展开</a> <a class="btn btn-xs btn-primary fa-compress" href="javascript:sysTypeTree.expandAll(false)"> 收缩</a>
			</div>-->
			<div id="sysTypeTree" class="ztree"></div>
		</div>

		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a> <a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> <i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>标题:</span><input class="inputText" type="text" name="Q^subject^SL" /></li>
								<li><span>类型:</span> <select name="Q^type^S" >
										<option value="">全部</option>
										<option value="copyto">抄送</option>
										<option value="trans">转发</option>
								</select></li>
								<li><span style="margin-left: -13px;">是否已读:</span> <select name="Q^isRead^S" >
										<option value="">全部</option>
										<option value="1">已读</option>
										<option value="0">未读</option>
								</select></li>
							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间 从:</span><input name="Q^createTimeStart^DL" class="inputText date" /></li>
								<li><span>至:</span><input name="Q^createTimeEnd_DG" class="inputText date" /></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid" ></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
    var sysTypeTree = null;
    $(function(){
		//加载分类树
		sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
	   loadGrid();
    });
    
    function showDetail(isRead,bId,instId)
    {
	    var title="查看抄送转发事宜";
		var url=isRead==0?"realDetail?bId="+bId+"&instId="+instId:"${ctx}/flow/instance/instanceGet?id="+instId;
		 HT.window.openEdit(url,title, 'view', 'grid', 500, 500, null, null, instId, true);
	 
    }
    
    function toCopyTo(instId){
		var title="流程转发";
		var url="${ctx}/flow/instance/instanceToCopyTo?instanceId="+instId;
		 HT.window.openEdit(url,title, 'view', 'grid', 500, 350, null, null, instId, false);
	}
    
    function loadGrid()
    {
    	$('#grid').datagrid($.extend($defaultOptions,{
            url: __ctx+"/office/receivedProcess/receiverCopyToJson",
            sortName: 'create_time_',
            sortOrder: 'desc',
            columns: [[
						{ field: 'subject',sortName:"subject_", title: '标题', width: 200, align: 'left', sortable: 'true'
						    ,formatter:function(value,row,index) {
						        return "<span class=\"cur\" onClick=\"showDetail("+row.isRead+","+row.bId+","+row.instId+");\" >"+value+"</span>";
						    }
						 },
                       { field: 'startor',sortName:"startor_", title: '抄送转发人', width: 200, align: 'center', sortable: 'true' },
                       { field: 'isRead', title: '是否读取', width:100, align: 'center', 
                    	   formatter:function(value,row,index){
	                   	       var arr=[{key:'1',value:'已读',css:'green'},{key:'0',value:'未读',css:'red'}];
	                   	       return Object.formatItemValue(arr,value);
                          }
                       },
                       { field: 'type',sortName:"a.type_" , title: '类型', width:100, align: 'center', sortable: 'true' , 
                    	   formatter:function(value,row,index){
	   	             	       var arr=[{key:'copyto',value:'抄送',css:'green'},{key:'trans',value:'转发',css:'red'}];
	   	             	   	   return Object.formatItemValue(arr,value);
   	                       }
                       },
                       { field: 'createTime',sortName:"create_time_" , title: '创建时间', align: 'center',width:100, sortable: 'true' ,formatter:dateTimeFormatter},
                   	   { field: 'colManage',  title: '操作', align: 'center' ,
                    	   formatter:function(value,row,index){
                    	   var result='<a class="btn btn-default fa fa-detail" onClick="showDetail('+row.isRead+','+row.bId+','+row.instId+');" herf="#">明细</a>'
                    	   				+"<a class='btn btn-default fa fa-detail' onClick='toCopyTo("+row.instId+")' herf='#'>转发</a>";
                    	   return  result;
                        }
                    }
            ]]}));    
    }
</script>