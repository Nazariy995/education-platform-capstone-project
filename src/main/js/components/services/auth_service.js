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

    console.log(headers);
    return this._$http
              .get("/rest/self", {headers : headers})
              .then(function (response) {
                    console.log(response);
                    var user = response.data;
                    var accessToken = response.headers("x-auth-token");
                    user = self._SessionService.create(user, accessToken);
//                    self._$http.defaults.headers.common['X-Auth-Token'] = accessToken;
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
