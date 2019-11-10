<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/bpm/service/bpmImageService.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/raphael/raphael.min.js"></script>
<style type="text/css">
div.icon {
	border: 1px solid #868686;
	line-height: 10px;
	width: 10px;
	height: 10px;
	float: left;
	overflow: hidden;
	margin-top: 3px;
}

.target span {
	margin: 0 10px 0 3px;
	font-size: 12px;
	font-weight: bold;
	float: left;
	vertical-align: middle;
	white-space: nowrap;
}
</style>
</head>
<body ng-app="BpmImageService">
	<div class="easyui-layout" style="height:100%">
		<div style="margin: 10px 5px 55px 10px;">
			<div class="target">
				<div class="icon" style="background: #FF0000;"></div>
				<span>待审批</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #F89800;"></div>
				<span>提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #FFE76E;"></div>
				<span>重新提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #00FF00;"></div>
				<span>同意</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #C33A1F;"></div>
				<span>挂起</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #0000FF;"></div>
				<span>反对</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #8A0902;"></div>
				<span>驳回</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #FFA500;"></div>
				<span>驳回到发起人</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #023B62;"></div>
				<span>撤销</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #F23B62;"></div>
				<span>撤销到发起人</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #338848;"></div>
				<span>会签通过</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #82B7D7;"></div>
				<span>会签不通过</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #EEAF97;"></div>
				<span>人工终止</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #4A4A4A;"></div>
				<span>完成 </span>
			</div>
			<c:if test="${parentInstId!=null&&parentInstId!=0}">
				<div class="target">
				<span style="cursor:pointer;" onclick="showFlowMap('${parentInstId}')"><i class='fa fa-map'></i>查看主流程图</span>
			</div>
           </c:if>
           <c:if test="${from!='task'}">
	           <a class="btn btn-xs btn-primary fa-play" onclick="plays()">
					<span>审批轨迹</span>
				</a>
			</c:if>
		</div>
		<div id="divContainer" style="overflow: scroll;height: 95%;">
		<c:choose>
		   <c:when test="${not empty bpmProcessInstanceList  }">  
		           <c:forEach var="instance" items="${bpmProcessInstanceList}">
						<div id="imageContainer" style="margin-top:40px;position:relative;background:url('${ctx}/bpm/bpmImage?<c:if test="${!empty instance.bpmnInstId}">bpmnInstId=${instance.bpmnInstId}</c:if><c:if test="${empty instance.bpmnInstId}">defId=${instance.procDefId}</c:if>') no-repeat;width:${bpmDefLayout.width}px;height:${bpmDefLayout.height}px;">
								<c:forEach var="layout" items="${bpmDefLayout.listLayout}">
									<c:choose>
										<c:when test="${layout.nodeType == 'CALLACTIVITY'}">
								   		<div class="flowNode"  onclick="showFlowMap('${instId}','${layout.nodeId}','${layout.nodeType}','subFlow')"  ht-bpm-image="{instId:'${instId}',nodeId:'${layout.nodeId}',nodeType:'${layout.nodeType}'}" style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;"></div>
									</c:when>
									<c:otherwise>
										<div class="flowNode" ht-bpm-image="{instId:'${instId}',nodeId:'${layout.nodeId}',nodeType:'${layout.nodeType}'}" style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;"></div>
									</c:otherwise>
							 	   </c:choose>
								</c:forEach>
						</div>
					</c:forEach>
		   </c:when>
		   <c:otherwise> 
		    	<div style="margin-top:40px;position:relative;background:url('${ctx}/bpm/bpmImage?defId=${defId}') no-repeat;width:${bpmDefLayout.width}px;height:${bpmDefLayout.height}px;">
								
				</div>
		   </c:otherwise>
		</c:choose>
		</div>
	</div>
</body>
</html>
<script>
	var paper = null,
		animationMs = 500,
		animationEasing = '>',
		dispearDelay = 2000,
		deviation = 5,
		amendX = 0,
		amendY = 0,
		defaultStyle = {"stroke" : "red", "stroke-width" : "3px"};

	function showFlowMap(instId,nodeId,nodeType,type) {
		var url= __ctx+"/flow/instance/instanceFlowImage?type="+type+"&id="+instId+"&nodeId="+nodeId+"&from="+'${from}';
		var title=type=="subFlow"?"查看子流程":"查看主流程";
		var def = {title:title,width:800,height:550, modal:true,resizable:true,iconCls: 'fa fa-table'};
	 
		$.topCall.dialog({
			src:url,
			base:def
		});
	}
	
	function initPaper(){
		var imageContainer = $("#imageContainer"),
			containerWidth = imageContainer.width(),
			containerHeight = imageContainer.height();
		
		paper = Raphael("imageContainer", containerWidth, containerHeight);  
	}
	
	function handle(c, i){
		setTimeout(function(){
			if(c.type=='event'){
				amendPoint(c.point);
				circle(c);
			}
			if(c.type=='line'){
				for(var i=0,m;m=c.points[i++];){
					amendPoint(m);
				}
				edge(c);
			}
			if(c.type=='rect'){
				amendPoint(c.point);
				rect(c);
			}
			if(c.type=='diamond'){
				amendPoint(c.point);
				diamond(c);
			}
		}, (c.sn-1)*animationMs);
	}
	
	// 坐标点修正
	function amendPoint(point){
		point.x = Math.round(point.x - amendX);
		point.y = Math.round(point.y - amendY);
	}
	
	// 绘制矩形
	function rect(c){
		var x=c.point.x,
			y=c.point.y,
			width=c.size.width,
			height=c.size.height;
		var element = paper.rect(x+width/2, y+height/2, 0, 0, 10).attr(defaultStyle);
		element.animate({x:x,y:y,width:width,height:height}, animationMs, animationEasing, function(){
			var me = this;
			document.getElementById('divContainer').scrollLeft=x;
			document.getElementById('divContainer').scrollTop=y;
			setTimeout(function(){
				me.remove();
			}, dispearDelay);
		});
	}
	
	// 绘制菱形
	function diamond(c){
		var x=c.point.x,
			y=c.point.y,
			length=c.length;
		var element = paper.rect(x + length/6, y + length/6, length/1.5, length/1.5).attr(defaultStyle);
		element.animate({transform:"r45"}, animationMs, animationEasing, function(){
			var me = this;
			document.getElementById('divContainer').scrollLeft=x;
			document.getElementById('divContainer').scrollTop=y;
			setTimeout(function(){
				me.remove();
			}, dispearDelay);
		});
	}
	
	// 绘制圆圈
	function circle(c){
		var x=c.point.x,
			y=c.point.y,
			r=c.radius;
		var element = paper.circle(x + r, y + r, 0).attr(defaultStyle);
		element.animate({"r":r}, animationMs, animationEasing, function(){
			var me = this;
			setTimeout(function(){
				me.remove();
			}, dispearDelay);
		});
	}
	
	// 绘制连线
	function edge(c){
		var points = c.points,
			size = points.length-1,
			sp = points[0],
			elements = [];
		
		for(var i=0; i < size; i++){
			var twins = [];
			twins.push(points[i]);
			twins.push(points[i+1]);
			edgeAssembly(twins, elements, i+1, size);
		}
	}
	
	// 每两个点为一组绘制连线
	function edgeAssembly(twins, elements, index, size){
		var sp = twins[0],
			ep = twins[1];
		var element = paper.path("M" + sp.x + " " + sp.y).attr(defaultStyle);
		elements.push(element);
		var adjustX = ep.x,
			adjustY = ep.y;
		if(index==size){
			if(sp.x != ep.x){
				adjustX = ep.x - deviation;
			}
			if(sp.y != ep.y){
				adjustY = ep.y - deviation;
			}
		}
		var path = "M" + sp.x + " " + sp.y + "L" + adjustX + " " + adjustY;
		var ms = animationMs/size;
		var anim = Raphael.animation({"path":path}, ms, animationEasing, function(){
			var me = this;
			if(index==1){
				setTimeout(function(){
					for(var i=0,c;c=elements[i++];){
						c.remove();
					}
				}, dispearDelay + ms*(size-1));
			}
			if(index==size){
				me.attr({"arrow-end" : "block-wide-long"});
			}
		});
		element.animate(anim.delay(ms*(index-1)));
	}
	
	//显示指定流程实例的轨迹图
	function plays(){
		if(!paper) initPaper();
		var dealNodes = getDealNodes();
		paper.clear();
		if(dealNodes && dealNodes.length >0){
			calcAmend(dealNodes[0]);
			for(var i=0,c;c=dealNodes[i++];){
				handle(c);
			}	
		}
	}
	
	// 计算修正值
	function calcAmend(track){
		var firstNode = $("#imageContainer").find("div.flowNode:first"),
			left = firstNode.css("left"),
			top = firstNode.css("top"),
			leftVal = Number(left.replace("px","")),
			topVal = Number(top.replace("px",""));
		
		amendX = track.point.x - leftVal;
		amendY = track.point.y - topVal;
	}

	function getDealNodes(){
		var nodes = null;
		$.ajaxSetup({  
		    async : false  
		});  
		var url= __ctx+"/flow/instance/getPathNodes?id="+'${instId}';
		$.post(url,function(result){
			nodes = result;
		});
		return nodes;
	}
</script>