
var controller = require('./controller');
var multipleChoice = require('./multiple_choice/module');
var numeric = require('./numeric/module');

module.exports = angular.module('app.components.quiz', [
    multipleChoice.name,
    numeric.name
])
.controller("InputComponentController", controller)
