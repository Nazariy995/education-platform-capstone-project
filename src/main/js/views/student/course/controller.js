function Controller($scope, $state, course){
    "ngInject";
    this.courseName = course.courseId;

    $state.go('app.course.assignments')
};

module.exports = angular.module('app.views.student.course.controller', [ ])
.controller('Student.Course', Controller);
