
function Controller($scope, $state, UserService){
    "ngInject";
    this._$scope = $scope;
    this._$state = $state;
    this._$scope.pageName = "Account";
    this._UserService = UserService;
    this.newPassword = {};
    this.changeSuccess = false;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
}


Controller.prototype.submit = function(){
    var self = this;
    self._UserService.newPassword(self.newPassword)
        .then(function(payload){
        self.changeSuccess = true;
    }, function(err){
       self.error = "ERROR changing your password";
    });
};

module.exports = angular.module('app.views.app.account.controller', [])
.controller('AccountCtrl', Controller);
