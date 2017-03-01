/*
Description: Add, Get, Set, Delete Courses
*/

function Service($http, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
    this.user = SessionService.getUser();
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
    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/courses';
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/courses';
    url = self.getUrl(url);
    console.log(url);
    return this._$http
          .get(url, self.config)
          .then(function (res) {
            return res.data;
          });
}

Service.prototype.getCourse = function(courseId){
    var self = this;
    var url = {}
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/course/'+courseId;
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/course/'+courseId;
    url = self.getUrl(url);
    console.log(url);
    return self._$http
        .get(url, self.config)
        .then(function(res){
            return res.data;
    });
};

Service.prototype.getUrl = function(url){
    var self = this;
    if(self.user.roles.indexOf(self.userRoles.user) != -1){
        return url[self.userRoles.user]
    } else if (self.user.roles.indexOf(self.userRoles.instructor) != -1){
        return url[self.userRoles.instructor]
    }
}



module.exports = angular.module('app.models.course', [
    'app.settings'
]).service('CourseService', Service);




