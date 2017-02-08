/*
Description: Add, Get, Set, Delete Courses
*/

function CourseService($http, appSettings){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
}

CourseService.prototype.getCourses = function(){
    return this._$http
          .get(this._appSettings.API.basePath + '/rest/student/courses/',
               { cache: true }
              )
          .then(function (res) {
            return res.data;
          });
}

CourseService.prototype.getCourse = function(courseId){
    var self = this;
    return self._$http
        .get(this._appSettings.API.basePath + '/rest/student/course/'+courseId+'/self')
        .then(function(res){
            return res.data;
    });
}


module.exports = angular.module('app.models.course', [
    'app.settings'
]).service('CourseService', CourseService);




