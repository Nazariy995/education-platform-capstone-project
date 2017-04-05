
function Controller($scope, $state, $stateParams, CourseMembersService, ConfirmationService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Members";
    this._CourseMembersService = CourseMembersService;
    this._ConfirmationService = ConfirmationService;
    this.courseId = $stateParams.courseId;
    this.members = [];
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    //Allow cloning of course only if we are creating a new course
    self.getMembers();
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

//Drop the course member and get confirmation
Controller.prototype.dropMember = function(memberIndex) {
    var self = this;
    var selMember = self.members[memberIndex];
    var confirmation = "Are you sure you want to delete the following user?";
    var footNote = selMember.firstName + ", " + selMember.lastName;
    var modalInstance = self._ConfirmationService.open("", confirmation, footNote);
    modalInstance.result.then(function(){
        self._CourseMembersService.dropCourseMember(self.courseId, selMember.id)
            .then(function(payload){
                self.members = payload;
        }, function(err){
           self.error = err;
        });
    }, function(){
        console.log("They said no");
    });
};



module.exports = angular.module('app.views.instructor.courses.members', [])
.controller('Instructor.CourseMembers', Controller);
