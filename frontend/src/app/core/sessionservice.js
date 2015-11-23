(function() {
    'use strict';

    angular
        .module('app.core')
        .factory('sessionservice', sessionservice);

    /* @ngInject */
    function sessionservice() {
        var tokenInfo = null;

        var service = {
            setTokenInfo: setTokenInfo,
            isLogged: isLogged,
            getAccessToken: getAccessToken
        };

        return service;

        function setTokenInfo(aTokenInfo) {
            tokenInfo = aTokenInfo;
        }

        function isLogged() {
            return tokenInfo !== null;
        }

        function getAccessToken() {
            return tokenInfo.access_token;
        }
    }
})();
