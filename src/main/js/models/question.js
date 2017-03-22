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
};

Service.prototype.getConfig = function(){
    var self = this;
    var params = {};
    params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    var config = {
        params : params
    };

    return config
};

Service.prototype.getQuestions = function(courseId, moduleId, pageNumber){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/student/course/'+ courseId
    +'/module/' + moduleId
    + '?page=' + pageNumber;
    config.cache = true;
    return this._$http
          .get(url, config)
          .then(function (res) {
            return res.data;
          });
};

//Purpose: Add a new page to the assignment
//Params: courseId - String, moduleId - String
Service.prototype.addPage = function(courseId, moduleId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'+ courseId
    +'/module/' + moduleId
    + '/add-page';
    return this._$http
          .post(url, null, config)
          .then(function (res) {
            return res.data;
          });
};

Service.prototype.saveAnswers = function(courseId, moduleId, groupId, payload){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/save";
    return this._$http
        .post(url, payload, config)
        .then(function (res) {
            console.log("Save all the answers");
            console.log(res.data);
            return res.data;
        });
}

Service.prototype.getAnswers = function(courseId, moduleId, groupId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/?showSaved=true";
    return this._$http
        .get(url, config)
        .then(function (res) {
            console.log("Get all answers");
            console.log(res.data);
            return res.data;
        });
};

Service.prototype.uploadImage = function(file){
    var self = this;
    var config = self.getConfig();
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
