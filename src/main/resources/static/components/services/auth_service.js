
function AuthService($http, $window){
    "ngInject";

    this._$http = $http;
    this._$window = $window;
}

AuthService.prototype.login = function(credentials){
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return this._$http
              .get('http://localhost:8080', {headers : headers})
              .then(function (headers) {
//                console.log(resposnse.headers("x-auth-token"));  
                return headers;
              });


}

module.exports = angular.module('app.components.services.auth_service', [])
    .service('AuthService', AuthService);
