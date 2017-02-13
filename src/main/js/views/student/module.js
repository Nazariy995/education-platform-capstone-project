
var homeController = require("./home/controller");
var assignmentController = require("./assignments/controller");
var assignmentDetailsController = require("./assignment/controller");
var assignmentGroupController = require("./assignment_create_group/controller");
var assignmentLoginController = require("./assignment_group_login/controller");
var assignmentQuestionsController = require('./assignment_questions/controller');
var courseController = require("./course/controller");
var gradesController = require("./grades/controller");

module.exports = angular.module('app.views.student', [
    homeController.name,
    assignmentController.name,
    courseController.name,
    gradesController.name,
    assignmentDetailsController.name,
    assignmentGroupController.name,
    assignmentLoginController.name,
    assignmentQuestionsController.name
]);
