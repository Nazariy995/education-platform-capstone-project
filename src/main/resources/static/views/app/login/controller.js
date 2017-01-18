

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

Controller.prototype.login = function(credentials){
    var self = this;
    self._AuthService.login(credentials).then(function(response){
        console.log(response);
        self._$state.go("home");
    },function(err){
        console.log("failed");
        self._$scope.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('LoginCtrl', Controller);

