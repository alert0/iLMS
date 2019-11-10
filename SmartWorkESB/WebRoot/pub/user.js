//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //定义变量
    var userName;
    var isChangeRole;
    var finalUserRole = [];
    
    //用户按钮
    var btnQueryUser = $('.my-query-user button[btnname=search]');
    var btnSaveUserRole = $('.my-query-user button[btnname=save]');

    /////////////////////////////////////////////////////////////////////////////////////
    
    //获取高度
    var getHeightFn = function() {
        return $(window).height() - $('.my-query-user').outerHeight()
    }
    
    //加载用户表格数据
    var gridUser = $('div.my-result-user');
    gridUser.datagrid({
        dataSource: {
            cols:[{
                name: 'userName'
                ,label: '用户名'
                ,width: 200
                ,html: true
                ,tip: true
            }, {
                name: 'userDesc'
                ,label: '用户姓名'
                ,width: 200
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                var data = smartwork.getFormValue($('form.my-query-user'));
                
                return smartwork.gridRemoteConfig({
                            url: '../pub/QueryUser.do' //请求地址
                            ,data: data
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            //增加双击时间
            if (cell.rowIndex > 0 && cell.colIndex > 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click : function(event){
                        event.preventDefault();
                        selectUserConfirmFn(cell.rowIndex);
                    }
                    ,dblclick : function(event){
                        event.preventDefault();
                        modifyUserFn(cell.rowIndex)
                    }
                });
            }
        }
        ,valueOperator: {
            
        }
        ,height: getHeightFn()
        ,checkable: false
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:10000}
        }
        ,onLoad: function() {
            smartwork.disable(btnQueryUser, false);
        }
    });
    
    //获取用户表格对象
    var gridUserObj = gridUser.data('zui.datagrid');
    
    //用户新增方法
    var addUserFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-user');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //提交修改
        var submitFn = function() {
            
            //隐藏提示框
            smartwork.hide();
            
            //检验表单
            if(!form.valid()) {
                return;
            }
            
            //获取表单数据
            var values = smartwork.getFormValue(form);
            
            //提交
            smartwork.ajax({
                url : '../pub/AddUser.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                btnQueryUser.click();
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增用户');
        smartwork.readonly(dialog.find('[update=false]'), false);
        
        //设置数据
        smartwork.setFormValue(form, {});
        
        //配置确定按钮
        dialog.find('button[btnname=ok]').off().on('click', function(){
            submitFn();
        });
        
        //配置取消按钮
        dialog.find('button[btnname=cancel]').off().on('click', function(){
            dialog.modal('hide');
        });
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    }
    
    //用户修改方法
    var modifyUserFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            if(!smartwork.isEmpty(userName)) {
                var gridDatas = gridUserObj.getData();
                var index = smartwork.indexOf(gridDatas, 'userName', {'userName': userName})
                data = gridDatas[index];
            }
        } else  { //双击
            data = gridUserObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的用户');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-user');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //提交修改
        var submitFn = function() {
            
            //隐藏提示框
            smartwork.hide();
            
            //检验表单
            if(!form.valid()) {
                return;
            }
            
            //获取表单数据
            var values = smartwork.getFormValue(form);
            
            //提交
            smartwork.ajax({
                url : '../pub/UpdateUser.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                var rowIndex = gridUserObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridUserObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridUserObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改用户');
        smartwork.readonly(dialog.find('[update=false]'), true);
        
        //设置数据
        smartwork.setFormValue(form, data);
        
        //配置确定按钮
        dialog.find('button[btnname=ok]').off().on('click', function(){
            submitFn();
        });
        
        //配置取消按钮
        dialog.find('button[btnname=cancel]').off().on('click', function(){
            dialog.modal('hide');
        });
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    }
    
    //重置密码
    var resetPasswordFn = function() {
        smartwork.hide();
        
        //获取选择数据
        if (smartwork.isEmpty(userName)) {
            smartwork.error('请选择要重置密码的用户');
            return;
        }
         
        smartwork.confirm('确定要重置选择的用户的密码？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
        
            //提交
            smartwork.ajax({
                url : '../pub/ResetUserPassword.do'
                ,'show-loading': true
                ,data: {'userName': userName}
                ,traditional: true
            }, function(result){
                
            });
        });
    }
    
    //用户删除
    var deleteUserFn = function() {
        smartwork.hide();
        
        //获取选择数据
        if (smartwork.isEmpty(userName)) {
            smartwork.error('请选择要删除的用户');
            return;
        }
         
        smartwork.confirm('确定要删除选择的用户？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
        
            //提交
            smartwork.ajax({
                url : '../pub/DeleteUser.do'
                ,'show-loading': true
                ,data: {'userName': userName}
                ,traditional: true
            }, function(result){
                btnQueryUser.click();
            });
        });
    }
    
    //用户查询按钮
    btnQueryUser.click(function(){
        
        //禁用按钮
        smartwork.disable(btnQueryUser, true);
        
        //重设数据
        disableUserDetailBtnFn(true);
        removeUserDetailGridFn();
        
        smartwork.hide();
        smartwork.gridQuery(gridUserObj);
    });
    
    //用户-新增按钮
    $('.my-query-user button[btnname=add]').click(function(){
        addUserFn();
    });
    
    //用户-修改按钮
    $('.my-query-user button[btnname=modify]').click(function(){
        modifyUserFn();
    });
    
    //用户-重置密码
    $('.my-query-user button[btnname=resetpassword]').click(function(){
        resetPasswordFn();
    });
    
    //用户-删除按钮
    $('.my-query-user button[btnname=delete]').click(function(){
        deleteUserFn();
    });
    
    /////////////////////////////////////////////////////////////////////
    
    //权限-加载表格数据
    var gridRole = $('div.my-result-role');
    gridRole.datagrid({
        dataSource: {
            cols:[{
                name: 'roleName'
                ,label: '角色名'
                ,width: 200
                ,html: true
                ,tip: true
            }, {
                name: 'roleDesc'
                ,label: '备注'
                ,width: 200
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../pub/QueryRole.do' //请求地址
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            //增加双击事件
            if (cell.colIndex == 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click: function(event) {
                        event.preventDefault();
                        window.setTimeout(function(){
                            changeUserRoleFn();
                        }, 100);
                        
                    }
                });
            }
        }
        ,valueOperator: {
        }
        ,height: getHeightFn()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {pager:{page:1, recPerPage:10000}}
        ,onLoad: function() {
            
        }
    });
    
    //获取权限表格对象
    var gridRoleObj = gridRole.data('zui.datagrid');
    
    //改变用户角色
    var changeUserRoleFn = function() {
        
        //没有数据
        if (smartwork.isEmpty(userName)) {
            return;
        }
        
        var checkDatas = gridRoleObj.getCheckItems();
        
        //数量不对，肯定有变动
        if (checkDatas.length != finalUserRole.length) {
            isChangeRole = true;
            smartwork.elementTip(btnSaveUserRole, true, '用户角色有变动，请注意保存');
            return;
        }
        
        //选中的数据不在已保持中
        for (var i = 0; i < checkDatas.length; i++) {
            if (-1 == finalUserRole.indexOf(checkDatas[i]['pkId'])) {
                isChangeRole = true;
                smartwork.elementTip(btnSaveUserRole, true, '用户角色有变动，请注意保存');
                return;
            }
        }
        
        //数据相同
        isChangeRole = false;
        smartwork.elementTip(btnSaveUserRole, false);
    }
    
    //用户角色-保存方法
    var saveUserRoleFn = function() {
        
        var values = {'userName': userName};
        
        //遍历数据
        var checkDatas = gridRoleObj.getCheckItems();
        var name = null;
        for (var i = 0; i < checkDatas.length; i++) {
            if (smartwork.isEmpty(checkDatas[i]) || smartwork.isEmpty(checkDatas[i]['pkId'])) {
                continue;
            }
            
            name = 'roleIds[{0}]'.format(i);
            values[name] = checkDatas[i]['pkId'];
        }
        
        //提交
        smartwork.ajax({
            url : '../pub/SaveUserRole.do'
            ,'show-loading': true
            ,data: values
        }, function(result){
            smartwork.info('提交成功');
            
            //重设以保持权限
            finalUserRole = []; 
            for (var i = 0; i < checkDatas.length; i++) {
                if (smartwork.isEmpty(checkDatas[i]) || smartwork.isEmpty(checkDatas[i]['pkId'])) {
                    continue;
                }
            
                finalUserRole.push(checkDatas[i]['pkId']);
            }
        
            //删除提示
            smartwork.elementTip(btnSaveUserRole, false);
            isChangeRole = false;
        });
    }
    
    //权限-保存按钮
    btnSaveUserRole.click(function(){
        saveUserRoleFn();
    });
    
    //////////////////////////////////////////////////////////////////////////
    
    //禁用配置从表按钮
    var disableUserDetailBtnFn = function(disable) {
        smartwork.disable(btnSaveUserRole, disable);
    }
    
    //选中用户-确定事件
    var selectUserConfirmFn = function(rowIndex) {
        
        //获取点击的数据
        var data = gridUserObj.getData()[rowIndex - 1];
        
        //用户已处理
        if (userName == data['userName']) {
            return;
        }
        
        if (isChangeRole) {
            smartwork.confirm('确定要放弃未保存的数据？', function(action){ 
                
                //不是确定不处理
                if ('ok' != action) {
                    smartwork.gridRollbackHighlightSelect(gridUserObj);
                    return;
                }
            
                //先删除，再查询
                removeUserDetailGridFn();
                selectUserFn(data, rowIndex);
            });
        } else {
            
            //先删除，再查询
            removeUserDetailGridFn();
            selectUserFn(data, rowIndex);
        }
    }
    
    //选中用户
    var selectUserFn = function(data, rowIndex) {
        
        //获取选择用户
        userName = data['userName']
        
        //启用发送配置、接收配置、权限、通用参数的按钮
        disableUserDetailBtnFn(false);
        
        //查询激活标签的表格数据
        finalUserRole = [];
        smartwork.ajax({
            url : '../pub/QueryRoleByUser.do'
            ,data: {'userName': userName}
        }, function(result){
            finalUserRole = smartwork.parseJson(result, 'root');
            
            //选择
            smartwork.uncheckAll(gridRoleObj);
            gridRoleObj.renderData();
            
            var gridDatas = gridRoleObj.getData();
            $.each(finalUserRole, function(i, item){
                var index = smartwork.indexOf(gridDatas, 'pkId', {'pkId': item});
                if (-1 != index) {
                    gridRoleObj.checkRow(index + 1, true);
                }
            });
        });
    }
    
    //删除发送配置、接收配置、权限、通用参数的数据
    var removeUserDetailGridFn = function() {
        
        //重置全局数据
        userName = null;
        finalUserRole = [];
        
        //删除数据
        smartwork.uncheckAll(gridRoleObj);
        gridRoleObj.renderData();
        
        //禁用所有从表按钮
        disableUserDetailBtnFn(true);
        
        //去除提示
        smartwork.elementTip(btnSaveUserRole, false);
        isChangeRole = false;
    }
    
    //初始化时禁用所有从表按钮
    disableUserDetailBtnFn(true);
    
    //用户自动查询
    window.setTimeout(function(){
        
        //自动查询
        btnQueryUser.click();
        
        //查询所有权限
        smartwork.gridQuery(gridRoleObj);
        
    }, 100);
    
    //高宽变动
    gridRole.on('resize', function() {
        
        gridUserObj.options.height = getHeightFn();
        gridUserObj.updateLayout();
        
        gridRoleObj.options.height = getHeightFn();
        gridRoleObj.updateLayout();
    });
    
    //提示
    $('label[my-tip-title]').each(function(){
        var that = $(this);
        var title = that.attr('my-tip-title');
        var message = null;
        
        //设置消息MAX-NUMBER
        if ('EN' == title) {
            message = '由英文、数字、下划线或-组成';
        }
        
        //初始化提示
        if (message) {
            that.popover({
                trigger:'hover'
                ,html: true
                ,placement: 'top'
                ,container: that.parents('.modal.fade')
                ,content: message
            });
        }
    });
});