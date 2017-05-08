

function Controller($scope, $state, $window, $stateParams, AuthService, UserService, appSettings){
    "ngInject";
    this._AuthService = AuthService;
    this._UserService = UserService;
    this.sessionExpired = $stateParams.sessionExpired;
    this._$state = $state;
    this._$scope = $scope;
    this.error = null;
    this.userRoles = appSettings.ROLES;
    this.passwordReset = false;

    this.credentials = {
        username: '',
        password : ''
    };
};

Controller.prototype.login = function(){
    var self = this;
    self.passwordReset = false;
    self._AuthService.login(self.credentials).then(function(user){
        if(user.roles.indexOf(self.userRoles.user) != -1){
            self._$state.go("app.courses");
        } else if(user.roles.indexOf(self.userRoles.instructor) != -1) {
            self._$state.go("app.courses");
        } else if (user.roles.indexOf(self.userRoles.admin) != -1) {
            console.log("You're an admin!");
            self._$state.go("app.admin");
        }
    },function(err){
        self.error = "There was a problem logging in. Please try again";
    });
};

Controller.prototype.resetPassword = function(){
    var self = this;
    if(self.credentials.username == ""){
        self.error = "Email required to reset password";
    } else {
        var payload = {
            email : self.credentials.username
        }
        self._UserService.resetPassword(payload)
        .then(function(res){
            self.passwordReset = true;
        }, function(err){
            self.error = "ERROR Resetting Password. Double check email"
        })
    };
}


module.exports = angular.module('app.views.app.login.controller', [
    'app.components.popup_modal'
])
.controller('LoginCtrl', Controller);

