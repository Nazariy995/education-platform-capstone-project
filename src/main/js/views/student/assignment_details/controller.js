
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this._AssignmentService = AssignmentService;
    this.assignment = {};
    this.assignmentMembers = [];
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self._AssignmentService.getAssignmentDetails(self.courseId, self.moduleId)
        .then(function(assignmentDetails){
            self.assignment = assignmentDetails;
            self.pageName = assignmentDetails.moduleTitle;
            console.log("Got the Assignment Details");
    }, function(err){
       self.error = err;
    });
}

Controller.prototype.setGroups = function(){
    var self = this;
    self._AssignmentService.getAssignmentGroup(self.courseId, self.moduleId)
        .then(function(assignmentMembers){
            self.assignmentMembers = assignmentMembers;
            console.log("Got the Assignment Group Data");
    }, function(err){
       self.error = err;
    });
}


module.exports = angular.module('app.views.student.assignment.details.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentDetailsCtrl', Controller);
