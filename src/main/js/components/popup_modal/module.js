var service = require('./service');
var controller = require('./controller');

module.exports = angular.module('app.components.popup_modal', [])
.service('ModalService', service)
.controller("PopupModalController", controller)
