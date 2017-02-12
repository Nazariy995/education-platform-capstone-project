
function Controller($scope, $state, $stateParams, AssignmentService, GroupService){
    "ngInject";

    this.pageName = "Login";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this._GroupService = GroupService;
    this.groupMembersCount = 0;
    this.membersLoginInfo = [];
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getGroupMembersCount();
};


Controller.prototype.getGroupMembersCount = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.groupMembersCount = payload.members.length;
            console.log("Get Group Members Count");
            console.log(self.groupMembersCount);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.groupCheckin = function(){
    var self = this;
    console.log(self.membersLoginInfo);
}


module.exports = angular.module('app.views.student.assignment.login.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentLoginCtrl', Controller);
