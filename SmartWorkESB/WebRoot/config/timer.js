//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //计划任务按钮
    var btnQueryTimer = $('.my-query-timer button[btnname=search]');
    var btnExportTimer = $('.my-query-timer button[btnname=export]');
    var autoQueryTimer = {JobClass: false};
    var isSendService, isTimerService, selectIfCode;
    
    //加载数据-任务类名
    var storeJobClass = null;
    smartwork.loadDataDict('JOB_CLASS'
    ,function(array){
        storeJobClass = array;
        autoQueryTimer.JobClass = true;
    }, function(){
        autoQueryTimer.JobClass = true;
    });
    
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    //加载计划任务表格数据
    var gridTimer = $('div.my-result-timer');
    
    //获取高度
    var getHeightFn = function() {
        return $(window).height() - $('.my-query-timer').outerHeight() - smartwork.config.pagerHeight
    }
    
    gridTimer.datagrid({
        dataSource: {
            cols:[{
                name: 'jobCode'
                ,label: '任务代码'
                ,width: 140
                ,html: true
                ,tip: true
            }, {
                name: 'jobDesc'
                ,label: '任务名称'
                ,width: 230
                ,html: true
                ,tip: true
            }, {
                name: 'runState'
                ,label: '是否启用'
                ,width: 70
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'updateState'
                ,label: '正在更新'
                ,width: 70
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'jobClass'
                ,label: '任务类名'
                ,width: 100
                ,valueType: 'jobClass'
                ,html: true
                ,tip: true
            }, {
                name: 'ifCode'
                ,label: '接口名称'
                ,width: 200
                ,valueType: 'ifCode'
                ,html: true
                ,tip: true
            }, {
                name: 'masterQueryTableId'
                ,label: '发送主配置'
                ,width: 150
                ,html: true
                ,tip: true
            }, {
                name: 'jobGroup'
                ,label: '任务分组'
                ,width: 120
                ,html: true
                ,tip: true
            }, {
                name: 'triggerValue'
                ,label: '表达式'
                ,width: 150
                ,html: true
                ,tip: true
            }, {
                name: 'startTime'
                ,label: '启用时间'
                ,width: 140
                ,html: true
                ,valueType: 'datetime'
                ,tip: true
            }, {
                name: 'lastRunTime'
                ,label: '上次运行时间'
                ,width: 140
                ,valueType: 'datetime'
                ,html: true
                ,tip: true
            }, {
                name: 'nextRunTime'
                ,label: '计划运行时间'
                ,width: 140
                ,valueType: 'datetime'
                ,html: true
                ,tip: true
            }, {
                name: 'threadState'
                ,label: '多线程正在运行'
                ,width: 110
                ,valueType: 'yes-no'
                ,html: true
                ,tip: true
            }, {
                name: 'threadRunTime'
                ,label: '多线程开始运行时间'
                ,width: 140
                ,valueType: 'datetime'
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                var data = smartwork.getFormValue($('form.my-query-timer'));
                data['currentPage'] = params.page;
                data['pageSize'] = params.recPerPage;
                
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryTimer.do' //请求地址
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
                        modifyTimerFn(cell.rowIndex)
                    }
                });
            }
        }
        ,valueOperator: {
            'yes-no': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.gridYesNoCellGetter(dataValue, cell, dataGrid);
                }
            }
            ,'jobClass': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeJobClass, dataValue);
                }
            }
            ,'ifCode': {
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
        }
        ,height: getHeightFn()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:100}
            ,fixedLeftUntil: 2
        }
        ,onLoad: function() {
            smartwork.disable(btnQueryTimer, false);
        }
    });
    
    //获取计划任务表格对象
    var gridTimerObj = gridTimer.data('zui.datagrid');
    
    //初始化选择接口弹窗
    var initSelectIfConfigDialogFn = function(dialog, callbackFn) {
        
        //获取控件
        if ('1' == dialog.attr('my-init')) {
            dialog.find('button[btnname=search]').click(); //自动查询
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
                    data['isSendService'] = isSendService ? '1' : '0';
                    data['isTimerService'] = isTimerService ? '1' : '0';
                    
                    return smartwork.gridRemoteConfig({
                                url: '../config/QueryDictIfConfig.do' //请求地址
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
        
        //判断任务类名
        var jobClass = form.find('[name=jobClass]').val();
        isSendService = 'com.smartwork.esb.job.ClientSenderJob' == jobClass;
        isTimerService = 'com.smartwork.esb.job.TimerJob' == jobClass;
        
        //初始化弹窗
        initSelectIfConfigDialogFn(dialog, function(data) {
            form.find('[name=ifCode]').val(data['ifCode']);
            form.find('[name=ifName]').val(data['ifName']);
        });
        
        //设置标题
        dialog.find('.modal-title').text('选择接口代码【双击选择】');
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    }
    
    //初始化选择发送主表配置弹窗
    var initSelectMasterQueryTableIdDialogFn = function(dialog, callbackFn) {
        
        //获取控件
        if ('1' == dialog.attr('my-init')) {
            return;
        }
        dialog.attr('my-init', '1');
        
        //加载发送配置表格数据
        var gridQueryConfig = dialog.find('div.my-result-select-queryconfig');
        gridQueryConfig.datagrid({
            dataSource: {
                cols:[{
                    name: 'tableId'
                    ,label: '配置代码'
                    ,width: 230
                    ,html: true
                    ,tip: true
                }, {
                    name: 'tableDesc'
                    ,label: '配置名称'
                    ,width: 410
                    ,html: true
                    ,tip: true
                }]
                ,array: []
                ,remote: function(params, datagrid) {
                    var data = {'ifCode': selectIfCode};
                    
                    return smartwork.gridRemoteConfig({
                                url: '../config/QueryMasterQueryConfig.do' //请求地址
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
                    });
                }
            }
            ,valueOperator: {
                
            }
            ,height: 300
            ,checkable: true
            ,checkByClickRow: false
            ,states: {
                pager:{page:1, recPerPage:10000}
            }
            ,onLoad: function() {
                
            }
        });
        
        //获取接口配置表格对象
        var gridQueryConfigObj = gridQueryConfig.data('zui.datagrid');
        
        //配置确定按钮
        dialog.find('button[btnname=ok]').off().on('click', function(){
            var checkDatas = gridQueryConfigObj.getCheckItems();
            smartwork.uncheckAll(gridQueryConfigObj);
            
            var tableIds = [];
            $.each(checkDatas, function(index, item){
                tableIds.push(item['tableId']);
            });
            
            //隐藏
            dialog.modal('hide');
            
            //回调
            callbackFn(tableIds);
        });
        
        //配置取消按钮
        dialog.find('button[btnname=cancel]').off().on('click', function(){
            dialog.modal('hide');
        });
    }
    
    //选择发送主配置
    var selectMasterQueryTableIdFn = function(form) {
        
        var dialog = $('#my-dialog-select-queryconfig');
        
        //获取选择接口代码
        selectIfCode = form.find('[name=ifCode]').val();
        
        //初始化弹窗
        initSelectMasterQueryTableIdDialogFn(dialog, function(tableIds) {
            form.find('[name=masterQueryTableId]').val(tableIds.join(';'));
        });
        
        //设置标题
        dialog.find('.modal-title').text('选择发送主配置');
        
        //自动查询
        smartwork.gridQuery(dialog.find('div.my-result-select-queryconfig').data('zui.datagrid')); 
        
        //显示
        dialog.modal({'show': true, backdrop:'static'});
    }
    
    //计划任务新增方法
    var addTimerFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-timer');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=jobClass]'), storeJobClass, {disable_search: false, use_query: true, column:3});
        smartwork.initDateTime(dialog.find('[name=startTime]'));
        
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
            values['triggerName'] = values['jobCode'];
            values['updateState'] = '1';
            values['nextRunTime'] = null;
            
            //提交
            smartwork.ajax({
                url : '../config/AddTimer.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                defaultData['jobClass'] = values['jobClass'];
                smartwork.setFormValue(form, defaultData);
                smartwork.info('提交成功');
                
                //重新显示数据
                btnQueryTimer.click();
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增计划任务');
        smartwork.readonly(dialog.find('[update=false]'), false);
        
        //设置数据
        var defaultData = {
            'runState': '1'
            ,'jobClass': 'com.smartwork.esb.job.ClientSenderJob'
            ,'triggerValue': '0 0/1 * * * ?'
        };
        smartwork.setFormValue(form, defaultData);
        
        //配置选择接口按钮
        dialog.find('button[btnname=selectIfCode]').off().on('click', function(){
            selectIfCodeFn(form);
        });
        
        //配置选择发送主配置按钮
        dialog.find('button[btnname=selectMasterQueryTableId]').off().on('click', function(){
            selectMasterQueryTableIdFn(form);
        });
        
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
    
    //计划任务修改方法
    var modifyTimerFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridTimerObj.getCheckItems()[0];
        } else { //双击
            data = gridTimerObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的计划任务');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-timer');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=jobClass]'), storeJobClass, {disable_search: false, use_query: true, column:3});
        smartwork.initDateTime(dialog.find('[name=startTime]'));
        
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
            values['triggerName'] = values['jobCode'];
            values['updateState'] = '1';
            values['nextRunTime'] = null;
            
            //提交
            smartwork.ajax({
                url : '../config/UpdateTimer.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                var rowIndex = gridTimerObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridTimerObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridTimerObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改计划任务');
        smartwork.readonly(dialog.find('[update=false]'), true);
        
        //设置数据
        smartwork.setFormValue(form, data);
        
        //配置选择接口按钮
        dialog.find('button[btnname=selectIfCode]').off().on('click', function(){
            selectIfCodeFn(form);
        });
        
        //配置选择发送主配置按钮
        dialog.find('button[btnname=selectMasterQueryTableId]').off().on('click', function(){
            selectMasterQueryTableIdFn(form);
        });
        
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
    
    //计划任务删除
    var deleteTimerFn = function() {
        smartwork.hide();
        
        //获取选择数据
        var datas = gridTimerObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的计划任务');
            return;
        }
         
        smartwork.confirm('确定要删除选择的计划任务？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'jobCodes': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.jobCode)) {
                    params['jobCodes'].push(item.jobCode);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/DeleteTimer.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                btnQueryTimer.click();
            });
        });
    }
    
    //计划任务启用
    var enableTimerFn = function() {
        smartwork.hide();
        
        //获取选择数据
        var datas = gridTimerObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要启用的计划任务');
            return;
        }
         
        smartwork.confirm('确定要启用选择的计划任务？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'jobCodes': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.jobCode)) {
                    params['jobCodes'].push(item.jobCode);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/EnableTimer.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                btnQueryTimer.click();
            });
        });
    }
    
    //计划任务禁用
    var disableTimerFn = function() {
        smartwork.hide();
        
        //获取选择数据
        var datas = gridTimerObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要禁用的计划任务');
            return;
        }
         
        smartwork.confirm('确定要禁用选择的计划任务？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'jobCodes': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.jobCode)) {
                    params['jobCodes'].push(item.jobCode);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/DisableTimer.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                btnQueryTimer.click();
            });
        });
    }
    
    //计划任务查询按钮
    btnQueryTimer.click(function(){
        
        //禁用按钮
        smartwork.disable(btnQueryTimer, true);
        
        smartwork.hide();
        smartwork.uncheckAll(gridTimerObj);
        smartwork.gridQuery(gridTimerObj);
    });
    
    //计划任务自动查询
    var queryTimerInv = window.setInterval(function(){
        
        //判断满足查询条件
        for (var item in autoQueryTimer) {
            if (!autoQueryTimer[item]) {
                return;
            }
        }
        
        //自动查询
        window.clearInterval(queryTimerInv);
        btnQueryTimer.click();
        
    }, 100);
    
    //计划任务-新增按钮
    $('.my-query-timer button[btnname=add]').click(function(){
        addTimerFn();
    });
    
    //计划任务-修改按钮
    $('.my-query-timer button[btnname=modify]').click(function(){
        modifyTimerFn();
    });
    
    //计划任务-删除按钮
    $('.my-query-timer button[btnname=delete]').click(function(){
        deleteTimerFn();
    });
    
    //计划任务-启用按钮
    $('.my-query-timer button[btnname=enable]').click(function(){
        enableTimerFn();
    });
    
    //计划任务-禁用按钮
    $('.my-query-timer button[btnname=disable]').click(function(){
        disableTimerFn();
    });
    
    //计划任务-导入按钮
    $('.my-query-timer .uploader').uploader(smartwork.uploadConfig({
        url: '../config/ImportTimer.do'
    }, function() {
        btnQueryTimer.click(); //查询
    }));
    
    //计划任务-导出按钮
    btnExportTimer.click(function(){
        
        smartwork.hide();
        smartwork.disable(btnExportTimer, true);
        
        //获取选择数据
        var datas = gridTimerObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要导出的数据');
            smartwork.disable(btnExportTimer, false);
            return;
        }
        
        //获取参数
        var url = '../config/ExportTimer.do';
        var params = {'jobCodes': []};
        $.each(datas, function(index, item) {
            if (item && !smartwork.isEmpty(item.jobCode)) {
                params['jobCodes'].push(item.jobCode);
            }
        });
        
        //导出数据
        smartwork.download(url, params);
        
        //最后处理
        smartwork.disable(btnExportTimer, false);
    });

    ///////////////////////////////////////////////////////////////////
    
    //高宽变动
    gridTimer.on('resize', function() {
        gridTimerObj.options.height = getHeightFn();
        gridTimerObj.updateLayout();
    });
    
    //提示
    $('label[my-tip-title]').each(function(){
        var that = $(this);
        var title = that.attr('my-tip-title');
        var message = null;
        
        //设置消息MAX-NUMBER
        if ('EN' == title) {
            message = '由英文、数字、下划线或-组成';
        } else if ('TRIGGER-VALUE' == title) {
            message = '组成：秒 分 时 日 月 周。详细配置请百度quartz表达式';
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