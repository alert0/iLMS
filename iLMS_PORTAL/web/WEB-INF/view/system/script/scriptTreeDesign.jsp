<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>设计脚本</title>
<%@include file="/commons/include/edit.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
	<div  data-options="region:'west',title:'脚本',split:true" style="width:200px;overflow: scroll;">
        <div id="tree" class="ztree"></div>
	</div>
	<div data-options="region:'center',border:false" >
			<div class="toolbar-panel">
				<div class="buttons" >
					<a href="javascript:void(0)" onclick="preViewScript()" class="btn btn-primary btn-sm fa-cog ">
	                		<span>脚本测试</span>
	            	</a>
					
				</div>
			</div>
			 <table class="table-form" cellspacing="0">
			  <tr>
				<th>
					<span>脚本:</span>
				</th>
				<td>
					<div >
						<textarea mode="text/x-groovy" codemirror="true" mirrorheight="300px" id="script" rows="12" cols="55" name="script" validate="{required:true}"></textarea>
					</div>
				</td>
			   </tr>
				
			  </table>
		</div>
	 
	</div>
</body>
</html>
<script>
//加载树
var expandByDepth = 0;
var curSelectNode;
function loadTree() {
	var setting = {
		async : {enable : false},
		data : {
			key : {name : "name"},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : 1
			}
		},
		view : {
			selectedMulti : false
		},
		edit : {
			drag : {
				prev : false,
				inner : false,
				next : false,
				isMove : false
			},
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		callback : {
			onClick : zTreeOnLeftClick,
		}
	};
	var url = __ctx + "/system/script/getScriptTreeData";
	var params = {};
	$.post(url, params, function(result) {
		typeTree = $.fn.zTree.init($("#tree"), setting, result);
		if (expandByDepth != 0) {
			var nodes = typeTree.getNodesByFilter(function(node) {
				return (node.level == expandByDepth);
			});
			if (nodes.length > 0) {
				for (var idx = 0; idx < nodes.length; idx++) {
					typeTree.expandNode(nodes[idx], false, false);
				}
			}
		} else {
			typeTree.expandAll(true);
		}

	});

};
//获取根节点并修改节点的图标。
function zTreeOnAsyncSuccess(e, treeId, treeNode, msg) {
	typeTree = $.fn.zTree.getZTreeObj("typeTree");
	var node = typeTree.getNodeByParam("id", catId);
 
	//如果有选择节点，则重新选择该节点。
	if (curSelectNode) {
		curSelectNode = glTypeTree.getNodeByParam("id", curSelectNode.typeId);
		typeTree.selectNode(curSelectNode);
	}
};
//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	curSelectNode = treeNode;
	var parentId = treeNode.id;
 
	if(curSelectNode.title==""){
		$.topCall.warn('请选择个体的脚本');
		return false;
	}
	InitMirror.save();
	var content=$("#script").val()+curSelectNode.title;
	InitMirror.editor.setCode(content);
};


function refreshNode() {
	loadTree();
};



//选择资源节点。
function getSelectNode() {
	typeTree = $.fn.zTree.getZTreeObj("tree");
	var nodes = typeTree.getSelectedNodes();
	var node = nodes[0];
	return node;
};
</script>
<script>
	$(function() {
		loadTree();
	});
	function preViewScript(){
		InitMirror.save();
		var script=$("#script").val();
		if(script==""){
			   $.topCall.error("请编写脚本内容");
			return false;
		}
		var parm={script:script,key:$("#name").val()};
		var data=Object.toAjaxJson(__ctx+"/system/script/executeScript", "", parm)
		 $.topCall.alert("执行结果","执行结果："+data.val);
		 
	}
	function getResult(){
		InitMirror.save();
		var script=$("#script").val();
		return script;
	}
</script>
