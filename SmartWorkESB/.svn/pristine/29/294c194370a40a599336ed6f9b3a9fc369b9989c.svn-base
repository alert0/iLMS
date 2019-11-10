//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //定时日志按钮
    var formQueryTimeLog = $('.my-query-timelog');
    var btnQueryTimeLog = formQueryTimeLog.find('button[btnname=search]');
    
    //加载数据-日志类型
    var storeLogType = null;
    smartwork.loadDataDict('LOG_LOG_TYPE'
    ,function(array){
        storeLogType = array;
    });
    
    //加载数据-处理结果
    var storeSendResult = null;
    smartwork.loadDataDict('LOG_SEND_RESULT'
    ,function(array){
        storeSendResult = array;
        var cbo = formQueryTimeLog.find('[name=sendResult]');
        smartwork.initCombox(cbo, storeSendResult, {show_text:true});
        cbo.parent().css('display', '');
    });

    /////////////////////////////////////////////////////////////////////////////////////
    
    //获取高度
    var getHeightFn = function() {
        return $(window).height() - $('.my-query-timelog').outerHeight() - smartwork.config.pagerHeight
    }
    
    //初始化时间控件
    smartwork.initDateTime(formQueryTimeLog.find('[name=startCreateTime]'));
    //smartwork.initDateTime(formQueryTimeLog.find('[name=endCreateTime]'));
    
    //加载定时日志表格数据
    var gridTimeLog = $('div.my-result-timelog');
    gridTimeLog.datagrid({
        dataSource: {
            cols:[{
                name: 'ifCode'
                ,label: '接口名称'
                ,width: 200
                ,valueType: 'ifCode'
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
                ,label: '处理结果'
                ,width: 75
                ,valueType: 'sendResult'
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
                ,width: 500
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                var data = smartwork.getFormValue(formQueryTimeLog);
                data['currentPage'] = params.page;
                data['pageSize'] = params.recPerPage;
                
                return smartwork.gridRemoteConfig({
                            url: '../log/QueryTimerLogHis.do' //请求地址
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
            ,'logType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeLogType, dataValue);
                }
            }
        }
        ,height: getHeightFn()
        ,checkable: false
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:100}
            ,fixedLeftUntil: 2
        }
        ,onLoad: function() {
            smartwork.disable(btnQueryTimeLog, false);
        }
    });
    
    //获取定时日志表格对象
    var gridTimeLogObj = gridTimeLog.data('zui.datagrid');
    
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
                                url: '../config/QueryTimerIfConfig.do' //请求地址
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
    formQueryTimeLog.find('button[btnname=selectIfCode]').off().on('click', function(){
        selectIfCodeFn(formQueryTimeLog);
    });
    
    //增加form验证
    smartwork.initValidate(formQueryTimeLog);
        
    //验证查询form
    var validQueryFormFn = function() {
        
        //隐藏提示框
        smartwork.hide();
        
        //检验表单
        if(!formQueryTimeLog.valid()) {
            return;
        }
        
        //获取开始记录时间和结束记录时间
        var startCreateTime = smartwork.parseDate(formQueryTimeLog.find('[name=startCreateTime]').val().trim());
        var endCreateTime = smartwork.parseDate(formQueryTimeLog.find('[name=endCreateTime]').val().trim());
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
    
    //定时日志查询按钮
    btnQueryTimeLog.click(function(){
        
        //验证不通过
        if (!validQueryFormFn()) {
            return;
        }
        
        //禁用按钮
        smartwork.disable(btnQueryTimeLog, true);
        
        smartwork.hide();
        smartwork.gridQuery(gridTimeLogObj);
    });
    
    //定时日志重置按钮
    formQueryTimeLog.find('button[btnname=reset]').off().on('click', function(){
        smartwork.setFormValue(formQueryTimeLog, {});
    });
    
    //高宽变动
    gridTimeLog.on('resize', function() {
        gridTimeLogObj.options.height = getHeightFn();
        gridTimeLogObj.updateLayout();
    });
});