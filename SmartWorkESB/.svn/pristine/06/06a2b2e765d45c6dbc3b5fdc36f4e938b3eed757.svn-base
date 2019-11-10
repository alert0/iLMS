
//JSON.stringify
$(document).ready(function(){
    
    //重写验证结果
    var validator = $('form:first').validate({
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
    
    //提交
    var submit = function() {
    
        //获取按钮
        var button = $('form:first button');
        
        //不可操作
        if (smartwork.isReadOnly(button)) {
            return;
        }
        
        //禁用按钮
        smartwork.disable(button, true);
        
        //隐藏提示
        smartwork.hide();
        
        //验证不通过
        if(!$('form:first').valid()) {
            smartwork.disable(button, false); //启用按钮
            return;
        }
        
        //提交
        smartwork.ajax({
            url : 'pub/Login.do'
            ,data : {
                userName : $('form:first input[name="userName"]').val()
                ,password : $('form:first input[name="password"]').val()
            }
        }, function(result){
            smartwork.disable(button, false); //启用按钮
            window.location = 'main.html?v=1.0';
        }, function() {
            smartwork.disable(button, false); //启用按钮
        });
    }
    
    //增加按钮点击时间
    $('form:first button').click(function(event) {
        submit();
    });
    
    //用户名、密码回车
    $('form:first input[name]').keyup(function(event) {
        if (13 == event.keyCode) {
            submit();
        }
    });
});