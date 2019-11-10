<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="com.hanthink.gps.util.AppContextUtil" %>
<%@ include file="/comm/jsp/common.jsp"%>
<%
// 取得页面权限
String pagesAuth = session.getAttribute(Constants.USER_PAGES_AUTH_KEY).toString();
//取得用户名
String userName=user.getName();
String userId = user.getUserId();
String userType = user.getUserType();
String loginFactoryCode = null == user.getCurLoginFactory() ? "1" : user.getCurLoginFactory();
%>
<script type="text/javascript">
var userIdInformation = '<%=userId%>';
var userTypeInformation = '<%=userType%>';
var pagesAuth = <%=pagesAuth%>;
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script type="text/javascript" src="comm/js/nav.js"></script>
<script type="text/javascript" src="comm/js/index.js"></script>
<script type="text/javascript">
 var loginTime = '<s:property value="loginTime" />';
 var loginIp = '<s:property value="loginIp" />';
 var loginNum = '<s:property value="loginNum" />';
 var loginInfo ='';
 if (loginNum != null && loginNum != ''){
	 if (loginNum =='1') {
		 loginInfo =  "<p >这是您第 " + loginNum +" 次登录本系统</span></p><br/>";
	 } else {
	 loginInfo =  "<p >这是您第 " + loginNum +" 次登录本系统</span></p><br/><p >您上次登录时间:</p><p>"+loginTime+"</p><br/><p >您上次登录地址:</p><p>"+loginIp+"</p><br/>";
	 }
 }
 
 var oldZeroMenuObj = null;
 var chgFirstMenu = function(menu, obj, idx){
	 if(oldZeroMenuObj){
		oldZeroMenuObj.className = "new2"; 
		//oldZeroMenuObj.style.display = "none";
	 }else{
		document.getElementById('zeroli_ul_firstlia').className = "new2";
	 }
	 
	 obj.className = "class1";
	 //obj.style.display = "block";
	 oldZeroMenuObj = obj;
	 
	 document.getElementById('firstmdiv').innerHTML = document
			.getElementById(menu).innerHTML;
	 
	 chgMenu('homepage_menu',document.getElementById('homepagenav'),1);
 } 
 
 var initZeroFirstMFn = function(){
	  var zeroLiFirstObj = document.getElementById('zeroli_ul').childNodes[1];
      if(zeroLiFirstObj){
    	  var obj = zeroLiFirstObj.firstChild;
    	  chgFirstMenu(obj.modelid,obj,1);
      }
 }
 
</script>
<style type="text/css">
#class .loading-indicator {
    font-size: 12px;
    height: 18px;
}

.loading-indicator {
    font-size: 11px;
    background-image: url('./images/loadmask32.gif');
    background-repeat: no-repeat;
    background-position: top left;
    padding-left: 20px;
    height: 18px;
    text-align: left;
}

#gsl-body .loading-indicator {
    font: bold 13px Tahoma, Verdana, Arial, Helvetica, sans-serif;
    position: absolute;
    top: 35%;
    left: 43%;
    color: #444;
    background-image:
        url(./images/loadmask32.gif);
    background-repeat: no-repeat;
    background-position: left 5px;
    padding: 10px 10px 10px 38px;
    text-align: left;
}

#loading-mask {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 20000;
    background-color: white;
}

#loading {
    position: absolute;
    left: 45%;
    top: 40%;
    padding: 2px;
    z-index: 20001;
    height: auto;
}

#loading img {
    margin-bottom: 5px;
}
#loading .loading-indicator {
    background: white;
    color: #555;
    font: bold 13px tahoma, arial, helvetica;
    padding: 10px;
    margin: 0;
    text-align: center;
    height: auto;
}
</style>
<title>广汽新能源i-LMS系统</title>
</head>
<body id="gsl-index" ><!-- onload="initZeroFirstMFn()" -->
<div id="loading-mask" style=""></div>
<div id="loading">
<div class="loading-indicator"><img
    src="./images/loadmask32.gif"
    width="32" height="32" style="margin-right: 8px;" align="absmiddle" /><script>document.write(l_loading)</script></div>
</div>
<!-------------菜单栏----------------------->
  <div id="west"></div> 
  
  <!-- ---------------以下为数据信息--------------------- -->
<!-------------头部 导航栏等信息------------>
  <div id="north" class="header">
    <!-- 
      <p style="position:absolute; z-index:1; top:5; left:330pt;color:red;font-size:20pt;font-family:宋体;" class="pColOne">
      [<s:property value="curLoginFactory" />]</p>
     -->
     <s:if test='curLoginFactory != ""'> 
	     <div style="position:absolute; z-index:1; top:0px; left:330pt;" class="pColOne">
	       <img src="images/Home_<%=loginFactoryCode %>.png"/  >
	     </div>
	 </s:if>
	 <s:else>
	     <div style="position:absolute; z-index:1; top:0px; left:330pt;" class="pColOne">
	       <img src="images/Home_Default.png"/  >
	     </div>
	 </s:else>
    
    <div class="divName">
    	<img src="images/car.gif"/  >
        <p  style="position:absolute; z-index:1; top:9; right:20;"class="pColOne">
        <a href='javascript:void(0)' target='_blank'><script>document.write(l_help)</script></a> | <b> <a href="javascript:void(0)" onclick="logout()"><script>document.write(l_exit)</script></a></b><br><script>document.write(l_welcomeLogin)</script><br><b><font color="#002393" ><%=userName%></font></b>&nbsp;&nbsp;<script>document.write(l_welcome)</script><br>
		</p>
    </div>
    <div class="hTop">
        <!-- <div class="logo"><img src="images/logo_new.jpg"/ ></div> <img src="images/logoPrint.jpg"/ > -->
        <div class="logo">
        	<div id="zeroli_list" class="nav2">
	        	<ul id="zeroli_ul">
	        	<li style="background-color:white;margin-top:-5px;background-image:none;"><img src="images/logoPrint.jpg"/></li>
	        	 
	        	<s:iterator id="zero_menu" value="#session.USER_ROLE_ZERO_MENU_KEY" status="index">
	        		<s:if test="#index.First"> 
						 <li><a href="javascript:void(0)" id="zeroli_ul_firstlia" class="class1" modelid="<s:property value="#zero_menu.menuId"/>" onclick="chgFirstMenu('<s:property value="#zero_menu.menuId"/>',this,2)"><s:property value="#zero_menu.menuName"/></a></li>
					</s:if>
					<s:else>
			  			<li><a href="javascript:void(0)" class="new2" modelid="<s:property value="#zero_menu.menuId"/>" onclick="chgFirstMenu('<s:property value="#zero_menu.menuId"/>',this,2)"><s:property value="#zero_menu.menuName"/></a></li>
					</s:else>
			    </s:iterator>
	        	</ul>
	        </div>
        </div>
        
    </div>
    <div class="nav" id="firstmdiv">
        <ul>
            <li><a href="javascript:void(0)" class="class1" id="homepagenav" onclick="chgMenu('homepage_menu',this,1)">首页</a></li>
		    <s:iterator id="zero_menu" value="#session.USER_ROLE_ZERO_MENU_KEY" status="index">
		    	<s:if test="#index.First"> 
					 <s:iterator id="menu" value="#session.USER_ROLE_MENU_KEY" status="index">
			        	<s:if test='#menu.parentId==#zero_menu.menuId'> 
							<li><a href="javascript:void(0)"  class="new2" onclick="chgMenu('<s:property value="#menu.url" />',this,2)"><s:property value="#menu.menuName" /></a></li>
					    </s:if> 
					</s:iterator>
				</s:if>
	    	</s:iterator>
        </ul>       
        
    </div>
  </div>
  
<!-----------------底部 权限信息------------>
  <div id="south" class="bottom">
    <p class="pLeft">版权所有：广汽乘用车</p>
    <p class="pRight">&nbsp;</p>
  </div>
  
  <!--------------菜单数据--------------->
  <!--主页菜单-->
  <div id="homepage_menu" style="display:none;">
  <div class="navHomeExtLeft">
  <table height="240px" width="100%"  cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
            <div id="time" class="timeStyle">
            </div>    
            </td>
        </tr>
               	<tr>
		<td align="center" valign="top">
		<div id="loginInfo" style="text-align: left"></div>
		</td>
	</tr>
		<tr>
		<td align="center" valign="top">
		<div id="changePwd" style="text-align: left"></div>
		</td>
	</tr>
    </table>
    </div>
    </div>
    <!--系统管理--><!---->
   <s:iterator id="menu" value="#session.USER_ROLE_MENU_KEY" status="index">
  		<div id='<s:property value="#menu.url" />' style="display:none;">
		<div class="navLeft">
	   <s:iterator id="subMenu" value="#menu.menuList" status="subIndex">
		    <ul>
		        <li class="navLeft_li"><a href="javascript:void(0)" onclick="DoMenu('<s:property value="#index.index"/>ChildMenu<s:property value="#subIndex.index"/>')"><s:property value="#subMenu.menuName" /></a></li>
		        <ul id='<s:property value="#index.index"/>ChildMenu<s:property value="#subIndex.index"/>' class="collapsed">
		        	<s:iterator id="endMenu" value="#subMenu.menuList" status="endIndex">
		            	<li class="navLeft_ul_li"><a href="javascript:void(0)" onclick="addNewTab(this,'<s:property value="#endMenu.url" />');"><s:property value="#endMenu.menuName" /></a></li>
		            </s:iterator>
		    	</ul>
	    	</ul>
	    </s:iterator>
	    </div>
	    </div>
    </s:iterator>
    
    <!-- 一级模块 -->
    <s:iterator id="zero_menu" value="#session.USER_ROLE_ZERO_MENU_KEY" status="index">
  		<div id='<s:property value="#zero_menu.menuId" />' style="display:none;">
		    <ul>
		        <li><a href="javascript:void(0)" class="class1" id="homepagenav" onclick="chgMenu('homepage_menu',this,1)">首页</a></li>
		        
		        <s:iterator id="menu" value="#session.USER_ROLE_MENU_KEY" status="index">
		        	<s:if test='#menu.parentId==#zero_menu.menuId'> 
						<li><a href="javascript:void(0)"  class="new2" onclick="chgMenu('<s:property value="#menu.url" />',this,2)"><s:property value="#menu.menuName" /></a></li>
				    </s:if> 
				</s:iterator>
	    	</ul>
	    </div>
    </s:iterator>
    
   <script type="text/javascript">
      
   </script>
   </body>
</html>