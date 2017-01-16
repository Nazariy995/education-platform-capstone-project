
var appSettings = require('app_settings');

function AuthService($http, $window, appSettings){
    "ngInject";

    this._$http = $http;
    this._$window = $window;
    this._appSettings = appSettings;
}

AuthService.prototype.login = function(credentials){
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return this._$http
              .get(this._appSettings.API.basePath, {headers : headers})
              .then(function (headers) {
//                console.log(resposnse.headers("x-auth-token"));  
                return headers;
              });


}

module.exports = angular.module('app.components.services.auth_service', [appSettings])
    .service('AuthService', AuthService);
