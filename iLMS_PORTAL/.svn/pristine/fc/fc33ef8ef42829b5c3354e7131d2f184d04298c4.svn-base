<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="tabs" fit="true" >
				<div title="组织简介">
					<iframe id="listFrame" src="${ctx}/org/org/orgGet?id=${param.id}&demId=${param.demId}" frameborder="no" width="100%" height="100%"></iframe>
				</div>
				<div title="组织人员" data-options="closable:false" style="overflow: auto; padding: 0; margin: 0">
					<iframe id="listFrame2" src="${ctx}/org/org/orgUserList?orgId=${param.id}&authId=${param.authId}&authOrgId=${param.authOrgId}&demId=${param.demId}" frameborder="no" width="100%" height="100%"></iframe>
				</div>
				<div title="组织岗位" data-options="closable:false" style="overflow: auto">
					<iframe id="listFrame2" src="${ctx}/org/orgRel/orgRelList?orgId=${param.id}&authId=${param.authId}&authOrgId=${param.authOrgId}" frameborder="no" width="100%" height="100%"></iframe>
				</div>
				<div title="组织参数" data-options="closable:false" style="overflow: auto">
					<iframe id="listFrame2" src="${ctx}/system/sysUserParams/sysUserParamsEdit?type=2&id=${param.id}" frameborder="no" width="100%" height="100%"></iframe>
				</div>
				<div title="组织分级管理设置" data-options="closable:false" style="overflow: auto">
					<iframe id="listFrame5" src="${ctx}/org/orgAuth/orgAuthList?demId=${param.demId}&orgId=${param.id}&authId=${param.authId}&isGrade=true" frameborder="no" width="100%" height="100%"></iframe>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
<script>
	//加载树
	var expandByDepth = 0;
	var curSelectNode;
	var authId = "${param.authId}";
	var authOrgId = "${param.authOrgId}";
	function loadTree() {
		var setting = {
			async : {
				enable : false
			},
			data : {
				key : {
					name : "name"
				},
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
				onRightClick : zTreeOnRightClick,
			//onAsyncSuccess : zTreeOnAsyncSuccess
			}
		};
		var url = __ctx + "/org/orgAuth/getAuthOrgTree";
		var params =  {"authId":authId,"orgId":authOrgId};
		$.post(url, params, function(result) {
			typeTree = $.fn.zTree.init($("#orgTree"), setting, result);
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

		$('#grid').datagrid("reload", {
			parentId : parentId
		})
	};
	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		//debugger;
		if (treeNode) {
			typeTree.selectNode(treeNode);
			curSelectNode = treeNode;
			var isfolder = treeNode.isFolder;
			var h = $(window).height();
			var w = $(window).width();
			var menuWidth = 120;
			var menuHeight = 75;
			var menu = null;
			if (treeNode != null) {
				menu = $('#orgMenu');
			}
			var x = e.pageX, y = e.pageY;
			if (e.pageY + menuHeight > h) {
				y = e.pageY - menuHeight;
			}
			if (e.pageX + menuWidth > w) {
				x = e.pageX - menuWidth;
			}
			menu.menu('show', {
				left : x,
				top : y
			});
		}
	};
	//菜单对应项
	function menuHandler(item) {
		if ('add' == item.name) {
			editNode(0);
		} else if ('remove' == item.name) {
			delNode();
		} else if ('sort' == item.name) {
			sortNode();
		} else if ('edit' == item.name) {
			editNode(1);
		} else if ('fresh' == item.name) {
			refreshNode();
		}
	};
	function refreshNode() {
		loadTree();
	};
	//添加节点
	function editNode(type) {
		var selectNode = getSelectNode();
		if (!selectNode) {
			return;
		}

		var url = "${ctx}/org/org/orgEdit?parentId=" + selectNode.id;
		if (type == 1)
			url = "${ctx}/org/org/orgEdit?id=" + selectNode.id;
		$("#listFrame").attr("src", url);

	};

	//删除
	function delNode() {
		var selectNode = getSelectNode();
		var nodeId = selectNode.id;
		if (nodeId == "0") {
			$.topCall.warn('该节点为根节点，不可删除');
			return;
		}

		var callback = function(rtn) {
			if (!rtn)
				return;
			var url = "${ctx}/org/org/remove";
			var params = {
				id : nodeId
			};
			$.post(url, params, function(responseText) {
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {//成功
					typeTree.removeNode(selectNode);
					$("#listFrame").attr("src", "about:blank");
				} else {
					$.topCall.error(obj.getMessage());
				}
				refreshNode();
			});
		};
		$.topCall.confirm('温馨提示', '确定删除？', callback);
	};
	//选择资源节点。
	function getSelectNode() {
		typeTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = typeTree.getSelectedNodes();
		var node = nodes[0];
		return node;
	};
</script>
<script>
	$(function() {
		$('#tabs').tabs({ 
		　　width: $("#tabs").parent().width(), 
		　　height: "auto" 
		}); 
		loadTree();
	});
</script>
