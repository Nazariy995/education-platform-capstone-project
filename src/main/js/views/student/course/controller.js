function Controller($scope, $state, $stateParams){
    "ngInject";
    this.courseName = $stateParams.courseId;

    $state.go('app.course.assignments')
};

module.exports = angular.module('app.views.student.course.controller', [ ])
.controller('Student.Course', Controller);
