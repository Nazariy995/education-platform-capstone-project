

var courseModel = require('./course');
var navigationLinksModel = require('./navigation_link');

module.exports = angular.module('app.models', [
    courseModel.name,
    navigationLinksModel.name
])
