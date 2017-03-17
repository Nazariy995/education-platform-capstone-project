
var directive = require('./main_navigation.js');

module.exports = angular.module('app.components.main_navigation', [
    "app.components.services.session_service",
    "app.settings"
])
.directive('mainNavigation', directive)
