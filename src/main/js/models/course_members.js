/*
Description: Add, Get, Post course members
*/

function Service($http, appSettings, SessionService, Upload){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
    this._Upload = Upload;
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
    this.init();
}

Service.prototype.init = function(){
    var self = this;
    self.getConfig();
};

Service.prototype.getConfig = function(){
    var self = this;
    self.params = {};
    self.params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    self.config = {
        params : self.params
    };
};

Service.prototype.getCourseMembers = function(courseId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'+ courseId
    +'/users';
    return this._$http
          .get(url, self.config)
          .then(function (res) {
            console.log("Get Course Members");
            console.log(res);
            return res.data;
          });
};

Service.prototype.addCourseMembers = function(courseId, file){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'+ courseId
    +'/bulk-students';
    var data = {};
    data["file"] = file;
    return self._Upload.upload({ url: url, data : data })
          .then(function (res) {
            console.log("Uploaded Course Members");
            console.log(res);
            return res.data;
          });
};

Service.prototype.dropCourseMember = function(courseId, courseUserId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'+ courseId
    + '/student/' + courseUserId
    + '/drop';
    return this._$http
          .put(url, self.config)
          .then(function (res) {
            console.log("Drop Course Member");
            console.log(res);
            return res.data;
          });
};


module.exports = angular.module('app.models.course_members', [
    'app.settings'
]).service('CourseMembersService', Service);
