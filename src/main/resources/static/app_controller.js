
function AppController($scope, $rootScope, AuthService, SessionService){
    "ngInject";
    
    this._$rootScope = $rootScope;
    this._$rootScope.auth = AuthService;
    this._$rootScope.session = SessionService;
}

module.exports = AppController;
