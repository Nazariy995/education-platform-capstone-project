var accountController = require("./account/controller");
var homeController = require("./home/controller");
var loginController = require("./login/controller");
var coursesController = require("./courses/controller");


module.exports = angular.module('app.views.app', [
    homeController.name,
    loginController.name,
    accountController.name,
    coursesController.name
]);
