/*
Description: Add, Get, Set, Delete Courses
*/

function Service($http, appSettings){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.init();
}

Service.prototype.init = function(){
    var self = this;
    self.getConfig();
};

Service.prototype.getConfig = function(){
    var self = this;
    self.config = {
        headers : {
            'Accept' : 'application/json'
        }
    };
};

Service.prototype.getCourses = function(){
    var self =this;
    var url = self._appSettings.API.basePath + '/rest/student/courses/';
    return this._$http
          .get(url, self.config)
          .then(function (res) {
            return res.data;
          });
}

Service.prototype.getCourse = function(courseId){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+courseId;
    return self._$http
        .get(url, self.config)
        .then(function(res){
            return res.data;
    });
};



module.exports = angular.module('app.models.course', [
    'app.settings'
]).service('CourseService', Service);




