/**
 * 引入页面禁用回车键刷新，在输入框按下回车键则进行查询（适用于搜索框）
 */
$(function() {
	document.onkeydown = function(event) {  
        var target, code, tag;  
        if (!event) {  
            event = window.event; //针对ie浏览器  
            target = event.srcElement;  
            code = event.keyCode;  
            if (code == 13) {  
                tag = target.tagName;  
                if (tag == "TEXTAREA") { return true; }  
                else { return false; }  
            }  
        }  
        else {  
            target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
            code = event.keyCode;  
            if (code == 13) {  
                tag = target.tagName;  
                if (tag == "INPUT") { 
                	$(".btn.btn-primary.btn-sm.fa-search").click();
                	return false; 
                }  
                else { return true; }  
            }  
        }  
    };  
});