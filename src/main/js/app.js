'use strict';

//External
require('angular');
require('angular-messages');
require('angular-ui-router');
require('angular-ui-bootstrap');
require('angular-animate');
require('ng-file-upload');
require('textAngular/dist/textAngular-sanitize')
require('textAngular');

//Internal
var views = require('./views/module');
var models = require('./models/module');
var components = require('./components/module');
var appController = require('./app_controller');
var appRouter = require('./app_router');
var appSettings = require('./app_settings');
var appRun = require('./app_run');
var appAuthInterceptor = require('./app_auth_interceptor');
var appProvider = require('./app_provider');

var appModule = angular.module("app", [
    //External
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',
    'ngFileUpload',
    'textAngular',
    'ngMessages',

    //Internal
    'app.templates',
    appSettings.name,
    models.name,
    components.name,
    views.name
])
.controller('AppController', appController)
.factory('SessionInjector', appAuthInterceptor)
.config(appRouter)
.config(['$httpProvider','$qProvider', function($httpProvider, $qProvider) {
    $httpProvider.interceptors.push('SessionInjector');
    $qProvider.errorOnUnhandledRejections(false);
}])
.config(appProvider)
.run(appRun);


