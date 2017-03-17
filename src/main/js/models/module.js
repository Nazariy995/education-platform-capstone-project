

var courseModel = require('./course');
var assignmentModel = require('./assignment');
var groupModel = require('./group');
var questionModel = require('./question');
var courseMembersModel = require('./course_members');

module.exports = angular.module('app.models', [
    courseModel.name,
    assignmentModel.name,
    groupModel.name,
    questionModel.name,
    courseMembersModel.name
])
