/*
Description: Add, Get, Post questions to assignments
*/

function Service($http, appSettings, SessionService, Upload){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._Upload = Upload;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
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

Service.prototype.getQuestions = function(courseId, moduleId, pageNumber){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/student/course/'+ courseId
    +'/module/' + moduleId
    + '?page=' + pageNumber;
    self.config.cache = true;
    return this._$http
          .get(url, self.config)
          .then(function (res) {
            return res.data;
          });
};

Service.prototype.saveAnswers = function(courseId, moduleId, groupId, payload){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/save";
    return this._$http
        .post(url, payload, self.config)
        .then(function (res) {
            console.log("Save all the answers");
            console.log(res.data);
            return res.data;
        });
}

Service.prototype.getAnswers = function(courseId, moduleId, groupId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/?showSaved=true";
    return this._$http
        .get(url, self.config)
        .then(function (res) {
            console.log("Get all answers");
            console.log(res.data);
            return res.data;
        });
};

Service.prototype.uploadImage = function(file){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath
    + 'rest/student/upload';
    var data = {};
    data["file"] = file;
    return self._Upload.upload({url, data })
          .then(function (res) {
            console.log("Uploaded Image");
            console.log(res);
            return res.data;
          });
};

module.exports = angular.module('app.models.question', [
    'app.settings'
]).service('QuestionService', Service);
