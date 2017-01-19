
function Controller($scope, $state){
    "ngInject";
    this._$state = $state;
    this._$scope = $scope;
    this._$scope.courses = [];
};

module.exports = angular.module('app.views.student.home.controller', [])
.controller('Student.HomeCtrl', Controller);

