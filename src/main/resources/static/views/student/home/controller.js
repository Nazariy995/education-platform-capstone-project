
function Controller($scope, $state, CourseService){
    "ngInject";
    this._$scope = $scope;
    this._$state = $state;
    this._$scope.pageName = "Courses";
    this._CourseService = CourseService;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    var courses = self._CourseService.getCourses().then(function(courses){
        self._$scope.courses = courses;
    }, function(err){
        self._$scope.error = err;
    });
}

module.exports = angular.module('app.views.student.home.controller', [ ])
.controller('Student.HomeCtrl', Controller);

