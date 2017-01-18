

var authService = require('./auth_service');
var sessionService = require('./session_service');

module.exports = angular.module('app.components.services', [
    authService.name,
    sessionService.name
]);
