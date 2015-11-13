'use strict';

angular.module('myApp.api', ['myApp.config'])

    .service('api', ['$http', '$rootScope', 'ConnectionConfig', function ($http, $rootScope, ConnectionConfig) {
        var apiService = this;

        apiService.login = function (username, password) {

            var auth = 'Basic ' + btoa(ConnectionConfig.client_id + ':' + ConnectionConfig.client_secret);
            var params = $.param({
                'username': username,
                'password': password,
                'grant_type': 'password'
            });

            return $http({
                method: 'POST',
                url: ConnectionConfig.token_endpoint,
                data: params,
                withCredentials: true,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': auth
                }

            });
        }
    }]);
