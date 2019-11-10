angular.module('CombinateDialogService', [ 'base' ]).service('CombinateDialog', [ '$rootScope', 'baseService', '$http', function($rootScope, BaseService, $http) {
	var service = {
		detail : function(param, callback) {
			// 获取CombinateDialog的详情
			BaseService.postForm(__ctx + '/form/combinateDialog/getObject', param).then(function(data) {
				if (callback) {
					callback(data);
				}
			});
		},
		// 保存
		save : function(CombinateDialog, callback) {
			BaseService.post(__ctx + '/form/combinateDialog/save', CombinateDialog).then(function(data) {
				if (callback) {
					callback(data);
				}
			});
		}
	}
	return service;
} ]);
