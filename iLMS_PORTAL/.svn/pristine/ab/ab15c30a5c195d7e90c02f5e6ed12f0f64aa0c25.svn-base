 /***********************************************************************

  * 扩展easyui的顶部窗口
  * <pre> 
  * 作者：hugh zhuang
  * 邮箱:zhuangxh@jee-soft.cn
  * 日期:2014-8-8-上午11:15:52
  * 版权：广州宏天软件有限公司版权所有
  * </pre>
  * 
  * easyUi的原来的属性写在base属性里面
 *
 **********************************************************/

 (function($) {
     var IconClsDic = {
         "确定": "fa fa-save",
         "选择": "fa fa-save",
         "保存": "fa fa-save",
         "确定并退出": "fa fa-paste",
         "取消": "fa fa-remove",
         "关闭": "fa fa-remove",
         "清空":"fa fa-rotate-left"
     };

     /**
      * 获取顶部窗口
      */
     function getParent(w) {
         var win = w ? w : window;
         //找到最顶层窗口
         if (win.parent != win){
        	 return getParent(win.parent);
         } 
         if(win.$&&win.$.messager){
        	 return win;
         }else{
        	 return window;
         }
     };
     /**
      * 消息提示
      */
     function messageCall(myopt, opt) {
         var conf = {
                 type: 'show'
             },
             parentMessager = myopt.win.$.messager;
         var $win ;
         $.extend(conf, opt);
         switch (conf.type) {
             case 'show':
            	 $win = parentMessager.show(conf);
                 break;
             case 'alert':
            	 $win =parentMessager.alert(conf.title, conf.msg, conf.icon, conf.fn);
                 break;
             case 'confirm':
            	 $win = parentMessager.confirm(conf.title, conf.msg, conf.fn);
                 break;
             case 'prompt':
            	 $win = parentMessager.prompt(conf.title, conf.msg, conf.fn);
                 break;
             case 'progress':
            	 $win = parentMessager.progress(conf);
                 break;
             case 'closeProgress':
            	 $win = parentMessager.progress('close');
                 break;
             default:
            	 $win = parentMessager.show(conf);
                 break;
         }
         
         if($win){
        	 createBackgroundIframe($win.parent(".messager-window"));
         }
     }
     /**
      * 输出指定位数的随机数的随机整数
      */
     function randomNum() {
         return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
             var r = Math.random() * 16 | 0,
                 v = c === 'x' ? r : (r & 0x3 | 0x8);
             return v.toString(16);
         });
     }
     /**
      * 对话框
      */
     function dialogCall(myopt, opt) {
         var winId = myopt.id || opt.id,
             $win = null,
             myWidom = myopt.win;
         rnd = randomNum();
         if (winId) {
             $win = myopt.currentWin.$('#' + winId);
             if (!$win) {
                 alert('未获取对话框ID');
                 return;
             }
             $win.removeClass('hidden').addClass('easyui-dialog');
         } else {
             winId = 'win' + rnd;
             $win = myWidom.$('#win').clone();
             if ($win && $win.length == 0) { //如果在父类找不到，则给父类创建个临时窗口
                 $win = $('<div id="' + winId + '"  title="win" style="width:600px;height:400px;z-index:10000;" data-options="modal:true"></div>');
             } else {
                 $win.attr('id', winId);
             }
         }
         $win.appendTo(myWidom.$('body'));
         $win=myWidom.$($win);
         if (myopt.src) {
             opt.href = '';
             opt.content = '<iframe id="iframe_' + winId + '" style="width:100%;height:100%;border:0"  frameborder="0"  src="' + myopt.src + '"></iframe>';
         }
         $win.dialog(opt);
         if (myopt.src) {
             //获取iframe窗口对象。
        	 $win.innerWin = myWidom.document.getElementById('iframe_' + winId).contentWindow;
             //当前对话框的父窗口
        	 $win.innerWin.parentWindow = myopt.currentWin;
             // 将当前窗口的引用放到自身上面，以便关闭自己。
        	 $win.innerWin.selfDialog = $win; //eg:在弹出的框中、关闭方法：this.window.selfDialog.dialog('close');
        	 $win.innerWin.passConf = opt.passConf;
        	 $win.innerWin.isSingleSelector = opt.isSingle; // 是否是单选、默认否 
        	 
        	 fixDialogShowBugs($win);
             return $win;
         }
         fixDialogShowBugs($win);
         
         return $win;
     }
     
     //1、部分页面滚动条很长的话，弹框位置出现异常 重新计算
     //2、在特殊页面office或者flash置顶处理
    function fixDialogShowBugs($win){
    	var winParent = $win.parent();
    	createBackgroundIframe(winParent);
    	
    	if($(document).scrollTop()<10){return;}
    	//弹出框后弹出框不在处理
    	if(winParent.is("div")&&(winParent.hasClass("panel") || winParent.hasClass("window"))){return;}
    	var top = Math.ceil(window.document.body.clientHeight-winParent.outerHeight())/2 + $(document).scrollTop(); 
        $win.dialog('move', {top:top});
    }
    
    
     /**
      * 窗口
      */
     function windowCall(myopt, opt) {
         if (myopt.id) {
             var $win = myopt.currentWin.$('#' + myopt.id);
             $win.appendTo(myopt.win.$('body'));
             myopt.win.$('#' + myopt.id).window(opt);
             return myopt.win.$('#' + myopt.id);
         } else {
             myopt.win.$('#win').window(opt);
             return myopt.win.$('#win');
         }

     };

     function handle(opt) {
         var def = {
             fn: 'message',
             base: {},
             'currentWin': window,
             'win': getParent()
         };
         $.extend(def, opt);
         //对话框的默认图标
         def.base.iconCls = def.base.iconCls || "fa fa-table";
         if (def.base.buttons) {
             //给按钮添加默认样式
             for (var i = 0, c; c = def.base.buttons[i++];) {
                 !c.iconCls && (c.iconCls = IconClsDic[c.text]);
             }
         }

         if ('message' == def.fn){
             messageCall(def, def.base);
         }
         else if ('window' == def.fn){
             return windowCall(def, def.base);
         }
         else if ('dialog' == def.fn){
             return dialogCall(def, def.base);
         }
         else if ('closeDialog' == def.fn && def.id) {
             def.window.$('#' + def.id).dialog('close');
//             hideBackgroundIfream();
         }
     };
     
     $.topCall = {
         window: function(opt) {
             opt.fn = 'window';
             return handle(opt);
         },
         message: function(opt) {
             opt.fn = 'message';
             handle(opt);
         },
         dialog: function(opt) {
             opt.fn = 'dialog';
             return handle(opt);
         },
         dialogClose: function(id) {
             var opt = {
                 fn: 'closeDialog',
                 id: id
             };
             return handle(opt);
         },
         alert: function(title, msg, icon, fn) {
             var opt = {
                 fn: 'message',
                 base: {
                     type: 'alert',
                     title: title,
                     msg: msg,
                     icon: (icon ? icon : 'info'),
                     fn: fn
                 }
             };
             handle(opt);
         },
         confirm: function(title, msg, fn) {
             var opt = {
                 fn: 'message',
                 base: {
                     type: 'confirm',
                     title: title,
                     msg: msg,
                     fn: fn
                 }
             };
             handle(opt);
         },
         show: function(options) {
             var opt = {
                 fn: 'message',
                 base: options
             };
             handle(opt);
         },
         toast: function(title, msg) {
             var opt = {
                 fn: 'message',
                 base: {
                     title: title,
                     msg: msg,
                     timeout: 3000,
                     showType: 'slide',
                     style: {
                         right: '',
                         top: document.body.scrollTop + document.documentElement.scrollTop,
                         bottom: ''
                     }
                 }
             };
             handle(opt);
         },
         prompt: function(title, msg, fn) {
             var opt = {
                 fn: 'message',
                 base: {
                     type: 'prompt',
                     title: title,
                     msg: msg,
                     fn: fn
                 }
             };
             handle(opt);
         },

         progress: function(options) {
             options = options || {};
             options.type = 'progress';
             var opt = {
                 fn: 'message',
                 base: options
             };
             handle(opt);
         },
         closeProgress: function() {
             var options = {
                 type: 'closeProgress'
             };
             var opt = {
                 fn: 'message',
                 base: options
             };
             handle(opt);
         },
         errorStack: function(msg, cause, title,fn) {
             var errorSatckDialog = "";
             var opt = {
                 fn: 'dialog',
                 base: {
                     width: 350,
                     height: 258,
                     modal: true,
                     resizable: true,
                     iconCls: 'fa fa-table',
                     title: (title ? title : '错误提示'),
                     href: '',
                     buttons: [{
                         text: '关闭',
                         iconCls: 'fa fa-remove',
                         handler: function() {
                        	 if(fn) fn();
                        	 errorSatckDialog.dialog('close'); 
                         }
                     }],
                     content: "<div class='div_error' >\
                     				<div class='div_error_msg'>" + msg + "</div>\
                     				<div class='div_error_detail' >\
                     						<textarea class='div_error_content'>" + cause + "</textarea>\
                     				</div>\
                     		  </div>"
                 }
             };
             errorSatckDialog = handle(opt);
         },
         error: function(msg, cause, fn, title) {
             if (cause) {
                 $.topCall.errorStack(msg, cause, title,fn);
             } else {
                 var opt = {
                     fn: 'message',
                     base: {
                         type: 'alert',
                         title: (title ? title : '错误提示'),
                         icon: 'error',
                         msg: msg,
                         fn: fn
                     }
                 };
                 handle(opt);
             }
         },
         success: function(msg, fn, title) {
             var opt = {
                 fn: 'message',
                 base: {
                     type: 'alert',
                     title: (title ? title : '成功提示'),
                     msg: msg,
                     icon: 'info',
                     fn: fn
                 }
             };
             handle(opt);
         },
         warn: function(msg, fn, title) {
             if (typeof(fn) != "function")
                 title = fn;
             if (typeof(title) == "function")
                 fn = title;
             var opt = {
                 fn: 'message',
                 base: {
                     type: 'alert',
                     title: (title ? title : '警告提示'),
                     icon: 'warning',
                     msg: msg,
                     fn: fn
                 }
             };
             handle(opt);
         }

     };
 })(jQuery);