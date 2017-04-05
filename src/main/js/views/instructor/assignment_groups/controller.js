
function Controller($scope, $state, $stateParams, GroupService, AssignmentService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Assignment Groups";
    this._GroupService = GroupService;
    this._AssignmentService= AssignmentService;
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groups = {};
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
    //Get Assignment Groups for grading
    self.getAssignmentGroups();
}

Controller.prototype.getAssignmentGroups = function() {
    var self = this;
    self._GroupService.getAssignmentGroups(self.courseId, self.moduleId)
        .then(function(payload){
            self.groups = payload;
    }, function(err){
       self.error = "ERROR getting the assignment groups";
    });
};

module.exports = angular.module('app.views.instructor.assignment.groups', [])
.controller('Instructor.AssignmentGroups', Controller);
