'use strict';


//External
//require('jquery');
require('angular');
require('angular-ui-router');

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


