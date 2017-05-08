/*
Description: Add, Get, Set, Delete Courses
*/

function Service($http, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
};

Service.prototype.getConfig = function(){
    var self = this;
    var config = {
        headers : {
            'Accept' : 'application/json'
        }
    };
    return config;
};

//Purpsoe: get courses that the person is associated with
Service.prototype.getCourses = function(){
    var self =this;
    var config = self.getConfig();

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/courses';
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/courses/?hideOpenSoon=false';
    url = self.getUrl(url);

    return this._$http
          .get(url, config)
          .then(function (res) {
            return res.data;
          });
};

//Get all the courses that the person is associated with
Service.prototype.getAllCourses = function(){
    var self =this;
    var config = self.getConfig();
    config.params = {};
    config.params.hideOpenSoon = false;
    config.params.hideClosed = false;

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/courses';
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/courses/';
    url = self.getUrl(url);

    return this._$http
          .get(url, config)
          .then(function (res) {
            return res.data;
          });
};

Service.prototype.getAllInstructorCourses = function(){
    var self =this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/instructor/courses/all';
    return this._$http
          .get(url, config)
          .then(function (res) {
            console.log("All possible Courses");
            console.log(res.data);
            return res.data;
          });
};

Service.prototype.getCourse = function(courseId){
    var self = this;
    var config = self.getConfig();

    var url = {}
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/course/'+courseId;
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/course/'+courseId;
    url = self.getUrl(url);

    return self._$http
        .get(url, config)
        .then(function(res){
            return res.data;
    });
};

Service.prototype.getCourseGrades = function(courseId){
    var self = this;
    var config = self.getConfig();

    var url = {}
    url[self.userRoles.user] = self._appSettings.API.basePath
        + '/rest/student/course/'
        + courseId + '/grades';
    url[self.userRoles.instructor] = self._appSettings.API.basePath
        + '/rest/instructor/course/'
        + courseId + '/grades';
    url = self.getUrl(url);

    return self._$http
        .get(url, config)
        .then(function(res){
            return res.data;
    });
};

Service.prototype.addCourse = function(payload){
    var self = this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath + '/rest/instructor/course';
    if("cloneCourseId" in payload){
        url += "?clone=" + payload["cloneCourseId"];
    }
    return self._$http
        .post(url, payload, config)
        .then(function(res){
            return res.data;
    });
};

//Purpose: delete a course
Service.prototype.dropCourse = function(courseId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'
    + courseId;
    return self._$http
        .delete(url, config)
        .then(function(res){
            return res.data;
    });
};

//Retrieve the correct API URL based on the user role
Service.prototype.getUrl = function(url){
    var self = this;
    var user = self._SessionService.getUser();
    if(user.roles.indexOf(self.userRoles.user) != -1){
        return url[self.userRoles.user]
    } else if (user.roles.indexOf(self.userRoles.instructor) != -1){
        return url[self.userRoles.instructor]
    }
}



module.exports = angular.module('app.models.course', [
    'app.settings'
]).service('CourseService', Service);




