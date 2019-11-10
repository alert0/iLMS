<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>我的请求</title>
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
		<div  data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a href="javascript:;" class="btn btn-sm btn-primary fa-search"><span>搜索</span></a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse"> <i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li><span>请 求 标 &nbsp;题:</span><input class="inputText" type="text" name="Q^subject_^SL" /></li>
								<li><span>流 程 名 称:</span><input class="inputText" type="text" name="Q^proc_def_name_^SL" /></li>
								<li><span>状态:</span>
								<select name="Q^status_^S" class="inputText" >
								<option value=""></option>
								<option value="draft">草稿</option>
								<option value="running">运行中</option>
								<option value="end">结束</option>
								<option value="manualend">人工结束</option>
								<option value="revokeToStart">撤销到发起人</option>
								<option value="backToStart">驳回到发起人</option>
								<option value="back">驳回</option>
								<option value="revoke">撤销</option>
								</select>
								
							</ul>
							<ul>
								<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								<li><span>创建时间从:</span><input name="Q^create_time_^DL" class="inputText date" /></li>
								<li><span >至:</span><input name="Q^create_time_^DG" class="inputText date" /></li>
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

    function revoke(id, subject,status){
    	//去除html标签
    	if(status == "revokeToStart"){
    		ht_showWarnMsg("已撤回，不能再撤回", "提示", "show", 1);
    		return false;
    	}
		var tmp = $("<div>" + subject + "</div>").text();
		var url = '${ctx}/office/initiatedProcess/revoke?instId=' + id;
		$.topCall.dialog(
		{
		    src : url, base :
		    {
			title : '撤回::[' + tmp + "]", width : 700, height : 400, modal : true, resizable : true
		    }
		});
		reloadLoad();
    }

    function reloadLoad(){
		$('.my-easyui-datagrid').datagrid('reload');
    }
	
    function openDetail(id)
	{
	    var title="查看我的请求";
	    HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
	}
    
    function loadGrid()
    {
    	$('#grid').datagrid($.extend($defaultOptions,{
             url: __ctx+"/office/initiatedProcess/myRequestJson",
             sortName: 'create_time_',
             sortOrder: 'desc',
            
              columns: [[
						{ field: 'subject',sortName:"subject_", title: '请求标题', width: 300, align: 'left', sortable: 'true'
						    ,formatter:function(value,row,index){
						        return "<span class=\"cur\" onClick=\"openDetail("+row.id+")\" >"+value+"</span>";
						    }
						},
                         { field: 'procDefName',sortName:"proc_def_name_", title: '流程名称', width: 200, align: 'center', sortable: 'true' },
                         { field: 'createTime', sortName:"create_time_ ",title: '创建时间', width:150, align: 'center'  ,formatter:dateTimeFormatter  },
                         { field: 'status',sortName:"status_" , title: '状态', width:100, align: 'center', sortable: 'true' 
                       	     ,formatter:function(value,row,index){
		  	                	  var arr=[{key:'draft',value:'草稿',css:'red'},{key:'running',value:'运行中',css:'green'},{key:'end',value:'结束',css:'red'},{key:'manualend',value:'人工结束',css:'red'},
		  	                	           {key:'revokeToStart',value:'撤回到发起人',css:'red'},{key:'backToStart',value:'驳回到发起人',css:'red'},{key:'back',value:'驳回',css:'red'},{key:'revoke',value:'撤回',css:'red'}];
		  	                	return Object.formatItemValue(arr, value);
                    		   }
                         },
                   	    { field: 'colManage',  title: '操作',  align: 'center' , sortable: 'true'
                            ,formatter:function(value,row,index){
 				                  var result="<a class='btn btn-default fa fa-reply-all'  onClick='revoke("+row.id+","+'"'+row.subject+'"'+","+'"'+row.status+'"'+")' herf='#'>撤回到发起人</a>";
 				                  return  result;
   			                   }
                   		}
              ]]
         }));     
    }   
 </script>