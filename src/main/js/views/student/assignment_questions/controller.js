
function Controller($scope, $state, $stateParams, AssignmentService){
    "ngInject";

    this.pageName = "Questions";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this._AssignmentService = AssignmentService;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
};


module.exports = angular.module('app.views.student.assignment.questions.controller', [
    'app.models.assignment'
])
.controller('Student.AssignmentQuestionsCtrl', Controller);
