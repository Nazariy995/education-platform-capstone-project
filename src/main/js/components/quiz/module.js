
var controller = require('./controller');
var multipleChoice = require('./multiple_choice/module');
var numeric = require('./numeric/module');
var freeResponse = require('./free_response/module');

module.exports = angular.module('app.components.quiz', [
    multipleChoice.name,
    numeric.name,
    freeResponse.name
])
.controller("InputComponentController", controller)
