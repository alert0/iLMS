angular.module('CustomDialogService', [ 'base' ])
.service('CustomDialog', [ 'baseService', function(baseService) {
	var service = {
		detail : function(CustomDialog, callback) {
			if (!CustomDialog || !CustomDialog.id) {
				if (callback) {
					callback();
				}
				return;
			}
			// 获取CustomDialog的详情
			var defer = baseService.postForm(__ctx + '/form/customDialog/getById', {
				id : CustomDialog.id
			});
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		getByAlias : function(alias, callback) {
			// 获取CustomDialog的详情
			var defer = baseService.postForm(__ctx + '/form/customDialog/getByAlias', {
				alias : alias
			});
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		// 保存
		save : function(CustomDialog, callback) {

			var defer = baseService.post(__ctx + '/form/customDialog/save', CustomDialog);
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		// 查询所有
		getAll : function(callback) {
			var defer = baseService.get(__ctx + '/form/customDialog/getAll');
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		// 查询所有选择器
		getSelectorAll:function(callback) {
			var defer = baseService.get(__ctx + '/form/selectorDef/getAll');
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		
		// 根据customDialog对象查询数据结果
		search : function(params, callback) {
			var defer = baseService.postForm(__ctx + '/form/customDialog/doQuery', params);
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		}
		,customDialog:function(parm){
			CustomDialog.openCustomDialog(parm);
		}
	}
	return service;
} ]);
