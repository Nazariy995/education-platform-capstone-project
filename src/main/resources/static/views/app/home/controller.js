
function Controller($scope, $state, SessionService) {
    "ngInject";

    var self = this;
    self._$scope = $scope;
    self._$scope.navigation_links = [];
    self.user_roles = SessionService.getUser().roles;
//
//    if(self.user_roles.indexOf("USER") != -1){
//        console.log("yes");
//        $state.go("home.teacher");
//    }

}

module.exports = angular.module('app.views.app.controller', [
    "app.components.services.session_service"
])
.controller('HomeCtrl', Controller);
