

var courseModel = require('./course');
var assignmentModel = require('./assignment');

module.exports = angular.module('app.models', [
    courseModel.name,
    assignmentModel.name
])
