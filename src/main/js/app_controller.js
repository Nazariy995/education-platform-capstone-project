
function AppController($scope, $window, $rootScope, AuthService, SessionService){
    "ngInject";
    this.auth = AuthService;
    this.session = SessionService;
}

module.exports = AppController;
