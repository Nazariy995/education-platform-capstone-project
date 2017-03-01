
function Controller($scope, $state, course, CourseService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add/Edit Course";
    this.course = course;
    this._CourseService = CourseService;
    this.today = new Date();

};

Controller.prototype.init = function(){


}

Controller.prototype.addCourse= function() {
    var self = this;
    self._CourseService.addCourse(self.course)
        .then(function(payload){
            console.log("Success");
            console.log(payload)
    }, function(err){
       self.error = err;
    });

}


module.exports = angular.module('app.views.instructor.courses.add_edit', [])
.controller('Instructor.CoursesAddEdit', Controller);
