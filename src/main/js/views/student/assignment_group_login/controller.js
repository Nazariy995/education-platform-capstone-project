
function Controller($scope, $state, $stateParams, $q, AssignmentService, GroupService){
    "ngInject";

    this.pageName = "Login";
    this.courseId = $stateParams.courseId;
    this.moduleId = $stateParams.moduleId;
    this.groupId = $stateParams.groupId;
    this._$state = $state;
    this._$q = $q;
    this._GroupService = GroupService;
    this.groupMembersCount = 0;
    this.membersLoginInfo = [];
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getGroupMembersCount();
};

Controller.prototype.getGroupMembersCount = function(){
    var self = this;
    self._GroupService.getGroupMembers(self.courseId, self.moduleId)
        .then(function(payload){
            self.groupMembersCount = payload.members.length-1;
            console.log("Get Group Members Count");
            console.log(self.groupMembersCount);
    }, function(err){
       self.error = err;
    });
};

Controller.prototype.groupCheckin = function(){
    var self = this;
    var loginError = false;

    var promise = self._$q.all(null);

    //loop through all of the members
    angular.forEach(self.membersLoginInfo, function(memberLogin, key){
        promise = promise.then(function(){
            //Delete error field so that it is not passed to the API
            if('error' in memberLogin) delete memberLogin.error;

            return self._GroupService.groupCheckin(self.courseId, self.moduleId, self.groupId, memberLogin)
            .then(function(payload){
                console.log("Response from Group Checkin");
            }, function(err){
                console.log("There was an error");
                console.log(err);
                memberLogin.error = "Incorrect Username/Password";
                loginError = true;
            });
        })
    });

    //when all of the requests have finished navigate to the questions page
    promise.then(function(){
        self.navToQuestions(loginError);
    })

};

Controller.prototype.navToQuestions = function(loginError){
    var self = this;
    if(!loginError){
        self._$state.go('app.course.assignment.questions', { groupId:self.groupId});
    };
};

module.exports = angular.module('app.views.student.assignment.login.controller', [
    'app.models.assignment',
    'app.models.group'
])
.controller('Student.AssignmentLoginCtrl', Controller);
