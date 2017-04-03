
function Controller($scope, $state, $stateParams, CourseMembersService, ConfirmationService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Grades";
    this._CourseMembersService = CourseMembersService;
    this._ConfirmationService = ConfirmationService;
    this.courseId = $stateParams.courseId;
    this.members = [];
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    //Allow cloning of course only if we are creating a new course
//    self.getMembers();
}

Controller.prototype.getMembers = function() {
    var self = this;
    self._CourseMembersService.getCourseMembers(self.courseId)
        .then(function(payload){
            self.members = payload;
    }, function(err){
       self.error = err;
    });
};



module.exports = angular.module('app.views.app.courses.grades', [])
.controller('CourseGrades', Controller);
