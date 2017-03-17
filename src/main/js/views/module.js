var viewsApp = require('./app/module');
var studentViews = require('./student/module');
var instructorViews = require('./instructor/module');

module.exports = angular.module('app.views', [
    viewsApp.name,
    studentViews.name,
    instructorViews.name
]);

