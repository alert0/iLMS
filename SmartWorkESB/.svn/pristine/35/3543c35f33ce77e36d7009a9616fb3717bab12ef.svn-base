//alert(JSON.stringify(result))
$(document).ready(function(){
    
    var origQueryFileKey, parentIfCode;
    
    //发送日志按钮
    var formQuerySendLog = $('.my-query-sendlog');
    var btnQuerySendLog = formQuerySendLog.find('button[btnname=search]');
    
    //加载数据-日志类型
    var storeLogType = null;
    smartwork.loadDataDict('LOG_LOG_TYPE'
    ,function(array){
        storeLogType = array;
    });
    
    //加载数据-发送结果
    var storeSendResult = null;
    smartwork.loadDataDict('LOG_SEND_RESULT'
    ,function(array){
        storeSendResult = array;
        var cbo = formQuerySendLog.find('[name=sendResult]');
        smartwork.initCombox(cbo, storeSendResult, {show_text:true});
        cbo.parent().css('display', '');
    });
    
    //加载数据-接收结果
    var storeReceiveResult = null;
    smartwork.loadDataDict('LOG_RECEIVE_RESULT'
    ,function(array){
        storeReceiveResult = array;
    });

    /////////////////////////////////////////////////////////////////////////////////////
    
    //初始化时间控件
    smartwork.initDateTime(formQuerySendLog.find('[name=startCreateTime]'));
    //smartwork.initDateTime(formQuerySendLog.find('[name=endCreateTime]'));
    
    //加载发送日志表格数据
    var gridSendLog = $('div.my-result-sendlog');
    gridSendLog.datagrid({
        dataSource: {
            cols:[{
                name: 'ifCode'
                ,label: '接口名称'
                ,width: 140
                ,valueType: 'ifCode'
                ,html: true
                ,tip: true
            }, {
                name: 'tableId'
                ,label: '配置代码'
                ,width: 140
                ,html: true
                ,tip: true
            }, {
                name: 'createTime'
                ,label: '记录时间'
                ,width: 140
                ,valueType: 'datetime'
                ,html: true
                ,tip: true
            }, {
                name: 'sendResult'
                ,label: '发送结果'
                ,width: 75
                ,valueType: 'sendResult'
                ,html: true
            }, {
                name: 'receiveResult'
                ,label: '接收结果汇总'
                ,width: 100
                ,valueType: 'receiveResult'
                ,html: true
            }, {
                name: 'logType'
                ,label: '日志类型'
                ,width: 75
                ,valueType: 'logType'
                ,html: true
            }, {
                name: 'errorCode'
                ,label: '错误代码'
                ,width: 75
                ,html: true
                ,tip: true
            }, {
                name: 'errorMsg'
                ,label: '信息'
                ,width: 300
                ,html: true
                ,tip: true
            }, {
                name: 'queryKey'
                ,label: '手动查询数据'
                ,width: 100
                ,valueType: 'link'
                ,html: true
            }, {
                name: 'origQueryFileKey'
                ,label: '原始发送数据'
                ,width: 100
                ,valueType: 'link'
                ,html: true
            }, {
                name: 'thirdQueryFileKey'
                ,label: '最终发送数据'
                ,width: 100
                ,valueType: 'link'
                ,html: true
            }, {
                name: 'receiveFileKey'
                ,label: '接收数据'
                ,width: 100
                ,valueType: 'link'
                ,html: true
            }, {
                name: 'XXX'
                ,label: '主键'
                ,width: 1000
                ,valueType: 'keyValue'
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                var data = smartwork.getFormValue(formQuerySendLog);
                data['currentPage'] = params.page;
                data['pageSize'] = params.recPerPage;
                
                //删除接收结果
                if (0 == data['receiveResult']) {
                    delete data['receiveResult'];
                }
                
                return smartwork.gridRemoteConfig({
                            url: '../log/QuerySendLogHis.do' //请求地址
                            ,data: data
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            if (1 == smartwork.parseJson(cell, 'config.data.sendResult')) {
                if (cell.colIndex > 0 && !$cell.hasClass('my-cell-error')) {
                    $cell.addClass('my-cell-error');
                }
            } else {
                if (cell.colIndex > 0 && $cell.hasClass('my-cell-error')) {
                    $cell.removeClass('my-cell-error');
                }
            }
            
            //增加双击时间
            if (cell.rowIndex > 0 && cell.colIndex > 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click : function(event){
                        event.preventDefault();
                        selectParentLogFn(cell.rowIndex);
                    }
                });
            }
        }
        ,valueOperator: {
            'ifCode': {
                getter: function(dataValue, cell, dataGrid) {
                    var ifName = smartwork.parseJson(cell, 'config.data.ifName');
                    return dataValue + '(' + ifName + ')';
                }
            }
            ,'datetime': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.formatDate(dataValue, 'yyyy-MM-dd hh:mm:ss');
                }
            }
            ,'sendResult': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeSendResult, dataValue);
                }
            }
            ,'receiveResult': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeReceiveResult, dataValue);
                }
            }
            ,'logType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeLogType, dataValue);
                }
            }
            ,'keyValue': {
                getter: function(dataValue, cell, dataGrid) {
                    
                    var keyValues = [];
                    for (var i = 1; i <= 10; i++) {
                        var key = smartwork.parseJson(cell, 'config.data.key{0}Name'.format(i)); //获取键
                        if (smartwork.isEmpty(key)) {
                            break;
                        }
                        
                        var value = smartwork.parseJson(cell, 'config.data.key{0}Value'.format(i)); //获取值
                        keyValues.push(key + '=' + (smartwork.isEmpty(value) ? '' : value));
                    }
                    
                   return keyValues.join(', ');
                }
            }
            ,'link': {
                getter: function(dataValue, cell, dataGrid) {
                    if (smartwork.isEmpty(dataValue)) {
                        return '';
                    }
                    
                    return '<a href="javascript:void(0)" style="color:inherit;" onclick="smartwork.download(\'../log/DownloadLogFile.do?fileKey={0}\')">{0}</a>'.format(dataValue);
                }
            }
        }
        ,height: 300
        ,checkable: false
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:100}
            ,fixedLeftUntil: 3
        }
        ,onLoad: function() {
            smartwork.disable(btnQuerySendLog, false);
        }
    });
    
    //获取发送日志表格对象
    var gridSendLogObj = gridSendLog.data('zui.datagrid');
    
    //初始化选择接口弹窗
    var initSelectIfConfigDialogFn = function(dialog, callbackFn) {
        
        //获取控件
        if ('1' == dialog.attr('my-init')) {
            return;
        }
        dialog.attr('my-init', '1');
        
        //获取查询按钮
        var btnQueryIfConfig = dialog.find('button[btnname=search]');
        
        //加载接口配置表格数据
        var gridIfConfig = dialog.find('div.my-result-select-ifconfig');
        gridIfConfig.datagrid({
            dataSource: {
                cols:[{
                    name: 'ifCode'
                    ,label: '接口代码'
                    ,width: 230
                    ,html: true
                    ,tip: true
                }, {
                    name: 'ifName'
                    ,label: '接口名称'
                    ,width: 430
                    ,html: true
                    ,tip: true
                }]
                ,array: []
                ,remote: function(params, datagrid) {
                    var data = smartwork.getFormValue(dialog.find('form'));
                    data['currentPage'] = params.page;
                    data['pageSize'] = params.recPerPage;
                    
                    return smartwork.gridRemoteConfig({
                                url: '../config/QuerySendIfConfig.do' //请求地址
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
                        click: function(event) {
                            event.preventDefault();
                        }
                        ,dblclick : function(event){
                            event.preventDefault();
                            selectIfConfigFn(cell.rowIndex)
                        }
                    });
                }
            }
            ,valueOperator: {
                
            }
            ,height: 300
            ,checkable: false
            ,checkByClickRow: false
            ,states: {
                pager:{page:1, recPerPage:100}
            }
            ,onLoad: function() {
                smartwork.disable(btnQueryIfConfig, false);
            }
        });
        
        //获取接口配置表格对象
        var gridIfConfigObj = gridIfConfig.data('zui.datagrid');
        
        //选择接口
        var selectIfConfigFn = function(rowIndex) {
            var data = gridIfConfigObj.getData()[rowIndex - 1];
            dialog.modal('hide');
            callbackFn($.extend({}, data));
        }
        
        //配置查询按钮
        btnQueryIfConfig.click(function() {
            
            //禁用按钮
            smartwork.disable(btnQueryIfConfig, true);
            
            smartwork.hide();
            smartwork.gridQuery(gridIfConfigObj);
        });
        
        //配置取消按钮
        dialog.find('button[btnname=cancel]').off().on('click', function(){
            dialog.modal('hide');
        });
        
        //默认查询
        btnQueryIfConfig.click();
    }
    
    //选择接口配置
    var selectIfCodeFn = function(form) {
        
        var dialog = $('#my-dialog-select-ifconfig');
        
        //初始化弹窗
        initSelectIfConfigDialogFn(dialog, function(data) {
            form.find('[name=ifCode]').val(data['ifCode']);
        });
        
        //设置标题
        dialog.find('.modal-title').text('选择接口代码【双击选择】');
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    }
    
    //配置选择接口按钮
    formQuerySendLog.find('button[btnname=selectIfCode]').off().on('click', function(){
        selectIfCodeFn(formQuerySendLog);
    });
    
    //增加form验证
    smartwork.initValidate(formQuerySendLog);
        
    //验证查询form
    var validQueryFormFn = function() {
        
        //隐藏提示框
        smartwork.hide();
        
        //检验表单
        if(!formQuerySendLog.valid()) {
            return;
        }
        
        //获取开始记录时间和结束记录时间
        var startCreateTime = smartwork.parseDate(formQuerySendLog.find('[name=startCreateTime]').val().trim());
        var endCreateTime = smartwork.parseDate(formQuerySendLog.find('[name=endCreateTime]').val().trim());
        if (smartwork.isEmpty(startCreateTime) || smartwork.isEmpty(endCreateTime)) {
            smartwork.error('记录时间格式错误');
            return;
        }
        if ((endCreateTime.getTime() - startCreateTime.getTime()) > (30 * 24 * 60 * 60 * 1000)) {
            smartwork.error('记录时间相差超过30天');
            return;
        }
        
            
        return true;
    }
    
    //发送日志查询按钮
    btnQuerySendLog.click(function(){
        
        //验证不通过
        if (!validQueryFormFn()) {
            return;
        }
        
        //禁用按钮
        smartwork.disable(btnQuerySendLog, true);
        
        //重设数据
        removeChildLogFn();
        
        smartwork.hide();
        smartwork.gridQuery(gridSendLogObj);
    });
    
    //发送日志重置按钮
    formQuerySendLog.find('button[btnname=reset]').off().on('click', function(){
        smartwork.setFormValue(formQuerySendLog, {});
    });
    
    /////////////////////////////////////////////////////////////////////
    
    //发送返回接收日志-加载表格数据
    var gridSendBackLog = $('div.my-result-sendbacklog');
    gridSendBackLog.datagrid({
        dataSource: {
            cols:[{
                name: 'receiveFileKey'
                ,label: '接收数据'
                ,width: 100
                ,valueType: 'link'
                ,html: true
            }, {
                name: 'tableId'
                ,label: '配置代码'
                ,width: 140
                ,html: true
                ,tip: true
            }, {
                name: 'createTime'
                ,label: '记录时间'
                ,width: 140
                ,valueType: 'datetime'
                ,html: true
                ,tip: true
            }, {
                name: 'receiveResult'
                ,label: '接收结果'
                ,width: 100
                ,valueType: 'receiveResult'
                ,html: true
            }, {
                name: 'logType'
                ,label: '日志类型'
                ,width: 75
                ,valueType: 'logType'
                ,html: true
            }, {
                name: 'errorCode'
                ,label: '错误代码'
                ,width: 75
                ,html: true
                ,tip: true
            }, {
                name: 'errorMsg'
                ,label: '信息'
                ,width: 300
                ,html: true
                ,tip: true
            }, {
                name: 'XXX'
                ,label: '主键'
                ,width: 1000
                ,valueType: 'keyValue'
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                
                var data = {'ifCode': parentIfCode, 'origQueryFileKey': origQueryFileKey};
                data['currentPage'] = params.page;
                data['pageSize'] = params.recPerPage;
                
                return smartwork.gridRemoteConfig({
                            url: '../log/QuerySendBackLogHis.do' //请求地址
                            ,data: data
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            if (1 == smartwork.parseJson(cell, 'config.data.receiveResult')) {
                if (cell.colIndex > 0 && !$cell.hasClass('my-cell-error')) {
                    $cell.addClass('my-cell-error');
                }
            } else {
                if (cell.colIndex > 0 && $cell.hasClass('my-cell-error')) {
                    $cell.removeClass('my-cell-error');
                }
            }
            
            //增加双击时间
            if (cell.rowIndex > 0 && cell.colIndex > 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click: function(event) {
                        event.preventDefault();
                    }                        
                });
            }
        }
        ,valueOperator: {
            'datetime': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.formatDate(dataValue, 'yyyy-MM-dd hh:mm:ss');
                }
            }
            ,'receiveResult': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeReceiveResult, dataValue);
                }
            }
            ,'logType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeLogType, dataValue);
                }
            }
            ,'keyValue': {
                getter: function(dataValue, cell, dataGrid) {
                    
                    var keyValues = [];
                    for (var i = 1; i <= 10; i++) {
                        var key = smartwork.parseJson(cell, 'config.data.key{0}Name'.format(i)); //获取键
                        if (smartwork.isEmpty(key)) {
                            break;
                        }
                        
                        var value = smartwork.parseJson(cell, 'config.data.key{0}Value'.format(i)); //获取值
                        keyValues.push(key + '=' + (smartwork.isEmpty(value) ? '' : value));
                    }
                    
                   return keyValues.join(', ');
                }
            }
            ,'link': {
                getter: function(dataValue, cell, dataGrid) {
                    if (smartwork.isEmpty(dataValue)) {
                        return '';
                    }
                    
                    return '<a href="javascript:void(0)" style="color:inherit;" onclick="smartwork.download(\'../log/DownloadLogFile.do?fileKey={0}\')">{0}</a>'.format(dataValue);
                }
            }
        }
        ,height: 300
        ,checkable: false
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:100}
            ,fixedLeftUntil: 3
        }
        ,onLoad: function() {
            
        }
    });
    
    //获取发送返回接收日志表格对象
    var gridSendBackLogObj = gridSendBackLog.data('zui.datagrid');
    
    //////////////////////////////////////////////
    
    //选择发送日志
    var selectParentLogFn = function(rowIndex, mustQuery) {
    
        //获取点击的数据
        var data = gridSendLogObj.getData()[rowIndex - 1];
        
        //判断
        if (!mustQuery && parentIfCode == data['ifCode'] && origQueryFileKey == data['origQueryFileKey']) {
            return;
        }
        
        //赋值
        parentIfCode = data['ifCode']; 
        origQueryFileKey = data['origQueryFileKey'];
        
        //没有原始查询数据
        if (smartwork.isEmpty(origQueryFileKey)) {
            smartwork.gridRemoveAll(gridSendBackLogObj);
            return;
        }
        
        //查询
        smartwork.hide();
        smartwork.gridQuery(gridSendBackLogObj);//删除数据
    }
    
    //删除发送返回日志
    var removeChildLogFn = function() {
        
        //重置数据
        parentIfCode = origQueryFileKey = null;
        
        //删除数据
        smartwork.gridRemoveAll(gridSendBackLogObj);
    }
});