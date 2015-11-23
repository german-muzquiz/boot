(function() {
    'use strict';

    angular
        .module('app.core')
        .factory('dataservice', dataservice);

    /* @ngInject */
    function dataservice($http, $location, $q, exception, logger, connSettings, sessionservice) {
        var isPrimed = false;
        var primePromise;

        var service = {
            login: login,
            ready: ready
        };

        return service;

        function login(username, password) {

            if (sessionservice.isLogged()) {
                return $q.when('');
            }

            var data = {
                'grant_type': 'password',
                'username': username,
                'password': password
            };

            var req = {
                method: 'POST',
                url: connSettings.backendUrl + '/oauth/token',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Basic ' + btoa(connSettings.clientId + ':')
                },
                data: $.param(data)
            };

            return $http(req).then(loginComplete);
        }

        function loginComplete(response) {
            sessionservice.setTokenInfo(response.data);
        }

        function prime() {
            // This function can only be called once.
            if (primePromise) {
                return primePromise;
            }

            primePromise = $q.when(true).then(success);
            return primePromise;

            function success() {
                isPrimed = true;
                //logger.info('Primed data');
            }
        }

        function ready(nextPromises) {
            var readyPromise = primePromise || prime();

            return readyPromise
                .then(function() { return $q.all(nextPromises); })
                .catch(exception.catcher('"ready" function failed'));
        }

    }
})();
