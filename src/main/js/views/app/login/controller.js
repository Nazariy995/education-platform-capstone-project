

function Controller($scope, $state, AuthService){
    "ngInject";

    this._AuthService = AuthService;
    this._$state = $state;
    this._$scope = $scope;
    this._$scope.error = null;

    this.credentials = {
        username: '',
        password : ''
    };
};

Controller.prototype.login = function(){
    var self = this;
    self._AuthService.login(self.credentials).then(function(user){

        if(user.roles.indexOf("USER") != -1){
            self._$state.go("home.student");
        } else if(user.roles.indexOf("INSTRUCTOR") != -1) {
            self._$state.go("home.teacher");
        }
    },function(err){
        console.log("failed");
        self._$scope.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('LoginCtrl', Controller);
