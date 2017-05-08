
var multipleChoice = require('./multiple_choice/module');
var numeric = require('./numeric/module');
var freeResponse = require('./free_response/module');
var imageUpload = require('./image_upload/module');

module.exports = angular.module('app.components.quiz', [
    multipleChoice.name,
    numeric.name,
    freeResponse.name,
    imageUpload.name
])
