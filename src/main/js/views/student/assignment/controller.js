
function Controller($scope, $state, $stateParams, AssignmentService, GroupService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = null;
    this._AssignmentService = AssignmentService;
    this._GroupService = GroupService;
    this.assignment = {};
    this.assignmentMembers = [];
    this.finalized = true;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self._AssignmentService.getAssignmentDetails(self.courseId, self.moduleId)
        .then(function(payload){
            self.assignment = payload;
            self.pageName = payload.moduleTitle;
            console.log("Got the Assignment Details");
            self.getGroup(); //Uncommented it for right now because currently it is giving me an error
    }, function(err){
       self.error = err;
    });
}

Controller.prototype.getGroup = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.assignmentMembers = payload.members;
            self.groupId = payload.id;
            self.finalized = payload.isFinalized;
            console.log("Got the Assignment Group Data");
            console.log(payload);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.navToGroup = function(){
    var self = this;
    if(!self.finalized){
        self._$state.go('app.course.assignment.group',{groupId:self.groupId});
    }
};

Controller.prototype.navToLogin = function(){
    var self = this;
    if(self.finalized){
        self._$state.go('app.course.assignment.login',{groupId:self.groupId});
    }
}


module.exports = angular.module('app.views.student.assignment.details.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentDetailsCtrl', Controller);
