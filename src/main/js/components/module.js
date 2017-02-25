
var services = require('./services/module');
var childNavigation = require('./child_navigation/module');
var mainNavigation = require('./main_navigation/module');
var quiz = require('./quiz/module');
var popup_modal = require('./popup_modal/module');

module.exports = angular.module('app.components', [
    services.name,
    childNavigation.name,
    mainNavigation.name,
    popup_modal.name,
    quiz.name
]);
