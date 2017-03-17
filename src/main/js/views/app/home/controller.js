
function Controller($scope, $state) {
    "ngInject";
    //Redirect to courses page
    $state.go("app.courses");
};


module.exports = angular.module('app.views.app.controller', [])
.controller('HomeCtrl', Controller);
