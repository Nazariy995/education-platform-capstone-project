var accountController = require("./account/controller");
var homeController = require("./home/controller");
var loginController = require("./login/controller");
var coursesController = require("./courses/controller");
var assignmentsController = require("./assignments/controller");
var courseGradesController = require("./course_grades/controller");


module.exports = angular.module('app.views.app', [
    homeController.name,
    loginController.name,
    accountController.name,
    coursesController.name,
    assignmentsController.name,
    courseGradesController.name
]);
