
function Controller($scope, $state, SessionService, appSettings) {
    "ngInject";

    this._$scope = $scope;
    this._$state = $state;
    this.navigationLinks = [];
    this._appSettings = appSettings;
    this._SessionService = SessionService;
    this.init();

}

Controller.prototype.init = function(){
    var self = this;
    var roles = self._SessionService.getUser().roles;
    if (roles.indexOf("USER") != -1){
        var role = "USER";
        self.navigationLinks = self._appSettings[role]["mainNavigationLinks"];
    }
}


module.exports = angular.module('app.views.app.controller', [
    "app.components.services.session_service",
    "app.settings"
])
.controller('HomeCtrl', Controller);
