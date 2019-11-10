<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="/commons/include/ngEdit.jsp"%>

<script type="text/javascript" src="${ctx}/js/hotent/index/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/js/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/indexPage.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/myHome.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/greensock.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/layerslider.transitions.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/layerslider.kreaturamedia.jquery.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/index/jquery.carouFredSel.min.js" ></script>

<f:link href="portal/assets/myHome.css"></f:link>
<f:link href="portal/assets/style.css"></f:link>
<f:link href="other/myHome-other.css"></f:link>
</head>
<script type="text/javascript">
/*
 * 个人工作台中更多按钮
 */
function parentAddTab(url,name,id){
	var currentmenu = {};
	currentmenu.defaultUrl = url;
	currentmenu.icon = '';
	currentmenu.name = name;
	currentmenu.id = id;
	parent.parent.addTab(currentmenu);
}
var height;
$(function(){
	$.isShowProcessBar=false;

	// 获取待办事项列表 实际上只需要获取待办事项的总数 但没有更好的接口 只能这么滴了
	var getTodoCount = (function() {
		var jqXhr = null;

		return function () {
			if (jqXhr) {
				jqXhr.abort();
			}

			jqXhr =  $.post('/x5/office/receivedProcess/pendingJson', {page: 1, rows: 1, sort: 'create_time_', order: 'des'});

			return jqXhr.then(function(res) {
				return res.total;
			}).always(function() {
				jqXhr = null;
			});
		}
	})();

	var getUnreadMailCount = (function() {
		var jqXhr = null;

		return function (data) {
			if (jqXhr) {
				jqXhr.abort();
			}

			jqXhr = $.post('/x5/mail/mail/mail/listJson?types=1&mailSetId=' + data.mailSetId, {
				'Q^IS_READ_^S': 0,
				'Q^SET_ID_^Q': data.mailSetId,
				'Q^TYPE_^Q': 1,
				'page': 1,
				'rows': 1,
				'sort':'ID_',
				'order':'desc'
			});

			return jqXhr.then(function(res) {
				return res.total;
			}).always(function() {
				jqXhr = null;
			});
		}
	})();	

	// 策略
	var modifyStrategies = {
		// 待办事项
		'1': function() {
			getTodoCount().done(function(total) {
				var node = $('.nav_a').eq(0).find('h6').text(total);
			});			
		},

		// 未读邮件
		'2': function(data) {
			getUnreadMailCount(data).done(function(total) {
				var node = $('.nav_a').eq(1).find('h6').text(total);
			});				
		}
	};

	// 监听storage事件 当发生修改时 改变页面状态

	window.addEventListener('storage', function(e) {
		var key = e.key;

		if (key !== '__type__') {
			return;
		}

		var newValue = e.newValue;
		// removeItem 防止ie在本页面触发
		if (newValue == null) {
			return;
		}

		newValue = JSON.parse(newValue);
		// 1 待办事项
		// 2 未读邮件
		var strategy = modifyStrategies[newValue.type];

		if (strategy) {
			strategy(newValue.data);
			localStorage.removeItem('__type__');
		}		
	});
});

window.onload=function(){ 

	function checkTime(i){
	  if (i<10){
	    i="0" + i
	  }
	  return i
	}

	function getDate(tm){
	    var date = new Date(tm); //转换成时间对象，这就简单了
	    var year = date.getFullYear();  //获取年
	    var mouth = date.getMonth() + 1;
	    var day = date.getDate();
	    return year + '-' + checkTime(mouth) + '-' + checkTime(day); 
	}

	
	
	//行业资讯点击切换事件
	$(document).on("click","#newsTit_can>a",function(){
		var datamsg = $(this).attr("datamsg")
		var indexNum = $(this).index()
		$(this).siblings().removeClass("active_setion")
		$(this).addClass("active_setion")
		$("#newsTitle_can h4").html(datamsg)
		$("#newsBody_can>li").eq(indexNum).siblings().removeClass("active")
		$("#newsBody_can>li").eq(indexNum).addClass("active")
		$("#moreLink_canB>i").eq(indexNum).siblings().removeClass("active")
		$("#moreLink_canB>i").eq(indexNum).addClass("active")

		switch(datamsg)
		{
		case "待办事宜":
			
			$.ajax({
		        type: 'GET',
		        url: __ctx + '/office/receivedProcess/pendingJson',
		        dataType: 'json',
		        async: true, // 默认为true，默认为true时，所有请求均为异步请求，如果需要发送同步请求，需设置为false,
		        timeout: 5000,
		        data: {},
		        beforeSend: function () {
	                // 在发送请求前就开始执行 （一般用来显示loading图）
	                $("#newsBody_can>li").eq(indexNum).children(".msgIsNull").remove();
	                $("#newsBody_can>li").eq(indexNum).children(".table").children("tbody").remove();
		        },
		        success: function (data) {
		        	console.log(data)
		        	var errmsg = '<div class="msgIsNull"></div>';
	                // 发送请求成功后开始执行，data是请求成功后，返回的数据
	                if(data.rows.length>0){
		                $.each(eval(data).rows, function(index,item){
		                	var createTime = getDate(item.createTime);
		                	var address = "${ctx}/flow/task/taskApprove?id=" + item.id;
		                    var block = '<tr openFull="'+ address +'"><td>'+ (index+1)+'</td><td><ul class="dbTableContent newsLink"><li class="dbTableContent">'+ item.subject +'</li></ul></td><td class="dbTableContent news_can_num">'+ createTime +'</td></tr>';
		                	if (index<8) {
		                    	$("#newsBody_can>li").eq(indexNum).children(".table").append(block);
		                    }else{
		                    	return;
		                    }
						});
					}else{
	                	$("#newsBody_can>li").eq(indexNum).append(errmsg)
	                } 
		        },
		        complete: function () {
		                //当请求完成后开始执行，无论成功或是失败都会执行 （一般用来隐藏loading图）
		        },
		        error: function (xhr, textStatus, errorThrown) {
		                //  请求失败后就开始执行，请求超时后，在这里执行请求超时后要执行的函数
		        }
			}).done(function () {
			        // 这个函数是在ajax数据加载完之后，对数据进行的判断，在涉及到对ajax数据进行操作无效时，在这个函数里面写是可以起到效果的
			})

			break;
		case "我的发起":
		   
			$.ajax({
		        type: 'GET',
		        url: __ctx + '/office/initiatedProcess/myRequestJson',
		        dataType: 'json',
		        async: true, // 默认为true，默认为true时，所有请求均为异步请求，如果需要发送同步请求，需设置为false,
		        timeout: 5000,
		        data: {},
		        beforeSend: function () {
	                // 在发送请求前就开始执行 （一般用来显示loading图）
	                $("#newsBody_can>li").eq(indexNum).children(".msgIsNull").remove();
	                $("#newsBody_can>li").eq(indexNum).children(".table").children("tbody").remove();
		        },
		        success: function (data) {
		        	console.log(data)
		        	var errmsg = '<div class="msgIsNull"></div>';
	                // 发送请求成功后开始执行，data是请求成功后，返回的数据
	                if(data.rows.length>0){
	                	$.each(eval(data).rows, function(index,item){
		                	var createTime = getDate(item.createTime);
		                	var address = "${ctx}/flow/instance/instanceGet?id=" + item.id;
		                    var block = '<tr openFull="'+ address +'"><td>'+ (index+1)+'</td><td><ul class="dbTableContent newsLink"><li class="dbTableContent">'+ item.subject +'</li></ul></td><td class="dbTableContent news_can_num">'+ createTime +'</td></tr>';
		                	if (index<8) {
		                    	$("#newsBody_can>li").eq(indexNum).children(".table").append(block);
		                    }else{
		                    	return;
		                    }
						});
	                }else{
	                	$("#newsBody_can>li").eq(indexNum).append(errmsg)
	                }

	                 
		        },
		        complete: function () {
		                //当请求完成后开始执行，无论成功或是失败都会执行 （一般用来隐藏loading图）
		        },
		        error: function (xhr, textStatus, errorThrown) {
		                //  请求失败后就开始执行，请求超时后，在这里执行请求超时后要执行的函数
		        }
			}).done(function () {
			        // 这个函数是在ajax数据加载完之后，对数据进行的判断，在涉及到对ajax数据进行操作无效时，在这个函数里面写是可以起到效果的
			})

			break;
		case "已办事宜":
			
			$.ajax({
		        type: 'GET',
		        url: __ctx + '/office/receivedProcess/handledJson',
		        dataType: 'json',
		        async: true, // 默认为true，默认为true时，所有请求均为异步请求，如果需要发送同步请求，需设置为false,
		        timeout: 5000,
		        data: {},
		        beforeSend: function () {
	                // 在发送请求前就开始执行 （一般用来显示loading图）
	                $("#newsBody_can>li").eq(indexNum).children(".msgIsNull").remove();
	                $("#newsBody_can>li").eq(indexNum).children(".table").children("tbody").remove();
		        },
		        success: function (data) {
		        	var errmsg = '<div class="msgIsNull"></div>';
	                // 发送请求成功后开始执行，data是请求成功后，返回的数据
	                if(data.rows.length>0){
		                $.each(eval(data).rows, function(index,item){

		                	var createTime = getDate(item.createTime);
		                	var address = "${ctx}/flow/instance/instanceGet?id=" + item.id;
		                    var block = '<tr openFull="'+ address +'"><td>'+ (index+1)+'</td><td><ul class="dbTableContent newsLink"><li class="dbTableContent">'+ item.subject +'</li></ul></td><td class="dbTableContent news_can_num">'+ createTime +'</td></tr>';
		                    if (index<8) {
		                    	// onclick="javascript:jQuery.openFullWindow('${ctx}/flow/task/taskApprove?id=${data.id}')"
		                    	$("#newsBody_can>li").eq(indexNum).children(".table").append(block);
		                    }else{
		                    	return;
		                    }
						}); 
					}else{
	                	$("#newsBody_can>li").eq(indexNum).append(errmsg)
	                }
		        },
		        complete: function () {
		                //当请求完成后开始执行，无论成功或是失败都会执行 （一般用来隐藏loading图）
		        },
		        error: function (xhr, textStatus, errorThrown) {
		                //  请求失败后就开始执行，请求超时后，在这里执行请求超时后要执行的函数
		        }
			}).done(function () {
			        // 这个函数是在ajax数据加载完之后，对数据进行的判断，在涉及到对ajax数据进行操作无效时，在这个函数里面写是可以起到效果的
			})

			break;
		case "办结事宜":
			
			$.ajax({
		        type: 'GET',
		        url: __ctx + '/office/receivedProcess/completedJson',
		        dataType: 'json',
		        async: true, // 默认为true，默认为true时，所有请求均为异步请求，如果需要发送同步请求，需设置为false,
		        timeout: 5000,
		        data: {},
		        beforeSend: function () {
	                // 在发送请求前就开始执行 （一般用来显示loading图）
	                $("#newsBody_can>li").eq(indexNum).children(".msgIsNull").remove();
	                $("#newsBody_can>li").eq(indexNum).children(".table").children("tbody").remove();
		        },
		        success: function (data) {
		        	var errmsg = '<div class="msgIsNull"></div>';
	                // 发送请求成功后开始执行，data是请求成功后，返回的数据
	                if(data.rows.length>0){
		                $.each(eval(data).rows, function(index,item){
		                	var createTime = getDate(item.createTime);
		                	var address = "${ctx}/flow/instance/instanceGet?id=" + item.id;
		                    var block = '<tr openFull="'+ address +'"><td>'+ (index+1)+'</td><td><ul class="dbTableContent newsLink"><li class="dbTableContent">'+ item.subject +'</li></ul></td><td class="dbTableContent news_can_num">'+ createTime +'</td></tr>';
		                	if (index<8) {
		                    	$("#newsBody_can>li").eq(indexNum).children(".table").append(block);
		                    }else{
		                    	return;
		                    }
						}); 
					}else{
	                	$("#newsBody_can>li").eq(indexNum).append(errmsg)
	                }
		        },
		        complete: function () {
		                //当请求完成后开始执行，无论成功或是失败都会执行 （一般用来隐藏loading图）
		        },
		        error: function (xhr, textStatus, errorThrown) {
		                //  请求失败后就开始执行，请求超时后，在这里执行请求超时后要执行的函数
		        }
			}).done(function () {
			        // 这个函数是在ajax数据加载完之后，对数据进行的判断，在涉及到对ajax数据进行操作无效时，在这个函数里面写是可以起到效果的
			})

			break;
		case "抄送事宜":
			
			$.ajax({
		        type: 'GET',
		        url: __ctx + '/office/initiatedProcess/myCopyToJson',
		        dataType: 'json',
		        async: true, // 默认为true，默认为true时，所有请求均为异步请求，如果需要发送同步请求，需设置为false,
		        timeout: 5000,
		        data: {},
		        beforeSend: function () {
	                // 在发送请求前就开始执行 （一般用来显示loading图）
	                $("#newsBody_can>li").eq(indexNum).children(".msgIsNull").remove();
	                $("#newsBody_can>li").eq(indexNum).children(".table").children("tbody").remove();

		        },
		        success: function (data) {
		        	var errmsg = '<div class="msgIsNull"></div>';
	                // 发送请求成功后开始执行，data是请求成功后，返回的数据
	                if(data.rows.length>0){
		                $.each(eval(data).rows, function(index,item){
		                	var createTime = getDate(item.createTime);
		                	var address = "${ctx}/flow/instance/instanceGet?id=" + item.instId;
		                    var block = '<tr openFull="'+ address +'"><td>'+ (index+1)+'</td><td><ul class="dbTableContent newsLink"><li class="dbTableContent">'+ item.subject +'</li></ul></td><td class="dbTableContent news_can_num">'+ createTime +'</td></tr>';
		                	if (index<8) {
		                    	$("#newsBody_can>li").eq(indexNum).children(".table").append(block);
		                    }else{
		                    	return;
		                    }
						});
					}else{
	                	$("#newsBody_can>li").eq(indexNum).append(errmsg)
	                } 
		        },
		        complete: function () {
		                //当请求完成后开始执行，无论成功或是失败都会执行 （一般用来隐藏loading图）
		        },
		        error: function (xhr, textStatus, errorThrown) {
		                //  请求失败后就开始执行，请求超时后，在这里执行请求超时后要执行的函数
		        }
			}).done(function () {
			        // 这个函数是在ajax数据加载完之后，对数据进行的判断，在涉及到对ajax数据进行操作无效时，在这个函数里面写是可以起到效果的
			})

			break;
		default:
		  console.log(123)
		}


	});


	$(document).on("click","#newsBody_can>li tr",function(){
		var openFull = $(this).attr("openFull");
		//console.log(openFull)
		if(openFull)
		jQuery.openFullWindow(openFull)

		// var ww = window.top.innerWidth - (window.top.innerWidth)/4;
		// var hh = window.top.innerHeight - 100;

		// parent.layer.open({
		// 	  title:"内容详情",
		// 	  type: 2,
		// 	  zIndex:8000,
		// 	  area: [ww + "px", hh + "px"],
		// 	  fixed: true, //不固定
		// 	  shadeClose:true,
		// 	  maxmin: true,
		// 	  resize: true,
		// 	  content: openFull,
		// 	  end:function(){
		// 	  	//reload();
		// 	  }
		// });

	});
    
    //待办事宜点击切换事件
	$(document).on("click","#newsTitA_can>a",function(){
		var datamsg = $(this).attr("datamsg")
		var indexNum = $(this).index()
		$(this).siblings().removeClass("active_setion")
		$(this).addClass("active_setion")
		$("#newsTitleA_can").html(datamsg)
		$("#newsBodyA_can>li").eq(indexNum).siblings().removeClass("active")
		$("#newsBodyA_can>li").eq(indexNum).addClass("active")
		$("#moreLink_canA>i").eq(indexNum).siblings().removeClass("active")
		$("#moreLink_canA>i").eq(indexNum).addClass("active")
	});
	
	me.parser();
	
}
</script>
	<body>
		<div class="index-page">
		${html}
		</div>
	</body>
</html>
