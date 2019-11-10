//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //定义变量
    var rolePkId;
    var isChangePrivileges;
    var finalRolePrivileges = [];
    
    //角色按钮
    var btnQueryRole = $('.my-query-role button[btnname=search]');
    var btnSaveRolePrivileges = $('.my-query-role button[btnname=save]');

    /////////////////////////////////////////////////////////////////////////////////////
    
    //获取高度
    var getHeightFn = function() {
        return $(window).height() - $('.my-query-role').outerHeight()
    }
    
    //加载角色表格数据
    var gridRole = $('div.my-result-role');
    gridRole.datagrid({
        dataSource: {
            cols:[{
                name: 'roleName'
                ,label: '角色名称'
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
                var data = smartwork.getFormValue($('form.my-query-role'));
                
                return smartwork.gridRemoteConfig({
                            url: '../pub/QueryRole.do' //请求地址
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
                        selectRoleConfirmFn(cell.rowIndex);
                    }
                    ,dblclick : function(event){
                        event.preventDefault();
                        modifyRoleFn(cell.rowIndex)
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
            smartwork.disable(btnQueryRole, false);
        }
    });
    
    //获取角色表格对象
    var gridRoleObj = gridRole.data('zui.datagrid');
    
    //角色新增方法
    var addRoleFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-role');
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
                url : '../pub/AddRole.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                btnQueryRole.click();
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增角色');
        
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
    
    //角色修改方法
    var modifyRoleFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            if(!smartwork.isEmpty(rolePkId)) {
                var gridDatas = gridRoleObj.getData();
                var index = smartwork.indexOf(gridDatas, 'pkId', {'pkId': rolePkId})
                data = gridDatas[index];
            }
        } else  { //双击
            data = gridRoleObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的角色');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-role');
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
                url : '../pub/UpdateRole.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                var rowIndex = gridRoleObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridRoleObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridRoleObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改角色');
        
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
    
    //角色删除
    var deleteRoleFn = function() {
        smartwork.hide();
        
        //获取选择数据
        if (smartwork.isEmpty(rolePkId)) {
            smartwork.error('请选择要删除的角色');
            return;
        }
         
        smartwork.confirm('确定要删除选择的角色？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
        
            //提交
            smartwork.ajax({
                url : '../pub/DeleteRole.do'
                ,'show-loading': true
                ,data: {'id': rolePkId}
                ,traditional: true
            }, function(result){
                btnQueryRole.click();
            });
        });
    }
    
    //角色查询按钮
    btnQueryRole.click(function(){
        
        //禁用按钮
        smartwork.disable(btnQueryRole, true);
        
        //重设数据
        disableRoleDetailBtnFn(true);
        removeRoleDetailGridFn();
        
        smartwork.hide();
        smartwork.gridQuery(gridRoleObj);
    });
    
    //角色-新增按钮
    $('.my-query-role button[btnname=add]').click(function(){
        addRoleFn();
    });
    
    //角色-修改按钮
    $('.my-query-role button[btnname=modify]').click(function(){
        modifyRoleFn();
    });
    
    //角色-删除按钮
    $('.my-query-role button[btnname=delete]').click(function(){
        deleteRoleFn();
    });
    
    /////////////////////////////////////////////////////////////////////
    
    //权限-加载表格数据
    var gridPrivileges = $('div.my-result-privileges');
    gridPrivileges.datagrid({
        dataSource: {
            cols:[{
                name: 'moduleName'
                ,label: '模块名称'
                ,width: 200
                ,html: true
                ,valueType: 'first'
                ,tip: true
            }, {
                name: 'privilegesDesc'
                ,label: '权限描述'
                ,width: 200
                ,valueType: 'first'
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../pub/QueryPrivileges.do' //请求地址
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
                            changeRolePrivilegesFn();
                        }, 100);
                        
                    }
                });
            }
        }
        ,valueOperator: {
            'first': {
                getter: function(dataValue, cell, dataGrid) {
                    var index = (dataValue || '').indexOf(';');
                    if (-1 != index) {
                        return dataValue.substring(0, index);
                    }
                    
                    return '';
                }
            }
        }
        ,height: getHeightFn()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {pager:{page:1, recPerPage:10000}}
        ,onLoad: function() {
            
        }
    });
    
    //获取权限表格对象
    var gridPrivilegesObj = gridPrivileges.data('zui.datagrid');
    
    //改变角色权限
    var changeRolePrivilegesFn = function() {
        
        //没有数据
        if (smartwork.isEmpty(rolePkId)) {
            return;
        }
        
        var checkDatas = gridPrivilegesObj.getCheckItems();
        
        //数量不对，肯定有变动
        if (checkDatas.length != finalRolePrivileges.length) {
            isChangePrivileges = true;
            smartwork.elementTip(btnSaveRolePrivileges, true, '角色权限有变动，请注意保存');
            return;
        }
        
        //选中的数据不在已保持中
        for (var i = 0; i < checkDatas.length; i++) {
            if (-1 == finalRolePrivileges.indexOf(checkDatas[i]['pkId'])) {
                isChangePrivileges = true;
                smartwork.elementTip(btnSaveRolePrivileges, true, '角色权限有变动，请注意保存');
                return;
            }
        }
        
        //数据相同
        isChangePrivileges = false;
        smartwork.elementTip(btnSaveRolePrivileges, false);
    }
    
    //角色权限-保存方法
    var saveRolePrivilegesFn = function() {
        
        var values = {'roleId': rolePkId};
        
        //遍历数据
        var checkDatas = gridPrivilegesObj.getCheckItems();
        var name = null;
        for (var i = 0; i < checkDatas.length; i++) {
            
            if (smartwork.isEmpty(checkDatas[i]) || smartwork.isEmpty(checkDatas[i]['pkId'])) {
                continue;
            }
            
            name = 'privilegesIds[{0}]'.format(i);
            values[name] = checkDatas[i]['pkId'];
        }
        
        //提交
        smartwork.ajax({
            url : '../pub/SaveRolePrivileges.do'
            ,'show-loading': true
            ,data: values
        }, function(result){
            smartwork.info('提交成功');
            
            //重设以保持权限
            finalRolePrivileges = []; 
            for (var i = 0; i < checkDatas.length; i++) {
                if (smartwork.isEmpty(checkDatas[i]) || smartwork.isEmpty(checkDatas[i]['pkId'])) {
                    continue;
                }
                finalRolePrivileges.push(checkDatas[i]['pkId']);
            }
        
            //删除提示
            smartwork.elementTip(btnSaveRolePrivileges, false);
            isChangePrivileges = false;
        });
    }
    
    //权限-保存按钮
    btnSaveRolePrivileges.click(function(){
        saveRolePrivilegesFn();
    });
    
    //////////////////////////////////////////////////////////////////////////
    
    //禁用配置从表按钮
    var disableRoleDetailBtnFn = function(disable) {
        smartwork.disable(btnSaveRolePrivileges, disable);
    }
    
    //选中角色-确定事件
    var selectRoleConfirmFn = function(rowIndex) {
        
        //获取点击的数据
        var data = gridRoleObj.getData()[rowIndex - 1];
        
        //角色已处理
        if (rolePkId == data['pkId']) {
            return;
        }
        
        if (isChangePrivileges) {
            smartwork.confirm('确定要放弃未保存的数据？', function(action){ 
                
                //不是确定不处理
                if ('ok' != action) {
                    smartwork.gridRollbackHighlightSelect(gridRoleObj);
                    return;
                }
            
                //先删除，再查询
                removeRoleDetailGridFn();
                selectRoleFn(data, rowIndex);
            });
        } else {
            
            //先删除，再查询
            removeRoleDetailGridFn();
            selectRoleFn(data, rowIndex);
        }
    }
    
    //选中角色
    var selectRoleFn = function(data, rowIndex) {
        
        //获取选择角色
        rolePkId = data['pkId']
        
        //启用发送配置、接收配置、权限、通用参数的按钮
        disableRoleDetailBtnFn(false);
        
        //查询激活标签的表格数据
        finalRolePrivileges = [];
        smartwork.ajax({
            url : '../pub/QueryPrivilegesByRole.do'
            ,data: {'roleId': rolePkId}
        }, function(result){
            finalRolePrivileges = smartwork.parseJson(result, 'root');
            
            //选择
            smartwork.uncheckAll(gridPrivilegesObj);
            gridPrivilegesObj.renderData();
            
            var gridDatas = gridPrivilegesObj.getData();
            $.each(finalRolePrivileges, function(i, item){
                var index = smartwork.indexOf(gridDatas, 'pkId', {'pkId': item});
                if (-1 != index) {
                    gridPrivilegesObj.checkRow(index + 1, true);
                }
            });
        });
    }
    
    //删除发送配置、接收配置、权限、通用参数的数据
    var removeRoleDetailGridFn = function() {
        
        //重置全局数据
        rolePkId = null;
        finalRolePrivileges = [];
        
        //删除数据
        smartwork.uncheckAll(gridPrivilegesObj);
        gridPrivilegesObj.renderData();
        
        //禁用所有从表按钮
        disableRoleDetailBtnFn(true);
        
        //去除提示
        smartwork.elementTip(btnSaveRolePrivileges, false);
        isChangePrivileges = false;
    }
    
    //初始化时禁用所有从表按钮
    disableRoleDetailBtnFn(true);
    
    //角色自动查询
    window.setTimeout(function(){
        
        //自动查询
        btnQueryRole.click();
        
        //查询所有权限
        smartwork.gridQuery(gridPrivilegesObj);
        
    }, 100);
    
    //高宽变动
    gridPrivileges.on('resize', function() {
        
        gridRoleObj.options.height = getHeightFn();
        gridRoleObj.updateLayout();
        
        gridPrivilegesObj.options.height = getHeightFn();
        gridPrivilegesObj.updateLayout();
    });
    
});