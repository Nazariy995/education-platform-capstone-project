
var services = require('./services/module');
var childNavigation = require('./child_navigation/module');
var mainNavigation = require('./main_navigation/module');
var quiz = require('./quiz/module');
var popupModal = require('./popup_modal/module');
var restrictModal = require('./restrict/module');

module.exports = angular.module('app.components', [
    services.name,
    childNavigation.name,
    mainNavigation.name,
    popupModal.name,
    quiz.name,
    restrictModal.name
]);
