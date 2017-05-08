
function Controller($scope, $state, $stateParams, AssignmentService, ConfirmationService){
    "ngInject";

    this.pageName = "Assignments";
    this.courseId = $stateParams.courseId;
    this._AssignmentService = AssignmentService;
    this._ConfirmationService = ConfirmationService;
    this.assignments = [];
    this.currentDate = new Date();
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getAssignments();
}

Controller.prototype.getAssignments = function(){
    var self = this;
    self._AssignmentService.getAssignments(self.courseId)
        .then(function(assignments){
        self.assignments = assignments;
    }, function(err){
       self.error = "ERROR getting the assignments";
    });
};

Controller.prototype.dropAssignment = function(moduleId){
    var self = this;
    var confirmation = "Are you sure you want to delete the Assignment?";
    var footNote = "Once deleted, you can't get it back.";
    var modalInstance = self._ConfirmationService.open("", confirmation, footNote);
    modalInstance.result.then(function(){
        self._AssignmentService.dropAssignment(self.courseId, moduleId)
        .then(function(payload){
            self.assignments = payload;
        }, function(err){
            self.error = "ERROR deleting the Assignment";
        });
    }, function(){
        console.log("They said no");
    });
}


module.exports = angular.module('app.views.app.assignments.controller', [
    'app.models.assignment'
])
.controller('AssignmentsCtrl', Controller);
