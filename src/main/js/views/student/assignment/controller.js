
function Controller($scope, $state, $stateParams, AssignmentService, GroupService){
    "ngInject";
    this._$scope = $scope;
    this._$state = $state;
    this.currentDate = new Date();
    this.pageName = "Assignment";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = "";
    this._AssignmentService = AssignmentService;
    this._GroupService = GroupService;
    this.assignment = {};
    this.grade = {};
    this.assignmentMembers = [];
    this.finalized = null;
    this.canStart = false;
    this.canCreateGroup = false;

    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    //watch finalized to set canStart and canCreateGroup accordingly
    self.setFinalized();
    //get Assignment Details
    self.getAssignmentDetails();
    //get Group Details
    self.getGroup();
    //Get Assignment Grade
    self.getAssignmentGrade();
};

//Watch finalized variable from the API
Controller.prototype.setFinalized = function(){
    var self = this;
    self._$scope.$watch(
        function watchFinalized( scope ){
            return (self.finalized);
        },
        function handleFinalized(newFinalized){
            //finalized, they can start the assignment
            //else they still have to create the group
            if(newFinalized == true){
                self.canStart = true;
                self.canCreateGroup = false;
            } else if(newFinalized == false){
                self.canStart = false;
                self.canCreateGroup = true;
            }

        }
    )
}

Controller.prototype.getAssignmentDetails = function(){
    var self = this;
    self._AssignmentService.getAssignmentDetails(self.courseId, self.moduleId)
        .then(function(payload){
            self.assignment = payload;
            self.pageName = payload.moduleTitle;
            self._AssignmentService.assignmentDetails = payload;
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.getAssignmentGrade = function(){
    var self = this;
    self._AssignmentService.getAssignmentGrade(self.courseId, self.moduleId)
        .then(function(payload){
            self.grade = payload;
    }, function(err){
       self.error = err;
    });
}

Controller.prototype.getGroup = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            if("isFinalized" in payload) {
                self.assignmentMembers = payload.members;
                self.groupId = payload.id;
                self.finalized = payload.isFinalized;
            } else {
                self.finalized = false;
            }
            console.log("Got the Assignment Group Data");
            console.log(payload);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.navToGroup = function(){
    var self = this;
    if(self.canCreateGroup){
        self._$state.go('app.course.assignment.group',{ groupId:self.groupId });
    }
};

Controller.prototype.navToLogin = function(){
    var self = this;
    if(self.canStart){
        self._$state.go('app.course.assignment.login',{groupId:self.groupId});
    }
}

Controller.prototype.navToQuestions = function(){
    var self = this;
    self._$state.go('app.course.assignment.questions',{ viewOnly: true, groupId:self.groupId });
}


module.exports = angular.module('app.views.student.assignment.details.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentDetailsCtrl', Controller);
