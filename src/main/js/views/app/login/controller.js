

function Controller($scope, $state, $window, AuthService, ModalService, appSettings){
    "ngInject";
    this._AuthService = AuthService;
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
    self._AuthService.login(self.credentials).then(function(user){

        if(user.roles.indexOf(self.userRoles.user) != -1){
            self._$state.go("app.courses");
        } else if(user.roles.indexOf(self.userRoles.instructor) != -1) {
            self._$state.go("app.courses");
        }
    },function(err){
        console.log("failed");
        self.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [
    'app.components.popup_modal'
])
.controller('LoginCtrl', Controller);

