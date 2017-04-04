
function Controller($scope, $state, $stateParams, CourseService, ConfirmationService, appSettings, SessionService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Grades";
    this._SessionService = SessionService;
    this.userRoles = appSettings.ROLES;
    this._CourseService = CourseService;
    this._ConfirmationService = ConfirmationService;
    this.courseId = $stateParams.courseId;
    this.grades = {};
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    var user = self._SessionService.getUser();
    //Allow cloning of course only if we are creating a new course
    if(user.roles.indexOf(self.userRoles.user) != -1){
        self.getStudentCourseGrades();
    }
}

Controller.prototype.getStudentCourseGrades  = function() {
    var self = this;
    self._CourseService.getCourseGrades(self.courseId)
        .then(function(payload){
            self.grades = payload;
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.getInstructorCourseGrades  = function() {
    var self = this;
    self._CourseService.getCourseGrades(self.courseId)
        .then(function(payload){
        var anchor = angular.element('<a/>');
        anchor.attr({
                 href: 'data:attachment/csv;charset=utf-8,' + encodeURI(payload),
                 target: '_blank',
                 download: 'grades.csv'
             })[0].click();
    }, function(err){
       self.error = err;
    });
};



module.exports = angular.module('app.views.app.courses.grades', [])
.controller('CourseGrades', Controller);
