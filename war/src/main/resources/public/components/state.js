'use strict';

angular.module('myApp.state', ['angular-storage'])

    .service('state', ['store', function (store) {
        var service = this;
        var currentUser = null;
        var tokenInfo = null;

        service.setCurrentUser = function(user) {
            currentUser = user;
            store.set('user', user);
            return currentUser;
        };

        service.getCurrentUser = function() {
            if (!currentUser) {
                currentUser = store.get('user');
            }
            return currentUser;
        };

        service.setTokenInfo = function(aTokenInfo) {
            tokenInfo = aTokenInfo;
            store.set('tokenInfo', tokenInfo);
            return tokenInfo;
        };

        service.getTokenInfo = function() {
            if (!tokenInfo) {
                tokenInfo = store.get('tokenInfo');
            }
            return tokenInfo;
        };
    }]);
