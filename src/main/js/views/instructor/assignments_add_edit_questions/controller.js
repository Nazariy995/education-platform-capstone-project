
function Controller($scope, $state, $stateParams, appSettings, AssignmentService, QuestionService, ConfirmationService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Add/Edit Assignment Questions";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.pageNum = $stateParams.pageNum;
    this.questionTypes = appSettings.QUESTION_TYPES;
    this._$stateParams  = $stateParams;
    this._AssignmentService = AssignmentService;
    this._QuestionService = QuestionService;
    this._ConfirmationService = ConfirmationService;
    this.selectedQuestionType = "MULTIPLE_CHOICE";
    this.questions = [];
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getQuestions();
}

Controller.prototype.getQuestions = function() {
    var self = this;
    self._QuestionService.getQuestions(self.courseId, self.moduleId, self.pageNum)
    .then(function(payload){
        self.questions = payload
    }, function(err){
        self.error = "ERROR getting questions"
    });
}

Controller.prototype.createPagesStruct = function(number){
    var pages = [];
    for(var i = 1; i<=number; i++){
        pages.push(i);
    }
    return pages;
}


Controller.prototype.addQuestion = function(){
    var self = this;
    if(self.selectedQuestionType){
        var params = {
            moduleId : self.moduleId,
            pageNum : self.pageNum,
            questionId : "new",
            questionType : self.selectedQuestionType,
            isNew : true
        }
        self._$state.go('app.course.assignments_add_edit_question', params);
    }
};

module.exports = angular.module('app.views.instructor.questions.add_edit', [])
.controller('Instructor.QuestionsAddEdit', Controller);
