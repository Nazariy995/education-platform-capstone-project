var appSettings = require("app_settings");
var sessionService = require("./session_service");

function AuthService($http, $window, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._$window = $window;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
}

AuthService.prototype.login = function(credentials){
    var self = this;
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return this._$http
              .get(this._appSettings.API.basePath+"/home", {headers : headers})
              .then(function (response) {
                    var user = {"name":"cool"};
                    var accessToken = response.headers("x-auth-token");
                    self._SessionService.create(user, accessToken);
                return user;
              });
}

AuthService.prototype.logout = function(){
    this._SessionService.destroy();
}

module.exports = angular.module('app.components.services.auth_service', [
    appSettings.name, 
    sessionService.name
])
.service('AuthService', AuthService);
