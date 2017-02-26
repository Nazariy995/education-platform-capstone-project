
function Controller($scope, $state, $stateParams, AssignmentService, QuestionService, appSettings){
    "ngInject";

    this.pageName = "Questions";
    this.pageNumber = 1;
    this._appSettings = appSettings;
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.data = {};
    this.questions = [];
    this._AssignmentService = AssignmentService;
    this._QuestionService = QuestionService;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getQuestions();
};

Controller.prototype.getQuestions = function(){
    var self = this;
    self._QuestionService.getQuestions(self.courseId, self.moduleId, self.pageNumber)
        .then(function(payload){
            self.questions = payload;
            console.log("Got the Assignment Questions Data");
            console.log(payload);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.submit = function(){
    var self = this;
    console.log(self.data);
}

module.exports = angular.module('app.views.student.assignment.questions.controller', [
    'app.models.assignment',
    'app.models.question',
    'app.settings'
])
.controller('Student.AssignmentQuestionsCtrl', Controller);
