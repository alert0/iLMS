/** 公告信息查看跟踪 */
Ext.onReady( function() {
	var managePageAuthRow = 'SP';
	var curPageSize = 500;
	
	gsl.PagingToolbar1 = function(opts) {
		var opts = Ext.apply( {
			pageSize :curPageSize*3,
			displayInfo :true}, opts);
		gsl.PagingToolbar.superclass.constructor.call(this, opts);
	};
	Ext.extend(gsl.PagingToolbar1, Ext.PagingToolbar, {
		updateInfo : function(){
        if(this.displayEl){
            var length = this.store.getCount();
            var count=length*3;
            if(length!=0){
	            if( store.data.get(length-1).get("supplierNo3")==null){
	            	count--;
	            }
	            if( store.data.get(length-1).get("supplierNo2")==null){
	            	count--;
	            }
            }
            var msg = count == 0 ?
                this.emptyMsg :
                String.format(
                    this.displayMsg,
                    this.cursor+1, this.cursor+count, this.store.getTotalCount()
                );
            this.displayEl.update(msg);
        }
    }});
	var busyFlg = false;
	// 供应商代码
	var lblInfoNo = new gsl.LabelField({
		fieldLabel  : '公告编号',
		dataIndex : 'infoId'
	});
	
	// 供应商代码
	var lblInfoTitle = new gsl.LabelField({
		fieldLabel  : '标题',
		dataIndex : 'infoTitle'
	});
	
	// 查询条件FormPanel
	var loadMaintainForm = new gsl.FormPanel({
	  	hideCollapseTool: false,
		title: "公告信息",
	  	titleCollapse: false,
	  	collapsible: false,
	    renderTo:'totalDiv',
	    width :(Ext.get('totalDiv').getWidth() > 800 ?Ext.get('totalDiv').getWidth():800),
	    labelAlign : 'right',
	    frame:true,
	    items : [{
	    	layout : 'column',
	    	labelSeparator : '：',
	    	items : [{
	    		labelWidth:80,
	    		columnWidth : .30,
	    		layout : 'form',
	    		items : [lblInfoNo]
	    	},{
	    		labelWidth:95,
	    		columnWidth : .60,
	    		layout : 'form',
	    		items : [lblInfoTitle]
	    	}]
	    }]     
    });
	
	var store = new gsl.JsonStore({
		url : 'pub/message-queryHomePageMsgQueryStatusForPage.action',
		fields : [ 'no', 'supplierNo', 'supplierName', 'viewStatus', 'downloadStatus',
		          'no2', 'supplierNo2', 'supplierName2', 'viewStatus2', 'downloadStatus2',
		          'no3', 'supplierNo3', 'supplierName3', 'viewStatus3', 'downloadStatus3']
	});
	
	loadMaintainForm.load({
    	url:'pub/message-queryMessageInfoByInfoId.action',
    	params:{'infoId': infoId},
    	fields: ["infoId", "infoTitle"],
    	success:function(form,action){}
    });
	store.baseParams = { 'infoId': infoId };
	store.load( { params : { start : 0, limit : curPageSize } });

	var cm = new Ext.grid.ColumnModel([
		{
			header: "NO.",
			dataIndex: "no",
			width: 35,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		},{
			header: "供应商代码",
			dataIndex: "supplierNo",
			width: 80,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		}, {
			header: "供应商名称",
			dataIndex: "supplierName",
			width: 100,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
				return v;
			}
		}, {
			header: "查看",
			dataIndex: "viewStatus",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
		 		if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else {
		 			return v;
		 		}
			}
		}, {
			header: "下载",
			dataIndex: "downloadStatus",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else if(v == "9") {
		 			return '<img alt="" src="./images/lampGrey.gif" />';
		 		}else {
		 			return v;
		 		}
			}
		},  {
		  	width: 25,
		  	dataIndex: "blank"
		}
		
		,{
			header: "NO.",
			dataIndex: "no2",
			width: 35,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		},{
			header: "供应商代码",
			dataIndex: "supplierNo2",
			width: 80,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		}, {
			header: "供应商名称",
			dataIndex: "supplierName2",
			width: 100,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
				return v;
			}
		}, {
			header: "查看",
			dataIndex: "viewStatus2",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
		 		if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else {
		 			return v;
		 		}
			}
		}, {
			header: "下载",
			dataIndex: "downloadStatus2",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else if(v == "9") {
		 			return '<img alt="" src="./images/lampGrey.gif" />';
		 		}else {
		 			return v;
		 		}
			}
		},  {
		  	width: 25,
		  	dataIndex: "blank"
		}
		
		,{
			header: "NO.",
			dataIndex: "no3",
			width: 35,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		},{
			header: "供应商代码",
			dataIndex: "supplierNo3",
			width: 80,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
			 	return v;
			}
		}, {
			header: "供应商名称",
			dataIndex: "supplierName3",
			width: 100,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				m.attr = 'style="text-align:left;"';
				return v;
			}
		}, {
			header: "查看",
			dataIndex: "viewStatus3",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
		 		if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else {
		 			return v;
		 		}
			}
		}, {
			header: "下载",
			dataIndex: "downloadStatus3",
			width: 40,
			align: 'center',
			renderer : function(v, m, rd, r, c, s) {
				if(v == "0"){
		 			return '<img alt="" src="./images/lampRed.gif" /></a>';
		 		}else if(v == "1") {
		 			return '<img alt="" src="./images/lampGreen.gif" /></a>';
		 		}else if(v == "9") {
		 			return '<img alt="" src="./images/lampGrey.gif" />';
		 		}else {
		 			return v;
		 		}
			}
		}
		
	]);
	
	
	var pagingBar = new gsl.PagingToolbar1({
		store : store,
		pageSize : curPageSize
	});
		
	var grid = new gsl.GridPanel({
		iconCls:'titList',
		renderTo: "infoDiv",
		width : (Ext.get('infoDiv').getWidth() > 800 ? Ext.get('infoDiv').getWidth() : 800),
		title: "公告查看状态一览",
		height: 267,
		cm: cm,
		store: store,
		bbar: pagingBar
	});

	// 调整sendQueryForm,grid的高度(自动调整)
	new gsl.adjHeight(loadMaintainForm, grid);
	// 调整sendQueryForm,grid的宽度(自动调整)
	adjWidthCtrs = [ loadMaintainForm, grid ];
	// 调整grid的高度(适应分辨率)
	var tempHeight = parent.document.getElementById("mainTabPanel").clientHeight - 15;
	grid.setHeight(tempHeight - loadMaintainForm.getSize().height - 20);
	
});