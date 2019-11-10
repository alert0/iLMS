//alert(JSON.stringify(result))
$(document).ready(function(){
    
    //按钮
    var btnQuery = $('.my-query button[btnname=search]');
    var btnExport = $('.my-query button[btnname=export]');
    
    //获取高度
    var getHeightFn = function() {
        return $(window).height() - $('.my-query-param').outerHeight()
    }
 
    //加载表格数据
    var gridParam = $('div.my-result');
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
                            url: '../config/QueryGlobalParam.do' //请求地址
                            ,data: smartwork.getFormValue($('form.my-query'))
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
                    click:function(event){
                    }
                    ,dblclick:function(event){
                        event.preventDefault();
                        modifyFn(cell.rowIndex)
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
        ,height: getHeightFn()
        ,checkable: true
        ,checkByClickRow: false
        ,states: {pager:{page:1, recPerPage:10000}}
        ,onLoad: function() {
            smartwork.disable(btnQuery, false);
        }
    });
    
    //获取表格对象
    var gridParamObj = gridParam.data('zui.datagrid');
   
    //修改方法
    var modifyFn = function(rowIndex) {
        
        //隐藏提示
        smartwork.hide();
        
        //获取数据
        var data = null;
        if (smartwork.isEmpty(rowIndex)) { //点击按钮
            data = gridParamObj.getCheckItems()[0];
        } else { //双击
            data = gridParamObj.getData()[rowIndex - 1];
        }
        
        //没有数据
        if (!data) {
            smartwork.error('请选择要修改的数据');
            return;
        }
        
        //不可编辑
        if (1 != data.editAble) {
            smartwork.error('选择的数据不可编辑');
            return;
        }
        
        //获取控件
        var dialog = $('#my-param');
        var form = dialog.find('form');
        
        //增加form验证
        smartwork.initValidate(form);
        
        //提交
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
                url : '../config/UpdateGlobalParam.do'
                ,'show-loading': true
                ,data: smartwork.prefixName(values, 'bean.')
            }, function(result){
                dialog.modal('hide'); //隐藏
                smartwork.info('提交成功');
                
                //重新显示数据
                var rowIndex = gridParamObj.getData().indexOf(data);
                if (rowIndex >= 0) {
                    $.extend(true, data, values);
                    gridParamObj.renderRow(rowIndex + 1);
                    smartwork.gridModifyLayout(gridParamObj);
                }
            });
        }
        
        //设置标题
        dialog.find('.modal-title').text('修改全局参数');
        
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
    
    //查询按钮
    btnQuery.click(function(){
        smartwork.hide();
        smartwork.uncheckAll(gridParamObj);
        smartwork.disable(btnQuery, true);
        smartwork.gridQuery(gridParamObj);
    });
    
    //修改按钮
    $('.my-query button[btnname=modify]').click(function(){
        modifyFn();
    });
    
    //导入按钮
    $('.my-query .uploader').uploader(smartwork.uploadConfig({
        url: '../config/ImportGlobalParam.do'
    }, function() {
        btnQuery.click(); //查询
    }));
    
    //导出
    btnExport.click(function(){
        
        smartwork.hide();
        smartwork.disable(btnExport, true);
        
        //获取选择数据
        var datas = gridParamObj.getCheckItems();
        if (null == datas || datas.length <= 0) {
            smartwork.error('请选择要导出的数据');
            smartwork.disable(btnExport, false);
            return;
        }
        
        //获取参数
        var url = '../config/ExportGlobalParam.do';
        var params = {'paramCodes': []};
        $.each(datas, function(index, item) {
            if (item && !smartwork.isEmpty(item.paramCode)) {
                params['paramCodes'].push(item.paramCode);
            }
        });
        
        //导出数据
        smartwork.download(url, params);
        
        //最后处理
        smartwork.disable(btnExport, false);
    });
    
    //自动查询
    window.setTimeout(function(){
        btnQuery.click();
    }, 100);
    
    //高宽变动
    gridParam.on('resize', function() {
        gridParamObj.options.height = getHeightFn();
        gridParamObj.updateLayout();
    });
    
    //提示
    $('label[my-tip-title]').each(function(){
        var that = $(this);
        var title = that.attr('my-tip-title');
        var message = null;
        
        //设置消息
        if ('EN' == title) {
            message = '由英文、数字、下划线或-组成';
        }
        
        //初始化提示
        if (message) {
            that.popover({
                trigger:'hover'
                ,placement: 'top'
                ,container: that.parents('.modal.fade')
                ,content: message
            });
        }
    });
});