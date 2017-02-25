
var controller = require('./controller');
var directive = require('./directive');

module.exports = angular.module('app.components.quiz', [])
.controller("InputComponentController", controller)
.directive("appStudentMultipleChoice", directive)
