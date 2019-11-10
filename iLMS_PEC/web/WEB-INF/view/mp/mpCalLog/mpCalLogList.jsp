<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>订单计算查询</title>
<%@include file="/commons/include/list.jsp"%>
<style type="text/css">
    .red {
        color:#FF0000 ;
    }
    
    .green {
    	color:#00FF00 ;
    }
	
</style>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							  <a id="demandCal" href="javascript:void(0)" class="btn btn-sm btn-primary fa-check" 
							  onclick="demandCal()">
						         <span>需求计算</span>
					          </a>
					          <a id="releaseOrder" href="javascript:;" class="btn btn-sm btn-primary fa-check"
					          onclick="releaseOrder()">
						         <span>发布订单</span>
					          </a>
					          
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
						</form>
					</div>
				
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
		<!-- 下载模板的框架 -->
		<iframe id="downloadiframe" style="display:none;"></iframe>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
		
		/* 每过2分钟查询一次数据 */
		var cal = setInterval(loadGrid,30000);
		clearInterval(cal);
		/* self.setInterval("loadGrid()",12000); */
	});
		
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :   __ctx+"/mp/mpCalLog/curdlistJson",
			idField : "uuid",
			sortName : 'UUID',
			sortOrder : 'desc',
			striped : true,
			fitColumns : false,
			columns : [ [
			{field : 'calType',sortName : "CAL_TYPE",title : '程序名',
		    width : 100,align : 'center',sortable : 'true',
		    	formatter:function(value,row,index){
					if('1' == value){
						return '<span>净需求计算</span>';
					}else{
						return '<span>生成采购订单</span>';
					}
					return value;
				} },
		    {field : 'isLock',sortName : "IS_LOCK",title : '运行状态',
		    width : 100,align : 'center',sortable : 'true',
		    formatter:function(value,row,index){
				if('1' == value){
					return '<span>空闲</span>';
				}else{
					return '<span>运行</span>';
				}
				return value;
			} },
		    {field : 'calStartTimeStr',sortName : "CAL_START_TIME",title : '开始时间',
			width : 200,align : 'center',sortable : 'true'},
			{field : 'calEndTimeStr',sortName : "CAL_END_TIME",title : '结束时间',
			width : 200,align : 'center',sortable : 'true'}
			] ],
			rowStyler: function (index, row) {
                if(row.isLock=='1'){
                    return 'color:green;';
                }else{
                	return 'color:red;';
                }

            },
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
	
	/* 需求计算 */
	function demandCal(){
		var demandCalUrl = encodeURI(__ctx + '/mp/mpCalLog/demandCal');
		$.messager.confirm('提示','是否进行需求计算？',function(r){
		    if (r){
		    	$("#demandCal").attr("disabled", true); 
		    	$("#releaseOrder").attr("disabled", true); 
		    	cal = setInterval(loadGrid,30000);
		    	$.ajax({ 
		    		url: demandCalUrl, 
		    		type: 'json',
		    		success: function(data){
		    			var info = $.parseJSON(data);
		    			if(info.message == '2'){
		    				$.messager.alert("操作提示", "需求计算成功","info");
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    				loadGrid();
		    				clearInterval(cal);
		    			}else if(info.message == '1'){
		    				$.messager.alert("操作提示", "推算失败,请检查数据或联系管理员", "error");
		    				clearInterval(cal);
		    				$("#releaseOrder").attr("disabled", false); 
		    			}else if(info.message == "IS_LOCK"){
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    		    	clearInterval(cal);
		    		    	$.messager.alert("操作提示", "净需求计算功能已被别的用户或进程启动，您现在不能使用，请稍等。", "warning");
		    			}
			            $(this).addClass("done");
			          },
			          error : function(XMLHttpRequest, textStatus, errorThrown){
			        	  $("#demandCal").attr("disabled", false); 
		    		      $("#releaseOrder").attr("disabled", false); 
		    		      $.messager.alert("操作提示", "操作失败,请联系管理员", "error");
			          }
		    	});
		    }else{
		    	return;
		    }
		});
	}
	
	/* 订单发布 */
	function releaseOrder(){
		var releaseOrderUrl = encodeURI(__ctx + '/mp/mpCalLog/releaseOrder');
		$.messager.confirm('提示','是否发布订单？',function(r){
		    if (r){
		    	$("#demandCal").attr("disabled", true); 
		    	$("#releaseOrder").attr("disabled", true); 
		    	cal = setInterval(loadGrid,30000);
		    	$.ajax({ 
		    		url: releaseOrderUrl, 
		    		type: 'json',
		    		success: function(data){
		    			var info = $.parseJSON(data);
		    			if(info.result == '1'){
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    		    	clearInterval(cal);
		    				$.messager.alert("操作提示", info.message,"warning");
		    			}else if(info.result == '2'){
		    				loadGrid();
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    		    	clearInterval(cal);
		    				$.messager.alert("操作提示", "订单生成成功!","info");
		    			}else if(info.result == 'IS_LOCK'){
		    				loadGrid();
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    		    	clearInterval(cal);
		    		    	$.messager.alert("操作提示", "生成采购订单功能已被别的用户或进程启动，您现在不能使用，请稍等!", "warning");
		    			}else{
		    				loadGrid();
		    				$("#demandCal").attr("disabled", false); 
		    		    	$("#releaseOrder").attr("disabled", false); 
		    		    	clearInterval(cal);
		    		    	$.messager.alert("操作提示", "采购订单生成失败!", "error");
		    			}
			            $(this).addClass("done");
			          }
		    	});
		    }else{
		    	return;
		    }
		});
	}
	
</script>
