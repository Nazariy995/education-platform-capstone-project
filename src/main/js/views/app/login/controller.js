

function Controller($scope, $state, $window, $stateParams, AuthService, appSettings){
    "ngInject";
    this._AuthService = AuthService;
    this.sessionExpired = $stateParams.sessionExpired;
    this._$state = $state;
    this._$scope = $scope;
    this.error = null;
    this.userRoles = appSettings.ROLES;

    this.credentials = {
        username: '',
        password : ''
    };
};

Controller.prototype.login = function(){
    var self = this;
    
    self._AuthService.login(self.credentials).then(function (user) {
        if(user.roles.indexOf(self.userRoles.user) != -1){
            self._$state.go("app.courses");
        } else if(user.roles.indexOf(self.userRoles.instructor) != -1) {
            self._$state.go("app.courses");
        } else if (user.roles.indexOf(self.userRoles.admin) != -1) {
            console.log("You're an admin!");
            self._$state.go("app.admin");
        }
    },function(err){
        self.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [
    'app.components.popup_modal'
])
.controller('LoginCtrl', Controller);

