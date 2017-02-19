

var courseModel = require('./course');
var assignmentModel = require('./assignment');
var groupModel = require('./group');

module.exports = angular.module('app.models', [
    courseModel.name,
    assignmentModel.name,
    groupModel.name
])
