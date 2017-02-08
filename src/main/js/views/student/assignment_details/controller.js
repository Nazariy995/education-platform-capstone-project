
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this._AssignmentService = AssignmentService;
    this.assignmentDetails = {};
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self._AssignmentService.getAssignmentDetails(self.courseId, self.moduleId)
        .then(function(assignmentDetails){
            self.assignmentDetails = assignmentDetails;
            console.log("Got the Assignment Details");
    }, function(err){
       self.error = err;
    });

}

module.exports = angular.module('app.views.student.assignment.details.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentDetailsCtrl', Controller);
