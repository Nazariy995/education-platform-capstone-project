
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Assignments";
    this._AssignmentService = AssignmentService;
    console.log($stateParams);
    this.init();

};

Controller.prototype.init = function(){
    var self = this;
}

module.exports = angular.module('app.views.student.assignments.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentCtrl', Controller);
