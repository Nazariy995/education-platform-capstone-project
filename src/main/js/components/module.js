
var services = require('./services/module');
var childNavigation = require('./child_navigation/module');

module.exports = angular.module('app.components', [
    services.name,
    childNavigation.name
]);
