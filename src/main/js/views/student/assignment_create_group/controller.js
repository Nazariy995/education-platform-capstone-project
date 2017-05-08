
function Controller($scope, $state, $stateParams, AssignmentService, ConfirmationService, GroupService, groupId){
    "ngInject";
    this.pageName = "Create/Edit Group";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = $stateParams.groupId;
    this._$state = $state;
    this._ConfirmationService = ConfirmationService;
    this._AssignmentService = AssignmentService;
    this._GroupService = GroupService;
    this.groupMembers = [];
    this.assignmentMembers = [];
    this.newGroupMember = "";
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getGroupMembers();
    self.getAssignmentMembers();
}

Controller.prototype.getGroupMembers = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.groupMembers = payload.members;
    }, function(err){
       self.error = "ERROR getting group members data";
    });
};

Controller.prototype.getAssignmentMembers = function(){
    var self = this;
    self._AssignmentService.getAssignmentMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.assignmentMembers = payload;
    }, function(err){
       self.error = "ERROR getting assignment errors data";
    });
};

Controller.prototype.addGroupMember = function(){
    var self = this;
    self._GroupService.addGroupMember(self.courseId, self.moduleId, self.groupId, self.newGroupMember)
        .then(function(payload){
            self.groupMembers = payload;
            self.newGroupMember = "";
            self.getAssignmentMembers();
    }, function(err){
       self.error = "ERROR adding a group member";
    });
};

Controller.prototype.removeGroupMember = function(memberToBeRemovedId){
    var self = this;
    self._GroupService.removeGroupMember(self.courseId, self.moduleId, self.groupId, memberToBeRemovedId)
        .then(function(payload){
            self.groupMembers = payload;
            self.getAssignmentMembers();
    }, function(err){
       self.error = "ERROR removing a group member";
    });
};

Controller.prototype.finalize = function(){
    var self= this;
    var confirmation = "Are you sure you want to finalize the group?";
    var footNote = "Once finalized, the group cannot be changed!";
    var modalInstance = self._ConfirmationService.open("", confirmation, footNote);
    modalInstance.result.then(function(){
        self._GroupService.finalize(self.courseId, self.moduleId, self.groupId)
            .then(function(payload){
                self._$state.go('app.course.assignment', {moduleId:self.moduleId}, { reload:true });
        }, function(err){
           self.error = "ERROR finalizing the group";
        });
    }, function(){
        console.log("They said no");
    });
}

module.exports = angular.module('app.views.student.assignment.group.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentGroupCtrl', Controller);
