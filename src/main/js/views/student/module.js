
var homeController = require("./home/controller");
var assignmentController = require("./assignment/controller");

module.exports = angular.module('app.views.student', [
    homeController.name,
    assignmentController.name
]);
