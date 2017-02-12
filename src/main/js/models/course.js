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
          .get(this._appSettings.API.basePath + '/rest/student/courses/')
          .then(function (res) {
            return res.data;
          });
}

CourseService.prototype.getCourse = function(courseId){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+courseId;
    return self._$http
        .get(url)
        .then(function(res){
            return res.data;
    });
};



module.exports = angular.module('app.models.course', [
    'app.settings'
]).service('CourseService', CourseService);




