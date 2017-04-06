
var directive = require('./directive.js');

module.exports = angular.module('app.components.confirmation_password', [])
.directive('compareTo', directive)
