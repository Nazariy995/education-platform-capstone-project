'use strict';


//External
require('angular');
require('angular-ui-router');
require('angular-ui-bootstrap');
require('angular-animate');

//Internal
var views = require('./views/module');
var models = require('./models/module');
var components = require('./components/module');
var appController = require('./app_controller');
var appRouter = require('./app_router');
var appSettings = require('./app_settings');
var appAuthInterceptor = require('./app_auth_interceptor');

angular.module("app", [
    //External
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',

    //Internal
    appSettings.name,
    models.name,
    components.name,
    views.name
])
.controller('AppController', appController)
.factory('SessionInjector', appAuthInterceptor)
.config(appRouter)
.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('SessionInjector');
}]);


