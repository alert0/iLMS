// 变换菜单
var lastObj = null;
var lastIdx = 1;
var tabs;
Date.prototype.format = function(format)
{ 
  var o = { 
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(),    //day 
    "h+" : this.getHours(),   //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter 
    "S" : this.getMilliseconds() //millisecond 
  } 
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
    (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o)if(new RegExp("("+ k +")").test(format)) 
    format = format.replace(RegExp.$1, 
      RegExp.$1.length==1 ? o[k] : 
        ("00"+ o[k]).substr((""+ o[k]).length)); 
  return format; 
} 

var   day=[l_sun,l_mon,l_tue,l_wen,l_thur,l_fri,l_sat];   
var datetimeInfo = function(){document.getElementById('time').innerHTML=new Date().format(l_dateFormat) +'<br/>'+day[new Date().getDay()];};
var chgMenu = function(menu, obj, idx) {
	if (!lastObj) {
		lastObj = document.getElementById("homepagenav");
		lastIdx = 1;
	}
	lastObj.className = "new" + lastIdx;
	var old = document.getElementById("list" + lastIdx);
	if (old)old.style.display = "none";

	obj.className = "class" + idx;
	var n = document.getElementById("list" + idx);
	if (n)
		n.style.display = "block";

	lastObj = obj;
	lastIdx = idx;
	
	

	document.getElementById('west').innerHTML = document
			.getElementById(menu).innerHTML;

	if (menu == 'homepage_menu') {
		var dateInterval = setInterval(datetimeInfo, 1000);	
		var originalPwd = new gsl.TextField( {
			id :'originalPwd',
			allowBlank :false,
			inputType :'password',
			name :'originalPwd',
			anchor :'95%',
			fieldLabel :l_originalPwd
		});
		var newPwd = new gsl.TextField( {
			id :'supplierPwd',
			name :'supplierPwd',
			maxLength :15,
			minLength : 6,
			inputType :'password',
			anchor :'95%',
			allowBlank :false,
			fieldLabel :l_newPwd
		});
		var confirmPwd = new gsl.TextField( {
			id :'confirmSupplierPwd',
			anchor :'95%',
			allowBlank :false,
			minLength : 6,
			inputType :'password',
			maxLength :15,
			name :'confirmSupplierPwd',
			invalidText:l_twoPwdDifferent,
			fieldLabel :l_confirmPwd,
			validator:function(){
			if(newPwd.getValue() ==confirmPwd.getValue()){
				return true;
			}else{
				return false;
			}
		}
		});
		var changePwdForm = new Ext.form.FormPanel( {
			width :180,
			title :l_changePwd,
			renderTo :'changePwd',
			frame :true,
			labelWidth :70,
			items : [ {
				layout :'column',// columnlayout
				items : [ {
					columnWidth :1,// 设置了该列的宽度占总宽度的50%（columnWidth:.5）
					layout :'form',// formlayout用来放置控件
					items : [ {},originalPwd, newPwd, confirmPwd ]
				} ]
			} ],
			buttons : [ {
				text :l_comfirm,
				handler : function() {
					if (changePwdForm.form.isValid()) {
						gsl.request({
					           url: 'login-changePwd.action',
					           params:{
									'originalPwd':originalPwd.getValue(),
									'supplierPwd':newPwd.getValue(),
									'confirmSupplierPwd':confirmPwd.getValue()
					           },
					           success :function(response ){
					        	   var result = Ext.decode(response.responseText);
					        	   if (result.success) {
					        		   gsl.InfoAlert(l_changePwdSuccess);
					        	   } else {
					        		   gsl.ErrorAlert(result.msg);
					        	   }
					           }
					        });
						
						//changePwdForm.collapse(true);
					}
				}
			} ]
		});
		changePwdForm.on('beforecollapse', function() {
			this.form.reset();
			changePwdForm.form.clearInvalid();
		});
		if (loginInfo != '' && loginInfo != null) {
			new Ext.FormPanel( {
				title :l_loginInfo,
				renderTo :'loginInfo',
				frame :true,
				html :loginInfo
			});
		}
	} else {
		clearInterval(dateInterval);
	}
};
// 移除页面
var removeTab = function(title) {
	var object = tabs.find('title',title);
	tabs.remove(object[0]);
}
/**
 * 登出处理
 */
var logout = function() {
   Ext.MessageBox.confirm(l_msg_tip, String.format(l_logOut), function(btnId) {
        if (btnId == 'yes'){
        	gsl.request({
        		url: 'login-exit.action',
        		success: function(response){
        			window.location.href="login.action";
        		}
        	});
        }
   });
};

Ext.onReady(function() {
	/**
	 * 主页信息
	 */
		
	// 主页左边栏信息
		setInterval(datetimeInfo, 1000);
		// 主页内容
		document.getElementById('west').innerHTML = document
				.getElementById('homepage_menu').innerHTML;

		if (loginInfo != '' && loginInfo != null) {
			new Ext.FormPanel( {
				title :l_loginInfo,
				renderTo :'loginInfo',
				frame :true,
				html :loginInfo
			});
		}
		var originalPwd2 = new gsl.TextField( {
			id :'originalPwd',
			allowBlank :false,
			name :'originalPwd',
			anchor :'95%',
			inputType :'password',
			fieldLabel :l_originalPwd
		});
		var newPwd2 = new gsl.TextField( {
			id :'supplierPwd',
			name :'supplierPwd',
			inputType :'password',
			minLength : 6,
			maxLength :15,
			anchor :'95%',
			allowBlank :false,
			fieldLabel :l_newPwd
		});
		var confirmPwd2 = new gsl.TextField( {
			id :'confirmSupplierPwd',
			anchor :'95%',
			inputType :'password',
			allowBlank :false,
			minLength : 6,
			maxLength :15,
			name :'confirmSupplierPwd',
			invalidText:l_twoPwdDifferent,
			fieldLabel :l_confirmPwd,
			validator:function(){
			if(newPwd2.getValue() ==confirmPwd2.getValue()){
				return true;
			}else{
				return false;
			}
		}
		});
		var changePwdForm2 = new Ext.FormPanel( {
			width :180,
			title :l_changePwd,
			renderTo :'changePwd',
			frame :true,
			labelWidth :70,
			items : [ {
				layout :'column',
				items : [ {
					columnWidth :1,
					layout :'form',
					items : [ {},originalPwd2, newPwd2, confirmPwd2 ]
				} ]
			} ],
			buttons : [ {
				text :l_comfirm,
				handler : function() {
					if (changePwdForm2.form.isValid()) {
						gsl.request({
					           url: 'login-changePwd.action',
					           params:{
									'originalPwd':originalPwd2.getValue(),
									'supplierPwd':newPwd2.getValue(),
									'confirmSupplierPwd':confirmPwd2.getValue()
					           },
					           success :function(response){
					        	   var result = Ext.decode(response.responseText);
					        	   if (result.success) {
					        		   gsl.InfoAlert(l_changePwdSuccess);
					        	   } else {
					        		   gsl.ErrorAlert(result.msg);
					        	   }
					           }
					        });
						//changePwdForm2.collapse(true);
					}
				}
			} ]
		});
		changePwdForm2.on('beforecollapse', function() {
			this.form.reset();
			changePwdForm2.form.clearInvalid();
		});
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

		// 内容
		tabs = new Ext.TabPanel( {
			id : 'mainTabPanel',
			region : 'center',
			margins : '0 2 2 0',
			deferredRender : false,
			enableTabScroll : true,
			plugins : new gsl.TabCloseMenu(),
			activeTab : 0,
			items : [ {
				autoLoad :  {url: 'pub/pub_homepage.action', scope:this,scripts:true},
				title : l_index,
				closable : false,
				autoScroll : true
			} ]
            ,listeners:{
			  tabchange:function(){adjCtrWidth(true);}
            }
		});

		
		// 追加Tab函数
		addNewTab = function(menu, src, refresh, destoryFn) {
			var tabTitle = "未命名";
			if (typeof menu == 'string') {
				tabTitle = menu;
			} else {
				tabTitle = menu.innerHTML;
			}
			var isExist = false;
			var actionIndex = src.indexOf('.');
			var jsptitlew = src.substring(0, actionIndex == -1 ? src.length : actionIndex);
			
			var existItem = null;
			tabs.items.each(function(item) {
				if (tabTitle == item.title) {
					isExist = true;
					existItem = item;
				}
			});
			if (!isExist) {
                var tempId = Ext.id();
				if (Ext.isIE6) {
					tabs.add({
						title : tabTitle,
                        id:tempId,
                        jsptitle : jsptitlew,
						autoScroll : true,
						closable : true,
						//autoLoad : {url: src, scope:this,scripts:true}
						html:'<iframe src="" id="iframe_'+tempId+'" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>'
					}).show();
					//iframeContent.location.href = src;
					Ext.get('iframe_' + tempId).dom.src=src;
				} else {
					if(destoryFn){
						tabs.add({
							layout:'fit',
							deferredRender :false,
	                        id:tempId,
							title : tabTitle,
							jsptitle : jsptitlew,
							autoScroll : true,
							closable : true,
							html:String.format('<iframe src={0} id="iframe_'+tempId+'" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>',src)
							//autoLoad :  {url: src, scripts:true}
							,destroy : destoryFn
						}).show();
					}else{
						tabs.add({
							layout:'fit',
							deferredRender :false,
	                        id:tempId,
							title : tabTitle,
							jsptitle : jsptitlew,
							autoScroll : true,
							closable : true,
							html:String.format('<iframe src={0} id="iframe_'+tempId+'" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>',src)
							//autoLoad :  {url: src, scripts:true}
						}).show();
					}
				}
				
				var tempDom = getDomById(tabs.getActiveTab().id);
	            if(Ext.isEmpty(tempDom)) return;
				tempDom.tabActived=true;
			} else {
                // 激活Tab
                tabs.activate(existItem.id);
                // 刷新内容
                if(refresh){
				  Ext.get('iframe_' + existItem.id).dom.src=src;
                }
			}
		};

		//tabpanel是否存在
		tabpanelIsExist = function(opts){
			var isExist = false;
			tabs.items.each(function(item) {
//				if (opts.title == item.title) {
//					isExist = true;
//				}
				if(opts.jsptitle = item.jsptitle){
					isExist = true;
				}
			});
			return isExist;
		}
		
		// 页面布局
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [
			// 头部导航栏，公司logo等信息
					new Ext.BoxComponent( {
						region : 'north',
						el : 'north',
						height : 84
					// 底部所有权信息
							}), new Ext.BoxComponent( {
						region : 'south',
						el : 'south',
						height : 20
					}), {
						// 左边栏
						region : 'west',
						id : 'west-panel',
						title : l_menu,
						split : true,
						width : 178,
						minSize : 178,
						maxSize : 178,
						collapsible : true,
						margins : '0 0 2 2',
						layoutConfig : {
							animate : true
						},
						items : [ {
							contentEl : 'west',
							border : false
						} ]
                        ,listeners:{
                            collapse:adjCtrWidth
                            ,expand:adjCtrWidth
                        }
					// 内容
					}, tabs ]
		});
        
        /**
         * 根据菜单栏展开和收缩,设定宽度
         */
        function adjCtrWidth(tabActive){
        	var westMenu = Ext.getCmp('west-panel');
            if(Ext.isEmpty(westMenu)) return;
        	var tempWin;
        	var tempCtrs;
        	// 菜单栏
        	if(tabs.getActiveTab().title=='主页')
        	{
        		tempWin=window;
        		tempCtrs=window.adjWidthCtrs;
        	}
        	else
        	{
	            // Iframe
	            var tempIframe = Ext.get('iframe_'+tabs.getActiveTab().id);
	            if(Ext.isEmpty(tempIframe)) return;
	            // Dom
	            var tempDom = tempIframe.dom;
	            if(Ext.isEmpty(tempDom)) return;
	            // Dom内的contentWindow
	            tempWin = tempDom.contentWindow;
	            if(Ext.isEmpty(tempWin)) return;
	            // contentWindow内定义的需调整宽度的控件
	            tempCtrs = tempWin.adjWidthCtrs;
        	}
            if(Ext.isEmpty(tempCtrs)) return;
            //用一个flag(tabActived 沿用之前的名字)记录tab的状态，ture为被拉宽，false为被缩小(默认宽度)
            if(tempWin.tabActived==null)
            {
            	tempWin.tabActived=false;
            	
            }
            // 调整控件的宽度
            for(var i=0;i<tempCtrs.length;i++){
                try{
                    if(westMenu.collapsed&&tempWin.tabActived==false){
                    	if((tempCtrs[i]).length!=null)//tabs
                    	{	 
            				 var width=tempCtrs[i-1].getSize().width;
                    		 for(var j=0;j<tempCtrs[i].length;j++)
                    		 { 
                        		 if(tempCtrs[i][j].length!=null)
                        		 {
                        			 for(var k=0;k<tempCtrs[i][j].length;k++)
                        			 {
                        				 tempCtrs[i][j][k].setWidth(tempCtrs[i][j][k].getSize().width+150/tempCtrs[i][j].length);
                        			 }
                        		 }
                        		 else
                        		 {
                        			 tempCtrs[i][j].setWidth(width);
                        		 }
                    		 }
                    	}
                    	else
                    	{
                    		tempCtrs[i].setWidth(tempCtrs[i].getSize().width+150);
                    	}
                    }else if(!(westMenu.collapsed)&&tempWin.tabActived==true){
                    	if((tempCtrs[i]).length!=null)//tabs
                    	{	 
                    	var width=tempCtrs[i-1].getSize().width;
                		 for(var j=0;j<tempCtrs[i].length;j++)
                		 { 
                    		 if(tempCtrs[i][j].length!=null)
                    		 {
                    			 for(var k=0;k<tempCtrs[i][j].length;k++)
                    			 {
                    				 tempCtrs[i][j][k].setWidth(tempCtrs[i][j][k].getSize().width-150/tempCtrs[i][j].length);
                    			 }
                    		 }
                    		 else
                    		 {
                    			 tempCtrs[i][j].setWidth(width);
                    		 }
                		 }
                	}
                    	else
                    	{
                    		tempCtrs[i].setWidth(tempCtrs[i].getSize().width-150);
                    	}
                    }
                }catch(err){
                }
            }
            if(westMenu.collapsed&&tempWin.tabActived==false){
            	tempWin.tabActived=true;
            }else if(!(westMenu.collapsed)&&tempWin.tabActived==true){
            	tempWin.tabActived=false;
            }
//            // 如果通过点击标签，激活Tab，且在菜单栏变化后，已被调整宽度
//            if(tabActive==true && tempDom.tabActived==true){
//            	return;
//            }
//            // 标签设置为已被调整宽度
//            tempDom.tabActived=true;
//            
//            // 菜单栏展开或收缩
//            if(tabActive!=true){
//	            tabs.items.each(function(item) {
//	            	// 除被激活的标签外，其他标签均设置为未被调整宽度
//	            	if(item.id != tabs.getActiveTab().id){
//	            		var tdom = getDomById(item.id);
//	            		if(tdom == undefined) return;
//	            		tdom.tabActived=false;
//	            	}
//				});
//            }
//            // 调整控件的宽度
//            for(var i=0;i<tempCtrs.length;i++){
//                try{
//                    if(westMenu.collapsed){
//                        tempCtrs[i].setWidth(tempCtrs[i].getSize().width+150);
//                    }else{
//                        tempCtrs[i].setWidth(tempCtrs[i].getSize().width-150);
//                    }
//                }catch(err){
//                }
//            }
        };
        
        /**
         * 根据Tab的id，取得iframe内的Dom
         */
        function getDomById(id){
        	var tempIframe = Ext.get('iframe_'+id);
            if(Ext.isEmpty(tempIframe)) return undefined;
            var tempDom = tempIframe.dom;
            if(Ext.isEmpty(tempDom)) return  undefined;
            return tempDom; 
        }
        
		// 移除画面遮罩
        setTimeout(function(){
        	Ext.get('loading').remove();
            Ext.get('loading-mask').fadeOut({remove:true});
        }, 250);
});