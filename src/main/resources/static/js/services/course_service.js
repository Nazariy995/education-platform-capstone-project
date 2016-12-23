var app = angular.module('hello');
app.factory('CourseService', function($http){
    var course_service = {};

    course_service.get = function(){
        return $http
              .get('./test/courses.json')
              .then(function (res) {
                return res.data;
              });
    };

    return course_service;
});
