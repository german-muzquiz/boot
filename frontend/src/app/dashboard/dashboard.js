(function() {
    'use strict';

    angular
        .module('app.dashboard')
        .controller('Dashboard', Dashboard);

    Dashboard.$inject = ['$q', 'dataservice', 'logger'];

    function Dashboard($q, dataservice, logger) {

        /*jshint validthis: true */
        var vm = this;

        vm.news = {
            title: 'Marvel Avengers',
            description: 'Marvel Avengers 2 is now in production!'
        };
        vm.avengerCount = 0;
        vm.avengers = [];
        vm.title = 'Dashboard';

        activate();

        function activate() {
            //var promises = [getAvengerCount(), getAvengersCast()];
            //return $q.all(promises).then(function() {
            //    logger.info('Activated Dashboard View');
            //});
        }
    }
})();
