'use strict';


//External
//require('angular');
//require('angular-ui-router');

//Internal
var views = require('./views/module');
var models = require('./models/module');
var components = require('./components/module');
var appController = require('./app_controller');
var appRouter = require('./app_router');

angular.module("app", [
    //External
    'ui.router',

    //Internal
    models.name,
    components.name,
    views.name
])
.controller('AppController', appController)
.config(appRouter);
