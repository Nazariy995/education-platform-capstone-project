
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Assignments";
    this.courseId = $stateParams.courseId;
    this._AssignmentService = AssignmentService;
    this.assignments = [];
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self._AssignmentService.getAssignments(self.courseId).then(function(assignments){
        self.assignments = assignments;
    }, function(err){
       self.error = err;
    });

}

module.exports = angular.module('app.views.app.assignments.controller', [
    'app.models.assignment'
])
.controller('AssignmentsCtrl', Controller);
