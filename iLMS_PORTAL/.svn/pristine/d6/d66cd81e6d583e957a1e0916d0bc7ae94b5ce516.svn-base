/*datagrid 适应调整 
 *luoxw 2015-12-09
 */

//当grid有冻结列时，调整第二个视图的滚动条
function reSizeView2()
{
    setTimeout(function(){
          //调整右侧滚动条  
	  if($(".datagrid-view2 > div[class='datagrid-body']").size()>0&&$("div[class$='colManage']").size()<=0)
	  { 
	    var width= $(".datagrid-view2 > div[class='datagrid-header']").width(); 
	    var view1Width= $(".datagrid-view1").width(); 
	    if(view1Width>300)
	    {
	       $(".datagrid-view2 > div[class='datagrid-body']").width(width-200);
	       $(".datagrid-view2 > div[class='datagrid-body']").css("overflow-x","auto");
	    }
	  }
	  $("div[class$='colManage']").width(92);
    },1000);
}

$(function(){
    $(window).resize(reSizeView2);
})