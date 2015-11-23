/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('connSettings', {
            'backendUrl': 'http://localhost:8080',
            'clientId': '59d14f01-38da-401c-8014-b6c0356271c9'
        });
})();
