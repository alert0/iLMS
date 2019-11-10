//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //定义变量
    var ifCode, toSysName, tableId, activeTabGrid;
    var isChangeParam, isChangeGeneralParam, isChangeElement;
    isChangeParam = isChangeGeneralParam = isChangeElement = false;
    
    //接口配置按钮
    var btnQueryIfConfig = $('.my-query-ifconfig button[btnname=search]');
    var btnExportIfConfig = $('.my-query-ifconfig button[btnname=export]');
    var autoQueryIfConfig = {ReceiveTransationType: false};
    
    //加载数据-接收事务类型
    var storeReceiveTransationType = null;
    smartwork.loadDataDict('RECEIVE_TRANSATION_TYPE'
    ,function(array){
        storeReceiveTransationType = array;
        autoQueryIfConfig.ReceiveTransationType = true;
    }, function(){
        autoQueryIfConfig.ReceiveTransationType = true;
    });
    
    //加载数据-服务类型
    var storeServiceType = null;
    smartwork.loadDataDict('RECEIVE_SERVICE_TYPE'
    ,function(array){
        storeServiceType = array;
    });
    
    //加载数据-接收表类型
    var storeReceiveTableType = null;
    smartwork.loadDataDict('RECEIVE_TABLE_TYPE'
    ,function(array){
        storeReceiveTableType = array;
    });
    
    //加载数据-接收表序列
    var storeReceiveKeySequence = null;
    smartwork.loadDataDict('RECEIVE_KEY_SEQUENCE'
    ,function(array){
        storeReceiveKeySequence = array;
    });
    
    //加载数据-接收更新数据类型
    var storeReceiveUpdateType = null;
    smartwork.loadDataDict('RECEIVE_UPDATE_TYPE'
    ,function(array){
        storeReceiveUpdateType = array;
    });
    
    //加载数据-查询特殊配置代码
    var storeQueryTableId = null;
    smartwork.loadDataDict('QUERY_TABLE_ID'
    ,function(array){
        storeQueryTableId = array;
    });
    
    //加载数据-接收特殊配置代码
    var storeReceiveTableId = null;
    smartwork.loadDataDict('RECEIVE_TABLE_ID'
    ,function(array){
        storeReceiveTableId = array;
    });
    
    //加载数据-特殊本方字段
    var storeElementFieldName = null;
    smartwork.loadDataDict('ELEMENT_FIELD_NAME'
    ,function(array){
        storeElementFieldName = array;
    });
    
    //加载数据-默认值
    var storeElementDefaultValue = null;
    smartwork.loadDataDict('ELEMENT_DEFAULT_VALUE'
    ,function(array){
        storeElementDefaultValue = array;
    });
    
    //加载数据-字段类型转换
    var storeElementFieldType = null;
    smartwork.loadDataDict('ELEMENT_FIELD_TYPE'
    ,function(array){
        storeElementFieldType = array;
    });
    
    //加载数据-自定义字段类型下标
    var storeElementUserFieldTypeIndex = null;
    smartwork.loadDataDict('ELEMENT_USER_FIELD_TYPE_INDEX'
    ,function(array){
        storeElementUserFieldTypeIndex = array;
    });
    
    //加载数据-数据转换
    var storeElementDataConvert = null;
    smartwork.loadDataDict('ELEMENT_DATA_CONVERT'
    ,function(array){
        storeElementDataConvert = array;
    });
    
    //加载数据-数据转换格式
    var storeElementDataFormat = null;
    smartwork.loadDataDict('ELEMENT_DATA_FORMAT'
    ,function(array){
        storeElementDataFormat = array;
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
                ,width: 230
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
                name: 'receiveTransationType'
                ,label: '接收事务类型'
                ,width: 100
                ,valueType: 'receiveTransationType'
                ,html: true
                ,tip: true
            }, {
                name: 'receiveDeleteFlag'
                ,label: '接收删除值'
                ,width: 90
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
                            url: '../config/QueryReceiveIfConfig.do' //请求地址
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
            'receiveTransationType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeReceiveTransationType, dataValue);
                }
            }
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
        smartwork.initCombox(dialog.find('[name=receiveTransationType]'), storeReceiveTransationType, {show_text: true});
        
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
                url : '../config/AddReceiveIfConfig.do'
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
            ,'receiveTransationType': smartwork.getDictFirstValue(storeReceiveTransationType)
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
        smartwork.initCombox(dialog.find('[name=receiveTransationType]'), storeReceiveTransationType, {show_text: true});
        
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
                url : '../config/UpdateReceiveIfConfig.do'
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
            
            //提交
            smartwork.ajax({
                url : '../config/CopyReceiveIfConfig.do'
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
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    //查询配置-加载表格数据
    var gridQueryConfig = $('div.my-result-queryconfig');
    gridQueryConfig.datagrid({
        dataSource: {
            cols:[{
                name: 'tableId'
                ,label: '配置代码'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'tableDesc'
                ,label: '配置名称'
                ,width: 120
                ,html: true
                ,tip: true
            }, {
                name: 'parentTableId'
                ,label: '父配置代码'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'approachPrefix'
                ,label: '前置合并途径'
                ,width: 100
                ,html: true
                ,tip: true
            }, {
                name: 'approach'
                ,label: '返回途径'
                ,width: 100
                ,html: true
                ,tip: true
            }, {
                name: 'approachSuffix'
                ,label: '单笔后缀途径'
                ,width: 100
                ,html: true
                ,tip: true
            }, {
                name: 'maxNumber'
                ,label: '每页行数'
                ,width: 69
                ,html: true
            }, {
                name: 'allowEmpty'
                ,label: '允许空数据'
                ,width: 84
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'emptyFromDefault'
                ,label: '空数据使用默认值'
                ,width: 122
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'handQuerySql'
                ,label: '查询SQL'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'successSql'
                ,label: '接收成功SQL'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'sortNo'
                ,label: '顺序号'
                ,width: 70
            }, {
                name: 'xmlAttr'
                ,label: 'XML属性'
                ,width: 80
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryQueryConfig.do' //请求地址
                            ,data: {'ifCode': ifCode}
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
                        selectQueryOrReceiveConfigConfirmFn(gridQueryConfigObj, cell.rowIndex);
                    }
                    ,dblclick: function(event){
                        event.preventDefault();
                        modifyQueryConfigFn(cell.rowIndex)
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
        ,height: gridQueryConfig.innerHeight()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:10000}
            ,fixedLeftUntil: 2
        }
        ,onLoad: function() {
            
        }
    });
    
    //获取查询配置表格对象
    var gridQueryConfigObj = gridQueryConfig.data('zui.datagrid');
 
    //查询配置-新增方法
    var addQueryConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-queryconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=tableId]'), storeQueryTableId, {disable_search: false, use_query:true, column:1.5});
        
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
            var submitValues = smartwork.prefixName(values, 'bean.');
            submitValues['ifCode'] = ifCode;
            
            //提交
            smartwork.ajax({
                url : '../config/AddQueryConfig.do'
                ,'show-loading': true
                ,data: submitValues
            }, function(result){
                smartwork.info('提交成功');
                
                //重新显示数据
                smartwork.hide();
                smartwork.uncheckAll(gridQueryConfigObj);
                smartwork.gridQuery(gridQueryConfigObj);
                
                //数据重置
                smartwork.setFormValue(form, defaultData); 
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增查询配置');
        
        //设置默认值
        var defaultData = {maxNumber:1};
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
    
    //查询配置-修改方法
    var modifyQueryConfigFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridQueryConfigObj.getCheckItems()[0];
        } else { //双击
            data = gridQueryConfigObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的查询配置');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-queryconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=tableId]'), storeQueryTableId, {disable_search: false, use_query:true, column:1.5});
        
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
            var submitValues = smartwork.prefixName(values, 'bean.');
            submitValues['ifCode'] = ifCode;
            
            //提交
            smartwork.ajax({
                url : '../config/UpdateQueryConfig.do'
                ,'show-loading': true
                ,data: submitValues
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //如果顺序号或者父配置的配置代码发送变化，则需要重新查询
                if (values['sortNo'] != data['sortNo']) {
                    smartwork.hide();
                    smartwork.uncheckAll(gridQueryConfigObj);
                    smartwork.gridQuery(gridQueryConfigObj);
                    return;
                }
                if (values['tableId'] != data['tableId']
                    && (-1 != smartwork.indexOf(gridQueryConfigObj.getData(), 'parentTableId', {'parentTableId': data['tableId']}))) {
                    smartwork.hide();
                    smartwork.uncheckAll(gridQueryConfigObj);
                    smartwork.gridQuery(gridQueryConfigObj);
                    return;
                }
                
                //重新显示数据
                var rowIndex = gridQueryConfigObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridQueryConfigObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridQueryConfigObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改查询配置');
        
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
    
    //查询配置-删除方法
    var deleteQueryConfigFn = function(rowIndex) {
        smartwork.hide();
        
        //获取选择数据
        var datas = gridQueryConfigObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的查询配置');
            return;
        }
         
        smartwork.confirm('确定要删除选择的查询配置？<span class="text-danger">删除会导致对应的所有子配置被删除。</span>', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'ids': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.pkId)) {
                    params['ids'].push(item.pkId);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/DeleteQueryConfig.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                smartwork.hide();
                smartwork.uncheckAll(gridQueryConfigObj);
                smartwork.gridQuery(gridQueryConfigObj);
            });
        });
    }
    
    //查询配置-复制方法
    var copyQueryConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-copy-queryconfig');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //获取数据
        var data = gridQueryConfigObj.getCheckItems()[0];
        
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
            if ((values['srcIfCode'] == values['toIfCode'])
                && (values['srcTableId'] == values['toTableId'])) {
                smartwork.error('不可复制到源配置');
                return;
            }
            
            smartwork.confirm('确定要复制？<span class="text-danger">复制会覆盖目标配置和目标的字段关系对照。</span>', function(action) {
                
                //不是确定不处理
                if ('ok' != action) {
                    return;
                }
                
                //提交
                smartwork.ajax({
                    url : '../config/CopyQueryConfig.do'
                    ,'show-loading': true
                    ,data: values
                }, function(result){
                    dialog.modal('hide'); //隐藏
                    smartwork.info('提交成功');
                    
                    //同一个接口,重新显示数据
                    if (values['srcIfCode'] == values['toIfCode']) {
                        smartwork.hide();
                        smartwork.uncheckAll(gridQueryConfigObj);
                        smartwork.gridQuery(gridQueryConfigObj);
                    }
                });
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('复制查询配置');
        
        //设置数据
        var newData = {
            'srcIfCode': ifCode
            ,'srcTableId': data['tableId']
            ,'toIfCode': ifCode
            ,'toTableId': data['tableId']
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
    
    //查询配置-新增按钮
    $('.my-query-queryconfig button[btnname=add]').click(function(){
        addQueryConfigFn();
    });
    
    //查询配置-修改按钮
    $('.my-query-queryconfig button[btnname=modify]').click(function(){
        modifyQueryConfigFn();
    });
    
    //查询配置-删除按钮
    $('.my-query-queryconfig button[btnname=delete]').click(function(){
        deleteQueryConfigFn();
    });
    
    //查询配置-复制按钮
    $('.my-query-queryconfig button[btnname=copy]').click(function(){
        copyQueryConfigFn();
    });
    
    /////////////////////////////////////////////////////////////////////////
    
    //接收配置-加载表格数据
    var gridReceiveConfig = $('div.my-result-receiveconfig');
    gridReceiveConfig.datagrid({
        dataSource: {
            cols:[{
                name: 'tableId'
                ,label: '配置代码'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'tableDesc'
                ,label: '配置名称'
                ,width: 120
                ,html: true
                ,tip: true
            }, {
                name: 'tableName'
                ,label: '接收表名'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'parentTableId'
                ,label: '父配置代码'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'approach'
                ,label: '接收途径'
                ,width: 100
                ,html: true
                ,tip: true
            }, {
                name: 'isValueApproach'
                ,label: '值途径'
                ,width: 58
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'approachSuffix'
                ,label: '单笔后缀途径'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'revisionName'
                ,label: '途径区分字段'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'emptyFromParent'
                ,label: '空数据使用父表'
                ,width: 110
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'tableType'
                ,label: '接收表类型'
                ,width: 86
                ,valueType: 'tableType'
                ,html: true
                ,tip: true
            }, {
                name: 'keySequence'
                ,label: '接收主键序列'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'keyClass'
                ,label: '接收主键生成类'
                ,width: 110
                ,html: true
                ,tip: true
            }, {
                name: 'createType'
                ,label: '允许新增'
                ,width: 69
                ,valueType: 'yes-no'
                ,html: true
                ,tip: true
            }, {
                name: 'updateType'
                ,label: '更新类型'
                ,width: 140
                ,valueType: 'updateType'
                ,html: true
                ,tip: true
            }, {
                name: 'deleteSql'
                ,label: '删除SQL'
                ,width: 96
                ,html: true
                ,tip: true
            }, {
                name: 'sortNo'
                ,label: '顺序号'
                ,width: 70
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryReceiveConfig.do' //请求地址
                            ,data: {'ifCode': ifCode}
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
                        selectQueryOrReceiveConfigConfirmFn(gridReceiveConfigObj, cell.rowIndex);
                    }
                    ,dblclick: function(event){
                        event.preventDefault();
                        modifyReceiveConfigFn(cell.rowIndex)
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
            ,'tableType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeReceiveTableType, dataValue);
                }
            }
            ,'updateType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getDictText(storeReceiveUpdateType, dataValue);
                }
            }
        }
        ,height: gridReceiveConfig.innerHeight()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:10000}
            ,fixedLeftUntil: 3
        }
        ,onLoad: function() {
            
        }
    });
    
    //获取接收配置表格对象
    var gridReceiveConfigObj = gridReceiveConfig.data('zui.datagrid');
    activeTabGrid = gridReceiveConfig;
 
    //接收配置-新增方法
    var addReceiveConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-receiveconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=tableId]'), storeReceiveTableId, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=tableType]'), storeReceiveTableType, {show_text: true});
        smartwork.initCombox(dialog.find('[name=keySequence]'), storeReceiveKeySequence, {disable_search: false, use_query:true});
        smartwork.initCombox(dialog.find('[name=updateType]'), storeReceiveUpdateType, {show_text: true});
        
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
            var submitValues = smartwork.prefixName(values, 'bean.');
            submitValues['ifCode'] = ifCode;
            
            //提交
            smartwork.ajax({
                url : '../config/AddReceiveConfig.do'
                ,'show-loading': true
                ,data: submitValues
            }, function(result){
                smartwork.info('提交成功');
                
                //重新显示数据
                smartwork.hide();
                smartwork.uncheckAll(gridReceiveConfigObj);
                smartwork.gridQuery(gridReceiveConfigObj);
                
                //数据重置
                smartwork.setFormValue(form, defaultData); 
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增接收配置');
        
        //设置默认值
        var defaultData = {
            'tableType': smartwork.getDictFirstValue(storeReceiveTableType)
            ,'createType': '1'
            ,'updateType': smartwork.getDictFirstValue(storeReceiveUpdateType)
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
    
    //接收配置-修改方法
    var modifyReceiveConfigFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridReceiveConfigObj.getCheckItems()[0];
        } else { //双击
            data = gridReceiveConfigObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的接收配置');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-receiveconfig');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=tableId]'), storeReceiveTableId, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=tableType]'), storeReceiveTableType, {show_text: true});
        smartwork.initCombox(dialog.find('[name=keySequence]'), storeReceiveKeySequence, {disable_search: false, use_query:true});
        smartwork.initCombox(dialog.find('[name=updateType]'), storeReceiveUpdateType, {show_text: true});
        
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
            var submitValues = smartwork.prefixName(values, 'bean.');
            submitValues['ifCode'] = ifCode;
            
            //提交
            smartwork.ajax({
                url : '../config/UpdateReceiveConfig.do'
                ,'show-loading': true
                ,data: submitValues
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //如果顺序号或者父配置的配置代码发送变化，则需要重新查询
                if (values['sortNo'] != data['sortNo']) {
                    smartwork.hide();
                    smartwork.uncheckAll(gridReceiveConfigObj);
                    smartwork.gridQuery(gridReceiveConfigObj);
                    return;
                }
                if (values['tableId'] != data['tableId']
                    && (-1 != smartwork.indexOf(gridReceiveConfigObj.getData(), 'parentTableId', {'parentTableId': data['tableId']}))) {
                    smartwork.hide();
                    smartwork.uncheckAll(gridReceiveConfigObj);
                    smartwork.gridQuery(gridReceiveConfigObj);
                    return;
                }
                
                //重新显示数据
                var rowIndex = gridReceiveConfigObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridReceiveConfigObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridReceiveConfigObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改接收配置');
        
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
    
    //接收配置-删除方法
    var deleteReceiveConfigFn = function(rowIndex) {
        
        smartwork.hide();
        
        //获取选择数据
        var datas = gridReceiveConfigObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的接收配置');
            return;
        }
         
        smartwork.confirm('确定要删除选择的接收配置？<span class="text-danger">删除会导致对应的所有子配置被删除。</span>', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //获取参数
            var params = {'ids': []};
            $.each(datas, function(index, item) {
                if (item && !smartwork.isEmpty(item.pkId)) {
                    params['ids'].push(item.pkId);
                }
            });
        
            //提交
            smartwork.ajax({
                url : '../config/DeleteReceiveConfig.do'
                ,'show-loading': true
                ,data: params
                ,traditional: true
            }, function(result){
                smartwork.hide();
                smartwork.uncheckAll(gridReceiveConfigObj);
                smartwork.gridQuery(gridReceiveConfigObj);
            });
        });
    }
    
    //接收配置-复制方法
    var copyReceiveConfigFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-copy-receiveconfig');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //获取数据
        var data = gridReceiveConfigObj.getCheckItems()[0];
        
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
            if ((values['srcIfCode'] == values['toIfCode'])
                && (values['srcTableId'] == values['toTableId'])) {
                smartwork.error('不可复制到源配置');
                return;
            }
            
            smartwork.confirm('确定要复制？<span class="text-danger">复制会覆盖目标配置和目标的字段关系对照。</span>', function(action) {
                
                //不是确定不处理
                if ('ok' != action) {
                    return;
                }
                
                //提交
                smartwork.ajax({
                    url : '../config/CopyReceiveConfig.do'
                    ,'show-loading': true
                    ,data: values
                }, function(result){
                    dialog.modal('hide'); //隐藏
                    smartwork.info('提交成功');
                    
                    //同一个接口,重新显示数据
                    if (values['srcIfCode'] == values['toIfCode']) {
                        smartwork.hide();
                        smartwork.uncheckAll(gridReceiveConfigObj);
                        smartwork.gridQuery(gridReceiveConfigObj);
                    }
                });
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('复制接收配置');
        
        //设置数据
        var newData = {
            'srcIfCode': ifCode
            ,'srcTableId': data['tableId']
            ,'toIfCode': ifCode
            ,'toTableId': data['tableId']
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
    
    //接收配置-新增按钮
    $('.my-query-receiveconfig button[btnname=add]').click(function(){
        addReceiveConfigFn();
    });
    
    //接收配置-修改按钮
    $('.my-query-receiveconfig button[btnname=modify]').click(function(){
        modifyReceiveConfigFn();
    });
    
    //接收配置-删除按钮
    $('.my-query-receiveconfig button[btnname=delete]').click(function(){
        deleteReceiveConfigFn();
    });
    
    //接收配置-复制按钮
    $('.my-query-receiveconfig button[btnname=copy]').click(function(){
        copyReceiveConfigFn();
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
    
    /////////////////////////////////////////////////////////////////
    
    //字段对照关系-提示控件
    var toTipElementElement = $('a[data-target="#tabElement"]').parent();
    var btnNextTableId = $('.my-query-element button[btnname=nextTableId]')
    
    //字段对照关系-加载表格数据
    var gridElement = $('div.my-result-element');
    gridElement.datagrid({
        dataSource: {
            cols:[{
                name: 'elementName'
                ,label: '对方字段'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'fieldName'
                ,label: '本方字段'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'elementDesc'
                ,label: '字段名称'
                ,width: 230
                ,html: true
                ,tip: true
            }, {
                name: 'isActive'
                ,label: '是否启用'
                ,width: 69
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'userFieldType'
                ,label: '字段类型'
                ,width: 160
                ,valueType: 'userFieldType'
                ,html: true
                ,tip: true
            }, {
                name: 'defaultValue'
                ,label: '默认值'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'nullAble'
                ,label: '允许为空'
                ,width: 69
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'maxLength'
                ,label: '数据最大长度'
                ,width: 100
                ,html: true
            }, {
                name: 'insertAble'
                ,label: '允许新增'
                ,width: 69
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'updateAble'
                ,label: '允许更新'
                ,width: 69
                ,valueType: 'yes-no'
                ,html: true
            }, {
                name: 'sortNo'
                ,label: '顺序号'
                ,width: 69
                ,html: true
                ,tip: true
            }, {
                name: 'dataConvert'
                ,label: '数据转换'
                ,width: 140
                ,html: true
                ,tip: true
            }, {
                name: 'dataFormat'
                ,label: '转换格式'
                ,width: 160
                ,html: true
                ,tip: true
            }, {
                name: 'xmlAttr'
                ,label: 'XML属性'
                ,width: 76
                ,html: true
                ,tip: true
            }, {
                name: 'creator'
                ,label: '元素生成类'
                ,width: 96
                ,html: true
                ,tip: true
            }]
            ,array: []
            ,remote: function(params, datagrid) {
                return smartwork.gridRemoteConfig({
                            url: '../config/QueryElement.do' //请求地址
                            ,data: {'ifCode': ifCode, 'tableId': tableId}
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
                        modifyElementFn(cell.rowIndex)
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
            ,'userFieldType': {
                getter: function(dataValue, cell, dataGrid) {
                    return smartwork.getUserFieldType(gridElementObj, storeElementFieldType, storeElementUserFieldTypeIndex, cell);
                }
            }
        }
        ,height: gridElement.innerHeight()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {
            pager:{page:1, recPerPage:10000}
            ,fixedLeftUntil: 3
        }
        ,onLoad: function() {
            if (!this.dataSource.array) {
                smartwork.disable(btnNextTableId, false);
            }
        }
    });
    
    //获取字段对照关系表格对象
    var gridElementObj = gridElement.data('zui.datagrid');
 
    //字段对照关系-粘贴方法
    var pasteElementFn = function() {
       
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-paste-element');
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
            
            //隐藏
            dialog.modal('hide');
            
            //正在操作中
            smartwork.loading();
            
            //获取表达数据
            var values = smartwork.getFormValue(form);
            
            //获取判断字段
            var fields = [];
            if ('1' == values['replaceRule']) {
                fields.push('elementName');
            } else if ('2' == values['replaceRule']) {
                fields.push('fieldName');
            } else if ('3' == values['replaceRule']) {
                fields.push('elementName');
                fields.push('fieldName');
            }
            
            //是否删除非粘贴数据
            var exsistsIndexs = [];
            var isDeteleOther = '1' == values['deleteOther'];
            
            //获取当前数据
            var gridDatas = gridElementObj.getData();
            
            //默认值
            var maxLength = gridDatas.length;
            var defaultAdd = {
                'fieldType': '0'
                ,'userFieldType': '00000000000000000000'
                ,'isActive': '1'
                ,'nullAble': '1'
                ,'insertAble': '1'
                ,'updateAble': '1'
            };
            
            //分割数据
            var lines = values['content'].split(/[\r|\n]/);
            for (var i = 0; i < lines.length; i++) {
                
                //空字符串不处理
                if ('' == lines[i].trim()) {
                    continue;
                }
                
                //获取对应列的数据
                var items = lines[i].split('\t');
                var data = {
                    'elementName': (items[0] || '').trim()
                    ,'fieldName': (items[1] || '').trim()
                    ,'elementDesc': (items[2] || '').trim()
                    ,'maxLength': parseInt((items[3] || '0').trim()) || 0
                    ,'sortNo': parseInt((items[4] || '').trim()) || i * 10
                };
                
                var index = smartwork.indexOf(gridDatas, fields, data);
                if (index < 0) {
                    $.extend(data, defaultAdd);
                    smartwork.gridAdd(gridElementObj, data, true);
                } else {
                    $.extend(gridDatas[index], data);
                    
                    //增加存在的数据
                    if (isDeteleOther) {
                        exsistsIndexs.push(index);
                    }
                }
            }
            
            //删除数据
            if (isDeteleOther) {
                var deteleDatas = [];
                for (var i = 0; i < maxLength; i++) {
                    if (exsistsIndexs.indexOf(i) < 0) {
                        deteleDatas.push(gridDatas[i]);
                    }
                }
                
                if (deteleDatas.length > 0) {
                    smartwork.gridRemove(gridElementObj, deteleDatas);
                    smartwork.uncheckAll(gridElementObj);
                } else {
                    gridElementObj.renderData(); //重新显示所有数据
                }
            } else {
                gridElementObj.renderData(); //重新显示所有数据
            }
            
            //增加提示
            smartwork.elementTip(toTipElementElement, true, '数据有变动，注意保存');
            isChangeElement = true;
            
            //隐藏操作中
            smartwork.hideLoading();
        }
        
        //设置标题
        dialog.find('.modal-title').text('粘贴字段关系对照【需点击保存才提交数据】');
        
        //设置默认值
        var defaultData = {
            'replaceRule': '3'
            ,'deleteOther': '0'
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
    
    //初始化字段类型
    var initUserFieldTypeFn = function(dialog) {
    
        //初始化字段类型
        var container = dialog.find('.my-fieldtype-container');
        
        //初始化完成
        if ('1' == container.attr('my-init')) {
            return;
        }
        container.attr('my-init', '1');
        
        var checkboxFormat = '<label style="margin-right:10px;width:90px;"><input type="checkbox" name="fieldType-{0}" my-limit="chkFieldType" my-mutex="{1}" value="1"><span>{2}</span></label>';
        $.each(storeElementUserFieldTypeIndex, function(index, item) {
            
            //普通字段不需要
            if (index <= 0) {
                return;
            }
            
            container.append(checkboxFormat.format(item['codeValue'], item['codeDesc'], item['codeValueName']));
        });
        
        //点击事件
        container.find('input').click(function(){
            var that = $(this);
            
            //取消选择不处理
            if ('1' != that.val()) {
                return;
            }
            
            //互斥处理，获取设置值
            var thisIndex = that.attr('name').replace('fieldType-', '');
            var mutex = that.attr('my-mutex');
            var index = mutex.indexOf('0');
            while(index != -1) {
                if (thisIndex != (index + 1)) {
                    container.find('[name=fieldType-{0}]'.format(index + 1)).prop('checked', false);
                }
                index = mutex.indexOf('0', index + 1)
            }
        });
    }
    
    //初始化设置属性
    var initPropertyElementFn = function(dialog) {
        
        //初始化完成
        if ('1' == dialog.attr('my-init')) {
            return;
        }
        dialog.attr('my-init', '1');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=defaultValue]'), storeElementDefaultValue, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataConvert]'), storeElementDataConvert, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataFormat]'), storeElementDataFormat, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=isActive]'),   [], {column:1.5});
        smartwork.initCombox(dialog.find('[name=nullAble]'),   [], {column:1.5});
        smartwork.initCombox(dialog.find('[name=insertAble]'), [], {column:1.5});
        smartwork.initCombox(dialog.find('[name=updateAble]'), [], {column:1.5});
        
        //初始化字段类型
        initUserFieldTypeFn(dialog);
        
        //标签点击事件
        dialog.find('.nav-tabs input').prop('checked', true).click(function() {
            var that = $(this);
            var disable = !that.is(':checked');
            
            //获取关联控件
            var limitElement = dialog.find('[my-limit={0}]'.format(that.attr('ctlname')));
            
            //禁用
            smartwork.disable(limitElement, disable);
        }).click(); 
    }
    
    //组合自定义字段类型
    var concatUserFieldTypeFn = function(values) {
        if ('fieldType-1' in values) {
            var userFieldType = '';
            var name;
            for (var i = 1; i < storeElementUserFieldTypeIndex.length; i++) {
                name = 'fieldType-' + i;
                userFieldType += values[name];
                delete values[name];
            }
            values['userFieldType'] = userFieldType;
        }
    }
    
    //分解自定义字段类型
    var splitUserFieldTypeFn = function(data) {
        for (var i = 1; i < storeElementUserFieldTypeIndex.length; i++) {
            data['fieldType-' + i] = data['userFieldType'].charAt(i - 1);
        }
    }
    
    //字段对照关系-设置方法
    var propertyElementFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取选择数据
        var checkDatas = gridElementObj.getCheckItems();
        if (checkDatas.length <= 0) {
            smartwork.error('请选择要设置属性的字段关系对照');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-property-element');
        var form = dialog.find('form');
        
        //初始化设置属性面板
        initPropertyElementFn(dialog);
        
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
            
            //隐藏
            dialog.modal('hide');
            
            //正在操作中
            smartwork.loading();
            
            //获取表单数据
            var values = smartwork.getFormValue(form);
            
            //删除不设置属性
            $.each(dialog.find('.nav-tabs input'), function(){
                var that = $(this);
                var checked = that.is(':checked');
                
                //删除管理数据
                var limitElement = dialog.find('[my-limit={0}]'.format(that.attr('ctlname')));
                $.each(limitElement, function(){
                    var name = $(this).attr('name');
                    if (!checked) {
                        delete values[name];
                    } else if ('bushezhi-1' == values[name]) {
                        delete values[name];
                    } else if ('maxLength' == name && '-1' == values[name]) {
                        delete values[name];
                    }
                });
            });
            
            //组合字段类型
            values['fieldType'] = '0';
            concatUserFieldTypeFn(values);
            
            //没有数据
            if ('{}' == JSON.stringify(values)) {
                smartwork.hideLoading(); //隐藏操作中
                return;
            }
            
            //修改数据
            $.each(checkDatas, function(index, item){
                $.extend(item, values);
            });
            
            //表格重新显示
            gridElementObj.renderData();
            
            //增加提示
            smartwork.elementTip(toTipElementElement, true, '数据有变动，注意保存');
            isChangeElement = true;
            
            //隐藏操作中
            smartwork.hideLoading();
        }
        
        //设置标题
        dialog.find('.modal-title').text('设置属性-字段关系对照【需点击保存才提交数据】');
        
        //获取设置值
        var data = $.extend({}, checkDatas[0]);
        
        //分解自定义字段类型
        splitUserFieldTypeFn(data);
        
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
    
    //获取下一个配置代码
    var nextTableIdFn = function() {
        
        smartwork.disable(btnNextTableId, true);
        
        var gridQueryDatas = gridQueryConfigObj.getData();
        var gridReceiveDatas = gridReceiveConfigObj.getData();
        var queryData = {'tableId': tableId};
        
        //查询查询配置
        var index = smartwork.indexOf(gridQueryDatas, 'tableId', queryData);
        if (-1 != index) {
            
            //获取下一个数据下标
            index++;
            if (index >= gridQueryDatas.length) {
                index = 0;
            }
            
            //选择了下一个，行数比数据下标多1
            selectQueryOrReceiveConfigConfirmFn(gridQueryConfigObj, index + 1); 
            
            return;
        }
        
        //查询接收配置
        index = smartwork.indexOf(gridReceiveDatas, 'tableId', queryData);
        if (-1 != index) {
            
            //获取下一个数据下标
            index++;
            if (index >= gridReceiveDatas.length) {
                index = 0;
            }
            
            //选择了下一个，行数比数据下标多1
            selectQueryOrReceiveConfigConfirmFn(gridReceiveConfigObj, index + 1); 
            
            return;
        }
        
        //没有查询到数据
        smartwork.disable(btnNextTableId, false);
    }
    
    //字段对照关系-新增方法
    var addElementFn = function() {
        
        //隐藏提示
        smartwork.hide();
        
        //获取控件
        var dialog = $('#my-dialog-element');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=fieldName]'), storeElementFieldName, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=defaultValue]'), storeElementDefaultValue, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataConvert]'), storeElementDataConvert, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataFormat]'), storeElementDataFormat, {disable_search: false, use_query:true, column:1.5});
        
        //初始化字段类型
        initUserFieldTypeFn(dialog);
        
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
            
            //组合字段类型
            values['fieldType'] = '0';
            concatUserFieldTypeFn(values);
            
            //根据参数代码更新或新增
            var gridDatas = gridElementObj.getData();
            var index = smartwork.indexOf(gridDatas, ['elementName', 'fieldName'], values);
            if (index < 0) {
                smartwork.gridAdd(gridElementObj, values); //增加到表格
            } else {
                $.extend(true, gridDatas[index], values);
                gridElementObj.renderRow(index + 1);
                smartwork.gridModifyLayout(gridElementObj);
            }
            
            //增加提示
            smartwork.elementTip(toTipElementElement, true, '数据有变动，注意保存');
            isChangeElement = true;
            
            //重置
            smartwork.setFormValue(form, defaultData);
        }
        
        //设置标题
        dialog.find('.modal-title').text('新增字段关系对照【需点击保存才提交数据】');
        
        //设置默认值
        var defaultData = {
            'isActive': '1'
            ,'nullAble': '1'
            ,'maxLength': 0
            ,'insertAble': '1'
            ,'updateAble': '1'
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
    
    //字段对照关系-修改方法
    var modifyElementFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridElementObj.getCheckItems()[0];
        } else { //双击
            data = gridElementObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的字段关系对照');
            return;
        }
        
        //获取控件
        var dialog = $('#my-dialog-element');
        var form = dialog.find('form');
        
        //初始化下拉框
        smartwork.initCombox(dialog.find('[name=fieldName]'), storeElementFieldName, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=defaultValue]'), storeElementDefaultValue, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataConvert]'), storeElementDataConvert, {disable_search: false, use_query:true, column:1.5});
        smartwork.initCombox(dialog.find('[name=dataFormat]'), storeElementDataFormat, {disable_search: false, use_query:true, column:1.5});
        
        //初始化字段类型
        initUserFieldTypeFn(dialog);
        
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
            
            //组合字段类型
            values['fieldType'] = '0';
            concatUserFieldTypeFn(values);
            
            //重新显示数据
            dialog.modal('hide');
            var rowIndex = gridElementObj.getData().indexOf(data);
            if (rowIndex >= 0) {
                $.extend(true, data, values);
                gridElementObj.renderRow(rowIndex + 1);
                smartwork.gridModifyLayout(gridElementObj);
            }
            
            //增加提示
            smartwork.elementTip(toTipElementElement, true, '数据有变动，注意保存');
            isChangeElement = true;
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改字段关系对照【需点击保存才提交数据】');
        
        //分解自定义字段类型
        var newData = $.extend({}, data);
        splitUserFieldTypeFn(newData);
        
        //设置数据
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
    
    //字段对照关系-删除方法
    var deleteElementFn = function() {
        
        smartwork.hide();
        
        //获取选择数据
        var datas = gridElementObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要删除的字段关系对照');
            return;
        }
         
        smartwork.confirm('确定要删除选择的字段关系对照【需点击保存才提交数据】？', function(action){ 
            
            //不是确定不处理
            if ('ok' != action) {
                return;
            }
            
            //删除界面数据
            smartwork.uncheckAll(gridElementObj);
            smartwork.gridRemove(gridElementObj, datas);
            
            //增加提示
            smartwork.elementTip(toTipElementElement, true, '数据有变动，注意保存');
            isChangeElement = true;
        });
    }
    
    //字段对照关系-保存方法
    var saveElementFn = function(rowIndex) {
        
        var values = {'ifCode': ifCode, 'tableId': tableId};
        
        //遍历数据
        var gridDatas = gridElementObj.getData();
        var prefix = null;
        for (var i = 0; i < gridDatas.length; i++) {
            prefix = 'beans[{0}].'.format(i);
            values[prefix + 'elementName'] = gridDatas[i]['elementName'];
            values[prefix + 'fieldName'] = gridDatas[i]['fieldName'];
            values[prefix + 'elementDesc'] = gridDatas[i]['elementDesc'];
            values[prefix + 'defaultValue'] = gridDatas[i]['defaultValue'];
            values[prefix + 'isActive'] = gridDatas[i]['isActive'];
            values[prefix + 'userFieldType'] = gridDatas[i]['userFieldType'];
            
            values[prefix + 'nullAble'] = gridDatas[i]['nullAble'];
            values[prefix + 'maxLength'] = gridDatas[i]['maxLength'];
            
            values[prefix + 'dataConvert'] = gridDatas[i]['dataConvert'];
            values[prefix + 'dataFormat'] = gridDatas[i]['dataFormat'];
            
            values[prefix + 'insertAble'] = gridDatas[i]['insertAble'];
            values[prefix + 'updateAble'] = gridDatas[i]['updateAble'];
            
            values[prefix + 'xmlAttr'] = gridDatas[i]['xmlAttr'];
            values[prefix + 'creator'] = gridDatas[i]['creator'];
            
            values[prefix + 'sortNo'] = gridDatas[i]['sortNo'];
        }
        
        //提交
        smartwork.ajax({
            url : '../config/SaveElement.do'
            ,'show-loading': true
            ,data: values
        }, function(result){
            smartwork.info('提交成功');
            
            //删除提示
            smartwork.elementTip(toTipElementElement, false);
            isChangeElement = false;
            
            //从后台查询数据
            smartwork.hide();
            smartwork.uncheckAll(gridElementObj);
            smartwork.gridQuery(gridElementObj);
        });
    }
    
    //字段对照关系-新增按钮
    btnNextTableId.click(function(){
        nextTableIdFn();
    });
    
    //字段对照关系-新增按钮
    $('.my-query-element button[btnname=add]').click(function(){
        addElementFn();
    });
    
    //字段对照关系-修改按钮
    $('.my-query-element button[btnname=modify]').click(function(){
        modifyElementFn();
    });
    
    //字段对照关系-删除按钮
    $('.my-query-element button[btnname=delete]').click(function(){
        deleteElementFn();
    });
    
    //字段对照关系-保存按钮
    $('.my-query-element button[btnname=save]').click(function(){
        saveElementFn();
    });
    
    //字段对照关系-粘贴按钮
    $('.my-query-element button[btnname=paste]').click(function(){
        pasteElementFn();
    });
    
    //字段对照关系-设置属性按钮
    $('.my-query-element button[btnname=property]').click(function(){
        propertyElementFn();
    });
    
    ////////////////////////////////////////////////////
    
    //禁用配置从表按钮（查询配置、接收配置、通用参数、接口参数）
    var disableIfConfigDetailBtnFn = function(disable) {
        smartwork.disable($('.my-query-queryconfig button[btnname]'), disable);
        smartwork.disable($('.my-query-receiveconfig button[btnname]'), disable);
        smartwork.disable($('.my-query-param button[btnname]'), disable);
        smartwork.disable($('.my-query-generalparam button[btnname]'), disable);
    }
    
    //禁用字段关系按钮
    var disableElementBtnFn = function(disable) {
        smartwork.disable($('.my-query-element button[btnname]'), disable);
    }
    
    //选中接口配置-确定事件
    var selectIfConfigConfirmFn = function(rowIndex) {
        
        //获取点击的数据
        var data = gridIfConfigObj.getData()[rowIndex - 1];
        
        //接口代码已处理
        if (ifCode == data['ifCode']) {
            return;
        }
        
        if (isChangeElement || isChangeParam || isChangeGeneralParam) {
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
        
        //设置查询配置、接收配置、接口参数、通用参数的提示信息
        smartwork.setFormValue($('form[my-ref=config]'), {'ifCode': ifCode, 'toSysName': toSysName});
        
        //启用查询配置、接收配置、接口参数、通用参数的按钮
        disableIfConfigDetailBtnFn(false);
        
        //处理字段对照关系
        disableElementBtnFn(true);
        removeElementGridFn();
        
        //查询激活标签的表格数据
        queryActiveTabGridFn();
    }
    
    //删除查询配置、接收配置、接口参数、通用参数的数据
    var removeIfConfigDetailGridFn = function() {
        
        //重置全局数据
        ifCode = toSysName = null;
        
        //删除数据
        smartwork.gridRemoveAll(gridQueryConfigObj);
        smartwork.gridRemoveAll(gridReceiveConfigObj);
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
        gridQueryConfig.data('ifCode', '');
        gridReceiveConfig.data('ifCode', '');
        gridParam.data('ifCode', '');
        gridGeneralParam.data('ifCode', '');
        
        //字段关系对照处理
        disableElementBtnFn(true);
        removeElementGridFn();
    }
    
    //查询激活标签的表格数据
    var queryActiveTabGridFn = function() {
        
        //获取表对象
        var grid = activeTabGrid;
        var gridObj = grid.data('zui.datagrid');
        if (!gridObj) {
            return;
        }
        
        //字段关系对照需要另处理
        if (gridObj == gridElementObj) {
            if (!tableId || (grid.data('ifCode') == ifCode && grid.data('tableId') == tableId)) {
                return;
            }
        } else {
            
            //没有选定数据或数据已查询则不处理
            if (!ifCode || grid.data('ifCode') == ifCode) {
                return;
            }
        }
        
        //去除提示
        if (gridObj == gridElementObj) {
            smartwork.elementTip(toTipElementElement, false);
            isChangeElement = false;
        } else if (gridObj == gridParamObj) {
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
    
    //删除字段对照数据
    var removeElementGridFn = function() {
        
        //重置全局数据
        tableId = null;
        
        //删除数据
        smartwork.gridRemoveAll(gridElementObj);
        
        //删除提示数据
        smartwork.setFormValue($('form.my-query-element'), {});
        
        //去除提示
        smartwork.elementTip(toTipElementElement, false);
        isChangeElement = false;
        
        //删除已查询标记
        gridElement.data('ifCode', '');
        gridElement.data('tableId', '');
    }
    
    //查询字段对照关系-确认
    var selectQueryOrReceiveConfigConfirmFn = function(gridObj, rowIndex) {
        
        //获取选中数据
        var data = gridObj.getData()[rowIndex - 1];
        
        //配置代码已查询
        if (tableId == data['tableId']) {
            smartwork.disable(btnNextTableId, false);
            return;
        }
        
        if (isChangeElement) {
            smartwork.confirm('确定要放弃未保存的字段关系对照？', function(action){ 
                
                //不是确定不处理
                if ('ok' != action) {
                    smartwork.gridRollbackHighlightSelect(gridObj);
                    return;
                }
            
                //先删除，再查询
                removeElementGridFn();
                selectQueryOrReceiveConfigFn(data, gridObj, rowIndex);
            });
        } else {
            
            //先删除，再查询
            removeElementGridFn();
            selectQueryOrReceiveConfigFn(data, gridObj, rowIndex);
        }
    }
    
    //查询字段对照关系
    var selectQueryOrReceiveConfigFn = function(data, gridObj, rowIndex) {
        
        //获取配置代码
        tableId = data['tableId'];
        
        //设置字段对照的提示信息
        smartwork.setFormValue($('form.my-query-element'), {'ifCode': ifCode, 'tableId': tableId});
        
        //启用字段对照关系
        disableElementBtnFn(false);
        
        //查询激活标签的表格数据
        if (activeTabGrid.data('zui.datagrid') == gridElementObj) {
            queryActiveTabGridFn();
        }
    }
    
    //初始化时禁用所有从表按钮
    disableIfConfigDetailBtnFn(true);
    disableElementBtnFn(true);
    
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
        } else if ('MAX-NUMBER' == title) {
            message = '<p>1.必须是整数</p><p>2.从表设置无效</p>';
        } else if ('INT' == title) {
            message = '必须是整数';
        } else if ('QUERY-APPROACH' == title) {
            message = '<p>1.所有的途径都是相对途径</p><p>2.XML的根节点、根属性在参数中配置</p>';
        } else if ('RECEIVE-APPROACH' == title) {
            message = '<p>1.没有父配置的为绝对途径，有父配置的为相对途径</p><p>2.json的绝对途径从第一个{开始</p>';
        } else if ('REVISION-NAME' == title) {
            message = '<p>1.只针对解析文件</p><p>2.支持直接对方字段，值存在就通过</p><p>3.支持对方字段=值</p><p>4.支持对方字段=/值正则表达式/i</p><p>5.多个区分以;分割</p>';
        } else if ('QUERY-INVALID' == title) {
            message = '针对查询配置无效';
        } else if ('RECEIVE-INVALID' == title) {
            message = '针对接收配置无效';
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