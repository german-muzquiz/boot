'use strict';

angular.module('myApp.login', ['ngRoute', 'myApp.api', 'myApp.state'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'public/login/login.html',
            controller: 'LoginController'
        });
    }])

    .controller('LoginController', ['$scope', '$rootScope', '$location', 'api', 'state',
        function($scope, $rootScope, $location, api, state) {

        $scope.model = {};
        $scope.model.username = "";
        $scope.model.password = "";
        $scope.login = function () {
            api.login($scope.model.username, $scope.model.password).then(
                function (response) {
                    state.setTokenInfo(response.data);
                    $location.path('/view1');

                }, function (error) {
                    console.log(error);
                });
        }

    }]);