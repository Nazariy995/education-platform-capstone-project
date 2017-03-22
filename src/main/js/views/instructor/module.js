
var coursesAddEditController = require("./courses_add_edit/controller");
var courseMembersController = require('./course_members/controller');
var courseMembersAddController = require('./course_members_add/controller');
var assignmentsAddEditController = require('./assignments_add_edit/controller');

module.exports = angular.module('app.views.instructor', [
    coursesAddEditController.name,
    courseMembersController.name,
    courseMembersAddController.name,
    assignmentsAddEditController.name
]);
