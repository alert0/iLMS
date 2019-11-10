//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //定义变量
    var ifCode, toSysName, tableId, activeTabGrid;
    var isChangeParam, isChangeGeneralParam;
    isChangeParam = isChangeGeneralParam = false;
    
    //接口配置按钮
    var btnQueryIfConfig = $('.my-query-ifconfig button[btnname=search]');
    var btnExportIfConfig = $('.my-query-ifconfig button[btnname=export]');
    var autoQueryIfConfig = {};
    
    //加载数据-服务类型
    var storeServiceType = null;
    smartwork.loadDataDict('TIMER_SERVICE_TYPE'
    ,function(array){
        storeServiceType = array;
    });

    /////////////////////////////////////////////////////////////////////////////////////
    
    //加载接口配置表格数据
    var gridIfConfig = $('div.my-result-ifconfig');
    gridIfConfig.datagrid({
        dataSource: {
            cols:[{
                name: 'ifCode'
                ,label: '接口代码'
                ,width: 120
                ,html: true
                ,tip: true
            }, {
                name: 'ifName'
                ,label: '接口名称'
                ,width: 200
                ,html: true
                ,tip: true
            }, {
                name: 'toSysName'
                ,label: '对方系统'
                ,width: 75
                ,html: true
                ,tip: true
            }, {
                name: 'serviceType'
                ,label: '服务类型'
                ,width: 75
                ,valueType: ''
                ,html: true
                ,tip: true
            }, {
                name: 'serviceClass'
                ,label: '服务实现类'
                ,width: 300
                ,html: true
                ,tip: true
            }, {
                name: 'dbProc'
                ,label: '存储过程'
                ,width: 200
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                var data = smartwork.getFormValue($('form.my-query-ifconfig'));
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
                    click : function(event){
                        event.preventDefault();
                        selectIfConfigConfirmFn(cell.rowIndex);
                    }
                    ,dblclick : function(event){
                        event.preventDefault();
                        modifyIfConfigFn(cell.rowIndex)
                    }
                });
            }
        }
        ,valueOperator: {
            
        }
        ,height: gridIfConfig.innerHeight() - smartwork.config.pagerHeight
        ,checkable: true
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:100}
            ,fixedLeftUntil: 2
        }
        ,onLoad: function() {
            smartwork.disable(btnQueryIfConfig, false);
        }
    });
    
    //获取接口配置表格对象
    var gridIfConfigObj = gridIfConfig.data('zui.datagrid');
    
    //接口配置新增方法
    var addIfConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-ifconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=serviceType]'), storeServiceType);
        
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
            
            //业务校验
            if ('Other' == values['serviceType'] && smartwork.isEmpty(values['serviceClass'])) {
                smartwork.error('服务实现类不允许为空');
                return;
            }
            
            //提交
            smartwork.ajax({
                url : '../config/AddTimerIfConfig.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                btnQueryIfConfig.click();
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增接口配置');
        smartwork.readonly(dialog.find('[update=false]'), false);
        
        //设置数据
        var defaultData = {
            'serviceType': smartwork.getDictFirstValue(storeServiceType)
        };
        smartwork.setFormValue(form, defaultData);
        
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
    
    //接口配置修改方法
    var modifyIfConfigFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridIfConfigObj.getCheckItems()[0];
        } else { //双击
            data = gridIfConfigObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的接口配置');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-ifconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=serviceType]'), storeServiceType);
        
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
            
            //业务校验
            if ('Other' == values['serviceType'] && smartwork.isEmpty(values['serviceClass'])) {
                smartwork.error('服务实现类不允许为空');
                return;
            }
            
            //提交
            smartwork.ajax({
                url : '../config/UpdateTimerIfConfig.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                var rowIndex = gridIfConfigObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridIfConfigObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridIfConfigObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改接口配置');
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
    
    //接口配置删除
    var deleteIfConfigFn = function() {
        smartwork.hide();
        
        //获取选择数据
        var datas = gridIfConfigObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的接口配置');
            return;
        }
         
        smartwork.confirm('确定要删除选择的接口配置？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'ifCodes': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.ifCode)) {
                    params['ifCodes'].push(item.ifCode);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/DeleteIfConfig.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                btnQueryIfConfig.click();
            });
        });
    }
    
    //复制接口配置
    var copyIfConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-copy-ifconfig');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //获取数据
        var data = gridIfConfigObj.getCheckItems()[0];
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要被复制的数据');
            return;
        }
        
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
            
            //业务校验
            if ('Other' == data['serviceType'] && smartwork.isEmpty(values['newServiceClass'])) {
                smartwork.error('新服务实现类不允许为空');
                return;
            }
            
            //提交
            smartwork.ajax({
                url : '../config/CopyTimerIfConfig.do'
                ,'show-loading': true
                ,data: values
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                btnQueryIfConfig.click();
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('复制接口配置');
        
        //设置数据
        var newData = {
            'srcIfCode': data['ifCode']
            ,'newToSysName': data['toSysName']
            ,'newServiceClass': data['serviceClass']
        };
        smartwork.setFormValue(form, newData);
        
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
    
    //接口配置查询按钮
    btnQueryIfConfig.click(function(){
        
        //禁用按钮
        smartwork.disable(btnQueryIfConfig, true);
        
        //重设数据
        disableIfConfigDetailBtnFn(true);
        removeIfConfigDetailGridFn();
        
        smartwork.hide();
        smartwork.uncheckAll(gridIfConfigObj);
        smartwork.gridQuery(gridIfConfigObj);
    });
    
    //接口配置自动查询
    var queryIfConfigInv = window.setInterval(function(){
        
        //判断满足查询条件
        for (var item in autoQueryIfConfig) {
            if (!autoQueryIfConfig[item]) {
                return;
            }
        }
        
        //自动查询
        window.clearInterval(queryIfConfigInv);
        btnQueryIfConfig.click();
        
    }, 100);
    
    //接口配置-新增按钮
    $('.my-query-ifconfig button[btnname=add]').click(function(){
        addIfConfigFn();
    });
    
    //接口配置-修改按钮
    $('.my-query-ifconfig button[btnname=modify]').click(function(){
        modifyIfConfigFn();
    });
    
    //接口配置-删除按钮
    $('.my-query-ifconfig button[btnname=delete]').click(function(){
        deleteIfConfigFn();
    });
    
    //接口配置-复制按钮
    $('.my-query-ifconfig button[btnname=copy]').click(function(){
        copyIfConfigFn();
    });
    
    //接口配置-导入按钮
    $('.my-query-ifconfig .uploader').uploader(smartwork.uploadConfig({
        url: '../config/ImportIfConfig.do'
    }, function() {
        btnQueryIfConfig.click(); //查询
    }));
    
    //接口配置-导出按钮
    btnExportIfConfig.click(function(){
        
        smartwork.hide();
        smartwork.disable(btnExportIfConfig, true);
        
        //获取选择数据
        var datas = gridIfConfigObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要导出的数据');
            smartwork.disable(btnExportIfConfig, false);
            return;
        }
        
        //获取参数
        var url = '../config/ExportIfConfig.do';
        var params = {'ifCodes': []};
        $.each(datas, function(index, item) {
            if (item && !smartwork.isEmpty(item.ifCode)) {
                params['ifCodes'].push(item.ifCode);
            }
        });
        
        //导出数据
        smartwork.download(url, params);
        
        //最后处理
        smartwork.disable(btnExportIfConfig, false);
    });
    
    
    /////////////////////////////////////////////////////////////////////
    
    //接口参数-提示控件
    var toTipParamElement = $('a[data-target="#tabParam"]').parent();
    
    //接口参数-加载表格数据
    var gridParam = $('div.my-result-param');
    gridParam.datagrid({
        dataSource: {
            cols:[{
                name: 'paramGroup'
                ,label: '参数分组'
                ,width: 165
                ,html: true
                ,tip: true
            }, {
                name: 'paramCode'
                ,label: '参数编码'
                ,width: 190
                ,html: true
                ,tip: true
            }, {
                name: 'paramName'
                ,label: '参数名称'
                ,width: 189
                ,html: true
                ,tip: true
            }, {
                name: 'paramValue'
                ,label: '参数值'
                ,width: 200
                ,html: true
                ,tip: true
            }, {
                name: 'editAble'
                ,label: '是否可编辑'
                ,width: 89
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'paramDesc'
                ,label: '备注'
                ,width: 280
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryParam.do' //请求地址
                            ,data: {'ifCode': ifCode, 'toSysName': toSysName}
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            //增加双击事件
            if (cell.rowIndex > 0 && cell.colIndex > 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click: function(event) {
                        event.preventDefault();
                    }
                    ,dblclick: function(event){
                        event.preventDefault();
                        modifyParamOrGenetalParamFn(cell.rowIndex, gridParamObj, toTipParamElement, '接口参数')
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
        }
        ,height: gridParam.innerHeight()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {pager:{page:1, recPerPage:10000}}
        ,onLoad: function() {
            
        }
    });
    
    //获取接口参数表格对象
    var gridParamObj = gridParam.data('zui.datagrid');
    activeTabGrid = gridParam;
    
    //初始化参数模板
    var initTemplateGridFn = function(dialog) {
        
        //已经初始化，则不处理
        if ('1' == dialog.attr('my-init')) {
            return;
        }
        dialog.attr('my-init', '1');
        
        //定义变量
        var templateParamGroup;
        
        //参数模板分组
        var gridTemplateGroup = dialog.find('.my-result-templategroup');
        gridTemplateGroup.datagrid({
            dataSource: {
                cols:[{
                    name: 'paramGroup'
                    ,label: '参数分组'
                    ,width: 165
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramGroupName'
                    ,label: '分组名称'
                    ,width: 160
                    ,html: true
                    ,tip: true
                }]
                ,array: []
                ,remote: function(params, datagrid) {
                    
                    return smartwork.gridRemoteConfig({
                                url: '../config/QueryParamTemplateGroup.do' //请求地址
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
                            queryTemplateParamFn(cell.rowIndex);
                        }
                    });
                }
            }
            ,valueOperator: {
            }
            ,height: gridTemplateGroup.innerHeight()
            ,checkable: false
            ,checkByClickRow: false
            ,states: {
                pager:{page:1, recPerPage:1000000000}
            }
            ,onLoad: function() {
                
            }
        });
        var gridTemplateGroupObj = gridTemplateGroup.data('zui.datagrid'); //参数模板分组表对象
        
        //参数模板
        var gridTemplateParam = dialog.find('.my-result-templateparam');
        gridTemplateParam.datagrid({
            dataSource: {
                cols:[{
                    name: 'paramCode'
                    ,label: '参数编码'
                    ,width: 190
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramName'
                    ,label: '参数名称'
                    ,width: 189
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramValue'
                    ,label: '默认参数值'
                    ,width: 120
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramDesc'
                    ,label: '备注'
                    ,width: 280
                    ,html: true
                    ,tip: true
                }]
                ,array: []
                ,remote: function(params, datagrid) {
                    
                    return smartwork.gridRemoteConfig({
                                url: '../config/QueryParamTemplate.do' //请求地址
                                ,data: {'paramGroup': templateParamGroup}
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
                            addSelectParamFn(cell.rowIndex);
                        }
                    });
                }
            }
            ,valueOperator: {
            }
            ,height: gridTemplateParam.innerHeight()
            ,checkable: false
            ,checkByClickRow: false
            ,states: {
                pager:{page:1, recPerPage:1000000000}
            }
            ,onLoad: function() {
                
            }
        });
        var gridTemplateParamObj = gridTemplateParam.data('zui.datagrid'); //参数模板表格对象
        
        //已选择参数
        var gridSelectParam = dialog.find('.my-result-selectparam');
        gridSelectParam.datagrid({
            dataSource: {
                cols:[{
                    name: 'paramGroup'
                    ,label: '参数分组'
                    ,width: 120
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramCode'
                    ,label: '参数编码'
                    ,width: 190
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramName'
                    ,label: '参数名称'
                    ,width: 189
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramValue'
                    ,label: '默认参数值'
                    ,width: 120
                    ,html: true
                    ,tip: true
                }, {
                    name: 'paramDesc'
                    ,label: '备注'
                    ,width: 280
                    ,html: true
                    ,tip: true
                }]
                ,array: []
                ,remote: function(params, datagrid) {
                    return 'none';
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
                            deleteSelectParamFn(cell.rowIndex);
                        }
                    });
                }
            }
            ,valueOperator: {
            }
            ,height: gridSelectParam.innerHeight()
            ,checkable: false
            ,checkByClickRow: false
            ,states: {
                pager:{page:1, recPerPage:1000000000}
            }
            ,onLoad: function() {
                
            }
        });
        var gridSelectParamObj = gridSelectParam.data('zui.datagrid'); //选择参数表格对象
        
        //查询参数模板
        var queryTemplateParamFn = function(rowIndex) {
            
            //获取选择参数数据
            var data = gridTemplateGroupObj.getData()[rowIndex - 1];
            if (templateParamGroup == data['paramGroup']) {
                return;
            }
            templateParamGroup = data['paramGroup'];
           
            //查询
            smartwork.hide();
            //smartwork.uncheckAll(gridTemplateParamObj);
            smartwork.gridQuery(gridTemplateParamObj);
        }
        
        //增加选择参数
        var addSelectParamFn = function(rowIndex) {
            
            //获取选择参数数据
            var data = gridTemplateParamObj.getData()[rowIndex - 1];
            
            //复制生成新数据
            var newData = $.extend({'paramGroup': templateParamGroup, 'editAble': 1}, data);
            
            //根据参数编码增加或修改表格数据
            var gridDatas = gridSelectParamObj.getData();
            var index = smartwork.indexOf(gridDatas, 'paramCode', newData);
            if (index < 0) {
                smartwork.gridAdd(gridSelectParamObj, newData); //增加到表格
            } else {
                $.extend(true, gridDatas[index], newData);
                gridSelectParamObj.renderRow(index + 1);
                smartwork.gridModifyLayout(gridSelectParamObj);
            }
        }
        
        //删除选择参数
        var deleteSelectParamFn = function(rowIndex) {
            
            //获取选择参数数据
            var data = gridSelectParamObj.getData()[rowIndex - 1];
            
            //删除选择数据
            smartwork.gridRemove(gridSelectParamObj, data);
        }
        
        //自动查询参数分组
        smartwork.gridQuery(gridTemplateGroupObj);
    }
 
    //接口参数/通用参数-参数模板方法
    var addParamOrGenetalParamFromTemplateFn = function(gridObj, tipElement, title) {
        
        //获取控件
        var dialog = $('#my-dialog-config-templateparam');
        
        //初始化
        initTemplateGridFn(dialog);
        
        //获取表格元素
        var gridSelectParam = dialog.find('.my-result-selectparam');
        
        //提交
        var submitFn = function() {
            
            //获取选择参数数据
            var newDatas = gridSelectParam.data('zui.datagrid').getData();
            if (newDatas.length <= 0) {
                dialog.modal('hide'); //隐藏
                return;
            }
            
            //正在操作中
            smartwork.loading();
            
            //获取表格数据
            var gridDatas = gridObj.getData();
            
            //隐藏
            dialog.modal('hide');
            
            //增加或修改数据
            $.each(newDatas, function(i, item){
                var index = smartwork.indexOf(gridDatas, 'paramCode', item);
                if (index < 0) {
                    smartwork.gridAdd(gridObj, item, true); //增加到表格
                } else {
                    delete item['paramValue']; //
                    $.extend(true, gridDatas[index], item);
                }
            });
            
            //显示所有数据
            gridObj.renderData(); 
            
            //增加提示
            smartwork.elementTip(tipElement, true, '数据有变动，注意保存');
            if (gridObj == gridParamObj) {
                isChangeParam = true;
            } else {
                isChangeGeneralParam = true;
            }
            
            //删除表格数据
            smartwork.gridRemoveAll(gridSelectParam.data('zui.datagrid'));
            
            //隐藏操作中
            smartwork.hideLoading();
        }
        
        //设置标题
        dialog.find('.modal-title').text('{0}模板【需点击保存才提交数据】'.format(title));
        
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
    
    //接口参数/通用参数-新增方法
    var addParamOrGenetalParamFn = function(gridObj, tipElement, title) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-config-param');
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
            
            //根据参数代码更新或新增
            var gridDatas = gridObj.getData();
            var index = smartwork.indexOf(gridDatas, 'paramCode', values);
            if (index < 0) {
                smartwork.gridAdd(gridObj, values); //增加到表格
            } else {
                $.extend(true, gridDatas[index], values);
                gridObj.renderRow(index + 1);
                smartwork.gridModifyLayout(gridObj);
            }
            
            //增加提示
            smartwork.elementTip(tipElement, true, '数据有变动，注意保存');
            if (gridObj == gridParamObj) {
                isChangeParam = true;
            } else {
                isChangeGeneralParam = true;
            }
            
            //重置
            smartwork.setFormValue(form, defaultData);
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增{0}【需点击保存才提交数据】'.format(title));
        smartwork.disable(dialog.find('[update=false]'), false);
        
        //设置默认值
        var defaultData = {
            'editAble': '1'
            ,'paramGroup': 'Other'
        };
        smartwork.setFormValue(form, defaultData);
        
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
    
    //接口参数/通用参数-修改方法
    var modifyParamOrGenetalParamFn = function(rowIndex, gridObj, tipElement, title) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridObj.getCheckItems()[0];
        } else { //双击
            data = gridObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的{0}'.format(title));
            return;
        }
        
        //判断不可编辑
        if (!smartwork.isEmpty(data['pkId']) && '0' == data['editAble']) {
            smartwork.error('数据不可编辑');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-config-param');
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
            
            //重新显示数据
            dialog.modal('hide');
            var rowIndex = gridObj.getData().indexOf(data);
            if (rowIndex >= 0) {
                $.extend(true, data, values);
                gridObj.renderRow(rowIndex + 1);
                smartwork.gridModifyLayout(gridObj);
            }
            
            //增加提示
            smartwork.elementTip(tipElement, true, '数据有变动，注意保存');
            if (gridObj == gridParamObj) {
                isChangeParam = true;
            } else {
                isChangeGeneralParam = true;
            }
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改{0}【需点击保存才提交数据】'.format(title));
        smartwork.disable(dialog.find('[update=false]'), true);
        
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
    
    //接口参数/通用参数-删除方法
    var deleteParamOrGenetalParamFn = function(gridObj, tipElement, title) {
        
        smartwork.hide();
        
        //获取选择数据
        var datas = gridObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的{0}'.format(title));
            return;
        }
         
        smartwork.confirm('确定要删除选择的{0}【需点击保存才提交数据】？'.format(title), function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //删除界面数据
            smartwork.uncheckAll(gridObj);
            smartwork.gridRemove(gridObj, datas);
            
            //增加提示
            smartwork.elementTip(tipElement, true, '数据有变动，注意保存');
            if (gridObj == gridParamObj) {
                isChangeParam = true;
            } else {
                isChangeGeneralParam = true;
            }
        });
    }
    
    //接口参数/通用参数-保存方法
    var saveParamOrGenetalParamFn = function(gridObj, tipElement, title, action) {
        
        var values = {'ifCode': ifCode, 'toSysName': toSysName};
        
        //遍历数据
        var gridDatas = gridObj.getData();
        var prefix = null;
        for (var i = 0; i < gridDatas.length; i++) {
            prefix = 'beans[{0}].'.format(i);
            values[prefix + 'paramGroup'] = gridDatas[i]['paramGroup'];
            values[prefix + 'paramCode'] = gridDatas[i]['paramCode'];
            values[prefix + 'paramName'] = gridDatas[i]['paramName'];
            values[prefix + 'paramValue'] = gridDatas[i]['paramValue'];
            values[prefix + 'paramDesc'] = gridDatas[i]['paramDesc'];
            values[prefix + 'editAble'] = gridDatas[i]['editAble'];
        }
        
        //提交
        smartwork.ajax({
            url : action
            ,'show-loading': true
            ,data: values
        }, function(result){
            smartwork.info('提交成功');
            
            //删除提示
            smartwork.elementTip(tipElement, false);
            if (gridObj == gridParamObj) {
                isChangeParam = false;
            } else {
                isChangeGeneralParam = false;
            }
            
            //从后台查询数据
            smartwork.hide();
            smartwork.uncheckAll(gridObj);
            smartwork.gridQuery(gridObj);
        });
    }
    
    //接口参数-新增按钮
    $('.my-query-param button[btnname=template]').click(function(){
        addParamOrGenetalParamFromTemplateFn(gridParamObj, toTipParamElement, '接口参数');
    });
    
    //接口参数-新增按钮
    $('.my-query-param button[btnname=add]').click(function(){
        addParamOrGenetalParamFn(gridParamObj, toTipParamElement, '接口参数');
    });
    
    //接口参数-修改按钮
    $('.my-query-param button[btnname=modify]').click(function(){
        modifyParamOrGenetalParamFn(null, gridParamObj, toTipParamElement, '接口参数');
    });
    
    //接口参数-删除按钮
    $('.my-query-param button[btnname=delete]').click(function(){
        deleteParamOrGenetalParamFn(gridParamObj, toTipParamElement, '接口参数');
    });
    
    //接口参数-保存按钮
    $('.my-query-param button[btnname=save]').click(function(){
        saveParamOrGenetalParamFn(gridParamObj, toTipParamElement, '接口参数', '../config/SaveParam.do');
    });
    
    //////////////////////////////////////////////////////////////////////////
    
    //通用参数-提示控件
    var toTipGeneralParamElement = $('a[data-target="#tabGeneralParam"]').parent();
    
    //通用参数-加载表格数据
    var gridGeneralParam = $('div.my-result-generalparam');
    gridGeneralParam.datagrid({
        dataSource: {
            cols:[{
                name: 'paramGroup'
                ,label: '参数分组'
                ,width: 165
                ,html: true
                ,tip: true
            }, {
                name: 'paramCode'
                ,label: '参数编码'
                ,width: 190
                ,html: true
                ,tip: true
            }, {
                name: 'paramName'
                ,label: '参数名称'
                ,width: 189
                ,html: true
                ,tip: true
            }, {
                name: 'paramValue'
                ,label: '参数值'
                ,width: 200
                ,html: true
                ,tip: true
            }, {
                name: 'editAble'
                ,label: '是否可编辑'
                ,width: 89
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'paramDesc'
                ,label: '备注'
                ,width: 280
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryGeneralParam.do' //请求地址
                            ,data: {'toSysName': toSysName}
                        });
            }
            ,remoteConverter: function(responseData, textStatus, jqXHR, datagrid) {
                return smartwork.gridRemoteConverter(responseData, textStatus, jqXHR, datagrid);
            }
        }
        ,cellFormator: function($cell, cell, dataGrid) {
            smartwork.gridCellFormator($cell, cell, dataGrid);
            
            //增加双击事件
            if (cell.rowIndex > 0 && cell.colIndex > 0 && '1' != $cell.attr('my-click')) {
                $cell.attr('my-click', '1');
                $cell.on({
                    click: function(event) {
                        event.preventDefault();
                    }
                    ,dblclick: function(event){
                        event.preventDefault();
                        modifyParamOrGenetalParamFn(cell.rowIndex, gridGeneralParamObj, toTipGeneralParamElement, '通用参数')
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
        }
        ,height: gridGeneralParam.innerHeight()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {pager:{page:1, recPerPage:10000}}
        ,onLoad: function() {
            
        }
    });
    
    //获取接口参数表格对象
    var gridGeneralParamObj = gridGeneralParam.data('zui.datagrid');
    
    //通用参数-新增按钮
    $('.my-query-generalparam button[btnname=template]').click(function(){
        addParamOrGenetalParamFromTemplateFn(gridGeneralParamObj, toTipGeneralParamElement, '通用参数');
    });
    
    //通用参数-新增按钮
    $('.my-query-generalparam button[btnname=add]').click(function(){
        addParamOrGenetalParamFn(gridGeneralParamObj, toTipGeneralParamElement, '通用参数');
    });
    
    //通用参数-修改按钮
    $('.my-query-generalparam button[btnname=modify]').click(function(){
        modifyParamOrGenetalParamFn(null, gridGeneralParamObj, toTipGeneralParamElement, '通用参数')
    });
    
    //通用参数-删除按钮
    $('.my-query-generalparam button[btnname=delete]').click(function(){
        deleteParamOrGenetalParamFn(gridGeneralParamObj, toTipGeneralParamElement, '通用参数');
    });
    
    //通用参数-保存按钮
    $('.my-query-generalparam button[btnname=save]').click(function(){
        saveParamOrGenetalParamFn(gridGeneralParamObj, toTipGeneralParamElement, '通用参数', '../config/SaveGeneralParam.do');
    });
    
    ////////////////////////////////////////////////////
    
    //禁用配置从表按钮（发送配置、接收配置、通用参数、接口参数）
    var disableIfConfigDetailBtnFn = function(disable) {
        smartwork.disable($('.my-query-param button[btnname]'), disable);
        smartwork.disable($('.my-query-generalparam button[btnname]'), disable);
    }
    
    //选中接口配置-确定事件
    var selectIfConfigConfirmFn = function(rowIndex) {
        
        //获取点击的数据
        var data = gridIfConfigObj.getData()[rowIndex - 1];
        
        //接口代码已处理
        if (ifCode == data['ifCode']) {
            return;
        }
        
        if (isChangeParam || isChangeGeneralParam) {
            smartwork.confirm('确定要放弃未保存的数据？', function(action){ 
                
                //不是确定不处理
                if ('ok' != action) {
                    smartwork.gridRollbackHighlightSelect(gridIfConfigObj);
                    return;
                }
            
                //先删除，再查询
                removeIfConfigDetailGridFn();
                selectIfConfigFn(data, rowIndex);
            });
        } else {
            
            //先删除，再查询
            removeIfConfigDetailGridFn();
            selectIfConfigFn(data, rowIndex);
        }
    }
    
    //选中接口配置
    var selectIfConfigFn = function(data, rowIndex) {
        
        //获取接口代码和对方系统
        ifCode = data['ifCode'];
        toSysName = data['toSysName'];
        
        //设置发送配置、接收配置、接口参数、通用参数的提示信息
        smartwork.setFormValue($('form[my-ref=config]'), {'ifCode': ifCode, 'toSysName': toSysName});
        
        //启用发送配置、接收配置、接口参数、通用参数的按钮
        disableIfConfigDetailBtnFn(false);
        
        //查询激活标签的表格数据
        queryActiveTabGridFn();
    }
    
    //删除发送配置、接收配置、接口参数、通用参数的数据
    var removeIfConfigDetailGridFn = function() {
        
        //重置全局数据
        ifCode = toSysName = null;
        
        //删除数据
        smartwork.gridRemoveAll(gridParamObj);
        smartwork.gridRemoveAll(gridGeneralParamObj);
        
        //删除提示数据
        smartwork.setFormValue($('form[my-ref=config]'), {});
        
        //去除提示
        smartwork.elementTip(toTipParamElement, false);
        smartwork.elementTip(toTipGeneralParamElement, false);
        isChangeParam = false;
        isChangeGeneralParam = false;
        
        //删除已查询标记
        gridParam.data('ifCode', '');
        gridGeneralParam.data('ifCode', '');
    }
    
    //查询激活标签的表格数据
    var queryActiveTabGridFn = function() {
        
        //获取表对象
        var grid = activeTabGrid;
        var gridObj = grid.data('zui.datagrid');
        if (!gridObj) {
            return;
        }
            
        //没有选定数据或数据已查询则不处理
        if (!ifCode || grid.data('ifCode') == ifCode) {
            return;
        }
        
        //去除提示
        if (gridObj == gridParamObj) {
            smartwork.elementTip(toTipParamElement, false);
            isChangeParam = false;
        } else if (gridObj == gridGeneralParamObj) {
            smartwork.elementTip(toTipGeneralParamElement, false);
            isChangeGeneralParam = false;
        }
        
        //查询
        smartwork.hide();
        smartwork.uncheckAll(gridObj);
        smartwork.gridQuery(gridObj);
        
        //设置已查询的标记
        grid.data('ifCode', ifCode);
        grid.data('tableId', tableId);
    }
    
    //初始化时禁用所有从表按钮
    disableIfConfigDetailBtnFn(true);
    
    //标签改变事件
    $('.nav-tabs').on('show.zui.tab', function(e) {
        
        //获取激活标签对应的表格
        var tabPanel = $($(e.target).data('target'));
        activeTabGrid = tabPanel.find('div.datagrid:first');
        
        //查询激活标签的表格数据
        queryActiveTabGridFn();
    });
    
    //提示
    $('label[my-tip-title]').each(function(){
        var that = $(this);
        var title = that.attr('my-tip-title');
        var message = null;
        
        //设置消息MAX-NUMBER
        if ('EN' == title) {
            message = '由英文、数字、下划线或-组成';
        } else if ('REGEX' == title) {
            message = '<p>1.普通字符串相等</p><p>2.正则表达式。如/^[a-z]$/i</p>';
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