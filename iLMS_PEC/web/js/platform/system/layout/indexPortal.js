var app = angular.module('app',['base']);
app.controller("indexCtrl",['$scope','baseService',function(scope,baseService){
	scope.getUserRes = function(){
		baseService.post(__ctx+"/base/base/sysResource/getResTree",{}).then(
			function(data){
				scope.userRes = data;
				
				var menuId = $.getCookie("default_menu");
				var currentMenu=null ;
				//获取cookie中的当前菜单
				if(menuId&&data){
					for(var i=0,m;m=data[i++];){
						if(m.id==menuId){
							currentMenu = m;
							break;
						}
					}
				}
				
				if(!currentMenu&&data){
					currentMenu =data[0];
				}
				//scope.topClick(currentMenu);
				
			},function(){}
		)
	}
	
	scope.getUserRes();
	
	scope.topClick = function(topMenu){
		if(!topMenu)return;
		if(topMenu.children && topMenu.children.length>0){
			//记录打开的主菜单
			$.setCookie("default_menu",topMenu.id);
			scope.memus = topMenu.children;
			$('.layout-panel-west').find('.panel-title').html(topMenu.name);
		}
		for(var i=0;i<scope.userRes.length;i++){
			var obj=scope.userRes[i];
			obj.active=(topMenu==obj)?"menuActive":"";
			//初始化时关闭所有菜单
			closeAllMenu(obj);
		}
	}
	scope.menuClick = function(menu){
		
		if(!menu.defaultUrl){
		    if(!menu.opened){
		      //closeOtherMenu(scope.memus,menu);
		      menu.opened = true;
		    }else{
		        menu.opened = false;
		    }
		}
		if (menu.newWindow==0) {
			addTab(menu);
		}else{
			$.openFullWindow(__ctx+menu.defaultUrl);
		}
	}
	
	
}]);

//点击导航项展开时，关闭其他打开的导航项
/*function closeOtherMenu(memus,menu){
    for(var i=0;i<memus.length;i++){
        //只关闭同级打开menu
        var childrenLen = memus[i].children.length;
        if(menu.parentId == memus[i].parentId && memus[i].opened && childrenLen>0){
            memus[i].opened = false;
        }else{
            if(childrenLen>0){
            	closeOtherMenu(memus[i].children,menu);
            }
        }
    }
}*/

/*//关闭所有菜单
function closeAllMenu(obj){
	if(obj.children){
		var childrens = obj.children;
		for(var i=0;i<childrens.length;i++){
			childrens[i].opened = 0;
			closeAllMenu(childrens[i]);
		}
	}
}*/
var height;
$(function(){
	if(!noRightsMsg){
		height = $('body').innerHeight()-$('.htmleaf-content').outerHeight()-$('.topbar2').outerHeight()-$('.page-topbar').outerHeight()-80;
		//添加欢迎页面
		welcome();
	}
})

var onlyOpenTitle = "首页";
//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
if(top!=this){
	  top.location= __ctx+'/main/home';
}
/**
 * 
 * @param {url,icon,name,closable}
 * @closable是否可以关闭
 * @url 不以http开头的默认为系统内部菜单。追加__ctx
 * @name tab的名字
 */
function addTab(menu){
	var url = menu.defaultUrl;
	if(!url) return;
	
	if(menu.closable!==false)menu.closable=true; 
	if(url.indexOf("http")==-1)url = __ctx+url;
	
	/*var tabs = $('#tabs');
	if(!tabs.tabs('exists', menu.name)) {
		tabs.tabs('add', {
			title : menu.name,
			closable : menu.closable,
			iconCls : "fa "+(menu.icon||'fa-building'),
			iframeUrl : url
		});
	}else{
		tabs.tabs('select', menu.name);
	}
	loadTabIframe({name:menu.name,url:url});
	initTabContextMenu();*/
	var tabId = menu.name + escapeJquery(url);
	 chromeTabs.addNewTab($chromeTabsExampleShell, {
		 iconClass: "fa "+(menu.icon||'fa-building'),
         title: menu.name,
         data: {
        	 tabId : tabId
         }
     });
	 if($("#"+tabId).attr("src")== undefined){
		 loadTabIframe({name:menu.name,url:url,id:tabId});
	 }else{
		 document.getElementById(tabId).contentWindow.location.reload(true);
	 }
}

function loadTabIframe(menu){
	/*var $tab = $('#tabs').tabs('getTab', menu.name);
   	if ($tab == null) return;
   	
   	var $tabBody = $tab.panel('body');
   	

   	
   
   	// 销毁已有的iframe
   	var $frame = $('iframe', $tabBody);
   	if ($frame.length > 0) {
   		$frame.attr("src",menu.url.getNewUrl());
   	}
   	else{
   		var iframe = document.createElement("iframe");
   	   	iframe.src = menu.url;
   	   	iframe.frameBorder = 0;
//   	   	iframe.style="width:100%;height:100%";
   	   	iframe.height = "100%";
	   	iframe.width = "100%";
   	   	iframe.scrolling = "auto";
   	   	$tabBody.append(iframe);
   	   	
   	}*/
	var $iframeWrap = $('.iframeWrap');
	var $show = $(".index_page_show");
	$show.removeClass("index_page_show");
	$show.addClass("index_page_hide");
	var iframe = document.createElement("iframe");
   	iframe.src = menu.url;
   	iframe.setAttribute("class","index_page_show");
   	iframe.id = menu.id;
   	iframe.frameBorder = 0;
   	iframe.height = height;
   	iframe.width = "100%";
   	$iframeWrap.append(iframe);
   	if(navigator.userAgent.indexOf("Firefox")>0){  
        return "Firefox";  
   	}  
   	var ifame=document.getElementsByClassName("index_page_show")[0];
   	var iframeWin = ifame.contentWindow || ifame.contentDocument.parentWindow;
   	ifame.height = iframeWin.document.documentElement.scrollHeight+100 || ifame.document.body.scrollHeight+100;
}

/*function initTabContextMenu(){
	var menu = $('#mm');
	var tabs = $('#tabs');
	
	// 双击关闭TAB选项卡
	$(".tabs-inner").dblclick(function() {
		title = $(this).children(".tabs-closable").text();
		if(!title) return;
		tabs.tabs('close', title);
	})
	// 为选项卡绑定右键
	.bind('contextmenu', function(e) {
		title = $(this).text();
		if(!title) return false;
		tabs.tabs('select', title);
		menu.menu('show', {left : e.pageX, top : e.pageY})
			.data("currtab", title);


		return false;
	});
	// 绑定右键菜单事件
	menu.menu({
		onClick : function(item) {
			tabContextMenu(item.id);
		}
	});
	
}*/

/**
* 绑定右键菜单事件
*//*
function tabContextMenu(action) {
var tabs = $('#tabs'),
	alltabs = tabs.tabs('tabs'),
	currentTab = tabs.tabs('getSelected'),
	allTabtitle = [],
	options= currentTab.panel('options'),
	currTabtitle =options.title;

$.each(alltabs, function(i, n) {
	allTabtitle.push($(n).panel('options').title);
});

switch (action) {
	case "tab-refresh":// 刷新
		this.loadTabIframe({
			name : currTabtitle,
			url : currentTab.children("iframe")[0].src
		});
		break;
	case "tab-close":// 关闭
		if (currTabtitle == onlyOpenTitle){
			this.msgShow('提示消息', '当前标签不能关闭');
			return;
		}
		tabs.tabs('close', currTabtitle);
		break;
	case "tab-close-all":
		$.each(allTabtitle, function(i, n) {
			if (n != onlyOpenTitle) {
				tabs.tabs('close', n);
			}
		});
		break;
	case "tab-close-other":
		$.each(allTabtitle, function(i, n) {
			if (n != currTabtitle && n != onlyOpenTitle) {
				tabs.tabs('close', n);
			}
		});
		break;
	case "tab-close-right":
		var tabIndex = tabs.tabs('getTabIndex', currentTab);
		if (tabIndex == alltabs.length - 1) {
			this.msgShow('提示消息', '亲，后边没有啦 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i > tabIndex) {
				if (n != onlyOpenTitle) {
					tabs.tabs('close', n);
				}
			}
		});

		break;
	case "tab-close-left":
		var tabIndex = tabs.tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			this.msgShow('提示消息', '亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i < tabIndex) {
				if (n != onlyOpenTitle) {
					tabs.tabs('close', n);
				}
			}
		});

		break;
	case "tab-exit":
		$('#closeMenu').menu('hide');
		break;
}
}*/


function welcome(){
	addTab({
		name:"个人工作台",
		defaultUrl: '/main/myHome',
		icon: 'fa-home',
		id:'home',
		closable:false
	});
}

function userInfo(){
	this.addTab({
		name:"个人信息",
		defaultUrl: '/org/user/userGet?id='+currentUserId+'&isheader=true',
		id:"userInfo",
		icon: 'fa-user',
		closable:true
	});
}
function editPassworld(){
	this.addTab({
		name:"修改个人密码",
		defaultUrl: '/org/user/userPswEdit',
		id:"userPswEdit",
		icon: 'fa-key',
		closable:true
	});
}
function editUserInfo(){
	this.addTab({
		name:"个人信息编辑",
		defaultUrl: '/org/user/userInfoEdit',
		id:"userInfoEdit",
		icon: 'fa-info',
		closable:true
	});
}
function editApprovalItemList(){
	this.addTab({
		name:"常用语设置",
		defaultUrl: '/flow/approvalItem/approvalItemList',
		id:"approvalItemList",
		icon: 'fa-building',
		closable:true
	});
}
function editAgentList(){
	this.addTab({
		name:"代理设定",
		defaultUrl: '/flow/agent/agentListMgr',
		id:"agentList",
		icon: 'fa-building',
		closable:true
	});
}
function loginOut(){ 
    $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
        if (r) {
        	window.location.href = __ctx+'/logout';
        }
    });
}

function changeSystem(id){
	window.location.href = __ctx+'/main/changeSystem?id='+id;
}
function changeOrg(id){
	window.location.href = __ctx+'/main/changeOrg?id='+id;
}
function changeSkin(skinName){
	window.location.href = __ctx+'/main/changeSkin?skinName='+skinName;
}
function changeLayout(id){
	window.location.href = __ctx+'/main/changeLayout?id='+id;
}
function changeStyle(styleName){
	var color = 'default';
	if(styleName == 'default'){
		color = 'blue';
	}else if(styleName == 'other'){
		color = 'green';
	}
	window.location.href = __ctx+'/main/changeStyle?styleName='+styleName+'&color='+color;
}

function msgShow(title,msg){
	$.messager.show({
        title:title,
        msg:msg,
        timeout:3000,
        showType:'slide',
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom:''
        }
    });
}
function escapeJquery(srcString)
{
    // 转义之后的结果
    var escapseResult = srcString;
 
    // javascript正则表达式中的特殊字符
    var jsSpecialChars = ["\\", "^", "$", "*", "?", ".", "+", "(", ")", "[",
            "]", "|", "{", "}"];
 
    // jquery中的特殊字符,不是正则表达式中的特殊字符
    var jquerySpecialChars = ["~", "`", "@", "#", "%", "&", "=", "'", "\"",
            ":", ";", "<", ">", ",", "/"];
 
    for (var i = 0; i < jsSpecialChars.length; i++) {
        escapseResult = escapseResult.replace(new RegExp("\\"
                                + jsSpecialChars[i], "g"), "");
    }
 
    for (var i = 0; i < jquerySpecialChars.length; i++) {
        escapseResult = escapseResult.replace(new RegExp(jquerySpecialChars[i],
                        "g"), "");
    }
 
    return escapseResult;
}



