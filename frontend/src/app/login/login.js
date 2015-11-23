(function() {
    'use strict';

    angular
        .module('app.login')
        .controller('Login', Login);

    /* @ngInject */
    function Login(dataservice, logger, $location) {
        /*jshint validthis: true */
        var vm = this;
        vm.username = null;
        vm.password = null;
        vm.isLoginError = false;
        vm.loginErrorMessage = '';
        vm.login = login;

        function login() {
            vm.isLoginError = false;

            dataservice.login(vm.username, vm.password).then(function (data) {
                $location.url('/');
            }, function(response) {
                if (response.status >= 400 && response.status <= 499) {
                    vm.loginErrorMessage = 'Invalid user name or password';
                } else {
                    vm.loginErrorMessage = 'There was an error logging in, please try again later';
                }
                vm.isLoginError = true;
            });
        }
    }
})();
