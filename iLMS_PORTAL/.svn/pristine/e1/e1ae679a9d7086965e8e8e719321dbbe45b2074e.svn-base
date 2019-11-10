<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>邮件</title>
	<%@include file="/commons/include/list.jsp"%>
	<%@include file="/commons/include/zTree.jsp"%>
	<style type="text/css">
	html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;}
	.tree-title{overflow:hidden;width:8000px;}
	.ztree{overflow: auto;}
	.group{
		margin-right: 2px;
		margin-left:0px;
		padding-left:0px;
		float:left;
	}
	.tree-toolbar {
	    border-bottom: medium none;
	    border-top: medium none;
	    height: 24px;
	    padding-left: 2px;
	    padding-top: 4px;
	}
	.mail-tree {
	    padding: 2px 6px;
	    line-height: 1.2;
	    font-size: 10px;
	}
	.ztree li span.button{
		height: 14px;
	}
</style>
	<script type="text/javascript">
		//树节点是否可点击
		var treeNodelickAble=true;
		$(function()
		{
			loadTree();
			$('#listFrame').attr('src','${ctx}/mail/mail/mail/mailList');
		});
		//树
		var treeObject;
		//加载树
		function loadTree(){
			var setting = {
				data: {
					key : {
						name: "nickName",
						title: "nickName"
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: 0
					}
				},
				view: {
					selectedMulti: false,
					showLine : false
				},
				callback:{
					onClick: zTreeOnLeftClick
				}
				
			};
			
			$.post("${ctx}/mail/mail/mail/getMailTreeData",
				 function(result){
					for(var i=0;i<result.length;i++){
						var n=result[i];
						n.types==null?n.iconSkin=getFieldIconPath(0):n.iconSkin=getFieldIconPath(n.types);
					}
					treeObject= $.fn.zTree.init($("#treeObject"), setting, result);
					treeObject.expandAll(true); 
				});
		};
		
		function getFieldIconPath(type){
			switch(type){
				case 0: 
					return "mail";
					break;	
				case 1: 
					return "mailInbox";
					break;
				case 2:
					return "mailOutbox";
					break;
				case 3:
					return "mailDrafts";
					break;
				case 4: 
					return "mailTrash";
					break;
				}
		}
		
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			if(treeNode.parentId!=0){
				returnUrl="${ctx}/mail/mail/mail/mailList?id="+treeNode.parentId+"&types="+treeNode.types;
				$("#listFrame").attr("src",returnUrl);
			}
		};
		
		//展开收起
		function treeExpandAll(type){
			treeObject = $.fn.zTree.getZTreeObj("treeObject");
			treeObject.expandAll(type);
		};

</script>

</head>

<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',border:false" title="邮箱" style="width: 220px;height: 100%;overflow: auto;">
			<div class="tree-toolbar">
				<a class="btn btn-sm btn-primary fa-refresh mail-tree" href="javascript:loadTree();"> 刷新</a> 
				<a class="btn btn-sm btn-primary fa-expand mail-tree" href="javascript:treeExpandAll(true)"> 展开</a> 
				<a class="btn btn-sm btn-primary fa-compress mail-tree" href="javascript:treeExpandAll(false)"> 收缩</a>
			</div>
			<div id="treeObject" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<iframe id="listFrame" frameborder="no" width="100%" height="100%"></iframe>
		</div>
	</div>
</body>
</html>

