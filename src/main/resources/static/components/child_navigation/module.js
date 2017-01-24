
var childNavigationDirective = require('./child_navigation.js');

module.exports = angular.module('app.components.child_navigation', [])
.directive('childNavigation', function() {
    return new childNavigationDirective();
})


