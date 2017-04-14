var adminAddEditController = require("./user_add_edit/controller");
var adminHomeController = require("./home/controller");

module.exports = angular.module('app.views.admin', [
    adminAddEditController.name,
    adminHomeController.name
]);
