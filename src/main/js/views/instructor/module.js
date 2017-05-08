
var coursesAddEditController = require("./courses_add_edit/controller");
var courseMembersController = require('./course_members/controller');
var courseMembersAddController = require('./course_members_add/controller');
var assignmentsAddEditController = require('./assignments_add_edit/controller');
var assignmentsAddEditPages = require('./assignments_add_edit_pages/controller');
var assignmentsAddEditQuestions = require('./assignments_add_edit_questions/controller');
var assignmentsAddEditQuestion = require('./assignments_add_edit_question/controller');
var assignmentGroupsController = require('./assignment_groups/controller');

module.exports = angular.module('app.views.instructor', [
    coursesAddEditController.name,
    courseMembersController.name,
    courseMembersAddController.name,
    assignmentsAddEditController.name,
    assignmentsAddEditPages.name,
    assignmentsAddEditQuestions.name,
    assignmentsAddEditQuestion.name,
    assignmentGroupsController.name
]);
