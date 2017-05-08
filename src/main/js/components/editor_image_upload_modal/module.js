var service = require('./service');
var controller = require('./controller');

module.exports = angular.module('app.components.editor_image_upload_modal', [])
.service('EditorUploadService', service)
.controller("EditorUploadModalController", controller)
