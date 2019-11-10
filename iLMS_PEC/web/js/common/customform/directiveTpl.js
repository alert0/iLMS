


var getDirectiveTplHtml = (function(){
	var templateHtml = {
/*htUpload 附件上传的HTML模板*/
		htUpload: {
	        PC: '<span ng-if="permission==\'w\' || permission==\'b\'">\
	        		<div class="ht-input">\
	        			<span class="span-user owner-span" ng-repeat="item in files track by $index">\
	        				<a class="download-btn" title="下载该文件"  ng-click="onClick(item.id)">{{item.name}}</a>\
	        				<a class="btn btn-xs fa-remove" title="移除该项" ng-click="remove($index)"></a>\
	        			</span>\
			    	</div>\
	        		<a class="btn btn-sm btn-primary fa-upload" ng-click="showDialog()"><span>上传</span></a>\
	        	</span>\
	        	<div ng-if="permission==\'r\'">\
					<span ng-repeat="item in files track by $index">\
	        			<a class="btn  fa-cloud-download" title="下载该文件" ng-click="onClick(item.id)">{{item.name}}</a>\
					</span>\
	        	</div>',

			mobile: '<div ng-if="permission==\'w\' || permission==\'b\'">\
					<div style="text-align:left;" class="row">\
						<span class="col-50"  style="word-break:normal; width:auto; display:block; white-space:normal;word-wrap : break-word ;overflow: hidden ;margin-left:0px;" ng-repeat="file in files track by $index"> 	\
							<a  ng-click="onClick(file.id)" style="text-overflow: ellipsis;overflow: hidden;padding-right: 25px;" class="button btn-sm">{{file.name}}\
				            <a class="fa fa-close icon-button" style="top: -7px;padding:7px;margin-left: -25px;" ng-click="remove($index,$event)"></a></a> \
						</span>				\
					<span class="col-50" style="margin-left:2px;">\
						<a style="margin-top:10px;" class="fa fa-upload" ng-click="showDialog()"></a>\
					</span> \
					</div>\
        		</div>\
				<div ng-if="permission==\'r\'" class="row" >\
					<span ng-repeat="file in files track by $index" class="col-50" >\
						<a ng-click="onClick(file.id)" class="button btn-sm">{{file.name}}</a> \
					</span>\
				</div>'
	    },
	    htSelector:{
	    	PC:'<div ng-if="permission==\'w\' || permission==\'b\'" class="input-group">\
						<div class="ht-input">\
							<span class="span-user owner-span" ng-repeat="item in names track by $index">\
								 <span  ng-click="selectedOnClick($index)">{{item}}</span>\
								 <span class="btn btn-xs  fa-remove"  title="移除该项" ng-click="remove($index)"></span>\
							</span>\
						</div>\
    			<a class="btn btn-sm btn-primary fa-search" ng-click="showDialog()">选择</a>\
				</div>\
				<div ng-if="permission==\'r\'" class="input-group" >\
					<span ng-repeat="item in names track by $index">\
						<a style="margin: 4px" class="btn span-user owner-span" ng-click="selectedOnClick($index)">{{item}}</a>\
					</span>\
				</div>',

			mobile:'<span>\
				<div  ng-if="permission==\'w\' || permission==\'b\'">\
					<div style="text-align:left;" class="row">\
						<span class="col-50" style="width:auto;margin:0 5px 0 0;" ng-repeat="item in names track by $index">\
				              <span  class="button" style="margin: 4px 0;"  title="移除该项" ng-click="remove($index)">\
				                   <span class="textSpan" style="text-overflow: ellipsis;overflow: hidden;display: inline-block;margin-bottom: -8px;">{{item}}</span>\
				                   <span class="fa fa-close"></span></span>\
						      </span>\
						<span style="margin-left: 2px;" class="col-50" >\
							<a style="margin-top:10px;" class="fa fa-search" ng-click="showDialog()">选择</a>\
						</span>\
					</div>\
				</div>\
				<div ng-if="permission==\'r\'" class="row" >\
					<span ng-repeat="item in names track by $index" class="col-50" >\
						<button class="button btn-sm" ng-click="selectedOnClick($index)">{{item}}</button>\
					</span>\
				</div>\
			</span>',
	    },
	    
	    
    htDic:{
	    	PC:'<div >\
		    		<div ng-show="permission==\'w\' || permission==\'b\'" class="dropdown">\
					<span ng-bind="dicData.value" readonly="readonly" type="text" class="form-control ht-input" style="width: 75%; float: left;margin-right:2px;" placeholder="点击选择" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></span>\
	    					<a href="javaScript:void(0)" ng-click="dicClean()" class="btn btn-sm btn-primary fa-rotate-left"></a>\
	    					<div class="dropdown-menu" style=" width: 80%; margin-left: 11px;">\
								<ul style="height:200px;overflow-y:auto;" id="{{treeId}}" class="ztree"></ul>\
							</div>\
					</div>\
					<div ng-show="permission==\'r\'">{{dicData.value}}</div>\
    		   </div>',

			mobile:'<div >\
		    		<div  ng-show="permission==\'w\' || permission==\'b\'" style="text-align: left" >\
						<div style="width: 100%">\
							<input  ng-model="dicData.value" style="width:100%;max-width: 100%;text-overflow: ellipsis;padding-right: 25px;" readonly="readonly" type="text" class="open-popover ht-input ht-dic" placeholder="点击选择" data-popover="#popover{{treeId}}">\
							<a style="word-wrap:break-word;padding:14px;position: absolute;" class="fa fa-close icon-button" ng-if="htDic" title="清除选中" ng-click="dicClean()"></a>\
							<div id="popover{{treeId}}" class="popover">\
								<ul id="{{treeId}}" class="ztree"></ul>\
							</div>\
						</div>\
				  	</div>\
					<div ng-show="permission==\'r\'">{{dicData.value}}</div>\
				</div>',
}
	
	};
	
	
	
	
return function(directiveName) {
	if(typeof(window.FORM_TYPE_) == "undefined")window.FORM_TYPE_="PC"
	
	if(!templateHtml[directiveName])alert(directiveName+"指令URL不存在");
	
	return templateHtml[directiveName][window.FORM_TYPE_];
}
})();
