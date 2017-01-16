

var authService = require('./auth_service');

module.exports = angular.module('app.components.services', [
    authService.name
]);
