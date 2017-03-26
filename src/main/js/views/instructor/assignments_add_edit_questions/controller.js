
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


Controller.prototype.addPage = function(){
    var self = this;
    self._QuestionService.addPage(self.courseId, self.moduleId)
    .then(function(payload){
        self.pages.push(self.pages.length+1);
    }, function(err){
        self.error = "ERROR adding a page to the assignment";
    });
}

Controller.prototype.dropPage = function(pageNum){
    var self = this;
    var confirmation = "Are you sure you want to delete page #" + pageNum + "?";
    var footNote = "This cannot be undone!";
    var modalInstance = self._ConfirmationService.open("", confirmation, footNote);

    modalInstance.result.then(function(){
        self._QuestionService.dropPage(self.courseId, self.moduleId, pageNum)
        .then(function(payload){
            self.pages = self.createPagesStruct(self.pages.length-1);
        }, function(err){
            self.error = "ERROR deleting a page from the assignment";
        });
    }, function(){
        console.log("They said no");
    });
}

module.exports = angular.module('app.views.instructor.questions.add_edit', [])
.controller('Instructor.QuestionsAddEdit', Controller);
