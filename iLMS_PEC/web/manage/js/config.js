function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, IdleProvider, KeepaliveProvider) {

    // Configure Idle settings
    IdleProvider.idle(5); // in seconds
    IdleProvider.timeout(120); // in seconds

    $urlRouterProvider.otherwise("/outlook");

    $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
    });

    $stateProvider
        .state('outlook', {
            url: "/outlook",
            templateUrl: "views/outlook.html",
            data: { pageTitle: 'Outlook view', specialClass: 'fixed-sidebar' }
        });

}
angular
    .module('hotent')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });
