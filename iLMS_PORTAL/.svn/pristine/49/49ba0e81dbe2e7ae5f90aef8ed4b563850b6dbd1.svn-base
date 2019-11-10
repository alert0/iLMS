<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<title>分类管理</title>
<base target="_self" />
<script type="text/javascript">
	var catId = "${sysCategory.id}";
	var rootId = 0;

	//根节点右键菜单
	var sysCategoryMenu;
	//私有节点右键菜单
	var priTypeMenu;
	//普通公共节点右键菜单
	var typeMenu;

	//
	var typeId = 1;

	//树
	var typeTree;
	var expandByDepth = 0;

	$(function() {
		//菜单
		//loadMenu();
		//加载树
		catId = $("#sysCategoryKey").val();
		loadTree();
		//改变分类
		$("#sysCategoryKey").change(function() {
			catId = $("#sysCategoryKey").val();
			loadTree();
		});
	});

	//加载树
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
				onAsyncSuccess : zTreeOnAsyncSuccess
			}
		};
		var url = __ctx + "/system/sysType/getByParentId";
		var params = {
			'catId' : catId
		};
		$.post(url, params, function(result) {
			typeTree = $.fn.zTree.init($("#typeTree"), setting, result);

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

	var curSelectNode;
	//左击
	function zTreeOnLeftClick(event, treeId, treeNode) {
		curSelectNode = treeNode;
		var typeId = treeNode.id;
		if (catId == typeId)
			return;
		var url = __ctx + "/system/sysType/sysTypeEdit?id=" + typeId;
		$("#listFrame").attr("src", url);
	};

	//获取根节点并修改节点的图标。
	function zTreeOnAsyncSuccess(e, treeId, treeNode, msg) {
		typeTree = $.fn.zTree.getZTreeObj("typeTree");
		var node = typeTree.getNodeByParam("id", catId);
		if (node && node.id == catId) {
			//设置根节点。
			node.isRoot = 1;
			node.icon = __ctx + "/styles/default/images/icon/root.png";
			typeTree.updateNode(node);
		}
		//如果有选择节点，则重新选择该节点。
		if (curSelectNode) {
			curSelectNode = glTypeTree.getNodeByParam("id", curSelectNode.typeId);
			typeTree.selectNode(curSelectNode);
		}
	};

	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		//debugger;
		if (treeNode) {
			typeTree.selectNode(treeNode);
			var isfolder = treeNode.isFolder;
			var h = $(window).height();
			var w = $(window).width();
			var menuWidth = 120;
			var menuHeight = 75;
			var menu = null;
			if (treeNode != null) {
				if (treeNode.id == catId) {
					menu = $('#sysCategoryMenu');
				} else if (treeNode.ownerId == 0) {
					menu = $('#typeMenu');
				} else {
					menu = $('#priTypeMenu');
				}
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

	//展开收起
	function treeExpandAll(type) {
		typeTree = $.fn.zTree.getZTreeObj("typeTree");
		typeTree.expandAll(type);
	};

	//添加节点
	function addNode(isPriNode) {
		var selectNode = getSelectNode();
		if (!selectNode)
			return;
		var isRoot = 0;
		if (selectNode.id == catId) {
			isRoot = 1;
		}
		//isPriNode标记添加的节点是公共节点还是私有节点  ，0 ==公共节点 ，1==私有节点
		var url = "${ctx}/system/sysType/sysTypeEdit?parentId=" + selectNode.id + "&isPriNode=" + isPriNode + "&isRoot=" + isRoot;
		$("#listFrame").attr("src", url);

	};

	//节点排序
	function sortNode() {
		var selectNode = getSelectNode();
		var nodeId = selectNode.id;
		var url = __ctx + '/system/sysType/sysTypeSortList?id=' + nodeId;
		url = url.getNewUrl();
		showDialog({
			url : url,
			width : 300,
			height : 300,
			title : "分类排序"
		});
	};
	//删除
	function delNode() {
		var selectNode = getSelectNode();
		var nodeId = selectNode.id;
		if (nodeId == rootId) {
			$.topCall.warn('该节点为系统分类 ,不允许该操作');
			return;
		}
		var callback = function(rtn) {
			if (!rtn)
				return;
			var url = "${ctx}/system/sysType/remove";
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
				reFresh();
			});
		};
		$.topCall.confirm('温馨提示', '确定删除？', callback);
	};

	//选择资源节点。
	function getSelectNode() {
		typeTree = $.fn.zTree.getZTreeObj("typeTree");
		var nodes = typeTree.getSelectedNodes();
		var node = nodes[0];
		return node;
	};
	//点刷新按钮
	function reFresh() {
		loadTree();
	};

	//菜单对应项
	function menuHandler(item) {
		if ('add' == item.name) {
			addNode(0);
		} else if ('remove' == item.name) {
			delNode();
		} else if ('sort' == item.name) {
			sortNode();
		} else if ('priadd' == item.name) {
			addNode(1);
		} else if ('fresh' == item.name) {
			reFresh()
		}
	};

	function showDialog(param) {
		var baseConfig = {
			title : '',
			width : 450,
			height : 300,
			modal : true,
			resizable : true,
			passConf : {
				loadTree : loadTree
			}
		};
		$.extend(baseConfig, param);
		$.topCall.dialog({
			src : baseConfig.url,
			base : baseConfig
		});
	};

	function refreshNode() {
		loadTree();
	}
</script>

<style>
</style>

</head>

<body class="easyui-layout">

	<div data-options="region:'west',split:true,border:false,title:'系统分类管理'" style="width: 200px;">
		<div style="width: 100%;">
			<select id="sysCategoryKey" class="form-control">
				<c:forEach var="key" items="${sysCategoryList}">
					<option style="text-align: left" value="${key.id}">${key.name}</option>
				</c:forEach>
			</select>
		</div>
		<!--<div class="tree-toolbar">
					<a class="btn btn-sm btn-primary fa-refresh sysType-tree" href="javascript:reFresh();"> 刷新</a> 
					<a class="btn btn-sm btn-primary fa-expand sysType-tree" href="javascript:treeExpandAll(true)"> 展开</a> 
					<a class="btn btn-sm btn-primary fa-compress sysType-tree" href="javascript:treeExpandAll(false)"> 收缩</a>
				</div>-->
		<div id="typeTree" class="ztree"></div>
	</div>

	<div data-options="region:'center',border:false" style="text-align: center;">
		<div class="treeFrame">
			<iframe id="listFrame" src="sysTypeGet" frameborder="no" width="100%" height="100%"></iframe>
		</div>
	</div>
	<div class="hidden">
		<div id="sysCategoryMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
			<div data-options="name:'add'">增加普通分类</div>
			<!-- 			     <div data-options="name:'priadd'" >增加私有分类</div> -->
			<!-- 			     <div data-options="name:''" >导出</div> -->
			<!-- 			     <div data-options="name:''" >导入</div> -->
			<div data-options="name:'fresh'">刷新</div>
			<div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>
		</div>
		<div id="typeMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
			<div data-options="name:'add'">增加普通分类</div>
			<!-- 			     <div data-options="name:'priadd'" >增加私有分类</div> -->
			<div data-options="name:'remove'">删除分类</div>
			<!-- 			     <div data-options="name:''" >导出</div> -->
			<!-- 			     <div data-options="name:''" >导入</div> -->
			<div data-options="name:'fresh'">刷新</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>
		</div>

		<div id="priTypeMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
			<!-- 			   	 <div data-options="name:'priadd'" >增加私有分类</div> -->
			<div data-options="name:'remove'">删除分类</div>
			<div data-options="name:'fresh'">刷新</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>
		</div>
	</div>


</body>
</html>

