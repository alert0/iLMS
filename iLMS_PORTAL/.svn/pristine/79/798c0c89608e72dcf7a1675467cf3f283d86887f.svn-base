
	  var $chromeTabsExampleShell = $('.chrome-tabs-shell')
      chromeTabs.init({
          $shell: $chromeTabsExampleShell,
          minWidth: 45,
          maxWidth: 100
      });
	  $chromeTabsExampleShell.bind("chromeTabRender", function(){
		  var $currentTab = $chromeTabsExampleShell.find('.chrome-tab-current');
		  var id = $currentTab.data('tabData').data.tabId;
		  var showIframe = $(".index_page_show");
		  showIframe.removeClass("index_page_show");
		  showIframe.addClass("index_page_hide");
		  var iframe = $("#"+id);
		  iframe.removeClass("index_page_hide");
		  iframe.removeClass("index_page_show");
		  iframe.addClass("index_page_show");
	  });
	  $chromeTabsExampleShell.bind("tabClose", function(shell, id){
		  var iframe = $("#"+id);
		  iframe.remove();
	  })