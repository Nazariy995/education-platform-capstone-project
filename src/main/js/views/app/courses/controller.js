
function Controller($state, $stateParams, CourseService, ConfirmationService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Courses";
    this._CourseService = CourseService;
    this._ConfirmationService = ConfirmationService;
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getCourses();
}

//Get the courses that are currently open or will be open in the future
Controller.prototype.getCourses = function(){
    var self = this;
    self._CourseService.getCourses().then(
        function(payload){
        self.courses = payload;
    }, function(err){
        self.error = "ERROR retrieving courses";
    });
};

//Purpose: Get all the courses including the ones that have already closed
Controller.prototype.getAllCourses = function(){
    var self = this;
    self._CourseService.getAllCourses().then(
        function(payload){
        self.courses = payload;
    }, function(err){
        self.error = "ERROR retrieving courses";
    });
};

Controller.prototype.dropCourse = function(course){
    var self = this;
    var confirmation = "Are you sure you want to delete the following Course?";
    var footNote = course.courseTitle + ", " + course.courseCode;
    var modalInstance = self._ConfirmationService.open("", confirmation, footNote);
    modalInstance.result.then(function(){
        self._CourseService.dropCourse(course.id)
        .then(function(payload){
            self.courses = payload;
        }, function(err){
            self.error = "ERROR deleting the Course";
        });
    }, function(){
        console.log("They said no");
    });
};

module.exports = angular.module('app.views.app.courses.controller', [])
.controller('CoursesCtrl', Controller);

