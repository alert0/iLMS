<%@page import="com.hotent.sys.util.ContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<!DOCTYPE html>
<html ng-app="app">
<head>
	<title>首页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=EDGE;">
	<style type="text/css">
		footer {
				text-align: center;
				margin: 20px auto;
				font-size: 12px;
			}
	</style>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<f:link href="bootstrap.min.css"></f:link>
	<link rel="stylesheet" href="${ctx}/js/lib/easyui/themes/bootstrap/easyui.css">
	<f:link href="font-awesome.min.css"></f:link>
	<f:link href="base.css"></f:link>
	<%-- <f:link href="index.css"></f:link>
	<f:link href="icons.css"></f:link> --%>
	<f:link href="portal/style.css"></f:link>
	<f:link href="portal/chrome-tabs.css"></f:link>
	<script type="text/javascript">
		var __ctx='${ctx}';
		var currentUserId = '<%=ContextUtil.getCurrentUserId()%>';
		var noRightsMsg = "${noRightsMsg}";
	</script>
	<script type="text/javascript" src="${ctx}/js/lib/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/index/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/easyui/jquery.easyui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/easyui/lang/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/service/BaseService.js"></script>
	<script type="text/javascript" src='${ctx}/js/lib/jquery/bootstrap.min.js'></script>
	<script type="text/javascript" src='${ctx}/js/portal/perfect-scrollbar/perfect-scrollbar.min.js'></script>
	<script type="text/javascript" src='${ctx}/js/portal/index/scripts.js'></script>
	<script type="text/javascript" src="${ctx}/js/lib/layer/layer.js"></script>

	<script id="menuTree" type="text/ng-template">
    	<li class="{{item.parentId==0?'dropdown':item.children.length>0?'dropdown-submenu':''}}" ng-repeat="item in userRes"> 
			<a href="#" class="{{item.children.length>0?'dropdown-toggle':''}}" data-toggle="{{item.parentId==0?'dropdown':''}}" ng-click="menuClick(item)">{{item.name}} <span ng-if="item.children.length && item.parentId==0" class="caret"></span></a>
		    <ul ng-if="item.children.length" class="dropdown-menu" ng-include="'menuTree'"  ng-init="userRes=item.children"></ul>
		</li>
	</script>
	<script type="text/javascript">
		function msgList(){
			this.addTab({
				name:"收到的消息",
				defaultUrl: '/innermsg/messageReceiverList',
				icon: 'fa-building',
				id:"69",
				closable:true
			});
		}
		function toolsClick(tool){
			if (tool.url) {
				if (tool.url.startWith("http")) {
					window.open(tool.url);
				} else {
					this.addTab({
						name: tool.name,
						defaultUrl: tool.url,
						icon: tool.icon,
						id:tool.id,
						closable:true
					});
				}
			}
		}
		$(document).on("mouseover",".navbar-nav li",function(){$(this).children("ul").stop().fadeIn("fast");});
		$(document).on("mouseleave",".navbar-nav li",function(){$(this).children("ul").stop().fadeOut("fast");});
	</script>
</head>
<body ng-controller="indexCtrl">

<div class='page-topbar sidebar_shift '>
  <div class='logo-area'>
    <ul class="info-menu left-links list-inline list-unstyled" style="padding-top: 18px;padding-left: 24px;">
      <li class="sidebar-toggle-wrap"> <a href="javascript:void(0);" data-toggle="sidebar" class="sidebar_toggle"> <i class="fa fa-bars"></i> </a> </li>
    </ul>
  </div>
  <div class='quick-area'>
    <div class='pull-left'>
      <div class="info-menu">
      <c:choose>
      	<c:when test="${empty sysLayoutSetting}">
		      <img src="${ctx}/css/image/logomtk-bf-03.png" alt="" height="30％" style="padding-left: 10px;"></div>
      	</c:when>
      	<c:otherwise>
		      <img src="${ctx}${sysLayoutSetting.logo}" alt="" height="40％" style="padding-left: 10px;"></div>
      	</c:otherwise>
      </c:choose>
    </div>
    <div class='pull-right' style="right: 0; position: absolute; padding-right: 10px;">
      <ul class="info-menu left-links list-inline list-unstyled" style="padding-right: 20px;">
        <li class=""> <a href="javascript:void(0);" onclick="msgList()" class="toggle"> <i class="fa fa-bell"></i> <span class="badge badge-orange">${msgSize}</span></a>
        </li>
      </ul>
      <ul class="info-menu right-links list-inline list-unstyled">
        <li class="profile"> <a href="javascript:void(0);" data-toggle="dropdown" class="toggle"> 
        <c:choose>
	        <c:when test="${empty currentUser.photo}">
		        <img src="${ctx}/css/image/user.png" alt="user-image" class="img-circle img-inline">
	        </c:when>
	        <c:otherwise>
		        <img src="${ctx}${currentUser.photo}" alt="user-image" class="img-circle img-inline">
	        </c:otherwise>
        </c:choose>${currentUser.fullname} <c:if test="${empty currentOrg.name}">尚未加入部门</c:if> ${currentOrg.name}<span><i class="fa fa-angle-down"></i></span> </a>
        
          <ul class="dropdown-menu profile animated fadeIn">
            <li> <a href="javascript:void(0);" onclick="userInfo()"> <i class="fa fa-user"></i>查看个人信息</a> </li>
            <li> <a href="javascript:void(0);" onclick="editUserInfo()"> <i class="fa fa-edit"></i>修改个人信息</a> </li>
            <li> <a href="javascript:void(0);" onclick="editPassworld()"> <i class="fa fa-key"></i>修改密码</a> </li>
            <li class="dropdown-submenu"> <a href="#"> <i class="fa fa-external-link-square"></i> 系统切换</a>
			  <ul class="dropdown-menu dropdown-left">
                  <li><a href="javascript:void(0);" style="cursor:not-allowed;">${currentSystem.name}</a></li>
                  <c:forEach items="${subsystemList}" var="system">
						<c:if test="${!(system.id eq currentSystem.id)}">
			                <li onclick="changeSystem('${system.id}')">
			                	<a href="javascript:void(0);"  title="切换系统">${system.name}</a>
			                </li>
			         	</c:if>
				  </c:forEach>
              </ul>
            </li>
            <c:if test="${not empty orgList && orgList.size() >1}">
            <li class="dropdown-submenu"><a href="#"> <i class="fa fa-server"></i>组织切换</a>
				<ul class="dropdown-menu dropdown-left">
                  <li><a href="javascript:void(0);" style="cursor:not-allowed;">${currentOrg.name}</a></li>
                  <c:forEach items="${orgList}" var="org">
						<c:if test="${!(org.id eq currentOrg.id)}">
			                <li onclick="changeOrg('${org.id}')" >
			                	<a href="javascript:void(0);" title="切换组织">${org.name}</a>
			                </li>
			         	</c:if>
				  </c:forEach>
              </ul>
            </li>
            </c:if>
            <li class="dropdown-submenu"><a href="#"> <i class="fa fa-server"></i>切换皮肤</a>
				<ul class="dropdown-menu dropdown-left">
                  <li onclick="changeSkin('blue')">
                	 <a href="javascript:void(0);"  title="切换皮肤">蓝色</a>
                  </li>
                  <li onclick="changeSkin('default')">
                	 <a href="javascript:void(0);"  title="切换皮肤">红色</a>
                  </li>
                  <li onclick="changeSkin('green')">
                	 <a href="javascript:void(0);"  title="切换皮肤">绿色</a>
                  </li>
                </ul>
            </li>
             <li class="dropdown-submenu"><a href="#"> <i class="fa fa-server"></i>切换风格</a>
				<ul class="dropdown-menu dropdown-left">
                  <li onclick="changeStyle('default')">
                	 <a href="javascript:void(0);"  title="切换风格">默认风格</a>
                  </li>
                  <li onclick="changeStyle('portal')">
                	 <a href="javascript:void(0);"  title="切换风格">门户风格</a>
                  </li>
                  <li onclick="changeStyle('other')">
                	 <a href="javascript:void(0);"  title="切换风格">其他风格</a>
                  </li>
                </ul>
            </li>
            <c:if test="${not empty layoutManageList}">
            <li class="dropdown-submenu"><a href="#"> <i class="fa fa-server"></i> 切换布局</a>
					<ul class="dropdown-menu dropdown-left">
					  <c:if test="${flagUserSetting}">
					  	  <li>
			                <a href="javascript:void(0);" style="cursor:not-allowed;">我的布局</a>
			              </li>
					  </c:if>
	                  <c:forEach items="${layoutManageList}" var="layout">
							<c:if test="${(layout.id eq userSetting.layoutId)}">
				                <li>
					                <a href="javascript:void(0);" style="cursor:not-allowed;">${layout.name}</a>
				                </li>
				         	</c:if>
							<c:if test="${!(layout.id eq userSetting.layoutId)}">
				                <li onclick="changeLayout('${layout.id}','${layout.memo}')">
				                	<a href="javascript:void(0);"  title="切换布局">${layout.name}</a>
				                </li>
				         	</c:if>
					  </c:forEach>
	              </ul>
            </li>
            </c:if>
            <li class="last"> <a onclick="loginOut()"><i class="fa fa-sign-out"></i>退出系统</a> </li>
            <c:if test="${not empty cookie.origSwitch}">
            	<li class="last"> <a href="${ctx}/j_spring_security_exit_user?j_username=${cookie.origSwitch.value}"><i class="fa fa-sign-out"></i> 退出切换用户</a> </li>
            </c:if>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>
<!-- END TOPBAR --> 
<!-- START CONTAINER -->
<div class="page-container row-fluid"> 
  <div class="page-sidebar collapseit "> 
    <div class="page-sidebar-wrapper" id="main-menu-wrapper">
      <ul class='wraplist'>
      <c:choose>
      		<c:when test="${empty sysIndexLayoutToolss}">
		        <li class=""> <a href="javascript:toolsClick({id:'bOEnt',name:'实体管理',icon:'',url:'/bo/def/bOEntList'})" > <i class="fa fa-suitcase"></i> <span class="title">实体管理</span> </a> </li>
		        <li class=""> <a href="javascript:toolsClick({id:'bODef',name:'业务对象定义',icon:'',url:'/bo/def/bODefList'})"> <i class="fa fa-th"></i> <span class="title">业务对象定义</span></a> </li>
		        <li class=""> <a href="javascript:toolsClick({id:'addressBook',name:'通讯录',icon:'',url:'/business/addressBook/addressBook/addressBookList'})"> <i class="fa fa-th"></i> <span class="title">通讯录</span></a> </li>
		        <li class=""> <a href="javascript:toolsClick({id:'needAttendMeeting',name:'待参加会议',icon:'',url:'/business/meeting/meeting/needAttendMeeting'})"> <i class="fa fa-th"></i> <span class="title">待参加会议</span></a> </li>
		        <li class=""> <a href="javascript:toolsClick({id:'task',name:'任务管理',icon:'',url:'/flow/task/taskList'})"> <i class="fa fa-th"></i> <span class="title">任务管理</span></a> </li>
      		</c:when>
      		<c:otherwise>
      		<c:forEach items="${sysIndexLayoutToolss}" var="tool">
      			<li> <a href="javascript:toolsClick({id:'${tool.id }',name:'${tool.name }',icon:'${tool.icon }',url:'${tool.url }'})"> <i class="fa ${tool.icon }"></i> <span class="title">${tool.name}</span> </a> </li>
      		</c:forEach>
      		</c:otherwise>
      </c:choose>
      </ul>
    </div>
  </div>
  <!--  SIDEBAR - END --> 
  <!-- START CONTENT -->
  <section id="main-content" class="sidebar_shift " >
    <section  class="wrapper" style='margin-top:36px;display:inline-block;width:100%;padding:15px 0 0 15px;'>
      <div class="topbar2">
        <nav class="navbar navbar-default">
          <div id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" ng-include="'menuTree'">
		    </ul>
          </div>
        </nav>
      </div>
	  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	  <c:choose>
	  	<c:when test="${not empty noRightsMsg}">
	  		<div title="未授权任何系统">
            	<div class="alert alert-danger" role="alert">${noRightsMsg }</div>
            </div>
	  	</c:when>
	  	<c:otherwise>
	  		<div class="htmleaf-content">
	          <div class="chrome-tabs-shell">
	            <div class="chrome-tabs">
	              
	            </div>
	            <div class="chrome-shell-bottom-bar"></div>
	          </div>
	        </div>
	        <br/>
	        <div class="iframeWrap" id="iframeWrap">
		        
		    </div>
	  	</c:otherwise>
	  </c:choose>
    </section>
  </section>
</div>
	<!-- <div class="hidden">
		窗口
		<div id="win" title="win" style="width: 600px; height: 400px" data-options="modal:true"></div>
		tabs右键菜单
		<div id="mm" class="easyui-menu" style="width: 150px; display: none;">
			<div id="tab-refresh" iconCls="fa fa-refresh">刷新</div>
			<div class="menu-sep"></div>
			<div id="tab-close-right" iconCls="fa fa-angle-double-right">关闭右侧标签页</div>
			<div id="tab-close-left" iconCls="fa fa-angle-double-left">关闭左侧标签页</div>
			<div class="menu-sep"></div>
			<div id="tab-close-other" iconCls="fa fa-times-circle-o">关闭其它标签页</div>
			<div id="tab-close-all" iconCls="fa fa-times-circle">关闭全部标签页</div>
		</div>
		tabs右键菜单end
	</div> -->
</body>
	<script type="text/javascript" src='${ctx}/js/portal/index/chrome-tabs.js'></script>
	<script type="text/javascript" src='${ctx}/js/portal/index/portal-index.js'></script>
	<script type="text/javascript" src='${ctx}/js/platform/system/layout/indexPortal.js'></script>
</html>
