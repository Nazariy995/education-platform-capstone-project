var app = angular.module('hello');
app.factory('NavigationLinksService', function($http){
    var navigation_links_service = {};

    navigation_links_service.get = function(){
        return $http
              .get('./test/courses.json')
              .then(function (res) {
                return res.data;
              });
    };

    return navigation_links_service;
});
