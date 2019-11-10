<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app='app'>
<head>
<%@include file="/commons/include/list.jsp"%>
<title>系统附件上传</title>
<script type="text/javascript" src="${ctx}/js/lib/angular/angular-file-upload.js"></script>
<style type="text/css">
.my-drop-zone {
	border: dotted 3px lightgray;
}
td {
	vertical-align: middle !important;
	font-size: 14px;
}
th {
	text-align: center;
	font-size: 14px;
}
.file {  
    position: relative;  
    display: inline-block;  
    background: #f1f5f9;  
    border: 1px solid #bfd6ec;  
    padding: 4px 0px;  
    text-decoration: none;  
    width: 60px;
    text-indent: 0;  
    text-align: center;
    line-height: 20px;  
    /*border-radius: 10px; // ie8不支持可以不加圆角*/  
    color: #333;  
    font-size: 13px;  
}  
.file-input{  
    position: absolute;  
    font-size: 13px;  
    width: 60px; 
    padding: 0;  
    left: 0;   
    margin: 0;  
    filter:alpha(opacity=0);  
    -moz-opacity:0;  
    opacity:0;   
    height: 30px;  
    top: 0;  
    z-index: 9999;
} 


</style>
<script type="text/javascript">
	var type = '${param.type}';
	var	max = Number('${param.max}');
	var	size = Number('${param.size}');
	var storeType = '${param.storeType}';
	var parent = window.parent;
	var sameOrigin = true;
	//判断是否同源
	$(function(){
		if(!parent||!parent.location)return;
		try{
			parent.location.href;
		}
		catch(err){
			sameOrigin = false;
		}
		
		
		
	});
	
	window.onmessage = function(e){
		e = e || event;
		if(e.data=="getData"){
			var scope = getData(); 
			if(scope.uploader.getNotUploadedItems().length>0){
				showMessage("有文件尚未上传，请上传该文件或删除该文件.");
		    	return;
		    }
			var ary = [];
			angular.forEach(scope.uploader.queue,function(item){
				ary.push(item.json);
			});
			post2parent(ary);
		}
	}

	function getData() {
		var scope = $("body").scope();
		return scope;
	}
	
	function post2parent(data){
		if(sameOrigin) return;
		parent.window.postMessage(data,"*");
	}
	
	function showMessage(message){
		if(sameOrigin){
			$.topCall.alert("提示信息", message);
		}
		else{
			alert(message);
		}
	}

	angular.module("app", [ 'angularFileUpload', 'base' ]).controller('uploadCtrl', [ '$scope', 'FileUploader','baseService', function($scope, FileUploader,baseService) {
		var url = __ctx + "/system/file/fileUpload?storeType=" + storeType;
		var uploader = $scope.uploader = new FileUploader({
			url : url
		});
		
		if (max && typeof max == 'number') {
			//上传文件数目上限过滤器
			uploader.filters.push({
				name : 'countFilter',
				fn : function(item, options) {
					var result = this.queue.length < max;
					!result && (showMessage("最多只能上传" + max + "个文件"));
					return result;
				}
			});
		}
		if (type) {
			type = type.replace(/,/g, '\|');
			var reg = new RegExp("^.*.(" + type + ")$");
			//上传文件的文件类型过滤器
			uploader.filters.push({
				name : 'typeFilter',
				fn : function(item, options) {
					var result = reg.test(item.name);
					!result && (showMessage("文件类型只能是" + type));
					return result;
				}
			});
		}
		if (size && typeof size == 'number') {
			var realSize = size * 1024 * 1024;
			//上传文件的大小过滤器 
			uploader.filters.push({
				name : 'sizeFilter',
				fn : function(item, options) {
					var result = item.size <= realSize;
					!result && (showMessage("单个文件大小不能超过" + size + "M"));
					return result;
				}
			});
		}
		uploader.onSuccessItem = function(fileItem, response) {
			fileItem.json = {
				id : response.fileId,
				name : response.fileName
			};
			if(!response.success){
				showMessage('文件上传失败！' + response.msg || '');
			}
		};
		
		uploader.onRemoveItem = function(fileItem){
			if(fileItem.isUploaded){
				var url  = __ctx + "/system/file/remove?id="+fileItem.json.id;
				baseService.post(url).then(function(){
				},function(data){
					$.topCall.error("删除附件失败");
				});
			}
		}
	} ]);
</script>
</head>
<body ng-controller="uploadCtrl" nv-file-drop uploader="uploader" filters="countFilter,typeFilter,sizeFilter">
	<div class="container-fluid">
		<div class="row" style="margin: 0 10px;">
			<div class="pull-left" style="margin: 15px 0 10px 10px; width: 280px;">
				<div class="file" for="myfile">选择文件
					<input type="file" class="file-input" id="myfile" nv-file-select uploader="uploader" multiple />
				</div>
				
			
				<div style="margin-top: 10px;">
					<a href="javascript:;" ng-click="uploader.uploadAll()" ng-disabled="!uploader.getNotUploadedItems().length" class="btn btn-primary btn-sm fa-upload">
						<span>上传</span>
					</a>
					<a href="javascript:;" ng-click="uploader.cancelAll()" ng-disabled="!uploader.isUploading" class="btn btn-sm btn-default fa-undo">
						<span>取消</span>
					</a>
					<a href="javascript:;" ng-click="uploader.clearQueue()" ng-disabled="!uploader.queue.length" class="btn btn-sm btn-danger fa-remove">
						<span>删除</span>
					</a>
				</div>
			</div>
			<div class="pull-right" ng-if="uploader.isHTML5" style="margin-top: 10px; width: 400px;" nv-file-drop="" uploader="uploader">
				<div nv-file-over="" uploader="uploader" over-class="another-file-over-class" class="well my-drop-zone">
					<p>将文件拖拽至此区域</p>
				</div>
			</div>
		</div>
		<div class="row" style="margin: 0 10px;">
			<div class="easyui-panel" title="上传队列" style="padding: 0 10px;">
				<table class="table">
					<thead>
						<tr>
							<th width="65">序号</th>
							<th>文件</th>
							<th width="90" ng-show="uploader.isHTML5">大小</th>
							<th width="90" ng-show="uploader.isHTML5">进度</th>
							<th width="60">状态</th>
							<th width="200">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in uploader.queue">
							<td>{{$index+1}}</td>
							<td>
								<div style="width: 200px; overflow: hidden;" title="{{ item.file.name }}">
									<strong>{{ item.file.name }}</strong>
								</div>
							</td>
							<td ng-show="uploader.isHTML5" nowrap>
								<span>{{ item.file.size/1024/1024|number:2 }} MB</span>
							</td>
							<td ng-show="uploader.isHTML5">
								<div class="progress" style="margin-bottom: 0;">
									<div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
								</div>
							</td>
							<td class="text-center">
								<span ng-show="item.isSuccess">
									<i class="glyphicon glyphicon-ok"></i>
								</span>
								<span ng-show="item.isCancel">
									<i class="glyphicon glyphicon-ban-circle"></i>
								</span>
								<span ng-show="item.isError">
									<i class="glyphicon glyphicon-remove"></i>
								</span>
							</td>
							<td nowrap>
								<a href="javascript:;" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess" class="btn btn-primary fa-upload">
									<span>上传</span>
								</a>
								<a href="javascript:;" ng-click="item.cancel()" ng-disabled="!item.isUploading" class="btn btn-default fa-undo">
									<span>取消</span>
								</a>
								<a href="javascript:;" ng-click="item.remove()" class="btn btn-danger fa-remove">
									<span>删除</span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>