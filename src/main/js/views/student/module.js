
var homeController = require("./home/controller");
var assignmentController = require("./assignment/controller");
var assignmentDetailsController = require("./assignment_details/controller");
var courseController = require("./course/controller");
var gradesController = require("./grades/controller");

module.exports = angular.module('app.views.student', [
    homeController.name,
    assignmentController.name,
    courseController.name,
    gradesController.name,
    assignmentDetailsController.name
]);
