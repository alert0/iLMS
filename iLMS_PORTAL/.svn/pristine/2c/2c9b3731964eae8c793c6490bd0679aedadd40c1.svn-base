<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<%@include file="/commons/include/incTree.jsp" %>
		<script type="text/javascript" src="${ctx}/js/common/base/enterKeyForSearch.js"></script>
		
		<script>
		var defCategoryTree =  null;
		$(function() {
			loadFlowTree();
			loadGrid("${selType}");
			//回显数据
			var conf = window.passConf;
	  	    if(conf){      //上级窗口的传过来的参数接收方法 
	  	    	var defJson = conf.defJson;
	  	    	if(defJson){
	  	    		for(var i=0;i<defJson.length;i++){
		  	    		$('#defSelectGrid').datagrid('appendRow',defJson[i]);	
					}
	  	    	}	
	  	    }
	  	//快速查找
			/* $("input.quick-find").bind('keyup',function(){
				var str = $(this).val();
				if(!str.trim())return;
				var records  = $('#defSelectGrid').datagrid('getRows');
				for(var i=0;i<records.length;i++){
					if(records[i]['name'].indexOf(str)>-1){
						var rowData = records[i];
						 $('#defSelectGrid').datagrid('deleteRow',i);
						 $('#defSelectGrid').datagrid('insertRow',{index:0, row: rowData});
					}
				} 
			}); */
		});
		
		var onClickRow = function(rowIndex,rowData){
			var records  = $('#defSelectGrid').datagrid('getRows');
			for(var i=0;i<records.length;i++){
				if(records[i]['defId']==rowData['defId']){
					return; 
				}
			}
			$('#defSelectGrid').datagrid('appendRow',rowData);		
		}
		
		function deleteSelect(defId,defKey){
			var records  = $('#defSelectGrid').datagrid('getRows');
			for(var i=0;i<records.length;i++){
				if(records[i]['defId']==defId || records[i]['defKey']==defKey){
					 $('#defSelectGrid').datagrid('deleteRow',i);
					 unSelectRow(defId,defKey);
					 break;
				}
			}
		}
		
		function onUncheck(rowIndex,rowData){
			var records  = $('#defSelectGrid').datagrid('getRows');
			for(var i=0;i<records.length;i++){
				if(records[i]['defId']==rowData['defId']){
					$('#defSelectGrid').datagrid('deleteRow',i);
					break;
				}
			}
		}
		
		function onUnselectAll(rows){
			var records  = $('#defSelectGrid').datagrid('getRows');
			for(var k=0;k<rows.length;k++){
				var rowData = rows[k];
				for(var i=0;i<records.length;i++){
					if(records[i]['defId']==rowData['defId']){
						$('#defSelectGrid').datagrid('deleteRow',i);
						break;
					}
				}
			}
		}
		
		
		function unSelectRow(defId,defKey){
			var records  = $('#bpmDefGridList').datagrid('getRows');
			for(var i=0;i<records.length;i++){
				if(records[i]['defId']==defId || records[i]['defKey']==defKey){
					 $("#bpmDefGridList").datagrid("unselectRow", i);
					 break;
				}
			}
		}
		
		function onCheckAll(rows){
			for(var i = 0 ;i<rows.length;i++){
				onClickRow(i,rows[i]);
			}
		}
		
		function loadFlowTree(){
			//加载分类树
			  defCategoryTree =  new SysTypeTree( $('#defCategoryTree'),{
				   	typeKey: __CAT_FLOW,
				   	beforeClick:function(treeId, treeNode, clickFlag){
				   		return (treeNode.click != false);
				   	},
					onClick:function(event, treeId, treeNode){
						var node = getSelectNode();
						$('#bpmDefGridList').datagrid('load',{"Q^name_^SL":$("#defName").val(),"Q^type_id_^SE":treeNode.id});
					}
				});
		};
		
		function getSelectNode(){
			defCategoryTree = $.fn.zTree.getZTreeObj("defCategoryTree");
			var nodes  = defCategoryTree.getSelectedNodes();
			var node   = nodes[0];
			return node;
		}	
	</script>
        

	</head>
	<body  class="easyui-layout">
		<div data-options="region:'west',split:true,border:false,title:'流程分类',minWidth:215"  style="width:220px;"  >
		
			<div id="defCategoryTree" class="ztree" ></div>	
		</div>
		<div data-options="region:'center',border:false"   style="overflow:hidden" >
				<c:set var="selType" value="true"></c:set>
			<c:if test="${type=='multi' }">
				<c:set var="selType" value="false"></c:set>
			</c:if>
			<!-- 顶部查询操作 -->
		    <div  class="toolbar-search col-md-13 ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons"> 		
							<a class="btn btn-primary btn-sm fa-search"  href="javascript:;" ><span>搜索</span></a>
							<a class="btn btn-sm btn-primary fa-rotate-left"  href="javascript:;" ><span>重置</span></a>
						</div>
						<div class="tools">
							<a href="javascript:void(0);" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body" >
						<form  id="searchForm" class="search-form" >
							<ul>
								<li><span>流程名称:</span><input id="defName" class="inputText" type="text" name="Q^name_^SL"></li>
							</ul>
						</form>
					</div>
			    </div>
			</div>

			<div id="bpmDefGridList" class="my-easyui-datagrid" ></div>
		</div>
		<div data-options="region:'east',border:false"  style="text-align:center;width:210px" >
				 <table  class="form-table" style="width:200px;">
				     <!-- <tr>
				     	<th colspan="2" style="height: 42px;text-align:center;" >已选流程
				     	<input size="5" type="text" class="quick-find" class="inputText"></th>
				    </tr>  -->
				 </table>
					<div id="defSelectGrid" class="my-easyui-datagrid"></div>
		</div>
	</body>
</html>
<script>
 
function loadGrid(isSingleSelect) {
	$('#bpmDefGridList').datagrid({
		url : __ctx + "/flow/def/listJson",
		sortName : 'create_time_',
		sortOrder : 'desc',
		idField:'defId',
		pageSize:10,
		pageList:[10,20,30,40,50],
		toolbar:'.toolbar-search',
  		singleSelect:isSingleSelect,
        onCheckAll:onCheckAll,
        onSelect:onClickRow,
        onUncheck:onUncheck,
        onUnselectAll:onUnselectAll,
        fit:true,
        checkOnSelect:true,
        pagination:true,
        rownumbers:false,
		columns : [ [
		              {field : 'defId',checkbox:true,  width:300,align: 'center'}, 
		              {field : 'name',sortName:"name_",title : '流程名称',width:400,align: 'center',sortable:'true'}, 
		              {field : 'defKey',sortName : "def_key_",title : '流程Key',width:400,align: 'center',sortable: 'true'} 
		           ] ]
	});
	
	$('#defSelectGrid').datagrid({
		data:[],
		sortName : 'defId',
		sortOrder : 'desc',
		idField:'defId',
		pagination:false,
		fitColumns:true,
		 rownumbers:false,
		fit:true,
		columns : [ [
           {field : 'defId', hidden:true,width:50,align: 'center'}, 
            {field : 'name',sortName:"name_",title : '流程名称',width:80,align: 'center'}, 
            {field : 'defKey',hidden:true,title : '流程Key', width:50,align: 'center'},
            {field:'action',title:"操作",width:50,align: 'center',formatter:function(value,row,index){
          	return  "<a onClick=\"javascript:deleteSelect('"+row.defId+"','"+row.defKey+"')\"  class=\"fa fa-delete\"> </a>";
            }}
            ] ],
	});
}
</script>
