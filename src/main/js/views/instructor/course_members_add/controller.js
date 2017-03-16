
function Controller($scope, $state, $stateParams, $timeout, CourseMembersService, Upload){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add Members";
    this._CourseMembersService = CourseMembersService;
    this.courseId = $stateParams.courseId;
    this._$timeout = $timeout;
    this.members = [];
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    //Allow cloning of course only if we are creating a new course
//    self.getMembers();
}

Controller.prototype.uploadMembers = function(file) {
    var self = this;
    self._CourseMembersService.addCourseMembers(self.courseId, file)
        .then(function(payload){
            self._$timeout(function(){
                file.result = payload;
            });
    }, function(err){
       self.error = err.status + ":" + response.data;
    });
};

module.exports = angular.module('app.views.instructor.courses.members.add', [])
.controller('Instructor.CourseMembersAdd', Controller);
