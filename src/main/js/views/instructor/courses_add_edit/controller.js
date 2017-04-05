
function Controller($scope, $state, course, CourseService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add/Edit Course";
    this.cloneAllowed = false;
    this.course = course;
    this.allCourses = [];
    this._CourseService = CourseService;
    this.today = new Date();
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    //Allow cloning of course only if we are creating a new course
    if(!self.course){
        self.cloneAllowed = true;
        self.getAllPossibleToCloneCourses();
    }
}

Controller.prototype.addCourse= function(valid, course) {
    var self = this;
    if(valid){
        self.error = null;
        self._CourseService.addCourse(course)
            .then(function(payload){
                self._$state.go('app.courses', { created_updated : true });
        }, function(err){
           self.error = "ERROR creating/updating the course";
        });
    }
};

Controller.prototype.getAllPossibleToCloneCourses = function(){
    var self = this;
    self._CourseService.getAllInstructorCourses()
        .then(function(payload){
            self.allCourses = payload;
    }, function(err){
       self.error = err;
    });
}


module.exports = angular.module('app.views.instructor.courses.add_edit', [])
.controller('Instructor.CoursesAddEdit', Controller);
