'use strict';

angular.module('myApp.config', [])
.constant('ConnectionConfig', {
    api_endpoint: 'http://localhost:8080/api/1.0',
    token_endpoint: 'http://localhost:8080/oauth/token',
    client_id: 'boot_webapp',
    client_secret: '59d14f01-38da-401c-8014-b6c0356271c8'
});
