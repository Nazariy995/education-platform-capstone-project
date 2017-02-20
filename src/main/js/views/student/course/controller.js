function Controller($scope, $state, course){
    "ngInject";
    this.courseName = course.courseTitle;
};

module.exports = angular.module('app.views.student.course.controller', [ ])
.controller('Student.Course', Controller);
