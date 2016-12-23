var app = angular.module('hello');
app.factory('AuthService', function($http){
    var auth_service = {};

    auth_service.login = function(credentials){
        return $http
              .get('./test/login.json', credentials)
              .then(function (res) {
                return res.data;
              });
    };

    return auth_service;
});
