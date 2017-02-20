
function Controller($scope, $state, $stateParams, AssignmentService, GroupService){
    "ngInject";

    this.pageName = "Create/Edit Group";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = $stateParams.groupId;
    this._$state = $state;
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
    console.log(self.groupId);
}

Controller.prototype.getGroupMembers = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.groupMembers = payload.members;
            console.log("Get Group Members");
            console.log(self.groupMembers);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.getAssignmentMembers = function(){
    var self = this;
    self._AssignmentService.getAssignmentMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.assignmentMembers = payload;
            console.log("Get Assignment Members");
            console.log(self.assignmentMembers);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.addGroupMember = function(){
    var self = this;
    self._GroupService.addGroupMember(self.courseId, self.moduleId, self.groupId, self.newGroupMember)
        .then(function(payload){
            self.groupMembers = payload;
            self.newGroupMember = "";
            self.getAssignmentMembers();
            console.log("Returned Group Members After Adding a member");
            console.log(self.groupMembers);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.removeGroupMember = function(memberToBeRemovedId){
    var self = this;
    self._GroupService.removeGroupMember(self.courseId, self.moduleId, self.groupId, memberToBeRemovedId)
        .then(function(payload){
            self.groupMembers = payload;
            self.getAssignmentMembers();
            console.log("Returned Group Members After Adding a member");
            console.log(self.groupMembers);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.finalize = function(){
    var self= this;
    self._GroupService.finalize(self.courseId, self.moduleId, self.groupId)
        .then(function(payload){
            self._$state.go('app.course.assignment', {moduleId:self.moduleId}, { reload:true });
    }, function(err){
       self.error = err;
    });
}

module.exports = angular.module('app.views.student.assignment.group.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentGroupCtrl', Controller);
