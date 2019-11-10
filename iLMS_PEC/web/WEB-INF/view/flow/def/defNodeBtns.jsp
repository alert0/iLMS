<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String defId = request.getParameter("defId");
request.setAttribute("defId", defId);
%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript">
			function reCycleNodeBtns(nodeId){
				var defId = ${defId};
				var url=__ctx+"/flow/def/initNodeBtn";
				$.post(url,{defId:defId,nodeId:nodeId},function(data){
					var rs = JSON2.parse(data);
					if(rs.result=="1"){
						$.topCall.success(rs.message,function(){
							window.location.reload();
						},'温馨提示');
					}else{
						$.topCall.error(rs.message,rs.cause);
					}
				});
			}
		</script>
        
        
        <style>
        
        .panel {
			overflow: hidden;
			text-align: left;
			margin: 0;
			border: 0;
			-moz-border-radius: 0 0 0 0;
			-webkit-border-radius: 0 0 0 0;
			border-radius: 0 0 0 0;
			border-top: 1px solid #ddd;
		}
		.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber {
			margin: 0;
			padding: 0 8px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 18px;
			line-height: 18px;
			font-size: 12px;
			color: #515151;
		}
        </style>
	</head>
	<body  class="easyui-layout" style="overflow-y:hidden; border:1px solid #959DA4"  fit="true"   scroll="no">
		<div data-options="region:'center',border:false" style="border-left:1px solid #eee" >
			<div class="border" style="	margin-top: 5px;margin-bottom: 5px;">
				<div class="buttons">
					<a href="javascript:reCycleNodeBtns();" class="btn btn-primary fa-recycle"><span>初始化全部按钮</span></a>
        		</div>
			</div>
			<div id="nodeBtnsList" class="my-easyui-datagrid" ></div>
		</div>
	</body>
</html>

<script>
$(function(){
	loadGrid();
});
function loadGrid()
{
	$('#nodeBtnsList').datagrid({
         url:__ctx+"/flow/def/getNodeBtns?defId=${defId}",
         idField:"nodeId",
		 sortName: 'nodeId',
         sortOrder: 'desc',
         fit: true,
         fitColumns: true,
         pagination: true,
         autoRowHeight: false,
         pageSize: $defaultPageSize,
         pageList: $defaultPageList,
         rownumbers: true,
         striped:true,
         frozenColumns: [[
                          { field: 'name',  title: '节点名', edit:true,  width: 130, align: 'center', sortable: 'true'},
                          { field: 'type',  title: '类型', width:100, align: 'center'  ,formatter:typeFormatter}
             ]],
         columns: [[
                   
                    { field: 'btns',  title: '操作按钮', width:300, align: 'center'  ,formatter:btnsFormatter},
                    { field: 'colManage',  title: '操作', width:80, align: 'center'
         	    	 ,formatter:function(value,row,index){
			                var result = "<a href='${ctx}/flow/def/nodeBtnSet?defId=${defId}&nodeId="+row.nodeId+"' class='btn btn-default fa fa-detail'   >编辑</a>";
			                result += "<a href=\"javascript:reCycleNodeBtns('"+row.nodeId+"');\" class=\"btn btn-default fa-recycle\">初始化</a>";
			                return result;
		                  }
         		  }
          ]],
          onLoadSuccess: function () {
        	  handGridLoadSuccess();
          }	
     });     
}   
    function  btnsFormatter(btns,row,index)
    {
		var rs="";
		for (x in btns){
			var b = btns[x];
			if(b.name&&b.name.length&&b.name.length!=0){
				rs += b.name+", ";
			}
		}	
		if(rs.length!=0){
			rs = rs.substring(0,rs.length-1);
		}
		return rs;
    }
    
    function typeFormatter(value,row,index){
        if(value=='START'){
			return '<span style="color: red;">'
			+"开始节点"
			+"</span>";
		}else if(value=="END"){
			return '<span style="color: orange;">'
			+"结束节点"
			+"</span>";
		}else if(value=="USERTASK"){
			return '<span style="color: blue;">'
			+"普通节点"
			+"</span>";
		}else if(value=="SIGNTASK"){
			return '<span style="color: green;">'
			+"会签节点"
			+"</span>";
		}
		else{
			return "全部";
		}  
    }
 
</script>
