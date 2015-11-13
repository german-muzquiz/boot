'use strict';

angular.module('myApp.publicDashboard', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/dashboard', {
            templateUrl: 'public/dashboard/dashboard.html',
            controller: 'PublicDashboardController'
        });
    }])

    .controller('PublicDashboardController', [function() {

    }]);