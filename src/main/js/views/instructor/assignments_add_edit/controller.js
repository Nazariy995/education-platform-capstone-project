
function Controller($scope, $state, $stateParams, $timeout, assignment, AssignmentService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.isNew = $stateParams.isNew;
    this._$stateParams  = $stateParams;
    this._AssignmentService = AssignmentService;
    this.assignment = assignment;
    this._$timeout = $timeout;
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
}

Controller.prototype.add_eidtAssignment = function(valid, assignment){
    var self = this;
    self.error = null;
    var service = null;
    if(valid){
        if(self.isNew == true){
            service = self._AssignmentService.addAssignment(self.courseId, assignment);
        } else if(self.isNew == false) {
            service = self._AssignmentService.editAssignment(self.courseId, self.moduleId, assignment);
        }
        service.then(function(payload){
            self._$state.go('app.course.assignments', { courseId : self.courseId, created_updated : true });
        }, function(err){
            self.error = "ERROR: trying to create/edit an assignment";
        })
    }
}

module.exports = angular.module('app.views.instructor.assignments.add_edit', [])
.controller('Instructor.AssignmentsAddEdit', Controller);
