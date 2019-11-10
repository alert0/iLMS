<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springsecurity.org/jsp"%>
<html>
<head>
<title>拉动推算控制台</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'north'" style="background-color: #F5F5F5;height:35px;">
			<div class="container-fluid" style="margin-top: 3px;">
				<div class="row">
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<span style="font-size: medium;">信息点:</span>
						<select id="planCode" onchange="planCodeChange()" class="inputText"></select>
					</div>
				    <div class="col-md-7">
				    	<div class="buttons">
				    		<div style="float: left;font-size: medium;">推算服务状态：</div>  
				    		<div id="reckon_state" style="background-color: white;width: 100px;height: 25px;float: left;text-align: center;font-size: medium;margin-top: 2.5px;"></div>
							<a style="float: left;"><span>&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
							<a style="float: left;" id="start_btn" class="btn btn-sm btn-success fa-ok" onclick="startReckon()">
								<span>启动</span>
							</a>
							<a style="float: left;"><span>&nbsp;&nbsp;&nbsp;</span></a>
							<a style="float: left" id="stop_btn" class="btn btn-sm btn-warning fa-cancel" onclick="stopReckon()">
								<span>停止</span>
							</a>
						</div>
				    </div>
				</div>
			</div>
	    </div>
	    <div data-options="region:'center'">
	    	<div id="tabs" class="easyui-tabs" fit="true" border="false">
	    		<div title="过点车序" data-options="closable:false" style="padding:2px;">
	    			<iframe id="listFrame1" src="" frameborder="no" width="100%" height="100%"></iframe>   
			    </div>   
			    <div title="当前零件余量" data-options="closable:false" style="overflow:auto;padding:2px;">
			    	<iframe id="listFrame2" src="" frameborder="no" width="100%" height="100%"></iframe>   
			    </div>   
			    <div title="截止产品编号零件余量" data-options="closable:false" style="padding:2px;">
			    	<iframe id="listFrame3" src="" frameborder="no" width="100%" height="100%"></iframe>   
			    </div>
			    <div title="推算服务状态查询" data-options="closable:false" style="padding:2px;">
			    	<iframe id="listFrame4" src="" frameborder="no" width="100%" height="100%"></iframe>   
			    </div>
			    <div title="零件余量修改日志" data-options="closable:false" style="padding:2px;">
			    	<iframe id="listFrame5" src="" frameborder="no" width="100%" height="100%"></iframe>   
			    </div>     
	    	</div>
	    </div>
    </div>  
</body>
</html>
<script>
$(function(){
	//初始禁止修改
	$("#start_btn").attr("disabled", true);
	$("#start_btn").css("pointer-events","none");
	$("#stop_btn").attr("disabled", true);
	$("#stop_btn").css("pointer-events","none");
	
	//信息点下拉框
	HtUtil.loadComboBox({
		url : __ctx + '/jit/jitReckon/loadPlanCodeComboData',
		param : {
			planCodeType : 'JITI'
		},
		valueKey : 'valueKey',
		textKey : 'valueName',
		dictArr:[{
			typeKey:'PLAN_CODE',
			el : '#planCode', 
			addBlank : true
		}]
	});
	
	$("#listFrame1").attr("src","jitVehQueue");
	$('#tabs').tabs({    
	    border:false,    
	    onSelect:function(title,index){     
	    	if(0 == index){
	    		$("#listFrame1").attr("src","jitVehQueue");
	    	}else if(1 == index){
	    		$("#listFrame2").attr("src","jitPartRemain");
	    	}else if(2 == index){
	    		$("#listFrame3").attr("src","jitPartRemainProd");
	    	}else if(3 == index){
	    		$("#listFrame4").attr("src","jitReckonState");
	    	}else if(4 == index){
	    		$("#listFrame5").attr("src","jitPartRemainLog");
	    	}
	    }    
	}); 
});
	
	//信息点切换事件
	function planCodeChange(){
		var planCode = $("#planCode").val();
		if(null == planCode || '' == planCode || undefined == planCode){
			$("#reckon_state").css("background-color","white");
			$("#reckon_state").html("");
			$("#start_btn").attr("disabled", true);
			$("#start_btn").css("pointer-events","none");
			$("#stop_btn").attr("disabled", true);
			$("#stop_btn").css("pointer-events","none");
		}else{
			$.ajax({
	    	    type: "post",
	    	    url: __ctx+"/jiso/jisoReckon/queryReckonState",
	    	    data: {
	    	        "planCode" : planCode
	    	    },
	    	    dataType: "json",
	    	    success: function(res){
	    	    	if(res.result == 1){
	    	    		if(res.message == '1'){
	    	    			$("#start_btn").attr("disabled", true);
	    	    			$("#start_btn").css("pointer-events","none");
	    	    			$("#stop_btn").removeAttr("disabled");
	    	    			$("#stop_btn").css("pointer-events","auto");
	    	    			$("#reckon_state").css("background-color","green");
	    	    			$("#reckon_state").html("启动");
	    	    		}else if(res.message == '0') {
	    	    			$("#stop_btn").attr("disabled", true);
	    	    			$("#stop_btn").css("pointer-events","none");
	    	    			$("#start_btn").removeAttr("disabled");
	    	    			$("#start_btn").css("pointer-events","auto");
	    	    			$("#reckon_state").css("background-color","red");
	    	    			$("#reckon_state").html("停止");
	    	    		} 
					}else{
						$.topCall.error("获取推算状态失败，请联系管理员");
					}
	    	    }
	    	});
			
		}
		
	}
	
	//启动厂外同步推算服务
	function startReckon(){
		$.messager.confirm('确认','确认要[启动]推算服务吗？',function(r){    
		    if (r){    
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/jit/jitReckon/updateReckonExecState",
		    	    data: {
		    	        "execState" : '1'
		    	    },
		    	    dataType: "json",
		    	    success: function(res){
		    	    	if(res.result == 1){
		    	    		$("#start_btn").attr("disabled", true);
		    	    		$("#start_btn").css("pointer-events","none");
		    	    		$("#stop_btn").removeAttr("disabled");
		    	    		$("#stop_btn").css("pointer-events", "auto");
		    	    		$("#reckon_state").css("background-color","green");
		    	    		$("#reckon_state").html("启动");
		    	    		$.topCall.success("启动推算服务成功");
						}else{
							$.topCall.error("启动推算失败，请联系管理员");
						}
		    	    }
		    	});
		    }    
		});
	}
	
	//停止厂外同步推算服务
	function stopReckon(){
		$.messager.confirm('确认','确认要[停止]推算服务吗？',function(r){    
		    if (r){    
		    	$.ajax({
		    	    type: "post",
		    	    url: __ctx+"/jit/jitReckon/updateReckonExecState",
		    	    data: {
		    	        "execState" : '0'
		    	    },
		    	    dataType: "json",
		    	    success: function(res){
		    	    	if(res.result == 1){
		    	    		$("#stop_btn").attr("disabled", true);
		    	    		$("#stop_btn").css("pointer-events","none");
		    	    		$("#start_btn").removeAttr("disabled");
		    	    		$("#start_btn").css("pointer-events", "auto");
		    	    		$("#reckon_state").css("background-color","red");
		    	    		$("#reckon_state").html("停止");
		    	    		$.topCall.success("停止推算服务成功");
						}else{
							$.topCall.error("停止推算失败，请联系管理员");
						}
		    	    }
		    	}); 
		    }    
		});
	}

</script>