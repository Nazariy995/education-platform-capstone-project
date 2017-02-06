
var homeController = require("./home/controller");
var assignmentController = require("./assignment/controller");
var courseController = require("./course/controller");

module.exports = angular.module('app.views.student', [
    homeController.name,
    assignmentController.name,
    courseController.name
]);
