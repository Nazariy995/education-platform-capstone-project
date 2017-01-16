
function Controller($scope,$state) {
    "ngInject";

    this._$scope = $scope;
    this._$scope.navigation_links = [];


}

module.exports = angular.module('app.views.app.controller', [])
.controller('AppHomeCtrl', Controller);
