'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.publicDashboard',
    'myApp.login',
    'myApp.view1',
    'myApp.view2',
    'myApp.version',
    'myApp.auth-interceptor'
]).
config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
    $routeProvider.otherwise({redirectTo: '/dashboard'});

    //Enable cross domain calls
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;

    $httpProvider.interceptors.push('authInterceptor');
}]);
