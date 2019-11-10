<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<html>
<head>
<title>数学函数编辑器</title> 
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/common/util/mathCaltools.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
<f:link href="expression.css"></f:link>
<script type="text/javascript">
	var scriptTree,
		scriptTypes = [],
		varTree,
		isSingleRecord = false,
		curElement,
		parentScope;
	
	$(function() {
		parentScope=window.passConf?window.passConf.scope:"";
		curElement = parentScope.editingField.option.statFun; 
		if(curElement){
			$("#exp_text").text(curElement);
		}
		initTools(MathCaltools.commonTools,$("#tools_comment"));
		initTools(MathCaltools.subTableTools,$("#sub_tools_comment"));
		$(".calTool").bind("mouseenter mouseleave", function() {
			$(this).toggleClass("tool-hover");
		});
		$(".toolbar_btn").bind("mouseenter mouseleave", function() {
			$(this).toggleClass("toolbar_btn_hover");
		});
		initFieldTree(parentScope);
	});
	
	function clickOk() {
		parentScope.editingField.option.statFun = InitMirror.editor.getCode();
		parentScope.$digest();
	};
	
	function clickHandler() {
		var exp = $(this).attr("exp");
		InitMirror.editor.insertCode(exp);
		//某些运算符 添加到脚本中以后  需要修改光标所在的位置
		InitMirror.editor.moveCursorPos($(this).attr("step"));
	}
	
	//初始化运算符工具窗口
	function initTools(calTools,t) {
		var tool;
		while (tool = calTools.shift()) {
			var div = document.createElement("div");
			div.title = tool.msg;
			div.innerHTML = tool.title;
			div.className = "calTool";
			div.setAttribute("exp", tool.exp);
			if(tool.step){
				div.setAttribute("step", tool.step);
			}
			div.onclick = tool.clickHandler || clickHandler;
			t.append(div);
		}
	}
	
	
	//获取表单字段
	function initFieldTree(scope) {
		var ztreeCreator = new ZtreeCreator('colstree',__ctx+"/bo/def/bODef/getBOTree")
			.setChildKey("children").setDataKey({idKey:"id",name:"desc",title:"desc"})
			.setCallback({beforeClick:nodeBeforeClick,onClick:zTreeOnClick})
			.initZtree({ids:scope.boIds.join(",")},function(treeObj){
					boTreeObject =treeObj 
			 }); 
	};
	
	function nodeBeforeClick(treeId, treeNode, clickFlag){
		if(treeNode.dataType!="number"){
			$.topCall.toast('提示信息','请选择数字类型的字段！');
			return false;
		}
	}
	//双击树添加内容到规则表达式
	function zTreeOnClick(event, treeId, treeNode) {
		var isMain = treeNode.getParentNode().nodeType=='main';
		var path="data."+treeNode.path+"."+treeNode.name;
		if(!isMain && isSingleRecord){
			path = "item."+treeNode.name;
		}
		
		if(isSingleRecord && isMain){
			$.topCall.error("子表中单条记录运算模式下,不能选择主表字段!");
			return;
		}
		
		if(isSingleRecord||isMain){
			var desc ='{'+treeNode.desc+"("+path+")"+'}'
		}
		else{
			var desc ='[{'+treeNode.desc+"("+path+")"+'}]'
		}
		InitMirror.editor.insertCode(desc);
	};

	//是否子表单条记录运算
	function singleSubRecord(t){
		isSingleRecord = t.checked?true:false;
		if(isSingleRecord){
			$("#sub_tools_comment").hide();
		}
		else{
			$("#sub_tools_comment").show();
		}
	};
</script>
</head>
<body>
	<div class="math_div">
		<div class="top_div">
			<div class="field_left_div" style="overflow:auto;">
				<div title="表字段">
					<ul id="colstree" style="margin:5px;" class="ztree"></ul>
				</div>
			</div>
			<div class="cal_right_div" style="margin-right: 20px;">
				<div style="height:60%;">
					<div class="l-tab-links title-div">
						<span style="margin-left:8px;">通用运算符</span>
					</div>
					<div id="tools_comment"></div>
			.	</div>
				
				<div style="height:40%;">
					<div class="l-tab-links title-div" style="border-top:1px solid #A7D3F0;">
						<span style="margin-left:8px;">子表字段运算符</span>
					</div>
					<div id="sub_tools_comment"></div>
				</div>
			</div>
		</div>
		<div class="bottom_div">
			<div class="l-tab-links title-div" style="height:42px;">
				<div style="width:100%;height:100%;font-weight:normal;padding-left:8px;">
					<span style="font-weight:bold;">运算表达式</span>
					<label style="margin-left:8px;"><input type="checkbox" onclick="singleSubRecord(this)"/>子表中单条记录运算</label>
				</div>
			</div>
			<div style="border:1px solid #A7D3F0; margin-left:3px; margin-right:10px;">
			     
				<textarea id="exp_text" codemirror="true" mirrorheight="140px" cols="110" rows="6"></textarea>
			</div>
		
		</div>
	</div>
</body>
</html>