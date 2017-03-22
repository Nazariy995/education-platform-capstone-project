var appSettings = require("app_settings");
var sessionService = require("./session_service");

function AuthService($http, $state, $window, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._$state = $state;
    this._$window = $window;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
}

AuthService.prototype.login = function(credentials){
    var self = this;
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return self._$http
              .get("/rest/self", {headers : headers})
              .then(function (response) {
                    var user = response.data;
                    var accessToken = response.headers("x-auth-token");
                    user = self._SessionService.create(user, accessToken);
                return user;
              });
};

AuthService.prototype.isAuthenticated = function(){
    var self = this;
    return self._$http
        .get("/rest/self")
        .then(function (response) {
            return response;
        });
};

AuthService.prototype.logout = function(params, config){
    var self = this;
    if(config == undefined){
        config = {};
    }
    if(params == undefined){
        params = {};
    }
    config.reload = true;
    self._SessionService.destroy();
    self._$state.go('app.login', params, config);
}

module.exports = angular.module('app.components.services.auth_service', [
    appSettings.name,
    sessionService.name
])
.service('AuthService', AuthService);
