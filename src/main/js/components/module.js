
var services = require('./services/module');
var childNavigation = require('./child_navigation/module');
var mainNavigation = require('./main_navigation/module');
var quiz = require('./quiz/module');
var questionCreation = require('./question_creation/module');
var questionGrading = require('./question_grading/module');
var confirmationModal = require('./confirmation_modal/module');
var restrictModal = require('./restrict/module');
var dateFormat = require('./date_format/module');
var editorImageUploadModal = require('./editor_image_upload_modal/module');
var confirmationPassword = require('./confirmation_password/module');

module.exports = angular.module('app.components', [
    services.name,
    childNavigation.name,
    mainNavigation.name,
    confirmationModal.name,
    quiz.name,
    restrictModal.name,
    dateFormat.name,
    questionCreation.name,
    editorImageUploadModal.name,
    confirmationPassword.name,
    questionGrading.name
]);
