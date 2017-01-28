var viewsApp = require('./app/module');
var studentViews = require('./student/module');

module.exports = angular.module('app.views', [
    viewsApp.name,
    studentViews.name
]);

