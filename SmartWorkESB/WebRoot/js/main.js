//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //当前用户
    smartwork.currentUser = {};
    
    //默认页面
    $('iframe[name=main]').attr('src', 'share/welcome.html');
    $('.my-center .my-right span[name=pathname]').html("主页&nbsp;&nbsp;>&nbsp;&nbsp;欢迎页");
    
    //获取菜单对象和重写菜单实现
    var menuTree = $('#treeMenu');
    var menuTreeObj = menuTree.data('zui.tree');
    menuTreeObj.options.itemCreator = function($li, item) {
        
        //增加模块代码属性
        if (item.modulecode) {
            $li.attr('modulecode', item.modulecode);
        } 
        
        //增加路径属性
        if (item.pathName) {
            $li.attr('pathname', item.pathName);
            $li.on({
                click : function() {
                    $('.my-center .my-right span[name=pathname]').html($li.attr('pathname'));
                }
            });
        }
        
        //链接
        var linkDom = $('<a/>', {href: item.url, target: item.modulecode ? '_self' : 'main' });
        
        //增加图标
        if (item.iconCls) {
            linkDom.append($('<i/>', {'class': item.iconCls}));
        }
        
        $li.append(linkDom.append(item.title));
    }
    
    //加载菜单
    var loadMenu = function(parentCode, parentName) {
        
        //提交
        smartwork.ajax({
            url : 'pub/QueryUserMenu.do'
            ,data : {
                moduleCode: parentCode
            }
        }, function(result){
            
            //数据内容
            var treeDatas = [];
            
            //获取模块
            var modules = smartwork.parseJson(result, 'root.module');
            $.each(modules, function(index, item) {
                
                //按分号分割，第一个：显示内容，第二个：是否展开；第三个：图标
                var names = (item.moduleName || '').replace(/(^\s+)|(\s+;)|(;\s+)|(\s+$)/gm,'').split(';');
                
                //增加节点数据
                treeDatas.push({
                    title : names[0]
                    ,url: '#'
                    ,iconCls: names[2]
                    ,modulecode: item.moduleCode
                    ,open: '1' == names[1]
                });
                
                //修改为名字
                item.moduleName = names[0];
            });
            
            //获取菜单
            var menus = smartwork.parseJson(result, 'root.privileges');
            $.each(menus, function(index, item) {
                
                //按分号分割，第一个：显示内容，第二个：图标
                var names = (item.privilegesDesc || '').replace(/(^\s+)|(\s+;)|(;\s+)|(\s+$)/gm,'').split(';');
                
                //增加节点数据
                treeDatas.push({
                    title : names[0]
                    ,url: (item.content + '?v=1.0') || 'javascript:;'
                    ,iconCls: names[1]
                    ,pathName: parentName ? (parentName + '&nbsp;&nbsp;>&nbsp;&nbsp;' + names[0]) : names[0]
                });
            });
            
            //增加到菜单中
            var toElement = ('ROOT' == parentCode) ? $('#treeMenu') : $('#treeMenu').find('[modulecode=' + parentCode + ']');
            menuTreeObj.add(toElement, treeDatas);
    
            //递归查询
            $.each(modules, function(index, item) {
                loadMenu(item.moduleCode, parentName ? (parentName + '&nbsp;&nbsp;>&nbsp;&nbsp;' + item.moduleName) : item.moduleName);
            });
        });
    }   
    
    //加载菜单
    loadMenu('ROOT');
    
    //显示登录用户
    var loadCurrentUser = function() {
        
        //提交
        smartwork.ajax({
            url : 'pub/GetUser.do'
        }, function(result){
            
            //获取用户信息
            var userInfo = smartwork.parseJson(result, 'root');
            if (!userInfo) {
                return;
            }
            
            //设置当前用户
            smartwork.currentUser.userName = userInfo.userName;
            smartwork.currentUser.userDesc = userInfo.userDesc;
            
            //显示当前用户
            var displayName = userInfo.userDesc + '（' + userInfo.userName + '）';
            $('.my-header span[name=user]').text(displayName);
            
            //增加修改密码提示
            if ('0' == userInfo.isUpdatePwd) {
                $('.my-header a[name=modify-password]').append('<span class="label label-badge label-warning my-label-badge">1</span>');
            }
        });
    }
    loadCurrentUser();
    
    //修改密码
    var btnModify = $('.my-header [name=modify-password]');
    btnModify.on('click', function(){
        
        var dialog = $('#my-password');
        
        //修改密码
        var modifyPassordFn = function() {
            
            //隐藏提示框
            smartwork.hide(); 
            
            //获取数据
            var oldPassword = dialog.find('input[name=oldPassword]').val();
            var newPassword = dialog.find('input[name=newPassword]').val();
            var confirmPassword = dialog.find('input[name=confirmPassword]').val();
            
            //判断数据
            if ('' === oldPassword) {
                smartwork.error('原始密码不允许为空');
                return;
            }
            if ('' === newPassword) {
                smartwork.error('新密码不允许为空');
                return;
            }
            if ('' === confirmPassword) {
                smartwork.error('确认新密码不允许为空');
                return;
            }
            if (newPassword != confirmPassword) {
                smartwork.error('确认新密码与新密码不一致');
                return;
            }
            
            //提交
            smartwork.ajax({
                url : 'pub/ChangeUserPassword.do'
                ,'show-loading': true
                ,data: {
                    'oldPassword': oldPassword
                    ,'newPassword': newPassword
                    ,'confirmPassword': confirmPassword
                }
            }, function(result){
                dialog.modal('hide'); //隐藏
                $('.my-header a[name=modify-password] span.label').remove(); //删除密码未更改提示
                dialog.find('form')[0].reset();//重置表单
                smartwork.info('修改成功');
            });
        }
        
        //重置表单
        dialog.find('form')[0].reset();
        
        //设置用户名
        dialog.find('input[name=userName]').val(smartwork.currentUser.userName);
        
        //配置确定按钮
        var okBtn = dialog.find('button[btnname=ok]');
        okBtn.off(); //取消绑定事件
        okBtn.on('click', function(){
            modifyPassordFn();
        });
        
        //配置取消按钮
        var cacelBtn = dialog.find('button[btnname=cancel]');
        cacelBtn.off();//取消绑定事件
        cacelBtn.on('click', function(){
            dialog.modal('hide');
        });
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    });
    
    //增加修改密码提示
    btnModify.hover(function(){
        var that = btnModify;
        
        //没有新消息则不处理
        if (that.find('span.label').length <= 0){
            return;    
        }
        
        //初始化提示
        if ('popover' != that.attr('data-toggle')) {
            that.popover({
                trigger: 'manual'
                ,content: '密码已过期，请修改密码'
                ,placement: 'bottom'
                ,container: 'body'
            });
            that.attr('data-toggle', 'popover'); 
        }
        
        //显示
        that.popover('show');
    }
    ,function(){
        var that = btnModify;
        
        //没有新消息则不处理
        if (that.find('span.label').length <= 0){
            return;    
        }
        
        if ('popover' == that.attr('data-toggle')) {
            that.popover('hide');
        }
    });
    
    //退出系统
    $('.my-header [name=logout]').on('click', function() {
        smartwork.confirm('确定要退出系统？', function(action){
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //提交
            smartwork.ajax({
                url : 'pub/Logout.do'
            }, function(result){
                window.location = 'index.html';
            });
        });
    });
});