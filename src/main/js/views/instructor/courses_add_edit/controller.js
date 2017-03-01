
function Controller($scope, $state, course){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add/Edit Course";
};


module.exports = angular.module('app.views.instructor.courses.add_edit', [])
.controller('Instructor.CoursesAddEdit', Controller);
