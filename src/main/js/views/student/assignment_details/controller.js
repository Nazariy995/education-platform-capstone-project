
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this._AssignmentService = AssignmentService;
    this.assignment = {};
    this.assignmentMembers = [];
    this.locked = true;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self._AssignmentService.getAssignmentDetails(self.courseId, self.moduleId)
        .then(function(assignmentDetails){
            self.assignment = assignmentDetails;
            self.pageName = assignmentDetails.moduleTitle;
            self.locked = assignmentDetails.locked;
            console.log("Got the Assignment Details");
            self.getGroup();
    }, function(err){
       self.error = err;
    });
}

Controller.prototype.getGroup = function(){
    var self = this;
    self._AssignmentService.getAssignmentGroup(self.courseId, self.moduleId)
        .then(function(assignmentGroupData){
            self.assignmentMembers = assignmentGroupData.members;
            console.log("Got the Assignment Group Data");
            console.log(assignmentGroupData);
    }, function(err){
       self.error = err;
    });
}


module.exports = angular.module('app.views.student.assignment.details.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentDetailsCtrl', Controller);
