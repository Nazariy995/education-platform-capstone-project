
var multipleChoice = require('./multiple_choice/module');
var numeric = require('./numeric/module');
var freeResponse = require('./free_response/module');
var imageUpload = require('./image_upload/module');
var frontText = require('./front_text/module');

module.exports = angular.module('app.components.question_creation', [
    freeResponse.name,
    imageUpload.name,
    frontText.name,
    numeric.name,
    multipleChoice.name
]);
