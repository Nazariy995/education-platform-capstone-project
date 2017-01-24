
function Controller($scope, $state, SessionService){
    "ngInject";
    this._$state = $state;
    this._$scope = $scope;
    this._$scope.courses = [];
    this.user =
};

module.exports = angular.module('app.views.student.home.controller', [])
.controller('Student.HomeCtrl', Controller);

