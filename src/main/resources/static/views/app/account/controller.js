
function Controller($scope, $state){
    "ngInject";
    this._$scope = $scope;
    this._$state = $state;
    this._$scope.pageName = "Account";
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
}



module.exports = angular.module('app.views.app.account.controller', [])
.controller('AccountCtrl', Controller);
