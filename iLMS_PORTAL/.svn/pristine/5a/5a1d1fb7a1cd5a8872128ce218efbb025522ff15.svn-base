<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${formName}管理</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
	<script>
		var  curSelectNode = null;
		var treeConf =JSON.parse('${treeConf}');
		var boDataTree = null;
		//加载树
		$(function() {
			loadTree();
			
			$("#treeLayout").mousedown(function(e){
				if(e.which==3){
					zTreeOnRightClick(e);
				}
			})
		});
		
		function loadTree(){
			var url = __ctx+"/form/formBus/${formKey}/getList";
		 	var ztreeCreator = new ZtreeCreator('boTree',url)
		 			.setDataKey(treeConf)
		 			.setCallback({onClick:zTreeOnLeftClick,onRightClick:zTreeOnRightClick})
		 			.initZtree({},function(treeObj){boDataTree=treeObj}); 
		}
		
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			curSelectNode = treeNode;
			var url = __ctx+"/form/formBus/${formKey}/get?id=" + curSelectNode.id_;
			if(curSelectNode.id_=="0"){
				$.topCall.warn('该节点为根节点');
				return false;
			}
			$("#listFrame").attr("src", url);
		};
		/**
		 * 树右击事件
		 */
		function zTreeOnRightClick(e, treeId, treeNode){
				curSelectNode=treeNode;

				var h = $(window).height();
				var w = $(window).width();
				var menuWidth = 120;
				var menuHeight = 75;
				var menu = null;
				if (treeNode) {
					menu = $('#boMenu');
				}
				if(!treeNode){
					menu = $('#rootMenu');
				}
				var x = e.pageX, y = e.pageY;
				if (e.pageY + menuHeight > h) {
					y = e.pageY - menuHeight;
				}
				if (e.pageX + menuWidth > w) {
					x = e.pageX - menuWidth;
				}
				menu.menu('show', { left : x, top : y });
				return false;
		};
		//菜单对应项
		function menuHandler(item) {
			if ('add' == item.name) {
				editNode(0);
			} else if (item.name.indexOf('remove')!= -1) {
				delNode( item.name);
			} else if ('edit' == item.name) {
				editNode(1);
			} else if('new' == item.name){
				$("#listFrame").attr("src","${ctx}/form/formBus/${formKey}/edit");
			}
		};
		function refreshZtrrListNode() {
			loadTree();
		};
		//添加节点
		function editNode(type) {
			if (!curSelectNode) return;
			var id=curSelectNode.id_;
			var url = "${ctx}/form/formBus/${formKey}/edit";
			if(type==1){
				url+="?id=" + id 
			}else{
				url+= "?parentId_="+treeConf.pIdKey+ "$"+ curSelectNode[treeConf.idKey]; //id=1 /parentId_=fieldName$fieldVal
			}
			$("#listFrame").attr("src", url);
		
		};
		
		
		//删除
		function delNode(type) {
			var nodeId = curSelectNode.id_;
			var deleteCascade = type=='removeAll';
			var tips = deleteCascade?"确定删除当前对象，并级联删除所属子节点？":"确定删除当前对象？";
			if(deleteCascade){
				var nodes  = boDataTree.getNodesByFilter(function(node){return true},false,curSelectNode);
				for(var i=0,node;node=nodes[i++];){
					nodeId=nodeId+","+node.id_
				}
			}
		 
			var callback = function(rtn) {
				if (!rtn) return;
				var url = "${ctx}/form/formBus/${formKey}/remove";
				$.post(url, {id:nodeId,deleteCascade:deleteCascade}, function(responseText){
					loadTree();
					var obj = new com.hotent.form.ResultMessage(responseText);
					if (obj.isSuccess()) {//成功
						$.topCall.success("删除成功！");
					}else{
						$.topCall.error(obj.getMessage());
					}
					refreshZtrrListNode();
				});
			};
			$.topCall.confirm('温馨提示',tips,callback);
		};
		
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'${formName}管理',split:true" style="width:200px;" id="treeLayout">
	        <div id="boTree" class="ztree" style="min-height: 400px"></div>
		</div>
		<div data-options="region:'center',border:false" style="text-align: center;">
			 	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
		</div>
		<div class="hidden">
			<div id="boMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'add'">添加子节点</div>
				 <div data-options="name:'edit'" >编辑</div> 
				 <div data-options="name:'remove'" >删除</div> 
				 <div data-options="name:'removeAll'" >删除(含子项)</div> 
			</div>
			<div id="rootMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width: 120px;">
				<div data-options="name:'new'">添加</div>
			</div>
		</div>
	</div>
</body>
</html>
