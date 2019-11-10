function initPortals(){
	var pendingPortal = $('<div/>').appendTo('body');
	var messagePortal = $('<div/>').appendTo('body');
	var addressListPortal = $('<div/>').appendTo('body');
	
	var pendingContent = '<table class="table-list" cellspacing="0"><tr>\
		<th>标题</th><th>创建时间</th></tr>\
		<tr ng-repeat="pending in data.pendingList">\
		<td ng-bind="pending.subject" ng-click="toTask(pending.id)" ></td>\
		<td width="150" ng-bind="pending.createTime | date :\'yyyy-MM-dd HH:mm:ss\'"></td></tr></table>'
	var messageContent = '<table class="table-list" cellspacing="0">\
		<tr><th>标题</th><th>消息类型</th><th>发信时间</th><th>收信时间</th></tr>\
		<tr ng-repeat="message in data.messageList">\
		<td ng-bind="message.subject" ng-click="openMsgDetail(message.id,message.isPlublic,message.canReply)"></td>\
		<td width="120" ng-bind="message.messageType"></td>\
		<td width="150" ng-bind="message.createTime | date :\'yyyy-MM-dd HH:mm:ss\'"></td>\
		<td width="150" ng-if="message.receiveTime" ng-bind="message.receiveTime | date :\'yyyy-MM-dd HH:mm:ss\'">\
		</td><td ng-if="!message.receiveTime">未读消息</td></tr></table>'
	var addressListContent = '<table class="table-list" cellspacing="0">\
		<tr><th>编号</th><th>姓名</th><th>账号</th><th>主部门</th><th>手机</th><th>固定电话</th><th>邮箱</th><th>微信</th></tr>\
		<tr ng-repeat="user in data.addressList">\
		<td ng-bind="user.id"></td>\
		<td ng-bind="user.fullname"></td>\
		<td ng-bind="user.account"></td>\
		<td ng-bind="user.orgName"></td>\
		<td ng-bind="user.mobile"></td>\
		<td ng-bind="user.telPhone"></td>\
		<td ng-bind="user.email">\
		</td><td ng-bind="user.weixin"></td></tr></table>'
	
	pendingPortal.panel({
		title:"我的待办",
		iconCls:'icon-pending',
		content:'<div id="pendingDiv" style="padding:5px;">'+pendingContent+'</div>',
		height:280,
		closable:false,
		collapsible:true,
		tools:'#pendingTool',
	});
	
	messagePortal.panel({
		title:"内部消息",
		iconCls:'icon-message',
		content:'<div id="messageDiv" style="padding:5px;">'+messageContent+'</div>',
		height:280,
		closable:false,
		collapsible:true,
		tools:'#messageTool',
	});
	
	addressListPortal.panel({
		title:"通讯录",
		iconCls:'icon-addressList',
		content:'<div id="addressDiv" style="padding:5px;">'+addressListContent+'</div>',
		height:280,
		closable:false,
		collapsible:true,
		tools:'#addressTool',
	});
	
	$('#welcomePortals1').portal('add', {
		panel:pendingPortal,
		columnIndex:0
	});
	
	$('#welcomePortals1').portal('add', {
		panel:messagePortal,
		columnIndex:1
	});
	
	$('#welcomePortals2').portal('add', {
		panel:addressListPortal,
		columnIndex:0
	});
	
	$('#welcomePortals1').portal('resize');
	$('#welcomePortals2').portal('resize');
	
}

function parentAddTab(type){
	var currentmenu = {};
	if(type=='pending'){
		currentmenu.defaultUrl = '/office/receivedProcess/pending';
		currentmenu.icon = '';
		currentmenu.name = '待办事项';
	}else if(type=='message'){
		currentmenu.defaultUrl = '/innermsg/messageReceiverList';
		currentmenu.icon = '';
		currentmenu.name = '收到的消息';
	}else if(type=='address'){
		currentmenu.defaultUrl = '/org/user/userAddressList';
		currentmenu.icon = '';
		currentmenu.name = '通讯录';
	}
	parent.addTab(currentmenu);
}

$(function(){
    $('#welcomePortals1').portal({
        border:false,
        fit:true,
    });
    $('#welcomePortals2').portal({
        border:false,
        fit:true,
    });
    //初始化大框架后，添加portal元素
    initPortals(); 
}); 
