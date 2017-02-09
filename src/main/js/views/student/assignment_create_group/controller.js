
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Create Group";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
}


module.exports = angular.module('app.views.student.assignment.group.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentGroupCtrl', Controller);
