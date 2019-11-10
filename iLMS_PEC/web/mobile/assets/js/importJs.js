function getContextPath(){   
    var pathName = document.location.pathname;
    var wxIndex = pathName.indexOf("/mobile/");
    if (wxIndex == -1) {
    	wxIndex = pathName.indexOf("/main/");
    }
    if (wxIndex == -1) {
    	wxIndex = pathName.indexOf("/form/");
    }
    var result = pathName.substr(0,wxIndex);   
    return result;   
}
var __ctx = getContextPath();

document.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
document.write("<meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1\">");
document.write("<meta name=\"renderer\" content=\"webkit\">");
document.write("<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />");
document.write("<link rel=\"icon\" type=\"image/png\" href=\""+__ctx+"/mobile/assets/img/i-f7.png\">");
document.write("<meta name=\"mobile-web-app-capable\" content=\"yes\">");
document.write("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
document.write("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">");
document.write("<meta name=\"msapplication-TileColor\" content=\"#0e90d2\">");
document.write("<meta charset=\"UTF-8\">");
document.write("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\">");

var aryCss__=["/mobile/assets/css/light7.min.css",
              "/mobile/assets/css/font-awesome.min.css",
              "/mobile/assets/css/mobile.css",
              "/mobile/assets/css/light7-swiper.min.css",
              "/mobile/assets/css/light7-swipeout.css",
              "/mobile/assets/css/style.css",
              "/mobile/assets/css/newMobile.css"];

var aryJs__=["/mobile/assets/js/jquery.min.js",
            "/mobile/assets/js/light7.min.js",
            "/mobile/assets/js/i18n/cn.js",
            "/mobile/assets/js/angular.min.js",
            "/js/lib/angular/angular-sanitize.js",
            "/mobile/assets/js/BaseService.js",
            "/js/platform/system/customQuery/customQueryService.js",
            "/mobile/assets/js/mobileDirective.js",
            "/mobile/assets/js/plugin/custFormHelper.js",
            "/js/platform/util/customUtil.js",
            "/mobile/assets/js/light7-swiper.min.js",
            "/mobile/assets/js/light7-swipeout.js",
            "/mobile/assets/js/dialog/Dialogs.js",
            "/mobile/assets/js/util.js",
            "/mobile/assets/js/WxUtil.js"];

//流程页面需要的css js
var flowAryCss =["/mobile/assets/js/ztree/css/zTreeStyle/zTreeStyle.css",
                 "/mobile/assets/js/ztree/css/outLook.css",
                 "/mobile/assets/css/mobiscroll.custom-3.0.0.css",
                 "/mobile/assets/css/jquery.qtip.css",
                 "/mobile/assets/css/mobile.css"];

var flowAryJs = [
                 "/mobile/assets/js/plugin/mobiscroll.custom-3.0.0.min.js",  
                 "/mobile/assets/js/plugin/CustomValid.js",
                 "/js/common/customform/directiveTpl.js",  // 表单公共
                 "/js/common/customform/FormDirective.js", // 表单公共
                 "/js/common/service/FormService.js", 		// 表单公共
                 
                 "/mobile/assets/js/plugin/jquery.qtip.min.js",
                 "/mobile/assets/js/ztree/js/jquery.ztree.all-3.5.min.js",
                 "/mobile/assets/js/ztree/ZtreeCreator.js",
                 "/mobile/assets/js/dialog/Dialogs.js",
                 "/mobile/assets/js/bpm/FlowService.js"];

var flowInstAryJs = ["/js/common/base/customValid.js",
                "/js/common/customform/directiveTpl.js",
                "/js/common/customform/FormDirective.js",
                "/js/common/service/FormService.js",
                "/mobile/assets/js/plugin/mobiscroll.custom-3.0.0.min.js",  
                "/mobile/assets/js/plugin/jquery.qtip.min.js",
                "/mobile/assets/js/ztree/js/jquery.ztree.all-3.5.min.js",
                "/mobile/assets/js/ztree/ZtreeCreator.js",
                "/mobile/assets/js/bpm/FlowService.js",
                "/mobile/assets/js/bpm/processInfoController.js"];

// 解析栏目的js
var parseHTMLJS = ["/js/hotent/index/jquery.blockUI.min.js",
               "/js/hotent/index/jquery.slimscroll.min.js",
               "/js/echarts/echarts.js",
               "/js/hotent/index/indexPage.js"];

var loginCss = ["/mobile/assets/css/login.css"];
var validateJs = ["/mobile/assets/js/plugin/CustomValid.js",
                  "/js/common/service/FormService.js",
                  "/mobile/assets/js/bpm/FormDirective.js"];
/**
 * js引入时导入必须的css样式。
 */
for(var i=0;i<aryCss__.length;i++){
	var str="<link rel=\"stylesheet\" href=\""+__ctx + aryCss__[i] +"\">";
	document.write(str);
}

/**
 * js引入时导入必须的js文件。
 */
for(var i=0;i<aryJs__.length;i++){
	var str="<script src=\""+__ctx + aryJs__[i] +"\"></script>";
	document.write(str);
}

/**
 * 外部导入的js文件。
 */
function importJs(aryJs){
	for(var i=0;i<aryJs.length;i++){
		var str="<script src=\""+__ctx + aryJs[i] +"\"></script>";
		document.write(str);
	}
}

/**
 * 外部导入css。
 */
function importCss(aryCss){
	for(var i=0;i<aryCss.length;i++){
		var str="<link rel=\"stylesheet\" href=\""+__ctx + aryCss[i] +"\">";
		document.write(str);
	}
}