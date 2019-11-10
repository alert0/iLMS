    
//字符串增加format方法
String.prototype.format = function(args) {
    var result = this;
    
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key] !== undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        } else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] !== undefined) {
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    
    return result;
}

//去除左右空格
String.prototype.trim = function(args) {
     return this.replace(/^\s+|\s+$/g, '');
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1-3 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).format('yyyy-MM-dd hh:mm:ss.S') ==> 2006-07-02 08:09:04.423 
// (new Date()).format('yyyy-M-d h:m:s.S')      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (format) { 
    
    //默认格式化
    format = format || 'yyyy-MM-dd hh:mm:ss';
    
    //格式化形式
    var formats = {
        'M+': this.getMonth() + 1 //月份 
        ,'d+': this.getDate() //日 
        ,'h+': this.getHours() //小时 
        ,'m+': this.getMinutes() //分 
        ,'s+': this.getSeconds() //秒 
        ,'q+': Math.floor((this.getMonth() + 3) / 3) //季度 
        //,'S+': this.getMilliseconds() //毫秒 
    };
    
    //定义变量
    var match = null;
    var matchLen = 0;
    
    //替换年份
    if (match = format.match(/(y+)/)) {
        match = match[0];
        matchLen = match.length > 4 ? 4 : match.length;
        format = format.replace(match, ('' + this.getFullYear()).substr(4 - matchLen));
    }
    
    //替换毫秒
    if (match = format.match(/(S+)/)) {
        match = match[0];
        matchLen = match.length > 3 ? 3 : match.length;
        format = format.replace(match, (matchLen == 1) ? this.getMilliseconds() : (('000' + this.getMilliseconds()).substr(('' + this.getMilliseconds()).length)));
    }
    
    //不足数据前补零
    for (var key in formats) {
        if (match = format.match(new RegExp('(' + key + ')'))) {
            match = match[0];
            matchLen = match.length > 2 ? 2 : match.length;
            format = format.replace(match, (matchLen == 1) ? (formats[key]) : (('00' + formats[key]).substr(('' + formats[key]).length)));
        }
    }
    
    return format;
}

var smartwork = {};

/**
 * 配置
 * @type 
 */
smartwork.config = {
    pagerHeight : 32 //分页栏高度
    ,inputWidth: 230
    ,colDictValue: 'codeValue'
    ,colDictValueName: 'codeValueName'
    ,colDictDesc: 'codeDesc'
}

/**
 * 字符串或对象是否为空
 * @param val 字符串或对象
 * @param isTrim 是否去除左右空格
 */
smartwork.isEmpty = function(val, isTrim) {
    
    //数据为空
    if (undefined === val || null === val) {
        return true;
    }
    
    //数字处理
    if ('number' == typeof(val) && isNaN(val)) {
        return true;
    }
    
    //字符串单独处理
    if ('string' == typeof(val)) {
        if (isTrim) {
            val = val.trim();
        }
         
        return '' == val;
    }
    
    //其他
    return false;
}

/**
 * 转换数据
 */
smartwork.parseDate = function(datePre, format) {

    //数据为空
    if (this.isEmpty(datePre, true)) {
        return null;
    }
    
    if (datePre instanceof Date) { //日期直接转换
        var date = new Date();
        date.setTime(datePre.getTime());
        return date;
    } else if (typeof(datePre) == 'number') { //数字则直接处理
        var date = new Date();
        date.setTime(datePre);
        return date;
    }
    
    //默认格式化
    format = format || 'yyyy-MM-dd hh:mm:ss';
    
    //解析格式化
    var defaultIndex = 999;
    var formats = [{
        name : 'year'
        ,regx : /(y+)/
        ,index : defaultIndex
        ,length : 4
        ,defValue : new Date().getFullYear() //默认值
    } ,{
        name : 'month'
        ,regx : /(M+)/
        ,index : defaultIndex
        ,length : 2
        ,defValue : 0 //默认值
        ,diffValue : -1 //显示与要求的差异值（月份从0开始）
    }, {
        name : 'day'
        ,regx : /(d+)/
        ,index : defaultIndex
        ,length : 2
        ,defValue : 1 //默认值
    }, {
        name : 'hour'
        ,regx : /(h+)/
        ,index : defaultIndex
        ,length : 2
        ,defValue : 0 //默认值
    }, {
        name : 'min'
        ,regx : /(m+)/
        ,index : defaultIndex
        ,length : 2
        ,defValue : 0 //默认值
    }, {
        name : 'sec'
        ,regx : /(s+)/
        ,index : defaultIndex
        ,length : 2
        ,defValue : 0 //默认值
    }, {
        name : 'ms'
        ,regx : /(S+)/
        ,index : defaultIndex
        ,length : 3
        ,defValue : 0 //默认值
    }];
    
    //统计格式化
    var match = null;
    var defFormatNames = [];
    for (var i = 0; i < formats.length; i++) {
        defFormatNames.push(formats[i].name); //默认顺序解析名称
        
        if (match = format.match(formats[i].regx)) {
            match = match[0]; //获取符合要求的字符串
            formats[i].length = match.length > formats[i].length ? formats[i].length : match.length;
            formats[i].index = format.indexOf(match);
        }
    }
    
    //根据index排序
    formats.sort(function(afterOne, one){
        return afterOne.index - one.index;
    });
    
    //解析数据
    var newFormatNames = [];
    datePre = datePre.replace('T', ' '); //去除T
    if (/[^\d]/.test(datePre)) { //存在分隔符
        
        //获取年月等
        var ymds = datePre.split(/[^\d]/);
        
        for (var i = 0; i < formats.length; i++) {
            newFormatNames.push(formats[i].name); //新顺序解析名称
            
            //没有格式要求或数据量未达到要求，则取默认值
            if (formats[i].index >= defaultIndex || i >= ymds.length) {
                formats[i].value = formats[i].defValue;
                continue;
            }
            
            //获取值
            formats[i].value = this.isEmpty(ymds[i]) ? formats[i].defValue : (parseInt(ymds[i]) + (formats[i].diffValue || 0));
        }
        
    } else { //纯数字，则截取
        
        var startIndex = 0;
        var subStr = null;
        for (var i = 0; i < formats.length; i++) {
            newFormatNames.push(formats[i].name); //新顺序解析名称
            
            //没有格式要求或数据量未达到要求，则取默认值
            if (formats[i].index >= defaultIndex || startIndex >= datePre.length) {
                formats[i].value = formats[i].defValue;
                continue;
            }
            
            //截取字符串
            subStr = datePre.substr(startIndex, formats[i].length);
            
            //获取值
            formats[i].value = this.isEmpty(subStr) ? formats[i].defValue : (parseInt(subStr) + (formats[i].diffValue || 0));
            
            //累加下标
            startIndex += formats[i].length;
        }
    }
    
    //根据年月日获取数据
    var dateValues = [];
    var ymdIndex = 0;
    for (var i = 0; i < defFormatNames.length; i++) {
        ymdIndex = newFormatNames.indexOf(defFormatNames[i]);
        dateValues.push(formats[ymdIndex].value);
    }
    
    //设置时间
    var result = new Date(2000, 1, 1);
    result.setFullYear(dateValues[0]);
    result.setMonth(dateValues[1]);
    result.setDate(dateValues[2]);
    result.setHours(dateValues[3]);
    result.setMinutes(dateValues[4]);
    result.setSeconds(dateValues[5]);
    result.setMilliseconds(dateValues[6]);
    
    //释放
    formats = null;
    defFormatNames = null;
    newFormatNames = null;
    dateValues = null;
    
    return result;
}

/**
 * 格式化日期
 */
smartwork.formatDate = function(value, format) {
    
    //转换成日期
    value = this.parseDate(value, format);
    
    return this.isEmpty(value) ? '' : value.format(format);
}

/**
 * 比较日期
 */
smartwork.compareDate = function(one, other) {
    if (null == one && null == other) {
        return 0;
    } else if (null == one || null == other) {
        return null == one ? -1 : 1;
    }

    return one.getTime() - other.getTime();
}

/**
 * 获取消息弹窗
 */
smartwork.$getMessageBox = function(options) {
    
    var dialog = $('#my-messagebox');
    if (dialog.length <= 0) {
        var buffer = '<div id="my-messagebox" class="modal fade">'
                    + '<div class="modal-dialog" style="width:300px;">'
                      + '<div class="modal-content">'
                        + '<div class="modal-header">'
                          + '<h4 class="modal-title">提示</h4>'
                        + '</div>'
                        + '<div class="modal-body">'
                          + '<i class="icon icon-question-sign" style="float:left;font-size:40px;color:#bd7b46;margin-right:10px;"/>'
                          + '<p name="content" style="line-height:15px;height:100%;padding-top:10px;"></p>'
                        + '</div>'
                        + '<div class="modal-footer">'
                          + '<button name="ok" type="button" class="btn btn-primary"><i class="icon icon-check"></i>确定</button>'
                          + '<button name="cancel" type="button" class="btn btn-default"><i class="icon icon-times"></i>取消</button>'
                        + '</div>'
                      + '</div>'
                    + '</div>'
                  + '</div>';
                  
        //增加配置          
        dialog = $(buffer);    
        $('body:first').append(dialog);
    }
    
    //配置内容
    dialog.find('p[name=content]').html(options.message || '');
    
    //配置确定按钮
    var okBtn = dialog.find('button[name=ok]');
    okBtn.off(); //取消绑定事件
    okBtn.on('click', function(event){
        event.preventDefault();
        dialog.modal('hide');
        if ($.isFunction(options.callback)) {
            options.callback('ok');
        }
    });
    
    //配置取消按钮
    var cacelBtn = dialog.find('button[name=cancel]');
    cacelBtn.off();//取消绑定事件
    if (true === options.confirm) { //确认弹窗
        cacelBtn.css('display', '');
        cacelBtn.on('click', function(event){
            event.preventDefault();
            dialog.modal('hide');
            if ($.isFunction(options.callback)) {
                options.callback('cancel');
            }
        });
    } else {
        cacelBtn.css('display', 'none');
    }
    
    return dialog;
}

/**
 * 获取加载中弹窗
 */
smartwork.$getLoadingBox = function(options) {
    
    var dialog = $('#my-loadingbox');
    if (dialog.length <= 0) {
        var buffer = '<div id="my-loadingbox" class="modal fade">'
                    + '<div class="modal-dialog" style="width:300px;">'
                      + '<div class="modal-content">'
                        + '<div class="modal-body">'
                          + '<i class="icon icon-spin icon-spinner-indicator" style="float:left;font-size:40px;margin-right:10px;"/>'
                          + '<p name="content" style="height:100%;margin-top:10px;">正在操作中...</p>'
                        + '</div>'
                      + '</div>'
                    + '</div>'
                  + '</div>';
                  
        //增加配置          
        dialog = $(buffer);    
        $('body:first').append(dialog);
    }
    
    return dialog;
}

/**
 * 提示
 * @param {} message 消息
 * @param {} timeout 超时时间（毫秒）
 * @param {} callback 点击按钮回调
 */
smartwork.info = function(message, timeout, callback) {
    $.zui.messager.show(message, {
        type: 'info' // 定义颜色主题
        ,icon: 'icon icon-info-sign'
        ,placement: 'center'
        ,time : 1000
    }).show();
    
    //不传超时时间
    if ($.isFunction(timeout)) {
        callback = timeout;
    }
    
    //回调
    if ($.isFunction(callback)) {
        callback();
    }
}

/**
 * 警告
 * @param {} message 消息
 * @param {} callback 点击按钮回调
 */
smartwork.warn = function(message, callback) {
    $.zui.messager.show(message, {
        type: 'warning' // 定义颜色主题
        ,icon: 'icon icon-warning-sign'
        ,placement: 'center'
        ,time : 10000
    }).show();
    
    //回调
    if ($.isFunction(callback)) {
        callback();
    }
}

/**
 * 错误
 * @param {} message 消息 
 * @param {} callback 点击按钮回调
 */
smartwork.error = function(message, callback) {
    $.zui.messager.show(message, {
        type: 'important' // 定义颜色主题
        ,icon: 'icon icon-exclamation-sign'
        ,placement: 'center'
        ,time : 10000
        ,onAction : function() {
            if ($.isFunction(callback)) {
                callback('close');
            }
        }
    }).show();
    
    //回调
    if ($.isFunction(callback)) {
        callback();
    }
}

/**
 * 确认
 * @param {} message 消息
 * @param {} callback 回调
 */
smartwork.confirm = function(message, callback) {
   
    //获取弹窗
    var dialog = this.$getMessageBox({
        message: message
        ,confirm: true
        ,callback : callback
    });
    
    //弹窗
    dialog.modal({'show': true, backdrop:'static'});
    
}

/**
 * 隐藏提示
 */
smartwork.hide = function() {
    $.zui.messager.hide();
    
}

/**
 * 加载中
 * @param {} message 消息
 * @param {} callback 
 */
smartwork.loading = function() {
    //获取弹窗
    var dialog = this.$getLoadingBox({});
    
    //弹窗
    dialog.modal({'show': true, backdrop:'static'});
}

/**
 * 隐藏正在提交
 */
smartwork.hideLoading = function(){
    $('#my-loadingbox').modal('hide');
}

/**
 * 获取控件的标签名
 * @param {} element
 */
smartwork.getLabel = function(element) {
    var labelText = element.parent().find('[for=' + element.attr('id') + ']').text();
    if (!labelText) {
        labelText = element.attr('placeholder');
    }
    if (labelText){
        labelText = labelText.replace(':', '');
    }
    
    return labelText;
}

/**
 * 初始化验证
 * @param {} form
 */
smartwork.initValidate = function(form) {
    
    //已经初始化完成
    if('1' == form.attr('my-validate')) {
        return;
    }
    form.attr('my-validate', '1');
    
    //自定义
    form.validate({
        onfocusout : false
        ,onkeyup : false
        ,showErrors : function(errorMap, errorList) {  
            
            //没有数据
            if (!errorList || !errorList[0]) {
                return;
            }
            
            //获取标签名和错误信息
            var label = smartwork.getLabel($(errorList[0].element));
            var msg = label + errorList[0].message;
            if ('' != msg) {
                smartwork.error(msg);  
            }
       }
    });
}

/**
 * 禁用启用控件
 * @param {} element 控件
 * @param {} disable 禁用
 */
smartwork.disable = function(element, disable) {
    
    if (!disable) {
        element.removeAttr('disabled');
        element.removeClass('disabled');
        element.nextAll('span').removeClass('disabled');
    } else if(!element.hasClass('disabled')) {
        element.attr('disabled', 'disabled');
        element.addClass('disabled');
        element.nextAll('span').addClass('disabled');
    }
    
    //下拉框单独处理
    if (element.hasClass('chosen-select')) {
        $.each(element, function(){
            var that = $(this);
            if (that.hasClass('chosen-select') && that.data('chosen')) {
                that.data('chosen').search_field_disabled();
            }
        });
    }
}

/**
 * 禁用启用控件
 * @param {} element 控件
 * @param {} disable 禁用
 */
smartwork.readonly = function(element, disable) {
    
    if (!disable) {
        element.removeAttr('readonly');
    } else if(!element.hasClass('disabled')) {
        element.attr('readonly', 'readonly');
    }
}

/**
 * 是否只读
 * @param {} element
 */
smartwork.isReadOnly = function(element) {
    return (element.attr('disabled') !== undefined) || (element.attr('readonly') !== undefined);
}

/**
 * 解析json对象
 * @param {} json json对象
 * @param {} path 解析路径（路径直接用.分割）
 * @return {}
 */
smartwork.parseJson = function(json, path) {
        
    //不存在路径或不是json，则直接返回
    if (!path || typeof(json) !== 'object') {
        return json;
    }
    
    // 按路径读取数据
    var paths = path.split('.');
    var pathJson = json;
    for (var i = 0; i < paths.length; i++) {
        pathJson = pathJson[paths[i]];
        if (smartwork.isEmpty(pathJson)) {
            return undefined;
        }
    }
    
    paths = null;
    return pathJson;
}

/**
 * ajax提交
 * @param {} options
 * @param {} successCallback
 * @param {} errorConfirmCallback
 * @return {}
 */
smartwork.ajax = function(options, successCallback, errorConfirmCallback) {
    
    //默认配置
    var defaultConfig = {
        type: 'post'
        ,async : true
        ,dataType: 'json'
        ,timeout: 5 * 60 * 1000 //请求超时时间
        ,success: function(result){
            var that = this;
            
            //隐藏加载中
            if (that['show-loading']) {
                smartwork.hideLoading();
            }
            
            //成功
            if (result.success) {
                if ($.isFunction(that.successCallback)) {
                    that.successCallback.call(that, result);
                }
                return;
            }
                
            //获取异常信息
            var errorCode = smartwork.parseJson(result, 'errors.errorCode');
            var message = smartwork.parseJson(result, 'errors.errorMessage');
            
            //弹错误窗口
            smartwork.error(message, function(action) {
                if ($.isFunction(that.errorConfirmCallback)) {
                    that.errorConfirmCallback.call(that, errorCode, message);
                }
                
                //释放
                errorCode = null;
                message = null;
           });
        }
        ,error: function(e, statusText){
            var that = this;
            
            //隐藏加载中
            if (that['show-loading']) {
                smartwork.hideLoading();
            }
            
            if ('abort' == e.statusText) { //取消操作
                return;
            } else if ('timeout' == e.statusText) { //请求超时
                smartwork.error('服务访问超时', that.errorConfirmCallback);
            } else if (0 === e.readyState) {
                smartwork.error('服务访问失败', that.errorConfirmCallback);
            } else if (404 === e.status) {
                smartwork.error('不存在action：' + that.url, that.errorConfirmCallback);
            } else {
                smartwork.error('服务访问失败：' + that.url, that.errorConfirmCallback);
            }
        }
    };
    
    //覆盖
    options = $.extend(true, defaultConfig, options);
    options.successCallback = successCallback;
    options.errorConfirmCallback = errorConfirmCallback;
    
    //显示加载中
    if (options && options['show-loading']) {
        smartwork.loading();
    }
    
    return $.ajax(options);
}

/**
 * 是否是IE
 * @param {} version
 */
smartwork.isIE = function(version) {
    return (window.navigator.userAgent.indexOf("MSIE") >= 1) || ("ActiveXObject" in window);
}

/**
 * 表格远程配置
 * @param {} options
 */
smartwork.gridRemoteConfig = function(options) {
    
    //默认配置
    var defaultConfig = {
        type: 'post'
        ,dataType: 'json'
        ,async : true
        ,timeout: 5 * 60 * 1000 //请求超时时间
    };
    
    //覆盖
    options = $.extend(true, defaultConfig, options);
    
    return options;
}

/**
 * 表格转换后台查询数据
 * @param {} responseData 返回的数据
 * @param {} textStatus 
 * @param {} jqXHR
 * @param {} gridObj 表格对象
 * @return {}
 */
smartwork.gridRemoteConverter = function(responseData, textStatus, jqXHR, gridObj) {
    var result = {};
    
    var isPager = $.isArray(smartwork.parseJson(responseData, 'root.rs'));
    result['result'] = (true === smartwork.parseJson(responseData, 'success')) ? 'success' : 'failure';
    result['message'] = smartwork.parseJson(responseData, 'errors.errorMessage');
    result['data'] = smartwork.parseJson(responseData, isPager ? 'root.rs' : 'root') || [];
    result['pager'] = {recTotal: isPager ? smartwork.parseJson(responseData, 'root.pager.totalRows') : result['data'].length};
    
    return result;
}

/**
 * 删除所有数据
 * @param {} grid 表格元素
 */
smartwork.gridRemoveAll = function(gridObj) {
    var gridDatas = gridObj.getData();
    
    if (gridDatas.length > 0) {
        smartwork.gridRemove(gridObj, gridDatas);
    }
}

/**
 * 表格查询
 * @param {} gridObj 表格对象
 */
smartwork.gridQuery = function(gridObj) {
    gridObj.dataSource.array = null; //取消静态数据
    gridObj.setPager(gridObj.pager.page, 0, gridObj.pager.recPerPage); //重置总行数
   // gridObj.scroll(0, 0); //滚动条到原位
    smartwork.gridHighlightSelect(gridObj, -1);
    gridObj.search('&nbsp;' + Math.random()); //要触发查询，就要改变查询数据
}

/**
 * 增加表格数据
 * @param {} gridObj
 * @param {} datas
 * @param {} handRender 手动显示
 */
smartwork.gridAdd = function(gridObj, datas, handRender) {
    
    //默认数组
    if (!$.isArray(datas)) {
        datas = [datas];
    }
    
    var gridDatas = gridObj.getData(); //获取所有数据
    $.each(datas, function(index, item){
        gridDatas.push(item);
    });
    
    gridObj.setPager(-1, gridDatas.length, -1); //修改总数，以防不显示
    if (!handRender) {
        gridObj.renderData(); //显示所有数据
    }
}

/**
 * 表格选择行
 * @param {} gridObj
 * @param {} rowIndex
 */
smartwork.gridHighlightSelect = function(gridObj, rowIndex) {
    
    var grid = gridObj.$;
    
    //保存当前选中行和上一次选中行
    grid.attr('my-select-last', (grid.attr('my-select-curr') || '-1')).attr('my-select-curr', rowIndex);
    
    //取消上一个选中
    grid.find('div[my-lighlight=1]').removeClass('my-cell-selected').attr('my-lighlight', '0');
        
    //选中当前
    if (-1 != rowIndex) {
        var cells = grid.find('div[data-row={0}][data-type=cell]'.format(rowIndex));
        if ('1' != cells.attr('my-lighlight')) {
            cells.addClass('my-cell-selected').attr('my-lighlight', '1');
        }
    }
}

/**
 * 回滚表格选择行
 * @param {} gridObj
 */
smartwork.gridRollbackHighlightSelect = function(gridObj) {
    var grid = gridObj.$;
    smartwork.gridHighlightSelect(gridObj, grid.attr('my-select-last'));
}

/**
 * 增加表格数据
 * @param {} gridObj
 * @param {} data
 */
smartwork.gridRemove = function(gridObj, datas) {
    
    //默认数组
    if (!$.isArray(datas)) {
        datas = [datas];
    }
    
    var gridDatas = gridObj.getData(); //获取所有数据
    $.each(datas, function(index, item){
        gridDatas.splice($.inArray(item, gridDatas), 1);
    });
    
    gridObj.setPager(-1, gridDatas.length, -1); //修改总数，以防多显示
    gridObj.renderData(); //显示所有数据
}

/**
 * 表格数据修改界面调整
 * @param {} gridObj
 */
smartwork.gridModifyLayout = function(gridObj) {
    var scrollLeft = gridObj.layout.scrollLeft;
    if (scrollLeft > 0) {
        gridObj.scroll(scrollLeft - 1, null);
        gridObj.scroll(scrollLeft, null);
    }
}

/**
 * 单元格式化
 * @param {} $cell
 * @param {} cell
 * @param {} gridObj
 */
smartwork.gridCellFormator = function($cell, cell, gridObj) {
    
    var isCheckbox = cell.config.checkbox || ((0 == cell.colIndex) && gridObj.options.checkable);
    var $content = isCheckbox ? $cell.find('.content') : $cell;
    if (cell.config.html) {
        $content.html($('<span/>').html(cell.value));
    } else {
        $content.text(cell.value);
    }
    
    //复选框结束
    if (isCheckbox) {
        return;
    }
    
    if (cell.rowIndex <= 0) {
        return;
    }
    
    //增加行选中高亮显示
    if (cell.colIndex > 0 && '1' != $cell.attr('my-select-highlight')) {
        $cell.attr('my-select-highlight', '1');
        $cell.click(function(){
            smartwork.gridHighlightSelect(gridObj, cell.rowIndex);
        });
    }
    
    //提示
    if (true === cell.config.tip && '1' != $cell.attr('my-popover')) {
        $cell.attr('my-popover', '1');
        
        $cell.hover(function () {
                
            //获取列宽和内存宽
            var width = $cell.width();
            var contentWidth = $cell.children().outerWidth() || 9999;
            
            //判断是否要显示
            if (smartwork.isIE() && (contentWidth > width - 30) && (contentWidth < width - 10)){
            } else if (contentWidth > width) {
            } else {
                return;
            }
            
            //初始化提示
            if ('popover' != $cell.attr('data-toggle')) {
                var parent = $cell.parents('.modal.fade');
                
                $cell.popover({
                    trigger: 'manual'
                    ,placement: 'top'
                    ,container: parent.length > 0 ? parent : 'body'
                });
                $cell.attr('data-toggle', 'popover'); 
            }
            
            //显示
            $cell.attr('data-content', $cell.text());
            $cell.popover('show');
        }
        ,function () {
            if ('popover' == $cell.attr('data-toggle')) {
                $cell.popover('hide');
            }
        });
        
        //点击关闭
        $cell.click(function() {
            $cell.parents('.datagrid').find('[data-toggle]').popover('hide');
        });
    }
}

/**
 * 是否单元格处理
 */
smartwork.gridYesNoCellGetter = function(dataValue, cell, gridObj) {
    return 1== dataValue ? '<i class="icon icon-checked"></i>' : '<i class="icon icon-check-empty"></i>';
}

/**
 * 全部取消选中
 */
smartwork.uncheckAll = function(gridObj) {
    var selections = gridObj.states.selections;
    for (var item in selections) {
        delete selections[item];
    }
}

/**
 * 控件提示
 * @param {} element 控件
 * @param {} isAdd 增加或删除
 * @param {} message 提示消息
 */
smartwork.elementTip = function(element, isAdd, message) {
    
    //删除
    if (!isAdd) {
        element.find('span.label-badge').remove();
        return;
    }
    
    //增加冒泡
    if (element.find('span.label-badge').length <= 0) {
        element.append('<span class="label label-badge label-warning my-label-badge">1</span>');
    }
    
    //增加提示
    if ('1' != element.attr('my-popover')) {
        element.attr('my-popover', '1');
        
        //初始化提示
        if ('popover' != element.attr('data-toggle')) {
            element.popover({
                trigger: 'manual'
                ,placement: 'bottom'
                ,container: 'body'
            });
            element.attr('data-toggle', 'popover'); 
        }
            
        //显示
        element.hover(function () {
            if (element.find('span.label-badge').length <= 0) {
                return;
            }
            element.popover('show');
        }
        ,function () {
            if ('popover' == element.attr('data-toggle')) {
                element.popover('hide');
            }
        });
        
        //点击关闭
        element.click(function() {
            element.popover('hide');
        });
    }
    
    //修改提示消息
    element.attr('data-content', message);
}

/**
 * 获取上传配置
 * @param {} options
 */
smartwork.uploadConfig = function(options, responseSuccessCallback) {
    
    var defaultConfig = {
        autoUpload: true
        ,dropPlaceholder: false
        ,multi_selection: false
        ,file_data_name: 'file'
        ,limitFilesCount: 1
        ,filters: {
            mime_types: [{title: 'ESB文件', extensions: 'esb'}]
            ,max_file_size: '2mb'
        }
        ,responseHandler: function(responseObject, file) {
            
            var result = JSON.parse(responseObject.response);
            if (!result.success) {
                smartwork.hide();
                smartwork.error(smartwork.parseJson(result, 'errors.errorMessage'));
            } else {
                smartwork.hide();
                smartwork.info('导入完成');
                
                //回调
                if ($.isFunction(responseSuccessCallback)) {
                    responseSuccessCallback();
                }
            }
            
            //删除所有文件
            var uploaderObj = this;
            var files = uploaderObj.getFiles();
            $.each(files, function(index, item) {
                uploaderObj.removeFile(item);
            });
        }
        ,onBeforeUpload: function() {
            smartwork.hide();
        }
        ,onError: function(error) {
            
            smartwork.hide();
            smartwork.error(error.message);
            
            //删除所有文件
            var uploaderObj = this;
            var files = uploaderObj.getFiles();
            $.each(files, function(index, item) {
                uploaderObj.removeFile(item);
            });
        }
    };
    
    return $.extend(true, defaultConfig, options);
}

/**
 * 下载文件
 * @param {} url 地址
 * @param {} params 参数
 */
smartwork.download = function(url, params) {
    
    //获取下载对象
    var downObj = $('#id-download');
    if (downObj.length <= 0) {
        downObj = $('<div id="id-download" style="display:none;"/>')
                    .html('<iframe name="download-file" src=""/><form action="" method="post" target="download-file"/>')
                    .appendTo('body:first');
    }
    
    //获取form对象
    var formObj = downObj.find('form');
    
    //设置属性
    formObj.attr('action', url);
    
    //设置参数
    formObj.children().remove();
    params = params || {};
    for (var item in params) {
        
        //单值
        if (!$.isArray(params[item])) {
            formObj.append($('<input/>', {'type': 'hidden', 'name': item, 'value': params[item]}));
            continue;
        }
        
        //多值
        $.each(params[item], function(index, value){
            formObj.append($('<input/>', {'type': 'hidden', 'name':item, 'value': value}));
        }); 
    }
    
    //提交
    formObj.submit();
    
    //取消url
    formObj.attr('action', '');
}

/**
 * 设置form的值
 * @param {} form
 * @param {} values
 */
smartwork.setFormValue = function(form, values) {
   
    if (!values) {
        return;
    }
    
    form.find('[name]').each(function(index, item){
        var that = $(this);
        var value = values[that.attr('name')];
        if (smartwork.isEmpty(value)) {
            value = '';
        }
        
        //chechbox和radio
        if ($.inArray(that.attr('type'), ['checkbox', 'radio']) != -1) {
            if (!smartwork.isEmpty(that.attr('value'))) {
                that.prop('checked', value == that.attr('value'));
            } else {
                that.prop('checked', 0 != value);
            }
        } else if (that.hasClass('chosen-select')) { //下拉框
            
            //赋值
            that.val(value); 
            
            //获取选中文本
            var text = null;
            if ('1' == that.attr('use-query')) { //下拉框允许使用输入数据
                if (that.get(0).selectedIndex < 0) {
                    that.find('option:eq(1)').attr('value', value);
                    text = value;
                    that.get(0).selectedIndex = 1;
                } else if (1 == that.get(0).selectedIndex) {
                    text = value;
                } else {
                    text = that.find("option:selected").text();
                }
            } else {
                text = that.find("option:selected").text();
            }
            
            //修改显示文本
            var chosen = that.nextAll('.chosen-container');
            chosen.find('a:first').removeClass('chosen-default').find('span').attr('title', text).text(text);
            
        } else {
            that.val(value);
        }
    });
}

/**
 * 获取form的值
 * @param {} form
 */
smartwork.getFormValue = function(form) {
    var values = form.serializeArray();
   
    var result = {};
    $.each(values, function(index, item){
        if (smartwork.isEmpty(item.name)) {
            return;
        }
        
        //多值
        if (undefined !== result[item.name]) {
            
            if (!$.isArray(result[item.name])) {
                result[item.name] = [result[item.name]];
            }
            result[item.name].push(('' + item.value).trim());
            return;
        }
        
        //单值
        result[item.name] = ('' + item.value).trim();
    });
    
    //遍历checkbox
    form.find('[type=checkbox]').each(function(){
        var that = $(this);
        var name = that.attr('name');
        if (smartwork.isEmpty(name)) {
            return;
        }
        
        //设置默认值
        if (!(name in result)) {
            result[name] = that.is(':checked') ? 1 : 0;
        }
    });
    
    return result;
}

/**
 * 追加json的名称
 * @param {} values
 * @param {} prefix
 */
smartwork.prefixName = function(values, prefix) {
    var result = {};
    
    for (var item in values) {
        result[prefix + item] = values[item];
    }
    
    return result;
}

/**
 * 加载数据字典
 * @param {} toArray
 * @param {} codeType
 * @param {} successFn
 * @param {} failureFn
 */
smartwork.loadDataDict = function(codeType, successFn, failureFn) {
    
    smartwork.ajax({
        url : '../pub/QueryDataDictByCodeType.do'
        ,data: {codeType: codeType}
    }, function(result){
        
        //复制数据
        var array = smartwork.parseJson(result, 'root');
        var toArray = [];
        $.each(array, function(index, item){
            toArray.push(item);
        });
        result['root'] = null;
        
        if ($.isFunction(successFn)) {
            successFn(toArray);
        }
        
    }, function(){
        if ($.isFunction(failureFn)) {
            failureFn();
        }
    });
}

/**
 * 获取json对象
 * @param {} array 数据字段数据
 * @param {} value 值
 */
smartwork.getObjectItem = function(array, value) {
    
    //数据为空
    if (null == array) {
        return null;
    }
    
    //遍历
    for (var i = 0; i < array.length; i++) {
        if (array[i][smartwork.config.colDictValue] == value) {
            return array[i];
        }
    }
    
    return null;
}

/**
 * 获取数据字典文本
 * @param {} array
 * @param {} value
 */
smartwork.getDictText = function(array, value) {
    var obj = smartwork.getObjectItem(array, value);
    if (null == obj) {
        return value;
    }
    
    return obj[smartwork.config.colDictValueName];
}

/**
 * 获取第一个值
 * @param {} array
 */
smartwork.getDictFirstValue  = function(array) {
    if (null == array || array.length <= 0) {
        return null;
    }
    
    return array[0][smartwork.config.colDictValue];
}

/**
 * 获取自定义字段类型
 * @param {} gridObj 表对象
 * @param {} arrayFieldType 字段类型字典
 * @param {} arrayUserFieldTypeIndex 自定义字段类型下标字典
 * @param {} cell 单元格信息
 */
smartwork.getUserFieldType = function(gridObj, arrayFieldType, arrayUserFieldTypeIndex, cell) {
    var fieldType = smartwork.parseJson(cell, 'config.data.fieldType');
    var isUserFieldType = '0' == fieldType;
    var userFieldType = isUserFieldType ? smartwork.parseJson(cell, 'config.data.userFieldType') : smartwork.getDictText(arrayFieldType, fieldType);
    
    //默认普通字段
    if (!userFieldType) {
        userFieldType = smartwork.getDictText(arrayFieldType, smartwork.getDictFirstValue(arrayFieldType)) || '';
    }
    
    //修改表格数据
    if (!isUserFieldType) {
        gridObj.getData()[cell.rowIndex - 1]['userFieldType'] = userFieldType;
    }
    
    //拼接文本
    var text = '';
    var index = userFieldType.indexOf('1');
    while (-1 != index) {
        text += '+' + smartwork.getDictText(arrayUserFieldTypeIndex, index + 1);
        index = userFieldType.indexOf('1', index + 1);
    }
    
    //默认文本
    if ('' == text) {
        text = smartwork.getDictText(arrayUserFieldTypeIndex, smartwork.getDictFirstValue(arrayUserFieldTypeIndex)) || '';
    } else {
        text = text.substring(1);
    }
    
    return text;
}

/**
 * 查找下标
 * @param {} array json数组
 * @param {} fields 匹配字段
 * @param {} data json数据
 * @param {} startIndex 查询开始行
 */
smartwork.indexOf = function(array, fields, data, startIndex) {
    
    //没有数据
    if (null == array || array.length <= 0) {
        return -1;
    }
    
    //默认数组
    if (!$.isArray(fields)) {
        fields = [fields];
    }
    
    for (var i = (startIndex || 0); i < array.length; i++) {
        
        var macth = true;
        for (var k = 0; k < fields.length; k++) {
            if (array[i][fields[k]] != data[fields[k]]) {
                macth = false;
                break;
            }
        }
        
        if (macth) {
            return i;
        }
    }
    
    return -1;
}

/**
 * 初始化下拉框
 * @param {} combox
 * @param {} array
 * @param {} options
 */
smartwork.initCombox = function(combox, array, options) {
    
    //已经初始化完成
    if('1' == combox.attr('my-init')) {
        return;
    }
    combox.attr('my-init', '1');
    
    //获取自定义数据的值
    options = options || {};
    var useQuery = options['use_query'] || false;
    var showText  = options['show_text'] || false;
    var column = options['column'] || 1;
    
    //删除自定义数据
    delete options['use_query'];
    delete options['show_text'];
    delete options['column'];
    
    //增加下拉框显示
    $.each(array, function(index, item){
        var value = item[smartwork.config.colDictValue];
        var text = showText ? item[smartwork.config.colDictValueName] : value;
        combox.append($('<option/>', {'value': value}).text(text));
    });
    
    //默认配置
    var defaultConfig = {
        disable_search: true
        ,search_contains: true
        ,inherit_select_classes: true
        ,middle_highlight: true
    };
    
    //初始化下拉框对象
    options.width = (smartwork.config.inputWidth * column + 10 * (column - 1)) + 'px';
    combox.chosen($.extend(defaultConfig, options));
    
    //调整自定义下拉框
    var chosen = combox.nextAll('.chosen-container');
    chosen.append(combox.nextAll('label').attr('for', chosen.attr('id'))); //将标签放入下拉框内
    chosen.find('a:first').addClass('form-control'); //增加样式
    
    //修改方法
    if (useQuery) {
        
        //设置属性
        combox.attr('use-query', '1');
        var comboxObj = combox.data('chosen');
        
        //无结果时默认为查询
        comboxObj.update_results_content = function(content) {
            
            //默认为查询数据
            if ('' === content) {
                
                //获取搜索文本
                var searchText = comboxObj.get_search_text();
                
                //下拉显示内容
                content = '<li title="" class="active-result" data-option-array-index="1"><em></em>' + searchText + '</li>';
                
                //修改预留项的值
                combox.find('option:eq(1)').attr('value', searchText);
                
                //修改文本，否则选择后没有内容
                var seachItem = comboxObj.results_data[1];
                seachItem['text'] = searchText;
            }
            
            return this.search_results.html(content);
        }
        
        //不返回无结果提示
        comboxObj.no_results = function(terms) {
            return '';
        }
        
        //无结果时搜索内容默认
        combox.on('chosen:showing_dropdown', function(){
            
            //预留项时，搜索内容为预留项的值
            if (1 == combox.get(0).selectedIndex) {
                chosen.find('input:first').val(combox.val());
                comboxObj.results_search();
            }
        });
    }
}

/**
 * 初始化日期时间控件
 * @param {} element
 * @param {} options
 */
smartwork.initDateTime = function(element, options) {
    
    if ('1' == element.attr('my-init')) {
        return;
    }
    element.attr('my-init', '1');
    
    //默认配置
    var defaultConfig = {
        language:  'zh-CN'
        ,weekStart: 1
        ,todayBtn:  1
        ,autoclose: 1
        ,todayHighlight: 1
        ,startView: 2
        ,forceParse: 0
        ,showMeridian: 1
        ,format: 'yyyy-mm-dd hh:ii:ss'
    };
    
    options = options ||{};
    if (options.format) {
        options.format = options.format.replace(/m/g, 'i').replace(/M/g, 'm')
    }
    
    $(".form-datetime").datetimepicker($.extend(defaultConfig, options));
}


//增加额外验证
$(document).ready(function(){
   
    //增加正则表达式验证
    if (jQuery.validator) { 
        jQuery.validator.addMethod('regex', 
            function(value, element, params) { 
                if ('' === value) {
                    return true;
                }
                
                var exp = new RegExp(params);
                return exp.test(value);
            }, 
            '数据格式错误');
    }
    
    //修改表格方法
    if ($.fn.datagrid) {
        var DataGrid = $.fn.datagrid.Constructor;
        DataGrid.prototype.showMessage = function(message, type, autoCloseTime) {
            var that = this;
            
            smartwork.hide();
            smartwork.error(message);
            
            //设置dataId，以防tab切换时重复查询
            var params = that.getFilterParams();
            var dataId = [params.page, params.recPerPage, params.search, params.sortBy, params.order].join('&');
            that.dataSource.dataId = dataId;
        }
        
        //修改BUG
        DataGrid.prototype.renderFixeds = function() {
            var that   = this;
            var states = that.states;
            var layout = that.layout;
    
            that.$cells.find('.datagrid-fixed').removeClass('datagrid-fixed');
            that.$cells.find('.datagrid-fixed-edge-top').removeClass('datagrid-fixed-edge-top');
            that.$cells.find('.datagrid-fixed-edge-bottom').removeClass('datagrid-fixed-edge-bottom');
            that.$cells.find('.datagrid-fixed-edge-left').removeClass('datagrid-fixed-edge-left');
            that.$cells.find('.datagrid-fixed-edge-right').removeClass('datagrid-fixed-edge-right');
    
            if (layout.vScrollSpare) {
                var fixedTopUntil = states.fixedTopUntil;
                if (typeof fixedTopUntil === 'number' && fixedTopUntil > -1) {
                    fixedTopUntil = Math.min(fixedTopUntil, layout.rowsLength);
                    for (var i = 0; i <= fixedTopUntil; ++i) {
                        var rowLayout = that.getRowLayout(i);
                        var $row = $('#' + that.id + '-row-' + i);
                        var fixedTop = layout.partialRendering ? rowLayout.top : (rowLayout.top + layout.scrollTop);
                        $row.addClass('datagrid-fixed').css('top', fixedTop);
                        if (i === fixedTopUntil && layout.scrollTop) {
                            $row.addClass('datagrid-fixed-edge-top');
                        }
                    }
                } else {
                    fixedTopUntil = -1;
                }
                var fixedBottomFrom = states.fixedBottomFrom;
                if (typeof fixedBottomFrom === 'number' && fixedBottomFrom > -1) {
                    fixedBottomFrom = Math.max(fixedTopUntil > -1 ? (fixedTopUntil + 1) : 1, Math.min(fixedBottomFrom, layout.rowsLength));
                    for (var i = fixedBottomFrom; i < layout.rowsLength; ++i) {
                        var rowLayout = that.getRowLayout(i);
                        var $row = $('#' + that.id + '-row-' + i);
                        var fixedTop = layout.partialRendering ? (rowLayout.top - layout.vScrollSpare) : (rowLayout.top - layout.vScrollSpare + layout.scrollTop);
                        $row.addClass('datagrid-fixed').css('top', fixedTop);
                        if (i === fixedBottomFrom && layout.scrollTop < layout.vScrollSpare) {
                            $row.addClass('datagrid-fixed-edge-bottom');
                        }
                    }
                }
            }
    
            //移动横向滚动条后layout.cols会为null
            if (layout.hScrollSpare) {
                var fixedLeftUntil = states.fixedLeftUntil;
                if (typeof fixedLeftUntil === 'number' && fixedLeftUntil > -1 && layout.cols) {
                    fixedLeftUntil = Math.min(fixedLeftUntil, layout.colsLength);
                    for (var i = 0; i <= fixedLeftUntil; ++i) {
                        var colLayout = layout.cols[i];
                        var $cols = that.$cells.find('.datagrid-cell[data-col="' + i + '"]');
                        var fixedLeft = colLayout.left + layout.scrollLeft - layout.borderWidth;
                        $cols.addClass('datagrid-fixed').css('left', fixedLeft);
                        if (i === fixedLeftUntil && layout.scrollLeft) {
                            $cols.addClass('datagrid-fixed-edge-left');
                        }
                    }
                } else {
                    fixedLeftUntil = -1;
                }
                var fixedRightFrom = states.fixedRightFrom;
                if (typeof fixedRightFrom === 'number' && fixedRightFrom > -1 && layout.cols) {
                    fixedRightFrom = Math.max(fixedLeftUntil > -1 ? (fixedLeftUntil + 1) : 1, Math.min(fixedRightFrom, layout.colsLength));
                    for (var i = fixedRightFrom; i < layout.colsLength; ++i) {
                        var colLayout = layout.cols[i];
                        var $cols = that.$cells.find('.datagrid-cell[data-col="' + i + '"]');
                        var fixedLeft = colLayout.left - layout.hScrollSpare + layout.scrollLeft;
                        $cols.addClass('datagrid-fixed').css('left', fixedLeft);
                        if (i === fixedRightFrom && layout.scrollLeft < layout.hScrollSpare) {
                            $cols.addClass('datagrid-fixed-edge-right');
                        }
                    }
                }
            }
        };
    }
});
    
    