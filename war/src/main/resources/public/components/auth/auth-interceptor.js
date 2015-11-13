'use strict';

angular.module('myApp.auth-interceptor', ['myApp.state', 'myApp.config'])

    .service('authInterceptor', ['$rootScope', '$q', '$location', 'state', 'ConnectionConfig', function($rootScope, $q, $location, state, ConnectionConfig) {

        var service = this;

        service.request = function(config) {

            var tokenInfo = state.getTokenInfo();

            // Protect admin pages
            if (config.url.lastIndexOf('/admin') === 0) {
                if (!tokenInfo) {
                    config.url = '/public/login';
                }
                return config;
            }

            // Only process requests to rest api
            if (config.url.lastIndexOf(ConnectionConfig.api_endpoint) !== 0) {
                return config;
            }

            if (tokenInfo && !config.headers.Authorization) {
                config.headers.Authorization = 'Bearer ' + tokenInfo.access_token;
            }
            return config;
        };

        service.responseError = function(response) {
            if (response.status === 401) {
                console.log('Error');
                $location.path('/public/login');
            }
            return $q.reject(response);
        };
    }]);
