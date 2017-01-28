var app = angular.module('hello');
app.factory('UserService', function($http){
    var user_service = {};

    user_service.get = function(){
        return $http
              .get('./test/student.json')
              .then(function (res) {
                return res.data;
              });
    };

    return user_service;
});
