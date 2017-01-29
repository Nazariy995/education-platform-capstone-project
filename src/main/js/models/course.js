/*
Description: Add, Get, Set, Delete Courses
*/

function CourseService($http, appSettings){
    "ngInject";

    this._$http = $http;

}

CourseService.prototype.getCourses = function(){
    return this._$http
          .get('test/courses.json')
          .then(function (res) {
            return res.data;
          });
}

module.exports = angular.module('app.models.course', [
    'app.settings'
])
    .service('CourseService', CourseService);




