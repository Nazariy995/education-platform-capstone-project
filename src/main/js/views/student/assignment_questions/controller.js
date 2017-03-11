
function Controller($scope, $state, $stateParams, lock, AssignmentService, QuestionService, appSettings){
    "ngInject";
    this.pageName = "Questions";
    this.maxPage = 1;
    this.minPage = 1;
    this.currentPage = 1;
    this.lock = lock;
    this.editable = false;
    this.pages = [];
    this._$scope = $scope;
    this._appSettings = appSettings;
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = $stateParams.groupId;
    this.data = {};
    this.questions = [];
    this.savedAnswers = {};
    this._AssignmentService = AssignmentService;
    this._QuestionService = QuestionService;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getLock();
    self._$scope.assignmentService = self._AssignmentService;
    self._$scope.$watch('assignmentService.assignmentDetails', function(newAssignmentDetails){
       if(newAssignmentDetails){
           self.maxPage = newAssignmentDetails.numPages;
           self.pages = new Array(self.maxPage);
       }
    });
    self.getAnswers();
    self.getQuestions(self.currentPage);
};

Controller.prototype.getLock = function(){
    var self = this;
    if(self.lock.hasLock && self.lock.isModuleEditable){
        self.editable = true;
    }
};

//Get Questions for the assignment
Controller.prototype.getQuestions = function(newPage){
    var self = this;
    self._QuestionService.getQuestions(self.courseId, self.moduleId, newPage)
        .then(function(payload){
            self.data = {};
            self.questions = payload;
            self.currentPage = newPage;
            console.log("Got the Assignment Questions Data");
            console.log(payload);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.saveAnswers = function(newPage){
    var self = this;
    self._QuestionService.saveAnswers(self.courseId, self.moduleId, self.groupId, self.data)
        .then(function(payload){
            self.savedAnswers = payload;
            self.getQuestions(newPage);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.getAnswers = function(newPage){
    var self = this;
    self._QuestionService.getAnswers(self.courseId, self.moduleId, self.groupId)
        .then(function(payload){
            self.savedAnswers = payload;
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.submit = function(){
    var self = this;
    console.log(self.data);
};

Controller.prototype.nextPage = function(){
    var self = this;
    if(self.currentPage <  self.maxPage){
        self.saveAnswers(self.currentPage+1);
    }
};

Controller.prototype.previousPage = function(){
    var self = this;
    if(self.currentPage > self.minPage){
        self.saveAnswers(self.currentPage-1);
    }
};

Controller.prototype.getPage = function(newPage){
    var self = this;
    if(newPage >= self.minPage && newPage <= self.maxPage){
        self.getQuestions(newPage);
    }
}

module.exports = angular.module('app.views.student.assignment.questions.controller', [
    'app.models.assignment',
    'app.models.question',
    'app.settings'
])
.controller('Student.AssignmentQuestionsCtrl', Controller);
