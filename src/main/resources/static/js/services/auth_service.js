var app = angular.module('hello');
app.factory('AuthService', function($http){
    var auth_service = {};

    auth_service.login = function(credentials){
        var headers = credentials ? {authorization : "Basic "
                             + btoa(credentials.username + ":" + credentials.password)
                            } : {};
        
        return $http
              .get('/home', {headers : headers})
              .then(function (resposnse) {
                console.log(resposnse.headers("x-auth-token"));   
                return resposnse;
              });
    };

    return auth_service;
});
