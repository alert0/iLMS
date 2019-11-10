<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
</head>
<body>
	<div id="resourcesTree" class="ztree"></div>
</body>
</html>

<script>
	var resourcesTree;
	function callBack(data,dialog) {
		
	}
	
	function getResult() {
		return resourcesTree.getCheckedNodes(true);
	}
	
	var systemId="${param.systemId}";
	var roleId="${param.roleId}";
	var expandDepth = 2; 
    $(function()
    { 
      //加载树
      if(systemId!=null && roleId!=null)
        loadTree();
      //改变子系统
      //$("#subSystem").change(function(){
      //  systemId=$("#subSystem").val();
      //  loadTree();
      //});
      //$("a.save").click(save);
    });
    
    //加载树
    function loadTree(){
    
      var setting = {
        data: {
          key : {
            name: "name",
            title: "name"
          },
          simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
          }
        },
        view: {
          selectedMulti: true
        },
        check: {
          enable: true,
          chkboxType: { "Y": "ps", "N": "s" }
        }
        
      };
      //一次性加载
      var url=__ctx +"/base/base/resRole/getTreeData?roleId="+ roleId +"&systemId=" + systemId;
      
      $.post(url,function(result){
        resourcesTree=$.fn.zTree.init($("#resourcesTree"), setting,result);
        if(expandDepth!=0)
        {
          resourcesTree.expandAll(false);
          var nodes = resourcesTree.getNodesByFilter(function(node){
            return (node.level < expandDepth);
          });
          if(nodes.length>0){
            for(var i=0;i<nodes.length;i++){
              resourcesTree.expandNode(nodes[i],true,false);
            }
          }
        }
        else 
          resourcesTree.expandAll(true);
      });
  
   /*   //保存
    function save(){
      var resourcesTree = $.fn.zTree.getZTreeObj("resourcesTree");
      var nodes = resourcesTree.getCheckedNodes(true);
      var resIds="";
      $.each(nodes,function(i,n){
        if(n.resId!=0)resIds+=n.resId+",";
      });
      
      resIds=resIds.substring(0,resIds.length-1);
    
      var url="";
      var data= "roleId=10000000040150&systemId="+systemId+"&resIds="+resIds;
      $.post(url,data,function(result){
        var obj=new com.hotent.form.ResultMessage(result);
        if(obj.isSuccess()){
          $.ligerDialog.confirm('角色资源分配成功,是否继续?','提示信息',function(rtn){
            if(!rtn){
              dialog.close();
            }
          });
        }else{
          $.ligerDialog.warn('角色资源分配出错!');
        }
      }) */
      
    }; 
</script>
